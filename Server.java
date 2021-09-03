package lab4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
//import java.util.Scanner;


public class Server {
	final int port = 9999;
	ServerSocket serverSocket; 
	Socket socket = null ; 
	ObjectInputStream input;
	ObjectOutputStream output = null ; 

	public Server(String name) {
		 try {
			serverSocket = new ServerSocket(9999);
			System.out.println("Server is started");
				socket = serverSocket.accept(); 
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean sendMess(packet packet1) {
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			Object objOutput = (Object) packet1; 
			output.writeObject(objOutput);
			output.flush();
			//output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false ; 
		}
		return true;
	
		
	}
	public packet receiveMess() {
		
		packet packet1  = null;
		try {
			input = new ObjectInputStream(socket.getInputStream());
			packet1 = (packet) input.readObject(); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} 
		
		return packet1;
	}
	public boolean close() {
		try {
			
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static void main(String[] args) {
//		Server server = new Server("Phan van");
//		if( server.socket != null) {
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					while(true) {
//						packet packet1 = server.receiveMess();
//						if( packet1 != null) {
//							System.out.println("Client: " +packet1.getMess());
//						}else {
//							System.out.println("Loig");
//						}
//					}
//				}
//			}).start();
//		}
//		
//		while(true) {
//			Scanner key = new Scanner(System.in);
//			System.out.println("me: " );
//			String mess = key.nextLine();
//			packet packetSend = new packet("helolo",mess, " 2/12/210",null, null);
//			server.sendMess(packetSend);
//			
//		}
//		
		
	}
}
