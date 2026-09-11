/****************************************************************
 *
 * 파일명 : storeTimeSaleBenson.js
 * 설  명 : (벤슨) 매출조회 > 지정가맹점_시간대별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.10     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 시 VALUE (00~23)
var Hh = [24];
for (i = 0; i < 24; i++) {
    var timeVal = i.toString();
    if (i >= 0 && i <= 9) {
        timeVal = "0" + timeVal;
    }
    Hh[i] = {"name": timeVal, "value": timeVal}
}

/**
 *  지정가맹점_시간대별 그리드 생성
 */
app.controller('storeTimeSaleBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeTimeSaleBensonCtrl', $scope, $http, false));

    // 검색조건에 조회기간 (기본 오늘~오늘)
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 조회시간 콤보박스 셋팅 (기본 00~23)
    $scope._setComboData("startTimeCombo", Hh);
    $scope._setComboData("endTimeCombo", Hh);
    $scope.startTime = "00";
    $scope.endTime = "23";

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 매출시간 컬럼 가운데정렬/회색톤 표시
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "saleTime1" || col.binding === "saleTime2") {
                    // 선택(파란배경) 시에도 글씨가 보이도록 인라인 배경 대신 공용 readonly 클래스 사용
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                    e.cell.style.textAlign = "center";
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("storeTimeSaleBensonCtrl", function (event, data) {
        $scope.searchStoreTimeSaleBensonList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회조건 유효성 체크
    $scope.chkSearchCondition = function () {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 31일(1달) 제한
        if (diffDay > 30) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 매장선택 필수 체크
        var srchStoreCd = $("#storeTimeSaleBensonStoreCd").val();
        if (srchStoreCd === undefined || srchStoreCd === null || srchStoreCd === "") {
            $scope._popMsg(messages['cmm.require.selectStore']); // 매장을 선택해 주세요.
            return false;
        }

        // 검색 시작 시간대가 종료 시간대보다 큰지 확인
        if (parseInt($scope.startTime, 10) > parseInt($scope.endTime, 10)) {
            $scope._popMsg(messages['storeTimeSaleBenson.startEnd']);
            return false;
        }

        return true;
    };

    $scope.searchStoreTimeSaleBensonList = function () {
        // 조회조건 유효성 체크
        if (!$scope.chkSearchCondition()) {
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.startTime = $scope.startTime;
        params.endTime = $scope.endTime;
        params.storeCd = $("#storeTimeSaleBensonStoreCd").val();

        // 가상로그인 대응한 session id 설정
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/benson/storeTimeSaleBenson/storeTimeSaleBenson/getStoreTimeSaleBensonList.sb", params, function (response) {
            var list = response.data.list;
            if (list === undefined || list === null) {
                list = [];
            }

            // 조회 시간범위 프레임 생성(값 없으면 0) — 범위 밖 시간대는 행에서 제외
            var startH = parseInt(params.startTime, 10);
            var endH = parseInt(params.endTime, 10);
            var frame = {};
            var hours = [];
            for (var h = startH; h <= endH; h++) {
                hours.push(h);
                frame[h] = {realSaleAmt: 0, billCnt: 0, guestCnt: 0, billUprc: 0, guestUprc: 0};
            }

            // 조회된 시간대 rows 를 프레임에 매핑
            for (var i = 0; i < list.length; i++) {
                var hour = parseInt(list[i].saleHour, 10);
                if (isNaN(hour) || frame[hour] === undefined) {
                    continue;
                }
                var realSaleAmt = Number(list[i].realSaleAmt) || 0;
                var billCnt = Number(list[i].billCnt) || 0;
                var guestCnt = Number(list[i].guestCnt) || 0;

                frame[hour].realSaleAmt = realSaleAmt;
                frame[hour].billCnt = billCnt;
                frame[hour].guestCnt = guestCnt;
                // 영수단가 = 순매출/영수건수, 객단가 = 순매출/고객수 (분모 0이면 0, 반올림 정수)
                frame[hour].billUprc = billCnt === 0 ? 0 : Math.round(realSaleAmt / billCnt);
                frame[hour].guestUprc = guestCnt === 0 ? 0 : Math.round(realSaleAmt / guestCnt);
            }

            // 조회범위를 절반으로 나눠 2단 조립(앞 절반 왼쪽 / 뒤 절반 오른쪽, 홀수면 왼쪽이 1행 더)
            var leftCount = Math.ceil(hours.length / 2);
            var rows = [];
            for (var r = 0; r < leftCount; r++) {
                var lh = hours[r];
                var rh = hours[r + leftCount]; // 없으면 undefined(빈칸)
                var left = frame[lh];
                var right = rh === undefined ? null : frame[rh];
                rows.push({
                    saleTime1: lh + ":00 - " + lh + ":59",
                    realSaleAmt1: left.realSaleAmt,
                    billCnt1: left.billCnt,
                    billUprc1: left.billUprc,
                    guestCnt1: left.guestCnt,
                    guestUprc1: left.guestUprc,
                    saleTime2: right === null ? "" : rh + ":00 - " + rh + ":59",
                    realSaleAmt2: right === null ? null : right.realSaleAmt,
                    billCnt2: right === null ? null : right.billCnt,
                    billUprc2: right === null ? null : right.billUprc,
                    guestCnt2: right === null ? null : right.guestCnt,
                    guestUprc2: right === null ? null : right.guestUprc
                });
            }

            var data = new wijmo.collections.CollectionView(rows);
            data.trackChanges = true;
            $scope.data = data;

            var grid = $scope.flex;
            grid.itemsSource = data;

            // 하단 합계 : 조회범위 합계 재계산 후 오른쪽 단 컬럼 위치에 표시
            var totRealSaleAmt = 0;
            var totBillCnt = 0;
            var totGuestCnt = 0;
            for (var h2 = 0; h2 < hours.length; h2++) {
                totRealSaleAmt += frame[hours[h2]].realSaleAmt;
                totBillCnt += frame[hours[h2]].billCnt;
                totGuestCnt += frame[hours[h2]].guestCnt;
            }
            // 합계 영수단가 = 총순매출/총영수건수, 합계 객단가 = 총순매출/총고객수 (단순 Sum 아님)
            var totBillUprc = totBillCnt === 0 ? 0 : Math.round(totRealSaleAmt / totBillCnt);
            var totGuestUprc = totGuestCnt === 0 ? 0 : Math.round(totRealSaleAmt / totGuestCnt);

            grid.columnFooters.setCellData(0, 0, '합계');    // 왼쪽 첫 칸 합계 라벨
            grid.columnFooters.setCellData(0, 7, totRealSaleAmt); // 순매출2
            grid.columnFooters.setCellData(0, 8, totBillCnt);     // 영수건수2
            grid.columnFooters.setCellData(0, 9, totBillUprc);    // 영수단가2(재계산)
            grid.columnFooters.setCellData(0, 10, totGuestCnt);   // 고객수2
            grid.columnFooters.setCellData(0, 11, totGuestUprc);  // 객단가2(재계산)
        });
    };
    // <-- //검색 호출 -->

    // 현재화면 엑셀다운로드
    $scope.excelDownload2 = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                "지정가맹점_시간대별_" + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format(endDate.value, 'yyyyMMdd') + '_' + getCurDateTime() + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);
