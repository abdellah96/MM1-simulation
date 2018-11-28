public class Main {
	
	public static double lambda;
	public static double mu;
	public static double simduree;
	public static int debogage;
	
    public static void main(String[] args) {
    	
    	
    	lambda=Double.parseDouble(args[0]);
		mu=Double.parseDouble(args[1]);
		simduree=Double.parseDouble(args[2]);
		debogage=Integer.parseInt(args[3]);
		
		
		Utile Simulation = new Stats(lambda,mu);
		Simulation.simuler(simduree,debogage);
    	
		
		/*int x=1;
    	while(x<10000) {
			//on fixe les paremtres (lambda,mu) en (5,6)
    		Utile Simulation = new Stats(5,6);
			//on simule pour durée=100000 sans debogage
			Simulation.simuler(1000*x,0);
			//on ecrit les résultats dans un fichier
			//on incréments par 1
			x=x+1000;
		}
    	*/
		
		}
}
