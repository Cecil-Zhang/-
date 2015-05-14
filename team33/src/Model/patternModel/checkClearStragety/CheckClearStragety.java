package Model.patternModel.checkClearStragety;

import Control.patternControl.propClear;
import Model.patternModel.TestHelper;
import Model.patternModel.ViewType;
import Model.patternModel.clearResult.ClearResult;
import Model.patternModel.clearResult.ClearResultCollection;
import Model.patternModel.dataContainer.DataMatricReadHelper;
import Model.patternModel.location.MatricLocation;
import Model.patternModel.propsStragety.PropsStragety;
import Model.patternModel.propsStragety.TwoPropsStragety;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

/** 消除的策略
 * Created by randy on 14-5-13.
 */
public class CheckClearStragety implements CheckIfClearStragety {
    public CheckClearStragety(DataMatricReadHelper dataMatricReadHelper) {
        this.dataMatricReadHelper = dataMatricReadHelper;
        length=dataMatricReadHelper.getDataMatricLength();
    }
    private int length;
    private DataMatricReadHelper dataMatricReadHelper=null;
    /*
    ----------------------------------------------------------------------------------------------------------------------
     */
    @Override
    public boolean isClearedWhenInit(MatricLocation location) {
        int i=location.getX();
        int j=location.getY();
        int value=location.getValue();
        for(int m=1;m<= minclearNumber;m++){

            TestHelper.testprint(" row clear for 位置"+i+","+j+" 值为"+value+" in location 第几个"+m );

            if(isClearedForRow(i,j,m,value)) {
                TestHelper.testJunit("-----------------------------------------------------------------");

                return true;
            }
            TestHelper.testprint(" column clear for 位置"+i+","+j+" 值为"+value+" in location 第几个"+m);
            if(isClearedForColumn(i,j,m,value)) {
                TestHelper.testJunit("-----------------------------------------------------------------");
                return true;
            }
        }

        TestHelper.testprint("has no clear");
        TestHelper.testJunit("-----------------------------------------------------------------");

        return false;
    }
    /*
    -------------------------------------------------------------------------------------------------------------------------------------------------
     */
    @Override

