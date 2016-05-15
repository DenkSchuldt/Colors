package com.dennyschuldt.colors.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by denny on 5/14/16.
 */
public class ColorsSQLiteHelper extends SQLiteOpenHelper {

  private static final String DB_NAME = "colors.db";
  private static final int DB_VERSION = 1;


  public static final String PALETTES_TABLE = "palettes";
  public static final String COLUMN_PALETTE_NAME = "name";
  public static final String COLUMN_PALETTE_CREATED = "created";
  public static final String COLUMN_PALETTE_LAST_UPDATED = "lastUpdated";
  public static final String COLUMN_PALETTE_CREATED_LATITUDE = "latitude";
  public static final String COLUMN_PALETTE_CREATED_LONGITUDE = "longitude";

  private static final String CREATE_PALETTES_TABLE =
      "CREATE TABLE " + PALETTES_TABLE + "(" +
          BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          COLUMN_PALETTE_NAME + " TEXT," +
          COLUMN_PALETTE_CREATED + " TEXT," +
          COLUMN_PALETTE_LAST_UPDATED + " TEXT," +
          COLUMN_PALETTE_CREATED_LATITUDE + " TEXT," +
          COLUMN_PALETTE_CREATED_LONGITUDE + " TEXT)";


  public static final String COLORS_TABLE = "colors";
  public static final String COLUMN_COLOR_CODE = "code";
  public static final String COLUMN_COLOR_ADDED = "added";
  public static final String COLUMN_FOREIGN_KEY_PALETTE = "palette_id";

  private static final String CREATE_COLORS_TABLE =
      "CREATE TABLE " + COLORS_TABLE + "(" +
          BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          COLUMN_COLOR_CODE + " TEXT," +
          COLUMN_COLOR_ADDED + " TEXT, " +
          COLUMN_FOREIGN_KEY_PALETTE + " INTEGER, " +
          "FOREIGN KEY(" + COLUMN_FOREIGN_KEY_PALETTE + ") REFERENCES palettes(_ID))";


  public ColorsSQLiteHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_PALETTES_TABLE);
    db.execSQL(CREATE_COLORS_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
}
