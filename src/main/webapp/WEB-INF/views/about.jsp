<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/bootstrap/css/bootstrap.css"/>"
	media="screen" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Mambo Converter</title>
<script type="text/javascript"
	src='<c:url value="/jquery/jquery.min.js" />'></script>
<script type="text/javascript"
	src='<c:url value="/bootstrap/js/bootstrap.min.js" />'></script>
</head>
<body>

	<div class="container">
		<div class="header clearfix">
			<nav>
				<ul class="nav nav-pills pull-right">
					<li role="presentation" ><a
						href='<c:url value="/" />'>Home</a></li>
					<li role="presentation" class="active"><a href="<c:url value="/about" />">About</a></li>
					<li role="presentation"><a href="<c:url value="/contact" />">Contact</a></li>
				</ul>
			</nav>
			<h3 class="text-muted">Mambo Converter</h3>
		</div>

		<div class="container-fluid">
			<div class="row">
				<div class="col-md-6">
					<h2>About Mambo</h2>
					<p>
					Mambo is a simple excel to json transformer, that takes the first row
					of the various sheet of the input excel file and returns a list of jsonObjects
					made by the name of the sheet and the array of json objects,
					that takes properties from columns and data from rows.
					<a href="<c:url value="/res/testMambo.xls"/>">this is an example</a> document 
					</p>
				</div>
			</div>

		</div>

		<footer class="footer">
			<p>Endeios.io 2015</p>
		</footer>

	</div>
	<!-- /container -->
</body>
</html>