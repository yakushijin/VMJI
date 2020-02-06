function HostOsInstance(){
	Utility = new Utility();
	OperationLog = new OperationLog();
	HttpResult = new HttpResult();
	ServerInterface = new ServerInterface();
	GridClickEvent = new GridClickEvent();
	Information = new Information;
}
function GridCreate(){
	var json = $("#json").val();
	var jsonlist = $.parseJSON(json)
    var grid = document.getElementById('grid');
    var gridlist = new Handsontable(grid, {
		data: jsonlist
		,colWidths: [0.1,120,100,80,80,80,80,80,80,80,80,80,400]
		,colHeaders: ['HostOsId','HostName','Ip','Cpu','Mem','Disk','UseCpu','UseMem','UseDisk','Cpu%','Mem%','Disk%','Remarks']
		,rowHeaders: true
		,columnSorting: {
	        column: 0,
	        sortColumn: false
	    }
    	,currentRowClassName: 'currentRow'
		,multiSelect: false
		,stretchH: 'last'
    	,contextMenu: {
            items: [
                {
                    key: 'GuestGsCheck',
                    name: 'GuestGsCheck',
                    callback: function() {
                    	var sel = this.getSelected();
                    	GridClickEvent.GuestOsCheck("HostOsScreen",gridlist.getDataAtCell(sel[0][0],0));
                    }
                },
                {
                    key: 'Update',
                    name: 'Update',
                    callback: function() {
                    	var sel = this.getSelected();
                    	var result = Utility.CommonConfirm("HostOsScreen",gridlist.getDataAtCell(sel[0][0],1),"update");
                    	if (result){
                    		GridClickEvent.HostOsUpdate("HostOsScreen",gridlist.getData(sel[0][0],0,sel[0][0],12));
                    	}
                    	Utility.ScreenStatus(1);
                    }
                }  
            ]
        }
	});
    	
};



        
