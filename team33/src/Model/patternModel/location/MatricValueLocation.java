package Model.patternModel.location;

import Model.patternModel.ViewType;
import Model.patternModel.TestHelper;


/**
 * Created by randy on 14-5-9.
 */
public class MatricValueLocation extends MatricLocation {
    /*
      ���ӹ��캯��
     */
    public MatricValueLocation(int x, int y) {
        super(x, y);
    }

    @Override
    public void setValue(int value) {
        if(ViewType.containValue(value)) {

            super.setValue(value);
        } else {
        	
            super.setValue(value);  //-1����Ҫ�����ģ������ִ����������
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
