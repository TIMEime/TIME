package io.github.timeime.time;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class KeyboardUtil  {

    private KeyboardView keyboardView;
    private ImeService imeService;
    private Keyboard keyboardSmallLetter, keyboardNumber, keyboardCapitalLetter;
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
        keyboardView.setKeyboard(keyboardSmallLetter);
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

                    imeService.findData();

                    if(keyboardView.getKeyboard() == keyboardCapitalLetter) {
                        keyboardView.setKeyboard(keyboardSmallLetter);
                    }else {
                        keyboardView.setKeyboard(keyboardCapitalLetter);
                    }
                    break;
                case Keyboard.KEYCODE_DELETE:
                    imeService.deleteText();
					///
                    LayoutInflater layoutInflater
                            = (LayoutInflater)context
                            .getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup_layout, null);
                    final PopupWindow popupWindow = new PopupWindow(
                            popupView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

                    Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                    btnDismiss.setOnClickListener(new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            popupWindow.dismiss();
                        }});
                    popupWindow.showAsDropDown(keyboardView, Gravity.LEFT|Gravity.TOP, 10, 70);
                    ///
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    imeService.hideInputMethod();
                    break;
                case -2:
                    keyboardView.setKeyboard(keyboardNumber);
                    break;
                case 10000:
                    keyboardView.setKeyboard(keyboardSmallLetter);
                    break;
                default:
                    imeService.commitText(Character.toString((char) primaryCode));
                    imeService.setCandidatesViewShown(true);
                    imeService.onKey(Character.toString((char) primaryCode));
                    break;
            }
        }
    };
}
