package chatSE;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class Server {

	static Set<ClientHandler> ar = new HashSet<>();
	static int i = 1;

	public static void main(String[] args) throws IOException {

		ManageFile mf = new ManageFile();
		mf.writeUsers();

		ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
		ServerSocket listener = factory.createServerSocket(1234);
		((SSLServerSocket) listener).setNeedClientAuth(true);
		((SSLServerSocket) listener).setEnabledCipherSuites(new String[] { "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256" });
		((SSLServerSocket) listener).setEnabledProtocols(new String[] { "TLSv1.2" });
		while (true) {
			Socket socket = listener.accept();
			// obtain input and output streams
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			// Create a new handler object for handling this request.

			ClientHandler mtch = new ClientHandler(socket, dis, "client" + i, dos);
			Thread t = new Thread(mtch);
			System.out.println("new client adding ...");
			ar.add(mtch);
			t.start();

			i++;

		}

	}
}
