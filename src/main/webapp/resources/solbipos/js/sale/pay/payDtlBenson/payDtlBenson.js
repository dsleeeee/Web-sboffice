/****************************************************************
 *
 * 파일명 : payDtlBenson.js
 * 설  명 : 벤슨 > 결제수단매출 > 결제수단상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.16     김유승      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('payDtlBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('payDtlBensonCtrl', $scope, $http, true));

    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList);
    $scope._setComboData("momsTeamCombo", momsTeamComboList);
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList);
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList);
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList);
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList);
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList);
    $scope._setComboData("branchCdCombo", branchCdComboList);
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList);
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList);
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList);
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList);
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList);

    $scope.initGrid = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    $scope.$on("payDtlBensonCtrl", function (event, data) {
        $scope.searchPayDtlBensonList();
        event.preventDefault();
    });

    // 결제수단상세 리스트 조회
    $scope.searchPayDtlBensonList = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000);

        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        if (diffDay > 30) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#payDtlBensonStoreCd").val();
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;
        params.listScale = 500;

        if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.storeHqBrandCd === undefined) {
            var momsHqBrandCd = "";
            for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
                if (momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ",";
                }
            }
            params.userBrands = momsHqBrandCd;
        }

        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }

        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/sale/pay/payDtlBenson/payDtlBenson/getPayDtlBensonList.sb", params, function (response) {
            var grid = $scope.flex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;

            // 페이징 처리
            var list = response.data.list;
            if (list.length === undefined || list.length === 0) {
                $scope.data = new wijmo.collections.CollectionView([]);
                if (true && response.message) {
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

            if (response.data.page && response.data.page.curr) {
                var pagingInfo = response.data.page;
                $scope._setPagingInfo('ctrlName', $scope.name);
                $scope._setPagingInfo('pageScale', pagingInfo.pageScale);
                $scope._setPagingInfo('curr', pagingInfo.curr);
                $scope._setPagingInfo('totCnt', pagingInfo.totCnt);
                $scope._setPagingInfo('totalPage', pagingInfo.totalPage);
                $scope._broadcast('drawPager');
            }
        });
    };

    // 현재 화면 엑셀다운로드
    $scope.excelDownloadCurrent = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000);

        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        if (diffDay > 30) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            }, menuNm + "_" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + "_" + wijmo.Globalize.format(endDate.value, 'yyyyMMdd') + "_" + getCurDateTime() + ".xlsx", function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive');
                }, 10);
            });
        }, 10);
    };

    // 분할 엑셀다운로드 조회조건 세팅
    $scope.excelDownloadDivision = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000);

        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        if (diffDay > 30) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#payDtlBensonStoreCd").val();
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;

        if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.storeHqBrandCd === undefined) {
            var momsHqBrandCd = "";
            for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
                if (momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ",";
                }
            }
            params.userBrands = momsHqBrandCd;
        }

        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function () {
            $scope._broadcast('payDtlBensonExcelCtrl', params);
        });
    };
}]);

app.controller('payDtlBensonExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('payDtlBensonExcelCtrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    $scope.$on("payDtlBensonExcelCtrl", function (event, data) {
        $scope.searchExcelDivisionList(data);
        event.preventDefault();
    });

    // 분할 엑셀다운로드
    $scope.searchExcelDivisionList = function (params) {
        $scope.excelUploadingPopup(true);
        $("#totalRows").html(0);

        var listSize = 0;
        var totFileCnt = 0;

        // 전체 데이터 수 조회
        params.limit = 1;
        params.offset = 1;

        $.postJSON("/sale/pay/payDtlBenson/payDtlBenson/getPayDtlBensonList.sb", params, function (response) {
            var list = response.data.list;
            listSize = list && list.length > 0 ? list[0].totCnt : 0;
            totFileCnt = Math.ceil(listSize / 50000); // 하나의 엑셀파일에 50000개씩 다운로드

            if (listSize === 0 || totFileCnt === 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]);
                $scope.excelUploadingPopup(false);
                return false;
            }

            $("#totalRows").html(totFileCnt);

            function delay(x) {
                return new Promise(function (resolve, reject) {
                    $("#progressCnt").html(x + 1);

                    // 페이징 50000개씩 지정해 분할 다운로드 진행
                    // 조회 범위 지정
                    params.limit = 50000 * (x + 1);
                    params.offset = (50000 * (x + 1)) - 49999;

                    if (document.getElementsByName('sessionId')[0]) {
                        params['sid'] = document.getElementsByName('sessionId')[0].value;
                    }

                    $.postJSON("/sale/pay/payDtlBenson/payDtlBenson/getPayDtlBensonExcelDivisionList.sb", params, function (response) {
                        var list = response.data.list;

                        if (list.length === undefined || list.length === 0) {
                            $scope.excelFlex.itemsSource = new wijmo.collections.CollectionView([]);
                            $scope.excelUploadingPopup(false);
                            return false;
                        }

                        $scope.excelFlex.itemsSource = list;
                        $scope.excelFlex.itemsSource.trackChanges = true;

                        setTimeout(function () {
                            if ($scope.excelFlex.rows.length <= 0) {
                                $scope._popMsg(messages["excelUpload.not.downloadData"]);
                                $scope.excelUploadingPopup(false);
                                return false;
                            }

                            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                                includeColumnHeaders: true,
                                includeCellStyles: false,
                                includeColumns: function (column) {
                                    return column.visible;
                                }
                            }, menuNm + "_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime() + "_" + (x + 1) + ".xlsx", function () {
                                $timeout(function () {
                                    getExcelFile(x + 1);
                                }, 500);
                            }, function (reason) {
                                $scope.excelUploadingPopup(false);
                            });
                        }, 1000);

                        resolve(x);
                    });
                });
            }

            async function getExcelFile(x) {
                if (totFileCnt > x) {
                    await delay(x);
                } else {
                    $scope.excelUploadingPopup(false);
                }
            }

            getExcelFile(0);
        });
    };

    // 분할 엑셀다운로드 진행 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            var innerHtml = '<div class="wj-popup-loading"><p class="bk">' + messages['cmm.progress'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 다운로드 진행 중...</div>';
            innerHtml += '<p><img src="/resource/solbipos/css/img/loading.gif" alt="" /></p></div>';
            $scope._loadingPopup.content.innerHTML = innerHtml;
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };
}]);
