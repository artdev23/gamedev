package ru.geekbrains;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;


public class StarGame
		extends ApplicationAdapter
{

  private SpriteBatch batch;
  private Texture img;


  @Override
  public void create()
  {
	batch = new SpriteBatch();
	img = new Texture("badlogic.jpg");
  }


  @Override
  public void render()
  {
	gl.glClearColor(0, 0, 0, 1);
	gl.glClear(GL_COLOR_BUFFER_BIT);

	batch.begin();

	batch.disableBlending();
	batch.draw(img, 0, 0);
	batch.enableBlending();

	batch.end();
  }


  @Override
  public void dispose()
  {
	batch.dispose();
	img.dispose();
  }

}