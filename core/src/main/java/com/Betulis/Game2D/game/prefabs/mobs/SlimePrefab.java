package com.Betulis.Game2D.game.prefabs.mobs;

import com.Betulis.Game2D.engine.animation.AnimationClip;
import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.animation.AnimationUpdater;
import com.Betulis.Game2D.engine.render.SpriteRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.utils.SpriteSheetSlicer;
import com.Betulis.Game2D.game.components.animation.SlimeAnimation;
import com.Betulis.Game2D.game.components.movement.EntityMover;
import com.Betulis.Game2D.game.components.movement.SlimeMovement;
import com.badlogic.gdx.graphics.Texture;

public class SlimePrefab {

    public GameObject create(float x, float y, Texture asset) {
        GameObject slimeObj = new GameObject("Slime");
        
        //Transform
        slimeObj.getComponent(Transform.class).setPosition(x, y);

        //Movement
        slimeObj.addComponent(new SlimeMovement());
        slimeObj.addComponent(new EntityMover(50));
        
        //Animation
        SpriteSheetSlicer sheet = new SpriteSheetSlicer(asset, 16,16, 3, 8);
        AnimationDirector director = new AnimationDirector();
        AnimationClip jump = new AnimationClip(sheet, 0.15, 0, 0, 2, 0);
        AnimationClip idle = new AnimationClip(sheet, 0.15, 0, 0, 0, 0);
        director.add("jump", jump);
        director.add("idle", idle);
        slimeObj.addComponent(director);
        slimeObj.addComponent(new AnimationUpdater());
        slimeObj.addComponent(new SpriteRenderer(16,16));
        slimeObj.addComponent(new SlimeAnimation());

        return slimeObj;
    }
   
}
