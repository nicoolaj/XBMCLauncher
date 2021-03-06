package se.blunden.xbmclauncher;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	
	private static final String TAG = "XBMCLauncher";

    private void launch()
	{
		// Load preferences
		SharedPreferences settings;
		settings = getSharedPreferences("se.blunden.xbmclauncher_preferences", Context.MODE_PRIVATE);

        String xbmcActivity = settings.getString("xbmc_variant", getString(R.string.xbmc_activity_default));
		
		// Build the Kodi/XBMC intent
        Intent activityIntent;
        activityIntent = new Intent(Intent.ACTION_MAIN);
        activityIntent.setComponent(ComponentName.unflattenFromString(xbmcActivity));
        /*
         * The launched activity will be considered the HOME activity and will be on top of the
         * stack as needed for the system to broadcast BOOT_COMPLETED.
         */
        activityIntent.addCategory(Intent.CATEGORY_HOME);
		
		try {
			startActivity(activityIntent);
        }
		catch (ActivityNotFoundException e)
		{
			Log.d(TAG, "Activity " + xbmcActivity + " not found. Launching settings activity...");
			
			Intent launchSettings = new Intent(this, LauncherSettingsActivity.class);
			startActivity(launchSettings);
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		launch();
		finish();
	}
}
