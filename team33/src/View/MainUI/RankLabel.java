package View.MainUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import View.MessageFrame;

import vo.RankVO;


/**
 * @ClassName UserLabel
 * @Discription  排行榜和好友列表中一个好友的显示
 * @author Fred Xue
 * @Date 2014-5-10
 */
public class RankLabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5240260666012388998L;
	
	public int width;
	public int height;
	private int score;
	private int rank;
	private String name;
	private int index =-1;
	
	private JLabel r;//排名
	private JLabel n;//姓名
	private JLabel s;//分数
	
	public RankLabel(Dimension d){
		this.width =d.width*13/16;
		this.height =d.height*13/96;
		setLayout(null);
		setOpaque(false);
		
		r = new JLabel();
		r.setFont(new Font("Brush Script Std",Font.BOLD,height*4/8));
		r.setForeground(Color.CYAN);
		r.setBounds(0,height/16,height*9/8,height*7/8);
	    r.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		n = new JLabel();
		n.setFont(new Font("华文行楷",Font.BOLD,height*3/8));
		n.setForeground(Color.GRAY);
		n.setBounds(width/7,height/16,width*3/7,height*7/8);
		
		s = new JLabel();
	    s.setFont(new Font("Brush Script Std",Font.BOLD,height*4/8));
	    s.setForeground(Color.GRAY);
	    s.setBounds(width*3/7,height/16,width*39/70,height*7/8);
	    s.setText("");
	    s.setHorizontalAlignment(SwingConstants.RIGHT);
		add(r);
		add(n);
		add(s);	
		
	}
	
	public void loadInfo(RankVO rankInfo,int in){

	try{
	    this.index =in;
		this.score=rankInfo.getScore();
		this.name=rankInfo.getSecondName();
        this.rank=rankInfo.getRankNo();
		r.setText(String.valueOf(rank));
		n.setText(name);
		NumberFormat formatter = new DecimalFormat("#,###,###");
		s.setText(formatter.format(score));
		}catch(NullPointerException e){
			MessageFrame.show("rankVO is fucking null");
		}
	}
	public int getIndex(){
		return this.index;
	}

	public void paintComponent(Graphics g) 
	 {
	  super.paintComponent(g);
	  Dimension size=this.getSize();
	  ImageIcon icon = new ImageIcon("graph/main/rank_backGround.png");
	  g.drawImage(icon.getImage(),0,0,size.width,size.height,null);
	 }
}
