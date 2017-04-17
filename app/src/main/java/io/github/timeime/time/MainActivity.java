package io.github.timeime.time;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> lunch;
    AlertDialog.Builder listDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        listDialog();
        listDialog.show();
    }
    public void initData() {
        lunch = new ArrayList<>();
        lunch.add("1");
        lunch.add("2");
        lunch.add("3");
        lunch.add("4");
        lunch.add("5");
        lunch.add("6");
    }
    public void listDialog(){
        listDialog =new AlertDialog.Builder(MainActivity.this)
                .setItems(lunch.toArray(new String[lunch.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = lunch.get(which);
                        Toast.makeText(getApplicationContext(), "123" + name, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
