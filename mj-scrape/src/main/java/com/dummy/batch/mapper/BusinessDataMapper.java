package com.dummy.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dummy.batch.model.BusinessData;

public class BusinessDataMapper implements RowMapper<BusinessData>  {

	@Override
	public BusinessData mapRow(ResultSet arg0, int arg1) throws SQLException {
		BusinessData data = new BusinessData();
		return data;
	}

}
