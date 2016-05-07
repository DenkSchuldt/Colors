package com.dennyschuldt.colors.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.dennyschuldt.colors.R;

/**
 * Created by denny on 5/6/16.
 */
public class PalettesDialogFragment extends DialogFragment {

  public static final String TAG = PalettesDialogFragment.class.getSimpleName();

  private View root;

  public static PalettesDialogFragment newInstance() {
    PalettesDialogFragment fragment = new PalettesDialogFragment();
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    root = inflater.inflate(R.layout.dialog_fragment_palettes, container, false);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    return root;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    PalettesListFragment fragment = PalettesListFragment.newInstance();
    getChildFragmentManager()
        .beginTransaction()
        .add(R.id.palettes_fragment_body, fragment, PalettesListFragment.TAG)
        .disallowAddToBackStack()
        .commit();
    super.onViewCreated(view, savedInstanceState);
  }
}
