package pk.tune.saad.studentregistration.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_student")
public class Student implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "student_name")
    private String studentName;
    @ColumnInfo(name = "student_age")
    private String studentAge;
    @ColumnInfo(name = "student_program")
    private String studentProgram;

    @Ignore
    public Student() {
    }

    public Student(String studentName, String studentAge, String studentProgram) {
        this.studentName = studentName;
        this.studentAge = studentAge;
        this.studentProgram = studentProgram;
    }


    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentAge() {
        return studentAge;
    }

    public String getStudentProgram() {
        return studentProgram;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Student(Parcel in){
        id = in.readInt();
        studentName = in.readString();
        studentAge = in.readString();
        studentProgram = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(studentName);
        parcel.writeString(studentAge);
        parcel.writeString(studentProgram);
    }
}
