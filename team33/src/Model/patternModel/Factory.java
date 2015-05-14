package Model.patternModel;

import java.awt.Point;
import java.util.ArrayList;

import po.PatternType;

/**
 * @ClassName propFactory
 * @Description 道具工厂，负责道具的生成
 * @author 张舒贤
 * @Date 2014-5-12
 */
public interface Factory {

	public PatternType getProp(ArrayList<Point> list);
}
