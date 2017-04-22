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
    private Button button;
    private Button button2;
    private ListView listView;
    private ArrayList<DB> mDBs = new ArrayList<DB>();
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById (R.id.button);
        button2=(Button)findViewById (R.id.button2);
        editText=(EditText)findViewById (R.id.edit_text);
        listView = (ListView) findViewById (R.id.main_list);

        mDBs = dbHelper.getAll();
        ListAdapter adapter = new ListAdapter(this, mDBs);
        listView.setAdapter(adapter);

        button.setOnClickListener(new Button.OnClickListener() {
            //存入資料
            @Override
            public void onClick(View view) {
                DB mDB = new DB(editText.getText().toString());
                dbHelper.addData(mDB);
                editText.setText("");
                updateAdapter();
            }
        });

        button2.setOnClickListener(new Button.OnClickListener() {
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