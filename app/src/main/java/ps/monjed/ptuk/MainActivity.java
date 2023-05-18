package ps.monjed.ptuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    
    
    //Monjed's Solution
    ArrayList<Student> students;
    static MyAdapter myAdapter;
    ListView list;
    MyDatabase myDatabase;
    boolean isSorted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = new MyDatabase(this);

//        students.add(new Student(33, "Monjed", 99));
//        students.add(new Student(44, "Sami", 99));
//        students.add(new Student(66, "Wael", 99));
        list = findViewById(R.id.list);

        if(isSorted){
            students = myDatabase.getSorted();
        }else {
            students = myDatabase.getAll();
        }
        myAdapter = new MyAdapter();
        list.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_average){
            Toast.makeText(this,"The Average = "+myDatabase.getAvg(), Toast.LENGTH_SHORT).show();
        }else if(id == R.id.menu_add){
            Intent intent = new Intent(MainActivity.this,AddStudentActivity.class);
            startActivity(intent);
        }else if(id == R.id.menu_first){
            goToDetails(R.id.menu_first);
            return true;
        }else if(id == R.id.menu_last){
            goToDetails(R.id.menu_last);
            return true;
        }else if(id == R.id.menu_sort){
            students = myDatabase.getSorted();
            myAdapter.notifyDataSetChanged();
            isSorted = true;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToDetails(int id) {
        Student student = null;
        if(id == R.id.menu_first){
            student = myDatabase.getFirst(students);
        }else if(id == R.id.menu_last){
            student = myDatabase.getLast(students);
        }

        if(student != null){
            Intent intent = new Intent(MainActivity.this,Details.class);
            intent.putExtra("name", student.getName());
            intent.putExtra("mark", student.getMark());

            startActivity(intent);
        }
    }

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public Object getItem(int i) {
            return students.get(i);
        }

        @Override
        public long getItemId(int i) {
            return students.get(i).getId();
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            @SuppressLint("ViewHolder")
            View v = getLayoutInflater().inflate(R.layout.list_item, viewGroup, false);
            TextView name, mark;
            name = v.findViewById(R.id.item_name);
            mark = v.findViewById(R.id.item_mark);

            name.setText(students.get(i).getName());
            mark.setText(String.valueOf(students.get(i).getMark()));

            v.setOnLongClickListener(view1 -> {
                PopupMenu popup = new PopupMenu(MainActivity.this, view1);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.popup_delete) {
                        if(myDatabase.delete(students.get(i).getId())){
                            notifyDataSetChanged();
                            recreate();
                        }
                    }
                    return false;
                });
                popup.show();
                return true;
            });

            return v;
        }
    }
}
