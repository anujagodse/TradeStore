package com.trade.tradeStore.dto;

import java.sql.Date;
/**
 * 
 * @author Anuja D
 * This class holds the  trade Data which has to be stored
 *
 */

public class TradeDetails {
	
private	String tradeID;
private	int version;
private	String cptyID;
private	String bookID;
private	Date maturityDate;
private	Date createdDate;
private	boolean expired;
public String getTradeID() {
	return tradeID;
}
public void setTradeID(String tradeID) {
	this.tradeID = tradeID;
}
public int getVersion() {
	return version;
}
public void setVersion(int version) {
	this.version = version;
}
public String getCptyID() {
	return cptyID;
}
public void setCptyID(String cptyID) {
	this.cptyID = cptyID;
}
public String getBookID() {
	return bookID;
}
public void setBookID(String bookID) {
	this.bookID = bookID;
}
public Date getMaturityDate() {
	return maturityDate;
}
public void setMaturityDate(Date maturityDate) {
	this.maturityDate = maturityDate;
}
public Date getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}
public boolean isExpired() {
	return expired;
}
public void setExpired(boolean expired) {
	this.expired = expired;
}
@Override
public String toString() {
	return "TradeDetails [tradeID=" + tradeID + ", version=" + version + ", cptyID=" + cptyID + ", bookID=" + bookID
			+ ", maturityDate=" + maturityDate + ", createdDate=" + createdDate + ", expired=" + expired + "]";
}
	


}
