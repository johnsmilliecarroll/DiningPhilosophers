public class Table {
	public boolean ch1 = false;
	public boolean ch2 = false;
	public boolean ch3 = false;
	public boolean ch4 = false;
	public boolean ch5 = false;
	private static final int NUM_PHILO = 5;
	
	
	public static void main(String[] args) {
		
	}
	
	
	Philosopher[] philos = new Philosopher[NUM_PHILO];
	
	Table(int threadNum) {
		

	}
	
	public void startTable() {
		for (int i = 0; i < NUM_PHILO; i++) {
			Philosopher p = new Philosopher(i);
			philos[i] = p; // start philosopher threads
		}
		//Chopsticks are true, meaning they are on the table.
		ch1 = true;
		ch2 = true;
		ch3 = true;
		ch4 = true;
		ch5 = true;
	}
	
	

}
