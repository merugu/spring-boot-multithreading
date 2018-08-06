package com.innovativeintelli.multithreading.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the current_dept_emp database table.
 * 
 */
@Entity
@Table(name="current_dept_emp")
@NamedQuery(name="CurrentDeptEmp.findAll", query="SELECT c FROM CurrentDeptEmp c")
public class CurrentDeptEmp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="dept_no")
	private String deptNo;

	@Column(name="emp_no")
	private int empNo;

	@Temporal(TemporalType.DATE)
	@Column(name="from_date")
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	@Column(name="to_date")
	private Date toDate;

	public CurrentDeptEmp() {
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public int getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public Date getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}