package io.github.timeime.time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private EditText name_editText;
    private EditText data_editText;
    private Button add_button;
    private Button delete_button;
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);
    private Button button,button2;

    /*public static final String LOGTAG = "";
    private static final int REQUEST_CODE = 1;
    private  void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                Log.i(LOGTAG, "onActivityResult granted");
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button7);
        add_button=(Button)findViewById (R.id.add_button);
        delete_button=(Button)findViewById (R.id.delete_button);
        name_editText=(EditText)findViewById (R.id.name_edit_text);
        data_editText=(EditText)findViewById (R.id.data_edit_text);

        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, data_place.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        add_button.setOnClickListener(new Button.OnClickListener() {
            //存入資料
            @Override
            public void onClick(View view) {
                DB mDB = new DB(name_editText.getText().toString(),data_editText.getText().toString(),login_place.account.getText().toString());
                dbHelper.addData(mDB);
                name_editText.setText("");
                data_editText.setText("");

            }
        });

        delete_button.setOnClickListener(new Button.OnClickListener() {
            //刪除資料
            @Override
            public void onClick(View view) {
                dbHelper.deleteData(name_editText.getText().toString());
                name_editText.setText("");
                data_editText.setText("");
            }
        });
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //實例化一個Intent物件
                Intent intent = new Intent();
                //設定要start的Avtivity，第一個參數是現在的Activity，第二個參數是要開啟的Activity
                intent.setClass(MainActivity.this, login_place.class);
                //開啟另一個Activity
                startActivity(intent);
                // login_place.this.finish();
            }
        });

        // 請求SYSTEM_ALERT_WINDOW權限
        //requestAlertWindowPermission();
    }
}

