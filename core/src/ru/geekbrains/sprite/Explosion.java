package ru.geekbrains.sprite;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;


public class Explosion
		extends Sprite
{

  private Sound explosionSound;

  private float animateInterval = 0.017f;
  private float animateTimer;


  public Explosion(TextureAtlas atlas, Sound explosionSound)
  {
	super(atlas.findRegion("explosion"), 9, 9, 74);
	this.explosionSound = explosionSound;
  }


  public void set(float height, Vector2 pos)
  {
	setHeightProportion(height);
	this.pos.set(pos);

	long id = explosionSound.play();
	explosionSound.setVolume(id, 0.1f);
  }


  @Override
  public void update(float delta)
  {
	animateTimer += delta;

	if (animateTimer >= animateInterval)
	{
	  animateTimer = 0;

	  if (++frame == regions.length)
		destroy();
	}
  }


  @Override
  public void destroy()
  {
	super.destroy();
	frame = 0;
  }

}