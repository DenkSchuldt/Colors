package com.dennyschuldt.colors.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dennyschuldt.colors.R;

/**
 * Created by denny on 5/6/16.
 */
public class NewPaletteFragment extends Fragment implements View.OnClickListener {

  public static final String TAG = NewPaletteFragment.class.getSimpleName();

  private View root;
  private EditText title;
  private Button save;
  private PalettesDialogFragment palletesDialogFragment;

  public static NewPaletteFragment newInstance() {
    NewPaletteFragment fragment = new NewPaletteFragment();
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    palletesDialogFragment = (PalettesDialogFragment) getParentFragment();
    super.onAttach(context);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    root = inflater.inflate(R.layout.fragment_new_palette, container, false);
    title = (EditText) root.findViewById(R.id.new_palette_fragment_title);
    title.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        save.setEnabled(false);
        if (charSequence.length()>0) {
          save.setEnabled(true);
        }
      }
      @Override
      public void afterTextChanged(Editable editable) {}
    });
    save = (Button) root.findViewById(R.id.new_palette_fragment_save);
    save.setOnClickListener(this);
    return root;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.new_palette_fragment_save:
        palletesDialogFragment.dismiss();
        break;
    }
  }
}