package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

import static ru.geekbrains.math.Rnd.nextFloat;


public class Star
		extends Sprite
{

  private Vector2 v;
  private Rect worldBounds;
  private float height;

  private static final float minheight = 0.0005f;
  private static final float maxheight = 0.005f;
  private static final float minspeed = 1;
  private static final float maxspeed = 10;


  public Star(TextureAtlas atlas)
  {
	super(atlas.findRegion("star"));

	height = nextFloat(minheight, maxheight);
	float vx = nextFloat(-minheight * 2, maxheight * 2);
	float vy = nextFloat(-maxspeed * height * 5, -minspeed * height * 10);
	v = new Vector2(vx, vy);

	setHeightProportion(height);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);

	this.worldBounds = worldBounds;

	float posX = nextFloat(worldBounds.getLeft(), worldBounds.getRight());
	float posY = nextFloat(worldBounds.getBottom(), worldBounds.getTop());
	pos.set(posX, posY);
  }


  @Override
  public void update(float delta)
  {
	super.update(delta);

	pos.mulAdd(v, delta);

	if (getRight() < worldBounds.getLeft())
	  setLeft(worldBounds.getRight());

	if (getLeft() > worldBounds.getRight())
	  setRight(worldBounds.getLeft());

	if (getTop() < worldBounds.getBottom())
	  setBottom(worldBounds.getTop());

	if (getBottom() > worldBounds.getTop())
	  setTop(worldBounds.getBottom());
  }

}