package View.LoginUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ImageObserver;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

import com.sun.awt.AWTUtilities;

import vo.UserVO;

import Control.userControl.UserControl;
import Control.userControl.UserControlService;
import Model.patternModel.TestHelper;
import View.MainFrame;
import View.MainJumpService;
import View.MessageFrame;
import View.GameUI.ViewObserver;
import View.LoginUI.LoginPanel.LoginListener;
import View.LoginUI.LoginPanel.RegisterListener;
import View.MainUI.MainPanel;

/**
 * @ClassName LoginFrame
 * @Description
 * @author 徐敏
 * @Date 上午10:54:44
 */
public class LoginFrame extends JFrame implements MainJumpService {
	
	public  int frameWidth;
	public  int frameHeight;
	public static int screenWidth;
	public static int screenHeight;
	private static LoginFrame instance;
	private LoginPanel loginPanel;
	private RegisterPanel registerPanel;
	private LoginFrame me;
    public Image MOUSE = new ImageIcon("graph/mouse.png").getImage();
	public Image logo = new ImageIcon("graph/logo.png").getImage();


	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(6600);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginFrame frame=LoginFrame.getInstance();
	}

	public static LoginFrame getInstance(){
		if(instance==null){
			instance=new LoginFrame();
		}
		return instance;
	}
	
	
	@SuppressWarnings("restriction")
	private LoginFrame() {
		setDimension();
	
		setBounds((screenWidth - frameWidth) / 2,
				(screenHeight - frameHeight) / 3, frameWidth,
				frameHeight);
		setIconImage(Toolkit.getDefaultToolkit().getImage("图标.jpg"));
		setTitle("登录界面");
	    setUndecorated(true); // 去除边框
		setIconImage(logo);
		setVisible(true);
		JLabel loadingLabel = new JLabel("Loading...");
		loadingLabel.setFont(new Font("Georgia", Font.BOLD, 18));
		loadingLabel.setForeground(Color.CYAN);
		loadingLabel.setBounds(frameWidth * 4 / 10, frameHeight / 2,
				frameWidth /8, frameWidth /10);
		loadingLabel.setVisible(true);
		this.add(loadingLabel);
        me=this;
        
		// 设置窗口圆角
		AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(0.0D,
				0.0D, this.getWidth(), this.getHeight(), 26.0D, 26.0D));
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				MOUSE, new Point(), null));
        loginPanel = new LoginPanel(this);
        registerPanel =new RegisterPanel(this);
        setLogin();
	}

	public void setDimension() {
		Toolkit kit = Toolkit.getDefaultToolkit(); // 得到工具箱
		Dimension screenSize = kit.getScreenSize(); // 获得本机屏幕分辨率
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
		frameWidth = 0;
		frameHeight = 0;
		if (screenWidth * 3 > screenHeight * 4) { // 将窗体显示在屏幕的合适位置，并设置合适大小
			frameHeight = screenHeight*4/5;
			frameWidth =frameHeight * 4 / 3;
		} else {
			frameWidth = screenWidth * 2 / 3;
			frameHeight = frameWidth * 4 / 3;
		}
	  }
	
    public void setLogin(){
        this.setContentPane(loginPanel);
    }
    public void setRegister(){
        this.setContentPane(registerPanel);
    }
	
	public void loginDispose() {
		me.dispose();
	}

	@Override
	public void loginJump(UserVO userVO) {
		// TODO Auto-generated method stub
		me.dispose();
		LoginFrame.instance=null;
		MainFrame frame = MainFrame.getInstance();
		TestHelper.testView(this+"");
		frame.setUser(userVO);
		MainPanel mainPanel = new MainPanel(frame);
	}

	@Override
	public void instruction(String result) {
	 	// TODO Auto-generated method stub
                  MessageFrame.show(result);
	}
	
	public void modifyImage(JLabel label, ImageIcon temp) {
		Image resulttemp = temp.getImage().getScaledInstance(label.getWidth(),
				label.getHeight(), temp.getImage().SCALE_DEFAULT);
		temp = new ImageIcon(resulttemp);
		label.setIcon(temp);
	}
	
	private Point loc = null;
	private Point tmp = null;
	private boolean isDragged = false;
	/**
	 * 拖动panel可以移动窗体位置
	 */
	public void setDragable(JPanel panel) {
		panel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				isDragged = false;
				// TODO 更换鼠标图案
				// jFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(java.awt.event.MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());
				isDragged = true;
				// TODO 更换鼠标图案
				
				// jFrame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});
		panel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				if (isDragged) {
					loc = new Point(me.getLocation().x + e.getX() - tmp.x, me
							.getLocation().y + e.getY() - tmp.y);
					me.setLocation(loc);
				}
			}
		});
	}
}
