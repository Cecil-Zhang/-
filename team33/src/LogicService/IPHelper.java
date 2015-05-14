package LogicService;

import java.net.InetAddress;
import java.net.UnknownHostException;

import View.MessageFrame;

/**
 * @ClassName IPHelper
 * @Description
 * @author 张舒贤
 * @Date 2014-5-24
 */
public class IPHelper {

	public static String getSelfIP(){
		String selfIP=null;
		try {
			InetAddress inet = InetAddress.getLocalHost();
			selfIP = inet.getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			MessageFrame.show("获取本机地址失败!");
			e1.printStackTrace();
		}
		return selfIP;
	}
}
