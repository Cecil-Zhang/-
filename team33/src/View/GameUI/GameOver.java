package View.GameUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import View.MainFrame;
import View.RButton;

public class GameOver {
	private ImageIcon gameOver[] = new ImageIcon[6];
	private MainFrame mainFrame;
	int WIDTH;
	int HEIGHT;
	JLabel backLabel;
	boolean isOver = true;
	JLabel infoLabel4;
	JLabel result;

	public void showGameOver(MainFrame frame, JPanel panel, String s,
			int combo, int money, int rivalScore) {
		this.showGameOver(frame, panel, s, combo, money);

		infoLabel4.setText("对手得分：  " + String.valueOf(rivalScore));
		infoLabel4.setVisible(true);
		if (rivalScore < Integer.parseInt(s)) {
			result.setText("Win!");
		} else {
			result.setText("Lose!");
		}
		result.setVisible(true);

		mainFrame.repaint();
	}

	public void showGameOver(MainFrame frame, JPanel panel, String s,
			int combo, int money) {
		mainFrame = frame;
		WIDTH = mainFrame.getWidth();
		HEIGHT = mainFrame.getHeight();

		JButton okButton = new RButton("确    定");
		okButton.setFont(new Font("楷体", Font.BOLD, 15));
		okButton.setForeground(Color.white);
		okButton.setBounds(WIDTH * 1 / 3 + WIDTH * 6 / 19, HEIGHT * 2 / 5
				- HEIGHT * 1 / 24 + HEIGHT * 1 / 10, WIDTH * 1 / 10,
				HEIGHT * 1 / 15);
		panel.add(okButton);
		okButton.addActionListener(new returnListener());
		okButton.setVisible(true);

		// 提示信息
		JLabel infoLabel = new JLabel("本局得分：  " + s);
		infoLabel.setFont(new Font("宋体", Font.BOLD, 19));
		infoLabel.setBounds(WIDTH * 1 / 4 + WIDTH * 3 / 25, HEIGHT * 2 / 5
				- HEIGHT * 2 / 14 - HEIGHT * 1 / 24 + HEIGHT * 1 / 10,
				WIDTH * 1 / 2, HEIGHT * 1 / 10);
		infoLabel.setVisible(true);
		panel.add(infoLabel);

		JLabel infoLabel2 = new JLabel("最多连击：  " + String.valueOf(combo));
		infoLabel2.setFont(new Font("宋体", Font.BOLD, 20));
		infoLabel2.setBounds(WIDTH * 1 / 4 + WIDTH * 4 / 25, HEIGHT * 2 / 5
				- HEIGHT * 1 / 14 - HEIGHT * 1 / 24 + HEIGHT * 1 / 10,
				WIDTH * 1 / 2, HEIGHT * 1 / 10);
		infoLabel2.setVisible(true);
		panel.add(infoLabel2);

		JLabel infoLabel3 = new JLabel("金币：   ￥" + String.valueOf(money));
		infoLabel3.setFont(new Font("宋体", Font.BOLD, 20));
		infoLabel3.setBounds(WIDTH * 1 / 4 + WIDTH * 5 / 25, HEIGHT * 2 / 5
				- HEIGHT * 1 / 24 + HEIGHT * 1 / 10, WIDTH * 1 / 2,
				HEIGHT * 1 / 10);
		infoLabel3.setVisible(true);
		panel.add(infoLabel3);

		// 对战时显示对手得分和对战结果
		infoLabel4 = new JLabel();
		infoLabel4.setFont(new Font("宋体", Font.BOLD, 19));
		infoLabel4.setBounds(WIDTH * 1 / 4 + WIDTH * 6 / 25, HEIGHT * 2 / 5
				+ HEIGHT * 1 / 14 - HEIGHT * 1 / 24 + HEIGHT * 1 / 10,
				WIDTH * 1 / 2, HEIGHT * 1 / 10);
		infoLabel4.setVisible(false);
		panel.add(infoLabel4);

		result = new JLabel();
		result.setFont(new Font("楷体", Font.BOLD, 45));
		result.setForeground(Color.yellow);
		result.setBounds(WIDTH * 5 / 7 - HEIGHT * 1 / 14, HEIGHT * 2 / 7,
				WIDTH * 1 / 2, HEIGHT * 1 / 6);
		result.setVisible(false);
		panel.add(result);

		Image temp;
		ImageIcon back;
		back = new ImageIcon("graph/gaming/提示背景.png");
		temp = back.getImage().getScaledInstance(WIDTH * 4 / 6, HEIGHT * 4 / 6,
				back.getImage().SCALE_DEFAULT);
		back = new ImageIcon(temp);

		// 提示框的背景
		JLabel backLabel2 = new JLabel();
		backLabel2.setOpaque(false);
		backLabel2.setBounds(WIDTH * 8 / 19 - WIDTH * 5 / 22, HEIGHT * 2 / 5
				- HEIGHT * 1 / 6, WIDTH * 4 / 6, HEIGHT * 4 / 6);
		backLabel2.setIcon(back);
		panel.add(backLabel2);
		backLabel2.setVisible(true);

		// Image temp;
		for (int i = 0; i < 6; i++) {
			gameOver[i] = new ImageIcon("graph/gaming/" + String.valueOf(i)
					+ ".png");
			temp = gameOver[i].getImage().getScaledInstance(WIDTH, HEIGHT,
					gameOver[i].getImage().SCALE_DEFAULT);
			gameOver[i] = new ImageIcon(temp);
		}
		backLabel = new JLabel();
		backLabel.setOpaque(false);
		backLabel.setBounds(0, 0, WIDTH, HEIGHT);
		panel.add(backLabel);
		backLabel.setIcon(gameOver[0]);
		backLabel.setVisible(true);

		mainFrame.setContentPane(panel);
		mainFrame.repaint();
		gameOverThread gThread = new gameOverThread();
		Thread t2 = new Thread(gThread);
		t2.start();

	}

	public void note(String result, JLabel s) {
		s.setText(result);

	}

	class returnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			isOver = false;
			mainFrame.getMp().setPanel();
			mainFrame.setContentPane(mainFrame.getMp());
			if (mainFrame.isMusic()) {
				mainFrame.playMusic("sound/back.wav");
			}
		}
	}

	class gameOverThread implements Runnable {
		@Override
		public synchronized void run() {
			int i = -1;
			boolean isOver = true;
			while (isOver) {
				backLabel.setVisible(true);
				i++;
				backLabel.setIcon(gameOver[i % 6]);
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mainFrame.repaint();
			}

		}
	}

}
