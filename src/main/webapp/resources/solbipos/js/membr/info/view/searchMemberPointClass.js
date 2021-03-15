/****************************************************************
 *
 * 파일명 : searchMemberPointClass.js
 * 설  명 : 회원 등급 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.03.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원 등급 조회 그리드 생성
 */
app.controller('searchMemberClassCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchMemberClassCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 회원번호
                if (col.binding === "membrNo") {
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

                // 재료코드 클릭시 상세정보 조회
                if ( col.binding === "membrNo") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    $scope.setSelectedMemberClass(selectedRow);

                    // 검색조건 지움
                    $scope.membrNo = "";
                    $scope.membrNm = "";
                    $scope.telNo = "";

                    $scope.wjSearchMemberClassLayer.hide();
                    event.preventDefault();
                }
            }
        });

        // 검색
        $scope.searchMemberPointClass();
    };

    // <-- 검색 호출 -->
    $scope.$on("searchMemberClassCtrl", function(event, data) {
        $scope.searchMemberPointClass();
        event.preventDefault();
    });

    $scope.searchMemberPointClass = function(){
        var params = {};
        params.membrNo = $scope.membrNo;
        params.membrNm = $scope.membrNm;
        params.telNo = $scope.telNo;

        $scope._inquirySub("/membr/info/view/base/getSearchMemberClassList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 회원
    $scope.selectedMemberClass;
    $scope.setSelectedMemberClass = function(member) {
        $scope.selectedMemberClass = member;
    };
    $scope.getSelectedMemberClass = function(){
        return $scope.selectedMemberClass;
    };

    // 팝업 닫기
    $scope.close = function() {
        // 검색조건 지움
        $scope.membrNo = "";
        $scope.membrNm = "";
        $scope.telNo = "";
        $scope.setSelectedMemberClass(null);

        $scope.wjSearchMemberClassLayer.hide();
        event.preventDefault();
    };

}]);