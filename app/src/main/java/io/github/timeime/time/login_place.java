package io.github.timeime.time;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_place extends Activity {
    private Button button, button2;
    public static EditText account,password;
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    DialogView is=new DialogView();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_place);
        button = (Button) findViewById(R.id.button3);
        button2 = (Button) findViewById(R.id.button4);
        account=(EditText) findViewById(R.id.account_edit_text);
        password=(EditText) findViewById(R.id.password_edit_text);
        ActivityCompat.requestPermissions(login_place.this,
                new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        ActivityCompat.requestPermissions(login_place.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //實例化一個Intent物件
                Intent intent = new Intent();
                //設定要start的Avtivity，第一個參數是現在的Activity，第二個參數是要開啟的Activity
                intent.setClass(login_place.this, signup_place.class);
                //開啟另一個Activity
                startActivity(intent);
                // login_place.this.finish();
            }
        });
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                if(dbHelper.findAccount(account.getText().toString())){
                    Toast.makeText(login_place.this,"此帳號不存在", Toast.LENGTH_SHORT).show();
                }else{
                    if(dbHelper.findPassword(account.getText().toString(),password.getText().toString())){
                        i=1;
                    }else{
                        Toast.makeText(login_place.this,"密碼錯誤", Toast.LENGTH_SHORT).show();
                        password.setText("");
                    }
                }
                if(i==1) {
                    Intent intent = new Intent();
                    intent.setClass(login_place.this, MainActivity.class);
                    startActivity(intent);
                    login_place.this.finish();
                }
            }
        });
    }
}