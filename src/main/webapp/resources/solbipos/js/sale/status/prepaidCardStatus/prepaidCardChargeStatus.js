/****************************************************************
 *
 * 파일명 : prepaidCardChargeStatus.js
 * 설  명 : 선불카드 충전 현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.04.01     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var chargeFgAllFgData = [
    {"name": "전체", "value": ""},
    {"name": "충전", "value": "Y"},
    {"name": "충전취소", "value": "N"}
];

/**
 *  선불카드 충전 현황 그리드 생성
 */
app.controller('prepaidCardChargeStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prepaidCardChargeStatusCtrl', $scope, $http, false));

    // 접속 사용자의 권한
    $scope.userOrgnFg = gvOrgnFg;

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartChargeDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndChargeDate", gvEndDate);

    //
    $scope._setComboData("chargeFg", chargeFgAllFgData);

    if($scope.userOrgnFg == "H"){

        $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList);             // 매장브랜드
        $scope._setComboData("momsTeamCombo", momsTeamComboList);                       // 팀별
        $scope._setComboData("momsAcShopCombo", momsAcShopComboList);                   // AC점포별
        $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList);                   // 지역구분
        $scope._setComboData("momsCommercialCombo", momsCommercialComboList);           // 상권
        $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList);               // 점포유형
        $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
        $scope._setComboData("branchCdCombo", branchCdComboList);                       // 그룹
        $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
        $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
        $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
        $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
        $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.brandCdDataMap = new wijmo.grid.DataMap(userHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "storeCd" || col.binding === "saleDate") {
                    var item = s.rows[e.row].dataItem;
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
                if (col.binding === "storeCd" || col.binding === "saleDate") {
                    var params = {};
                    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
                    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
                    params.saleYn = $scope.chargeFgCombo.selectedValue;
                    params.storeCd = selectedRow.storeCd;

                    if (orgnFg === "STORE") {
                        params.saleDate = selectedRow.saleDate.replaceAll("-","");
                    }

                    $scope._broadcast('prepaidCardChargeStatusDtlCtrl', params);
                    $scope.prepaidCardChargeStatusDtlLayer.show(true);

                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    $scope.$on("prepaidCardChargeStatusCtrl", function (event, data) {

        // 조회
        $scope.searchPrepaidCardChargeStatus();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.searchPrepaidCardChargeStatus = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.saleYn = $scope.chargeFgCombo.selectedValue;
        params.storeCds = $("#prepaidCardChargeStatusStoreCd").val();
        params.listScale = 50;

        if(momsEnvstVal === "1" && orgnFg === "HQ") { // 확장조회는 본사권한이면서 맘스터치만 사용
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;
        }

        if(brandUseFg === "1" && orgnFg === "HQ"){
            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;

            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/prepaidCardStatus/getPrepaidCardChargeStatus.sb", params, function() {

        });
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

}]);