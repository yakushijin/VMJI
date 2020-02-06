package Thread;

import ApiInterface.GuestOperation;
import mainPackage.MainThread;

public class GuestOperationThread extends MainThread{
	@Override
    public void run(){
         GuestOperation guestOperation = new GuestOperation();
         guestOperation.StartServerReceive();
    }
}
