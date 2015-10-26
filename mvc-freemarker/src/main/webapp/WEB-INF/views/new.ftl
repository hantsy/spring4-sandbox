<#import "/spring.ftl" as spring/>
<#import "template.ftl" as t/>
<@t.page title="Spring MVC Sample with Freemarker">
<div class="page-header">
	<h1> Add a new task</h1>
</div>
<form class="form" role="form" action="<@spring.url '/tasks'/>" method="post">
		  
		  <@spring.bind "task.name"/>
		  <div class="form-group <#if spring.status.errorMessages?size gt 0>has-error</#if>">
		    <label for="name"> Task Name:</label>
            
		    <input id="name" 
		     type="text" 
		     class="form-control" 
             name="${spring.status.expression}"
             value="${spring.status.value?default('')}"></input>
            <div class="help-block">
                <#list spring.status.errorMessages as error> <p>${error}</p> </#list>
            </div>
		  </div>

		  <@spring.bind "task.description"/>
		  <div class="form-group <#if spring.status.errorMessages?size gt 0>has-error</#if>">
            
		    <label for="description">Description:</label>
		    <textarea 
		    id="description" 
		    class="form-control" 
		    name="${spring.status.expression}"
            value="${spring.status.value?default('')}"  
            rows="8"></textarea>
            <div class="help-block">
                <#list spring.status.errorMessages as error> <p>${error}</p> </#list>
            </div>
		  </div>
	
		 <div class="form-group">
		  	<button type="submit" class="btn btn-lg btn-primary">Add Task</button>
		 </div>
</form>
</@t.page>