package com.BisagN.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.BisagN.dao.CategoryDAO;
import com.BisagN.models.TB_CASTE_CATEGORY_MASTER;

import freemarker.core.ParseException;

@Controller
@RequestMapping(value = { "admin", "/", "user" })
public class CasteCategoryController {

//	private static final String Cdao = null;
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory rc) {
		this.sessionFactory = rc;
	}

	@Autowired
	private CategoryDAO Pdao;

	@RequestMapping(value = "/CastecategoryUrl", method = RequestMethod.GET)
	public ModelAndView CastecategoryUrl(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) {
		try {

			Mmap.put("msg", msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("CastecategoryTiles");

	}

	// ====================================save/edit================================//

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/cat_mstrAction", method = RequestMethod.POST)
	public ModelAndView cat_mstrAction(@Valid @ModelAttribute("cat_mstrCMD") TB_CASTE_CATEGORY_MASTER rc,
			BindingResult result, HttpServletRequest request, ModelMap model, HttpSession session) {
		if (rc.getCategory() == null || rc.getCategory().equals("")) {
			model.put("msg", "Please Enter Category Name. ");
			return new ModelAndView("redirect:CastecategoryUrl");
		}

		try {
			int id = rc.getId() > 0 ? rc.getId() : 0;
			Date date = new Date();
//					System.out.println("id==="+id);
			String username = session.getAttribute("username").toString();
			Session sessionHQL = this.sessionFactory.openSession();
			Transaction tx = sessionHQL.beginTransaction();

			try {

				Query q0 = sessionHQL.createQuery(
						"select count(id) from TB_CASTE_CATEGORY_MASTER where upper(category)=:category and id!=:id");
				q0.setParameter("category", rc.getCategory().toUpperCase());
				q0.setParameter("id", id);
				Long c = (Long) q0.uniqueResult();

				if (id == 0) {
//						System.out.println("in if----------");
					rc.setCategory_createdby(username);
					rc.setCategory_createddate(date);
					if (c == 0) {
//								System.out.println("hellooooooooooooooooo----");
						sessionHQL.save(rc);
						sessionHQL.flush();
						sessionHQL.clear();
						model.put("msg", "Data Saved Successfully.");

					} else {
						model.put("msg", "Data already Exist.");
					}
				} else {
//								System.out.println("abccccc----------");

					rc.setCategory_updatedby(username);
					rc.setCategory_updateddate((java.sql.Date) date);
					if (c == 0) {
						// String msg = cate.updatecategory(rc);
						// model.put("msg", msg);
					} else {
						model.put("msg", "Data already Exist.");
					}
				}
				tx.commit();
			} catch (RuntimeException e) {
				try {
					tx.rollback();
					model.put("msg", "roll back transaction");
				} catch (RuntimeException rbe) {
					model.put("msg", "Couldn?t roll back transaction " + rbe);
				}
				throw e;
			} finally {
				if (sessionHQL != null) {
					sessionHQL.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:CastecategoryUrl");
	}

	@PostMapping("/getFiltercategory_data")

	public @ResponseBody List<Map<String, Object>> getFilterRegistration_data(int startPage, int pageLength,

			String Search, String orderColunm, String orderType, String category, HttpSession sessionUserId) {

		return Pdao.DataTablecategoryDataList(startPage, pageLength, Search, orderColunm, orderType, category);

	}

	@PostMapping("/getTotalcategory_dataCount")

	public @ResponseBody long getTotalRegistration_dataCount(HttpSession sessionUserId, String Search,
			String category) {
		return Pdao.DataTablecategoryDataTotalCount(Search, category);

	}
	
	
	
	@RequestMapping(value = "/Edit_categoryUrl")
	public ModelAndView Edit_categoryUrl(@ModelAttribute("id1") String updateid, ModelMap Mmap,
			@RequestParam(value = "msg", required = false) String msg, Authentication authentication,
			HttpSession sessionEdit) {

		TB_CASTE_CATEGORY_MASTER Category_Details = Pdao.getCastcategoryByid(Integer.parseInt(updateid));
//                    Mmap.put("Edit_StateCMD", stateDetails);
//                    Mmap.put("country_id", mcommon.getMedCountryName("", sessionEdit));
//                    Mmap.put("getStatusMasterList", mcommon.getStatusMasterList());
		Mmap.addAttribute("msg", msg);
		Mmap.put("Category_Details", Category_Details);
		return new ModelAndView("EditcastcategoryTiles");
	}

	@RequestMapping(value = "/edit_Category_Action", method = RequestMethod.POST)
	public ModelAndView edit_Category_Action(@ModelAttribute("edit_CategoryCMD") TB_CASTE_CATEGORY_MASTER rs,
			HttpServletRequest request, ModelMap model, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, RedirectAttributes ra) throws ParseException {

		String username = session.getAttribute("username").toString();

		int id = Integer.parseInt(request.getParameter("id"));
		String category = request.getParameter("category").trim();
		String status = request.getParameter("status");

//           System.err.println("prefix---"+prefix+"---"+status+ "id"+id);

//                    if (rs.getCountry_id() == 0 || rs.getCountry_id() == null || rs.getCountry_id().equals(null)) {
//                            TB_STATE stateDetails = State_dao.getstateByid((id));
//                            model.put("Edit_StateCMD", stateDetails);
//                            model.put("country_id", mcommon.getMedCountryName("", session));
//                            model.put("getStatusMasterList", mcommon.getStatusMasterList());
//                            model.put("msg", "Please Select Country");
//                            //model.put("id2", id);
//                            return new ModelAndView("EditStateTiles");
//                    }
//    
//                    if (state_name.equals("") || state_name.equals("null") || state_name.equals(null)) {
//                            TB_STATE stateDetails = State_dao.getstateByid((id));
//                            model.put("Edit_StateCMD", stateDetails);
//                            model.put("country_id", mcommon.getMedCountryName("", session));
//                            model.put("getStatusMasterList", mcommon.getStatusMasterList());
//                            model.put("msg", "Please Enter State");
////                            model.put("id2", id);
//                            return new ModelAndView("EditStateTiles");
//                    }
//                    
//                    if (rs.getStatus() == "inactive" || rs.getStatus().equals("inactive")) {
//                            TB_STATE stateDetails = State_dao.getstateByid((id));
//                            model.put("Edit_StateCMD", stateDetails);
//                            model.put("country_id", mcommon.getMedCountryName("", session));
//                            model.put("getStatusMasterList", mcommon.getStatusMasterList());
//                            model.put("msg", "Only Select Active Status.");
//                            return new ModelAndView("EditStateTiles");
//    
//                    }
//                    
		Session session1 = this.sessionFactory.openSession();
		Transaction tx = session1.beginTransaction();
		try {
			Query q0 = session1.createQuery(
					"select count(id) from TB_CASTE_CATEGORY_MASTER where category=:category and status=:status and id !=:id");
			q0.setParameter("category", category);

			q0.setParameter("status", status);

			q0.setParameter("id", id);

			Long c = (Long) q0.uniqueResult();

			if (c == 0) {
				String hql = "update TB_CASTE_CATEGORY_MASTER set category=:category,status=:status,category_updatedby=:category_updatedby,category_updateddate=:category_updateddate"
						+ " where id=:id";

				Query query = session1.createQuery(hql).setParameter("category", category).setParameter("status", status)
						.setParameter("category_updatedby", username).setParameter("category_updateddate", new Date())
						.setParameter("id", id);
				msg = query.executeUpdate() > 0 ? "1" : "0";
				tx.commit();

				if (msg.equals("1")) {
//                                     	System.err.println("----------msg");
					ra.addAttribute("msg", "Data Updated Successfully.");
				} else {
					ra.addAttribute("msg", "Data Not Updated.");
				}
			} else {
				ra.addAttribute("msg", "Data already Exist.");
			}

		} catch (RuntimeException e) {
			try {
				tx.rollback();
				ra.addAttribute("msg", "roll back transaction");
			} catch (RuntimeException rbe) {
				ra.addAttribute("msg", "Couldnï¿½t roll back transaction " + rbe);
			}
			throw e;

		} finally {
			if (session1 != null) {
				session1.close();
			}
		}
		return new ModelAndView("redirect:CastecategoryUrl");
	}
	
	
	
	

	@PostMapping(value = "/deletecategory_Url")
	public ModelAndView deletecategory_Url(@ModelAttribute("id2") int id, BindingResult result, RedirectAttributes ra,
			HttpServletRequest request, ModelMap model, HttpSession session1) {

//				String roleid = session1.getAttribute("roleid").toString();
//				  Boolean val = roledao.ScreenRedirect("", roleid); 
//				  if (val ==false) 
//				  { return new ModelAndView("redirect:403"); } 

		List<String> liststr = new ArrayList<String>();

		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String username = session1.getAttribute("username").toString();
		try {
//				    System.err.println("id----------"+id);
			int app = session.createQuery(
					"update TB_CASTE_CATEGORY_MASTER set category_updatedby=:category_updatedby,category_updateddate=:category_updateddate,status=:status where id=:id")
					.setParameter("id", id).setParameter("category_updatedby", username)
					.setParameter("category_updateddate", new Date()).setParameter("status", "2").executeUpdate();

			// int app = session.createQuery("delete from TB_MSTR_GENDER where
			// id=:id").setParameter("id", id).executeUpdate();
			tx.commit();
			session.close();
			if (app > 0) {
				liststr.add("Data Deleted Successfully.");
			} else {
				liststr.add("Data not Deleted.");
			}
			ra.addAttribute("msg", liststr.get(0));
		} catch (Exception e) {
			liststr.add("CAN NOT DELETE THIS DATA BECAUSE IT IS ALREADY IN USED.");
			ra.addAttribute("msg", liststr.get(0));
		}
		return new ModelAndView("redirect:CastecategoryUrl");
	}

}
