package pk.tune.saad.studentregistration.viewmodel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import pk.tune.saad.studentregistration.repository.RemoteListingRepository;
import pk.tune.saad.studentregistration.repository.StudentRepository;

public class ViewModelProvider extends androidx.lifecycle.ViewModelProvider.NewInstanceFactory {

    private StudentRepository mStudentRepository;
    private RemoteListingRepository mRemoteListingRepository;

    @Inject
    public ViewModelProvider(StudentRepository studentRepository, RemoteListingRepository remoteListingRepository) {
        mStudentRepository = studentRepository;
        mRemoteListingRepository = remoteListingRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StudentViewModel.class)) {
            //noinspection unchecked
            return (T) new StudentViewModel(mStudentRepository);
        }else if(modelClass.isAssignableFrom(RemoteListingViewModel.class)){
            //noinspection unchecked
            return (T) new RemoteListingViewModel(mRemoteListingRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
