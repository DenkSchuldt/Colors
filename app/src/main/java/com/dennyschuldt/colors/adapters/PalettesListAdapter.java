package com.dennyschuldt.colors.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dennyschuldt.colors.R;

import java.util.ArrayList;

/**
 * Created by denny on 5/6/16.
 */
public class PalettesListAdapter extends ArrayAdapter<String>{

  public PalettesListAdapter(Context context, ArrayList<String> palettes) {
    super(context, 0, palettes);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    String palette = getItem(position);
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pallete_option, parent, false);
    }
    TextView name = (TextView) convertView.findViewById(R.id.item_pallete_name);
    name.setText(palette);
    return convertView;
  }

}
