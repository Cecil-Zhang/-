package Model.patternModel.propsStragety;

import Model.patternModel.ViewType;
import Model.patternModel.dataContainer.DataMatricReadHelper;
import Model.patternModel.location.MatricLocation;
import Model.patternModel.TestHelper;

import java.awt.Point;
import java.util.*;

import org.omg.CosNaming.IstringHelper;

/**道具生成的具体策略类，负责生成道具A和道具B
 * Created by randy on 14-5-21.
 */
public class TwoPropsStragety implements PropsStragety {
	private DataMatricReadHelper readHelper;
    ArrayList<ArrayList<MatricLocation>> colAllThreeList =new ArrayList<ArrayList<MatricLocation>>();
    ArrayList<ArrayList<MatricLocation>> colAllFourList =new ArrayList<ArrayList<MatricLocation>>();
    ArrayList<ArrayList<MatricLocation>> rowAllThreeList =new ArrayList<ArrayList<MatricLocation>>();
    ArrayList<ArrayList<MatricLocation>> rowAllFourList =new ArrayList<ArrayList<MatricLocation>>();
    public TwoPropsStragety(DataMatricReadHelper reader) {
    	readHelper=reader;
    }
    /**
     * 从datamatric中获得list中位置的值，进行赋值
     * @param list
     * @return
     */
    private ArrayList<MatricLocation> getListFromDataMatric(ArrayList<MatricLocation> list) {
    	MatricLocation temp;
    	for(Iterator<MatricLocation> itr=list.iterator();itr.hasNext();) {
    		temp=itr.next();
    		int tempValue=readHelper.getViewTypeInDataMatric(temp).ordinal()+1;
    		temp.setValue(tempValue);
    	}
    	return list;
    }
    @Override
    public MatricLocation produceProps(ArrayList<MatricLocation> list) {
        MatricLocation resultLocation=null;
        MatricLocation tempLocation=null;
        MatricLocation tempLocation2=null;
     
        //为了防止list中的matriclocaion 没有赋值，所以进行赋值
        Collections.sort(list, rowComparator);
        list=getListFromDataMatric(list);
       
        /*
            生成道具的判断：
                可生成多个道具时，只生成生成要求最高的道具
                1 判读是否生成4连A道具，如果不可以，跳到4，如果可以，跳到2
                2 判读是否为5连，如果可以，直接生成道具B，如果不可以跳到3
                3 判读是否是两个4连和3连，如果是，生成B，如果不是返回1
                4 判读是否为两个相连的3连
         */
        for(int i=0;i<list.size();i++) {
            tempLocation=list.get(i);
            TestHelper.propsTest("the list in 外侧循环"+i+"is "+tempLocation.getX()+""+tempLocation.getY()+
            																	tempLocation.getValue());
            int count=1;
            ArrayList<MatricLocation> tempList=new ArrayList<MatricLocation>(5);
            tempList.add(tempLocation); //每次指向一个新的对象，不用clear了
            for(int j=i+1;j<list.size();j++) {
                tempLocation2=list.get(j);
                TestHelper.propsTest("the list in 内侧循环"+i+"is "+tempLocation2.getX()+""+tempLocation2.getY()+
						tempLocation2.getValue());
                //判断是否相连
                boolean isRight=tempLocation2.getY()==tempLocation.getY()&&
                        tempLocation2.getX()-tempLocation.getX()==1&&
                        tempLocation2.getValue()==tempLocation.getValue();
                if(isRight) {
                	TestHelper.propsTest("has a 相连 in col");
                    count++;
                    tempList.add(tempLocation2);
                    tempLocation=tempLocation2;
                } 
                if(!isRight||j==list.size()-1){   //一旦相连断掉
                    if(count<3) {
                        TestHelper.propsTest("in the clearlist has not 3 相连的情况.......in col");
                        i=j-1;
                        break;

                    }
                    if(count==3) {
                    	TestHelper.propsTest("in the clearlist has  3 相连的情况.......in col");
                        colAllThreeList.add(tempList);
                        i=j-1;//将已经检查过的去掉
                        break;//跳出j循环
                    }
                    if(count==4) {   //一定生成道具A，看是否生成道具B
                    	TestHelper.propsTest("in the clearlist has  4 相连的情况.......in col");
                        colAllFourList.add(tempList);//加入待检查队列
                        i=j-1;//将已经检查过的去掉
                        TestHelper.propsTest("count==4 in col");
                        break;
                    }
                    if(count==5) {  //可以直接返回
                    	TestHelper.propsTest("count==5 in col");
                        MatricLocation resultTempLocation=tempList.get(tempList.size()/2);
                        resultLocation=new MatricLocation(resultTempLocation.getX(), resultTempLocation.getY());
                        //ViewType proptype=ViewType.getTypeFromValue(tempLocation.getValue());
                        //resultLocation.setValue(proptype.ordinal()+1);  //设置成道具不同种的B
                        resultLocation.setValue(ViewType.prop2.ordinal()+1);
                        return resultLocation;
                    }

                }
            }

        }
        /**
         * ------------------------------------------------------------------------------------------------------------------------------------------------
         */
        //接着进行的判断
        Collections.sort(list,colComparator);
        for(int i=0;i<list.size();i++) {
            tempLocation=list.get(i);
            TestHelper.propsTest("the list in "+i+"is "+tempLocation.getX()+""+tempLocation.getY()+
					tempLocation.getValue());
            int count=1;
            ArrayList<MatricLocation> tempList=new ArrayList<MatricLocation>(5);
            tempList.add(tempLocation);
            
            for(int j=i+1;j<list.size();j++) {
            	
                
            	
            	tempLocation2=list.get(j);
            	   TestHelper.propsTest("the list in  temp2"+i+"is "+tempLocation2.getX()+""+tempLocation2.getY()+
       					tempLocation2.getValue());
            	boolean isRight=tempLocation2.getX()==tempLocation.getX()&&
                        tempLocation2.getY()-tempLocation.getY()==1&&
                        tempLocation2.getValue()==tempLocation.getValue();
                if(isRight) {
                    count++;
                    tempList.add(tempLocation2);
                    tempLocation=tempLocation2;
                    TestHelper.propsTest(count+"");
                } 
                if(!isRight||j==list.size()-1) {
                    if(count<3) {
                        TestHelper.propsTest("in the row ....no three connetc.....");
                        i=j-1;
                        break;
                    }
                    if(count==3) {
                    	TestHelper.propsTest("count==3 in col");
                        rowAllThreeList.add(tempList);
                        i=j-1;
                        break;
                    }
                    if(count==4) {
                    	TestHelper.propsTest("count==4 in col");
                        rowAllFourList.add(tempList);
                        i=j-1;
                        break;

                    }
                    if(count==5) {
                    	TestHelper.propsTest("count==5 in col");
                    	MatricLocation resultTempLocation=(MatricLocation)tempList.get(tempList.size()/2);   //位置为3?????
                    	resultLocation=new MatricLocation(resultTempLocation.getX(), 
                    														resultTempLocation.getY());
                    	// ViewType proptype=ViewType.getTypeFromValue(tempLocation.getValue());
                         //resultLocation.setValue(proptype.ordinal()+1);  //设置成道具不同种的B
                        resultLocation.setValue(ViewType.prop2.ordinal()+1);
                        return resultLocation;
                    }
                    TestHelper.propsTest("dadadadaad");
                }
            }

        }
        /*进行情况二的判断
        看3连的list中是否有相连的，看3和4或者4和4之间是否有相连的
        */
        //生成道具B
        if(colAllThreeList.isEmpty()&&colAllFourList.isEmpty()) {
        	if(!rowAllFourList.isEmpty()) {
        		ArrayList<MatricLocation> temp=rowAllFourList.get(0);
        		//强制向上转型
        		MatricLocation tempresult=temp.get(temp.size()/2);
        		resultLocation=new MatricLocation(tempresult.getX(), tempresult.getY());
        		ViewType viewtype=ViewType.getTypeFromValue(tempresult.getValue()+10);
        		resultLocation.setValue(viewtype.ordinal()+1);
        		TestHelper.testprint("produce the props B"+tempresult.getValue()+10);
        		return resultLocation;
        	} else {
        		TestHelper.testprint("the row is empty");
        	}
        }else {
        	TestHelper.testprint("col is not empty");
        }
        //生成道具A的情况
        if(rowAllThreeList.isEmpty()&&rowAllFourList.isEmpty()) {
        	if(!colAllFourList.isEmpty()) {
        		ArrayList<MatricLocation> temp=colAllFourList.get(0);
        		//强制向上转型
        		MatricLocation tempresult=temp.get(temp.size()/2);
        		resultLocation=new MatricLocation(tempresult.getX(), tempresult.getY());
        		ViewType viewtype=ViewType.getTypeFromValue(tempresult.getValue()+10);
        		resultLocation.setValue(viewtype.ordinal()+1);
//        		resultLocation.setValue(ViewType.prop1.ordinal()+1);
        		TestHelper.testprint("produce the props B"+tempresult.getValue()+10);
        		return resultLocation;
        	} else {
        		TestHelper.testprint("the col is empty");
        	}
        }else {
        	TestHelper.testprint("row is not empty");
        }
        TestHelper.propsTest("use isHasCOntainSame colAllFour row AllThreee");
        resultLocation=isHasContainSame(colAllThreeList,rowAllFourList);//
        if(resultLocation!=null) {
        	
    		//resultLocation.setValue(viewtype.ordinal()+1);
        	resultLocation.setValue(ViewType.prop2.ordinal()+1);
            return resultLocation;
        }
        TestHelper.propsTest("use isHasCOntainSame colAllFour row AllFour");
        resultLocation=isHasContainSame(colAllFourList,rowAllFourList);//
        if(resultLocation!=null) {
        	resultLocation.setValue(ViewType.prop2.ordinal()+1);
            return resultLocation;
        }
        TestHelper.propsTest("use isHasCOntainSame colAllFour row AllThreee");
        resultLocation=isHasContainSame(colAllFourList,rowAllThreeList);
        if(resultLocation!=null) {
        	resultLocation.setValue(ViewType.prop2.ordinal()+1);
            return resultLocation;
        }
        ////下边是生成道具A
        TestHelper.propsTest("use isHasCOntainSame colAllThreee row AllThreee");
        resultLocation=isHasContainSame(colAllThreeList,rowAllThreeList);
        if(resultLocation!=null) {
        	MatricLocation temp=(MatricLocation)colAllThreeList.get(0).get(0);
        	ViewType viewtype=ViewType.getTypeFromValue(temp.getValue()+10);
        	resultLocation.setValue(viewtype.ordinal()+1);
        }
        if(resultLocation!=null) {
        TestHelper.propsTest("the return props is"+resultLocation.getX()+""+
        																	resultLocation.getY()+""+resultLocation.getValue());		
        } else {
//        	resultLocation;
        }
        return resultLocation;

    }
    /**
     * 按照down的padding的规程，要先按照y进行排序比较好,升序啊！！！！
     */
    private Comparator<MatricLocation> colComparator =new Comparator<MatricLocation>() {
        @Override
        public int compare(MatricLocation o1, MatricLocation o2) {
            if (o1.getX() != o2.getX()) {
                return o1.getX() - o2.getX();
            } else {
                return o1.getY() - o2.getY();
            }
        }
    };
    /**
     * 按照down的padding的规程，要先按照y进行排序比较好,升序啊！！！！
     */
    private Comparator<MatricLocation> rowComparator=new Comparator<MatricLocation>() {
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
     * 判断两个list中是否有相同的元素，
     * @param list1
     * @param list2
     * @return  MatricLocation 相同的地方，可能为null
     */
    private  MatricLocation isContainSame(ArrayList<MatricLocation> list1,ArrayList<MatricLocation> list2) {
        MatricLocation temp;

        for(int i=0;i<list1.size();i++) {
            temp=list1.get(i);
            if(list2.contains(temp)) {
            	MatricLocation result=new MatricLocation(temp.getX(), temp.getY());
            	result.setValue(temp.getValue());
                return result;
            } else {
            	TestHelper.propsTest("not contain"+temp.getX()+""+temp.getY()+""+temp.getValue());
            }
        }
        return null;
    }

    /**
     * 判断两个ArrayList<ArraLust>是否有相同
     * @param list1
     * @param list2
     * @return
     */
    private MatricLocation isHasContainSame(ArrayList<ArrayList<MatricLocation>> list1,
                                                                                ArrayList<ArrayList<MatricLocation>> list2) {
        ArrayList<MatricLocation> tempList1;   //list1
        ArrayList<MatricLocation> tempList2;
       MatricLocation result=null;

        for(int i=0;i<list1.size();i++) {
            for(int j=0;j<list2.size();j++) {
                tempList1=list1.get(i);
                tempList2=list2.get(j);
                result=isContainSame(tempList1,tempList2);
                if(result!=null) {
                    return result;
                }
            }
        }
        return result;
    }
    /**
	 * @title generateAclears
	 * @Description 生成道具A周围的8个坐标
	 * @param p 道具A坐标
	 * @return 坐标列表
	 */
	public ArrayList<MatricLocation> generateAclears(MatricLocation p){
		ArrayList<MatricLocation> list=new ArrayList<MatricLocation>();
		boolean left=true;
		boolean right=true;
		boolean top=true;
		boolean bottom=true;
		if (p.getX()==0){
			left=false;
		}
		if (p.getX()==8){
			right=false;
		}
		if (p.getY()==0){
			top=false;
		}
		if (p.getY()==8){
			bottom=false;
		}
		if (left&&top){
			list.add(new MatricLocation(p.getX()-1,p.getY()-1));
		}
		if (left){
			list.add(new MatricLocation(p.getX()-1,p.getY()));
		}
		if (left&&bottom){
			list.add(new MatricLocation(p.getX()-1,p.getY()+1));
		}
		if (right&&top){
			list.add(new MatricLocation(p.getX()+1,p.getY()-1));
		}
		if (right){
			list.add(new MatricLocation(p.getX()+1,p.getY()));
		}
		if (right&&bottom){
			list.add(new MatricLocation(p.getX()+1,p.getY()+1));
		}
		if (top){
			list.add(new MatricLocation(p.getX(),p.getY()-1));
		}
		if (bottom){
			list.add(new MatricLocation(p.getX(),p.getY()+1));
		}
		return list;
	}
	public ArrayList<MatricLocation> generateBclears(MatricLocation p){
		ArrayList<MatricLocation> list=new ArrayList<MatricLocation>();
		for(int i=0;i<9;i++){
			list.add(new MatricLocation(i,p.getY()));
		}
		for(int i=0;i<9;i++){
			if(i!=p.getY()){
				list.add(new MatricLocation(p.getX(),i));
			}
		}
		return list;
	}

}
