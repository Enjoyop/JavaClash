package com.xeon.javaclash.logic;

import org.json.JSONObject;
import org.json.JSONException;

public class DataSlot {
  private int _id;
  private int _count;

  public DataSlot(int id, int count) {
    this._id = id;
    this._count = count;
  }

  public void load() {
    JSONObject jsonObject = new JSONObject();
    try {
        this._id = jsonObject.getInt("id");
        this._count = jsonObject.getInt("cnt");
    } catch(JSONException JsonException) {
      JsonException.getLocalizedMessage();
    }
  }

  public void save(JSONObject jsonObject) {
    try {
      jsonObject.put("id", this._id);
      jsonObject.put("cnt", this._count);
    } catch(JSONException JsonException) {
      JsonException.getLocalizedMessage();
    }
  }

  public int getId() {
    return this._id;
  }

  public int getCount() {
    return this._count;
  }

  public void setCount(int count) {
    this._count = count;
  }
}
