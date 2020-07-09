/**
 * get application
 */
var app = agrid.getApp();
app.controller('kdsDayCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kdsDayCtrl', $scope, $http, false));

    $scope._getComboDataQuery('400', 'kdsMakeDate', 'S');
    $scope._getComboDataQuery('401', 'picDate', 'S');
    $scope._getComboDataQuery('402', 'kdsMakeDateSec', 'S');
    $scope._getComboDataQuery('403', 'picDateSec', 'S');

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




        function getRandomPalette() {
            let palettes = Object.getOwnPropertyNames(wijmo.chart.Palettes)
                .filter(prop => typeof wijmo.chart.Palettes[prop] === "object" && prop !== 'prototype');
            let rand = Math.floor(Math.random() * palettes.length);
            //
            return wijmo.chart.Palettes[palettes[rand]];
        }

        function getData() {
            // some raw data
            var dataList = [
                { make: 'Vokswagen', sales: 2532435 },
                { make: 'Toyota', sales: 2338453 },
                { make: 'Renault-Nissan', sales: 2336543 },
                { make: 'GM', sales: 2252543 },
                { make: 'Hyundai-Kia', sales: 1553547 },
                { make: 'Ford', sales: 1521153 },
                { make: 'Suzuki', sales: 739123 },
                { make: 'PSA', sales: 682121 },
                { make: 'Daimler', sales: 440234 },
                { make: 'BMW Group', sales: 586121 },
                { make: 'Geely', sales: 410353 },
                { make: 'Mazda', sales: 389243 },
                { make: 'Chang\'an', sales: 389323 },
                { make: 'Subaru', sales: 230221 },
                { make: 'Tata Group', sales: 242123 }
            ];
            let view = new wijmo.collections.CollectionView(dataList);
            view.sortDescriptions.push(new wijmo.collections.SortDescription('sales', false));

            // add cumulative sales
            updateCumSales(view);

            // update cumulative sales when the data changes
            view.collectionChanged.addHandler(() => updateCumSales(view));

            // done
            return view;
        }

        function updateCumSales(view) {
            let totalSales = view.getAggregate(wijmo.Aggregate.Sum, 'sales'), cumSales = 0;
            //
            view.items.forEach((item) => {
                cumSales += item.sales;
                item.cumSales = cumSales / totalSales;
            });
        }




        var axisY2 = new wijmo.chart.Axis();
        axisY2.position = wijmo.chart.Position.Right;
        axisY2.title = 'Cumulative Sales';
        axisY2.format = 'p0';
        axisY2.min = 0;
        axisY2.axisLine = true;
        //
        // create the Pareto chart
        var chart1 = new wijmo.chart.FlexChart('#chart1', {
            itemsSource: getData(),
            chartType: 'Column',
            bindingX: 'make',
            axisY: {
                format: 'n0,',
                title: 'Sales (thousands)',
                axisLine: true
            },
            axisX: {
                labelAngle: -90
            },
            legend: {
                position: 'None'
            },
            series: [
                {
                    binding: 'sales',
                    name: 'Sales (thousands)'
                },
                {
                    binding: 'cumSales',
                    name: 'Cumulative Sales',
                    chartType: 'Line',
                    axisY: axisY2,
                    style: {
                        stroke: 'orange',
                        strokeWidth: 4
                    }
                }
            ],
            palette: getRandomPalette()
        });


    };

    // 조회
    $scope.$on("kdsDayList", function () {
        var params = {};
        params.periodStartDate = dateToDaystring($scope.periodStartDate).replaceAll('-', '');
        params.periodEndDate = dateToDaystring($scope.periodEndDate).replaceAll('-', '');
        params.listScale = $scope.listScale;
        $scope._inquiryMain("/kds/anals/day/view/getKdsDay.sb", params, function () {
        });
    });
}])
;
