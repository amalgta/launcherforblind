package com.akash.thirdeye;

import com.akash.thirdeye.activitysupport.AbstractArrayActivity;
import com.akash.thirdeye.listentries.BatteryListEntry;
import com.akash.thirdeye.listentries.ListEntry;
import com.akash.thirdeye.listentries.NavigatorListEntry;
import com.akash.thirdeye.listentries.StaticListEntry;
import com.akash.thirdeye.listentries.TimeListEntry;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AbstractArrayActivity {
	private static final String TAG = "launcherforblind";

	private TextView txtMain;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.main);

		txtMain = (TextView) findViewById(R.id.txtMain);
	}

	@Override
	protected ListEntry[] getList() {
		return new ListEntry[] {
				new StaticListEntry(getString(R.string.main_screen)),
				new NavigatorListEntry(getString(R.string.phonebook),
						PhoneBookActivity.class),
				new NavigatorListEntry(getString(R.string.dialer),
						DialerActivity.class),
				new NavigatorListEntry(getString(R.string.missedcalls),
						MissedCallsActivity.class),
				new NavigatorListEntry(getString(R.string.sms),
						SMSActivity.class),
				new NavigatorListEntry(getString(R.string.apps),
						AppsActivity.class),
				new TimeListEntry(getString(R.string.currenttime),
						getString(R.string.time_format)),
				new BatteryListEntry(getString(R.string.battery_level)), };
	}

	@Override
	protected void giveFeedback(String label) {
		txtMain.setText(label);
	}

	@Override
	protected boolean announceHelp() {
		if (Settings.announceMainHelp()) {
			say(getString(R.string.main_help));
			Settings.updateAnnounceMainHelp();
			return true;
		}
		return false;
	}
}
