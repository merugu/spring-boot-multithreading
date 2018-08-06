package com.innovativeintelli.multithreading.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the invoice database table.
 * 
 */
@Entity
@NamedQuery(name="Invoice.findAll", query="SELECT i FROM Invoice i")
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int invoiceid;

	private String status;

	private int subinvoiceid;

	public Invoice() {
	}

	public int getInvoiceid() {
		return this.invoiceid;
	}

	public void setInvoiceid(int invoiceid) {
		this.invoiceid = invoiceid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSubinvoiceid() {
		return this.subinvoiceid;
	}

	public void setSubinvoiceid(int subinvoiceid) {
		this.subinvoiceid = subinvoiceid;
	}

}