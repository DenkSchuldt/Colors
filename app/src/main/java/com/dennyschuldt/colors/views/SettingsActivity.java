package com.dennyschuldt.colors.views;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dennyschuldt.colors.R;
import com.dennyschuldt.colors.utils.Constants;

/**
 * Created by denny on 4/23/16.
 */
public class SettingsActivity extends AppCompatActivity {

  private int deleteNewsTime;
  private ViewHolder viewHolder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    viewHolder = new ViewHolder();
    viewHolder.buildLayout();
  }

  @Override
  protected void onStart() {
    super.onStart();
    updateContent();
  }

  /**
   *
   */
  public void updateContent() {
    updateVersionName();
  }

  /**
   *
   */
  public void updateVersionName() {
    try {
      PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
      viewHolder.versionLabel.setText("v." + pInfo.versionName);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }


  /**
   *
   */
  private class ViewHolder implements View.OnClickListener{

    private LinearLayout layout;

    private TextView versionLabel;
    private LinearLayout version;

    private LinearLayout sendComments;

    public void buildLayout() {
      findViews();
      setOnClickListeners();
    }

    /**
     *
     */
    private void findViews() {
      layout = (LinearLayout) findViewById(R.id.settings_layout);
      versionLabel = (TextView) findViewById(R.id.settings_version_label);
      version = (LinearLayout) findViewById(R.id.settings_version);
      sendComments = (LinearLayout) findViewById(R.id.settings_send_comments);
    }

    /**
     *
     */
    private void setOnClickListeners() {
      version.setOnClickListener(this);
      sendComments.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.settings_version:
          Snackbar.make(layout, R.string.settings_version, Snackbar.LENGTH_LONG).show();
          break;
        case R.id.settings_send_comments:
          Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", Constants.CONTACT_EMAIL, null));
          i.putExtra(android.content.Intent.EXTRA_SUBJECT, "About the Colors app");
          startActivity(Intent.createChooser(i, getResources().getString(R.string.settings_comments_label)));
          break;
      }
    }

  }

}
