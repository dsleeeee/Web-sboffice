/****************************************************************
 *
 * 파일명 : dailySaleReportReport.js
 * 설  명 : 국민대 > 총괄정보 > 일일매출보고서 출력 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.23     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */

/** 일일매출보고서 출력 팝업 controller */
app.controller('dailySaleReportReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dailySaleReportReportCtrl', $scope, $http, true));

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dailySaleReportReportCtrl", function (event, data) {

        $scope.data = data;

        $scope.wjDailySaleReportReportLayer.show(true);

        // html 내용 초기화
        $("#dailySaleReportReport").html('');

        // 일일매출보고서 리스트 조회
        $scope.getDailySaleReportList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();

    });

    // 일일매출보고서 리스트 조회
    $scope.getDailySaleReportList = function () {

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
            url    : '/kookmin/generalInfo/dailySaleReport/dailySaleReport/getDailySaleReportList.sb', /* 통신할 URL */
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
                    var srchStartDate          = $scope.data.startDate.substring(0,4) + '-' + $scope.data.startDate.substring(4,6) + '-' + $scope.data.startDate.substring(6,8);
                    var srchEndDate            = $scope.data.endDate.substring(0,4) + '-' + $scope.data.endDate.substring(4,6) + '-' + $scope.data.endDate.substring(6,8);
                    var rowNo                  = 0;  // row 의 No. 를 위한 변수
                    var arrTotReportList       = ''; // 화면에 뿌려질 최종 html 을 담기위한 변수
                    var titleHtml              = ''; // 시간대별 매출분석 최상단 html
                    var arrHtml                = ''; // 근로학생별 근무내역리스트 html
                    var nextPageHtml           = '<p class="nextPage"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
                    var arrHeaderHtml          = '<table class="w100 mt5 small-text-table">' // 시간대별 매출분석리스트 header html
                        + '<colgroup>'
                        + '<col style="width:4%;">'
                        + '<col style="width:6%;">'
                        + '<col style="width:12%;">'
                        + '<col style="width:9%;">'
                        + '<col style="width:9%;">'
                        + '<col style="width:9%;">'
                        + '<col style="width:9%;">'
                        + '<col style="width:9%;">'
                        + '<col style="width:9%;">'
                        + '</colgroup>'
                        + '<tr class="tc">'
                        + '<th>No.</th>'
                        + '<th>구분</th>'
                        + '<th>매장명</th>'
                        + '<th>매출 합계</th>'
                        + '<th>전년도 매출<br>대비 증감액</th>'
                        + '<th>현금</th>'
                        + '<th>현금 합계</th>'
                        + '<th>외상매출</th>'
                        + '<th>신용카드</th>'
                        + '</tr>';

                    // 총 합계 계산용
                    var tot_totSaleAmt     = 0; // 매출 합계
                    var tot_contrast       = 0; // 전년도 매출 대비 증감액 합계
                    var tot_cashAmt        = 0; // 현금
                    var tot_cashAmtTot     = 0; // 현금 합계
                    var tot_postpaidAmt    = 0; // 외상매출
                    var tot_cardAmt        = 0; // 신용카드

                    // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
                    for (var i = 0; i < arrReportList.length; i++) {
                        var reportList = arrReportList[i];
                        // 상품수만큼 for문 돈다
                        for (var j = 0; j < reportList.length; j++) {
                            var item = reportList[j];
                            
                            // 총 합계 계산을 위해 더한다.
                            tot_totSaleAmt += item.totSaleAmt;
                            tot_contrast += item.contrast;
                            tot_cashAmt += item.cashAmt;
                            tot_cashAmtTot += item.cashAmtTot;
                            tot_postpaidAmt += item.postpaidAmt;
                            tot_cardAmt += item.cardAmt;

                            arrHtml += '<tr class="h25">'
                                + '<td class="tc">' + (++rowNo) + '</td>'
                                + '<td class="tc">' + nvl(item.branchCd, '') + '</td>'
                                + '<td class="tc">' + item.storeNm + '</td>'
                                + '<td class="tc">' + addComma(item.totSaleAmt) + '</td>';

                                if (item.contrast > 0) {
                                    arrHtml += '<td class="tc">' + "▲" + addComma(item.contrast) + '</td>'
                                } else if (item.contrast == 0) {
                                    arrHtml += '<td class="tc">' + addComma(item.contrast) + '</td>'
                                } else {
                                    arrHtml += '<td class="tc">' + "▽" +addComma(item.contrast) + '</td>'
                                }

                            arrHtml += '<td class="tc">' + addComma(item.cashAmt) + '</td>'
                                + '<td class="tc">' + addComma(item.cashAmtTot) + '</td>'
                                + '<td class="tc">' + addComma(item.postpaidAmt) + '</td>'
                                + '<td class="tc">' + addComma(item.cardAmt) + '</td>'
                                + '</tr>';
                        }

                        // 마지막 페이지 하단에 총 합계 출력
                        if ((i + 1) == totPageCnt) {
                            arrHtml += '<tr>'
                                + '<td class="tc" colspan="3"> 합계 </td>'
                                + '<td class="tc">' + addComma(tot_totSaleAmt) + '</td>';

                            if (Math.sign(tot_contrast) === 1) {
                                arrHtml += '<td class="tc">' + "▲" + addComma(tot_contrast) + '</td>';
                            } else if (Math.sign(tot_contrast) === -1) {
                                arrHtml += '<td class="tc">' + "▽" + addComma(tot_contrast) + '</td>';
                            } else {
                                arrHtml += '<td class="tc">' + addComma(tot_contrast) + '</td>';
                            }

                            arrHtml += '<td class="tc">' + addComma(tot_cashAmt) + '</td>'
                                + '<td class="tc">' + addComma(tot_cashAmtTot) + '</td>'
                                + '<td class="tc">' + addComma(tot_postpaidAmt) + '</td>'
                                + '<td class="tc">' + addComma(tot_cardAmt) + '</td>'
                                + '</tr>';
                        }

                        arrHtml += '</table>';

                        titleHtml = '<table class="w100">'
                            + '<colgroup>'
                            + '<col style="width: 65%">'
                            + '<col style="width: 20%">'
                            + '<col style="width: 15%">'
                            + '</colgroup>';

                        // 첫번째 페이지에 결제라인 추가
                        if (i === 0) {
                            titleHtml += '<tr>'
                                + '<td class="tl br0"></td>'
                                + '<td class="tl br0" colspan="2">'
                                + '<table class="w100" style="height: 80px;">'
                                + '<tr>'
                                + '<td style="width:8%;" rowspan="2" align="center">결재</td>'
                                + '<td style="width:23%; height: 10%;" align="center">담당</td>'
                                + '<td style="width:23%;" align="center">팀장</td>'
                                + '<td style="width:23%;" align="center">국장</td>'
                                + '<td style="width:23%;" align="center">집행이사</td>'
                                + '</tr>'
                                + '<tr>'
                                + '<td class="" style="height: 90%;"></td>'
                                + '<td class=""></td>'
                                + '<td class=""></td>'
                                + '<td class=""></td>'
                                + '</tr>'
                                + '</table>'
                                + '</td>'
                                + '</tr>';
                        }

                        titleHtml += '<tr>'
                            + '<td class="tl br0"><p class="bk s20">일자별매출보고서</p></td>'
                            + '</tr>'
                            + '<tr>'
                            + '<td class="tl br0" valign="bottom">기간 : ' + srchStartDate + ' ~ ' + srchEndDate + '</td>'
                            + '<td class="tr br0" valign="bottom">출력일자 : ' + month + '-' + day + ' ' + time + '</td>'
                            + '<td class="tr br0" valign="bottom">페이지 : ' + (i + 1) + ' / ' + totPageCnt + '</td>'
                            + '</tr>'
                            + '</table>';
                        arrTotReportList += (i > 0 ? nextPageHtml : '') + titleHtml + arrHeaderHtml + arrHtml;
                        arrHtml = '';
                    }

                    // console.log(arrTotReportList);
                    $('#dailySaleReportReport').append(arrTotReportList);

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
            title: '일자별매출보고서'
        });

        // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
        var browser = navigator.userAgent.toLowerCase();
        if (-1 != browser.indexOf('chrome')) {
            // doc.append('<div style="height: 9mm;"></div>');
        }

        // add content to it
        var view = document.querySelector('#dailySaleReportReport');
        doc.append(view);

        // and print it
        doc.print();
    };

}]);