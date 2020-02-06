package ApiInterface;

import SocketData.GuestListGetData;
import SocketData.SocketData;

public class GuestListGet extends MainApiInterface<GuestListGetData>{

	public void StartServerReceive(){
		SocketData<GuestListGetData> guestListGetData = new SocketData<GuestListGetData>();
		try {
			ServerReceive(guestListGetData,GuestListGetData.port);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}
