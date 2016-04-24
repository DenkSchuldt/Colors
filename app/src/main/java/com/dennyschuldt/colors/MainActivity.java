package com.dennyschuldt.colors;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

  private LinearLayout mainColorPallete;
  private LinearLayout mainColorVariations;
  private LinearLayout mainColorVariationsEmpty;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mainColorPallete = (LinearLayout) findViewById(R.id.main_color_pallete);
    mainColorVariations = (LinearLayout) findViewById(R.id.main_color_variations);
    mainColorVariationsEmpty = (LinearLayout) findViewById(R.id.main_color_variations_empty);

    populateColorPallete();
    getSupportActionBar().setElevation(0);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_settings:
        Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settings);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
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
    mainColorVariationsEmpty.setVisibility(View.INVISIBLE);
    mainColorVariations.removeAllViewsInLayout();
    double factor = 0.9;
    while (factor > 0.0) {
      int lighten = Utils.lighter(color, (float)factor);
      View view = inflateColorVariation(lighten);
      mainColorVariations.addView(view);
      factor -= 0.10;
    }
    factor = 1.0;
    while (factor > 0.3) {
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

    int textColor = ContextCompat.getColor(MainActivity.this, R.color.color_black);
    if (Utils.colorIsDark(color)) {
      textColor = ContextCompat.getColor(MainActivity.this, R.color.color_white);
    }

    String hex = Utils.getHex(color);
    TextView hexField = (TextView) view.findViewById(R.id.item_color_variation_hex);
    hexField.setText(hex);
    hexField.setTextColor(textColor);

    String rgb = Utils.getRgb(color);
    TextView rgbField = (TextView) view.findViewById(R.id.item_color_variation_rgb);
    rgbField.setText(rgb);
    rgbField.setTextColor(textColor);

    return view;
  }

}
