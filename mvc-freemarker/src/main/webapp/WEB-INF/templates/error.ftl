<#import "/spring.ftl" as spring/>
<#import "template.ftl" as t/>
<@t.page title="Spring MVC Sample with Freemarker">
<div class="page-header">
	<h1> Oops, something is wrong...</h1>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-default">
		  <!-- Default panel contents -->
		  <div class="panel-heading"><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>  Error Details</div>
		  <div class="panel-body">
		    <p>${ex}</p>
		  </div>	
		</div>	
	</div> 
</div>
</@t.page>
