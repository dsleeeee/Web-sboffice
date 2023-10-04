<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="baseUrl" value="/pos/confg/verRecv/verStore/"/>

<div class="subCon" id="verStoreView" style="padding: 10px 20px 40px">
  <div class="searchBar">
    <a href="#" class="open fl">
          <c:if test="${orgnFg != 'HQ'}">${menuNm}</c:if>
          <c:if test="${orgnFg == 'HQ'}"><s:message code="verHq.verStore" /></c:if>
    </a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="nxBtnSearch3" onclick="getVersionList()">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>

  <div ng-controller="verInfoCtrl">
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
            <input type="text" id="verSerNo" name="verSerNo" ng-model="verSerNo" class="sb-input w100" size="50" onkeyup="fnNxBtnSearch('3');">
          </td>
          <%-- 버전적용명 --%>
          <th><s:message code="verRecv.verSerNm" /></th>
          <td>
            <input type="text" id="verSerNm" name="verSerNm" ng-mode="verSerNm" class="sb-input w100" size="50" onkeyup="fnNxBtnSearch('3');">
          </td>
        </tr>
        <tr>
          <%-- 수신구분 --%>
          <th><s:message code="verRecv.verRecvYn" /></th>
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
                id="progFg3"
                ng-model="progFg3"
                control="progFg3Combo"
                items-source="_getComboData('progFg3Combo')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false">
              </wj-combo-box>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <%--- left --%>
    <div class="wj-TblWrap mt10 mb20 w30 fl" style="overflow-y: hidden">
      <div class="wj-TblWrapBr mr10 pd10" style="height:470px;">
        <span><s:message code="verRecv.verInfo" /></span>
        <div class="sb-select dkbr mb10 oh">
        </div>
        <%-- 버전정보 그리드 --%>
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
              <wj-flex-grid-column header="<s:message code="verRecv.verSerNo"/>" binding="verSerNo" align="center" width="110" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="verRecv.verSerNm"/>" binding="fileDesc" align="left" width="110" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="verRecv.progFg"/>" binding="progFg" data-map="progFg3DataMap" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
          <%-- id --%>
          <ul id="verInfoCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
      </div>
    </div>
  </div>

  <%-- right --%>
  <div class="wj-TblWrap mt10 mb20 w70 fr" ng-controller="verInfoDtlCtrl" style="overflow-y: hidden;">
    <div class="wj-TblWrapBr pd10" style="height:470px;">
      <span><s:message code="verRecv.recvStore"/></span>
      <div class="sb-select dkbr mb10 oh">
      </div>
      <p class="s12 bk tl mb10 mt10" id="storeTit"></p>
      <%-- 버전별 매장수신정보 그리드 --%>
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
            <wj-flex-grid-column header="<s:message code="verRecv.verSerNo"/>" binding="verSerNo" align="center" width="140" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verRecv.storeCd"/>" binding="storeCd" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verRecv.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verRecv.posNo"/>" binding="posNo" width="75" align="center" is-read-only="true" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verRecv.verRecvFg"/>" binding="verRecvFg" data-map="verRecvFgDatMap" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verRecv.regDt"/>" binding="regDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verRecv.verRecvDt"/>" binding="verRecvDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verRecv.posIp"/>" binding="posIp" width="140" align="center" is-read-only="true"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="verInfoDtlCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>

  </div>
</div>
<script>
  var posFg  = ${cnv.getEnvCodeExcpAll("4020")};
  var verRecvFg = ${ccu.getCommCodeExcpAll("060")};
  var orgnFg = "${orgnFg}";
  var hqOfficeCd = "${hqOfficeCd}"
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verRecv/verStore.js?ver=20220822.01" charset="utf-8"></script>
