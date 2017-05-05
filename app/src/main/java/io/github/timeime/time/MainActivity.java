package io.github.timeime.time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static io.github.timeime.time.R.id.add_button;
import static io.github.timeime.time.R.id.delete_button;

public class MainActivity extends Activity {
    private EditText nameEditText;
    private EditText dataEditText;
    private Button addButton;
    private Button deleteButton;
    private Button logoutButton;
    private Button checkdataButton;
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkdataButton=(Button)findViewById(R.id.check_data_button);
        logoutButton=(Button)findViewById(R.id.logout_button);
        addButton=(Button)findViewById (add_button);
        deleteButton=(Button)findViewById (delete_button);
        nameEditText=(EditText)findViewById (R.id.name_edit_text);
        dataEditText=(EditText)findViewById (R.id.data_edit_text);

        addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB mDB = new DB(nameEditText.getText().toString(),dataEditText.getText().toString(),login_place.ACCOUNT);
                dbHelper.addData(mDB);
                nameEditText.setText("");
                dataEditText.setText("");
            }
        });

        deleteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteData(nameEditText.getText().toString());
                nameEditText.setText("");
                dataEditText.setText("");
            }
        });
        logoutButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, login_place.class);
                startActivity(intent);
                login_place.ACCOUNT="";
            }
        });
        checkdataButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, data_place.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }
}

