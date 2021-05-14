/****************************************************************
 *
 * 파일명 : prodBatchChange.js
 * 설  명 : 상품정보일괄변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.28     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 상품등록구분
var regFgData = [
    {"name":"본사","value":"H"},
    {"name":"매장","value":"S"}
];
// 상품등록구분
var regFgTotData = [
    {"name":"전체","value":""},
    {"name":"본사","value":"H"},
    {"name":"매장","value":"S"}
];
// 판매상품여부
var saleProdYnTotData = [
    {"name":"전체","value":""},
    {"name":"미사용","value":"N"},
    {"name":"사용","value":"Y"}
];
// 포인트적립여부
var pointSaveYnTotData = [
    {"name":"전체","value":""},
    {"name":"미사용","value":"N"},
    {"name":"사용","value":"Y"}
];
// 가격관리구분
var prcCtrlFgTotData = [
    {"name":"전체","value":""},
    {"name":"본사","value":"H"},
    {"name":"매장","value":"S"}
];

/**
 *  상품정보일괄변경 그리드 생성
 */
app.controller('prodBatchChangeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodBatchChangeCtrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData('listScaleBox', gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("saleProdYnCombo", saleProdYnTotData); // 판매상품여부
    $scope._setComboData("pointSaveYnCombo", pointSaveYnTotData); // 포인트적립여부
    $scope._setComboData("prcCtrlFgCombo", prcCtrlFgTotData); // 가격관리구분
    $scope._setComboData("regFgCombo", regFgTotData); // 상품등록구분

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("saleProdYnChgCombo", saleProdYnData); // 판매상품여부
    $scope._setComboData("pointSaveYnChgCombo", pointSaveYnData); // 포인트적립여부
    $scope._setComboData("prcCtrlFgChgCombo", prcCtrlFgData); // 가격관리구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.saleProdYnDataMap = new wijmo.grid.DataMap(saleProdYnData, 'value', 'name'); // 판매상품여부
        $scope.pointSaveYnDataMap = new wijmo.grid.DataMap(pointSaveYnData, 'value', 'name'); // 포인트적립여부
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
        $scope.regFgDataMap = new wijmo.grid.DataMap(regFgData, 'value', 'name'); // 상품등록구분

        // 프랜 매장일때만
        if(orgnFg == "STORE" && hqOfficeCd != "00000") {
            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];

                    // 체크박스
                    if (col.binding === "gChk" || col.binding === "saleProdYn" || col.binding === "pointSaveYn" || col.binding === "prcCtrlFg") {
                        var item = s.rows[e.row].dataItem;

                        // 값이 있으면 링크 효과
                        if (item[("regFg")] === 'H') {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            item[("gChk")] = false; // 전체 체크시 오류

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
            });
        }
    };

    // <-- 검색 호출 -->
    $scope.$on("prodBatchChangeCtrl", function(event, data) {
        $scope.searchProdBatchChange();
        event.preventDefault();
    });

    $scope.searchProdBatchChange = function(){
        var params = {};
        params.listScale = $scope.listScaleCombo.text;

        $scope._inquiryMain("/base/prod/prodBatchChange/prodBatchChange/getProdBatchChangeList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 일괄적용
    $scope.batchChange = function(chgGubun) {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                // 판매상품여부
                if(chgGubun == "saleProdYnChg") {
                    $scope.flex.collectionView.items[i].saleProdYn = $scope.saleProdYnChg;
                }
                // 포인트적립여부
                else if(chgGubun == "pointSaveYnChg") {
                    $scope.flex.collectionView.items[i].pointSaveYn = $scope.pointSaveYnChg;
                }
                // 가격관리구분
                else if(chgGubun == "prcCtrlFgChg") {
                    $scope.flex.collectionView.items[i].prcCtrlFg = $scope.prcCtrlFgChg;
                }
            }
        }
        $scope.flex.refresh();
    };

    // <-- 그리드 저장 -->
    $scope.save = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 프랜 매장일때만
            if(orgnFg == "STORE" && hqOfficeCd != "00000") {
                for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                    if($scope.flex.collectionView.items[i].gChk) {
                        // REG_FG 상품등록구분 S인 상품만 수정가능
                        if ($scope.flex.collectionView.items[i].regFg === "H") {
                            $scope._popMsg(messages["prodBatchChange.regFgHqBlank"]); // 상품등록구분이 '본사'인 상품은 수정할 수 없습니다.
                            return false;
                        }
                    }
                }
            }

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/prodBatchChange/prodBatchChange/getProdBatchChangeSave.sb", params, function(){ $scope.allSearch() });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchProdBatchChange();
    };
    // <-- //그리드 저장 -->

}]);