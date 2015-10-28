<#assign spring=JspTaglibs["/WEB-INF/spring.tld"]/>
<#assign form=JspTaglibs["/WEB-INF/spring-form.tld"]/>
<#import "template.ftl" as t/>
<@t.page title="Spring MVC Sample with Freemarker">
<div class="page-header">
	<h1> Edit task #${task.id}</h1>
</div>
<@spring.url var="actionUrl" value="/tasks/${task.id}"/>
<@form.form class="form" role="form" commandName="task" action="${actionUrl}" method="post">
		  <input type="hidden" name="_method" value="PUT"/>
		  
		  <div class="form-group <@spring.hasBindErrors name='task'><#if errors.hasFieldErrors('name')>has-error</#if></@spring.hasBindErrors>">
		    <label for="name"> Task Name:</label>
            
		    <@form.input 
		    	id="name" 
		     type="text" 
		     cssClass="form-control" 
             path="name"></@form.input>
            <div class="help-block">
            	<@form.errors path = "name"/>
            </div>
		  </div>

		  <div class="form-group <@spring.hasBindErrors name='task'><#if errors.hasFieldErrors('description')>has-error</#if></@spring.hasBindErrors>">
            
		    <label for="description">Description:</label>
		    <@form.textarea 
		    id="description" 
		    cssClass="form-control" 
		    path="description" 
            rows="8"></@form.textarea>
            <div class="help-block">
            	<@form.errors path = "description"/>
            </div>
		  </div>
	
		 <div class="form-group">
		  	<button type="submit" class="btn btn-lg btn-primary">Update Task</button>
		 </div>
</@form.form>
</@t.page>