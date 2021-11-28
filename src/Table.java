public class Table {
	public static final int NUM_PHILO = 5;
	public Chopstick[] chopsticks = new Chopstick[NUM_PHILO];
	
	
	public static void main(String[] args) {
		new Table();
	}
	
	Philosopher[] philos = new Philosopher[NUM_PHILO];
	
	Table() {
		startTable();
	}
	
	public void startTable() {
		//Start Chopsticks
		for(int i = 0; i < chopsticks.length; i ++)
		{
			chopsticks[i] = new Chopstick(i);
		}
		//Start Philosophers
		for (int i = 0; i < NUM_PHILO; i++) {
			if(i==NUM_PHILO-1) {
				Philosopher p = new Philosopher(i, chopsticks[0], chopsticks[i]);
				philos[i] = p;
			}
			else if(i%2==0) { //even philosopher
				Philosopher p = new Philosopher(i, chopsticks[i], chopsticks[i+1]);
				philos[i] = p;
			}
			else { //odd philosopher
				Philosopher p = new Philosopher(i, chopsticks[i+1], chopsticks[i]);
				philos[i] = p;
			}
			
		}
	}
	
	

}

class Chopstick {
	boolean acquired = false;
	int owner = -1; //-1 means it's on the table
	int index;
	
	
	Chopstick(int i) {
		index = i;
	}
	
	void acquire(int myowner)
	{
		if(owner == -1)
		{
			acquired = true;
			owner = myowner;
		}
	}
	
	void release() {
		acquired = false;
		owner = -1;
	}
	
	int getIndex() {
		return index;
	}
}
