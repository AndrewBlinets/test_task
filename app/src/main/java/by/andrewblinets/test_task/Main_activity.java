package by.andrewblinets.test_task;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Main_activity extends Activity implements View.OnClickListener {

    private static int LAYOUT = R.layout.main_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_new:
            {
                break;
            }
            case R.id.button_top:
            {
                break;
            }
            default:
                break;
        }
    }
}
