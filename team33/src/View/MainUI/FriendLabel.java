package View.MainUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import vo.FriendVO;
import View.AudioPlayer;
import View.MainFrame;



/**
 * @ClassName FriendLabel
 * @Discription
 * @author Fred Xue
 * @Date 2014-5-20
 */
public class FriendLabel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3863871906459557093L;

	public int width;
	public int height;
	private FriendVO friend;
	private int index;
	private MainFrame mainFrame;

	private JLabel portrait;//Í·Ïñ
	private JLabel sex;
	private JTextField name;//ºÃÓÑêÇ³Æ
	private JLabel modifyName;//±à¼­ºÃÓÑ±¸×¢Ãû
    private JLabel delete;//É¾³ýºÃÓÑ
    
	public FriendLabel(MainFrame m){
		this.mainFrame =m;
		this.width = mainFrame.getWidth()*3/5*13/16;
		this.height = mainFrame.getHeight()*3/5*13/96;
		setLayout(null);
		setOpaque(false);
		portrait = new JLabel();
		portrait.setBounds(height/8,height/8,height*5/8,height*5/8);
		sex = new JLabel();
		sex.setBounds(width/9,height/4,height*4/8,height*4/8);
		
		
		modifyName = new JLabel();
		modifyName.setBounds(width*50/60,height/8,height*4/8,height*4/8);
		modifyImage(modifyName,mainFrame.allImage.MODIFYNAME);
		modifyName.addMouseListener(new MouseAdapter(){

		   public void mouseEntered(MouseEvent e){
			  modifyImage(modifyName,mainFrame.allImage.MODIFYNAME2);
				if(mainFrame.isVoice()){
				      new AudioPlayer("sound/anjian.wav");				
				      }
		  }
	
		public void mouseClicked(MouseEvent e){
			  name.setEditable(true);
//			  name.setOpaque(true);
//			  name.setBackground(Color.BLACK);
		  }
	
		public void mouseExited(MouseEvent e){
			  modifyImage(modifyName,mainFrame.allImage.MODIFYNAME);
		  }


		});
		delete = new JLabel();
		delete.setBounds(width*55/60,height/8,height*4/8,height*4/8);
		modifyImage(delete,mainFrame.allImage.DELETE);
		delete.addMouseListener(new MouseAdapter(){
			
			public void mouseEntered(MouseEvent e){
				  modifyImage(delete,mainFrame.allImage.DELETE2);
					if(mainFrame.isVoice()){
					      new AudioPlayer("sound/anjian.wav");				
					      }
			  }
		
			public void mouseClicked(MouseEvent e){
			  mainFrame.fcs.deleteFriend(mainFrame.user.getId(), friend.getId());
			  }
			 
			public void mouseExited(MouseEvent e){
				  modifyImage(delete,mainFrame.allImage.DELETE);
			  }
			});
		
		name = new JTextField("ÕÅÊ÷ÏÍ");
		name.setFont(new Font("ËÎÌå",Font.BOLD,height*3/8));
		name.setForeground(Color.GRAY);
		name.setBorder(null);
		name.setEditable(false);
		name.setOpaque(false);
		name.setBounds(width/6,height/16,width*3/7,height*7/8);
		name.addKeyListener(new KeyAdapter(){
		public void keyPressed(KeyEvent e ){
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				name.setText(name.getText());
				friend.setSecondName(name.getText());
			  mainFrame.fcs.modifyFriend(friend);
				name.setEditable(false);
			}
		}
		});

	}
	 
	public void loadInfo(FriendVO info,int in){
		this.friend=info;
       if(info.getGender()){
    	   modifyImage(sex,mainFrame.allImage.MALE);
       }else{
    	   modifyImage(sex,mainFrame.allImage.FEMALE);
       }
       modifyImage(portrait,mainFrame.allImage.PORTRAIT[info.getImage()-1]);
       this.name.setText(info.getSecondName());
       this.index=in;
		add(portrait);
		add(name);
		add(delete);
		add(sex);
		add(modifyName);
	}
	
	
	@SuppressWarnings("static-access")
	public void modifyImage(JLabel label,ImageIcon temp){
		 Image resulttemp = temp.getImage().getScaledInstance(label.getWidth(),  
             label.getHeight(), temp.getImage().SCALE_DEFAULT);
      temp = new ImageIcon(resulttemp);
      label.setIcon(temp);
	}
	public void paintComponent(Graphics g) 
	 {
	  super.paintComponent(g);
	  Dimension size=this.getSize();
	  ImageIcon icon = new ImageIcon("graph/main/rank_backGround.png");
	  g.drawImage(icon.getImage(),0,0,size.width,size.height,null);
	 }
	public int getIndex(){
		return this.index;
	}
}
