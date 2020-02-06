package SocketData;

import java.io.Serializable;
import java.net.Socket;

public class GuestOperationData implements Serializable{

	private static final long serialVersionUID = -8448891186969615527L;
	public String guestid;
	public static int port = 8002;

	
	public GuestOperationData(String guestid) {
		this.guestid = guestid;
	}
	
	public GuestOperationData() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public String getGuestid() {
		return guestid;
	}
	
}
