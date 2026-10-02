// 시 VALUE
var Hh = [24];
for (i = 0; i < 24; i++) {
  var timeVal = i.toString();
  if (i >= 0 && i <= 9) {
    timeVal = "0" + timeVal;
  }
  Hh[i] = {"name": timeVal, "value": timeVal}
}

// 분 VALUE
var MmSs = [60];
for (i = 0; i < 60; i++) {
  var timeVal = i.toString();
  if (i >= 0 && i <= 9) {
    timeVal = "0" + timeVal;
  }
  MmSs[i] = {"name": timeVal, "value": timeVal}
}

/** 특정일 신규등록 controller */
app.controller('speRegistCtrl', ['$scope', '$http', function ($scope, $http) {
  $scope.default = {
    bizFg    : "1",
    startHour: "00",
    startMs  : "00",
    endHour  : "23",
    endMs    : "59",
    remark   : ""
  };
  $scope.speData = angular.copy($scope.default);

  var bizDate = wcombo.genDate("#bizDate");
  $scope._setComboData("speBizFgCombo", bizFgData);
  $scope._setComboData("speStartHourCombo", Hh);
  $scope._setComboData("speStartMsCombo", MmSs);
  $scope._setComboData("speEndHourCombo", Hh);
  $scope._setComboData("speEndMsCombo", MmSs);

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on('speRegistCtrl', function (event, paramObj) {
    // 신규등록 팝업 오픈
    $scope.wjSpeRegistLayer.show(true);
    bizDate.value  = getCurDate('-'); // 특정일 오늘날짜로 초기화
    $scope.speData = angular.copy($scope.default);
    // 매장선택 모듈 초기화
    $("#speRegistStoreCd").val("");
    $("#speRegistStoreNm").val("선택");
    event.preventDefault();
  });

  // 특정일 저장
  $scope.submitForm = function () {
    // 매장 체크(본사)
    if (orgnFg === "HQ" && $("#speRegistStoreCd").val() === "") {
      s_alert.popOk(messages["bizHour.require.selectStore"]);
      return false;
    }

    // 비고 길이 체크
    if (nvl($scope.speData.remark, '') !== '' && $scope.speData.remark.getByteLengthForOracle() > 200) {
      s_alert.popOk(messages["bizHour.remark"] + " " + messages["cmm.overLength"] + " 200");
      return false;
    }

    var startTime = $scope.speStartHourCtrl.selectedValue + $scope.speStartMsCtrl.selectedValue;
    var endTime   = $scope.speEndHourCtrl.selectedValue + $scope.speEndMsCtrl.selectedValue;

    // 시작 < 종료 체크
    if (startTime >= endTime) {
      s_alert.popOk(messages["bizHour.invalidTime"]);
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

    $scope.speData.bizDate = wijmo.Globalize.format(bizDate.value, 'yyyyMMdd');
    if (orgnFg === "STORE") {
      $scope.speData.storeCd = orgnCd;
    } else {
      $scope.speData.storeCd = $("#speRegistStoreCd").val();
    }
    $scope.speData.bizFg     = $scope.speBizFgCtrl.selectedValue;
    $scope.speData.startTime = startTime;
    $scope.speData.endTime   = endTime;

    // 가상로그인 session 설정
    if (document.getElementsByName('sessionId')[0]) {
      $scope.speData.sid = document.getElementsByName('sessionId')[0].value;
    }

    $http({
      method : 'POST',
      url    : baseUrl + "specificDate/saveNew.sb",
      params : $scope.speData,
      headers: {'Content-Type': 'application/json; charset=utf-8'}
    }).then(function successCallback(response) {
      var resData = response.data;
      $scope.$broadcast('loadingPopupInactive');
      if (resData.status == "FAIL") {
        // 시간중복 등 업무오류 : 팝업 유지
        s_alert.pop(resData.message);
      } else if (resData.status == "SERVER_ERROR") {
        $scope._popMsg(messages["cmm.saveFail"]);
      } else {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope._broadcast('specificCtrl');
        $scope.wjSpeRegistLayer.hide();
      }
    }, function errorCallback(response) {
      $scope.$broadcast('loadingPopupInactive');
      $scope._popMsg((response && response.data && response.data.message) ? response.data.message : messages["cmm.saveFail"]);
      return false;
    });
  };

  // 매장선택 모듈
  $scope.speRegistStoreShow = function () {
    $scope._broadcast('speRegistStoreCtrl');
  };

}]);
