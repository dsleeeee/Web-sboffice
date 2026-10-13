/****************************************************************
 *
 * 파일명 : bizHour.js
 * 설  명 : 매장영업시간관리 탭 JavaScript
 *
 *    수정일      수정자      Version
 * ------------  ---------   -------------
 * 2026.10.01     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('bizHourTabCtrl', ['$scope', function ($scope) {
  $scope.init = function () {
    $("#daysView").show();
  };

  // 요일별 탭 보이기
  $scope.daysShow = function () {
    $("#daysTab").addClass("on");
    $("#specificTab").removeClass("on");
    $("#bizHourCopyTab").removeClass("on");

    $("#daysView").show();
    $("#specificView").hide();
    $("#bizHourCopyView").hide();

    // angular 그리드 hide 시 깨지므로 refresh() (탭 전환 시 재조회 안 함)
    var scope = agrid.getScope("daysCtrl");
    scope.flex.refresh();
  };

  // 특정일 탭 보이기
  $scope.specificShow = function () {
    $("#daysTab").removeClass("on");
    $("#specificTab").addClass("on");
    $("#bizHourCopyTab").removeClass("on");

    $("#daysView").hide();
    $("#specificView").show();
    $("#bizHourCopyView").hide();

    // angular 그리드 hide 시 깨지므로 refresh() (탭 전환 시 재조회 안 함)
    var scope = agrid.getScope("specificCtrl");
    scope.flex.refresh();
  };

  // 영업시간 복사 탭 보이기 (본사 전용)
  $scope.bizHourCopyShow = function () {
    $("#daysTab").removeClass("on");
    $("#specificTab").removeClass("on");
    $("#bizHourCopyTab").addClass("on");

    $("#daysView").hide();
    $("#specificView").hide();
    $("#bizHourCopyView").show();

    // angular 그리드 hide 시 깨지므로 refresh()
    var daysScope = agrid.getScope("bizHourCopyDaysCtrl");
    if (daysScope && daysScope.flex) daysScope.flex.refresh();
    var speScope = agrid.getScope("bizHourCopySpecificCtrl");
    if (speScope && speScope.flex) speScope.flex.refresh();
  };

  // 매장영업시간관리 사용여부 팝업 열기 (본사 전용)
  $scope.useYnPopShow = function () {
    $scope._broadcast('bizHourUseYnPopCtrl');
  };

}]);

/** 매장영업시간관리 사용여부 (매장 전용 : 드롭다운 + 저장) */
app.controller('bizHourUseYnCtrl', ['$scope', '$http', '$rootScope', function ($scope, $http, $rootScope) {
  $scope.useYn = "0";
  $scope.orgUseYn = "0";   // 진입 시 로드한 원래값(변경 여부 판단용)
  $scope.useYnList = [
    {value: "1", name: "사용"},
    {value: "0", name: "미사용"}
  ];

  function withSid(params) {
    if (document.getElementsByName('sessionId')[0]) {
      params.sid = document.getElementsByName('sessionId')[0].value;
    }
    return params;
  }

  // 사용여부 조회
  $scope.loadUseYn = function () {
    $http({
      method : 'POST',
      url    : baseUrl + "useYn/get.sb",
      params : withSid({}),
      headers: {'Content-Type': 'application/json; charset=utf-8'}
    }).then(function (response) {
      if (response.data && response.data.status === "OK") {
        $scope.useYn = response.data.data || "0";
        $scope.orgUseYn = $scope.useYn;
      }
    });
  };

  // 사용여부 저장
  $scope.saveUseYn = function () {
    // 변경 없으면 저장 안 함
    if ($scope.useYn === $scope.orgUseYn) {
      s_alert.pop(messages["cmm.not.modify"]);
      return false;
    }
    $rootScope.$broadcast('loadingPopupActive', messages['cmm.saving']);
    $http({
      method : 'POST',
      url    : baseUrl + "useYn/save.sb",
      params : withSid({envstVal: $scope.useYn}),
      headers: {'Content-Type': 'application/json; charset=utf-8'}
    }).then(function (response) {
      $rootScope.$broadcast('loadingPopupInactive');
      if (response.data && response.data.status === "OK") {
        $scope.orgUseYn = $scope.useYn;
        s_alert.pop(messages["cmm.saveSucc"]);
      } else {
        s_alert.pop((response.data && response.data.message) ? response.data.message : messages["cmm.saveFail"]);
      }
    }, function (response) {
      $rootScope.$broadcast('loadingPopupInactive');
      s_alert.pop(messages["cmm.saveFail"]);
    });
  };
}]);
