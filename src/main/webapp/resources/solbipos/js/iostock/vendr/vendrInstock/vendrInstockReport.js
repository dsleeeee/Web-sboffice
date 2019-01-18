/** 반출서 controller */
app.controller('vendrInstockReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrInstockReportCtrl', $scope, $http, true));

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("vendrInstockReportCtrl", function (event, data) {
    $scope.slipNo  = data.slipNo;
    $scope.slipFg  = data.slipFg;
    $scope.vendrCd = data.vendrCd;

    // report html 내용 초기화
    $("#report").html('');
    // 인쇄, 엑셀다운 버튼 컨트롤
    $scope.btnPrintIfFg = false;
    // $scope.btnExcelDownIfFg = false;

    $scope.vendrInstockInfo();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 반출정보 조회(반출처, 공급자 정보)
  $scope.vendrInstockInfo = function () {
    // 로딩바 show
    $scope.$broadcast('loadingPopupActive');

    var params     = {};
    params.slipNo  = $scope.slipNo;
    params.slipFg  = $scope.slipFg;
    params.vendrCd = $scope.vendrCd;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/vendr/vendrInstock/vendrInstockReport/vendrInstockReportInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var data = response.data.data;
          // console.log(response.data.data);

          // 등록된 상품이 있을 경우
          if (nvl(data.dtlCnt, 0) > 0) {
            // 인쇄, 엑셀다운 버튼 컨트롤
            $scope.btnPrintIfFg = true;
            // $scope.btnExcelDownIfFg = true;

            $scope.instockDate       = nvl(data.instockDate, '');
            $scope.instockNm         = nvl(data.instockNm, '');
            $scope.instockTelNo      = nvl(data.instockTelNo, '');
            $scope.instockFaxNo      = nvl(data.instockFaxNo, '');
            $scope.instockEmailAddr  = nvl(data.instockEmailAddr, '');
            $scope.instockAddr       = nvl(data.instockAddr, '');
            $scope.supplierNm        = nvl(data.supplierNm, '');
            $scope.supplierTelNo     = nvl(data.supplierTelNo, '');
            $scope.supplierFaxNo     = nvl(data.supplierFaxNo, '');
            $scope.supplierEmailAddr = nvl(data.supplierEmailAddr, '');
            $scope.supplierAddr      = nvl(data.supplierAddr, '');
            $scope.remark            = nvl(data.remark, '');

            // 반출 상품 리스트 조회
            $scope.vendrInstockProdList();
          } else {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(messages['vendrInstock.report.noData']);
            return false;
          }
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


  // 타이틀 정보 html 생성
  $scope.getTitleHtml = function () {
    var titleHtml = '<table class="w100">'
      + '<colgroup>'
      + '<col class="w60">'
      + '<col class="w20">'
      + '<col class="w20">'
      + '</colgroup>'
      + '<tr class="h25" valign="middle"><td rowspan="2" class="br0 tc"><p class="bk s35">' + messages['vendrInstock.report.returnReport'] + '</p><td>' + messages['vendrInstock.report.slipNo'] + '</td><td>' + $scope.slipNo + '</td></tr>'
      + '<tr class="h25"><td>' + messages['vendrInstock.report.returnDate'] + '</td><td>' + getFormatDate($scope.instockDate) + '</td></tr>'
      + '</table>';
    return titleHtml;
  };


  // 반출처, 공급자 정보 html 생성
  $scope.getInstockInfoHtml = function () {
    var instockInfoHtml = '<table class="w100 mt5 br1">'
      + '<colgroup>'
      + '<col style="width: 6%;">'
      + '<col style="width: 44%;">'
      + '<col style="width: 6%;">'
      + '<col style="width: 44%;">'
      + '</colgroup>'
      + '<tr><th colspan="2" class="tc">' + messages['vendrInstock.report.orderPlace'] + '</th><th colspan="2" class="tc">' + messages['vendrInstock.report.supplier'] + '</th></tr>'
      + '<tr class="h20"><td class="br0">' + messages['vendrInstock.report.storeNm'] + '</td><td class="br0"> : ' + $scope.instockNm + '</td><td class="brl1 br0">' + messages['vendrInstock.report.storeNm'] + '</td><td class="br0"> : ' + $scope.supplierNm + '</td></tr>'
      + '<tr class="h20"><td class="br0">' + messages['vendrInstock.report.telNo'] + '</td><td class="br0"> : ' + $scope.instockTelNo + '</td><td class="brl1 br0">' + messages['vendrInstock.report.telNo'] + '</td><td class="br0"> : ' + $scope.supplierTelNo + '</td></tr>'
      + '<tr class="h20"><td class="br0">' + messages['vendrInstock.report.faxNo'] + '</td><td class="br0"> : ' + $scope.instockFaxNo + '</td><td class="brl1 br0">' + messages['vendrInstock.report.faxNo'] + '</td><td class="br0"> : ' + $scope.supplierFaxNo + '</td></tr>'
      + '<tr class="h20"><td class="br0">' + messages['vendrInstock.report.email'] + '</td><td class="br0"> : ' + $scope.instockEmailAddr + '</td><td class="brl1 br0">' + messages['vendrInstock.report.email'] + '</td><td class="br0"> : ' + $scope.supplierEmailAddr + '</td></tr>'
      + '<tr class="h20"><td class="br0">' + messages['vendrInstock.report.addr'] + '</td><td class="br0"> : ' + $scope.instockAddr + '</td><td class="brl1 br0">' + messages['vendrInstock.report.addr'] + '</td><td class="br0"> : ' + $scope.supplierAddr + '</td></tr>'
      + '</table>';
    return instockInfoHtml;
  };


  // 반출상품 조회
  $scope.vendrInstockProdList = function () {
    var params     = {};
    params.slipNo  = $scope.slipNo;
    params.slipFg  = $scope.slipFg;
    params.vendrCd = $scope.vendrCd;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/vendr/vendrInstock/vendrInstockProd/list.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var dataList = response.data.data.list;
          var loopCnt  = dataList.length;

          var arrProdList = [];
          var arrList     = [];
          var pageRowCnt  = 21; // 한 페이지에 표시할 row 수 변수
          var rowCnt      = 0;
          var totPageCnt  = Math.ceil(parseInt(loopCnt) / parseInt(pageRowCnt));
          var totAmt      = 0;
          // 한 페이지에 표시할 row 수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
          for (var i = 0; i < loopCnt; i++) {
            if (rowCnt >= pageRowCnt) {
              rowCnt = 0;
              arrProdList.push(arrList);
              arrList = [];
            }

            var item = dataList[i];
            totAmt   = parseInt(totAmt) + parseInt(item.inTot); // 반출상품 총합계금액
            arrList.push(item);
            rowCnt++;
          }
          arrProdList.push(arrList);

          var reportHtml         = ''; // 화면에 뿌려질 최종 html 을 담기위한 변수
          var titleHtml          = $scope.getTitleHtml(); // 반출서 최상단 html
          var instockInfoHtml    = $scope.getInstockInfoHtml(); // 반출처, 공급자 정보 html
          var prodListHtml       = ''; // 상품리스트 html
          var footerHtml         = ''; // 반출서 최하단 정보 html (비고, 페이지카운트)
          var nextPageHtml       = '<p class="nextPage"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
          var prodListHeaderHtml = '<table class="w100 mt5">' // 상품리스트 header html
            + '<colgroup>'
            + '<col style="width:5%;">'
            + '<col style="width:55%;">'
            + '<col style="width:10%;">'
            + '<col style="width:10%;">'
            + '<col style="width:7%;">'
            + '<col style="width:13%;">'
            + '</colgroup>'
            + '<tr class="tc">'
            + '<th>' + messages['vendrInstock.report.num'] + '</th>'
            + '<th>' + messages['vendrInstock.report.prodNm'] + '</th>'
            + '<th>' + messages['vendrInstock.report.poUnitFg'] + '</th>'
            + '<th>' + messages['vendrInstock.report.uprc'] + '</th>'
            + '<th>' + messages['vendrInstock.report.qty'] + '</th>'
            + '<th>' + messages['vendrInstock.report.amt'] + '</th>'
            + '</tr>';

          // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
          for (var i = 0; i < arrProdList.length; i++) {
            var prodList   = arrProdList[i];
            var pageTotAmt = 0;
            // 한 페이지에 표시할 row 수만큼 for문 돈다
            for (var j = 0; j < pageRowCnt; j++) {
              // 상품에 대한 데이터가 없더라도 row 는 만들어줌
              if (nvl(prodList[j], '') === '') {
                prodListHtml += '<tr class="h25">'
                  + '<td class="tc">' + (j + 1) + '</td>'
                  + '<td></td>'
                  + '<td></td>'
                  + '<td></td>'
                  + '<td></td>'
                  + '<td></td>'
                  + '</tr>';
              } else {
                var item   = prodList[j];
                var prodNm = '[' + item.prodCd + '] ' + item.prodNm;
                prodListHtml += '<tr class="h25">'
                  + '<td class="tc">' + (j + 1) + '</td>'
                  + '<td class="tl"><input type="text" value="' + prodNm + '" class="w100" readonly></td>'
                  + '<td class="tc">' + item.poUnitFgNm + '</td>'
                  + '<td class="tr">' + addComma(item.costUprc) + '</td>'
                  + '<td class="tr">' + addComma(item.inTotQty) + '</td>'
                  + '<td class="tr">' + addComma(item.inTot) + '</td>'
                  + '</tr>';
                pageTotAmt = parseInt(pageTotAmt) + parseInt(item.inTot);
              }
            }
            prodListHtml += '<tr class="h25"><td colspan="5" class="tr">' + messages['vendrInstock.report.pageSumAmt'] + '</td><td class="tr">' + addComma(pageTotAmt) + '</td></tr>'
              + '<tr class="h25"><td colspan="5" class="tr">' + messages['vendrInstock.report.totSumAmt'] + '</td><td class="tr">' + addComma(totAmt) + '</td></tr>'
              + '</table>';
            // 비고가 5줄 이상 넘어가는 경우 5줄까지만 보여주고 나머지 줄은 ... 으로 표시.
            var remark      = $scope.remark.replace(/\n/gi, '<br>');
            var remarkLimit = 5;
            if (remark.split('<br>').length > remarkLimit) {
              remark = remark.split('<br>').slice(0, remarkLimit).join('<br>');
              remark += '...';
            }
            footerHtml   = '<table class="w100">'
              + '<tr class="h100">'
              + '<td class="brt0 tc w15">' + messages['vendrInstock.report.remark'] + '</td>'
              + '<td class="brt0 tl w85">' + remark + '</td>' // 비고 줄이 길경우 어떻게 표시할지 논의 필요
              + '</tr>'
              + '<tr>'
              + '<td class="tc br0" colspan="2"><p class="mt5 s12 bk">' + (i + 1) + ' / ' + totPageCnt + '</p></td>'
              + '</tr>'
              + '</table>';
            reportHtml += (i > 0 ? nextPageHtml : '') + titleHtml + instockInfoHtml + prodListHeaderHtml + prodListHtml + footerHtml;
            prodListHtml = '';
          }

          // console.log(reportHtml);
          $('#report').append(reportHtml);

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

    });
  };


  // 반출서 인쇄
  $scope.print = function () {
    // create document
    var doc = new wijmo.PrintDocument({
      title: '반출서'
    });

    // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
    var browser = navigator.userAgent.toLowerCase();
    if (-1 != browser.indexOf('chrome')) {
      // doc.append('<div style="height: 9mm;"></div>');
    }

    // add content to it
    var view = document.querySelector('#report');
    doc.append(view);

    // and print it
    doc.print();
  };


  // 반출서 엑셀다운
  $scope.excelDown = function () {
  };

}]);
