package com.dennyschuldt.colors;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

  private LinearLayout mainColorPallete;
  private LinearLayout mainColorVariations;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mainColorPallete = (LinearLayout) findViewById(R.id.main_color_pallete);
    mainColorVariations = (LinearLayout) findViewById(R.id.main_color_variations);

    populateColorPallete();
  }

  /**
   *
   */
  public void populateColorPallete() {
    String[] colorPallete = getResources().getStringArray(R.array.color_pallete);
    for (int i=0; i<colorPallete.length; i++) {
      View item = getLayoutInflater().inflate(R.layout.item_color_pallete, mainColorPallete, false);
      item.setTag(colorPallete[i]);
      item.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          int color = Color.parseColor((String) v.getTag());
          getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Utils.darker(color, (float)0.8));
          }
          populateColorVariations(color);
        }
      });
      GradientDrawable drawable = (GradientDrawable) item.findViewById(R.id.item).getBackground();
      drawable.setColor(Color.parseColor(colorPallete[i]));
      mainColorPallete.addView(item);
    }
  }

  /**
   *
   * @param color
   */
  public void populateColorVariations(int color) {
    mainColorVariations.removeAllViews();
    double factor = 0.9;
    while (factor > 0.0) {
      int lighten = Utils.lighter(color, (float)factor);
      View view = inflateColorVariation(lighten);
      mainColorVariations.addView(view);
      factor -= 0.10;
    }
    factor = 0.0;
    while (factor > 0.0) {
      int darken = Utils.darker(color, (float) factor);
      View view = inflateColorVariation(darken);
      mainColorVariations.addView(view);
      factor -= 0.10;
    }
  }

  /**
   *
   * @param color
   * @return
   */
  public View inflateColorVariation(int color) {
    View view = getLayoutInflater().inflate(
        R.layout.item_color_variation,
        mainColorVariations, false);
    GradientDrawable drawable = (GradientDrawable) view.getBackground();
    drawable.setColor(color);

    String hex = Utils.getHex(color);
    TextView hexField = (TextView) view.findViewById(R.id.item_color_variation_hex);
    hexField.setText(hex);

    String rgb = Utils.getRgb(color);
    TextView rgbField = (TextView) view.findViewById(R.id.item_color_variation_rgb);
    rgbField.setText(rgb);

    return view;
  }

}
