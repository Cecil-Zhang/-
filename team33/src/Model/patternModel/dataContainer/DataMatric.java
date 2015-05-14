package Model.patternModel.dataContainer;

import Model.patternModel.location.MatricLocation;
import Model.patternModel.observerPattern.Observable;
import Model.patternModel.observerPattern.Observer;
import Model.patternModel.TestHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * ����������Ϸ���̵ĵײ�ģ��,����洢���޸�����
 * ����ģʽ
 * Created by randy on 14-3-2.
 */
public class DataMatric {
    public static DataMatric dataMatric;   //��������
    private static int length=9;                 //����
    private static boolean isInited=false;  //�Ƿ��ʼ���ˡ�
    int board[][];//����9*9������

    /*
    -----------------------------------------------------------------------------------------------------------------
     */
    private DataMatric() {
        board=new int[length][length];
        

    }
    public static DataMatric getDataMatric() {
        if(dataMatric==null) {
            dataMatric=new DataMatric();
            return dataMatric;
        } else {
            return dataMatric;
        }
    }
    /*
    ----------------------------------------------------------------------------------------------------------------------
     */

    /**
     * for test 
     * @param i
     * @param j
     */
    public boolean testForCoder(int i,int j) {
        for(int x=0;x<length;x++) {
            for(int y=0;y<length;y++) {
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
        return false;
    }

    
    /**
     *  SetType
     * @param i
     * @param j
     * @param value
     */
    private void  setType(int i,int j,int value) {
        //TODO:simple
        board[i][j]=value;
    }



    /**
     * ������Ƴ���
     * @return
     */
    public int getLength() {
        return length;
    }
    /**
     * ���λ���ϵ�ֵ
     * @param i
     * @param j
     * @return
     */
    public int getValue(int i,int j) {
        return board[i][j];
    }
    
    
   
    
   
	


   

    /*
    ------------------------------------------------------------------------------------------------------------------
     */
    /**
     * ���ӿڣ����λ���ϵ�ֵ
     * @param location
     * @return
     */
    public int getDataMatricValue(MatricLocation location) {
        int x=location.getX();
        int y=location.getY();
        if(isInLength(x)&&isInLength(y)) {
            //TestHelper.testJunit(board[x][y]+" in board "+x+" "+y);
            return board[x][y];
        } else {
            TestHelper.testprint("getDataMatricValue i,j beyond return 0");
            return 0; //default

        }
    }

    /**
     * �ж���ȡ���̵�Point��x,y�Ƿ�����������̵ĳ���
     * @param value
     * @return
     */
    public boolean isInLength(int value) {
        if(value<0||value>=length) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * ���ӿڣ�����λ���ϵ�ֵ
     * @param x
     * @return
     */
    public boolean setValue(MatricLocation x) {
        int i=x.getX();
        int j=x.getY();
        if(isInLength(i)&&isInLength(j)) {
            TestHelper.testJunit("dataMatric setValue "+i+j+x.getValue());
            board[i][j]=x.getValue();
            return true;
        } else {
//            TestHelper.testprint("is not in length when setValue");
            return false;
        }
    }
}
