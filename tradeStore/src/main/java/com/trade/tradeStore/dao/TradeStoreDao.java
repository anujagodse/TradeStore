package com.trade.tradeStore.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.trade.tradeStore.dto.TradeDetails;

/**
 * DB handle
 * @author Anuja D
 *
 */
@Service
public class TradeStoreDao implements InitializingBean  {

	@Autowired
	private JdbcTemplate storeJDBCTemplate;

	@Autowired
	private NamedParameterJdbcTemplate  storeNamedJDBCTemplate;

	@Value("${tradeStore.selectTradeData}")
	private String queryTradeDetails;

	@Value("${tradeStore.updateTradeData}")
	private String updateTradeDetails;

	@Value("${tradeStore.insertTradeData}")
	private String insertTradeDetails;

	@Value("${tradeStore.selectExpiredTrades}")
	private String selectExpiredTrades;

	@Value("${}radeStore.updateExpiredTrades")
	private String updateExpiredTrades;
	
	
	public TradeDetails getTradeDetailsbyTradeID(String tradeID)
	{
		TradeDetails  t=	storeJDBCTemplate.query(queryTradeDetails, new ResultSetExtractor<TradeDetails>()
		{

			@Override
			public TradeDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
				TradeDetails tempTradeDetails=new TradeDetails();

				tempTradeDetails.setTradeID(rs.getString("tradeID"));
				tempTradeDetails.setBookID(rs.getString("bookID"));
				tempTradeDetails.setCptyID(rs.getString("counterPartyId"));
				tempTradeDetails.setCreatedDate(rs.getDate("createdDate"));
				tempTradeDetails.setMaturityDate(rs.getDate("maturityDate"));
				return tempTradeDetails;
			}


		});
		return t;
	}
	public List<String> getMaturedTrades(Date todaysDate) {
		List<String> tradeIds=new ArrayList<String>();
		Map<String, Object> namedParameters= Collections.singletonMap("maturityDate", todaysDate);

		tradeIds= (List<String>) storeNamedJDBCTemplate.query(selectExpiredTrades, namedParameters, new ResultSetExtractor<List<String> >() {

			@Override
			public List<String >extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> tradeIds=new ArrayList<String>();
				while(rs.next())
				{ 
					tradeIds.add(rs.getString("tradeID "));
				}
				return tradeIds;
			}
		});
		return tradeIds;
	}


	public void updateExpirtyFlag(List<String> subset) {

		KeyHolder keyholder=new GeneratedKeyHolder();
		SqlParameterSource ps =new MapSqlParameterSource().addValue("tradeIDs", subset);
		storeNamedJDBCTemplate.update(updateExpiredTrades, ps,keyholder);

	}
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(storeJDBCTemplate, "StoreJBCDTeamplate can not be null");
;	storeNamedJDBCTemplate=new NamedParameterJdbcTemplate(storeJDBCTemplate);
		
	}
	public JdbcTemplate getStoreJDBCTemplate() {
		return storeJDBCTemplate;
	}
	public void setStoreJDBCTemplate(JdbcTemplate storeJDBCTemplate) {
		this.storeJDBCTemplate = storeJDBCTemplate;
	}
	public NamedParameterJdbcTemplate getStoreNamedJDBCTemplate() {
		return storeNamedJDBCTemplate;
	}
	public void setStoreNamedJDBCTemplate(NamedParameterJdbcTemplate storeNamedJDBCTemplate) {
		this.storeNamedJDBCTemplate = storeNamedJDBCTemplate;
	}
	public String getQueryTradeDetails() {
		return queryTradeDetails;
	}
	public void setQueryTradeDetails(String queryTradeDetails) {
		this.queryTradeDetails = queryTradeDetails;
	}
	public String getUpdateTradeDetails() {
		return updateTradeDetails;
	}
	public void setUpdateTradeDetails(String updateTradeDetails) {
		this.updateTradeDetails = updateTradeDetails;
	}
	public String getInsertTradeDetails() {
		return insertTradeDetails;
	}
	public void setInsertTradeDetails(String insertTradeDetails) {
		this.insertTradeDetails = insertTradeDetails;
	}
	public String getSelectExpiredTrades() {
		return selectExpiredTrades;
	}
	public void setSelectExpiredTrades(String selectExpiredTrades) {
		this.selectExpiredTrades = selectExpiredTrades;
	}
	public String getUpdateExpiredTrades() {
		return updateExpiredTrades;
	}
	public void setUpdateExpiredTrades(String updateExpiredTrades) {
		this.updateExpiredTrades = updateExpiredTrades;
	}
	
	

}

