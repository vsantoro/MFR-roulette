package roulette_server;

/**
 * Created by Jovan on 5/17/2015.
 */

import roulette_server.Croupier;

public class TableWheel implements Runnable
{
    private Thread TableWheelThread=new Thread(this);
    private Croupier croupier;
    private boolean spinning;
    private int winningNumber;

    public TableWheel()
    {
        croupier=null;
        spinning=false;
        TableWheelThread.start();
    }
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
                Thread.sleep(8000);
                setWinningNumber();
                croupier.wheelFinished();
                spinning=false;
            }
        }
        catch (InterruptedException e) {}
    }

    public void setCroupier(Croupier _croupier)
    {
        croupier=_croupier;
    }

    public Croupier getCroupier()
    {
        return croupier;
    }

    public synchronized void spinWheel()
    {
        spinning=true;
        notify();
    }
    public void setWinningNumber()
    {
        winningNumber= (int)(Math.random()*37);
    }
    public int getWinningNumber()
    {
        return winningNumber;
    }
}

