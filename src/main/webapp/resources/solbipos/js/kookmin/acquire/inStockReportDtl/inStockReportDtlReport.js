/****************************************************************
 *
 * 파일명 : inStockReportDtlReport.js
 * 설  명 : 국민대 > 매입관리 > 매입처별 입고내역서 출력 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.12.02     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */

/** 근로학생별 근무내역 출력 팝업 controller */
app.controller('inStockReportDtlReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('inStockReportDtlReportCtrl', $scope, $http, true));

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("inStockReportDtlReportCtrl", function (event, data) {
        $scope.data = data;

        $scope.wjInStockReportDtlReportLayer.show(true);

        // inStockReportDtlReport html 내용 초기화
        $("#inStockReportDtlReport").html('');

        $scope.getInStockReportDtlReport();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 근로학생별 근무내역 리스트 조회
    $scope.getInStockReportDtlReport = function () {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');

        var params    = {};
        params.startDate    = $scope.data.startDate;
        params.endDate      = $scope.data.endDate;
        params.storeCd      = $scope.data.storeCd;
        params.prodClassCd  = $scope.data.prodClassCd;
        params.vendrCd      = $scope.data.vendrCd;

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/kookmin/acquire/inStockReportDtl/inStockReportDtl/getInStockReportDtlList.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {
                    var dataList = response.data.data.list;
                    var loopCnt  = dataList.length;

                    var arrInStockReportList = [];
                    var arrList     = [];
                    var pageRowCnt  = 30; // 한 페이지에 표시할 row 수 변수
                    var rowCnt      = 0;
                    var totPageCnt  = Math.ceil(parseInt(loopCnt) / parseInt(pageRowCnt));
                    // 한 페이지에 표시할 row 수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
                    for (var i = 0; i < loopCnt; i++) {
                        if (rowCnt >= pageRowCnt) {
                            rowCnt = 0;
                            arrInStockReportList.push(arrList);
                            arrList = [];
                        }

                        var item = dataList[i];
                        arrList.push(item);
                        rowCnt++;
                    }
                    arrInStockReportList.push(arrList);

                    // 현재 월, 일, 시간 세팅
                    var year               = getCurDate('-').split('-')[0];
                    var month              = getCurDate('-').split('-')[1];
                    var day                = getCurDate('-').split('-')[2];
                    var time               = getCurTime(':');
                    var srchStartDate      = $scope.data.startDate.substring(0,4) + '-' + $scope.data.startDate.substring(4,6) + '-' + $scope.data.startDate.substring(6,8);
                    var srchEndDate        = $scope.data.endDate.substring(0,4) + '-' + $scope.data.endDate.substring(4,6) + '-' + $scope.data.endDate.substring(6,8);
                    var rowNo              = 0;  // row 의 No. 를 위한 변수
                    var arrInStockReportHtml = ''; // 화면에 뿌려질 최종 html 을 담기위한 변수
                    var titleHtml          = ''; // 최상단 html
                    var arrInStockReportListHtml       = ''; // 리스트 html
                    var nextPageHtml       = '<p class="nextPage"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
                    var arrInStockReportListHeaderHtml = '<table class="w100 mt5">' // header html
                        + '<colgroup>'
                        + '<col style="width:6%;">'
                        + '<col style="width:10%;">'
                        + '<col style="width:6%;">'
                        + '<col style="width:10%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:10%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '</colgroup>'
                        + '<tr class="tc">'
                        + '<th>매입처</th>'
                        + '<th>매입처명</th>'
                        + '<th>매장</th>'
                        + '<th>매장명</th>'
                        + '<th>상품코드</th>'
                        + '<th>상품명</th>'
                        + '<th>구분</th>'
                        + '<th>판매가</th>'
                        + '<th>매입가</th>'
                        + '<th>매입율</th>'
                        + '<th>매입수량</th>'
                        + '<th>매입금액</th>'
                        + '<th>반품수량</th>'
                        + '<th>반품금액</th>'
                        + '<th>합계</th>'
                        + '</tr>';

                    // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
                    for (var i = 0; i < arrInStockReportList.length; i++) {
                        var payHistList = arrInStockReportList[i];
                        // 상품수만큼 for문 돈다
                        for (var j = 0; j < payHistList.length; j++) {
                            var item = payHistList[j];
                            var vendrCd = item.vendrCd;
                            var vendrNm = item.vendrNm;
                            var storeCd = item.storeCd;
                            var storeNm = item.storeNm;
                            if(j > 0){
                                var prevItem = payHistList[j-1];
                                if(item.vendrCd === prevItem.vendrCd){
                                    vendrCd = '';
                                }
                                if(item.vendrNm === prevItem.vendrNm){
                                    vendrNm = '';
                                }
                                if(item.storeCd === prevItem.storeCd){
                                    storeCd = '';
                                }
                                if(item.storeNm === prevItem.storeNm){
                                    storeNm = '';
                                }
                            }
                            arrInStockReportListHtml += '<tr class="h25">'
                                + '<td class="tc">' + nvl(vendrCd,'') + '</td>'
                                + '<td class="tc">' + nvl(vendrNm,'') + '</td>'
                                + '<td class="tc">' + nvl(storeCd,'') + '</td>'
                                + '<td class="tc">' + nvl(storeNm,'') + '</td>'
                                + '<td class="tc">' + nvl(item.prodCd,'') + '</td>'
                                + '<td class="tc">' + nvl(item.prodNm,'') + '</td>'
                                + '<td class="tc">' + nvl(item.tradeFg,'') + '</td>'
                                + '<td class="tc">' + addComma(item.saleUprc) + '</td>'
                                + '<td class="tc">' + addComma(item.costUprc) + '</td>'
                                + '<td class="tc">' + nvl(item.acquireRate,'') + '</td>'
                                + '<td class="tc">' + addComma(item.acquireQty) + '</td>'
                                + '<td class="tc">' + addComma(item.acquireAmt) + '</td>'
                                + '<td class="tc">' + addComma(item.rtnQty) + '</td>'
                                + '<td class="tc">' + addComma(item.rtnAmt) + '</td>'
                                + '<td class="tc">' + addComma(item.totAmt) + '</td>'
                                + '</tr>';
                        }
                        arrInStockReportListHtml += '</table>';

                        titleHtml    = '<table class="w100">'
                            + '<colgroup>'
                            + '<col style="width: 50%">'
                            + '<col style="width: 15%">'
                            + '<col style="width: 20%">'
                            + '<col style="width: 15%">'
                            + '</colgroup>'
                            + '<tr>'
                            + '<td class="tl br0"><p class="bk s20">매입처별 상세매입내역(상품별) </p></td>'
                            + '</tr>'
                            + '<tr>'
                            + '<td class="tl br0" valign="bottom">(기간 : ' + srchStartDate + ' ~ ' + srchEndDate + ')</td>'
                            + '<td class="tr br0" valign="bottom">(단위 : 원)</td>'
                            + '<td class="tr br0" valign="bottom">출력일자 : ' + month + '-' + day + ' ' + time +'</td>'
                            + '<td class="tr br0" valign="bottom">페이지 : ' + (i + 1) + ' / ' + totPageCnt + '</td>'
                            + '</tr>'
                            + '</table>';
                        arrInStockReportHtml += (i > 0 ? nextPageHtml : '') + titleHtml + arrInStockReportListHeaderHtml + arrInStockReportListHtml;
                        arrInStockReportListHtml = '';
                    }

                    // console.log(arrInStockReportHtml);
                    $('#inStockReportDtlReport').append(arrInStockReportHtml);

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
            title: '매입처별 상세매입내역(상품별)'
        });

        // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
        var browser = navigator.userAgent.toLowerCase();
        if (-1 != browser.indexOf('chrome')) {
            // doc.append('<div style="height: 9mm;"></div>');
        }

        // add content to it
        var view = document.querySelector('#inStockReportDtlReport');
        doc.append(view);

        // and print it
        doc.print();
    };

}]);
