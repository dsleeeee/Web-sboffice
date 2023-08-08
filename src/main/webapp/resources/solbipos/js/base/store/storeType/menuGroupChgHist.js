/****************************************************************
 *
 * 파일명 : menuGroupChgHist.js
 * 설  명 : 매장타입관리 - 메뉴그룹변경이력 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.11.18     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */

var app = agrid.getApp();

// 매장타입관리-매장타입변경이력
app.controller('menuGroupChgHistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('menuGroupChgHistCtrl', $scope, $http, true));

    $scope._setComboData("mgchProcFg", procFgData);

    // 변경일자 셋팅
    var startDate = wcombo.genDateVal("#mgchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#mgchEndDate", gvEndDate);

    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name'); // 사용여부
        $scope.procFgDataMap = new wijmo.grid.DataMap(procFgData, 'value', 'name'); // 입력구분

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.storeGroupCd = messages["storeType.storeGroupCd"];
        dataItem.storeGroupNm  = messages["storeType.menuGroupNm"];
        dataItem.useYn = messages["storeType.storeTypeCur"];
        dataItem.remark = messages["storeType.storeTypeCur"];
        dataItem.bUseYn  = messages["storeType.storeTypeB"];
        dataItem.bRemark = messages["storeType.storeTypeB"];
        dataItem.procFg = messages["storeType.procFg"];
        dataItem.procDt = messages["storeType.procDt"];
        dataItem.modId = messages["storeType.modId"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging    = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display    : 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display      : 'table-cell',
                    verticalAlign: 'middle',
                    textAlign    : 'center'
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

    $scope.$on("menuGroupChgHistCtrl", function(event, data) {
        // 메뉴그룹변경이력 조회
        $scope.srchMenuGroupChgHist();
        event.preventDefault();
    });

    // 메뉴그룹변경이력 조회
    $scope.srchMenuGroupChgHist =  function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 3달(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.storeGroupCd = $("#mgchMenuGroupCd").val();
        params.storeGroupNm = $("#mgchMenuGroupNm").val();
        params.procFg = $scope.mgchProcFgCombo.selectedValue;

        $scope._inquiryMain('/base/store/storeType/storeType/getMenuGroupChgHist.sb', params);
    };

    // 엑셀다운로드
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
            }, '메뉴그룹변경이력_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);