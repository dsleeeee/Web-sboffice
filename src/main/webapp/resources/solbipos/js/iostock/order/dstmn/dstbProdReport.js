/** 분배지시서(상품) controller */
app.controller('dstbProdReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dstbProdReportCtrl', $scope, $http, true));

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dstbProdReportCtrl", function (event, data) {
    $scope.strSlipNo = data.strSlipNo;

    $scope.wjDstbProdReportLayer.show(true);

    // dstbProdReport html 내용 초기화
    $("#dstbProdReport").html('');

    $scope.prodList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 전표의 상품 리스트 조회
  $scope.prodList = function () {
    // 로딩바 show
    $scope.$broadcast('loadingPopupActive');

    var params    = {};
    params.slipNo = $scope.strSlipNo;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/dstmn/dstbProdReport/list.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var dataList = response.data.data.list;
          var loopCnt  = dataList.length;

          var arrProdList = [];
          var arrList     = [];
          var pageRowCnt  = 35; // 한 페이지에 표시할 row 수 변수
          var rowCnt      = 0;
          var totPageCnt  = Math.ceil(parseInt(loopCnt) / parseInt(pageRowCnt));
          // 한 페이지에 표시할 row 수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
          for (var i = 0; i < loopCnt; i++) {
            if (rowCnt >= pageRowCnt) {
              rowCnt = 0;
              arrProdList.push(arrList);
              arrList = [];
            }

            var item = dataList[i];
            arrList.push(item);
            rowCnt++;
          }
          arrProdList.push(arrList);

          // 현재 월, 일, 시간 세팅
          var month              = getCurDate('-').split('-')[1];
          var day                = getCurDate('-').split('-')[2];
          var time               = getCurTime(':');
          var rowNo              = 0;  // row 의 No. 를 위한 변수
          var dstbProdReportHtml = ''; // 화면에 뿌려질 최종 html 을 담기위한 변수
          var titleHtml          = ''; // 분배지시서 최상단 html
          var prodListHtml       = ''; // 상품리스트 html
          var nextPageHtml       = '<p class="nextPage"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
          var prodListHeaderHtml = '<table class="w100 mt5">' // 상품리스트 header html
            + '<colgroup>'
            + '<col style="width:5%;">'
            + '<col style="width:15%;">'
            + '<col style="width:45%;">'
            + '<col style="width:10%;">'
            + '<col style="width:5%;">'
            + '<col style="width:5%;">'
            + '<col style="width:15%;">'
            + '</colgroup>'
            + '<tr class="tc">'
            + '<th>No.</th>'
            + '<th>상품코드</th>'
            + '<th>상품명</th>'
            + '<th>단위</th>'
            + '<th colspan="2">수량</th>'
            + '<th>비고</th>'
            + '</tr>';

          // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
          for (var i = 0; i < arrProdList.length; i++) {
            var prodList = arrProdList[i];
            // 상품수만큼 for문 돈다
            for (var j = 0; j < prodList.length; j++) {
              var item = prodList[j];
              prodListHtml += '<tr class="h25">'
                + '<td class="tc">' + (++rowNo) + '</td>'
                + '<td class="tc">' + item.prodCd + '</td>'
                + '<td class="tl"><input type="text" value="' + item.prodNm + '" class="w100" readonly></td>'
                + '<td class="tc">' + item.poUnitFgNm + '</td>'
                + '<td class="tr">' + (item.outUnitQty === 0 ? '' : addComma(item.outUnitQty)) + '</td>'
                + '<td class="tr">' + (item.outEtcQty === 0 ? '' : addComma(item.outEtcQty)) + '</td>'
                // + '<td class="tl"><input type="text" value="' + nvl(item.remark,'') + '" class="w100" readonly></td>'
                + '<td class="tl"></td>'
                + '</tr>';
            }
            prodListHtml += '</table>';

            titleHtml    = '<table class="w100">'
              + '<colgroup>'
              + '<col style="width: 60%">'
              + '<col style="width: 20%">'
              + '<col style="width: 20%">'
              + '</colgroup>'
              + '<tr>'
              + '<td class="tl br0"><p class="bk s20">분배지시서 (상품)</p></td>'
              + '<td class="tc br0" valign="bottom">출력 : ' + month + '-' + day + ' ' + time + '</td>'
              + '<td class="tc br0" valign="bottom">페이지 : ' + (i + 1) + ' / ' + totPageCnt + '</td>'
              + '</tr>'
              + '</table>';
            dstbProdReportHtml += (i > 0 ? nextPageHtml : '') + titleHtml + prodListHeaderHtml + prodListHtml;
            prodListHtml = '';
          }

          // console.log(dstbProdReportHtml);
          $('#dstbProdReport').append(dstbProdReportHtml);

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
      title: '분배지시서(상품)'
    });

    // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
    var browser = navigator.userAgent.toLowerCase();
    if (-1 != browser.indexOf('chrome')) {
      // doc.append('<div style="height: 9mm;"></div>');
    }

    // add content to it
    var view = document.querySelector('#dstbProdReport');
    doc.append(view);

    // and print it
    doc.print();
  };

}]);
