package pk.tune.saad.studentregistration.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import pk.tune.saad.studentregistration.R;
import pk.tune.saad.studentregistration.databinding.StudentListItemBinding;
import pk.tune.saad.studentregistration.repository.model.Student;

//ListAdapter is a subclass of RecyclerViewAdapter for which handles the provided Arraylist for add remove operations using DiffUtils class
public class StudentListAdapter extends ListAdapter<Student, StudentListAdapter.StudentListViewHolder> {

    private Context context;
    private onClickListener onClickListener;

    public StudentListAdapter(Context context, onClickListener onClickListener ) {
        super(oldDiffCallBack);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    private static DiffUtil.ItemCallback<Student> oldDiffCallBack = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getStudentName().equals(newItem.getStudentName());
        }
    };

    @NonNull
    @Override
    public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StudentListItemBinding studentListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.student_list_item, parent, false);
        return new StudentListViewHolder(studentListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListViewHolder holder, int position) {
        Student student = getItem(position);
        holder.studentListItemBinding.setInsertData(student);
    }


    public Student getStudentAt(int position){
       return getItem(position);
    }

    class StudentListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private StudentListItemBinding studentListItemBinding;

        StudentListViewHolder(@NonNull StudentListItemBinding studentListItemBinding) {
            super(studentListItemBinding.getRoot());
            studentListItemBinding.getRoot().setOnClickListener(this);
            this.studentListItemBinding = studentListItemBinding;
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(getItem(getAdapterPosition()));
        }
    }

    public interface onClickListener{
        void onClick(Student student);
    }
}
