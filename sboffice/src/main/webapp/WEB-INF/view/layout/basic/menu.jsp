<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 왼쪽 메뉴 --%>

  <!--로고영역-->
  <h1>
    <a href="/" class="on">
      <span><img src="/resource/solbipos/css/img/logo_main.png" alt="" /></span>
    </a>
  </h1>
  <!-- 활성화 : class="on" -->
  <!--//로고영역-->

  <!--전체,즐겨찾기-->
  <div class="menuTab">
    <p class="all">
      <a href="#" id="_all" class="on">
        <span>전체</span>
      </a>
    </p>
    <p class="favorite">
      <a href="#" id="_favorite" >
        <span>즐겨찾기</span>
      </a>
    </p>

    <!--close : 즐겨찾기 메뉴 (1차 depth만 노출하고 클릭하면 2차, 3차 각각 노출한다.)-->
    <div class="fahoMenu">
        <ul id="fahoMenuList"></ul>
    </div>
    <!--//close : 즐겨찾기 메뉴-->
   
  </div>
  <!--//전체,즐겨찾기-->

  <div class="menuTree">
    <!-- 즐겨찾기한 메뉴가 없을때--> 
    <div class="faMenu" id="faMenu" style="display:none;">
      <p class="btn_faManage"><a href="#">즐겨찾기 관리</a></p> <!-- 즐겨찾기 관리 버튼은 즐겨찾기 있어도 들어감 -->
      <p class="txt">즐겨찾기한 메뉴가 없습니다.<br /><br />‘즐겨찾기 관리’ 버튼을 클릭하시면<br />즐겨찾기 메뉴와 고정메뉴를<br />설정할 수 있습니다.</p>
    </div>
    <!--//즐겨찾기한 메뉴가 없을때-->
    
    <!--위즈모 메뉴-->
    <div id="theTreeAll" style="display:block;"></div>
    <div id="theTreeBkmk" style="display:none;"></div>
    <!--//위즈모 메뉴-->
    
    <!--닫혔을때의 메뉴 : hover하면 기본 2depth까지 보이고 2depth클릭하면 3depth펼쳐지게 해주세요-->
    <div class="smallMenu" id="smallMenu">
        <ul id="smallMenuList"></ul>
    </div>
    <!--//닫혔을때의 메뉴-->
  </div>

<script>

// 선택된 메뉴의 메뉴코드와 부모메뉴의 메뉴코드
var cResrce = "";
var pResrce = "";

//var cResrce = "000173";
//var pResrce = "000030";

// 메뉴 트리 생성
var allMenuTree;
var bmkMenuTree;
var smallMenu = "N";
var smallFaho = "N";

// 현재 선택된 메뉴 depth 저장
var sel1Depth = 0;
var sel2Depth = 0;
var sel3Depth = 0;


// 전체 메뉴 데이터 조회
function getAllMenu(){
  var data = ${sessionScope.sessionInfo.menuData};
  return data;
}

// 즐겨찾기 데이터 조회
function getbmkMenu(){
  var data = ${sessionScope.sessionInfo.bkmkMenuData};
  return data;
}

