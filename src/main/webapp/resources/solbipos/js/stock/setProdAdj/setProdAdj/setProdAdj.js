/**
 * get application
 */
var app = agrid.getApp();

/** 세트재고조정 그리드 controller */
app.controller('setProdAdjCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('setProdAdjCtrl', $scope, $http, true));

  $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  $scope.setMakeFgMap = new wijmo.grid.DataMap([
    {id: "1", name: messages["setProdAdj.setMakeFg1"]},
    {id: "2", name: messages["setProdAdj.setMakeFg2"]}
  ], 'id', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("setProdAdjCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

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
  $scope.$on("setProdAdjCtrl", function (event, data) {
    $scope.searchSetProdAdjList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 리스트 조회
  $scope.searchSetProdAdjList = function () {
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/setProdAdj/setProdAdj/setProdAdj/list.sb", params);
  };


  // 세트 삭제
  $scope.deleteSetProdAdj = function () {
    /** 선택하신 자료를 삭제하시겠습니까? */
    var msg = messages["setProdAdj.delMsg"];
    s_alert.popConf(msg, function () {
      var params = [];

      if ($scope.flex.collectionView.itemsEdited.length <= 0) {
        $scope._popMsg(messages["cmm.not.modify"]);
        return false;
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];
        if (item.gChk === true) {
          item.status = "U";
          params.push(item);
        }
      }

      $scope._save("/stock/setProdAdj/setProdAdj/setProdAdj/delete.sb", params, function () {
        $scope.searchSetProdAdjList();
      });
    });
  };


  // 세트 구성/해체 등록
  $scope.newSetProdAdj = function () {
    var params = {};
    $scope._broadcast('setProdAdjRegistCtrl', params);
  };


}]);
