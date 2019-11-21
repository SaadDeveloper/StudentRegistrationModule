package pk.tune.saad.studentregistration.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pk.tune.saad.studentregistration.MyApplication;
import pk.tune.saad.studentregistration.R;
import pk.tune.saad.studentregistration.Utils;
import pk.tune.saad.studentregistration.databinding.ActivityListingBinding;
import pk.tune.saad.studentregistration.repository.model.ApiResponse;
import pk.tune.saad.studentregistration.repository.model.JsonPlaceHolder;
import pk.tune.saad.studentregistration.view.RemoteListAdapter;
import pk.tune.saad.studentregistration.viewmodel.RemoteListingViewModel;
import pk.tune.saad.studentregistration.viewmodel.ViewModelProvider;

import static pk.tune.saad.studentregistration.repository.model.ApiResponse.ERROR;
import static pk.tune.saad.studentregistration.repository.model.ApiResponse.LOADING;
import static pk.tune.saad.studentregistration.repository.model.ApiResponse.SUCCESS;

public class ListingActivity extends AppCompatActivity {

    @Inject
    public RemoteListingViewModel remoteListingViewModel;
    @Inject
    public ViewModelProvider viewModelProviders;
    private ProgressDialog progressDialog;
    private RemoteListAdapter rxListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((MyApplication)getApplicationContext()).appComponent.inject(this);
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModel();
    }

    private void initBinding(){
        ActivityListingBinding activityListingBinding = DataBindingUtil.setContentView(this, R.layout.activity_listing);
        RecyclerView rxExtraList = activityListingBinding.rvExtraList;
        rxExtraList.setLayoutManager(new LinearLayoutManager(this));
        rxListAdapter = new RemoteListAdapter();
        rxExtraList.setAdapter(rxListAdapter);

    }

    private void initViewModel(){
        progressDialog = Utils.showProgressDialog(this, "loading content ...");
        remoteListingViewModel = ViewModelProviders.of(this, viewModelProviders).get(RemoteListingViewModel.class);
        remoteListingViewModel.getAllList().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                consumeResponse(apiResponse);
            }
        });
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                progressDialog.show();
                break;

            case SUCCESS:
                progressDialog.dismiss();
                //noinspection unchecked
                renderSuccessResponse((List<JsonPlaceHolder>) apiResponse.responseData);
                break;

            case ERROR:
                progressDialog.dismiss();
                Toast.makeText(ListingActivity.this,"Error in Response", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    /*
     * method to handle success response
     * */
    private void renderSuccessResponse(List<JsonPlaceHolder> response) {
        if (response != null) {
            rxListAdapter.submitList(response);
        } else {
            Toast.makeText(ListingActivity.this,"Empty List", Toast.LENGTH_SHORT).show();
        }
    }
}
