/****************************************************************
 *
 * 파일명 : dataRcvStatus.js
 * 설  명 : 맘스터치 > 매장관리 > 자료수신현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.03     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 자료수신현황 헤더 컨트롤러
 */
app.controller('dataRcvStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dataRcvStatusCtrl', $scope, $http, false));

    // 조회기간 datepicker
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList);    // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList);                // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList);            // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList);            // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList);    // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList);        // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList);                // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList);      // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList);      // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList);      // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList);      // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList);      // 매장그룹5

    // 헤더 그리드 초기화
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.hqBrandCdDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name'); // 브랜드
        $scope.momsTeamDataMap  = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');      // 팀별
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');   // AC점포별

        // 일자 컬럼 링크 스타일 적용
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === 'logDate') {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 일자 셀 클릭 시 우측 상세 조회
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                if (col.binding === 'logDate') {
                    var item = s.rows[ht.row].dataItem;
                    if (item) {
                        $scope._broadcast('dataRcvStatusDtlCtrl', item);
                    }
                }
            }
        });
    };

    // 헤더 목록 조회
    $scope.$on('dataRcvStatusCtrl', function (event, data) {
        $scope.searchDataRcvStatusHdrList();
        event.preventDefault();
    });

    $scope.searchDataRcvStatusHdrList = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt   = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000);

        // 시작일자가 종료일자보다 큰 경우
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회기간 31일 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 상세 그리드 초기화
        $scope._broadcast('dataRcvStatusDtlCtrl', null);

        var params = {};
        params.startDate        = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate          = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeHqBrandCd   = $scope.storeHqBrandCd;
        params.storeCds         = $("#dataRcvStatusStoreCd").val();
        params.momsTeam         = $scope.momsTeam;
        params.momsAcShop       = $scope.momsAcShop;
        params.momsAreaFg       = $scope.momsAreaFg;
        params.momsCommercial   = $scope.momsCommercial;
        params.momsShopType     = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd         = $scope.branchCd;
        params.momsStoreFg01    = $scope.momsStoreFg01;
        params.momsStoreFg02    = $scope.momsStoreFg02;
        params.momsStoreFg03    = $scope.momsStoreFg03;
        params.momsStoreFg04    = $scope.momsStoreFg04;
        params.momsStoreFg05    = $scope.momsStoreFg05;

        // 매장브랜드 '전체' 일때
        if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
                if (momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ",";
                }
            }
            params.userBrands = momsHqBrandCd;
        }

        // 가상로그인 대응
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/store/storeMoms/dataRcvStatus/dataRcvStatus/getDataRcvStatusHdrList.sb", params, function (response) {
            var list = response.data.list;
            if (list.length <= 0) {
                $scope.flex.itemsSource = new wijmo.collections.CollectionView([]);
                $scope._popMsg(response.message);
                return false;
            }
            $scope.flex.itemsSource = new wijmo.collections.CollectionView(list);
        });
    };

    // 헤더 엑셀다운로드
    $scope.excelDownloadHdr = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);
            return false;
        }
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) { return column.visible; }
            }, messages["dataRcvStatus.hdrTitle"] + '_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive');
                }, 10);
            });
        }, 10);
    };

}]);

/**
 * 자료수신현황 상세 컨트롤러
 */
app.controller('dataRcvStatusDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dataRcvStatusDtlCtrl', $scope, $http, false));

    // 상세 그리드 초기화
    $scope.initGrid = function (s, e) {};

    // 상세 목록 조회 (헤더 일자 셀 클릭 시)
    $scope.$on('dataRcvStatusDtlCtrl', function (event, data) {
        if (!data) {
            $scope.flex.itemsSource = new wijmo.collections.CollectionView([]);
            event.preventDefault();
            return;
        }

        var params = {};
        params.logDate  = data.logDate.replaceAll('-', '');
        params.storeCd  = data.storeCd;
        params.posNo    = data.posNo;
        params.logSeq   = data.logSeq;

        // 가상로그인 대응
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/store/storeMoms/dataRcvStatus/dataRcvStatus/getDataRcvStatusDtlList.sb", params, function (response) {
            var list = response.data.list;
            if (list.length <= 0) {
                $scope.flex.itemsSource = new wijmo.collections.CollectionView([]);
                return false;
            }
            $scope.flex.itemsSource = new wijmo.collections.CollectionView(list);
        });
        event.preventDefault();
    });

    // 상세 엑셀다운로드
    $scope.excelDownloadDtl = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);
            return false;
        }
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) { return column.visible; }
            }, messages["dataRcvStatus.dtlTitle"] + '_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive');
                }, 10);
            });
        }, 10);
    };

}]);
