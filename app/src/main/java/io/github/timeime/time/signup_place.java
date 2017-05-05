package io.github.timeime.time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup_place extends Activity {
    int i=0;
    private Button signButton,backButton;
    private EditText account,password,confirmPassword;
    public  DBHelper dbHelper = new DBHelper(this, null, null, 1);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_place);
        signButton = (Button) findViewById(R.id.sign_button);
        backButton=(Button) findViewById(R.id.back_button);
        account=(EditText)findViewById(R.id.sign_account_edit);
        password=(EditText)findViewById(R.id.sign_password_edit);
        confirmPassword=(EditText)findViewById(R.id.sign_confirm_password_edit);

        signButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.findAccount(account.getText().toString())) {
                    if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                        Toast.makeText(signup_place.this,"密碼與確認密碼不符", Toast.LENGTH_SHORT).show();
                        password.setText("");
                        confirmPassword.setText("");
                    }else {
                        AccountDB mDB = new AccountDB(account.getText().toString(), password.getText().toString()
                                );
                        dbHelper.addAccount(mDB);
                        i = 1;
                    }
                }else{
                    account.setText("");
                    Toast.makeText(signup_place.this,"此帳號已存在",Toast.LENGTH_SHORT).show();
                }
                if (i == 1) {
                    Intent intent = new Intent();
                    intent.setClass(signup_place.this, login_place.class);
                    startActivity(intent);
                    signup_place.this.finish();
                }
            }
        });
        backButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(signup_place.this, login_place.class);
                startActivity(intent);
                signup_place.this.finish();
            }
        });

}


}