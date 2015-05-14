package View.MainUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import vo.FriendVO;
import Model.patternModel.TestHelper;
import View.MainFrame;
import View.MessageFrame;
import View.RButton;

public class SelectGameModePanel extends JPanel {

	private static final long serialVersionUID = -4692828240904727744L;
	private int money;
	private MainFrame mainFrame;
	private int width;
	private int height;
	private JLabel xiezuoLabel;
	private JLabel vsLabel;
	private JLabel singleLabel;
	private JTextField money2;
	boolean item0;
	boolean item1;
	boolean item2;
	private JRadioButton[] item = new JRadioButton[3];

	private JLabel[] itemLabel = new JLabel[3];

	private JPanel[] p = new JPanel[3];
	@SuppressWarnings("rawtypes")
	private JComboBox[] friend = new JComboBox[6];
	private ArrayList<String> sendDuiyou = new ArrayList<String>();
	private ArrayList<String> sendDuishou = new ArrayList<String>();
	public ArrayList<FriendVO> friendList;

	private String[] friendName;

	@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
	public SelectGameModePanel(MainFrame mFrame) {
		this.mainFrame = mFrame;
		this.width = mainFrame.getWidth() * 3 / 5;
		this.height = mainFrame.getHeight() * 3 / 5;

		setLayout(null);
		setOpaque(false);
		setSize(width, height);

		JLabel wordLabel = new JLabel();
		wordLabel.setBounds(width / 6, 3, width * 3 / 5, height / 7);
		mainFrame.modifyImage(wordLabel, mainFrame.allImage.SELECTWORD);

		JSeparator sep1 = new JSeparator(JSeparator.HORIZONTAL);
		sep1.setForeground(Color.CYAN);
		sep1.setSize(width * 15 / 16, 4);
		sep1.setLocation(width / 32, height / 6);
		JSeparator sep2 = new JSeparator(JSeparator.VERTICAL);
		sep2.setForeground(Color.CYAN);
		sep2.setSize(4, height * 3 / 4);
		sep2.setLocation(width / 5, height / 6);

		xiezuoLabel = new JLabel();
		vsLabel = new JLabel();
		singleLabel = new JLabel();
		xiezuoLabel.setBounds(width / 32, height * 7 / 18, width / 6,
				height * 2 / 9);
		vsLabel.setBounds(width / 32, height * 11 / 18, width / 6,
				height * 2 / 9);
		singleLabel
				.setBounds(width / 32, height / 6, width / 6, height * 2 / 9);

		mainFrame.modifyImage(xiezuoLabel, mainFrame.allImage.XIEZUO);
		mainFrame.modifyImage(vsLabel, mainFrame.allImage.DUIZHAN);
		mainFrame.modifyImage(singleLabel, mainFrame.allImage.SINGLE);

		Point[] itemPoint = { new Point(width / 4 - 3, height / 4 + 8),
				new Point(width / 4 + height / 4 - 3, height / 4 + 8),
				new Point(width / 4 - 3 + height / 2, height / 4 + 8) };
		Point[] itemPoint2 = { new Point(width / 4, height / 6 + 3),
				new Point(width / 4 + height / 4, height / 6 + 3),
				new Point(width / 4 + height / 2, height / 6 + 3) };
		for (int i = 0; i < 3; i++) {
			Image resulttemp = mainFrame.allImage.ITEM[i]
					.getImage()
					.getScaledInstance(height / 6, height / 6,
							mainFrame.allImage.ITEM[i].getImage().SCALE_DEFAULT);
			ImageIcon temp = new ImageIcon(resulttemp);
			itemLabel[i] = new JLabel(temp);
			itemLabel[i].setLocation(itemPoint2[i]);
			itemLabel[i].setSize(height / 6, height / 6);
			switch (i) {
			case 0:
				item[i] = new JRadioButton("快速提示");
				break;
			case 1:
				item[i] = new JRadioButton("得分加成");

				break;
			case 2:
				item[i] = new JRadioButton("加强连击");
				break;
			default:
				break;
			}
			item[i].setForeground(Color.CYAN);
			item[i].setLocation(itemPoint[i]);
			item[i].setSize(height / 4, height / 6);
			item[i].setOpaque(false);
			item[i].setSelected(false);
			add(item[i]);
			add(itemLabel[i]);
	
		}

		JLabel money1 = new JLabel();
		money1.setBounds(width * 4 / 5, height / 4, height / 16, height / 16);
		mainFrame.modifyImage(money1, mainFrame.allImage.MONEY);
		add(money1);
		money2 = new JTextField("000");
		money2.setEditable(false);
		money2.setBorder(null);
		money2.setOpaque(false);
		money2.setFont(new Font("微软雅黑",Font.BOLD,height/20));
		money2.setBounds(width * 44 / 50, height / 4, height / 8, height / 18);
		add(money2);
		
		// 三个panel
		final JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new CardLayout());
		contentPanel.setBounds(width * 22 / 100, height * 7 / 16,
				width * 3 / 4, height / 2);
		contentPanel.setOpaque(false);
		this.add(contentPanel);

