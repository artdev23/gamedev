package ru.geekbrains.base;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import ru.geekbrains.math.Rect;

import static ru.geekbrains.utils.Regions.split;


public class Sprite
		extends Rect
{

  private boolean isDestroyed;
  protected float angle;
  protected float scale = 1f;
  protected TextureRegion[] regions;
  protected int frame;
  protected BaseScreen screen;


  public Sprite()
  {
  }


  public Sprite(TextureRegion region)
  {
	regions = new TextureRegion[1];
	regions[0] = region;
  }


  public Sprite(TextureRegion region, int rows, int cols, int frames)
  {
	this.regions = split(region, rows, cols, frames);
  }


  public Sprite(TextureRegion region, BaseScreen scr)
  {
	this(region);

	screen = scr;
  }


  public void setHeightProportion(float height)
  {
	setHeight(height);
	float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
	setWidth(height * aspect);
  }


  public void setWidthProportion(float width)
  {
	setWidth(width);
	float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
	setHeight(width / aspect);
  }


  public void resize(Rect worldBounds)
  {

  }


  public void update(float delta)
  {

  }


  public void draw(SpriteBatch batch)
  {
	batch.draw(
			regions[frame],
			getLeft(), getBottom(),
			halfWidth, halfHeight,
			getWidth(), getHeight(),
			scale, scale,
			angle
	);
  }


  public boolean touchDown(Vector2 touch, int pointer)
  {
	return false;
  }


  public boolean touchUp(Vector2 touch, int pointer)
  {
	return false;
  }


  public float getAngle()
  {
	return angle;
  }


  public void setAngle(float angle)
  {
	this.angle = angle;
  }


  public float getScale()
  {
	return scale;
  }


  public void setScale(float scale)
  {
	this.scale = scale;
  }


  public void destroy()
  {
	isDestroyed = true;
  }


  public void flushDestroy()
  {
	isDestroyed = false;
  }


  public boolean isDestroyed()
  {
	return isDestroyed;
  }


  public void setAlignHoriz(int align)
  {
	switch (align)
	{
	  case Align.center:
		pos.x = screen.worldBounds.pos.x;
		break;
	  case Align.left:
		pos.x = screen.worldBounds.getLeft();
		break;
	  case Align.right:
		pos.x = screen.worldBounds.getRight();
		break;
	  default:
		break;
	}
  }

}