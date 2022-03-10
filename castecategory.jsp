<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript"
	src="js/InfiniteScroll/js/datatables.mockjax.js"></script>
<script type="text/javascript"
	src="js/InfiniteScroll/new/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="js/InfiniteScroll/new/jquery.mockjax.js"></script>
<link rel="stylesheet" href="js/InfiniteScroll/css/datatables.min.css">
<form:form name="cat_mstrAction" id="cat_mstrAction" action="cat_mstrAction"
	commandName="cat_mstrCMD" method="POST">
	<div class="container" align="center">
		<div class="card">
			<div class="card-header">
				<h5>CASTE CATEGORY</h5>
			</div>
			<div class="card-body card-block cue_text">
				<div class="errMsgClient"></div>
				<div class="errMsgServer"></div>
				<div class="col-md-12" id="divLine" style="display: none;">
					<span class="line"></span>
				</div>
				<input type="hidden" id="id" name="id" value=""></input>
				<div class='col-md-6'>
					<div class='row form-group'>
						<div class='col col-md-4'>
							<label for="text-input" class="form-control-label">Category<strong
								style="color: red;">*</strong></label>
						</div>
						<div class='col-12 col-md-6'>
							<input type="text" id="category" name="category" value=""
								class="form-control" autocomplete='off'></input>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class='row form-group'>
						<div class='col col-md-4'>
							<label for="text-input" class="form-control-label">Status<strong
								style="color: red;">*</strong></label>
						</div>
						<div class='col-12 col-md-6'>
<!-- 							<input type="text" id="ac_arm_description" -->
<!-- 								name="ac_arm_description" value="" class="form-control" -->
<!-- 								autocomplete='off'></input> -->
							<select name="status" id="status" class="form-control">
<!-- 								<option value="">--select--</option> -->
								<option value="1">Active</option>
								<option value="2">Inactive</option>
								
								
							</select>
							<div class="col-md-6">
									<input type="hidden" id="id" name="id" value="0" class="form-control"
										autocomplete="off" />
								</div>

						</div>
					</div>
				</div>
			</div>
			<div class='card-footer' align='center'>
				<input type="submit" id="save_btn" class="btn btn-primary btn-sm"
					value="Save" onclick="return isValid();"> <input
					type='reset' class='btn btn-success btn-sm' value='Clear'
					onclick='clearall()'> 
					<!-- <input type='submit'
					class='btn btn-primary btn-sm' value='Update'
					onclick='return isValidateClientSide()'> -->
					 <i class="action_icons searchButton"></i><input type="button" class="btn btn-info btn-sm" id="btn-reload" value="Search">
			</div>
		</div>
	</div>
</form:form>

<div class="container-fluid" id="getcategorySearch">
	<div class="container">
<div id="divShow" style="display: block;"></div>

<div class="watermarked" data-watermark="" id="divwatermark"
	style="display: block;">
	<span id="ip"></span>
	<table id="search_category"
		class="display table no-margin table-striped  table-hover  table-bordered">
		<thead>
			<tr style="font-size: 15px;">
				<th style="width: 10%;">Ser No</th>
				<th style="text-align: center; width: 20%;">Category Name</th>
				<th style="text-align: center; width: 10%;" class='lastCol'>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${list}" varStatus="num">
				<tr style="font-size: 13px;">
					<td style="width: 10%;">${num.index+1}</td>
					<td style="text-align: center; width: 20%;">${item.category_name}</td>
					<td style="text-align: center; width: 10%;">${item.id}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</div>
</div>

<c:url value="Edit_categoryUrl" var="Edit_categoryUrl" />
<form:form action="${Edit_categoryUrl}" method="post" id="updateForm"
	name="updateForm" modelAttribute="id1">
	<input type="hidden" name="id1" id="id1" value="0" />
</form:form>


<c:url value="deletecategory_Url" var="deletecategory_Url" />
	<form:form action="${deletecategory_Url}" method="post" id="deleteForm"
		name="deleteForm" modelAttribute="id2">
		<input type="hidden" name="id2" id="id2" value="0" />
	</form:form>


<script>
function printDiv() 
{
 	var printLbl = [];
 	var printVal = [];
 	printDivOptimize('getcategorySearch','CATEGORY',printLbl,printVal,"divwatermark");
}
/* $(document).ready(function () {
	
	$("#searchInput").val("");
	 $("#searchInput").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$("#getcategorySearch tbody tr").filter(function() { 
		$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	}); */
	
// 	 if('${list.size()}' == ""){
// 		   $("div#getcitySearch").hide();
// 		 }
	/* $("div#divwatermark").val('').addClass('watermarked');	
	watermarkreport();
	document.getElementById("id").value = 0;
	var category = $("#category").val();
	$("#category").val('${category}'); */
/* 	 
}); */



$(document).ready(function() {

	mockjax1('search_category');
	table = dataTable('search_category');
	$('#btn-reload').on('click', function() {
		table.ajax.reload();
	});

});
function data(search_category) {
	//debugger;
	jsondata = [];
	var table = $('#' + search_category).DataTable();
	var info = table.page.info();
//		var currentPage = info.page;
	var pageLength = info.length;
	var startPage = info.start;
	var endPage = info.end;
	var Search = table.search();
	var order = table.order();
	//var orderColunm = $(table.column(order[0][0]).header()).attr('id').toLowerCase();
	var orderColunm = $(table.column(order[0][0]).header()).html()
			.toLowerCase();
	var orderType = order[0][1];

	//var wksp=$("#veh_id").val() ;
	var category = $("#category").val();


	$.post("getFiltercategory_data?" + key + "=" + value, {
		startPage : startPage,
		pageLength : pageLength,
		Search : Search,
		orderColunm : orderColunm,
		orderType : orderType,
		category:category
	}, function(j) {

		for (var i = 0; i < j.length; i++) {
			jsondata.push([ i + 1, j[i].category,j[i].action ]);
		}
	});
	$.post("getTotalcategory_dataCount?" + key + "=" + value, {
		category:category
	}, function(j) {
		
		

		});
}
function editData(id) {
	
	$("#id1").val(id);
	document.getElementById('updateForm').submit();
}

function deleteData(id) {
	$("#id2").val(id);
	document.getElementById('deleteForm').submit();
}


function Validation(){
	
	if ($("#category").val().trim() == "0") {
		alert("Please Enter Category ");
		$("input#category").focus();
		return false;
	}

	if ($("select#status").val() == "2") {
		alert("Only Select Active Status");
		$("select#status").focus();
		return false;
	}

	return true;
	
}
</script>
