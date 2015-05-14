package Model.patternModel.location;

import Model.patternModel.ViewType;
import Model.patternModel.TestHelper;


/**
 * Created by randy on 14-5-9.
 */
public class MatricValueLocation extends MatricLocation {
    /*
      钩子构造函数
     */
    public MatricValueLocation(int x, int y) {
        super(x, y);
    }

    @Override
    public void setValue(int value) {
        if(ViewType.containValue(value)) {

            super.setValue(value);
        } else {
        	
            super.setValue(value);  //-1代表要消除的，界面层执行消除动作
            TestHelper.testprint("^^^^MatricValueLocation setValue not contain^^^^^^^^"
            							+ "										^^^");
        }
    }
       /*
    ------------------------------------------------------------------------------------------------------------------
     */

    @Override
    protected void setType() {
        isPattern=true;
        }
}
