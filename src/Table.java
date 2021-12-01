public class Table {
	public static final int NUM_PHILO = 5;
	public Chopstick[] chopsticks = new Chopstick[NUM_PHILO];
	public static final DisplayPanel mywindow = new DisplayPanel();
	
	public static void main(String[] args) {
		new Table();
	}
	
	Philosopher[] philos = new Philosopher[NUM_PHILO];
	
	Table() {
		startTable();
	}
	
	/**
	 *  Initialize the table and create our chopsticks and philosophers. If a philosopher is even,
	 *  then he is to take the chopstick of his own index and his index plus one. If odd, then 
	 *  these are flipped. This is so we don't just grab chopsticks in a circle.
	 */
	
	public void startTable() {
		//Start Chopsticks
		for(int i = 0; i < chopsticks.length; i ++)
		{
			chopsticks[i] = new Chopstick(i);
		}
		//Start Philosophers
		for (int i = 0; i < NUM_PHILO; i++) {
			if(i==NUM_PHILO-1) {
				Philosopher p = new Philosopher(i, chopsticks[0], chopsticks[i],0,i);
				philos[i] = p;
			}
			else if(i%2==0) { //even philosopher
				Philosopher p = new Philosopher(i, chopsticks[i], chopsticks[i+1], i, i+1);
				philos[i] = p;
			}
			else { //odd philosopher
				Philosopher p = new Philosopher(i, chopsticks[i+1], chopsticks[i], i+1, i);
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
	
	/**
	 * Here we check if the chopstick is on the table (owner = -1), which means it is able to be
	 * picked up. If so, we set the owner and acquire it.
	 * @param myowner denotes the philosopher grabbing the chopstick.
	 */
	
	void acquire(int myowner)
	{
		if(owner == -1)
		{
			acquired = true;
			owner = myowner;
		}
	}
	
	/**
	 *  We return the chopstick to the table to be grabbed by someone else.
	 */
	
	void release() {
		acquired = false;
		owner = -1;
	}
	
	/**
	 * @return index of chopstick
	 */
	
	int getIndex() {
		return index;
	}
}
