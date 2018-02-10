package com.example.baselibrary.photomanager;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;


public class TouchImageView extends ImageView {

    private final static int DRAG = 1;
    private final static int SCALE = 2;
    float oldRotation = 0;
    private PointF startPoint = new PointF(); // 初始点
    private Matrix mCurrentMatrix = new Matrix(); // 图片初始的matrix值
    private Matrix mMatrix = new Matrix(); // 一个临时的matrix
    private PointF midPointF; // 中心点
    private float midDistance; // 两点间的距离
    private int type = 0;
    private boolean canClick;

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchImageView(Context context) {
        super(context);
    }

    public Matrix getMMatrix() {
        return mCurrentMatrix;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK; // 得到动作的类型
        // 只有低8位有效,&255即可
        switch (action) {
            case MotionEvent.ACTION_DOWN: // 手指按下
                float x = event.getX();
                float y = event.getY();
                startPoint.set(x, y);
                mCurrentMatrix.set(getImageMatrix()); // 图片没有移动的位置
                type = DRAG;
                canClick = true;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:// 屏幕上已经有一根手指,再按下一根手指
                mCurrentMatrix.set(getImageMatrix()); // 图片没有缩放的大小
                midPointF = getMidPointF(event);
                midDistance = getMidDistance(event);
                oldRotation = rotation(event);
                type = SCALE;
                canClick = false;
                break;

            case MotionEvent.ACTION_MOVE: // 手指一动
                if (type == DRAG) { // 移动
                    // 将原先没有移动的matrix放进临时的matrix
                    mMatrix.set(mCurrentMatrix);
                    float dx = event.getX() - startPoint.x;
                    float dy = event.getY() - startPoint.y;
                    mMatrix.postTranslate(dx, dy);
                    if (dx > 5 || dy > 5 || dx < -5 || dy < -5) {
//					LogOut.d("onTouchEvent:","dx:"+dx+",dy:"+dy);
                        canClick = false;
                    }
                } else if (type == SCALE) { // 缩放
                    mMatrix.set(mCurrentMatrix); // 设置当前的
                    float distance = getMidDistance(event); // 得到当前两个手指间的距离
                    float sx = distance / midDistance; // 得到缩放的倍数
                    //旋转
                    float rotation = rotation(event) - oldRotation;
                    if (rotation > 10) {
                        mMatrix.postRotate(rotation, midPointF.x, midPointF.y);// 旋轉
                    } else {
                        //缩放
                        mMatrix.postScale(sx, sx, midPointF.x, midPointF.y);
                    }
                    canClick = false;
                }

                break;
            case MotionEvent.ACTION_UP: // 手指弹起
                type = 0;
                if (canClick) {
                    performClick();
                }
                break;
            case MotionEvent.ACTION_POINTER_1_UP: // 弹起一根手指,屏幕上还有一根手指
                type = 0; // 手指弹起就置为0
                break;

            default:
                break;
        }
        setImageMatrix(mMatrix); // 将拖动后的matrix设置到图片
        return true;
    }

    /**
     * 获取中心点
     *
     * @param event
     * @return
     */
    private PointF getMidPointF(MotionEvent event) {
        // (后面的手指 + 前面的手指)/2
        float x = (event.getX(1) + event.getX(0)) / 2;
        float y = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(x, y);
    }

    /**
     * 计算两点之间的距离
     *
     * @param event
     * @return
     */
    private float getMidDistance(MotionEvent event) {

        float dx = event.getX(1) - event.getX(0);
        float dy = event.getX(1) - event.getX(0);

        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    // 取旋转角度
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

}
