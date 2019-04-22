package ru.geekbrains.sprite;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;


public class Ship
		extends Sprite
{

  private final Vector2 v = new Vector2();
  private Rect worldBounds;
  private Rect moveBounds;

  private final BulletPool bulletPool;
  private final TextureRegion bulletRegion;
  private final Vector2 bulletV = new Vector2(0f, 0.5f);

  private boolean pressedRight;
  private boolean pressedLeft;
  private int rightPointer = INVALID_POINTER;
  private int leftPointer = INVALID_POINTER;

  private final float reloadInterval = 0.1f;
  private float reloadTimer;


  private static final float HEIGHT = 0.1f;
  private static final int INVALID_POINTER = -1;
  private static final float BULLET_HEIGHT = 0.005f;


  public Ship(TextureRegion region, TextureRegion bulletRegion, BulletPool bulletPool)
  {
	super(region);

	this.bulletPool = bulletPool;
	this.bulletRegion = bulletRegion;
	setHeightProportion(HEIGHT);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);

	this.worldBounds = worldBounds;
	moveBounds = new Rect(worldBounds);
	moveBounds.setTop(0);

	setHeightProportion(HEIGHT);
	setBottom(worldBounds.getBottom() + 0.05f);
  }


  @Override
  public void update(float delta)
  {
	super.update(delta);

	pos.mulAdd(v, delta);

	reloadTimer += delta;
	if (reloadTimer >= reloadInterval)
	{
	  reloadTimer = 0f;
	  shoot();
	}

	if (getRight() > worldBounds.getRight())
	{
	  setRight(worldBounds.getRight());
	  stop();
	}

	if (getLeft() < worldBounds.getLeft())
	{
	  setLeft(worldBounds.getLeft());
	  stop();
	}
  }


  public boolean keyDown(int keycode)
  {
	switch (keycode)
	{
	  case Keys.A:
	  case Keys.LEFT:
		pressedLeft = true;
		moveLeft();
		break;

	  case Keys.D:
	  case Keys.RIGHT:
		pressedRight = true;
		moveRight();
		break;

//	  case Keys.UP:
//		shoot();
//		break;
	}

	return false;
  }


  public boolean keyUp(int keycode)
  {
	switch (keycode)
	{
	  case Keys.A:
	  case Keys.LEFT:
	  {
		pressedLeft = false;

		if (pressedRight)
		  moveRight();
		else
		  stop();

		break;
	  }

	  case Keys.D:
	  case Keys.RIGHT:
	  {
		pressedRight = false;

		if (pressedLeft)
		  moveLeft();
		else
		  stop();

		break;
	  }
	}

	return false;
  }


  @Override
  public boolean touchDown(Vector2 touch, int pointer)
  {
	if (touch.x < worldBounds.pos.x)
	{
	  if (leftPointer != INVALID_POINTER) return false;
	  leftPointer = pointer;
	  moveLeft();
	}
	else
	{
	  if (rightPointer != INVALID_POINTER) return false;
	  rightPointer = pointer;
	  moveRight();
	}

	return false;
  }


  @Override
  public boolean touchUp(Vector2 touch, int pointer)
  {
	if (pointer == leftPointer)
	{
	  leftPointer = INVALID_POINTER;

	  if (rightPointer != INVALID_POINTER)
		moveRight();
	  else
		stop();
	}
	else if (pointer == rightPointer)
	{
	  rightPointer = INVALID_POINTER;

	  if (leftPointer != INVALID_POINTER)
		moveLeft();
	  else
		stop();
	}

	return false;
  }


  private void shoot()
  {
	Bullet bullet = bulletPool.obtain();
	bullet.set(this, bulletRegion, pos, bulletV, BULLET_HEIGHT, worldBounds, 1);
  }


  private void moveRight()
  {
	v.set(0.5f, 0);
  }


  private void moveLeft()
  {
	v.set(-0.5f, 0);
  }


  private void stop()
  {
	v.setZero();
  }

}