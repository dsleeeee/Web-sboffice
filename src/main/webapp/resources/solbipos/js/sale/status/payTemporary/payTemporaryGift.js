/****************************************************************
 *
 * 파일명 : payTemporaryGift.js
 * 설  명 : 가승인-상품권결제차액 상세내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.05.03     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매출구분
var saleYnDataMapData = [
    {"name":"매출","value":"Y"},
    {"name":"반품","value":"N"}
];

/**
 *  가승인-상품권결제차액 상세내역 그리드 생성
 */
app.controller('payTemporaryGiftCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('payTemporaryGiftCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.saleYnDataMap = new wijmo.grid.DataMap(saleYnDataMapData, 'value', 'name'); // 매출구분
        $scope.branchCdDataMap = new wijmo.grid.DataMap(branchCdComboList2, 'value', 'name'); // 그룹
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList2, 'value', 'name'); // 팀별
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList2, 'value', 'name'); // AC점포별
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList2, 'value', 'name'); // 지역구분
        $scope.momsCommercialDataMap = new wijmo.grid.DataMap(momsCommercialComboList2, 'value', 'name'); // 상권
        $scope.momsShopTypeDataMap = new wijmo.grid.DataMap(momsShopTypeComboList2, 'value', 'name'); // 점포유형
        $scope.momsStoreManageTypeDataMap = new wijmo.grid.DataMap(momsStoreManageTypeComboList2, 'value', 'name'); // 매장관리타입
        $scope.momsStoreFg01DataMap = new wijmo.grid.DataMap(momsStoreFg01ComboList2, 'value', 'name'); // 매장그룹
        $scope.momsStoreFg02DataMap = new wijmo.grid.DataMap(momsStoreFg02ComboList2, 'value', 'name'); // 매장그룹2
        $scope.momsStoreFg03DataMap = new wijmo.grid.DataMap(momsStoreFg03ComboList2, 'value', 'name'); // 매장그룹3
        $scope.momsStoreFg04DataMap = new wijmo.grid.DataMap(momsStoreFg04ComboList2, 'value', 'name'); // 매장그룹4
        $scope.momsStoreFg05DataMap = new wijmo.grid.DataMap(momsStoreFg05ComboList2, 'value', 'name'); // 매장그룹5

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "billNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "billNo") {
                    var params = {};
                    params.storeCd = selectedRow.storeCd;
                    params.saleDate = selectedRow.saleDate.replaceAll("-", "");
                    params.posNo = selectedRow.posNo;
                    params.billNo = selectedRow.billNo;
                    params.saleYn = selectedRow.saleYn;

                    $scope._broadcast('billInfoCtrl', params);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("payTemporaryGiftCtrl", function(event, data) {
        $scope.searchPayTemporaryGift();
        event.preventDefault();
    });

    $scope.searchPayTemporaryGift = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#payTemporaryGiftStoreCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
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

        $scope._inquiryMain("/sale/status/payTemporary/payTemporaryGift/getPayTemporaryGiftList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.payTemporaryGiftStoreShow = function () {
        $scope._broadcast('payTemporaryGiftStoreCtrl');
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow2").css("display") === 'none') {
            $("#tblSearchAddShow2").show();
        } else {
            $("#tblSearchAddShow2").hide();
        }
    };

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#payTemporaryGiftStoreCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
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

        $scope._broadcast('payTemporaryGiftExcelCtrl', params);
    };

}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('payTemporaryGiftExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('payTemporaryGiftExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.saleYnDataMap = new wijmo.grid.DataMap(saleYnDataMapData, 'value', 'name'); // 매출구분

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("payTemporaryGiftExcelCtrl", function(event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/payTemporary/payTemporaryGift/getPayTemporaryGiftExcelList.sb", params, function (){

            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles: false,
                    includeColumns: function (column) {
                        return column.visible;
                    }
                },  '가승인-상품권결제차액 상세내역' + '_' + params.startDate + '_' + params.endDate + '_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
    // <-- //검색 호출 -->

}]);