package ApiInterface;

import SocketData.GuestOperationData;
import SocketData.SocketData;

public class GuestOperation extends MainApiInterface<GuestOperationData>{

	public void StartServerReceive(){		
		SocketData<GuestOperationData> guestOperationData = new SocketData<GuestOperationData>();
		try {
			ServerReceive(guestOperationData,GuestOperationData.port);
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
	
}
