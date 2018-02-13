<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

sampleView-View 

<br>
<br>
1
<br>


${sessionScope.sessionInfo.fixMenu[0].activation}
<br>

<br>
1
<br>
<br>


<button id="registBtn" type="button" class="btn btn_dark btn_md w_lg" onclick="popOpen2();">
  팝업2
</button>
<button id="registBtn" type="button" class="btn btn_dark btn_md w_lg" onclick="popOpen3();">
  팝업3
</button>
<button id="registBtn" type="button" class="btn btn_dark btn_md w_lg" onclick="popOpen4();">
  팝업4
</button>

<br>

<a href="exRedis.sb">레디스 이동</a>
<br>

<a href="sample3.sb">이동</a>
<br>
<a href="sample2.sb">샘플 2 이동</a>
<br>
<button id="registBtn" type="button" class="btn btn_dark btn_md w_lg" onclick="ajaxTest();">
  Ajax
</button>
<br>
<br>
<a href="sampleInput.sb">Input 샘플</a>
<br>
<br>
<br>
<a href="sampleInput2.sb">Input 샘플2</a>
<br>
<div id="theTree2"></div>
<br>
<br>
<br>
<script>

function popOpen() {
  $.open("samplepop.sb", { method:'get', width:'800px', height:'390px' } );  
}


function ajaxTest() {
  var paramStr = "";
  
  $.postJSON( "/samplejson.sb", paramStr, function( result ){
    var s = "result : " + result.status + ", dataSize : " + result.data.length;
    alert(s);
  })
  // 오류발생 시 
  .fail(function(){
    alert("Ajax Fail");
  });
}

</script>

<script>
onload = function() {
  
  function getDataTest() {
      var test = ${sessionScope.sessionInfo.menuData};
      var currentMenu = "${sessionScope.sessionInfo.currentMenu.resrceCd}";
      return test;
  }
  
  var tree = new wijmo.nav.TreeView('#theTree2', {
    itemsSource: getDataTest(),
    displayMemberPath: 'header',
    childItemsPath: 'items',
    expandOnClick : true,
    isContentHtml: true,
    loadedItems: function(s, e) {
    console.log("loadedItems...");
    s.collapseToLevel(10);
    }
  });
}
  
</script>



