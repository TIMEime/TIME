package io.github.timeime.time;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private EditText editText;
    private EditText editText2;
    private Button addButton;
    private Button deleteButton;
    private ListView listView;
    private ArrayList<DB> mDBs = new ArrayList<DB>();
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton=(Button)findViewById (R.id.add_button);
        deleteButton=(Button)findViewById (R.id.delete_button);
        editText=(EditText)findViewById (R.id.edit_text);
        editText2=(EditText)findViewById (R.id.edit_text2);
        listView = (ListView) findViewById (R.id.main_list);

        mDBs = dbHelper.getAll();
        ListAdapter adapter = new ListAdapter(this, mDBs);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new Button.OnClickListener() {
            //存入資料
            @Override
            public void onClick(View view) {
                DB mDB = new DB(editText.getText().toString(),editText2.getText().toString());
                dbHelper.addData(mDB);
                editText.setText("");
                editText2.setText("");
                updateAdapter();
            }
        });

        deleteButton.setOnClickListener(new Button.OnClickListener() {
            //刪除資料
            @Override
            public void onClick(View view) {
                dbHelper.deleteData(editText.getText().toString());
                editText.setText("");
                updateAdapter();
            }
        });
    }

    public void updateAdapter(){
        mDBs = dbHelper.getAll();
        ListAdapter adapter = new ListAdapter(this, mDBs);
        listView.setAdapter(adapter);
    }
}