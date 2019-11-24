## Steps to run the application in detail
- Run Server class 
- Run multiple client class
  - console will ask you about user name then password 
  - there are two users saved [user,user123] , [person,person123]
  - you can add more in ManageFile class
- Start Chat after authenticate
- To send a message from any client, type the message, followed by a “#” and then the name of the recipient client 
  - in this case name of client client0 , client1 , ...
- chat will end when enter bye
- After end chat you will recive statistics of words in server

## Design Notes
- To support multiple clients at the same time use thread as shown in picture
  - ![image](https://github.com/yassersamir/Chat/blob/master/chat/target/chatImage.PNG)

- Creating User class implement Serializable to save in file
- Creating ManageFile to mange save and read from file
