package io.github.timeime.time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

    public class WelcomeActivity extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.welcome_place);
            mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 1100); //2秒跳轉
        }

        private static final int GOTO_MAIN_ACTIVITY = 0;
        private Handler mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case GOTO_MAIN_ACTIVITY:
                        Intent intent = new Intent();
                        //將原本Activity的換成MainActivity
                        intent.setClass(WelcomeActivity.this, login_place.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
    }

