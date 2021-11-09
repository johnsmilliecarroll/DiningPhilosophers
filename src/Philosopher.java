
public class Philosopher implements Runnable {
	private final Thread t;
	
	Philosopher(int numb) {
		t = new Thread(this, "Philosopher " + numb);
		t.start();
	}
	
	public void grabChopstick() {
		
	}
	
	public void eat() {
		
	}
	
	public void run() {
		
	}

	
	
}
