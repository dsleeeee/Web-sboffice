/****************************************************************
 *
 * 파일명 : incln.js
 * 설  명 : 회원 구매성향 분석 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.10.12    Daniel      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원 구매성향 분석 그리드 생성
 */
app.controller('inclnCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('inclnCtrl', $scope, $http, true));

    //groupRow 접고 펼치기 flag 변수
    $scope.setCollapsed = false;

    // <-- 검색 호출 -->
    $scope.$on("inclnCtrl", function (event, data) {
        $scope.searchInclnList();
        // 기능수행 종료
        event.preventDefault();
    });

    $scope.searchInclnList = function () {

        $scope.setCollapsed = false;

        // 파라미터
        var params = {};
        params.option = $(":input:radio[name=searchOption]:checked").val();
        params.startDate = wijmo.Globalize.format($scope.periodStartDate, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.periodEndDate, 'yyyyMMdd');

        $scope.params = params;
        console.log("params: ", params);

        if (params.startDate.replace(/-/gi, "") > params.endDate.replace(/-/gi, "")) {
            $scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
            return false;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/membr/anals/incln/incln/getInclnList.sb", params, function () {}, false);

        //create a group to show the grand totals
        var grpLv1 = new wijmo.collections.PropertyGroupDescription('전체');
        var grpLv2 = new wijmo.collections.PropertyGroupDescription('level1Nm');
        var grpLv3 = new wijmo.collections.PropertyGroupDescription('level2Nm');

        var theGrid = new wijmo.Control.getControl('#inclnGrid');

        theGrid.itemsSource = new wijmo.collections.CollectionView();
        // custom cell calculation
        theGrid.formatItem.addHandler(function (s, e) {

            var lengthTemp = s.collectionView.groupDescriptions.length;

            if (lengthTemp < 3) {
                s.collectionView.groupDescriptions.push(grpLv1);
                s.collectionView.groupDescriptions.push(grpLv2);
                s.collectionView.groupDescriptions.push(grpLv3);
            }

            s.rows.forEach(function (row) {
                if (row instanceof wijmo.grid.GroupRow) {
                    var groupProp = row.dataItem.groupDescription.propertyName;
                    var className = null;
                    switch (groupProp) {
                        case "전체":
                            className = "grp-lv-1";
                            break;
                        case "level1Nm":
                            className = "grp-lv-2";
                            break;
                        case "level2Nm":
                            className = "grp-lv-3";
                            break;
                    }
                    if (className) {
                        row.cssClass = className;
                    }
                    // 3단계 group row 접기
                    if (row.level == 2) {
                        if (!$scope.setCollapsed) {
                            row.isCollapsed = true;
                        }
                    }
                }
            });

            // if (e.panel === s.cells) {
            //     var col = s.columns[e.col];
            //     if (col.binding === "level4Nm") { // 4단계 분류
            //         wijmo.addClass(e.cell, 'wijLink');
            //         // wijmo.addClass(e.cell, 'wj-align-right');
            //         // wijmo.addClass(e.cell, 'wj-align-center');
            //     }
            // }
        });
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 'AllHeaders';
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.level1Nm = messages["incln.prodClassNm"];
        dataItem.level2Nm = messages["incln.prodClassNm"];
        dataItem.level3Nm = messages["incln.prodClassNm"];
        dataItem.sumSale = messages["incln.sumSale"];
        dataItem.sumGendrF = messages["incln.gendrFg"];
        dataItem.sumGendrM = messages["incln.gendrFg"];
        dataItem.sumGendrNon = messages["incln.gendrFg"];
        dataItem.sumAge10 = messages["incln.ageGroup"];
        dataItem.sumAge20 = messages["incln.ageGroup"];
        dataItem.sumAge30 = messages["incln.ageGroup"];
        dataItem.sumAge40 = messages["incln.ageGroup"];
        dataItem.sumAge50 = messages["incln.ageGroup"];
        dataItem.sumAge60 = messages["incln.ageGroup"];

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
        };

        s.refresh();

        // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
                var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
                if (rng && rng.columnSpan > 1) {
                    e.preventDefault();
                }
            }

            if (ht.cellType === wijmo.grid.CellType.Cell) {
                // var col = ht.panel.columns[ht.col];
                // var selectedRow = s.rows[ht.row].dataItem;

                // groupRow 펼치기
                if (s.rows[ht.row].level == 2) { // 3단계 분류
                    $scope.setCollapsed = true;
                    // var isCollapsed = s.rows[ht.row].isCollapsed;
                    s.rows[ht.row].isCollapsed ? false : true;
                }
            }

            // 클릭시 상세 그리드 조회
            // if ((s.rows[ht.row].level !== 1 && s.rows[ht.row].level !== 2)) {
            //     if (col.binding === "lv1Nm" || col.binding === "lv2Nm" || col.binding === "lv3Nm") { // 3단계 분류
            //         // if (col.binding === "lv3Nm") { // 3단계 분류
            //         //     console.log(params);
            //         //     $scope.$broadcast('versusPeriodClassDtlCtrlSrch', params);
            //         // }
            //     }
            // }
        }, true);
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {
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
            }, '회원구매성향분석_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);