		;
		RButton[] start = new RButton[3];
		for (int i = 0; i < 3; i++) {
			p[i] = new JPanel();
			p[i].setSize(width * 3 / 4, height / 2);
			p[i].setLayout(null);
			p[i].setOpaque(false);
			contentPanel.add(p[i], String.valueOf(i));

			start[i] = new RButton("开始游戏");
			start[i].setFont(new Font("微软雅黑", Font.PLAIN, height / 18));
			start[i].setBounds(p[i].getWidth() / 3, p[i].getHeight() * 4 / 5,
					height / 3, height / 10);
			p[i].add(start[i]);
			buy();
		}

		// 金钱

		// 获取好友数据
		if (mainFrame.isConnected) {
			friendList = mainFrame.fcs.getList(mainFrame.user.getId())
					.getFriendList();
			ArrayList<String> friendNameList = new ArrayList<String>();
			friendNameList.add("尚未选择好友");
			for (int i = 0; i < friendList.size(); i++) {
				friendNameList.add(friendList.get(i).getSecondName());
			}
			friendName = new String[friendNameList.size()];
			for (int i = 0; i < friendName.length; i++) {
				friendName[i] = friendNameList.get(i);
			}
		} else {
			friendName = new String[3];
			friendName[0] = "asd";
			friendName[1] = "a";
			friendName[2] = "fds";
		}

		for (int i = 0; i < 6; i++) {
			friend[i] = new JComboBox(friendName);
			friend[i].setBackground(Color.CYAN);
			friend[i].setSize(width / 5, height / 11);
			friend[i].setForeground(Color.DARK_GRAY);
		}

		// 协作游戏组件
		JLabel mateLabel1 = new JLabel("请选择您的队友：");
		mateLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mateLabel1.setBounds(p[1].getWidth() / 20, p[1].getHeight() / 10,
				width / 5, height / 18);
		p[1].add(mateLabel1);
		friend[0].setLocation(p[1].getWidth() / 20, p[1].getHeight() / 3);
		friend[1].setLocation(p[1].getWidth() * 15 / 40, p[1].getHeight() / 3);
		friend[2].setLocation(p[1].getWidth() * 14 / 20, p[1].getHeight() / 3);
		p[1].add(friend[0]);
		p[1].add(friend[1]);
		p[1].add(friend[2]);
		// 对战游戏组件
		JLabel mateLabel2 = new JLabel("请选择您的队友：");
		mateLabel2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mateLabel2.setBounds(p[1].getWidth() / 20, p[1].getHeight() / 10,
				width / 5, height / 18);
		JLabel vsLabel2 = new JLabel("请选择您的对手：");
		vsLabel2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		vsLabel2.setBounds(p[1].getWidth() * 2 / 5, p[1].getHeight() / 10,
				width / 5, height / 18);
		friend[3].setLocation(p[1].getWidth() / 20, p[1].getHeight() / 3);
		friend[4].setLocation(p[1].getWidth() * 9 / 20, p[1].getHeight() / 3);
		friend[5].setLocation(p[1].getWidth() * 29 / 40, p[1].getHeight() / 3);
		p[2].add(friend[3]);
		p[2].add(friend[4]);
		p[2].add(friend[5]);
		p[2].add(mateLabel2);
		p[2].add(vsLabel2);

