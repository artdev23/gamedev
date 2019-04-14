package ru.geekbrains.base;


import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static java.lang.System.out;


public abstract class BaseScreen
		implements Screen, InputProcessor
{

  protected SpriteBatch batch;


  @Override
  public void show()
  {
	out.println("show");

	batch = new SpriteBatch();
	input.setInputProcessor(this);
  }


  @Override
  public void render(float delta)
  {
	gl.glClearColor(0, 0, 0, 1);
	gl.glClear(GL_COLOR_BUFFER_BIT);
  }


  @Override
  public void resize(int width, int height)
  {
	out.println("resize width = " + width + " height = " + height);
  }


  @Override
  public void pause()
  {
	out.println("pause");
  }


  @Override
  public void resume()
  {
	out.println("resume");
  }


  @Override
  public void hide()
  {
	out.println("hide");
	dispose();
  }


  @Override
  public void dispose()
  {
	out.println("dispose");
	batch.dispose();
  }


  @Override
  public boolean keyDown(int keycode)
  {
	out.println("keyDown keycode = " + keycode);
	return false;
  }


  @Override
  public boolean keyUp(int keycode)
  {
	out.println("keyUp keycode = " + keycode);
	return false;
  }


  @Override
  public boolean keyTyped(char character)
  {
	out.println("keyTyped character = " + character);
	return false;
  }


  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button)
  {
	out.println("touchDown screenX = " + screenX + " screenY = " + screenY);
	return false;
  }


  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button)
  {
	out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
	return false;
  }


  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer)
  {
	out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
	return false;
  }


  @Override
  public boolean mouseMoved(int screenX, int screenY)
  {
	return false;
  }


  @Override
  public boolean scrolled(int amount)
  {
	return false;
  }

}