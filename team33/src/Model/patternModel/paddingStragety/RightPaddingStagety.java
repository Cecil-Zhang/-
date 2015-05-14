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
public class RightPaddingStagety implements PaddingStragety {
    private DataMatricReadHelper readHelper=null;
    private PropsStragety propsStragety;

    public RightPaddingStagety(DataMatricReadHelper readHelper) {
        this.readHelper = readHelper;
       
        propsStragety=new TwoPropsStragety(readHelper);
    }

    @Override
    public ArrayList<MatricLocation> producePaddingList(ArrayList<MatricLocation> list) {
    	MatricLocation propLocation=propsStragety.produceProps(list);
    	TestHelper.propsTest("call the rightPadding");
    	list=trimArrayList(list);
    	ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();
        MatricLocation temp=null;
        int x,y;
        for(Iterator<MatricLocation> iterator=list.iterator();
        				iterator.hasNext();) {
            temp=iterator.next();
            TestHelper.testprint("the padding is from"+temp.getX()+" "+temp.getY());
            result.addAll(produceColumnList(temp));

        }
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

            ////
        
        	MatricLocation temp5;
        	
            return result;
    }
    private Comparator<MatricLocation> comparator=new Comparator<MatricLocation>() {
        @Override
        public int compare(MatricLocation o1, MatricLocation o2) {
            if (o1.getX() != o2.getX()) {
                return o2.getX() - o1.getX();
            } else {
                return o1.getY() - o2.getY();
            }
        }
    };
    private ArrayList<MatricLocation> trimArrayList(ArrayList<MatricLocation> list) {
      
		Collections.sort(list,comparator);
       
     
        ArrayList<MatricLocation> result=new ArrayList<MatricLocation>();
        int location=0;
        for(int i=0;i<list.size();i++) {

            MatricLocation temp1=list.get(i);
            if(i<list.size()-1) {
                MatricLocation temp2 = list.get(i + 1);

                if (temp1.getX() != temp2.getX()) {
                	
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

	 private ArrayList<MatricLocation>  produceColumnList(MatricLocation location) {
		 int x=location.getX();
	     int y=location.getY();
	     int div=location.getValue();
	     int dataMatricLength=readHelper.getDataMatricLength();
	     ArrayList<MatricLocation> result=new ArrayList<MatricLocation>(dataMatricLength);
	     MatricLocation locationTemp=null;
	        TestHelper.testprint("the div is"+div);
	     // 下落
	        if(y>div) {   // 对y进行操作
	            for (int i = y; i > div; i--) {
	                locationTemp = new MatricLocation(x, i);

	                location.setY(i - 1 - div);
	                int value = readHelper.getViewTypeInDataMatric(location).ordinal() + 1;
	                locationTemp.setValue(value);
	                result.add(locationTemp);
	            }
	        }
	        for (int i=0;i<=div;i++) {
	            locationTemp=new MatricLocation(x,i);
	            TestHelper.testprint("set random number"+locationTemp.getX()+" "+locationTemp.getY());
	            locationTemp.setValue(productRandomNumber());

	            result.add(locationTemp);
	        }
	        System.out.println("show the list ===================");
	        for(int i=0;i<result.size();i++) {
	            System.out.println(result.get(i).getValue()+" "+result.get(i).getX()+" "+result.get(i).getY()+"");
	        }
	        return result;
	 }
	 private int productRandomNumber() {
	        int result = (int) (Math.random() * 5 )+1;
	        //½÷É÷¡£¡£¡£
	        if (result < 0 || result > readHelper.getDataMatricValueNumber()) {
	            TestHelper.testprint("productRandomNumber beyond number limit");
	            return 1;  //Ä¬ÈÏÎª1
	        } else {
	        	if(result==0) {
	        		TestHelper.propsTest("^^^^^^^^^^^notice random is 0---");
	        	}
	            return result;
	        }
	    }

	public ArrayList<MatricLocation> getTheBeginLocationList(
			ArrayList<MatricLocation> bangList,
			ArrayList<MatricLocation> changList) {
		if (bangList == null || changList == null) {
			TestHelper.propsTest("getTheBeginLocationList is return null");
			return null;
		}
		ArrayList<MatricLocation> result = new ArrayList<MatricLocation>(
				changList.size());

		if (bangList != null && changList != null) {
			int listLength = readHelper.getDataMatricLength();
			int[] valueList = new int[listLength];
		
			for (int i = 0; i < listLength; i++) {
				valueList[i] = 0;
			}
		
			for (int j = 0; j < bangList.size(); j++) {
				MatricLocation temp = bangList.get(j);
				valueList[temp.getX()]++;
			} 
			for (int q = 0; q < valueList.length; q++) {
				System.out.println("" + valueList[q]);
			}
			
			for (int m = 0; m < changList.size(); m++) {
				MatricLocation temp2 = changList.get(m);
				MatricLocation resultLocation = new MatricLocation(
						temp2.getX(), temp2.getY());
				int value = temp2.getValue();
				if (value != (ViewType.prop1.ordinal() + 1)
						&& value != (ViewType.prop2.ordinal() + 1)) { 
					resultLocation.setValue(temp2.getY()-valueList[temp2.getX()]
					                                   );
					
					if (resultLocation.getValue() < 0) { 
						resultLocation.setValue(-1);
					}
				} else { 
					resultLocation.setValue(-1);
				}
				result.add(resultLocation);
			}
		}
		TestHelper
				.propsTest("))))))))))))))))))))))))))))))))))))))))))))))))))))");
		for (int i = 0; i < result.size(); i++) {
			TestHelper.propsTest("the result is " + result.get(i).getX()
					+ result.get(i).getY() + result.get(i).getValue());
		}
		return result;
	}
	 }
