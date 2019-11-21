package pk.tune.saad.studentregistration.repository.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import pk.tune.saad.studentregistration.repository.model.Student;

@Dao
public interface StudentDao {

    @Insert
    void insert(Student student);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);

    @Query("DELETE FROM TABLE_STUDENT")
    void deleteAll();

    @Query("SELECT * FROM TABLE_STUDENT ORDER BY id ASC")
    LiveData<List<Student>> getAllStudents();
}