    public ArrayList<MatricLocation> checkClearList(ArrayList<MatricLocation> list) {

        ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();  //TODO 最好可以预先决定大小
        //依次判断所有的list中的点是否可以消
        ArrayList<MatricLocation> temp;
        for(Iterator<MatricLocation> iterator=list.iterator();iterator.hasNext();) {
            temp=clear(iterator.next());
            //加入到结果的list中，不要加入重复的值
            MatricLocation tempLocation;
            for(Iterator<MatricLocation> itr=temp.iterator();itr.hasNext();) {
                tempLocation=itr.next();
                TestHelper.testprint("check list for if contain");
                if(!result.contains(tempLocation)) {
                    result.add(tempLocation);
                }else {
                    TestHelper.testprint("...already has"+tempLocation.getX()+" "+tempLocation.getY()+" "+tempLocation.getValue());
                }
            }
        }

        return result;
    }
    /*
    -----------------------------------------------------------------------------------------------------------------------------
     */
    public ArrayList<MatricLocation> clear(MatricLocation location) {
        ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();
        ClearResultCollection tempResult=isCleared(location);
        TestHelper.testprint("the length of tempResult is "+tempResult.getListLength());
        ArrayList<MatricLocation> temp;
       if(tempResult.getIsClear()) {   //当可以消除时，进行转换，并去掉重复的。
           for (int i = 0; i < tempResult.getListLength(); i++) {
               ClearResult temp2 = tempResult.getClearResult(i);
               if (temp2 == null) {
                   TestHelper.testprint("temp is null");
               } else {
                   TestHelper.testprint("temp is  not null");

               }
               TestHelper.testprint("chang the list");
               temp = changeResultToMatricLocation(tempResult.getClearResult(i), location);
               //遍历，然后将重复的删掉便可
               for(int m=0;m<result.size();m++) {
                   TestHelper.testprint(result.get(i).getValue()+""+result.get(i).getX()+result.get(i).getY());
               }
               for (int j = 0; j < temp.size(); j++) {
                   TestHelper.testprint("the changResultToMatric list is"+temp.get(j).getValue()+" "+
                           temp.get(j).getX()+" "+temp.get(j).getY());
                   if (!result.contains(temp.get(j))) {
                       result.add(temp.get(j));
                       TestHelper.testprint("the  list add is"+temp.get(j).getValue()+" "+
                               temp.get(j).getX()+" "+temp.get(j).getY());

                   }else {
                       TestHelper.testprint("al has");
                   }
               }
           }
       }else {
            TestHelper.testprint("is not cleared check the position");
       }
        for(int i=0;i<result.size();i++) {
            TestHelper.testprint(result.get(i).getValue()+""+result.get(i).getX()+result.get(i).getY());
        }
        return result;
    }
    /**
     * 从消除结果中获得消除位置
     * @param tempResult
     * @param location
     * @return list
     */
    private ArrayList<MatricLocation> changeResultToMatricLocation(ClearResult tempResult,MatricLocation
                                                                            location) {
        int x=location.getX();
        int y=location.getY();
        ArrayList<MatricLocation> list=new ArrayList<MatricLocation>();//最大为5吧
        if(tempResult==null) {
            TestHelper.testprint("tempResult is null");
        }else {
            TestHelper.testprint("tempResult is null--------not ");

        }
        if(tempResult.isCleared()) { //判断是否消除
            list.add(location); //判断的点一定在其中国
            if(tempResult.getDirection()) {   //判断消除的方方向是否为横向
                int temp=tempResult.getLocation();  // x++
                switch (temp) {
                    case 1: {
                        MatricLocation temp1=new MatricLocation(x+1,y);
                        MatricLocation temp2=new MatricLocation(x+2,y);

                        list.add(temp1);
                        list.add(temp2);
                        return list;
                    }
                    case 2: {
                        MatricLocation temp1=new MatricLocation(x-1,y);
                        MatricLocation temp2=new MatricLocation(x+1,y);

                        list.add(temp1);
                        list.add(temp2);
                        return list;
                    }
                    case 3: {
                        MatricLocation temp1=new MatricLocation(x-1,y);
                        MatricLocation temp2=new MatricLocation(x-2,y);
                        list.add(temp1);
                        list.add(temp2);
                        return list;
                    }
                    default:
                        return list;
                }
            }else {    //y++
                int temp=tempResult.getLocation();
                switch (temp) {
                    case 1: {
                        MatricLocation temp1=new MatricLocation(x,y+1);
                        MatricLocation temp2=new MatricLocation(x,y+2);
                        list.add(temp1);
                        list.add(temp2);
                        return list;
                    }
                    case 2: {
                        MatricLocation temp1=new MatricLocation(x,y-1);
                        MatricLocation temp2=new MatricLocation(x,y+1);
                        list.add(temp1);
                        list.add(temp2);
                        return list;
                    }
                    case 3: {
                        MatricLocation temp1=new MatricLocation(x,y-1);
                        MatricLocation temp2=new MatricLocation(x,y-2);
                        list.add(temp1);
                        list.add(temp2);
                        return list;
                    }
                    default:
                        return list;
                }
            }
        }else {
            return list;
        }
    }
     /*
    -------------------------------------------------------------------------------------------------------------------
     */
    /**
     *判读一行上是否有满足最小消除数的相连，
     *
     * @param i
     * @param j
     * @param location
     * @param value
     * @return
     */
    private boolean isClearedForColumn(int i,int j,int location,int value) {

        int frontLocation=location-1;//计算出前边应该有多少个位子

        if(i<frontLocation) {                   //前边没有足够的位置
            TestHelper.testprint("No front location in row");
            return false;
        }
        //判断后边是否有位置
        int backLocation=minclearNumber-location;
        if(length-1-i<backLocation) {
            TestHelper.testprint("No back location in row");
            return false;
        }

        //有足够的位置
        int headLocation=i-frontLocation;//开头位置
        MatricLocation temp=new MatricLocation(0,0);
        for(int m=0;m<minclearNumber;m++) {
            temp.setX(headLocation + m);
            temp.setY(j);
            int compare=dataMatricReadHelper.getViewTypeInDataMatric(temp).ordinal()+1;
            if((compare%10)==(value%10)){
                //do nothing
            }else {
//                TestHelper.testprint("位置:"+(headLocation+m)+"*"+j+"位置上的值:"+
//                        dataMatric.getValue(headLocation+m,j)+" 比较值: "+value);
            TestHelper.testprint("compare is"+compare+""+value);
               return false;
            }
        }
        TestHelper.testprint("has a clear in row");
        return true;


    }
    /*
    -------------------------------------------------------------------------------------------------------------------------------------
     */

