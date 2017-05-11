package io.github.timeime.time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class data_place  extends Activity {
    private ListView listView;
    private ArrayList<DB> mDBs = new ArrayList<DB>();
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);
    ImageButton addDataPlace;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_place);
        listView = (ListView) findViewById (R.id.main_list);
        mDBs = dbHelper.getAccountData(login_place.ACCOUNT);
        ListAdapter adapter = new ListAdapter(this, mDBs);
        listView.setAdapter(adapter);

        addDataPlace = (ImageButton) findViewById(R.id.button2);

        addDataPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(data_place.this, MainActivity.class);
                    startActivity(intent);
                }

        });
    }
}
