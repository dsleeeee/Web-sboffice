/****************************************************************
 *
 * 파일명 : badwordManage.js
 * 설  명 : 금칙어관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.01     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 카테고리
var categoryFg = [
    {"name":"전체","value":""},
    {"name":"대출/금융","value":"loan"},
    {"name":"불법도박","value":"gambling"},
    {"name":"성인/음란","value":"adult"},
    {"name":"스미싱/피싱","value":"phishing"},
    {"name":"불법의약품/마약","value":"illegal_drug"},
    {"name":"불법사기","value":"scam"},
    {"name":"기타","value":"other"}
];

// 매칭방식
var matchTypeFg = [
    {"name":"전체","value":""},
    {"name":"일치","value":"exact"},
    {"name":"포함","value":"contains"},
    {"name":"정규식","value":"regex"}
];

// 처리방식
var severityFg = [
    {"name":"전체","value":""},
    {"name":"차단","value":"block"},
    {"name":"보류","value":"hold"},
    {"name":"주의","value":"warn"}
];

// 사용여부(활성여부)
var isActiveFg = [
    {"name":"전체","value":""},
    {"name":"사용","value":"1"},
    {"name":"미사용","value":"0"}
];

app.controller('badwordManageCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('badwordManageCtrl', $scope, $http, $timeout, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("category", categoryFg);   // 카테고리
    $scope._setComboData("matchType", matchTypeFg); // 매칭타입
    $scope._setComboData("severity", severityFg);   // 처리방식
    $scope._setComboData("isActive", isActiveFg);   // 사용여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정 ("전체" 항목 제외하고 slice(1))
        $scope.categoryDataMap  = new wijmo.grid.DataMap(categoryFg.slice(1),  'value', 'name'); // 카테고리
        $scope.matchTypeDataMap = new wijmo.grid.DataMap(matchTypeFg.slice(1), 'value', 'name'); // 매칭타입
        $scope.severityDataMap  = new wijmo.grid.DataMap(severityFg.slice(1),  'value', 'name'); // 처리방식
        $scope.isActiveDataMap  = new wijmo.grid.DataMap(isActiveFg.slice(1),  'value', 'name'); // 사용여부

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 변경시 체크박스 체크
                if (col.binding === "keyword" || col.binding === "keywordNormalized" || col.binding === "category" || col.binding === "matchType"
                    || col.binding === "severity" || col.binding === "source" || col.binding === "isActive") {

                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    };

    $scope.$on("badwordManageCtrl", function (event, data) {
        // 조회
        $scope.searchBadwordManage();
        event.preventDefault();
    });
    
    // 조회
    $scope.searchBadwordManage = function () {
        var params = {};
        params.keyword = $("#keyword").val();
        params.category = $scope.categoryCombo.selectedValue;
        params.matchType = $scope.matchTypeCombo.selectedValue;
        params.severity = $scope.severityCombo.selectedValue;
        params.isActive = $scope.isActiveCombo.selectedValue;
        //params.listScale = $scope.listScale;

        $scope._inquiryMain("/adi/sms/smsBadword/badwordManage/getBadwordManageList.sb", params, function () {}, false);
    };

    // 추가
    $scope.addRow = function () {
        var params = {};
        params.gChk               = true;
        params.id                 = "";
        params.keyword            = "";
        params.keywordNormalized  = "";
        params.category           = "loan";
        params.matchType          = "contains";
        params.severity           = "block";
        params.source             = "";
        params.isActive           = "1";

        $scope._addRow(params);
    };
    
    // 저장
    $scope.save = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 필수값 체크
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].keyword === "") {
                // 금칙어를 입력하세요.
                $scope._popMsg(messages["smsBadwordTab.keywordBlank"]);
                return false;
            }
        }

        // 저장할 데이터가 있는지 확인
        var paramsChk = [];
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            paramsChk.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            paramsChk.push($scope.flex.collectionView.itemsAdded[i]);
        }

        if (paramsChk.length <= 0) {
            $scope._popMsg(messages["cmm.not.select"]);
            return;
        }
        
        $scope._popConfirm(messages["cmm.choo.save"], function() {

            var params = [];
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                $scope.flex.collectionView.itemsAdded[i].status = "I";
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }

            $scope._postJSONSave.withPopUp("/adi/sms/smsBadword/badwordManage/getBadwordManageSave.sb", params, function () {
                // 재조회
                $scope.searchBadwordManage();
            });
        });
    };

    // 삭제
    $scope.del = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }
        
        // 삭제할 데이터가 있는지 확인 (체크된 항목)
        var paramsChk = [];
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                paramsChk.push($scope.flex.collectionView.items[i]);
            }
        }

        if (paramsChk.length <= 0) {
            $scope._popMsg(messages["cmm.not.select"]);
            return;
        }

        $scope._popConfirm(messages["cmm.choo.delete"], function () {

            // 그리드에서 먼저 제거
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // itemsRemoved 중 id 있는 것만 (신규 추가 후 삭제 시 id 없으면 DB 호출 불필요)
            var params = [];
            for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
                if ($scope.flex.collectionView.itemsRemoved[i].id) {
                    $scope.flex.collectionView.itemsRemoved[i].status = "D";
                    params.push($scope.flex.collectionView.itemsRemoved[i]);
                }
            }

            if (params.length <= 0) {
                // 신규 추가행만 삭제한 경우 서버 호출 없이 종료
                return;
            }

            $scope._postJSONSave.withPopUp("/adi/sms/smsBadword/badwordManage/getBadwordManageSave.sb", params, function () {
                // 재조회
                $scope.searchBadwordManage();
            });
        });
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    //return column.visible;
                    return column.binding != 'gChk';
                }
            }, 'SMS금칙어_금칙어관리_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };
}]);
