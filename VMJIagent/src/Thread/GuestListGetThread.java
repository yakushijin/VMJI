package Thread;

import ApiInterface.GuestListGet;
import mainPackage.MainThread;

public class GuestListGetThread extends MainThread{

	@Override
    public void run(){       
         GuestListGet guestListGet = new GuestListGet(); 
         guestListGet.StartServerReceive();
	}
	
}
