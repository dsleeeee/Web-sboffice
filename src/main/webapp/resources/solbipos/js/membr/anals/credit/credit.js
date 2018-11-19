/****************************************************************
 *
 * 파일명 : credit.js
 * 설  명 : 외상입금/후불 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.20     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  후불회원 그리드 생성
 */
app.controller('creditCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('creditCtrl', $scope, $http, true));

  $scope.orgnFg = gvOrgnFg;

  if($scope.orgnFg === 'S') {
    $scope.storeCds = gvStoreCd;
  }

  // comboBox 초기화
  $scope._setComboData("srchArrayCombo", arrayData);
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.creditInFgDataMap = new wijmo.grid.DataMap(creditInFgData, 'value', 'name');
    $scope.creditPayFgDataMap = new wijmo.grid.DataMap(creditPayFgData, 'value', 'name');
  };

  $scope.$on("creditCtrl", function(event, data) {
    $scope.searchCredit();
    event.preventDefault();
  });

  // 후불회원 그리드 조회
  $scope.searchCredit = function(){
    // 파라미터
    var params = {};
    params.storeCds = $("#storeCd").val();
    params.array = srchArrayCombo.selectedValue;

    if($scope.orgnFg === 'H' && params.storeCds  === '') {
      alert("매장을 선택해주세요.");
      return false;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain(baseUrl + "credit/getCreditMemberList.sb", params, function() {}, false);
  };

  // 외상입금 팝업
  $scope.deposit = function(){
    var popup = $scope.depositLayer;
    popup.show(true, function (s) {
    });
  };

  // 매장찾기
  $scope.searchStore = function(){
    var storeTxt = "";
    var checked = "";
    c_store.init(checked, function(arr){

      $("#storeCdText").val("");
      $("#storeCd").val("");

      if(arr[0].cd === "") {
        $("#storeCdText").val("전체");
        arr.splice(0, 1);
      }

      if(arr.length > 1) {
        var a = arr.length -1;
        $("#storeCdText").val(arr[0].nm + "외 " + a.toString() + " 선택");
      }
      else if(arr.length == 1){
        $("#storeCdText").val(arr[0].nm);
      }
      for(var i=0; i<arr.length; i++) {
        if(i == arr.length - 1) {
          storeTxt += arr[i].cd.toString();
          $("#storeCd").val(storeTxt);
        }
        else {
          storeTxt += arr[i].cd.toString() + ",";
          $("#storeCd").val(storeTxt);
        }
      }
    });
  };
}]);
