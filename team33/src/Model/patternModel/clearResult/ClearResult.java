package Model.patternModel.clearResult;

import Model.patternModel.ViewType;

import java.util.ArrayList;

/**
 * Created by randy on 14-5-10.
 */
public class ClearResult {
    private int location;  //�ڼ�����
    private ViewType props;    //λ���ϵ���ֵ
    private boolean isVertical;   //����
    private boolean isCleared;   //�Ƿ�����
    private int length;   //�������еĳ���
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
