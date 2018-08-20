<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Redis!!</h1>
<br>

sessionId : <input id="sessionId" />
<br>
<br>

userId : <input id="userId" />

<br>
<br>
<button id="set" type="button" class="btn btn_dark btn_md w_lg" onclick="set();">
  set
</button>
<br>
<br>
<button id="get" type="button" class="btn btn_dark btn_md w_lg" onclick="get();">
  get
</button>

<br>
<br>

<div id="result"></div>

<br>
<br>

<div id="comboBox"></div>

<br><br>
<div id="comboResult"></div>

<script>

var comboData = ${ccu.getCommCode("011")};

var theComboBox = wcombo.genCommonBoxFun("#comboBox", comboData, f);

function f(s, e) {
	var r = s["selectedItem"]["name"] + " : " + s["selectedItem"]["value"];
	
	var d = theComboBox.selectedItem["name"] + " : " + theComboBox.selectedItem["value"];
	
	$("#comboResult").text(r);
}



function set() {
	var param = {};
	
	param.userId = $("#userId").val();
	
	ajaxSend("/exRedisSet.sb", param);
}


function get() {
	var param = {};
	
	param.sessionId = $("#sessionId").val();
	
	ajaxSend("/exRedisGet.sb", param);
}


function ajaxSend(url, param) {
	$.postJSON( url, param, function( result ){
		try {
		  var data = result.data;
			
			if(typeof data == "object") {
				var keys = Object.keys(data);
				var str = "";
				for ( var i in keys) {
					var item = keys[i]+ " : "+ data[keys[i]] + ", ";
					str += item;
				}
				
				$("#result").text(str);	
			}
			else {
				$("#sessionId").val(data);
			}  
		}
		catch(e) {
		  $("#loading").hide();
		}
		
		
	},
    function (result) {
      s_alert.pop(result.message);
    });
}



</script>





