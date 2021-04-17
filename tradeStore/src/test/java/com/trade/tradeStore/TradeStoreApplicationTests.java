package com.trade.tradeStore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.trade.tradeStore.dto.TradeDetails;
import com.trade.tradeStore.helpers.TradeStoreHelper;

@SpringBootTest
class TradeStoreApplicationTests {

	@Autowired
	private TradeStoreHelper helper;
	
	@Test
	void contextLoads() {
	}

	@Test
    public void testHelperClass() {
        assertEquals(
                "class com.trade.tradeStore.helpers.TradeStoreHelper",
                this.helper.getClass().toString());
    }
	
	@Test
	public void testBackdatedTrade() throws Exception
	{
		TradeDetails d=new TradeDetails();
		d.setTradeID("XYZ");
		String str="2015-03-31";
		Date date=Date.valueOf(str);//converting string into sql date.
		d.setMaturityDate(date);
		boolean ret=helper.validateAndInsertTrade(d);
		assertFalse(ret);
	}
}
