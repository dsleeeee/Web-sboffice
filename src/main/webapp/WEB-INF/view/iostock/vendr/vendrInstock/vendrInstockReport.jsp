<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrInstock/vendrInstockReport/"/>

<style type="text/css">
  .h25 {height: 25px;}
  .h30 {height: 30px;}
  .pdb2 {padding-bottom: 2px;}
</style>

<div id="reportView" style="display: none;" ng-controller="vendrInstockReportCtrl">
  <div class="mt5 tr">
    <%-- 반출서 인쇄 --%>
    <button type="button" class="btn_skyblue ml5" id="btnPrint" ng-click="print()" ng-if="btnPrintIfFg">
      <s:message code="vendrInstock.report.print"/></button>
    <%-- 반출서 엑셀다운 --%>
    <%--<button type="button" class="btn_skyblue ml5" id="btnExcelDown" ng-click="excelDown()" ng-if="btnExcelDownIfFg">--%>
    <%--<s:message code="vendrInstock.report.excelDown"/></button>--%>
  </div>

  <div class="w100 mt10" id="report">
  </div>
</div>


<script type="text/javascript">
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
              $scope._popMsg(messages['vendrInstock.report.noData']);
              return false;
            }
          }
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
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
        + '<tr class="h25" valign="middle"><td rowspan="2" align="center"><p style="font-size:35px;">' + messages['vendrInstock.report.returnReport'] + '</p><td class="b4 pd5">' + messages['vendrInstock.report.slipNo'] + '</td><td class="b4 pd5">' + $scope.slipNo + '</td></tr>'
        + '<tr class="h25"><td class="b4 pd5">' + messages['vendrInstock.report.returnDate'] + '</td><td class="b4 pd5">' + getFormatDate($scope.instockDate) + '</td></tr>'
        + '</table>';
      return titleHtml;
    };


    // 반출처, 공급자 정보 html 생성
    $scope.getInstockInfoHtml = function () {
      var instockInfoHtml = '<table class="w100 mt5 b4 pd5">'
        + '<colgroup>'
        + '<col class="w10">'
        + '<col class="w40">'
        + '<col class="w10">'
        + '<col class="w40">'
        + '</colgroup>'
        + '<tr class="h30"><th align="center" colspan="2" class="b4 pd5">' + messages['vendrInstock.report.orderPlace'] + '</th><th align="center" colspan="2" class="b4 pd5">' + messages['vendrInstock.report.supplier'] + '</th></tr>'
        + '<tr><td class="s12 pdl5 pdb2 pdt5">' + messages['vendrInstock.report.storeNm'] + '</td><td class="s12 pdb2 pdt5"> : ' + $scope.instockNm + '</td><td class="s12 bl pdl5 pdb2 pdt5">' + messages['vendrInstock.report.storeNm'] + '</td><td class="s12 pdb2 pdt5"> : ' + $scope.supplierNm + '</td></tr>'
        + '<tr><td class="s12 pdl5 pdb2">' + messages['vendrInstock.report.telNo'] + '</td><td class="s12 pdb2"> : ' + $scope.instockTelNo + '</td><td class="s12 bl pdl5 pdb2">' + messages['vendrInstock.report.telNo'] + '</td><td class="s12 pdb2"> : ' + $scope.supplierTelNo + '</td></tr>'
        + '<tr><td class="s12 pdl5 pdb2">' + messages['vendrInstock.report.faxNo'] + '</td><td class="s12 pdb2"> : ' + $scope.instockFaxNo + '</td><td class="s12 bl pdl5 pdb2">' + messages['vendrInstock.report.faxNo'] + '</td><td class="s12 pdb2"> : ' + $scope.supplierFaxNo + '</td></tr>'
        + '<tr><td class="s12 pdl5 pdb2">' + messages['vendrInstock.report.email'] + '</td><td class="s12 pdb2"> : ' + $scope.instockEmailAddr + '</td><td class="s12 bl pdl5 pdb2">' + messages['vendrInstock.report.email'] + '</td><td class="s12 pdb2"> : ' + $scope.supplierEmailAddr + '</td></tr>'
        + '<tr><td class="s12 pdl5 pdb5">' + messages['vendrInstock.report.addr'] + '</td><td class="s12 pdb5"> : ' + $scope.instockAddr + '</td><td class="s12 bl pdl5 pdb5">' + messages['vendrInstock.report.addr'] + '</td><td class="s12 pdb5"> : ' + $scope.supplierAddr + '</td></tr>'
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
            var prodListCnt = 25; // 반출서 한 페이지에 표시할 상품수 변수
            var rowCnt      = 0;
            var totPageCnt  = Math.ceil(parseInt(loopCnt) / parseInt(prodListCnt));
            var totAmt      = 0;
            // 한 페이지에 표시할 상품수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
            for (var i = 0; i < loopCnt; i++) {
              if (rowCnt >= prodListCnt) {
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
            var nextPageHtml       = '<p style="page-break-before:always"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
            var prodListHeaderHtml = '<table class="w100 mt5 b4">' // 상품리스트 header html
              + '<colgroup>'
              + '<col style="width:5%;">'
              + '<col style="width:45%;">'
              + '<col style="width:10%;">'
              + '<col style="width:10%;">'
              + '<col style="width:7%;">'
              + '<col style="width:13%;">'
              + '</colgroup>'
              + '<tr align="center" class="h30">'
              + '<th class="b4 pd5">' + messages['vendrInstock.report.num'] + '</th>'
              + '<th class="b4 pd5">' + messages['vendrInstock.report.prodNm'] + '</th>'
              + '<th class="b4 pd5">' + messages['vendrInstock.report.poUnitFg'] + '</th>'
              + '<th class="b4 pd5">' + messages['vendrInstock.report.uprc'] + '</th>'
              + '<th class="b4 pd5">' + messages['vendrInstock.report.qty'] + '</th>'
              + '<th class="b4 pd5">' + messages['vendrInstock.report.amt'] + '</th>'
              + '</tr>';

            // 데이터 조회 후 한 페이지에 표시할 상품수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
            for (var i = 0; i < arrProdList.length; i++) {
              var prodList   = arrProdList[i];
              var pageTotAmt = 0;
              // 한 페이지에 표시할 상품수만큼 for문 돈다
              for (var j = 0; j < prodListCnt; j++) {
                // 상품에 대한 데이터가 없더라도 row 는 만들어줌
                if (nvl(prodList[j], '') === '') {
                  prodListHtml += '<tr class="h25">'
                    + '<td class="b4 tc s12 pd5">' + (j + 1) + '</td>'
                    + '<td class="b4 pd5"></td>'
                    + '<td class="b4 pd5"></td>'
                    + '<td class="b4 pd5"></td>'
                    + '<td class="b4 pd5"></td>'
                    + '<td class="b4 pd5"></td>'
                    + '</tr>';
                } else {
                  var item    = prodList[j];
                  var prodNm  = '[' + item.prodCd + '] ' + item.prodNm;
                  var nmLimit = 50; // 상품명이 해당 변수의 길이보다 긴 경우 변수의 길이만큼 자른 후 뒤에 .. 을 붙여 표시한다.
                  if (prodNm.length > nmLimit) {
                    prodNm = prodNm.substr(0, nmLimit) + '..';
                  }
                  // console.log(prodList[j]);
                  prodListHtml += '<tr class="h25">'
                    + '<td class="b4 tc s12 pd5">' + (j + 1) + '</td>'
                    + '<td class="b4 tl s12 pd5">' + prodNm + '</td>'
                    + '<td class="b4 tc s12 pd5">' + item.poUnitFgNm + '</td>'
                    + '<td class="b4 tr s12 pd5">' + addComma(item.costUprc) + '</td>'
                    + '<td class="b4 tr s12 pd5">' + addComma(item.inTotQty) + '</td>'
                    + '<td class="b4 tr s12 pd5">' + addComma(item.inTot) + '</td>'
                    + '</tr>';
                  pageTotAmt = parseInt(pageTotAmt) + parseInt(item.inTot);
                }
              }
              prodListHtml += '<tr class="h25"><td colspan="5" class="b4 tr pd5">' + messages['vendrInstock.report.pageSumAmt'] + '</td><td class="b4 tr pd5">' + addComma(pageTotAmt) + '</td></tr>'
                + '<tr class="h25"><td colspan="5" class="b4 tr pd5">' + messages['vendrInstock.report.totSumAmt'] + '</td><td class="b4 tr pd5">' + addComma(totAmt) + '</td></tr>'
                + '</table>';
              // 비고가 5줄 이상 넘어가는 경우 5줄까지만 보여주고 나머지 줄은 ... 으로 표시.
              var remark      = $scope.remark.replace(/\n/gi, '<br>');
              var remarkLimit = 5;
              if (remark.split('<br>').length > remarkLimit) {
                remark = remark.split('<br>').slice(0, remarkLimit).join('<br>');
                remark += '<br>...';
              }
              footerHtml   = '<table class="w100">'
                + '<tr  style="height:100px;">'
                + '<td class="bl bb br tc w15 pd5">' + messages['vendrInstock.report.remark'] + '</td>'
                + '<td class="bl bb br tl w85 s12 pd5">' + remark + '</td>' // 비고 줄이 길경우 어떻게 표시할지 논의 필요
                + '</tr>'
                + '<tr class="h25">'
                + '<td class="tc s12" colspan="2">' + (i + 1) + ' / ' + totPageCnt + '</td>'
                + '</tr>'
                + '</table>';
              reportHtml += (i > 0 ? nextPageHtml : '') + titleHtml + instockInfoHtml + prodListHeaderHtml + prodListHtml + footerHtml;
              prodListHtml = '';
            }

            $('#report').append(reportHtml);
          }
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
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
      var doc  = new wijmo.PrintDocument({
        title: '반출서'
      });
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
</script>

<%--<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrInstock/vendrInstockReport.js?ver=20181227.01" charset="utf-8"></script>--%>
