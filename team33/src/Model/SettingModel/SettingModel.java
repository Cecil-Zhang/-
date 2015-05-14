package Model.SettingModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SettingModel implements SettingModelService {

	@Override
	public ArrayList<String> getInfo() {
		// TODO Auto-generated method stub
		ArrayList<String> result = new ArrayList<String>();

		String temp = null;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("GameInfo.txt"));
			while ((temp = br.readLine()) != null) {
				result.add(temp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void setWay(boolean way) {
		// TODO Auto-generated method stub

		try {
			FileWriter fw = new FileWriter("Way.txt");
			if (way) {
				fw.write("true");

			} else {
				fw.write("false");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean getWay() {
		// TODO Auto-generated method stub
		boolean result = true;

		String temp = null;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("Way.txt"));
			while ((temp = br.readLine()) != null) {
				if (temp.equals("true")) {
					result = true;
				} else {
					result = false;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void setStyle(boolean s) {
		// TODO Auto-generated method stub
		try {
			FileWriter fw = new FileWriter("Style.txt");
			if (s) {
				fw.write("true");

			} else {
				fw.write("false");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}

	@Override
	public boolean getStyle() {
		// TODO Auto-generated method stub
		boolean result = true;

		String temp = null;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("Style.txt"));
			while ((temp = br.readLine()) != null) {
				if (temp.equals("true")) {
					result = true;
				} else {
					result = false;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

}