    /**
     * 判读一列上是否形成最小消除数的相连，返回值要改。。。
     * @param i
     * @param j
     * @param location
     * @param value
     * @return
     */
    private boolean isClearedForRow(int i,int j,int location,int value) {
        int frontLocation=location-1;//计算出前边应该有多少个位子

        if(j<frontLocation) {                   //前边没有足够的位置
            TestHelper.testprint("No front location in column");
            return false;
        }
        //判断后边是否有位置
        int backLocation=minclearNumber-location;
        if(length-1-j<backLocation) {
            TestHelper.testprint("No back location in column");
            return false;
        }

        //有足够的位置
        int headLocation=j-frontLocation;//开头位置
        MatricLocation temp=new MatricLocation(0,0);
        for(int m=0;m<minclearNumber;m++) {
            temp.setX(i);
            temp.setY(headLocation+m);
            int compare=dataMatricReadHelper.getViewTypeInDataMatric(temp).ordinal()+1;
            
            if((compare%10)==(value%10)){
                //do nothing
            }else {
            	TestHelper.testprint("compare is"+compare+""+value);
                TestHelper.testprint("has check");
                return false;
            }
        }
        TestHelper.testprint("has a clear");
        return true;
    }
    /*
    --------------------------------------------------------------------------------------------------------------------

     */
    @Override
    public ClearResultCollection isCleared(MatricLocation location) {
        ClearResultCollection clearResultCollection=new ClearResultCollection();
        int i=location.getX();
        int j=location.getY();
        int value=location.getValue();

        clearResultCollection.setIsClear(false);
        ArrayList<ClearResult> resultList=new ArrayList<ClearResult>();
        ClearResult  result;

        for(int m=1;m<= minclearNumber;m++){

            TestHelper.testprint(" row clear for 位置" + i + "," + j + " 值为" + value + " in location 第几个" + m);

            if(isClearedForRow(i,j,m,value)) {

                result=new ClearResult();
                result.setCleared(true);
                result.setDirection(false);
                result.setLocation(m);
                if(result.isCleared()) {
                    TestHelper.testprint("add result into collection in row");
                }else {
//                    TestHelper.testprint("add result into collection in ");

                }
                clearResultCollection.setIsClear(true);
                clearResultCollection.addClearResult(result);
                resultList.add(result);
            }
            TestHelper.testprint(" column clear for 位置"+i+","+j+" 值为"+value+" in location 第几个"+m);
            if(isClearedForColumn(i,j,m,value)) {

                result=new ClearResult();

                result=new ClearResult();
                result.setCleared(true);
                result.setDirection(true);
                result.setLocation(m);
                TestHelper.testprint("add result into collection in row" + result.isCleared());

                clearResultCollection.setIsClear(true);
                clearResultCollection.addClearResult(result);
                resultList.add(result);
            }
        }
        if(clearResultCollection.getIsClear()) {
            TestHelper.testprint("has no clear");
            TestHelper.testprint("the size of collection is" + clearResultCollection.getClearResult(0).isCleared());
        }
        TestHelper.testprint("------------------------------------------------");
        if(resultList.size()!=0) {
            TestHelper.testprint("the size of collection is" + resultList.get(0).isCleared());

        }else {
            TestHelper.testprint("resultList is 0");

        }
        TestHelper.testprint("------------------------------------------------");

        return clearResultCollection;
    }
    
