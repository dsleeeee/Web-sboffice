<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 사원 상세 팝업 --%>
<wj-popup control="systemEmpDetailLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:600px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="systemEmpDetailCtrl">

    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="systemEmp.systemEmpInfo"/><span>{{systemEmp.empInfo}}</span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
      <h3 class="h3_tbl brt"><s:message code="systemEmp.systemEmpInfo"/></h3>

      <%-- 사원기초정보 탭 --%>
      <%--
      <div class="tabType1">
        <ul>
          &lt;%&ndash; 사원기초정보 탭 &ndash;%&gt;
          <li><a id="systemEmpInfoTab" href="#" class="on"><s:message code="systemEmp.systemEmpInfo" /></a></li>
          &lt;%&ndash; 메뉴권한 탭 &ndash;%&gt;
          &lt;%&ndash; &ndash;%&gt;
        </ul>
      </div>
      --%>

      <%-- 상세 --%>
      <div style="height: 200px; overflow-y: auto;">
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
              <div class="impWrap"><s:message code="systemEmp.empNo" /></div>
            </th>
            <td >{{systemEmp.empNo}}</td>
            <%-- 사원명 --%>
            <th>
              <div class="impWrap"><s:message code="systemEmp.empNm" /></div>
            </th>
            <td>{{systemEmp.empNm}}</td>
          </tr>
          <tr>
            <%-- 웹사용여부 --%>
            <th>
              <div class="impWrap"><s:message code="systemEmp.webUseYn" /></div>
            </th>
            <td>{{systemEmp.webUseYnNm}}</td>
            <%-- 웹사용자ID --%>
            <th ng-if="systemEmp.webUseYn == 'Y'">
              <div class="impWrap"><s:message code="systemEmp.userId" /></div>
            </th>
            <td ng-if="systemEmp.webUseYn == 'Y'">{{systemEmp.userId}}</td>
            <th ng-if="systemEmp.webUseYn != 'Y'"></th>
            <td ng-if="systemEmp.webUseYn != 'Y'"></td>
          </tr>
          <tr>
            <%-- 휴대폰번호 --%>
            <th>
              <div class="impWrap"><s:message code="systemEmp.mpNo" /></div>
            </th>
            <td>{{systemEmp.mpNo}}</td>
            <%-- SMS수신여부 --%>
            <th>
              <div class="impWrap"><s:message code="systemEmp.smsRecvYn" /></div>
            </th>
            <td>{{systemEmp.smsRecvYnNm}}</td>
          </tr>
          <tr>
            <%-- 재직여부 --%>
            <th>
              <div class="impWrap"><s:message code="systemEmp.serviceFg" /></div>
            </th>
            <td>{{systemEmp.serviceFgNm}}</td>
            <%-- 관리자구분 --%>
            <th>
              <div class="impWrap"><s:message code="systemEmp.adminFg" /></div>
            </th>
            <td>{{systemEmp.adminFgNm}}</td>
          </tr>
          <tr ng-if="systemEmp.adminFg != 'A'">
            <%-- 대리점(관리업체) --%>
            <th>
              <div class="impWrap"><s:message code="systemEmp.agency" /></div>
            </th>
            <td>{{systemEmp.agencyNm}}</td>
            <th></th>
            <td></td>
          </tr>
          <tr>
            <%-- 매핑사원코드 --%>
            <th>
              <div class="impWrap"><s:message code="systemEmp.mapEmpNo" /></div>
            </th>
            <td>{{systemEmp.mapEmpNo}}</td>
            <%-- 사용여부 --%>
            <th>
              <div class="impWrap"><s:message code="systemEmp.useYn" /></div>
            </th>
            <td>{{systemEmp.useYnNm}}</td>
          </tr>
          <tr>
            <%-- 비고 --%>
            <th>
              <div class="impWrap"><s:message code="systemEmp.remark" /></div>
            </th>
            <td colspan="3">{{systemEmp.remark}}</td>
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

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/systemEmpDetail.js?ver=20181121.02" charset="utf-8"></script>
