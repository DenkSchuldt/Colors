package com.dennyschuldt.colors.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dennyschuldt.colors.R;
import com.dennyschuldt.colors.utils.Constants;
import com.dennyschuldt.colors.utils.Utils;

/**
 * Created by denny on 5/6/16.
 */
public class PalettesDialogFragment extends DialogFragment implements View.OnClickListener {

  public static final String TAG = PalettesDialogFragment.class.getSimpleName();

  private int color;
  private View root;
  private ImageView back;
  private ImageView close;
  private RelativeLayout header;
  private Animation fadeIn, fadeOut;

  public static PalettesDialogFragment newInstance(final int color) {
    PalettesDialogFragment fragment = new PalettesDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(Constants.COLOR, color);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    getDialog().setCanceledOnTouchOutside(false);

    color = getArguments().getInt(Constants.COLOR);

    fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
    fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);

    root = inflater.inflate(R.layout.dialog_fragment_palettes, container, false);
    header = (RelativeLayout) root.findViewById(R.id.palettes_fragment_header);
    back = (ImageView) root.findViewById(R.id.palettes_fragment_header_icon_back);
    close = (ImageView) root.findViewById(R.id.palettes_fragment_header_icon_close);
    back.setOnClickListener(this);
    close.setOnClickListener(this);
    return root;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    header.setBackgroundColor(color);
    if (Utils.colorIsLight(color)) {
      for (int i=0; i<header.getChildCount(); i++) {
        ImageView iv = (ImageView) header.getChildAt(i);
        iv.setColorFilter(ContextCompat.getColor(getContext(), R.color.color_grey_dark));
      }
    }
    palettesListFragment(false);
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return new Dialog(getActivity(), getTheme()){
      @Override
      public void onBackPressed() {
        if (getChildFragmentManager().getBackStackEntryCount()>0) {
          getChildFragmentManager().popBackStack();
          hideBackButton();
        } else {
          dismiss();
        }
      }
    };
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.palettes_fragment_header_icon_close:
        getDialog().dismiss();
        break;
      case R.id.palettes_fragment_header_icon_back:
        palettesListFragment(true);
        break;
    }
  }

  /**
   *
   */
  public void palettesListFragment(boolean goBack) {
    if (goBack) {
      getChildFragmentManager().popBackStack();
    } else {
      PalettesListFragment fragment = PalettesListFragment.newInstance();
      FragmentTransaction ft = getChildFragmentManager().beginTransaction();
      ft.add(R.id.palettes_fragment_body, fragment, PalettesListFragment.TAG);
      ft.disallowAddToBackStack();
      ft.commit();
    }
    hideBackButton();
  }

  /**
   *
   */
  public void newPaletteFragment() {
    NewPaletteFragment fragment = NewPaletteFragment.newInstance();
    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
    ft.replace(R.id.palettes_fragment_body, fragment, NewPaletteFragment.TAG);
    ft.addToBackStack(NewPaletteFragment.TAG);
    ft.commit();
    showBackButton();
  }

  /**
   *
   */
  public void showBackButton() {
    back.startAnimation(fadeIn);
    back.setVisibility(View.VISIBLE);
  }

  /**
   *
   */
  public void hideBackButton() {
    back.startAnimation(fadeOut);
    back.setVisibility(View.GONE);
  }

}
