package Model.patternModel;

import java.awt.Point;
import java.util.ArrayList;

import po.PatternType;

/**
 * @ClassName PropFactory
 * @Description ���𷵻ظ��ֵ���
 * @author ������
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
		
		//�ж����������xֵ�Ƿ���ͬ
		boolean xEqual=true;
		int firstX=x.get(0).intValue();
		for(int i=1;i<x.size();i++){
			int temp=x.get(i).intValue();
			if(firstX!=temp){
				xEqual=false;
				break;
			}
		}
		
		//���x�������ͬ����4���������ɵ���A��5���������ɵ���B
		if(xEqual&&(x.size()==4)){
			return PatternType.propA;
		}else if(xEqual&&(x.size()==5)){
			return PatternType.propB;
		}
		
		//����y���꣬��x���귽����ͬ
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
