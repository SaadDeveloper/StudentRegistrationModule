package pk.tune.saad.studentregistration.repository.retrofit;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApiClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static Retrofit getClient() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .retryOnConnectionFailure(true)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());
            if (request.method().compareToIgnoreCase("post") == 0) {
                requestLog = "\n" + requestLog + "\n" + bodyToString(request);
            }
            Log.i("Retrofit", "request" + "\n" + requestLog);
            //Headers headers = request.headers().newBuilder().add("Authorization", credentials).build();
            //request = request.newBuilder().headers(headers).build();
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());
            String bodyString = response.body().string();
            Log.e("Retrofit", "response" + "\n" + responseLog + "\n" + bodyString);
            //String base64EncodedCredentials = "Basic " + Utils.getBase64EncodedString(credentials).replaceAll("\n","");;
            return response.newBuilder()
                    //.header("Authorization", base64EncodedCredentials.trim())
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
        }
    }

    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
