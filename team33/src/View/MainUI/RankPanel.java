package View.MainUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import vo.RankVO;
import View.AudioPlayer;
import View.MainFrame;
import View.MessageFrame;

public class RankPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8347147179183662983L;

	public MainFrame mainFrame;
	private int width;
	private int height;
	private ArrayList<RankVO> rank;
	RankLabel[] rankContent = new RankLabel[6];
	private JPanel RCPanel;

	public RankPanel(MainFrame mFrame) {
		this.mainFrame = mFrame;
		this.width = mainFrame.getWidth() * 3 / 5;
		this.height = mainFrame.getHeight() * 3 / 5;
		setLayout(null);
		setOpaque(false);
		setSize(width, height);

		JLabel wordLabel = new JLabel();
		wordLabel.setBounds(width / 5, 3, width * 3 / 5, height / 7);
		mainFrame.modifyImage(wordLabel, mainFrame.allImage.RANKWORD);
		add(wordLabel);

		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		sep.setForeground(Color.CYAN);
		sep.setSize(width * 15 / 16, 4);
		sep.setLocation(width / 32, height / 6);
		add(sep);

		final JLabel foreLabel = new JLabel();
		final JLabel nextLabel = new JLabel();
		foreLabel.setBounds(width / 32, height * 7 / 40, width / 16,
				height * 4 / 5);
		nextLabel.setBounds(width * 29 / 32, height * 7 / 40, width / 16,
				height * 4 / 5);
		mainFrame.modifyImage(foreLabel, mainFrame.allImage.FORE1);
		mainFrame.modifyImage(nextLabel, mainFrame.allImage.NEXT1);
		foreLabel.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (rank.size() == 0) {
					MessageFrame.show("快去添加玩伴吧~~");
					return;
				}
				try {
					if (rankContent[0].getIndex() == 0) {
						MessageFrame.show("已经是第一页了");
					} else {
						loadInfo(rank, rankContent[0].getIndex() - 6,
								rankContent[0].getIndex() - 1);
					}
				} catch (NullPointerException e2) {
					MessageFrame.show("What's the hell?!");
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mainFrame.modifyImage(foreLabel, mainFrame.allImage.FORE2);
				if (mainFrame.isVoice()) {
					new AudioPlayer("sound/anjian.wav");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mainFrame.modifyImage(foreLabel, mainFrame.allImage.FORE1);

			}
		});
		nextLabel.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (rank.size() == 0) {
					MessageFrame.show("快去添加玩伴吧~~");
					return;
				}
				try {
					if (rankContent[5].getIndex() == (rank.size() - 1)
							|| rankContent[5].getIndex() < 0) {
						MessageFrame.show("已经是最后一页了");
					} else {
						loadInfo(rank, rankContent[5].getIndex() + 1,
								rankContent[5].getIndex() + 6);
					}
				} catch (NullPointerException e3) {
					MessageFrame.show("What's the hell?!");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mainFrame.modifyImage(nextLabel, mainFrame.allImage.NEXT2);
				if (mainFrame.isVoice()) {
					new AudioPlayer("sound/anjian.wav");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mainFrame.modifyImage(nextLabel, mainFrame.allImage.NEXT1);

			}
		});
		add(foreLabel);
		add(nextLabel);

		// rankContentPanel
		RCPanel = new JPanel();
		RCPanel.setBounds(width * 49 / 512, height / 6, width * 259 / 320,
				height * 39 / 48);
		RCPanel.setLayout(new GridLayout(6, 1, 3, 1));
		RCPanel.setOpaque(false);
		add(RCPanel);

		for (int i = 0; i < 6; i++) {
			rankContent[i] = new RankLabel(new Dimension(this.getWidth(),
					this.getHeight()));
			RCPanel.add(rankContent[i]);
		}
		updateInfo();
	}

	public void loadInfo(ArrayList<RankVO> rank, int start, int end) {
		if (rank.size() < start) {
			MessageFrame.show("这尼玛！rank怎么会出现我的");
		} else if ((rank.size() - 1) >= end) {
			for (int i = start; i <= end; i++) {
				rankContent[i - start].loadInfo(rank.get(i), i);
			}
		} else {
			System.out.println("排行榜的size是" + rank.size());
			for (int i = start; i < rank.size(); i++) {
				rankContent[i].loadInfo(rank.get(i), i);
			}
		}
	}

	public void updateInfo() {
		// 数据获取
		if (mainFrame.isConnected) {
			rank = mainFrame.fcs.getRanks(mainFrame.user.getId());
			if (rank == null) {
				System.out.println("Rank空指针");
			} else {
				loadInfo(rank, 0, 5);
			}
		}

	}

}
