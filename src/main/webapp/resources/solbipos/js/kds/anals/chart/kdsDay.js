/**
 * get application
 */
var app = agrid.getApp();
app.controller('kdsDayCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kdsDayCtrl', $scope, $http, false));

    $scope._getComboDataQuery('400', 'kdsMakeDate', 'S');
    $scope._getComboDataQuery('401', 'kdsPicDate', 'S');
    $scope._getComboDataQuery('402', 'kdsMakeDateSec', 'S');
    $scope._getComboDataQuery('403', 'kdsPicDateSec', 'S');

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

    $scope.initGrid = function (s, e) {
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "dlvrAddr") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        function getData() {
            // some raw data
            var dataList =
                [
                    // {DATE: '2020-07-10', 'CNT': 3000, 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    // {DATE: '2020-07-11', 'CNT': 3000, 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    // {DATE: '2020-07-10', 'CNT': 3000, 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    // {DATE: '2020-07-11', 'CNT': 3000, 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    // {DATE: '2020-07-10', 'CNT': 3000, 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    // {DATE: '2020-07-11', 'CNT': 3000, 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    // {DATE: '2020-07-10', 'CNT': 3000, 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    // {DATE: '2020-07-11', 'CNT': 3000, 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    // {DATE: '2020-07-10', 'CNT': 3000, 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    // {DATE: '2020-07-11', 'CNT': 3000, 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    // {DATE: '2020-07-10', 'CNT': 3000, 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    // {DATE: '2020-07-11', 'CNT': 3000, 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    // {DATE: '2020-07-10', 'CNT': 3000, 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    // {DATE: '2020-07-11', 'CNT': 3000, 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    // {DATE: '2020-07-10', 'CNT': 3000, 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    // {DATE: '2020-07-11', 'CNT': 3000, 'CREATE_TIME': 31, 'PICK_TIME': 28}


                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28},
                    {DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30},
                    {DATE: '2020-07-11', 'CNT': '건수 7', 'CREATE_TIME': 31, 'PICK_TIME': 28}


                ];

            let view = new wijmo.collections.CollectionView(dataList);
            // view.sortDescriptions.push(new wijmo.collections.SortDescription('CREATE_TIME', false));

            // add cumulative sales
            // updateCumSales(view);

            // update cumulative sales when the data changes
            // view.collectionChanged.addHandler(() => updateCumSales(view));

            // done
            return view;
        }

        // function updateCumSales(view) {
        //     let totalSales = view.getAggregate(wijmo.Aggregate.Sum, 'CREATE_TIME'), cumCreateTime = 0;
        //     //
        //     view.items.forEach((item) => {
        //         cumCreateTime += item.CREATE_TIME;
        //         item.cumCreateTime = cumCreateTime / totalSales;
        //     });
        // }


        // var axisY2 = new wijmo.chart.Axis();
        // axisY2.position = wijmo.chart.Position.Right;
        // axisY2.title = 'Cumulative Sales';
        // axisY2.format = 'p0';
        // axisY2.min = 0;
        // axisY2.axisLine = true;
        //
        // create the Pareto chart
        var chart1 = new wijmo.chart.FlexChart('#chart1', {
            itemsSource: getData(),
            // chartType: 'Column',
            bindingX: 'DATE',
            // axisY: {
            //     format: 'n0,',
            //     title: 'Sales (thousands)',
            //     axisLine: true
            // },
            legend: {
                position: 'None'
            },
            // { DATE: '2020-07-10', 'CNT': '건수 5', 'CREATE_TIME': 35, 'PICK_TIME': 30 },
            series: [
                {
                    binding: 'CREATE_TIME',
                    name: '제조시간'
                },
                {
                    binding: 'PICK_TIME',
                    name: '픽업시간'
                },
                {
                    binding: 'CREATE_TIME',
                    name: '제조시간Line',
                    chartType: wijmo.chart.ChartType.Line
                },
                {
                    binding: 'PICK_TIME',
                    name: '픽업시간Line',
                    chartType: wijmo.chart.ChartType.Line
                }
            ],
            dataLabel: {
                // content: '{seriesName}:{y}',
                content: '{CNT}',
                position: 'Top',
                rendering: function (s, e) {
                    console.log("s: ", s);
                    console.log("e: ", e);
                    if(e.hitTestInfo.name == '제조시간' || e.hitTestInfo.name == '제조시간Line' || e.hitTestInfo.name == '픽업시간'){
                        e.text = '';
                    }
                },
            },
            palette: ['#ff9d39', '#00ba8b', '#ff9d39', '#00ba8b']
        });


    };

    // 조회
    $scope.$on("kdsDayList", function () {
        var date1 = new Date($scope.kdsDayStartDate);
        var date2 = new Date($scope.kdsDayEndDate);
        var diffDay = (date2.getTime() - date1.getTime()) / (1000*60*60*24);
        if(diffDay > 30){
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
        params.regStoreCd = $("#regStoreCd").val();
        $scope._inquiryMain("/kds/anals/day/view/getKdsDay.sb", params, function () {
        });
    });

    $scope.regStoreShow = function () {
        $scope._broadcast('regStoreCtrl');
    };
}])
;
