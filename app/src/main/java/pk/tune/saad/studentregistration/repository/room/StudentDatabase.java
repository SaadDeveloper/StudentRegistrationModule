package pk.tune.saad.studentregistration.repository.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import pk.tune.saad.studentregistration.repository.model.Student;

//For Multiple table, just add a comma and mention another table name ahead of Student.class
@Database(entities = {Student.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase {

    private static StudentDatabase instance;

    public abstract StudentDao studentDao(); //Room Database automatically manage the reference of this abstract method

    public static synchronized StudentDatabase getInstance(Context context){
        if(instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext(), StudentDatabase.class, "Student_Database")
                    .allowMainThreadQueries() //This method is used to forcefully allow room to execute queries on Main Thread, without this app will crash on execution of queries or we have to use asynctask for executing queries.
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
