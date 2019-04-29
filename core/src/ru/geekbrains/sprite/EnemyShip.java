package ru.geekbrains.sprite;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;


public class EnemyShip
		extends Ship
{

  private State state;
  private Vector2 descentV;
  private MainShip mainShip;


  public EnemyShip(MainShip mainShip, Rect worldBounds, BulletPool bulletPool,
				   Sound shootSound, ExplosionPool explosionPool)
  {
	this.mainShip = mainShip;
	this.worldBounds = worldBounds;
	this.bulletPool = bulletPool;
	this.shootSound = shootSound;
	this.explosionPool = explosionPool;
	descentV = new Vector2(0, -0.3f);
	angle = -90;
  }


  public void set(TextureRegion[] regions, Vector2 v0, TextureRegion bulletRegion,
				  float bulletHeight, float bulletVY, int damage,
				  float reloadInterval, float height, int hp)
  {
	this.regions = regions;
	this.v0.set(v0);
	this.bulletRegion = bulletRegion;
	this.bulletHeight = bulletHeight;
	this.bulletV.set(0, bulletVY);
	this.damage = damage;
	this.reloadInterval = reloadInterval;
	setHeightProportion(height);
	this.hp = hp;
	v.set(descentV);
	reloadTimer = reloadInterval;
	state = State.DESCENT;
  }


  @Override
  public void update(float delta)
  {
	super.update(delta);

	if (getTop() <= worldBounds.getTop())
	{
	  state = State.FIGHT;
	  v.set(v0);
	}

	if (state == State.FIGHT)
	{
	  reloadTimer += delta;
	  if (reloadTimer >= reloadInterval)
	  {
		reloadTimer = 0;
		shoot();
	  }
	}

	if (isOutside(worldBounds))
	  destroy();
  }


  private enum State
  {
	DESCENT,
	FIGHT
  }

}