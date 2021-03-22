package com.example.wiscpets.clinic_module;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wiscpets.R;


/** Displays information about how to use App. */
public class AboutERVisitActivity extends AppCompatActivity implements ImageGetter {
	private LinearLayout mainLayoutAbout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScrollView scrollView = new ScrollView(this);
		mainLayoutAbout = new LinearLayout(this);
		mainLayoutAbout.setOrientation(LinearLayout.VERTICAL);
		setContentView(scrollView);
		LinearLayout layoutScreen = new LinearLayout(this);
		TextView text = new TextView(this);
		text.setText(Html.fromHtml(getString(R.string.about), this, null));
		layoutScreen.addView(text);
		mainLayoutAbout.addView(layoutScreen);
		scrollView.addView(mainLayoutAbout);
		setContentView(scrollView);
	}

	/**
	 * Populate image tags in HTML string */
	@Override
	public Drawable getDrawable(String argurementPictureImage) {
		int idPictureImage = 0;

		if (argurementPictureImage.equals("ic_action_add_prescription_light.png")){
			idPictureImage = R.drawable.ic_action_add_prescription_light;
		} else if (argurementPictureImage.equals("ic_action_search_light.png")){
			idPictureImage = R.drawable.ic_action_search_light;
		} else if (argurementPictureImage.equals("ic_action_content_add_light.png")){
			idPictureImage = R.drawable.ic_action_content_add_light;
		} else if (argurementPictureImage.equals("ic_action_content_new_light.png")){
			idPictureImage = R.drawable.ic_action_content_new_light;
		} else if (argurementPictureImage.equals("ic_action_file_folder_shared_light.png")){
			idPictureImage = R.drawable.ic_action_file_folder_shared_light;
		} else if (argurementPictureImage.equals("ic_action_send_to_doctor_light.png")){
			idPictureImage = R.drawable.ic_action_send_to_doctor_light;
		} else if (argurementPictureImage.equals("ic_action_content_discard_light.png")){
			idPictureImage = R.drawable.ic_action_content_discard_light;
		} else if (argurementPictureImage.equals("ic_action_social_person_add_light.png")){
			idPictureImage = R.drawable.ic_action_social_person_add_light;
		}
		LevelListDrawable dLevelListDrawable = new LevelListDrawable();
		Drawable emptyDrawable = (Drawable) getResources().getDrawable(idPictureImage);
		dLevelListDrawable.addLevel(0, 0, emptyDrawable );
		dLevelListDrawable.setBounds(0, 0, 60, 60);
		return dLevelListDrawable;
	}

	/** Handle events from menu  **/
	@Override
	public boolean onOptionsItemSelected(MenuItem itemMenu) {
		switch (itemMenu.getItemId()) {
			case R.id.menu_back:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(itemMenu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menuTmp) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.about_activity, menuTmp);
		return true;
	}
}
