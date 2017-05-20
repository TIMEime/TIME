package io.github.timeime.time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static io.github.timeime.time.login_place.ACCOUNT;

public class MainActivity extends Activity {
    private EditText nameEditText;
    private EditText dataEditText;
    private Button addButton;
    private Button deleteButton;
    private Button logoutButton;
    private Button checkdataButton;
    private TextView accountLabel;
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountLabel=(TextView)findViewById(R.id.account_label);
        checkdataButton=(Button)findViewById(R.id.check_data_button);
        logoutButton=(Button)findViewById(R.id.logout_button);
        addButton=(Button)findViewById(R.id.add_button);
        deleteButton=(Button)findViewById (R.id.delete_button);
        nameEditText=(EditText)findViewById (R.id.name_edit_text);
        dataEditText=(EditText)findViewById (R.id.data_edit_text);

        accountLabel.setText(login_place.ACCOUNT);

        //新增資料的按鈕事件
        addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameEditText.getText().toString().equals("") || dataEditText.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"資料名稱與內容不可為空值",Toast.LENGTH_SHORT).show();
                }
                else if(dbHelper.findDataExists(nameEditText.getText().toString())) {
                    DB mDB = new DB(nameEditText.getText().toString(), dataEditText.getText().toString(), ACCOUNT);
                    dbHelper.addData(mDB);
                    nameEditText.setText("");
                    dataEditText.setText("");
                }else{
                    Toast.makeText(MainActivity.this,"此資料已經存在",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //刪除資料的按鈕事件
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameEditText.getText().toString().equals("") ){
                    Toast.makeText(MainActivity.this,"資料名稱不可為空值",Toast.LENGTH_SHORT).show();
                }else if(!dbHelper.findDataExists(nameEditText.getText().toString())) {
                    dbHelper.deleteData(nameEditText.getText().toString());
                    nameEditText.setText("");
                    dataEditText.setText("");
                }else{
                    Toast.makeText(MainActivity.this,"找不到此資料",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //登出的按鈕事件
        logoutButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, login_place.class);
                startActivity(intent);
                ACCOUNT="";
            }
        });

        //跳到資料存放列表的按鈕事件
        checkdataButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, data_place.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }
}

