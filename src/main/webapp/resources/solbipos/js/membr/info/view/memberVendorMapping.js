/****************************************************************
 *
 * 파일명 : memberVendorMapping.js
 * 설  명 : 회원 거래처 매핑 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.12.23     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('memberVendorMappingCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberVendorMappingCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.emailRecvDataMap = new wijmo.grid.DataMap(recvDataMap, 'value', 'name');
        $scope.smsRecvDataMap = new wijmo.grid.DataMap(recvDataMap, 'value', 'name');
        $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "membrNo" || col.binding === "membrNm") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 회원명,회원번호 클릭시 상세정보 조회
                if ( col.binding === "membrNo" || col.binding === "membrNm") {
                    $scope.setSelectedMember(s.rows[ht.row].dataItem);
                    $scope.memberRegistLayer.show(true);
                    event.preventDefault();
                }
            }
        });
    };

    $scope.selectedMember;
    $scope.setSelectedMember = function(data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function(){
        return $scope.selectedMember;
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("memberVendorMappingCtrl", function(event, data) {
        $scope.memberVendorMappingLayer.show(true);
        $scope.searchMemberVendorMappingList();
        event.preventDefault();
    });

    $scope.searchMemberVendorMappingList = function(){
        var params = {};
        params.membrNo = $("#vendorMemberNo").val();
        params.membrNm = $("#vendorMemberNm").val();
        params.listScale = 30;

        $scope._inquirySub("/membr/info/view/view/getMemberVendorMappingList.sb", params, function() {}, false);
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 회원 등록 및 수정 팝업 핸들러 추가
        // $scope.memberRegistLayer.shown.addHandler(function (s) {
        //     setTimeout(function() {
        //         $scope._broadcast('memberRegistCtrl', $scope.getSelectedMember());
        //     }, 50)
        // });
    });

}]);