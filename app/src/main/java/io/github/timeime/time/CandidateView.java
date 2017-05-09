package io.github.timeime.time;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.scrollX;
import static android.R.attr.width;
import static android.R.attr.x;

public class CandidateView extends View {
    private static final int OUT_OF_BOUNDS = -1;
    private ImeService imeService;
    private List<String> mSuggestions;
    private int mSelectedIndex;
    private int mTouchY = OUT_OF_BOUNDS;
    private Drawable mSelectionHighlight;
    private boolean mTypedWordValid;
    private Rect mBgPadding;
    private static final int MAX_SUGGESTIONS = 32;
    private static final int SCROLL_PIXELS = 20;
    private int[] mWordHeight = new int[MAX_SUGGESTIONS];
    private int[] mWordY = new int[MAX_SUGGESTIONS];
    private static final int Y_GAP = 10;
    private static final List<String> EMPTY_LIST = new ArrayList<String>();
    private int mColorNormal;
    private int mColorRecommended;
    private int mColorOther;
    private int mVerticalPadding;
    private Paint mPaint;
    private boolean mScrolled;
    private int mTargetScrollY;
    private int mTotalHeight;
    private GestureDetector mGestureDetector;

    public CandidateView(Context context) {
        super(context);
        mSelectionHighlight = context.getResources().getDrawable(
                android.R.drawable.list_selector_background);//getResouces這個函數用來得到這個應用程序的所有資源,就連android自帶的資源也要如此
        mSelectionHighlight.setState(new int[] {//mSelectionHighlight類型是Drawable,而Drawable設置狀態就是這樣
                android.R.attr.state_enabled,//這行如果去掉，點擊候選詞的時候是灰色，但是也可以用
                android.R.attr.state_focused,//用處不明。。。。
                android.R.attr.state_window_focused,//這行如果去掉，當點擊候選詞的時候背景不會變成橙色
                android.R.attr.state_pressed//點擊候選詞語時候背景顏色深淺的變化，不知深層意義是什麼？
        });
        Resources r = context.getResources();
        setBackgroundColor(r.getColor(R.color.candidate_background));
        mColorNormal = r.getColor(R.color.candidate_normal);//這個顏色，是非首選詞的顏色
        mColorRecommended = r.getColor(R.color.candidate_recommended);//這個是顯示字體的顏色
        mColorOther = r.getColor(R.color.candidate_other);//這個是候選詞語分割線的顏色
        mVerticalPadding = r.getDimensionPixelSize(R.dimen.candidate_vertical_padding);
        mPaint = new Paint();//畫筆
        mPaint.setColor(mColorNormal);
        mPaint.setAntiAlias(true);//抗鋸齒
        mPaint.setTextSize(r.getDimensionPixelSize(R.dimen.candidate_font_height));//候選詞字大小
        mPaint.setStrokeWidth((float)0.2);//每個候選詞的間隔線寬度

        //用手可以滑動，這是在構造函數裡面對滑動監聽的重載，猜測，這個函數與onTouchEvent函數應該是同時起作用?
        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2,float distanceX, float distanceY) {
                mScrolled = true;
                int sy = getScrollY();
                sy += distanceY;
                if (sy < 0) {
                    sy = 0;
                }
                if (sy + getHeight() > mTotalHeight) {
                    sy -= distanceY;
                }
                mTargetScrollY = sy;
                scrollTo(getScrollX(), sy);
                invalidate();
                return true;
            }
        });
        setHorizontalFadingEdgeEnabled(true);//拖動時刻左右兩邊的淡出效果
        setWillNotDraw(false);//複寫了onDraw所以要清除
        setHorizontalScrollBarEnabled(false);//關閉平行滾動條
        setVerticalScrollBarEnabled(false);//關閉垂直滾動條
    }

    public void setService(ImeService listener) {//自己定義的廢柴函數，使得私有變量mService的值得以改變
        imeService = listener;
    }

    @Override
    public int computeHorizontalScrollRange() {//給別的類用的廢柴函數
        return mTotalHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Rect padding = new Rect();
        mSelectionHighlight.getPadding(padding);
        //final int desiredHeight = ((int)mPaint.getTextSize()) + mVerticalPadding + padding.top + padding.bottom;
        setMeasuredDimension(resolveSize(50, widthMeasureSpec),resolveSize(1000, heightMeasureSpec));
    }

    //這是每個View對象繪制自己的函數，重載之。經測試：沒有這個函數的重載，則顯示不出字來，這個就是用來顯示字條
    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null) {
            super.onDraw(canvas);
        }
        mTotalHeight = 0;
        if (mSuggestions == null){
            return;
        }
        if (mBgPadding == null) {
            mBgPadding = new Rect(0, 0, 0, 0);
            if (getBackground() != null) {
                getBackground().getPadding(mBgPadding);//這個View的背景，也就是整個候選區域的背景，一個Drawable類型
            }
        }
        int y = 0;
        final int count = mSuggestions.size();
        final int width = getWidth();
        final Rect bgPadding = mBgPadding;
        final Paint paint = mPaint;
        final int touchY = mTouchY;
        final int scrollY = getScrollY();
        final boolean scrolled = mScrolled;
        final boolean typedWordValid = mTypedWordValid;
        final int x = 0;

        for (int i = 0; i < count; i++) {//開始一個一個地添置候選詞
            Paint.FontMetrics fm=paint.getFontMetrics();
            String suggestion = mSuggestions.get(i);
            final int wordHeight = (int)Math.ceil(fm.descent-fm.top)+2 + Y_GAP * 2;//整體寬度是詞語寬度加上兩倍間隙
            mWordY[i] = y;
            mWordHeight[i] = wordHeight;
            paint.setColor(mColorNormal);
            if (touchY + scrollY >= y && touchY + scrollY < y + wordHeight && !scrolled) {//保持正常輸出而不受觸摸影響的復雜條件
                if (canvas != null) {
                    canvas.translate(0, y);//畫布轉變位置，按下候選詞後，看到的黃色區域是畫布處理的位置
                    mSelectionHighlight.setBounds(bgPadding.left, 0, width, wordHeight);
                    mSelectionHighlight.draw(canvas);
                    //上面兩句是密不可分的，第一步給框，第二步畫畫
                    canvas.translate(0, -y);
                }
                mSelectedIndex = i;
            }

            if (canvas != null) {
                if ((i == 1 && !typedWordValid) || (i == 0 && typedWordValid)) {
                    paint.setFakeBoldText(true);
                    paint.setColor(mColorRecommended);
                } else if (i != 0) {
                    paint.setColor(mColorOther);
                }
                canvas.drawText(suggestion, x, y+Y_GAP, paint);
                paint.setColor(mColorOther);
                canvas.drawLine(bgPadding.left,y + wordHeight + 0.5f ,
                        width + 1, y + wordHeight + 0.5f, paint);
                paint.setFakeBoldText(false);
            }
            y += wordHeight;
        }
        mTotalHeight = y;
        if (mTargetScrollY != getScrollY()) {
            scrollToTarget();
        }
    }

    private void scrollToTarget() {
        int sy = getScrollY();
        if (mTargetScrollY > sy) {
            sy += SCROLL_PIXELS;
            if (sy >= mTargetScrollY) {
                sy = mTargetScrollY;
                requestLayout();
            }
        } else {
            sy -= SCROLL_PIXELS;
            if (sy <= mTargetScrollY) {
                sy = mTargetScrollY;
                requestLayout();
            }
        }
        scrollTo(getScrollX(), sy);
        invalidate();
    }

    public void setSuggestions(List<String> suggestions, boolean completions,boolean typedWordValid) {
        clear();
        if (suggestions != null) {
            mSuggestions = new ArrayList<String>(suggestions);
        }
        mTypedWordValid = typedWordValid;
        scrollTo(0, 0);
        mTargetScrollY = 0;
        onDraw(null);
        invalidate();
        requestLayout();
    }

    public void clear() {
        mSuggestions = EMPTY_LIST;
        mTouchY = OUT_OF_BOUNDS;
        mSelectedIndex = -1;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        if (mGestureDetector.onTouchEvent(me)) {
            return true;
        }
        int action = me.getAction();
        int x = (int) me.getX();
        int y = (int) me.getY();
        mTouchY = y;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mScrolled = false;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (x <= 0) {
                    if (mSelectedIndex >= 0) {
                        imeService.pickSuggestionManually(mSelectedIndex);
                        mSelectedIndex = -1;
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (!mScrolled) {
                    if (mSelectedIndex >= 0) {
                        imeService.pickSuggestionManually(mSelectedIndex);
                    }
                }
                mSelectedIndex = -1;
                removeHighlight();
                requestLayout();
                break;
        }
        return true;
    }

    private void removeHighlight() {
        mTouchY = OUT_OF_BOUNDS;
        invalidate();
    }
}