package com.Betulis.Game2D.game.prefabs.player;

import com.Betulis.Game2D.engine.animation.AnimationClip;
import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.animation.AnimationUpdater;
import com.Betulis.Game2D.engine.render.SpriteRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.utils.SpriteSheetSlicer;
import com.Betulis.Game2D.game.components.AABB.Hurtbox;
import com.Betulis.Game2D.game.components.animation.PlayerAnimation;
import com.Betulis.Game2D.game.components.combat.AttackSpawner;
import com.Betulis.Game2D.game.components.combat.CombatState;
import com.Betulis.Game2D.game.components.movement.EntityMover;
import com.Betulis.Game2D.game.components.movement.PlayerController;
import com.Betulis.Game2D.game.components.stats.Health;
import com.Betulis.Game2D.game.input.PlayerInput;
import com.badlogic.gdx.graphics.Texture;

public class PlayerPrefab {
    public GameObject create(float x, float y, Texture asset) {
        GameObject playerObj = new GameObject("Player");
        
        //Transform
        playerObj.getComponent(Transform.class).setPosition(x, y);

        //Movement
        playerObj.addComponent(new PlayerController());
        playerObj.addComponent(new EntityMover(100f));

        //Animation
        SpriteSheetSlicer sheet = new SpriteSheetSlicer(asset, 32, 32, 3,8);
        AnimationDirector director = new AnimationDirector();
        walkClips(sheet, director);
        idleClips(sheet, director);
        playerObj.addComponent(director);
        playerObj.addComponent(new AnimationUpdater());
        playerObj.addComponent(new SpriteRenderer(32, 32));
        playerObj.addComponent(new PlayerAnimation());

        //Collision

        //Health
        playerObj.addComponent(new Health(100, 0));
        
        //Combat
        playerObj.addComponent(new CombatState());

        //Hurtbox
        playerObj.addComponent(new Hurtbox(10,20,0,2));

        //Attack
        playerObj.addComponent(new AttackSpawner());
        playerObj.addComponent(new PlayerInput());


        return playerObj;
    }

    public void walkClips(SpriteSheetSlicer sheet, AnimationDirector director) {
        //walk
        AnimationClip walk_up = new AnimationClip(sheet, 0.2, 1, 0, 2, 0);
        AnimationClip walk_right = new AnimationClip(sheet, 0.2, 1, 2, 2, 2);
        AnimationClip walk_down = new AnimationClip(sheet, 0.2, 1, 4, 2, 4);
        AnimationClip walk_left = new AnimationClip(sheet, 0.2, 1, 6, 2, 6);
        
        AnimationClip walk_up_right = new AnimationClip(sheet, 0.2, 1, 1, 2, 1);
        AnimationClip walk_down_right = new AnimationClip(sheet, 0.2, 1, 3, 2, 3);
        AnimationClip walk_down_left = new AnimationClip(sheet, 0.2, 1, 5, 2, 5);
        AnimationClip walk_up_left = new AnimationClip(sheet, 0.2, 1, 7, 2, 7);
        
        director.add("walk_up", walk_up);
        director.add("walk_up_right", walk_up_right);
        director.add("walk_right", walk_right);
        director.add("walk_down_right", walk_down_right);
        director.add("walk_down", walk_down);
        director.add("walk_down_left", walk_down_left);
        director.add("walk_left", walk_left);
        director.add("walk_up_left", walk_up_left);
    }

    public void idleClips(SpriteSheetSlicer sheet, AnimationDirector director) {
        //idle
        AnimationClip idle_up = new AnimationClip(sheet, 0.3, 0, 0, 0, 0);
        AnimationClip idle_up_right = new AnimationClip(sheet, 0.3, 0, 1, 0, 1);
        AnimationClip idle_right = new AnimationClip(sheet, 0.3, 0, 2, 0, 2);
        AnimationClip idle_down_right = new AnimationClip(sheet, 0.3, 0, 3, 0, 3);
        AnimationClip idle_down = new AnimationClip(sheet, 0.3, 0, 4, 0, 4);
        AnimationClip idle_down_left = new AnimationClip(sheet, 0.3, 0, 5, 0, 5);
        AnimationClip idle_left = new AnimationClip(sheet, 0.3, 0, 6, 0, 6);
        AnimationClip idle_up_left = new AnimationClip(sheet, 0.3, 0, 7, 0, 7);
    

        director.add("idle_up", idle_up);
        director.add("idle_up_right", idle_up_right);
        director.add("idle_right", idle_right);
        director.add("idle_down_right", idle_down_right);
        director.add("idle_down", idle_down);
        director.add("idle_down_left", idle_down_left);
        director.add("idle_left", idle_left);
        director.add("idle_up_left", idle_up_left);
    }
}
