package clientserveur.tpnote2019;
/**
 * Classe permettant d'encapsuler une destination :
 * contient le nom de la destination, le prix du billet et le nombre de billets restants.
 *
 */
public class Dest {
	private final String name;
	private final int qtity;
	private final int price;
	
	public Dest(String name, int price, int qtity) {
		this.name = name;
		this.qtity = qtity;
		this.price = price;
	}
	
	public String getName() {return name;}
	public int getQtity(){return qtity;}
	public int getPrice() {return price;}

}
