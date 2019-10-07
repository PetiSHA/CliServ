package clientserveur.tp04;

import java.util.Map;
import java.util.TreeMap;

/**
 *  Classe associant des noms de comptes (sous forme de chaînes de caractères)
 *  à des sommes (sous forme d'entiers);
 */
public class Bank {
	private Map<String, Integer> bank;

	public Bank() { 
		bank = new TreeMap<>();
	}

	public synchronized Integer getAccountValue(String key)
	{
		/*
		 * À COMPLÉTER (ne pas oublier de remplacer ou supprimer return null;
		 */
		
		return bank.get(key);
	}

	public synchronized boolean changeAmount(String account, Integer amount) {

		/*
		 * À COMPLÉTER (ne pas oublier de remplacer ou supprimer return false;
		 */
	Integer x = bank.get(account);
	if(x == null || x+amount < 0)
		return false;
	else
	{
		bank.replace(account, x+amount);
		return true;
	}
}
	
	public synchronized boolean createAccount(String key, Integer amount)
	{

		/*
		 * À COMPLÉTER (ne pas oublier de remplacer ou supprimer return false;
		 */
		if(!bank.containsKey(key) || amount > 0)
		{
			bank.put(key, amount);
			return true;
		}
		return false;
	}

	public synchronized Map<String,Integer> getAll()
	{
		/* NE PAS MODIFIER */
		return new TreeMap<>(bank);
	}
}