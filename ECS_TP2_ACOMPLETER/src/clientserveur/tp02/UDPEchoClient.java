package clientserveur.tp02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPEchoClient {

	private DatagramSocket clientSocket;
	
	public UDPEchoClient() {}

	public void start(String server, int port) throws SocketException, UnknownHostException
	{
		//A COMPLETER 2.2.1
		//INITIALISE UNE CONNEXION UDP VERS server SUR LE PORT port.
		clientSocket = new DatagramSocket(null);
		clientSocket.connect(InetAddress.getByName(server), port);
	}
	
	public void stop()
	{
	  if (clientSocket != null && clientSocket.isConnected())
	  { 
		  clientSocket.disconnect();
	  }
	  clientSocket.close();
	  clientSocket = null;
	}
	
	protected void send(String message) throws IOException
	{
		//A COMPLETER 2.2.2 puis 2.4.4
		byte[] buff = message.getBytes();
		byte[] temp;
		for (int i = 0; i< buff.length; i+=UDPEchoServer.PACKET_LENGTH)
			{
				DatagramPacket p = new DatagramPacket(buff,i,Math.min(buff.length-i,UDPEchoServer.PACKET_LENGTH));
				clientSocket.send(p);			}
	}
	
	
	public void mainLoop() throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.println("Entrer un message pour le serveur 'ctrl-] <enter>' pour quitter:");
			String message = in.readLine();
			if (message.length() > 0 && message.charAt(0) == '\u001d')
			{
				System.out.println("Terminaison du client");
				return;
			}
			this.send(message);
			
		}
		
	}
	
	public static void main(String [] args)
	{
		try
		{
			UDPEchoClient s = new UDPEchoClient();
			s.start("localhost", 12345);
			s.mainLoop();
			s.stop();
		} catch (Exception e)
		{
			System.err.println("Erreur du client, abandon : ");
			e.printStackTrace();
			return;
		}
	}
	
}
