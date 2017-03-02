package by.andrewblinets.test_task;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_activity extends Activity implements View.OnClickListener {

    private static int LAYOUT = R.layout.main_activity;

    private RestApi restApi;

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        restApi = new RestApi();
        list = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_new:
            {
                list.clear();
                function_button(Constans.Url_new);
                break;
            }
            case R.id.button_top:
            {
                list.clear();
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
                if(response.isSuccessful()) {
                    if(response == null) {
                        try {
                            parseJSON(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Answer is null" , Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Not a good answer" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSON(String str_response) {
        JSONObject DataJsonstr = null;
        try {
            DataJsonstr = new JSONObject(str_response);
            JSONObject data = DataJsonstr.getJSONObject("data");

            JSONArray children = data.getJSONArray("children");
            for (int i = 0; i < children.length(); i++) {
                JSONObject data_children = children.getJSONObject(i);
                JSONObject data_child = data_children.getJSONObject("data");
                if(data_child.getString("post_hint").equals("image"))
                {
                    list.add(data_child.getString("url"));
                }
            }


        }
        catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error, parsing JSON" , Toast.LENGTH_SHORT).show();
        }
    }

}
