package com.Betulis.Game2D.game.prefabs.mobs;

import com.Betulis.Game2D.engine.animation.AnimationClip;
import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.animation.AnimationUpdater;
import com.Betulis.Game2D.engine.config.ConfigLoader;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.render.SpriteRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.utils.SpriteSheetSlicer;
import com.Betulis.Game2D.game.components.animation.SlimeAnimation;
import com.Betulis.Game2D.game.components.combat.CombatState;
import com.Betulis.Game2D.game.components.AABB.Hurtbox;
import com.Betulis.Game2D.game.components.movement.EntityMover;
import com.Betulis.Game2D.game.components.movement.SlimeMovement;
import com.Betulis.Game2D.game.components.render.HealthRenderer;
import com.Betulis.Game2D.game.components.stats.Health;
import com.badlogic.gdx.graphics.Texture;

public class SlimePrefab {

    public GameObject create(float x, float y, Texture asset) {
        EntityConfig cfg = new ConfigLoader().load("data/config/slime.json");
        GameObject slimeObj = new GameObject("Slime");
        
        //Transform
        slimeObj.getComponent(Transform.class).setPosition(x, y);

        //Movement
        slimeObj.addComponent(new SlimeMovement());
        slimeObj.addComponent(new EntityMover(50));
        
        //Animation
        SpriteSheetSlicer sheet = new SpriteSheetSlicer(asset, cfg.sprite.width, cfg.sprite.height, 3, 8);
        AnimationDirector director = new AnimationDirector();
        AnimationClip jump = new AnimationClip(sheet, 0.15, 0, 0, 2, 0);
        AnimationClip idle = new AnimationClip(sheet, 0.15, 0, 0, 0, 0);
        director.add("jump", jump);
        director.add("idle", idle);
        slimeObj.addComponent(director);
        slimeObj.addComponent(new AnimationUpdater());
        slimeObj.addComponent(new SpriteRenderer(cfg.sprite.width, cfg.sprite.height));
        slimeObj.addComponent(new SlimeAnimation());

        //Health
        slimeObj.addComponent(new Health(cfg.stats.maxHealth,0));
        slimeObj.addComponent(new HealthRenderer());

        //Combat
        slimeObj.addComponent(new CombatState());
        
        //Hurtbox
        slimeObj.addComponent(new Hurtbox(cfg.hurtbox.width,cfg.hurtbox.height,cfg.hurtbox.offsetX, cfg.hurtbox.offsetY));


        return slimeObj;
    }
   
}
