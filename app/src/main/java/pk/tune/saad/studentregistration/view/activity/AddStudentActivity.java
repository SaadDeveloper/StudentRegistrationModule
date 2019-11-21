package pk.tune.saad.studentregistration.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import pk.tune.saad.studentregistration.R;
import pk.tune.saad.studentregistration.databinding.ActivityAddStudentBinding;
import pk.tune.saad.studentregistration.repository.model.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import static pk.tune.saad.studentregistration.view.activity.MainActivity.EXTRA_STUDENT;

public class AddStudentActivity extends AppCompatActivity {

    private ActivityAddStudentBinding activityAddStudentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddStudentBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_student);
        getIntentData();
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Student student = bundle.getParcelable(EXTRA_STUDENT);
            if (student != null) {
                getSupportActionBar().setTitle("Edit Student");
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activityAddStudentBinding.setSetStudentData(student);
            } else {
                getSupportActionBar().setTitle("Add Student");
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } else {
            getSupportActionBar().setTitle("Add Student");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void saveData() {
        String name = activityAddStudentBinding.etName.getText().toString();
        String age = activityAddStudentBinding.etAge.getText().toString();
        String program = activityAddStudentBinding.etProgram.getText().toString();
        if (name.trim().isEmpty() || age.trim().isEmpty() || program.trim().isEmpty()) {
            Toast.makeText(this, "Insert all the fields", Toast.LENGTH_SHORT).show();
        } else {
            Student student = new Student(name, age, program);
            Intent intent = new Intent(AddStudentActivity.this, MainActivity.class);
            intent.putExtra(EXTRA_STUDENT, student);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
