package clientserveur.tpnote2019;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//La classe du contrôleur

@WebServlet("/PlaneTicketServlet")
public class PlaneTicketServlet extends HttpServlet {

	private static final long serialVersionUID = 6489069438791307255L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Récupère le paramètre de requête s et calcule toute les villes dont le nom contient s.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			/* 1.8 À COMPLÉTER */

			/*
			 * Le servlet doit
			 * - récupérer le paramètre de requête cid
			 * - récupérer dans la session une instance du modèle PlaneTicketDB ou le créer s'il n'existe pas.
			 * - contertir cid en entier. Si ce n'est pas possible ou que l'on obtient un entier négatif,
			 *   alors effectuer une redirection externe vers index.html
			 * - appeler la méthode getTicketFromCity sur cid pour obtenir la collection des billets
			 * - stocker le billet dans un attribut de requête nommé "planeticket"
			 * - effectuer une redirection interne (avec un RequestDispatcher) vers "WEB-INF/jsp/planeticket_detail.jsp"
			 */
			PlaneTicketDB db = (PlaneTicketDB) request.getSession().getAttribute("db");
			if (db == null) 
			{          
				db = new PlaneTicketDB();
				request.getSession().setAttribute("db", db);
			}
			String cid = request.getParameter("cid");
			System.out.println(cid);
			try { 
					int cid_ = Integer.parseInt(cid);
					if(cid_ < 0) {response.sendRedirect("index.html");}	
					else {
					request.setAttribute("planeticket", db.getTicketFromCity(cid_));
					RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/planeticket_detail.jsp");
					rd.forward(request, response);
					}
			} catch (NumberFormatException e) { response.sendRedirect("index.html");}
			
			
			

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

}
