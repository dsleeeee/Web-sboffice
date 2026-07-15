/****************************************************************
 *
 * 파일명 : acStoreOption.js
 * 설  명 : 벤슨 > 회계관리 > 회계관리 > 매장별항목관리 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.13     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 항목2 (방식구분)
var option02ComboData = [
    {"name":"전체","value":""},
    {"name":"방식1","value":"1"},
    {"name":"방식2","value":"2"},
    {"name":"방식3","value":"3"}
];

// 항목2 그리드 편집용 dataMap (검색조건과 달리 "전체" 항목 없음)
var option02DataMapData = [
    {"name":"방식1","value":"1"},
    {"name":"방식2","value":"2"},
    {"name":"방식3","value":"3"}
];

// 항목3 (체크여부)
var option03ComboData = [
    {"name":"전체","value":""},
    {"name":"체크","value":true},
    {"name":"미체크","value":false}
];

/**
 * 매장별항목관리 그리드 생성
 */
app.controller('acStoreOptionCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('acStoreOptionCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("option02Combo", option02ComboData); // 항목2
    $scope._setComboData("option03Combo", option03ComboData); // 항목3

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.option02DataMap = new wijmo.grid.DataMap(option02DataMapData, 'value', 'name'); // 항목2

        // 수정 시 체크박스 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                $scope.checked(item);
            }
            s.collectionView.commitEdit();
        });
    };

    // 수정 시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    };

    // <-- 검색 호출 -->
    $scope.$on("acStoreOptionCtrl", function (event, data) {
        $scope.searchAcStoreOption();
        event.preventDefault();
    });

    $scope.searchAcStoreOption = function () {
        var params = {};
        params.storeCd = $("#acStoreOptionStoreCd").val(); // 매장선택(멀티)
        params.option01 = $scope.option01;
        params.option02 = $scope.option02;
        params.option03 = $scope.option03;
        params.option04 = $scope.option04;

        $scope._inquiryMain("/accounting/accountingMain/acStoreOption/getAcStoreOptionList.sb", params, function () {});
    };
    // <-- //검색 호출 -->

    // 저장 (변경된 row만 전송)
    $scope.saveAcStoreOption = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }

        // 항목4 범위 체크 (0~100)
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            var item = $scope.flex.collectionView.itemsEdited[i];
            if (item.option04 !== null && item.option04 !== "" && item.option04 !== undefined) {
                var option04Val = Number(item.option04);
                if (isNaN(option04Val) || option04Val < 0 || option04Val > 100) {
                    $scope._popMsg("[" + item.storeCd + "] " + item.storeNm + " " + messages["acStoreOption.option04RangeChk"]);
                    return false;
                }
            }
        }

        $scope._popConfirm(messages["cmm.choo.save"], function () {
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            $scope._save("/accounting/accountingMain/acStoreOption/saveAcStoreOption.sb", params, function () {
                $scope.searchAcStoreOption();
            });
        });
    };

    // 삭제 (체크된 row만)
    $scope.delAcStoreOption = function () {
        var params = new Array();
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            if ($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if (params.length <= 0) {
            $scope._popMsg(messages["cmm.not.select"]); // 선택된 데이터가 없습니다.
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.delete"], function () {
            $scope._save("/accounting/accountingMain/acStoreOption/delAcStoreOption.sb", params, function () {
                $scope.searchAcStoreOption();
            });
        });
    };

    // 엑셀다운로드 (클라이언트 사이드 export)
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.binding != 'gChk'; // 선택 컬럼 제외
                }
            }, messages["acStoreOption.title"] + "_" + getCurDateTime() + ".xlsx", function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);
