package com.Betulis.Game2D.game.prefabs.attacks;

import com.Betulis.Game2D.engine.animation.AnimationClip;
import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.animation.AnimationUpdater;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.math.Vector2;
import com.Betulis.Game2D.engine.render.RotatedSpriteRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.utils.SpriteSheetSlicer;
import com.Betulis.Game2D.game.components.AABB.Hitbox;
import com.Betulis.Game2D.game.components.combat.DamageOnHit;
import com.Betulis.Game2D.game.components.movement.EntityMover;
import com.Betulis.Game2D.game.components.movement.Projectile;
import com.badlogic.gdx.graphics.Texture;

public final class AttackPrefabs {

    public static GameObject createFireball(EntityConfig cfg, GameObject owner,Vector2 dir, Texture asset) {
        GameObject attack = new GameObject("Fireball");
        
        //Tranform
        Transform ot = owner.getTransform();
        attack.getTransform().setWorldPosition(ot.getWorldX(),ot.getWorldY());   
        float angle =(float) Math.toDegrees(Math.atan2(dir.y, dir.x));
        attack.getTransform().setRotation(angle);

        //Animation
        SpriteSheetSlicer sheet = new SpriteSheetSlicer(asset, cfg.sprite().width(), cfg.sprite().height(), 8,1);
        AnimationClip fly = new AnimationClip(sheet, 0.2, 0, 0, 7, 0);
      
        AnimationDirector director = new AnimationDirector();
        director.add("fly", fly);
        attack.addComponent(director);
        attack.addComponent(new AnimationUpdater());
        attack.addComponent(new RotatedSpriteRenderer(16,16));

        //Despawner
        //attack.addComponent(new AttackOutsideMapDespawner());
        //attack.addComponent(new AttackDurationDespawner(cfg.stats().duration()));

        //Movement
        attack.addComponent(new Projectile(dir));
        attack.addComponent(new EntityMover(500));

        //Hitbox
        EntityConfig.Hitbox hi = cfg.hitbox();
        attack.addComponent(new Hitbox(hi.width(),hi.height(),hi.offsetX(),hi.offsetY()));
        
        //Damage
        attack.addComponent(new DamageOnHit(owner, cfg.stats().damage()));

        return attack;
    }

    public static GameObject createLightningBolt(EntityConfig cfg, GameObject owner,Vector2 dir, Texture asset) {
        GameObject attack = new GameObject("LightningBolt");

        //Transform
        Transform ot = owner.getTransform();
        attack.getTransform().setWorldPosition(ot.getWorldX(),ot.getWorldY());   
        float angle = (float) Math.toDegrees(Math.atan2(dir.y, dir.x));
        attack.getTransform().setRotation(angle);

        //Animation
        SpriteSheetSlicer sheet = new SpriteSheetSlicer(asset, cfg.sprite().width(), cfg.sprite().height(), 8,1);
        AnimationClip fly = new AnimationClip(sheet, 0.2, 0, 0, 7, 0);
        AnimationDirector director = new AnimationDirector();
        director.add("fly", fly);
        attack.addComponent(director);
        attack.addComponent(new AnimationUpdater());
        attack.addComponent(new RotatedSpriteRenderer(16,16));

        //Despawner
        //attack.addComponent(new AttackOutsideMapDespawner());
        //attack.addComponent(new AttackDurationDespawner(cfg.stats().duration()));

        //Movement
        attack.addComponent(new Projectile(dir));
        attack.addComponent(new EntityMover(100));

        //Hitbox
        EntityConfig.Hitbox hi = cfg.hitbox();
        attack.addComponent(new Hitbox(hi.width(),hi.height(),hi.offsetX(),hi.offsetY()));
        
        //Damage
        attack.addComponent(new DamageOnHit(owner, cfg.stats().damage()));

        return attack;
    }
}
