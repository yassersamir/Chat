package chatSE;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {

	Scanner scn = new Scanner(System.in);
	final DataInputStream dis;
	final DataOutputStream dos;
	Socket s;
	boolean isloggedin;
	String name;
	Map<String,Integer> conversations ;
	ManageFile mf;

	public ClientHandler(Socket s, DataInputStream dis,String username, DataOutputStream dos) {
		this.dis = dis;
		this.dos = dos;
		this.s = s;
		this.isloggedin = true;
		this.mf = new ManageFile();
		this.conversations = new HashMap<>();
		this.name=username;
	}

	@Override
	public void run() {
		String received;
		while (true) {
			try {
				// receive the string
				received = dis.readUTF();
				if (received.equals("bye")) {
					this.isloggedin = false;
					mf.writeMapFile(conversations,"user");
					System.out.println("statistics of "+name);
					System.out.println(conversations);
					this.s.close();
					return;
				}
				// break the string into message and recipient part
				StringTokenizer st = new StringTokenizer(received, "#");
				String MsgToSend = st.nextToken();
				String recipient = st.nextToken();
				
				
				// search for the recipient in the connected devices list.
				
				for (ClientHandler mc : Server.ar) {
					if (mc.name.equals(recipient) && mc.isloggedin == true) {
						mc.dos.writeUTF(this.name+ " : " + MsgToSend);
						// save words in map
						mf.writeConversationMap(MsgToSend.split(" "), conversations);
						break;
					}
				}
			} catch (IOException e) {
				break;
			}
		}
		try {
			// closing resources
			this.dis.close();
			this.dos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
