package mainPackage;

import Thread.GuestListGetThread;
import Thread.GuestOperationThread;

public class Main {

	public static void main(String[] args) {
		GuestListGetThread guestListGetThread = new GuestListGetThread();
		guestListGetThread.start();

		GuestOperationThread guestOperationThread = new GuestOperationThread();
		guestOperationThread.start();
		
	}

}
