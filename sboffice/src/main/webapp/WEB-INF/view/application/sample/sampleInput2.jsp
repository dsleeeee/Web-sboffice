<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="http://cdn.wijmo.com/5.latest/styles/wijmo.min.css">
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.min.js"></script>
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.nav.min.js"></script>

<style>
pop-layer .pop-container {
  padding: 20px 25px;
}
 
.pop-layer p.searchArea {
  color: #fff;
  line-height: 40px;
  background-color:#3571B5;
  text-align:center;
  font-size:1.2em;
}
 
.pop-layer p.contentsArea {
  color: #666;
  padding: 5px;
}
 
.btn-r {
  width: 100%;
  margin: 10px 0 20px;
  text-align: right;
}
 
.pop-layer {
  display: none;
  position: absolute;
  top: 30%;
  left: 20%;
  width: 400px;
  height: auto;
  background-color: #fff;
  border: 5px solid #3571B5;
  z-index: 10;
}

a.btn-shopSearch {
  display: inline-block;
  height: 25px;
  padding: 0 14px 0;
  border: 1px solid #304a8a;
  background-color: #3f5a9d;
  font-size: 13px;
  color: #fff;
  line-height: 25px;
  margin-left:15px;
}
 
a.btn-shopSearch:hover {
  border: 1px solid #091940;
  background-color: #1f326a;
  color: #fff;
}

a.btn-layerClose {
  display: inline-block;
  height: 25px;
  padding: 0 14px 0;
  border: 1px solid #304a8a;
  background-color: #3f5a9d;
  font-size: 13px;
  color: #fff;
  line-height: 25px;
}
 
a.btn-layerClose:hover {
  border: 1px solid #091940;
  background-color: #1f326a;
  color: #fff;
}
</style>

<div>
  <h1>매장조회</h1>
  <br>
  <div>
    <input type="text" id="storeNm1" readonly="readonly">
    <button id="storeSearchPop1" class="sStoreSrch">매장단일 조회</button>
    <button id="storeSearchPop2" class="mStoreSrch">매장멀티 조회</button>
  </div>
</div>

<div id="pop"> </div>

<script>

onload = function(){
  
  var shopTreeData = [];
  var storeTree;  
  var srchFg = "S";
  
  $(".sStoreSrch").click(function(){
    srchFg = "S";
    srchStoreOpen();
  });

  $(".mStoreSrch").click(function(){
    srchFg = "M";
    srchStoreOpen();
  });
  
  /* 팝업 오픈 */
  /* 팝업 오픈 함수 수정 필요 : 파라미터로 id 명 들어가야 함 (조회 결과 리턴) */
  srchStoreOpen = function(){

    var popDiv1 = document.createElement('div');
    popDiv1.setAttribute("id", "storelayer");
    popDiv1.setAttribute("class", "pop-layer");
    
    var popDiv2 = document.createElement('div');
    popDiv2.className = "pop-container";
    
    var popDiv3 = document.createElement('div');
    popDiv3.className = "pop-conts";
    popDiv3.innerHTML =  "<div class=\"searchArea\" style=\"padding-top:10px;padding-left:15px;\"><input type=\"text\" id=\"searchStore\"><a class=\"btn-shopSearch\" id=\"srchStoreBtn\" >조회</a><a class=\"btn-shopSearch\" id=\"saveStoreBtn\" style=\"display:none;\">저장</a></div>";
    popDiv3.innerHTML += "<div class=\"contentsArea\"><div id=\"storeTree\"></div></div><div class=\"btn-r\"><a class=\"btn-layerClose\" >닫기</a></div>"

    popDiv2.appendChild(popDiv3);
    popDiv1.appendChild(popDiv2);

    $("#pop").append(popDiv1); 
    $("#storelayer").show();
    
    if(srchFg == "M") {
      $("#saveStoreBtn").show();
    }
    
    $(".btn-layerClose").click(function(){
      srchStroeClose();
    });
    
    $("#srchStoreBtn").click(function(){
      srchStore();
    });
    

    $("#saveStoreBtn").click(function(){
      saveStore();
    });
  }
  
  
  /* 팝업 닫기 */
  srchStroeClose = function(){
    $("#storelayer").hide();
    $("#searchStore").val(""); 
    if(storeTree) {
      storeTree.itemsSource = [];
    }
  }
  
  /* 매장 조회 */
  srchStore = function(){
    /* hqCd, brandCd, storeCd도 들어가야 함 */
    var paramStr = "storeNm="+document.getElementById("searchStore").value;
    
    $.postJSON( "/sampleInput2Res.sb", paramStr, function( result ){
      var s = "result : " + result.status + ", dataSize : " + result.data.length;
      
      if(result.data.length > 0) {
        if(storeTree) {
          var treeData = makeShopTree(result.data);
          storeTree.itemsSource = treeData;
          storeTree.loadTree();
        } else {
          
          if(srchFg == "S") { // 단일
            storeTree = new wijmo.nav.TreeView('#storeTree', {
              itemsSource: makeShopTree(result.data),
              displayMemberPath: 'name',
              childItemsPath: 'children',
              showCheckboxes : false , // 다중선택에 따라 true, fase값 변경
              expandOnClick : true,
              itemClicked: function(s, e) {
                if(wijmo.format('{shopFg}', s.selectedItem) == "S") { //  단일매장 선택시 
                  $("#storeNm1").val(wijmo.format('{storeNm}', s.selectedItem)); // 매장코드도 저장필요
                  // 매장코드를 value에 담을 것인지
                  srchStroeClose();
                } 
              }
            });
          } else {          // 멀티
            storeTree = new wijmo.nav.TreeView('#storeTree', {
              itemsSource: makeShopTree(result.data),
              displayMemberPath: 'name',
              childItemsPath: 'children',
              showCheckboxes : true , // 다중선택에 따라 true, fase값 변경
              expandOnClick : true,
              itemClicked: function(s, e) {
                if(srchFg == "S" && (wijmo.format('{shopFg}', s.selectedItem) == "S")) { //  단일매장 선택시 
                  $("#storeNm1").val(wijmo.format('{storeNm}', s.selectedItem)); // 매장코드도 저장필요
                  // 매장코드를 value에 담을 것인지
                  srchStroeClose();
                } 
              }
            });
          }
        }
      }
    })
    // 오류발생 시 
    .fail(function(){
      alert("Ajax Fail");
    });
  }
  
  saveStore = function(){
    console.log('chk : '+ storeTree.checkedItems);
    var returnVal = "";
    for(var i=0; i< storeTree.checkedItems.length; i++) {
      console.log(storeTree.checkedItems[i])
      if(returnVal == "") returnVal += storeTree.checkedItems[i].storeNm;
      else                returnVal += ("|"+ storeTree.checkedItems[i].storeNm);
    }
    $("#storeNm1").val(returnVal); // 매장코드도 저장필요
  }
}

/* 계층구조 json으로 변경 */
makeShopTree = function(data){

  var newData = {name : "shopList", children : [] },
      levels  = ["hqNm", "storeNm"];

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
         if(depth == 1) {
           //depthCursor.push({name : d[property], shopFg : shopFg , hdShopCd : d['hdShopCd'], shopCdNm : d['shopCdNm'] , shopCd : d['shopCd'] , shopNm : d['shopNm'] , children : []});
           depthCursor.push({name : d[property], shopFg : shopFg , hqCd : d['hqCd'], storeNm : d['storeNm'] , storeCd : d['storeCd'] , children : []});
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
  return newData.children;
}


</script>