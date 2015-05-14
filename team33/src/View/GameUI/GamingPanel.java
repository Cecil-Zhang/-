package View.GameUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Control.patternControl.RivalConditionVS;
import Control.patternControl.RivalConditionVSService;
import Control.patternControl.patternControl;
import Control.patternControl.patternControlService;
import LogicService.IPHelper;
import Model.SettingModel.SettingModel;
import Model.patternModel.GameLogic;
import Model.patternModel.GameState;
import Model.patternModel.TestHelper;
import Model.patternModel.location.MatricLocation;
import View.Animation3;
import View.AudioPlayer;
import View.MainFrame;

/**
 * @ClassName GamingPanel
 * @Description
 * @author 徐敏
 * @Date 下午10:45:20
 */
public class GamingPanel extends JPanel implements ViewObserver, Serializable {

	/**
	 * 
	 */
	boolean action = true;
	boolean isDown = true;
	private ArrayList<Thread> threadList = new ArrayList<Thread>();
	int threadNum = 0;
	private static final long serialVersionUID = 4992523374704128153L;
	public MainFrame mainFrame;
	private JPanel panel;
	private GamingPanel me;
	JPanel panel2;
	JPanel panel_1;
	JPanel opponent_Panel;
	JButton opponent;// 显示对手头像
	JTextField scoreOpponent;// 显示对手分数
	JTextField note;// 提示对手进入无敌模式，道具C减5s
	JLabel superLabel;
	private JButton stopButton;
	private JButton startButton;

	int WIDTH;
	int HEIGHT;
	int preciousScore = 0;
	int scoresGet = 0;

	private JLabel scoreTotal = new JLabel();
	private JLabel scores = new JLabel();
	boolean isSuper = false;
	
	boolean isAdvised = false;
	MyThread myThread = new MyThread();

	JLabel process;
	int time = 0;
	int percent = 0;
	boolean isContinue = true;
	JTextField time_Field;

	Image temp;
	JButton button[][] = new JButton[9][9];
	JButton bangButton[] = new JButton[30];
	int animal[][] = new int[9][9];// 存储游戏区域的图案
	ImageIcon[] process_Icon = new ImageIcon[61];// 进度条
	ImageIcon Icon[] = new ImageIcon[16];// 存储鼠标未进入该区域时的图像
	ImageIcon Icon2[] = new ImageIcon[16];// 存储鼠标进入该区域后的图像
	ImageIcon super_Icon[] = new ImageIcon[2];
	ImageIcon stop;
	ImageIcon start;

	GameState gs;
	patternControlService pcService;
	mouseListener mouseListener = new mouseListener();
	private VSPanel vsPanel;

	// 若选择风格一，则调用getImage();若选择风格二，则调用getImage(boolean a)
	public GamingPanel(patternControlService service,
			ArrayList<Integer> initList, boolean direction) {
		this.pcService = service;
		this.me = this;
		mainFrame = MainFrame.getInstance();
		setLayout(null);
		// mainFrame.setContentPane(this);
		WIDTH = mainFrame.getWidth();
		HEIGHT = mainFrame.getHeight();
		isDown = direction;
		boolean style = mainFrame.sc.getStyle();
		if (style) {
			getImage();// 得到界面所需图片
		} else {
			getImage(true);
		}
		setIcon();// 初始化图片

		panel2 = new JPanel();
		panel2.setBounds(0, 0, WIDTH, HEIGHT);
		panel2.setOpaque(false);
		panel2.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel2);
		panel2.setLayout(null);
		panel2.setVisible(true);

		opponent_Panel = new JPanel();
		opponent_Panel.setBounds(WIDTH * 1 / 7, HEIGHT * 1 / 4, WIDTH * 1 / 5,
				HEIGHT * 3 / 5);
		opponent_Panel.setOpaque(false);
		opponent_Panel.setBackground(Color.white);
		add(opponent_Panel);
		opponent_Panel.setLayout(null);
		opponent_Panel.setVisible(true);

		opponent = new JButton();
		opponent_Panel.add(opponent);
		opponent.setBounds(0, HEIGHT * 1 / 35, HEIGHT * 2 / 7, HEIGHT * 2 / 7);
		opponent.setContentAreaFilled(false);// 按钮背景设为透明
		opponent.setBorderPainted(false);
		opponent.setVisible(true);

		// 对手得分
		scoreOpponent = new JTextField();
		scoreOpponent.setOpaque(false);
		scoreOpponent.setFont(new Font("宋体", Font.BOLD, 22));
		scoreOpponent.setBorder(null);
		scoreOpponent.setBounds(WIDTH * 1 / 20, HEIGHT * 7 / 21, WIDTH / 6,
				HEIGHT / 14);
		scoreOpponent.setForeground(Color.yellow);
		opponent_Panel.add(scoreOpponent);
		scoreOpponent.setVisible(false);

