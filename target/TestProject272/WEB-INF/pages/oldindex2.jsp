<html>
<head>
<meta charset="utf-8">
<title>Twitter DB2 Connector</title>
<link rel="stylesheet" type="text" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
</head>
<script>
    function disable() {
        var limit = document.forms[0].elements.length;
        for (i=0;i<limit;i++) {
            document.forms[0].elements[i].disabled = true;
        }
    }
</script>

<script>
    var $radioButtons = $("input:radio");
    
    $radioButtons.change(function(){
                         var anyRadioButtonHasValue = false;
                         $radioButtons.each(function(){
                                            if(this.checked){
                                            anyRadioButtonHasValue = true;

                                            return false;
                                            }
                                            });
                         if(anyRadioButtonHasValue){
                         $("input[name='submit']").removeAttr("disabled");
                         }
                         else{
                         $("input[name='submit']").attr("disabled", "");
                         }
                         });
</script>
<body  style="color: #2E2E2E; background-image:url('http://fc06.deviantart.net/fs71/f/2012/108/8/0/light_blue_windows_8_bubbles_background_by_gifteddeviant-d4wkhej.png');"></style>
    <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo">
    <!-- <img src="images/TwitterIBMLogo.png" alt="logo"> -->
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"> </script>
	<div class="container" id="JSon">
	<h1 style="color:#2E2E2E;text-align:center">${message}<!-- JSON DB2 Connector --></h1>
	
    <br><br>
    <form input name="form1" action = "" style="margin-top:20px; margin-left:375px" ></style>
       <fieldset style="width: 400px; height: 250px; border: 2px solid #c0c0c0; padding: 1.0em 1em 0.5em;"></style>
           <p style="color: #2E2E2E;"><b>Please enter the type of Twitter data you want </b> </p></style>
            <input style = "color: #2E2E2E;" type="radio" name ="datatype" value = "StreamingData"> StreamingData
			<br />
            <input style = "color: #2E2E2E;" type="radio" name ="datatype" value = "TimelineData"> TimelineData
            <br />
			<tr>
       			<br><td> ScreenName/Keyword:<input style = "color: #2E2E2E;" align= type="text" name="keyWord"> </td>
           	</tr>
			<br>
			<tr>
				<br><td><input type="checkbox" name="Store" value="DBStore"> Store in DB<br></td>
				</tr>
            <br />
            <center><a input type="submit" name="submit" text-align="center" disabled = "disabled" class="btn btn-large">Submit</a></center>
       </fieldset>
    </form>
    
    <br>
    
    <form input name="form3" style="margin-top:100px; margin-left:50px"></style>
      <fieldset style="width: 1000px; height: 650px; border: 1px solid #0404B4; padding: 0.5em 0em 0.5em;border-style:groove;box-shadow:20px 10px 50px #4000FF,-20px -10px 50px #4000FF;"></style>
        <p style = "color: #2E2E2E; text-align: center"><b><u>Results</u></b></p></style>
        <input type="text" name="resultText" style = "width : 1000px; height: 400px; background-color: #E6E6FA;"></style>
        <br /><p style = "color: #2E2E2E; padding: 0.5em 0em 0.5em; text-align: center" ><b><u>Query</u></b></p>
        <input type="text" name="query" style= "width :1000px; height: 100px;background-color: #E6E6FA">
        <br /><br />
        <center><a input type="submit" name="submit" text-align="center" class="btn btn-large" padding: 0.5em 0em 0.5em;>Submit</a></center>
      </fieldset>
    </form>
    
    
            
	</div>
	</body>
	</html>