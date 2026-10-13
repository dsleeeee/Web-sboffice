<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 매장영업시간관리 사용여부 - 본사 팝업 (좌:사용 / 우:미사용, 각 그리드 헤더 버튼으로 이동) --%>
<wj-popup id="wjBizHourUseYnLayer" control="wjBizHourUseYnLayer" show-trigger="Click" hide-trigger="Click" style="width:900px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="bizHourUseYnPopCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      매장영업시간관리 사용여부
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body">

      <%-- 조회 : 매장코드 / 매장명 --%>
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="bizHour.storeCd"/></th>
          <td><input type="text" id="useYnSearchStoreCd" class="w100"/></td>
          <th><s:message code="bizHour.storeNm"/></th>
          <td><input type="text" id="useYnSearchStoreNm" class="w100"/></td>
        </tr>
        </tbody>
      </table>
      <div class="mt10 tr">
        <button class="btn_skyblue" ng-click="searchUseYn()"><s:message code="cmm.search"/></button>
      </div>

      <div class="mt20 oh">

        <%-- 사용 --%>
        <div class="w50 fl" ng-controller="bizHourUseYnUseCtrl">
          <div class="wj-TblWrap mr10" style="height:405px; overflow-y:hidden;">
            <div class="oh mb10">
              <span class="fl bk lh20 s14">사용</span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="moveToUnuse()">미사용</a></span>
            </div>
            <div style="height:370px;">
              <wj-flex-grid
                  autoGenerateColumns="false"
                  control="flex"
                  initialized="initGrid(s,e)"
                  sticky-headers="true"
                  selection-mode="Row"
                  items-source="data"
                  item-formatter="_itemFormatter">
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bizHour.storeCd"/>" binding="storeCd" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bizHour.storeNm"/>" binding="storeNm" width="*" is-read-only="true"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>

        <%-- 미사용 --%>
        <div class="w50 fr" ng-controller="bizHourUseYnUnuseCtrl">
          <div class="wj-TblWrap ml10" style="height:405px; overflow-y:hidden;">
            <div class="oh mb10">
              <span class="fl bk lh20 s14">미사용</span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="moveToUse()">사용</a></span>
            </div>
            <div style="height:370px;">
              <wj-flex-grid
                  autoGenerateColumns="false"
                  control="flex"
                  initialized="initGrid(s,e)"
                  sticky-headers="true"
                  selection-mode="Row"
                  items-source="data"
                  item-formatter="_itemFormatter">
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bizHour.storeCd"/>" binding="storeCd" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bizHour.storeNm"/>" binding="storeNm" width="*" is-read-only="true"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/bizHour/bizHourUseYn.js?ver=20261013.01" charset="utf-8"></script>
