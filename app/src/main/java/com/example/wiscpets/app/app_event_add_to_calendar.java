package com.example.wiscpets.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wiscpets.R;

import java.util.Calendar;



public class app_event_add_to_calendar extends AppCompatActivity {
Intent i=getIntent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_edit);
	}

	public void setevent(View v){
		EditText event=(EditText)findViewById(R.id.event_edit_name);
		DatePicker datepick=(DatePicker) findViewById(R.id.event_pick_date);
		String name=event.getText().toString();
		int day, month, year;
		day=datepick.getDayOfMonth();
		month=datepick.getMonth();
		year=datepick.getYear();
		String type;
		int checkedId;
		Toast.makeText(getApplicationContext(), "Updating Event in Calendar", Toast.LENGTH_LONG).show();
		Calendar cal = Calendar.getInstance();
		Intent intent = new Intent(Intent.ACTION_EDIT);
		intent.setType("vnd.android.cursor.item/event");
		intent.putExtra("beginTime", cal.getTimeInMillis());
		intent.putExtra("allDay", false);
		intent.putExtra("rrule", "FREQ=DAILY");
		intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
		intent.putExtra("title", name);
		startActivity(intent);
		finish();
	}
}
