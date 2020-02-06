package apiInterface;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import SocketData.GuestListGetData;
import SocketData.GuestOperationData;
import SocketData.SocketData;

/**
 * エージェントとの通信を行うクラス
 */
abstract class ClientApiInterface<T> {
	protected SocketData<T> ServerSend(SocketData<T> data,int port) {
	try{
		//IPアドレスとポート番号を指定してクライアント側のソケットを作成
		Socket cSocket = new Socket(data.getHostIp(), port);
		ObjectOutputStream  cOutputStream = new ObjectOutputStream(cSocket.getOutputStream());

		//APIへデータを送信
		cOutputStream.writeObject(data);
		
		//APIからデータを受け取るソケットを作成
		ObjectInputStream cInputStream = new ObjectInputStream(cSocket.getInputStream());
		
		//APIからデータを受け取り
		data = (SocketData)cInputStream.readObject();
		System.out.println(data.opid);
		
		//
		cOutputStream.close();
		cSocket.close();
		
		return data;
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return data;
	
	}
	
	abstract  SocketData<T> SendStart(SocketData<T> data);
}
