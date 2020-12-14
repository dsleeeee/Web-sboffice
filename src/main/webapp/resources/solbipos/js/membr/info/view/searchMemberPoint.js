/****************************************************************
 *
 * 파일명 : searchMemberPoint.js
 * 설  명 : 회원 포인트 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.12.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원 포인트 조회 그리드 생성
 */
app.controller('searchMemberPointCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchMemberPointCtrl', $scope, $http, true));

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
                    $scope.setSelectedMember(selectedRow);
                    $scope.wjSearchMemberPointLayer.hide();
                }
            }
        });

        // 검색
        $scope.searchMemberPointMove();
    };

    // <-- 검색 호출 -->
    $scope.$on("searchMemberPointCtrl", function(event, data) {
        $scope.searchMemberPointMove();
        event.preventDefault();
    });

    $scope.searchMemberPointMove = function(){
        var params = {};
        params.membrNo = $scope.membrNo;
        params.membrNm = $scope.membrNm;

        $scope._inquirySub("/membr/info/view/base/getSearchMemberPointList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 회원
    $scope.selectedMember;
    $scope.setSelectedMember = function(member) {
        $scope.selectedMember = member;
    };
    $scope.getSelectedMember = function(){
        return $scope.selectedMember;
    };

}]);