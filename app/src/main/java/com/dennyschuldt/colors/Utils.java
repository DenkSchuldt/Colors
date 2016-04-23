package com.dennyschuldt.colors;

import android.graphics.Color;

/**
 * Created by denny on 4/21/16.
 */
public class Utils {

  /**
   * @returns darker version of specified <code>color</code>.
   * @source http://stackoverflow.com/a/26554179
   */
  public static int darker (int color, float factor) {
    int a = Color.alpha(color);
    int r = Color.red( color );
    int g = Color.green( color );
    int b = Color.blue( color );
    return Color.argb(a,
        Math.max((int)(r * factor), 0),
        Math.max((int)(g * factor), 0),
        Math.max((int)(b * factor), 0));
  }

  /**
   * Lightens a color by a given factor.
   *
   * @param color   The color to lighten
   * @param factor  The factor to lighten the color. 0 will make
   *                the color unchanged. 1 will make the color white.
   * @return lighter version of the specified color.
   * @source http://stackoverflow.com/a/28058035
   */
  public static int lighter(int color, float factor) {
    int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
    int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
    int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
    return Color.argb(Color.alpha(color), red, green, blue);
  }

  /**
   *
   * @param color
   * @return
   */
  public static String getHex(int color) {
    return String.format("#%06X", (0xFFFFFF & color));
  }

  /**
   *
   * @param color
   * @return
   */
  public static String getRgb(int color) {
    int red = Color.red(color);
    int green = Color.green(color);
    int blue = Color.blue(color);
    return "rgb(" + red + ", " + green + ", " + blue + ")";
  }

}
