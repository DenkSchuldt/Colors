package com.dennyschuldt.colors.models;

import java.util.Date;

/**
 * Created by denny on 5/14/16.
 */
public class Color {

  private String code;
  private Date added;

  public Color() {
    code = "";
    added = new Date();
  }

  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }

  public Date getAdded() { return added; }
  public void setAdded(Date added) { this.added = added; }

  @Override
  public String toString() {
    return "Color {" +
        "code: " + code + ",\n" +
        "added: " + added + "\n" +
        '}';
  }

}
