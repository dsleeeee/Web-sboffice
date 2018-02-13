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

  </div>

</nav>

<script>
  var tree = new wijmo.nav.TreeView('#theTree', {
    itemsSource : getData(),
    displayMemberPath : 'header',
    childItemsPath : 'items',
    isContentHtml : true,
    loadedItems : function(s, e) {
      s.collapseToLevel(0);
    }
  });

  // get the tree data
  function getData() {
    return [ {
      header : '테스트 메뉴',
      items : [ {
        header : '그리드',
        items : [ {
          header : '<a href="/sampleGrid.sb">그리드 샘플 이동(json)</a>'
        }, {
          header : '<a href="/sampleGrid2.sb">그리드 샘플 이동(test)</a>'
        }, {
          header : '<a href="/exGridPage.sb">그리드 페이징 샘플 이동</a>'
        }, {
          header : '<a href="/exGridPage2.sb">그리드 페이징 샘플 이동2</a>'
        }, {
          header : '<a href="/exGridHeader.sb?rnum=1000">그리드 헤더 번역 샘플 이동</a>'
        }, {
          header : '<a href="/exInput.sb">INPUT 테스트</a>'
        }, {
          header : '<a href="/exTree.sb">Tree 테스트</a>'
        }, {
          header : '<a href="/sampleGridMain.sb">그리드 샘플</a>'
        } ]
      } ]
    } ];
  }
</script>




















