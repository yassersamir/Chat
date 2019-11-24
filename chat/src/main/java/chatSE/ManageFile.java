package chatSE;

import java.io.*;
import java.util.*;


public class ManageFile {
	static List<User> users = new ArrayList<>();

	public void writeUsers() {
		User user1 = new User("user", "user123");
		User user2 = new User("person", "person123");
		users.add(user1);
		users.add(user2);
		try {
			FileOutputStream f = new FileOutputStream(new File("users.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(user1);
			o.writeObject(user2);

			o.close();
			f.close();
		} catch (FileNotFoundException f) {
			System.out.println("File not found");
		} catch (IOException i) {
			System.out.println("Error initializing stream writing");
		}
	}

	public boolean readUsers(String username, String password) {
		try {

			FileInputStream fi = new FileInputStream(new File("users.txt"));

			ObjectInputStream oi = new ObjectInputStream(fi);

			while (true) {
				User user = (User) oi.readObject();
				if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
					oi.close();
					fi.close();
					return true;
				}
			}

		} catch (FileNotFoundException f) {
			System.out.println("File not found");
		} catch (IOException i) {
			System.out.println("user name or password error");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public void writeConversationMap(String [] words ,Map<String,Integer> conversations) {
		for(String word :words) {
			if(conversations.containsKey(word)) {
				conversations.replace(word,(conversations.get(word)+1));
			}
			else {
				conversations.put(word,1);
			}
		}
	}
	public void writeMapFile(Map<String,Integer> conversation ,String user) {
		 try
         {
                FileOutputStream fos =
                   new FileOutputStream(user+".txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(conversation);
                oos.close();
                fos.close();
         }catch(IOException ioe)
          {
                ioe.printStackTrace();
          }
		
	}
}