		// 游戏区域
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(239, 249, 251));
		panel_1.setForeground(Color.WHITE);
		panel_1.setBounds(WIDTH * 8 / 19 + 1, HEIGHT * 2 / 10 + 3,
				HEIGHT * 8 / 99 * 9, HEIGHT * 8 / 99 * 9);
		add(panel_1);
		panel_1.setOpaque(false);
		panel_1.setLayout(null);
		panel_1.setVisible(true);

		// 添加进度条、得分、设置等按钮
		panel = new JPanel();
		panel.setBounds(0, 0, WIDTH, HEIGHT);
		panel.setOpaque(false);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel);
		panel.setLayout(null);
		panel.setVisible(true);

		note = new JTextField("Sorry,prop C minus 5s");
		panel_1.add(note);
		note.setOpaque(false);
		note.setFont(new Font("宋体", Font.BOLD, 22));
		note.setLocation(WIDTH * 1 / 12, HEIGHT * 5 / 8);
		note.setForeground(Color.white);
		note.setBorder(null);
		note.setSize(WIDTH / 2, HEIGHT / 10);
		note.setVisible(false);

		JLabel word = new JLabel("得分：");
		word.setOpaque(false);
		word.setFont(new Font("宋体", Font.BOLD, 22));
		word.setBorder(null);
		word.setBounds(WIDTH * 11 / 19, HEIGHT * 2 / 17, WIDTH / 6, HEIGHT / 14);
		word.setForeground(Color.yellow);
		panel.add(word);
		word.setVisible(true);

		scoreTotal = new JLabel("0");
		scoreTotal.setOpaque(false);
		scoreTotal.setFont(new Font("宋体", Font.BOLD, 22));
		scoreTotal.setBorder(null);
		scoreTotal.setBounds(WIDTH * 6 / 9, HEIGHT * 2 / 17, WIDTH / 6,
				HEIGHT / 14);
		scoreTotal.setForeground(Color.yellow);
		panel.add(scoreTotal);
		scoreTotal.setVisible(true);

		scores = new JLabel(" ");
		panel_1.add(scores);
		scores.setOpaque(false);
		scores.setFont(new Font("宋体", Font.BOLD, 22));
		scores.setForeground(Color.white);
		scores.setBorder(null);
		scores.setSize(WIDTH / 6, HEIGHT / 14);
		scores.setVisible(false);

		superLabel = new JLabel();
		superLabel.setBounds(WIDTH * 8 / 19 + 1 - HEIGHT * 3 / 99, HEIGHT * 2
				/ 10 + 3 - HEIGHT * 3 / 99, HEIGHT * 8 / 99 * 9 + HEIGHT * 6
				/ 99, HEIGHT * 8 / 99 * 9 + HEIGHT * 6 / 99);
		superLabel.setIcon(super_Icon[1]);
		panel.add(superLabel);
		superLabel.setVisible(false);

		for (int k = 0; k < 30; k++) {
			bangButton[k] = new JButton();// 一定要有，不然指针错误
			panel_1.add(bangButton[k]);
			bangButton[k].setVisible(false);
			bangButton[k].setContentAreaFilled(false);
			bangButton[k].setBorderPainted(false);
		}

		int m;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// m = (int) (Math.random() * 5);
				m = initList.get(i * 9 + j).intValue();
				button[i][j] = new JButton(Icon[m]);
				// button[i][j] = new JButton();
				// button[i][j].setText(String.valueOf(m));
				panel_1.add(button[i][j]);
				button[i][j].setBounds(HEIGHT * 8 / 99 * j,
						HEIGHT * 8 / 99 * i, HEIGHT * 8 / 99, HEIGHT * 8 / 99);
				button[i][j].setContentAreaFilled(false);// 按钮背景设为透明
				animal[i][j] = m;
				// button[i][j].addMouseListener(mouseListener);
				button[i][j].setBorderPainted(false);
				button[i][j].setVisible(true);
				// button[i][j].setEnabled(true);
				// TestHelper.testView("button["+i+"]["+j+"]"+button[i][j].getLocation());
				// TestHelper.testView("button["+i+"]["+j+"]"+button[i][j].getLocation().x+","+button[i][j].getLocation().y);
			}

		}

		process = new JLabel();
		process.setBounds(WIDTH * 5 / 19, HEIGHT / 12, WIDTH * 6/ 12,
				HEIGHT * 3 / 99);
		process.setIcon(process_Icon[0]);
		panel.add(process);
		process.setVisible(true);

		time_Field = new JTextField("0s");
		// panel.add(time_Field);
		time_Field.setOpaque(false);
		time_Field.setFont(new Font("楷体", Font.BOLD, 22));
		time_Field.setForeground(Color.white);
		time_Field.setBorder(null);
		time_Field.setBounds(WIDTH * 5 / 19, HEIGHT / 18, HEIGHT * 8 / 12,
				HEIGHT * 3 / 99);
		time_Field.setVisible(true);

		stopButton = new JButton(stop);
		panel.add(stopButton);
		stopButton.setBounds(WIDTH * 15 / 19, HEIGHT * 4 / 51, HEIGHT * 5 / 99,
				HEIGHT * 5 / 99);
		stopButton.setContentAreaFilled(false);// 按钮背景设为透明
		stopButton.addActionListener(new StopListener());
		stopButton.setBorderPainted(false);
		stopButton.setVisible(false);

		startButton = new JButton(start);
		panel.add(startButton);
		startButton.setBounds(WIDTH * 15 / 19, HEIGHT * 4 / 51,
				HEIGHT * 5 / 99, HEIGHT * 5 / 99);
		startButton.setContentAreaFilled(false);// 按钮背景设为透明
		startButton.addActionListener(new StartListener());
		startButton.setBorderPainted(false);
		startButton.setVisible(false);

		// 最小化和关闭图标
		final JLabel minimizeLabel = new JLabel();
		minimizeLabel.setBounds(WIDTH * 19 / 21, 0, WIDTH / 22, WIDTH / 22);
		mainFrame.modifyImage(minimizeLabel, mainFrame.allImage.MINIMIZE_ICON);
		minimizeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainFrame.setExtendedState(mainFrame.ICONIFIED);
			}

			public void mouseEntered(MouseEvent e) {
				mainFrame.modifyImage(minimizeLabel,
						mainFrame.allImage.MINIMIZE_ICON2);
			}

			public void mouseExited(MouseEvent e) {
				mainFrame.modifyImage(minimizeLabel,
						mainFrame.allImage.MINIMIZE_ICON);
			}
		});
		this.add(minimizeLabel);

		final JLabel closeLabel = new JLabel();
		closeLabel.setBounds(WIDTH * 20 / 21, 0, WIDTH / 22, WIDTH / 22);
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

			public void mouseEntered(MouseEvent e) {
				mainFrame.modifyImage(closeLabel,
						mainFrame.allImage.CLOSE_ICON2);
			}

			public void mouseExited(MouseEvent e) {
				mainFrame
						.modifyImage(closeLabel, mainFrame.allImage.CLOSE_ICON);
			}
		});
		this.add(minimizeLabel);
		this.add(closeLabel);

		JLabel gamingBackLabel = new JLabel();
		gamingBackLabel.setBounds(0, 0, HEIGHT * 8 / 99 * 9,
				HEIGHT * 8 / 99 * 9);
		// gamingBackLabel.setIcon(gamingBack);
		panel_1.add(gamingBackLabel);
		gamingBackLabel.setVisible(true);

		mainFrame.repaint();
		mainFrame.validate();

	}

	class StopListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			stopButton.setVisible(false);
			startButton.setVisible(true);
			isContinue = false;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					button[i][j].setEnabled(false);
					button[i][j].removeMouseListener(mouseListener);
				}
			}

			try {
				pcService.haltGame();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			startButton.setVisible(false);
			stopButton.setVisible(true);

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					button[i][j].setEnabled(true);
					button[i][j].addMouseListener(mouseListener);
				}
			}

			isContinue = true;
			try {
				pcService.restartGame();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			processThread pThread = new processThread();
			pThread.setTime(time);
			Thread t2 = new Thread(pThread);
			t2.start();
		}
	}

	class mouseListener implements MouseListener {
		Point beforeDrag;
		Point actualDrag;
		Point afterDrag;
		double x = 0.0;
		double y = 0.0;

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (action) {
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if (e.getSource() == button[i][j]) {
							// TestHelper.testView(e.getXOnScreen()+" "+e.getYOnScreen());
							TestHelper
									.testView("Mouse click at " + i + "," + j);
							try {
								MatricLocation clicked = new MatricLocation(i,
										j);
								clicked.setValue(animal[i][j]);
								pcService.mouseClick(clicked);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if (action) {
				int pic = -1;
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if (e.getSource() == button[i][j]) {
							pic = animal[i][j];
							button[i][j].setIcon(Icon2[pic]);
							mainFrame.repaint();
						}
					}
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if (action) {
				int pic = -1;
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if (e.getSource() == button[i][j]) {
							pic = animal[i][j];
							button[i][j].setIcon(Icon[pic]);
							mainFrame.repaint();
						}
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if (action) {
				beforeDrag = e.getPoint();
				actualDrag = e.getPoint();
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if (e.getSource() == button[i][j]) {
							x = HEIGHT * 8 / 99 * j + e.getPoint().getX();
							y = HEIGHT * 8 / 99 * i + e.getPoint().getY();
						}
					}
				}
				actualDrag.setLocation(x, y);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (action) {
				afterDrag = e.getPoint();
				int tmp = HEIGHT * 8 / 99;
				int dx = 0;
				int dy = 0;
				int I = 0;
				int J = 0;
				int I2 = 0;
				int J2 = 0;

				J2 = (int) ((afterDrag.getX() + actualDrag.getX() - beforeDrag
						.getX()) / tmp);
				I2 = (int) ((afterDrag.getY() + actualDrag.getY() - beforeDrag
						.getY()) / tmp);
				J = (int) ((actualDrag.getX() - beforeDrag.getX()) / tmp);
				I = (int) ((actualDrag.getY() - beforeDrag.getY()) / tmp);
				if (I != I2 || J != J2) {
					// TestHelper.testView("Mouse dragged from " + I + "," + J +
					// " to "
					// + I2 + "," + J2);
					// pcService.mouseDrag(new Point(I,J),new Point(I2,J2));
				}
				if (I == I2 || J == J2) {
					if ((I == I2) && (J2 > J)) {
						J2 = J + 1;
					} else if ((I == I2) && (J2 < J)) {
						J2 = J - 1;
					} else if ((J == J2) && (I < I2)) {
						I2 = I + 1;
					} else if ((J == J2) && (I > I2)) {
						I2 = I - 1;
					}

					if ((J2 >= 0 && J2 <= 8) && (I2 >= 0 && I2 <= 8)
							&& ((I != I2) || (J != J2))) {
						TestHelper.testView("Mouse dragged from " + I + "," + J
								+ "," + animal[I][J] + " to " + I2 + "," + J2
								+ "," + animal[I2][J2]);
						try {
							MatricLocation temp1 = new MatricLocation(I, J);
							temp1.setValue(animal[I][J]);
							MatricLocation temp2 = new MatricLocation(I2, J2);
							temp2.setValue(animal[I2][J2]);
							pcService.mouseDrag(temp1, temp2);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					dx = dy = I = J = 0;
				}
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = this.getParent().getSize();
		ImageIcon icon = new ImageIcon("graph/游戏背景.png");
		g.drawImage(icon.getImage(), 0, 0, size.width, size.height, null);

		// ImageIcon icon2 = new ImageIcon("graph/竖排文字.png");
		// g.drawImage(icon2.getImage(), 0, 0, size.width, size.height, null);
	}

	@Override
	public synchronized void update(boolean ifSuperMode, int sco, boolean flag,
			ArrayList<MatricLocation> tips, ArrayList<MatricLocation> bang,
			ArrayList<MatricLocation> clears,
			ArrayList<MatricLocation> beginList) {
		// TODO Auto-generated method stub

		int X, Y, Z;
		boolean a = (bang != null && bang.size() != 0);
		TestHelper.testView("-------------------bwubbi------------");
		TestHelper.testView("" + ifSuperMode);
		TestHelper.testView("-------------------end-------");

		if (clears != null && clears.size() > 2) {
			TestHelper.testView("beginList+++++++++++++++++++++++++++++++++++"
					+ beginList.size());
			TestHelper.testView("clears+++++++++++++++++++++++++++++++++++"
					+ clears.size());
			for (int k = 0; k < clears.size(); k++) {

				// TestHelper.testView("beforeAndAfter"+k+"after:"+clears.get(k).getX()+","+clears.get(k).getY()+","+clears.get(k).getValue());
				TestHelper.testView("beforeAndAfter" + k + "before:"
						+ beginList.get(k).getX() + ","
						+ beginList.get(k).getY() + ","
						+ beginList.get(k).getValue());
				if (beginList.get(k).getValue() != -1) {
					if (isDown == true) {
						beginList.get(k).setX(beginList.get(k).getValue());
					} else {
						beginList.get(k).setY(beginList.get(k).getValue());
					}
					beginList.get(k).setValue(clears.get(k).getValue());
				} else {
					beginList.get(k).setValue(clears.get(k).getValue());
				}
			}
		}
		// 当flag为true时，处理分数及超级模式
		if (flag) {

			if (sco > -1) {
				TestHelper.testprint("score:" + sco);
				scoresGet = sco - preciousScore;
				scores.setText("100");
				scores.setText(String.valueOf(scoresGet));

				// TestHelper.testView("-------------------bwubbi------------");
				// TestHelper.testView(String.valueOf(scoresGet));
				// TestHelper.testView("-------------------bwubbi------------");

				preciousScore = sco;
				scoreTotal.setText(String.valueOf(sco));
			}
			if ((isSuper == false) && ifSuperMode == true) {
				isSuper = ifSuperMode;
				Thread st = new Thread(new SuperModeThread());
				st.start();
			} else
				isSuper = ifSuperMode;

		}

		// 处理提示
		if (tips != null && tips.size() != 0) {
			isAdvised = true;
			ArrayList<MatricLocation> temp = new ArrayList<MatricLocation>();
			for (MatricLocation m : tips) {
				MatricLocation p = new MatricLocation(m.getX(), m.getY());
				temp.add(p);
			}
			MyThread myThread = new MyThread();
			myThread.setTip(temp);
			Thread t2 = new Thread(myThread);
			t2.start();
		} else {
			isAdvised = false;
		}

		// 处理消除部分
		if (bang != null && bang.size() != 0) {
			int bangX, bangY;
			bangX = bangY = -1;
			bangX = bang.get(0).getX();
			bangY = bang.get(0).getY();

			for (int i = 0; i < bang.size(); i++) {
				MatricLocation tmp2 = bang.get(i);
				X = tmp2.getX();
				Y = tmp2.getY();
				Z = tmp2.getValue();
				TestHelper.testprint("坐标：X：" + X + ",Y：" + Y + ",Z:" + Z);
			}
			mainFrame.repaint();

			if (clears != null && clears.size() != 0) {
				if (clears.size() > 2) {
					MatricLocation tmp;
					for (int i = 0; i < clears.size(); i++) {
						tmp = clears.get(i);
						X = tmp.getX();
						Y = tmp.getY();
						Z = tmp.getValue();
						System.out.println(X + "" + Y + "" + Z);
						animal[X][Y] = Z;
						// frame.repaint();
					}

					mainFrame.repaint();
					DownAndFillThread downT = new DownAndFillThread();

					ArrayList<MatricLocation> tempBang = new ArrayList<MatricLocation>();
					for (MatricLocation m : bang) {
						MatricLocation temp = (MatricLocation) m.clone();
						tempBang.add(temp);
					}
					ArrayList<MatricLocation> tempBegin = new ArrayList<MatricLocation>();
					for (MatricLocation m : beginList) {
						MatricLocation temp = (MatricLocation) m.clone();
						tempBegin.add(temp);
					}
					ArrayList<MatricLocation> tempClears = new ArrayList<MatricLocation>();
					for (MatricLocation m : clears) {
						MatricLocation temp = (MatricLocation) m.clone();
						tempClears.add(temp);
					}
					downT.setBeginAndAfter(tempBang, tempBegin, tempClears);
					Thread dt = new Thread(downT);
					if (threadNum < 2) {
						threadNum++;
						dt.start();
						// Thread.yield();
					} else {
						threadList.add(dt);
					}

				}

			}
			mainFrame.repaint();

			myThread.setScore(bangX, bangY);
			Thread t = new Thread(myThread);
			t.start();

		}

		if ((bang == null) && (clears != null && clears.size() == 2)) {
			MatricLocation tmp;
			for (int i = 0; i < clears.size(); i++) {
				tmp = clears.get(i);
				X = tmp.getX();
				Y = tmp.getY();
				Z = tmp.getValue();
				animal[X][Y] = Z;
			}

			DownAndFillThread downT = new DownAndFillThread();

			ArrayList<MatricLocation> tempClears = new ArrayList<MatricLocation>();
			for (MatricLocation m : clears) {
				MatricLocation temp = (MatricLocation) m.clone();
				tempClears.add(temp);
			}
			downT.setBeginAndAfter(null, null, tempClears);
			Thread dt = new Thread(downT);
			if (threadNum < 2) {
				threadNum++;
				dt.start();
				Thread.yield();
			} else {
				threadList.add(dt);
			}
			Thread.yield();
		}

	}

	class SuperModeThread implements Runnable {
		@Override
		public synchronized void run() {
			int i = 0;
			while (isSuper == true) {
				superLabel.setVisible(true);
				i++;
				superLabel.setIcon(super_Icon[i % 2]);
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mainFrame.repaint();

			}
			superLabel.setVisible(false);
			TestHelper.testprint("--------------------stop-----------");
		}
	}

	class DownAndFillThread implements Runnable {
		private ArrayList bangTmp = new ArrayList<MatricLocation>();
		private ArrayList<MatricLocation> before = new ArrayList<MatricLocation>();
		private ArrayList<MatricLocation> after = new ArrayList<MatricLocation>();

		//
		public synchronized void setBeginAndAfter(
				ArrayList<MatricLocation> bang,
				ArrayList<MatricLocation> before,
				ArrayList<MatricLocation> after) {
			this.bangTmp = bang;
			this.before = before;
			this.after = after;
		}

		@Override
		public void run() {
			synchronized (DownAndFillThread.class) {
				TestHelper
						.testprint("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				// TestHelper.testView("123@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				// 交换位置
				if ((bangTmp == null) && (after != null && after.size() == 2)) {

					if (mainFrame.isVoice()) {
						new AudioPlayer("sound/move.wav");
					}

					int x1, y1, z1, x2, y2, z2;
					x1 = x2 = y1 = y2 = z1 = z2 = -1;
					MatricLocation a = after.get(0);
					MatricLocation b = after.get(1);
					x1 = a.getX();
					y1 = a.getY();
					z1 = a.getValue();
					x2 = b.getX();
					y2 = b.getY();
					z2 = b.getValue();
					// TestHelper.testView("clears1:"+x1+","+y1+","+z1);
					// TestHelper.testView("clears2:"+x2+","+y2+","+z2);
					button[x1][y1].setIcon(Icon[z1]);
					button[x2][y2].setIcon(Icon[z2]);
					int same = 0;

					// 对于四种可能位置的情况进行讨论
					if ((x1 == x2) && (y1 - y2 == 1)) {
						same = button[x1][y1].getLocation().y;

						button[x1][y1].setLocation(
								button[x1][y1].getLocation().x - HEIGHT * 8
										/ 99, same);
						button[x2][y2].setLocation(
								button[x2][y2].getLocation().x + HEIGHT * 8
										/ 99, same);
						for (int i = 0; i < 5; i++) {
							button[x1][y1].setLocation(
									button[x1][y1].getLocation().x + HEIGHT * 8
											/ (99 * 5), same);
							button[x2][y2].setLocation(
									button[x2][y2].getLocation().x - HEIGHT * 8
											/ (99 * 5), same);
							try {
								Thread.sleep(20);
							} catch (Exception e) {
								e.printStackTrace();
							}
							mainFrame.repaint();
						}

					} else if ((x1 == x2) && (y1 - y2 == -1)) {
						same = button[x1][y1].getLocation().y;

						button[x1][y1].setLocation(
								button[x1][y1].getLocation().x + HEIGHT * 8
										/ 99, same);
						button[x2][y2].setLocation(
								button[x2][y2].getLocation().x - HEIGHT * 8
										/ 99, same);

						for (int i = 0; i < 5; i++) {
							button[x1][y1].setLocation(
									button[x1][y1].getLocation().x - HEIGHT * 8
											/ (99 * 5), same);
							button[x2][y2].setLocation(
									button[x2][y2].getLocation().x + HEIGHT * 8
											/ (99 * 5), same);

							try {
								Thread.sleep(20);
							} catch (Exception e) {
								e.printStackTrace();
							}

							mainFrame.repaint();
						}

					} else if ((y1 == y2) && (x1 - x2 == 1)) {
						same = button[x1][y1].getLocation().x;

						button[x1][y1].setLocation(same,
								button[x1][y1].getLocation().y - HEIGHT * 8
										/ 99);
						button[x2][y2].setLocation(same,
								button[x2][y2].getLocation().y + HEIGHT * 8
										/ 99);
						for (int i = 0; i < 5; i++) {
							button[x1][y1].setLocation(same,
									button[x1][y1].getLocation().y + HEIGHT * 8
											/ (99 * 5));
							button[x2][y2].setLocation(same,
									button[x2][y2].getLocation().y - HEIGHT * 8
											/ (99 * 5));
							try {
								Thread.sleep(20);
							} catch (Exception e) {
								e.printStackTrace();
							}
							mainFrame.repaint();
						}

					} else if ((y1 == y2) && (x1 - x2 == -1)) {
						same = button[x1][y1].getLocation().x;

						button[x1][y1].setLocation(same,
								button[x1][y1].getLocation().y + HEIGHT * 8
										/ 99);
						button[x2][y2].setLocation(same,
								button[x2][y2].getLocation().y - HEIGHT * 8
										/ 99);

						for (int i = 0; i < 5; i++) {
							button[x1][y1].setLocation(same,
									button[x1][y1].getLocation().y - HEIGHT * 8
											/ (99 * 5));
							button[x2][y2].setLocation(same,
									button[x2][y2].getLocation().y + HEIGHT * 8
											/ (99 * 5));
							try {
								Thread.sleep(20);
							} catch (Exception e) {
								e.printStackTrace();
							}
							mainFrame.repaint();
						}

					} else {
						// TestHelper.testprint("坐标错误！");
					}

				}
				// 消除成功，进行处理，先展示爆炸效果后掉落及填充
				else if ((bangTmp != null && bangTmp.size() != 0)
						&& (after != null && after.size() > 2)) {
					// 开始处理爆炸效果
					int num = bangTmp.size();
					MatricLocation tmp;
					if (mainFrame.isVoice()) {
						new AudioPlayer("sound/xiaochu.wav");
					}
					int X = -1, Y = -1, Z = -1;
					int k = -1;
					// Icon[7] = new ImageIcon(temp);
					Image temp;
					for (int j = 0; j < num; j++) {
						tmp = (MatricLocation) bangTmp.get(j);
						X = tmp.getX();
						Y = tmp.getY();
						Z = tmp.getValue();
						bangButton[j].setIcon(Icon[8]);
						bangButton[j].setBounds(button[X][Y].getLocation().x,
								button[X][Y].getLocation().y,
								button[2][3].getSize().width,
								button[2][3].getSize().height);
					}
					mainFrame.repaint();
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < num; j++) {
							tmp = (MatricLocation) bangTmp.get(j);
							X = tmp.getX();
							Y = tmp.getY();
							Z = tmp.getValue();
							// bangButton[j].setVisible(true);
							bangButton[j].setBounds(
									bangButton[j].getLocation().x - 1,
									bangButton[j].getLocation().y - 1,
									button[2][3].getSize().width + 2 * i,
									button[2][3].getSize().height + 2 * i);
							bangButton[j].setVisible(true);
							try {
								Thread.sleep(10);
							} catch (Exception e) {
								e.printStackTrace();
							}
							mainFrame.repaint();
						}
					}
					for (int j = 0; j < num; j++) {
						bangButton[j].setVisible(false);
					}
					mainFrame.repaint();
					// 爆炸效果处理结束，开始进行掉落及填充的处理
					TestHelper
							.testView("downAndFill  begin%%%%%%%%%%%%%%%%%%%&&&&&&&&&&&&&&&");
					X = Y = Z = 0;
					ArrayList downLoc = new ArrayList();
					ArrayList fill = new ArrayList();
					ArrayList endX = new ArrayList();
					ArrayList endY = new ArrayList();
					ArrayList beginX = new ArrayList();
					ArrayList beginY = new ArrayList();

					int longestX = -1;
					int longestY = -1;
					int tmp2 = -1;

					for (int k2 = 0; k2 < before.size(); k2++) {
						if ((before.get(k2).getX() == after.get(k2).getX())
								&& (before.get(k2).getY() == (after.get(k2)
										.getY()))) {
							fill.add(k2);
							button[after.get(k2).getX()][after.get(k2).getY()]
									.setVisible(false);
						} else {
							downLoc.add(k2);
							tmp2 = HEIGHT * 8 / 99 * after.get(k2).getY()
									- HEIGHT * 8 / 99 * before.get(k2).getY();
							if (tmp2 > longestX)
								longestX = tmp2;
							tmp2 = HEIGHT * 8 / 99 * after.get(k2).getX()
									- HEIGHT * 8 / 99 * before.get(k2).getX();
							if (tmp2 > longestY)
								longestY = tmp2;

							beginX.add(HEIGHT * 8 / 99 * before.get(k2).getY());
							beginY.add(HEIGHT * 8 / 99 * before.get(k2).getX());
							endX.add(HEIGHT * 8 / 99 * after.get(k2).getY());
							endY.add(HEIGHT * 8 / 99 * after.get(k2).getX());
						}

					}

					if (downLoc != null && downLoc.size() != 0) {
						for (int i = 0; i < downLoc.size(); i++) {
							X = after.get((Integer) downLoc.get(i)).getX();
							Y = after.get((Integer) downLoc.get(i)).getY();
							Z = after.get((Integer) downLoc.get(i)).getValue();
							button[X][Y].setIcon(Icon[Z]);
							button[X][Y].setLocation((Integer) beginX.get(i),
									(Integer) beginY.get(i));
							mainFrame.repaint();
						}
					}

					for (int i = 0; i < downLoc.size(); i++) {
						X = after.get((Integer) downLoc.get(i)).getX();
						Y = after.get((Integer) downLoc.get(i)).getY();
						TestHelper.testView("-------------------location:" + X
								+ "," + Y + ":" + button[X][Y].getLocation().x
								+ "," + button[X][Y].getLocation().y);
					}

					if (downLoc != null && downLoc.size() != 0) {

						boolean isOver = true;
						int i = 0;
						TestHelper
								.testView("move begin%%%%%%%%%%%%%%%%%%%&&&&&&&&&&&&&&&&%%%%%%%%%");

						// boolean值isDown控制下落方向

						if (isDown) {// 竖直下落
							for (int n = 0; n < longestY; n = n + 11) {
								for (i = 0; i < downLoc.size(); i++) {
									X = after.get((Integer) downLoc.get(i))
											.getX();
									Y = after.get((Integer) downLoc.get(i))
											.getY();
									if ((button[X][Y].getLocation().y < (HEIGHT
											* 8 / 99 * X - 1))) {
										button[X][Y]
												.setLocation(
														button[X][Y]
																.getLocation().x,
														button[X][Y]
																.getLocation().y
																+ HEIGHT
																* 8
																/ (99 * 5));
									}

								}

								try {
									Thread.sleep(25);
								} catch (Exception e) {
									e.printStackTrace();
								}
								mainFrame.repaint();

							}

						} else {// 从左向右下落
							for (int n = 0; n < longestX; n = n + 11) {
								for (i = 0; i < downLoc.size(); i++) {
									X = after.get((Integer) downLoc.get(i))
											.getX();
									Y = after.get((Integer) downLoc.get(i))
											.getY();
									if ((button[X][Y].getLocation().x < (HEIGHT
											* 8 / 99 * Y - 1))) {
										button[X][Y]
												.setLocation(
														button[X][Y]
																.getLocation().x
																+ HEIGHT
																* 8
																/ (99 * 5),
														button[X][Y]
																.getLocation().y);
									}
								}
								try {
									Thread.sleep(25);
								} catch (Exception e) {
									e.printStackTrace();
								}
								mainFrame.repaint();
							}

						}

						TestHelper
								.testView("move stop&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
					}

					if (after != null && after.size() != 2) {
						MatricLocation tmp3;
						for (int m = 0; m < after.size(); m++) {
							tmp3 = after.get(m);
							X = tmp3.getX();
							Y = tmp3.getY();
							Z = tmp3.getValue();
							button[X][Y].setIcon(Icon[Z]);
							button[X][Y].setVisible(true);
							button[X][Y].setLocation(HEIGHT * 8 / 99 * Y,
									HEIGHT * 8 / 99 * X);
							// animal[X][Y] = Z;
							mainFrame.repaint();
						}
					}
					TestHelper
							.testView("downAndFill  end%%%%%%%%%%%%%%%%%%%&&&&&&&&&&&&&&&");
				}

			}
			notifyThread();
		}

		private void notifyThread() {
			threadNum--;
			if (threadList.size() != 0) {
				Thread t = threadList.get(0);
				threadList.remove(t);
				t.start();
				threadNum++;
				Thread.yield();
			} else {

			}
		}
	}

	class MyThread implements Runnable {

		private ArrayList<MatricLocation> tip = new ArrayList<MatricLocation>();
		// private boolean isAdvised;

		private ArrayList<MatricLocation> bang = new ArrayList<MatricLocation>();
		private ArrayList<MatricLocation> clears = new ArrayList<MatricLocation>();

		private int kindOfEffect;// 控制是提示还是得分
		private int bangX;
		private int bangY;

		public void setTip(ArrayList<MatricLocation> tips) {
			this.tip.addAll(tips);
			this.kindOfEffect = 1;
		}

		public void setScore(int x, int y) {
			this.bangX = x;
			this.bangY = y;
			this.kindOfEffect = 2;
		}

		@Override
		public void run() {

			if (kindOfEffect == 1) {
				// 提示效果的展示
				int i = 0;
				int x1 = -1, y1 = -1, x2 = -1, y2 = -1;
				MatricLocation tmp = (MatricLocation) tip.get(0);
				x1 = (Integer) tmp.getX();
				y1 = (Integer) tmp.getY();
				tmp = (MatricLocation) tip.get(1);
				x2 = (Integer) tmp.getX();
				y2 = (Integer) tmp.getY();
				int count = 0;
				while (isAdvised) {
					count++;
					if (count % 2 == 1) {
						button[x1][y1].setVisible(false);
						button[x2][y2].setVisible(true);
					} else {
						button[x1][y1].setVisible(true);
						button[x2][y2].setVisible(false);
					}
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
					mainFrame.repaint();
					if (count == 4) {
						break;
					}
				}
				button[x1][y1].setVisible(true);
				button[x2][y2].setVisible(true);
				// System.out
				// .println("isAdvised+++++++++++++++++++++" + isAdvised);
			} else if (kindOfEffect == 2) {
				// 得分效果的展示
				kindOfEffect = -1;
				scores.setVisible(true);
				scores.setLocation(button[bangX][bangY].getLocation().x,
						button[bangX][bangY].getLocation().y);

				for (int i = 0; i < 10; i++) {
					scores.setLocation(scores.getLocation().x,
							scores.getLocation().y - 15);
					mainFrame.repaint();
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				scores.setVisible(false);
				scores.setText(" ");
				mainFrame.repaint();
			}

		}
	}

	class processThread implements Runnable {

		int seconds;

		void setTime(int i) {
			seconds = i;
		}

		@Override
		public void run() {
			while (percent < 60) {
				if (isContinue) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					process.setIcon(process_Icon[percent]);
					percent++;
					time_Field.setText(String.valueOf(percent) + "s");
					mainFrame.repaint();
				} else {
					break;
				}
			}
			if (percent < 60) {
				time = percent;
			} else {
				process.setIcon(process_Icon[60]);
				mainFrame.repaint();
				action = false;
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					TestHelper
							.testControl("GamingPanel process called saveGameRecord");
					pcService.saveGameRecored(MainFrame.getUserID());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}

		}
	}

	class ReadyThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Animation3 a3 = new Animation3(me);
			mainFrame.setContentPane(me);
		}
	}

	@Override
	public void gameReady() {
		// TODO Auto-generated method stub
		// 播放开始动画
		// ReadyThread t=new ReadyThread();
		// Thread ready=new Thread(t);
		// ready.start();
		Animation3 a3 = new Animation3(me);
		mainFrame.setContentPane(me);
		TestHelper.testControl("GamingPanel setContentPane");
		this.setVisible(true);
		mainFrame.validate();
		if (mainFrame.isMusic()) {
			mainFrame.playMusic("sound/gamebackground.wav");
		}
		TestHelper.testControl("Game Start " + new Date().getTime());

		processThread pThread = new processThread();
		pThread.setTime(0);
		Thread t2 = new Thread(pThread);
		t2.start();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				button[i][j].setEnabled(true);
				button[i][j].addMouseListener(mouseListener);
			}
		}
	}

	@Override
	public void showGameResult(Date d, String s, int combo, int money)
			throws RemoteException {
		// TODO Auto-generated method stub
		// MessageFrame.show(s + "分 " + combo + "连击  ￥" + money);
		GameOver gOver = new GameOver();
		if (vsPanel != null) {
			gOver.showGameOver(mainFrame, panel2, s, combo, money,
					vsPanel.getRivalScore());
		} else {
			gOver.showGameOver(mainFrame, panel2, s, combo, money);
		}
	}

	private void setOpponenet(VSPanel panel, int k) {
		vsPanel = panel;
		// 得到界面,游戏区域的按钮,按钮图案，对手头像，对手分数,道具C减5s的提示
		vsPanel.setFrame(mainFrame, panel_1, button, animal, opponent,
				scoreOpponent, note, k, mouseListener);
	}

	@Override
	public void setRivaltoFriend(int rivalImg) {
		// TODO Auto-generated method stub
		VSPanel vspanel = new VSPanel();
		this.setOpponenet(vspanel, rivalImg);
		String ip = IPHelper.getSelfIP();
		try {
			RivalConditionVSService rivalContiditionVS = new RivalConditionVS(
					vspanel);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getImage() {
		mainFrame.allImage.initialGamingImage();
		start = mainFrame.allImage.START;
		stop = mainFrame.allImage.STOP;
		process_Icon = mainFrame.allImage.process_Icon;
		Icon = mainFrame.allImage.Icon;
		Icon2 = mainFrame.allImage.Icon2;
		super_Icon = mainFrame.allImage.super_Icon;
	}

	public void getImage(Boolean a) {
		mainFrame.allImage.initialGamingImage();
		start = mainFrame.allImage.START;
		stop = mainFrame.allImage.STOP;
		process_Icon = mainFrame.allImage.process_Icon;
		Icon = mainFrame.allImage.Icon3;
		Icon2 = mainFrame.allImage.Icon4;
		super_Icon = mainFrame.allImage.super_Icon;
	}

	public void setIcon() {
		JButton iconButton = new JButton("开始");
		iconButton.setSize(HEIGHT * 8 / 99, HEIGHT * 8 / 99);

		Image temp;
		temp = super_Icon[0].getImage().getScaledInstance(
				HEIGHT * 8 / 99 * 9 + HEIGHT * 6 / 99,
				HEIGHT * 8 / 99 * 9 + HEIGHT * 6 / 99,
				super_Icon[0].getImage().SCALE_DEFAULT);
		super_Icon[0] = new ImageIcon(temp);

		temp = super_Icon[1].getImage().getScaledInstance(
				HEIGHT * 8 / 99 * 9 + HEIGHT * 6 / 99,
				HEIGHT * 8 / 99 * 9 + HEIGHT * 6 / 99,
				super_Icon[1].getImage().SCALE_DEFAULT);
		super_Icon[1] = new ImageIcon(temp);

		// Icon[1-5]是消除的五种图案，6是风车，7是爆炸

		for (int i = 1; i < 6; i++) {
			temp = Icon[i].getImage().getScaledInstance(iconButton.getWidth(),
					iconButton.getHeight(), Icon[i].getImage().SCALE_DEFAULT);
			Icon[i] = new ImageIcon(temp);
		}
		for (int i = 11; i < 16; i++) {
			temp = Icon[i].getImage().getScaledInstance(iconButton.getWidth(),
					iconButton.getHeight(), Icon[i].getImage().SCALE_DEFAULT);
			Icon[i] = new ImageIcon(temp);
		}

		for (int i = 7; i < 9; i++) {
			temp = Icon[i].getImage().getScaledInstance(iconButton.getWidth(),
					iconButton.getHeight(), Icon[i].getImage().SCALE_DEFAULT);
			Icon[i] = new ImageIcon(temp);
		}

		// 鼠标在按钮上时应变化的图案

		for (int i = 1; i < 6; i++) {
			temp = Icon2[i].getImage().getScaledInstance(iconButton.getWidth(),
					iconButton.getHeight(), Icon2[i].getImage().SCALE_DEFAULT);
			Icon2[i] = new ImageIcon(temp);
		}
		for (int i = 11; i < 16; i++) {
			temp = Icon2[i].getImage().getScaledInstance(iconButton.getWidth(),
					iconButton.getHeight(), Icon2[i].getImage().SCALE_DEFAULT);
			Icon2[i] = new ImageIcon(temp);
		}

		temp = Icon2[7].getImage().getScaledInstance(iconButton.getWidth(),
				iconButton.getHeight(), Icon2[7].getImage().SCALE_DEFAULT);
		Icon2[7] = new ImageIcon(temp);

		for (int i = 0; i < 61; i++) {
			temp = process_Icon[i].getImage().getScaledInstance(
					WIDTH * 6/ 12, HEIGHT * 3 / 99,
					process_Icon[i].getImage().SCALE_DEFAULT);
			process_Icon[i] = new ImageIcon(temp);
		}

		temp = start.getImage().getScaledInstance(HEIGHT * 5 / 99,
				HEIGHT * 5 / 99, start.getImage().SCALE_DEFAULT);
		start = new ImageIcon(temp);

		temp = stop.getImage().getScaledInstance(HEIGHT * 5 / 99,
				HEIGHT * 5 / 99, stop.getImage().SCALE_DEFAULT);
		stop = new ImageIcon(temp);

	}

	public void showStopButton() {
		stopButton.setVisible(true);
		mainFrame.repaint();
	}
}
