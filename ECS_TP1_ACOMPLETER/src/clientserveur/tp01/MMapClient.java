package clientserveur.tp01;

import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MMapClient {
	private final int number;
	private static int MAX_COUNT = 10000;
	

	public MMapClient(int number) {
		this.number = Math.min(MMapServer.MAX_NUM, Math.max(number, 0));
	}

	public void mainLoop() throws IOException {

		System.out.println("Écriture dans l'emplacement " + this.number);

	
			FileChannel file = FileChannel.open(Paths.get(MMapServer.MAP_FILE), 
					StandardOpenOption.READ,
					StandardOpenOption.WRITE
					);
			FileLock lock = file.lock(number*4, 4, false);
			if (lock == null) {
			throw new IOException ("Pas possible de verrouiller le fichier");
			};
			
			MappedByteBuffer buff = file.map(MapMode.READ_WRITE, number*4, 4);
			IntBuffer ibuff = buff.asIntBuffer();
		for (int i = 0 ; i < MAX_COUNT; i++)
		{
			int n = ibuff.get(0);
			n = n+1;
			ibuff.put(0, n);
					
			
		}	
		lock.release();
		file.close();
			

	}

	public static void main(String[] args) {
		try {
			MMapClient c = new MMapClient(Integer.parseInt(args[0]));
			c.mainLoop();
		} catch (Exception e) {
			System.err.println("Erreur :\n");
			e.printStackTrace(System.err);
		}
	}
}
