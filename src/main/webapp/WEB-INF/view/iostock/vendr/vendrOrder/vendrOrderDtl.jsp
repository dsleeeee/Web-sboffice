<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrOrder/vendrOrderDtl/"/>

<div id="dtlView" style="display: none;" ng-controller="vendrOrderDtlCtrl">

  <form id="slipForm" name="slipForm" ng-submit="submitForm()">

    <h3 class="h3_tbl pdt5 lh30">
      <s:message code="vendrOrder.dtl.slipRegInfo"/>
      <div class="fr">
        <%-- 저장 --%>
        <button type="submit" class="btn_skyblue mr5" id="btnSave" ng-if="btnSaveFg">
          <s:message code="cmm.save"/></button>
        <%-- 삭제 --%>
        <button type="button" class="btn_skyblue " id="btnDel" ng-click="delete()" ng-if="btnDelFg">
          <s:message code="cmm.delete"/></button>
      </div>
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
          <%-- 거래처선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
          --%>
          <jsp:include page="/WEB-INF/view/iostock/vendr/vendrOrder/selectVendrS.jsp" flush="true">
            <jsp:param name="targetId" value="vendrOrderDtlSelectVendr"/>
          </jsp:include>
          <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
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
            <%--<span class="txtIn"><input id="orderDate" ng-model="slipInfo.orderDate" class="w120px"></span>--%>
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
            <%--<span class="txtIn"><input id="orderReqDate" ng-model="slipInfo.orderReqDate" class="w120px"></span>--%>
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
      <td><span id="slipDtlRegDt" class="pd5 txtIn s12" ng-bind="slipInfo.orderRegDt"></span>
      </td>
      <%-- 등록자 --%>
      <th><s:message code="vendrOrder.dtl.regNm"/></th>
      <td><span id="slipDtlRegNm" class="pd5 txtIn s12" ng-bind="slipInfo.orderRegNm"></span>
      </td>
    </tr>
    <tr>
      <%-- 확정일시 --%>
      <th><s:message code="vendrOrder.dtl.confmDt"/></th>
      <td><span id="slipDtlConfmDt" class="pd5 txtIn s12" ng-bind="slipInfo.confmDt"></span></td>
      <%-- 확정자 --%>
      <th><s:message code="vendrOrder.dtl.confmNm"/></th>
      <td><span id="slipDtlConfmNm" class="pd5 txtIn s12" ng-bind="slipInfo.confmNm"></span></td>
    </tr>
    <tr>
      <%-- 완료일시 --%>
      <th><s:message code="vendrOrder.dtl.endDt"/></th>
      <td><span id="slipDtlEndDt" class="pd5 txtIn s12" ng-bind="slipInfo.endDt"></span></td>
      <%-- 완료자 --%>
      <th><s:message code="vendrOrder.dtl.endNm"/></th>
      <td><span id="slipDtlEndNm" class="pd5 txtIn s12" ng-bind="slipInfo.endNm"></span></td>
    </tr>
    <tr>
      <%-- 발주총수량 --%>
      <th><s:message code="vendrOrder.dtl.orderTotQty"/></th>
      <td><span id="slipDtlOrderTotQty" class="pd5 txtIn s12" ng-bind="slipInfo.orderQty"></span>
      </td>
      <%-- 발주총금액 --%>
      <th><s:message code="vendrOrder.dtl.orderTotAmt"/></th>
      <td><span id="slipDtlOrderTotAmt" class="pd5 txtIn s12" ng-bind="slipInfo.orderTot"></span>
      </td>
    </tr>
    <tr>
      <%-- 입고총수량 --%>
      <th><s:message code="vendrOrder.dtl.inTotQty"/></th>
      <td><span id="slipDtlInTotQty" class="pd5 txtIn s12" ng-bind="slipInfo.confmDt"></span>
      </td>
      <%-- 입고총금액 --%>
      <th><s:message code="vendrOrder.dtl.inTotAmt"/></th>
      <td><span id="slipDtlInTotAmt" class="pd5 txtIn s12" ng-bind="slipInfo.confmNm"></span>
      </td>
    </tr>
    <tr>
      <%-- 최초입고일자 --%>
      <th><s:message code="vendrOrder.dtl.inFirstDate"/></th>
      <td>
        <span id="slipDtlInFirstDate" class="pd5 txtIn s12" ng-bind="slipInfo.inFirstDate"></span>
      </td>
      <%-- 최종입고일자 --%>
      <th><s:message code="vendrOrder.dtl.inLastDate"/></th>
      <td>
        <span id="slipDtlInLastDate" class="pd5 txtIn s12" ng-bind="slipInfo.inLastDate"></span>
      </td>
    </tr>
    </tbody>
  </table>

</div>


