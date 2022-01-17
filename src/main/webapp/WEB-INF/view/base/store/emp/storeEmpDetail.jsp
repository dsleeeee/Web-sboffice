<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 매장 사원 상세 팝업 --%>
<wj-popup control="storeEmpDetailLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:600px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="storeEmpDetailCtrl">

    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeEmp.storeEmpInfo"/><span>{{storeEmp.empInfo}}</span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body sc2" style="overflow-y: hidden;">

      <%-- 사원기초정보 탭 --%>
      <div class="tabType1">
        <ul>
          <%-- 사원기초정보 탭 --%>
          <li><a id="storeEmpInfoTab" href="#" class="on"><s:message code="storeEmp.storeEmpInfo" /></a></li>
          <%-- 메뉴권한 탭 --%>
          <li><a id="storeEmpMenuAuthTab" href="#" ng-click="changeTab()"><s:message code="storeEmp.menuSetting" /></a></li>
        </ul>
      </div>

      <%-- 상세 --%>
      <div style="height: 193px; overflow-y: auto;">
        <table class="tblType01">
          <colgroup>
            <col class="w25" />
            <col class="w25" />
            <col class="w25" />
            <col class="w25" />
          </colgroup>
          <tbody>
          <tr>
            <%-- 사원번호 --%>
            <th>
              <div class="impWrap"><s:message code="storeEmp.empNo" /></div>
            </th>
            <td >{{storeEmp.empNo}}</td>
            <%-- 사원명 --%>
            <th>
              <div class="impWrap"><s:message code="storeEmp.empNm" /></div>
            </th>
            <td>{{storeEmp.empNm}}</td>
          </tr>
          <tr>
            <%-- 웹사용여부 --%>
            <th>
              <div class="impWrap"><s:message code="storeEmp.webUseYn" /></div>
            </th>
            <td>{{storeEmp.webUseYnNm}}</td>
            <%-- 웹사용자ID --%>
            <th ng-if="storeEmp.webUseYn == 'Y'">
              <div class="impWrap"><s:message code="storeEmp.userId" /></div>
            </th>
            <td ng-if="storeEmp.webUseYn == 'Y'">{{storeEmp.userId}}</td>
            <th ng-if="storeEmp.webUseYn != 'Y'"></th>
            <td ng-if="storeEmp.webUseYn != 'Y'"></td>
          </tr>
          <tr>
            <%-- 휴대폰번호 --%>
            <th>
              <div class="impWrap"><s:message code="storeEmp.mpNo" /></div>
            </th>
            <td>{{storeEmp.mpNo}}</td>
            <%-- SMS수신여부 --%>
            <th>
              <div class="impWrap"><s:message code="storeEmp.smsRecvYn" /></div>
            </th>
            <td>{{storeEmp.smsRecvYnNm}}</td>
          </tr>
          <tr>
            <%-- 재직여부 --%>
            <th>
              <div class="impWrap"><s:message code="storeEmp.serviceFg" /></div>
            </th>
            <td>{{storeEmp.serviceFgNm}}</td>
            <%-- 사용여부 --%>
            <th>
              <div class="impWrap"><s:message code="storeEmp.useYn" /></div>
            </th>
            <td>{{storeEmp.useYn}}</td>
          </tr>
          <tr>
            <%-- 메인화면매출표시 --%>
            <th>
              <div class="impWrap"><s:message code="storeEmp.mainSaleFg" /></div>
            </th>
            <td>{{storeEmp.mainSaleFg}}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="wj-dialog-footer">
      <div class="btnSet">
        <%-- 수정 --%>
        <span><a href="#" class="btn_blue" ng-click="modify()"><s:message code="cmm.edit" /></a></span>
        <%-- 닫기 --%>
        <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
      </div>
    </div>

  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/storeEmpDetail.js?ver=20181121.05" charset="utf-8"></script>
