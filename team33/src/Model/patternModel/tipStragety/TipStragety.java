package Model.patternModel.tipStragety;

import java.util.ArrayList;

import Model.patternModel.location.MatricLocation;

public interface TipStragety {
	/**
	 * 获得当前棋牌的提示
	 * @return ArrayList<MatricLocation>
	 */
	public ArrayList<MatricLocation> getTips();
}
