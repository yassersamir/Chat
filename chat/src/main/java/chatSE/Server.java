package chatSE;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

	static Set<ClientHandler> ar = new HashSet<>();
	static int i = 1;

	public static void main(String[] args) throws IOException {

		ManageFile mf = new ManageFile();
		mf.writeUsers();
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(1234);
		Socket socket;
		// getting client request
		while (true) {
			socket = server.accept();

			// obtain input and output streams
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			// Create a new handler object for handling this request.

			ClientHandler mtch = new ClientHandler(socket ,dis, "client"+i, dos);
			Thread t = new Thread(mtch);
			System.out.println("new client adding ...");
			ar.add(mtch);
			t.start();
			
			i++;
		}
	}
}

