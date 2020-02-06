package Operation;

import SocketData.GuestListGetData;
import SocketData.SocketData;
import mainPackage.OperationContents;

public class GuestListGetExecution extends ServerOperationExecution<GuestListGetData>{

	public SocketData<GuestListGetData> Operation(SocketData<GuestListGetData> data){
		
		OperationContents operationContents = new OperationContents();
		
		String operation = operationContents.ContentsGet(data.getOpid());
		System.out.println(operation);
		data.setCmd(operation);
		
		try {
			data = OperationExecution(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
