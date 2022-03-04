<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" id="sideMenuSoldOutView" ng-controller="soldOutChgCtrl" style="display: none;">
  <%--searchTbl--%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl"><s:message code="soldOut.sideMenu" /></a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="nxBtnSearch2" ng-click="_pageView('sideMenuSoldOutCtrl',1)">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>

    <c:if test="${orgnFg == 'HQ'}">
    <table class="searchTbl">
      <colgroup>
        <col class="w15" />
        <col class="w35" />
        <col class="w15" />
        <col class="w35" />
      </colgroup>
      <tbody>
        <th><s:message code="soldOut.store" /></th>
        <td>
            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
          <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
            <jsp:param name="targetId" value="sideMenuSoldOutStore"/>
          </jsp:include>
        </td>
      </tr>
      </tbody>
    </table>
    </c:if>
    <div class="oh sb-select dkbr mt20">
      <button class="btn_blue ml5 fr" id="btnSoldOutYnSave" ng-click="save()"><s:message code="cmm.save" /></button>
    </div>

    <%-- 일괄적용 --%>
    <table class="searchTbl mt10">
      <colgroup>
        <col class="w15" />
        <col class="w15" />
        <col class="w20" />
        <col class="w15" />
        <col class="w15" />
        <col class="w20" />
      </colgroup>
      <tbody>
      <tr class="brt">
        <%-- 판매상품여부 --%>
        <th>
          <s:message code="soldOut.soldOutYn" />
        </th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="srchSoldOutYnChg"
                    ng-model="soldOutYnChg"
                    items-source="_getComboData('soldOutYnComboChg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </div>
        </td>
        <%-- 일괄적용 --%>
        <td>
          <a href="#" class="btn_grayS ml10" ng-click="batchChange()"><s:message code="prodBatchChange.batchChange" /></a>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="w40 fl mt10">
    <div>
      <%--위즈모 테이블--%>
      <div id="gridPrint" class="wj-TblWrapBr pd5" style="height: 240px;" ng-controller="sideMenuSoldOutCtrl">
        <div class="updownSet oh mb5">
          <span class="fl bk lh30"><s:message code='sideMenu.selectMenu.sdselGrp' /></span>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div style="height:170px">
          <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter"
            ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselGrpCd"/>" binding="sdselGrpCd" width="70" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselGrpNm"/>" binding="sdselGrpNm" width="*" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="cnt" width="*" visible="false"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <div>
      <%--위즈모 테이블--%>
      <div id="gridMapng" class="wj-TblWrapBr pd5" style="height: 240px;" ng-controller="sideMenuSelectClassCtrl">
        <div class="updownSet oh mb10" style="height:60px;">
          <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselClass' /><span id="sideSelectGroupTitle"></span> </span>
            </button>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div style="height:130px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter"
            ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassCd"/>" binding="sdselClassCd" width="70" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselClassNm"/>" binding="sdselClassNm" width="*" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="sdselQty" width="50" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="cnt" width="*" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.sdselQty"/>" binding="fixProdCnt" width="*" visible="false"></wj-flex-grid-column>
            <%--<wj-flex-grid-column header="순서" binding="dispSeq" width="50"></wj-flex-grid-column>--%>
          </wj-flex-grid>
        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    </div>
    <div class="w60 fl mt10">
    <div>
      <%--위즈모 테이블--%>
      <div id="gridMapng" class="wj-TblWrapBr ml10 pd5" style="height: 480px;" ng-controller="sideMenuSelectProdCtrl">
        <div class="updownSet oh mb10" style="height:60px;">
          <span class="fl bk lh30" style="white-space: nowrap;"><s:message code='sideMenu.selectMenu.sdselProd' /><span id="sideClassTitle"></span> </span>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div style="height:370px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter"
            ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodCd"/>" binding="prodCd" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodNm"/>" binding="prodNm" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="soldOut.soldOutYn"/>" binding="soldOutYn" width="80" data-map="soldOutYnDataMap" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.addProdUprc"/>" binding="addProdUprc" width="50" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.addProdQty"/>" binding="addProdQty" width="50" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.fixProdFg"/>" binding="fixProdFg" width="50" data-map="fixProdFgDataMap" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>

        </div>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    </div>
  </div>
<script>
    var orgnFg = "${orgnFg}";
</script>
  <script type="text/javascript" src="/resource/solbipos/js/base/prod/soldOut/sideMenuSoldOut.js?ver=20220228.01" charset="utf-8"></script>

  <%-- 레이어 팝업 : 상품선택 --%>
  <c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenuProdView.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
  </c:import>
