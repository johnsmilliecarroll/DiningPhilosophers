public class Philosopher implements Runnable {
	private final Thread t;
	boolean timeToEat = false;
	boolean running;
	int myindex;
	Chopstick mychop1;
	Chopstick mychop2;
	final int minWaitTime = 2000; //2 second min
	final int maxWaitTime = 6000; //6 second max
	
	Philosopher(int numb, Chopstick first, Chopstick second) {
		t = new Thread(this, "Philosopher " + numb);
		myindex = numb;
		running = true;
		t.start();
		mychop1 = first;
		mychop2 = second;
	}
	
	public void grabChopstick() {
		mychop1.acquire(myindex);
		if(mychop1.owner == myindex){
			//I have managed to grab a chopstick!
			mychop2.acquire(myindex);
			if(mychop2.owner == myindex){
				//I have grabbed 2 chopsticks! I can stop waiting and eat.
				timeToEat = true;
			}
			else {
				//Bad luck, put down the first.
				mychop1.release();
			}
		}
		else
		{
			//failure. Keep waiting.
		}
	}
	
	public void eat() {
		System.out.println(t.getName() + " is eating now.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println(t.getName() + " wasn't given enough time to eat.");
		}
		mychop1.release();
		mychop2.release();
	}
	
	public void run() {
		synchronized (this) {
			do {
				// Wait to eat
				while (!timeToEat && running) {
					try {
						Thread.sleep(minWaitTime + (int)(Math.random() * ((maxWaitTime - minWaitTime) + 1)));
						grabChopstick();
					} catch (InterruptedException e) {
						
					}
				}
				if (running) {
					// I can eat
					eat();
				}

			} while (running);
		}
	}

	
	
}
