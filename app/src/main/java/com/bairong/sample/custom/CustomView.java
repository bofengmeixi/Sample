package com.bairong.sample.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.bairong.sample.R;

/**
 * Created by zhangwei on 17/6/12.
 */

public class CustomView extends View {

    private int as;
    private Bitmap imagesrc;
    private int imagePadding;
    private String text;
    private int textPadding;
    private int imageloc;
    private int textSize;
    private Paint paint;
    private TextPaint textPaint;

    private Rect rect;

    private int mWidth;
    private int mHeight;

    private final int MIN_SIZE = 12;

    /**
     * 图片伸缩模式常量 fillXY
     */
    private static final int SCALE_TYPE_FILLXY = 0;
    /**
     * 图片伸缩模式常量 center
     */
    private static final int SCALE_TYPE_CENTER = 1;

    public CustomView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            switch (typedArray.getIndex(i)) {
                case R.styleable.CustomView_imagesrc:
                    imagesrc = BitmapFactory.decodeResource(context.getResources(), typedArray.getResourceId(i, 0));
                    break;
                case R.styleable.CustomView_imagepadding:
                    imagePadding = typedArray.getDimensionPixelSize(i, 0);
                    break;
                case R.styleable.CustomView_text:
                    text = typedArray.getString(i);
                    break;
                case R.styleable.CustomView_textpadding:
                    textPadding = typedArray.getDimensionPixelSize(i, 0);
                    break;
                case R.styleable.CustomView_imageloc:
                    imageloc = typedArray.getInt(i, 0);
                    break;
                case R.styleable.CustomView_textsize:
                    textSize=typedArray.getDimensionPixelSize(i,0);
                    break;
            }
        }
        typedArray.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new TextPaint(paint);
        rect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int desired = getPaddingLeft() + getPaddingRight() + 2 * imagePadding;
            desired += imagesrc != null ? imagesrc.getWidth() : 0;
            width = Math.max(MIN_SIZE, desired);
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(desired, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int desired = getPaddingTop() + getPaddingBottom() + imagePadding * 2;
            desired += imagesrc != null ? imagesrc.getHeight() : 0;

            if (text != null) {
                paint.setTextSize(textSize);
                Paint.FontMetrics fontMetrics = paint.getFontMetrics();
                int textHeight = (int) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
                desired += textHeight + textPadding * 2;
            }
            height = Math.max(MIN_SIZE, desired);
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(desired, heightSize);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingLeft();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingTop();

        if (text != null) {
            paint.setTextSize(textSize);
            paint.setAlpha(255);
            paint.setColor(getResources().getColor(R.color.colorPrimary));

            int left = getPaddingLeft() + textPadding;
            int right = mWidth - getPaddingRight() - textPadding;
            int bottom = mHeight - getPaddingBottom();

            Paint.FontMetrics fm = paint.getFontMetrics();
            int textHeight = (int) Math.ceil(fm.descent - fm.ascent);


            String msg = TextUtils.ellipsize(text, textPaint, right - left, TextUtils.TruncateAt.END).toString();
            float textWidth = paint.measureText(msg);
            canvas.drawText(msg, (right - left - textWidth) / 2, bottom - fm.descent, paint);
            rect.bottom -= (textHeight + textPadding * 2);
        }

        if (imagesrc != null) {
            rect.left += imagePadding;
            rect.right -= imagePadding;
            rect.top += imagePadding;
            rect.bottom -= imagePadding;

            paint.setAlpha(255);
            if (imageloc == SCALE_TYPE_FILLXY) {
                canvas.drawBitmap(imagesrc, null, rect, paint);
            } else if (imageloc == SCALE_TYPE_CENTER) {
                int bw=imagesrc.getWidth();
                int bh=imagesrc.getHeight();
                if (bw<rect.right-rect.left){
                    rect.left+=(rect.right-rect.left-bw)/2;
                    rect.right=rect.left+bw;
                }
                if (bh<rect.bottom-rect.top){
                    rect.top+=(rect.bottom-rect.top-bh)/2;
                    rect.bottom=rect.top+bh;
                }
                canvas.drawBitmap(imagesrc,null,rect,paint);
            }
        }
    }
}
