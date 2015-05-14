package Model.patternModel.dataContainer;


import Model.patternModel.ViewType;
import Model.patternModel.checkClearStragety.CheckClearStragety;
import Model.patternModel.checkClearStragety.CheckIfClearStragety;
import Model.patternModel.location.MatricLocation;

import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

/**dataMatric的秘书，他的一切她都知道 。负责对DataMatric的读取类的操作，读取位置上的数据，判读是否消除(策略模式）等
 *
 * Created by randy on 14-5-9.
 */
public class DataMatricReadHelper{
    private DataMatric dataMatric;
    private int minclearNumber;
    private int length;//dataMatric中的长度
    private CheckIfClearStragety clearStragety;//消除的策略
    public DataMatricReadHelper() {
        dataMatric= DataMatric.getDataMatric();
        minclearNumber=3;
        length=dataMatric.getLength();

    }

    /**
     * 根据参数的list的点，获得于此有关的可消除的所有点的坐标。
     * @param list  list中点
     * @return   获得可消除的点的list
     */
    public ArrayList<MatricLocation> checkClearList(ArrayList<MatricLocation> list) {
        chooseClearStragety();
        return clearStragety.checkClearList(list);
    }
    public ArrayList<MatricLocation> checkpropList(ArrayList<MatricLocation> list) {
    	chooseClearStragety();
    	return clearStragety.checkIfPropExist(list);
    }
    private void chooseClearStragety() {
        clearStragety=new CheckClearStragety(this);
    }

    public boolean isClearedWhenInit(MatricLocation location) {
        chooseClearStragety();
        return clearStragety.isClearedWhenInit(location);
    }
    public ArrayList<MatricLocation> getPropList() {
    	chooseClearStragety();
    	return clearStragety.getPropList();
    }
    /*
    ------------------------------------------------------------------------------------------------------------------------
 	public ArrayList<Matri

 
  /*
    --------------------------------------------------------------------------------------------------------------------
    简单的读取，类型，长度，value的个数
     */

    /**
     * 获取DataMatric底层的数据类型，转换成ViewType使用,使用空的MatricLocation
     * @param x
     * @return ViewType
     */
    public ViewType getViewTypeInDataMatric(MatricLocation x) {
        int temp=dataMatric.getDataMatricValue(x);
        return ViewType.getTypeFromValue(temp);

    }

    /**
     * 获得dataMatric的图案viewtype值的个数,使用ViewType的个数代替啦。
     * @return
     */
    public int getDataMatricValueNumber() {

        return ViewType.length();
    }

    /**
     * 获得dataMatric的数组的长度
     * @return
     */
    public int getDataMatricLength() {
        return dataMatric.getLength();
    }

}
 






