/****************************************************************
 *
 * 파일명 : saleAnalysisByProductReport.js
 * 설  명 : 국민대 > 매출분석 > 상품별 매출분석 출력 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.29     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */

/** 상품별 매출분석 현황 그리드 controller */
app.controller('saleAnalysisByProductReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleAnalysisByProductReportCtrl', $scope, $http, true));

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("saleAnalysisByProductReportCtrl", function (event, data) {
        $scope.data = data;

        $scope.wjSaleAnalysisByProductReportLayer.show(true);


        // saleAnalysisByProductReport html 내용 초기화
        $("#saleAnalysisByProductReport").html('');

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
            url    : '/kookmin/saleAnalysis/saleAnalysisByProduct/saleAnalysisByProduct/getSaleAnalysisByProductList.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {
                    var dataList = response.data.data.list;
                    var loopCnt  = dataList.length;

                    var prodCnt = '';
                    if(dataList.length > 0) {
                        var prodSet = new Set();

                        for (var i = 0; i < dataList.length; i++) {
                            prodSet.add(dataList[i].prodNm);
                        }
                        var prodArr = Array.from(prodSet);
                        if(($scope.data.prodNm !== null && $scope.data.prodNm !== '' && $scope.data.prodNm !== undefined)
                            || ($scope.data.prodCd !== null && $scope.data.prodCd !== '' && $scope.data.prodCd !== undefined)){
                            if(prodArr.length > 1) {
                                prodCnt = prodArr[0] + "외 " + (prodArr.length - 1) + "개";
                            }else{
                                prodCnt = prodArr[0];
                            }
                        }else{
                            prodCnt = '전체';
                        }
                    }

                    var arrSaleAnalysisList = [];
                    var arrList     = [];
                    var pageRowCnt  = 20; // 한 페이지에 표시할 row 수 변수
                    var rowCnt      = 0;
                    var totPageCnt  = Math.ceil(parseInt(loopCnt) / parseInt(pageRowCnt));
                    // 한 페이지에 표시할 row 수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
                    for (var i = 0; i < loopCnt; i++) {
                        if (rowCnt >= pageRowCnt) {
                            rowCnt = 0;
                            arrSaleAnalysisList.push(arrList);
                            arrList = [];
                        }

                        var item = dataList[i];
                        arrList.push(item);
                        rowCnt++;
                    }
                    arrSaleAnalysisList.push(arrList);

                    // 현재 월, 일, 시간 세팅
                    var year               = getCurDate('-').split('-')[0];
                    var month              = getCurDate('-').split('-')[1];
                    var day                = getCurDate('-').split('-')[2];
                    var time               = getCurTime(':');
                    var srchStartDate      = $scope.data.startDate.substring(0,4) + '-' + $scope.data.startDate.substring(4,6) + '-' + $scope.data.startDate.substring(6,8);
                    var srchEndDate        = $scope.data.endDate.substring(0,4) + '-' + $scope.data.endDate.substring(4,6) + '-' + $scope.data.endDate.substring(6,8);
                    var rowNo              = 0;  // row 의 No. 를 위한 변수
                    var arrSaleAnalysisReportList = ''; // 화면에 뿌려질 최종 html 을 담기위한 변수
                    var titleHtml          = ''; // 근로학생별 근무내역 최상단 html
                    var arrSaleAnalysisListHtml       = ''; // 근로학생별 근무내역리스트 html
                    var nextPageHtml       = '<p class="nextPage"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
                    var arrSaleAnalysisListHeaderHtml = '<table class="w100 mt5">' // 근로학생별 근무내역리스트 header html
                        + '<colgroup>'
                        + '<col style="width:4%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:9%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:8%;">'
                        + '<col style="width:7%;">'
                        + '<col style="width:5%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:6%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '<col style="width:4%;">'
                        + '</colgroup>'
                        + '<tr class="tc">'
                        + '<th>No.</th>'
                        + '<th>매장코드</th>'
                        + '<th>매장명</th>'
                        + '<th>상품코드</th>'
                        + '<th>상품명</th>'
                        + '<th>중분류/출판사</th>'
                        + '<th>소분류/저자</th>'
                        + '<th>매입처코드</th>'
                        + '<th>매입처명</th>'
                        + '<th>구분</th>'
                        + '<th>판매가</th>'
                        + '<th>매입가</th>'
                        + '<th>매입율</th>'
                        + '<th>판매수량</th>'
                        + '<th>총매출</th>'
                        + '<th>총할인</th>'
                        + '<th>실매출</th>'
                        + '</tr>';

                    // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
                    for (var i = 0; i < arrSaleAnalysisList.length; i++) {
                        var payHistList = arrSaleAnalysisList[i];
                        // 상품수만큼 for문 돈다
                        for (var j = 0; j < payHistList.length; j++) {
                            var item = payHistList[j];
                            arrSaleAnalysisListHtml += '<tr class="h25">'
                                + '<td class="tc">' + (++rowNo) + '</td>'
                                + '<td class="tc">' + item.storeCd + '</td>'
                                + '<td class="tc">' + item.storeNm + '</td>'
                                + '<td class="tc">' + item.prodCd + '</td>'
                                + '<td class="tc">' + item.prodNm + '</td>'
                                + '<td class="tc">' + nvl(item.mClsNPubNm,'') + '</td>'
                                + '<td class="tc">' + nvl(item.sClsNAuthor,'') + '</td>'
                                + '<td class="tc">' + nvl(item.acquireCd,'') + '</td>'
                                + '<td class="tc">' + nvl(item.acquireNm,'') + '</td>'
                                + '<td class="tc">' + '' + '</td>'
                                + '<td class="tc">' + item.saleAmt + '</td>'
                                + '<td class="tc">' + '' + '</td>'
                                + '<td class="tc">' + '' + '</td>'
                                + '<td class="tc">' + item.totSaleQty + '</td>'
                                + '<td class="tc">' + item.totSaleAmt + '</td>'
                                + '<td class="tc">' + item.totDcAmt + '</td>'
                                + '<td class="tc">' + item.realSaleAmt + '</td>'
                                + '</tr>';
                        }
                        arrSaleAnalysisListHtml += '</table>';

                        titleHtml    = '<table class="w100">'
                            + '<colgroup>'
                            + '<col style="width: 65%">'
                            + '<col style="width: 20%">'
                            + '<col style="width: 15%">'
                            + '</colgroup>'
                            + '<tr>'
                            + '<td class="tl br0"><p class="bk s20">상품별 매출분석 </p></td>'
                            + '</tr>'
                            + '<tr>'
                            + '<td class="tl br0" valign="bottom">기간 : ' + srchStartDate + ' ~ ' + srchEndDate + ' 매입처 : ' + nvl($scope.data.acquireNm, '전체')
                            + ' 지점 : 전체' + ' 점소 : ' + item.storeNm + ' 상품명 : ' + prodCnt +'</td>'
                            + '<td class="tr br0" valign="bottom">출력일자 : ' + month + '-' + day + ' ' + time + '</td>'
                            + '<td class="tr br0" valign="bottom">페이지 : ' + (i + 1) + ' / ' + totPageCnt + '</td>'
                            + '</tr>'
                            + '</table>';
                        arrSaleAnalysisReportList += (i > 0 ? nextPageHtml : '') + titleHtml + arrSaleAnalysisListHeaderHtml + arrSaleAnalysisListHtml;
                        arrSaleAnalysisListHtml = '';
                    }

                    // console.log(arrSaleAnalysisReportList);
                    $('#saleAnalysisByProductReport').append(arrSaleAnalysisReportList);

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
        var view = document.querySelector('#saleAnalysisByProductReport');
        doc.append(view);

        // and print it
        doc.print();
    };

}]);
