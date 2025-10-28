/****************************************************************
 *
 * 파일명 : branchKmu.js
 * 설  명 : 그룹관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.27     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var useYnFg = [
    {"name":"전체","value":""},
    {"name":"사용","value":"Y"},
    {"name":"사용안함","value":"N"}
];

// 사용여부 DropBoxDataMap
var useYnFgDataMap = new wijmo.grid.DataMap([
    {id: "", name: "전체"},
    {id: "Y", name: "사용"},
    {id: "N", name: "사용안함"}
], 'id', 'name');

app.controller('branchKmuCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('branchCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("srchUseYnFg", useYnFg);


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if( col.binding == "branchCd" || col.binding == "branchNm") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            var row = ht.row;
            if( ht.cellType == wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if( col.binding == "branchCd" || col.binding == "branchNm") {

                    $scope.wjBranchKmuDtlLayer.show(true);
                    var scope = agrid.getScope("branchKmuDtlCtrl");
                    scope._broadcast('branchKmuDtlCtrl', selectedRow);
                    event.preventDefault();
                }
            }
        });
    };

    $scope.$on("branchKmuCtrl", function(event, data) {
        $scope.searchBranchKmuList();
        event.preventDefault();
    });

    // 그리드 조회
    $scope.searchBranchKmuList = function(){
        var param = {};
        $scope._inquiryMain("/store/hq/branchKmu/branchKmu/getBranchKmuList.sb", param, function(result){});
    };

    $scope.newAdd = function (){
        $scope.wjBranchKmuDtlLayer.show(true);
        var scope = agrid.getScope("branchKmuDtlCtrl");
        scope._broadcast('branchKmuDtlCtrl');
        event.preventDefault();
    };
}]);
