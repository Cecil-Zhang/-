package Model.patternModel.propsStragety;

import Model.patternModel.location.MatricLocation;

import java.util.ArrayList;

/**道具生成的策略接口
 * Created by randy on 14-5-21.
 */
public interface PropsStragety {
    /**
     * 根据传进来的消除的位置，来判断是否形成道具，并将道具的位置和类型返回
     * @param list  消除的位置的列表
     * @return
     */
    public MatricLocation produceProps(ArrayList<MatricLocation> list);
    /**
     * 
     * @param p
     * @return
     */
    public ArrayList<MatricLocation> generateAclears(MatricLocation p);
    /**
     * 
     * @param p
     * @return
     */
    public ArrayList<MatricLocation> generateBclears(MatricLocation p);
}
