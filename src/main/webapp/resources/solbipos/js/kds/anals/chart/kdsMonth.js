/**
 * get application
 */
var app = agrid.getApp();
app.controller('kdsMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kdsMonthCtrl', $scope, $http, $timeout, true));

    $scope._getComboDataQuery('400', 'kdsMakeDate', '');
    $scope._getComboDataQuery('401', 'kdsPicDate', '');
    $scope._getComboDataQuery('402', 'kdsMakeDateSec', '');
    $scope._getComboDataQuery('403', 'kdsPicDateSec', '');
    $scope.picChecked = true;
    $scope.makeChecked = true;

    $scope._setComboData("rRegStoreCd", regstrStoreList);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    $scope.picChecked = true;
    $scope.makeChecked = true;

    // 검색조건에 조회기간
    var kdsDayStartDate = new wijmo.input.InputDate('#kdsDayStartDate', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var kdsDayEndDate = new wijmo.input.InputDate('#kdsDayEndDate', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

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
                if (col.binding === "saleMon") {
                    e.cell.innerHTML = e.cell.innerText.substr(0, 4)  + "-" + e.cell.innerText.substr(4, 2);
                }
            }
        });
    };

    function getData(dataList) {
        view = new wijmo.collections.CollectionView(dataList);
        return view;
    }

    var chart1 = ''

    function chartList(list) {
        // create the Pareto chart
        chart1 = new wijmo.chart.FlexChart('#chart1', {
            itemsSource: getData(list),
            bindingX: 'saleMon',
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
                content: '건수: {saleQty}',
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
    $scope.$on("kdsMonthList", function () {

        if (!$scope.valueCheck()) return false;

        var params = {};
        params.kdsDayStartDate = wijmo.Globalize.format(kdsDayStartDate.value, 'yyyyMM'); //조회기간
        params.kdsDayEndDate = wijmo.Globalize.format(kdsDayEndDate.value, 'yyyyMM'); //조회기간
        /*params.kdsDayStartDate = dateToDaystring($scope.kdsDayStartDate).replaceAll('-', '');
        params.kdsDayEndDate = dateToDaystring($scope.kdsDayEndDate).replaceAll('-', '');*/
        params.makeDate = $scope.makeDate;
        params.makeDateSec = $scope.makeDateSec;
        params.prodClassCd = $scope.prodClassCd;
        params.prodClassCdNm = $scope.prodClassCdNm;
        params.picDate = $scope.picDate;
        params.picDateSec = $scope.picDateSec;
        params.prodClassCd = $scope.prodClassCd;
        params.prodClassCdNm = $scope.prodClassCdNm;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.storeCd = $("#regStoreCd").val();
        params.orgnFg = $("#resurceFg").val();

        $scope.kdsSearch(params);
    });

    $scope.kdsSearch = function (params) {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');

        // 차트영역 보이도록
        $("#divChart").css("display", "");

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
            url: "/kds/anals/chart/month/getKdsMonth.sb", /* 통신할 URL */
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
                    $("#divChart").css("display", "none");

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
        // $scope._inquiryMain("/kds/anals/chart/dayProd/getKdsMonth.sb", params, function (res) {
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
        chart1.series = chart1.series.forEach(function(e, i)  {
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
        chart1.series = chart1.series.forEach(function(e, i)  {
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
        chart1.dataLabel.content = '건수: {saleQty}';
        chart1.dataLabel.position = 'Top';
    };

// 체크
    $scope.valueCheck = function () {

        var date1 = new Date(wijmo.Globalize.format(kdsDayStartDate.value, 'yyyy-MM-dd'));
        var date2 = new Date(wijmo.Globalize.format(kdsDayEndDate.value, 'yyyy-MM-dd'));
        date2.setFullYear(date2.getFullYear() -1)
        var diff = date2;
        if (diff > date1) {
            $scope._popMsg(messages['kds.date.year.error']);
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
            }, 'KDS_월별_상품별_' + today + '.xlsx', function () {
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

    // 상품분류정보 선택취소
    $scope.delProdClass = function () {
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

}])
;
