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
        //先对list进行简化，同一列的取最下边的一个matric便可;
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
        //重构中，原来是没有没有生成道具的，现在加入道具，为了不改变原有的框架，进行额外的处理
        
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
     * 用于排序list，用于加入道具的排序,优先安置y的值进行排序
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
     * //先对list进行简化，同一列的取最下边的一个matric便可
     * @param list   原始的list
     * @return    简化后的list
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
     * 按照down的padding的规程，要先按照y进行排序比较好,升序啊！！！！
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
     * 获得传入的location之上所有的要填充值的location的列表.
     * @param location
     * @return ArrayList</MatricLocation>
     */
    private ArrayList<MatricLocation>  produceColumnList(MatricLocation location) {
        int x=location.getX();
        int y=location.getY();
        int div=location.getValue();
        int dataMatricLength=readHelper.getDataMatricLength();
        ArrayList<MatricLocation> result=new ArrayList<MatricLocation>(dataMatricLength);
        //处理传入坐标点到第二行的所有点，获得上一行的点的值

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
        }//最后location已经的x已经为0
        //处理最上边的要使用随机数得到的值
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
     * 产生随机数
     *
     * @return
     */
    private int productRandomNumber() {
        int result = (int) (Math.random() * 5 )+1;
        //谨慎。。。
        if (result < 0 || result > readHelper.getDataMatricValueNumber()) {
            TestHelper.testprint("productRandomNumber beyond number limit");
            return 1;  //默认为1
        } else {
        	if(result==0) {
        		TestHelper.propsTest("^^^^^^^^^^^notice random is 0---");
        	}
            return result;
        }
    }
    /**
     * 根据bangList和changList中的数值，获得changList中的location的起始位置
     * @param bangList
     * @param changList
     * @return ArrayList<MatricLocation> 和changList一一对应
     */
    public ArrayList<MatricLocation> getTheBeginLocationList(ArrayList<MatricLocation> bangList
    																		,ArrayList<MatricLocation> changList) {
    	if(bangList==null||changList==null) {
    		TestHelper.propsTest("getTheBeginLocationList is return null");
    		return null;
    	}
    	ArrayList<MatricLocation> result=new ArrayList<MatricLocation>(changList.size());
    	//先遍历bangList中的所有的y值，相当于每个列的下落值
    	if(bangList!=null&&changList!=null) {
    	int listLength=readHelper.getDataMatricLength();
    	int[] valueList=new int[listLength];
    	//给valueList赋值初0
    	for(int i=0;i<listLength;i++) {
    		valueList[i]=0;
    	}
    	//遍历bangList，给valueList赋值
    	for(int j=0;j<bangList.size();j++) {
    		MatricLocation temp=bangList.get(j);
    		valueList[temp.getY()]++;   //对应的y的int数组的值+1
    	} //valueList中为bangList中对应y值得location的个数

    	/*
    	 * 遍历changList，赋值resultList
    	 * 
    	 */
    	for(int m=0;m<changList.size();m++) {
    		MatricLocation temp2=changList.get(m);
    		MatricLocation resultLocation=new MatricLocation(temp2.getX(), temp2.getY());
    		int value=temp2.getValue();
    		if(value!=(ViewType.prop1.ordinal()+1)&&value!=(ViewType.prop2.ordinal()+1)) { //当不是道具AB时
    		resultLocation.setValue(temp2.getX()-valueList[temp2.getY()]);   
    		//resultLocation的值是对应的changList的y的位置减去y列上的值,即原来的y的位置
    		if(resultLocation.getValue()<0) {   //如果小于零，则说明是random生成的，没有原位置
    			resultLocation.setValue(-1);
    		}
    		} else {   //是道具AB时,没有原位置,设置为-1
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
