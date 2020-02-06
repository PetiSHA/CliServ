package clientserveur.tpnote2019;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

//Classe représentant le modèle

public class PlaneTicketDB {

	private Connection conn;

	public PlaneTicketDB () throws ClassNotFoundException, SQLException
	{
		/*
		 * NE PAS MODIFIER
		 */
		//Chargement du driver postgresql et connexion à la base
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://tp-postgres:5432/knguye10_a",
											"knguye10_a","knguye10_a");

	}

	@Override
	protected void finalize()
	{

		/*
		 * NE PAS MODIFIER
		 */
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {}
	}

	/**
	 * Renvoie la liste des villes dont le nom contient la chaîne s
	 * @param s
	 * @return
	 */

	public Vector<City> searchCity(String s) throws SQLException {
		Vector<City> res = new Vector<>();
		Statement st = conn.createStatement();
		ResultSet r = st.executeQuery("SELECT CID, NAME FROM CITY WHERE NAME LIKE '%"+s+"%' ORDER BY NAME");
		
		while(r.next())
		{
			res.add(new City(r.getInt("CID"),r.getString("NAME")));
		}
		if (res.isEmpty()) {return null;}
		return res;
		
                /* À décommenter et utiliser UNIQUEMENT pour tester la suite sans faire cette question : */
                /*
                  res.add(new City(3441, "Antananarivo"));
                  res.add(new City(27907, "Paramaribo"));
                  res.add(new City(28023, "Paris"));
                  res.add(new City(279, "San Marino"));

                */

	}

	public PlaneTicket getTicketFromCity(int cid) throws SQLException {
		Statement st = conn.createStatement();
		Statement stmt = conn.createStatement();
		ResultSet r1 = st.executeQuery("SELECT NAME FROM CITY WHERE CID = '"+cid+"'");
		
		if(!r1.next()) {return null;}
		
		ResultSet r2 =  stmt.executeQuery("SELECT ORIG, DEST, PRICE, QTITY, CITY.NAME FROM PLANE_TICKET, CITY WHERE ORIG = "+cid+" AND CITY.CID = DEST ORDER BY PRICE");
		Vector<Dest> res = new Vector<>();
		while (r1.next())
		{
			res.add(new Dest(r2.getString("CITY.NAME"), r2.getInt("QTITY"), r2.getInt("PRICE")));
		}
		if(res.isEmpty()) {return null;}
		
		String ville = " ";
		
		while(r1.next()) {ville = r1.getString("NAME");}
		
		PlaneTicket pt = new PlaneTicket(ville, res);
		return pt;
		
	}


	public static void main(String[] args) {
		try {
			PlaneTicketDB db = new PlaneTicketDB();

			System.out.println("Villes dont le nom contient 'ari'");
			Vector<City> c = db.searchCity("ari");
			System.out.println(c.size() + " résultats trouvés");
			System.out.println("Billets depuis Paris");
			PlaneTicket p = db.getTicketFromCity(28023);
			System.out.println( p == null ? "pas de résultat" : "un résultat");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
