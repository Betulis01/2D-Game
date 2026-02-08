package com.Betulis.Game2D.engine.tiled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LibGDX Tiled (.tmx) loader that supports:
 * - <tileset> inline or TSX via source=""
 * - Grid tilesets (<tileset><image .../></tileset>) sliced into regions
 * - Image-collection tilesets (<tile><image .../></tile>)
 * - CSV layer data
 * - objectgroup with object properties
 *
 * Notes:
 * - Paths are resolved relative to the TMX/TSX file directory.
 * - Textures are owned by this loader; call dispose() when done.
 */
public class TiledMapLoader {

    private final XmlReader xml = new XmlReader();

    // Cache textures so multiple tiles referencing the same file don't reload.
    private final Map<String, Texture> textureCache = new HashMap<>();

    public TiledMap load(String tmxPath) {
        try {
            Element mapElem = parseXML(tmxPath);

            int width      = intAttr(mapElem, "width");
            int height     = intAttr(mapElem, "height");
            int tileWidth  = intAttr(mapElem, "tilewidth");
            int tileHeight = intAttr(mapElem, "tileheight");

            TiledMap map = new TiledMap(width, height, tileWidth, tileHeight);

            String baseDir = getBaseDir(tmxPath);

            // ---------------------------
            // Tilesets
            // ---------------------------
            for (int i = 0; i < mapElem.getChildCount(); i++) {
                Element child = mapElem.getChild(i);
                if ("tileset".equals(child.getName())) {
                    loadTileset(child, baseDir, map);
                }
            }

            // ---------------------------
            // Tile Layers
            // ---------------------------
            for (int i = 0; i < mapElem.getChildCount(); i++) {
                Element child = mapElem.getChild(i);
                if ("layer".equals(child.getName())) {
                    map.addTileLayer(parseTileLayer(child, width, height));
                }
            }

            // ---------------------------
            // Object Layers
            // ---------------------------
            for (int i = 0; i < mapElem.getChildCount(); i++) {
                Element child = mapElem.getChild(i);
                if ("objectgroup".equals(child.getName())) {
                    map.addObjectLayer(parseObjectLayer(child));
                }
            }

            return map;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load tiled map: " + tmxPath, e);
        }
    }

    /**
     * Dispose textures loaded by this loader.
     * Call this when the map is no longer needed (unless you transfer ownership elsewhere).
     */
    public void dispose() {
        for (Texture t : textureCache.values()) {
            t.dispose();
        }
        textureCache.clear();
    }

    // -------------------------------------------------------------------------
    // TILESET LOADING
    // -------------------------------------------------------------------------

    private void loadTileset(Element tsElem, String baseDir, TiledMap map) throws Exception {
        if (tsElem.hasAttribute("source")) {
            String tsxRel = tsElem.getAttribute("source");
            String tsxPath = normalizePath(baseDir + tsxRel);

            Element tsxRoot = parseXML(tsxPath);
            int firstgid = intAttr(tsElem, "firstgid");

            String tsxBaseDir = getBaseDir(tsxPath); // use TSX folder for images
            loadTilesetInternal(tsxRoot, tsxBaseDir, firstgid, map);
        } else {
            int firstgid = intAttr(tsElem, "firstgid");
            loadTilesetInternal(tsElem, baseDir, firstgid, map);
        }
    }

