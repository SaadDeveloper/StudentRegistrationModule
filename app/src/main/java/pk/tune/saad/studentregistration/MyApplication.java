package pk.tune.saad.studentregistration;

import android.app.Application;

import pk.tune.saad.studentregistration.di.AppComponent;
import pk.tune.saad.studentregistration.di.DaggerAppComponent;


public class MyApplication extends Application {

    public AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().application(this).build();
    }
}
