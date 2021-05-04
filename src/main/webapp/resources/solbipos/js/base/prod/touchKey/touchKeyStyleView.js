/****************************************************************
 *
 * 파일명 : touchKeyStyleView.js
 * 설  명 : 터치키 스타일 미리보기 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.30     권지현      1.0
 *
 * **************************************************************/
/**
 * 팝업 그리드 생성
 */

// 버튼사용 필터 DropBoxDataMap
var styleData = [
  {"name":"Key Color 1","value":"01"},
  {"name":"Key Color 2","value":"02"},
  {"name":"Key Color 3","value":"03"},
  {"name":"Key Color 4","value":"04"},
  {"name":"Key Color 5","value":"05"},
  {"name":"Key Color 6","value":"06"},
  {"name":"Key Color 7","value":"07"},
  {"name":"Key Color 8","value":"08"},
  {"name":"Key Color 9","value":"09"},
  {"name":"Key Color 10","value":"10"}
];

app.controller('touchKeyStyleCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('touchKeyStyleCtrl', $scope, $http, false));

  // 버튼사용여부 필터 콤보
  $scope._setComboData("styleCombo", styleData);

  $scope.$on("touchKeyStyleCtrl", function(event, data) {
    $scope.viewStyle = data;
    $scope.setViewStyle();
  });

  $scope.setViewStyle = function(s) {
    if($scope.viewStyle == undefined){
      $scope.viewStyle = "01";
    }
    var styleCd = $scope.viewStyle;
    var path = "/resource/solbipos/css/img/touchKey/";
    var ext = ".png";
    var styleBtnFile = styleCd + ext;
    var viewStyleLayer = document.getElementById("viewStyleLayer");
    viewStyleLayer.style.backgroundImage = "url('" + path + "/touchKey_style" + styleBtnFile + "')";
  };
}]);


