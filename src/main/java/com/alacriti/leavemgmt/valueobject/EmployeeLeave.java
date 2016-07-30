package com.alacriti.leavemgmt.valueobject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmployeeLeave {
	private EmployeeProfile employeeProfile;
	private short casualLeave;
	private short privilegeLeave;
	private short sickLeave;
	private short compOff;
	private short petLeave;
	private short metleave;
	
	public EmployeeLeave(){
		
	}
	public EmployeeLeave(EmployeeProfile employeeProfile, short casualLeave,
			short privilegeLeave, short sickLeave, short compOff,
			short petLeave, short metleave) {
		super();
		this.employeeProfile = employeeProfile;
		this.casualLeave = casualLeave;
		this.privilegeLeave = privilegeLeave;
		this.sickLeave = sickLeave;
		this.compOff = compOff;
		this.petLeave = petLeave;
		this.metleave = metleave;
	}
	public EmployeeProfile getEmployeeProfile() {
		return employeeProfile;
	}
	public void setEmployeeProfile(EmployeeProfile employeeProfile) {
		this.employeeProfile = employeeProfile;
	}
	public short getCasualLeave() {
		return casualLeave;
	}
	public void setCasualLeave(short casualLeave) {
		this.casualLeave = casualLeave;
	}
	public short getPrivilegeLeave() {
		return privilegeLeave;
	}
	public void setPrivilegeLeave(short privilegeLeave) {
		this.privilegeLeave = privilegeLeave;
	}
	public short getSickLeave() {
		return sickLeave;
	}
	public void setSickLeave(short sickLeave) {
		this.sickLeave = sickLeave;
	}
	public short getCompOff() {
		return compOff;
	}
	public void setCompOff(short compOff) {
		this.compOff = compOff;
	}
	public short getPetLeave() {
		if(this.employeeProfile.isGender() == true)
			return petLeave;
		return -1;
	}
	public void setPetLeave(short petLeave) {
		if(this.employeeProfile.isGender() == true)
			this.petLeave = petLeave;
		else
			this.petLeave = -1;
	}
	public short getMetleave() {
		if(this.employeeProfile.isGender() == false)
			return metleave;
		return -1;
		
	}
	public void setMetleave(short metleave) {
		if(this.employeeProfile.isGender() == false)
			this.metleave = metleave;
		else
			this.metleave = -1;
	}
		
}
