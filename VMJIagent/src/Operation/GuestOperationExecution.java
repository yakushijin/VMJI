package Operation;

import SocketData.GuestOperationData;
import SocketData.SocketData;
import mainPackage.OperationContents;

public class GuestOperationExecution extends ServerOperationExecution<GuestOperationData>{

	public SocketData<GuestOperationData> Operation(SocketData<GuestOperationData> data){
		
		OperationContents operationContents = new OperationContents();
		
		String operation = operationContents.ContentsGet(data.getOpid());
		data.setCmd(operation + " " + data.getData().getGuestid());
				
		try {
			data = OperationExecution(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
