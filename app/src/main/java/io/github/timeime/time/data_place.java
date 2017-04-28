package io.github.timeime.time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by user on 2017/4/25.
 */

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
        ImageButton button2 = (ImageButton) findViewById(R.id.button2);
        //設定button監聽事件
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //實例化一個Intent物件
                    Intent intent = new Intent();
                    //設定要start的Avtivity，第一個參數是現在的Activity，第二個參數是要開啟的Activity
                    intent.setClass(data_place.this, MainActivity.class);
                    //開啟另一個Activity
                    startActivity(intent);
                }

        });

}

}
