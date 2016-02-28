<#import "/spring.ftl" as spring/>
<#macro page title>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
  	<title>${title?html}</title>

	<link href="<@spring.url '/webjars/bootstrap/3.3.5/css/bootstrap.min.css'/>" rel="stylesheet"
	              media="screen"/>
	<link href="<@spring.url '/webjars/font-awesome/4.3.0/css/font-awesome.min.css'/>" rel="stylesheet"
	              media="screen"/>	
    <link href="<@spring.url '/css/main.css'/>" rel="stylesheet"/> 
    <link href="<@spring.url '/webjars/bootstrap-material-design/0.3.0/css/material.min.css'/>" rel="stylesheet"/>
    <link href="<@spring.url '/webjars/bootstrap-material-design/0.3.0/css/ripples.min.css'/>" rel="stylesheet"/>
    <link href="<@spring.url '/webjars/bootstrap-material-design/0.3.0/css/roboto.min.css'/>" rel="stylesheet"/>

    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js'/>"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js'/>"></script>
    <![endif]-->
  </head>

  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<@spring.url '/tasks'/>"> Spring MVC Sample with Freemarker</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="<@spring.url '/tasks'/>">Home</a></li>
            <li><a href="<@spring.url '/tasks/new'/>"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> New Task</a></li>
          </ul>
        </div>
      </div>
    </nav>
    <div id="main" class="container">
    	<#if flashMessage??>
    	<div class="alert alert-${flashMessage.type} alert-dismissible" role="alert">
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		  <p>${flashMessage.text}</p>
		</div>
		</#if>
		<#nested>
    </div>
    <script type="text/javascript" src="<@spring.url '/webjars/jquery/2.1.3/jquery.min.js'/>"></script>
	<script type="text/javascript" src="<@spring.url '/webjars/bootstrap/3.3.5/js/bootstrap.min.js'/>"></script>	
	<script type="text/javascript" src="<@spring.url '/webjars/bootstrap-material-design/0.3.0/js/material.min.js'/>"></script>  
    <script type="text/javascript" src="<@spring.url '/webjars/bootstrap-material-design/0.3.0/js/ripples.min.js'/>"></script>    	
    <script>
   		$.material.init();
    </script>
  </body>
</html>
</#macro>