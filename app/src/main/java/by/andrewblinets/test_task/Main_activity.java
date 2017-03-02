package by.andrewblinets.test_task;


import android.app.Activity;
import android.os.Bundle;

public class Main_activity extends Activity {

    private static int LAYOUT = R.layout.main_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
    }
}
