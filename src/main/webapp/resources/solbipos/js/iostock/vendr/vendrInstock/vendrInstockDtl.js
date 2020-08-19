/** 입고/반출정보 controller */
app.controller('vendrInstockDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrInstockDtlCtrl', $scope, $http, true));

  // 발주/미발주입고 구분
  $scope._setComboData("instockType", [
    {"name": messages["vendrInstock.dtl.orderInstock"], "value": "Y"},
    {"name": messages["vendrInstock.dtl.notOrderInstock"], "value": "N"}
  ]);

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("vendrInstockDtlCtrl", function (event, data) {
    $scope.slipNo = data.slipNo;
    $scope.slipFg = data.slipFg;

    // 발주/무발주입고에 대한 내용을 발주입고 상태로 layer show
    $scope.orderLayerShowFg = true;

    // 값 초기화
    $scope.setDefault();
    // 입고/반출 텍스트 세팅
    $scope.setText();

    // 신규등록이 아닌 경우 상세정보 조회
    if ($scope.slipNo !== '') {
      $scope.getSlipInfo();
    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 기본값 설정 및 버튼 세팅
  $scope.setDefault = function () {
    // 등록한 내역 조회를 한 후 instockType 값을 조회해 와서 세팅하면서 이벤트가 발생하는데
    // DB에서 조회해 온 값으로 세팅하는 경우에는 초기화하지 않기 위해 slipSearchYn 변수를 사용.
    $scope.slipSearchYn = 'N';

    if($scope.slipFg === 1) {
      $scope.btnSaveShowFg                         = true;  // 저장
      $scope.btnDelShowFg                          = false; // 삭제
      $scope.selectOrderSlipShowFg                 = true;  // 발주번호선택
      $scope.procLayerIfFg                         = false; // 진행상태내역
      $scope.instockTypeDisabledFg                 = false; // 발주/무발주 combo
      $scope.vendrInstockDtlSelectVendrNmDisabled  = false; // 입고 거래처선택 모듈 input
      $scope.vendrInstockDtlSelectVendrBtnDisabled = false; // 입고 선택취소

      $("#vendrInstockDtlSelectVendrCd").val('');
      $("#vendrInstockDtlSelectVendrNm").val('선택');
    } else {
      $scope.btnSaveShowFg                         = true;  // 저장
      $scope.btnDelShowFg                          = false; // 삭제
      $scope.vendrInstockDtlRtnSelectVendrNmDisabled  = false; // 반출 거래처선택 모듈 input
      $scope.vendrInstockDtlRtnSelectVendrBtnDisabled = false; // 반출 선택취소

      $("#vendrInstockDtlRtnSelectVendrCd").val('');
      $("#vendrInstockDtlRtnSelectVendrNm").val('선택');
    }

    // $("#vendrInstockDtlSelectVendrCd").val('');
    // $("#vendrInstockDtlSelectVendrNm").val('선택');

    // 기본값 설정
    $scope.default = {
      instockType: 'Y',
      orderSlipNo: '',
      instockDate: new Date(),
      remark     : '',
    };

    $scope.slipInfo = angular.copy($scope.default);
  };


  // slipFg 값에 따라 입고 또는 반출에 대한 텍스트를 설정한다.
  $scope.setText = function () {
    // 입고인 경우
    if ($scope.slipFg === 1) {
      $scope.instockLayerIfFg = true;
      $scope.rtnLayerIfFg     = false;

      $scope.instockDateTxt = messages["vendrInstock.dtl.instockDate"];
      $scope.instockTotQtyTxt = messages["vendrInstock.dtl.inTotQty"];
      $scope.instockTotAmtTxt = messages["vendrInstock.dtl.inTotAmt"];
    }
    // 반출인 경우
    else if ($scope.slipFg === -1) {
      $scope.instockLayerIfFg = false;
      $scope.rtnLayerIfFg     = true;

      $scope.instockDateTxt = messages["vendrInstock.dtl.rtnDate"];
      $scope.instockTotQtyTxt = messages["vendrInstock.dtl.rtnTotQty"];
      $scope.instockTotAmtTxt = messages["vendrInstock.dtl.rtnTotAmt"];      
    }
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
      url    : "/iostock/vendr/vendrInstock/vendrInstockDtl/slipInfo.sb", /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 진행구분이 조정등록이 아니면 상품추가/변경 불가
        if (!$.isEmptyObject(response.data.data)) {
          var data            = response.data.data;
          $scope.slipSearchYn = 'Y';

          // 전표상태가 등록인 경우 버튼 컨트롤
          if (data.procFg != "" && data.procFg == "0") {
            $scope.btnSaveShowFg             = true;
            $scope.btnDelShowFg              = true;
            $scope.btnDelShowFg              = true;
            $scope.btnDtlConfirmShowFg       = true;
            $scope.btnDtlConfirmCancelShowFg = false;
            $scope.procNm                    = messages["vendrInstock.dtl.procFg0"];
          }
          // 전표상태가 확정인 경우 버튼 컨트롤
          else {
            $scope.btnSaveShowFg             = false;
            $scope.btnDelShowFg              = false;
            $scope.btnDtlConfirmShowFg       = false;
            $scope.btnDtlConfirmCancelShowFg = true;
            $scope.procNm                    = messages["vendrInstock.dtl.procFg1"];
          }

          // 진행상태 관련 레이어 show 여부
          $scope.procLayerIfFg = true;

          // 입고
          if ($scope.slipFg === 1) {
            // 무발주 입고인 경우
            if (data.instockType === 'N') {
              // 거래처 선택 모듈값 세팅
              $("#vendrInstockDtlSelectVendrCd").val(data.vendrCd);
              $("#vendrInstockDtlSelectVendrNm").val('[' + data.vendrCd + '] ' + data.vendrNm);
            }
          }
          // 반출
          else if ($scope.slipFg === -1) {
            // 거래처 선택 모듈값 세팅
            $("#vendrInstockDtlRtnSelectVendrCd").val(data.vendrCd);
            $("#vendrInstockDtlRtnSelectVendrNm").val('[' + data.vendrCd + '] ' + data.vendrNm);
          }

          data.instockDate = new Date(getFormatDate(data.instockDate, "-"));

          data.regDt    = (nvl(data.regDt, '') !== '' ? getFormatDateTime(data.regDt) : '');
          data.confmDt  = (nvl(data.confmDt, '') !== '' ? getFormatDateTime(data.confmDt) : '');
          data.inTotQty = (nvl(data.inTotQty, '') !== '' ? addComma(data.inTotQty) : '');
          data.inTot    = (nvl(data.inTot, '') !== '' ? addComma(data.inTot) : '');

          // 상품이 등록되어 있는 경우에는 거래처 선택 불가
          if (data.dtlCnt > 0) {
            // 발주/무발주 입고 disabled
            $scope.instockTypeDisabledFg = true;

            // 입고
            if ($scope.slipFg === 1) {
              // 발주 입고인 경우
              if (data.instockType === 'Y') {
                // 발주번호선택 버튼 show 여부
                $scope.selectOrderSlipShowFg = false;
              } else if (data.instockType === 'N') {
                // 거래처선택 모듈 disabled 컨트롤
                $scope.vendrInstockDtlSelectVendrNmDisabled  = true;
                $scope.vendrInstockDtlSelectVendrBtnDisabled = true;
              }
            }
            // 반출
            else if ($scope.slipFg === -1) {
              // 거래처선택 모듈 disabled 컨트롤
              $scope.vendrInstockDtlRtnSelectVendrNmDisabled  = true;
              $scope.vendrInstockDtlRtnSelectVendrBtnDisabled = true;
            }
          }
          // 상품이 등록되어 있지 않은 경우
          else {
            $scope.instockTypeDisabledFg = false; // 발주/무발주 combo disabled
            $scope.selectOrderSlipShowFg = true;  // 발주번호선택 버튼 show 여부

            // 입고
            if ($scope.slipFg === 1) {
              // 거래처선택 모듈 disabled 컨트롤
              $scope.vendrInstockDtlSelectVendrNmDisabled  = false;
              $scope.vendrInstockDtlSelectVendrBtnDisabled = false;
            }
            // 반출
            else if ($scope.slipFg === -1) {
              // 거래처선택 모듈 disabled 컨트롤
              $scope.vendrInstockDtlRtnSelectVendrNmDisabled  = false;
              $scope.vendrInstockDtlRtnSelectVendrBtnDisabled = false;
            }
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


  // 입고/반출 저장
  $scope.submitForm = function () {
    //값체크
    if (!$scope.valueCheck()) return false;

    var params = {};
    // 저장시 저장은 잘 되지만 Assertion failed in Wijmo: Date expected. 라고 debug에서 에러가 발생해 따로따로 넣도록 수정.
    // params              = $scope.slipInfo;
    params.slipNo      = $scope.slipNo;
    params.slipFg      = $scope.slipFg;
    params.instockDate = wijmo.Globalize.format($scope.slipInfo.instockDate, 'yyyyMMdd');
    params.remark      = $scope.slipInfo.remark;

    // 입고
    if ($scope.slipFg === 1) {
      params.vendrCd     = ($scope.slipInfo.instockType === 'Y' ? $scope.slipInfo.vendrCd : $("#vendrInstockDtlSelectVendrCd").val());
      params.orderSlipNo = $scope.slipInfo.orderSlipNo;
      params.instockType = $scope.slipInfo.instockType;
    }
    // 반출
    else if ($scope.slipFg === -1) {
      params.vendrCd = $("#vendrInstockDtlRtnSelectVendrCd").val();
    }
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }
    
    $http({
      method : 'POST', //방식
      url    : "/iostock/vendr/vendrInstock/vendrInstockDtl/save.sb", /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        $scope._popMsg(messages['cmm.saveSucc']);

        if (!$.isEmptyObject(response.data.data)) {
          var data = response.data.data;

          // 입고/반출 리스트 그리드 조회
          var vendrInstockScope = agrid.getScope('vendrInstockCtrl');
          vendrInstockScope.searchVendrInstockList();

          var params     = {};
          params.slipNo  = data.slipNo;
          params.slipFg  = parseInt(data.slipFg);
          params.vendrCd = data.vendrCd;
          $scope._broadcast('vendrInstockPopCtrl', params);
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
    if ($scope.slipFg === 1) {
      // 발주 입고인 경우
      if ($scope.slipInfo.instockType === 'Y') {
        // 발주타입을 선택해주세요.
        if ($scope.slipInfo.orderSlipNo === "") {
          $scope._popMsg(messages["vendrInstock.dtl.require.selectOrderSlipNo"]);
          return false;
        }
      }
      // 무발주 입고인 경우
      else {
        // 거래처를 선택해주세요.
        if ($("#vendrInstockDtlSelectVendrCd").val() === "") {
          $scope._popMsg(messages["vendrInstock.dtl.require.selectVendr"]);
          return false;
        }
      }
    } else if ($scope.slipFg === -1) {
      // 거래처를 선택해주세요.
      if ($("#vendrInstockDtlRtnSelectVendrCd").val() === "") {
        $scope._popMsg(messages["vendrInstock.dtl.require.selectVendr"]);
        return false;
      }
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
        url    : "/iostock/vendr/vendrInstock/vendrInstockDtl/delete.sb", /* 통신할 URL */
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


  // 확정 및 확정취소
  $scope.confirm = function (procFg) {
    var msg = '';
        
    var inTotQty = $scope.slipInfo.inTotQty;
    if(inTotQty <= 0 ){
    	msg = messages["vendrInstock.dtl.confirmCheck"];
    	$scope._popMsg(msg);
    	return false;
    }
    
    
    if (procFg === '1') {
      /** 확정처리 하시겠습니까? */
      msg = messages["vendrInstock.dtl.confirmMsg"];
    } 
    else if (procFg === '0') {
      /** 확정취소 하시겠습니까? */
      msg = messages["vendrInstock.dtl.confirmCancelMsg"];
    }

    s_alert.popConf(msg, function () {
      var params         = {};
      params.slipNo      = $scope.slipNo;
      params.procFg      = procFg;

      // 입고이면서 발주입고인 경우만 발주번호를 파라미터에 세팅
      if ($scope.slipFg === 1) {
        params.orderSlipNo = ($scope.slipInfo.instockType === 'Y' ? $scope.slipInfo.orderSlipNo : '');
      }
      
      //가상로그인 session 설정
      if(document.getElementsByName('sessionId')[0]){
      	params['sid'] = document.getElementsByName('sessionId')[0].value;
      }
      
      $http({
        method : 'POST', //방식
        url    : "/iostock/vendr/vendrInstock/vendrInstockDtl/saveProcFg.sb", /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if (response.data.status === 'OK') {
          $scope._popMsg(messages['cmm.saveSucc']);

          // 입고/반출 리스트 그리드 조회
          var vendrInstockScope = agrid.getScope('vendrInstockCtrl');
          vendrInstockScope.searchVendrInstockList();

          var params    = {};
          params.slipNo = $scope.slipNo;
          params.slipFg = $scope.slipFg;

          // 입고
          if ($scope.slipFg === 1) {
            params.vendrCd = ($scope.slipInfo.instockType === 'Y' ? $scope.slipInfo.vendrCd : $("#vendrInstockDtlSelectVendrCd").val());
          }
          // 반출
          else if ($scope.slipFg === -1) {
            params.vendrCd = $("#vendrInstockDtlRtnSelectVendrCd").val();
          }

          $scope._broadcast('vendrInstockPopCtrl', params);
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


  // 발주번호선택 팝업 호출
  $scope.selectOrderSlip = function () {
    var params = {};
    $scope._broadcast('vendrInstockOrderSlipCtrl', params);
  };


  // 팝업 닫기, 입고/반출 리스트 그리드 조회
  $scope.popupClose = function () {
    // 팝업 닫기
    var vendrInstockPopScope = agrid.getScope("vendrInstockPopCtrl");
    vendrInstockPopScope.wjVendrInstockPopLayer.hide(true);

    // 입고/반출 리스트 그리드 조회
    var vendrInstockScope = agrid.getScope('vendrInstockCtrl');
    vendrInstockScope.searchVendrInstockList();
  };


  angular.element(document).ready(function () {
    // 발주/무발주 입고 선택시 이벤트.
    // 콤보를 생성하면서 이벤트가 한번 발생하는데 컨트롤하는 내역이 페이지에 생성되지 않은 경우가 발생하여 document ready가 되면 이벤트 생성.
    $scope.selectedIndexChanged = function (s, e) {
      // 발주 입고
      if (s.selectedValue === "Y") {
        $scope.orderLayerShowFg    = true;
        $scope.notOrderLayerShowFg = false;
      }
      //무발주 입고
      else {
        $scope.orderLayerShowFg    = false;
        $scope.notOrderLayerShowFg = true;
      }

      // 등록한 내역 조회를 한 후 instockType 값을 조회해 와서 세팅하면서 이벤트가 발생하는데
      // DB에서 조회해 온 값으로 세팅하는 경우에는 초기화하지 않기 위해 slipSearchYn 변수를 사용.
      if ($scope.slipSearchYn === 'N') { // 등록한 내역 조회인 경우에는 초기화하지 않는다.
        // 발주/무발주 선택변경시 값 초기화
        $scope.slipInfo.orderSlipNo = '';
        $scope.slipInfo.vendrCd     = '';
        $scope.slipInfo.vendrNm     = '-거래처-';
        $("#vendrInstockDtlSelectVendrCd").val('');
        $("#vendrInstockDtlSelectVendrNm").val('선택');
      } else {
        $scope.slipSearchYn = 'N';
      }
    };
  });




  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.vendrInstockDtlSelectVendrShow = function () {
    $scope._broadcast('vendrInstockDtlSelectVendrCtrl');
  };

  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.vendrInstockDtlRtnSelectVendrShow = function () {
    $scope._broadcast('vendrInstockDtlRtnSelectVendrCtrl');
  };


}]);
