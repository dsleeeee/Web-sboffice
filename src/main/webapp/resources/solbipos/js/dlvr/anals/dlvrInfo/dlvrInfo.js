/****************************************************************
 *
 * 파일명 : dlvrInfo.js
 * 설  명 : 배달 내역JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.07.09     Joshua      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일자별회원 구매내역 그리드 생성
 */
app.controller('dlvrInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrInfoCtrl', $scope, $http, true));
    /*
    // 접속사용자의 권한(H : 본사, S: 매장)
    $scope.orgnFg = gvOrgnFg;

    // 매장권한으로 로그인 한 경우, 본인매장만 내역 조회가능.
    if($scope.orgnFg === 'S') {
       $scope.storeCds = gvStoreCd;
    }
   */
    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

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


        // 그리드 포맷
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "billNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
                if(col.binding === "saleDate"){
                    e.cell.innerHTML = getFormatDate(e.cell.innerText.substring(0, 8));
                }
                if(col.binding === "billDt"){
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "billNo") {
                    $scope.setSelectedMember(selectedRow);
                    $scope._broadcast('popBillInfo', selectedRow);
                    $scope.wjDlvrInfoLayer.show(true);
                }
            }
        });

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
    $scope.$on("dlvrInfoCtrl", function (event, data) {
        $scope.searchDlvrInfo();
        event.preventDefault();
    });

    // 일자별회원 구매내역 그리드 조회
    $scope.searchDlvrInfo = function () {

        var params = {};
        // params.membrNm = $scope.membrNm;
        params.empNm = $scope.dlvrNm;
        params.dlvrPayEmpNm = $scope.collectNm;
        params.listScale = $scope.listScale;
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간

        $scope._inquiryMain("/dlvr/manage/anals/dlvr/getDlvrInfoList.sb", params, function () {
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
            }, '배달내역_' + today + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);