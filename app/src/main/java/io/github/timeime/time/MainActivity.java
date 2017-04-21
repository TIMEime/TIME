package io.github.timeime.time;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<DB> mProducts = new ArrayList<DB>();
    private EditText editText;
    private Button button;
    DBHelper dbHelper = new DBHelper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById (R.id.button);
        editText=(EditText)findViewById (R.id.edit_text);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB newDB = new DB(editText.getText().toString());
                dbHelper.addProduct(newDB);
            }
        });
        // 寫入資料
    }
}