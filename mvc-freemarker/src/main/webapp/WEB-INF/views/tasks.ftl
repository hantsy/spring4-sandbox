<#import "/spring.ftl" as spring/>
<#import "template.ftl" as t/>
<@t.page title="Spring MVC Sample with Freemarker">
<div class="page-header">
	<h1> Task List</h1>
</div>
<div class="row">
	<div class="col-md-4 col-xs-12">
		<div class="panel panel-default">
		  <!-- Default panel contents -->
		  <div class="panel-heading">TODO List</div>
		  <div class="panel-body">
		    <p>Tasks newly added in the backlog.</p>
		  </div>
		
		  <!-- List group -->
		  <ul class="list-group">
			 <#if todotasks??>
				<#list todotasks as task>
					<li class="list-group-item">
						<h4>#${task.id} ${task.name}</h4>
						<p> ${task.description}</p>
						<p> 
							<form action="<@spring.url '/tasks/'+task.id+'?action=MARK_DOING'/>" method="post">
								<input type="hidden" name="_method" value="PUT"></input>
								<button type="submit" class="btn btn-sm btn-primary">START</button>
							</form>
						</p>
					</li>	
				</#list>
			</#if>
		  </ul>
		</div>		
	</div>
	
    <div class="col-md-4 col-xs-12">
		<div class="panel panel-info">
		  <!-- Default panel contents -->
		  <div class="panel-heading">WIP List</div>
		  <div class="panel-body">
		    <p>Tasks had been assigned and started.</p>
		  </div>
		
		  <!-- List group -->
		  <ul class="list-group">
			 <#if doingtasks??>
				<#list doingtasks as task>
					<li class="list-group-item">
						<h4>#${task.id} ${task.name}</h4>
						<p> ${task.description}</p>
						<p> 
							<form action="<@spring.url '/tasks/'+task.id+'?action=MARK_DONE'/>" method="post">
								<input type="hidden" name="_method" value="PUT"></input>
								<button type="submit" class="btn btn-sm btn-success">DONE</button>
							</form>
						</p>
					</li>	
				</#list>
			</#if>
		  </ul>
		</div>		
	</div>
	<div class="col-md-4 col-xs-12">
		<div class="panel panel-success">
		  <!-- Default panel contents -->
		  <div class="panel-heading">DONE List</div>
		  <div class="panel-body">
		    <p>Tasks had been done successfully.</p>
		  </div>
		
		  <!-- List group -->
		  <ul class="list-group">
			 <#if donetasks??>
				<#list donetasks as task>
					<li class="list-group-item">
					<h4>#${task.id} ${task.name}</h4>
					<p> ${task.description}</p>
					<p> 
							<form action="<@spring.url '/tasks/'+task.id/>" method="post">
								<input type="hidden" name="_method" value="DELETE"></input>
								<button type="submit" class="btn btn-sm btn-danger">DEL</button>
							</form>
					</p>
					</li>	
				</#list>
			</#if>
		  </ul>
		</div>		
	</div>
</div>
</@t.page>
