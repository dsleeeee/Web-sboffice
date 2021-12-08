/****************************************************************
 *
 * 파일명 : storeProdSaleReport.js
 * 설  명 : 지사-지역관리 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.01     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 지역관리 등록에 추가버튼, '지사코드' 미선택시 막을려고 ("V":선택, "N":미선택)
var addSelected = "N";

/**
 *  지사관리 그리드 생성
 */
app.controller('branchAreaCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('branchAreaCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 지사코드
                if (col.binding === "branchCd") {
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

                // 지사코드 클릭시 상세정보 조회
                if ( col.binding === "branchCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params = {};
                    params.branchCd = selectedRow.branchCd;
                    params.branchNm = selectedRow.branchNm;

                    var storeScope = agrid.getScope('branchAreaDetailCtrl');
                    storeScope._broadcast('branchAreaDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("branchAreaCtrl", function(event, data) {
        $scope.searchBranchArea();
        event.preventDefault();
    });

    $scope.searchBranchArea = function(){
        var params = {};

        $scope._inquiryMain("/sale/status/storeProdSaleReport/branchArea/getBranchAreaList.sb", params, function() {
            addSelected = "N";
            $scope.$apply(function() {
                var storeScope = agrid.getScope('branchAreaDetailCtrl');
                storeScope._gridDataInit();
                storeScope._broadcast('branchAreaDetailCtrl', null);
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // <-- 그리드 행 추가 -->
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.status = "I";
        params.gChk = true;
        params.branchCd = "자동채번";
        params.branchNm = "";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };
    // <-- //그리드 행 추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];

                if(item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 저장
            $scope.save();
        });
    };
    // <-- //그리드 행 삭제 -->

    // <-- 그리드 저장 -->
    $scope.save = function() {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].branchNm === "") {
                $scope._popMsg(messages["branchArea.branchNmBlank"]); // 지사명을 입력해주세요.
                return false;
            }
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sale/status/storeProdSaleReport/branchArea/getBranchAreaSave.sb", params, function(){
            $scope.searchBranchArea();
        });
    };
    // <-- //그리드 저장 -->
}]);


/**
 *  지사-지역관리 그리드 생성
 */
app.controller('branchAreaDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('branchAreaDetailCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("branchAreaDetailCtrl", function(event, data) {
        $scope.setSelectedBranchArea(data);

        if(!$.isEmptyObject($scope.selectedBranchArea) ) {
            addSelected = "Y";
        }
        if(addSelected === "Y") {
            $("#lblBranchCd").text(" ( [ " + $scope.selectedBranchArea.branchCd + " ] ");
            $("#lblBranchNm").text($scope.selectedBranchArea.branchNm + " )");
            $scope.searchBranchAreaDetail();

        } else if(addSelected === "N") {
            $("#lblBranchCd").text("");
            $("#lblBranchNm").text("");
        }
        event.preventDefault();
    });

    $scope.searchBranchAreaDetail = function(){
        var params = {};
        params.branchCd = $scope.selectedBranchArea.branchCd;

        $scope._inquiryMain("/sale/status/storeProdSaleReport/branchArea/getBranchAreaDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedBranchArea;
    $scope.setSelectedBranchArea = function(store) {
        $scope.selectedBranchArea = store;
    };
    $scope.getSelectedBranchArea = function(){
        return $scope.selectedBranchArea;
    };

    // <-- 그리드 행 추가 -->
    $scope.addRow = function() {
        if(addSelected === "N") {
            $scope._popMsg(messages["branchArea.branchCdAlert"]); // 지사코드을 선택해주세요.
            return false;
        }

        // 파라미터 설정
        var params = {};
        params.status = "I";
        params.gChk = true;
        params.areaCd = "자동채번";
        params.areaNm = "";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };
    // <-- //그리드 행 추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        if(addSelected === "N") {
            $scope._popMsg(messages["branchArea.branchCdAlert"]); // 지사코드을 선택해주세요.
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];

                if(item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 저장
            $scope.save();
        });
    };
    // <-- //그리드 행 삭제 -->

    // <-- 그리드 저장 -->
    $scope.save = function() {
        if(addSelected == "N") {
            $scope._popMsg(messages["branchArea.branchCdAlert"]); // 지사코드을 선택해주세요.
            return;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].areaNm === "") {
                $scope._popMsg(messages["branchArea.areaNmBlank"]); // 지역명을 입력해주세요.
                return false;
            }
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].branchCd = $scope.selectedBranchArea.branchCd;
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].branchCd = $scope.selectedBranchArea.branchCd;
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].branchCd = $scope.selectedBranchArea.branchCd;
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sale/status/storeProdSaleReport/branchArea/getBranchAreaDetailSave.sb", params, function(){
            $scope.searchBranchAreaDetail();
        });
    };
    // <-- //그리드 저장 -->
}]);