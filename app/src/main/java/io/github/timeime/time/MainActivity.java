package io.github.timeime.time;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;





        public class MainActivity extends AppCompatActivity {
        }
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                	//mainactivity是class name
                builder.setTitle("this is標題");
                builder.setMessage("here are內容");
                builder.show();


        }






