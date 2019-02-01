<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/outstockReqDate/reqDateCopy/"/>


<div id="reqDateCopyView" class="subCon" style="display: none;">
  <div ng-controller="reqDateCopyCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open">${menuNm}</a>
    </div>
    <table class="searchTbl">
      <colgroup>
        <col class="w15"/>
        <col class="w35"/>
        <col class="w15"/>
        <col class="w35"/>
      </colgroup>
      <tbody>
      <tr>
        <%-- 매장 --%>
        <th><s:message code="outstockReqDate.store"/></th>
        <td colspan="3">
          <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
            <jsp:param name="targetId" value="targetSelectStore"/>
          </jsp:include>
          <%--// 매장선택 모듈 싱글 선택 사용시 include --%>
        </td>
      </tr>
      <tr>
        <%-- 요청일 복사매장 --%>
        <th><s:message code="outstockReqDate.copyStore"/></th>
        <td colspan="3">
          <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
            <jsp:param name="targetId" value="copySelectStore"/>
          </jsp:include>
          <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          <%-- 복사 --%>
          <a href="#" class="btn_grayS" ng-click="reqDateCopy()"><s:message code="cmm.save"/></a>
          <%--<button class="btn_blue" id="btnSave" ng-click="reqDateCopy();"><s:message code="cmm.save" /></button>--%>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="mt10 pdb20 oh bb">
      <%-- 조회 --%>
      <button class="btn_blue fr" id="btnSearch" ng-click="search();">
        <s:message code="cmm.search"/></button>
    </div>
  </div>

  <div class="w100" ng-controller="reqDateCopyDaysCtrl">
    <%--위즈모 테이블--%>
    <div class="theGrid mt10" style="height: 120px;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeNm"/>" binding="storeNm" width="*" align="left" format="cDate" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>" binding="ownerNm" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sysStatFg"/>" binding="sysStatFg" width="50" align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.orderCloseYn"/>" binding="orderCloseYn" width="80" align="center" data-map="orderCloseYnMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sun"/>" binding="sun" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.mon"/>" binding="mon" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.tue"/>" binding="tue" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.wed"/>" binding="wed" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.thu"/>" binding="thu" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.fri"/>" binding="fri" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sat"/>" binding="sat" width="50" align="center" is-read-only="true" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.remark"/>" binding="daysRemark" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="reqDateCopyCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <div class="w100" ng-controller="reqDateCopySpecificCtrl">
    <%--위즈모 테이블--%>
    <div class="theGrid mt10" style="height: 300px;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="false"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>" binding="ownerNm" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.sysStatFg"/>" binding="sysStatFg" width="50" align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.specificDate"/>" binding="specificDate" width="100" align="center" format="date" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.specificDateRemark"/>" binding="specificDateRemark" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockReqDate.outstockReqYn"/>" binding="outstockReqYn" width="70" align="center" data-map="outstockReqYnMap" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="reqDateCopyCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/outstockReqDate/reqDateCopy.js?ver=20181224.01" charset="utf-8"></script>


