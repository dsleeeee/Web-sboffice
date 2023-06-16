/**
 * get application
 */
var app = agrid.getApp();


app.controller('alimtalkCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('alimtalkCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("alimtalkCtrl");

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
      }
    });

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("alimtalkCtrl", function (event, data) {
    $scope.searchAlimtalkList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 리스트 조회
  $scope.searchAlimtalkList = function () {
    // 파라미터
    var params       = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/adi/etc/alimtalk/alimtalk/getAlimtalkList.sb", params, function (){});
  };

  // 그리드 행 추가
  $scope.addRow = function() {
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.alimtalkFg = "0";
    params.mpNo="";
    params.mpInfo = "";
    params.remark = "";
    $scope._addRow(params);
  };

  // 그리드 행 삭제
  $scope.delete = function(){
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
      var item = $scope.flex.collectionView.items[i];
      if(item.gChk){
          $scope.flex.collectionView.removeAt(i);
      }
    }
  };

  // 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      if($scope.flex.collectionView.itemsEdited[i].mpInfo.getByteLengthForOracle() > 200){
        $scope._popMsg(messages["alimtalk.mpInfo"] + messages["alimtalk.max200Chk"]);
        return false;
      }
      if($scope.flex.collectionView.itemsEdited[i].remark.getByteLengthForOracle() > 200){
        $scope._popMsg(messages["alimtalk.remark"] + messages["alimtalk.max200Chk"]);
        return false;
      }
      if($scope.flex.collectionView.itemsEdited[i].mpNo !== ""){
        if($scope.flex.collectionView.itemsEdited[i].remark.getByteLengthForOracle() > 15){
          $scope._popMsg(messages["alimtalk.mpNo"] + messages["alimtalk.max15Chk"]);
          return false;
        } else {
          $scope.flex.collectionView.itemsEdited[i].status = "U";
          params.push($scope.flex.collectionView.itemsEdited[i]);
        }
      } else {
        $scope._popMsg(messages["alimtalk.mpNo"] + messages["alimtalk.mpNoChkMsg"]);
        return false;
      }
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      if($scope.flex.collectionView.itemsAdded[i].mpInfo.getByteLengthForOracle() > 200){
        $scope._popMsg(messages["alimtalk.mpInfo"] + messages["alimtalk.max200Chk"]);
        return false;
      }
      if($scope.flex.collectionView.itemsAdded[i].remark.getByteLengthForOracle() > 200){
        $scope._popMsg(messages["alimtalk.remark"] + messages["alimtalk.max200Chk"]);
        return false;
      }
      if($scope.flex.collectionView.itemsAdded[i].mpNo !== ""){
        if($scope.flex.collectionView.itemsAdded[i].mpNo.getByteLengthForOracle() > 15) {
          $scope._popMsg(messages["alimtalk.mpNo"] + messages["alimtalk.max15Chk"]);
          return false;
        } else {
          $scope.flex.collectionView.itemsAdded[i].status = "U";
          params.push($scope.flex.collectionView.itemsAdded[i]);
        }
      } else {
        $scope._popMsg(messages["alimtalk.mpNo"] + messages["alimtalk.mpNoChkMsg"]);
        return false;
      }
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // console.log(params);

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/adi/etc/alimtalk/alimtalk/getAlimtalkSave.sb", params, function(){ $scope.getKitchenMemoList() });
  }

}]);
