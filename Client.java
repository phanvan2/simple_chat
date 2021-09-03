
package lab4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	

	Socket socket ;
	
	public Client() {
		try {
			
			socket = new Socket(Constant.serverName, Constant.port);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean sendMess(packet packet1) {
		ObjectOutputStream output;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject( (Object) packet1 );
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return false;
		}
		return true; 
		
	}
	
	public packet receiveMess() {
		ObjectInputStream input ; 
		packet packet1 = null;
		try {
			input = new ObjectInputStream(socket.getInputStream());
			 packet1 = (packet) input.readObject();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return packet1;
	}

	
	public static void main(String[] args) {
//		Client client = new Client();
//		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(true) {
//					
//					packet packetReceive = client.receiveMess();
//					if( packetReceive != null) {
//						System.out.println("serrver: " + packetReceive.getMess());
//					}
//					
//				}	
//			}
//		}).start();
//		
//		while(true) {
//			Scanner key = new Scanner(System.in);
//			System.out.print("me: " );
//			String mess = key.nextLine();
//			
//			packet packet1 = new packet("helolo",mess, " 2/12/210",null, null);
//			client.sendMess(packet1);
//
//
//
//		}
//		
//		
		
	}
}
