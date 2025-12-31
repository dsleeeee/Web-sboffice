/****************************************************************
 *
 * 파일명 : saleMcoupon.js
 * 설  명 : 모바일쿠폰 현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.04.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  모바일쿠폰 현황 그리드 생성
 */
app.controller('saleMcouponCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleMcouponCtrl', $scope, $http, false));

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
        $scope.brandCdDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "storeCd" || col.binding === "saleDate") {
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
                    selectedRow.startDate = $scope.startDate;
                    selectedRow.endDate = $scope.endDate;

                    $scope.setSelectedSaleMcoupon(selectedRow);
                    $scope.wjSaleMcouponDtlLayer.show(true);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("saleMcouponCtrl", function(event, data) {
        $scope.searchSaleMcoupon();
        event.preventDefault();
    });

    $scope.searchSaleMcoupon = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 매장 1년, 본사(매장외) 31일
        if(orgnFg != null && orgnFg == 'STORE')
        {
            // 조회일자 최대 1년(365일) 제한
            if (diffDay > 365) {
                $scope._popMsg(messages['cmm.dateOver.1year.error']);
                return false;
            }
        }
        else
        {
            // 조회일자 최대 1달(31일) 제한
            if (diffDay > 31) {
                $scope._popMsg(messages['cmm.dateOver.1month.error']);
                return false;
            }
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#saleMcouponStoreCd").val();
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
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

        $scope.startDate = params.startDate;
        $scope.endDate  = params.endDate;

        // 페이징 처리
        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }
        // 가상로그인 대응한 session id 설정
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/sale/status/saleMcoupon/saleMcoupon/getSaleMcouponList.sb", params, function(response) {
            var grid = $scope.flex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;

            var list = response.data.list;
            if (list.length === undefined || list.length === 0) {
                $scope.data = new wijmo.collections.CollectionView([]);
                if (true && response.message) {

                    // 페이징 처리
                    $scope._setPagingInfo('ctrlName', $scope.name);
                    $scope._setPagingInfo('pageScale', 10);
                    $scope._setPagingInfo('curr', 1);
                    $scope._setPagingInfo('totCnt', 1);
                    $scope._setPagingInfo('totalPage', 1);

                    $scope._broadcast('drawPager');

                    $scope._popMsg(response.message);
                }
                return false;
            }
            var data = new wijmo.collections.CollectionView(list);
            data.trackChanges = true;
            $scope.data = data;

            // 페이징 처리
            if (response.data.page && response.data.page.curr) {
                var pagingInfo = response.data.page;
                $scope._setPagingInfo('ctrlName', $scope.name);
                $scope._setPagingInfo('pageScale', pagingInfo.pageScale);
                $scope._setPagingInfo('curr', pagingInfo.curr);
                $scope._setPagingInfo('totCnt', pagingInfo.totCnt);
                $scope._setPagingInfo('totalPage', pagingInfo.totalPage);
                $scope._broadcast('drawPager');
            }
        }, function(response) {
            s_alert.pop(response.message);
            var grid = $scope.flex;
            grid.itemsSource = new wijmo.collections.CollectionView([]);
        });
    };
    // <-- //검색 호출 -->

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 선택
    $scope.selectedSaleMcoupon;
    $scope.setSelectedSaleMcoupon = function(store) {
        $scope.selectedSaleMcoupon = store;
    };
    $scope.getSelectedSaleMcoupon = function() {
        return $scope.selectedSaleMcoupon;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 모바일쿠폰 현황 상세 팝업 핸들러 추가
        $scope.wjSaleMcouponDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('saleMcouponDtlCtrl', $scope.getSelectedSaleMcoupon());
            }, 50)
        });
    });

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

        // 매장 1년, 본사(매장외) 31일
        if(orgnFg != null && orgnFg == 'STORE')
        {
            // 조회일자 최대 1년(365일) 제한
            if (diffDay > 365) {
                $scope._popMsg(messages['cmm.dateOver.1year.error']);
                return false;
            }
        }
        else
        {
            // 조회일자 최대 1달(31일) 제한
            if (diffDay > 31) {
                $scope._popMsg(messages['cmm.dateOver.1month.error']);
                return false;
            }
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#saleMcouponStoreCd").val();
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
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

        $scope._broadcast('saleMcouponExcelCtrl', params);
    };

}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('saleMcouponExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleMcouponExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.brandCdDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("saleMcouponExcelCtrl", function(event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/status/saleMcoupon/saleMcoupon/getSaleMcouponExcelList.sb", params, function (response){
            var grid = $scope.excelFlex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;

            if (grid.rows.length <= 0) {
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
                },  '모바일쿠폰 현황' + '_' + params.startDate + '_' + params.endDate + '_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        }, function(response) {
            s_alert.pop(response.message);
            var grid = $scope.excelFlex;
            grid.itemsSource = new wijmo.collections.CollectionView([]);
        });
    };
    // <-- //검색 호출 -->

}]);