package clientserveur.tp06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Driver {

	private IMovieDB db;

	public Driver(IMovieDB db) {
		this.db = db;
	}

	public void display(List<Movie> l) {
		System.out.println("Résultat :");
		l.stream().forEach(m -> System.out.println(m));
	}

	private String readString(BufferedReader in, String msg) throws IOException {
		System.out.println(msg);
		return in.readLine();
	}

	private int readInt(BufferedReader in, String msg) throws NumberFormatException, IOException {
		System.out.println(msg);
		return Integer.parseInt(in.readLine());

	}

	private int[] readInterval(BufferedReader in) throws NumberFormatException, IOException {
		int[] output = new int[2];
		output[0] = readInt(in, "Premier entier > ");
		System.out.println("Deuxième entier > ");
		output[1] = readInt(in, "Deuxième entier > ");
		return output;
	}

	public void mainLoop() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int numbers[];
		String s;
		while (true) {
			try {

				System.out.println("[n]ouveau film" + "\t[S]upprimer un film" + "\t[i]mporter un fichier"
						+ "\t[c]harger la base" + "\t[s]auver la base" + "\t[q]uitter");
				System.out.println(
						"rechercher par:\n" + "[t]itre" + "\t[C]lassement" + "\t[d]urée" + "\t[a]nnée" + "\t[T]ous");
				s = readString(in, "Choisir une action > ");

				if (s == null)
					s = "q";

				switch (s) {
				case "c":
					db.load();
					break;
				case "s":
					db.save();
					break;
				case "n":
					s = readString(in, "Entrer les caractéristiques d'un film > ");					
					String [] fields = s.split(";");
						if (fields.length != 4) throw new IOException ();						
						Movie m = new Movie (fields[0],
										     Integer.parseInt(fields[1]),
										     Integer.parseInt(fields[2]),
										     Integer.parseInt(fields[3]));						
						db.add(m);
					break;
				case "S":
					s = readString(in, "Entrer le nom du film à supprimer > ");
					db.remove((db.queryTitle(s)).get(0).getId());
					break;
				case "f":
					s = readString(in, "Entrer l'id du film recherché > ");
					db.get(Integer.getInteger(s)).toString();
					break;
				case "i":
					s = readString(in, "Entrer un nom de fichier > ");
					String path = Driver.class.getResource(s).getPath();
					if (path == null)
						System.out.println("Nom de fichier invalide");
					else
						db.importFile(path);
					break;
				case "t":
					s = readString(in, "Entrer une chaîne de caractères > ");
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

			} catch (IOException e) {
				System.out.println("Une erreur d'entrée/sortie est survenue : " + e);
			} catch (ClassNotFoundException e) {
				System.out.println("Erreur de chargement d'objet : " + e);
			}

		}
	}

	public static void main(String[] args) {

		try {
			IMovieDB db = new MovieDB();
			Driver driver = new Driver(db);
			driver.mainLoop();
		} catch (Exception e) {
			System.err.println("Driver exception:");
			e.printStackTrace();
		}
	}

}
