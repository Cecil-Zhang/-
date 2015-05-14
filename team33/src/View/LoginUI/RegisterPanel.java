package View.LoginUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Control.userControl.UserControl;
import Control.userControl.UserControlService;
import Model.patternModel.TestHelper;
import View.AudioPlayer;
import View.MessageFrame;
import View.RButton;

/**
 * @ClassName RegisterPanel
 * @Description
 * @author 徐敏
 * @Date 上午10:55:12
 */
public class RegisterPanel extends JPanel {

    private LoginFrame loginFrame;
	private RButton registerButton;
	private RButton quitButton;
	private JTextField idField;
	private JTextField passwordField;
	private JRadioButton female;
	private JRadioButton male;
	private ButtonGroup gender;
	
	private JRadioButton[] portrait = new JRadioButton[5];
	private ButtonGroup port;
	private ImageIcon portraitIcon[] = new ImageIcon[5];
//	private ImageIcon portraitIcon[] = new ImageIcon[5];

	private int WIDTH;
	private int HEIGHT;

	public ImageIcon MINIMIZE_ICON = new ImageIcon("graph/minimize.png");
	public ImageIcon MINIMIZE_ICON2 = new ImageIcon("graph/minimize2.png");
	public ImageIcon CLOSE_ICON = new ImageIcon("graph/close.png");
	public ImageIcon CLOSE_ICON2 = new ImageIcon("graph/close2.png");
	public ImageIcon male_ICON = new ImageIcon("graph/男.png");
	public ImageIcon female_ICON = new ImageIcon("graph/女.png");

	
	 public RegisterPanel(LoginFrame lFrame){


			this.loginFrame=lFrame;
			
			setLayout(null);
			setOpaque(false);
			this.WIDTH=loginFrame.getWidth();
		    this.HEIGHT=loginFrame.getHeight();
		    loginFrame.setDragable(this);
		    
		    setSize(WIDTH,HEIGHT);



		 // 最小化和关闭图标
			Image temp1 = MINIMIZE_ICON.getImage().getScaledInstance(WIDTH / 14,
					WIDTH / 14, MINIMIZE_ICON.getImage().SCALE_DEFAULT);
			MINIMIZE_ICON = new ImageIcon(temp1);

			final JLabel minimizeLabel = new JLabel();
			minimizeLabel
					.setBounds(WIDTH * 13 / 15, 0, WIDTH / 15, WIDTH / 15);
			minimizeLabel.setIcon(MINIMIZE_ICON);
			minimizeLabel.addMouseListener(new MouseAdapter() {	
				@Override
				public void mouseClicked(MouseEvent e) {
					loginFrame.setExtendedState(loginFrame.ICONIFIED);
				}
				public void mouseEntered(MouseEvent e){
					loginFrame.modifyImage(minimizeLabel, MINIMIZE_ICON2);
				}
		        public void mouseExited(MouseEvent e){
		        	loginFrame.modifyImage(minimizeLabel, MINIMIZE_ICON);
				}
			});

			Image temp2 = CLOSE_ICON.getImage().getScaledInstance(WIDTH / 14,
					WIDTH / 14, CLOSE_ICON.getImage().SCALE_DEFAULT);
			CLOSE_ICON = new ImageIcon(temp2);
			final JLabel closeLabel = new JLabel();
			closeLabel.setBounds(WIDTH * 14 / 15, 0, WIDTH / 15, WIDTH / 15);
			closeLabel.setIcon(CLOSE_ICON);
			closeLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.exit(0);
				}
				public void mouseEntered(MouseEvent e){
					loginFrame.modifyImage(closeLabel, CLOSE_ICON2);
				}
		        public void mouseExited(MouseEvent e){
		        	loginFrame.modifyImage(closeLabel, CLOSE_ICON);
				}
			});
			

				


			add(minimizeLabel);
			add(closeLabel);

			idField = new JTextField();