onload = function() {
  
  createAllMenu();

  $(".menuTab .all").click(function() {
    $("#_all").addClass("on");
    $("#_favorite").removeClass();
    
    $("#faMenu").hide();
    
    if(!allMenuTree) createAllMenu();
    
    $("#theTreeAll").show();
    $("#theTreeBkmk").hide();

  });

  $(".menuTab .favorite").click(function() {
    $("#_all").removeClass();
    $("#_favorite").addClass("on");

    console.log("즐겨찾기 on : "+ bmkMenuTree)
    
    // 메뉴가 열려있을때만 보여줘야 함.
    // 메뉴 닫혔을때는 보여주는 즐겨찾기 아이콘 hover하면 작은 메뉴로 보임.
    if($("#_nav").attr("class") == "menuOpen"){
      $("#faMenu").show();
      if(!bmkMenuTree)  createBmkMenu();
      $("#theTreeAll").hide();
      $("#theTreeBkmk").show();
    }
  });
  
 
  // 전체 메뉴 생성
  function createAllMenu(){

    allMenuTree = new wijmo.nav.TreeView('#theTreeAll', {
      itemsSource: getAllMenu(),
      displayMemberPath: 'header',
      childItemsPath: 'items',
      loadedItems: function(s, e) {
        s.collapseToLevel(0);
      },
      itemClicked: function(s, e){
        
        if(wijmo.format('{items}', s.selectedItem)) { 
          
          // 1depth 초기화 후 class on
          if(wijmo.format('{level1Seq}', s.selectedItem)){
            sel1Depth = wijmo.format('{level1Seq}', s.selectedItem);
            $("#theTreeAll div[wj-part=root] > .wj-node").each(function(i,e){ $(this).removeClass("on");  });
            $("#theTreeAll div[wj-part=root] > .wj-node").eq(sel1Depth).addClass("on");
          }
          
          // 2depth 초기화 후 class on 
          if(wijmo.format('{level2Seq}', s.selectedItem)){
            sel2Depth = wijmo.format('{level2Seq}', s.selectedItem);
            $("#theTreeAll div[wj-part=root] > .wj-nodelist").children('.wj-node').each(function(i, element){ $(this).removeClass("on");  });
            $("#theTreeAll div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-node').eq(sel2Depth).addClass("on");
          }
        }
        // 3depth 초기화 후 class on
        if(wijmo.format('{url}', s.selectedItem) != "") {
          sel3Depth = wijmo.format('{level3Seq}', s.selectedItem);
          // 초기화하면서 접혀야하는데?
          $("#theTreeAll div[wj-part=root] > .wj-nodelist .wj-nodelist").children('.wj-node').each(function(i, e){  $(this).removeClass("wj-state-selected"); });
          $("#theTreeAll div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-nodelist').eq(sel2Depth).children('.wj-node').eq(sel3Depth).addClass("wj-state-selected");
          location.href = wijmo.format('{url}', s.selectedItem);
        }
      }
    });
    setIcon(getAllMenu(), "theTreeAll");
    initMenu("theTreeAll");
  }

  // 즐겨찾기 메뉴 생성
  function createBmkMenu(){

    if(stringUtilObj.isEmpty(getbmkMenu())){
      $(".faMenu .txt").css("display","block");
    } else {
      $(".faMenu .txt").css("display","none");
    } 
    
    bmkMenuTree = new wijmo.nav.TreeView('#theTreeBkmk', {
      itemsSource: getbmkMenu(),
      displayMemberPath: 'header',
      childItemsPath: 'items',
      loadedItems: function(s, e) {
        s.collapseToLevel(0);
      },
      itemClicked: function(s, e){
        
        if(wijmo.format('{items}', s.selectedItem)) { 
          
          // 1depth 초기화 후 class on
          if(wijmo.format('{level1Seq}', s.selectedItem)){
            sel1Depth = wijmo.format('{level1Seq}', s.selectedItem);
            $("#theTreeBkmk div[wj-part=root] > .wj-node").each(function(i,e){  $(this).removeClass("on");  });
            $("#theTreeBkmk div[wj-part=root] > .wj-node").eq(sel1Depth).addClass("on");
          }
          
          // 2depth 초기화 후 class on 
          if(wijmo.format('{level2Seq}', s.selectedItem)){
            sel2Depth = wijmo.format('{level2Seq}', s.selectedItem);
            
            $("#theTreeBkmk div[wj-part=root] > .wj-nodelist").children('.wj-node').each(function(i, element){ $(this).removeClass("on"); });
            $("#theTreeBkmk div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-node').eq(sel2Depth).addClass("on")
          }
        }
        
        // 3depth 초기화 후 class on
        if(wijmo.format('{url}', s.selectedItem) != "") {
          sel3Depth = wijmo.format('{level3Seq}', s.selectedItem);
          // 초기화하면서 접혀야하는데?
          $("#theTreeBkmk div[wj-part=root] > .wj-nodelist .wj-nodelist").children('.wj-node').each(function(i, e){ $(this).removeClass("wj-state-selected"); });
          $("#theTreeBkmk div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-nodelist').eq(sel2Depth).children('.wj-node').eq(sel3Depth).addClass("wj-state-selected");
          location.href = wijmo.format('{url}', s.selectedItem);
        }
      } 
    });
    setIcon(getbmkMenu(), "theTreeBkmk");
    initMenu("theTreeBkmk");
  }
  
  // 메뉴 생성 후, 메뉴 아이콘 셋팅
  function setIcon(data, tree) {
    
    var iconArr = new Array(Object.keys(data).length);
    $.each(data, function(key, entry){
      if(data[key].iconNm != "" && data[key].iconNm != "null") {
        iconArr[key] = data[key].iconNm;
      }
    });
    
    $("#"+ tree +" div[wj-part=root] > .wj-node").each(function(i){
      $(this).addClass(iconArr[i]);
    });
  }
  
  // 메뉴 초기화 : 선택된 메뉴가 있을 경우, 해당 메뉴 보여줌.
  function initMenu(tree){
    if(pResrce != "" && cResrce !=""){
      var stat = false;
      var items = (tree == "theTreeAll"? getAllMenu():getbmkMenu());
      
      var pCnt = 0; // 1depth의 index 알아내기 위한 변수
      for(var i=0; i<items.length; i++){
        var item = items[i];
        pCnt = item.level1Seq;
        if(item.items){
          for(var j=0; j<item.items.length; j++){
            var item2 = item.items[j];
            if(item2.resrceCd == cResrce){ // 2depth가 마지막 depth일때
              //sel1Depth = pCnt;
              sel1Depth = i;
              sel2Depth = j;
              sel3Depth = j;
              stat = true;
            }
            if(item2.resrceCd == pResrce){ // 3depth가 마지막 depth일때
              
              //sel1Depth = pCnt;
              sel1Depth = i;
              //sel2Depth = item2.level2Seq;
              sel2Depth = j;

              for(var k=0; k<item2.items.length; k++){
                var item3 = item2.items[k];
                if(item3.resrceCd == cResrce){
                  //sel3Depth = item3.level3Seq;
                  sel3Depth = k;
                  stat = true;
                }
              }
            }
          }
        }
      }
      if(stat){
        $("#"+ tree +" div[wj-part=root] > .wj-node").eq(sel1Depth).click();
        $("#"+ tree +" div[wj-part=root] > .wj-node").eq(sel1Depth).addClass("on");
        $("#"+ tree +" div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-node').eq(sel2Depth).click();
        $("#"+ tree +" div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-node').eq(sel2Depth).addClass("on");
        $("#"+ tree +" div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-nodelist').eq(sel2Depth).children('.wj-node').eq(sel3Depth).addClass("wj-state-selected");
      }
    }
  }

  ////// 확장형 전체 메뉴와 즐겨찾기 메뉴 클릭 이벤트관련 //////
  
  // smallMenu에서 2depth 선택시 기존에 선택되었던 3depth 풀어주기
  $("#smallMenuList").on("click", "a.depth2", function(){
    $("#smallMenuList ul.depth3").each(function (i, e){
        $(this).hide();
    });
    
    if($("[pLevelId="+$(this).attr("levelId")+"]").css("display") == "block"){
      $("[pLevelId="+$(this).attr("levelId")+"]").hide();
    }else{
      $("[pLevelId="+$(this).attr("levelId")+"]").show(); 
    }
  });
  
  //확장형 즐겨찾기에서 1depth 선택
  $("#fahoMenuList").on("click", "a.depth1", function(){
    var clickId = $(this).attr("id");
    
    // 1depth 초기화
    $("#fahoMenuList li.depth1").each(function(i, e){
      if($(this).attr("cId") == clickId){
        $(this).addClass("on");
      }else{
        $(this).removeClass("on");
      }
    });

    // 2depth 초기화
    $("#fahoMenuList ul.depth2").each(function(i,e){
      if($(this).attr("pId") == clickId){
        $(this).show();
      }else{
        $(this).hide();
      }
    });

  });

  //확장형 즐겨찾기에서 2depth 선택
  $("#fahoMenuList").on("click", "a.depth2", function(){
    var clickId = $(this).attr("id");
    $("#fahoMenuList a.depth2").each(function(i,e){
      $(this).removeClass("on");
      if($(this).attr("id") == clickId){
        $(this).addClass("on"); 
      }else{
        $(this).removeClass("on");
      }
    });
    $("#fahoMenuList ul.depth3").each(function (i, e){
      if($(this).attr("pId") == clickId){
        $(this).show();
      }else{
        $(this).hide();
      }
    });
  });
}

// 확장형 메뉴 생성여부 체크
function showSmallMenu(){
  console.log("showSmallMenu");
  if(smallMenu == "N") createSmallMenu();
  if(smallFaho == "N") createSmallFaho();
  
  //initSmallMenu('A');
  initSmallMenu('F');
}

//확장형 전체 메뉴 생성
function createSmallMenu(){
  var menuData = getAllMenu();
  try{
    if(menuData.length > 0){
      $.each(menuData, function(i){
        var mData1 = menuData[i];
        var iconClass = (mData1.iconNm)? "depth1 "+mData1.iconNm : "depth1";
        var $li = $("<li/>").addClass(iconClass).appendTo("#smallMenuList");
        var $a = $("<a/>").addClass("depth1").attr("href","#").appendTo($li);
        var $span = $("<span/>").text(mData1.header).appendTo($a);
        
        if(mData1.items){
          var $ul2 = $("<ul/>").addClass("depth2").appendTo($li);
          $.each(mData1.items, function(j){
            var mData2 = mData1.items[j];
            var $li2 = $("<li/>").addClass("depth2").appendTo($ul2);
            var $a2 = $("<a/>").addClass("depth2").attr("levelId", mData2.level2Seq).attr("href","#").text(mData2.header).appendTo($li2);
            if(mData2.items){
              var $ul3 = $("<ul/>").addClass("depth3").attr("pLevelId",mData2.level2Seq).attr("style","display:none;").appendTo($li2);
              $.each(mData2.items, function(k){
                var mData3 = mData2.items[k];
                var $li3 = $("<li/>").addClass("depth3").appendTo($ul3);
                var $a3 = $("<a/>").addClass("depth3").attr("href","#").text(mData3.header).appendTo($li3);
              });
            }
          });
        }
      });
    }
    smallMenu = "Y";
  }catch(e){
    smallMenu = "N";
  }
}

//확장형 즐겨찾기 메뉴 생성
function createSmallFaho(){
  var fahoData = getbmkMenu();
  
  try{
    if(fahoData.length > 0){
      $.each(fahoData, function(i){
        var fData1 = fahoData[i];
        var iconClass = (fData1.iconNm)? "depth1 "+fData1.iconNm : "depth1";
        var $li = $("<li/>").addClass(iconClass).attr("cId",fData1.resrceCd).appendTo("#fahoMenuList");
        var $a = $("<a/>").addClass("depth1").attr("href","#").attr("id",fData1.resrceCd).appendTo($li);
        var $span = $("<span/>").text(fData1.header).appendTo($a);

        if(fData1.items){
          var $ul2 = $("<ul/>").addClass("depth2").attr("pId",fData1.resrceCd).attr("style","display:none;").appendTo($li);
          $.each(fData1.items, function(j){
            var fData2 = fData1.items[j];
            var $li2 = $("<li/>").addClass("depth2").appendTo($ul2);
            var $a2 = $("<a/>").addClass("depth2").attr("href","#").attr("id",fData2.resrceCd).text(fData2.header).appendTo($li2);
            if(fData2.items){
              var $ul3 = $("<ul/>").addClass("depth3").attr("pId",fData2.resrceCd).attr("style","display:none;").appendTo($li2);
              $.each(fData2.items, function(k){
                var fData3 = fData2.items[k];
                var $li3 = $("<li/>").addClass("depth3").appendTo($ul3);
                var $a3 = $("<a/>").addClass("depth3").attr("href","#").attr("id",fData3.resrceCd).text(fData3.header).appendTo($li3);
              });
            }
          });
        }
      });
    }
    smallFaho = "Y";
  }catch(e){
    smallFaho = "N";
  }
}

// 확장형 메뉴 선택된 메뉴가 있을 경우 선택 초기화
function initSmallMenu(menuType){
  console.log("initSmallMenu");
  
  if(pResrce != "" && cResrce !=""){
    if(menuType == "A"){  // 전체메뉴
    } else { // 즐겨찾기 메뉴
  
      console.log("pResrce : "+ pResrce + ", cResrce : "+ cResrce);
      $("#fahoMenuList li.depth1").each(function(i,e){
//        if()
  
      });
    }
  }
}

////////////////////////////////////////// StringUtil //////////////////////////////////////////

// check if the character or object is empty 

var stringUtil = function(){
  this.startObject = null;
  this.endObject = null;
  this.args = null;
}


stringUtil.prototype.isEmpty = function(value){
  if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
    return true;
  } else {
    return false;
  }
}

/* 
var isEmpty = function(value){
}
 */
String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};

var stringUtilObj = new stringUtil();


////////////////////////////////////////// NumberUtil //////////////////////////////////////////
Number.prototype.zf = function(len){return this.toString().zf(len);};

////////////////////////////////////////// DateUtil //////////////////////////////////////////

var dateUtil = function(){
  this.startObject = null;
  this.endObject = null;
  this.args = null;
}

dateUtil.prototype.formatLen = function(str){
  return str = (""+str).length<2 ? "0"+str : str;
}

dateUtil.prototype.formatDate = function(dateStr, f) {
  if(!this.valueOf()) return " ";
  
  var weekName = ["일요일","월요일","화요일","수요일","목요일","금요일","토요일"]
  var y = parseInt(dateStr.substr(0,4));
  var m = parseInt(dateStr.substr(4,2));
  var d = parseInt(dateStr.substr(6,2));

  var date = new Date(y, m, d);
  return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1){
    switch($1) {
      case "yyyy" : return date.getFullYear();
      case "yy" : return (date.getFullYear()%1000).zf(2);
      case "MM" : return (date.getMonth()+1).zf(2);
      case "dd" : return date.getDate().zf(2);
      case "E" : return weekName[date.getDay()];
      case "HH" : return date.getHours().zf(2);
      case "hh" : return ((h=date.getHours()%12)? h : 12).zf(2);
      case "mm" : return date.getMinutes().zf(2);
      case "ss" : return date.getSeconds().zf(2);
      case "a/p" : return d.getHours() < 12? "오전" : "오후";
      default : return $1;
    }
  }); 
} 

var dateUtilObj = new dateUtil();

</script>









