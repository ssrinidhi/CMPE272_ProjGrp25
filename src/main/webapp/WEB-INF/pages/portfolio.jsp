<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title> TWi-DB </title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet">

    <!-- Add custom CSS here -->
    <link href="<c:url value="/resources/css/stylish-portfolio.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/font-awesome/css/font-awesome.min.css" />" rel="stylesheet">
  </head>

  <body>
  
    <!-- Full Page Image Header Area -->
    <div id="top" class="header">
      <div class="vert-text">
        <h1>${message}</h1>
        <h3><em>We</em> Connect your dreams<em>You</em> Please dream</h3>
        <p><br><br><br>Twitter is an online social networking and microblogging service that enables users to send and read "tweets", which are of type JSON objects.</p>
        <p>JavaScript Object Notation, is an open standard format that uses human-readable text to transmit data objects consisting of attribute–value pairs.</p>
        <p>DB2® JSON enables developers to write applications using a popular JSON-oriented query language created by MongoDB to interact with data stored in IBM® DB2 for Linux®, UNIX®, and Windows®. </p>
        
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-md-offset-3 text-center">
                    <h2>Need a connector to connect your twitter tweets to the the DB2 JSON store?</h2>
                    <p class="lead">We will help you with it.</p>
                </div>
            </div>
        </div>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 text-center">
            <h3>Find out more </h3>
            <a href="index" class="btn btn-lg btn-default">Get Started</a>
        </div>
    </div>
</div>
      </div>
    </div>
    <!-- /Full Page Image Header Area -->
  
  
    <!-- Footer -->
    <footer>
      <div class="container">
        <div class="row">
          <div class="col-md-6 col-md-offset-3 text-center">
            <div class="top-scroll">
              <a href="#top"><i class="fa fa-circle-arrow-up scroll fa-4x"></i></a>
            </div>
            <hr>
            <p>Copyright &copy; Twi-DB 2013</p>
          </div>
        </div>
      </div>
    </footer>
    <!-- /Footer -->

    <!-- Bootstrap core JavaScript -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.js" />"></script>

    <!-- Custom JavaScript for the Side Menu and Smooth Scrolling - Put in a custom JavaScript file to clean this up -->
    <script>
        $("#menu-close").click(function(e) {
            e.preventDefault();
            $("#sidebar-wrapper").toggleClass("active");
        });
    </script>
    <script>
        $("#menu-toggle").click(function(e) {
            e.preventDefault();
            $("#sidebar-wrapper").toggleClass("active");
        });
    </script>
    <script>
      $(function() {
        $('a[href*=#]:not([href=#])').click(function() {
          if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') 
            || location.hostname == this.hostname) {

            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
            if (target.length) {
              $('html,body').animate({
                scrollTop: target.offset().top
              }, 1000);
              return false;
            }
          }
        });
      });
    </script>

  </body>

</html>