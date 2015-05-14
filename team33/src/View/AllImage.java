package View;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @ClassName AllImage
 * @Discription ����ͼƬ��ʼ��
 * @author Fred Xue
 * @Date 2014-5-9
 */
public class AllImage {

	public Image IMG_JZ_FRAME = new ImageIcon("graphics/text/frame.png")
			.getImage();
	public ImageIcon FRIENDWORD = new ImageIcon("graph/main/����.png");
	public ImageIcon FORE1 = new ImageIcon("graph/main/fore.png");
	public ImageIcon FORE2 = new ImageIcon("graph/main/fore2.png");
	public ImageIcon NEXT1 = new ImageIcon("graph/main/next.png");
	public ImageIcon NEXT2 = new ImageIcon("graph/main/next2.png");
	public ImageIcon default_portrait = new ImageIcon("graph/portrait/0.jpg");
	public ImageIcon COVER = new ImageIcon("graph/main/info_cover.png");
	public ImageIcon INFO_BACK = new ImageIcon("graph/main/info_back.png");
	public ImageIcon FEMALE = new ImageIcon("graph/main/female.png");
	public ImageIcon MALE = new ImageIcon("graph/main/male.png");
	public ImageIcon MINIMIZE_ICON = new ImageIcon("graph/minimize.png");
	public ImageIcon MINIMIZE_ICON2 = new ImageIcon("graph/minimize2.png");
	public ImageIcon CLOSE_ICON = new ImageIcon("graph/close.png");
	public ImageIcon CLOSE_ICON2 = new ImageIcon("graph/close2.png");
	public ImageIcon PANEL_BACKGROUND = new ImageIcon(
			"graph/main/panel_background.png");
	public ImageIcon MAIN_ICON[] = { new ImageIcon("graph/main/������Ϣ.png"),
			new ImageIcon("graph/main/���а�.png"),
			new ImageIcon("graph/main/���ѹ���.png"),
			new ImageIcon("graph/main/����.png"),
			new ImageIcon("graph/main/��ʼ��Ϸ.png") };
	public ImageIcon CANDY = new ImageIcon("graph/candy2.png");
	public ImageIcon LOGO_VERTICAL = new ImageIcon(
			"graph/main/logo_vertical.png");
	public ImageIcon RANKWORD = new ImageIcon("graph/main/����.png");
	public ImageIcon[] PORTRAIT = { new ImageIcon("graph/portrait/1.jpg"),
			new ImageIcon("graph/portrait/2.jpg"),
			new ImageIcon("graph/portrait/3.jpg"),
			new ImageIcon("graph/portrait/4.jpg"),
			new ImageIcon("graph/portrait/5.jpg") };
	public ImageIcon MODIFYNAME = new ImageIcon("graph/main/text.png");
	public ImageIcon MODIFYNAME2 = new ImageIcon("graph/main/text2.png");
	public ImageIcon DELETE = new ImageIcon("graph/main/delete.png");
	public ImageIcon DELETE2 = new ImageIcon("graph/main/delete2.png");
	public ImageIcon SELECTWORD = new ImageIcon("graph/main/ѡ����Ϸģʽ.png");
	public ImageIcon XIEZUO = new ImageIcon("graph/main/Э��.png");
	public ImageIcon DUIZHAN = new ImageIcon("graph/main/��ս.png");
	public ImageIcon XIEZUO2 = new ImageIcon("graph/main/Э��2.png");
	public ImageIcon DUIZHAN2 = new ImageIcon("graph/main/��ս2.png");
	public ImageIcon SINGLE = new ImageIcon("graph/main/����.png");
	public ImageIcon SINGLE2 = new ImageIcon("graph/main/����2.png");
	public ImageIcon SEARCH = new ImageIcon("graph/main/search.png");
	public Image MOUSE = new ImageIcon("graph/mouse.png").getImage();
	public ImageIcon MAILBOX1 = new ImageIcon("graph/main/mailbox.png");
	public ImageIcon MAILBOX2 = new ImageIcon("graph/main/mailbox2.png");
	public ImageIcon AXIS = new ImageIcon("graph/main/ͼ��.png");
	public ImageIcon[] ITEM = { new ImageIcon("graph/main/����A.png"),
			new ImageIcon("graph/main/����B.png"),
			new ImageIcon("graph/main/����C.png") };
	public ImageIcon MONEY = new ImageIcon("graph/main/money.png");
	public ImageIcon ABOUTUS = new ImageIcon("graph/main/Aboutus.png");
	public Image ANIMATION3_END = new ImageIcon("graph/animation3_end")
			.getImage();
	public Image ANIMATION1_END = new ImageIcon("graph/animation1_end")
			.getImage();

