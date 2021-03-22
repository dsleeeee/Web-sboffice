/****************************************************************
 *
 * 파일명 : searchEnvConfg.js
 * 설  명 : 환경설정 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.02.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  환경설정 조회 그리드 생성
 */
app.controller('searchEnvConfgCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchEnvConfgCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 설정코드
                if (col.binding === "envstCd") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 설정코드 클릭시 상세정보 조회
                if ( col.binding === "envstCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    $scope.setSelectedEnvConfg(selectedRow);
                    $scope.wjSearchEnvConfgLayer.hide();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("searchEnvConfgCtrl", function(event, data) {
        $("#lblTitle").text("");

        if(data.gubun == "HQ") {
            $("#lblTitle").text("본사");

        } else if(data.gubun == "STORE") {
            $("#lblTitle").text("매장");

        } else if(data.gubun == "STORE_POS") {
            $("#lblTitle").text("매장포스");
        }
        $scope.searchEnvConfg(data);
        event.preventDefault();
    });

    $scope.searchEnvConfg = function(data){
        var params = {};
        params.gubun = data.gubun;

        $scope._inquiryMain("/store/manage/envConfgBatchChange/searchEnvConfg/getSearchEnvConfgList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedEnvConfg;
    $scope.setSelectedEnvConfg = function(store) {
        $scope.selectedEnvConfg = store;
    };
    $scope.getSelectedEnvConfg = function(){
        return $scope.selectedEnvConfg;
    };

    // 팝업 닫기
    $scope.close = function() {
        $scope.setSelectedEnvConfg(null);
        $scope.wjSearchEnvConfgLayer.hide();
        event.preventDefault();
    };

}]);