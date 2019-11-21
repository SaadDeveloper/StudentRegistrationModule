package pk.tune.saad.studentregistration.di;

import android.app.Application;
import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import pk.tune.saad.studentregistration.repository.retrofit.BaseApiClient;
import pk.tune.saad.studentregistration.repository.room.StudentDatabase;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    public Context provideContext(Application application){
        return application;
    }

    @Provides
    @Singleton
    public StudentDatabase getStudentDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), StudentDatabase.class, "Student_Database")
                .allowMainThreadQueries() //This method is used to forcefully allow room to execute queries on Main Thread, without this app will crash on execution of queries or we have to use asynctask for executing queries.
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new BaseApiClient.LoggingInterceptor())
                .retryOnConnectionFailure(true)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit getClient(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
