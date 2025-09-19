/****************************************************************
 *
 * 파일명 : workHistory.js
 * 설  명 : 국민대 > 근로학생관리 > 근로학생별 근무내역 출력 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.19     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */

/** 근로학생별 근무내역 출력 팝업 controller */
app.controller('workHistoryByWorkStudentReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('workHistoryByWorkStudentReportCtrl', $scope, $http, true));

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("workHistoryByWorkStudentReportCtrl", function (event, data) {
        $scope.data = data;

        $scope.wjWorkHistoryByWorkStudentReportLayer.show(true);

        // workHistoryByWorkStudentReport html 내용 초기화
        $("#workHistoryByWorkStudentReport").html('');

        $scope.getWorkHistoryByWorkStudentReportList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 근로학생별 근무내역 리스트 조회
    $scope.getWorkHistoryByWorkStudentReportList = function () {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');

        var params    = {};
        params.startDate    = $scope.data.startDate;
        params.endDate      = $scope.data.endDate;
        params.storeCd      = $scope.data.storeCd;
        params.storeNm      = $scope.data.storeNm;
        params.studentNo    = $scope.data.studentNo;
        params.studentNm    = $scope.data.studentNm;

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/kookmin/workStudent/workHistoryByWorkStudent/workHistoryByWorkStudent/getWorkHistoryByWorkStudentList.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {
                    var dataList = response.data.data.list;
                    var loopCnt  = dataList.length;
                    console.log(dataList);

                    var arrWorkStudentList = [];
                    var arrList     = [];
                    var pageRowCnt  = 35; // 한 페이지에 표시할 row 수 변수
                    var rowCnt      = 0;
                    var totPageCnt  = Math.ceil(parseInt(loopCnt) / parseInt(pageRowCnt));
                    // 한 페이지에 표시할 row 수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
                    for (var i = 0; i < loopCnt; i++) {
                        if (rowCnt >= pageRowCnt) {
                            rowCnt = 0;
                            arrWorkStudentList.push(arrList);
                            arrList = [];
                        }

                        var item = dataList[i];
                        arrList.push(item);
                        rowCnt++;
                    }
                    arrWorkStudentList.push(arrList);

                    // 현재 월, 일, 시간 세팅
                    var year               = getCurDate('-').split('-')[0];
                    var month              = getCurDate('-').split('-')[1];
                    var day                = getCurDate('-').split('-')[2];
                    var time               = getCurTime(':');
                    var rowNo              = 0;  // row 의 No. 를 위한 변수
                    var workStudentReportHtml = ''; // 화면에 뿌려질 최종 html 을 담기위한 변수
                    var titleHtml          = ''; // 근로학생별 근무내역 최상단 html
                    var workStudentListHtml       = ''; // 근로학생별 근무내역리스트 html
                    var nextPageHtml       = '<p class="nextPage"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
                    var workStudentListHeaderHtml = '<table class="w100 mt5">' // 근로학생별 근무내역리스트 header html
                        + '<colgroup>'
                        + '<col style="width:5%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:12%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:20%;">'
                        + '</colgroup>'
                        + '<tr class="tc">'
                        + '<th>No.</th>'
                        + '<th>근로학생</th>'
                        + '<th>근무일자</th>'
                        + '<th>근무장소</th>'
                        + '<th>출근시간</th>'
                        + '<th>퇴근시간</th>'
                        + '<th>근무시간</th>'
                        + '<th>비고</th>'
                        + '</tr>';

                    // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
                    for (var i = 0; i < arrWorkStudentList.length; i++) {
                        var payHistList = arrWorkStudentList[i];
                        // 상품수만큼 for문 돈다
                        for (var j = 0; j < payHistList.length; j++) {
                            var item = payHistList[j];
                            workStudentListHtml += '<tr class="h25">'
                                + '<td class="tc">' + (++rowNo) + '</td>'
                                + '<td class="tc">' + item.studentNm + '</td>'
                                + '<td class="tc">' + item.workDate + '</td>'
                                + '<td class="tc">' + item.storeNm + '</td>'
                                + '<td class="tc">' + item.realStartTime + '</td>'
                                + '<td class="tc">' + item.realEndTime + '</td>'
                                + '<td class="tc">' + item.baseWorkTime + '</td>'
                                + '<td class="tc">' + nvl(item.remark,'') + '</td>'
                                + '</tr>';
                        }
                        workStudentListHtml += '</table>';

                        titleHtml    = '<table class="w100">'
                            + '<colgroup>'
                            + '<col style="width: 60%">'
                            + '<col style="width: 20%">'
                            + '<col style="width: 20%">'
                            + '</colgroup>'
                            + '<tr>'
                            + '<td class="tl br0"><p class="bk s20">근로학생별 근로내역 현황 </p></td>'
                            + '<td class="tc br0" valign="bottom">출력일자 : ' + month + '-' + day + ' ' + time + '</td>'
                            + '<td class="tc br0" valign="bottom">페이지 : ' + (i + 1) + ' / ' + totPageCnt + '</td>'
                            + '</tr>'
                            + '</table>';
                        workStudentReportHtml += (i > 0 ? nextPageHtml : '') + titleHtml + workStudentListHeaderHtml + workStudentListHtml;
                        workStudentListHtml = '';
                    }

                    // console.log(workStudentReportHtml);
                    $('#workHistoryByWorkStudentReport').append(workStudentReportHtml);

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
            title: '근로학생별 근로내역 현황'
        });

        // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
        var browser = navigator.userAgent.toLowerCase();
        if (-1 != browser.indexOf('chrome')) {
            // doc.append('<div style="height: 9mm;"></div>');
        }

        // add content to it
        var view = document.querySelector('#workHistoryByWorkStudentReport');
        doc.append(view);

        // and print it
        doc.print();
    };

}]);
