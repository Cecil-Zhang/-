package View.MainUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import vo.ApplicationVO;
import vo.FriendVO;
import vo.UserVO;
import View.AudioPlayer;
import View.MainFrame;
import View.MessageFrame;

/**
 * @ClassName FriendPanel
 * @Discription
 * @author Fred Xue
 * @Date 2014-5-10
 */
public class FriendPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4750561999940863544L;
    
	private MainFrame mainFrame;
    private int width;
    private int height;
    private JPanel FCPanel;
    private ArrayList<FriendVO> friend;
    FriendLabel[] friendContent = new FriendLabel[6];
    
	
	public FriendPanel(MainFrame mFrame){
		 this.mainFrame=mFrame;
		 this.width=mainFrame.getWidth()*3/5;
		 this.height=mainFrame.getHeight()*3/5;
		 
		 setLayout(null);
		 setOpaque(false);
		 setSize(width,height);
		 
		 JLabel wordLabel = new JLabel();
		 wordLabel.setBounds(width/10, 3, width*2/5, height/7);
		 mainFrame.modifyImage(wordLabel, mainFrame.allImage.FRIENDWORD);
		 add(wordLabel);
		 
		 JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		 sep.setForeground(Color.CYAN);
		 sep.setSize(width*15/16,4);
		 sep.setLocation(width/32,height/6);
		 add(sep);
		 
	
		 final JTextField searchie = new JTextField();
		 searchie.setBounds(width*35/50,height/20,width/5,height/14);
		// searchie.setOpaque(false);
		 searchie.setBackground(Color.GRAY);
		 searchie.setForeground(Color.CYAN);
		 JLabel search = new JLabel();
		 search.setBounds(width*45/50,height/20,height/13,height/13);
		 mainFrame.modifyImage(search, mainFrame.allImage.SEARCH);
		 search.addMouseListener(new MouseAdapter(){
			 public void mouseClicked(MouseEvent e){
				 if(searchie.getText()==null || searchie.getText()==""){
					 MessageFrame.show("同学，加好友要输入好友ID的噢~");
				 }else{
				 String friendID = searchie.getText();
			       UserVO uv = mainFrame.fcs.findUser(friendID); 
			       mainFrame.mcs.sendApplication(new ApplicationVO(mainFrame.user.getId(),mainFrame.user.getName(),friendID,uv.getName()));
				 }
		      }
		 });
		 add(search);
		 add(searchie);
		 
		 final JLabel foreLabel = new JLabel();
		 final JLabel nextLabel = new JLabel();	 
		 foreLabel.setBounds(width/32,height*7/40,width/16,height*4/5);
		 nextLabel.setBounds(width*29/32,height*7/40,width/16,height*4/5);
		 mainFrame.modifyImage(foreLabel, mainFrame.allImage.FORE1);
		 mainFrame.modifyImage(nextLabel, mainFrame.allImage.NEXT1);
		 foreLabel.addMouseListener(new MouseAdapter() {
			 
			    public void mouseClicked(MouseEvent e){
			    	if(friend.size()==0){
		    			MessageFrame.show("快去添加玩伴吧~~");
		    			return;
		    		}
			    try{
		           if(friendContent[0].getIndex()==0){
		        	   MessageFrame.show("已经是第一页了");
		           }else{loadInfo(friend,friendContent[0].getIndex()-6,friendContent[0].getIndex()-1);}
		           }catch(NullPointerException e2){
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
			 
			    public void mouseClicked(MouseEvent e){
			    	if(friend.size()==0){
		    			MessageFrame.show("快去添加玩伴吧~~");
		    			return;
		    		}
			    try{
		           if(friendContent[0].getIndex()==0){
		        	   MessageFrame.show("已经是第一页了");
		           }else{loadInfo(friend,friendContent[0].getIndex()-6,friendContent[0].getIndex()-1);}
		           }catch(NullPointerException e2){
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
		 
		//rankContentPanel
		 FCPanel =new JPanel();
		 FCPanel.setBounds(width*49/512, height/6, width*259/320, height*39/48);
         FCPanel.setLayout(new GridLayout(6,1,3,1));
         FCPanel.setOpaque(false);
         add(FCPanel);
         
         for(int i=0;i<6;i++){
        	 friendContent[i] = new FriendLabel(mainFrame);
        	 FCPanel.add(friendContent[i]);
    }
     
   }
	
	public void loadInfo(ArrayList<FriendVO> friend,int start,int end){
        if(friend.size()<start){
       	 MessageFrame.show("这尼玛！friend怎么会出现我的");
        }else if ((friend.size()-1)>=end){
       	 for(int i=start;i<=end;i++){
       		 friendContent[i].loadInfo(friend.get(i), i);
       	 }
        }else{
       	 System.out.println("好友列表的size是"+friend.size());
       	 for(int i=start;i<friend.size();i++){
       		 friendContent[i].loadInfo(friend.get(i), i);
       	 }
        }
	}
	public void updateInfo(){
		 if(mainFrame.isConnected){
		        //	 
		       if(mainFrame.fcs.getFriendList(mainFrame.user.getId())==null){
		        		 System.out.println("what the hell");
		        }else{
				   friend = mainFrame.fcs.getFriendList(mainFrame.user.getId()).getFriendList();
		        }
				   if(friend==null){
		    	  MessageFrame.show("您还没有任何好友噢！快去添加吧！");
		           }else{
		           loadInfo(friend,0,5);
		                }
		  }else{
		        	 friend = new ArrayList<FriendVO>();
		         }
	}
	

}
