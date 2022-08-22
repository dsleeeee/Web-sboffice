<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<%--<c:set var="baseUrl" value="/pos/confg/verRecv/verRecv/"/>--%>

<div class="subCon" id="verRecvView">
  <div class="searchBar">
    <a href="#" class="open fl">
      <c:if test="${orgnFg != 'HQ'}">${menuNm}</c:if>
      <c:if test="${orgnFg == 'HQ'}"><s:message code="verHq.verRecv" /></c:if>
    </a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="nxBtnSearch1" onclick="searchPosVerList();">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>

  <div>
    <table class="searchTbl">
      <colgroup>
          <col class="w15" />
          <col class="w35" />
          <col class="w15" />
          <col class="w35" />
      </colgroup>
      <tbody>
        <tr>
          <%-- 버전일련번호 --%>
          <th><s:message code="verRecv.verSerNo" /></th>
          <td>
            <input type="text" id="verSerNo" name="verSerNo" ng-model="verSerNo" size="50" class="sb-input w100" onkeyup="fnNxBtnSearch('1');">
          </td>
          <%-- 버전적용명 --%>
          <th><s:message code="verRecv.verSerNm" /></th>
          <td>
            <input type="text" id="verSerNm" name="verSerNm" ng-model="verSerNm" size="50" class="sb-input w100" onkeyup="fnNxBtnSearch('1');">
          </td>
        </tr>
        <tr>
          <%-- 수신구분 --%>
          <th><s:message code="verRecv.verRecvYnM" /></th>
          <td>
            <div class="sb-select w100">
              <wj-combo-box
                  id="verRecvYn"
                  ng-model="verRecvYn"
                  items-source="_getComboData('verRecvYnCombo')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
              </wj-combo-box>
            </div>
          </td>
          <%-- 프로그램구분 --%>
          <th><s:message code="verRecv.progFg"/></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                id="progFg"
                ng-model="progFg"
                control="progFgCombo"
                items-source="_getComboData('progFgCombo')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false">
              </wj-combo-box>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="wj-TblWrap mt20 mb20 w40 fl" ng-controller="verRecvCtrl">
      <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
        <span><s:message code="verRecv.verInfo" /></span>
        <div class="sb-select dkbr mb10 oh">
        </div>
        <%-- 버전 수신현황 그리드 --%>
        <div class="w100 mt10 mb20">
          <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    items-source="data"
                    item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="90" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="verRecv.verSerNo"/>" binding="verSerNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="verRecv.verSerNm"/>" binding="fileDesc" align="left" width="120" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="verRecv.regCnt"/>" binding="regCnt" width="75" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="verRecv.recvCntM"/>" binding="recvCnt" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="verRecv.progFg"/>" binding="progFg" data-map="progFgDataMap" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt10">
          <%-- id --%>
          <ul id="verRecvCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
      </div>
    </div>
  </div>

  <div class="wj-TblWrap mt20 mb20 w60 fr" ng-controller="verRecvStoreCtrl">
    <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
      <%--<span><s:message code="verRecv.recvStore"/></span>--%>
      <div class="sb-select dkbr mb10 oh">
        <s:message code="verRecv.recvStore"/>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
      </div>
      <p class="s12 bk tl mb10 mt10" id="storeTit"></p>

      <%-- 버전등록매장 그리드 --%>
      <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:350px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
                  control="flex"
                  autoGenerateColumns="false"
                  selection-mode="Row"
                  initialized="initGrid(s,e)"
                  items-source="data"
                  item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="90" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verRecv.storeCd"/>" binding="storeCd" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verRecv.storeNm"/>" binding="storeNm" align="left" width="*" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verRecv.posNo"/>" binding="posNo" align="center" width="70" is-read-only="true" ></wj-flex-grid-column>
            <c:if test="${orgnFg == 'HQ'}">
              <wj-flex-grid-column header="<s:message code="verRecv.verRecvFg"/>" binding="verRecvFg" data-map="verRecvFgDatMap" align="center" width="70" is-read-only="true" ></wj-flex-grid-column>
            </c:if>
            <wj-flex-grid-column header="<s:message code="verRecv.regDt"/>" binding="regDt"  align="center" width="120" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verRecv.verRecvDt"/>" binding="verRecvDt"  align="center" width="120" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt10">
        <%-- id --%>
        <ul id="verRecvStoreCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>

  <%-- 엑셀다운로드 그리드 --%>
  <div class="w100 mt10 mb20" style="display:none;" ng-controller="verRecvStoreExcelCtrl">
    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              control="excelFlex"
              autoGenerateColumns="false"
              selection-mode="Row"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              items-source="data"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="verRecv.verSerNo"/>" binding="verSerNo" align="center" width="110" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.storeCd"/>" binding="storeCd" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.storeNm"/>" binding="storeNm" align="left" width="*" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.posNo"/>" binding="posNo" align="center" width="70" is-read-only="true" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.regDt"/>" binding="regDt"  align="center" width="120" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="verRecv.verRecvDt"/>" binding="verRecvDt"  align="center" width="120" is-read-only="true"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

</div>
<script>
  var hqOfficeCd = "${hqOfficeCd}";
  var progFg      = ${ccu.getCommCode("059")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verRecv/verRecv.js?ver=20220822.01" charset="utf-8"></script>
