package SocketData;

import java.io.Serializable;

public class SocketData<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8655430000038903113L;
	public int opid;
	public String cmd;
	public String hostid;
	public int resultCode;
	public String resultData;
	public T data;
	
	public SocketData(int opid,String cmd,String hostid,int resultCode,String resultData) {
		this.opid = opid;
		this.cmd = cmd;
		this.hostid = hostid;
		this.resultCode = resultCode;
		this.resultData = resultData;	
	}
	
	public SocketData() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getOpid() {
		return this.opid;
	}
	
	public void setOpid(int opid) {
		this.opid = opid;
	}

	public String getCmd() {
		return this.cmd;
	}
	
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getHostid() {
		return this.hostid;
	}
	
	public void setHostid(String hostid) {
		this.hostid = hostid;
	}
		
	public int getResultCode() {
		return this.resultCode;
	}
	
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultData() {
		return this.resultData;
	}
	
	public void setResultData(String resultData) {
		this.resultData = resultData;
	}
	
	public T getData() {
		return this.data;
	}
	
	public void setdata(T data) {
		this.data = data;
	}

}
