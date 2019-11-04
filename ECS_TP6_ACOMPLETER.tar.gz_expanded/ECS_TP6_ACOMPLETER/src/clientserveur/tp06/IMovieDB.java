package clientserveur.tp06;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;



/* Interface de la base de donnée en mémoire. */

public interface IMovieDB extends Remote{

	/* Ajout d'un film à la base */
	public void add(Movie m) throws RemoteException;
	
	/* Suppression d'un film */
	public void remove(int id) throws RemoteException;
	
	/* Récupération d'un film par ID */
	public Movie get(int id) throws RemoteException;
	
	/* Liste de tous les films */
	public List<Movie> queryAll() throws RemoteException;
	
	/* Liste des films pour les années spécifiées */
	public List<Movie> queryYear(int yBegin, int yEnd) throws RemoteException;
	
	/* Liste des films pour les durées spécifiées */
	public List<Movie> queryDuration(int dBegin, int dEnd) throws RemoteException;

	/* Liste des films pour les score spécifiés */
	public List<Movie> queryRanking(int rBegin, int rEnd) throws RemoteException;
	
	/* Liste des films pour les titres spécifiés */
	public List<Movie> queryTitle(String pat) throws RemoteException;
	
	/* Charge une base de donnée stockée sur le disque */
	public void load() throws FileNotFoundException, IOException, ClassNotFoundException, RemoteException;

	/* Sauve une base de donnée sur le disque */
	public void save() throws FileNotFoundException, IOException, RemoteException;	

	/* Importe un fichier texte */
	public void importFile(String file) throws IOException, RemoteException;
	
}
