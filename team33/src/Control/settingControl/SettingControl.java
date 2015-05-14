package Control.settingControl;

import java.util.ArrayList;

import Model.SettingModel.SettingModel;
import Model.SettingModel.SettingModelService;

public class SettingControl implements SettingControlService{
	SettingModelService sm=new SettingModel();
	
	@Override
	public void changeVolume(int vol) {
		// TODO Auto-generated method stub
		
	}
	public void setWay(boolean w){
		sm.setWay(w);
	}
	public void setStyle(boolean s){

		sm.setStyle(s);
	}
	@Override
	public void closeVoice() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getWay() {

// TODO Auto-generated method stub
		return sm.getWay();
	}


	public ArrayList<String > getInfo() {
		// TODO Auto-generated method stub
		ArrayList<String > info=sm.getInfo();
		return info;
	}
	@Override
	public boolean getStyle() {
		// TODO Auto-generated method stub
		return sm.getStyle();
	}

}
