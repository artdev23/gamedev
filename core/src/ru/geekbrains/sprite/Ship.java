package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;


public class Ship
		extends Sprite
{

  private Vector2 v;
  private Vector2 destPos;
  private Vector2 buf;
  private Rect worldBounds;
  private Rect moveBounds;
  private float speed = 0.01f;

  private static final float height = 0.1f;


  public Ship(TextureAtlas atlas)
  {
	this(atlas.findRegion("ship"));
  }


  public Ship(TextureRegion region)
  {
	super(region);

	v = new Vector2();
	destPos = new Vector2();
	buf = new Vector2();

	setHeightProportion(height);
	setBottom(-0.4f);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	this.worldBounds = worldBounds;
	moveBounds = new Rect(worldBounds);
	moveBounds.setTop(0);

	setHeightProportion(height);
  }


  @Override
  public boolean touchDown(Vector2 touch, int pointer)
  {
	destPos.set(touch);
	v = touch.cpy()
			 .sub(pos)
			 .setLength(speed);

	return false;
  }


  @Override
  public void update(float delta)
  {
	buf.set(destPos);

	if (buf.sub(pos).len() < v.len())
	{
	  pos.set(destPos);
	}
	else
	{
	  pos.add(v);
	}

	if (!moveBounds.isMe(pos))
	{
	  if (pos.x > moveBounds.getRight()) pos.x = moveBounds.getRight();
	  if (pos.x < moveBounds.getLeft()) pos.x = moveBounds.getLeft();
	  if (pos.y > moveBounds.getTop()) pos.y = moveBounds.getTop();
	  if (pos.y < moveBounds.getBottom()) pos.y = moveBounds.getBottom();
	  v.setZero();
	}
  }

}