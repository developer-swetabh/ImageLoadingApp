package swetabh.suman.com.imageloadingapp.data.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abhi on 26/01/17.
 */

public class RestClient {
    public static Retrofit retrofit;

    public static <T> T createService(Class<T> service) {
        if (retrofit == null) {
            createRetrofit();
        }

        return retrofit.create(service);
    }

    private static void createRetrofit() {


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(NetworkConstants.TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(NetworkConstants.TIMEOUT, TimeUnit.SECONDS);
        // add your other interceptors …
        // add logging as last interceptor
        if (NetworkConstants.IS_DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);  // <-- this is the important line!
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
