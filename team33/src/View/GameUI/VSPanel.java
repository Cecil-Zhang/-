package View.GameUI;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import View.MainFrame;
import View.GameUI.GamingPanel.mouseListener;

public class VSPanel extends JPanel {
	private ImageIcon portraitIcon[] = new ImageIcon[5];
	int WIDTH;
	int HEIGHT;
	JFrame mainFrame;
	JPanel gamingPanel;
	JButton[][] button;
	int[][] animal;
	JButton opp;
	JTextField score;
	JTextField note;
	mouseListener mouseListener;
	int rivalScore;

	void setFrame(MainFrame frame, JPanel panel_1, JButton[][] a,
			int[][] animals, JButton opponent, JTextField scoreOpponent,
			JTextField minusCL, int k, mouseListener mouseLis) {
		mainFrame = frame;
		gamingPanel = panel_1;
		WIDTH = mainFrame.getWidth();
		HEIGHT = mainFrame.getHeight();
		button = a;
		animal = animals;
		opp = opponent;
		score = scoreOpponent;
		note = minusCL;
		mouseListener=mouseLis;
		showOpponent(k);
	}

	// ��ʾ����ͷ��
	void showOpponent(int k) {
		Image temp;
		for (int i = 0; i < 5; i++) {
			portraitIcon[i] = new ImageIcon("graph/portrait/"
					+ String.valueOf(i + 1) + ".png");
			temp = portraitIcon[i].getImage().getScaledInstance(HEIGHT * 2 / 7,
					HEIGHT * 2 / 7, portraitIcon[i].getImage().SCALE_DEFAULT);
			portraitIcon[i] = new ImageIcon(temp);
		}
		opp.setVisible(true);
		opp.setIcon(portraitIcon[k]);
		score.setVisible(true);
		score.setText("�÷֣�0");
		mainFrame.repaint();
	}

	// ���ֵ÷�
	public void updateScore(int sco) {
		// opponent.setIcon()
		rivalScore=sco;
		mainFrame = MainFrame.getInstance();
		score.setText("�÷�:"+String.valueOf(sco));
		ScoreThread scoT = new ScoreThread();
		scoT.setOpp(opp);
		Thread st = new Thread(scoT);
		st.start();
	}

	// ����ʩ�ӵ���B,����ĳ��ͼ�������ƶ�
	public void propB(int k) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (animal[i][j] == k) {
					button[i][j].setEnabled(false);
					button[i][j].removeMouseListener(mouseListener);
				}
			}
		}
	}

	public void propBReleased(int k) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (animal[i][j] == k) {
					button[i][j].setEnabled(true);
					button[i][j].addMouseListener(mouseListener);
				}
			}
		}
	}

	// �����γ��޵�״̬,�������C���ܼ���5s
	// ���������ʾ�������ڵ÷֣���ʾ"prop C minus 5s"
	public void propC() {
		note.setVisible(true);
		Thread st = new Thread(new propCThread());
		st.start();
	}
	
	public int getRivalScore(){
		return rivalScore;
	}

	class propCThread implements Runnable {
		@Override
		public void run() {
			note.setVisible(true);
		    note.setLocation(WIDTH * 1 / 12, HEIGHT * 5 / 8);
			for (int i = 0; i < 10; i++) {
				note.setLocation(note.getLocation().x,
						note.getLocation().y - 15);
				if (i < 4) {
					if (i % 2 == 0) {
						gamingPanel.setLocation(
								gamingPanel.getLocation().x + 2,
								gamingPanel.getLocation().y + 2);
					} else {
						gamingPanel.setLocation(
								gamingPanel.getLocation().x - 2,
								gamingPanel.getLocation().y - 2);
					}
				}
				mainFrame.repaint();
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			note.setVisible(false);
			mainFrame.repaint();

		}
	}

	class ScoreThread implements Runnable {
		private JButton opponent;

		void setOpp(JButton opp) {
			opponent = opp;
		}

		@Override
		public void run() {
			// synchronized (ScoreThread.class) {
			// TODO Auto-generated method stub
			for (int i = 0; i < 4; i++) {
				if (i % 2 == 1) {
					opponent.setVisible(true);
				} else {
					opponent.setVisible(false);
				}

				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mainFrame.repaint();
			}
		}
		// }
	}

}
