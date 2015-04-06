<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link href="<c:url value="/bootstrap/css/bootstrap.css"/>" media="screen" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>Mambo Converter</title>
	<script type="text/javascript" src='<c:url value="/jquery/jquery.min.js" />'></script>
	<script type="text/javascript" src='<c:url value="/bootstrap/js/bootstrap.min.js" />'></script>
	<%-- <script type="text/javascript" src='<c:url value="/mamboApp/main.js" />'></script> --%>
</head>
<body>

	<div class="container">
		<div class="header clearfix">
			<nav>
			<ul class="nav nav-pills pull-right">
				<li role="presentation" class="active"><a href='<c:url value="/" />'>Home</a></li>
				<li role="presentation"><a href="<c:url value="/about" />">About</a></li>
				<li role="presentation"><a href="<c:url value="/contact" />">Contact</a></li>
			</ul>
			</nav>
			<h3 class="text-muted">Mambo Converter</h3>
		</div>

		<div class="jumbotron">
			<h1>Mambo Converter</h1>
			<p class="lead">Convert documents from .xls to json</p>
				<form action="<c:url value="/transformToJson"/>" method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label for="file">File</label>
						<input type="file" name="file" id="file" class="btn btn-info"/>
						<input type="submit" value="XForm!" class="btn btn-success"/>
					</div>
				</form>
				<p class="lead">Convert documents from .xls to aef</p>
				<form action="<c:url value="/transformToAEF"/>" method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label for="file">File</label>
						<input type="file" name="file" id="file" class="btn btn-info"/>
						<input type="submit" value="XForm!" class="btn btn-success"/>
					</div>
				</form>
		</div>

		<footer class="footer">
		<p>Endeios.io 2015</p>
		</footer>

	</div>
	<!-- /container -->
</body>
</html>