package ru.geekbrains.screen;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Ship;


public class GameScreen
		extends BaseScreen
{

  private Background background;
  private Ship ship;
  private Texture bg;
  private Texture img;


  @Override
  public void show()
  {
	super.show();

	bg = new Texture("background.jpg");
	background = new Background(new TextureRegion(bg));

	img = new Texture("ship.png");
	ship = new Ship(new TextureRegion(img));
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);
	background.resize(worldBounds);
	ship.resize(worldBounds);
  }


  @Override
  public void render(float delta)
  {
	super.render(delta);

	ship.update(delta);
	batch.begin();
	background.draw(batch);
	ship.draw(batch);
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
  public boolean touchDown(Vector2 touch, int pointer)
  {
	super.touchDown(touch, pointer);
	ship.touchDown(touch, pointer);
	return false;
  }

}