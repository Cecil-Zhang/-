package TestDriver;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Model.patternModel.GameState;

/**
 * @ClassName GameStateDriver
 * @Description
 * @author ’≈ ÊœÕ
 * @Date 2014-5-14
 */
public class GameStateDriver {

	GameLogicStub gl;
	GameState gs;
	/**
	 * @title main
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		GameStateDriver driver=new GameStateDriver();
		driver.test();
	}
	
	public void test(){
		JFrame frame=new JFrame();
		JButton b1=new JButton("Start");
		JButton b2=new JButton("End");
		JButton button=new JButton("Clear");
		frame.getContentPane().add(BorderLayout.CENTER,button);
		frame.getContentPane().add(BorderLayout.WEST, b1);
		frame.getContentPane().add(BorderLayout.EAST,b2);
		b1.setSize(30, 30);
		b2.setSize(30, 30);
		button.setSize(30, 30);
		frame.setSize(300, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button.addActionListener(new myListener());
		b1.addActionListener(new startListener());
		b2.addActionListener(new endListener());
		gl=new GameLogicStub();
		gs=new GameState();
	}

	class myListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			gl.myNotify();
		}
		
	}
	
	class startListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			gs.startGame();
		}
		
	}
	
	class endListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			gs.endGame();
		}
		
	}
}
