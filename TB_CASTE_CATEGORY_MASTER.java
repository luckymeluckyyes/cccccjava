package com.BisagN.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table
(name = "tb_caste_category", uniqueConstraints = {
@UniqueConstraint(columnNames = "id"),})
public class TB_CASTE_CATEGORY_MASTER {
	

	private int id;
	 private String category;
	 private String category_createdby;
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
	 private Date category_createddate;
	 
	 private String category_updatedby;
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
	 private Date category_updateddate;
	 
	
	 private String status;
	 
	 @Id
		@GeneratedValue(strategy = IDENTITY)
		@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory_createdby() {
		return category_createdby;
	}
	public void setCategory_createdby(String category_createdby) {
		this.category_createdby = category_createdby;
	}
	public Date getCategory_createddate() {
		return category_createddate;
	}
	public void setCategory_createddate(Date category_createddate) {
		this.category_createddate = category_createddate;
	}
	public String getCategory_updatedby() {
		return category_updatedby;
	}
	public void setCategory_updatedby(String category_updatedby) {
		this.category_updatedby = category_updatedby;
	}
	public Date getCategory_updateddate() {
		return category_updateddate;
	}
	public void setCategory_updateddate(Date category_updateddate) {
		this.category_updateddate = category_updateddate;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

		 
	 
}






