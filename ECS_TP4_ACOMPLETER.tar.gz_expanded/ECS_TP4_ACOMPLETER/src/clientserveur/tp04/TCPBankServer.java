package clientserveur.tp04;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

class InvalidCommandException extends Exception {

	private static final long serialVersionUID = 1L;
	public InvalidCommandException (String msg) { super (msg); }

}

/**
 *  Classe permettant au serveur de gérer une connexion avec un client
 *  Un objet de cette classe est créé pour chaque client qui se connecte.
 *  Cette classe hérite de la classe Thread, donc sa méthode {@link #run()}
 *  s'exécute en parallèle du reste du programme.
 */
class TCPBankConnection extends Thread {
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

	Bank bank;
	/**
	 * Constructeur de la classe.
	 * @param s : une socket obtenu via un appel à  {@link ServerSocket#accept()} dans le
	 * 			  thread principal.
	 * @param a : un stock partagé par tous les threads et obtenu dans le thread principal
	 * @throws IOException : levée si une erreur survient lors de la récupération des
	 * 						 flux d'entrée ou de sortie associée à la socket.
	 */
	TCPBankConnection(Socket s, Bank a) throws IOException {

		/*
		 * À COMPLÉTER : initialiser les champs toClient, bank, out et in;
		 */
		toClient = s;
		bank = a;
		out = toClient.getOutputStream();
		in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
		
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
			// En attente de lecture sur le flux d'entrée
			msg = in.readLine();

			// On vérifie que l'a obtenu le message CONNECT depuis le client
			if (!msg.equals("CONNECT"))
				throw new IOException();

			// On répond comme le demande le protocole.
			sendMsg("CONNECT OK\n");

			while (true) {
				msg = in.readLine();
				if (msg == null) {
					// Socket fermée côté client, on sort de la boucle
					break;
				} else {
					try {
						System.err.println(msg);
						String[] cmd = msg.split(":");

						switch (cmd[0]) {

						case "CREATE":
							/* À COMPLÉTER :
							 * contrôler les valeurs de cmd[1] et cmd[2], puis créer le compte.
							 * En cas de commande invalide, faire :
							 *    throw new InvalidCommandException(""); 
							 * ce dernier étant rattrappé en fin de boucle.
							 */
							if (cmd[1].contains(":") || cmd[1].contains("\n") || Integer.parseInt(cmd[2]) < 0)
								
							break;
						case "CHANGE":
							/* À COMPLÉTER :
							 * contrôler les valeurs de cmd[1] et cmd[2], puis changer la valeur sur le compte.
							 * En cas de commande invalide, faire :
							 *    throw new InvalidCommandException(""); 
							 * ce dernier étant rattrappé en fin de boucle.
							 */

							break;
						case "GET":
							/* À COMPLÉTER :
							 * contrôler les valeurs de cmd[1], puis renvoyer le montant du compte.
							 * En cas de commande invalide, faire :
							 *    throw new InvalidCommandException(""); 
							 * ce dernier étant rattrappé en fin de boucle.
							 */
							break;
						case "AUDIT":
							/* À COMPLÉTER :
							 * Renvoyer au client la liste des compte au format demandé.
							 * En cas de commande invalide, faire :
							 *    throw new InvalidCommandException(""); 
							 * ce dernier étant rattrappé en fin de boucle.
							 */

							break;
						case "DISCONNECT":
							/* À COMPLÉTER :
							 * Renvoyer au client BYE\n, puis fermer la socket.
							 * En cas de commande invalide, faire :
							 *    throw new InvalidCommandException(""); 
							 * ce dernier étant rattrappé en fin de boucle.
							 */

														
							return; //Attention ici return, pas break, pour quitter la méthode.

						default: //Si la commande n'est pas reconnue
							throw new InvalidCommandException("");
						}
					} catch (InvalidCommandException e) {
						sendMsg("INVALID:" + msg + "\n");
					}
				}
			}
			// Fin while(true)
			// On est sorti de la boucle, on ferme la connection de notre coté.
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
public class TCPBankServer {
	/**
	 * Socket serveur sur lequel on attend les connexions des clients
	 */
	ServerSocket serverSocket;

	/**
	 * Tableau d'objets {@link TCPEchoConnection}, utilisé pour terminer toutes
	 * les connexions lors de la fermeture du serveur.
	 */
	List<TCPBankConnection> clients;

	Bank bank;
	/**
	 * Constructeur de la classe.
	 */
	public TCPBankServer() {
		clients = new ArrayList<>();
		bank = new Bank();
	};

	/**
	 * Méthode de démarage du serveur.
	 * @param port : le port TCP sur lequel le serveur écoute
	 * @throws IOException : levée par {@link ServerSocket#bind(java.net.SocketAddress)} si
	 * 						 le port est déjà occupé par un autre programme.
	 */
	public void start(int port) throws IOException {
		if (serverSocket == null) {
			serverSocket = new ServerSocket();
		}
		
		/* À COMPLÉTER : mettre la serverSocket en écoute sur le port donné en argument,
		 * et définir un timeout de 1000ms sur la socket.
		 */

		serverSocket.bind(new InetSocketAddress(port));
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
		for (TCPBankConnection client : clients) {
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
					Socket s = serverSocket.accept();
					TCPBankConnection c = new TCPBankConnection(s, bank);
					clients.add(c);
					c.start();
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
			TCPBankServer s = new TCPBankServer();

			s.start(12345);
			s.mainLoop();
			s.stop();
		} catch (IOException e) {

			System.err.println("Erreur lors du démarage du serveur");
			e.printStackTrace();
		}
	}

}
