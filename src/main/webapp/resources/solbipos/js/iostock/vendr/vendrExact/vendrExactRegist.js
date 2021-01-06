/** 지급액 등록 controller */
app.controller('vendrExactRegistCtrl', ['$scope', '$http', '$compile', '$timeout', function ($scope, $http, $compile, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrExactRegistCtrl', $scope, $http, true));

  $scope.excclcDate = wcombo.genDate("#excclcDate");

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on('vendrExactRegistCtrl', function (event, data) {

    // 신규등록인 경우
    if (isEmptyObject(data)) {

        // 기본값 세팅
        $scope.excclcDate.value = getCurDate('-');
        $scope.excclcTot = '';
        $scope.seqNo = '';
        $scope.remark = '';

        // 삭제 버튼 비활성
        $scope.btnDeleteIfFg = false;
    	$scope.excclcDateFg	= false;

    	//거래처선택 모듈 disabled 컨트롤
        $scope.vendrExactRegistSelectVendrNmDisabled  = false;
        $scope.vendrExactRegistSelectVendrBtnDisabled = false;

    } else {

        $scope.excclcDateFg	= true;

        // 조회
    	$scope.getExactInfo(data);
    	
    	//거래처선택 모듈 disabled 컨트롤
        $scope.vendrExactRegistSelectVendrNmDisabled  = true;
        $scope.vendrExactRegistSelectVendrBtnDisabled = true;

    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();

  });

  // 지급액 상세 조회
  $scope.getExactInfo = function (data) {
    var params        = {};
    params.vendrCd    = data.vendrCd;
    params.excclcDate = data.excclcDate;
    params.seqNo      = data.seqNo;
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }
    
    $http({
      method : 'POST', //방식
      url    : "/iostock/vendr/vendrExact/vendrExactRegist/getExactInfo.sb", /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 진행구분이 조정등록이 아니면 상품추가/변경 불가
        if (!$.isEmptyObject(response.data.data)) {
          var rData = response.data.data;

          // 삭제 버튼 활성화
          $scope.btnDeleteIfFg = true;
          
          // 거래처 선택 모듈값 세팅
          $scope.excclcDate.value = new Date(getFormatDate(rData.excclcDate, "-"));
          $("#vendrExactRegistSelectVendrCd").val(rData.vendrCd);
          $("#vendrExactRegistSelectVendrNm").val("[" + rData.vendrCd + "] " + rData.vendrNm);
          $scope.excclcTot = addComma(rData.excclcTot);
          $scope.seqNo = rData.seqNo;
          $scope.remark = rData.remark;

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
    /** 거래처를 선택해주세요. */
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
    params.excclcDate = wijmo.Globalize.format($scope.excclcDate.value, 'yyyyMMdd');
    params.excclcTot  = removeComma($scope.excclcTot);
    params.excclcFg   = '2';
    params.remark     = $scope.remark;
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }
    
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
    /** 선택하신 자료를 삭제하시겠습니까? */
    var msg = messages["cmm.choo.delete"];
    s_alert.popConf(msg, function () {

      var params        = {};
      params.vendrCd    = $("#vendrExactRegistSelectVendrCd").val();
      params.seqNo      = $scope.seqNo;
      params.excclcDate = wijmo.Globalize.format($scope.excclcDate.value, 'yyyyMMdd');
      
      //가상로그인 session 설정
      if(document.getElementsByName('sessionId')[0]){
      	params['sid'] = document.getElementsByName('sessionId')[0].value;
      }
      
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
    $scope.excclcTot = addComma($scope.excclcTot);
  };


  // 지급액 input onfocus 시 함수호출. 값에 comma 제거
  $scope.excclcTotOnFocus = function ($event) {
    $scope.excclcTot = removeComma($scope.excclcTot);
  };

  // 팝업 닫기
  $scope.close = function(){
    // 거래처 초기화
    $("#vendrExactRegistSelectVendrCd").val('');
    $("#vendrExactRegistSelectVendrNm").val('선택');
    $scope.wjVendrExactRegistLayer.hide();
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
