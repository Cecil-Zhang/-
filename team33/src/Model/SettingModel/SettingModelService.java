package Model.SettingModel;

import java.util.ArrayList;

public interface SettingModelService {
	public ArrayList<String > getInfo();
	public void setWay(boolean way);
	public boolean getWay();
	public void setStyle(boolean s);
	public boolean getStyle();
}
