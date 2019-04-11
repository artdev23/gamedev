package ru.geekbrains.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;
import static java.lang.System.out;


public class GameScreen
		extends BaseScreen
{

  private Vector2 touch;
  private Vector2 pos, v, maxpos;
  private Texture bg;
  private TextureRegion region;
  private Texture img;
  private int imgWidth, imgHeight;
  private boolean isTouchControl;
  private boolean isKeyPressed;
  private int keyPressed;

  private static final Color bgColor = new Color(0.8f, 0, 0.8f, 1);
  private static final int WIDTH = Gdx.graphics.getWidth();
  private static final int HEIGHT = Gdx.graphics.getHeight();


  @Override
  public void show()
  {
	super.show();

	touch = new Vector2();
	pos = new Vector2(0, 0);
	v = new Vector2();

	bg = new Texture("background.jpg");
	region = new TextureRegion(bg);

	img = new Texture("ship.png");
	imgWidth = img.getWidth() / 2;
	imgHeight = img.getHeight() / 2;

	maxpos = new Vector2(WIDTH - imgWidth, HEIGHT - imgHeight);

	isTouchControl = false;
	isKeyPressed = false;
  }


  @Override
  public void render(float delta)
  {
	super.render(delta);

	pos.add(v);

	if (isTouchControl)
	{
	  if (pos.cpy().sub(touch).len() < v.len())
	  {
		pos = touch.cpy();
		v.setZero();
	  }
	}

	if (pos.x > maxpos.x || pos.y > maxpos.y || pos.x < 0 || pos.y < 0)
	{
	  if (pos.x > maxpos.x) pos.x = maxpos.x;
	  if (pos.y > maxpos.y) pos.y = maxpos.y;
	  if (pos.x < 0) pos.x = 0;
	  if (pos.y < 0) pos.y = 0;
	  v.setZero();
	}

	batch.begin();

	batch.disableBlending();
	batch.setColor(bgColor);
	batch.draw(region, 0, 0, WIDTH, HEIGHT);
	batch.enableBlending();
	batch.draw(img, pos.x, pos.y, imgWidth, imgHeight);

	batch.end();
  }


  @Override
  public void dispose()
  {
	super.dispose();
	bg.dispose();
	img.dispose();
  }


  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button)
  {
	isTouchControl = true;

	touch.set(screenX, HEIGHT - screenY);
	out.println("touchDown at point " + touch);

	v = touch.cpy().sub(pos).nor();

	return false;
  }


  @Override
  public boolean keyDown(int keycode)
  {
	if (isTouchControl || isKeyPressed)
	  return false;

	switch (keycode)
	{
	  case UP:
		v.set(0, 1);
		break;

	  case DOWN:
		v.set(0, -1);
		break;

	  case LEFT:
		v.set(-1, 0);
		break;

	  case RIGHT:
		v.set(1, 0);
		break;

	  default:
		return false;
	}

	isTouchControl = false;
	isKeyPressed = true;
	keyPressed = keycode;

	return false;
  }


  @Override
  public boolean keyUp(int keycode)
  {
	if (isTouchControl)
	  return false;

	if (keycode == keyPressed)
	{
	  v.setZero();
	  isKeyPressed = false;
	}

	return false;
  }

}