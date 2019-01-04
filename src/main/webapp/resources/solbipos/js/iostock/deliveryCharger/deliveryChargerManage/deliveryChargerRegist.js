/** 배송기사 관리 상세 controller */
app.controller('dlvrRegistCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dlvrRegistCtrl', $scope, $http, true));

  $scope.default = {useYn: "Y"};

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on('dlvrRegistCtrl', function (event, data) {
    $scope.dlvrCd = data.dlvrCd;
    $scope.dlvrNm = data.dlvrNm;

    // 배송기사 상세 팝업 오픈
    $scope.wjDlvrRegistLayer.show(true);

    // 배송기사코드가 있는 경우 배송기사 상세 조회
    if ($scope.dlvrCd !== '') {
      // 타이틀의 배송기사 명칭 세팅
      $("#registTitleDlvrNm").html("[" + $scope.dlvrNm + "]");
      // 배송기사 상세 조회
      $scope.searchDlvrInfo();

      // 그리드 조회
      $scope.searchDlvrChgrStorageList();

      // 버튼 show
      $scope.btnDel = true;
      $scope.btnAddStorage = true;
      $scope.btnDelStorage = true;
    }
    // 신규등록인 경우
    else {
      $("#registTitleDlvrNm").html("신규등록");
      $scope.dlvr = angular.copy($scope.default); // 기본값 세팅

      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      // 버튼 hide
      $scope.btnDel = false;
      $scope.btnAddStorage = false;
      $scope.btnDelStorage = false;
    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 배송기사 상세 조회
  $scope.searchDlvrInfo = function () {
    var param    = {};
    param.dlvrCd = $scope.dlvrCd;

    $http({
      method : 'POST', //방식
      url    : "/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/dlvrInfo.sb", /* 통신할 URL */
      params : param, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      // this callback will be called asynchronously
      // when the response is available

      var data = response.data.data;
      if (data === undefined || data === null) {
        // 팝업 닫기 및 값 초기화
        $scope.popupClose();
        s_alert.pop(response.data.message);
        return false;
      }

      $scope.dlvr = data;
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      s_alert.pop(response.data.message);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };


  // 배송기사 저장
  $scope.submitForm = function () {
    //값체크
    if (!valueCheck()) return false;

    $http({
      method : 'POST', //방식
      url    : "/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/dlvrSave.sb", /* 통신할 URL */
      params : $scope.dlvr, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        s_alert.pop(messages["cmm.saveSucc"]);

        // 신규등록인 경우
        if ($scope.dlvrCd === '') {
          // 팝업 닫기 및 값 초기화
          $scope.popupClose();
        }
        else {
          // 배송기사 리스트 그리드 조회
          var dlvrChgrScope = agrid.getScope('dlvrChgrListCtrl');
          dlvrChgrScope.searchDlvrChgrList();
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      s_alert.pop(response.data.message);
      return false;
    });
  };


  // 배송기사 삭제
  $scope.fnDlvrDel = function () {
    var msg = messages["deliveryCharger.delConfirmMsg"]; // 배송기사와 등록된 관리창고가 모두 삭제됩니다. 삭제하시겠습니까?
    s_alert.popConf(msg, function() {
      var params    = {};
      params.dlvrCd = $scope.dlvrCd;

      // 로딩바 show
      $scope.$broadcast('loadingPopupActive', messages['cmm.saving']);

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/deleteDlvr.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          s_alert.pop(messages["cmm.saveSucc"]);
          $scope.popupClose();
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        $scope._popMsg(messages["cmm.saveFail"]);
        return false;
      }).then(function () {
        // "complete" code here
        // 로딩바 hide
        $scope.$broadcast('loadingPopupInactive');
        // $scope.popupClose();
      });
    });
  };


  // 팝업 닫기, 값 초기화, 배송기사 리스트 그리드 조회
  $scope.popupClose = function () {
    // 값 초기화
    $scope.dlvr = angular.copy($scope.default);
    $scope.wjDlvrRegistLayer.hide(true);
    $("#registTitleDlvrNm").html("");

    // 배송기사 리스트 그리드 조회
    var dlvrChgrScope = agrid.getScope('dlvrChgrListCtrl');
    dlvrChgrScope.searchDlvrChgrList();
  };


  // 그리드 조회
  $scope.searchDlvrChgrStorageList = function () {
    // 파라미터
    var params    = {};
    params.dlvrCd = $scope.dlvrCd;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/list.sb", params, "", false);
  };


  // 창고 삭제
  $scope.delStorage = function () {
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    $scope._save("/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/delete.sb", params, function () {
      $scope.searchDlvrChgrStorageList();
    });
  };


  // 창고 추가 팝업 오픈
  $scope.openPopAddStorage = function () {
    var params    = {};
    params.dlvrCd = $scope.dlvrCd;
    params.dlvrNm = $scope.dlvrNm;
    $scope._broadcast('dlvrStorageMgrCtrl', params);
  };

}]);

// 값 체크
function valueCheck() {
  /** 기사명을 입력해주세요. */
  var msg = messages["deliveryCharger.dlvrNm"] + " " + messages["cmm.require.text"];
  if ($("#dlvrNm").val() === "") {
    s_alert.popOk(msg, function () {
      $("#dlvrNm").focus();
    });
    return false;
  }

  /** 기사명의 길이가 너무 깁니다. */
  var msg = messages["deliveryCharger.dlvrNm"] + " " + messages["deliveryCharger.textOver"];
  if ($("#dlvrNm").val().getByteLengthForOracle() > 18) {
    s_alert.popOk(msg, function () {
      $("#dlvrNm").select();
    });
    return false;
  }

  /** 차량번호를 입력해주세요. */
  var msg = messages["deliveryCharger.carNo"] + " " + messages["cmm.require.text"];
  if ($("#carNo").val() === "") {
    s_alert.popOk(msg, function () {
      $("#carNo").focus();
    });
    return false;
  }

  /** 차량번호의 길이가 너무 깁니다. */
  var msg = messages["deliveryCharger.carNo"] + " " + messages["deliveryCharger.textOver"];
  if ($("#carNo").val().getByteLengthForOracle() > 14) {
    s_alert.popOk(msg, function () {
      $("#carNo").select();
    });
    return false;
  }

  /** 전화번호는 숫자만 입력할 수 있습니다. */
  var msg          = messages["deliveryCharger.telNo"] + " " + messages["cmm.require.number"];
  var numChkregexp = /[^0-9]/g;
  if (numChkregexp.test($("#telNo").val())) {
    s_alert.popOk(msg, function () {
      $("#telNo").select();
    });
    return false;
  }

  /** 전화번호를 정확히 입력해주세요. */
  var msg = messages["deliveryCharger.telNo"] + " " + messages["deliveryCharger.validCheck"];
  if ($("#telNo").val() !== "" && $("#telNo").val().length < 10) {
    s_alert.popOk(msg, function () {
      $("#telNo").select();
    });
    return false;
  }

  /** 핸드폰번호는 숫자만 입력할 수 있습니다. */
  var msg = messages["deliveryCharger.hpNo"] + " " + messages["cmm.require.number"];
  if (numChkregexp.test($("#hpNo").val())) {
    s_alert.popOk(msg, function () {
      $("#hpNo").select();
    });
    return false;
  }

  /** 핸드폰번호를 정확히 입력해주세요. */
  var msg = messages["deliveryCharger.hpNo"] + " " + messages["deliveryCharger.validCheck"];
  if ($("#hpNo").val() !== "" && $("#hpNo").val().length < 10) {
    s_alert.popOk(msg, function () {
      $("#hpNo").select();
    });
    return false;
  }
  return true;
}
