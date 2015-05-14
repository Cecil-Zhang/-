package Model.userModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.stream.events.StartDocument;

import po.AddressPO;
import po.FriendPO;
import po.GameRecordPO;
import po.MessagePO;
import po.SingleRecordPO;
import po.UserPO;
import vo.UserVO;
import LogicService.MessageRMI;
import LogicService.MessageService;
import LogicService.Start;
import LogicService.UserLogic;
import LogicService.UserLogicService;
import Model.friendModel.FriendList;
import View.MessageFrameRMI;
import View.MessageFrameService;

/**
 * @ClassName UserModel
 * @Descriptio n
 * @author 臧晓杰
 * @Date 下午9:03:54
 */
public class UserModel implements UserModelService {
	FriendList fl;
	UserLogic ul;

	public UserModel() {
		fl = new FriendList();
		ul = new UserLogic();// 服务器ip

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Model.userModel.UserModelService#register(po.UserPO)
	 */
	public String register(UserPO up) {
		String id;
		id = ul.getId();
		String newId = Integer.toString(Integer.parseInt(id) + 1);
		up.setUserId(newId);
		ul.updateId(newId);
		GameRecordPO grp = new GameRecordPO(up.getUserId(), 0, 0.0,
				0, 0);
		ul.updateRecord(grp);
		ul.updateUser(up);
		return newId;
	}
	public void updateMoney(String userId,int money){
		UserPO up=findUser(userId);
		up.setMoney(up.getMoney()+money);
		ul.updateUser(up);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see Model.userModel.UserModelService#changePassword(java.lang.String,
	 * java.lang.String)
	 */
	public boolean changePassword(String userId, String newPassword) {
		boolean result = true;
		try {
			UserPO up = findUser(userId);
			up.setPassword(newPassword);
			ul.updateUser(up);
		} catch (Exception ex) {
			result = false;
			ex.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Model.userModel.UserModelService#login(java.lang.String,
	 * java.lang.String)
	 */
	public String login(String userId, String password) {
//		if(judgeLogin()){
//			return "登陆程序不可重复运行";
//		}
		UserPO up = findUser(userId);
		
		if (up==null) {
			return "不存在该用户";
		}
		if (password.equals(up.getPassword())) {
			if (up.isLogin()) {
				return "该账号已经登录";
			} else {
//				changeLogin("login");
				up.setLogin(true);
				ul.updateUser(up);
				InetAddress inet;
				String ipString = "";
				try {
					inet = InetAddress.getLocalHost();
					ipString = inet.getHostAddress();
					ul.saveAddress(new AddressPO(userId, ipString));
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					MessageService mRmi = new MessageRMI();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return "";
			}
		} else {
			return "密码错误";
		}
	}

	public void logoff(String id) {
		UserPO up = findUser(id);
		up.setLogin(false);
		ul.updateUser(up);
		up = findUser(id);
		System.out.println(up.isLogin());
		System.out.println("我应该注销了");
		ul.deleteAddress(ul.getAddress(id));
//		changeLogin("logoff");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * @see Model.userModel.UserModelService#findUser(java.lang.String)
	 */
	public UserPO findUser(String id) {
		UserPO up = null;
		up = ul.getUser(id);
        
		return up;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Model.userModel.UserModelService#checkRecord(java.lang.String)
	 */
	@Override
	public GameRecordPO checkRecord(String id) {
		GameRecordPO grp = ul.getRecord(id);
		
		return grp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Model.userModel.UserModelService#getHighestScore(java.lang.String)
	 */
	public int getHighestScore(String userId) {
		GameRecordPO grp = checkRecord(userId);
		if(grp==null){
			return 0;
		}
		return grp.getHighestScore();

	}

	public ArrayList<Rank> getRank(String id) {
		ArrayList<Rank> rankList = new ArrayList<Rank>();
		ArrayList<FriendPO> friendList = fl.findList(id);
		UserPO selfPo = findUser(id);
		friendList.add(new FriendPO(selfPo.getUserId(), selfPo.getUserId(),
				selfPo.getUserName(), selfPo.getUserName(), selfPo.isGender(),
				selfPo.getImage()));
		for (int i = 0; i < friendList.size(); i++) {
			String friendId = friendList.get(i).getFriendId();
			String secondName = friendList.get(i).getSecondName();
			int highestScore = getHighestScore(friendId);
			Rank temp = new Rank(friendId, secondName, highestScore);
			int index = 0;
			for (; index < i; index++) {
				if (highestScore > rankList.get(index).getScore()) {
					break;
				}
			}
			rankList.add(index, temp);
		}
		System.out.println(rankList.size());
		return rankList;
	}

	/* (non-Javadoc)
	 * @see Model.userModel.UserModelService#saveSingleRecord(java.lang.String, java.util.Date, int, int, int)
	 */
	public void saveSingleRecord(String userID, Date d, int score, int money,
			int combo) {
		int date = (int) ((new Date().getTime()) / (1000 * 24 * 60 * 60));
		SingleRecordPO srp = new SingleRecordPO(userID, date, score, combo,
				money);
		updateMoney(userID,money);
		ul.saveSingleRecord(srp);
		GameRecordPO grp = ul.getRecord(srp.getUserID());
		if(grp==null){
			grp = new GameRecordPO(userID, 1, (double) score,
					score, combo);
			return ;
		}
		if (srp.getScore() > grp.getHighestScore()) {
			grp.setHighestScore(srp.getScore());
		}
		if (srp.getHighestCombo() > grp.getHighestCombo()) {
			grp.setHighestCombo(srp.getHighestCombo());
		}

		grp.setTotalRound(grp.getTotalRound() + 1);
		grp.setAverageScore((grp.getAverageScore() * (grp.getTotalRound() - 1) + srp
				.getScore()) / grp.getTotalRound());
		ul.updateRecord(grp);
	}

	public ArrayList<SingleRecordPO> getSingleRecords(String id) {
		return ul.getSingleRecords(id);
	}

	public AddressPO getIp(String id) {
		return ul.getAddress(id);
	}

	public int[] getEveryRounds(String id) {
		int[] rounds = {0,0,0,0,0,0,0};
		ArrayList<SingleRecordPO> singleRecords = ul.getSingleRecords(id);
		int date = (int) ((new Date().getTime()) / (1000 * 24 * 60 * 60));
		for (int i = 0; i < singleRecords.size(); i++) {
			SingleRecordPO srp = singleRecords.get(i);
			if (srp.getDate() == date) {
				rounds[0]+=1;
			} else if (srp.getDate() == date - 1) {
				rounds[1]+=1;
			} else if (srp.getDate() == date - 2) {
				rounds[2]+=1;
			} else if (srp.getDate() == date - 3) {
				rounds[3]+=1;
			} else if (srp.getDate() == date - 4) {
				rounds[4]+=1;
			} else if (srp.getDate() == date - 5) {
				rounds[5]+=1;
			} else if (srp.getDate() == date - 6) {
				rounds[6]+=1;
			}
		}
		return rounds;
	}

	@Override
	public double[] getAverageScores(String id) {
	    double[] averageScores = {0.0,0.0,0.0,0.0,0.0,0.0,0.0};
	    int[] rounds = getEveryRounds(id);
	    ArrayList<SingleRecordPO> singleRecords = ul.getSingleRecords(id);
		int date = (int) ((new Date().getTime()) / (1000 * 24 * 60 * 60));
		for (int i = 0; i < singleRecords.size(); i++) {
			SingleRecordPO srp = singleRecords.get(i);
			if(srp.getDate()==date){
				averageScores[0]+=srp.getScore();
			}else if(srp.getDate()==date-1){
				averageScores[1]+=srp.getScore();	
			}else if(srp.getDate()==date-2){
				averageScores[2]+=srp.getScore();	
			}else if(srp.getDate()==date-3){
				averageScores[3]+=srp.getScore();	
			}else if(srp.getDate()==date-4){
				averageScores[4]+=srp.getScore();
			}else if(srp.getDate()==date-5){
				averageScores[5]+=srp.getScore();
			}else if(srp.getDate()==date-6){
				averageScores[6]+=srp.getScore();
			}
		}
		for(int i=0;i<7;i++){
			averageScores[i]/=rounds[i];
		}
		for(int i=0;i<7;i++){
			averageScores[i]/=1000;
		}
		return averageScores;
	}

	public boolean judgeLogin(){
		boolean  result =true;
		String s="";
		try {
			String encoding = "GBK";
			File file = new File("Login.txt");
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					s = lineTxt;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		if(s.equals("login")){
			result =true;
		}
		else {
			result=false;
		}
		return result;
	}
	public void changeLogin(String s){
		try {
			FileWriter fw=new FileWriter("Login.txt");
			fw.write(s);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
