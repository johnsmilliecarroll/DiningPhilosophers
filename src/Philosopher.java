public class Philosopher implements Runnable {
	private final Thread t;
	boolean timeToEat = false;
	boolean running;
	int myindex;
	Chopstick mychop1;
	Chopstick mychop2;
	int mychopindex1;
	int mychopindex2;
	final int minWaitTime = 3000; //3 second min
	final int maxWaitTime = 10000; //10 second max
	final int EatTime = 4000; //4 seconds to eat
	final int GrabTime = 1000; //2 seconds to grab
	private int totalWaitTime = 0;
	private int totalEatTime = 0;
	private int timeSpentHungry = 0;
	private int maxStarvationTime = 15000;
	DisplayPanel window = null;
	
	Philosopher(int numb, Chopstick first, Chopstick second, int index1, int index2) {
		t = new Thread(this, "Philosopher " + numb);
		myindex = numb;
		running = true;
		t.start();
		mychop1 = first;
		mychopindex1 = index1;
		mychop2 = second;
		mychopindex2 = index2;
		window = Table.mywindow;
	}
	
	/**
	 *  Here, we try to grab a chopstick. Note that Chopstick.acquire() isn't a sure thing,
	 *  but merely an attempt, so a check is performed in order to see if that chopstick was 
	 *  actually acquired by the Philosopher. If the first has been grabbed, try to grab another.
	 *  If two have been successfully acquired, we are allowed to eat now. Otherwise, we release
	 *  and continue to wait.
	 */
	
	public synchronized void grabChopstick() {
		window.setState("GRABBING",myindex);
		mychop1.acquire(myindex);
		if(mychop1.owner == myindex){
			//I have managed to grab a chopstick!
			window.hideChopstick(mychopindex1);
			mychop2.acquire(myindex);
			try {
				Thread.sleep(GrabTime);
				timeSpentHungry += GrabTime; //increment time since last bite.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(mychop2.owner == myindex){
				//I have grabbed 2 chopsticks! I can stop waiting and eat.
				window.hideChopstick(mychopindex2);
				timeToEat = true;
			}
			else {
				//Bad luck, put down the first.
				mychop1.release();
				window.showChopstick(mychopindex1);
				window.setState("THINKING",myindex);
			}
		}
		else
		{
			//failure. Keep waiting.
			window.setState("THINKING",myindex);
		}
		if(timeSpentHungry >= maxStarvationTime)
		{
			starving();
		}
	}
	
	/**
	 * Here we take our designated time to eat and sleep for that amount of time. After we have
	 * successfully eaten, we release our chopsticks for others to grab.
	 */
	
	public void eat() {
		System.out.println(t.getName() + " is eating with chopsticks " + mychop1.index + " and " + mychop2.index);
		try {
			window.setState("EATING",myindex);
			Thread.sleep(EatTime);
			timeSpentHungry=0;
			totalEatTime += EatTime; //increment our total
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println(t.getName() + " wasn't given enough time to eat.");
		}
		timeToEat = false;
		mychop1.release();
		mychop2.release();
		window.setState("THINKING",myindex);
		window.showChopstick(mychopindex1);
		window.showChopstick(mychopindex2);
		System.out.println(t.getName() + " has released chopsticks " + mychop1.index + " and " + mychop2.index);
	}
	
	/**
	 * Here, we sleep a random amount of time, during which time the philosopher is given time
	 * to "think". Afterwards grabChopstick() is called. If I am allowed to eat I do so.
	 */
	
	public void run() {
		synchronized (this) {
			do {
				// Wait to eat
				while (!timeToEat && running) {
					try {
						int sleepTime = minWaitTime + (int)(Math.random() * ((maxWaitTime - minWaitTime) + 1));
						Thread.sleep(sleepTime);
						totalWaitTime += sleepTime; //increment our total
						timeSpentHungry += sleepTime; //increment time since last bite.
						if(timeSpentHungry >= maxStarvationTime)
						{
							starving();
						}
						grabChopstick();
						
					} catch (InterruptedException e) {
						
					}
				}
				if (timeToEat && running) {
					// I can eat
					eat();
				}

			} while (running);
		}
	}
	
	private void starving()
	{
		window.setState("STARVING",myindex);
	}
	
	/**
	 * Stops the philosopher after the program duration has elapsed, notifies it that it is time
	 * to stop waiting.
	 */
	public void stopMember() {
		running = false;
		synchronized (this) {
			notify(); //let me know to stop waiting
		}
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	
}
