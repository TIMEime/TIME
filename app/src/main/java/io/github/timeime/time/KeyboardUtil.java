//鍵盤的類別
package io.github.timeime.time;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class KeyboardUtil  {

    private KeyboardView keyboardView;
    private ImeService imeService;
    private Keyboard keyboardSmallLetter, keyboardNumber, keyboardCapitalLetter,keyboardSpecial1,keyboardSpecial2;
    Context context;

    public KeyboardUtil(ImeService imeService1, KeyboardView keyboardView1) {
        super();
        context = imeService1.getApplicationContext();
        keyboardView = keyboardView1;
        keyboardView.setOnKeyboardActionListener(listener);
        imeService = imeService1;
        keyboardCapitalLetter = new Keyboard(imeService.getApplicationContext(), R.xml.keyboard_capital_letter);
        keyboardNumber = new Keyboard(imeService.getApplicationContext(), R.xml.keyboard_number);
        keyboardSmallLetter = new Keyboard(imeService.getApplicationContext(), R.xml.keyboard_small_letter);
        keyboardSpecial1=new Keyboard(imeService.getApplicationContext(), R.xml.keyboard_special_1);
        keyboardSpecial2=new Keyboard(imeService.getApplicationContext(), R.xml.keyboard_special_2);
        keyboardView.setKeyboard(keyboardSpecial1);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(true);
    }

    private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            switch (primaryCode) {
                case Keyboard.KEYCODE_SHIFT:
                    if(keyboardView.getKeyboard() == keyboardCapitalLetter) {
                        keyboardView.setKeyboard(keyboardSmallLetter);
                    }else if(keyboardView.getKeyboard() == keyboardSmallLetter){
                        keyboardView.setKeyboard(keyboardCapitalLetter);
                    }else if(keyboardView.getKeyboard() ==keyboardSpecial1){
                        keyboardView.setKeyboard(keyboardSpecial2);
                    }else{
                        keyboardView.setKeyboard(keyboardSpecial1);
                    }
                    break;
                case Keyboard.KEYCODE_DELETE:
                    imeService.deleteText();
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    imeService.hideInputMethod();
                    break;
                case -2:
                    keyboardView.setKeyboard(keyboardNumber);
                    break;
                case 10000:
                    keyboardView.setKeyboard(keyboardSpecial1);
                    break;
                case -20000:
                    keyboardView.setKeyboard(keyboardSmallLetter);
                    break;
                case 1000:
                    imeService.commitText("TдT");
                    break;
                case 1001:
                    imeService.commitText("^__^");
                    break;
                case 1002:
                    imeService.commitText(":-(");
                    break;
                case 1003:
                    imeService.commitText("◞‸◟");
                    break;
                case 1004:
                    imeService.commitText(">∀<");
                    break;
                case 1005:
                    imeService.commitText("・ε・");
                    break;
                case 1006:
                    imeService.commitText("òロó");
                    break;
                case 1007:
                    imeService.commitText("T^T");
                    break;
                case 1008:
                    imeService.commitText("ꉺɷꉺ");
                    break;
                case 1009:
                    imeService.commitText("^O^");
                    break;
                case 1010:
                    imeService.commitText("★");
                    break;
                case 1011:
                    imeService.commitText("☆");
                    break;
                case 1012:
                    imeService.commitText("✿");
                    break;
                case 1014:
                    imeService.commitText("❀");
                    break;
                case 1015:
                    imeService.commitText("㐃");
                    break;
                case 1016:
                    imeService.commitText("㊣");
                    break;
                case 1017:
                    imeService.commitText("◎");
                    break;
                case 1018:
                    imeService.commitText("╳");
                    break;
                case 1019:
                    imeService.commitText("㐃");
                    break;
                case 1020:
                    imeService.commitText("〒");
                    break;
                case 9999:
                    LayoutInflater layoutInflater
                            = (LayoutInflater)context
                            .getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup_layout, null);
                    final PopupWindow popupWindow = new PopupWindow(
                            popupView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    ListView listView;
                    TextView textView;
                    ArrayList<DB> mDBs = new ArrayList<DB>();
                    DBHelper dbHelper = new DBHelper(context, null, null, 1);
                    listView = (ListView) popupView.findViewById (R.id.list);
                    textView=(TextView)popupView.findViewById(R.id.textView5);
                    mDBs = dbHelper.getAccountData(login_place.ACCOUNT);
                    ListAdapterDataName adapter = new ListAdapterDataName(context, mDBs);
                    listView.setAdapter(adapter);
                    textView.setText(login_place.ACCOUNT);
                    AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            TextView text=(TextView)view.findViewById(R.id.text_list111);
                            imeService.findData(text.getText().toString());
                            popupWindow.dismiss();
                        }
                    };
                    listView.setOnItemClickListener(itemListener);
                    Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                    if(login_place.ACCOUNT.equals("")){
                        Toast.makeText(context,"請先登入帳號",Toast.LENGTH_SHORT).show();
                        Intent intent = context.getPackageManager().getLaunchIntentForPackage("io.github.timeime.time");
                        context.startActivity(intent);
                    }else{
                        popupWindow.showAtLocation(keyboardView.getRootView(),Gravity.TOP,0,0);
                    }
                    btnDismiss.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            popupWindow.dismiss();
                        }});
                    break;
                default:
                    imeService.commitText(Character.toString((char) primaryCode));
                    //imeService.setCandidatesViewShown(true);
                    imeService.onKey(Character.toString((char) primaryCode));
                    break;
            }
        }
    };
}
