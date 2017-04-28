package io.github.timeime.time;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class DialogView extends View{
    Paint mPaint;
    public DialogView(Context context){
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Paint paint=new Paint();
        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(160,160,150,paint);
        paint.setColor(Color.BLUE);
        Rect rect =new Rect(100,110,120,130);
        canvas.drawRect(rect ,paint);
        paint.setColor(Color.GREEN);
        RectF rectf=new RectF(200,110,220,130);
        canvas.drawRoundRect(rectf,7,7,paint);
        paint.setColor(Color.YELLOW);
        RectF oval=new RectF(50,150,270,250);
        canvas.drawArc(oval,180,-180,true,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Andy",160,350,paint);


    }

}
