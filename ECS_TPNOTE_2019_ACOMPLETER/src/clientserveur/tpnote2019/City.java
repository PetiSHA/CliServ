package clientserveur.tpnote2019;

/**
 * Class utilitaire permetant d'encapsuler le nom d'une ville et son identifiant.
 * 
 */
public class City {
	
	private final int cid;
	private final String name;
	
	public City(int cid, String name) {
		this.cid = cid;
		this.name = name;
	}	
	

	public int getCid() {return cid;}
	public String getName() {return name;}
}
