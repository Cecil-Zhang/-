package Model.patternModel.tipStragety;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Model.patternModel.dataContainer.DataMatricReadHelper;
import Model.patternModel.location.MatricLocation;
import Model.patternModel.TestHelper;

public class TwoTipStragety implements TipStragety{
	DataMatricReadHelper reader;
	public TwoTipStragety(DataMatricReadHelper reader) {
		this.reader=reader;
	}
	@Override
	public ArrayList<MatricLocation> getTips() {
		// TODO Auto-generated method stub
		ArrayList<MatricLocation> allList=new ArrayList<MatricLocation>();
		ArrayList<MatricLocation> result=new ArrayList<MatricLocation>(2);
		///先遍历棋牌，获得拥有相同的图案的pattern
		ArrayList<MatricLocation> twoList=getTwoPatternList();
		//遍历list，两个两个的处理。
		MatricLocation handle1,handle2;
		for(int i=0;i<twoList.size();i=i+2) {
			handle1=twoList.get(i);
			handle2=twoList.get(i+1);
			allList.addAll(isCanCleared(handle1, handle2));
		}
		//随机生成
		int i=(int)Math.random()*(allList.size()/2);
		TestHelper.propsTest("--------"+i);
		result.add(allList.get(i*2));
		result.add(allList.get(i*2+1));
		return result;
	}
	/**
	 * 判断两个相连的pattern的上下左右开始判断是否有相同的pattern
	 * @param location1
	 * @param location2
	 * @return ArrayList<MatricLocation>
	 */
	private ArrayList<MatricLocation> isCanCleared(MatricLocation location1,
																			MatricLocation location2) {
		ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();
		//判断两个的相连的关系
		int location1X,location1Y,location2X,location2Y;
		location1X=location1.getX();
		location1Y=location1.getY();
		location2X=location2.getX();
		location2Y=location2.getY();
		MatricLocation temp1,temp2;
		int locationValue;
		if(location1Y==location2Y) {  //这是表明是一列，x不同，y相同
			//只有上下,判断1X和2X看有上下,判断1Y判断有左右
			//由于location1一定在location2之前，或之左
			if(location1X!=0) { //有上边
				if(location1Y!=0) {//有上左 
					temp1=new MatricLocation(location1X-1, location1Y-1);
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location1X-1, location1Y);
						result.add(temp1);
						result.add(temp2);
					}
						
				}
				if(location1Y!=reader.getDataMatricLength()-1) {
					temp1=new MatricLocation(location1X-1, location1Y+1);
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location1X-1, location1Y);
						result.add(temp1);
						result.add(temp2);
					}
				}
				
			}
			if(location2X!=reader.getDataMatricLength()-1) { //有下边
				if(location2Y!=0) {  //有左边
					temp1=new MatricLocation(location2X+1, location2Y-1);
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location2X+1, location1Y);
						result.add(temp1);
						result.add(temp2);
					}
				}
				if(location2Y!=reader.getDataMatricLength()-1) { //有右边
					temp1=new MatricLocation(location2X+1, location2Y+1);
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location2X+1, location2Y);
						result.add(temp1);
						result.add(temp2);
					}
				}
				if(location1X>=2) {  //有最上边的
					temp1=new MatricLocation(location1X-2, location1Y);
					TestHelper.propsTest("有最下方列的是一行");
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location1X-1, location2Y);
						result.add(temp1);
						result.add(temp2);
					}
					
				}
				if(location2X<=reader.getDataMatricLength()-2) {  //有最下边
					temp1=new MatricLocation(location2X+2, location2Y);
					TestHelper.propsTest("有最下方列的是一行");
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location2X+1, location2Y);
						result.add(temp1);
						result.add(temp2);
					}
				}
			}
		}
		if(location1X==location2X) { //是一行,x相同，y不同
			if(location1Y!=0) { //有左边
				if(location1X!=0) { //有上边
					
					temp1=new MatricLocation(location1X-1, location1Y-1);
					TestHelper.propsTest("kaitong的是一行");
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location1X, location1Y-1);
						result.add(temp1);
						result.add(temp2);
					}
					
				}
				if(location1X!=reader.getDataMatricLength()-1) {  //有下边
					temp1=new MatricLocation(location1X+1, location1Y-1);
					TestHelper.propsTest("有xiazuo的是一行");
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location1X, location1Y-1);
						result.add(temp1);
						result.add(temp2);
					}
				}
				
			}
			if(location2Y!=reader.getDataMatricLength()-1) { //有右边
				if(location2X!=0) {//有右上
					temp1=new MatricLocation(location2X-1, location2Y+1);
					TestHelper.propsTest("有右shang的是一行");
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location2X, location2Y+1);
						result.add(temp1);
						result.add(temp2);
					}
					
				}
				if(location2X!=reader.getDataMatricLength()-1) { //有右下
					temp1=new MatricLocation(location2X+1, location2Y+1);
					TestHelper.propsTest("有右下的是一行");
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location2X, location2Y+1);
						result.add(temp1);
						result.add(temp2);
					}
				}
			}
			if(location1Y>=2) {  //有最左边
				temp1=new MatricLocation(location1X, location1Y-2);
				TestHelper.propsTest("_____"+"最右边");
				locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
				if(locationValue==location1.getValue()&&
							locationValue==location2.getValue()) {
					temp2=new MatricLocation(location1X, location1Y-1);
					result.add(temp1);
					result.add(temp2);
				}
				
			}
			if(location2Y<=reader.getDataMatricLength()-2) { //有最右边
				temp1=new MatricLocation(location2X, location2Y+2);
				TestHelper.propsTest("........"+"最右边");
				locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
				if(locationValue==location1.getValue()&&
							locationValue==location2.getValue()) {
					temp2=new MatricLocation(location2X, location2Y+1);
					result.add(temp1);
					result.add(temp2);
				}
				
			}
			
		}
		return result;
	}	
	/**
	 * 获得当前的两个的相同pattern
	 * @return
	 */
	private ArrayList<MatricLocation> getTwoPatternList() {
		ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();
		int length=reader.getDataMatricLength();
		MatricLocation temp1,temp2;
		int value1,value2;
		for(int i=0;i<length;i++) {
			for(int j=0;j<length;j++) {
				//只检查下边和右边的值
				if(j!=length-1) { //可以有右边
					temp1=new MatricLocation(i, j);
					temp2=new MatricLocation(i, j+1);
					value1=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					value2=reader.getViewTypeInDataMatric(temp2).ordinal()+1;
					if(value1==value2) {
						temp1.setValue(value1);
						temp2.setValue(value2);
						result.add(temp1);
						result.add(temp2);
					}
				}
				if(i!=length-1) {  //有下边
					temp1=new MatricLocation(i, j);
					temp2=new MatricLocation(i+1, j);
					value1=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					value2=reader.getViewTypeInDataMatric(temp2).ordinal()+1;
					if(value1==value2) {
						temp1.setValue(value1);
						temp2.setValue(value2);
						result.add(temp1);
						result.add(temp2);
					}
				}
			}
		}
		return result;
	}

}
