package by.andrewblinets.test_task;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Андрей on 02.03.2017.
 */

public class RestApi {

    public RestApi() {
    }

    private static Retrofit getRetrofitInstance()
    {
        return new Retrofit.Builder().baseUrl(Constans.HOST)
                .addConverterFactory(GsonConverterFactory.create( )).build();
    }

    private static Retrofit getRetrofitInstanceImage(String URL)
    {
        return new Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create( )).build();
    }

    public static Api_tes_task getApi() {
        return  getRetrofitInstance().create(Api_tes_task.class);
    }


    public static Api_tes_task getImageApi(String URL) {

        return getRetrofitInstanceImage(URL).create(Api_tes_task.class);
    }

}
