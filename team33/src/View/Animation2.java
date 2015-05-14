package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;

import Control.patternControl.patternControl;
import Control.patternControl.patternControlService;
import Model.patternModel.TestHelper;
import Model.patternModel.location.MatricLocation;
import View.GameUI.GamingPanel;

/**
 * @ClassName Animation2
 * @Discription 游戏loading
 * @author Fred Xue
 * @Date 2014-6-7
 */
public class Animation2 extends JPanel implements Runnable {
	/**
	 * 游戏loading
	 */
	MainFrame mainFrame;
	int imageNumber = 0;
	float ii = 0.0f;
	boolean isGameReady = false;
	boolean isNetReady = false;
	private GamingPanel game = null;
	patternControlService pControl;

	public Animation2(patternControlService pcService, ArrayList<Integer> list,boolean direction) {
		this.mainFrame = MainFrame.getInstance();
//		mainFrame.stopMusic();
		this.setLayout(null);
		this.pControl=pcService;
		mainFrame.setDragable(this);
		this.setBounds(0, 0, mainFrame.frameWidth, mainFrame.frameHeight);
		mainFrame.setContentPane(this);
		mainFrame.validate();
		mainFrame.repaint();
		Thread ani=new Thread(this);
		ani.start();
		
		NewGamingPanelThread newPanel = new NewGamingPanelThread(pcService,
				list,direction);
		new Thread(newPanel).start();
	}

	public GamingPanel getPanel() {
		while (true) {
			TestHelper.testprint("game "+(game!=null));
			if (game != null) {
				TestHelper.testControl("Gotten GamingPanel!!!");
				return game;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// 设置透明度递增
		// g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		// ii));
		g2.drawImage(mainFrame.allImage.animation2.get(imageNumber), 0, 0,
				this.getWidth(), this.getHeight(), this);

		this.requestFocus();
	}

	@Override
	public void run() {
		for (int i = 0; i < 23; i++) {
			TestHelper.testControl("Loading to "+i);
			if (isGameReady && isNetReady) {
				imageNumber=22;				
				this.repaint();	
				try {
					TestHelper.testControl("");
					pControl.startGame();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			imageNumber++;
			// if(ii<0.99f){
			// ii+=0.002f;
			// }
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			this.repaint();
		}

	}

	public void setGameReady(GamingPanel gamep) {
//		this.game = gamep;
		this.isGameReady = true;
	}

	public void setNetReady() {
		this.isNetReady = true;
	}

	class NewGamingPanelThread implements Runnable {
		patternControlService pcService;
		ArrayList<Integer> list;
		boolean direction;

		NewGamingPanelThread(patternControlService service, ArrayList<Integer> l,boolean d) {
			super();
			this.pcService = service;
			this.list = l;
			this.direction=d;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			game = new GamingPanel(pcService, list,direction);
			isGameReady=true;
		}

	}

}
