package Model.patternModel.dataContainer;

import Model.patternModel.location.MatricLocation;
import Model.patternModel.location.MatricPropsLocation;
import Model.patternModel.location.MatricValueLocation;
import Model.patternModel.observerPattern.Observable;
import Model.patternModel.observerPattern.Observer;
import Model.patternModel.TestHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**dataMatric 的写入者对DataMatric进行写入的类，交换两个位置的值
 * Created by randy on 14-5-9.
 */
public class DataMatricwriteHelper  {
    private DataMatric dataMatric;

    public DataMatricwriteHelper() {
        this.dataMatric = DataMatric.getDataMatric();
       
    }

    /**
     * 交换两个点的值，然后通知数据改变啦。
     * @param x
     * @param y
     * @return
     */
    public boolean changeValueInDataMatric(MatricValueLocation x,MatricValueLocation y) {

            //交换x和y的值，然后写入DataMatric
            int temp=x.getValue();
            x.setValue(y.getValue());
            y.setValue(temp);
            if(setValue(x)&&setValue(y)) {
                /////TODO;通知啦，DataMatric底层的数据改变啦。。

                return true;
            } else {
                return false;
            }
    }
    /*
    -------------------------------------------------------------------------------------------------------------------
    设置图案和道具
     */
    public boolean setProps(MatricPropsLocation x) {
        if(dataMatric.setValue(x)) {
            return true;
        } else {
            return false;
        }

    }
    public boolean setValue(MatricLocation x){

        if(dataMatric.setValue(x)){
            //TODO:通知。。。。。
            return true;
        } else {

            return false;
        }
    }
    public boolean setValueList(Collection<MatricLocation> list) {
        for(Iterator<MatricLocation> itr=list.iterator(); itr.hasNext();) {
        	
            if(setValue(itr.next())) {
                TestHelper.testprint("setValueList sucess.......");
            }
        }
        return true;
    }
}
