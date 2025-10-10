/****************************************************************
 *
 * 파일명 : storeSaleKmuReport.js
 * 설  명 : 국민대 > 매출관리 > 점소별매출일보 > 점소별매출일보 출력 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.02     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */

/** 점소별매출일보 출력 팝업 controller */
app.controller('storeSaleKmuReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSaleKmuReportCtrl', $scope, $http, true));

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeSaleKmuReportCtrl", function (event, data) {
        $scope.data = data;

        $scope.wjStoreSaleKmuReportLayer.show(true);

        // html 내용 초기화
        $("#storeSaleKmuReport").html('');

        $scope.getStoreSaleKmuReportList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 점소별매출일보 리스트 조회
    $scope.getStoreSaleKmuReportList = function () {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');

        var params    = {};
        params.startDate    = $scope.data.startDate;
        params.endDate      = $scope.data.endDate;
        params.teamCd      = $scope.data.teamCd;
        params.teamNm      = $scope.data.teamNm;
        params.branchCd    = $scope.data.branchCd;
        params.branchNm    = $scope.data.branchNm;
        params.storeCds    = $scope.data.storeCds;

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/sale/status/storeSaleKmu/storeSaleKmu/getStoreSaleKmuList.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {
                    var dataList = response.data.data.list;
                    var loopCnt  = dataList.length;

                    var arrStoreSaleKmuList = [];
                    var arrList     = [];
                    var pageRowCnt  = 30; // 한 페이지에 표시할 row 수 변수
                    var rowCnt      = 0;
                    var totPageCnt  = Math.ceil(parseInt(loopCnt) / parseInt(pageRowCnt));
                    // 한 페이지에 표시할 row 수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
                    for (var i = 0; i < loopCnt; i++) {
                        if (rowCnt >= pageRowCnt) {
                            rowCnt = 0;
                            arrStoreSaleKmuList.push(arrList);
                            arrList = [];
                        }

                        var item = dataList[i];
                        arrList.push(item);
                        rowCnt++;
                    }
                    arrStoreSaleKmuList.push(arrList);

                    // 현재 월, 일, 시간 세팅
                    var year               = getCurDate('-').split('-')[0];
                    var month              = getCurDate('-').split('-')[1];
                    var day                = getCurDate('-').split('-')[2];
                    var time               = getCurTime(':');
                    var srchStartDate      = $scope.data.startDate.substring(0,4) + '-' + $scope.data.startDate.substring(4,6) + '-' + $scope.data.startDate.substring(6,8);
                    var srchEndDate        = $scope.data.endDate.substring(0,4) + '-' + $scope.data.endDate.substring(4,6) + '-' + $scope.data.endDate.substring(6,8);
                    var rowNo              = 0;  // row 의 No. 를 위한 변수
                    var storeSaleKmuReportHtml = ''; // 화면에 뿌려질 최종 html 을 담기위한 변수
                    var titleHtml          = ''; // 점소별매출일보 최상단 html
                    var storeSaleKmuListHtml       = ''; // 점소별매출일보 리스트 html
                    var nextPageHtml       = '<p class="nextPage"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
                    var storeSaleKmuListHeaderHtml = '<table class="w100 mt5">' // 점소별매출일보 리스트 header html
                        + '<colgroup>'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:7%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:7%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:10%;">'
                        + '<col style="width:7%;">'
                        + '<col style="width:7%;">'
                        + '<col style="width:7%;">'
                        + '<col style="width:7%;">'
                        + '<col style="width:7%;">'
                        + '<col style="width:7%;">'
                        + '<col style="width:7%;">'
                        + '<col style="width:7%;">'
                        + '</colgroup>'
                        + '<tr class="tc">'
                        + '<th>No.</th>'
                        + '<th>팀코드(수정예정)</th>'
                        + '<th>팀명(수정예정)</th>'
                        + '<th>지점코드(수정예정)</th>'
                        + '<th>지점명(수정예정)</th>'
                        + '<th>매장코드</th>'
                        + '<th>매장명</th>'
                        + '<th>총매출액</th>'
                        + '<th>할인액</th>'
                        + '<th>실매출액</th>'
                        + '<th>에누리</th>'
                        + '<th>외상</th>'
                        + '<th>상품권(수정예정)</th>'
                        + '<th>카드</th>'
                        + '<th>현금</th>'
                        + '</tr>';

                    // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
                    for (var i = 0; i < arrStoreSaleKmuList.length; i++) {
                        var storeSaleList = arrStoreSaleKmuList[i];
                        // 상품수만큼 for문 돈다
                        for (var j = 0; j < storeSaleList.length; j++) {
                            var item = storeSaleList[j];
                            storeSaleKmuListHtml += '<tr class="h25">'
                                + '<td class="tc">' + (++rowNo) + '</td>'
                                + '<td class="tc">' + nvl(item.temaCd,'') + '</td>'
                                + '<td class="tc">' + nvl(item.temaNm,'') + '</td>'
                                + '<td class="tc">' + nvl(item.branchCd,'') + '</td>'
                                + '<td class="tc">' + nvl(item.branchNm,'') + '</td>'
                                + '<td class="tc">' + nvl(item.storeCd,'')+ '</td>'
                                + '<td class="tc">' + nvl(item.storeNm,'') + '</td>'
                                + '<td class="tr">' + nvl(item.totSaleAmt,0) + '</td>'
                                + '<td class="tr">' + nvl(item.totDcAmt,0) + '</td>'
                                + '<td class="tr">' + nvl(item.realSaleAmt,0) + '</td>'
                                + '<td class="tr">' + nvl(item.totEtcAmt,0) + '</td>'
                                + '<td class="tr">' + nvl(item.postpaidAmt,0) + '</td>'
                                + '<td class="tr">' + nvl(item.giftAmt,0) + '</td>'
                                + '<td class="tr">' + nvl(item.cardAmt,0) + '</td>'
                                + '<td class="tr">' + nvl(item.cashAmt,0) + '</td>'
                                + '</tr>';
                        }
                        storeSaleKmuListHtml += '</table>';

                        titleHtml    = '<table class="w100">'
                            + '<colgroup>'
                            + '<col style="width: 60%">'
                            + '<col style="width: 20%">'
                            + '<col style="width: 20%">'
                            + '</colgroup>'
                            + '<tr>'
                            + '<td class="tl br0"><p class="bk s20">점소별매출일보</p></td>'
                            + '</tr>'
                            + '<tr>'
                            + '<td class="tl br0" valign="bottom">(기간 : ' + srchStartDate + ' ~ ' + srchEndDate + ')</td>'
                            + '<td class="tr br0" valign="bottom">출력일자 : ' + month + '-' + day + ' ' + time + '</td>'
                            + '<td class="tr br0" valign="bottom">페이지 : ' + (i + 1) + ' / ' + totPageCnt + '</td>'
                            + '</tr>'
                            + '</table>';
                        storeSaleKmuReportHtml += (i > 0 ? nextPageHtml : '') + titleHtml + storeSaleKmuListHeaderHtml + storeSaleKmuListHtml;
                        // storeSaleKmuListHtml = '';
                    }

                    // console.log(storeSaleKmuReportHtml);
                    $('#storeSaleKmuReport').append(storeSaleKmuReportHtml);

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
            title: '점소별매출일보'
        });

        // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
        var browser = navigator.userAgent.toLowerCase();
        if (-1 != browser.indexOf('chrome')) {
            // doc.append('<div style="height: 9mm;"></div>');
        }

        // add content to it
        var view = document.querySelector('#storeSaleKmuReport');
        doc.append(view);

        // and print it
        doc.print();
    };

}]);
