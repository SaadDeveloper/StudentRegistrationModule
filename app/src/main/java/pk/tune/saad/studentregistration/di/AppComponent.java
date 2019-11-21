package pk.tune.saad.studentregistration.di;


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import pk.tune.saad.studentregistration.view.activity.ListingActivity;
import pk.tune.saad.studentregistration.view.activity.MainActivity;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(ListingActivity listingActivity);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
