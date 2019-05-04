package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.audio.BackgroundMusic;
import ru.geekbrains.base.Background;
import ru.geekbrains.math.Rect;


public class GameBackground
		extends Background
{

  private Star[] stars;

  private static Texture img = new Texture("textures/background.png");
  private static BackgroundMusic music = new BackgroundMusic("audio/music.mp3", 0.2f);
  private static final int starCount = 128;


  public GameBackground(TextureAtlas atlas)
  {
	super(img);

	stars = new Star[starCount];
	for (int i = 0; i < starCount; i++)
	  stars[i] = new Star(atlas);
  }


  @Override
  public void resize(Rect worldBounds)
  {
	super.resize(worldBounds);

	for (Star s : stars)
	{
	  s.resize(worldBounds);
	}
  }


  @Override
  public void update(float delta)
  {
	for (Star s : stars)
	{
	  s.update(delta);
	}

	if (!music.isPlaying())
	  music.play();
  }


  @Override
  public void draw(SpriteBatch batch)
  {
	super.draw(batch);

	for (Star s : stars)
	{
	  s.draw(batch);
	}
  }


  @Override
  public void dispose()
  {
	img.dispose();
	music.dispose();
  }


}