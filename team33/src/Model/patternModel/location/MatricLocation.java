package Model.patternModel.location;

import java.io.Serializable;

/**
 * 为了代替Point这个类，因为它是double类型的xy值,而且它会根据ViewType中的值，限定其包含的值 Created by randy on
 * 14-5-9.
 */
public class MatricLocation implements Serializable, Cloneable {
	private int x;
	private int y;
	private int value; // TODO:这里可以换成ViewType的其实。。。简单点
	protected boolean isPattern;// 子类的区分，MatricValueLocation is true ,other is
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

	// 钩子
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
