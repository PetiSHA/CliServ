package clientserveur.tp06;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class MovieDB implements IMovieDB {

	private HashMap <Integer, Movie> base;
	private static final String BASE_FILE = "/tmp/movie.tmp";
	
	public MovieDB ()
	{
		
		base = new HashMap<Integer, Movie>();
	}
	
	public void add(Movie m)
	{
		
		///À COMPLÉTER : MODIFIER POUR
		/// UTILISER LE CONSTRUCTEUR PAR COPIE	
		List<Movie> titres = queryTitle(m.getTitle());
		if (titres.size() > 0) return;
		Movie copie = new Movie(m);
		base.put(copie.getId(), copie);
	}
	
	public void remove(int id)
	{
		base.remove(new Integer(id));
	}
	
	public Movie get(int id)
	{
		return base.get(new Integer(id));
	}
	
	public List<Movie> queryAll() {
		return select (e -> true);
	}
	
	public List<Movie> select(Predicate<Map.Entry<Integer, Movie>> f)
	{
		return base.entrySet()
				.stream().filter(f)
				.map(e -> e.getValue())				
				.collect(Collectors.toList());
	}
	
	public List<Movie> queryYear(int yBegin, int yEnd)
	{
		
		return select(e -> e.getValue().getYear() <= yEnd &&
						   e.getValue().getYear() >= yBegin);
		
	}

	public List<Movie> queryDuration(int dBegin, int dEnd)
	{
		
		return select(e -> e.getValue().getDuration() <= dEnd &&
						   e.getValue().getDuration() >= dBegin);
		
	}
	
	public List<Movie> queryRanking(int rBegin, int rEnd)
	{
		
		return select(e -> e.getValue().getRanking() <= rEnd &&
						   e.getValue().getRanking() >= rBegin);
		
	}
	
	public List<Movie> queryTitle(String pat)
	{
		
		return select(e -> e.getValue().getTitle().contains(pat));	
		
	}
	
	public void load() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(BASE_FILE));

		@SuppressWarnings("unchecked")
		HashMap<Integer,Movie> m = (HashMap<Integer,Movie>) in.readObject();
		
		/// REMPLACER LA LIGNE CI-DESSOUS POUR AJOUTER LES FILMS DE LA
		/// BASE CHARGÉE DANS LA BASE EXISTANTE
		for (Map.Entry<Integer, Movie> e : m.entrySet()) 
		{
			Integer id = e.getKey();
			Movie movie = e.getValue();
			if(base.containsKey(id) && queryTitle(movie.getTitle()).size()<= 0)
				add(movie);
		}

		in.close();
		
	}
	
	public void save() throws FileNotFoundException, IOException 
	{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BASE_FILE));
		out.writeObject(base);
		out.close();
	}
	
	public void importFile(String file) throws IOException
	{
		BufferedReader buff = new BufferedReader(new FileReader(file));
		String s;
		try {
		while ((s = buff.readLine()) != null)
		{
			String [] fields = s.split(";");
			if (fields.length != 4) throw new IOException ();
			Movie m = new Movie (fields[0],
							     Integer.parseInt(fields[1]),
							     Integer.parseInt(fields[2]),
							     Integer.parseInt(fields[3]));			
			base.put(m.getId(), m);
			
		}
		} finally { buff.close(); };
	}
	
	public static void main(String[] args)
	{
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
			try {
			String name = "MyMovieDB";
			IMovieDB adder = new MovieDB();
			IMovieDB stub = (IMovieDB) UnicastRemoteObject.exportObject(adder, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(name, stub);
			System.out.println("Ok");
			} catch (Exception e) { };
	}
}
