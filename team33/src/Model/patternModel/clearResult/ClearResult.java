package Model.patternModel.clearResult;

import Model.patternModel.ViewType;

import java.util.ArrayList;

/**
 * Created by randy on 14-5-10.
 */
public class ClearResult {
    private int location;  //第几个；
    private ViewType props;    //位置上的数值
    private boolean isVertical;   //横竖
    private boolean isCleared;   //是否消除
    private int length;   //消除队列的长度
    /*
    --------------------------------------------------------------------------------------------------------------------
     */

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setLocation(int value) {
       location=value;
    }
    public int getLocation() {
        return location;
    }
    public void  setDirection(boolean isVertical) {
        this.isVertical=isVertical;
    }
    public boolean getDirection() {
        return isVertical;
    }

    public boolean isCleared() {
        return isCleared;
    }

    public void setCleared(boolean isCleared) {
        this.isCleared = isCleared;
    }

}
