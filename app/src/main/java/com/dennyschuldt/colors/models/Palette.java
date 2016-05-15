package com.dennyschuldt.colors.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by denny on 5/14/16.
 */
public class Palette {

  private int _id;
  private String name;
  private Date created;
  private Date lastUpdated;
  private long latitude;
  private long longitude;
  private ArrayList<Color> colors;

  public Palette(){
    name = "";
    created = new Date();
    lastUpdated = new Date();
    latitude = 0;
    longitude = 0;
    colors = new ArrayList<>();
  }

  public Palette(int id, String _name){
    _id = id;
    name = _name;
    created = new Date();
    lastUpdated = new Date();
    latitude = 0;
    longitude = 0;
    colors = new ArrayList<>();
  }

  public int get_id() { return _id; }
  public void set_id(int _id) { this._id = _id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public Date getCreated() { return created; }
  public void setCreated(Date created) { this.created = created; }

  public Date getLastUpdated() { return lastUpdated; }
  public void setLastUpdated(Date lastUpdated) { this.lastUpdated = lastUpdated; }

  public long getLatitude() { return latitude; }
  public void setLatitude(long latitude) { this.latitude = latitude; }

  public long getLongitude() { return longitude; }
  public void setLongitude(long longitude) { this.longitude = longitude; }

  public ArrayList<Color> getColors() { return colors; }
  public void setColors(ArrayList<Color> colors) { this.colors = colors; }

  @Override
  public String toString() {
    return "Palette {" +
        "_id: " + _id + ",\n" +
        "name: " + name + ",\n" +
        "created: " + created + ",\n" +
        "lastUpdated: " + lastUpdated + ",\n" +
        "latitude: " + latitude + ",\n" +
        "longitude: " + longitude + ",\n" +
        "colors: " + colors.size() + "\n" +
        "}";
  }

}
