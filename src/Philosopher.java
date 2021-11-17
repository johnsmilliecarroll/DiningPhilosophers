
public class Philosopher implements Runnable {
	private final Thread t;
	boolean timeToEat = false;
	boolean running;
	int myindex;
	
	
	Philosopher(int numb) {
		t = new Thread(this, "Philosopher " + numb);
		myindex = numb;
		running = true;
		t.start();
	}
	
	public void grabChopstick(Chopstick chop) {
		chop.acquire(myindex);
		if(chop.owner == myindex){
			//I have managed to grab a chopstick!
			Chopstick neighbor = chop.getNeighbor();
			neighbor.acquire(myindex);
			if(neighbor.owner == myindex){
				//I have grabbed 2 chopsticks! I can stop waiting and eat.
				timeToEat = true;
			}
			else {
				//Bad luck, put down the first.
				chop.release();
			}
		}
		else
		{
			//failure. Keep waiting.
		}
	}
	
	public void eat() {
		System.out.println(t.getName() + " is eating now.");
	}
	
	public void run() {
		synchronized (this) {
			do {
				// Wait to eat
				while (!timeToEat && running) {
					try {
						wait();
					} catch (InterruptedException ignored) {
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
