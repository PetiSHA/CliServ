package clientserveur.tpnote2019;

import java.util.Vector;

/**
 * Classe permettant de stocker des billets d'avions. On associe à la ville d'origine
 * la liste des destination.
 */

public class PlaneTicket {
	
	private final String orig;
	private final Vector<Dest> dests;
	
	public PlaneTicket(String orig, Vector<Dest> dests) {
		this.orig = orig;
		this.dests = dests;
	}

	public String getOrig() {return orig;}
	public Vector<Dest> getDests(){return dests;}
	
}
