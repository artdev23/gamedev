package ru.geekbrains.utils;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import static com.badlogic.gdx.Gdx.files;
import static com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;


public class Font
		extends BitmapFont
{

  public Font(String fontFile, String imageFile)
  {
	super(files.internal(fontFile), files.internal(imageFile), false, false);

	Texture txr = getRegion().getTexture();
	txr.setFilter(Linear, Linear);
  }


  public void setFontSize(float size)
  {
	float scale = size / getCapHeight();
	BitmapFontData data = getData();

	data.setScale(scale);
  }


  public GlyphLayout draw(Batch batch, String str, float x, float y, int align)
  {
	return draw(batch, str, x, y, 0, align, false);
  }

}