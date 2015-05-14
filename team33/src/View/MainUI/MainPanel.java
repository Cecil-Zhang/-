package View.MainUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import vo.ApplicationVO;
import vo.toInvitationVO;
import View.AudioPlayer;
import View.MainFrame;
import View.MessageFrame;

public class MainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6070884808770007295L;

	private StarButton[] mainButton = new StarButton[5];
	private JLabel[] mainLabel = new JLabel[5];
	public JLabel mailbox;
	public int IMG_STAR_BUTTON_WIDTH;
	public int width;
	public int height;
	@SuppressWarnings("unused")
	private int MESS_COUNT;
	private CardLayout layout;
	private JPanel contentPanel2;
	private ArrayList<ApplicationVO> app;
	private ArrayList<toInvitationVO> inv;

	MainFrame mainFrame;

	public MainPanel(MainFrame mFrame) {
		mainFrame = mFrame;
		mainFrame.setMp(this);
		setLayout(null);
		mainFrame.setDragable(this);

		width = mainFrame.getWidth();
		height = mainFrame.getHeight();
		IMG_STAR_BUTTON_WIDTH = mainFrame.getWidth() / 12;

		// 最小化和关闭图标
		final JLabel minimizeLabel = new JLabel();
		minimizeLabel
				.setBounds(width * 19/21 , 0, width / 22, width / 22);
		mainFrame.modifyImage(minimizeLabel, mainFrame.allImage.MINIMIZE_ICON);
		minimizeLabel.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent e) {
				mainFrame.setExtendedState(mainFrame.ICONIFIED);
			}
			public void mouseEntered(MouseEvent e){
				if(mainFrame.isVoice()){
				      new AudioPlayer("sound/anjian.wav");				
				      }
				mainFrame.modifyImage(minimizeLabel, mainFrame.allImage.MINIMIZE_ICON2);
			}
	        public void mouseExited(MouseEvent e){
	        	mainFrame.modifyImage(minimizeLabel, mainFrame.allImage.MINIMIZE_ICON);
			}
		});
		this.add(minimizeLabel);

		final JLabel closeLabel = new JLabel();
		closeLabel.setBounds(width * 20/21,0, width / 22, width / 22);
		mainFrame.modifyImage(closeLabel, mainFrame.allImage.CLOSE_ICON);
		closeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("我点了关闭");
				if (mainFrame.isConnected) {
					mainFrame.ucs.logoff(mainFrame.user.getId());
					System.out.println("我在注销-----------");
				}
				System.exit(0);
			}
			
			public void mouseEntered(MouseEvent e){
				if(mainFrame.isVoice()){
				      new AudioPlayer("sound/anjian.wav");				
				      }
				mainFrame.modifyImage(closeLabel, mainFrame.allImage.CLOSE_ICON2);
			}
	        public void mouseExited(MouseEvent e){
	        	mainFrame.modifyImage(closeLabel, mainFrame.allImage.CLOSE_ICON);
			}
		});
		this.add(minimizeLabel);
		this.add(closeLabel);

		// 信箱处理
		mailbox = new JLabel();
		mailbox.setBounds(width * 51 / 60, 5, width / 22, width / 22);
		mainFrame.modifyImage(mailbox, mainFrame.allImage.MAILBOX1);
		if (mainFrame.isConnected) {
			app = mainFrame.mcs.getApplicationVO(mainFrame.user.getId());
			if (app == null || app.size() == 0) {
				mainFrame.modifyImage(mailbox, mainFrame.allImage.MAILBOX1);
			} else {
				mainFrame.modifyImage(mailbox, mainFrame.allImage.MAILBOX2);
				if(mainFrame.isVoice()){
				      new AudioPlayer("sound/tishi.wav");				
				      }
			}
		}

		mailbox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//
				if (mainFrame.isConnected) {
					app = mainFrame.mcs.getApplicationVO(mainFrame.user.getId());
					inv = mainFrame.mcs.getInvitationVO(mainFrame.user.getId());
				}
				try {
					if (app.size() != 0) {
						for (int i = 0; i < app.size(); i++) {
							mainFrame.showFriendMess(app.get(i).getFromId(),
									app.get(i).getFromName());
						}
						if (inv.size() != 0) {
							for (int i = 0; i < app.size(); i++) {
								mainFrame.showGameMess(inv.get(i).getFromId(),
										inv.get(i).getFromName());
							}
						}
					} else if (inv.size() != 0) {
						for (int i = 0; i < inv.size(); i++) {
							mainFrame.showGameMess(inv.get(i).getFromId(), inv
									.get(i).getFromName());
						}
					} else {
						MessageFrame.show("您没有未读信息！");
					}
				} catch (NullPointerException npe) {
					System.out.println("没有信息");
				}
				MESS_COUNT = 0;
				mainFrame.modifyImage(mailbox, mainFrame.allImage.MAILBOX1);
			}

			public void mouseEntered(MouseEvent e) {
				if(mainFrame.isVoice())  new AudioPlayer("sound/anjian.wav");
			}

		});
		this.add(mailbox);
		
		JSeparator sep1 = new JSeparator(JSeparator.VERTICAL);
		sep1.setForeground(Color.CYAN);
		sep1.setSize(4,height/15);
		sep1.setLocation(width*162/180, height /100);
		add(sep1);
		
		// cardLayout.
		final JPanel contentPanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1084888759643210707L;

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Dimension size = this.getSize();
				ImageIcon icon = new ImageIcon(
						"graph/main/panel_background.png");
				g.drawImage(icon.getImage(), 0, 0, size.width, size.height,
						null);
			}
		};

		contentPanel.setBounds(width / 13, height / 10, width * 3 / 5,
				height * 3 / 5);
		contentPanel.setLayout(new CardLayout());
		contentPanel.setOpaque(false);
		contentPanel2=contentPanel;
		this.add(contentPanel);

		final InfoPanel info = new InfoPanel(this.mainFrame);
		final FriendPanel friend = new FriendPanel(this.mainFrame);
		final RankPanel rank = new RankPanel(this.mainFrame);
		final SelectGameModePanel select = new SelectGameModePanel(
				this.mainFrame);
		SettingPanel set = new SettingPanel(this.mainFrame);

		contentPanel.add(rank, "rank");
		contentPanel.add(info, "info");
		contentPanel.add(friend, "friend");
		contentPanel.add(select, "select");
		contentPanel.add(set, "set");

		final CardLayout cl = (CardLayout) contentPanel.getLayout();
		cl.show(contentPanel, "rank");
		layout=cl;

		Point[] mainPoint1 = {
				new Point(width / 17, height * 15 / 20),
				new Point(width * 4 / 17, height * 15 / 20),
				new Point(width * 7 / 17, height * 15 / 20),
				new Point(width * 10 / 17, height * 15 / 20),
				new Point(width * 10 / 13, height * 15 / 20
						- IMG_STAR_BUTTON_WIDTH * 2 / 5) };
		Point[] mainPoint2 = { new Point(width * 50 / 1700, height * 18 / 20),
				new Point(width * 350 / 1700, height * 18 / 20),
				new Point(width * 680 / 1700, height * 18 / 20),
				new Point(width * 950 / 1700, height * 18 / 20),
				new Point(width * 1280 / 1700, height * 18 / 20) };

		for (int i = 0; i < 5; i++) {

			if (i == 4) {
				mainButton[i] = new StarButton("1",
						IMG_STAR_BUTTON_WIDTH * 3 / 2,
						IMG_STAR_BUTTON_WIDTH * 3 / 2);
				mainFrame.modifyImage(mainButton[i], mainFrame.allImage.CANDY);
			} else {
				mainButton[i] = new StarButton("1", IMG_STAR_BUTTON_WIDTH,
						IMG_STAR_BUTTON_WIDTH);

			}
			mainButton[i].setLocation(mainPoint1[i]);

			mainLabel[i] = new JLabel();
			mainLabel[i].setSize(IMG_STAR_BUTTON_WIDTH * 4 / 3,
					IMG_STAR_BUTTON_WIDTH * 8 / 15);
			mainLabel[i].setLocation(mainPoint2[i]);
			mainFrame
					.modifyImage(mainLabel[i], mainFrame.allImage.MAIN_ICON[i]);
			add(mainButton[i]);
			add(mainLabel[i]);
		}

		mainButton[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(contentPanel, "info");
				info.updateInfo();
			}
		});
		mainButton[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(contentPanel, "rank");
				rank.updateInfo();
			}
		});
		mainButton[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(contentPanel, "friend");
				friend.updateInfo();
			}
		});
		mainButton[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(contentPanel, "set");
			}
		});
		mainButton[4].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(contentPanel, "select");
				select.updateInfo();
			}
		});

		JLabel logoLabel = new JLabel();
		logoLabel.setBounds(width * 3 / 4, height / 7, width / 6, height*5 / 8);
		mainFrame.modifyImage(logoLabel, mainFrame.allImage.LOGO_VERTICAL);
		add(logoLabel);
		if(mainFrame.isMusic()){
			mainFrame.playMusic("sound/back.wav");		
		      }
	
		mainFrame.setContentPane(this);
		mainFrame.repaint();
		mainFrame.validate();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = this.getSize();
		ImageIcon icon = new ImageIcon("graph/背景2.jpg");
		g.drawImage(icon.getImage(), 0, 0, size.width, size.height, null);
	}

	public void newMess() {
		// TODO Auto-generated method stub
		mainFrame.modifyImage(mailbox, mainFrame.allImage.MAILBOX2);
		if(mainFrame.isVoice()){
			new AudioPlayer("sound/tishi.wav");
		}
		MESS_COUNT++;
	}
	
	public void setPanel(){
		layout.show(contentPanel2, "rank");
	}

}
