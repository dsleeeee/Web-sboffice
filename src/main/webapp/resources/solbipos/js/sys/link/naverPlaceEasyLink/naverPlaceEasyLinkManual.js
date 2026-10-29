/****************************************************************
 *
 * 파일명 : naverPlaceEasyLinkManual.js
 * 설  명 : 네이버플레이스 간편연동 수기등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.10.28     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var regYnComboData = [
    {"name":"미등록","value":"N"},
    {"name":"기등록","value":"Y"}
];

/**
 *  간편연동 수기등록 팝업
 */
app.controller('naverPlaceEasyLinkManualCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('naverPlaceEasyLinkManualCtrl', $scope, $http, false));

    // 입력값
    $scope.manual = {};

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name'); // 상태
        $scope.regYnDataMap = new wijmo.grid.DataMap(regYnComboData, 'value', 'name'); // 기등록여부

        // 기등록 매장은 빨간색으로 표시(등록 불가)
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var item = s.rows[e.row].dataItem;
                if (item && item["regYn"] === "Y") {
                    wijmo.addClass(e.cell, 'wij_gridText-red');
                }
            }
        });

        // 1건만 선택 가능 : 체크하면 나머지 체크 해제
        s.cellEditEnded.addHandler(function (s, e) {
            if (s.columns[e.col].binding !== 'gChk') {
                return;
            }
            var item = s.rows[e.row].dataItem;
            if (!item || item.gChk !== true) {
                return;
            }
            s.beginUpdate();
            for (var i = 0; i < s.rows.length; i++) {
                if (i !== e.row) {
                    s.setCellData(i, e.col, false);
                }
            }
            s.endUpdate();
        });
    };

    // itemFormatter : 공통 포맷터 적용 후, 1건 선택이므로 헤더의 전체선택 체크박스는 제거
    $scope.manualItemFormatter = function (panel, r, c, cell) {
        $scope._itemFormatter(panel, r, c, cell);
        if (panel.cellType === wijmo.grid.CellType.ColumnHeader && panel.columns[c].binding === 'gChk') {
            cell.textContent = panel.columns[c].header;
        }
    };

    // 팝업 오픈 시 초기화
    $scope.$on("naverPlaceEasyLinkManualCtrl", function (event, data) {

        $scope.manual = {};
        $scope._gridDataInit();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 매장 검색
    $scope.search = function () {

        // 사업자번호 - 숫자 이외 문자(하이픈 등) 제거
        var bizNo = ($scope.manual.bizNo || '').replace(/[^0-9]/g, '');
        var addr = ($scope.manual.addr || '').trim();
        var storeCd = ($scope.manual.storeCd || '').trim();
        var storeNm = ($scope.manual.storeNm || '').trim();

        if (bizNo === '' && addr === '' && storeCd === '' && storeNm === '') {
            // 검색조건(사업자번호, 주소, 매장코드, 매장명)을 1개 이상 입력하세요.
            $scope._popMsg(messages["naverPlaceEasyLink.manualReg.chk.msg1"]);
            return false;
        }

        var params = {};
        params.bizNo = bizNo;
        params.addr = addr;
        params.storeCd = storeCd;
        params.storeNm = storeNm;

        $scope._inquirySub("/sys/link/naverPlaceEasyLink/getNaverPlaceManualStoreList.sb", params, function () {

            // 기등록 매장은 체크할 수 없도록 행 읽기전용 처리
            for (var i = 0; i < $scope.flex.rows.length; i++) {
                var item = $scope.flex.rows[i].dataItem;
                if (item && item.regYn === "Y") {
                    $scope.flex.rows[i].isReadOnly = true;
                }
            }
        }, true);
    };

    // 저장
    $scope.save = function () {

        var placeId = ($scope.manual.placeId || '').trim();
        if (placeId === '') {
            // 네이버 플레이스ID를 입력하세요.
            $scope._popMsg(messages["naverPlaceEasyLink.manualReg.chk.msg2"]);
            return false;
        }

        // 체크된 매장
        var checkedStores = [];
        var items = $scope.flex.collectionView ? $scope.flex.collectionView.items : [];
        for (var i = 0; i < items.length; i++) {
            if (items[i].gChk) {
                checkedStores.push(items[i]);
            }
        }

        if (checkedStores.length <= 0) {
            // 등록할 매장을 선택하세요.
            $scope._popMsg(messages["naverPlaceEasyLink.manualReg.chk.msg3"]);
            return false;
        }

        if (checkedStores.length > 1) {
            // 등록할 매장을 1개만 선택하세요.
            $scope._popMsg(messages["naverPlaceEasyLink.manualReg.chk.msg4"]);
            return false;
        }

        var store = checkedStores[0];

        if (store.regYn === "Y") {
            // 기등록 매장은 등록할 수 없습니다.
            $scope._popMsg(messages["naverPlaceEasyLink.manualReg.chk.msg5"]);
            return false;
        }

        $scope._popConfirm("[" + store.storeCd + "] " + store.storeNm + " 매장에 네이버 플레이스ID(" + placeId + ")를 등록하시겠습니까?", function () {

            var params = {};
            params.placeId = placeId;
            params.businessName = ($scope.manual.businessName || '').trim();
            params.hqOfficeCd = store.hqOfficeCd;
            params.storeCd = store.storeCd;

            $scope._postJSONSave.withPopUp("/sys/link/naverPlaceEasyLink/saveNaverPlaceManual.sb", params, function () {

                // 사용자현황 재조회
                var scope = agrid.getScope('naverPlaceEasyLinkCtrl');
                scope.getNaverPlaceUserList();

                // 팝업 닫기
                $scope.wjNaverPlaceEasyLinkManualLayer.hide(true);
            });
        });
    };

}]);
