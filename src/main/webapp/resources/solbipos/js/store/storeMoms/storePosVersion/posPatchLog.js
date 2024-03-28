/****************************************************************
 *
 * 파일명 : posPatchLog.js
 * 설  명 : 매장포스버전현황 > 포스패치로그탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.03.13     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 적용매장구분
var regStoreFgAllData = [
    {"name":"전체","value":""},
    {"name":"등록","value":"Y"},
    {"name":"미등록","value":"N"}
];

// 최종매출일
var dateAllData = [
    {"name":"전체","value":""},
    {"name":"7일이내","value":"7"},
    {"name":"31일이내","value":"31"}
];

// 포스메인여부
var mainValAllData = [
    {"name":"전체","value":""},
    {"name":"메인포스","value":"1"},
    {"name":"서브포스","value":"2"}
];

// 패치일자 사용여부
var patchDtTypeComboData = [
    {"name":"미사용","value":"N"},
    {"name":"사용","value":"Y"}
];

// 패치성공여부
var patchFgData = [
    {"name":"전체","value":""},
    {"name":"성공","value":"Y"},
    {"name":"실패","value":"N"}
];

app.controller('posPatchLogCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posPatchLogCtrl', $scope, $http, true));

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("patchDtType", patchDtTypeComboData); // 패치일자
    $scope._setComboData("registFgCombo", regStoreFgAllData); // 적용매장구분
    $scope._setComboData("selectVerCombo2", selectVerComboListSel); // 버전체크
    $scope._setComboData("lastSaleCombo", dateAllData); // 최종매출일
    $scope._setComboData("mainValCombo", mainValAllData); // 포스메인여부
    $scope._setComboData("subValCombo", selectSubPos); // 포스용도
    $scope._setComboData("verChkCombo", verChkAllData); // 버전체크
    $scope._setComboData("posLogDtCombo", dateAllData); // 포스로그인일자
    $scope._setComboData("patchFgCombo", patchFgData); // 패치여부


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.lastSale = "7";

        var ac = 0;
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if(item.patchCd !== '0000') {
                    wijmo.addClass(e.cell, 'red');
                }
            }
        });

    };

    // 사용 구분 콤보박스 선택 이벤트
    $scope.setUseYnCombo = function (s) {
        if (s.selectedValue === "Y") {
            $scope.startDateCombo.isReadOnly = false;
            $scope.endDateCombo.isReadOnly = false;
        } else if (s.selectedValue === "N") {
            $scope.startDateCombo.isReadOnly = true;
            $scope.endDateCombo.isReadOnly = true;
        }
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("posPatchLogCtrl", function (event, data) {
        $scope.searchPosPatchLogList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 리스트 조회
    $scope.searchPosPatchLogList = function () {

        // 파라미터
        var params       = {};
        params.storeCd              = $scope.storeCd;
        params.storeNm              = $scope.storeNm;
        params.prodHqBrandCd        = $scope.prodHqBrandCd;
        params.momsTeam             = $scope.momsTeam;
        params.momsAcShop           = $scope.momsAcShop;
        params.momsAreaFg           = $scope.momsAreaFg;
        params.momsCommercial       = $scope.momsCommercial;
        params.momsShopType         = $scope.momsShopType;
        params.momsStoreManageType  = $scope.momsStoreManageType;
        params.branchCd             = $scope.branchCd;
        params.storeHqBrandCd       = $scope.storeHqBrandCd;
        params.registFg             = $scope.registFg;
        params.selectVer            = $scope.selectVer;
        params.lastSale             = $scope.lastSale;
        params.mainVal              = $scope.mainVal;
        params.subVal               = $scope.subVal;
        params.posLogDt             = $scope.posLogDt;


        var verCd =params.selectVer.indexOf("]");
        params.selectVerCd = params.selectVer.substring(1,verCd);
        $scope.selectVerCd = params.selectVerCd;

        // 패치일자 '사용/미사용' 선택에 따른 params
        if($scope.patchDtTypeCombo.selectedValue === "Y"){
            params.startDate = wijmo.Globalize.format($scope.startDateCombo.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.endDateCombo.value, 'yyyyMMdd');
        }

        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;
        params.listScale = 500;

        $scope._inquiryMain("/store/storeMoms/storePosVersion/posPatchLog/posPatchLogList.sb", params, function() {}, false);
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange2 = function(){
        if( $("#tblSearchAddShow2").css("display") === 'none') {
            $("#tblSearchAddShow2").show();
        } else {
            $("#tblSearchAddShow2").hide();
        }
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
                    return column.visible;
                }
            },
                messages["storePosVersion.posPatchLog"] + '_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);