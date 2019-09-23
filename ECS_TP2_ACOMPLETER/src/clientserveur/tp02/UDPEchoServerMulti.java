package clientserveur.tp02;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;
import java.util.ArrayList;

public class UDPEchoServerMulti extends UDPEchoServer {
	private List<Thread> threads;
	
	@Override
	public void start(int port) throws SocketException
	{
		threads = new ArrayList<>();
		super.start(port);
	}
	
	@Override
	public void stop()
	{
		for (Thread t : threads)
		{
			try {
				t.join();
			} catch (InterruptedException e) { }
		}
		super.stop();
	}
	
	private void callParentHandler(DatagramPacket packet)
	{
		super.handlePacket(packet);
		
	}
	
	@Override
	protected void handlePacket(DatagramPacket p)
	{
		final DatagramPacket fpacket = p;

		Thread thread = new Thread () {
			private final DatagramPacket packet = null; //À COMPLÉTER 4.4 (rempalcer null)
			
			public void run() {
				UDPEchoServerMulti.super.handlePacket(packet);
			}
		};
		
		thread.start();
	}
	public static void main(String[] args) {
		try
		{
			UDPEchoServer s = new UDPEchoServerMulti();
			s.start(12345);
			s.mainLoop();
			s.stop();
		} catch (Exception e)
		{
			System.err.println("Erreur du serveur, abandon : ");
			e.printStackTrace();
			return;
		}
	}

}
