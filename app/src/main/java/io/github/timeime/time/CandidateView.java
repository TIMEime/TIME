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

public class CandidateView extends View {
    private static final int OUT_OF_BOUNDS = -1;
    private ImeService imeService;
    private List<String> mSuggestions;
    private int mSelectedIndex;
    private int mTouchX = OUT_OF_BOUNDS;
    private Drawable mSelectionHighlight;
    private boolean mTypedWordValid;
    private Rect mBgPadding;
    private static final int MAX_SUGGESTIONS = 32;
    private static final int SCROLL_PIXELS = 20;
    private int[] mWordWidth = new int[MAX_SUGGESTIONS];
    private int[] mWordX = new int[MAX_SUGGESTIONS];
    private static final int X_GAP = 10;
    private static final List<String> EMPTY_LIST = new ArrayList<String>();
    private int mColorNormal;
    private int mColorRecommended;
    private int mColorOther;
    private int mVerticalPadding;
    private Paint mPaint;
    private boolean mScrolled;
    private int mTargetScrollX;
    private int mTotalWidth;
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
        mPaint.setStrokeWidth((float)0.1);//每個候選詞的間隔線寬度

        //用手可以滑動，這是在構造函數裡面對滑動監聽的重載，猜測，這個函數與onTouchEvent函數應該是同時起作用?
        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                    float distanceX, float distanceY) {
                mScrolled = true;
                int sx = getScrollX();//得到滑動開始的橫坐標
                sx += distanceX;//加上滑動的距離，這個滑動距離是最後一次call滑動之間的距離，很小，應該
                if (sx < 0) {
                    sx = 0;
                }
                if (sx + getWidth() > mTotalWidth) {
                    sx -= distanceX;
                }
                mTargetScrollX = sx;//記錄將要移動到的位置，後面會用到
                scrollTo(sx, getScrollY());//這是處理滑動的函數，view類的函數。後面一個參數，說明Y軸永遠不變,如果你嘗試去改變一下，經測試，太好玩了
                invalidate();//文檔中說的是使得整個VIew作廢，但是如果不用這句，會發生什麼?
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
        return mTotalWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = resolveSize(50, widthMeasureSpec);//判斷視窗大小
        Rect padding = new Rect();
        mSelectionHighlight.getPadding(padding);
        final int desiredHeight = ((int)mPaint.getTextSize()) + mVerticalPadding + padding.top + padding.bottom;
        setMeasuredDimension(measuredWidth,resolveSize(desiredHeight, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {//這是每個View對象繪制自己的函數，重載之。經測試：沒有這個函數的重載，則顯示不出字來，這個就是用來顯示字條
        if (canvas != null) {
            super.onDraw(canvas);
        }
        mTotalWidth = 0;
        if (mSuggestions == null){
            return;
        }
        if (mBgPadding == null) {
            mBgPadding = new Rect(0, 0, 0, 0);
            if (getBackground() != null) {
                getBackground().getPadding(mBgPadding);//這個View的背景，也就是整個候選區域的背景，一個Drawable類型
            }
        }
        int x = 0;//第一個詞左側為0,測試知道：這個地方能改變文字的左側開端
        final int count = mSuggestions.size();
        final int height = getHeight();
        final Rect bgPadding = mBgPadding;
        final Paint paint = mPaint;//paint的功效就和畫筆一樣
        final int touchX = mTouchX;//取得被點擊詞語的橫坐標
        final int scrollX = getScrollX();
        final boolean scrolled = mScrolled;
        final boolean typedWordValid = mTypedWordValid;
        final int y = (int) (((height - mPaint.getTextSize()) / 2) - mPaint.ascent());

        for (int i = 0; i < count; i++) {//開始一個一個地添置候選詞
            String suggestion = mSuggestions.get(i);
            float textWidth = paint.measureText(suggestion);//獲取詞語寬度
            final int wordWidth = (int) textWidth + X_GAP * 2;//整體寬度是詞語寬度加上兩倍間隙
            mWordX[i] = x;
            mWordWidth[i] = wordWidth;
            paint.setColor(mColorNormal);
            if (touchX + scrollX >= x && touchX + scrollX < x + wordWidth && !scrolled) {//保持正常輸出而不受觸摸影響的復雜條件
                if (canvas != null) {
                    canvas.translate(x, 0);//畫布轉變位置，按下候選詞後，看到的黃色區域是畫布處理的位置
                    mSelectionHighlight.setBounds(0, bgPadding.top, wordWidth, height);
                    mSelectionHighlight.draw(canvas);
                    //上面兩句是密不可分的，第一步給框，第二步畫畫
                    canvas.translate(-x, 0);
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
                canvas.drawText(suggestion, x + X_GAP, y, paint);
                paint.setColor(mColorOther);
                canvas.drawLine(x + wordWidth + 0.5f, bgPadding.top,
                        x + wordWidth + 0.5f, height + 1, paint);
                paint.setFakeBoldText(false);
            }
            x += wordWidth;
        }
        mTotalWidth = x;
        if (mTargetScrollX != getScrollX()) {
            scrollToTarget();
        }
    }

    private void scrollToTarget() {
        int sx = getScrollX();
        if (mTargetScrollX > sx) {
            sx += SCROLL_PIXELS;
            if (sx >= mTargetScrollX) {
                sx = mTargetScrollX;
                requestLayout();
            }
        } else {
            sx -= SCROLL_PIXELS;
            if (sx <= mTargetScrollX) {
                sx = mTargetScrollX;
                requestLayout();
            }
        }
        scrollTo(sx, getScrollY());
        invalidate();
    }

    public void setSuggestions(List<String> suggestions, boolean completions,boolean typedWordValid) {
        clear();
        if (suggestions != null) {
            mSuggestions = new ArrayList<String>(suggestions);
        }
        mTypedWordValid = typedWordValid;
        scrollTo(0, 0);
        mTargetScrollX = 0;
        onDraw(null);
        invalidate();
        requestLayout();
    }

    public void clear() {
        mSuggestions = EMPTY_LIST;
        mTouchX = OUT_OF_BOUNDS;
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
        mTouchX = x;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mScrolled = false;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (y <= 0) {
                    // Fling up!?
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

    public void takeSuggestionAt(float x) {
        mTouchX = (int) x;
        onDraw(null);
        if (mSelectedIndex >= 0) {
            imeService.pickSuggestionManually(mSelectedIndex);
        }
        invalidate();
    }

    private void removeHighlight() {
        mTouchX = OUT_OF_BOUNDS;
        invalidate();
    }
}
