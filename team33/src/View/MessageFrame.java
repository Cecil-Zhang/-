package View;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import com.sun.awt.AWTUtilities;

@SuppressWarnings("restriction")
public class MessageFrame extends JWindow{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6418077336969855600L;
	private int height;
	private int width;
	private int locationX;
	private int locationY;
	private MessageFrame me = this;
	String message;

    public ImageIcon CLOSE = new ImageIcon("graph/message/close.png");
    public ImageIcon CLOSE2 = new ImageIcon("graph/message/close2.png");
    
	public static void main(String[] args){
		MessageFrame.show("水电费很卡寄售点卡刷卡撒加和说道具发送");
	}

	public MessageFrame (final String message){

        setFrameSize();
	
	    setLayout(null);

	    //设置窗口圆角
	    AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(  
	            0.0D, 0.0D, this.getWidth(), this.getHeight(), 26.0D,  
	            26.0D));  
	    
	    JPanel panel = new JPanel(){
	    	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) 
	   	 {
	   	  super.paintComponent(g);
	   	  Dimension size=this.getSize();
	   	  ImageIcon icon = new ImageIcon("graph/Message/message.png");
	   	  g.drawImage(icon.getImage(),0,0,size.width,size.height,null);
	   	  g.setFont(new Font("微软雅黑",Font.BOLD,height/5));
	   	  if(message.length()<=16){
	   	      g.drawString(message, width*3/10, height/2);
	   	  }else{
	   		  g.drawString(message.substring(0, 16), width*3/10, height*8/20);
	   		  g.drawString(message.substring(16), width*3/10, height*14/20);
 
	   	  }
	   	  
	   	 }
	    };
	    panel.setLayout(null);
	    panel.setBounds(0,0,width,height);
	    
		//关闭图标

		final JLabel closeLabel = new JLabel();
		closeLabel.setBounds(width*14/15, 0, width/15, width/15);
        modifyImage(closeLabel,CLOSE);
		closeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		      me.dispose();
			}
			public void mouseEntered(MouseEvent e) {
				new AudioPlayer("sound/anjian.wav");
			    modifyImage(closeLabel,CLOSE2);
			}
			public void mouseExited(MouseEvent e) {
			    modifyImage(closeLabel,CLOSE);
			}
			});
		panel.add(closeLabel);
	    
	    setContentPane(panel);
        setVisible(true);
        this.requestFocus();
        Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
			
				try {
					this.wait(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MessageFrame.this.dispose();
			}
		});
   }
    
    
    public void setFrameSize(){
//    	 获取屏幕大小
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        height=screenSize.height/7;
        width=screenSize.width*7/20;
  
        setSize(width,height);

        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screen=toolkit.getScreenSize();
        this.locationX=(screen.width -this.getWidth())/2;
        this.locationY=(screen.height -this.getHeight()*2)/2;       
	    this.setLocation(this.locationX,this.locationY);
    }
    
    public int getHeight(){
    	return this.height;
    }
    public int getWidth(){
    	return this.width;
    }
	@SuppressWarnings("static-access")
	public void modifyImage(JLabel label,ImageIcon temp){
		 Image resulttemp = temp.getImage().getScaledInstance(label.getWidth(),  
              label.getHeight(), temp.getImage().SCALE_DEFAULT);
       temp = new ImageIcon(resulttemp);
       label.setIcon(temp);
	}
	
	public static void show(String message){
		new MessageFrame(message);	
	}
	

		
}

	 



