<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<style>
.row {
	justify-content: center;
}
</style>






<form:form action="edit_Category_Action" method="POST" class="form-horizontal" modelAttribute="edit_CategoryCMD">
	<div class="container" align="center">
		<div class="card">
			<div class="card-header title">
				<h5>
					<span id="lbladd"></span> UPDATE CasteCategory MASTER
				</h5>
			</div>
			<div class="card-body card-block">
	            			<div class="col-md-12">	              					
	              				<div class="col-md-6">
	              					<div class="row form-group">
						               <div class="col-md-4">
						                    <label class=" form-control-label"><strong style="color: red;">* </strong>Category</label>
						                </div>
						                <div class="col-md-8">
						                <input type="text" name="category" id="category" class="form-control">
						                  
						                </div>
						            </div>
	              				</div>
	              				<div class="col-md-6">
									<div class="row form-group">
										<div class="col-md-4">
											<label class=" form-control-label"><strong style="color: red;">* </strong>STATUS</label>
										</div>
										<div class="col-md-8">
										<select name="status" id="status" class="form-control">
												<option value="1">Active</option>
												<option value="2">Inactive</option>
								
								</select>
										</div>
									</div>
								</div>		        
	              			</div>
	              			
	              			
	              			
	              			
            			</div>
			
				<input type="hidden" name="id" id="id" value="0" />
			
			<div class="card-footer" align="center">
				<a href="Edit_categoryUrl" class="btn btn-success btn-sm">Reset</a> <input
					type="submit" class="btn btn-primary btn-sm" value="Update"
					onclick="return Validation();"> 
			</div>
			

		</div>
	</div>
</form:form>



<script type="text/javascript">


$(document).ready(function() {


	
	$('#id').val('${Category_Details.id}');
	$('input#category').val('${Category_Details.category}');
	$('select#status').val('${Category_Details.status}');
});

function Validation(){
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
}

</script>


