package ru.geekbrains.screen;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ExitButton;
import ru.geekbrains.sprite.PlayButton;


public class MenuScreen
		extends BaseScreen
{

  private Game game;

  private Texture bg;
  private Background background;
  private TextureAtlas atlas;

  private ExitButton buttonExit;
  private PlayButton buttonPlay;


  public MenuScreen(Game game)
  {
	this.game = game;
  }


  @Override
  public void show()
  {
	super.show();

	bg = new Texture("textures/menuBg.jpg");
	background = new Background(new TextureRegion(bg));

	atlas = new TextureAtlas("textures/menuAtlas.tpack");

	buttonExit = new ExitButton(atlas);
	buttonPlay = new PlayButton(atlas, game);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);

	background.resize(worldBounds);
	buttonExit.resize(worldBounds);
	buttonPlay.resize(worldBounds);
  }


  @Override
  public void render(float delta)
  {
	super.render(delta);
	draw();
  }


  private void draw()
  {
	batch.begin();

	batch.setColor(0.8f, 0.1f, 0.8f, 1);
	background.draw(batch);
	buttonExit.draw(batch);
	buttonPlay.draw(batch);

	batch.end();
  }


  @Override
  public void dispose()
  {
	super.dispose();
	bg.dispose();
	atlas.dispose();
  }


  @Override
  public boolean touchDown(Vector2 touch, int pointer)
  {
	buttonExit.touchDown(touch, pointer);
	buttonPlay.touchDown(touch, pointer);

	return false;
  }


  @Override
  public boolean touchUp(Vector2 touch, int pointer)
  {
	buttonExit.touchUp(touch, pointer);
	buttonPlay.touchUp(touch, pointer);

	return false;
  }

}