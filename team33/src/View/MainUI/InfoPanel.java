package View.MainUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vo.GameRecordVO;
import View.MainFrame;

/**
 * @ClassName InfoPanel
 * @Discription 个人信息画板
 * @author Fred Xue
 * @Date 2014-5-10
 */
public class InfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2166607446780265913L;

	private MainFrame mainFrame;
	private JLabel sexLabel;
	private JLabel portraitLabel;
	private JLabel infoLabel[] = new JLabel[6];
	GameRecordVO record;
	private int width;
	private int height;

	private int[] games =new int[7];
	private double[] scores = new double[7];
	private ChartPanel1 gamesPanel;
	private ChartPanel2 scoresPanel;

	public InfoPanel(MainFrame mFrame) {
		this.mainFrame = mFrame;
		this.width = mainFrame.getWidth() * 3 / 5;
		this.height = mainFrame.getHeight() * 3 / 5;

		setLayout(null);
		setOpaque(false);
		setSize(width, height);

		JLabel backLabel = new JLabel();
		backLabel.setBounds(0, 0, width, height);
		mainFrame.modifyImage(backLabel, mainFrame.allImage.INFO_BACK);
		add(backLabel);

		// 头像
		portraitLabel = new JLabel();
		portraitLabel.setSize(width / 8, width / 8);
		// mainFrame.modifyImage(portraitLabel,mainFrame.allImage.default_portrait);
		portraitLabel.setLocation(width / 12, height / 5);

		sexLabel = new JLabel();
		sexLabel.setBounds(width / 4, height / 4, height / 18, height / 18);
		mainFrame.modifyImage(sexLabel, mainFrame.allImage.MALE);

		// ID NAME GAME HIGHEST_SCORE HIGHEST_COMBO AVERAGE_SCORE

		Point infoPoint[] = { new Point(width / 6, height * 12 / 20),
				new Point(width / 6, height * 23 / 32),
				new Point(width * 14 / 24, height * 28 / 40),
				new Point(width * 21 / 24, height * 28 / 40),
				new Point(width * 14 / 24, height * 32 / 40),
				new Point(width * 21 / 24, height * 32 / 40) };

		for (int i = 0; i < 6; i++) {
			infoLabel[i] = new JLabel();
			infoLabel[i].setFont(new Font("微软雅黑", Font.BOLD, height / 24));
			infoLabel[i].setBounds(infoPoint[i].x, infoPoint[i].y, width / 9,
					height / 24);
		}
		if(mainFrame.isConnected){
	    record = mainFrame.ucs.checkRecord(mainFrame.user.getId());
		games = record.getEveryDayRound();
		scores = record.getAverageScores();
		}
	
		
        gamesPanel = new ChartPanel1();
        scoresPanel=new ChartPanel2();

		gamesPanel.setBounds(width / 3, height / 30, width * 3 / 5,
				height * 5 / 8);
		gamesPanel.setOpaque(false);
		scoresPanel.setBounds(width / 3, height / 30, width * 3 / 5,
				height * 5 / 8);
		scoresPanel.setOpaque(false);


	}

	public void updateInfo() {

		if (mainFrame.isConnected) {
			record = mainFrame.ucs.checkRecord(mainFrame.user.getId());
			games = record.getEveryDayRound();
			scores = record.getAverageScores();
			String[] infodata = new String[6];
			infodata[0] = mainFrame.user.getId();
			infodata[1] = mainFrame.user.getName();
			if (mainFrame.user.isGender()) {
				mainFrame.modifyImage(sexLabel, mainFrame.allImage.FEMALE);
			} else {
				mainFrame.modifyImage(sexLabel, mainFrame.allImage.MALE);

			}
			try {
				System.out.println("Before !!!!!!!!!!!!!!!in the Panel :");
				
			
		System.out.println("!!!!!!!!!!!!!!!in the Panel :"
						+ record.getAverageScore());
				infodata[2] = String.valueOf(record.getTotalRound());
				infodata[3] = String.valueOf(record.getHighestScore());
				infodata[4] = String.valueOf(record.getHighestCombo());
				DecimalFormat df=new DecimalFormat("#.0"); 
				infodata[5] = String.valueOf(df.format(record.getAverageScore()));

			} catch (NullPointerException npe2) {
				System.out.println("个人信息特么的也有空指针？");
			}

			for (int i = 0; i < 6; i++) {
				System.out.println("个人信息："+infodata[i]);
				System.out.println("Games"+scores[i]);
				infoLabel[i].setText(infodata[i]);
				add(infoLabel[i]);
			}
			System.out.println("Games"+games[6]);
			
			mainFrame.modifyImage(portraitLabel,
					mainFrame.allImage.PORTRAIT[mainFrame.user.getImage() - 1]);
			gamesPanel.repaint();
			scoresPanel.repaint();
			
			this.add(gamesPanel);
			this.add(scoresPanel);
			this.add(sexLabel);
			this.add(portraitLabel);

		} else {

			JLabel cover = new JLabel();
			cover.setBounds(0, 0, width, height);
			mainFrame.modifyImage(cover, mainFrame.allImage.COVER);
			add(cover);
		}
	}
//局数折线
	class ChartPanel1 extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			ImageIcon AXIS = new ImageIcon("graph/main/图表.png");
			int w = width * 3 / 5;
			int h = height * 3 / 5;

			// 绘制图
			Graphics2D g2d = (Graphics2D) g;
			g.drawImage(AXIS.getImage(), 0, 0, w, h, null);
			// 绘制每日局数

			int drawHigh[] = new int[7];
			int drawwidth[] = new int[7];

			// 折点坐标
			for (int i = 0; i < 7; i++) {
				drawHigh[i] = h * 410 / 500
						- (int) (Math.ceil(games[6-i] * 18));
				drawwidth[i] = w * (116 + i * 59) / 600;
				// System.out.println(drawHigh[i]);
			}
			// 折线粗细
			g2d.setStroke(new BasicStroke(3.0f));
			// 折线的颜色
			g2d.setPaint(Color.RED);
			// 画折线
			g2d.drawPolyline(drawwidth, drawHigh, 7);

			g.dispose();
		}
	}
//得分折线
	class ChartPanel2 extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			int w = width * 3 / 5;
			int h = height * 3 / 5;
			// 绘制每日平均得分
			Graphics2D g2d = (Graphics2D) g;
			int drawHigh2[] = new int[7];
			int drawwidth2[] = new int[7];
			// 折点坐标
			for (int i = 0; i < 7; i++) {
				drawHigh2[i] = h * 410 / 500 - (int) (scores[6-i]) * 16;
				drawwidth2[i] = w * (116 + i * 59) / 600;
				// System.out.println(drawHigh2[i]);
			}
			// 折线粗细
			g2d.setStroke(new BasicStroke(3.0f));
			// 折线的颜色
			g2d.setPaint(Color.BLUE);
			// 画折线
			g2d.drawPolyline(drawwidth2, drawHigh2, 7);
			// 产生图像
			g.dispose();
		}
	}

}
