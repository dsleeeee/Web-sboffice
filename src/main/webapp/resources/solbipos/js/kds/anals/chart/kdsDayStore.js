/**
 * get application
 */
var app = agrid.getApp();
app.controller('kdsDayStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kdsDayStoreCtrl', $scope, $http, $timeout, true));

    $scope._getComboDataQuery('400', 'kdsMakeDate', '');
    $scope._getComboDataQuery('401', 'kdsPicDate', '');
    $scope._getComboDataQuery('402', 'kdsMakeDateSec', '');
    $scope._getComboDataQuery('403', 'kdsPicDateSec', '');
    $scope.picChecked = true;
    $scope.makeChecked = true;

    // $scope.kdsMakeDate = [
    //     {name: '최초조리일시', value: 'AVG_S_CK_TO_E_CK'},
    //     {name: '최종주문일시', value: 'AVG_L_OD_TO_E_CK'}
    // ];
    // $scope.kdsPicDate = [
    //     {name: '최종조리일시', value: 'AVG_S_CK_TO_E_CK'}
    // ];
    // $scope.kdsMakeDateSec = [
    //     {name: '주문시작일시', value: 'AVG_S_CK_TO_E_CK'},
    //     {name: '최종픽업일시', value: 'AVG_L_OD_TO_E_CK'}
    // ];
    // $scope.kdsPicDateSec = [
    //     {name: '최초조리일시', value: 'AVG_S_CK_TO_E_CK'},
    //     {name: '최종주문일시', value: 'AVG_L_OD_TO_E_CK'}
    // ];

    $scope._setComboData("rRegStoreCd", regstrStoreList);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    $scope.storeNmChk = '';
    $scope.conStoreNmChk = '';
    $scope.initGrid = function (s, e) {
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "dlvrAddr") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });
        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        $scope.dataItem = {};
        $scope.dataItem.saleDate = messages["kds.saleDate"];
        $scope.dataItem.prodCd = messages["kds.search.store"];
        $scope.dataItem.prodNm = messages["kds.search.store"];
        $scope.dataItem.saleQty = messages["kds.search.store"];
        $scope.dataItem.conProdCd = messages["kds.con.store"];
        $scope.dataItem.conProdNm = messages["kds.con.store"];
        $scope.dataItem.conSaleQty = messages["kds.con.store"];

        $scope.dataItem.orderCnt = messages["kds.search.store"];
        $scope.dataItem.avgMake = messages["kds.search.store"];
        $scope.dataItem.avgPic = messages["kds.search.store"];
        $scope.dataItem.conOrderCnt = messages["kds.con.store"];
        $scope.dataItem.conAvgMake = messages["kds.con.store"];
        $scope.dataItem.conAvgPic = messages["kds.con.store"];

        s.columnHeaders.rows[0].dataItem = $scope.dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display: 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display: 'table-cell',
                    verticalAlign: 'middle',
                    textAlign: 'center'
                });
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
    };

    function getData(dataList) {
        // dataList.forEach((e, i)=> {
        //     console.log(e);
        // })
        let view = new wijmo.collections.CollectionView(dataList);
        return view;
    }

    var chart1 = '';
    var chart2 = '';

    function chartList(list) {
        // create the Pareto chart
        chart1 = new wijmo.chart.FlexChart('#chart1', {
            itemsSource: getData(list),
            bindingX: "saleDate",
            legend: {
                position: wijmo.chart.Position.None
            },
            // series: [
            //     {binding: 'cntHh00', name: '제조시간'},
            //     {binding: '', name: '픽업시간'},
            // ],
            series: [
                {
                    rendering: function (s, e) {
                        s.binding = 'avgMake';
                        s.name = '조회매장';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'conAvgMake';
                        s.name = '대비매장';
                    }
                }
            ],
            palette: ['#ff9d39', '#00ba8b', '#ff9d39', '#00ba8b']
        });

        chart2 = new wijmo.chart.FlexChart('#chart2', {
            itemsSource: getData(list),
            bindingX: "saleDate",
            legend: {
                // position: wijmo.chart.Position.Top
                position: wijmo.chart.Position.None
            },
            // series: [
            //     {binding: 'cntHh00', name: '제조시간'},
            //     {binding: '', name: '픽업시간'},
            // ],
            series: [
                {
                    rendering: function (s, e) {
                        s.binding = 'avgPic';
                        s.name = '조회매장';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'conAvgPic';
                        s.name = '대비매장';
                    }
                }
            ],
            palette: ['#ff9d39', '#00ba8b', '#ff9d39', '#00ba8b']
        });
    };

    var list = [];
    // 조회
    $scope.$on("kdsDayStoreList", function () {

        if (!$scope.valueCheck()) return false;
        var params = {};
        params.kdsDayStartDate = dateToDaystring($scope.kdsDayStartDate).replaceAll('-', '');
        params.kdsDayEndDate = dateToDaystring($scope.kdsDayEndDate).replaceAll('-', '');
        params.makeDate = $scope.makeDate;
        params.makeDateSec = $scope.makeDateSec;
        params.picDate = $scope.picDate;
        params.picDateSec = $scope.picDateSec;
        params.startHh = $scope.timeZone;
        params.endHh = $scope.timeZoneSec;
        params.kdsTimeList = $scope.kdsTimeZone2;
        params.storeCd = $("#regStoreCd").val();
        params.conStoreCd = $("#conRegStoreCd").val();
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.prodClassCd = $scope.prodClassCd;
        params.prodClassCdNm = $scope.prodClassCdNm;

        $scope.kdsSearch(params);
    });

    $scope.kdsSearch = function (params) {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');
        // 마스터그리드 여부
        if (true) {
            var el = angular.element('input');
            var name = '';
            for (var i = 0, l = el.length; i < l; i += 1) {
                name = angular.element(el[i]).attr('ng-model');
                if (name && $scope[name]) {
                    params[name] = $scope[name];
                }
            }
        }
        // 가상로그인 세션정보
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }
        $http({
            method: 'POST', //방식
            url: "/kds/anals/chart/store/getKdsStore.sb", /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (response.data.status === "OK") {
                $scope.prodChk = $scope.prodCd;
                $scope.storeNmChk = $("#regStoreNm").val();
                $scope.conStoreNmChk = $("#conRegStoreNm").val();
                $scope.dataItem.saleDate = messages["kds.saleDate"];
                $scope.dataItem.prodCd = messages["kds.search.store"] + " " + $scope.storeNmChk;
                $scope.dataItem.prodNm = messages["kds.search.store"] + " " + $scope.storeNmChk;
                $scope.dataItem.saleQty = messages["kds.search.store"] + " " + $scope.storeNmChk;
                $scope.dataItem.conProdCd = messages["kds.con.store"] + " " + $scope.conStoreNmChk;
                $scope.dataItem.conProdNm = messages["kds.con.store"] + " " + $scope.conStoreNmChk;
                $scope.dataItem.conSaleQty = messages["kds.con.store"] + " " + $scope.conStoreNmChk;
                $scope.dataItem.orderCnt = messages["kds.search.store"] + " " + $scope.storeNmChk;
                $scope.dataItem.avgMake = messages["kds.search.store"] + " " + $scope.storeNmChk;
                $scope.dataItem.avgPic = messages["kds.search.store"] + " " + $scope.storeNmChk;
                $scope.dataItem.conOrderCnt = messages["kds.con.store"] + " " + $scope.conStoreNmChk;
                $scope.dataItem.conAvgMake = messages["kds.con.store"] + " " + $scope.conStoreNmChk;
                $scope.dataItem.conAvgPic = messages["kds.con.store"] + " " + $scope.conStoreNmChk;

                $scope.flex.columnHeaders.rows[0].dataItem = $scope.dataItem;
                list = response.data.data.list;
                if (list.length === undefined || list.length === 0) {
                    $scope.data = new wijmo.collections.CollectionView([]);
                    if (true && response.data.message) {
                        $scope._popMsg(response.data.message);
                    }
                    return false;
                }
                var data = new wijmo.collections.CollectionView(list);
                data.trackChanges = true;
                $scope.data = data;
                if (chart1 === '') {
                    chartList(list);
                } else {
                    chart1.itemsSource = getData(list);
                }
                if (chart2 === '') {
                    chartList(list);
                } else {
                    chart2.itemsSource = getData(list);
                }
            }
        }, function errorCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            if (typeof callback === 'function') {
                setTimeout(function () {
                    callback();
                }, 10);
            }
        });
        // $scope._inquiryMain("/kds/anals/chart/day/getKdsDay.sb", params, function (res) {
        //     console.log(res);
        // });
    };
