package hope.base.ui.widget.imageview.progressbar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.facebook.drawee.drawable.ProgressBarDrawable;

public class VideoProgressBarDrawable extends ProgressBarDrawable {
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
        int bWidth = Math.abs(bounds.right - bounds.left);
        int bHeight = Math.abs(bounds.top - bounds.bottom);
        float size = (float) (((float) (bWidth > bHeight ? bHeight : bWidth)) * .1);
        RectF rectF = new RectF((float) (bounds.right * .5 - size), (float) (bounds.bottom * .5 - size),
                (float) (bounds.right * .5 + size), (float) (bounds.bottom * .5) + size);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        if (level != 0)
            canvas.drawArc(rectF, 0, (float) (level * 360 / maxLevel), true, mPaint);
    }

    @Override
    public int getColor() {
        return Color.WHITE;
    }

    @Override
    public int getBackgroundColor() {
        return 0x40FFFFFF;
    }
}

