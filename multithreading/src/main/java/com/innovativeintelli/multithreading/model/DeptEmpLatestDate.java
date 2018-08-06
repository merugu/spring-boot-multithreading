package com.innovativeintelli.multithreading.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the dept_emp_latest_date database table.
 * 
 */
@Entity
@Table(name="dept_emp_latest_date")
@NamedQuery(name="DeptEmpLatestDate.findAll", query="SELECT d FROM DeptEmpLatestDate d")
public class DeptEmpLatestDate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="emp_no")
	private int empNo;

	@Temporal(TemporalType.DATE)
	@Column(name="from_date")
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	@Column(name="to_date")
	private Date toDate;

	public DeptEmpLatestDate() {
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