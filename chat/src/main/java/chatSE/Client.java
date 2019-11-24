package chatSE;

import java.io.*;
import java.net.*;
import java.util.Scanner;

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
			
			// establish the connection
			@SuppressWarnings("resource")
			Socket s = new Socket(ip, ServerPort);
			
			// obtaining input and out streams
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			

			// sendMessage thread
			Thread sendMessage = new Thread(() -> {
				
				while (true) {
					String msg = scn.nextLine();
					try {
						dos.writeUTF(msg);
					} catch (IOException e) {
						return;
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
						return;
					}
				}
			});
			sendMessage.start();
			readMessage.start();
		}
	}
}
