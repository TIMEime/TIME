package io.github.timeime.time;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;

public class login_place extends Activity {
    static String ACCOUNT="";//存帳號的全域變數
    private Button loginButton,signupButton,helpButton;//登入and註冊的按鈕
    private EditText accountEdit,passwordEdit;//帳號and密碼的輸入框
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);//開啟資料庫

    protected void onCreate(Bundle savedInstanceState) {
        ACCOUNT="";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_place);
        loginButton = (Button) findViewById(R.id.login_button);
        signupButton = (Button) findViewById(R.id.signup_button);
        accountEdit=(EditText) findViewById(R.id.account_edit_text);
        passwordEdit=(EditText) findViewById(R.id.password_edit_text);
        helpButton=(Button) findViewById(R.id.help_button);

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
                if(accountEdit.getText().toString().equals("") || passwordEdit.getText().toString().equals("")){
                    Toast.makeText(login_place.this,"請輸入帳號密碼", Toast.LENGTH_SHORT).show();
                }
                else if(dbHelper.findAccount(accountEdit.getText().toString())){
                    Toast.makeText(login_place.this,"此帳號不存在", Toast.LENGTH_SHORT).show();
                }else{
                    if(dbHelper.findPassword(accountEdit.getText().toString(),passwordEdit.getText().toString())){
                        i=1;
                        ACCOUNT=accountEdit.getText().toString();
                    }else{
                        Toast.makeText(login_place.this,"密碼錯誤", Toast.LENGTH_SHORT).show();
                        passwordEdit.setText("");
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
        helpButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                help();
            }
        });
    }

    private void help() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("請先註冊帳號後登入，帳號可為任何字元，所以也可以當作資料類型的名稱，用以區分存入的資料。\n" +
                "登入後輸入資料名稱與資料內容存入，刪除資料只需輸入資料名稱後按下刪除即可。\n" +
                "之後開啟TIME輸入法的鍵盤方可使用。")
                .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog about_dialog = builder.create();
        about_dialog.show();
    }
}