package com.dennyschuldt.colors.fragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dennyschuldt.colors.R;
import com.dennyschuldt.colors.adapters.PalettesListAdapter;
import com.dennyschuldt.colors.db.ColorsDataSource;
import com.dennyschuldt.colors.models.Palette;

import java.util.ArrayList;

/**
 * Created by denny on 5/6/16.
 */
public class PalettesListFragment extends Fragment {

  public static final String TAG = PalettesListFragment.class.getSimpleName();

  private View root;
  private ListView palettesList;
  private ColorsDataSource dataSource;
  private PalettesDialogFragment palletesDialogFragment;

  public static PalettesListFragment newInstance() {
    PalettesListFragment fragment = new PalettesListFragment();
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    palletesDialogFragment = (PalettesDialogFragment) getParentFragment();
    super.onAttach(context);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    dataSource = new ColorsDataSource(getContext());
    root = inflater.inflate(R.layout.fragment_palettes_list, container, false);
    palettesList = (ListView) root.findViewById(R.id.palettes_fragment_list);
    return root;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    updateContent();
  }

  @Override
  public void onResume() {
    super.onResume();
    dataSource = new ColorsDataSource(getContext());
  }

  /**
   *
   */
  public void updateContent() {
    ArrayList<Palette> palettes = new ArrayList<>();
    palettes.add(new Palette(0, getString(R.string.new_palette)));
    palettes.addAll(dataSource.getAllPalettes());
    PalettesListAdapter adapter = new PalettesListAdapter(getContext(), palettes);
    palettesList.setAdapter(adapter);
    palettesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Palette palette = (Palette) view.getTag();
        System.out.println(palette);
        //palletesDialogFragment.newPaletteFragment();
      }
    });
  }

}
