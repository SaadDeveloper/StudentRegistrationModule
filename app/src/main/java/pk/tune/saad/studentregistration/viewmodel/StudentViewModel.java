package pk.tune.saad.studentregistration.viewmodel;

import android.app.Application;
import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import pk.tune.saad.studentregistration.repository.StudentRepository;
import pk.tune.saad.studentregistration.repository.model.ApiResponse;
import pk.tune.saad.studentregistration.repository.model.Student;

public class StudentViewModel extends ViewModel {

    private StudentRepository studentRepository;

    /*public StudentViewModel(Context context) {
        studentRepository = new StudentRepository(context);
        listMutableLiveData = studentRepository.callEndPointFlowable();
        allStudents = studentRepository.getAllStudents();
    }*/
    @Inject
    public StudentViewModel(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public void insertStudent(Student student){
        studentRepository.insertStudent(student);
    }

    public void updateStudent(Student student){
        studentRepository.updateStudent(student);
    }

    public void deleteStudent(Student student){
        studentRepository.deleteStudent(student);
    }

    public void deleteAllStudents(){
        studentRepository.deleteAllStudents();
    }

    public LiveData<List<Student>> getAllStudents(){
        return studentRepository.getAllStudents();
    }
}
