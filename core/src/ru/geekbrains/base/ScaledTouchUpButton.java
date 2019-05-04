package ru.geekbrains.base;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import ru.geekbrains.utils.Font;


public abstract class ScaledTouchUpButton
		extends Sprite
{

  private int pointer;
  private boolean pressed;
  protected String text;
  protected Font font;

  private static final float PRESS_SCALE = 0.9f;
  private static final Color PRESS_COLOR = new Color(0.8f, 0.1f, 0.8f, 1);


  public ScaledTouchUpButton(TextureRegion region, BaseScreen scr)
  {
	super(region, scr);
  }


  @Override
  public void draw(SpriteBatch batch)
  {
	super.draw(batch);

	float height = font.getXHeight();
	font.draw(batch, text, pos.x, pos.y + height / 2, Align.center);

  }


  @Override
  public boolean touchDown(Vector2 touch, int pointer)
  {
	if (pressed || !isMe(touch))
	  return false;

	this.pointer = pointer;
	scale = PRESS_SCALE;
	font.setColor(PRESS_COLOR);
	pressed = true;

	return false;
  }


  @Override
  public boolean touchUp(Vector2 touch, int pointer)
  {
	if (this.pointer != pointer || !pressed)
	  return false;

	if (isMe(touch))
	  action();

	pressed = false;
	scale = 1;

	return false;
  }


  protected abstract void action();

}