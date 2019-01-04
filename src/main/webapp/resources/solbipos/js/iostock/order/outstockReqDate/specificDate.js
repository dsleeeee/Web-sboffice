/** 특정일 그리드 controller */
app.controller('specificCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('specificCtrl', $scope, $http, true));

  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("specificCtrl");

    // 그리드 DataMap 설정
    $scope.sysStatFgMap     = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.outstockReqYnMap = new wijmo.grid.DataMap([
      {id: "Y", name: messages["outstockReqDate.outstockReqYnY"]},
      {id: "N", name: messages["outstockReqDate.outstockReqYnN"]},
    ], 'id', 'name');

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        // if (col.binding === "storeCd") {
        //   wijmo.addClass(e.cell, 'wijLink');
        //   wijmo.addClass(e.cell, 'wj-custom-readonly');
        // }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
        else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("specificCtrl", function (event, data) {
    $scope.searchspecificDateList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 특정일 그리드 조회
  $scope.searchspecificDateList = function () {
    // 파라미터
    var params     = {};
    params.storeCd = $("#speSelectStoreCd").val();
    // params.listScale = 15;
    // params.listScale = listScaleBoxSpecific.selectedValue;
    // params.curr = 1;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/order/outstockReqDate/specificDate/list.sb", params);
  };

  // 특정일 신규등록
  $scope.newSpecificDate = function () {
    $scope._broadcast('speDateRegistCtrl');
  };

  // 특정일 저장
  $scope.saveSpecificDate = function () {
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    $scope._save("/iostock/order/outstockReqDate/specificDate/save.sb", params, function () {
      $scope.searchspecificDateList()
    });
  };

  // 특정일 삭제
  $scope.deleteSpecificDate = function () {
    // 삭제 하시겠습니까?
    var msg = messages["cmm.choo.delete"];
    s_alert.popConf(msg, function () {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        $scope.flex.collectionView.itemsEdited[i].status = "U";
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }
      $scope._save("/iostock/order/outstockReqDate/specificDate/delete.sb", params, function () {
        $scope.searchspecificDateList()
      });
    });
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.speSelectStoreShow = function () {
    $scope._broadcast('speSelectStoreCtrl');
  };

}]);
