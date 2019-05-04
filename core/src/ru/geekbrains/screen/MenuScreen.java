package ru.geekbrains.screen;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.ExitButton;
import ru.geekbrains.sprite.MenuBackground;
import ru.geekbrains.sprite.PlayButton;
import ru.geekbrains.utils.Font;

import static com.badlogic.gdx.utils.Align.center;


public class MenuScreen
		extends BaseScreen
{

  private MenuBackground background;
  private Font font;
  private String header;
  private Texture txrButton;
  private PlayButton buttonPlay;
  private ExitButton buttonExit;

  private static final float FontSize = 0.08f;
  private static final Color COLOR = new Color(0.8f, 0.1f, 0.8f, 1);


  public MenuScreen(StarGame game)
  {
	super(game);

	background = new MenuBackground();

	font = new Font("font/font.fnt", "font/font.png");
	font.setFontSize(FontSize);
	font.setColor(COLOR);

	header = game.getName()
				 .toUpperCase()
				 .replaceFirst(" ", "\n");

	txrButton = new Texture("textures/button.png");
	buttonPlay = new PlayButton(this, txrButton, game);
	buttonExit = new ExitButton(this, txrButton);
  }


  @Override
  public void show()
  {
	super.show();

	float y = worldBounds.getBottom() + 0.1f;
	buttonPlay.setBottom(y + buttonExit.getHeight() + 0.01f);
	buttonPlay.setAlignHoriz(center);
	buttonExit.setBottom(y);
	buttonExit.setAlignHoriz(center);
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

	batch.setColor(COLOR);
	background.draw(batch);
	float x = worldBounds.pos.x;
	float y = worldBounds.getTop() - 0.18f;
	font.draw(batch, header, x, y, center);
	buttonPlay.draw(batch);
	buttonExit.draw(batch);

	batch.end();
  }


  @Override
  public void dispose()
  {
	super.dispose();

	background.dispose();
	font.dispose();
	txrButton.dispose();
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