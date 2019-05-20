package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.screen.GameScreen;

import static com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888;


public class HealthBar
		extends Sprite
{

  private int hpFull;
  private int hpCur;
  private final Bar bar;

  private static final float HEIGHT = 0.04f;
  private static final Color BGCOLOR = new Color(0.4f, 0.4f, 0.4f, 0.4f);
  private static final Color FGCOLOR = new Color(0.1f, 0.8f, 0.1f, 1);


  public HealthBar(GameScreen screen, int hp)
  {
	this.screen = screen;
	hpFull = hp;
	hpCur = hp;

	Pixmap pixmap = new Pixmap(100, 10, RGBA8888);
	pixmap.setColor(BGCOLOR);
	pixmap.fill();

	TextureRegion region = new TextureRegion(new Texture(pixmap));
	regions = new TextureRegion[]{region};
	pixmap.dispose();

	setHeightProportion(HEIGHT);
	bar = new Bar();
  }


  public void update(float delta, int hp)
  {
	bar.setLeft(getLeft());
	bar.setTop(getTop());

	if (hpCur == hp) return;

	super.update(delta);

	float p = (float) hp / hpFull;
	bar.setWidth(bar.getWidth() * p);

	hpCur = hp;
  }


  @Override
  public void draw(SpriteBatch batch)
  {
	super.draw(batch);

	bar.draw(batch);
  }


  public void reset(int hp)
  {
	hpFull = hp;
	hpCur = hp;
	bar.setWidth(getWidth());
	bar.setLeft(getLeft());
	bar.setTop(getTop());
  }


  private class Bar
		  extends Sprite
  {

	Bar()
	{
	  Pixmap pixmap = new Pixmap(100, 10, RGBA8888);
	  pixmap.setColor(FGCOLOR);
	  pixmap.fill();

	  TextureRegion region = new TextureRegion(new Texture(pixmap));
	  regions = new TextureRegion[]{region};
	  pixmap.dispose();

	  setHeightProportion(HEIGHT);
	}

  }

}