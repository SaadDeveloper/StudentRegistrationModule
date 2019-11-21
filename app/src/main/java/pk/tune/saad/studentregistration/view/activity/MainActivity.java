package pk.tune.saad.studentregistration.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pk.tune.saad.studentregistration.MyApplication;
import pk.tune.saad.studentregistration.R;
import pk.tune.saad.studentregistration.databinding.ActivityMainBinding;
import pk.tune.saad.studentregistration.repository.model.Student;
import pk.tune.saad.studentregistration.view.StudentListAdapter;
import pk.tune.saad.studentregistration.viewmodel.StudentViewModel;
import pk.tune.saad.studentregistration.viewmodel.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

/*
References
RxAndroid References
    <ref>https://medium.com/@kurtisnusbaum/rxandroid-basics-part-1-c0d5edcf6850</>
    <ref>https://medium.com/@kurtisnusbaum/rxandroid-basics-part-2-6e877af352</>
MvvM References
    <ref>https://medium.com/@saquib3705/consuming-rest-api-using-retrofit-library-with-the-help-of-mvvm-dagger-livedata-and-rxjava2-in-67aebefe031d</>
Dagger References
    <ref>https://developer.android.com/training/dependency-injection/dagger-android#java</>
    <ref>https://developer.android.com/training/dependency-injection/dagger-basics#java</>
 */

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;
    public static final String EXTRA_STUDENT = "student";
    @Inject
    public StudentViewModel studentViewModel;
    @Inject
    public ViewModelProvider viewModelProviders;
    private StudentListAdapter studentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyApplication) getApplicationContext()).appComponent.inject(this);
        super.onCreate(savedInstanceState);
        initMainDataBinding();
        initViewModel();
    }

    private void initMainDataBinding() {
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        RecyclerView rvStudentList = activityMainBinding.rvStudentList;
        rvStudentList.setLayoutManager(new LinearLayoutManager(this));
        rvStudentList.setHasFixedSize(true);
        studentListAdapter = new StudentListAdapter(this, new StudentListAdapter.onClickListener() {
            @Override
            public void onClick(Student student) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                intent.putExtra(EXTRA_STUDENT, student);
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
        rvStudentList.setAdapter(studentListAdapter);

        activityMainBinding.floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });
        //Used for Slide for Delete Feature in RecyclerView
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0;
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                studentViewModel.deleteStudent(studentListAdapter.getStudentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Student Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rvStudentList);
    }

    private void initViewModel() {
        studentViewModel = ViewModelProviders.of(this, viewModelProviders).get(StudentViewModel.class);
        studentViewModel.getAllStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                if (students != null) {
                    studentListAdapter.submitList(students);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK) {
            Student student = data.getParcelableExtra(EXTRA_STUDENT);
            studentViewModel.insertStudent(student);
        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            Student student = data.getParcelableExtra(EXTRA_STUDENT);
            studentViewModel.updateStudent(student);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete_all) {
            studentViewModel.deleteAllStudents();
            Toast.makeText(MainActivity.this, "All Students Deleted", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_rx_screen) {
            startActivity(new Intent(this, ListingActivity.class));
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}
