package com.dennyschuldt.colors.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dennyschuldt.colors.R;
import com.dennyschuldt.colors.utils.Constants;
import com.dennyschuldt.colors.utils.Utils;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private ArrayList colors;
  private Animation fadeIn;
  private Animation fadeOut;
  private FloatingActionButton mainFab;
  private FloatingActionButton mainFabWallpaperIcon;
  private RelativeLayout mainFabWallpaper;
  private LinearLayout mainColorPallete;
  private LinearLayout mainColorVariations;
  private LinearLayout mainColorVariationsEmpty;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    colors = new ArrayList();
    mainFab = (FloatingActionButton) findViewById(R.id.main_fab);
    mainFabWallpaper = (RelativeLayout) findViewById(R.id.main_fab_wallpaper);
    mainFabWallpaperIcon = (FloatingActionButton) findViewById(R.id.main_fab_wallpaper_icon);
    mainColorPallete = (LinearLayout) findViewById(R.id.main_color_pallete);
    mainColorVariations = (LinearLayout) findViewById(R.id.main_color_variations);
    mainColorVariationsEmpty = (LinearLayout) findViewById(R.id.main_color_variations_empty);

    fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
    fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

    populateColorPalette();
    getSupportActionBar().setElevation(0);

    SharedPreferences preferences = getApplicationContext().getSharedPreferences(
        getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
    int color = preferences.getInt(Constants.SELECTED_COLOR, 0);
    if (color != 0) {
      selectMainColor(color);
    }

    mainFab.setOnClickListener(this);
    mainFabWallpaper.setOnClickListener(this);

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

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.main_fab:
        if (mainFab.isSelected()) {
          hideOptions();
        } else {
          revealOptions();
        }
        mainFab.setSelected(!mainFab.isSelected());
        break;
      case R.id.main_fab_wallpaper:
        hideOptions();
        mainFab.setSelected(!mainFab.isSelected());
        if (Utils.setWallpaper(Integer.valueOf((String)colors.get(0)), MainActivity.this)) {
          Snackbar.make(findViewById(R.id.main_layout),
              "Color #" + Utils.getHex(Integer.valueOf((String) colors.get(0))) + " set as Wallpaper", Snackbar.LENGTH_LONG).show();
        }
        break;
    }
  }

  /**
   *
   */
  public void populateColorPalette() {
    String[] colorPallete = getResources().getStringArray(R.array.color_pallete);
    for (int i=0; i<colorPallete.length; i++) {
      View item = getLayoutInflater().inflate(R.layout.item_color_palette, mainColorPallete, false);
      item.setTag(colorPallete[i]);
      item.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          int color = Color.parseColor((String) v.getTag());
          selectMainColor(color);
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
      int lighten = Utils.lighter(color, (float) factor);
      View view = inflateColorVariation(lighten);
      mainColorVariations.addView(view);
      factor -= 0.10;
    }
    factor = 0.9;
    while (factor > 0.3) {
      int darken = Utils.darker(color, (float) factor);
      View view = inflateColorVariation(darken);
      mainColorVariations.addView(view);
      factor -= 0.10;
    }
    addVariationSpace();
  }

  /**
   *
   * @param color
   */
  public void selectMainColor(int color) {
    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(Utils.darker(color, (float) 0.8));
    }
    populateColorVariations(color);
    mainFab.setBackgroundTintList(ColorStateList.valueOf(color));
    SharedPreferences preferences = getApplicationContext().getSharedPreferences(
        getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt(Constants.SELECTED_COLOR, color);
    editor.apply();
  }

  /**
   *
   * @param color
   * @return
   */
  public View inflateColorVariation(final int color) {
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
    hexField.setText("#"+hex);
    hexField.setTextColor(textColor);

    String rgb = Utils.getRgb(color);
    TextView rgbField = (TextView) view.findViewById(R.id.item_color_variation_rgb);
    rgbField.setText(rgb);
    rgbField.setTextColor(textColor);

    ImageView check = (ImageView) view.findViewById(R.id.item_color_variation_check);
    check.setColorFilter(textColor);
    if (colors.contains(hex)) {
      view.setSelected(true);
      check.setAlpha(1f);
    }
    view.setTag(color);

    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int color = (int) v.getTag();
        boolean isSelected = v.isSelected();
        ImageView check = (ImageView) v.findViewById(R.id.item_color_variation_check);
        if (isSelected) {
          check.setAlpha(0.15f);
          colors.remove(String.valueOf(color));
        } else {
          check.setAlpha(1f);
          colors.add(String.valueOf(color));
        }
        checkFab();
        v.setSelected(!isSelected);
      }
    });

    /*
    view.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        PalettesDialogFragment dialogFragment = PalettesDialogFragment.newInstance(color);
        dialogFragment.show(getSupportFragmentManager(), PalettesDialogFragment.TAG);
        return false;
      }
    });*/

    return view;
  }

  public void addVariationSpace() {
    View space = getLayoutInflater().inflate(
        R.layout.item_color_variation_space,
        mainColorVariations, false);
    mainColorVariations.addView(space);
  }

  /**
   *
   */
  private void revealOptions() {
    mainFabWallpaper.startAnimation(fadeIn);
    mainFabWallpaper.setVisibility(View.VISIBLE);
    ObjectAnimator rotate = ObjectAnimator.ofFloat(mainFab, "rotation", 0.0f, -45f);
    rotate.setDuration(150);
    rotate.start();
    int wallpaper = Integer.valueOf((String) colors.get(0));
    mainFabWallpaperIcon.setBackgroundTintList(ColorStateList.valueOf(wallpaper));
  }

  /**
   *
   */
  private void hideOptions() {
    mainFabWallpaper.startAnimation(fadeOut);
    mainFabWallpaper.setVisibility(View.GONE);
    ObjectAnimator rotate = ObjectAnimator.ofFloat(mainFab, "rotation", -45.0f, -0.0f);
    rotate.setDuration(150);
    rotate.start();
  }

  /**
   *
   */
  public void checkFab() {
    if (colors.isEmpty()) {
      mainFab.hide();
    } else {
      mainFab.show();
    }
  }
}
