package ps.monjed.ptuk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    //Monjed's Solution
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the students table
        db.execSQL("CREATE TABLE students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, mark INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    @SuppressLint("Range")
    public ArrayList<Student> getAll(){
        SQLiteDatabase reader = getReadableDatabase();
        ArrayList<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        @SuppressLint("Recycle") Cursor cursor = reader.rawQuery(sql,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Student s = new Student(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("mark"))
            );
            s.setId(cursor.getInt(cursor.getColumnIndex("id")));
            list.add(s);
            cursor.moveToNext();
        }
        return list;
    }

    public boolean delete(int id) {
        SQLiteDatabase writer = getWritableDatabase();
        String sql = "DELETE FROM students WHERE id = ?";
        writer.execSQL(sql, new String[]{String.valueOf(id)});
        return true;
    }

    public ArrayList<Student> getSorted(){
        ArrayList<Student> list;
        list = getAll();
        list.sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
        return list;
    }

    public void addStudent(Student student) {
        String name = student.getName();
        int mark = student.getMark();
        SQLiteDatabase writer = getWritableDatabase();
        String sql = "INSERT INTO students (name, mark) VALUES(?, ?)";
        writer.execSQL(sql,new String[]{name, String.valueOf(mark)});
    }

    public double getAvg(){
        ArrayList<Student> list;
        list = getAll();

        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum+= list.get(i).getMark();
        }
        return sum/list.size();
    }

    public Student getFirst(ArrayList<Student> list){
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public Student getLast(ArrayList<Student> list){
        if(!list.isEmpty()){
            return list.get(list.size()-1);
        }
        return null;
    }

}
