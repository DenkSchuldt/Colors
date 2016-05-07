package com.dennyschuldt.colors.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.dennyschuldt.colors.R;
import com.dennyschuldt.colors.adapters.PalettesListAdapter;

import java.util.ArrayList;

/**
 * Created by denny on 5/6/16.
 */
public class PalettesListFragment extends Fragment {

  public static final String TAG = PalettesListFragment.class.getSimpleName();

  private View root;
  private ListView palettesList;
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
    root = inflater.inflate(R.layout.fragment_palettes_list, container, false);
    palettesList = (ListView) root.findViewById(R.id.palettes_fragment_list);
    return root;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    updateContent();
  }

  /**
   *
   */
  public void updateContent() {
    ArrayList<String> palettes = new ArrayList<>();
    palettes.add("Añadir nueva Paleta");
    PalettesListAdapter adapter = new PalettesListAdapter(getContext(), palettes);
    palettesList.setAdapter(adapter);
  }

}
