package com.zhp.base.ui.widget.imageview.progressbar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.facebook.drawee.drawable.ProgressBarDrawable;

public class CircleProgressBarDrawable extends ProgressBarDrawable {
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mLevel = 0;
    private int maxLevel = 10000;

    public void setLevelChange(int level) {
        onLevelChange(level);
    }

    @Override
    protected boolean onLevelChange(int level) {
        mLevel = level;
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        if (getHideWhenZero() && mLevel == 0) {
            return;
        }
        drawBar(canvas, maxLevel, getBackgroundColor());
        drawBar(canvas, mLevel, getColor());
    }

    private void drawBar(Canvas canvas, int level, int color) {
        Rect bounds = getBounds();
        float centerX = bounds.centerX();
        float centerY = bounds.centerY();
        float diff;
        if (bounds.width() > bounds.height()) {
            diff = bounds.height() * 0.06f;
        } else {
            diff = bounds.width() * 0.06f;
        }
        RectF rectF = new RectF((centerX - diff), centerY-diff,
                centerX+diff, centerY+diff);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        if (level != 0)
            canvas.drawArc(rectF, 0, (float) (level * 360 / maxLevel), false, mPaint);
    }
}

