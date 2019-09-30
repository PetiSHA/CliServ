package clientserveur.tp03;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.ArrayList;

/**
 *  Classe permettant au serveur de gérer une connexion avec un client
 *  Un objet de cette classe est créé pour chaque client qui se connecte.
 *  Cette classe hérite de la classe Thread, donc sa méthode {@link #run()}
 *  s'exécute en parallèle du reste du programme.
 */
class TCPEchoConnection extends Thread {
	/**
	 * Flux de sortie obtenu à partir de la socket.
	 */
	OutputStream out;
	/**
	 * Flux d'entrée obtenu à partir de la socket.
	 */
	BufferedReader in;
	/**
	 * Socket reliant le serveur à un client
	 */
	Socket toClient;

	/**
	 * Constructeur de la classe.
	 * @param s : une socket obtenu via un appel à  {@link ServerSocket#accept()} dans le
	 * 			  thread principal.
	 * 
	 * @throws IOException : levée si une erreur survient lors de la récupération des
	 * 						 flux d'entrée ou de sortie associée à la socket.
	 */
	TCPEchoConnection(Socket s) throws IOException {
		toClient = s;
		out = toClient.getOutputStream();
		in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
		System.out.println("Création d'un nouveau thread pour le client "
							+ s.getInetAddress()
							+ ", port "
							+ s.getPort());
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
	 * Méthode contenant le code principal du thread, c'est à dire le dialogue entre le serveur
	 * et un client.
	 */
	@Override
	public void run() {
		try {
			String msg;
			//En attente de lecture sur le flux d'entrée
			msg = in.readLine();
			
			//COMPLETER CI-DESSOUS, TEST QUE LE MESSAGE EST BIEN "CONNECT"
			//ET REPONDRE "CONNECT OK\n"
			if(msg == "CONNECT\n")
			{
				sendMsg("CONNECT OK\n");
			}
			if (msg == "DISCONNECT\n")
			{
				sendMsg("BYE\n");				
			}
			
			while (true) {
				//Lecture du message envoyé par le client
				msg = in.readLine();
				//COMPLETER CI-DESSOUS POUR IMPLEMENTER LE PROTOCOLE
				if()
				{
					sendMsg("RECEIVED" + msg.length());
				}
				//retirer 'break;' ci-dessous :
				break;

				
			}//Fin while(true)
			//On est sorti de la boucle, on ferme la connection de notre coté.
			toClient.close(); 
			
		} catch (IOException e) {
		}
		System.err.println("Client déconnecté");
	}

}

/**
 * Classe principale implémentant le serveur.
 * 
 */
public class TCPEchoServer {
	/**
	 * Socket serveur sur lequel on attend les connexions des clients
	 */
	ServerSocket serverSocket;
	
	/**
	 * Tableau d'objets {@link TCPEchoConnection}, utilisé pour terminer toutes
	 * les connexions lors de la fermeture du serveur.
	 */
	List<TCPEchoConnection> clients;

	
	/**
	 * Constructeur de la classe.
	 */
	public TCPEchoServer() {
		clients = new ArrayList<>();
	};

	/**
	 * Méthode de démarage du serveur.
	 * @param port : le port TCP sur lequel le serveur écoute
	 * @throws IOException : levée par {@link ServerSocket#bind(java.net.SocketAddress)} si
	 * 						 le port est déjà occupé par un autre programme.
	 */
	public void start(int port) throws IOException {
		if (serverSocket == null) {
			
			serverSocket = new ServerSocket(); //REMPLACER null PAR L'EXPRESSION CORRECTE
		}
		
		
		//COMPLETER CI-DESSOUS POUR METTRE LA SOCKET EN MODE ECOUTE.
		serverSocket.bind(new InetSocketAddress(port));
		//COMPLETER CI-DESSOUS POUR METTRE LE TIMEOUT D'ATTENTE SUR
		//.accept() À 1000ms
		serverSocket.setSoTimeout(1000);
		
		
		
		System.out.println("Serveur démarré sur le port TCP " + port);
	}

	/**
	 * Méthode d'arrêt du serveur. Termine tous les threads puis ferme
	 * la socket serveur.
	 */
	public void stop() {
		System.out.println("Arrêt du serveur");
		// Pour arrêter le serveur, on ferme toutes les connections
		for (TCPEchoConnection client : clients) {
			try {
				//On ferme la socket de la connexion, ce qui permet au
				//thread de terminer (la lecture blocante termine)
				client.toClient.shutdownInput();
				client.toClient.shutdownOutput();
				client.toClient.close();
				//On donne la main au thread pour qu'il s'exécute et termine.
				client.join();
			} catch (IOException e) {
			} catch (InterruptedException e) {
			}
			
			try {
				//Une fois tous les threads terminés, on ferme la socket.
				serverSocket.close();
			} catch (IOException e) {
			}
			serverSocket = null;
		}

	}
	
	/**
	 * Méthode principale du serveur qui attends les connexions des clients et crée les threads
	 * de traitement quand elles arrivent.
	 */
	public void mainLoop() {
		
		System.out.println("Presser sur une touche pour terminer le serveur");
		while (true) {
			try {
				try {
					
					//COMPLETER CI-DESSOUS POUR ATTENDRE UNE CONNEXION
					Socket cli = serverSocket.accept();
					
					//COMPLETER CI-DESSOUS POUR CREER UNE NOUVEL OBJET
					//DE TRAITEMENT DE CONNEXION, L'AJOUTER À clients
					//ET LE DÉMARRER
					TCPEchoConnection co = new TCPEchoConnection(cli);
					clients.add(co);
					co.start();
					
					
					//Retirer le code ci-dessous:
					throw new SocketTimeoutException();
				
				
				} catch (SocketTimeoutException e) {
					//Il s'est écoulé 1000ms sans aucun client, on regarde si on doit
					//Arrêtre le serveur.
					if (System.in.available() > 0) return;
				}
			} catch (IOException e) {
				// le serveur ne peut plus lire sur sa socket, on quitte;
				return;
			}

		}
	}

	public static void main(String[] args) {

		try {
			TCPEchoServer s = new TCPEchoServer();

			s.start(12345);
			s.mainLoop();
			s.stop();
		} catch (IOException e) {

			System.err.println("Erreur lors du démarage du serveur");
			e.printStackTrace();
		}
	}

}
