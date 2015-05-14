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
		///�ȱ������ƣ����ӵ����ͬ��ͼ����pattern
		ArrayList<MatricLocation> twoList=getTwoPatternList();
		//����list�����������Ĵ���
		MatricLocation handle1,handle2;
		for(int i=0;i<twoList.size();i=i+2) {
			handle1=twoList.get(i);
			handle2=twoList.get(i+1);
			allList.addAll(isCanCleared(handle1, handle2));
		}
		//�������
		int i=(int)Math.random()*(allList.size()/2);
		TestHelper.propsTest("--------"+i);
		result.add(allList.get(i*2));
		result.add(allList.get(i*2+1));
		return result;
	}
	/**
	 * �ж�����������pattern���������ҿ�ʼ�ж��Ƿ�����ͬ��pattern
	 * @param location1
	 * @param location2
	 * @return ArrayList<MatricLocation>
	 */
	private ArrayList<MatricLocation> isCanCleared(MatricLocation location1,
																			MatricLocation location2) {
		ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();
		//�ж������������Ĺ�ϵ
		int location1X,location1Y,location2X,location2Y;
		location1X=location1.getX();
		location1Y=location1.getY();
		location2X=location2.getX();
		location2Y=location2.getY();
		MatricLocation temp1,temp2;
		int locationValue;
		if(location1Y==location2Y) {  //���Ǳ�����һ�У�x��ͬ��y��ͬ
			//ֻ������,�ж�1X��2X��������,�ж�1Y�ж�������
			//����location1һ����location2֮ǰ����֮��
			if(location1X!=0) { //���ϱ�
				if(location1Y!=0) {//������ 
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
			if(location2X!=reader.getDataMatricLength()-1) { //���±�
				if(location2Y!=0) {  //�����
					temp1=new MatricLocation(location2X+1, location2Y-1);
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location2X+1, location1Y);
						result.add(temp1);
						result.add(temp2);
					}
				}
				if(location2Y!=reader.getDataMatricLength()-1) { //���ұ�
					temp1=new MatricLocation(location2X+1, location2Y+1);
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location2X+1, location2Y);
						result.add(temp1);
						result.add(temp2);
					}
				}
				if(location1X>=2) {  //�����ϱߵ�
					temp1=new MatricLocation(location1X-2, location1Y);
					TestHelper.propsTest("�����·��е���һ��");
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location1X-1, location2Y);
						result.add(temp1);
						result.add(temp2);
					}
					
				}
				if(location2X<=reader.getDataMatricLength()-2) {  //�����±�
					temp1=new MatricLocation(location2X+2, location2Y);
					TestHelper.propsTest("�����·��е���һ��");
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
		if(location1X==location2X) { //��һ��,x��ͬ��y��ͬ
			if(location1Y!=0) { //�����
				if(location1X!=0) { //���ϱ�
					
					temp1=new MatricLocation(location1X-1, location1Y-1);
					TestHelper.propsTest("kaitong����һ��");
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location1X, location1Y-1);
						result.add(temp1);
						result.add(temp2);
					}
					
				}
				if(location1X!=reader.getDataMatricLength()-1) {  //���±�
					temp1=new MatricLocation(location1X+1, location1Y-1);
					TestHelper.propsTest("��xiazuo����һ��");
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location1X, location1Y-1);
						result.add(temp1);
						result.add(temp2);
					}
				}
				
			}
			if(location2Y!=reader.getDataMatricLength()-1) { //���ұ�
				if(location2X!=0) {//������
					temp1=new MatricLocation(location2X-1, location2Y+1);
					TestHelper.propsTest("����shang����һ��");
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location2X, location2Y+1);
						result.add(temp1);
						result.add(temp2);
					}
					
				}
				if(location2X!=reader.getDataMatricLength()-1) { //������
					temp1=new MatricLocation(location2X+1, location2Y+1);
					TestHelper.propsTest("�����µ���һ��");
					locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
					if(locationValue==location1.getValue()&&
								locationValue==location2.getValue()) {
						temp2=new MatricLocation(location2X, location2Y+1);
						result.add(temp1);
						result.add(temp2);
					}
				}
			}
			if(location1Y>=2) {  //�������
				temp1=new MatricLocation(location1X, location1Y-2);
				TestHelper.propsTest("_____"+"���ұ�");
				locationValue=reader.getViewTypeInDataMatric(temp1).ordinal()+1;
				if(locationValue==location1.getValue()&&
							locationValue==location2.getValue()) {
					temp2=new MatricLocation(location1X, location1Y-1);
					result.add(temp1);
					result.add(temp2);
				}
				
			}
			if(location2Y<=reader.getDataMatricLength()-2) { //�����ұ�
				temp1=new MatricLocation(location2X, location2Y+2);
				TestHelper.propsTest("........"+"���ұ�");
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
	 * ��õ�ǰ����������ͬpattern
	 * @return
	 */
	private ArrayList<MatricLocation> getTwoPatternList() {
		ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();
		int length=reader.getDataMatricLength();
		MatricLocation temp1,temp2;
		int value1,value2;
		for(int i=0;i<length;i++) {
			for(int j=0;j<length;j++) {
				//ֻ����±ߺ��ұߵ�ֵ
				if(j!=length-1) { //�������ұ�
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
				if(i!=length-1) {  //���±�
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
