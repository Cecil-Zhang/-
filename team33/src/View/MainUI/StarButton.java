package View.MainUI;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import View.AudioPlayer;
import View.MainFrame;

 
/**
 * @ClassName StarButton
 * @Discription
 * @author Fred Xue
 * @Date 2014-5-9
 */
public class StarButton extends JLabel {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7293580690929292316L;
	
	public static ImageIcon IMG_STAR_BUTTON = new ImageIcon("graph/candy.png");
	
    @SuppressWarnings("static-access")
	public StarButton(String name,int width,int height) { 	
        super(name);
        this.setSize(width, height);
        Image temp = IMG_STAR_BUTTON.getImage().getScaledInstance(width,  
                         height, IMG_STAR_BUTTON.getImage().SCALE_DEFAULT);  
        IMG_STAR_BUTTON = new ImageIcon(temp);   
        this.setIcon(IMG_STAR_BUTTON);
        this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(MainFrame.getInstance().isVoice()){
			            new AudioPlayer("sound/anjian.wav");
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
			
			}
		});
    }

 

 
}