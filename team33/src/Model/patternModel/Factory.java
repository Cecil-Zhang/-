package Model.patternModel;

import java.awt.Point;
import java.util.ArrayList;

import po.PatternType;

/**
 * @ClassName propFactory
 * @Description ���߹�����������ߵ�����
 * @author ������
 * @Date 2014-5-12
 */
public interface Factory {

	public PatternType getProp(ArrayList<Point> list);
}
