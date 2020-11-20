/**
 * get application
 */
var app = agrid.getApp();

app.controller('popDlvrInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('popDlvrInfoCtrl', $scope, $http, true));

    // 선택 회원
    $scope.selectedMember;
    $scope.setSelectedMember = function (member) {
        $scope.selectedMember = member;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        // $scope.saleFgDataMap = new wijmo.grid.DataMap(saleFgData, 'value', 'name'); //판매구분

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // //그리드 링크설정
        // // ReadOnly 효과설정
        // s.formatItem.addHandler(function (s, e) {
        //   if (e.panel === s.cells) {
        //     var col = s.columns[e.col];
        //     if (col.binding === "totSaleAmt" || col.binding === "membrNm") {
        //       wijmo.addClass(e.cell, 'wijLink');
        //     }
        //   }
        // });
    };

    // <-- 검색 호출 -->
    $scope.$on("popBillInfo", function (event, data) {
        $scope.searchBillInfo(data);
        event.preventDefault();
    });

    // 영수증 상세
    $scope.searchBillInfo = function (data) {
        console.log("영수증:: ", data);
        var params = data;

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/anals/dlvr/getBillInfo.sb", params, function (response) {
            console.log(response);
            $scope.searchModel = response.data.data;
            if ($scope.searchModel.payCd === "01") {
                $scope.searchModel.card = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "02") {
                $scope.searchModel.cash = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "03") {
                $scope.searchModel.payco = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "04") {
                $scope.searchModel.vpoint = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "05") {
                $scope.searchModel.vcoupn = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "06") {
                $scope.searchModel.vcharge = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "07") {
                $scope.searchModel.mpay = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "08") {
                $scope.searchModel.mcoupn = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "09") {
                $scope.searchModel.membr = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "10") {
                $scope.searchModel.prepaid = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "11") {
                $scope.searchModel.postpaid = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "12") {
                $scope.searchModel.coupn = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "13") {
                $scope.searchModel.gift = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "14") {
                $scope.searchModel.fstmp = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "15") {
                $scope.searchModel.partner = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "16") {
                $scope.searchModel.okcsb = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "17") {
                $scope.searchModel.empCard = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "18") {
                $scope.searchModel.temporary = $scope.searchModel.payAmt
            } else if ($scope.searchModel.payCd === "19") {
                $scope.searchModel.smartOrder = $scope.searchModel.payAmt
            }
            $scope._postJSONQuery.withOutPopUp("/dlvr/manage/anals/dlvr/getBillInfoList.sb", params, function (result) {
                console.log(result);
                $scope.data = new wijmo.collections.CollectionView(result.data.data.list);
            });
        });
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1; //January is 0!
        var yyyy = today.getFullYear();

        if (dd < 10) {
            dd = '0' + dd;
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
            }, '배달내역_영수증상품내역_' + today + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };
}]);