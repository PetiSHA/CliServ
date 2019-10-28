package clientserver.tp05;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IMovieDB {

	
	public void add(Movie m);
	
	public void remove(int id);
	
	public Movie get(int id);
	
	//Renvoie la liste de tous les films
	public List<Movie> queryAll();
	
	//Renvoie les films dont l'année est a est telle que
	// yBegin <= a <= yEnd
	public List<Movie> queryYear(int yBegin, int yEnd);
	
	//Renvoie les films dont la durée d est a est telle que
	// dBegin <= d <= dEnd
	public List<Movie> queryDuration(int dBegin, int dEnd);
	
	//Renvoie les films dont le rang r est a est tel que
	// rBegin <= r <= rEnd
	public List<Movie> queryRanking(int rBegin, int rEnd);
	
	//Renvoie les films qui contiennent la chaîne 'pat'
	public List<Movie> queryTitle(String pat);
	public void load() throws FileNotFoundException, IOException, ClassNotFoundException;
	public void save() throws FileNotFoundException, IOException ;
	
	//Ajoute tous les films se trouvant dans file dans la base.
	//Voir le fichier resources/movie.txt pour le format du fichier
	public void importFile(String file) throws IOException;
	
	
}
