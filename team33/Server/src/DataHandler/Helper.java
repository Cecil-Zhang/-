package DataHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import po.UserPO;

/**
 * @ClassName Helper
 * @Description �����ļ��Ķ�ȡ�洢
 * @author �����
 * @Date ����9:41:10
 */
public class Helper {

	/**
	 * @title inputSer
	 * @Description ��ȡ���л��ļ�
	 * @param fileName
	 * @return
	 */
	public ArrayList<Object> inputSer(String fileName) {
		ArrayList o = new ArrayList();
		try {
			System.out.println("��ȡ:"+fileName);
			FileInputStream fileStream = new FileInputStream(fileName);
			ObjectInputStream os = new ObjectInputStream(fileStream);
			o = (ArrayList) os.readObject();
			os.close();
			
		} catch (Exception e) {
			System.out.println("read is wrong");
			e.printStackTrace();

		}

		return o;
	}

	/**
	 * @title outputSer
	 * @Description �������л��ļ�
	 * @param fileName
	 * @param o
	 */
	public void outputSer(String fileName,
			@SuppressWarnings("rawtypes") ArrayList o) {
		try {
			System.out.println("�洢:"+fileName);
			FileOutputStream fileStream = new FileOutputStream(fileName);
			ObjectOutputStream os = new ObjectOutputStream(fileStream);
			os.writeObject(o);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @title inputTxt
	 * @Description ��ȡ�ı��ļ�
	 * @param fileName
	 * @return
	 */
	public String inputTxt(String fileName) {
		String result = "";
		try {
			String encoding = "GBK";
			File file = new File(fileName);
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					result = lineTxt;
				}
				read.close();
			} else {
				System.out.println("�Ҳ���ָ�����ļ�");
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @title outputTxt
	 * @Description �����ı��ļ�
	 * @param txt
	 * @param fileName
	 */
	public void outputTxt(String txt,String fileName){
		try {
			FileWriter fw=new FileWriter(fileName);
			fw.write(txt);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
