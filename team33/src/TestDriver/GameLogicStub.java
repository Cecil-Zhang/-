package TestDriver;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import po.PatternType;
import Model.patternModel.Observer;
import Model.patternModel.TestHelper;

public class GameLogicStub extends Model.patternModel.GameLogic {
	
	ArrayList<Observer> observers=new ArrayList<Observer>();
	
	public void changePatternLocation(Point location1,Point location2) {
      
    }

    /*
     * @param x
     * @return
     */
    public PatternType queryPosition(Point x) {
        return PatternType.propB;
    }
    
    public void clear(ArrayList<Point> collection){
    	TestHelper.testprint("propB generates clear list"+String.valueOf(collection));
    }

    /*
     * @param collection
     * @return
     */
    public int clear(Collection<Point> collection) {
    	System.out.println("Call GameLogic.clear:");
    	for(Point p:collection){
    		System.out.print(p);
    	}
    	System.out.println();
        return 1;
    }
    
    public void registerObserver(Observer o){
    	observers.add(o);
    }
    
    public void remove(Observer o){
    	observers.remove(o);
    }

    public void myNotify(){
    }
}
