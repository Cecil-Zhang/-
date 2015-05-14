package Model.patternModel.propsStragety;

import Model.patternModel.location.MatricLocation;

import java.util.ArrayList;

/**�������ɵĲ��Խӿ�
 * Created by randy on 14-5-21.
 */
public interface PropsStragety {
    /**
     * ���ݴ�������������λ�ã����ж��Ƿ��γɵ��ߣ��������ߵ�λ�ú����ͷ���
     * @param list  ������λ�õ��б�
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
