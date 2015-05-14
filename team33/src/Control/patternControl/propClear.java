package Control.patternControl;

import java.awt.Point;
import java.util.ArrayList;

import po.PatternType;

/**
 * @ClassName propClear
 * @Description ���ݵ�������������Ҫ�����������б�
 * @author ������
 * @Date 2014-5-9
 */
public class propClear {

	/**
	 * @title generateClears
	 * @Description �������괦�ĵ������ͷ�����Ҫ�����������б�
	 * @param p ����
	 * @param type ��������
	 * @return �����������б�
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
	 * @Description ���ɵ���A��Χ��8������
	 * @param p ����A����
	 * @return �����б�
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
	 * @Description ���ɵ���B���ڵ�ˮƽ��ֱ�������ϵ������б�
	 * @param p ����B����
	 * @return �����б�
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
