/****************************************************************
 *
 * 파일명 : popUpNoTouchKey.js
 * 설  명 : 터치키미적용상품 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.11     권지현      1.0
 *
 * **************************************************************/
var useYnFg = [
  {"name":"사용","value":"Y"},
  {"name":"미사용","value":"N"}
];
var saleProdYnFg = [
  {"name":"판매상품","value":"Y"},
  {"name":"미판매상품","value":"N"}
];
var prodInfoFg = [
  {"name":"등록","value":"Y"},
  {"name":"미등록","value":"N"}
];

// 팝업 그리드 생성
app.controller('popUpNoTouchKeyCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('popUpNoTouchKeyCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');
    $scope.saleProdYnDataMap = new wijmo.grid.DataMap(saleProdYnFg, 'value', 'name');
    $scope.prodInfoDataMap = new wijmo.grid.DataMap(prodInfoFg, 'value', 'name');

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "prodInfo") {
          if(item.prodInfo === 'N') {
            wijmo.setCss(e.cell, {
              color:'#ff0000',
              fontWeight:'bold'
            });
          } else if(item.prodInfo === 'Y'){
            wijmo.setCss(e.cell, {
              color:'#666',
              fontWeight:'none'
            });
          }
        } else if (col.binding === "saleProdYn") {
          if(item.saleProdYn === 'N') {
            wijmo.setCss(e.cell, {
              color:'#ff0000',
              fontWeight:'bold'
            });
          } else if(item.saleProdYn === 'Y'){
            wijmo.setCss(e.cell, {
              color:'#666',
              fontWeight:'none'
            });
          }
        } else if (col.binding === "useYn") {
          if(item.useYn === 'N') {
            wijmo.setCss(e.cell, {
              color:'#ff0000',
              fontWeight:'bold'
            });
          } else if(item.useYn === 'Y'){
            wijmo.setCss(e.cell, {
              color:'#666',
              fontWeight:'none'
            });
          }
        }
      }
    });
  };

  // 팝업 그리드 조회
  $scope.$on("popUpNoTouchKeyCtrl", function(event, data) {
   // 파라미터
    var params = {};
    params.tukeyGrpCd = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/touchKey/touchKey/noTouchKey.sb", params, function() {});
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

}]);