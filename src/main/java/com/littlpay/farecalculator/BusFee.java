package com.littlpay.farecalculator;

import java.io.Serializable;
import java.util.Date;

public class BusFee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date startDate; 
	private Date endDate; 
	private Long duration;
	private String fromStopId;
	private String endStopId;
	private Double chargeAmount;
	
	private String companyId;
	private String busID;
	private String panNo;
	private String status;
	
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getFromStopId() {
		return fromStopId;
	}
	public void setFromStopId(String fromStopId) {
		this.fromStopId = fromStopId;
	}
	public String getEndStopId() {
		return endStopId;
	}
	public void setEndStopId(String endStopId) {
		this.endStopId = endStopId;
	}
	public Double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getBusID() {
		return busID;
	}
	public void setBusID(String busID) {
		this.busID = busID;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
