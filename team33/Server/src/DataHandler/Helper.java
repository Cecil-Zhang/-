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
 * @Description 辅助文件的读取存储
 * @author 臧晓杰
 * @Date 下午9:41:10
 */
public class Helper {

	/**
	 * @title inputSer
	 * @Description 读取序列化文件
	 * @param fileName
	 * @return
	 */
	public ArrayList<Object> inputSer(String fileName) {
		ArrayList o = new ArrayList();
		try {
			System.out.println("读取:"+fileName);
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
	 * @Description 保存序列化文件
	 * @param fileName
	 * @param o
	 */
	public void outputSer(String fileName,
			@SuppressWarnings("rawtypes") ArrayList o) {
		try {
			System.out.println("存储:"+fileName);
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
	 * @Description 读取文本文件
	 * @param fileName
	 * @return
	 */
	public String inputTxt(String fileName) {
		String result = "";
		try {
			String encoding = "GBK";
			File file = new File(fileName);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					result = lineTxt;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @title outputTxt
	 * @Description 保存文本文件
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
