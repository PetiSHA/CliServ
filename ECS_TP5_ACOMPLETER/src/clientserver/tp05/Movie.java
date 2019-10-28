package clientserver.tp05;
import java.util.concurrent.atomic.AtomicInteger;

public class Movie  {

	private static final AtomicInteger currentId = new AtomicInteger();
    //DÉCOMMENTER 'final' ET INITIALISER CORRECTEMENT DANS LE CONSTRUCTEUR
	private final int id;
	private final String title;
	private final int  year;
	private final int  duration;
	private final int  ranking;
	
	public Movie(String title, int year, int duration, int ranking)
	{
		this.title = title;
		this.year = year;
		this.duration = duration;
		this.ranking = ranking;
		this.id = currentId.incrementAndGet();
	}
	//integer incrémenté: non, accès concurrent= 2 films avec le même id.
	public int getId() { return id; }
	public String getTitle() { return title; }
	public int getYear() { return year; }
	public int getDuration() { return duration; }
	public int getRanking() { return ranking; }
	
	
	@Override
	public String toString()
	{
		return "id: " + id + "\ntitre: "+ title + "\nyear: " + year+ "\nduration: "+duration+"\nranking: "+ranking;
	}
	
}
