package pk.tune.saad.studentregistration.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import pk.tune.saad.studentregistration.repository.model.ApiResponse;
import pk.tune.saad.studentregistration.repository.model.JsonPlaceHolder;
import pk.tune.saad.studentregistration.repository.model.Student;
import pk.tune.saad.studentregistration.repository.retrofit.ApiCallsInterface;
import pk.tune.saad.studentregistration.repository.retrofit.BaseApiClient;
import pk.tune.saad.studentregistration.repository.room.StudentDao;
import pk.tune.saad.studentregistration.repository.room.StudentDatabase;
import retrofit2.Retrofit;

@Singleton
public class StudentRepository {

    private StudentDao studentDao;
    private LiveData<List<Student>> allStudents;

    @Inject
    public StudentRepository(StudentDatabase studentDatabase) {
        studentDao = studentDatabase.studentDao();
        allStudents = studentDao.getAllStudents();
    }

    public void insertStudent(Student student) {
        new AsyncTaskIUD(studentDao, AsyncTaskIUD.INSERT).execute(student);
    }

    public void updateStudent(Student student) {
        new AsyncTaskIUD(studentDao, AsyncTaskIUD.UPDATE).execute(student);
    }

    public void deleteStudent(Student student) {
        new AsyncTaskIUD(studentDao, AsyncTaskIUD.DELETE).execute(student);
    }

    public void deleteAllStudents() {
        new AsyncTaskIUD(studentDao, AsyncTaskIUD.DELETE_ALL).execute();
    }


    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    private static class AsyncTaskIUD extends AsyncTask<Student, Void, Void> {

        public static final int INSERT = 0;
        public static final int UPDATE = 1;
        public static final int DELETE = 2;
        public static final int DELETE_ALL = 3;

        private StudentDao studentDao;
        private int operation;

        private AsyncTaskIUD(StudentDao studentDao, int selection) {
            this.studentDao = studentDao;
            operation = selection;
        }

        @Override
        protected Void doInBackground(Student... students) {
            switch (operation) {
                case INSERT:
                    studentDao.insert(students[0]);
                    break;
                case UPDATE:
                    studentDao.update(students[0]);
                    break;
                case DELETE:
                    studentDao.delete(students[0]);
                    break;
                case DELETE_ALL:
                    studentDao.deleteAll();
                    break;
            }

            return null;
        }
    }
}
