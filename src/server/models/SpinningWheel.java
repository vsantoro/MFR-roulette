package server.models;

import server.models.Croupier;

public class SpinningWheel implements Runnable
{

    //angle of a one field on a spinning wheel
    private static final double FIELD_ANGLE = 9.73;

    //contains roulette wheel numbers in the right order
    private static final int[] numberArray;

    //initialization of the roulette wheel numbers
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

    //separate thread for the wheel activity
    private Thread tableThread=new Thread(this);

    //registered croupier
    private Croupier croupier;
    private boolean isSpinning;
    private int winningNumber;

    //required for the implementation of physics of a table rotation
    private double startingSpeed;
    private double rotationAngle;


    public SpinningWheel()
    {
        croupier = null;
        isSpinning = false;
        rotationAngle = 0;
        tableThread.start();
    }

    public void terminate() {
        tableThread.interrupt();
    }

    //Croupier setter/getter

    public void setCroupier(Croupier _croupier)
    {
        croupier=_croupier;
    }


    public synchronized void startSpinning(double speed)
    {
        startingSpeed = speed;
        isSpinning = true;
        notify();
    }

    //pauses thread execution and updates the croupier
    private synchronized void stopSpinning()
    {
        rotationAngle = 0;
        croupier.registerWheelStopping();
        isSpinning = false;
    }

    //calculates current number based on rotation and field angle
    public int calculateNumber()
    {
        int number=(int)Math.floor(((int) rotationAngle % 360) / FIELD_ANGLE);
        return number;
    }

    public int getWinningNumber()
    {
        return winningNumber;
    }


    public void run()
    {
        try
        {
            while(!Thread.interrupted())
            {
                synchronized (this)
                {
                    while(!isSpinning)
                        wait();
                }
                double tempSpeed=startingSpeed;
                int i=1;

                /*
                decreases speed until it is lower than 1% of the starting speed, calculates
                rotation angle
                */

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

