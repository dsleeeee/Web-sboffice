/**
 * get application
 */
var app = agrid.getApp();
app.controller('kdsDayProdTimeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kdsDayProdTimeCtrl', $scope, $http, $timeout, true));
    $scope._getComboDataQuery('400', 'kdsMakeDate', '');
    $scope._getComboDataQuery('401', 'kdsPicDate', '');
    $scope._getComboDataQuery('402', 'kdsMakeDateSec', '');
    $scope._getComboDataQuery('403', 'kdsPicDateSec', '');
    $scope.picChecked = true;
    $scope.makeChecked = true;

    // 검색조건에 조회기간
    var kdsDayStartDate = wcombo.genDateVal("#kdsDayStartDate", gvStartDate);
    var kdsDayEndDate = wcombo.genDateVal("#kdsDayEndDate", gvEndDate);

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

    $scope.kdsTimeZone = [
        {name: '00시', value: '00'},
        {name: '01시', value: '01'},
        {name: '02시', value: '02'},
        {name: '03시', value: '03'},
        {name: '04시', value: '04'},
        {name: '05시', value: '05'},
        {name: '06시', value: '06'},
        {name: '07시', value: '07'},
        {name: '08시', value: '08'},
        {name: '09시', value: '09'},
        {name: '10시', value: '10'},
        {name: '11시', value: '11'},
        {name: '12시', value: '12'},
        {name: '13시', value: '13'},
        {name: '14시', value: '14'},
        {name: '15시', value: '15'},
        {name: '16시', value: '16'},
        {name: '17시', value: '17'},
        {name: '18시', value: '18'},
        {name: '19시', value: '19'},
        {name: '20시', value: '20'},
        {name: '21시', value: '21'},
        {name: '22시', value: '22'},
        {name: '23시', value: '23'}
    ];
    $scope.kdsTimeZone2 = ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'];

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
    $scope.timeZoneSec = "23";
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
        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};

        dataItem.storeCd = messages["kds.storeCd"];
        dataItem.storeNm = messages["kds.storeNm"];
        dataItem.saleDate = messages["kds.saleDate"];
        dataItem.totalOrderCnt = messages["kds.totalOrderCnt"];
        dataItem.avgMakeDate = messages["kds.avgMakeDate"];
        dataItem.avgPicDate = messages["kds.avgPicDate"];
        dataItem.qtyHh00 = messages["kds.time.00"];
        dataItem.qtyHh01 = messages["kds.time.01"];
        dataItem.qtyHh02 = messages["kds.time.02"];
        dataItem.qtyHh03 = messages["kds.time.03"];
        dataItem.qtyHh04 = messages["kds.time.04"];
        dataItem.qtyHh05 = messages["kds.time.05"];
        dataItem.qtyHh06 = messages["kds.time.06"];
        dataItem.qtyHh07 = messages["kds.time.07"];
        dataItem.qtyHh08 = messages["kds.time.08"];
        dataItem.qtyHh09 = messages["kds.time.09"];
        dataItem.qtyHh10 = messages["kds.time.10"];
        dataItem.qtyHh11 = messages["kds.time.11"];
        dataItem.qtyHh12 = messages["kds.time.12"];
        dataItem.qtyHh13 = messages["kds.time.13"];
        dataItem.qtyHh14 = messages["kds.time.14"];
        dataItem.qtyHh15 = messages["kds.time.15"];
        dataItem.qtyHh16 = messages["kds.time.16"];
        dataItem.qtyHh17 = messages["kds.time.17"];
        dataItem.qtyHh18 = messages["kds.time.18"];
        dataItem.qtyHh19 = messages["kds.time.19"];
        dataItem.qtyHh20 = messages["kds.time.20"];
        dataItem.qtyHh21 = messages["kds.time.21"];
        dataItem.qtyHh22 = messages["kds.time.22"];
        dataItem.qtyHh23 = messages["kds.time.23"];

        dataItem.makeHh00 = messages["kds.time.00"];
        dataItem.makeHh01 = messages["kds.time.01"];
        dataItem.makeHh02 = messages["kds.time.02"];
        dataItem.makeHh03 = messages["kds.time.03"];
        dataItem.makeHh04 = messages["kds.time.04"];
        dataItem.makeHh05 = messages["kds.time.05"];
        dataItem.makeHh06 = messages["kds.time.06"];
        dataItem.makeHh07 = messages["kds.time.07"];
        dataItem.makeHh08 = messages["kds.time.08"];
        dataItem.makeHh09 = messages["kds.time.09"];
        dataItem.makeHh10 = messages["kds.time.10"];
        dataItem.makeHh11 = messages["kds.time.11"];
        dataItem.makeHh12 = messages["kds.time.12"];
        dataItem.makeHh13 = messages["kds.time.13"];
        dataItem.makeHh14 = messages["kds.time.14"];
        dataItem.makeHh15 = messages["kds.time.15"];
        dataItem.makeHh16 = messages["kds.time.16"];
        dataItem.makeHh17 = messages["kds.time.17"];
        dataItem.makeHh18 = messages["kds.time.18"];
        dataItem.makeHh19 = messages["kds.time.19"];
        dataItem.makeHh20 = messages["kds.time.20"];
        dataItem.makeHh21 = messages["kds.time.21"];
        dataItem.makeHh22 = messages["kds.time.22"];
        dataItem.makeHh23 = messages["kds.time.23"];

        dataItem.picHh00 = messages["kds.time.00"];
        dataItem.picHh01 = messages["kds.time.01"];
        dataItem.picHh02 = messages["kds.time.02"];
        dataItem.picHh03 = messages["kds.time.03"];
        dataItem.picHh04 = messages["kds.time.04"];
        dataItem.picHh05 = messages["kds.time.05"];
        dataItem.picHh06 = messages["kds.time.06"];
        dataItem.picHh07 = messages["kds.time.07"];
        dataItem.picHh08 = messages["kds.time.08"];
        dataItem.picHh09 = messages["kds.time.09"];
        dataItem.picHh10 = messages["kds.time.10"];
        dataItem.picHh11 = messages["kds.time.11"];
        dataItem.picHh12 = messages["kds.time.12"];
        dataItem.picHh13 = messages["kds.time.13"];
        dataItem.picHh14 = messages["kds.time.14"];
        dataItem.picHh15 = messages["kds.time.15"];
        dataItem.picHh16 = messages["kds.time.16"];
        dataItem.picHh17 = messages["kds.time.17"];
        dataItem.picHh18 = messages["kds.time.18"];
        dataItem.picHh19 = messages["kds.time.19"];
        dataItem.picHh20 = messages["kds.time.20"];
        dataItem.picHh21 = messages["kds.time.21"];
        dataItem.picHh22 = messages["kds.time.22"];
        dataItem.picHh23 = messages["kds.time.23"];


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

    function getData(dataList) {
        // dataList.forEach((e, i)=> {
        //     console.log(e);
        // })
        view = new wijmo.collections.CollectionView(dataList);
        return view;
    }

    var chart1 = ''

    function chartList(list) {
        // create the Pareto chart
        chart1 = new wijmo.chart.FlexChart('#chart1', {
            itemsSource: getData(list),
            bindingX: "saleHh",
            axisY: {
                min: 0
            },
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
                content: '수량: {saleQty}',
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
    };

    var list = [];
    // 조회
    $scope.$on("kdsDayProdTimeList", function () {
        var date1 = new Date(wijmo.Globalize.format(kdsDayStartDate.value, 'yyyy-MM-dd'));
        var date2 = new Date(wijmo.Globalize.format(kdsDayEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
        if (diffDay > 30) {
            $scope._popMsg(messages['kds.date.error']);
            return false;
        }

        if ($scope.timeZone > $scope.timeZoneSec) {
            $scope._popMsg(messages['kds.date.hour.error']);
            return false;
        }
        if (!$scope.valueCheck()) return false;

        var params = {};
        params.kdsDayStartDate = wijmo.Globalize.format(kdsDayStartDate.value, 'yyyyMMdd'); //조회기간
        params.kdsDayEndDate = wijmo.Globalize.format(kdsDayEndDate.value, 'yyyyMMdd'); //조회기간

        // params.kdsDayStartDate = dateToDaystring($scope.kdsDayStartDate).replaceAll('-', '');
        // params.kdsDayEndDate = dateToDaystring($scope.kdsDayEndDate).replaceAll('-', '');
        params.makeDate = $scope.makeDate;
        params.makeDateSec = $scope.makeDateSec;
        params.picDate = $scope.picDate;
        params.picDateSec = $scope.picDateSec;
        params.startHh = $scope.timeZone;
        params.endHh = $scope.timeZoneSec;
        params.kdsTimeList = $scope.kdsTimeZone2;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        if ($("#regStoreCd").val()) {
            params.storeCd = $("#regStoreCd").val();
        }
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
            url: "/kds/anals/chart/dayProdTime/getKdsDayProdTime.sb", /* 통신할 URL */
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

                // 시간대 조회조건에 따라 리스트에서 보여지는 시간대 조정
                var grid = wijmo.Control.getControl("#wjGridList");
                var columns = grid.columns;
                var start = 0;
                var end = 0;

                start =  3 * parseInt($scope.timeZone);
                end = 3 * parseInt($scope.timeZoneSec);

                if(orgnFg === 'HQ') {
                    start = start + 3;
                    end = end + 5;

                    for(var i = 3; i <= 74; i++){
                        if(i >= start && i <= end){
                            columns[i].visible = true;
                        }else{
                            columns[i].visible = false;
                        }
                    }

                }else{
                    start = start + 1;
                    end = end + 3;

                    for(var i = 1; i <= 72; i++){
                        if(i >= start && i <= end){
                            columns[i].visible = true;
                        }else{
                            columns[i].visible = false;
                        }
                    }
                }
                
                // 차트 조회
                $scope.chartKds();
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

        if (!$scope.valueCheck()) return false;

        var params = {};
        params.kdsDayStartDate = wijmo.Globalize.format(kdsDayStartDate.value, 'yyyyMMdd'); //조회기간
        params.kdsDayEndDate = wijmo.Globalize.format(kdsDayEndDate.value, 'yyyyMMdd'); //조회기간
        // params.kdsDayStartDate = dateToDaystring($scope.kdsDayStartDate).replaceAll('-', '');
        // params.kdsDayEndDate = dateToDaystring($scope.kdsDayEndDate).replaceAll('-', '');
        params.makeDate = $scope.makeDate;
        params.makeDateSec = $scope.makeDateSec;
        params.picDate = $scope.picDate;
        params.picDateSec = $scope.picDateSec;
        params.startHh = $scope.timeZone;
        params.endHh = $scope.timeZoneSec;
        params.kdsTimeList = $scope.kdsTimeZone2;
        params.storeCd = $("#regStoreCd").val();
        params.orgnFg = $("#resurceFg").val();

        // 가상로그인 세션정보
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');
        $http({
            method: 'POST', //방식
            url: "/kds/anals/chart/dayProdTime/getKdsDayProdTimeChart.sb", /* 통신할 URL */
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
        chart1.series = chart1.series.forEach(function (e, i) {
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
        chart1.series = chart1.series.forEach(function (e, i) {
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

    $scope.regStoreShow = function () {
        $scope._broadcast('regStoreCtrl');
    };

    // 체크
    $scope.valueCheck = function () {
        var date1 = new Date(wijmo.Globalize.format(kdsDayStartDate.value, 'yyyy-MM-dd'));
        var date2 = new Date(wijmo.Globalize.format(kdsDayEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
        if (diffDay > 30) {
            $scope._popMsg(messages['kds.date.error']);
            return false;
        }
        if (diffDay < 0) {
            $scope._popMsg(messages["kds.date.hour.error"]);
            return false;
        }

        if ($scope.timeZone > $scope.timeZoneSec) {
            $scope._popMsg(messages['kds.date.hour.error']);
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

        /*if (isNull($scope.prodClassCd)) {
            var msg = messages["kds.prodCd"] + messages["cmm.require.text"];
            if (isNull($scope.prodCd)) {
                $scope._popMsg(msg);
                return false;
            }
        }

        if (isNull($scope.prodCd)) {
            var msg = messages["kds.prodClassNm"] + messages["cmm.require.text"];
            if (isNull($scope.prodClassCd)) {
                $scope._popMsg(msg);
                return false;
            }
        }*/

        if ($("#resurceFg").val() === "HQ") {
            var msg = messages["kds.store"] + messages["cmm.require.text"];
            if (isNull($("#regStoreCd").val())) {
                $scope._popMsg(msg);
                return false;
            }
        }
        return true;
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
    }

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
            }, 'KDS_일_상품_시간대별_' + today + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}])
;
