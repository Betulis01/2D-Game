package com.Betulis.Game2D.game.prefabs.attacks;

import com.Betulis.Game2D.engine.animation.AnimationAutoDespawner;
import com.Betulis.Game2D.engine.animation.AnimationClip;
import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.animation.AnimationUpdater;
import com.Betulis.Game2D.engine.render.RotatedSpriteRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.utils.SpriteSheetSlicer;
import com.badlogic.gdx.graphics.Texture;

public class FireballExplosion {
     public static GameObject create(GameObject owner, Texture asset) {
        GameObject effect = new GameObject("FireballExplosion");
        
        //Tranform
        Transform ot = owner.getTransform();
        effect.getTransform().setWorldPosition(ot.getWorldX(),ot.getWorldY());   
        
        //Animation
        SpriteSheetSlicer sheet = new SpriteSheetSlicer(asset, 16,16,3,1);
        AnimationClip explode = new AnimationClip(sheet, 0.08, 0, 0, 2, 0);

        AnimationDirector director = new AnimationDirector();
        director.add("explode", explode);
        director.play("explode",false);
        effect.addComponent(director);
        effect.addComponent(new AnimationUpdater());
        effect.addComponent(new RotatedSpriteRenderer(16,16));

        //Despawn
        effect.addComponent(new AnimationAutoDespawner());

        return effect;
    }
}
