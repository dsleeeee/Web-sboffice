<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/base/store/storage/"/>

<div class="subCon3" ng-controller="storageListCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('storageListCtrlSrch')">
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
        <%-- 창고코드 --%>
        <th><s:message code="storageManage.storageCd" /></th>
        <td>
          <input type="text" id="srchStorageCd" class="sb-input w100" ng-model="storageCd" maxlength="3" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 창고명 --%>
        <th><s:message code="storageManage.storageNm" /></th>
        <td>
          <input type="text" id="srchStorageNm" class="sb-input w100" ng-model="storageNm" maxlength="30" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
        <%-- 사용여부 --%>
        <th><s:message code="storageManage.useYn" /></th>
        <td colspan="3">
          <div class="sb-select">
          	<div class="txtIn">
            	<wj-combo-box
                    id="srchUseYn"
                    ng-model="useYn"
                    control="useYnAllCombo"
                    items-source="_getComboData('useYnAllComboData')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            	</wj-combo-box>
            </div>
          </div>
        </td>
      </tr>
      </tbody>
  </table>

  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScale"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
    </wj-combo-box>
	<div class="txtIn fr">
	    <%-- 신규창고등록 --%>
	    <button class="btn_skyblue" ng-click="regStoragePopup()"  >
	      <s:message code="storageManage.addStorage" />
	    </button>
	
	    <%-- 엑셀 다운로드 --%>
	    <button class="btn_skyblue" ng-click="excelDownloadStorage()">
	       <s:message code="cmm.excel.down" />
	    </button>
    </div>
  </div>

  <%-- 창고목록 그리드 --%>
  <div  id="wjWrapType1" class="w100 mt10">
    <div class="wj-gridWrap">
      <wj-flex-grid
              control="flex"
              autoGenerateColumns="false"
              selection-mode="Row"
              initialized="initGrid(s,e)"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="storageManage.storageCd"/>"   binding="storageCd" width="90"   align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storageManage.storageNm"/>"   binding="storageNm" width="200"  align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storageManage.useYn"/>"       binding="useYn"     data-map="useYnDataMap" width="100"  align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storageManage.lastModId"/>"   binding="empNm"     width="150"  align="center" is-read-only="true" format="d"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storageManage.lastModDate"/>" binding="modDt"     width="200"  align="center" is-read-only="true"></wj-flex-grid-column>
       </wj-flex-grid>
	   <%-- ColumnPicker 사용시 include --%>
	   <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	       <jsp:param name="pickerTarget" value="storageListCtrl"/>
	   </jsp:include>
	   <%--// ColumnPicker 사용시 include --%>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storageListCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>



<script type="text/javascript" src="/resource/solbipos/js/base/store/storage/storage.js?ver=20200929.01" charset="utf-8"></script>

<%-- 창고 등록 레이어 --%>
<c:import url="/WEB-INF/view/base/store/storage/pop/regStorage.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 창고 정보 레이어 --%>
<c:import url="/WEB-INF/view/base/store/storage/pop/modStorage.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>