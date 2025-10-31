/****************************************************************
 *
 * 파일명 : saleAnalysisByMonthlyReport.js
 * 설  명 : 국민대 > 총괄정보 > 월별매출분석 출력 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.23     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */

/** 월별매출분석 출력 팝업 controller */
app.controller('saleAnalysisByMonthlyReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleAnalysisByMonthlyReportCtrl', $scope, $http, true));

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("saleAnalysisByMonthlyReportCtrl", function (event, data) {

        $scope.data = data;

        $scope.wjSaleAnalysisByMonthlyReportLayer.show(true);

        // html 내용 초기화
        $("#saleAnalysisByMonthlyReport").html('');

        // 월별매출분석 리스트 조회
        $scope.getSaleAnalysisByMonthlyList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();

    });

    // 월별매출분석 리스트 조회
    $scope.getSaleAnalysisByMonthlyList = function () {

        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');

        var params = $scope.data;

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/kookmin/generalInfo/saleAnalysisByMonthly/saleAnalysisByMonthly/getSaleAnalysisByMonthlyList.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {
                    var dataList = response.data.data.list;
                    var loopCnt  = dataList.length;

                    var arrReportList = [];
                    var arrList     = [];
                    var pageRowCnt  = 30; // 한 페이지에 표시할 row 수 변수
                    var rowCnt      = 0;
                    var totPageCnt  = Math.ceil(parseInt(loopCnt) / parseInt(pageRowCnt));

                    // 한 페이지에 표시할 row 수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
                    for (var i = 0; i < loopCnt; i++) {

                        if (rowCnt >= pageRowCnt) {
                            rowCnt = 0;
                            arrReportList.push(arrList);
                            arrList = [];
                        }

                        var item = dataList[i];
                        arrList.push(item);
                        rowCnt++;
                    }

                    arrReportList.push(arrList);

                    // 현재 월, 일, 시간 세팅
                    var year                   = getCurDate('-').split('-')[0];
                    var month                  = getCurDate('-').split('-')[1];
                    var day                    = getCurDate('-').split('-')[2];
                    var time                   = getCurTime(':');
                    //var srchStartDate          = $scope.data.startDate.substring(0,4) + '-' + $scope.data.startDate.substring(4,6) + '-' + $scope.data.startDate.substring(6,8);
                    //var srchEndDate            = $scope.data.endDate.substring(0,4) + '-' + $scope.data.endDate.substring(4,6) + '-' + $scope.data.endDate.substring(6,8);
                    var srchStartMonth         = $scope.data.startMonth;
                    var srchEndMonth         = $scope.data.endMonth;
                    var rowNo                  = 0;  // row 의 No. 를 위한 변수
                    var arrTotReportList       = ''; // 화면에 뿌려질 최종 html 을 담기위한 변수
                    var titleHtml              = ''; // 시간대별 매출분석 최상단 html
                    var arrHtml                = ''; // 근로학생별 근무내역리스트 html
                    var nextPageHtml           = '<p class="nextPage"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
                    var arrHeaderHtml          = '<table class="w100 mt5 small-text-table">' // 시간대별 매출분석리스트 header html
                        + '<colgroup>'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '</colgroup>'
                        + '<tr class="tc">'
                        + '<th rowspan="2">No.</th>'
                        + '<th rowspan="2">구분</th>'
                        + '<th rowspan="2">매장명</th>'
                        + '<th colspan="4">매출</th>'
                        + '<th colspan="4">판매수량</th>'
                        + '<th colspan="4">이용자수</th>'
                        + '<th colspan="6">운영일</th>'
                        + '</tr>'
                        + '<tr class="tc">'
                        + '<th>금년</th>'
                        + '<th>전년</th>'
                        + '<th>대비</th>'
                        + '<th>%</th>'
                        + '<th>금년</th>'
                        + '<th>전년</th>'
                        + '<th>대비</th>'
                        + '<th>%</th>'
                        + '<th>금년</th>'
                        + '<th>전년</th>'
                        + '<th>대비</th>'
                        + '<th>%</th>'
                        + '<th>금년평일</th>'
                        + '<th>전년평일</th>'
                        + '<th>대비</th>'
                        + '<th>금년주말</th>'
                        + '<th>전년주말</th>'
                        + '<th>대비</th>'
                        + '</tr>';

                    var tot_totSaleAmt = 0;
                    var tot_bTotSaleAmt = 0;
                    var tot_totSaleAmtContrast = 0;
                    var tot_saleQty = 0;
                    var tot_bSaleQty = 0;
                    var tot_saleQtyContrast = 0;
                    var tot_totGuestCnt = 0;
                    var tot_bTotGuestCnt = 0;
                    var tot_totGuestCntContrast = 0;
                    var tot_weekdays = 0;
                    var tot_bWeekdays = 0;
                    var tot_weekdaysContrast = 0;
                    var tot_weekend = 0;
                    var tot_bWeekend = 0;
                    var tot_weekendContrast = 0;

                    // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
                    for (var i = 0; i < arrReportList.length; i++) {
                        var reportList = arrReportList[i];
                        // 상품수만큼 for문 돈다
                        for (var j = 0; j < reportList.length; j++) {
                            var item = reportList[j];

                            tot_totSaleAmt += item.totSaleAmt;
                            tot_bTotSaleAmt += item.bTotSaleAmt;
                            tot_totSaleAmtContrast += item.totSaleAmtContrast;
                            tot_saleQty += item.saleQty;
                            tot_bSaleQty += item.bSaleQty;
                            tot_saleQtyContrast += item.saleQtyContrast;
                            tot_totGuestCnt += item.totGuestCnt;
                            tot_bTotGuestCnt += item.bTotGuestCnt;
                            tot_totGuestCntContrast += item.totGuestCntContrast;
                            tot_weekdays += item.weekdays;
                            tot_bWeekdays += item.bWeekdays;
                            tot_weekdaysContrast += item.weekdaysContrast;
                            tot_weekend += item.weekend;
                            tot_bWeekend += item.bWeekend;
                            tot_weekendContrast += item.weekendContrast;

                            arrHtml += '<tr class="h25">'
                                + '<td class="tc">' + (++rowNo) + '</td>'
                                + '<td class="tc">' + nvl(item.branchCd, '') + '</td>'
                                + '<td class="tc">' + item.storeNm + '</td>'
                                + '<td class="tc">' + addComma(item.totSaleAmt) + '</td>'
                                + '<td class="tc">' + addComma(item.bTotSaleAmt) + '</td>'
                                + '<td class="tc">' + addComma(item.totSaleAmtContrast) + '</td>'
                                + '<td class="tc">' + (nvl(item.totSaleAmtPercent, '') != '' ? nvl(item.totSaleAmtPercent, '') + "%" : "") + '</td>'
                                + '<td class="tc">' + addComma(item.saleQty) + '</td>'
                                + '<td class="tc">' + addComma(item.bSaleQty) + '</td>'
                                + '<td class="tc">' + addComma(item.saleQtyContrast) + '</td>'
                                + '<td class="tc">' + (nvl(item.saleQtyPercent, '') != '' ? nvl(item.saleQtyPercent, '') + "%" : "") + '</td>'
                                + '<td class="tc">' + addComma(item.totGuestCnt) + '</td>'
                                + '<td class="tc">' + addComma(item.bTotGuestCnt) + '</td>'
                                + '<td class="tc">' + addComma(item.totGuestCntContrast) + '</td>'
                                + '<td class="tc">' + (nvl(item.totGuestCntPercent, '') != '' ? nvl(item.totGuestCntPercent, '') + "%" : "") + '</td>'
                                + '<td class="tc">' + item.weekdays + '</td>'
                                + '<td class="tc">' + item.bWeekdays + '</td>'
                                + '<td class="tc">' + item.weekdaysContrast + '</td>'
                                + '<td class="tc">' + item.weekend + '</td>'
                                + '<td class="tc">' + item.bWeekend + '</td>'
                                + '<td class="tc">' + item.weekendContrast + '</td>'
                                + '</tr>';
                        }

                        // 합계 출력
                        if ((i + 1) == totPageCnt) {
                            arrHtml += '<tr>'
                                + '<td class="tc" colspan="3"> 합계 </td>'
                                + '<td class="tc">' + addComma(tot_totSaleAmt) + '</td>'
                                + '<td class="tc">' + addComma(tot_bTotSaleAmt) + '</td>'
                                + '<td class="tc">' + addComma(tot_totSaleAmtContrast) + '</td>';

                            if (tot_bTotSaleAmt != 0) {
                                arrHtml += '<td class="tc">' + Math.round((tot_totSaleAmtContrast / tot_bTotSaleAmt) * 100 * 10) / 10 + '%' + '</td>';
                            } else {
                                arrHtml += '<td class="tc">' + '</td>';
                            }

                            arrHtml += '<td class="tc">' + addComma(tot_saleQty) + '</td>'
                                + '<td class="tc">' + addComma(tot_bSaleQty) + '</td>'
                                + '<td class="tc">' + addComma(tot_saleQtyContrast) + '</td>';

                            if (tot_bSaleQty != 0) {
                                arrHtml += '<td class="tc">' + Math.round((tot_saleQtyContrast / tot_bSaleQty) * 100 * 10) / 10 + '%' + '</td>';
                            } else {
                                arrHtml += '<td class="tc">' + '</td>';
                            }

                            arrHtml += '<td class="tc">' + addComma(tot_totGuestCnt) + '</td>'
                                + '<td class="tc">' + addComma(tot_bTotGuestCnt) + '</td>'
                                + '<td class="tc">' + addComma(tot_totGuestCntContrast) + '</td>';

                            if (tot_bTotGuestCnt != 0) {
                                arrHtml += '<td class="tc">' + Math.round((tot_totGuestCntContrast / tot_bTotGuestCnt) * 100 * 10) / 10 + '%' + '</td>';
                            } else {
                                arrHtml += '<td class="tc">' + '</td>';
                            }

                            arrHtml += '<td class="tc">' + tot_weekdays + '</td>'
                                + '<td class="tc">' + tot_bWeekdays + '</td>'
                                + '<td class="tc">' + tot_weekdaysContrast + '</td>'
                                + '<td class="tc">' + tot_weekend + '</td>'
                                + '<td class="tc">' + tot_bWeekend + '</td>'
                                + '<td class="tc">' + tot_weekendContrast + '</td>'
                                + '</tr>';
                        }

                        arrHtml += '</table>';

                        titleHtml = '<table class="w100">'
                            + '<colgroup>'
                            + '<col style="width: 65%">'
                            + '<col style="width: 20%">'
                            + '<col style="width: 15%">'
                            + '</colgroup>'
                            + '<tr>'
                            + '<td class="tl br0"><p class="bk s20">월별매출분석</p></td>'
                            + '</tr>'
                            + '<tr>'
                            + '<td class="tl br0" valign="bottom">기간 : ' + srchStartMonth + ' ~ ' + srchEndMonth + '</td>'
                            + '<td class="tr br0" valign="bottom">출력일자 : ' + month + '-' + day + ' ' + time + '</td>'
                            + '<td class="tr br0" valign="bottom">페이지 : ' + (i + 1) + ' / ' + totPageCnt + '</td>'
                            + '</tr>'
                            + '</table>';
                        arrTotReportList += (i > 0 ? nextPageHtml : '') + titleHtml + arrHeaderHtml + arrHtml;
                        arrHtml = '';
                    }

                    // console.log(arrTotReportList);
                    $('#saleAnalysisByMonthlyReport').append(arrTotReportList);

                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                }
            }
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // "complete" code here
        });
    };


    // 인쇄
    $scope.print = function () {
        // create document
        var doc = new wijmo.PrintDocument({
            title: '월별매출분석'
        });

        // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
        var browser = navigator.userAgent.toLowerCase();
        if (-1 != browser.indexOf('chrome')) {
            // doc.append('<div style="height: 9mm;"></div>');
        }

        // add content to it
        var view = document.querySelector('#saleAnalysisByMonthlyReport');
        doc.append(view);

        // and print it
        doc.print();
    };


}]);