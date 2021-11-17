public class Table {
	private static final int NUM_PHILO = 5;
	public Chopstick[] chopsticks = new Chopstick[NUM_PHILO];
	
	
	public static void main(String[] args) {
		
	}
	
	
	Philosopher[] philos = new Philosopher[NUM_PHILO];
	
	Table() {
		startTable();
	}
	
	public void startTable() {
		//Start Philosophers
		for (int i = 0; i < NUM_PHILO; i++) {
			Philosopher p = new Philosopher(i);
			philos[i] = p;
		}
		//Start Chopsticks
		for(int i = 0; i < chopsticks.length; i ++)
		{
			chopsticks[i] = new Chopstick();
		}
	}
	
	

}

class Chopstick {
	boolean acquired = false;
	int owner = -1; //-1 means it's on the table
	
	
	Chopstick() {
		
	}
	
	void acquire(int myowner)
	{
		if(owner == -1)
		{
			owner = myowner;
		}
	}
	
	void release() {
		acquired = false;
		owner = -1;
	}
	
	Chopstick getNeighbor()
	{
		return this;
	}
}
