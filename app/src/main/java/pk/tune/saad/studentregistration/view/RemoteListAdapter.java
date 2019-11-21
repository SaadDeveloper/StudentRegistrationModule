package pk.tune.saad.studentregistration.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import pk.tune.saad.studentregistration.R;
import pk.tune.saad.studentregistration.databinding.JsonListItemBinding;
import pk.tune.saad.studentregistration.repository.model.JsonPlaceHolder;

public class RemoteListAdapter extends ListAdapter<JsonPlaceHolder, RemoteListAdapter.RxListViewHolder> {

    public RemoteListAdapter() {
        super(jsonPlaceHolderItemCallback);
    }

    private static DiffUtil.ItemCallback<JsonPlaceHolder> jsonPlaceHolderItemCallback = new DiffUtil.ItemCallback<JsonPlaceHolder>() {
        @Override
        public boolean areItemsTheSame(@NonNull JsonPlaceHolder oldItem, @NonNull JsonPlaceHolder newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull JsonPlaceHolder oldItem, @NonNull JsonPlaceHolder newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };

    @NonNull
    @Override
    public RxListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        JsonListItemBinding jsonListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.json_list_item, parent, false);
        return new RxListViewHolder(jsonListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RxListViewHolder holder, int position) {
        JsonPlaceHolder jsonPlaceHolder = getItem(position);
        holder.jsonListItemBinding.setInsertJsonRecord(jsonPlaceHolder);
    }


    class RxListViewHolder extends RecyclerView.ViewHolder {
        JsonListItemBinding jsonListItemBinding;

        private RxListViewHolder(@NonNull JsonListItemBinding jsonListItemBinding) {
            super(jsonListItemBinding.getRoot());
            this.jsonListItemBinding = jsonListItemBinding;
        }
    }
}
