package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;

import javax.swing.JPanel;

import Model.patternModel.TestHelper;
import View.GameUI.GamingPanel;


/**
 * @ClassName Animation3
 * @Discription 游戏开始倒计时
 * @author Fred Xue
 * @Date 2014-6-7
 */
public class Animation3 extends JPanel implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 520622956008520453L;
	MainFrame mainFrame;
	int imageNumber=0;
	float ii = 0.0f;
    boolean jump;

	public Animation3(GamingPanel gPanel){
		super();
		this.mainFrame=gPanel.mainFrame;
		mainFrame.stopMusic();
		this.setLayout(null);
		this.setOpaque(false);
		this.setVisible(true);
		mainFrame.setDragable(this);  
		mainFrame.setContentPane(this);
		TestHelper.testControl("Animation3 setContentPane");
		mainFrame.validate();
		mainFrame.repaint();
		this.setBounds(0, 0, mainFrame.frameWidth, mainFrame.frameHeight);
		Thread ani3=new Thread(this);
		ani3.start();
		TestHelper.testControl("动画开始于 "+new Date());
		try {
			ani3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestHelper.testControl("动画结束于 "+new Date());
	}
  
	@Override
	public void paintComponent(Graphics g){
		 Graphics2D g2 = (Graphics2D) g;
	//设置透明度递增
		// g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  ii));
		g2.drawImage(mainFrame.allImage.animation3.get(imageNumber), 0, 0,this.getWidth(),this.getHeight(), this); 
	
		this.requestFocus();
	}
	
	@Override
	public void run() {
		for (int i = 0; i <28; i++) {	
			if(imageNumber==18&&mainFrame.isVoice()) {
				mainFrame.playMusic("sound/Readygo.wav");
			}
			imageNumber++;
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
		}
		mainFrame.stopMusic();
		this.setVisible(false);
		
	}
}
