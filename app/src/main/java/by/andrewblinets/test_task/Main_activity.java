package by.andrewblinets.test_task;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
        final LinearLayout linear = (LinearLayout) findViewById(R.id.liner_main);
        switch (v.getId())
        {
            case R.id.button_new:
            {
                list.clear();
                linear.removeAllViews();
                function_button(Constans.Url_new);
                break;
            }
            case R.id.button_top:
            {
                list.clear();
                linear.removeAllViews();
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
                    if(response.body() != null) {
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
            setImageFile();
        }
        catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error, parsing JSON" , Toast.LENGTH_SHORT).show();
        }
    }

    private void setImageFile() {

        final LinearLayout linearLayoutmain = (LinearLayout) findViewById(R.id.liner_main);
        LinearLayout.LayoutParams paramslinerlayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        paramslinerlayout.setMargins((int) getResources().getDimension(R.dimen.marginlinerlayout),
                (int) getResources().getDimension(R.dimen.marginlinerlayout),
                (int) getResources().getDimension(R.dimen.marginlinerlayout),
                (int) getResources().getDimension(R.dimen.marginlinerlayout));

        for(int i = 0; i < list.size() / 2; i ++) {
            final LinearLayout.LayoutParams paramsbuttonstart = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    (int) getResources().getDimension(R.dimen.heightImagerightandleft));
            paramsbuttonstart.weight = 1;
            paramsbuttonstart.setMargins((int) getResources().getDimension(R.dimen.marginImage),
                    (int) getResources().getDimension(R.dimen.marginImage),
                    (int) getResources().getDimension(R.dimen.marginImage),
                    (int) getResources().getDimension(R.dimen.marginImage));
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView imageViewleft = new ImageView(this);
            imageViewleft.setLayoutParams(paramsbuttonstart);
            imageViewleft.setId(i * 2);
            imageViewleft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView im = (ImageView) findViewById(v.getId());
                    LinearLayout.LayoutParams parametrsleftbutton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            (int) getResources().getDimension(R.dimen.heightImageone));
                    parametrsleftbutton.setMargins((int) getResources().getDimension(R.dimen.marginImage),
                            (int) getResources().getDimension(R.dimen.marginImage),
                            (int) getResources().getDimension(R.dimen.marginImage),
                            (int) getResources().getDimension(R.dimen.marginImage));
                    im.setLayoutParams(parametrsleftbutton);
                }
            });

            linearLayout.addView(imageViewleft);

            ImageView imageViewright = new ImageView(this);
            imageViewright.setLayoutParams(paramsbuttonstart);
            imageViewright.setId(i * 2 + 1);
            imageViewright.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView im = (ImageView) findViewById(v.getId());
                    LinearLayout.LayoutParams paramsrightbutton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            (int) getResources().getDimension(R.dimen.heightImageone));
                    paramsrightbutton.setMargins((int) getResources().getDimension(R.dimen.marginLeftforsecondImage),
                            (int) getResources().getDimension(R.dimen.marginImage),
                            (int) getResources().getDimension(R.dimen.marginImage),
                            (int) getResources().getDimension(R.dimen.marginImage));
                    im.setLayoutParams(paramsrightbutton);
                }
            });
            linearLayout.addView(imageViewright);

            linearLayoutmain.addView(linearLayout, paramslinerlayout);
            ImageView imgleft = (ImageView)findViewById(i * 2);
            ImageView imgright = (ImageView)findViewById(i * 2 + 1);
            Picasso.with(this)
                    .load(list.get(i * 2))
                    .fit()
                    .error(R.drawable.error)
                    .placeholder(R.drawable.load)
                    .into(imgleft);
            Picasso.with(this)
                    .load(list.get(i * 2 + 1))
                    .fit()
                    .error(R.drawable.error)
                    .placeholder(R.drawable.load)
                    .into(imgright);

        }
    }

}
