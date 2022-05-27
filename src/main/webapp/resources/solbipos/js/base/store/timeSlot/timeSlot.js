 /****************************************************************
 *
 * 파일명 : timeSlot.js
 * 설  명 : 시간대분류관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.20     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

 var timeData = [
   {"name":"00", "value":"0"},
   {"name":"01", "value":"1"},
   {"name":"02", "value":"2"},
   {"name":"03", "value":"3"},
   {"name":"04", "value":"4"},
   {"name":"05", "value":"5"},
   {"name":"06", "value":"6"},
   {"name":"07", "value":"7"},
   {"name":"08", "value":"8"},
   {"name":"09", "value":"9"},
   {"name":"10", "value":"10"},
   {"name":"11", "value":"11"},
   {"name":"12", "value":"12"},
   {"name":"13", "value":"13"},
   {"name":"14", "value":"14"},
   {"name":"15", "value":"15"},
   {"name":"16", "value":"16"},
   {"name":"17", "value":"17"},
   {"name":"18", "value":"18"},
   {"name":"19", "value":"19"},
   {"name":"20", "value":"20"},
   {"name":"21", "value":"21"},
   {"name":"22", "value":"22"},
   {"name":"23", "value":"23"},
   {"name":"24", "value":"24"}
 ];
/**
 * 시간대분류 그리드 생성
 */
app.controller('timeSlotCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('timeSlotCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.timeDataMap = new wijmo.grid.DataMap(timeData, 'value', 'name');
    // 목록 조회
    $scope.getTimeSlotList();

    if(orgnFg ==="HQ") {
      $("button").show();
    } else if(orgnFg === "STORE" && hqOfficeCd === "00000"){
      $("button").show();
    } else {
      $("button").hide();
    }
  };

  $scope.$on("timeSlotCtrl", function(event, data) {
    $scope.getTimeSlotList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 그리드 조회
  $scope.getTimeSlotList = function(data){

    var params = {};

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/base/store/timeSlot/timeSlot/getTimeSlot.sb", params, function(){}, false);

  };

  // 그리드 행 추가
  $scope.addRow = function() {
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.timeSlotNm = "";
    params.startTime = "0";
    params.endTime = "0";
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

  // 시간체크
  $scope.timeChk = function (){

    var time = 0;
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].nmcodeNm !== "") {
        if($scope.flex.collectionView.items[i].startTime == $scope.flex.collectionView.items[i].endTime){
          console.log($scope.flex.collectionView.items[i].startTime + ' ' + $scope.flex.collectionView.items[i].endTime);
          $scope._popMsg(messages["timeSlot.timeChk"]);
          return false;
        } else if($scope.flex.collectionView.items[i].startTime < $scope.flex.collectionView.items[i].endTime){
          time = time + ($scope.flex.collectionView.items[i].endTime - $scope.flex.collectionView.items[i].startTime);
        } else if($scope.flex.collectionView.items[i].startTime > $scope.flex.collectionView.items[i].endTime) {
          time = time + (24 - ($scope.flex.collectionView.items[i].startTime - $scope.flex.collectionView.items[i].endTime));
        }
      } else {
        $scope._popMsg(messages["timeSlot.nmcodeNm.none"]);
        return false;
      }
    }

    if(time == 24){
      $scope.save();
    } else {
      console.log(time);
      console.log("!24시간");
      $scope._popMsg(messages["timeSlot.timeChk"]);
      return false;
    }

  }

  // 저장
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
    $scope._save("/base/store/timeSlot/timeSlot/saveTimeSlot.sb", params, function(){
      $scope.getTimeSlotList();
    });
  }

}]);


