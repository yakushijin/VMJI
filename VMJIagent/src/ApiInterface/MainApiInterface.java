package ApiInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import SocketData.SocketData;
import mainPackage.SocketOperationBridge;


abstract class MainApiInterface<T>{
	protected void ServerReceive(SocketData<T> data,int port) throws ClassNotFoundException {
		while(true) {
		try{
			SocketData<T> sdata = new SocketData<T>();
			
			ServerSocket sSocket = new ServerSocket(port);
			Socket sock = sSocket.accept();
			
			ObjectInputStream sInputStream = new ObjectInputStream(sock.getInputStream());
			
			data = (SocketData<T>)sInputStream.readObject();
			
			SocketOperationBridge<T> socketOperationBridge = new SocketOperationBridge<T>();
			sdata = socketOperationBridge.InitOperation(data.getOpid(),data);
			
			ObjectOutputStream sOutputStream = new ObjectOutputStream(sock.getOutputStream());
			sOutputStream.writeObject(sdata);
			
			sInputStream.close();
			sOutputStream.close();
			sSocket.close();
			sock.close();
			
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	
	}
	
	abstract void StartServerReceive();

}
