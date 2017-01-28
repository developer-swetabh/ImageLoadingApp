package swetabh.suman.com.imageloadingapp.data.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import swetabh.suman.com.imageloadingapp.data.model.ResponseModel;

/**
 * Created by swetabh on 26/01/17.
 */

public interface IApiService {

    @GET("./")
    Call<List<ResponseModel>> getList();
}
