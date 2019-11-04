package clientserveur.tp06;

import java.util.concurrent.atomic.AtomicInteger;

public class Movie implements java.io.Serializable {

	/**
	 * Généré automatiquement.
	 */
	private static final long serialVersionUID = 4498145443327907427L;
	
	private static final AtomicInteger currentId = new AtomicInteger();
	
	private final int id;
	private final String title;
	private final int  year;
	private final int  duration;
	private final int  ranking;
	
	///À COMPLÉTER : RAJOUTER UN CONSTRUCTEUR PAR COPIE
	public Movie(Movie m)
	{
		this(m.getTitle(), m.getYear(),m.getDuration(),m.getRanking());
	}
	
	
	public Movie(String title, int year, int duration, int ranking)
	{
		this.id = currentId.getAndIncrement();
		this.title = title;
		this.year = year;
		this.duration = duration;
		this.ranking = ranking;
	}
	
	public int getId() { return id; }
	public String getTitle() { return title; }
	public int getYear() { return year; }
	public int getDuration() { return duration; }
	public int getRanking() { return ranking; }
	
	
	@Override
	public String toString()
	{
		return title + " (" + id + ", " + year + ", " +  duration + ", "+ ranking + ")";
	}
	
}