//			idField.setCaretPosition(0);
			idField.setFont(new Font("宋体", Font.BOLD, 22));
			idField.setOpaque(false);
			idField.setBorder(new LineBorder(new Color(158,95,66)));
			idField.setBounds(WIDTH * 4 / 9, HEIGHT * 2/ 17, WIDTH * 1 / 4,
					HEIGHT * 1 / 18);
			idField.setBackground(new Color(239, 249, 251));
			idField.setForeground(Color.black);
			// idField.setBorder(false);
			add(idField);
			idField.setColumns(10);
			idField.setVisible(true);

			passwordField = new JPasswordField();
			passwordField.setFont(new Font("宋体", Font.BOLD, 22));
			passwordField.setOpaque(false);
			passwordField.setBorder(new LineBorder(new Color(158,95,66)));
			passwordField.setBounds(WIDTH * 4 / 9, HEIGHT * 3/ 14, WIDTH * 1 / 4,
					HEIGHT * 1 / 18);
			passwordField.setForeground(Color.black);
			passwordField.setBackground(new Color(239, 249, 251));
			add(passwordField);
			passwordField.setColumns(10);
			passwordField.setVisible(true);
	
			
			gender = new ButtonGroup();
			male = new JRadioButton("男");
			male.setFont(new Font("楷体", Font.BOLD, 24));
			male.setBounds(WIDTH * 4 / 9, HEIGHT * 5/ 16, WIDTH * 1 / 16,
					HEIGHT * 1 / 18);
			male.setOpaque(false);
			gender.add(male);
			add(male);
			male.setVisible(true);

			//
			female = new JRadioButton("女");
			female.setFont(new Font("楷体", Font.BOLD, 24));
			female.setBounds(WIDTH * 5/ 9, HEIGHT * 5/ 16, WIDTH * 1 / 4,
					HEIGHT * 1 / 18);
			female.setOpaque(false);
			gender.add(female);
			add(female);
			female.setVisible(true);
			
			Image temp;
			for (int i = 0; i < 5; i++) {
				portraitIcon[i] = new ImageIcon("graph/portrait/" + String.valueOf(i+1) + ".jpg");
				temp = portraitIcon[i].getImage().getScaledInstance(WIDTH * 1/ 11, WIDTH / 11
						, portraitIcon[i].getImage().SCALE_DEFAULT);
				portraitIcon[i] = new ImageIcon(temp);
			}
			
			
			JLabel []porLabel = new JLabel[5];
			for (int i = 0; i < 5; i++) {
			porLabel[i] =new JLabel();
			porLabel[i].setIcon(portraitIcon[i]);
			porLabel[i].setVisible(true);
		    add(porLabel[i]);
			}
			porLabel[0].setBounds(WIDTH * 15/ 34,HEIGHT*6/14, WIDTH / 11, WIDTH / 11);
			porLabel[1].setBounds(WIDTH * 21/ 34,HEIGHT*6/14, WIDTH / 11, WIDTH / 11);
			porLabel[2].setBounds(WIDTH * 27/ 34,HEIGHT*6/14, WIDTH / 11, WIDTH / 11);
			porLabel[3].setBounds(WIDTH * 15/ 34,HEIGHT*9/14, WIDTH / 11, WIDTH / 11);
			porLabel[4].setBounds(WIDTH * 21/ 34,HEIGHT*9/14, WIDTH / 11, WIDTH / 11);
			
			port = new ButtonGroup();
			
			for(int j=0;j<5;j++){
			portrait[j] = new JRadioButton("   "+String.valueOf(j));
			portrait[j].setFont(new Font("楷体", Font.BOLD, 28));
					portrait[j].setOpaque(false);
			port.add(portrait[j]);
			add(portrait[j]);
			portrait[j].setVisible(true);
			}
			portrait[0].setBounds(WIDTH * 14/ 35,HEIGHT*7/16, WIDTH * 1 / 10,HEIGHT * 1 / 10);
			portrait[1].setBounds(WIDTH * 14/ 35+WIDTH * 6/ 34,HEIGHT*7/16, WIDTH * 1 / 10,HEIGHT * 1 / 10);
			portrait[2].setBounds(WIDTH * 14/ 35+WIDTH * 12/ 34,HEIGHT*7/16, WIDTH * 1 / 10,HEIGHT * 1 / 10);
			portrait[3].setBounds(WIDTH * 14/ 35,HEIGHT*7/16+HEIGHT*3/14, WIDTH * 1 / 10,HEIGHT * 1 / 10);
			portrait[4].setBounds(WIDTH * 14/ 35+WIDTH * 6/ 34,HEIGHT*7/16+HEIGHT*3/14, WIDTH * 1 / 10,HEIGHT * 1 / 10);

			
				
			registerButton = new RButton("注册");
			registerButton.setFont(new Font("楷体", Font.BOLD, 22));
			registerButton.setBounds(WIDTH * 4 / 15, HEIGHT * 5 / 6, WIDTH * 1 / 8,
					HEIGHT * 1 / 13);
			registerButton.setForeground(new Color(255, 255, 255));
			add(registerButton);
			registerButton.addActionListener(new RegisterListener());
			registerButton.setVisible(true);

			quitButton = new RButton("取消");
			quitButton.setFont(new Font("楷体", Font.BOLD, 20));
			quitButton.setBounds(WIDTH * 5 / 9, HEIGHT * 5 / 6, WIDTH * 1 / 8,
					HEIGHT * 1 / 13);
			quitButton.setForeground(new Color(255, 255, 255));
			add(quitButton);
			quitButton.addActionListener(new QuitListener());
			quitButton.setVisible(true);
			loginFrame.setContentPane(this);
	 }
	 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = this.getSize();
		ImageIcon icon = new ImageIcon("graph/login_background.jpg");
		g.drawImage(icon.getImage(), 0, 0, size.width, size.height, null);
		ImageIcon icon2 = new ImageIcon("graph/register_word.png");
		g.drawImage(icon2.getImage(), 0, 0, size.width, size.height, null);
	}
	
	class RegisterListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String userName = idField.getText();
			if(userName.equals("")){
				MessageFrame.show("请输入用户名");
				return;
			}
			String pw = passwordField.getText();
			if(pw.equals("")){
				MessageFrame.show("请输入密码");
				return;
			}
			boolean gender=false;
			
			if (female.isSelected()) {
				gender = false;
			}else if(male.isSelected()) {
				gender = true;
			}else{
				MessageFrame.show("亲，请选择是否有把儿");
				return;
			}			
			int img=0;
			
			for(int i =0;i<5;i++){
				if(portrait[i].isSelected()){
					img=i;
					break;
				}
			}
		
			TestHelper.testView(userName+"\n"+pw+"\n"+gender+"\n"+img);
			UserControlService service = new UserControl();
			String id=service.register(userName, pw, gender, img);
			TestHelper.testView(id);
			
			try{
		    	int temp=Integer.parseInt(id);
		    }catch(NumberFormatException e1){
		    	e1.printStackTrace();
		    	MessageFrame.show(id);
		    	return;
		    }

			MessageFrame.show("您的账号ID为"+id);
		}
		
		public void mouseEntered(MouseEvent e){
			new AudioPlayer("sound/anjian.wav");
		}
		
	}

	class QuitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
	        loginFrame.setLogin();
			// new RegisterFrame();
		}
		public void mouseEntered(MouseEvent e){
			new AudioPlayer("sound/anjian.wav");
		}
	}

}
