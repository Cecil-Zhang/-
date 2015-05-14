package Control.patternControl;

import java.awt.Point;
import java.util.ArrayList;

import po.PatternType;

/**
 * @ClassName propClear
 * @Description 根据道具坐标生成需要消除的坐标列表
 * @author 张舒贤
 * @Date 2014-5-9
 */
public class propClear {

	/**
	 * @title generateClears
	 * @Description 根据坐标处的道具类型返回需要消除的坐标列表
	 * @param p 坐标
	 * @param type 道具类型
	 * @return 消除的坐标列表
	 */
	public ArrayList<Point> generateClears(Point p,PatternType type){
		ArrayList<Point> list = null;
		if (type==PatternType.propA){
			list=generateAclears(p);
		}else if(type==PatternType.propB){
			list=generateBclears(p);
		}
		return list;
	}
	
	/**
	 * @title generateAclears
	 * @Description 生成道具A周围的8个坐标
	 * @param p 道具A坐标
	 * @return 坐标列表
	 */
	private ArrayList<Point> generateAclears(Point p){
		ArrayList<Point> list=new ArrayList<Point>();
		boolean left=true;
		boolean right=true;
		boolean top=true;
		boolean bottom=true;
		if (p.x==0){
			left=false;
		}
		if (p.x==8){
			right=false;
		}
		if (p.y==0){
			top=false;
		}
		if (p.y==8){
			bottom=false;
		}
		if (left&&top){
			list.add(new Point(p.x-1,p.y-1));
		}
		if (left){
			list.add(new Point(p.x-1,p.y));
		}
		if (left&&bottom){
			list.add(new Point(p.x-1,p.y+1));
		}
		if (right&&top){
			list.add(new Point(p.x+1,p.y-1));
		}
		if (right){
			list.add(new Point(p.x+1,p.y));
		}
		if (right&&bottom){
			list.add(new Point(p.x+1,p.y+1));
		}
		if (top){
			list.add(new Point(p.x,p.y-1));
		}
		if (bottom){
			list.add(new Point(p.x,p.y+1));
		}
		return list;
	}
	
	/**
	 * @title generateBclears
	 * @Description 生成道具B所在的水平垂直两条线上的坐标列表
	 * @param p 道具B坐标
	 * @return 坐标列表
	 */
	private ArrayList<Point> generateBclears(Point p){
		ArrayList<Point> list=new ArrayList<Point>();
		for(int i=0;i<9;i++){
			list.add(new Point(i,p.y));
		}
		for(int i=0;i<9;i++){
			if(i!=p.y){
				list.add(new Point(p.x,i));
			}
		}
		return list;
	}
}
