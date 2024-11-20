/****************************************************************
 *
 * 파일명 : searchCommCode.js
 * 설  명 : 공통코드 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.11.14     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용대상 DataMap
var useTargetFgDataMap = new wijmo.grid.DataMap([
  {id: "H", name: "본사세팅"},
  {id: "S", name: "매장세팅"},
  {id: "HS", name: "본사->매장세팅"}
  ], 'id', 'name');

/**
 *  공통코드 조회 팝업 그리드 생성
 */
app.controller('searchCommCodeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchCommCodeCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 내 콤보박스 설정
        $scope.useTargetFgDataMap = useTargetFgDataMap;

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 공통코드
                if (col.binding === "nmcodeCd" || col.binding === "nmcodeNm") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 공통코드 클릭시
                if (col.binding === "nmcodeCd" || col.binding === "nmcodeNm") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    $scope.setSelectedCommCode(selectedRow);
                    $scope.wjSearchCommCodeLayer.hide();
                }
            }
        });
    };

    $scope.$on("searchCommCodeCtrl", function(event, data) {
       // 조회
       $scope.searchCommCode();
       event.preventDefault();
    });

    // 조회
    $scope.searchCommCode = function(){
       var params = {};
       $scope._inquiryMain("/store/manage/envConfgBatchChange/searchCommCode/getSearchCommCodeList.sb", params);
    };

    // 선택 공통코드
    $scope.selectedCommCode;
    $scope.setSelectedCommCode = function(data) {
        $scope.selectedCommCode = data;
    };
    $scope.getSelectedCommCode = function(){
        return $scope.selectedCommCode;
    };

    // 팝업 닫기
    $scope.close = function() {
        $scope.setSelectedCommCode(null);
        $scope.wjSearchCommCodeLayer.hide();
        event.preventDefault();
    };
}]);