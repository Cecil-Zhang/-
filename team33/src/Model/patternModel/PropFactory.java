package Model.patternModel;

import java.awt.Point;
import java.util.ArrayList;

import po.PatternType;

/**
 * @ClassName PropFactory
 * @Description 负责返回各种道具
 * @author 张舒贤
 * @Date 2014-5-12
 */
public class PropFactory implements Factory {

	@Override
	public PatternType getProp(ArrayList<Point> list) {
		// TODO Auto-generated method stub
		ArrayList<Integer> x=new ArrayList<Integer>();
		ArrayList<Integer> y=new ArrayList<Integer>();
		for(Point p:list){
			x.add(new Integer(p.x));
			y.add(new Integer(p.y));
		}
		
		//判断所有坐标的x值是否相同
		boolean xEqual=true;
		int firstX=x.get(0).intValue();
		for(int i=1;i<x.size();i++){
			int temp=x.get(i).intValue();
			if(firstX!=temp){
				xEqual=false;
				break;
			}
		}
		
		//如果x坐标均相同，则4个坐标生成道具A，5个坐标生成道具B
		if(xEqual&&(x.size()==4)){
			return PatternType.propA;
		}else if(xEqual&&(x.size()==5)){
			return PatternType.propB;
		}
		
		//处理y坐标，与x坐标方法相同
		boolean yEqual=true;
		int firstY=y.get(0).intValue();
		for(int i=1;i<y.size();i++){
			int temp=y.get(i).intValue();
			if(firstY!=temp){
				yEqual=false;
				break;
			}
		}
		
		if(yEqual&&(y.size()==4)){
			return PatternType.propA;
		}else if(yEqual&&(y.size()==5)){
			return PatternType.propB;
		}
		return PatternType.pattern1;
	}

}
