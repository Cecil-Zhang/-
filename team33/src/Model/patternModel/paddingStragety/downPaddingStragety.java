package Model.patternModel.paddingStragety;

import Model.patternModel.TestHelper;
import Model.patternModel.ViewType;
import Model.patternModel.dataContainer.DataMatricReadHelper;
import Model.patternModel.location.MatricLocation;
import Model.patternModel.propsStragety.PropsStragety;
import Model.patternModel.propsStragety.TwoPropsStragety;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by randy on 14-5-13.
 */
public class downPaddingStragety implements PaddingStragety {
    private DataMatricReadHelper readHelper=null;
    private PropsStragety propsStragety;
    public downPaddingStragety(DataMatricReadHelper readHelper) {
        this.readHelper = readHelper;
        propsStragety=new TwoPropsStragety(readHelper);
    }


    @Override
    public ArrayList<MatricLocation> producePaddingList(ArrayList<MatricLocation> list) {
        //�ȶ�list���м򻯣�ͬһ�е�ȡ���±ߵ�һ��matric���;
    	TestHelper.propsTest("call the downpaddingList");
    	MatricLocation propLocation=propsStragety.produceProps(list);
    	
        list=trimArrayList(list);
        ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();
        MatricLocation temp=null;
        int x,y;
        for(Iterator<MatricLocation> iterator=list.iterator();iterator.hasNext();) {
            temp=iterator.next();
            TestHelper.testprint("the padding is from"+temp.getX()+" "+temp.getY());
            result.addAll(produceColumnList(temp));

        }
        //�ع��У�ԭ����û��û�����ɵ��ߵģ����ڼ�����ߣ�Ϊ�˲��ı�ԭ�еĿ�ܣ����ж���Ĵ���
        
        if(propLocation!=null) {
        for(int m=0;m<result.size();m++) {
        	MatricLocation tempForProps=result.get(m);
        	if(tempForProps.getX()==propLocation.getX()&&
        						tempForProps.getY()==propLocation.getY()) {
        		tempForProps.setValue(propLocation.getValue());
        		TestHelper.propsTest("change to prop in"+tempForProps.getX()+""+tempForProps.getY()+""
        																				+tempForProps.getValue());
        	}	
        }
        } else {
        	TestHelper.propsTest("dont have props");
        }

        
        return result;
    }
    /**
     * ��������list�����ڼ�����ߵ�����,���Ȱ���y��ֵ��������
     */
    Comparator<MatricLocation> comparatorForProps=new Comparator<MatricLocation>() {

		@Override
		public int compare(MatricLocation o1, MatricLocation o2) {
			// TODO Auto-generated method stub
			if(o1.getY()!=o2.getY()) {
				return o1.getY()-o2.getY();
			} else {
				return o1.getX()-o2.getX();
			}
		}
	};

    /**
     * //�ȶ�list���м򻯣�ͬһ�е�ȡ���±ߵ�һ��matric���
     * @param list   ԭʼ��list
     * @return    �򻯺��list
     */
    private ArrayList<MatricLocation> trimArrayList(ArrayList<MatricLocation> list) {
        Collections.sort(list,comparator);
        TestHelper.testprint("-------------------------------------------------");
        for(int i=0;i<list.size();i++) {
            TestHelper.testprint(list.get(i).getX()+" "+list.get(i).getY()+" ");
        }
        ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();
        int location=0;
        for(int i=0;i<list.size();i++) {

            MatricLocation temp1=list.get(i);
            if(i<list.size()-1) {
                MatricLocation temp2 = list.get(i + 1);

                if (temp1.getY() != temp2.getY()) {
                	TestHelper.propsTest("the div is from"+i+""+location);
                    temp1.setValue(i-location);   //2-0=2;
                    result.add(temp1);
                    location=i+1;
                }
            }else {
                temp1.setValue(i-location);
                result.add(temp1);
                location=i+1;
            }
        }
        return result;

    }

    /**
     * ����down��padding�Ĺ�̣�Ҫ�Ȱ���y��������ȽϺ�,���򰡣�������
     */
    private Comparator<MatricLocation> comparator=new Comparator<MatricLocation>() {
        @Override
        public int compare(MatricLocation o1, MatricLocation o2) {
            if (o1.getY() != o2.getY()) {
                return o1.getY() - o2.getY();
            } else {
                return o1.getX() - o2.getX();
            }
        }
    };

