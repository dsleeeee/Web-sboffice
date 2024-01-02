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
app.controller('popUpNoTouchKeyCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
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

  // 엑셀다운로드 -->
  $scope.excelDownload = function(){
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function()	{
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
          {
            includeColumnHeaders: 	true,
            includeCellStyles	: 	false,
            includeColumns      :	function (column) {
              // return column.visible;
              return column.binding != 'gChk';
            }
          },messages["touchKey.touchKey"]  + '_' + messages["touchKey.noTouchKey"]+ '_'+ getCurDateTime() +'.xlsx',
          function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
            }, 10);
          }
      );
    }, 10);
  };

}]);