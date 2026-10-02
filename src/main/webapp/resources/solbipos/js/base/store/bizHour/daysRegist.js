/** 요일별 신규등록 controller */
app.controller('daysRegistCtrl', ['$scope', '$http', function ($scope, $http) {
  $scope.default = {
    dayFg    : "1",
    startHour: "00",
    startMs  : "00",
    endHour  : "23",
    endMs    : "59"
  };
  $scope.dayData = angular.copy($scope.default);

  $scope._setComboData("daysRegistDayCombo", dayFgData);
  $scope._setComboData("daysStartHourCombo", Hh);
  $scope._setComboData("daysStartMsCombo", MmSs);
  $scope._setComboData("daysEndHourCombo", Hh);
  $scope._setComboData("daysEndMsCombo", MmSs);

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on('daysRegistCtrl', function (event, paramObj) {
    // 신규등록 팝업 오픈
    $scope.wjDaysRegistLayer.show(true);
    $scope.dayData = angular.copy($scope.default);
    // 매장선택 모듈 초기화
    $("#daysRegistStoreCd").val("");
    $("#daysRegistStoreNm").val("선택");
    event.preventDefault();
  });

  // 요일별 저장
  $scope.submitForm = function () {
    // 매장 체크(본사)
    if (orgnFg === "HQ" && $("#daysRegistStoreCd").val() === "") {
      s_alert.popOk(messages["bizHour.require.selectStore"]);
      return false;
    }

    var startTime = $scope.daysStartHourCtrl.selectedValue + $scope.daysStartMsCtrl.selectedValue;
    var endTime   = $scope.daysEndHourCtrl.selectedValue + $scope.daysEndMsCtrl.selectedValue;

    // 시작 < 종료 체크
    if (startTime >= endTime) {
      s_alert.popOk(messages["bizHour.invalidTime"]);
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

    if (orgnFg === "STORE") {
      $scope.dayData.storeCd = orgnCd;
    } else {
      $scope.dayData.storeCd = $("#daysRegistStoreCd").val();
    }
    $scope.dayData.dayFg     = $scope.daysRegistDayComboCtrl.selectedValue;
    $scope.dayData.startTime = startTime;
    $scope.dayData.endTime   = endTime;

    // 가상로그인 session 설정
    if (document.getElementsByName('sessionId')[0]) {
      $scope.dayData.sid = document.getElementsByName('sessionId')[0].value;
    }

    $http({
      method : 'POST',
      url    : baseUrl + "days/saveNew.sb",
      params : $scope.dayData,
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
        $scope._broadcast('daysCtrl');
        $scope.wjDaysRegistLayer.hide();
      }
    }, function errorCallback(response) {
      $scope.$broadcast('loadingPopupInactive');
      $scope._popMsg((response && response.data && response.data.message) ? response.data.message : messages["cmm.saveFail"]);
      return false;
    });
  };

  // 매장선택 모듈
  $scope.daysRegistStoreShow = function () {
    $scope._broadcast('daysRegistStoreCtrl');
  };

}]);
