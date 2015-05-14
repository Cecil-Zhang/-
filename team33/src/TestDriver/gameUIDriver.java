package TestDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Control.gameControl.GameControl;

/**
 * @ClassName gameUIDriver
 * @Description
 * @author 张舒贤
 * @Date 2014-5-16
 */
public class gameUIDriver {
	JButton button;

	public static void main(String[] args){
		gameUIDriver driver=new gameUIDriver();
		driver.generateUI();
	}
	
	public void generateUI(){
		JFrame frame=new JFrame();
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocation(100, 150);
		frame.setLayout(null);
		button=new JButton("开始游戏");
		button.setSize(100, 70);
		button.setLocation(80, 100);
		button.setVisible(true);
		button.addActionListener(new myListener());
		frame.add(button);
		
	}
	
	class myListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			GameControl controller=new GameControl();
			controller.initialSoloGame("100003",false,false,false);
		}
		
	}
}
