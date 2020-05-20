package widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import com.zues.ruiyu.R;

public class RYTitleView extends LinearLayout {
    private TextView tvTitle;
    private ImageView ivBack;
    String titleText;

    public RYTitleView(Context context) {
        super(context);
    }

    public RYTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.ry_title_view, this);
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_left);
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RYTitleView);
        try {
            titleText = typedArray.getString(R.styleable.RYTitleView_rytv_title_text);
        }finally {
            typedArray.recycle();
        }
        tvTitle.setText(titleText);
    }

    public void setBack(OnClickListener onClickListener){
        ivBack.setOnClickListener(onClickListener);
    }
    public void setTitleText(String titleText){
        tvTitle.setText(titleText);
    }
}
