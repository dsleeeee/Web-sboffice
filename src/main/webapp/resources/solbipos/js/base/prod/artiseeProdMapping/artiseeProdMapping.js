/****************************************************************
 *
 * 파일명 : artiseeProdMapping.js
 * 설  명 : 아티제상품코드맵핑 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.09.27     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var regYn = [
    {"name": "전체", "value": ""},
    {"name": "등록", "value": "Y"},
    {"name": "미등록", "value": "N"}
];

var useYnAllComboData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 아티제상품코드맵핑
app.controller('artiseeProdMappingCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('artiseeProdMappingCtrl', $scope, $http, true));

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

    // ComboBox 셋팅
    $scope._setComboData('regYn', regYn);
    $scope._setComboData('useYnAllComboData', useYnAllComboData);
    $scope._setComboData('prodTypeFg', prodTypeFg);

    // 등록일자 기본 '전체기간'으로
    $scope.isChecked = true;

    $scope.initGrid = function (s, e) {

        // 등록일자 기본 '전체기간'으로
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;


        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'prodCd') {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 카테고리 코드 클릭 시, 키맵과 상품 조회
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "prodCd") {
                    var artiseeProdCtrl = agrid.getScope("artiseeProdCtrl");
                    artiseeProdCtrl._pageView('artiseeProdCtrl', 1);
                    $scope._broadcast('artiseeProdCtrl', selectedRow);
                    var artiseeProdCtrlPager = document.getElementById('artiseeProdCtrlPager');
                    artiseeProdCtrlPager.style.visibility='visible'
                    event.preventDefault();
                }
            }
        });
    };

    $scope.$on("artiseeProdMappingCtrl", function(event, data) {
        $scope.mapStrList();
        event.preventDefault();
    });

    //전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    // 키오스크 카테고리(분류) 조회
    $scope.mapStrList = function(){

        var params = {};
        params.chkDt = $scope.isChecked;
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.prodClassCd = $scope.prodClassCd;
        params.barCd = $scope.barCd;
        params.useYn = $scope.useYn;
        params.prodTypeFg = $scope.prodTypeFg;
        params.regYn = $scope.regYn;

        $scope._inquiryMain("/base/prod/artiseeProdMapping/artiseeProdMapping/getMapStrList.sb", params, function() {

            $scope.reset();

        }, false);
    };

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
    }

    // 등록 상품 삭제
    $scope.delete = function() {

        $scope._popConfirm(messages["cmm.choo.delete"], function () {
            var params = [];

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            if(params.length <= 0) {
                $scope._popMsg(messages["cmm.not.modify"]);
                return false;
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/artiseeProdMapping/artiseeProdMapping/getDeleteMappingProd.sb", params, function () {
                $scope.mapStrList()
            });
        });
    };

    // 초기화
    $scope.reset = function(){

        // 상품 검색조건 초기화
        var scope = agrid.getScope('artiseeProdCtrl');
        scope.srchStartDate.selectedValue = new Date();
        scope.srchEndDate.selectedValue = new Date();
        scope.srchStartDate.isReadOnly = true;
        scope.srchEndDate.isReadOnly = true;
        scope.isChecked = true;
        scope.prodCd = "";
        scope.prodNm = "";
        scope.prodClassCd = "";
        scope.prodClassCdNm = "";
        scope.barCd = "";
        scope.useYnAllCombo.selectedIndex = 1;
        scope.prodTypeFgAllCombo.selectedIndex = 0;
        scope.regYnAllCombo.selectedIndex = 0;

        var divBtnProd = document.getElementById('divBtnProd');
        divBtnProd.style.visibility='hidden'

        // 상품 grid 초기화
        var wjGridProd = wijmo.Control.getControl("#wjGridProd");
        while(wjGridProd.rows.length > 0){
            wjGridProd.rows.removeAt(wjGridProd.rows.length-1);
        }

        // 상품 grid paging 초기화(숨기기.. 아예 없애는거 모름..)
        var artiseeProdCtrlPager = document.getElementById('artiseeProdCtrlPager');
        artiseeProdCtrlPager.style.visibility='hidden'
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    // return column.visible;
                    return column.binding != 'gChk';
                }
            }, '아티제상품맵핑코드_'+getCurDateTime()+'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);

// 맵핑 상품
app.controller('artiseeProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('artiseeProdCtrl', $scope, $http, true));

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#prodSrchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#prodSrchTimeEndDate", gvEndDate);

    // ComboBox 셋팅
    $scope._setComboData('regYn', regYn);
    $scope._setComboData('useYnAllComboData', useYnAllComboData);
    $scope._setComboData('prodTypeFg', prodTypeFg);

    // 등록일자 기본 '전체기간'으로
    $scope.isChecked = true;

    $scope.initGrid = function (s, e) {

        // 등록일자 기본 '전체기간'으로
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'prodCd') {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 상품 코드 클릭 시, 등록
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "prodCd") {
                    $scope.regProd(selectedRow);
                }
            }
        });
    };

    $scope.$on("artiseeProdCtrl", function(event, data) {

        if(data !== null && data!== undefined) {
            $scope.selectedRow    = data
        }

        var divBtnProd = document.getElementById('divBtnProd');
        divBtnProd.style.visibility='visible'

        // 상품 조회
        $scope.searchProd();
        event.preventDefault();
    });

    //전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

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
    }

    // 상품 조회
    $scope.searchProd = function () {

        var params = {};
        params.chkDt = $scope.isChecked;
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.prodClassCd = $scope.prodClassCd;
        params.barCd = $scope.barCd;
        params.useYn = $scope.useYn;
        params.prodTypeFg = $scope.prodTypeFg;
        params.regYn = $scope.regYn;

        $scope._inquiryMain("/base/prod/artiseeProdMapping/artiseeProdMapping/getProdList.sb", params, function() {

        }, false);

    }

    // 키맵 등록
    $scope.regProd = function (data) {

        $scope._popConfirm(messages["cmm.choo.save"], function() {

            var params = data;
            params.erpProdCd        = data.prodCd;
            params.mappingString    = $scope.selectedRow.mappingString;
            params.mappingCd        = $scope.selectedRow.mappingCd;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/artiseeProdMapping/artiseeProdMapping/getRegProd.sb", params, function(){

                // 상품 재조회
                $scope.searchProd()

                // 재조회
                var artiseeProdMappingCtrl = agrid.getScope("artiseeProdMappingCtrl");
                artiseeProdMappingCtrl._pageView('artiseeProdMappingCtrl', 1);

            });
        });
    }

    // 조회조건 엑셀다운로드
    $scope.excelDownload2 = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.chkDt = $scope.isChecked;
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.prodClassCd = $scope.prodClassCd;
        params.barCd = $scope.barCd;
        params.useYn = $scope.useYn;
        params.prodTypeFg = $scope.prodTypeFg;
        params.regYn = $scope.regYn;

        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('artiseeProdExcelCtrl', params);
        });
    };

}]);

/** 엑셀다운로드 controller */
app.controller('artiseeProdExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('artiseeProdExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("artiseeProdExcelCtrl", function (event, data) {
        $scope.getProdExcelList(data);
        // 기능수행 종료 : 반드시 추가
        //    event.preventDefault();
    });

    // 상품매출순위 리스트 조회
    $scope.getProdExcelList = function (data) {
        // 파라미터
        var params = data;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/prod/artiseeProdMapping/artiseeProdMapping/getProdExcelList.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        // return column.visible;
                        return column.binding != 'gChk';
                    }
                }, '아티제상품맵핑코드_상품_'+getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);