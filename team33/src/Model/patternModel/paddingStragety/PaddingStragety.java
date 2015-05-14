package Model.patternModel.paddingStragety;

import Model.patternModel.location.MatricLocation;

import java.util.ArrayList;

/**填充的策略算法。。。
 * Created by randy on 14-5-13.
 */
public interface PaddingStragety {
    /**
     * 填充算法
     * @param list
     * @return
     */
    public ArrayList<MatricLocation> producePaddingList(ArrayList<MatricLocation> list);
    /**
     * 
     * @param bangList
     * @param changList
     * @return
     */
    public ArrayList<MatricLocation> getTheBeginLocationList(ArrayList<MatricLocation> bangList
			,ArrayList<MatricLocation> changList);
}
