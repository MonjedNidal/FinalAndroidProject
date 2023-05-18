package ps.monjed.ptuk;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddStudentActivity extends AppCompatActivity {

    
    //Monjed's Solution
    TextView addName, addMark;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        addName = findViewById(R.id.add_name);
        addMark = findViewById(R.id.add_mark);
        add = findViewById(R.id.add_btn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student(addName.getText()+"", Integer.parseInt(addMark.getText()+""));
                MyDatabase myDatabase = new MyDatabase(AddStudentActivity.this);
                myDatabase.addStudent(student);
                MainActivity.myAdapter.notifyDataSetChanged();//Monjed's Solution
                clear();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {//Monjed's Solution
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void clear(){//Monjed's Solution
        addName.setText("");
        addMark.setText("");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(AddStudentActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
