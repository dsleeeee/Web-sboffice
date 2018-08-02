<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.2.1/angular.js"></script>
<!--     <link rel="stylesheet" type="text/css" href="/css/result-light.css">-->  
<link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="http://cdn.wijmo.com/5.latest/styles/wijmo.min.css">

<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.min.js"></script>
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.nav.min.js"></script>
<%-- 
<script src="/resource/vender/wijmo/js/wijmo.min.js"></script>
<script src="/resource/vender/wijmo/js/wijmo.nav.min.js"></script>
 --%>


<style>
.wj-treeview {
    display:block;
    height: 50%;
    font-size: 120%;
    margin-bottom: 8px;
    padding: 6px;
    background: #f0f0f0;
    box-shadow: 0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23);
}
body {
  margin-bottom: 24pt;
}
</style>


exTreeMenu

<br>

<div class="container">
  <div class="page-header">
    <h3>Create Wijmo Controls</h3>
  </div>

  <br>
  <button id="btnCollapse" class="btn btn-default">트리접기</button>
  <button id="btnExpand" class="btn btn-default">트리펼치기</button>

  <br>
  <br>
  <div id="theTree"></div>
  
  <div id="selection"></div>
  <div id="click"></div>
</div>

<script>
onload = function() {
  var rdata = ${menuList};
  var data = rdata;
  var newData = {name : "menuData", children : [] },
      levels  = ["hPgmLclsNm", "hPgmMclsNm", "hPgmNm"];

  data.forEach(function(d) {
     var depthCursor = newData.children;
     levels.forEach(function(property, depth) {
       var index;
       depthCursor.forEach(function(child, i){
         if( d[property] == child.name ) index = i;
         else { }
       });
       
       if( isNaN(index) ){
         //console.log('depth : '+ depth + ", property : "+ d['pgmFileNm']);
         if(depth == 2) {
           depthCursor.push({name : d[property] , url : d['pgmFileNm'] , children : []});
           index = depthCursor.length - 1;
         }
         else {
           depthCursor.push({name : d[property], children : []});
           index = depthCursor.length - 1;
         }
       }
       depthCursor = depthCursor[index].children;
       if( depth == levels.length - 1) {
        //depthCursor.push({ name : d.name });
       }
     });
   });
   
  var menuData = newData.children;
  
  var tree = new wijmo.nav.TreeView('#theTree', {
    itemsSource:  menuData,
    displayMemberPath: 'name',
    childItemsPath: 'children',
    showCheckboxes : false,
    expandOnClick : true,
    loadedItems: function(s, e) {
      console.log("loadedItems...");
      s.collapseToLevel(10);
    },
    itemClicked: function(s, e) {
      if( (s.selectedItem.url != undefined) && (s.selectedItem.url != null) ) {
        var msg = wijmo.format('{url}', s.selectedItem);
        //location.href = msg; // 페이지 이동
        alert("페이지 이동 : "+ msg);  
      }
    }
  });
  
  //handle collapse/expand buttons
  document.getElementById('btnCollapse').addEventListener('click', function () {
    tree.collapseToLevel(0);
  });
  document.getElementById('btnExpand').addEventListener('click', function () {
    tree.collapseToLevel(1000);
  });
}
</script>
