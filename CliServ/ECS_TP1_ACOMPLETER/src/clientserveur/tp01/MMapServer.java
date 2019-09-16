package clientserveur.tp01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.FileLock;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Timer;
import java.util.TimerTask;



public class MMapServer {
	public final static String MAP_FILE = "/tmp/tp01.map";
	public final static int MAX_NUM = 4;

	public MMapServer() {
	}

	private void initMapFile() throws IOException {
	
		FileChannel file = FileChannel.open(Paths.get(MAP_FILE), 
						StandardOpenOption.READ,
						StandardOpenOption.WRITE,
						StandardOpenOption.CREATE,
						StandardOpenOption.TRUNCATE_EXISTING
						);
	
		FileLock lock = file.tryLock(0L, MAX_NUM*4L, false);
		if (lock == null) {
			throw new IOException ("Pas possible de verrouiller le fichier");
		};
		
		//section critique
		MappedByteBuffer buff = file.map(MapMode.READ_WRITE, 0,MAX_NUM*4L );
		IntBuffer ibuff = buff.asIntBuffer();
		for (int i = 0; i<MAX_NUM; i++)
		{
			ibuff.put(i,0);
		}
		lock.release();
		file.close();
	}

	private MappedByteBuffer openMapFile() throws IOException {
		FileChannel file = FileChannel.open(Paths.get(MAP_FILE), 
				StandardOpenOption.READ);
		MappedByteBuffer buff = file.map(MapMode.READ_ONLY, 0L, MAX_NUM*4L);
		file.close();
		return buff;
	}

	private Timer monitor(IntBuffer b) throws IOException {

		/*
		 * Création d'une classe anonyme qui implémente/étend
		 * TimerTask. Cela permet d'encapsuler le code et de
		 * ne pas le déplacer artificiellement dans un autre
		 * fichier ou ailleurs dans ce fichier.
		 */
		
		TimerTask task = new TimerTask() {
			private IntBuffer ibuff = b;
			private long origin = java.lang.System.currentTimeMillis();
			public void run() {
			 long time = java.lang.System.currentTimeMillis() - origin;

				for (int i = 0; i<MAX_NUM; i++)
				{
					System.out.print(i + ":" + ibuff.get(i) + " ");
				}
				System.out.println("("+(time/1000) + "s)");

			}
		};

		Timer t = new Timer();
		t.schedule(task, 0, 5000); // affiche toutes les 5s
		return t;
	}

	public void mainLoop() throws IOException {
		Timer timer = null;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("Choisir une action :\n" + "- [i]nitialiser le fichier de map\n"
					+ "- [c]ommencer le monitoring\n" + "- [t]erminer le monitoring\n" + "- [q]uiter\n");
			switch (input.readLine()) {

			case "i":
				initMapFile();
				break;
			case "c":
				MappedByteBuffer buff = openMapFile();
				timer = monitor(buff.asIntBuffer());
				break;

			case "t":
				if (timer != null)
					timer.cancel();
				break;
			case "q":
				if (timer != null)
					timer.cancel();
				return;
			default:
				System.out.println("Commande invalide\n");
			}

		}

	}

	public static void main(String[] args) {
		try {

				MMapServer server = new MMapServer();
				server.mainLoop();

		} catch (Exception e) {
			System.err.println("Erreur :\n");
			e.printStackTrace(System.err);
		}
	}

}