<script type="text/javascript">
  /** 발주정보 controller */
  app.controller('vendrOrderDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrOrderDtlCtrl', $scope, $http, true));

    // 발주타입 dynamic combo 조회
    var vendrOrderCtrlScope = agrid.getScope("vendrOrderCtrl");
    var url                 = '/iostock/vendr/vendrOrder/vendrOrderDtl/getDynamicCombo.sb';
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "AA1";
    vendrOrderCtrlScope._queryCombo("combo", "orderType", null, url, comboParams, "S"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("vendrOrderDtlCtrl", function (event, data) {
      $scope.slipNo = data.slipNo;
      $scope.slipFg = data.slipFg;

      // 값 초기화
      $scope.setDefaultValue();

      // 신규등록이 아닌 경우 발주정보 조회
      if ($scope.slipNo !== '') {
        $scope.getSlipInfo();
      }
      // 신규등록인 경우 저장버튼만 활성화
      else {
        $scope.btnSaveFg = true;
        $scope.btnDelFg  = false;
      }

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    $scope.setDefaultValue = function () {
      $("#vendrOrderDtlSelectVendrCd").val('');
      $("#vendrOrderDtlSelectVendrNm").val('선택');

      // 기본값 설정
      $scope.default = {
        orderType   : '',
        orderDate   : new Date(),
        orderReqDate: new Date(),
        remark      : '',
      };

      $scope.slipInfo = angular.copy($scope.default);
    };


    // 전표 상세 조회
    $scope.getSlipInfo = function () {
      var params    = {};
      params.slipNo = $scope.slipNo;
      params.slipFg = $scope.slipFg;

      $http({
        method : 'POST', //방식
        url    : "/iostock/vendr/vendrOrder/vendrOrderDtl/slipInfo.sb", /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response)) {
          // 진행구분이 조정등록이 아니면 상품추가/변경 불가
          if (!$.isEmptyObject(response.data.data)) {
            var data = response.data.data;

            console.log('getSlipInfo data');
            console.log(data);

            if (data.procFg != "" && data.procFg == "0") {
              $scope.btnSaveFg = true;
              $scope.btnDelFg  = true;
            }
            else {
              $scope.btnSaveFg = false;
              $scope.btnDelFg  = false;
            }

            $("#vendrOrderDtlSelectVendrCd").val(data.vendrCd);
            $("#vendrOrderDtlSelectVendrNm").val('[' + data.vendrCd + '] ' + data.vendrNm);
            data.orderDate    = new Date(getFormatDate(data.orderDate, "-"));
            data.orderReqDate = new Date(getFormatDate(data.orderReqDate, "-"));

            data.orderRegDt  = (nvl(data.orderRegDt, '') !== '' ? getFormatDateTime(data.orderRegDt) : '');
            data.confmDt     = (nvl(data.confmDt, '') !== '' ? getFormatDateTime(data.confmDt) : '');
            data.endDt       = (nvl(data.endDt, '') !== '' ? getFormatDateTime(data.endDt) : '');
            data.inFirstDate = (nvl(data.inFirstDate, '') !== '' ? getFormatDate(data.inFirstDate) : '');
            data.inLastDate  = (nvl(data.inLastDate, '') !== '' ? getFormatDate(data.inLastDate) : '');

            $scope.slipInfo = data;
          }
          else {
            // 팝업 닫기 및 값 초기화
            $scope.popupClose();
            $scope._popMsg(response.data.message);
            return false;
          }
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        $scope._popMsg(response.data.message);
        return false;
      }).then(function () {
        // "complete" code here
      });
    };


    // 발주 저장
    $scope.submitForm = function () {
      //값체크
      // if (!valueCheck()) return false;

      // 거래처를 선택해주세요.
      if ($("#vendrOrderDtlSelectVendrCd").val() === "") {
        $scope._popMsg(messages["vendrOrder.dtl.require.selectVendr"]);
        return false;
      }
      // 발주타입을 선택해주세요.
      if ($scope.slipInfo.orderType === "") {
        $scope._popMsg(messages["vendrOrder.dtl.require.orderType"]);
        return false;
      }

      var params = {};
      // 저장시 저장은 잘 되지만 Assertion failed in Wijmo: Date expected. 라고 debug에서 에러가 발생해 따로따로 넣도록 수정.
      // params              = $scope.slipInfo;
      params.vendrCd      = $("#vendrOrderDtlSelectVendrCd").val();
      params.slipNo       = $scope.slipNo;
      params.slipFg       = $scope.slipFg;
      params.orderType    = $scope.slipInfo.orderType;
      params.orderDate    = wijmo.Globalize.format($scope.slipInfo.orderDate, 'yyyyMMdd');
      params.orderReqDate = wijmo.Globalize.format($scope.slipInfo.orderReqDate, 'yyyyMMdd');
      params.remark       = $scope.slipInfo.remark;

      console.log('submitForm params');
      console.log(params);

      $http({
        method : 'POST', //방식
        url    : "/iostock/vendr/vendrOrder/vendrOrderDtl/save.sb", /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response)) {
          $scope._popMsg(messages['cmm.saveSucc']);

          console.log('submitForm response');
          console.log(response);

          if (!$.isEmptyObject(response.data.data)) {
            var data = response.data.data;

            console.log('submitForm data');
            console.log(data);

            // 발주 리스트 그리드 조회
            var vendrOrderScope = agrid.getScope('vendrOrderCtrl');
            vendrOrderScope.searchVendrOrderList();

            var params    = {};
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
        $scope._popMsg(response.data.message);
        return false;
      });
    };


    $scope.delete = function () {
      <%-- 선택하신 자료를 삭제하시겠습니까? --%>
      var msg = messages["cmm.choo.delete"];
      s_alert.popConf(msg, function () {
        var params    = {};
        params.slipNo = $scope.slipNo;

        $http({
          method : 'POST', //방식
          url    : "/iostock/vendr/vendrOrder/vendrOrderDtl/delete.sb", /* 통신할 URL */
          params : params, /* 파라메터로 보낼 데이터 */
          headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
          console.log('delete response');
          console.log(response);
          if (response.data.status === 'OK') {
            $scope._popMsg(messages['cmm.delSucc']);
            $scope.popupClose();
          }
          else if (response.data.status === 'FAIL') {
            $scope._popMsg(response.data.message);
          }
          else {
            var msg = response.data.status + ' : ' + response.data.message;
            $scope._popMsg(msg);
            return false;
          }
        }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
          $scope._popMsg(response.data.message);
          return false;
        });
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


    // 거래처선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.vendrOrderDtlSelectVendrShow = function () {
      $scope._broadcast('vendrOrderDtlSelectVendrCtrl');
    };


  }]);
</script>

