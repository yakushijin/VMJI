package mainPackage;

import Operation.GuestListGetExecution;
import Operation.GuestOperationExecution;
import SocketData.GuestListGetData;
import SocketData.GuestOperationData;
import SocketData.SocketData;

public class SocketOperationBridge<T> {
	public SocketData<T> InitOperation(int id,SocketData<T> data) {
		SocketData<T> rdata = new SocketData<T>();
		
		switch(id) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			SocketData<GuestOperationData> guestOperationData = (SocketData<GuestOperationData>) data;
			GuestOperationExecution guestOperationExecution = new GuestOperationExecution();
			rdata = (SocketData<T>) guestOperationExecution.Operation(guestOperationData);
			break;
						
		case 101:
			SocketData<GuestListGetData> guestListGetData = (SocketData<GuestListGetData>) data;	
			GuestListGetExecution guestListGetDataExecution = new GuestListGetExecution();
			rdata = (SocketData<T>) guestListGetDataExecution.Operation(guestListGetData);
			break;
		}

		return rdata;
	}
}
