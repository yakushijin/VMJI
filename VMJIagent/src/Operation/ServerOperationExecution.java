package Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import SocketData.SocketData;

abstract class ServerOperationExecution<T> {
    public SocketData<T> OperationExecution(SocketData<T> data) throws InterruptedException {
    	try {
    		
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(data.getCmd());
            
            int resultCode = process.waitFor();
            InputStreamReader resultData;
            switch(resultCode) {
            
            case 0:
            	resultData = new InputStreamReader(process.getInputStream(), "UTF-8");
            	break;
            case 1:
            	resultData = new InputStreamReader(process.getErrorStream(), "UTF-8");
                break;
            default:
            	resultData = null; 
            }
  
            BufferedReader reader = new BufferedReader(resultData);
            StringBuilder builder = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                builder.append((char)c);
            }
            System.out.println(process.waitFor());
            System.out.println(builder.toString());
            
            data.setResultCode(resultCode);
            data.setResultData(builder.toString());
            
            return data;

        } catch (IOException ex) {
        }
        
        return data;
    }
    
    abstract SocketData<T> Operation(SocketData<T> data);
}
