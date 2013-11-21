<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Twitter DB2 Connector</title>
<link rel="stylesheet" type="text"
	href="<c:url value="/resources/css/bootstrap.css" />">
</head>

<body
	style="color: #2E2E2E; background-image: url('http://fc06.deviantart.net/fs71/f/2012/108/8/0/light_blue_windows_8_bubbles_background_by_gifteddeviant-d4wkhej.png');">
	<img src="<c:url value="/resources/images/logo.png" />" alt="logo">
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="<c:url value="/resources/js/bootstrap.js" />"> </script>

	<script>
    //checked
    var checked = $('input[type=checkbox]:checked').val() != undefined;
    
    //unchecked
    var unchecked = $('input[type=checkbox]:checked').val() == undefined;
    
    $(function() {
      $('#storeDBCheckBox').click(function() {
                        var satisfied = $('#storeDBCheckBox:checked').val();
                            if (satisfied != undefined){
                            $('#db2PropertiesFileTextbox').removeAttr('disabled');
                            $('#collectionTextBox').removeAttr('disabled');
                            }
                         
                            else {
                            $('#db2PropertiesFileTextbox').attr('disabled', 'disabled');
                            $('#collectionTextBox').attr('disabled', 'disabled');
                            }
                        });
      });
    </script>
	<script>
        function stoppedTyping(v){
            if(v.length > 0) {
                $('#submitbtn').removeAttr('disabled');
            } else {
                $('#submitbtn').attr('disabled','disabled');
            }
        }
    </script>
	<script>
        function submitEnable(v){
            if(v.length > 0) {
                $('#querySubmit').removeAttr('disabled');
            } else {
                $('#querySubmit').attr('disabled','disabled');
            }
        }
    </script>
	<script>
    function doSubmitAction() {
    	var propertiesFileName = $('#db2PropertiesFileTextbox').val();
    	//document.write(propertiesFileName);
    	var tweetType = $("input:radio[name=tweetType]:checked").val();
    	//document.write(tweetType);
    	var keyword = $('#keyword').val();
    	//document.write(keyword);
    	var collection = $('#collectionTextBox').val();
    	//document.write(collection);
    	var storeData = $('#storeDBCheckBox').val();
    	if (storeData == 'checked') {
    		storeData = true;
    	} else {
    		storeData = false;
    	}
    	//document.write(storeData);
    	if (storeData) {
    			$.ajax({
    			url: 'dbStore?propertiesFileName='+propertiesFileName,
    			type: "POST",
    			success: function(response) {
    				$('#dbInfo').html("DB Store set");
    			},
    			error: function(e) {
    				$('#dbInfo').html('Sorry.. Couldn\'t set your DB.. Please check the configuration again.');
    			}
    		});
    	}
    	
    	$.ajax({
    		url: tweetType+'/'+keyword+'?storeData='+storeData+'&collection='+collection,
    		dataType: "json",
    		type: "GET",
    		success: function(data) {
    			var tweets = [];
    			  $.each( data, function( key, val ) {
    			    tweets.push( "<li id='" + key + "'>" + val + "</li>" );
    			  });
    			 
    			  $( "<ul/>", {
    			    "class": "my-new-list",
    			    html: tweets.join( "" )
    			  }).appendTo( '#resultTextArea' );
    			  
    			$('#dbInfo').html("Tweets being retrieved..");
    		},
    		error: function(e) {
    			$('#dbInfo').html('Oops!! Something went wrong while getting the Tweets..');
    		}
    	});
    }

    
    </script>	

	<div class="container" id="Json">
	<h1 style="color: #2E2E2E; text-align: center">JSON DB2 Connector</h1>
		<br /> <br />
		<div id="dbInfo"></div>
		<!-- <form name="dbKeywordForm" action=""
			style="margin-top: 20px; margin-left: 75px">
			<fieldset
				style="width: 400px; height: 350px; border: 2px solid #c0c0c0; padding: 1.0em 1em 0.5em;">
		 -->
		<div>
			<table style="cell-padding: 3px; cell-spacing: 2px; margin-left: 375px;">
				<tr>
					<td colspan="2">
						<p style="color: #2E2E2E;">
							<b>Please enter the type of Twitter data you want </b>
						</p>
					</td>
				</tr>
				<tr>
					<td><input style="color: #2E2E2E;" type="radio"
						name="tweetType" checked="checked" value="streamingTweets">
						StreamingData</td>
					<td><input style="color: #2E2E2E;" type="radio"
						name="tweetType" value="timelineTweets"> TimelineData</td>
				</tr>
				<tr>
					<td>ScreenName / Keyword :</td>
					<td><input style="color: #2E2E2E; width: 215px;" value=""
						type="text" id="keyword" onkeyup="stoppedTyping(this.value)"></td>
				</tr>
				<tr>
					<td colspan="2"><input id="storeDBCheckBox" type="checkbox"
						name="Store" value="DBStore"> Store in DB</td>
				</tr>
				<tr>
					<td colspan="2">Please provide the DB2 properties file path:</td>
				</tr>
				<tr>
					<td colspan="2"><input id="db2PropertiesFileTextbox"
						style="color: #2E2E2E; width: 390px; align: center"
						disabled="disabled" type="text" name="dbFileText"></td>
				</tr>
				<tr>
					<td colspan="2">
						<p style="font-style: italic; font-size: 10px;">File should
							contain jdbcURL , username , password and schema properties</p>
					</td>
				</tr>
				<tr>
					<td>Enter The Collection Name:</td>
					<td><input style="color: #2E2E2E; width: 215px;" type="text"
						id="collectionTextBox" disabled="disabled" name="collection">
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p style="font-style: italic; font-size: 10px;">
							Default value will be <b>ScreenName</b>Collection/<b>Keyword</b>Collection
						</p>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="GO"
						id="submitbtn" style="text-align: center; disabled: disabled;"
						class="btn btn-large" onclick="doSubmitAction()" /></td>
				</tr>
			</table>
		</div>
		<!-- 		</fieldset>
		</form>
 -->
		<br />
		<br />
		<br />
		<br />

		<!-- <form name="form3" style="margin-top: 100px; margin-left: 50px">
			<fieldset
				style="width: 450px; height: 650px; border: 1px solid #0404B4; padding: 0.5em 0em 0.5em; border-style: groove; box-shadow: 20px 10px 50px #4000FF, -20px -10px 50px #4000FF;">
		 -->
		 <div>
		<table style="cell-padding: 3px; cell-spacing: 2px; width:800px; height: 650px; margin-left: 175px">
			<tr>
				<td>
					<p style="color: #2E2E2E; text-align: center">
						<b>Results</b>
					</p>
				</td>
			</tr>
			<tr>
				<td><!-- <textarea id="resultTextArea" name="resultText"
						style="width: 800px; height: 400px; background-color: #E6E6FA; align: center"> -->
					<div id="resultTextArea" style="width: 800px; height: 400px; background-color: #E6E6FA; align: center">
					
					</div>
					<!-- </textarea> -->
				</td>
			</tr>
			<tr>
				<td>
					<p
						style="color: #2E2E2E; padding: 0.5em 0em 0.5em; text-align: center">
						<b>Query</b>
					</p>
				</td>
			</tr>
			<tr>
				<td><input type="text" id="queryTextBox" name="query"
					style="width: 800px; height: 100px; background-color: #E6E6FA; align: center">
				</td>
			</tr>
			<tr>
				<td align="center"><a type="submit" id="querySubmit"
					style="text-align: center; disabled: disabled; padding: 0.5em 0em 0.5em; align: center;"
					class="btn btn-large">Submit</a></td>
			</tr>
		</table>
		</div>
		<!-- 		</fieldset>
		</form>
 -->
		<br /> <br />
		<hr />
		<br /> <br />
	</div>
</body>
</html>