package ru.geekbrains.sprite;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.utils.EnemyGenerator;


public class EnemyShip
		extends Ship
{

  private State state;

  private static final Vector2 descentV = new Vector2(0, -0.3f);


  public EnemyShip(Rect worldBounds, BulletPool bulletPool,
				   Sound shootSound, ExplosionPool explosionPool)
  {
	this.worldBounds = worldBounds;
	this.bulletPool = bulletPool;
	this.shootSound = shootSound;
	this.explosionPool = explosionPool;
  }


  public void set(TextureRegion bulletRegion, EnemyGenerator.EnemyParams par)
  {
	this.bulletRegion = bulletRegion;

	regions = new TextureRegion[]{par.Region};
	setHeightProportion(par.HEIGHT);
	bulletHeight = par.BULLET_HEIGHT;
	v0.set(par.V);
	bulletV.set(0, par.BULLET_VY);
	reloadInterval = par.RELOAD_INTERVAL;
	damage = par.DAMAGE;
	hp = par.HP;

	reloadTimer = reloadInterval;
	v.set(descentV);
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