<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" /> -->
<link rel="stylesheet" type="text/css" href="http://cdn.wijmo.com/5.latest/styles/wijmo.min.css">

<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.min.js"></script>
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.nav.min.js"></script>


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
exTreeStore
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
  <button id="btnChkAll" class="btn btn-default">전체선택</button>
  <button id="btnUnChkAll" class="btn btn-default">전체선택해제</button>
  <br>
  <br>
  <button id="btnSelStore" class="btn btn-default">매장선택</button>
  <br>
  <br>
  <div id="theTree"></div>
</div>

<script>
onload = function() {
  var rdata = ${shopList};
  var data = rdata;
  var newData = {name : "shopList", children : [] },
      levels  = ["hdShopCdNm", "shopCdNm"];

  /* 계층구조 json으로 변경 */
  data.forEach(function(d) {
     var depthCursor = newData.children;
     levels.forEach(function(property, depth) {
       var index;
       depthCursor.forEach(function(child, i){
         if( d[property] == child.name ) index = i;
         else { }
       });
       
       if( isNaN(index) ){
         var shopFg = (depth == 0 ? "H" : "S");
         if(depth == 2) {
           depthCursor.push({name : d[property], shopFg : shopFg , hdShopCd : d['hdShopCd'], shopCdNm : d['shopCdNm'] , shopCd : d['shopCd'] , shopNm : d['shopNm'] , children : []});
           index = depthCursor.length - 1;
         }
         else {
           depthCursor.push({name : d[property], children : []});
           index = depthCursor.length - 1;
         }
       }
       depthCursor = depthCursor[index].children;
       if( depth == levels.length - 1) {
       }
     });
   });
   
  var menuData = newData.children;
  
  /* 트리 생성 */
  var tree = new wijmo.nav.TreeView('#theTree', {
    itemsSource:  menuData,
    displayMemberPath: 'name',
    childItemsPath: 'children',
    showCheckboxes : true,
    expandOnClick : true,
    loadedItems: function(s, e) {
      //console.log("loadedItems...");
      s.collapseToLevel(10);
    }
  });
  
  /* 트리접기 */
  document.getElementById('btnCollapse').addEventListener('click', function () {
    tree.collapseToLevel(0);
  });
  
  /* 트리펼치기 */
  document.getElementById('btnExpand').addEventListener('click', function () {
    tree.collapseToLevel(1000);
  });
  //checkAllItems(check: boolean)
  
  /* 트리펼치기 */
  document.getElementById('btnChkAll').addEventListener('click', function () {
    tree.checkAllItems(true);
  });
  
  /* 트리펼치기 */
  document.getElementById('btnUnChkAll').addEventListener('click', function () {
    tree.checkAllItems(false);
  });
  
  /* 매장선택 */
  document.getElementById('btnSelStore').addEventListener('click', function () {

    console.log(':::: btnSelStore :::: ');
    var items = tree.checkedItems; 
    console.log('items : '+ JSON.stringify(items));

    if(JSON.stringify(items) == '[]') {
      alert('매장을 선택해주세요.');
      return false;
    }
    
  });
  
}
</script>
