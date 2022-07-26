<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="vendrCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('vendrCtrl',1)" id="nxBtnSearch">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
            <tr>
                <%-- 거래처코드 --%>
                <th><s:message code="vendr.vendrCd" /></th>
                <td><input type="text"  class="sb-input w100" id="vendrCd" ng-model="vendrCd" onkeyup="fnNxBtnSearch();"/></td>
                <%-- 거래처명 --%>
                <th><s:message code="vendr.vendrNm" /></th>
                <td><input type="text"  class="sb-input w100" id="vendrNm" ng-model="vendrNm" onkeyup="fnNxBtnSearch();"/></td>
            </tr>
            <tr>
                <%-- 거래처구분 --%>
                <th><s:message code="vendr.vendorFg" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="vendorFg"
                                ng-model="vendorFg"
                                items-source="_getComboData('vendorFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <th></th>
                <td></td>
            </tr>
        </tbody>
    </table>

    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScaleBox"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>

      <%-- 거래처 등록 --%>
      <button class="btn_skyblue fr" id="btnRegVendr" ng-click="regVendr()">
        <s:message code="vendr.reg" />
      </button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="vendr.vendrCd"/>" binding="vendrCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="vendr.vendrNm"/>" binding="vendrNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="vendr.bizNo"/>" binding="bizNo" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="vendr.vendorFg"/>" binding="vendorFg" data-map="vendorFgDataMap"  width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="vendr.ownerNm"/>" binding="ownerNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="vendr.telNo"/>" binding="telNo" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="vendr.addr"/>" binding="addr" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="vendr.useYn"/>" binding="useYn" data-map="useYnDataMap"  width="90" is-read-only="true" align="center"></wj-flex-grid-column>

                <%--팝업 조회시 필요
                <wj-flex-grid-column header="<s:message code="board.boardCd"/>" binding="boardCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="board.boardSeqNo"/>" binding="boardSeqNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="board.userId"/>" binding="userId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>--%>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="vendrCtrl"/>
            </jsp:include>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="vendrCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript">

  var vendorFg    = ${ccu.getCommCodeSelect("011")};
  var vendorFgNm = ${ccu.getCommCodeExcpAll("011")};
  var useYn      = ${ccu.getCommCodeExcpAll("067")};

</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/vendr/vendr.js?ver=20220726.01" charset="utf-8"></script>

<%-- 거래처관리 상세 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/vendr/info.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 거래처관리 신규등록,수정 팝업 거래처등록탭 --%>
<c:import url="/WEB-INF/view/base/prod/vendr/regist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 거래처관리 신규등록,수정 팝업 취급상품탭 --%>
<c:import url="/WEB-INF/view/base/prod/vendr/trtmnt.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>