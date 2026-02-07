package com.Betulis.Game2D.engine.config;

public record EntityConfig(String id, String name, Sprite sprite, Collision collision, Hurtbox hurtbox, Hitbox hitbox, Stats stats) {
  public record Sprite(String sheet, int width, int height, int frames, int directions) {}
  public record Collision(int width, int height, float offsetX, float offsetY) {}
  public record Hurtbox(int width, int height, float offsetX, float offsetY) {}
  public record Hitbox(int width, int height, float offsetX, float offsetY) {}
  public record Stats(float moveSpeed,float maxHealth, float attack, float defense,float attackSpeed, float critChance, float damage, float cooldown,float duration) {}
}
