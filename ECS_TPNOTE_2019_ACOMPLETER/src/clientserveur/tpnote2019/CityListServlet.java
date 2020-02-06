package clientserveur.tpnote2019;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//La classe du contrôleur

@WebServlet("/CityListServlet")
public class CityListServlet extends HttpServlet {

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
			/* 1.5 À COMPLÉTER
			 * Le servlet doit
			 * - récupérer dans la session une instance du modèle PlaneTicketDB ou le créer s'il n'existe pas.
			 * - récupérer le paramètre de requête s
			 * - appeler la méthode searchCity du modèle sur s pour trouve la liste des villes
			 * - stocker la liste des villes dans un attribut de requête nommé "city_list"
			 * - stocker la chaîne s dans un attribut de requête nommé "s"
			 * - effectuer une redirection interne (avec un RequestDispatcher) vers "WEB-INF/jsp/city_list.jsp"
			 */
			
			PlaneTicketDB db = (PlaneTicketDB) request.getSession().getAttribute("db");
			if (db == null ) 
			{
				db = new PlaneTicketDB();request.getSession().setAttribute("db", db);
			}
			// ...
			
			String s = request.getParameter("s");
			
			request.setAttribute("city_list", db.searchCity(s));
			request.setAttribute("s", s);
			
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/city_list.jsp");
			rd.forward(request,  response);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

}