// 차트
    $scope.chartKds = function () {
        var date1 = new Date($scope.kdsDayStartDate);
        var date2 = new Date($scope.kdsDayEndDate);
        var diffDay = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
        if (diffDay > 30) {
            $scope._popMsg(messages['kds.date.error']);
            return false;
        }
        var params = {};
        params.kdsDayStartDate = dateToDaystring($scope.kdsDayStartDate).replaceAll('-', '');
        params.kdsDayEndDate = dateToDaystring($scope.kdsDayEndDate).replaceAll('-', '');
        params.makeDate = $scope.makeDate;
        params.makeDateSec = $scope.makeDateSec;
        params.picDate = $scope.picDate;
        params.picDateSec = $scope.picDateSec;
        params.startHh = $scope.timeZone;
        params.endHh = $scope.timeZoneSec;
        params.kdsTimeList = $scope.kdsTimeZone2;
        params.storeCd = $("#regStoreCd").val();
        params.conStoreCd = $("#conRegStoreCd").val();
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');
        $http({
            method: 'POST', //방식
            url: "/kds/anals/chart/dayTime/getKdsDayTimeChart.sb", /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (response.data.status === "OK") {
                list = response.data.data.list;
                if (list.length === undefined || list.length === 0) {
                    if (true && response.data.message) {
                        $scope._popMsg(response.data.message);
                    }
                    return false;
                }
                var data = new wijmo.collections.CollectionView(list);
                data.trackChanges = true;
                if (chart1 === '') {
                    chartList(list);
                } else {
                    chart1.itemsSource = getData(list);
                }
            }
        }, function errorCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            if (typeof callback === 'function') {
                setTimeout(function () {
                    callback();
                }, 10);
            }
        });
    };

