package clientserveur.tp02;

import java.io.IOException;

public class UDPEchoClientFlood extends UDPEchoClient {
	private static int NUM_MESSAGES = 300;

	
	@Override
	public void mainLoop () throws IOException
	{
	
		//A COMPLETER 3.2
		for (int  i = 0; i <= NUM_MESSAGES; i++)
		{
			this.send("" +  i);
		}
		
	}

	
	public static void main(String[] args) {
		try
		{
			UDPEchoClient s = new UDPEchoClientFlood();
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
