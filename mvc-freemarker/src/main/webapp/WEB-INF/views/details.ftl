<#import "/spring.ftl" as spring/>
<#import "template.ftl" as t/>
<@t.page title="Spring MVC Sample with Freemarker">
<div class="page-header">
	<h1> Task Details (#${details.id})</h1>
</div>

<dl class="dl-horizontal">
  <dt> Task Name:</dt>
  <dd> ${details.id}</dd>
  <dt> Task Description:</dt>
  <dd> ${details.description}</dd>
  <dt> Status:</dt>
  <dd> ${details.status}</dd>
  <dt> Created At:</dt>
  <dd> ${details.createdDate}</dd>
  <dt> Last Modified At:</dt>
  <dd> ${details.lastModifiedDate}</dd>
</dl>
</@t.page>