package Model.patternModel.propsStragety;

import Model.patternModel.location.MatricLocation;

import java.util.ArrayList;

/**���ڵ������ɵ��ж�����
 * Created by randy on 14-5-21.
 */
public class MatricLocationContainer {
    int length;
    ArrayList<MatricLocation> list;

    /**
     * ��������
     * @param length
     */
    public MatricLocationContainer(int length) {
        this.length = length;
        list=new ArrayList<MatricLocation>(length);
    }
}
