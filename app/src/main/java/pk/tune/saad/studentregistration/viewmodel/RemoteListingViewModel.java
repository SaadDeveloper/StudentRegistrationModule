package pk.tune.saad.studentregistration.viewmodel;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import pk.tune.saad.studentregistration.repository.RemoteListingRepository;
import pk.tune.saad.studentregistration.repository.model.ApiResponse;
import retrofit2.Retrofit;

public class RemoteListingViewModel extends ViewModel {

    private RemoteListingRepository remoteListingRepository;

    @Inject
    public RemoteListingViewModel(RemoteListingRepository remoteListingRepository){
        this.remoteListingRepository = remoteListingRepository;
    }

    @Override
    protected void onCleared() {
        remoteListingRepository.compositeDisposable.dispose();
        super.onCleared();
    }

    public MutableLiveData<ApiResponse> getAllList(){
        remoteListingRepository.callEndpointWithSingle();
        return remoteListingRepository.getRemoteData();
    }
}
