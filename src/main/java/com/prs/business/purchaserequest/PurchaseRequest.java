package com.prs.business.purchaserequest;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prs.business.user.User;

@Entity
public class PurchaseRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="UserId")
	private User user;
//	private int userID;
	private String description;
	private String justification;
	private String dateNeeded;
	private String deliveryMode;
	private String status;
	private double total;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd'T'HH:mm:ss.SSS")
	private Timestamp submittedDate;
	private String reasonForRejection;
	
	public static final String STATUS_NEW = "New"; 
	public static final String STATUS_REVIEW = "Review";
	public static final String STATUS_EDIT = "Edit";
	public static final String STATUS_APPROVED = "Approved";
	public static final String STATUS_REJECTED = "Rejected";
	
	
	public PurchaseRequest(User user, String description, String justification, String dateNeeded, String deliveryMode,
			String status, double total, Timestamp submittedDate, String reasonForRejection) {
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
		
	}
	public PurchaseRequest(int id, User user, String description, String justification, String dateNeeded,
			String deliveryMode, String status, double total, Timestamp submittedDate, String reasonForRejection) {
		super();
		this.id = id;
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
	
	}
	public PurchaseRequest() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public String getDateNeeded() {
		return dateNeeded;
	}
	public void setDateNeeded(String dateNeeded) {
		this.dateNeeded = dateNeeded;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;

	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Timestamp getSubmittedDate(Timestamp ts) {
		return submittedDate;
	}
	public void setSubmittedDate(Timestamp submittedDate) {
		this.submittedDate = submittedDate;
	}
	public static String getNew() {
		return STATUS_NEW;
	}
	public static String getReview() {
		return STATUS_REVIEW;
	}
	public static String getEdit() {
		return STATUS_EDIT;
	}
	public static String getApproved() {
		return STATUS_APPROVED;
	}
	public static String getRejected() {
		return STATUS_REJECTED;
	}
	public String getReasonForRejection() {
		return reasonForRejection;
	}
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	@Override
	public String toString() {
		return "PurchaseRequest [ID=" + id + ", user=" + user + ", description=" + description + ", justification="
				+ justification + ", dateNeeded=" + dateNeeded + ", deliveryMode=" + deliveryMode + ", status="
				+ status + ", total=" + total + ", submittedDate=" + submittedDate
				+ "]";
	}

}