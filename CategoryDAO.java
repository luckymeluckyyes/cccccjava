package com.BisagN.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import com.BisagN.models.TB_CASTE_CATEGORY_MASTER;


public interface CategoryDAO {

	
	public List<Map<String, Object>> DataTablecategoryDataList(int startPage, int pageLength, String  Search, String orderColunm, String orderType, String category);
	
	public long DataTablecategoryDataTotalCount(String Search, String category);
	
	
	
	
	public String GenerateQueryWhereClause_SQL(String Search, String category);
	
	public PreparedStatement setQueryWhereClause_SQL(PreparedStatement stmt, String Search, String category);
	
	

	public TB_CASTE_CATEGORY_MASTER getCastcategoryByid(int id);




	
	
}
