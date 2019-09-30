package clientserveur.tp03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Classe principale du Client.
 *
 */
public class TCPEchoClient {
	/**
	 * Flux de sortie obtenu à partir de la socket.
	 */
	OutputStream out;
	/**
	 * Flux d'entrée obtenu à partir de la socket.
	 */
	BufferedReader in;
	/**
	 * Socket reliant le client au server
	 */
	Socket toServer;

	/**
	 * Constructeur
	 */
	public TCPEchoClient() {
	}

	/**
	 * Méthode initialisant le client.
	 * @param host : le nom ou l'adresse du serveur
	 * @param port : le port TCP sur lequel on désire effectuer la connexion
	 * @throws UnknownHostException : levée si le nom ou l'adresse sont invalides.
	 * @throws IOException : levée si on n'arrive pas à récupérer les flux d'entrée ou de sortie.
	 */
	public void start(String host, int port) throws UnknownHostException, IOException {
		if (toServer == null) {
			toServer = new Socket();//COMPLETER AVEC L'EXPRESSION CORRECTE
		}
		//COMPLETER CI-DESSOUS POUR
		//CONNECTER LA SOCKET AU SERVEUR SUR LE BON PORT
		toServer.connect(new InetSocketAddress(InetAddress.getByName(host),port));
		//ET RECUPERER LES FLUX DE SORTIE ET D'ENTREE DANS LES
		//ATTRIBUTS out ET in DE LA CLASSE.
		out = toServer.getOutputStream();     
		in = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
		
		System.out.println("Connexion au serveur réussie");
		
	}
	
    /**
     * Méthode d'arrêt du client. Elle ferme simplement la socket.
     */
	public void stop() {

		try {
			if (toServer != null && !toServer.isClosed())
				toServer.close();
		} catch (IOException e) {
		}

		toServer = null;

	}
	
	/**
	 * Méthode auxiliaire permettant d'écrire une chaine sur le flux de sortie.
	 * @param msg : la chaîne de caractères à envoyer
	 * @throws IOException  : levée en cas de problème d'écriture.
	 */
	private void sendMsg(String msg) throws IOException
	{
		out.write(msg.getBytes());
		out.flush();
	}
	
	/**
	 * Méthode principale du client qui implémente le protocole.
	 */
	public void mainLoop()
	{
		try {

			//COMPLETER CI-DESSOUS
			//PREMIER MESSAGE DU PROTOCOLE ENVOYER "CONNECT\n" AU SERVEUR
			sendMsg("CONNECT\n");
			
			//On lit la réponse du serveur. Attention readLine() supprime le retour à la ligne.
			String ans = in.readLine();
			System.out.println(" <- Réponse du serveur : '" + ans + "'");

			//COMPLETER CI-DESSOUS
			//TESTER LA REPONSE DU SERVEUR ET TERMINER SI ELLE EST INVALIDE
			if(ans == "BYE\n" || ans != "CONNECT OK\n")
			{
				sendMsg("INVALID" + ans);
				stop();
			}
			//Pour lire l'entrée standard ligne à ligne
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			
			while (true)
			{
				//COMPLETER CI-DESSOUS
				//LIRE UNE LIGNE SUR L'ENTREE STANDARD, L'ENVOYER AU SERVEUR
				//ET AFFICHER LA REPONSE DE CE DERNIER
				//On lit une commande sur l'entrée standard et on l'envoie au serveur
				String send = in.readLine();
				
				
				//COMPLETER CI-DESSOUS
				//SI ON A ENVOYER LA COMMANDE "DISCONECT\n" ALORS QUITTER
			}
			
			
		} catch (IOException e) {
			//Levée lors d'une écriture sur out si le serveur est déconnecté.
		}
		
	}
	
	
	public static void main(String[] args) {

		try {
			TCPEchoClient c = new TCPEchoClient();
			c.start("localhost", 12345);
			c.mainLoop();
			c.stop();			
		} catch (Exception e) {
			System.err.println("Erreur :");
			e.printStackTrace(System.err);
		}
		
	}

}
