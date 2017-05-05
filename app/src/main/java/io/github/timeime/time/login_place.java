package io.github.timeime.time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_place extends Activity {
    static String ACCOUNT="";//存帳號的全域變數
    private Button loginButton,signupButton;//登入and註冊的按鈕
    private EditText account,password;//帳號and密碼的輸入框
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);//開啟資料庫

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_place);
        loginButton = (Button) findViewById(R.id.login_button);
        signupButton = (Button) findViewById(R.id.signup_button);
        account=(EditText) findViewById(R.id.account_edit_text);
        password=(EditText) findViewById(R.id.password_edit_text);

        //註冊按鈕按下的事件
        signupButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(login_place.this, signup_place.class);
                startActivity(intent);
                login_place.this.finish();
            }
        });

        //登入按鈕按下的事件
        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                if(dbHelper.findAccount(account.getText().toString())){
                    Toast.makeText(login_place.this,"此帳號不存在", Toast.LENGTH_SHORT).show();
                }else{
                    if(dbHelper.findPassword(account.getText().toString(),password.getText().toString())){
                        i=1;
                        ACCOUNT=account.getText().toString();
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