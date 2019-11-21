package pk.tune.saad.studentregistration.repository;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import pk.tune.saad.studentregistration.repository.model.ApiResponse;
import pk.tune.saad.studentregistration.repository.model.JsonPlaceHolder;
import pk.tune.saad.studentregistration.repository.retrofit.ApiCallsInterface;
import retrofit2.Retrofit;

@Singleton
public class RemoteListingRepository {

    private Retrofit retrofit;
    private final ApiCallsInterface apiCallsInterface;
    private MutableLiveData<ApiResponse> mutableLiveData;
    public CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public RemoteListingRepository(Retrofit retrofit){
        this.retrofit = retrofit;
        apiCallsInterface = retrofit.create(ApiCallsInterface.class);
        mutableLiveData = callEndPoint();
    }

    public MutableLiveData<ApiResponse> getRemoteData(){
        return mutableLiveData;
    }

    public MutableLiveData<ApiResponse> callEndPoint() {
        final MutableLiveData<ApiResponse> mutableLiveData = new MutableLiveData<>();
        /*singleRequest().subscribe(new SingleObserver<List<JsonPlaceHolder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mutableLiveData.setValue(ApiResponse.loading());
            }

            @Override
            public void onSuccess(List<JsonPlaceHolder> jsonPlaceHolders) {
                mutableLiveData.setValue(ApiResponse.result(jsonPlaceHolders));
            }

            @Override
            public void onError(Throwable e) {
                mutableLiveData.setValue(ApiResponse.error(e));
            }
        });*/
        compositeDisposable.add(flowableRequest().subscribe(new Consumer<List<JsonPlaceHolder>>() {
            @Override
            public void accept(List<JsonPlaceHolder> jsonPlaceHolders) throws Exception {
                Log.i("Rx", "subscribe_success");
                mutableLiveData.setValue(ApiResponse.result(jsonPlaceHolders));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("Rx", "subscribe_error");
                mutableLiveData.setValue(ApiResponse.error(throwable));
            }
        }));

        return mutableLiveData;
    }

    private Single<List<JsonPlaceHolder>> singleRequest() {
        return apiCallsInterface.getSingleList().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }

    private Flowable<List<JsonPlaceHolder>> flowableRequest(){
        return apiCallsInterface.getFlowableList().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                doOnNext(new Consumer<List<JsonPlaceHolder>>() {
                    @Override
                    public void accept(List<JsonPlaceHolder> jsonPlaceHolders) throws Exception {
                        Log.i("Rx", "doOnNext");
                    }
                }).
                doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        Log.i("Rx", "doOnSubscribe");
                    }
                });
    }

}
