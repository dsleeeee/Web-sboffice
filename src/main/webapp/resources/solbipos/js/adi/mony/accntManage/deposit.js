/****************************************************************
 *
 * 파일명 : deposit.js
 * 설  명 : 계정관리 설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.12     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/***************************************************************************
 *  입금계정 그리드 생성
 ***************************************************************************/
app.controller('depositCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('depositCtrl', $scope, $http, true));

  // comboBox 초기화
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');
    $scope.searchDepositAccntList();
  };

  $scope.$on("depositCtrl", function(event, data) {
    event.preventDefault();
  });

  // 입금계정 조회
  $scope.searchDepositAccntList = function(){
    // 파라미터
    var params = {};
    params.accntFg="1"; // 입금

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "deposit/getDepositAccntList.sb", params, function() {
      // 입금계정 조회 완료 후, 출금계정 조회
      var withdrawScope = agrid.getScope('withdrawCtrl');
      withdrawScope.searchWithdrawAccntList();

      $("#depositBtnArea").show();

    }, false);
  };

  // 입금계정 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.accntCd="자동채번";
    params.accntFg="1"; // 입금
    params.useYn = "Y";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 입금계정 그리드 삭제
  $scope.delete = function() {
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      if ($scope.flex.collectionView.items[i].gChk === true) {
        $scope.flex.itemsSource.removeAt(i);
      }
    }
  };

  // 입금계정 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "deposit/saveDepositAccntList.sb", params,
      function(){
        $scope.searchDepositAccntList();
      }
    );
  };

}]);


/**************************************************************************
 *  출금계정 그리드 생성
 **************************************************************************/
app.controller('withdrawCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('withdrawCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');
  };

  $scope.$on("withdrawCtrl", function(event, data) {
    event.preventDefault();
  });

  // 출금계정 조회
  $scope.searchWithdrawAccntList = function(){
    var params = {};
    params.accntFg="2"; // 출금

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "deposit/getDepositAccntList.sb", params, function() {

      $("#withdrawBtnArea").show();

    }, false);
  };

  // 출금계정 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.accntCd="자동채번";
    params.accntFg="2"; // 출금
    params.useYn = "Y";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 출금계정 그리드 삭제
  $scope.delete = function() {
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      if ($scope.flex.collectionView.items[i].gChk === true) {
        $scope.flex.itemsSource.removeAt(i);
      }
    }
  };

  // 출금계정 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "deposit/saveDepositAccntList.sb", params,
        function(){
          $scope.searchDepositAccntList();
        }
    );
  };
}]);


