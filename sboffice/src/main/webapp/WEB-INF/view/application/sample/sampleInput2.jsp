<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%-- 
<link rel="stylesheet" type="text/css" href="/resource/vender/wijmo/css/wijmo.min.css" />
<script type="text/javascript" src="/resource/vender/wijmo/js/angular.js"></script>
<script type="text/javascript" src="/resource/vender/wijmo/js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="/resource/vender/wijmo/js/wijmo.min.js"></script>
<script type="text/javascript" src="/resource/vender/wijmo/js/wijmo.nav.min.js"></script>
<script type="text/javascript" src="/resource/vender/wijmo/js/wijmo.input.js"></script>
<script type="text/javascript" src="/resource/vender/wijmo/js/wijmo.angular.js"></script>
 --%>
<%-- 
<script type="text/javascript" src="http://fiddle.jshell.net/Wijmo5/9vxtfaf3/show/light/js/lib/dummy.js"></script>
<link rel="stylesheet" type="text/css" href="http://fiddle.jshell.net/Wijmo5/9vxtfaf3/show/light/css/result-light.css">
 --%>
<link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="http://cdn.wijmo.com/5.latest/styles/wijmo.min.css">
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.min.js"></script>
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.input.min.js"></script>
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.grid.min.js"></script>
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.grid.filter.min.js"></script>
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.gauge.min.js"></script>

<style type="text/css">
label {
  width: 90px;
  text-align: right;
  margin-right: 6px;
}

.wj-combobox {
  width: 140px;
  margin-bottom: 6px;
}

body {
  margin-bottom: 24pt;
}

.wj-flexgrid {
  max-height: 250px;
  margin-bottom: 12px;
}
.header {
  display: inline-block;
  width: 150px;
  text-align: right;
  font-weight: normal;
}
</style>


<div class="container">
  <h1>
     매장조회
  </h1>
  <!-- 
  <label for="btnShow">Show</label>
  <button id="btnShow" class="btn btn-primary">Show Popup</button>
   -->
  <br/>
  <br/>
  <div class="row">

    <div class="col-xs-6">
      <!-- important options -->
      <label for="header">매장명</label>
      <input id="shopNm">
      <!--  //TODO 
      1. 여기에 autocomplete 걸고
      2. 조회시 아래 grid에 조회 결과
      3. grid에 체크박스 추가해서 체크 후 저장
       -->
      <button id="btnShow" class="btn btn-primary" onclick="searchStore();">조회</button>
      <br/>
    </div>
    <div id="theGrid">
    <br>
    <button id="btnShow" class="btn btn-primary" onclick="searchStore();">저장</button>
  </div>
</div>

<script>


function searchStore() {
  //var shopNm = document.getElementById('shopNm').value;
  var paramStr = "shopNm="+document.getElementById('shopNm').value;

  $.postJSON( "/sampleInput2Res.sb", paramStr, function( result ){
    var s  = "result : " + result.status + ", dataSize : " + result.data.length;
    alert(s);
    
    if(result.data.length > 0){
      //var rData = result.data;
      var data = [];
      for(var i=0; i<result.data.length; i++) {
        data.push({
          active: i % 5 != 0,
          shopCd : result.data[i].shopCd,
          shopNm : result.data[i].shopNm
        });
      }
      
      var grid = new wijmo.grid.FlexGrid('#theGrid', {
        itemsSource : data
        , columns: [
          { binding: 'active',  header: '선택'},
          { binding: 'shopCd',  header: '매장코드'},
          { binding: 'shopNm',  header: '매장명'}
          ]
      });
    }
  })
  // 오류발생 시 
  .fail(function(){
    alert("Ajax Fail");
  });

}
</script>