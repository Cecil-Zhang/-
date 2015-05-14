package Model.patternModel.dataContainer;


import Model.patternModel.ViewType;
import Model.patternModel.checkClearStragety.CheckClearStragety;
import Model.patternModel.checkClearStragety.CheckIfClearStragety;
import Model.patternModel.location.MatricLocation;

import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

/**dataMatric�����飬����һ������֪�� �������DataMatric�Ķ�ȡ��Ĳ�������ȡλ���ϵ����ݣ��ж��Ƿ�����(����ģʽ����
 *
 * Created by randy on 14-5-9.
 */
public class DataMatricReadHelper{
    private DataMatric dataMatric;
    private int minclearNumber;
    private int length;//dataMatric�еĳ���
    private CheckIfClearStragety clearStragety;//�����Ĳ���
    public DataMatricReadHelper() {
        dataMatric= DataMatric.getDataMatric();
        minclearNumber=3;
        length=dataMatric.getLength();

    }

    /**
     * ���ݲ�����list�ĵ㣬����ڴ��йصĿ����������е�����ꡣ
     * @param list  list�е�
     * @return   ��ÿ������ĵ��list
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
    �򵥵Ķ�ȡ�����ͣ����ȣ�value�ĸ���
     */

    /**
     * ��ȡDataMatric�ײ���������ͣ�ת����ViewTypeʹ��,ʹ�ÿյ�MatricLocation
     * @param x
     * @return ViewType
     */
    public ViewType getViewTypeInDataMatric(MatricLocation x) {
        int temp=dataMatric.getDataMatricValue(x);
        return ViewType.getTypeFromValue(temp);

    }

    /**
     * ���dataMatric��ͼ��viewtypeֵ�ĸ���,ʹ��ViewType�ĸ�����������
     * @return
     */
    public int getDataMatricValueNumber() {

        return ViewType.length();
    }

    /**
     * ���dataMatric������ĳ���
     * @return
     */
    public int getDataMatricLength() {
        return dataMatric.getLength();
    }

}
 