// 픽업시간
    $scope.picChkDt = function () {
        // getData(list);
        chart1.series = chart1.series.forEach((e, i) => {
            if ($scope.picChecked !== true) {
                if (e.binding === 'avgPic') {
                    e.visibility = 3;
                }
            } else {
                if (e.binding === 'avgPic') {
                    e.visibility = 0;
                }
            }
        });
    };


// 제조시간
    $scope.makeChkDt = function () {
        chart1.series = chart1.series.forEach((e, i) => {
            if ($scope.makeChecked !== true) {
                if (e.binding === 'avgMake') {
                    e.visibility = 3;
                }
            } else {
                if (e.binding === 'avgMake') {
                    e.visibility = 0;
                }
            }
        });
    };

    // 조회 매장
    $scope.regStoreShow = function () {
        $scope._broadcast('regStoreCtrl');
    };

    // 대비 매장
    $scope.conRegStoreShow = function () {
        $scope._broadcast('conRegStoreCtrl');
    };

    // 체크
    $scope.valueCheck = function () {
        var date1 = new Date($scope.kdsDayStartDate);
        var date2 = new Date($scope.kdsDayEndDate);
        var diffDay = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
        if (diffDay > 30) {
            $scope._popMsg(messages['kds.date.error']);
            return false;
        }
        var msg = messages["kds.makeDate.setting"] + messages["cmm.require.text"];
        if (isNull($scope.makeDateCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }
        var msg = messages["kds.makeDate.setting"] + messages["cmm.require.text"];
        if (isNull($scope.makeDateSecCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }
        var msg = messages["kds.picDate.setting"] + messages["cmm.require.text"];
        if (isNull($scope.picDateCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }
        var msg = messages["kds.picDate.setting"] + messages["cmm.require.text"];
        if (isNull($scope.picDateSecCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        var msg = messages["kds.store"] + messages["cmm.require.text"];
        if (isNull($("#regStoreCd").val())) {
            $scope._popMsg(msg);
            return false;
        }
        var msg = messages["kds.store"] + messages["cmm.require.text"];
        if (isNull($("#conRegStoreCd").val())) {
            $scope._popMsg(msg);
            return false;
        }
        return true;
    };

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {
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
            }, 'KDS_주문단위_시간대별_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
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
                    function (response) {
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };
}])
;
