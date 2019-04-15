package ru.geekbrains.math;


import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;


public class MatrixUtils
{

  private MatrixUtils()
  {
  }


  /**
   * Расчёт матрицы перехода 4x4
   */
  public static Matrix4 getTransitionMatrix4(Rect src, Rect dst)
  {
	Matrix4 mat = new Matrix4();

	float scaleX = dst.getWidth() / src.getWidth();
	float scaleY = dst.getHeight() / src.getHeight();

	return mat.translate(dst.pos.x, dst.pos.y, 0f)
			  .scale(scaleX, scaleY, 1f)
			  .translate(-src.pos.x, -src.pos.y, 0f);
  }


  /**
   * Расчёт матрицы перехода 3x3
   */
  public static Matrix3 getTransitionMatrix3(Rect src, Rect dst)
  {
	Matrix3 mat = new Matrix3();

	float scaleX = dst.getWidth() / src.getWidth();
	float scaleY = dst.getHeight() / src.getHeight();

	return mat.idt().translate(dst.pos.x, dst.pos.y)
			  .scale(scaleX, scaleY)
			  .translate(-src.pos.x, -src.pos.y);
  }

}