	public ImageIcon START = new ImageIcon("graph/��ʼ.png");
	public ImageIcon STOP = new ImageIcon("graph/��ͣ.png");
	public ImageIcon[] process_Icon = new ImageIcon[61];
	
	//���һ
	public ImageIcon Icon[] = new ImageIcon[16];// �洢���δ���������ʱ��ͼ��
	public ImageIcon Icon2[] = new ImageIcon[16];// �洢�������������ͼ��
	
	//����
	public ImageIcon Icon3[] = new ImageIcon[16];// �洢���δ���������ʱ��ͼ��
	public ImageIcon Icon4[] = new ImageIcon[16];// �洢�������������ͼ��
	
	public ImageIcon super_Icon[] = new ImageIcon[2];

	public List<BufferedImage> animation2;
	public List<BufferedImage> animation3;

	public AllImage() {
		animation2();
		animation3();
	}

	public void animation2() {
		animation2 = new ArrayList<BufferedImage>(24);
		for (int i = 1; i <= 24; i++) {
			try {
				animation2.add(ImageIO.read(new File("graph/animation2/b" + i
						+ ".jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(animation2.size());
	}

	public void animation3() {
		animation3 = new ArrayList<BufferedImage>(29);
		for (int i = 1; i <= 29; i++) {
			try {
				animation3.add(ImageIO.read(new File("graph/animation3/c" + i
						+ ".png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void initialGamingImage() {
		super_Icon[0] = new ImageIcon("graph/����ģʽ.png");
		super_Icon[1] = new ImageIcon("graph/����ģʽ2.png");

		//���һͼ����ȡ��ʼ
		for (int i = 1; i < 6; i++) {
			Icon[i] = new ImageIcon("graph/theme/theme1/"
					+ String.valueOf(i - 1) + "_1.png");
		}
		for (int i = 11; i < 16; i++) {
			Icon[i] = new ImageIcon("graph/����/0-"
					+ String.valueOf((i - 10)*2) + ".png");
		}
		Icon[7] = new ImageIcon("graph/����/1.png");
		Icon[8] = new ImageIcon("graph/��ըЧ��.png");
		// ����ڰ�ť��ʱӦ�仯��ͼ��
		for (int i = 1; i < 6; i++) {
			Icon2[i] = new ImageIcon("graph/theme/theme1/"
					+ String.valueOf(i - 1) + "_2.png");
		}
		for (int i = 11; i < 16; i++) {
			Icon2[i] = new ImageIcon("graph/����/0-"
					+ String.valueOf((i - 10)*2-1) + ".png");
		}
		Icon2[7] = new ImageIcon("graph/����/2.png");
		
		
		
		//����ͼ����ȡ��ʼ
		for (int i = 1; i < 6; i++) {
			Icon3[i] = new ImageIcon("graph/theme/theme2/"
					+ String.valueOf(i) + "_1.png");
		}
		for (int i = 11; i < 16; i++) {
			Icon3[i] = new ImageIcon("graph/theme/theme2/"
					+ String.valueOf(i-10) + "_3.png");
		}
		Icon3[7] = new ImageIcon("graph/����/1.png");
		Icon3[8] = new ImageIcon("graph/��ըЧ��.png");
		// ����ڰ�ť��ʱӦ�仯��ͼ��
		for (int i = 1; i < 6; i++) {
			Icon4[i] = new ImageIcon("graph/theme/theme2/"
					+ String.valueOf(i) + "_2.png");
		}
		for (int i = 11; i < 16; i++) {
			Icon4[i] = new ImageIcon("graph/theme/theme2/"
					+ String.valueOf(i - 10) + "_4.png");
		}
		Icon4[7] = new ImageIcon("graph/����/2.png");


		for (int i = 0; i < 61; i++) {
			process_Icon[i] = new ImageIcon("graph/process/"
					+ String.valueOf(i) + ".png");
		}
	}
}
