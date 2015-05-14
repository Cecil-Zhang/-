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

/**dataMatric ��д���߶�DataMatric����д����࣬��������λ�õ�ֵ
 * Created by randy on 14-5-9.
 */
public class DataMatricwriteHelper  {
    private DataMatric dataMatric;

    public DataMatricwriteHelper() {
        this.dataMatric = DataMatric.getDataMatric();
       
    }

    /**
     * �����������ֵ��Ȼ��֪ͨ���ݸı�����
     * @param x
     * @param y
     * @return
     */
    public boolean changeValueInDataMatric(MatricValueLocation x,MatricValueLocation y) {

            //����x��y��ֵ��Ȼ��д��DataMatric
            int temp=x.getValue();
            x.setValue(y.getValue());
            y.setValue(temp);
            if(setValue(x)&&setValue(y)) {
                /////TODO;֪ͨ����DataMatric�ײ�����ݸı�������

                return true;
            } else {
                return false;
            }
    }
    /*
    -------------------------------------------------------------------------------------------------------------------
    ����ͼ���͵���
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
            //TODO:֪ͨ����������
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
