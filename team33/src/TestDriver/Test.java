package TestDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Timer;

public class Test {

	/**
	 * @title main
	 * @Description
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println((int) Math.rint( ( Math.random() * 4 )) +1 );
		System.out.println((int) Math.rint( ( Math.random() * 4 )) +1 );
		System.out.println((int) Math.rint( ( Math.random() * 4 )) +1 );
		System.out.println((int) Math.rint( ( Math.random() * 4 )) +1 );
		System.out.println((int) Math.rint( ( Math.random() * 4 )) +1 );
		System.out.println((int) Math.rint( ( Math.random() * 4 )) +1 );
		System.out.println((int) Math.rint( ( Math.random() * 4 )) +1 );
		System.out.println((int) Math.rint( ( Math.random() * 4 )) +1 );
		System.out.println((int) Math.rint( ( Math.random() * 4 )) +1 );
		
		Test t= new Test();
		ArrayList<String> friends=new ArrayList<String>();
		friends.add("Wade");
		ArrayList<String> rivals=new ArrayList<String>();
		rivals.add("Parker");
		rivals.add("Duncan");
//		t.go(friends,rivals);
//		t.ola();
	}
	
	public void ola(){
		String s=1200+"plus" + 200 ;
		String[] s1=s.split("plus");
		for(String temp:s1){
			System.out.println("1 "+temp);
		}
		String d="1200";
		String[] s2=d.split(" + ");
		for(String temp:s2){
			System.out.println("2 "+temp);
		}
	}
	
	public void run(){
		Date now = new Date();
		System.out.println(now);
		int delay = 5000;
		ActionListener frozenListener=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Date now = new Date();
				System.out.println(now);
			}
		};
		
		Timer frozenTimer = new Timer(delay,frozenListener);
		frozenTimer.setRepeats(false);
		frozenTimer.start();
		while(true){
			
		}
	}

	public void go(ArrayList<String> friends,ArrayList<String> rivals){
		String ID="James";
		String rivalID = rivals.get(0);
		ArrayList<String> newRival = new ArrayList<String>();
		newRival.add(ID);
		for (int i = 0; i < friends.size(); i++) {
			String id = friends.get(i);
			newRival.add(id);
		}
		ArrayList<String> newFriends = new ArrayList<String>();
		for (int i = 1; i < rivals.size(); i++) {
			System.out.println(i);
			System.out.println(rivals.size());
			String id = rivals.get(i);
			newFriends.add(id);
		}
		System.out.println(newRival);
		System.out.println(newFriends);
	}
	
	public void test(){
		Date now = new Date();
		System.out.println(now);
		Thread t=new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date now = new Date();
				System.out.println(now);
			}
			
		});
		t.start();
	}
}
