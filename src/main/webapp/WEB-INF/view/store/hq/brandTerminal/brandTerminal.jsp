<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="baseUrl" value="/store/hq/brandTerminal/" />

<div class="subCon">
  <%-- 터미널 사용 설정 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="$broadcast('brandTerminalCtrl')">
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
      <%-- 브랜드코드 --%>
      <th>
        <s:message code="brandTerminal.hqBrandCd" />
      </th>
      <td>
        <input type="text" class="sb-input w100" id="srchHqBrandCd" ng-model="hqBrandCd" onkeyup="fnNxBtnSearch();"/>
      </td>
      <%-- 브랜드명 --%>
      <th>
        <s:message code="brandTerminal.hqBrandNm" />
      </th>
      <td>
        <input type="text" class="sb-input w100" id="srchHqBrandNm" ng-model="hqBrandNm" onkeyup="fnNxBtnSearch();"/>
      </td>
    </tr>
    <tr>
      <%-- 사용여부 --%>
      <th><s:message code="brandTerminal.useYn" /></th>
      <td>
        <div class="sb-select">
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
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt20" >
    <div  ng-controller="brandTerminalCtrl">
      <%-- 그리드 --%>
      <div class="w100">
        <div class="wj-gridWrap" style="height:200px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
                  autoGenerateColumns="false"
                  control="flex"
                  initialized="initGrid(s,e)"
                  sticky-headers="true"
                  selection-mode="Row"
                  items-source="data"
                  item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="brandTerminal.hqBrandCd"/>" binding="hqBrandCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="brandTerminal.hqBrandNm"/>" binding="hqBrandNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="brandTerminal.useYn"/>" binding="useYn" width="80" is-read-only="true" align="center" data-map="useYnDataMap" ></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
      </div>

      <%-- 선택한 매장정보 --%>
      <div class="mt10 mb10 oh sb-select dkbr">
        <label id="lblBrandInfo"></label>
        <label id="lblHqBrandCd" style="display: none"></label>
        <label id="lblHqBrandNm" style="display: none"></label>
      </div>
      <div class="updownSet oh mb10" id="btnArea" style="display: none;">
        <%-- 추가 --%>
        <button class="btn_skyblue" ng-click="posAddRow()"><s:message code="cmm.add"/></button>
        <%-- 저장 --%>
        <button class="btn_skyblue" ng-click="posSave()"><s:message code="cmm.save"/></button>
        <%-- 삭제 --%>
        <button class="btn_skyblue" ng-click="posDel()"><s:message code="cmm.del"/></button>
      </div>
      </div>
    </div>

    <div class="wj-gridWrap" style="height:150px; overflow-y: hidden;" ng-controller="terminalCtrl" >
      <wj-flex-grid
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter"
              beginning-edit="changeVendorFg(s,e)"
              ime-enabled="true">
        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="brandTerminal.hqBrandCd"/>" binding="hqBrandCd" width="*" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="brandTerminal.vendorFg"/>" binding="vendorFg" data-map="vendorFgDataMap" is-read-only="true" width="*"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="brandTerminal.vendorFgNm"/>" binding="vendorFgNm" visible="false" width="*"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="brandTerminal.vendorCd"/>" binding="vendorNm" data-map="vanCdDataMap" width="*"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="brandTerminal.vendorCd"/>" binding="vendorCd" visible="false" width="*"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="brandTerminal.vendorTermnlNo"/>" binding="vendorTermnlNo" width="*"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="brandTerminal.vendorSerNo"/>" binding="vendorSerNo" width="*"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>

  </div>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var baseUrl = "${baseUrl}";
  var vendorFg = ${ccu.getCommCodeExcpAll("078")};
  var vandorList = ${vendorList};
  var terminalFg = ${cnv.getEnvCodeExcpAll("2028")};
  var hqOfficeCd = "${hqOfficeCd}";
  var clsFg = ${ccu.getCommCodeSelect("001")};
  var sysStatFg = ${ccu.getCommCodeSelect("005")};
  var useYn = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/hq/brandTerminal/brandTerminal.js?ver=20230706.01" charset="utf-8"></script>
