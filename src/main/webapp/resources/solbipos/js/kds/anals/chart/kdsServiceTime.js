/**
 * get application
 */
var app = agrid.getApp();
app.controller('kdsServiceTimeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kdsServiceTimeCtrl', $scope, $http, $timeout, true));

    $scope._getComboDataQuery('400', 'kdsMakeDate', '');
    $scope._getComboDataQuery('401', 'kdsPicDate', '');
    $scope._getComboDataQuery('402', 'kdsMakeDateSec', '');
    $scope._getComboDataQuery('403', 'kdsPicDateSec', '');

    $scope._setComboData("rRegStoreCd", regstrStoreList);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    $scope.picChecked = true;
    $scope.makeChecked = true;

    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

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
        var dataItem = {};

        dataItem.storeCd = messages["kds.storeCd"];
        dataItem.storeNm = messages["kds.storeNm"];
        dataItem.saleDate = messages["kds.saleDate"];
        dataItem.totalCount = messages["kds.total.count"];

        dataItem.opt1Cook1 = messages["kds.makeDate.standard"];
        dataItem.opt1Cook3 = messages["kds.makeDate.standard"];
        dataItem.opt1Cook5 = messages["kds.makeDate.standard"];
        dataItem.opt1Cook7 = messages["kds.makeDate.standard"];
        dataItem.opt1Cook10 = messages["kds.makeDate.standard"];
        dataItem.opt1CookOver10 = messages["kds.makeDate.standard"];

        dataItem.opt2Pickup1 = messages["kds.picDate.standard"];
        dataItem.opt2Pickup3 = messages["kds.picDate.standard"];
        dataItem.opt2Pickup5 = messages["kds.picDate.standard"];
        dataItem.opt2Pickup7 = messages["kds.picDate.standard"];
        dataItem.opt2Pickup10 = messages["kds.picDate.standard"];
        dataItem.opt2PickupOver10 = messages["kds.picDate.standard"];

        s.columnHeaders.rows[0].dataItem = dataItem;

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

    function testList() {
        var testList = [
            {
                opt1Cook1: 11,
                opt1Cook3: 20,
                opt1Cook5: 20,
                opt1Cook7: 30,
                opt1Cook10: 10,
                opt1CookOver10: 50,
                saleDate: "20200801"
            }
        ];
        return testList
    }

    function getData(dataList) {
        // dataList.forEach((e, i)=> {
        //     console.log(e);
        // })
        let view = new wijmo.collections.CollectionView(dataList);
        return view;
    }

    var chart1 = '';
    var chart2 = '';
    var theChartSelector1 = '';
    var theChartSelector2 = '';
    // var chart2 = '';
    // var chart2 = '';

    function chartList(list) {
        // create the Pareto chart
        chart1 = new wijmo.chart.FlexChart('#chart1', {
            itemsSource: getData(list),
            // itemsSource: testList(),
            bindingX: "saleDate",
            chartType: parseInt(0),
            stacking: parseInt(1),
            tooltip: {content: "{seriesName} {value}건"},
            legend: {
                position: wijmo.chart.Position.Left
            },
            // series: [
            //     {binding: 'cntHh00', name: '제조시간'},
            //     {binding: '', name: '픽업시간'},
            // ],
            series: [
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1Cook1';
                        s.name = '1분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1Cook3';
                        s.name = '3분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1Cook5';
                        s.name = '5분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1Cook7';
                        s.name = '7분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1Cook10';
                        s.name = '10분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1CookOver10';
                        s.name = '10분이상';
                    }
                }
            ],
            palette: ['#0085c7', '#00ba8b', '#ff9d39', '#ae63ff', '#6b1ce0', '#FF5C87']

        });


        if (getData(list).length === 1) {
            chart1.options = {groupWidth: "8%"};
        }

        // create the chart with range selector
        theChartSelector1 = new wijmo.chart.FlexChart('#theChartSelector1', {
            itemsSource: getData(list),
            bindingX: 'saleDate',
            tooltip: {content: "{seriesName} {value}건"},
            chartType: wijmo.chart.ChartType.Area,
            legend: {
                position: wijmo.chart.Position.None
            },
            axisX: {
                position: wijmo.chart.Position.None
            },
            axisY: {
                position: wijmo.chart.Position.None
            },
            palette: ['#0085c7', '#00ba8b', '#ff9d39', '#ae63ff', '#6b1ce0', '#FF5C87'],
            series: [
                {
                    rendering: function (s, e) {
                        s.binding = 'close';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1Cook1';
                        s.name = '1분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1Cook3';
                        s.name = '3분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1Cook5';
                        s.name = '5분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1Cook7';
                        s.name = '7분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1Cook10';
                        s.name = '10분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt1CookOver10';
                        s.name = '10분이상';
                    }
                }
            ],
            plotMargin: 'NaN 40 NaN 40' // top, right, bottom, left
        });
        var rangeSelector1 = new wijmo.chart.interaction.RangeSelector(theChartSelector1, {
            seamless: true,
            rangeChanged: function (s, e) {
                chart1.beginUpdate();
                chart1.axisX.min = s.min;
                chart1.axisX.max = s.max;
                chart1.endUpdate();
            }
        });

        chart2 = new wijmo.chart.FlexChart('#chart2', {
            itemsSource: getData(list),
            bindingX: "saleDate",
            chartType: parseInt(0),
            stacking: parseInt(1),
            tooltip: {content: "{seriesName} {value}건"},
            options: {groupWidth: "5%"},
            legend: {
                position: wijmo.chart.Position.Left
            },
            // series: [
            //     {binding: 'cntHh00', name: '제조시간'},
            //     {binding: '', name: '픽업시간'},
            // ],
            series: [
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2Pickup1';
                        s.name = '1분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2Pickup3';
                        s.name = '3분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2Pickup5';
                        s.name = '5분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2Pickup7';
                        s.name = '7분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2Pickup10';
                        s.name = '10분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2PickupOver10';
                        s.name = '10분이상';
                    }
                }
            ],
            palette: ['#0085c7', '#00ba8b', '#ff9d39', '#ae63ff', '#6b1ce0', '#FF5C87']
        });

        // if (getData(list).length === 1) {
        //     chart2.options = {groupWidth: "5%"};
        // }

        // create the chart with range selector
        theChartSelector2 = new wijmo.chart.FlexChart('#theChartSelector2', {
            itemsSource: getData(list),
            bindingX: 'saleDate',
            tooltip: {content: "{seriesName} {value}건"},
            chartType: wijmo.chart.ChartType.Area,
            legend: {
                position: wijmo.chart.Position.None
            },
            axisX: {
                position: wijmo.chart.Position.None
            },
            axisY: {
                position: wijmo.chart.Position.None
            },
            palette: ['#0085c7', '#00ba8b', '#ff9d39', '#ae63ff', '#6b1ce0', '#FF5C87'],
            series: [
                {
                    rendering: function (s, e) {
                        s.binding = 'close';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2Pickup1';
                        s.name = '1분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2Pickup3';
                        s.name = '3분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2Pickup5';
                        s.name = '5분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2Pickup7';
                        s.name = '7분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2Pickup10';
                        s.name = '10분미만';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'opt2PickupOver10';
                        s.name = '10분이상';
                    }
                }
            ],
            plotMargin: 'NaN 40 NaN 40' // top, right, bottom, left
        });
        var rangeSelector2 = new wijmo.chart.interaction.RangeSelector(theChartSelector2, {
            seamless: true,
            rangeChanged: function (s, e) {
                chart2.beginUpdate();
                chart2.axisX.min = s.min;
                chart2.axisX.max = s.max;
                chart2.endUpdate();
            }
        });
    }

    var list = [];
    // 조회
    $scope.$on("kdsServiceTimeList", function () {
        console.log($scope.valueCheck());
        if (!$scope.valueCheck()) {
            return false;
        }
        var params = {};
        params.kdsDayStartDate = dateToDaystring($scope.kdsDayStartDate).replaceAll('-', '');
        params.kdsDayEndDate = dateToDaystring($scope.kdsDayEndDate).replaceAll('-', '');
        params.makeDate = $scope.makeDate;
        params.makeDateSec = $scope.makeDateSec;
        params.picDate = $scope.picDate;
        params.picDateSec = $scope.picDateSec;
        params.storeCd = $("#regStoreCd").val();

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
        $http({
            method: 'POST', //방식
            url: "/kds/anals/chart/service/getKdsServiceTime.sb", /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (response.data.status === "OK") {
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
                    theChartSelector1.itemsSource = getData(list);
                }
                if (chart2 === '') {
                    chartList(list);
                } else {
                    chart2.itemsSource = getData(list);
                    theChartSelector2.itemsSource = getData(list);
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

    $scope.regStoreShow = function () {
        $scope._broadcast('regStoreCtrl');
    };

// 차트
    $scope.chartKds = function () {
        console.log("chart!!!");
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

// 체크
    $scope.valueCheck = function () {
        var msg = messages['kds.date.error'];
        var date1 = new Date($scope.kdsDayStartDate);
        var date2 = new Date($scope.kdsDayEndDate);
        var diffDay = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
        console.log("diffDay: ", diffDay);
        if (diffDay < 0) {
            $scope._popMsg(messages["kds.date.hour.error"]);
            return false;
        }
        if (diffDay > 30) {
            $scope._popMsg(msg);
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

        if ($("#resurceFg").val() === "HQ") {
            var msg = messages["kds.store"] + messages["cmm.require.text"];
            if (isNull($("#regStoreCd").val())) {
                $scope._popMsg(msg);
                return false;
            }
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
            }, 'KDS_Service_Time_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}])
;
