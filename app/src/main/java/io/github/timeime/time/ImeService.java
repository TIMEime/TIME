package io.github.timeime.time;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ImeService extends InputMethodService {
    private List<String>suggestionlist; // 当前候选词表
    private Hashtable<String, List<String>> data; // 词典数据
    private KeyboardView mkeyView;
    private CandidateView mCandView;

    @Override
    public View onCreateInputView() {
        View mkeyView = LayoutInflater.from(this).inflate(
                R.layout.layout_keyboardview, null);
        new KeyboardUtil(this, (KeyboardView) mkeyView.findViewById(R.id.keyboardView));
        return mkeyView;
    }

    @Override
    public View onCreateCandidatesView() {
        mCandView = new CandidateView(this);
        mCandView.setService(this);
        return mCandView;
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

    public void pickSuggestionManually(int mSelectedIndex){
        getCurrentInputConnection().commitText(suggestionlist.get(mSelectedIndex), 0); // 往输入框输出内容
        setCandidatesViewShown(false); // 隐藏 CandidatesView
    }
    public void onInitializeInterface() { //InputMethodService在启动时，系统会调用该方法，具体内容下回再表
        // 初始化 词典数据

        data = new Hashtable<String, List<String>>();
        List<String> list = new ArrayList<String>();
        list.add("abc");
        list.add("ab");
        list.add("aaif(){}else{}");
        data.put("a", list);

        list = new ArrayList<String>();
        list.add("bb");
        list.add("bbss");
        data.put("b", list);
        list=new ArrayList<String>();
        list.add("密碼");
        list.add("電子郵件");
        data.put("",list);
    }
    public void onKey(CharSequence text){
        // 根据按下的按钮设置候选词列表
        suggestionlist = data.get(text);
        mCandView.setSuggestions(suggestionlist,true,true);
    }
}
