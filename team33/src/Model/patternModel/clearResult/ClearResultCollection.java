package Model.patternModel.clearResult;

import Model.patternModel.TestHelper;

import java.util.ArrayList;

/**
 * Created by randy on 14-5-17.
 */
public class ClearResultCollection {
    private ArrayList<ClearResult> list=new ArrayList<ClearResult>();
    private boolean isClear;
    public void addClearResult(ClearResult result) {
        list.add(result);
    }
    public void setIsClear(boolean isClear) {
        this.isClear=isClear;
    }
    public boolean getIsClear() {
        return isClear;
    }
    public ClearResult getClearResult(int i) {

            return list.get(i);

    }
    public int getListLength() {
        return list.size();
    }

    public ArrayList<ClearResult> getList() {
        return list;
    }

    public void setList(ArrayList<ClearResult> list) {
        this.list = list;
    }
}