    /**
     * ��ô����location֮�����е�Ҫ���ֵ��location���б�.
     * @param location
     * @return ArrayList</MatricLocation>
     */
    private ArrayList<MatricLocation>  produceColumnList(MatricLocation location) {
        int x=location.getX();
        int y=location.getY();
        int div=location.getValue();
        int dataMatricLength=readHelper.getDataMatricLength();
        ArrayList<MatricLocation> result=new ArrayList<MatricLocation>(dataMatricLength);
        //����������㵽�ڶ��е����е㣬�����һ�еĵ��ֵ

        MatricLocation locationTemp=null;
        TestHelper.testprint("the div is"+div);
        if(x>div) {
            for (int i = x; i > div; i--) {
                locationTemp = new MatricLocation(i, y);

                location.setX(i - 1 - div);
                int value = readHelper.getViewTypeInDataMatric(location).ordinal() + 1;
                locationTemp.setValue(value);
                result.add(locationTemp);
            }
        }//���location�Ѿ���x�Ѿ�Ϊ0
        //�������ϱߵ�Ҫʹ��������õ���ֵ
        for (int i=0;i<=div;i++) {
            locationTemp=new MatricLocation(i,y);
            TestHelper.testprint("set random number"+locationTemp.getX()+" "+locationTemp.getY());
            locationTemp.setValue(productRandomNumber());

            result.add(locationTemp);
        }
        /////////////////////////show the list
 //       System.out.println("show the list ===================");
        for(int i=0;i<result.size();i++) {
 //           System.out.println(result.get(i).getValue()+" "+result.get(i).getX()+" "+result.get(i).getY()+"");
        }
        return result;
    }
    /**
     * ���������
     *
     * @return
     */
    private int productRandomNumber() {
        int result = (int) (Math.random() * 5 )+1;
        //����������
        if (result < 0 || result > readHelper.getDataMatricValueNumber()) {
            TestHelper.testprint("productRandomNumber beyond number limit");
            return 1;  //Ĭ��Ϊ1
        } else {
        	if(result==0) {
        		TestHelper.propsTest("^^^^^^^^^^^notice random is 0---");
        	}
            return result;
        }
    }
    /**
     * ����bangList��changList�е���ֵ�����changList�е�location����ʼλ��
     * @param bangList
     * @param changList
     * @return ArrayList<MatricLocation> ��changListһһ��Ӧ
     */
    public ArrayList<MatricLocation> getTheBeginLocationList(ArrayList<MatricLocation> bangList
    																		,ArrayList<MatricLocation> changList) {
    	if(bangList==null||changList==null) {
    		TestHelper.propsTest("getTheBeginLocationList is return null");
    		return null;
    	}
    	ArrayList<MatricLocation> result=new ArrayList<MatricLocation>(changList.size());
    	//�ȱ���bangList�е����е�yֵ���൱��ÿ���е�����ֵ
    	if(bangList!=null&&changList!=null) {
    	int listLength=readHelper.getDataMatricLength();
    	int[] valueList=new int[listLength];
    	//��valueList��ֵ��0
    	for(int i=0;i<listLength;i++) {
    		valueList[i]=0;
    	}
    	//����bangList����valueList��ֵ
    	for(int j=0;j<bangList.size();j++) {
    		MatricLocation temp=bangList.get(j);
    		valueList[temp.getY()]++;   //��Ӧ��y��int�����ֵ+1
    	} //valueList��ΪbangList�ж�Ӧyֵ��location�ĸ���

    	/*
    	 * ����changList����ֵresultList
    	 * 
    	 */
    	for(int m=0;m<changList.size();m++) {
    		MatricLocation temp2=changList.get(m);
    		MatricLocation resultLocation=new MatricLocation(temp2.getX(), temp2.getY());
    		int value=temp2.getValue();
    		if(value!=(ViewType.prop1.ordinal()+1)&&value!=(ViewType.prop2.ordinal()+1)) { //�����ǵ���ABʱ
    		resultLocation.setValue(temp2.getX()-valueList[temp2.getY()]);   
    		//resultLocation��ֵ�Ƕ�Ӧ��changList��y��λ�ü�ȥy���ϵ�ֵ,��ԭ����y��λ��
    		if(resultLocation.getValue()<0) {   //���С���㣬��˵����random���ɵģ�û��ԭλ��
    			resultLocation.setValue(-1);
    		}
    		} else {   //�ǵ���ABʱ,û��ԭλ��,����Ϊ-1
    			resultLocation.setValue(-1);
    		}
    		result.add(resultLocation);
    	}
    	}
    	TestHelper.propsTest("))))))))))))))))))))))))))))))))))))))))))))))))))))");
    	for(int i=0;i<result.size();i++) {
    		TestHelper.propsTest("the result is "+result.get(i).getX()+result.get(i).getY()+result.get(i).getValue());
    	}
    	return result;
    }
}
