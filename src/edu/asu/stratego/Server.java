package edu.asu.stratego;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import edu.asu.stratego.game.ServerGameManager;

/**
 * The Stratego Server creates a socket and listens for connections from every 
 * two players to form a game session. Each session is handled by a thread, 
 * ServerGameManager, that communicates with the two players and determines the 
 * status of the game.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        
        String hostAddress    = null;
        ServerSocket listener = null;
        int sessionNumber     = 1;
        
        Socket socket = new Socket();								//Creates socket to connect to arbitrary website		
        socket.connect(new InetSocketAddress("google.com", 80));	//Connects to website using wireless adapter ONLY
        hostAddress = socket.getLocalAddress().toString();			//Gets ip address of wireless adapter
        
        socket.close();												//Closes socket
        
        
        try {
            listener = new ServerSocket(4212);
            System.out.println("Server started @ " + hostAddress);
            System.out.println("Waiting for incoming connections...\n");
            
            while (true) {
                Socket playerOne = listener.accept();
                System.out.println("Session " + sessionNumber + 
                                   ": Player 1 has joined the session");
                
                Socket playerTwo = listener.accept();
                System.out.println("Session " + sessionNumber + 
                                   ": Player 2 has joined the session");
                
                Thread session = new Thread(new ServerGameManager(
                        playerOne, playerTwo, sessionNumber++));
                session.setDaemon(true);
                session.start();
            }
        }
        
        finally { listener.close(); }
    }
}
