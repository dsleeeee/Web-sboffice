<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<wj-popup id="wjVendrExactRegistLayer" control="wjVendrExactRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="vendrExactRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrExactRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vendrExact.reg.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">
      <form id="vendrExactForm" ng-submit="submitForm()" novalidate>
        <table class="tblType01">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="vendrExact.reg.excclcDate"/></th>
            <td>
              <div class="sb-select">
                <span class="txtIn"><input id="excclcDate" class="w200px" ng-model="vendrExact.excclcDate"></span>
              </div>
            </td>
            <th><s:message code="vendrExact.reg.vendr"/></th>
            <td>
              <%-- 거래처선택 모듈 싱글 선택 사용시 include
                   param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                closeFunc - 팝업 닫기시 호출할 함수
              --%>
              <jsp:include page="/WEB-INF/view/iostock/vendr/vendrOrder/selectVendrS.jsp" flush="true">
                <jsp:param name="targetId" value="vendrExactRegistSelectVendr"/>
              </jsp:include>
              <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
            </td>
          </tr>
          <tr>
            <th><s:message code="vendrExact.reg.excclcTot"/></th>
            <td>
              <input type="text" id="excclcTot" name="excclcTot" ng-model="vendrExact.excclcTot" class="sb-input w100" maxlength="8"
                     data-check="1,10,number" ng-required="true" ng-blur="excclcTotOnBlur($event)" ng-focus="excclcTotOnFocus($event)"/>
            </td>
          </tr>
          <tr>
            <th><s:message code="vendrExact.reg.remark"/></th>
            <td colspan="3">
              <div>
                <textarea id="remark" class="w100 tArea1" style="height:100px;" ng-model="vendrExact.remark" data-check="1,10"></textarea>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="mt10 pdb20 oh bb fr">
          <!-- 저장 -->
          <button type="submit" id="btnSave" class="btn_blue">
            <s:message code="cmm.save"/></button>
          <!-- 삭제 -->
          <button type="button" id="btnDelete" class="btn_blue" ng-if="btnDeleteIfFg" ng-click="delete()">
            <s:message code="cmm.delete"/></button>
        </div>
      </form>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">
  /** 지급액 등록 controller */
  app.controller('vendrExactRegistCtrl', ['$scope', '$http', '$compile', '$timeout', function ($scope, $http, $compile, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrExactRegistCtrl', $scope, $http, true));

    $scope.default = {
      excclcDate: new Date(),
      excclcTot : '',
      remark    : ''
    };

    $scope.excclcDate = wcombo.genDate("#excclcDate");

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on('vendrExactRegistCtrl', function (event, data) {

      $timeout(function () {
        // 신규등록 팝업 오픈시 거래처선택모듈의 값 초기화
        $("#vendrExactRegistSelectVendrCd").val('');
        $("#vendrExactRegistSelectVendrNm").val('선택');
        // 기본값 세팅
        $scope.vendrExact = angular.copy($scope.default);

        // 삭제 버튼 비활성
        $scope.btnDeleteIfFg = false;
      }, 10);

      $scope.vendrCd    = nvl(data.vendrCd, '');
      $scope.excclcDate = nvl(data.excclcDate, '');
      $scope.seqNo      = nvl(data.seqNo, '');

      // 신규등록인 경우
      if (nvl($scope.vendrCd, '') === '') {
        $scope.wjVendrExactRegistLayer.show(true);
      } else {
        $scope.getExactInfo();
      }

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 지급액 상세 조회
    $scope.getExactInfo = function () {
      var params        = {};
      params.vendrCd    = $scope.vendrCd;
      params.excclcDate = $scope.excclcDate;
      params.seqNo      = $scope.seqNo;

      $http({
        method : 'POST', //방식
        url    : "/iostock/vendr/vendrExact/vendrExactRegist/getExactInfo.sb", /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          // 진행구분이 조정등록이 아니면 상품추가/변경 불가
          if (!$.isEmptyObject(response.data.data)) {
            var data = response.data.data;

            // 삭제 버튼 활성화
            $scope.btnDeleteIfFg = true;

            // 거래처 선택 모듈값 세팅
            $("#vendrExactRegistSelectVendrCd").val(data.vendrCd);
            $("#vendrExactRegistSelectVendrNm").val('[' + data.vendrCd + '] ' + data.vendrNm);
            data.excclcDate = new Date(getFormatDate(data.excclcDate, "-"));
            data.excclcTot  = (nvl(data.excclcTot, '') !== '' ? addComma(data.excclcTot) : '');

            $scope.vendrExact = data;
          } else {
            $scope.wjVendrExactRegistLayer.hide(true);
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
        $scope.wjVendrExactRegistLayer.show(true);
      });
    };


    // 지급액 저장
    $scope.submitForm = function () {
      <%--거래처를 선택해주세요. --%>
      var msg = messages["vendrExact.reg.require.selectVendr"];
      if ($("#vendrExactRegistSelectVendrCd").val() === "") {
        s_alert.popOk(msg, function () {
        });
        return false;
      }

      // 값체크
      if (!valueCheck($scope, $compile, 'excclcTot', 1, 10, 'number')) return false;
      if (!valueCheck($scope, $compile, 'remark', 0, 1000, 'char')) return false;

      var params        = {};
      params.vendrCd    = $("#vendrExactRegistSelectVendrCd").val();
      params.seqNo      = $scope.seqNo;
      params.excclcDate = wijmo.Globalize.format($scope.vendrExact.excclcDate, 'yyyyMMdd');
      params.excclcTot  = removeComma($scope.vendrExact.excclcTot);
      params.excclcFg   = '2';
      params.remark     = $scope.vendrExact.remark;

      $http({
        method : 'POST', //방식
        url    : "/iostock/vendr/vendrExact/vendrExactRegist/save.sb", /* 통신할 URL */
        // params : $scope.vendrExact, /* 파라메터로 보낼 데이터 */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          $scope._popMsg(messages["cmm.saveSucc"]);
          // 거래처정산 그리드 조회
          $scope._broadcast('vendrExactCtrl');

          // 수정인 경우 상세그리드 조회
          if ($scope.seqNo !== '') {
            var vendrExactDtlScope = agrid.getScope('vendrExactDtlCtrl');
            vendrExactDtlScope.searchVendrExactDtlList();
          } else {
            // 상세내역 그리드 초기화
            var vendrExactDtlScope = agrid.getScope('vendrExactDtlCtrl');
            vendrExactDtlScope.dtlGridDefault();
          }
          // 팝업 닫기
          $scope.wjVendrExactRegistLayer.hide();
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        $scope._popMsg(response.data.message);
        return false;
      });
    };


    // 지급액 삭제
    $scope.delete = function () {
      <%-- 선택하신 자료를 삭제하시겠습니까? --%>
      var msg = messages["cmm.choo.delete"];
      s_alert.popConf(msg, function () {

        var params        = {};
        params.vendrCd    = $("#vendrExactRegistSelectVendrCd").val();
        params.seqNo      = $scope.seqNo;
        params.excclcDate = wijmo.Globalize.format($scope.vendrExact.excclcDate, 'yyyyMMdd');

        $http({
          method : 'POST', //방식
          url    : "/iostock/vendr/vendrExact/vendrExactRegist/delete.sb", /* 통신할 URL */
          // params : $scope.vendrExact, /* 파라메터로 보낼 데이터 */
          params : params, /* 파라메터로 보낼 데이터 */
          headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
          if ($scope._httpStatusCheck(response, true)) {
            $scope._popMsg(messages["cmm.delSucc"]);
            // 거래처별 정산 그리드 조회
            $scope._broadcast('vendrExactCtrl');
            // 상세 그리드 조회
            var vendrExactDtlScope = agrid.getScope('vendrExactDtlCtrl');
            vendrExactDtlScope.searchVendrExactDtlList();

            // 팝업 닫기
            $scope.wjVendrExactRegistLayer.hide();
          }
        }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
          $scope._popMsg(response.data.message);
          return false;
        });
      });
    };


    // 거래처선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.vendrExactRegistSelectVendrShow = function () {
      $scope._broadcast('vendrExactRegistSelectVendrCtrl');
    };


    // 지급액 input onblur 시 함수호출. 값에 comma 추가
    $scope.excclcTotOnBlur = function ($event) {
      $scope.vendrExact.excclcTot = addComma($scope.vendrExact.excclcTot);
    };


    // 지급액 input onfocus 시 함수호출. 값에 comma 제거
    $scope.excclcTotOnFocus = function ($event) {
      $scope.vendrExact.excclcTot = removeComma($scope.vendrExact.excclcTot);
    };

  }]);

  function valueCheck(scope, compile, eleId, min, max, type) {
    var valueCheckFg = true;
    var popMsg       = '';
    var el           = $('#' + eleId);
    var value        = el.val();

    var numChkregexp = /[^0-9]/g;
    if (type === 'number' && numChkregexp.test(removeComma(value))) {
      popMsg       = '<p class="s12" style="line-height:14px;">문자열에 숫자가 아닌 값이 있습니다.</p>';
      valueCheckFg = false;
    }

    if (value.getByteLengthForOracle() < min || value.getByteLengthForOracle() > max) {
      popMsg       = '<p class="s12" style="line-height:14px;">문자열의 길이를 조정해주세요.<br>(한글은 3Byte로 계산됩니다.)<br>최소 : ' + min + ', 최대 : ' + max + '</p>';
      valueCheckFg = false;
    }

    if (!valueCheckFg) {
      if (!el.attr('uib-popover-html')) {
        el.attr('uib-popover-html', "'" + popMsg + "'");
        // el.attr('uib-popover-html', "'문자열의 길이를 조정해주세요.<br>(한글은 3Byte로 계산됩니다.)<br>최대:'");
        el.attr('popover-placement', 'bottom-left');
        el.attr('popover-is-open', true);

        el.on('click', function (event) {
          if ($(this).attr('uib-popover-html')) {
            $(this).removeAttr('uib-popover-html');
            $(this).removeAttr('popover-placement');
            $(this).removeAttr('popover-is-open');
            $(this).off('click');
          }
        });

        compile(el)(scope);
      }
    }

    return valueCheckFg;
  }

</script>
