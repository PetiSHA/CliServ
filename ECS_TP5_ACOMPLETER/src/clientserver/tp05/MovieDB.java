package clientserver.tp05;

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

public class MovieDB implements IMovieDB {

	private HashMap <Integer, Movie> base;
	private static final String BASE_FILE = "/tmp/movie.tmp";
	
	public MovieDB ()
	{
		
		base = new HashMap<Integer, Movie>();
	}
	
	public void add(Movie m)
	{	
		base.put(m.getId(), m);
	}
	
	public void remove(int id)
	{
		base.remove(id);
	}
	
	public Movie get(int id)
	{
		return base.get(id);
	}
		
	public List<Movie> select(Predicate<Map.Entry<Integer, Movie>> f)
	{
		return base.entrySet() //Renvoie un Set<Map.Entry<Integer, Movie>>
				               //Qui implémente Collection<…>
							   //Sur lequel on peut appeler .stream()
				.stream()      //Renvoie un flux basé sur le Set<…>
				.filter(f)     //Fonction de Map.Entry<Integer,Movie>
				               //         vers Boolean
				               //         qui renvoie vrai si on veut
				               //         garder le film
				.map(e -> e.getValue())
				               //Pour chaque (i, m) gardé, renvoie m
				.collect(Collectors.toList());
								//retransforme tout le flux en
								//List<Movie>
	}
	//permet de sélectionner/filtrer les films selon la condition décrite par la fonction f et de les renvoyer sous forme de liste
	public List<Movie> queryAll() {
		
		return select(m -> true);
	}

	public List<Movie> queryYear(int yBegin, int yEnd)
	{
		return select(m -> ((m.getValue().getYear()) >= yBegin || (m.getValue().getYear()) <= yEnd));
	}

	public List<Movie> queryDuration(int dBegin, int dEnd)
	{
		
		return select(m -> ((m.getValue().getDuration()) >= dBegin || (m.getValue().getDuration()) <= dEnd));
	}
	
	public List<Movie> queryRanking(int rBegin, int rEnd)
	{
		return select(m -> ((m.getValue().getRanking()) >= rBegin || (m.getValue().getRanking()) <= rEnd));	
	}
	
	public List<Movie> queryTitle(String pat)
	{
		return select(m -> m.getValue().getTitle().equals(pat));
	}
	
	
	
	
	public void load() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(BASE_FILE));

		@SuppressWarnings("unchecked")
		HashMap<Integer,Movie> m = (HashMap<Integer,Movie>) in.readObject();
		base =  m;
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
			BufferedReader in = new BufferedReader(new FileReader(file));
			String li;
			try {
			while((li = in.readLine()) != null)
			{	
				String[] part= li.split(";");
				System.out.println(li);
				Movie m = new Movie(part[0],Integer.parseInt(part[1]),Integer.parseInt(part[2]),Integer.parseInt(part[3]));
				add(m);
			}
			}finally {
			in.close();} 		
	}
	
}
