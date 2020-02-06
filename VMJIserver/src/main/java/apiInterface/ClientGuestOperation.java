package apiInterface;

import SocketData.GuestOperationData;
import SocketData.SocketData;

public class ClientGuestOperation extends ClientApiInterface<GuestOperationData>{
	@Override
	public SocketData<GuestOperationData> SendStart(SocketData<GuestOperationData> data){
		
		data = ServerSend(data,GuestOperationData.port);

		return data;
	}
}
