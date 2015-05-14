package Model.patternModel.propsStragety;

import Model.patternModel.location.MatricLocation;

import java.util.ArrayList;

/**用于道具生成的判断容器
 * Created by randy on 14-5-21.
 */
public class MatricLocationContainer {
    int length;
    ArrayList<MatricLocation> list;

    /**
     * 构造器，
     * @param length
     */
    public MatricLocationContainer(int length) {
        this.length = length;
        list=new ArrayList<MatricLocation>(length);
    }
}
