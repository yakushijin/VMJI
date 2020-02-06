package apiInterface;

import SocketData.GuestListGetData;
import SocketData.SocketData;

public class ClientGuestListGet extends ClientApiInterface<GuestListGetData>{
	
	@Override
	public SocketData<GuestListGetData> SendStart(SocketData<GuestListGetData> data){
		
		data = ServerSend(data,GuestListGetData.port);

		return data;
	}

}