    private void loadTilesetInternal(Element tsElem, String baseDir, int firstgid, TiledMap map) {
        // Tile metadata (properties) keyed by tile "id"
        Map<Integer, Map<String, String>> metadata = parseTileProperties(tsElem);

        // --- CASE A: Grid tileset (tileset has a direct <image> child) ---
        Element directImage = tsElem.getChildByName("image");
        if (directImage != null) {
            String src = directImage.getAttribute("source");
            int imgWidth  = intAttr(directImage, "width");
            int imgHeight = intAttr(directImage, "height");

            Texture sheet = loadTexture(baseDir + src);

            int cols = imgWidth / map.tileWidth;
            int rows = imgHeight / map.tileHeight;

            int gid = firstgid;
            int tileId = 0;

            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    TextureRegion region = new TextureRegion(
                            sheet,
                            x * map.tileWidth,
                            y * map.tileHeight,
                            map.tileWidth,
                            map.tileHeight
                    );

                    Map<String, String> props = metadata.getOrDefault(tileId, Collections.emptyMap());
                    map.addTileData(new TileData(gid, region, props));

                    gid++;
                    tileId++;
                }
            }
            return;
        }

        // --- CASE B: Image Collection tileset (each <tile> has its own <image>) ---
        Array<Element> tileNodes = tsElem.getChildrenByName("tile");
        for (Element tileElem : tileNodes) {
            int id = intAttr(tileElem, "id");

            Element imgElem = tileElem.getChildByName("image");
            if (imgElem == null) continue;

            String src = imgElem.getAttribute("source");
            Texture tex = loadTexture(baseDir + src);

            // Whole texture as region (if you need sub-rects, read width/height or source rects here)
            TextureRegion region = new TextureRegion(tex);

            int gid = firstgid + id;
            Map<String, String> props = metadata.getOrDefault(id, Collections.emptyMap());
            map.addTileData(new TileData(gid, region, props));
        }
    }

    private Map<Integer, Map<String, String>> parseTileProperties(Element tsElem) {
        Map<Integer, Map<String, String>> out = new HashMap<>();

        Array<Element> tileNodes = tsElem.getChildrenByName("tile");
        for (Element tileElem : tileNodes) {
            int id = intAttr(tileElem, "id");

            Map<String, String> props = new HashMap<>();
            Element propsElem = tileElem.getChildByName("properties");
            if (propsElem != null) {
                Array<Element> propertyNodes = propsElem.getChildrenByName("property");
                for (Element prop : propertyNodes) {
                    String name = prop.getAttribute("name", "");
                    String value = prop.getAttribute("value", "");
                    // Tiled sometimes stores property value as text content if "value" attr is missing
                    if (value.isEmpty() && prop.getText() != null) value = prop.getText();
                    props.put(name, value);
                }
            }

            out.put(id, props);
        }

        return out;
    }

    // -------------------------------------------------------------------------
    // LAYERS
    // -------------------------------------------------------------------------

    private TileLayer parseTileLayer(Element layerElem, int width, int height) {
        String name = layerElem.getAttribute("name", "");

        float opacity = layerElem.hasAttribute("opacity")
                ? Float.parseFloat(layerElem.getAttribute("opacity"))
                : 1f;

        boolean visible = !layerElem.hasAttribute("visible")
                || !"0".equals(layerElem.getAttribute("visible"));

        Element dataElem = layerElem.getChildByName("data");
        int[] gids = parseCSV(dataElem, width * height);

        return new TileLayer(name, width, height, gids, opacity, visible);
    }

    private int[] parseCSV(Element dataElem, int expected) {
        if (dataElem == null) throw new RuntimeException("<layer> missing <data>");

        // You assumed CSV in your JavaFX version; keep it.
        String text = dataElem.getText().trim();
        String[] toks = text.split(",");

        int[] gids = new int[expected];
        int n = Math.min(toks.length, expected);

        for (int i = 0; i < n; i++) {
            String s = toks[i].trim();
            gids[i] = s.isEmpty() ? 0 : Integer.parseInt(s);
        }
        // If toks is shorter than expected, remaining will be 0.
        return gids;
    }

    // -------------------------------------------------------------------------
    // OBJECT LAYERS
    // -------------------------------------------------------------------------

    private ObjectLayer parseObjectLayer(Element layerElem) {
        String name = layerElem.getAttribute("name", "");

        List<MapObject> objects = new ArrayList<>();
        Array<Element> objectNodes = layerElem.getChildrenByName("object");

        for (Element o : objectNodes) {
            String objName = o.getAttribute("name", "");
            String type    = o.getAttribute("type", "");

            float x = floatAttr(o, "x", 0);
            float y = floatAttr(o, "y", 0);
            float w = floatAttr(o, "width", 0);
            float h = floatAttr(o, "height", 0);

            Integer gid = o.hasAttribute("gid") ? Integer.parseInt(o.getAttribute("gid")) : null;

            Map<String, String> props = parseObjectProperties(o);

            objects.add(new MapObject(objName, type, x, y, w, h, gid, props));
        }

        return new ObjectLayer(name, objects);
    }

    private Map<String, String> parseObjectProperties(Element obj) {
        Map<String, String> props = new HashMap<>();

        Element propsElem = obj.getChildByName("properties");
        if (propsElem == null) return props;

        Array<Element> p = propsElem.getChildrenByName("property");
        for (Element e : p) {
            String name = e.getAttribute("name", "");
            String value = e.getAttribute("value", "");
            if (value.isEmpty() && e.getText() != null) value = e.getText();
            props.put(name, value);
        }

        return props;
    }

    // -------------------------------------------------------------------------
    // UTIL
    // -------------------------------------------------------------------------

    private Element parseXML(String path) throws Exception {
        FileHandle fh = Gdx.files.internal(normalizePath(path));
        return xml.parse(fh);
    }

    private Texture loadTexture(String path) {
        String p = normalizePath(path);
        Texture cached = textureCache.get(p);
        if (cached != null) return cached;

        Texture t = new Texture(Gdx.files.internal(p));
        textureCache.put(p, t);
        return t;
    }

    private String getBaseDir(String path) {
        String p = normalizePath(path);
        int idx = p.lastIndexOf('/');
        return idx == -1 ? "" : p.substring(0, idx + 1);
    }

    private String normalizePath(String path) {
        // Convert backslashes, remove leading "./"
        String p = path.replace('\\', '/');
        while (p.startsWith("./")) p = p.substring(2);
        // Do NOT force leading "/" in LibGDX internal paths.
        if (p.startsWith("/")) p = p.substring(1);
        return p;
    }

    private int intAttr(Element e, String name) {
        return Integer.parseInt(e.getAttribute(name));
    }

    private float floatAttr(Element e, String name, float def) {
        if (!e.hasAttribute(name)) return def;
        return Float.parseFloat(e.getAttribute(name));
    }
}
