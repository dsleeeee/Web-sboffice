<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon">
  <div ng-controller="regEnvstCtrl">
    <%-- 조회조건 --%>
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <button class="btn_blue fr" id="nxBtnSearch1" ng-click="_broadcast('regEnvstCtrl')">
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
          <th><s:message code="envstHqMsMng.orgn"/></th>
          <td>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectHqStoreS.jsp" flush="true">
              <jsp:param name="targetId" value="selectHqStore"/>
            </jsp:include>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="wj-TblWrap mt20 mb20 w50 fl">
      <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
        <span><s:message code="envstHqMsMng.regEnvst" /></span>
        <button class="btn_skyblue ml5 fr" ng-click="del()"><s:message code="cmm.del" /></button>
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
              <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envstHqMsMng.envstCd"/>" binding="envstCd" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envstHqMsMng.envstNm"/>" binding="envstNm" align="left" width="*" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envstHqMsMng.orgnCd"/>" binding="orgnCd" align="center" width="70" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="envstHqMsMng.orgnFg"/>" binding="orgnFg" align="center" width="70" visible="false"></wj-flex-grid-column>
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

  <div class="wj-TblWrap mt20 mb20 w50 fr" ng-controller="noRegEnvstCtrl">
    <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
      <div class="sb-select dkbr mb10 oh">
        <s:message code="envstHqMsMng.noRegEnvst"/>
        <button class="btn_skyblue ml5 fr" ng-click="save()"><s:message code="cmm.add"/></button>
      </div>

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
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="envstHqMsMng.envstCd"/>" binding="envstCd" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="envstHqMsMng.envstNm"/>" binding="envstNm" align="left" width="*" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="envstHqMsMng.orgnCd"/>" binding="orgnCd" align="center" width="70" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="envstHqMsMng.orgnFg"/>" binding="orgnFg" align="center" width="70" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/cd/envstHqMsMng/envstHqMsMng.js?ver=20230306.01" charset="utf-8"></script>
