/****************************************************************
 *
 * 파일명 : posFuncAuthDtl.js
 * 설  명 : 포스기능키 인증관리 인증허용등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.10.08    이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// angular 그리드 생성
app.controller('posFuncAuthDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posFuncAuthDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        s.formatItem.addHandler(function(s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                if( col.binding === "buttons"){
                    e.cell.innerHTML = "대상선택";
                    e.cell.dataItem = item;
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        s.addEventListener(s.hostElement, 'click', function(e) {
            var ht = s.hitTest(e);
            var row = ht.row;
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                if( col.binding === "buttons") {
                    selectedFnkey = s.rows[ht.row].dataItem;
                    openSetAuthLayer();
                }
            }
        });
    };

    $scope.$on("posFuncAuthDtlCtrl", function (event, data) {

        $scope.posFuncAuthDtlLayer.show(true);

        // 선택한 fnkey 이름 셋팅 및 hidden 에 값 가지고 있기
        $("#authDtlTitle").text(data.nmcodeNm);
        $("#hdStoreCd").val(data.storeCd);

        // 포스기능키목록 그리드 조회
        $scope.getPosFuncAuthDetail(data);

        event.preventDefault();

    });

    // 포스기능인증 목록 상세 조회
    $scope.getPosFuncAuthDetail = function(data) {

        var params = {};

        params.storeCd = data.storeCd;
        params.fnkeyFg = data.nmcodeCd;

        $scope._inquirySub("/base/store/posfunc/auth/getPosConfAuthDetail.sb", params, function() {}, false);

    }

    // 저장
    $scope.save = function() {

        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            $scope.flex.collectionView.items[i].status = 'U';
            $scope.flex.collectionView.items[i].storeCd = $("#hdStoreCd").val();
            params.push($scope.flex.collectionView.items[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/store/posfunc/auth/savePosAuthConf.sb', params, function(){
            $scope.posFuncAuthDtlLayer.hide(true);
        });
    }

}]);