    /*
     *-----------------------------------------------------------------------
     */
    
    /**
     * 检查消除的list中是否有道具
     * @param list
     * @return
     */
    public ArrayList<MatricLocation> checkIfPropExist(ArrayList<MatricLocation> list) {
    	ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();
    	MatricLocation temp;
    	ViewType tempvalue;
    	PropsStragety propsStragety=new TwoPropsStragety(dataMatricReadHelper);
    	
    	for (int i=0;i<list.size();i++) {
    		temp=list.get(i);
    		if(!result.contains(temp)) {
    			
    		
    		result.add(temp);
    		}
    		int x=temp.getX();
			int y=temp.getY();
    		tempvalue=dataMatricReadHelper.getViewTypeInDataMatric(temp);
    		if(tempvalue.equals(ViewType.prop1pattern1)||
    				tempvalue.equals(ViewType.prop1pattern2)||
    				tempvalue.equals(ViewType.prop1pattern3)||
    				tempvalue.equals(ViewType.prop1pattern4)||
    				tempvalue.equals(ViewType.prop1pattern5)) {
    			TestHelper.propsTest("clearList has a propB");
    		
    			/////获得消除的部分
    			ArrayList<MatricLocation> templist=propsStragety.generateAclears(temp);
    			for(int m=0;m<templist.size();m++) {
    				MatricLocation temp2=templist.get(m);
    				if(!result.contains(temp2)) {
    					result.add(temp2);
    				}
    			}
    			}//添加完道具A周围的东西
    		if(tempvalue.equals(ViewType.prop2)) {  //道具B
    			ArrayList<MatricLocation> templist=propsStragety.generateBclears(temp);
    			//添加到result
    			for(int m=0;m<templist.size();m++) {
    				MatricLocation temp2=templist.get(m);
    				if(!result.contains(temp2)) {
    					result.add(temp2);
    				}
    			}
    			
    		}
    			
    		}
    	//获得了整体的result
    	for(int q=0;q<result.size();q++) {
    		temp=result.get(q);
    		tempvalue=dataMatricReadHelper.getViewTypeInDataMatric(temp);
    		if(tempvalue.equals(ViewType.prop1pattern1)||
    				tempvalue.equals(ViewType.prop1pattern2)||
    				tempvalue.equals(ViewType.prop1pattern3)||
    				tempvalue.equals(ViewType.prop1pattern4)||
    				tempvalue.equals(ViewType.prop1pattern5)||
    				tempvalue.equals(ViewType.prop2)) {
    			if(!list.contains(temp)) {
    			 return checkIfPropExist(result);
    			}
    		}
    	}
    	return result;
    	}
    /**
     * 获得道具列表
     * @return
     */
    public ArrayList<MatricLocation> getPropList() {
    	ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();
    	for(int i=0;i<dataMatricReadHelper.getDataMatricLength()-1;i++) {
    		for(int j=0;j<dataMatricReadHelper.getDataMatricLength()-1;j++) {
    		//遍历
    		MatricLocation temp=new MatricLocation(i, j);
    		ViewType type=dataMatricReadHelper.getViewTypeInDataMatric(temp);
    		if(type.equals(ViewType.prop1pattern1)||
    				type.equals(ViewType.prop1pattern2)||
    				type.equals(ViewType.prop1pattern3)||
    				type.equals(ViewType.prop1pattern4)||
    				type.equals(ViewType.prop1pattern5)) {
    			result.add(temp);
    		}
    		if(type.equals(ViewType.prop2)) {
    			result.add(temp);
    		}
    		}
    	}
    	return result;
    }
    
    	
} 


