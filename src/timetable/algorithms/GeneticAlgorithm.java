package timetable.algorithms;

public class GeneticAlgorithm {
	
	static int n = 10;
	
	public static void main(String[] args) {
		String[] generatedTimetables = start(n);
		int[] generatedTimetablesFitness = fitness(generatedTimetables);
		newPopulation();
		replace();
		test();
	}
	
	/**Generate random population of n chromosomes*/
	public static String[] start(int n) {
		String[] generatedTimetables = null;
		
		return generatedTimetables;
	}
	
	/**Generates fitness value of chromosomes*/
	public static int[] fitness(String[] boards) {
		int[] generatedTimetablesFitness = null;
		
		return generatedTimetablesFitness;
	}
	
	/**Creates new population*/
	public static void newPopulation() {
		selection();
		crossover();
		mutation();
		accepting();
	}
	
	/**Select two parent chromosomes from population. Higher fitness = higher priority*/
	public static void selection() {
		
	}
	
	/**Cross over parents to form new offspring. If no crossover performed, offspring is exact copy of parent*/
	public static void crossover() {
		
	}
	
	/**Mutate new offspring at each position in chromosome*/
	public static void mutation() {
		
	}
	
	/**Place new offspring in new population*/
	public static void accepting() {
		
	}
	
	/**Use new generated population for a further run of the algorithm*/
	public static void replace() {
		
	}
	
	/**IF end condition satisfied - stop and return best solution. Otherwise, repeat from fitness.*/
	public static void test() {
		
	}
}
