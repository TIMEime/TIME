package io.github.timeime.time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class data_place  extends Activity {
    private ListView listView;
    private ArrayList<DB> mDBs = new ArrayList<DB>();
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);
    private Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_place);
        listView = (ListView) findViewById (R.id.main_list);
        mDBs = dbHelper.getAll();
        ListAdapter adapter = new ListAdapter(this, mDBs);
        listView.setAdapter(adapter);
        ImageButton button_data_place = (ImageButton) findViewById(R.id.button2);

        button_data_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(data_place.this, MainActivity.class);
                    startActivity(intent);
                }

        });
    }
}
