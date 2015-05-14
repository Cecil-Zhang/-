package Model.patternModel.checkClearStragety;

import Model.patternModel.clearResult.ClearResultCollection;
import Model.patternModel.location.MatricLocation;
import Model.patternModel.location.MatricValueLocation;
import java.util.ArrayList;

/**
 * Created by randy on 14-5-13.
 */
public interface CheckIfClearStragety {
    int minclearNumber=3;

    /**
     * 检查dataMatric的消除策略。
     * @param list  之前从新设置取值的点，因为只有这里可能产生消除，故只需检查这些点便可
     * @return  要消除的list
     */
    public ArrayList<MatricLocation> checkClearList(ArrayList<MatricLocation> list);
    /**
     * 判断某个是否可以形成消除。在交换了两个棋子位置时使用
     * @param location
     *
     * @return 返回它作为第几个位置，可以消除
     */
    public ClearResultCollection isCleared(MatricLocation location);

    /**
     * 判读参数是否在一个消除的相连中，返回消除的相连的所有点
     * @param location   判读的点
     * @return   可消除的点
     */
    public ArrayList<MatricLocation> clear(MatricLocation location);
    /**
     *      * 在初始化时，生成棋盘时判断是否生成连续3个的棋子

     * @param location
     * @return
     */
    public boolean isClearedWhenInit(MatricLocation location);
    /**
     * 检查list中是否有prop，并在list中加入prop爆炸的位置
     * @param list
     * @return
     */
    public ArrayList<MatricLocation> checkIfPropExist(ArrayList<MatricLocation> list);
    /**
     * 检查当前棋牌上的prop道具
     * @return
     */
    public ArrayList<MatricLocation> getPropList(); 
}
