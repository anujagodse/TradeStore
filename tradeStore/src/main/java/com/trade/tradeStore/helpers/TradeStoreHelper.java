package com.trade.tradeStore.helpers;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.trade.tradeStore.dao.TradeStoreDao;
import com.trade.tradeStore.dto.TradeDetails;

public class TradeStoreHelper {

	private static Logger tradeStoreHelperLogger=LoggerFactory.getLogger(TradeStoreHelper.class);
	private int maxLimit=999;
	@Autowired
	private TradeStoreDao tradeStoreDao;
	/**
	 * 1. Checks maturity date is not lower than sysdate
	 * 2. compares verison
	 * @param tradeDetails
	 * @return
	 * @throws Exception 
	 */
	
	public boolean validateAndInsertTrade(TradeDetails tradeDetails) throws Exception {

		Date todaysDate=new Date(System.currentTimeMillis());
		if(tradeDetails.getMaturityDate().before(todaysDate))
		{
			tradeStoreHelperLogger.info("Input Trade is already matured  hence not inserting into DB,"  , tradeDetails);
			return false;
		}
		TradeDetails dbTrade=  tradeStoreDao.getTradeDetailsbyTradeID(tradeDetails.getTradeID());
		if(dbTrade.getVersion()>tradeDetails.getVersion())
		{
			tradeStoreHelperLogger.info("Trade is already stored with higher version ,"  , tradeDetails);
			throw new Exception ("Lower versioned trade recieved " + tradeDetails);

		}

		if(dbTrade.getVersion()==tradeDetails.getVersion())
		{
			tradeStoreHelperLogger.info("Trade is already stored with same version, uodating the trade ,"  , dbTrade);
			updateTrade(tradeDetails);
		}
		else
		{
			tradeStoreHelperLogger.info("Inserting new Trade Entry ,"  , tradeDetails);
			insertTrade(tradeDetails);
		}

		return  true;
	}

	public TradeStoreDao getTradeStoreDao() {
		return tradeStoreDao;
	}

	public void setTradeStoreDao(TradeStoreDao tradeStoreDao) {
		this.tradeStoreDao = tradeStoreDao;
	}

	private void updateTrade(TradeDetails tradeDetails) {
		// TODO Auto-generated method stub

	}

	public boolean insertTrade(TradeDetails tradeDetails) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<String> getTodaysMaturityTrades() {
		Date todaysDate=new Date(System.currentTimeMillis());
		return tradeStoreDao.getMaturedTrades(todaysDate);


	}

	public void  updateExpiryFlag(List<String> listTradeDetailsIds) {

		for (int i=0;i<listTradeDetailsIds.size();i+=maxLimit)
		{
			List<String> subset=listTradeDetailsIds.subList(i, Math.min(listTradeDetailsIds.size(), +maxLimit));
			tradeStoreDao.updateExpirtyFlag(subset);
		}

	
	}

}
