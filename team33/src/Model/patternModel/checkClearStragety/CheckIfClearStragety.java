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
     * ���dataMatric���������ԡ�
     * @param list  ֮ǰ��������ȡֵ�ĵ㣬��Ϊֻ��������ܲ�����������ֻ������Щ����
     * @return  Ҫ������list
     */
    public ArrayList<MatricLocation> checkClearList(ArrayList<MatricLocation> list);
    /**
     * �ж�ĳ���Ƿ�����γ��������ڽ�������������λ��ʱʹ��
     * @param location
     *
     * @return ��������Ϊ�ڼ���λ�ã���������
     */
    public ClearResultCollection isCleared(MatricLocation location);

    /**
     * �ж������Ƿ���һ�������������У��������������������е�
     * @param location   �ж��ĵ�
     * @return   �������ĵ�
     */
    public ArrayList<MatricLocation> clear(MatricLocation location);
    /**
     *      * �ڳ�ʼ��ʱ����������ʱ�ж��Ƿ���������3��������

     * @param location
     * @return
     */
    public boolean isClearedWhenInit(MatricLocation location);
    /**
     * ���list���Ƿ���prop������list�м���prop��ը��λ��
     * @param list
     * @return
     */
    public ArrayList<MatricLocation> checkIfPropExist(ArrayList<MatricLocation> list);
    /**
     * ��鵱ǰ�����ϵ�prop����
     * @return
     */
    public ArrayList<MatricLocation> getPropList(); 
}
