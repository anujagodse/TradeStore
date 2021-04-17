package com.trade.tradeStore.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trade.tradeStore.constants.AppConstants;
import com.trade.tradeStore.dto.TradeDetails;
import com.trade.tradeStore.helpers.TradeStoreHelper;

/**
 * 
 * 
 * @author Anuja  D
 * This is the main class
 *
 */

public class TradeStoreController {

	@Autowired
	TradeStoreHelper tradeStoreHelper;
	private static Logger tradeStoreServiceLogger=LoggerFactory.getLogger(TradeStoreController.class);

	
	public TradeStoreHelper getTradeStoreHelper() {
		return tradeStoreHelper;
	}

	public void setTradeStoreHelper(TradeStoreHelper tradeStoreHelper) {
		this.tradeStoreHelper = tradeStoreHelper;
	}

	/**
	 * This service is used to sore Trade Data
	 * @throws Exception 
	 */
	@RequestMapping(value=AppConstants.URL_INSERT , produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
			)
	public void saveTradeData(TradeDetails tradeDetails) 
	{
		tradeStoreServiceLogger.info("Started saveTradeData ",tradeDetails );
		try {
			
	
		boolean success=tradeStoreHelper.validateAndInsertTrade(tradeDetails);
		if(success)
			tradeStoreServiceLogger.info("Trade details saved successfully");		
		}
		catch (Exception e)
		{
			tradeStoreServiceLogger.error("Exception while storing trade details ", e);
		}

	}

	/**
	 * This is the batch job which will run end of the on entire set of trades to mark expired if the trade corsses maturity date
	 */
	@Scheduled(cron = "${tradeStore.update.expirtyFlag.cron}")
	public void batchUpdate()
	{
		tradeStoreServiceLogger.info("batchUpdate job kicked off");		
		List<String> listTradeDetailsIds=tradeStoreHelper.getTodaysMaturityTrades();
		tradeStoreServiceLogger.info("TradeIds to be updated - ", listTradeDetailsIds);	
		tradeStoreHelper.updateExpiryFlag(listTradeDetailsIds);
	}

}
