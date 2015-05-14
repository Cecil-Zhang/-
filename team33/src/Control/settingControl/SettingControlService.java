package Control.settingControl;

import java.util.ArrayList;

/**
 * @ClassName SettingControlService
 * @Description SettingControl的抽象接口
 * @author 张舒贤
 * @Date 2014-5-12
 */
public interface SettingControlService {

	public void changeVolume(int vol);
	
	public void closeVoice();
	
	public boolean  getWay();
	
	public boolean getStyle();
	
	public ArrayList<String> getInfo();
	public void setWay(boolean way);
	public void setStyle(boolean s);
	
}
