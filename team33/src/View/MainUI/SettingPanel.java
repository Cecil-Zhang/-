package View.MainUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Control.userControl.UserControl;
import Control.userControl.UserControlService;
import Model.SettingModel.SettingModel;
import Model.patternModel.TestHelper;
import View.AudioPlayer;
import View.MainFrame;
import View.MessageFrame;
import View.RButton;
import View.LoginUI.LoginFrame;

/**
 * @ClassName SettingPanel
 * @Description
 * @author ����
 * @Date ����11:22:54
 */
public class SettingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8541950661264859444L;
	private MainFrame mainFrame;
	private int width;
	private int height;

	private JPanel showPanel; // ��Ϣչʾ��
	private JButton basicSetting; // ��������
	private JButton teamMessage; // ��Ϸ�Ŀ����Ŷӵ���Ϣ
	private JButton gameSetting; // ��Ϸ����
	private JButton security; // ��ȫ����
	private JButton save;
	private JButton save1;
	private JButton save2;
	private JButton cancel;
	JButton voiceUp;
	JButton voiceDown;
	private boolean style = true;
	private boolean way = true;

	JRadioButton soundOpen;
	JRadioButton soundClosed;
	JRadioButton musicOpen;
	JRadioButton musicClosed;
	JRadioButton longitudina;
	JRadioButton lateral;
	JRadioButton style1;
	JRadioButton style2;
	JPasswordField oldPassword;
	JPasswordField password;
	JPasswordField password2;
	JTextField number = new JTextField("50");;
	JLabel aboutus;

	JButton exit;
	BasicListener a = new BasicListener();
	GameListener b = new GameListener();
	SafeListener c = new SafeListener();

	public ImageIcon MINIMIZE_ICON = new ImageIcon("graph/minimize.png");
	public ImageIcon CLOSE_ICON = new ImageIcon("graph/close.png");
	private ImageIcon typeface_Icon[] = new ImageIcon[10];

	public SettingPanel(MainFrame mFrame) {
		this.mainFrame = mFrame;
		this.width = mainFrame.getWidth() * 3 / 5;
		this.height = mainFrame.getHeight() * 3 / 5;
		initialPic();

		setLayout(null);
		setOpaque(false);
		setVisible(true);
		setSize(width, height);

		showPanel = new JPanel();
		showPanel.setBounds(width / 4, 0, width * 3 / 4, height);
		showPanel.setOpaque(false);
		showPanel.setBackground(new Color(239, 249, 251));
		showPanel.setForeground(new Color(239, 249, 251));
		add(showPanel);
		showPanel.setLayout(null);
		showPanel.setVisible(true);

		basicSetting = new JButton();
		basicSetting.setBounds(0, height * 2 / 25, width / 4, height * 3 / 25);
		basicSetting.setBorderPainted(false);
		basicSetting.setContentAreaFilled(false);
		basicSetting.setIcon(typeface_Icon[0]);
		add(basicSetting);
		basicSetting.addMouseListener(new mouseListener());
		basicSetting.setVisible(true);

		gameSetting = new JButton();
		gameSetting.setBounds(0, height * 6 / 25, width / 4, height * 3 / 25);
		gameSetting.setBorderPainted(false);
		gameSetting.setContentAreaFilled(false);
		gameSetting.setIcon(typeface_Icon[1]);
		add(gameSetting);
		gameSetting.addMouseListener(new mouseListener());
		gameSetting.setVisible(true);

		security = new JButton();
		security.setBounds(0, height * 10 / 25, width / 4, height * 3 / 25);
		security.setBorderPainted(false);
		security.setContentAreaFilled(false);
		security.setIcon(typeface_Icon[2]);
		add(security);
		security.addMouseListener(new mouseListener());
		security.setVisible(true);

		teamMessage = new JButton();
		teamMessage.setBounds(0, height * 14 / 25, width / 4, height * 3 / 25);
		teamMessage.setBorderPainted(false);
		teamMessage.setContentAreaFilled(false);
		teamMessage.setIcon(typeface_Icon[3]);
		add(teamMessage);
		teamMessage.addMouseListener(new mouseListener());
		teamMessage.setVisible(true);

		// ���ע����ť���˻ص�¼����
		exit = new JButton();
		exit.setBounds(0, height * 18 / 25, width / 4, height * 3 / 25);
		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);
		exit.setIcon(typeface_Icon[4]);
		add(exit);
		exit.addMouseListener(new mouseListener());
		exit.setVisible(true);

		JSeparator sep = new JSeparator(JSeparator.VERTICAL);
		sep.setForeground(Color.CYAN);
		sep.setSize(4, height * 28 / 30);
		sep.setLocation(width / 4, height / 30);
		add(sep);

		cancel = new RButton("ȡ   ��");
		cancel.setFont(new Font("΢���ź�", Font.BOLD, 17));
		cancel.setBounds(width * 8 / 15, height * 11 / 14, width / 7,
				height * 4 / 50);
		cancel.setVisible(false);
		cancel.addActionListener(new CancelListener());

		aboutus = new JLabel();
		aboutus.setBounds(0, 0, width * 3 / 4, height);
		mainFrame.modifyImage(aboutus, mainFrame.allImage.ABOUTUS);

		mainFrame.repaint();
		mainFrame.validate();

	}

	class mouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == basicSetting) {
				TestHelper.testprint("��������");
				showPanel.removeAll();
				showPanel.add(cancel);
				showBasicSetting();

			} else if (e.getSource() == gameSetting) {
				TestHelper.testprint("��Ϸ����");
				showPanel.removeAll();
				showPanel.add(cancel);
				showGameSetting();
			} else if (e.getSource() == security) {
				TestHelper.testprint("��ȫ����");
				showPanel.removeAll();
				showPanel.add(cancel);
				showSecurity();
			} else if (e.getSource() == teamMessage) {
				TestHelper.testprint("����");
				showPanel.removeAll();
				showPanel.add(aboutus);
				mainFrame.repaint();

			} else if (e.getSource() == exit) {
				TestHelper.testprint("ע��");
				MainFrame mf = MainFrame.getInstance();
				UserControlService ucs = new UserControl();
				ucs.logoff(mf.user.getId());
				mainFrame.stopMusic();
				MainFrame.distroy();
				LoginFrame.getInstance();
			} else
				TestHelper.testprint("ϵͳ����");

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == basicSetting) {
				basicSetting.setIcon(typeface_Icon[5]);
			} else if (e.getSource() == gameSetting) {
				gameSetting.setIcon(typeface_Icon[6]);
			} else if (e.getSource() == security) {
				security.setIcon(typeface_Icon[7]);
			} else if (e.getSource() == teamMessage) {
				teamMessage.setIcon(typeface_Icon[8]);
			} else if (e.getSource() == exit) {
				exit.setIcon(typeface_Icon[9]);
			}

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == basicSetting) {
				basicSetting.setIcon(typeface_Icon[0]);
			} else if (e.getSource() == gameSetting) {
				gameSetting.setIcon(typeface_Icon[1]);
			} else if (e.getSource() == security) {
				security.setIcon(typeface_Icon[2]);
			} else if (e.getSource() == teamMessage) {
				teamMessage.setIcon(typeface_Icon[3]);
			} else if (e.getSource() == exit) {
				exit.setIcon(typeface_Icon[4]);
			}

		}
	}

	// ������С��number�ı�������Ҫ��ʾ��ǰ������С
	class VoiceListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == voiceUp) {
				int x = Integer.parseInt(number.getText()) + 1;
				if (x < 101){
					number.setText(String.valueOf(x));
				double vol = (double) Integer.parseInt(number.getText());
				AudioPlayer.setVolume(vol, true);
				}else{
					number.setText("100");
					double vol = (double) Integer.parseInt(number.getText());
					AudioPlayer.setVolume(vol, true);
				}
			} else if (e.getSource() == voiceDown) {
				int x = Integer.parseInt(number.getText()) - 1;
				if (x > -1){
					number.setText(String.valueOf(x));
					double vol = (double) Integer.parseInt(number.getText());
					AudioPlayer.setVolume(vol, true);
				}else{
					number.setText("0");
					double vol = (double) Integer.parseInt(number.getText());
					AudioPlayer.setVolume(vol, true);
				}
			}
			mainFrame.repaint();
		}
	}

	// ������Ч���õı���
	class BasicListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// save.removeActionListener(a);
			if (soundOpen.isSelected()) {
				mainFrame.setVoice(true);
				RButton.isVoice=true;
			} else if (soundClosed.isSelected()) {
				mainFrame.setVoice(false);
				RButton.isVoice=false;
			}
			if (musicOpen.isSelected()) {
				mainFrame.setMusic(true);
				mainFrame.playMusic("sound/back.wav");
			} else if (musicClosed.isSelected()) {
				mainFrame.stopMusic();
				mainFrame.setMusic(false);
			}
		
		}
	}

	// ��Ϸ������䷽��
	class GameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TestHelper.testprint("bb");
			if (longitudina.isSelected()) {
				way = true;
			} else if (lateral.isSelected()) {
				way = false;
			}
			if (style1.isSelected()) {
				style = true;
			} else if (style2.isSelected()) {
				style = false;
			}
			mainFrame.sc.setWay(way);
			mainFrame.sc.setStyle(style);

		}
	}

	// �޸����룬��Ҫ��֤��������������Ƿ���ͬ
	class SafeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String newPassword1 = password.getText();
			String newPassword2 = password2.getText();
			if (!newPassword1.equals(newPassword2)) {
				/* ֱ����ʾ�������������벻ͬ */
				MessageFrame.show("�������벻ͬ��������");
			} else {
				String UserId = MainFrame.getUserID();
				String result = mainFrame.uc.changePassword(UserId,
						newPassword1);
				/* ��ʾ�����result��� */
				MessageFrame.show(result);
			}

		}
	}

	// ȡ������
	class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/* ��ת�����а���� */
			mainFrame.getMp().setPanel();
		}
	}

	void showBasicSetting() {
		// ��������
		JLabel voice = new JLabel("Ŀǰ����:");
		voice.setFont(new Font("΢���ź�", Font.BOLD, 18));
		voice.setBounds(width * 1 / 17, height * 1 / 14, width / 6,
				height * 5 / 48);
		voice.setForeground(new Color(0, 0, 0));
		showPanel.add(voice);

		number.setText(number.getText());
		number.setOpaque(false);
		number.setFont(new Font("΢���ź�", Font.BOLD, 18));
		number.setBorder(null);
		number.setBounds(width * 5 / 17, height * 1 / 14, width / 6,
				height * 5 / 48);
		// number.setBackground(new Color(0,0,0));
		showPanel.add(number);
		number.setVisible(true);
		// number.setText("abcd");

		voiceUp = new RButton("����");
		voiceUp.setFont(new Font("΢���ź�", Font.BOLD, 17));
		voiceUp.setBounds(width * 1 / 11, height * 5 / 24, width / 9,
				height * 4 / 50);
		showPanel.add(voiceUp);
		voiceUp.addActionListener(new VoiceListener());
		voiceUp.setVisible(true);

		voiceDown = new RButton("��С");
		voiceDown.setFont(new Font("΢���ź�", Font.BOLD, 17));
		voiceDown.setBounds(width * 2 / 8, height * 5 / 24, width / 9,
				height * 4 / 50);
		showPanel.add(voiceDown);
		voiceDown.addActionListener(new VoiceListener());
		voiceDown.setVisible(true);

		JLabel voice2 = new JLabel("��Ч");
		voice2.setFont(new Font("΢���ź�", Font.BOLD, 18));
		voice2.setBounds(width * 1 / 14, height * 5 / 16, width / 6,
				height * 5 / 48);
		voice2.setForeground(new Color(0, 0, 0));
		showPanel.add(voice2);

		ButtonGroup sound = new ButtonGroup();
		soundOpen = new JRadioButton("��");
		soundOpen.setFont(new Font("΢���ź�", Font.BOLD, 14));
		soundOpen.setBounds(width * 1 / 10, height * 6 / 16 + 3, width / 6,
				height * 5 / 48);
		soundOpen.setOpaque(false);
		soundOpen.setSelected(true);
		sound.add(soundOpen);
		showPanel.add(soundOpen);
		soundOpen.setVisible(true);

		soundClosed = new JRadioButton("�ر�");
		soundClosed.setFont(new Font("΢���ź�", Font.BOLD, 14));
		soundClosed.setBounds(width * 3 / 10, height * 6 / 16 + 3, width / 6,
				height * 5 / 48);
		soundClosed.setOpaque(false);
		sound.add(soundClosed);
		showPanel.add(soundClosed);
		soundClosed.setVisible(true);

		JLabel musicLabel = new JLabel("����");
		musicLabel.setFont(new Font("΢���ź�", Font.BOLD, 18));
		musicLabel.setBounds(width * 1 / 14, height * 8 / 16, width / 6,
				height * 5 / 48);
		musicLabel.setForeground(new Color(0, 0, 0));
		showPanel.add(musicLabel);

		ButtonGroup music = new ButtonGroup();
		musicOpen = new JRadioButton("��");
		musicOpen.setFont(new Font("΢���ź�", Font.BOLD, 14));
		musicOpen.setBounds(width * 1 / 10, height * 9 / 16 + 3, width / 6,
				height * 5 / 48);
		musicOpen.setOpaque(false);
		musicOpen.setSelected(true);
		music.add(musicOpen);
		showPanel.add(musicOpen);
		musicOpen.setVisible(true);

		musicClosed = new JRadioButton("�ر�");
		musicClosed.setFont(new Font("΢���ź�", Font.BOLD, 14));
		musicClosed.setBounds(width * 3 / 10, height * 9 / 16 + 3, width / 6,
				height * 5 / 48);
		musicClosed.setOpaque(false);
		music.add(musicClosed);
		showPanel.add(musicClosed);
		musicClosed.setVisible(true);

		save = new RButton("��   ��");
		save.setFont(new Font("΢���ź�", Font.BOLD, 17));
		save.setBounds(width * 5 / 17, height * 11 / 14, width / 7,
				height * 4 / 50);
		save.setVisible(true);
		save.addActionListener(a);
		showPanel.add(save);
		
		cancel.setVisible(true);

		mainFrame.repaint();

	}

	void showGameSetting() {
		JLabel direction = new JLabel("���䷽��");
		direction.setFont(new Font("΢���ź�", Font.BOLD, 18));
		direction.setBounds(width / 15, height * 5 / 48, width / 6,
				height * 5 / 48);
		direction.setForeground(new Color(0, 0, 0));
		showPanel.add(direction);

		// ���ڵ��䷽��ĵ�ѡ��
		ButtonGroup direc = new ButtonGroup();
		longitudina = new JRadioButton("�Ӷ�������");
		longitudina.setFont(new Font("΢���ź�", Font.BOLD, 14));
		longitudina.setBounds(width / 12, height * 5 / 21, width / 6,
				height * 5 / 48);
		longitudina.setOpaque(false);
		direc.add(longitudina);
		showPanel.add(longitudina);
		longitudina.setVisible(true);

		lateral = new JRadioButton("��������");
		lateral.setFont(new Font("΢���ź�", Font.BOLD, 14));
		lateral.setBounds(width * 4 / 11, height * 5 / 21, width / 6,
				height * 5 / 48);
		lateral.setOpaque(false);
		direc.add(lateral);
		showPanel.add(lateral);
		lateral.setVisible(true);
		
		SettingModel um=new SettingModel();
		boolean dire=um.getWay();
		if(dire){
			longitudina.setSelected(true);
		}else{
			lateral.setSelected(true);
		}

		JLabel style = new JLabel("��Ϸ���");
		style.setFont(new Font("΢���ź�", Font.BOLD, 18));
		style.setBounds(width / 15, height * 5 / 13, width / 6, height * 5 / 48);
		style.setForeground(new Color(0, 0, 0));
		showPanel.add(style);

		// ������Ϸ���ĵ�ѡ��
		ButtonGroup gameStyle = new ButtonGroup();
		style1 = new JRadioButton("��������");
		style1.setFont(new Font("΢���ź�", Font.BOLD, 14));
		style1.setBounds(width / 12, height / 2, width / 6, height * 5 / 48);
		style1.setOpaque(false);
//		style1.setSelected(true);
		gameStyle.add(style1);
		showPanel.add(style1);
		style1.setVisible(true);

		style2 = new JRadioButton("��ͨ����");
		style2.setFont(new Font("΢���ź�", Font.BOLD, 14));
		style2.setBounds(width * 4 / 11, height / 2, width / 6, height * 5 / 48);
		style2.setOpaque(false);
		gameStyle.add(style2);
		showPanel.add(style2);
		style2.setVisible(true);
		
		boolean s = mainFrame.sc.getStyle();
		if(s){
			style1.setSelected(true);
		}
		else{
			style2.setSelected(true);
		}
		
		
		save1 = new RButton("��   ��");
		save1.setFont(new Font("΢���ź�", Font.BOLD, 17));
		save1.setBounds(width * 5 / 17, height * 11 / 14, width / 7,
				height * 4 / 50);
		save1.setVisible(true);
		save1.addActionListener(b);
		showPanel.add(save1);
		
		cancel.setVisible(true);
		
		mainFrame.repaint();

	}

	void showSecurity() {
		JLabel changePassword = new JLabel("�޸�����");
		changePassword.setFont(new Font("΢���ź�", Font.BOLD, 18));
		changePassword.setBounds(width * 1 / 17, height * 5 / 48, width / 6,
				height * 5 / 48);
		changePassword.setForeground(new Color(0, 0, 0));
		showPanel.add(changePassword);

		JLabel changePassword1 = new JLabel();
		changePassword1.setFont(new Font("΢���ź�", Font.BOLD, 16));
		changePassword1.setText("ԭ����");
		changePassword1.setBounds(width * 1 / 13, height * 2 / 9, width / 6,
				height * 5 / 48);
		changePassword1.setForeground(new Color(0, 0, 0));
		showPanel.add(changePassword1);

		JLabel changePassword2 = new JLabel();
		changePassword2.setFont(new Font("΢���ź�", Font.BOLD, 16));
		changePassword2.setText("������");
		changePassword2.setBounds(width * 1 / 13, height * 3 / 8, width / 6,
				height * 5 / 48);
		changePassword2.setForeground(new Color(0, 0, 0));
		showPanel.add(changePassword2);

		JLabel changePassword3 = new JLabel();
		changePassword3.setFont(new Font("΢���ź�", Font.BOLD, 16));
		changePassword3.setText("ȷ��������");
		changePassword3.setBounds(width * 1 / 13, height * 4 / 8, width / 6,
				height * 5 / 48);
		changePassword3.setForeground(new Color(0, 0, 0));
		showPanel.add(changePassword3);

		oldPassword = new JPasswordField();
		// oldPassword.setOpaque(false);
		oldPassword.setFont(new Font("΢���ź�", Font.BOLD, 16));
		oldPassword.setBorder(new LineBorder(new Color(209, 249, 251)));
		oldPassword.setBounds(width * 3 / 10, height * 2 / 8, width * 5 / 24,
				height * 4 / 54);
		// oldPassword.setBackground(new Color(0,0,0));
		// passwordField.set
		showPanel.add(oldPassword);
		oldPassword.setVisible(true);

		password = new JPasswordField();
		// password.setOpaque(false);
		password.setFont(new Font("΢���ź�", Font.BOLD, 16));
		password.setBorder(new LineBorder(new Color(209, 249, 251)));
		password.setBounds(width * 3 / 10, height * 3 / 8, width * 5 / 24,
				height * 4 / 54);
		// password.setBackground(new Color(0,0,0));
		showPanel.add(password);
		password.setVisible(true);

		password2 = new JPasswordField();
		// password2.setOpaque(false);
		password2.setFont(new Font("΢���ź�", Font.BOLD, 16));
		password2.setBorder(new LineBorder(new Color(209, 249, 251)));
		password2.setBounds(width * 3 / 10, height * 4 / 8, width * 5 / 24,
				height * 4 / 54);
		// password2.setBackground(new Color(0,0,0));
		showPanel.add(password2);
		password2.setVisible(true);
		
		save2 = new RButton("��   ��");
		save2.setFont(new Font("΢���ź�", Font.BOLD, 17));
		save2.setBounds(width * 5 / 17, height * 11 / 14, width / 7,
				height * 4 / 50);
		save2.setVisible(true);
		save2.addActionListener(c);
		showPanel.add(save2);
		
		cancel.setVisible(true);
		
		mainFrame.repaint();

	}

	void initialPic() {
		Image temp;
		int i;
		for (i = 0; i < 5; i++) {
			typeface_Icon[i] = new ImageIcon("graph/setting/"
					+ String.valueOf(i) + "_1.png");
			temp = typeface_Icon[i].getImage().getScaledInstance(width * 1 / 4,
					height * 3 / 25, typeface_Icon[i].getImage().SCALE_DEFAULT);
			typeface_Icon[i] = new ImageIcon(temp);
		}
		for (i = 5; i < 10; i++) {
			typeface_Icon[i] = new ImageIcon("graph/setting/"
					+ String.valueOf(i - 5) + "_2.png");
			temp = typeface_Icon[i].getImage().getScaledInstance(width * 2 / 9,
					height * 3 / 25, typeface_Icon[i].getImage().SCALE_DEFAULT);
			typeface_Icon[i] = new ImageIcon(temp);
		}

	}
}
