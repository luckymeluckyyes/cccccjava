package com.BisagN.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.BisagN.models.TB_CASTE_CATEGORY_MASTER;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private DataSource dataSource;


	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public List<Map<String, Object>> DataTablecategoryDataList(int startPage, int pageLength, String Search,
			String orderColunm, String orderType, String category) {

		String SearchValue = GenerateQueryWhereClause_SQL(Search, category);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		String q = "";
		try {

			conn = dataSource.getConnection();
			String pageL = "";
			if (pageLength == -1) {
				pageL = "ALL";
			} else {
				pageL = String.valueOf(pageLength);
			}

			if(Search.equals("") && category.equals("")) {
				q = "select id,category,status from tb_caste_category where status= '1'  "  + " ORDER BY category " + orderType + " limit "
						+ pageL + " OFFSET " + startPage;
			}else {
			q = "select id,category,status from tb_caste_category where status= '1' "  + SearchValue + " ORDER BY category " + orderType + " limit "
					+ pageL + " OFFSET " + startPage;
			}
			
			
			
			// "+orderColunm +"
			
//			where status= '1'
//			q="select id,gender_name,status from tb_mstr_gender where status='1' " + SearchValue + " " ;
			
//			SELECT ROW_NUMBER() OVER(order by cv.id) as sr_no,* FROM candidate_view cv\n" + " " + SearchValue + " " + " ORDER BY " + orderColunm + " "
//					+ orderType + " limit " + pageL + " OFFSET " + startPage + " "
//			
			
			// "+orderColunm +"

//			q = "select re.id,re.name,re.username,re.organization,re.ic_no,rl.role from tb_iaap_registration re \n" + 
//					"inner join roleinformation rl on rl.role_id = re.role where re.id!=0 "  + SearchValue + " ORDER BY re.id " + orderType + " limit "
//					+ pageL + " OFFSET " + startPage;
			
			
			PreparedStatement stmt = conn.prepareStatement(q);
			stmt = setQueryWhereClause_SQL(stmt, Search, category);
		//	System.err.println("stmt------------H-" + stmt);
			ResultSet rs = stmt.executeQuery();
			

			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				
			
				String f = "";
				String action = "";
				String f1 = "";

				
			
				
				String ADD = "onclick=\" if (confirm('Are You Sure You Want to Edit Detail ?') ){editData('"+ rs.getString("id") +"','"+ rs.getString("category") +"','"+ rs.getString("status") +"') }else{ return false;}\"";
				f = "<i class='fa fa-pencil '  " + ADD + " title='Edit Data'></i>";
				
				String ADD1 = "onclick=\" if (confirm('Are You Sure You Want to Delete Detail ?') ){deleteData('"+ rs.getString("id") + "') }else{ return false;}\"";
				f1 = "<i class='fa fa-trash '  " + ADD1 + " title='Delete Data'></i>";

				action = f+" "+f1;
				columns.put("action", action);

				list.add(columns);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		//System.err.println("list------H-" + list);
		return list;
	}
	
	
	


	@Override
	public long DataTablecategoryDataTotalCount(String Search, String category) {
		String SearchValue = GenerateQueryWhereClause_SQL(Search, category);
		int total = 0;
		String q = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
//			q = "select count(*) \n" + " from tb_mstr_gender" + SearchValue;
			q = "select count(*) \n" + " from tb_caste_category where id!=0 " + SearchValue;
			PreparedStatement stmt = conn.prepareStatement(q);
			
			stmt = setQueryWhereClause_SQL(stmt, Search, category);
			System.err.println("for count------"+stmt);
		
//
//			System.err.println("ddddddddddddddddddddddd]]]]]]]]]]"+stmt);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return (long) total;
	}
	
	public String GenerateQueryWhereClause_SQL(String Search, String category) {
		String SearchValue = "";
		if (Search!=null && !Search.equals("")) { // for Input Filter
			SearchValue += " and (  upper(category) like ? )";
			System.err.println("globalllll search"+SearchValue);
		
		}
		
		///advance search
	
	
	   if(!category.trim().equals("")){
			SearchValue += " and upper(category) like ? ";
			System.err.println("parameter search"+SearchValue);
		
	
	    }


		return SearchValue;
	}

	public PreparedStatement setQueryWhereClause_SQL(PreparedStatement stmt, String Search, String category) {
		int flag = 0;
		try {
			if (Search!=null &&  !Search.equals("")) {
				
				flag += 1;
				stmt.setString(flag, "%" + Search.toUpperCase() + "%");
			
				
			}
			
			if (!category.equals("") && category != null) {
				flag += 1;
				stmt.setString(flag,"%"+category.toUpperCase()+"%");
			}
			
			
			/*
			 * if (!status.equals("") && status != null) { flag += 1; stmt.setInt(flag,
			 * Integer.parseInt(status)); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}

		return stmt;
	}

	@Override
	public TB_CASTE_CATEGORY_MASTER getCastcategoryByid(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		TB_CASTE_CATEGORY_MASTER updateid = (TB_CASTE_CATEGORY_MASTER) session.get(TB_CASTE_CATEGORY_MASTER.class, id);
		session.getTransaction().commit();
		session.close();
		return updateid;
		
	}
	
	
	
	 

}
