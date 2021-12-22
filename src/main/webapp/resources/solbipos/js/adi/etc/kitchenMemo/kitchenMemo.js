/****************************************************************
 *
 * 파일명 : kitchenMemo.js
 * 설  명 : 주방메모 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 주방메모 그리드 생성
 */
app.controller('kitchenMemoCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('kitchenMemoCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    $scope.memoFgDataMap = new wijmo.grid.DataMap(memoFg, 'value', 'name');
    $scope.regFgDataMap = new wijmo.grid.DataMap(regFg, 'value', 'name');

    // 주방메모코드 ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "kitchnMemoCd") {
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
      }
    });

    // 주방메모코드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      var dataItem = s.rows[e.row].dataItem;

      // 쿠폰 코드는 입력 불가능
      if (col.binding === "kitchnMemoCd") {
        e.cancel = true;
      }

      if(col.binding !== "gChk") {
        if(orgnFg === 'STORE') {
          // 본사통제시 => 본사에서 등록한 메모만 조회 가능 / 수정 불가
          if(envstVal === '1') {
            e.cancel = true;
          }
          // 매장통제시 => 본사+매장에서 등록한 메모 조회 가능 / 매장에서 등록한 메모만 수정 가능
          else if(envstVal === '2') {
            if(dataItem.regFg === "H") {
              e.cancel = true;
            }else {
              e.cancel = false;
            }
          }
        }
      }

      // 메모 내용 length 체크 //TODO
    });

    // 주방메모 목록 조회
    $scope.getKitchenMemoList();

  };

  $scope.$on("kitchenMemoCtrl", function(event, data) {
    $scope.getKitchenMemoList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 주방메모 그리드 조회
  $scope.getKitchenMemoList = function(data){

    var params = {};
    params.envstVal = envstVal;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "getKitchenMemoList.sb", params, function(){}, false);

    if(orgnFg ==="HQ") {
      $("button").show();
    } else if(orgnFg === "STORE" && envstVal === "2"){
      $("button").show();
    } else {
      $("button").hide();
    }
  };

  // 주방메모 그리드 행 추가
  $scope.addRow = function() {
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.kitchnMemoCd="자동채번";
    params.memoFg = "2";
    params.useYn = "Y";
    if(orgnFg === "STORE") {
      params.regFg = "S";
    }
    $scope._addRow(params, 2);
  };

  // 주방메모 그리드 행 삭제
  $scope.delete = function(){
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
      var item = $scope.flex.collectionView.items[i];
      if(item.gChk){
        // 매장통제시, 본사에서 등록한 것 삭제 불가능
        if(orgnFg === 'STORE' && envstVal === '2' && item.regFg === "H"){
          // s_alert.pop();
          $scope._popMsg(messages['kitchenMemo.disable.delete']);
        } else {
          $scope.flex.collectionView.removeAt(i);
        }
      }
    }
  };

  // 주방메모 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      if($scope.flex.collectionView.itemsEdited[i].kitchnMemoNm !== ""){
        $scope.flex.collectionView.itemsEdited[i].status = "U";
        params.push($scope.flex.collectionView.itemsEdited[i]);
      } else {
        $scope._popMsg(messages["kitchenMemo.kitchnMemoNm"] + messages["kitchenMemo.inputEnv"]);
        return false;
      }
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      if($scope.flex.collectionView.itemsAdded[i].kitchnMemoNm !== ""){
        $scope.flex.collectionView.itemsAdded[i].status = "I";
        params.push($scope.flex.collectionView.itemsAdded[i]);
      } else {
        $scope._popMsg(messages["kitchenMemo.kitchnMemoNm"] + messages["kitchenMemo.inputEnv"]);
        return false;
      }
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // console.log(params);

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "saveKitchenMemo.sb", params, function(){ $scope.getKitchenMemoList() });
  }

}]);


