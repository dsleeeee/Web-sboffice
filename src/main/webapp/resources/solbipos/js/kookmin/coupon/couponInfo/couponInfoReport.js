/****************************************************************
 *
 * 파일명 : couponInfoReport.js
 * 설  명 : 국민대 > 쿠폰관리 > 쿠폰정보관리 > 출력팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.24     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */

/** 상품별 매출분석 현황 그리드 controller */
app.controller('couponInfoReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('couponInfoReportCtrl', $scope, $http, true));

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("couponInfoReportCtrl", function (event, data) {
        $scope.data = data;

        $scope.wjCouponInfoReportLayer.show(true);


        // couponInfoReport html 내용 초기화
        $("#couponInfoReport").html('');

        $scope.getSaleAnalysisByProductList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 근로학생별 근무내역 리스트 조회
    $scope.getSaleAnalysisByProductList = function () {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');

        var params    = $scope.data;

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/kookmin/coupon/couponInfo/couponInfo/getCouponInfoList.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {
                    var dataList = response.data.data.list;
                    var loopCnt  = dataList.length;

                    var couponInfoList = [];
                    var arrList     = [];
                    var pageRowCnt  = 20; // 한 페이지에 표시할 row 수 변수
                    var rowCnt      = 0;
                    var totPageCnt  = Math.ceil(parseInt(loopCnt) / parseInt(pageRowCnt));
                    // 한 페이지에 표시할 row 수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
                    for (var i = 0; i < loopCnt; i++) {
                        if (rowCnt >= pageRowCnt) {
                            rowCnt = 0;
                            couponInfoList.push(arrList);
                            arrList = [];
                        }

                        var item = dataList[i];
                        arrList.push(item);
                        rowCnt++;
                    }
                    couponInfoList.push(arrList);

                    // 현재 월, 일, 시간 세팅
                    var year               = getCurDate('-').split('-')[0];
                    var month              = getCurDate('-').split('-')[1];
                    var day                = getCurDate('-').split('-')[2];
                    var time               = getCurTime(':');
                    var rowNo              = 0;  // row 의 No. 를 위한 변수
                    var arrCouponInfoReportList = ''; // 화면에 뿌려질 최종 html 을 담기위한 변수
                    var titleHtml          = ''; // 근로학생별 근무내역 최상단 html
                    var couponInfoListHtml       = ''; // 근로학생별 근무내역리스트 html
                    var nextPageHtml       = '<p class="nextPage"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
                    var couponInfoListHeaderHtml = '<table class="w100 mt5">' // 근로학생별 근무내역리스트 header html
                        + '<colgroup>'
                        + '<col style="width:3%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:6%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:3%;">'
                        + '<col style="width:3%;">'
                        + '<col style="width:3%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:3%;">'
                        + '<col style="width:3%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:3%;">'
                        + '<col style="width:3%;">'
                        + '</colgroup>'
                        + '<tr class="tc">'
                        + '<th>No.</th>'
                        + '<th>쿠폰코드</th>'
                        + '<th>쿠폰명</th>'
                        + '<th>사용부서코드</th>'
                        + '<th>사용부서명</th>'
                        + '<th>사용기간시작</th>'
                        + '<th>사용기간종료</th>'
                        + '<th>상품코드</th>'
                        + '<th>상품명</th>'
                        + '<th>가격</th>'
                        + '<th>발행수량</th>'
                        + '<th>발행금액</th>'
                        + '<th>발행일</th>'
                        + '<th>회수수량</th>'
                        + '<th>회수금액</th>'
                        + '<th>최종회수일</th>'
                        + '<th>미회수량</th>'
                        + '<th>미회수금액</th>'
                        + '</tr>';

                    // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
                    for (var i = 0; i < couponInfoList.length; i++) {
                        var payHistList = couponInfoList[i];
                        // 상품수만큼 for문 돈다
                        for (var j = 0; j < payHistList.length; j++) {
                            var item = payHistList[j];
                            couponInfoListHtml += '<tr class="h25">'
                                + '<td class="tc">' + (++rowNo) + '</td>'
                                + '<td class="tc">' + item.mergeCoupnCd + '</td>'
                                + '<td class="tc">' + item.coupnNm + '</td>'
                                + '<td class="tc">' + item.coupnUsePart + '</td>'
                                + '<td class="tc">' + item.vendrNm + '</td>'
                                + '<td class="tc">' + item.startDate + '</td>'
                                + '<td class="tc">' + item.endDate + '</td>'
                                + '<td class="tc">' + item.coupnProdCd + '</td>'
                                + '<td class="tc">' + item.prodNm + '</td>'
                                + '<td class="tc">' + addComma(item.saleUprc) + '</td>'
                                + '<td class="tc">' + addComma(item.coupnCount) + '</td>'
                                + '<td class="tc">' + addComma(item.coupnIssueUprc) + '</td>'
                                + '<td class="tc">' + nvl(item.coupnIssueDate, '') + '</td>'
                                + '<td class="tc">' + addComma(item.saleCnt) + '</td>'
                                + '<td class="tc">' + addComma(item.saleAmt) + '</td>'
                                + '<td class="tc">' + nvl(item.coupnLastSaleDate, '') + '</td>'
                                + '<td class="tc">' + addComma(item.notSaleCnt) + '</td>'
                                + '<td class="tc">' + addComma(item.notSaleAmt) + '</td>'
                                + '</tr>';
                        }
                        couponInfoListHtml += '</table>';

                        titleHtml    = '<table class="w100">'
                            + '<colgroup>'
                            + '<col style="width: 65%">'
                            + '<col style="width: 20%">'
                            + '<col style="width: 15%">'
                            + '</colgroup>'
                            + '<tr>'
                            + '<td class="tl br0"><p class="bk s20">쿠폰정보관리 </p></td>'
                            + '</tr>'
                            + '<tr>'
                            + '<td class="tl br0" valign="bottom"></td>'
                            + '<td class="tr br0" valign="bottom">출력일자 : ' + month + '-' + day + ' ' + time + '</td>'
                            + '<td class="tr br0" valign="bottom">페이지 : ' + (i + 1) + ' / ' + totPageCnt + '</td>'
                            + '</tr>'
                            + '</table>';
                        arrCouponInfoReportList += (i > 0 ? nextPageHtml : '') + titleHtml + couponInfoListHeaderHtml + couponInfoListHtml;
                        couponInfoListHtml = '';
                    }

                    // console.log(arrCouponInfoReportList);
                    $('#couponInfoReport').append(arrCouponInfoReportList);

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
            title: '상품별 매출분석'
        });

        // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
        var browser = navigator.userAgent.toLowerCase();
        if (-1 != browser.indexOf('chrome')) {
            // doc.append('<div style="height: 9mm;"></div>');
        }

        // add content to it
        var view = document.querySelector('#couponInfoReport');
        doc.append(view);

        // and print it
        doc.print();
    };

}]);
