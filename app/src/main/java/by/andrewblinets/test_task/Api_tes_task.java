package by.andrewblinets.test_task;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Андрей on 02.03.2017.
 */

public interface Api_tes_task {

    @GET
    Call<ResponseBody> getJsonString(@Url String url);

    @GET
    Call<ResponseBody> getImageFile(@Url String url);

}
