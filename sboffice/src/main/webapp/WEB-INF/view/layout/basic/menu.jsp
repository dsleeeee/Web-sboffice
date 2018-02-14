<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 왼쪽 메뉴 --%>
<nav id="_nav" class="menuOpen">

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
    <!-- 활성화 : class="on" -->
    <p class="favorite">
      <a href="#" id="_favorite">
        <span>즐겨찾기</span>
      </a>
    </p>

  </div>
  <!--//전체,즐겨찾기-->




  <div class="menuTree">

    <!--위즈모 메뉴-->
    <div>
      <div id="theTree"></div>
    </div>
    <!--//위즈모 메뉴-->
    
    <!-- 위즈모 메뉴 TODO -->
    <!-- 
    1차 depth div에 메뉴별 class 추가 : 포스관리(menu_pos), 가맹점괌리(menu_franchise), 시스템관리(menu_system), 기초관리(menu_base), 매출관리(menu_sales), 회원관리(menu_member), 수불관리(menu_receivepay), 정산관리(menu_calculate), 부가서비스(menu_addservice)
    1차 & 2차 focus일때 class name :  on 추가 (1depth, 2depth)
    -->
    <!-- 위즈모 메뉴 //TODO -->
  
  </div>
<a href="/auth/logout.sb">    로그아웃</a>
</nav>

<script>
onload = function() {
  
  function getData() {
      var menu = ${sessionScope.sessionInfo.menuData};
      //var currentMenu = "${sessionScope.sessionInfo.currentMenu.resrceCd}"; 
      return menu;
  }
  
  var tree = new wijmo.nav.TreeView('#theTree', {
    itemsSource: getData(),
    displayMemberPath: 'header',
    childItemsPath: 'items',
    loadedItems: function(s, e) {
      s.collapseToLevel(0);
    },
    itemClicked: function(s, e){
      
      var sel1Depth = 0;
      var sel2Depth = 0;
      
      if(wijmo.format('{items}', s.selectedItem)) { // 1depth, 2depth : class on 
        
        if(wijmo.format('{1depthNo}', s.selectedItem)){
          sel1Depth = wijmo.format('{1depthNo}', s.selectedItem);
          $("div[wj-part=root] > .wj-node").eq(sel1Depth).addClass("on");
        }
        if(wijmo.format('{2depthNo}', s.selectedItem)){
          sel2Depth = wijmo.format('{2depthNo}', s.selectedItem);
//          var sel2DepthChild = $("div[wj-part=root] > .wj-nodelist").eq(sel1Depth);
//          sel2DepthChild.eq(sel1Depth).(".wj-nodelist > .wj-node").children().eq(sel2Depth).addClass("on");
//            $("div[wj-part=root] > .wj-nodelist").eq(sel1Depth).eq(sel2Depth).addClass("on");
//            console.log($("div[wj-part=root] > .wj-nodelist").eq(sel1Depth).eq(sel2Depth))
/* 
          var nodeLst = $("div[wj-part=root] > .wj-nodelist").eq(sel1Depth);
          console.log('nodeLst : '+ JSON.stringify(nodeLst ));
          var nodeLst2 = nodeLst.children('.wj-node').eq(sel2Depth);
          console.log('nodeLst2 : '+ JSON.stringify(nodeLst2 ));
          $("div[wj-part=root] > .wj-nodelist").eq(sel1Depth).children(".wj-node").eq(sel2Depth).addClass("on")
            
             */
        }
        
        // 1. 1depth면 이전에 선택시 추가한 on class 삭제
        // 2. 해당 태그 찾기
        
        // 3. 해당 클래스에 on 추가
        // 4. 페이지 세로고침시 선택된 메뉴에 on
      }
      if(wijmo.format('{url}', s.selectedItem) != "") { // 3depth : url 클릭
        console.log("clicked!!!  " + wijmo.format('{url}', s.selectedItem));
        //location.href = wijmo.format('{url}', s.selectedItem);
      }
    }
  });
  
  formatSetting(getData());
  
  // 메뉴 아이콘 셋팅
  function formatSetting(data) {
    var iconArr = new Array(Object.keys(data).length);
    $.each(data, function(key, entry){
      if(data[key].iconNm != "" && data[key].iconNm != "null") {
        iconArr[key] = data[key].iconNm;
      }
    });
    $("div[wj-part=root] > .wj-node").each(function(i){
      $(this).addClass(iconArr[i]);
    });
  }
}

</script>




















