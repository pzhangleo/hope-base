package hope.base.ui.widget.imageview;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.RoundingParams;

/**
 * 显示圆形图片的控件
 * Created by zhangpeng on 15/12/31.
 */
public class CropCircleImageView extends WebImageView{
    public CropCircleImageView(Context context) {
        super(context);
        init();
    }

    public CropCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CropCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        getHierarchy().setRoundingParams(RoundingParams.asCircle());
    }

}
