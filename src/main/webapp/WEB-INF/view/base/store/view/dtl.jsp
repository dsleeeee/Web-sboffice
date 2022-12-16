<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="storeInfoViewLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;height:570px;" fade-in="false" fade-out="false">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="storeInfoViewCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeManage.storeInfo" />
      <span id="storeViewInfoTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">
      <div style="height:400px; overflow-y: auto;">
        <h3 class="h3_tbl"><s:message code="storeView.basicInfo" /></h3>
        <table class="searchTbl">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
          <tr>
            <%-- 매장코드---%>
            <th><s:message code="storeView.storeCd" /></th>
            <td>{{storeInfo.storeCd}}</td>
            <%-- 매장명---%>
            <th><s:message code="storeView.storeNm" /></th>
            <td>{{storeInfo.storeNm}}</td>
          </tr>
          <tr>
            <th><s:message code="storeView.bizStoreNm" /></th>
            <td>{{storeInfo.bizStoreNm}}</td>
            <th><s:message code="storeView.ownerNm" /></th>
            <td>{{storeInfo.ownerNm}}</td>
          </tr>
          <tr>
            <th><s:message code="storeView.bizNo" /></th>
            <td>{{storeInfo.bizNo}}</td>
            <th><s:message code="storeView.areaNm" /></th>
            <td>{{storeInfo.areaNm}}</td>
          </tr>
          <tr>
            <th><s:message code="storeView.telNo" /></th>
            <td>{{storeInfo.telNo}}</td>
            <th><s:message code="storeView.faxNo" /></th>
            <td>{{storeInfo.faxNo}}</td>
          </tr>
          <tr>
            <th><s:message code="storeView.mpNo" /></th>
            <td>{{storeInfo.mpNo}}</td>
          </tr>
          <tr>
            <th><s:message code="storeView.emailAddr" /></th>
            <td>{{storeInfo.emailAddr}}</td>
            <th><s:message code="storeView.hmpgAddr" /></th>
            <td><a href="#" class="link">{{storeInfo.hmpgAddr}}</a></td>
          </tr>
          <tr>
            <th><s:message code="storeView.address" /></th>
            <td colspan="3">{{storeInfo.address}}</td>
          </tr>
          <tr>
            <%-- 용도구분---%>
            <th><s:message code="storeView.clsFgNm" /></th>
            <td>{{storeInfo.clsFgNm}}</td>
            <%-- 매장상태---%>
            <th><s:message code="storeView.sysStatFg" /></th>
            <td>{{storeInfo.sysStatFgNm}}</td>
          </tr>
          <tr>
            <%-- 설치포스수--%>
            <th><s:message code="storeView.posCnt" /></th>
            <td colspan="3">{{storeInfo.posCnt}} 대</td>
          </tr>
          <tr ng-if="storeInfo.sysStatFg == '2'">
            <th><s:message code="storeView.sysOpenDate" /></th>
            <td>{{storeInfo.sysOpenDate}}</td>
            <th><s:message code="storeView.sysClosureDate" /></th>
            <td ng-if="storeInfo.sysStatFg !== '2'"><s:message code="storeView.openStore"/></td>
            <td ng-if="storeInfo.sysStatFg === '2'">{{storeInfo.sysClosureDate}}</td>
          </tr>
          <tr>
            <th><s:message code="storeView.agencyNm" /></th>
            <td>{{storeInfo.agencyNm}}</td>
            <th><s:message code="storeView.vanNm" /></th>
            <td>{{storeInfo.vanNm}}</td>
          </tr>
          </tbody>
        </table>

        <h3 class="h3_tbl"><s:message code="storeView.bigo" /></h3>
        <table class="searchTbl">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="storeView.sysRemark" /></th>
            <td colspan="3">{{storeInfo.sysRemark}}</td>
          </tr>
          <tr> <%-- //todo 본사에서 입력 가능하도록 열여줘야하나  --%>
            <th><s:message code="storeView.hdRemark" /></th>
            <td colspan="3">{{storeInfo.hdRemark}}</td>
          </tr>
          <tr>
            <th><s:message code="storeView.remark" /></th>
            <td colspan="3">{{storeInfo.remark}}</td>
          </tr>
          </tbody>
        </table>
      </div>

      <div class="btnSet">
        <%-- 닫기 --%>
        <span><a href="#" class="btn_blue pd20" id="btnClose" ng-click="close()"><s:message code="cmm.close" /></a></span>
      </div>
    </div>

  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/base/store/view/dtl.js?ver=2018112001" charset="utf-8"></script>
