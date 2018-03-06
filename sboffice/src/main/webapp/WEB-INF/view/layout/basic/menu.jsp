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

    <!--접혔을때 : 즐겨찾기 hover 메뉴 (1차 depth만 노출하고 클릭하면 2차, 3차 각각 노출한다.)-->
    <%-- 
    <div class="fahoMenu">
        <ul>
            <li class="depth1 menu_pos on"><!--메뉴별 class 추가 (하단 주석에서 class name 참고), focus중일때 on 추가-->
                <a href="#" class="depth1"><span>포스관리</span></a>
                <ul class="depth2">
                    <li class="depth2">
                        <a href="#" class="depth2 on">POS 운영관리</a><!--focus중일때 on 추가-->
                        <ul class="depth3">
                            <li class="depth3"><a href="#" class="depth3 on">미사용 라이센스 조회</a></li><!--focus중일때 on 추가 (1차, 2차, 3차 depth 모두 함께 on 처리되야함) -->
                            <li class="depth3"><a href="#" class="depth3">패스워드 임의변경</a></li>
                        </ul>
                    </li>
                    <li class="depth2"><a href="#" class="depth2">본사 / 매장마스터</a></li>
                    <li class="depth2"><a href="#" class="depth2">포스출력물관리</a></li>
                </ul>
            </li>
            <li class="depth1 menu_franchise">
                <a href="#" class="depth1"><span>가맹점괌리</span></a>
            </li>
            <li class="depth1 menu_system">
                <a href="#" class="depth1"><span>시스템관리</span></a>
            </li>
        </ul>    
    </div>
     --%>
    
    
    <!--//접혔을때 : 즐겨찾기 hover 메뉴-->

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
    <div>
      <div id="theTreeAll"  style="display:block;"></div>
      <div id="theTreeBkmk" style="display:none;"></div>
    </div>
    <!--//위즈모 메뉴-->
  </div>

<script>
onload = function() {
  

  // 선택된 메뉴의 메뉴코드와 부모메뉴의 메뉴코드
  var cResrce = "";
  var pResrce = "";
  
  //var cResrce = "000173";
  //var pResrce = "000030";
  
  var allMenuTree;
  var bmkMenuTree;
  
  showAllMenu();

  $(".menuTab .all").click(function() {
    $("#_all").addClass("on");
    $("#_favorite").removeClass();
    
    $("#faMenu").hide();
    
    if(!allMenuTree) showAllMenu();
    
    $("#theTreeAll").show();
    $("#theTreeBkmk").hide();

  });

  $(".menuTab .favorite").click(function() {
    $("#_all").removeClass();
    $("#_favorite").addClass("on");

    $("#faMenu").show();
    
    if(!bmkMenuTree)  showBmkMenu();

    $("#theTreeAll").hide();
    $("#theTreeBkmk").show();
  });
  
  
  // 현재 선택된 메뉴 depth 저장
  var sel1Depth = 0;
  var sel2Depth = 0;
  var sel3Depth = 0;

  // 전체 메뉴 
  function getAllMenu(){
    var data = ${sessionScope.sessionInfo.menuData};
    return data;
  }
  
  // 즐겨찾기
  function getbmkMenu(){
    var data = ${sessionScope.sessionInfo.bkmkMenuData};
    return data;
  }
  
  function showAllMenu(){

    //메뉴 (정리 필요)
    
    //var cResrce = ${sessionScope.sessionInfo.currentMenu.resrceCd};
    //var pResrce = "";
    
    // test case 1 : 선택된 메뉴가 없을 경우
    
    // test case 2 : 마지막 depth가 2 level
    //var cResrce = "000314"; // 선택된 메뉴
    //var pResrce = "000010"; // 선택된 메뉴의 상위 메뉴
    
    //test case 3 : 마지막 depth가 3 level
    //var cResrce = "000113"; // 선택된 메뉴
    //var pResrce = "000022"; // 선택된 메뉴의 상위 메뉴
    
    
    allMenuTree = new wijmo.nav.TreeView('#theTreeAll', {
      itemsSource: getAllMenu(),
      displayMemberPath: 'header',
      childItemsPath: 'items',
      loadedItems: function(s, e) {
        s.collapseToLevel(0);
      },
      itemClicked: function(s, e){
        if(wijmo.format('{items}', s.selectedItem)) { 
          if(wijmo.format('{level1Seq}', s.selectedItem)){
            $("div[wj-part=root] > .wj-node").eq(sel1Depth).removeClass("on");

            sel1Depth = wijmo.format('{level1Seq}', s.selectedItem);
            $("div[wj-part=root] > .wj-node").eq(sel1Depth).addClass("on");
          }
          if(wijmo.format('{level2Seq}', s.selectedItem)){
            $("div[wj-part=root] > .wj-nodelist").children('.wj-node').each(function(i, element){
              $(this).removeClass("on");
            });
            sel2Depth = wijmo.format('{level2Seq}', s.selectedItem);
            $("div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-node').eq(sel2Depth).addClass("on")
          }
        }
        if(wijmo.format('{url}', s.selectedItem) != "") { // 3depth : url 클릭
          console.log("clicked!!!  " + wijmo.format('{url}', s.selectedItem));
          //location.href = wijmo.format('{url}', s.selectedItem);
        }
      }
    });
    setIcon(getAllMenu(), "theTreeAll");
    initMenu("theTreeAll");
  }
  
  
  function showBmkMenu(){
    
    console.log('isempty : '+ stringUtilObj.isEmpty(getbmkMenu()))
    
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
          if(wijmo.format('{level1Seq}', s.selectedItem)){
            $("div[wj-part=root] > .wj-node").eq(sel1Depth).removeClass("on");  //class 초기화

            sel1Depth = wijmo.format('{level1Seq}', s.selectedItem);
            $("div[wj-part=root] > .wj-node").eq(sel1Depth).addClass("on");
          }
          if(wijmo.format('{level2Seq}', s.selectedItem)){
            $("div[wj-part=root] > .wj-nodelist").children('.wj-node').each(function(i, element){ //class 초기화
              $(this).removeClass("on");
            });
            sel2Depth = wijmo.format('{level2Seq}', s.selectedItem);
            $("div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children('.wj-node').eq(sel2Depth).addClass("on")
          }
        }
        if(wijmo.format('{url}', s.selectedItem) != "") { // 3depth : url 클릭
          console.log("clicked!!!  " + wijmo.format('{url}', s.selectedItem));
          //location.href = wijmo.format('{url}', s.selectedItem);
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
              sel1Depth = pCnt;
              sel2Depth = j;
              sel3Depth = j;
              stat = true;
            }
            if(item2.resrceCd == pResrce){ // 3depth가 마지막 depth일때
              sel1Depth = pCnt;
              sel2Depth = item2.level2Seq;
              for(var k=0; k<item2.items.length; k++){
                var item3 = item2.items[k];
                if(item3.resrceCd == cResrce){
                 sel3Depth = item3.level3Seq;
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




















