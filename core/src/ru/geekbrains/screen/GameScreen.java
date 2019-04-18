package ru.geekbrains.screen;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Ship;
import ru.geekbrains.sprite.Star;


public class GameScreen
		extends BaseScreen
{

  private Texture bg;
  private Background background;
  private TextureAtlas atlas;
  private Star starList[];
  private Texture txr;
  private TextureRegion reg;
  private Ship ship;

  private static final int starCount = 128;


  @Override
  public void show()
  {
	super.show();

	bg = new Texture("textures/background.png");
	background = new Background(new TextureRegion(bg));

	atlas = new TextureAtlas("textures/menuAtlas.tpack");

	starList = new Star[starCount];
	for (int i = 0; i < starList.length; i++)
	{
	  starList[i] = new Star(atlas);
	}

	txr = new Texture("textures/ship.png");
	reg = new TextureRegion(txr);
	ship = new Ship(reg);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);

	background.resize(worldBounds);
	ship.resize(worldBounds);

	for (Star star : starList)
	{
	  star.resize(worldBounds);
	}
  }


  @Override
  public void render(float delta)
  {
	super.render(delta);

	update(delta);
	draw(delta);
  }


  private void draw(float delta)
  {
	batch.begin();

	background.draw(batch);

	for (Star star : starList)
	{
	  star.draw(batch);
	}

	ship.draw(batch);

	batch.end();
  }


  private void update(float delta)
  {
	ship.update(delta);

	for (Star star : starList)
	{
	  star.update(delta);
	}
  }


  @Override
  public void dispose()
  {
	super.dispose();
	bg.dispose();
  }


  @Override
  public boolean touchDown(Vector2 touch, int pointer)
  {
	super.touchDown(touch, pointer);
	ship.touchDown(touch, pointer);

	return false;
  }

}