package ru.geekbrains.base;


import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.StarGame;
import ru.geekbrains.math.Rect;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static java.lang.System.out;
import static ru.geekbrains.math.MatrixUtils.getTransitionMatrix3;
import static ru.geekbrains.math.MatrixUtils.getTransitionMatrix4;


public abstract class BaseScreen
		implements Screen, InputProcessor
{

  protected SpriteBatch batch;

  protected Rect worldBounds;
  private Rect screenBounds;
  private Rect glBounds;
  private Matrix4 worldToGl;
  private Matrix3 screenToWorld;
  private Matrix3 worldToScreen;
  private Vector2 touch;
  protected StarGame game;


  public BaseScreen(StarGame game)
  {
	this.game = game;

	batch = new SpriteBatch();

	worldBounds = new Rect();
	worldBounds.setHeight(1f);
	screenBounds = new Rect();
	glBounds = new Rect(0, 0, 1f, 1f);
	worldToGl = new Matrix4();
	screenToWorld = new Matrix3();
	touch = new Vector2();
  }


  @Override
  public void show()
  {
	out.println("show");

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
	screenBounds.setSize(width, height);
	screenBounds.setLeft(0);
	screenBounds.setBottom(0);

	float aspect = width / (float) height;
	worldBounds.setWidth(1f * aspect);
	worldToGl = getTransitionMatrix4(worldBounds, glBounds);
	batch.setProjectionMatrix(worldToGl);
	screenToWorld = getTransitionMatrix3(screenBounds, worldBounds);
	worldToScreen = getTransitionMatrix3(worldBounds, screenBounds);
	resize(worldBounds);
  }


  public void resize(Rect worldBounds)
  {
	;
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
//	dispose();
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
	touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
	touchDown(touch, pointer);
	return false;
  }


  public boolean touchDown(Vector2 touch, int pointer)
  {
	out.println("touchDown touchX = " + touch.x + " touchY = " + touch.y);
	return false;
  }


  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button)
  {
	out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
	touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
	touchUp(touch, pointer);
	return false;
  }


  public boolean touchUp(Vector2 touch, int pointer)
  {
	out.println("touchUp touchX = " + touch.x + " touchY = " + touch.y);
	return false;
  }


  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer)
  {
	out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
	touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
	touchDragged(touch, pointer);
	return false;
  }


  public boolean touchDragged(Vector2 touch, int pointer)
  {
	out.println("touchDragged touchX = " + touch.x + " touchY = " + touch.y);
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