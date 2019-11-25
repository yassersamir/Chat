package chatSE;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLParameters;

public class Client {
	final static int ServerPort = 1234;

	public static void main(String args[]) throws UnknownHostException, IOException {
		@SuppressWarnings("resource")
		Scanner scn = new Scanner(System.in);
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		ManageFile mf = new ManageFile();

		InetAddress ip = InetAddress.getByName("localhost");
		// check user authentication
		System.out.println("enter your name : ");
		String username = bf.readLine();
		System.out.println("enter your password : ");
		String password = bf.readLine();

		if (mf.readUsers(username, password)) {

			SocketFactory factory = SSLSocketFactory.getDefault();

			Socket connection = factory.createSocket(ip, ServerPort);
			((SSLSocket) connection).setEnabledCipherSuites(new String[] { "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256" });
			((SSLSocket) connection).setEnabledProtocols(new String[] { "TLSv1.2" });
			SSLParameters sslParams = new SSLParameters();
			sslParams.setEndpointIdentificationAlgorithm("HTTPS");
			((SSLSocket) connection).setSSLParameters(sslParams);
			// obtaining input and out streams
			DataInputStream dis = new DataInputStream(connection.getInputStream());
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

			// sendMessage thread
			Thread sendMessage = new Thread(() -> {

				while (true) {
					String msg = scn.nextLine();
					try {
						dos.writeUTF(msg);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			// readMessage thread
			Thread readMessage = new Thread(() -> {
				while (true) {
					try {
						String msg = dis.readUTF();
						System.out.println(msg);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			sendMessage.start();
			readMessage.start();
		}

	}
}
