<#import "/spring.ftl" as spring/>
<#import "template.ftl" as t/>
<@t.page title="Spring MVC Sample with Freemarker">
<div class="page-header">
	<h1> TASK LIST</h1>
</div>
<div class="row">
	<div class="col-md-4 col-xs-12">
		<div class="panel panel-default">
		  <!-- Default panel contents -->
		  <div class="panel-heading"><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> TODO </div>
		  <div class="panel-body">
		    <p>Tasks newly added in the backlog.</p>
		  </div>
		
		  <!-- List group -->
		  <#if todotasks??>
		  	<ul id="todotasks" class="list-group">		
				<#list todotasks as task>
					<li class="list-group-item">
						<h4>#${task.id} ${task.name}
						<a href="<@spring.url '/tasks/'+task.id />">
							<span class="glyphicon glyphicon-file" aria-hidden="true"></span>
						</a>
						<a href="<@spring.url '/tasks/'+task.id+'/edit' />">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
						</a>
						</h4>
						<p> ${task.description}</p>
						<p> 						
							<form action="<@spring.url '/tasks/'+task.id+'?action=MARK_DOING'/>" method="post">
								<input type="hidden" name="_method" value="PUT"></input>
								<button type="submit" class="btn btn-sm btn-primary"><span class="glyphicon glyphicon-play" aria-hidden="true"></span> START</button>
							</form>
						</p>
					</li>	
				</#list>			
		  	</ul>
		  </#if>
		</div>		
	</div>
	
    <div id="doingtasks" class="col-md-4 col-xs-12">
		<div class="panel panel-info">
		  <!-- Default panel contents -->
		  <div class="panel-heading"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> WORK IN PROGRESS</div>
		  <div class="panel-body">
		    <p>Tasks had been assigned and started.</p>
		  </div>
		
		  <!-- List group -->
		  <#if doingtasks??>
		  	<ul id="doingtasks" class="list-group">		
				<#list doingtasks as task>
					<li class="list-group-item">
						<h4>#${task.id} ${task.name}</h4>
						<p> ${task.description}</p>
						<p> 
							<form action="<@spring.url '/tasks/'+task.id+'?action=MARK_DONE'/>" method="post">
								<input type="hidden" name="_method" value="PUT"></input>
								<button type="submit" class="btn btn-sm btn-success"><span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span> DONE</button>
							</form>
						</p>
					</li>	
				</#list>		
		  	</ul>
		  </#if>
		</div>		
	</div>
	<div id="donetasks" class="col-md-4 col-xs-12">
		<div class="panel panel-success">
		  <!-- Default panel contents -->
		  <div class="panel-heading"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> DONE</div>
		  <div class="panel-body">
		    <p>Tasks had been done successfully.</p>
		  </div>
		
		  <!-- List group -->
		  <#if donetasks??>
		  	<ul id="donetasks" class="list-group">		 
				<#list donetasks as task>
					<li class="list-group-item">
					<h4>#${task.id} ${task.name}</h4>
					<p> ${task.description}</p>
					<p> 
							<form action="<@spring.url '/tasks/'+task.id/>" method="post">
								<input type="hidden" name="_method" value="DELETE"></input>
								<button type="submit" class="btn btn-sm btn-danger"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> DELETE</button>
							</form>
					</p>
					</li>	
				</#list>			
		  	</ul>
		  </#if>
		</div>		
	</div>
</div>
</@t.page>
