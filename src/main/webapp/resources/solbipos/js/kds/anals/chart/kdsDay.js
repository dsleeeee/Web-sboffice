/**
 * get application
 */
var app = agrid.getApp();
app.controller('kdsDayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kdsDayCtrl', $scope, $http, $timeout, true));

    $scope._getComboDataQuery('400', 'kdsMakeDate', '');
    $scope._getComboDataQuery('401', 'kdsPicDate', '');
    $scope._getComboDataQuery('402', 'kdsMakeDateSec', '');
    $scope._getComboDataQuery('403', 'kdsPicDateSec', '');

    $scope._setComboData("rRegStoreCd", regstrStoreList);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    $scope.picChecked = true;
    $scope.makeChecked = true;

    // 검색조건에 조회기간
    var kdsDayStartDate = wcombo.genDateVal("#kdsDayStartDate", gvStartDate);
    var kdsDayEndDate = wcombo.genDateVal("#kdsDayEndDate", gvEndDate);

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
                if (col.binding === "saleDate") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText.substring(0, 8));
                }
            }
        });
    };

    function getData(dataList) {
        view = new wijmo.collections.CollectionView(dataList);
        return view;
    }

    var chart1 = '';

    function chartList(list) {
        // create the Pareto chart
        chart1 = new wijmo.chart.FlexChart('#chart1', {
            itemsSource: getData(list),
            bindingX: 'saleDate',
            axisY: {
                min: 0
            },
            legend: {
                position: wijmo.chart.Position.None
            },
            // series: [
            //     {binding: 'avgMake', name: '제조시간'},
            //     {binding: 'avgPic', name: '픽업시간'},
            //     {binding: 'avgMake', name: '제조시간Line', chartType: wijmo.chart.ChartType.Line},
            //     {binding: 'avgPic', name: '픽업시간Line', chartType: wijmo.chart.ChartType.Line}
            // ],
            series: [
                {
                    rendering: function (s, e) {
                        s.binding = 'avgMake';
                        s.name = '제조시간';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'avgPic';
                        s.name = '픽업시간';
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'avgMake';
                        s.name = '제조시간Line';
                        s.chartType = wijmo.chart.ChartType.Line;
                    }
                },
                {
                    rendering: function (s, e) {
                        s.binding = 'avgPic';
                        s.name = '픽업시간Line';
                        s.chartType = wijmo.chart.ChartType.Line;
                    }
                },
            ],
            dataLabel: {
                content: '건수: {orderCnt}',
                position: 'Top',
                rendering: function (s, e) {
                    if ($scope.picChecked && $scope.makeChecked) {
                        if (e.hitTestInfo.item.avgMake >= e.hitTestInfo.item.avgPic) {
                            if (e.hitTestInfo.name == '제조시간' || e.hitTestInfo.name == '픽업시간' || e.hitTestInfo.name == '픽업시간Line') {
                                e.text = '';
                            }
                        } else {
                            if (e.hitTestInfo.name == '제조시간' || e.hitTestInfo.name == '제조시간Line' || e.hitTestInfo.name == '픽업시간') {
                                e.text = '';
                            }
                        }
                    } else if (!$scope.picChecked && $scope.makeChecked) {
                        if (e.hitTestInfo.name == '픽업시간' || e.hitTestInfo.name == '픽업시간Line') {
                            e.text = '';
                        }
                    } else if ($scope.picChecked && !$scope.makeChecked) {
                        if (e.hitTestInfo.name == '제조시간' || e.hitTestInfo.name == '제조시간Line') {
                            e.text = '';
                        }
                    }
                }
            },
            palette: ['#ff9d39', '#00ba8b', '#ff9d39', '#00ba8b']
        });
    }

    var list = [];
    // 조회
    $scope.$on("kdsDayList", function () {
        if (!$scope.valueCheck()) {
            return false;
        }
        var params = {};
        params.kdsDayStartDate = wijmo.Globalize.format(kdsDayStartDate.value, 'yyyyMMdd'); //조회기간
        params.kdsDayEndDate = wijmo.Globalize.format(kdsDayEndDate.value, 'yyyyMMdd'); //조회기간
        //params.kdsDayStartDate = dateToDaystring($scope.kdsDayStartDate).replaceAll('-', '');
        //params.kdsDayEndDate = dateToDaystring($scope.kdsDayEndDate).replaceAll('-', '');
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
        
        // 차트영역 보이도록
        $("#divChart").css("visibility", "");

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

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $http({
            method: 'POST', //방식
            url: "/kds/anals/chart/day/getKdsDay.sb", /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (response.data.status === "OK") {
                list = response.data.data.list;
                if (list.length === undefined || list.length === 0) {
                    $scope.data = new wijmo.collections.CollectionView([]);
                    chart1.itemsSource = [];
                    if (true && response.data.message) {
                        $scope._popMsg(response.data.message);
                    }
                    
                    // 데이터가 없는경우 차트영역 숨기기
                    $("#divChart").css("visibility", "hidden");

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
    };

// 픽업시간
    $scope.picChkDt = function () {
        // getData(list);
        chart1.series = chart1.series.forEach(function(e, i) {
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
        chart1.series = chart1.series.forEach(function(e, i) {
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
        var date1 = new Date(wijmo.Globalize.format(kdsDayStartDate.value, 'yyyy-MM-dd'));
        var date2 = new Date(wijmo.Globalize.format(kdsDayEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
        if (diffDay > 30) {
            $scope._popMsg(msg);
            return false;
        }
        if (diffDay < 0) {
            $scope._popMsg(messages["kds.date.hour.error"]);
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

        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth()+1; //January is 0!
        var yyyy = today.getFullYear();

        if (dd < 10) {
            dd= '0' + dd;
        }

        if (mm < 10) {
            mm = '0' + mm;
        }

        today = String(yyyy) + String(mm) + dd;

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
            }, 'KDS_주문단위_일자별_' + today + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}])
;
