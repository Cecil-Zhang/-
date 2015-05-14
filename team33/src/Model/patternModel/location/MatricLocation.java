package Model.patternModel.location;

import java.io.Serializable;

/**
 * Ϊ�˴���Point����࣬��Ϊ����double���͵�xyֵ,�����������ViewType�е�ֵ���޶��������ֵ Created by randy on
 * 14-5-9.
 */
public class MatricLocation implements Serializable, Cloneable {
	private int x;
	private int y;
	private int value; // TODO:������Ի���ViewType����ʵ�������򵥵�
	protected boolean isPattern;// ��������֣�MatricValueLocation is true ,other is
								// false;

	/*
	 * --------------------------------------------------------------------------
	 * -----------------------------------------
	 */

	public MatricLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.value = 0;
		setType();
	}

	/*
	 * --------------------------------------------------------------------------
	 * ------------------------------------------
	 */

	public Object clone() {
		MatricLocation o = null;

		try {
			o = (MatricLocation) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return o;
	}

	/**
	 * get x
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	/*
	 * --------------------------------------------------------------------------
	 * ----------------------------------------
	 */
	/**
	 * get y
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/*
	 * --------------------------------------------------------------------------
	 * -------------------------------------
	 */
	public int getValue() {

		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	/*
	 * --------------------------------------------------------------------------
	 * -----------------------------------------
	 */
	public boolean getType() {
		return isPattern;
	}

	// ����
	protected void setType() {

	}

	@Override
	public boolean equals(Object obj) {
		MatricLocation temp = (MatricLocation) obj;
		if (temp.getX() == x && temp.getY() == y) {
			return true;
		} else {
			return false;
		}

	}
}
