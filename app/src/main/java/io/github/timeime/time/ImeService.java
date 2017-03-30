package io.github.timeime.time;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.view.LayoutInflater;
import android.view.View;

public class ImeService extends InputMethodService {


    @Override
    public View onCreateInputView() {
        View mkeyView = LayoutInflater.from(this).inflate(
                R.layout.layout_keyboardview, null);
        new KeyboardUtil(this, (KeyboardView) mkeyView.findViewById(R.id.keyboardView));
        return mkeyView;
    }

    @Override
    public View onCreateCandidatesView() {
        return null;
    }

    public void commitText(String data) {
        getCurrentInputConnection().commitText(data, 0); // 往輸入框送出內容
        setCandidatesViewShown(false); // 隱藏 CandidatesView
    }

    public void deleteText() {
        getCurrentInputConnection().deleteSurroundingText(1, 0);
    }

    public void hideInputMethod() {
        hideWindow();
    }
}
