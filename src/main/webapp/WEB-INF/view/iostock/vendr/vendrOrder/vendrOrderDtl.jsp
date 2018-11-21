<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrOrder/vendrOrderDtl/"/>

<div id="dtlView" class="subCon" style="display: none;" ng-controller="vendrOrderDtlCtrl">

  <form id="slipForm" name="slipForm" ng-submit="submitForm()" novalidate>

    <h3 class="h3_tbl pdt5 lh30">
      <s:message code="vendrOrder.dtl.slipRegInfo"/>
      <%-- 저장 --%>
      <button type="submit" class="btn_skyblue fr" id="btnSave">
        <s:message code="cmm.save"/></button>
    </h3>

    <table class="tblType01">
      <colgroup>
        <col class="w20"/>
        <col class="w80"/>
      </colgroup>
      <tbody>
      <tr>
        <%-- 거래처 --%>
        <th><s:message code="vendrOrder.dtl.vendr"/></th>
        <td>
          <input type="text" id="vendrCd" ng-model="slipInfo.vendrCd" maxlength="10">
        </td>
      </tr>
      <tr>
        <%-- 발주타입 --%>
        <th><s:message code="vendrOrder.dtl.orderType"/></th>
        <td>
          <div class="sb-select">
          <span class="txtIn w150px">
            <wj-combo-box
              id="orderType"
              ng-model="slipInfo.orderType"
              items-source="_getComboData('orderType')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 발주일자 --%>
        <th><s:message code="vendrOrder.dtl.orderDate"/></th>
        <td>
          <div class="sb-select">
          <span class="txtIn w120px">
            <wj-input-date
              value="orderDate"
              ng-model="slipInfo.orderDate"
              control="orderDateCombo"
              min="2000-01-01"
              max="2099-12-31"
              initialized="_initDateBox(s)">
            </wj-input-date>
          </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 입고요청일자 --%>
        <th><s:message code="vendrOrder.dtl.orderReqDate"/></th>
        <td>
          <div class="sb-select">
          <span class="txtIn w120px">
            <wj-input-date
              value="orderReqDate"
              ng-model="slipInfo.orderReqDate"
              control="orderReqDateCombo"
              min="2000-01-01"
              max="2099-12-31"
              initialized="_initDateBox(s)">
            </wj-input-date>
          </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 비고 --%>
        <th><s:message code="vendrOrder.dtl.remark"/></th>
        <td>
          <textarea id="remark" class="w100 tArea1" style="height:100px;" ng-model="slipInfo.remark"></textarea>
        </td>
      </tr>
      </tbody>
    </table>
  </form>

  <h3 class="h3_tbl pdt5 lh30 mt10"><s:message code="vendrOrder.dtl.slipDtlInfo"/></h3>
  <table class="tblType01">
    <colgroup>
      <col class="w15"/>
      <col class="w35"/>
      <col class="w15"/>
      <col class="w35"/>
    </colgroup>
    <tbody>
    <tr>
      <%-- 등록일시 --%>
      <th><s:message code="vendrOrder.dtl.regDt"/></th>
      <td><span id="slipDtlRegDt" class="pd5 txtIn s12"></span></td>
      <%-- 등록자 --%>
      <th><s:message code="vendrOrder.dtl.regNm"/></th>
      <td><span id="slipDtlRegNm" class="pd5 txtIn s12"></span></td>
    </tr>
    <tr>
      <%-- 확정일시 --%>
      <th><s:message code="vendrOrder.dtl.confmDt"/></th>
      <td><span id="slipDtlConfmDt" class="pd5 txtIn s12"></span></td>
      <%-- 확정자 --%>
      <th><s:message code="vendrOrder.dtl.confmNm"/></th>
      <td><span id="slipDtlConfmNm" class="pd5 txtIn s12"></span></td>
    </tr>
    <tr>
      <%-- 완료일시 --%>
      <th><s:message code="vendrOrder.dtl.endDt"/></th>
      <td><span id="slipDtlEndDt" class="pd5 txtIn s12"></span></td>
      <%-- 완료자 --%>
      <th><s:message code="vendrOrder.dtl.endNm"/></th>
      <td><span id="slipDtlEndNm" class="pd5 txtIn s12"></span></td>
    </tr>
    <tr>
      <%-- 발주총수량 --%>
      <th><s:message code="vendrOrder.dtl.orderTotQty"/></th>
      <td><span id="slipDtlOrderTotQty" class="pd5 txtIn s12"></span></td>
      <%-- 발주총금액 --%>
      <th><s:message code="vendrOrder.dtl.orderTotAmt"/></th>
      <td><span id="slipDtlOrderTotAmt" class="pd5 txtIn s12"></span></td>
    </tr>
    <tr>
      <%-- 입고총수량 --%>
      <th><s:message code="vendrOrder.dtl.inTotQty"/></th>
      <td><span id="slipDtlInTotQty" class="pd5 txtIn s12"></span></td>
      <%-- 입고총금액 --%>
      <th><s:message code="vendrOrder.dtl.inTotAmt"/></th>
      <td><span id="slipDtlInTotAmt" class="pd5 txtIn s12"></span></td>
    </tr>
    <tr>
      <%-- 최초입고일자 --%>
      <th><s:message code="vendrOrder.dtl.inFirstDate"/></th>
      <td><span id="slipDtlInFirstDate" class="pd5 txtIn s12"></span></td>
      <%-- 최종입고일자 --%>
      <th><s:message code="vendrOrder.dtl.inLastDate"/></th>
      <td><span id="slipDtlInLastDate" class="pd5 txtIn s12"></span></td>
    </tr>
    </tbody>
  </table>

