/****************************************************************
 *
 * 파일명 : workHistory.js
 * 설  명 : 국민대 > 근로학생관리 > 근로장학금 지급내역 출력 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.18     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */

/** 근로장학금 지급내역 출력 팝업 controller */
app.controller('workStudentPayHistoryReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('workStudentPayHistoryReportCtrl', $scope, $http, true));

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("workStudentPayHistoryReportCtrl", function (event, data) {
        $scope.srchYm = data.srchYm;

        $scope.wjWorkStudentPayHistoryReportLayer.show(true);

        // workStudentPayHistoryReport html 내용 초기화
        $("#workStudentPayHistoryReport").html('');

        $scope.getWorkStudentPayHistoryReportList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 근로장학금 지급내역 조회
    $scope.getWorkStudentPayHistoryReportList = function () {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');

        var params    = {};
        params.srchYm = $scope.srchYm;

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/kookmin/workStudent/workStudentPayHistory/workStudentPayHistory/getWorkStudentPayHistoryList.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {
                    var dataList = response.data.data.list;
                    var loopCnt  = dataList.length;

                    var arrPayHistList = [];
                    var arrList     = [];
                    var pageRowCnt  = 30; // 한 페이지에 표시할 row 수 변수
                    var rowCnt      = 0;
                    var totPageCnt  = Math.ceil(parseInt(loopCnt) / parseInt(pageRowCnt));
                    // 한 페이지에 표시할 row 수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
                    for (var i = 0; i < loopCnt; i++) {
                        if (rowCnt >= pageRowCnt) {
                            rowCnt = 0;
                            arrPayHistList.push(arrList);
                            arrList = [];
                        }

                        var item = dataList[i];
                        arrList.push(item);
                        rowCnt++;
                    }
                    arrPayHistList.push(arrList);

                    // 현재 월, 일, 시간 세팅
                    var year               = getCurDate('-').split('-')[0];
                    var month              = getCurDate('-').split('-')[1];
                    var day                = getCurDate('-').split('-')[2];
                    var time               = getCurTime(':');
                    var rowNo              = 0;  // row 의 No. 를 위한 변수
                    var payHistoryReportHtml = ''; // 화면에 뿌려질 최종 html 을 담기위한 변수
                    var titleHtml          = ''; // 출력물 최상단 html
                    var payHistListHtml       = ''; // 근로장학금 지급내역리스트 html
                    var nextPageHtml       = '<p class="nextPage"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
                    var payHistListHeaderHtml = '<table class="w100 mt5">' // 근로장학금 지급내역리스트 header html
                        + '<colgroup>'
                        + '<col style="width:5%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:12%;">'
                        + '<col style="width:12%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:14%;">'
                        + '<col style="width:9%;">'
                        + '<col style="width:9%;">'
                        + '<col style="width:24%;">'
                        + '</colgroup>'
                        + '<tr class="tc">'
                        + '<th>No.</th>'
                        + '<th>성명</th>'
                        + '<th>근로장소</th>'
                        + '<th>학부</th>'
                        + '<th>학번</th>'
                        + '<th>근로기간</th>'
                        + '<th>근로시간</th>'
                        + '<th>지급금액</th>'
                        + '<th>입금계좌</th>'
                        + '</tr>';

                    // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
                    for (var i = 0; i < arrPayHistList.length; i++) {
                        var payHistList = arrPayHistList[i];
                        // 상품수만큼 for문 돈다
                        for (var j = 0; j < payHistList.length; j++) {
                            var item = payHistList[j];
                            payHistListHtml += '<tr class="h25">'
                                + '<td class="tc">' + (++rowNo) + '</td>'
                                + '<td class="tc">' + item.studentNm + '</td>'
                                + '<td class="tc">' + item.storeNm + '</td>'
                                + '<td class="tc">' + item.department + '</td>'
                                + '<td class="tc">' + item.studentNo + '</td>'
                                + '<td class="tc">' + item.workPeriod + '</td>'
                                + '<td class="tc">' + item.workTime + '</td>'
                                + '<td class="tc">' + addComma(item.payAmt) + '</td>'
                                + '<td class="tc">' + item.bankAccount + '</td>'
                                + '</tr>';
                        }
                        payHistListHtml += '</table>';

                        titleHtml    = '<table class="w100">'
                            + '<colgroup>'
                            + '<col style="width: 60%">'
                            + '<col style="width: 20%">'
                            + '<col style="width: 20%">'
                            + '</colgroup>'
                            + '<tr>'
                            + '<td class="tl br0"><p class="bk s20">근로장학금 지급내역 </p></td>'
                            + '<td class="tc br0" valign="bottom">출력일자 : ' + month + '-' + day + ' ' + time + '</td>'
                            + '<td class="tc br0" valign="bottom">페이지 : ' + (i + 1) + ' / ' + totPageCnt + '</td>'
                            + '</tr>'
                            + '</table>';
                        payHistoryReportHtml += (i > 0 ? nextPageHtml : '') + titleHtml + payHistListHeaderHtml + payHistListHtml;
                        payHistListHtml = '';
                    }

                    // console.log(payHistoryReportHtml);
                    $('#workStudentPayHistoryReport').append(payHistoryReportHtml);

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
            title: '근로장학금 지급내역'
        });

        // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
        var browser = navigator.userAgent.toLowerCase();
        if (-1 != browser.indexOf('chrome')) {
            // doc.append('<div style="height: 9mm;"></div>');
        }

        // add content to it
        var view = document.querySelector('#workStudentPayHistoryReport');
        doc.append(view);

        // and print it
        doc.print();
    };

}]);
