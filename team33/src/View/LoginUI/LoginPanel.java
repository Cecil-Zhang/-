package View.LoginUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Control.userControl.UserControl;
import Control.userControl.UserControlService;
import View.AudioPlayer;
import View.RButton;


/**
 * @ClassName LoginPanel
 * @DescriptionA
 * @author 徐敏
 * @Date 上午10:54:59
 */
public class LoginPanel extends JPanel {

	public int WIDTH;
	public int HEIGHT;
	private LoginFrame loginFrame;
	
	private RButton loginButton;
	private JButton registerButton;
	private JTextField idField;
	private JTextField passwordField;

	public ImageIcon MINIMIZE_ICON = new ImageIcon("graph/minimize.png");
	public ImageIcon MINIMIZE_ICON2 = new ImageIcon("graph/minimize2.png");
	public ImageIcon CLOSE_ICON = new ImageIcon("graph/close.png");
	public ImageIcon CLOSE_ICON2 = new ImageIcon("graph/close2.png");

	public LoginPanel(LoginFrame lFrame){
		this.loginFrame=lFrame;
		setLayout(null);
		setOpaque(false);
		this.WIDTH=loginFrame.getWidth();
	    this.HEIGHT=loginFrame.getHeight();
	    loginFrame.setDragable(this);
	    
	    setSize(WIDTH,HEIGHT);

	    
	    //爱消软糖
	    idField = new JTextField();
	    idField.setFont(new Font("宋体", Font.BOLD, 22));
		idField.setOpaque(false);
//		idField.setCaretPosition(idField.getText().length());
		idField.setBorder(new LineBorder(new Color(158,95,66)));
		idField.setBounds(WIDTH * 5/28, HEIGHT * 15/43, WIDTH * 3 /10,
				HEIGHT * 1 / 14);
		add(idField);
		idField.setColumns(10);
		idField.setVisible(true);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("宋体", Font.BOLD, 22));
		passwordField.setOpaque(false);
		passwordField.setBorder(new LineBorder(new Color(158,95,66)));
		passwordField.setBounds(WIDTH * 5/28, HEIGHT * 22/ 43, WIDTH * 3 /10,
				HEIGHT * 1 / 14);
//		passwordField.setBackground(new Color(239, 249, 251));
		add(passwordField);
		passwordField.setColumns(10);
		passwordField.setVisible(true);
		passwordField.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e ){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					UserControlService ucs = new UserControl();
					ucs.login(idField.getText(), passwordField.getText());
				}
			}
		});

		loginButton = new RButton("登    录");
		// loginButton.setIcon(new ImageIcon("return.png"));
		loginButton.setFont(new Font("楷体", Font.BOLD, 24));
		loginButton.setBounds(WIDTH * 1 / 9, HEIGHT * 11/ 16, WIDTH * 1 / 5,
				HEIGHT * 1 / 12);
	    add(loginButton);
		loginButton.addActionListener(new LoginListener());
		loginButton.setVisible(true);
		

		registerButton = new JButton("注册");
		registerButton.setFont(new Font("楷体", Font.BOLD, 22));
		registerButton.setBounds(WIDTH * 7 / 9, HEIGHT * 8 / 9, WIDTH * 1 / 3,
				HEIGHT * 1 / 8);
		registerButton.setBorderPainted(false);
		registerButton.setContentAreaFilled(false);
		registerButton.setForeground(new Color(255, 255, 255));
		add(registerButton);
		registerButton.addActionListener(new RegisterListener());
		registerButton.setVisible(true);

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
						if(RButton.isVoice){
						      new AudioPlayer("sound/anjian.wav");				
						      }
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
						if(RButton.isVoice){
						      new AudioPlayer("sound/anjian.wav");				
						      }
						loginFrame.modifyImage(closeLabel, CLOSE_ICON2);
					}
			        public void mouseExited(MouseEvent e){
			        	loginFrame.modifyImage(closeLabel, CLOSE_ICON);
					}
				});
				
			add(closeLabel);
			add(minimizeLabel);

	}
	
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			UserControlService ucs = new UserControl();
			ucs.login(idField.getText(), passwordField.getText());

		}
    }

	class RegisterListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
             loginFrame.setRegister();

		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = this.getSize();
		ImageIcon icon = new ImageIcon("graph/login_background.jpg");
		g.drawImage(icon.getImage(), 0, 0, size.width, size.height, null);
		ImageIcon icon2 = new ImageIcon("graph/login_word.png");
	   g.drawImage(icon2.getImage(), 0, 0, size.width, size.height, null);
	}
}
