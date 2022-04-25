/**
 * get application
 */
var app = agrid.getApp();

/** 신용카드 상세 팝업 내역 controller */
app.controller('storeIndexNoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeIndexNoCtrl', $scope, $http, true));
  var gubun;
  var startDate;
  var endDate;
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        } else if (col.format === "time") {
          e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storeIndexNoCtrl", function (event, data) {
    $scope.wjStoreIndexNoLayer.show(true);
    $scope.searchStoreIndexNoList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 리스트 조회
  $scope.searchStoreIndexNoList = function (data) {
    // 파라미터
    var params      = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/anals/store/rank/getStoreList.sb", params);
  };

  // 위로 옮기기 버튼
  $scope.rowMoveUp = function() {
    var movedRows = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
      var item = $scope.flex.collectionView.items[i];
      if (i > 0 && item.gChk) {
        if (!$scope.flex.collectionView.items[i - 1].gChk) {
          movedRows = i - 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };
  // 아래로 옮기기 버튼
  $scope.rowMoveDown = function() {
    var movedRows = 0;
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      var item = $scope.flex.collectionView.items[i];
      if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
        if (!$scope.flex.collectionView.items[i + 1].gChk) {
          movedRows = i + 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };

  $scope.save = function () {
    var params = new Array();

    for(var i = 0; i < $scope.flex.collectionView.items.length; i++ ){
      $scope.flex.collectionView.items[i].indexNo = i + 1;
      params.push($scope.flex.collectionView.items[i]);
    }

    $scope._save("/sale/anals/store/rank/saveStoreIndexNo.sb", params, function(){
      $scope.searchStoreIndexNoList();
    });
  };

}]);
