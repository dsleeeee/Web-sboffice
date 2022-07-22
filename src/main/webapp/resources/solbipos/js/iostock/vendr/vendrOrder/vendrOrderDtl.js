/** 발주정보 controller */
app.controller('vendrOrderDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrOrderDtlCtrl', $scope, $http, true));

  // 발주타입 dynamic combo 조회
  var vendrOrderCtrlScope = agrid.getScope("vendrOrderCtrl");
  var url                 = '/iostock/vendr/vendrOrder/vendrOrderDtl/getOrderTypeCombo.sb';
  var comboParams         = {};
  comboParams.nmcodeGrpCd = "024";
  // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
  vendrOrderCtrlScope._queryCombo("combo", "orderType", null, url, comboParams, ""); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

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
      $scope.btnSaveShowFg = true;
      $scope.btnDelShowFg  = false;
      $scope.procLayerIfFg = false;
    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  $scope.setDefaultValue = function () {
    $("#vendrOrderDtlSelectVendrCd").val('');
    $("#vendrOrderDtlSelectVendrNm").val('선택');

    $scope.vendrOrderDtlSelectVendrNmDisabled  = false; // 거래처선택 모듈 input
    $scope.vendrOrderDtlSelectVendrBtnDisabled = false; // 선택취소

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
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }
    
    $http({
      method : 'POST', //방식
      url    : "/iostock/vendr/vendrOrder/vendrOrderDtl/slipInfo.sb", /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 진행구분이 조정등록이 아니면 상품추가/변경 불가
        if (!$.isEmptyObject(response.data.data)) {
          var data = response.data.data;

          if (data.procFg != "" && data.procFg == "0") {
            $scope.btnSaveShowFg = true;
            $scope.btnDelShowFg  = true;
          } else {
            $scope.btnSaveShowFg = false;
            $scope.btnDelShowFg  = false;
          }

          // 진행상태가 '완료' 인 경우, 진행상태 선택 dropdown과 진행상태변경 버튼 숨기기
          if (data.procFg != "" && data.procFg == "5") {
            $scope.btnProcFgShowFg = false;
            $scope.btnProcFgShowFg  = false;
          } else {
            $scope.btnProcFgShowFg = true;
            $scope.btnProcFgShowFg  = true;
          }

          // 진행상태 관련 레이어 show 여부
          $scope.procLayerIfFg = true;

          // 발주타입 dynamic combo 조회
          var vendrOrderCtrlScope = agrid.getScope("vendrOrderCtrl");
          var url                 = '/iostock/vendr/vendrOrder/vendrOrderDtl/getProcFgCombo.sb';
          var comboParams         = {};
          comboParams.procFg      = data.procFg;
          // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
          vendrOrderCtrlScope._queryCombo("combo", "procFg", null, url, comboParams, "S");

          // 거래처 선택 모듈값 세팅
          $("#vendrOrderDtlSelectVendrCd").val(data.vendrCd);
          $("#vendrOrderDtlSelectVendrNm").val('[' + data.vendrCd + '] ' + data.vendrNm);
          data.orderDate    = new Date(getFormatDate(data.orderDate, "-"));
          data.orderReqDate = new Date(getFormatDate(data.orderReqDate, "-"));

          data.orderRegDt  = (nvl(data.orderRegDt, '') !== '' ? getFormatDateTime(data.orderRegDt) : '');
          data.confmDt     = (nvl(data.confmDt, '') !== '' ? getFormatDateTime(data.confmDt) : '');
          data.endDt       = (nvl(data.endDt, '') !== '' ? getFormatDateTime(data.endDt) : '');
          data.inFirstDate = (nvl(data.inFirstDate, '') !== '' ? getFormatDate(data.inFirstDate) : '');
          data.inLastDate  = (nvl(data.inLastDate, '') !== '' ? getFormatDate(data.inLastDate) : '');
          data.orderTotQty = (nvl(data.orderTotQty, '') !== '' ? addComma(data.orderTotQty) : '');
          data.orderTot    = (nvl(data.orderTot, '') !== '' ? addComma(data.orderTot) : '');
          data.inTotQty    = (nvl(data.inTotQty, '') !== '' ? addComma(data.inTotQty) : '');
          data.inTot       = (nvl(data.inTot, '') !== '' ? addComma(data.inTot) : '');

          // 상품이 등록되어 있는 경우에는 거래처 선택 불가
          if (data.dtlCnt > 0) {
            $scope.vendrOrderDtlSelectVendrNmDisabled  = true;
            $scope.vendrOrderDtlSelectVendrBtnDisabled = true;
          } else {
            $scope.vendrOrderDtlSelectVendrNmDisabled  = false;
            $scope.vendrOrderDtlSelectVendrBtnDisabled = false;
          }

          $scope.slipInfo = data;
        } else {
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
    if (!$scope.valueCheck()) return false;

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
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }
    
    $http({
      method : 'POST', //방식
      url    : "/iostock/vendr/vendrOrder/vendrOrderDtl/save.sb", /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        $scope._popMsg(messages['cmm.saveSucc']);

        if (!$.isEmptyObject(response.data.data)) {
          var data = response.data.data;

          // 발주 리스트 그리드 조회
          var vendrOrderScope = agrid.getScope('vendrOrderCtrl');
          vendrOrderScope.searchVendrOrderList();

          var params     = {};
          params.slipNo  = data.slipNo;
          params.slipFg  = data.slipFg;
          params.vendrCd = data.vendrCd;
          $scope._broadcast('vendrOrderPopCtrl', params);
        } else {
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


  // 값 체크
  $scope.valueCheck = function () {
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

    return true;
  };


  $scope.delete = function () {
    /** 선택하신 자료를 삭제하시겠습니까? */
    var msg = messages["cmm.choo.delete"];
    s_alert.popConf(msg, function () {
      var params    = {};
      params.slipNo = $scope.slipNo;
      
      //가상로그인 session 설정
      if(document.getElementsByName('sessionId')[0]){
      	params['sid'] = document.getElementsByName('sessionId')[0].value;
      }
      
      $http({
        method : 'POST', //방식
        url    : "/iostock/vendr/vendrOrder/vendrOrderDtl/delete.sb", /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if (response.data.status === 'OK') {
          $scope._popMsg(messages['cmm.delSucc']);
          $scope.popupClose();
        } else if (response.data.status === 'FAIL') {
          $scope._popMsg(response.data.message);
        } else {
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


  $scope.procFgUpdate = function () {
    /** 진행상태를 선택해 주세요. */
    if ($scope.slipInfo.procFg === '') {
      $scope._popMsg(messages['vendrOrder.dtl.require.procFg']);
      return false;
    }

    /** 진행상태를 변경하시겠습니까? */
    var msg = messages["vendrOrder.dtl.procFgUpdateMsg"];
    s_alert.popConf(msg, function () {
      var params    = {};
      params.slipNo = $scope.slipNo;
      params.procFg = $scope.slipInfo.procFg;
      
      //가상로그인 session 설정
      if(document.getElementsByName('sessionId')[0]){
      	params['sid'] = document.getElementsByName('sessionId')[0].value;
      }
      
      $http({
        method : 'POST', //방식
        url    : "/iostock/vendr/vendrOrder/vendrOrderDtl/saveProcFg.sb", /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if (response.data.status === 'OK') {
          $scope._popMsg(messages['cmm.saveSucc']);

          // 발주 리스트 그리드 조회
          var vendrOrderScope = agrid.getScope('vendrOrderCtrl');
          vendrOrderScope.searchVendrOrderList();

          var params     = {};
          params.slipNo  = $scope.slipNo;
          params.slipFg  = $scope.slipFg;
          params.vendrCd = $("#vendrOrderDtlSelectVendrCd").val();
          $scope._broadcast('vendrOrderPopCtrl', params);
        } else if (response.data.status === 'FAIL') {
          $scope._popMsg(response.data.message);
        } else {
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

  // 발주타입관리 팝업
  $scope.vendrOrderTypeRegPop = function () {
    $scope.wjVendrOrderTypeRegPopLayer.show(true);
    $scope._broadcast('vendrOrderTypeRegPopCtrl');
  }


}]);