		final CardLayout card = (CardLayout) contentPanel.getLayout();
		card.show(contentPanel, "0");
		singleLabel.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				card.show(contentPanel, "0");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mainFrame.modifyImage(singleLabel, mainFrame.allImage.SINGLE2);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mainFrame.modifyImage(singleLabel, mainFrame.allImage.SINGLE);
			}
		});
		xiezuoLabel.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				updateInfo();
				card.show(contentPanel, "1");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mainFrame.modifyImage(xiezuoLabel, mainFrame.allImage.XIEZUO2);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mainFrame.modifyImage(xiezuoLabel, mainFrame.allImage.XIEZUO);
			}
		});
		vsLabel.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				updateInfo();
				card.show(contentPanel, "2");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mainFrame.modifyImage(vsLabel, mainFrame.allImage.DUIZHAN2);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mainFrame.modifyImage(vsLabel, mainFrame.allImage.DUIZHAN);
			}
		});

		start[0].addActionListener(new SingleListener());
		start[1].addActionListener(new XiezuoListener());
		start[2].addActionListener(new VSListener());

		add(wordLabel);
		add(sep2);
		add(sep1);
		add(xiezuoLabel);
		add(vsLabel);
		add(singleLabel);

	}

	@SuppressWarnings("unchecked")
	public void updateInfo() {
		if (mainFrame.isConnected) {
			item[0].setSelected(false);
			item[1].setSelected(false);
			item[2].setSelected(false);
            money2.setText(mainFrame.uc.updateUser(mainFrame.user.getId()).getMoney()+"");
			friendList = mainFrame.fcs.getList(mainFrame.user.getId())
					.getFriendList();
			System.out.println("在线好友size是" + friendList.size());
			for (int i = 0; i < 6; i++) {
				friend[i].removeAllItems();
				friend[i].addItem("尚未选择好友");
				for (int j = 0; j < friendList.size(); j++) {
					friend[i].addItem(friendList.get(j).getSecondName());
				}
			}
			sendDuiyou.clear();
			sendDuishou.clear();

		} else {
			MessageFrame.show("没有联网，不能更新数据");
		}

	}

	class SingleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {		
			mainFrame.gs.initialSoloGame(MainFrame.getUserID(),item[2].isSelected(),
					item[1].isSelected(), item[0].isSelected());
			updateInfo();
		}

		
	}

	class XiezuoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println(friend[0].getSelectedIndex());
			for (int i = 0; i < 3; i++) {
				if (friend[i].getSelectedIndex() != 0) {
					String ID = friendList
							.get(friend[i].getSelectedIndex() - 1)
							.getFriendId();
					System.out.println("YAOQING : "+ID);
					if (sendDuiyou.contains(ID)) {
						System.out.println("重复好友呢！！ ");
						MessageFrame.show("您不能选择重复好友!");
						return;
					} else {
						sendDuiyou.add(ID);
						TestHelper.testControl(ID);						
					}
				}
			}
			mainFrame.gs.initialFriendsNet(mainFrame.user.getId(),
					sendDuiyou, item[2].isSelected(),
					item[1].isSelected(), item[0].isSelected());
			sendDuiyou.clear();
			sendDuishou.clear();
		}
	}

	class VSListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int f1 = friend[3].getSelectedIndex();
			int o1 = friend[4].getSelectedIndex();
			int o2 = friend[5].getSelectedIndex();
			if (o1 == 0 && o2 == 0) {
				MessageFrame.show("您必须选择至少一个对手！");
				return;
			} else {
				if (f1 != 0) {
					sendDuiyou.add(friendList.get(f1 - 1).getFriendId());
				}
				if (o1 != 0) {
					String q = friendList.get(o1 - 1).getFriendId();
					if (sendDuiyou.contains(q)) {
						MessageFrame.show("您不能选择重复好友！");
						return;
					} else {
						sendDuishou.add(q);
					}
				}
				if (o2 != 0) {
					String w = friendList.get(o2 - 1).getFriendId();
					if (sendDuiyou.contains(w) || sendDuishou.contains(w)) {
						MessageFrame.show("您不能选择重复好友！");
						return;
					} else {
						sendDuishou.add(friendList.get(o2 - 1).getFriendId());
					}
				}
				// 队友有可能为空
				mainFrame.gs.broadcastVSGame(mainFrame.user.getId(), sendDuiyou,
						sendDuishou, item[2].isSelected(), item[1].isSelected(),
						item[0].isSelected());
			}
			
			
			sendDuiyou.clear();
			sendDuishou.clear();
		}

	}
	public void buy(){
		item0 = false;
		item1 = false;
		item2 = false;
		if(mainFrame.isConnected){
			money = mainFrame.user.getMoney();
			money2.setText(money + "");
		}
			judge(money);
			item[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("IN the item0");
					item0 = !item0;
					if (item0) {
						money = money - 100;
						money2.setText((money) + "");
					} else {
						money = money + 100;
						money2.setText((money) + "");
					}
					judge(money);
				}
				
			});
			item[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					item1 = !item1;
					if (item1) {
						money = money - 200;
						money2.setText((money) + "");
					} else {
						money = money + 200;
						money2.setText((money) + "");
					}
					judge(money);
				}
			});
			item[2].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					item2 = !item2;
					if (item2) {
						money = money - 300;
						money2.setText((money) + "");
					} else {
						money = money + 300;
						money2.setText((money) + "");
					}
					judge(money);
				}
			});
		}
	public void judge(int money) {
		if ((money < 100)&&!item[0].isSelected()) {
			item[0].setEnabled(false);
		}
		if (money >= 100) {
			item[0].setEnabled(true);
		}
		if (money < 200&&!item[1].isSelected()) {
			item[1].setEnabled(false);
		}
		if (money >= 200) {
			item[1].setEnabled(true);
		}
		if (money < 300&&!item[2].isSelected()) {
			item[2].setEnabled(false);
		}
		if (money >= 300) {
			item[2].setEnabled(true);
		}
	}

	
}
