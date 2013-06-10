package com.liu.trymylanguage.server;

import java.util.TimerTask;

public class InterruptTimerTask
extends TimerTask
{
	private boolean isTimedout; 
	private Thread thread;

	public InterruptTimerTask(Thread t)
	{
		isTimedout = false;
		this.thread = t;
	}

	public void run()
	{
		thread.interrupt();
		isTimedout = true;
	}
	public boolean isTimedout(){
		return isTimedout;
	}
	

}
