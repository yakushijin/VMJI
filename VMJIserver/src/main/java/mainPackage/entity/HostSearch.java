package mainPackage.entity;

import com.google.gson.annotations.Expose;

public class HostSearch {

	@Expose
	private int HostOsId;

	@Expose
	private String HostName;

	@Expose
	private String Ip;

	@Expose
	private double Cpu;

	@Expose
	private double Mem;

	@Expose
	private double Disk;
	
	@Expose
	private long UseCpu;

	@Expose
	private long UseMem;

	@Expose
	private long UseDisk;
	
	@Expose
	private double CpuPercent;
	
	@Expose
	private double MemPercent;
	
	@Expose
	private double DiskPercent;

	@Expose
	private String Remarks;
	
	private GuestOsEntity guestOsEntity;
    
	public HostSearch(int HostOsId,String HostName,String Ip,double Cpu,double Mem,double Disk,long UseCpu,long UseMem,long UseDisk,double CpuPercent,double MemPercent,double DiskPercent,String Remarks) {

		this.HostOsId = HostOsId;
		this.HostName = HostName;
		this.Ip = Ip;
		this.Cpu = Cpu;
		this.Mem = Mem;
		this.Disk = Disk;
		this.UseCpu = UseCpu;
		this.UseMem = UseMem;
		this.UseDisk = UseDisk;
		this.CpuPercent = CpuPercent;
		this.MemPercent = MemPercent;
		this.DiskPercent = DiskPercent;
		this.Remarks = Remarks;
	}
	
	public int getHostOsId() {
		return HostOsId;
	}
	public void setHostOsId(int HostOsId) {
		this.HostOsId = HostOsId;
	}
	
	public String getHostName() {
		return HostName;
	}
	public void setHostName(String HostName) {
		this.HostName = HostName;
	}
	
	public String getIp() {
		return Ip;
	}
	public void setIp(String Ip) {
		this.Ip = Ip;
	}
	
	public double getCpu() {
		return Cpu;
	}
	public void setCpu(double Cpu) {
		this.Cpu = Cpu;
	}
	
	public double getMem() {
		return Mem;
	}
	public void setMem(double Mem) {
		this.Mem = Mem;
	}
	
	public double getDisk() {
		return Disk;
	}
	public void setDisk(double Disk) {
		this.Disk = Disk;
	}
	
	public long getUseCpu() {
		return UseCpu;
	}
	public void setUseCpu(long UseCpu) {
		this.UseCpu = UseCpu;
	}
	
	public long getUseMem() {
		return UseMem;
	}
	public void setUseMem(long UseMem) {
		this.UseMem = UseMem;
	}
	
	public long getUseDisk() {
		return UseDisk;
	}
	public void setUseDisk(long UseDisk) {
		this.UseDisk = UseDisk;
	}
	
	public double getCpuPercent() {
		return CpuPercent;
	}
	public void setCpuPercent(double CpuPercent) {
		this.CpuPercent = CpuPercent;
	}
	
	public double getMemPercent() {
		return MemPercent;
	}
	public void setMemPercent(double MemPercent) {
		this.MemPercent = MemPercent;
	}
	
	public double getDiskPercent() {
		return DiskPercent;
	}
	public void setDiskPercent(double DiskPercent) {
		this.DiskPercent = DiskPercent;
	}
}


