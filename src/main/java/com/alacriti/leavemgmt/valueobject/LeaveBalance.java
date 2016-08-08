package com.alacriti.leavemgmt.valueobject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LeaveBalance {
	private int empId;
	private short casualLeave;
	private short sickLeave;
	private short privilegeLeave;
	private short compOff;
	private short metLeave;
	private short petLeave;
	private int financialyear;
	public LeaveBalance(){
		
	}

	public LeaveBalance(int empId, short casualLeave, short sickLeave,
			short privilegeLeave, short compOff, short metLeave, short petLeave,int financialyear) {
		super();
		this.empId = empId;
		this.casualLeave = casualLeave;
		this.sickLeave = sickLeave;
		this.privilegeLeave = privilegeLeave;
		this.compOff = compOff;
		this.metLeave = metLeave;
		this.petLeave = petLeave;
		this.financialyear = financialyear;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public short getCasualLeave() {
		return casualLeave;
	}

	public void setCasualLeave(short casualLeave) {
		this.casualLeave = casualLeave;
	}

	public short getSickLeave() {
		return sickLeave;
	}

	public void setSickLeave(short sickLeave) {
		this.sickLeave = sickLeave;
	}

	public short getPrivilegeLeave() {
		return privilegeLeave;
	}

	public void setPrivilegeLeave(short privilegeLeave) {
		this.privilegeLeave = privilegeLeave;
	}

	public short getCompOff() {
		return compOff;
	}

	public void setCompOff(short compOff) {
		this.compOff = compOff;
	}

	public short getMetLeave() {
		return metLeave;
	}

	public void setMetLeave(short metLeave) {
		this.metLeave = metLeave;
	}

	public short getPetLeave() {
		return petLeave;
	}

	public void setPetLeave(short petLeave) {
		this.petLeave = petLeave;
	}

	public int getFinancialyear() {
		return financialyear;
	}

	public void setFinancialyear(int financialyear) {
		this.financialyear = financialyear;
	}
	
}
