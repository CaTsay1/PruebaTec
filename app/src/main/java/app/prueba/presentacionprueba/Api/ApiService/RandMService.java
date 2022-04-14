package app.prueba.presentacionprueba.Api.ApiService;

import app.prueba.presentacionprueba.Models.RandM;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandMService {


    @GET("character")
    Call<RandM> getInfo(@Query("page") String page);
}