</div>


<script type="text/javascript">
  /** 발주정보 controller */
  app.controller('vendrOrderDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrOrderDtlCtrl', $scope, $http, true));

    // 기본값 설정
    $scope.default = {
      vendrCd: '',
      vendrNm: '선택하세요',
      remark: '',
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("vendrOrderDtlCtrl", function (event, data) {
      $scope.slipNo = data.slipNo;

      var vendrOrderCtrlScope = agrid.getScope("vendrOrderCtrl");

      var url = '/iostock/vendr/vendrOrder/vendrOrderDtl/getDynamicCombo.sb';
      var comboParams         = {};
      comboParams.nmcodeGrpCd = "AA1";
      vendrOrderCtrlScope._queryCombo("combo", "orderType", url, comboParams, "S"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

      // 값 초기화
      $scope.slip = angular.copy($scope.default);

      if($scope.slipNo !== '') {
        $scope.getSlipInfo();
      }

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 전표 상세 조회
    $scope.getSlipInfo = function () {
      var param    = {};
      param.slipNo = $scope.slipNo;

      $http({
        method : 'POST', //방식
        url    : "/iostock/vendr/vendrOrder/vendrOrderDtl/slipInfo.sb", /* 통신할 URL */
        params : param, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response)) {
          // 진행구분이 조정등록이 아니면 상품추가/변경 불가
          if (!$.isEmptyObject(response.data.data)) {
            var data = response.data.data;
            $scope.slipInfo = data;
          }
          else {
            // 팝업 닫기 및 값 초기화
            $scope.popupClose();
            s_alert.pop(response.data.message);
            return false;
          }
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        s_alert.pop(response.data.message);
        return false;
      }).then(function () {
        // "complete" code here
      });
    };


    // 발주 저장
    $scope.submitForm = function () {
      //값체크
      // if (!valueCheck()) return false;
      var params = {};
      params = $scope.slipInfo;
      params.slipNo = $scope.slipNo;

      console.log(params);

      $http({
        method : 'POST', //방식
        url    : "/iostock/vendr/vendrOrder/vendrOrderDtl/save.sb", /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response)) {
          s_alert.pop(messages["cmm.saveSucc"]);

          if (!$.isEmptyObject(response.data.data)) {
            var data    = response.data.data;
            var params = {};
            params.slipNo = data.slipNo;
            $scope._broadcast('vendrOrderPopCtrl', params);
          }
          else {
            $scope.popupClose();
          }
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        s_alert.pop(response.data.message);
        return false;
      });
    };


    // 팝업 닫기, 값 초기화, 발주 리스트 그리드 조회
    $scope.popupClose = function () {
      // 팝업 닫기
      var vendrOrderPopScope = agrid.getScope("vendrOrderPopCtrl");
      vendrOrderPopScope.wjVendrOrderPopLayer.hide(true);

      // 발주 리스트 그리드 조회
      var vendrOrderScope = agrid.getScope('vendrOrderCtrl');
      vendrOrderScope.searchVendrOrderList();
    };


  }]);
</script>

