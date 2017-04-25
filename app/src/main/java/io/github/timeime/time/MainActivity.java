package io.github.timeime.time;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private EditText name_editText;
    private EditText data_editText;
    private ImageButton add_button;
    private ImageButton delete_button;
    private ListView listView;
    private ArrayList<DB> mDBs = new ArrayList<DB>();
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_button=(ImageButton)findViewById (R.id.add_button);
        delete_button=(ImageButton)findViewById (R.id.delete_button);
        name_editText=(EditText)findViewById (R.id.name_edit_text);
        data_editText=(EditText)findViewById (R.id.data_edit_text);
        listView = (ListView) findViewById (R.id.main_list);

        mDBs = dbHelper.getAll();
        ListAdapter adapter = new ListAdapter(this, mDBs);
        listView.setAdapter(adapter);

        add_button.setOnClickListener(new Button.OnClickListener() {
            //存入資料
            @Override
            public void onClick(View view) {
                DB mDB = new DB(name_editText.getText().toString(),data_editText.getText().toString());
                dbHelper.addData(mDB);
                name_editText.setText("");
                data_editText.setText("");
                updateAdapter();
            }
        });

        delete_button.setOnClickListener(new Button.OnClickListener() {
            //刪除資料
            @Override
            public void onClick(View view) {
                dbHelper.deleteData(name_editText.getText().toString());
                name_editText.setText("");
                data_editText.setText("");
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

