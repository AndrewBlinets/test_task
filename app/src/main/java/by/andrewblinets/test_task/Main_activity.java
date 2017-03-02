package by.andrewblinets.test_task;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_activity extends Activity implements View.OnClickListener {

    private static int LAYOUT = R.layout.main_activity;

    private RestApi restApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        restApi = new RestApi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_new:
            {
                function_button(Constans.Url_new);
                break;
            }
            case R.id.button_top:
            {
                function_button(Constans.Url_top);
                break;
            }
            default:
                break;
        }
    }

    private void function_button(String url) {
        Api_tes_task api = RestApi.getApi();
        Call<ResponseBody> call = api.getJsonString(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str_response = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

}
