package pk.tune.saad.studentregistration.repository.retrofit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import pk.tune.saad.studentregistration.repository.model.JsonPlaceHolder;
import retrofit2.http.GET;

public interface ApiCallsInterface {

    /*
    <ref>https://jsonplaceholder.typicode.com/</>
    Getting the records from this site
     */

    /*
    RxAndroid Guide Links
    <ref>https://medium.com/@kurtisnusbaum/rxandroid-basics-part-1-c0d5edcf6850</>
    <ref>https://medium.com/@kurtisnusbaum/rxandroid-basics-part-2-6e877af352</>
     */
    @GET("photos/")
    Observable<List<JsonPlaceHolder>> getJsonList();

    @GET("photos/")
    Single<List<JsonPlaceHolder>> getSingleList();

    @GET("photos/")
    Flowable<List<JsonPlaceHolder>> getFlowableList();

    @GET("photos/")
    Completable getCompletableList();
}
