<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<wj-popup id="wjSpeDateRegistLayer" control="wjSpeDateRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="speDateRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="speDateRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="outstockReqDate.specificDate"/> &nbsp;<s:message code="cmm.new.add"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">
      <form id="speDateForm" ng-submit="submitForm()">
        <table class="tblType01">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="outstockReqDate.specificDate"/><em class="imp">*</em></th>
            <td>
              <div class="sb-select">
                <span class="txtIn"><input id="specificDate" class="w200" ng-model="speDate.specificDate"></span>
              </div>
            </td>
            <th><s:message code="outstockReqDate.store"/><em class="imp">*</em></th>
            <td>
              <%-- 매장선택 모듈 싱글 선택 사용시 include --%>
              <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopS.jsp" flush="true">
                <jsp:param name="targetId" value="speDateRegistStore"/>
              </jsp:include>
              <%--// 매장선택 모듈 싱글 선택 사용시 include --%>
            </td>
          </tr>
          <tr>
            <th><s:message code="outstockReqDate.outstockReqYn"/></th>
            <td>
              <select id="outstockReqYn" ng-model="speDate.outstockReqYn">
                <option value="N"><s:message code="outstockReqDate.outstockReqYnN"/></option>
                <option value="Y"><s:message code="outstockReqDate.outstockReqYnY"/></option>
              </select>
            </td>
          </tr>
          <tr>
            <th><s:message code="outstockReqDate.specificDateRemark"/><em class="imp">*</em></th>
            <td colspan="3">
              <div>
                <textarea id="specificDateRemark" class="w100 tArea1" style="height:100px;" ng-model="speDate.specificDateRemark"></textarea>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="mt10 pdb20 oh bb">
          <button type="submit" id="btnSave" class="btn_blue fr">저장</button>
        </div>
      </form>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">
  /** 특정일 신규등록 controller */
  app.controller('speDateRegistCtrl', ['$scope', '$http', function ($scope, $http) {
    $scope.default = {
      outstockReqYn     : "N",
      storeCd           : "",
      specificDateRemark: ""
    };

    var specificDate = wcombo.genDate("#specificDate");

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on('speDateRegistCtrl', function (event, paramObj) {
      // 특정일 신규등록 팝업 오픈
      $scope.wjSpeDateRegistLayer.show(true);
      specificDate.value = getCurDate('-'); // 특정일 오늘날짜로 초기화
      $scope.speDate     = angular.copy($scope.default);
      // 신규등록 팝업 오픈시 매장선택모듈의 값 초기화
      $("#speDateRegistStoreCd").val("");
      $("#speDateRegistStoreNm").val("선택");

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 특정일 저장
    $scope.submitForm = function () {
      //값체크
      if (!valueCheck()) return false;

      // 특정일값 넘길 파라미터에 세팅
      $scope.speDate.specificDate = wijmo.Globalize.format(specificDate.value, 'yyyyMMdd');
      // 매장 선택 모듈의 매장코드값 파라미터 세팅
      $scope.speDate.storeCd      = $("#speDateRegistStoreCd").val();

      $http({
        method : 'POST', //방식
        url    : "/iostock/order/outstockReqDate/specificDate/saveNew.sb", /* 통신할 URL */
        params : $scope.speDate, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        // this callback will be called asynchronously
        // when the response is available
        var resData = response.data;
        // 통신은 성공하였으나 데이터 엑세스에 실패한 경우
        if (resData.status == "FAIL") {
          $scope.s_alert(resData.message);
        }
        // 성공한 경우
        else {
          s_alert.pop(messages["cmm.saveSucc"]);
          // 특정일 그리드 새로고침
          $scope._broadcast('specificCtrl');
        }
        // 팝업 닫기
        $scope.wjSpeDateRegistLayer.hide();

      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        s_alert.pop(response.data.message);
        return false;
      });
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.speDateRegistStoreShow = function () {
      $scope._broadcast('speDateRegistStoreCtrl');
    };

  }]);

  // 값 체크
  function valueCheck() {
    <%-- 매장을 선택해주세요. --%>
    var msg = "<s:message code='outstockReqDate.require.selectStore'/>";
    if ($("#speDateRegistStoreCd").val() === "") {
      s_alert.popOk(msg, function () {
      });
      return false;
    }

    <%-- 설명(을)를 입력하세요. --%>
    var msg = "<s:message code='outstockReqDate.specificDateRemark'/> <s:message code='cmm.require.text'/>";
    if ($("#specificDateRemark").val() === "") {
      s_alert.popOk(msg, function () {
        $("#specificDateRemark").select();
      });
      return false;
    }

    <%-- 설명의 길이가 너무 깁니다. --%>
    var msg = "<s:message code='outstockReqDate.specificDateRemark'/> <s:message code='outstockReqDate.textOver'/>";
    if ($("#specificDateRemark").val().getByteLengthForOracle() > 18) {
      s_alert.popOk(msg, function () {
        $("#specificDateRemark").select();
      });
      return false;
    }

    return true;
  }
</script>
