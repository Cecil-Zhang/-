package Model.patternModel;
import Model.patternModel.TestHelper;
import po.PatternType;


/**社会名媛。。好多类都需要她
 * Created by randy on 14-3-2.
 */
public enum ViewType {
    //改变值的个数要更改length,lengthOfProps方法!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
    pattern1,pattern2,pattern3,pattern4,pattern5,prop1,prop2,prop3,prop4,nothing,prop1pattern1,
    					prop1pattern2,prop1pattern3,prop1pattern4,prop1pattern5;




    /*
    ------------------------------------------------------------------------------------------------------------------------
     */
    public PatternType changeToPatternType(ViewType type) {
        switch (type) {
            case pattern1:
                return PatternType.pattern1;
            case pattern2:
                return PatternType.pattern2;
            case pattern3:
                return PatternType.pattern3;
            case pattern4:
                return PatternType.pattern4;
            case pattern5:
                return PatternType.pattern5;
            case prop1:
                return PatternType.propA;
            case prop2:
                return PatternType.propB;
            default:
            	TestHelper.propsTest("return the propA when query the location");
                return PatternType.propA;  //for all the prop1pattern***
                
        }

    }

    /**
     * 返回ViewType的中动物值的个数
     * @return
     */
    public static int length() {
        return 5;
    }
    /**
     * 返回道具的个数
     * @return
     */
    public static int lengthforprops() {
        return 4;
    }

    /*
    -------------------------------------------------------------------------------------------------------------------
     */
    /**
     * 判读棋盘值是否在范围内，不包括道具的值
     * @param value
     * @return
     */
    public static boolean containValue(int value) {
        if(value<0||length()<value) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判读道具的种类是否在范围之内
     * @param value
     * @return
     */
    public static boolean containProps(int value) {
        if(value<0||lengthforprops()<=value) {
            return false;
        } else {
            return true;
        }
    }

    /*
    ---------------------------------------------------------------------------------------------------------------------
     */

    /**
     * 不解释
     * @param value
     * @return
     */
    public static ViewType getTypeFromValue(int value) {
        if (value <= 0) {
            TestHelper.testprint("getTypeFromValue value <0 return default");
            return pattern1;  //当初始化时由于数组为0，所有要返回个不能为五个系统的。
        } else {
            switch (value - 1) {
                case 0:
                    return pattern1;

                case 1:
                    return pattern2;

                case 2:
                    return pattern3;
                case 3:
                    return pattern4;
                case 4:
                    return pattern5;
                case 5:
                	return prop1;
                case 6:
                	return prop2;
                case 7:
                	return prop3;
                case 8:
                	return prop4;
                case 9:
                	return nothing;
                case 10:
                	return prop1pattern1;
                case 11:
                	return prop1pattern2;
                case 12:
                	return prop1pattern3;
                case 13:
                	return prop1pattern4;
                case 14:
                	return prop1pattern5;
                default:
                    TestHelper.testprint("getTypeFromValue has no the value return first" + value);
                    return nothing;

            }
        }
    }


}