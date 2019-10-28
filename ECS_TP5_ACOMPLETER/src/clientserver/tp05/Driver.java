package clientserver.tp05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Driver {
	
	private IMovieDB db;
	
	public Driver(IMovieDB db)
	{
		this.db = db;
	}

	
	public void display(List<Movie> l)
	{
		System.out.println("Résultat :");
		l.stream().forEach(m -> System.out.println(m));
	}
	
	private int[] readInterval(BufferedReader in) throws NumberFormatException, IOException
	{
		int[] output = new int[2];
		System.out.println("Premier entier > ");
		output[0] = Integer.parseInt(in.readLine());
		System.out.println("Deuxième entier > ");
		output[1] = Integer.parseInt(in.readLine());
		return output;
	}
	
	public void mainLoop()
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int numbers[];
		String s;
		while (true)
		{
			try {
			System.out.println("Entrer une commande > ");
			System.out.println("[n]ouveau film    [S]upprimer un film   [i]mporter un fichier"
							 + "    [c]harger la base    [s]auver la base     [q]uitter");
			System.out.println("rechercher par:   [t]itre   [C]lassement   [d]urée  [a]nnée [T]ous [I]dentifiant");
			s = in.readLine();
			if (s == null) s = "q";
			switch (s)
			{
			case "n":
			case "I":
			case "S":
				System.err.println("Fonctionalité non implémentée");
				break;
			case "c":
				db.load();
				break;
			case "s":
				db.save();
				break;
			case "i":
				System.out.println("Entrer un nom de fichier > ");
				s = in.readLine();
				db.importFile(Driver.class.getResource(s).getPath());
				break;
			case "t":
				System.out.println("Entrer une chaîne de caractères > ");
				s = in.readLine();
				display(db.queryTitle(s));
				break;
			case "T":
				display(db.queryAll());
				break;
			case "a":
				System.out.println("Recherche par année");
				numbers = readInterval(in);
				display(db.queryYear(numbers[0], numbers[1]));
				break;
			case "C":
				System.out.println("Recherche par classement");
				numbers = readInterval(in);
				display(db.queryRanking(numbers[0], numbers[1]));
				break;
			case "d":
				System.out.println("Recherche par durée");
				numbers = readInterval(in);
				display(db.queryDuration(numbers[0], numbers[1]));
				break;
			case "q":
				return;
			default:
				System.out.println("Commande invalide");			
			}
				
				
			} catch (Exception e) {
				System.out.println ("Une erreur est survenue : " + e);
			}
				
				
				
			}
	}
	
	
	public static void main(String [] args)
	{
		MovieDB m = new MovieDB();
		Driver d = new Driver(m);
		d.mainLoop();
	}
	
	
}
