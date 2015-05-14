package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vo.ApplicationVO;
import vo.UserVO;
import Control.friendControl.FriendControl;
import Control.friendControl.FriendControlService;
import Control.gameControl.GameControl;
import Control.gameControl.GameControlService;
import Control.settingControl.SettingControl;
import Control.settingControl.SettingControlService;
import Control.userControl.MessageControl;
import Control.userControl.MessageControlService;
import Control.userControl.UserControl;
import Control.userControl.UserControlService;
import View.MainUI.MainPanel;

import com.sun.awt.AWTUtilities;

@SuppressWarnings("restriction")
public class MainFrame extends JFrame {

	/**
	 * @author Fred 爱消除主界面frame
	 */
	private static final long serialVersionUID = -3043291220471151953L;

	public int frameHeight;
	public int frameWidth;
	public AllImage allImage;
	public AudioPlayer audioPlayer;
	private boolean music=true;
	private boolean voice=true;
	private boolean musicPlaying=false;
	public UserVO user;
	public Image logo = new ImageIcon("graph/logo.png").getImage();
	private static MainFrame Singleton;
	public boolean isConnected =true;
	private static String userID = null;
	public FriendControlService fcs;
	public UserControlService ucs;
	public MessageControlService mcs;
	public GameControlService gs;
	public UserControlService uc;
	public SettingControlService sc;
	private MainFrame me;
	private MainPanel mp;

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		MainPanel mainPanel = new MainPanel(frame);
	}

	private MainFrame() {
		setFrameSize();
		if (isConnected) {
			fcs = new FriendControl();
			ucs = new UserControl();
			mcs = new MessageControl();
			gs = new GameControl();
		    uc=new UserControl();
			sc=new SettingControl();
		}
		// 消除windows自带边框
		this.setUndecorated(true);
		this.setLayout(null);
		setIconImage(logo);
		JLabel loadingLabel = new JLabel("Loading...");
		loadingLabel.setFont(new Font("Georgia", Font.BOLD, 18));
		loadingLabel.setForeground(Color.CYAN);
		loadingLabel.setBounds(frameWidth * 4 / 10, frameHeight / 2,
				frameWidth / 10, frameWidth / 20);
		loadingLabel.setVisible(true);
		this.add(loadingLabel);

		allImage = new AllImage();

		// 设置窗口圆角
		AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(0.0D,
				0.0D, this.getWidth(), this.getHeight(), 26.0D, 26.0D));
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				allImage.MOUSE, new Point(), null));

		setVisible(true);
		me = this;

	}

	public void setFrameSize() {
		// 获取屏幕大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frameHeight = screenSize.height * 9 / 10;
		frameWidth = screenSize.width * 3 / 4;

		setSize(frameWidth, frameHeight);
		setLocation(screenSize.width / 2 - frameWidth / 2,
				screenSize.height / 27);
	}

	public int getHeight() {
		return this.frameHeight;
	}

	public int getWidth() {
		return this.frameWidth;
	}

	public void setUser(UserVO u) {
		this.user = u;
		MainFrame.userID = u.getId();
	}

	public static MainFrame getInstance() {
		if (MainFrame.Singleton == null) {
			MainFrame.Singleton = new MainFrame();
			return MainFrame.Singleton;
		} else {
			return MainFrame.Singleton;
		}

	}

	@SuppressWarnings("static-access")
	public void modifyImage(JLabel label, ImageIcon temp) {
		Image resulttemp = temp.getImage().getScaledInstance(label.getWidth(),
				label.getHeight(), temp.getImage().SCALE_DEFAULT);
		temp = new ImageIcon(resulttemp);
		label.setIcon(temp);
	}

	public void showFriendMess(final String ID, String name) {
		// TODO Auto-generated method stub
		final MessageFrame mf1 = new MessageFrame(name + "(" + ID
				+ ")，想要添加您为好友！");
		RButton acceptButton1 = new RButton("接受");
		RButton declineButton1 = new RButton("拒绝");
		acceptButton1.setBounds(mf1.getWidth() / 2, mf1.getHeight() * 29 / 40,
				mf1.getWidth() / 5, mf1.getHeight() / 4);
		declineButton1.setBounds(mf1.getWidth() * 3 / 4,
				mf1.getHeight() * 29 / 40, mf1.getWidth() / 5,
				mf1.getHeight() / 4);
		acceptButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				me.mcs.replyApplication(new ApplicationVO(me.user.getId(),
						me.user.getName(), ID, ""), true);
				mf1.dispose();
			}
		});
		declineButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				me.mcs.replyApplication(new ApplicationVO(me.user.getId(), "",
						ID, ""), false);
				mf1.dispose();
			}
		});
		mf1.add(acceptButton1);
		mf1.add(declineButton1);

	}

	public void showGameMess(final String ID, String name) {
		// TODO Auto-generated method stub
		final MessageFrame mf2 = new MessageFrame(name + "(" + ID
				+ ")，邀请您参加一局游戏！");
		RButton acceptButton2 = new RButton("接受");
		RButton declineButton2 = new RButton("拒绝");
		acceptButton2.setBounds(mf2.getWidth() / 2, mf2.getHeight() * 29 / 40,
				mf2.getWidth() / 5, mf2.getHeight() / 4);
		declineButton2.setBounds(mf2.getWidth() * 3 / 4,
				mf2.getHeight() * 29 / 40, mf2.getWidth() / 5,
				mf2.getHeight() / 4);
		acceptButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				me.mcs.replyInvitation(me.user.getId(), ID, true);
				mf2.dispose();
			}
		});
		declineButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				me.mcs.replyInvitation(me.user.getId(), ID, false);
				mf2.dispose();
			}
		});
		mf2.add(acceptButton2);
		mf2.add(declineButton2);
	}

	public void setMp(MainPanel mainPanel) {
		this.mp = mainPanel;
	}

	public MainPanel getMp() {
		return this.mp;
	}
	
	public boolean isMusic() {
		return music;
	}

	public void setMusic(boolean music) {
		this.music = music;
	}
	
	public void playMusic(String filename){
		if(!music){
			return;
		}
		stopMusic();
		if(!musicPlaying){
			audioPlayer=new AudioPlayer(filename,1);
			musicPlaying=true;
		}	
	}
	
	public void stopMusic(){
		if(audioPlayer!=null){
			audioPlayer.stop();
			musicPlaying=false;
		}
	}
	
	public boolean isMusicPlaying() {
		return musicPlaying;
	}
	public void setMusicPlaying(boolean musicPlaying) {
		this.musicPlaying = musicPlaying;
	}
	public boolean isVoice() {
		return voice;
	}

	public void setVoice(boolean voice) {
		this.voice = voice;
	}

	private Point loc = null;
	private Point tmp = null;
	private boolean isDragged = false;

	/**
	 * 拖动panel可以移动窗体位置
	 */
	public void setDragable(JPanel panel) {
		panel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				isDragged = false;
				// TODO 更换鼠标图案
				// jFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());
				isDragged = true;
				// TODO 更换鼠标图案
				// jFrame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});
		panel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isDragged) {
					loc = new Point(me.getLocation().x + e.getX() - tmp.x, me
							.getLocation().y + e.getY() - tmp.y);
					me.setLocation(loc);
				}
			}
		});
	}
	
	public static String getUserID(){
		return userID;
	}
	public static void distroy(){
		MainFrame.getInstance().dispose();
		Singleton=null;
	}
}
