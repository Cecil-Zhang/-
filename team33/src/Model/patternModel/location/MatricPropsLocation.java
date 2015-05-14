package Model.patternModel.location;

import Model.patternModel.ViewType;
import Model.patternModel.TestHelper;

/**
 * Created by randy on 14-5-9.
 */
public class MatricPropsLocation extends MatricLocation {
    public MatricPropsLocation(int x, int y) {
        super(x, y);
    }

    @Override
    public void setValue(int value) {
        if(ViewType.containProps(value)) {
            super.setValue(value);
        } else {
            TestHelper.testprint("MatricPropsLocation setValue not contain");
            super.setValue(3); //第一个道具
        }
    }
    /*
    -------------------------------------------------------------------------------------------------------------------
     */

    @Override
    protected void setType() {
        isPattern=false;
    }
}
