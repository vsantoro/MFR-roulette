package roulette_server;

import roulette_server.Croupier;

import java.lang.reflect.Modifier;

public class TableWheel implements Runnable
{
    private Thread tableThread=new Thread(this);
    private Croupier croupier;
    private boolean spinning;
    private int winningNumber;
    private double startingSpeed;
    private double rotationAngle;
    private static final int[] numberArray;
    private static final double fieldAngle = 9.73;

    static
    {
        numberArray=new int[37];
        numberArray[0]=22;
        numberArray[1]=18;
        numberArray[2]=29;
        numberArray[3]=7;
        numberArray[4]=28;
        numberArray[5]=12;
        numberArray[6]=35;
        numberArray[7]=3;
        numberArray[8]=26;
        numberArray[9]=0;
        numberArray[10]=32;
        numberArray[11]=15;
        numberArray[12]=19;
        numberArray[13]=4;
        numberArray[14]=21;
        numberArray[15]=2;
        numberArray[16]=25;
        numberArray[17]=17;
        numberArray[18]=34;
        numberArray[19]=6;
        numberArray[20]=27;
        numberArray[21]=13;
        numberArray[22]=36;
        numberArray[23]=11;
        numberArray[24]=30;
        numberArray[25]=8;
        numberArray[26]=23;
        numberArray[27]=10;
        numberArray[28]=5;
        numberArray[29]=24;
        numberArray[30]=16;
        numberArray[31]=33;
        numberArray[32]=1;
        numberArray[33]=20;
        numberArray[34]=14;
        numberArray[35]=31;
        numberArray[36]=9;
    }
    //==========================
    //constructor and terminator
    //==========================

    public TableWheel()
    {
        croupier = null;
        spinning = false;
        rotationAngle = 0;
        tableThread.start();
    }

    public void terminate() {
        tableThread.interrupt();
    }



    //===================
    //croupier management
    //===================

    public void setCroupier(Croupier _croupier)
    {
        croupier=_croupier;
    }

    public Croupier getCroupier()
    {
        return croupier;
    }



    //================
    //table management
    //================

    public synchronized void startSpinning(double speed) {
        startingSpeed = speed;
        spinning = true;
        notify();
    }

    public synchronized void stopSpinning() {
        rotationAngle = 0;
        croupier.wheelStopped();
        spinning = false;
    }

    public int calculateNumber()
    {
        int number=(int)Math.floor(((int) rotationAngle % 360) / fieldAngle);
        return number;
    }

    public int getWinningNumber()
    {
        return winningNumber;
    }



    //===
    //run
    //===

    public void run()
    {
        try
        {
            while(!Thread.interrupted())
            {
                synchronized (this)
                {
                    while(!spinning)
                        wait();
                }
                double tempSpeed=startingSpeed;
                int i=1;
                while(tempSpeed>=(startingSpeed*0.01))
                {
                    Thread.sleep(10);
                    rotationAngle=rotationAngle+tempSpeed*(i++)*10;
                    tempSpeed*=0.99;
                }
                winningNumber=numberArray[calculateNumber()];
                stopSpinning();
            }
        }
        catch (InterruptedException e) {}
    }
}

