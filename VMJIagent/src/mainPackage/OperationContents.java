package mainPackage;

public class OperationContents {

	private String dir = "/opt/vmoperation/";
	
	private String id1 = dir + "start";
	private String id2 = dir + "stop";
	private String id3 = dir + "reboot";
	private String id4 = dir + "destroy";
	private String id5 = dir + "vminfo_unit";
	
	private String id101 = dir + "vminfo";
	
	public String ContentsGet(int id) {
		String operation;
		switch(id) {
			case 1:	operation = this.id1; break;
			case 2: operation = this.id2; break;
			case 3: operation = this.id3; break;
			case 4: operation = this.id4; break;
			case 5: operation = this.id5; break;
			case 101: operation = this.id101; break;
			default:operation = "";
		}
		
		return operation;
	}
	
}
