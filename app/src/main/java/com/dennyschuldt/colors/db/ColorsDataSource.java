package com.dennyschuldt.colors.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.dennyschuldt.colors.models.Color;
import com.dennyschuldt.colors.models.Palette;

import java.util.ArrayList;

/**
 * Created by denny on 5/14/16.
 */
public class ColorsDataSource {

  private Context context;
  private ColorsSQLiteHelper colorsSQLiteHelper;

  public ColorsDataSource(Context context) {
    this.context = context;
    colorsSQLiteHelper = new ColorsSQLiteHelper(context);
  }

  /**
   *
   * @return
   */
  private SQLiteDatabase open() {
    return colorsSQLiteHelper.getWritableDatabase();
  }

  /**
   *
   * @param database
   */
  private void close(SQLiteDatabase database) {
    database.close();
  }

  /**
   *
   * @param cursor
   * @param columnName
   * @return
   */
  private int getIntFromColumnName(Cursor cursor, String columnName) {
    int columnIndex = cursor.getColumnIndex(columnName);
    return cursor.getInt(columnIndex);
  }

  /**
   *
   * @param cursor
   * @param columnName
   * @return
   */
  private String getStringFromColumnName(Cursor cursor, String columnName) {
    int columnIndex = cursor.getColumnIndex(columnName);
    return cursor.getString(columnIndex);
  }

  /**
   *
   * @param palette
   */
  public void createPalette(Palette palette) {
    SQLiteDatabase database = open();
    database.beginTransaction();
    ContentValues paletteValues = new ContentValues();
    paletteValues.put(colorsSQLiteHelper.COLUMN_PALETTE_NAME, palette.getName());
    paletteValues.put(colorsSQLiteHelper.COLUMN_PALETTE_CREATED, palette.getCreated().toString());
    paletteValues.put(colorsSQLiteHelper.COLUMN_PALETTE_LAST_UPDATED, palette.getLastUpdated().toString());
    paletteValues.put(colorsSQLiteHelper.COLUMN_PALETTE_CREATED_LATITUDE, palette.getLatitude());
    paletteValues.put(colorsSQLiteHelper.COLUMN_PALETTE_CREATED_LONGITUDE, palette.getLongitude());
    long paletteID = database.insert(colorsSQLiteHelper.PALETTES_TABLE, null, paletteValues);
    for (Color color : palette.getColors()) {
      ContentValues colorValues = new ContentValues();
      colorValues.put(colorsSQLiteHelper.COLUMN_COLOR_CODE, color.getCode());
      colorValues.put(colorsSQLiteHelper.COLUMN_COLOR_ADDED, color.getAdded().toString());
      colorValues.put(colorsSQLiteHelper.COLUMN_FOREIGN_KEY_PALETTE, paletteID);
      database.insert(colorsSQLiteHelper.COLORS_TABLE, null, colorValues);
    }
    database.setTransactionSuccessful();
    database.endTransaction();
    close(database);
  }

  /**
   *
   * @return
   */
  public ArrayList<Palette> getAllPalettes() {
    SQLiteDatabase database = open();
    Cursor cursor = database.query(
        ColorsSQLiteHelper.PALETTES_TABLE,
        new String[] {ColorsSQLiteHelper.COLUMN_PALETTE_NAME, BaseColumns._ID},
        null,  // Selection
        null,  // Selection args
        null,  // Group by
        null,  // Having
        null); // Order
    ArrayList<Palette> palettes = new ArrayList<>();
    if (cursor.moveToFirst()) {
      do {
        int id = getIntFromColumnName(cursor, BaseColumns._ID);
        String name = getStringFromColumnName(cursor, ColorsSQLiteHelper.COLUMN_PALETTE_NAME);
        Palette palette = new Palette(id, name);
        palettes.add(palette);
      } while (cursor.moveToNext());
    }
    cursor.close();
    database.close();
    return palettes;
  }

  /**
   *
   * @param colors
   */
  public void addPaletteColors(ArrayList<Color> colors, Palette palette) {
    SQLiteDatabase database = open();
    database.beginTransaction();
    for (Color color : colors) {
      ContentValues colorValues = new ContentValues();
      colorValues.put(colorsSQLiteHelper.COLUMN_COLOR_CODE, color.getCode());
      colorValues.put(colorsSQLiteHelper.COLUMN_COLOR_ADDED, color.getAdded().toString());
      colorValues.put(colorsSQLiteHelper.COLUMN_FOREIGN_KEY_PALETTE, palette.get_id());
      database.insert(colorsSQLiteHelper.COLORS_TABLE, null, colorValues);
    }
    database.endTransaction();
    database.close();
  }

}
