<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/cmmExcelUpload/excelUpload/excelUpload/"/>

<div style="display: none;" ng-controller="excelUploadCtrl">
    <input type="file" class="form-control" id="excelUpFile"
           ng-model="excelUpFile"
           onchange="angular.element(this).scope().excelFileChanged()"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

    <input type="file" class="form-control" id="memberExcelUpload"
           ng-model="memberExcelUpload"
           ng-controller="memberExcelUploadCtrl"
           onchange="angular.element(this).scope().memberExcelUpload()"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

    <input type="file" class="form-control" id="memberPointUpload"
           ng-model="memberPointUpload"
           ng-controller="memberPointCtrl"
           onchange="angular.element(this).scope().memberPointUpload()"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

    <input type="file" class="form-control" id="textUpFile"
           ng-model="textUpFile"
           onchange="angular.element(this).scope().textFileChanged()"
           accept=".txt"/>

    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px;">
        <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="excelUpload.prodBarcdCd"/>" binding="prodBarcdCd" width="100"
                                 align="center" data-type="String"
                                 visible="{{prodBarcdCdVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUpload.prodCd"/>" binding="prodCd" width="100"
                                 align="center" visible="{{prodCdVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUpload.barcdCd"/>" binding="barcdCd" width="100"
                                 align="center" visible="{{barcdCdVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUpload.unitQty"/>" binding="unitQty" width="70"
                                 align="right" data-type="Number" format="n0"
                                 visible="{{unitQtyVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUpload.etcQty"/>" binding="etcQty" width="70"
                                 align="right" data-type="Number" format="n0"
                                 visible="{{etcQtyVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUpload.qty"/>" binding="qty" width="70" align="right"
                                 data-type="Number" format="n0" visible="{{qtyVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUpload.uprc"/>" binding="uprc" width="70" align="right"
                                 visible="{{uprcVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUpload.remark"/>" binding="remark" width="70"
                                 align="left" data-type="String" visible="{{remarkVisibleFg}}"></wj-flex-grid-column>

            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" data-type="Boolean" binding="gChk"
                                 visible="{{gChk}}"
                                 width="40" align="center" is-read-only="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.check.result"/>" binding="result"
                                 visible="{{result}}"
                                 width="115" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.check.result"/>" binding="memberResult"
                                 visible="{{memberResult}}"
                                 width="115" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.nm.kr"/>" binding="membrNm" data-type="String"
                                 visible="{{membrNm}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.nm.en"/>" binding="memberEngNm"
                                 data-type="String" visible="{{memberEngNm}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.membrClassCd"/>" binding="membrClassCd"
                                 data-type="String" visible="{{membrClassCd}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.membrNo"/>" binding="membrNo" data-type="String"
                                 visible="{{membrNo}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.store"/>" binding="membrStore" data-type="String"
                                 visible="{{membrStore}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.gendrFg"/>" binding="gendrFg" data-type="String"
                                 visible="{{gendrFg}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.membrCardNo"/>" binding="membrCardNo"
                                 data-type="String" visible="{{membrCardNo}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.birthday"/>" binding="birthday"
                                 data-type="String" visible="{{birthday}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.weddingYn"/>" binding="weddingYn"
                                 data-type="String" visible="{{weddingYn}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.weddingday"/>" binding="weddingday"
                                 data-type="String" visible="{{weddingday}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.telNo"/>" binding="memberTelNo"
                                 data-type="String" visible="{{memberTelNo}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <%-- <wj-flex-grid-column header="<s:message code="member.excel.phonneNo"/>" binding="phonneNo" width="100"
                                 align="left"
                                 data-type="String" visible="{{phonneNo}}"></wj-flex-grid-column> --%>
            <wj-flex-grid-column header="<s:message code="member.excel.shortNo"/>" binding="memberShortNo"
                                 data-type="String" visible="{{memberShortNo}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.email"/>" binding="memberEmail"
                                 data-type="String" visible="{{memberEmail}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.postNo"/>" binding="memberPostNo"
                                 data-type="String" visible="{{memberPostNo}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.addr"/>" binding="memberAddr" data-type="String"
                                 visible="{{memberAddr}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.addrDtl"/>" binding="memberAddrDtl"
                                 data-type="String" visible="{{memberAddrDtl}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.emailRecvYn"/>" binding="emailRecvYn"
                                 data-type="String" visible="{{emailRecvYn}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.smsRecvYn"/>" binding="smsRecvYn"
                                 data-type="String" visible="{{smsRecvYn}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.avablPoint"/>" binding="avablPoint"
                                 data-type="String" visible="{{avablPoint}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="member.excel.totPoint"/>" binding="totSavePoint"
                                 data-type="String" visible="{{totSavePoint}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="memberPoint.totAdjPoint"/>" binding="totAdjPoint"
                                 data-type="String" visible="{{totAdjPoint}}"
                                 width="100" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="memberPoint.totAdjPointAfter"/>" binding="totAdjPointAfter"
                                 data-type="String" visible="{{totAdjPointAfter}}"
                                 width="100" align="left">
            </wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="memberPoint.totAdjPointAfter"/>" binding="tmpTotAdjPoint"
                                 data-type="String" visible="{{tmpTotAdjPoint}}"
                                 width="100" align="left"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
</div>

<script type="text/javascript">
    /**
     * get application
     */
    var app = agrid.getApp();
    app.config(['$qProvider', function ($qProvider) {
        $qProvider.errorOnUnhandledRejections(false);
    }]);

    /** 엑셀업로드 그리드 controller */
    app.controller('excelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('excelUploadCtrl', $scope, $http, true));


        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            $scope.gridVisibleDefault();

            // 컬럼헤더:바인딩명 형태의 JSON 데이터 생성.
            $scope.colHeaderBind = {};
            for (var i = 0; i < $scope.flex.columns.length; i++) {
                var col = $scope.flex.columns[i];
                $scope.colHeaderBind[col.header] = col.binding;
            }
        };

        $scope.itemFormatter = function (panel, r, c, cell) {
            // 컬럼헤더 merged 의 헤더타이틀 중앙(vertical) 정렬
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                var mRange = $scope.flex.getMergedRange(panel, r, c);
                if (mRange) {
                    cell.innerHTML = '<div class=\"wj-header merged-custom\">' + cell.innerHTML + '</div>';
                }
            }

            var flex = panel.grid;
            var column = flex.columns[c];

            if (column) {
                if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
                    // create and initialize checkbox
                    if (column.format === 'checkBoxText') {
                        cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
                            + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
                    } else {
                        cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
                    }
                }
            }
        };


        // 그리드 컬럼 visible 초기화
        $scope.gridVisibleDefault = function () {
            $scope.prodBarcdCdVisibleFg = true; // 상품코드/바코드
            $scope.prodCdVisibleFg = true; // 상품코드
            $scope.barcdCdVisibleFg = true; // 바코드
            $scope.unitQtyVisibleFg = true; // 단위수량
            $scope.etcQtyVisibleFg = true; // 낱개수량
            $scope.qtyVisibleFg = true; // 수량
            $scope.uprcVisibleFg = true; // 단가
            $scope.remarkVisibleFg = true; // 비고
            $scope.gChk = true;
        };


        // 다른 컨트롤러의 broadcast 받기
        // $scope.$on("excelUploadCtrl", function (event, data) {

        // 기능수행 종료 : 반드시 추가
        //   event.preventDefault();
        // });


        // 엑셀양식 다운로드
        $scope.excelFormDownload = function (uploadFg) {
            $scope.uploadFg = uploadFg;

            $scope.addRow();

            var excelFileName = "excelForm";
            if ($scope.uploadFg === "memberExcel") {
                excelFileName = "회원엑셀업로드";
            } else if ($scope.uploadFg === "memberPoint") {
                excelFileName = "회원포인트조정";
            }

            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                    includeColumnHeaders: true,
                    includeCellStyles: false,
                    formatItem: saveFormatItem,
                    includeColumns: function (column) {
                        return column.visible;
                    }
                }, excelFileName +'.xlsx');
            }, 10);
        };

        function saveFormatItem(args) {
            let p = args.panel, row = args.row, col = args.col, xlsxCell = args.xlsxCell, cell, color;
            if (p.cellType === wijmo.grid.CellType.Cell) {
                if (p.columns[col].binding === 'membrNo') {
                    if (xlsxCell.value) {
                        xlsxCell.style.format = "@"
                    }
                }
            }
        };

        // 엑셀 양식 다운로드시 1줄 생성하여 어떤값 넣어야할지 양식생성.
        $scope.addRow = function () {
            // 그리드 초기화
            var flex = $scope.flex;
            flex.itemsSource = new wijmo.collections.CollectionView();
            flex.collectionView.trackChanges = true;

            var params = {};
            // 주문등록, 반품등록
            if ($scope.uploadFg === 'order') {
                $scope.prodBarcdCdVisibleFg = true;  // 상품코드/바코드
                $scope.prodCdVisibleFg = false; // 상품코드
                $scope.barcdCdVisibleFg = false; // 바코드
                $scope.unitQtyVisibleFg = true;  // 단위수량
                $scope.etcQtyVisibleFg = true;  // 낱개수량
                $scope.qtyVisibleFg = false; // 수량
                $scope.uprcVisibleFg = false; // 단가
                $scope.remarkVisibleFg = false; // 비고

                params.prodBarcdCd = '상품코드입력';
                params.unitQty = 0;
                params.etcQty = 0;
            }
            // 분배마감, 반품마감
            else if ($scope.uploadFg === 'dstbCloseStore') {
                $scope.prodBarcdCdVisibleFg = true;  // 상품코드1바코드
                $scope.prodCdVisibleFg = false; // 상품코드
                $scope.barcdCdVisibleFg = false; // 바코드
                $scope.unitQtyVisibleFg = true;  // 단위수량
                $scope.etcQtyVisibleFg = true;  // 낱개수량
                $scope.qtyVisibleFg = false; // 수량
                $scope.uprcVisibleFg = true;  // 단가
                $scope.remarkVisibleFg = false; // 비고

                params.prodBarcdCd = '상품코드입력';
                params.unitQty = 0;
                params.etcQty = 0;
                params.uprc = 0;
            }
            // 실사,조정,폐기
            else if ($scope.uploadFg === 'acins' || $scope.uploadFg === 'adj' || $scope.uploadFg === 'disuse') {
                $scope.prodBarcdCdVisibleFg = true;  // 상품코드/바코드
                $scope.prodCdVisibleFg = false; // 상품코드
                $scope.barcdCdVisibleFg = false; // 바코드
                $scope.unitQtyVisibleFg = false; // 단위수량
                $scope.etcQtyVisibleFg = false; // 낱개수량
                $scope.qtyVisibleFg = true;  // 수량
                $scope.uprcVisibleFg = false; // 단가
                $scope.remarkVisibleFg = true;  // 비고

                params.prodBarcdCd = '상품코드입력';
                params.qty = 0;
            }
            // 거래처 발주, 거래처 입고
            else if ($scope.uploadFg === 'vendr') {
                $scope.prodBarcdCdVisibleFg = true;  // 상품코드/바코드
                $scope.prodCdVisibleFg = false; // 상품코드
                $scope.barcdCdVisibleFg = false; // 바코드
                $scope.unitQtyVisibleFg = true;  // 단위수량
                $scope.etcQtyVisibleFg = true;  // 낱개수량
                $scope.qtyVisibleFg = false; // 수량
                $scope.uprcVisibleFg = true;  // 단가
                $scope.remarkVisibleFg = false; // 비고

                params.prodBarcdCd = '상품코드입력';
                params.unitQty = 0;
                params.etcQty = 0;
                params.uprc = 0;
            }
            // 회원excel업로드
            else if ($scope.uploadFg === 'memberExcel') {
                $scope.prodBarcdCdVisibleFg = false; // 상품코드/바코드
                $scope.prodCdVisibleFg = false; // 상품코드
                $scope.barcdCdVisibleFg = false; // 바코드
                $scope.unitQtyVisibleFg = false; // 단위수량
                $scope.etcQtyVisibleFg = false; // 낱개수량
                $scope.qtyVisibleFg = false; // 수량
                $scope.uprcVisibleFg = false; // 단가
                $scope.remarkVisibleFg = false; // 비고
                $scope.gChk = false;
                $scope.result = false;
                $scope.membrNm = true; // 회원명(한글)
                $scope.memberEngNm = true; // 회원명(영문)
                $scope.membrClassCd = true; // 회원등급분류
                $scope.membrStore = true; // 등록매장
                $scope.gendrFg = true; // 성별구분
                $scope.membrCardNo = true; // 회원카드번호
                $scope.birthday = true; // 생년월일
                $scope.weddingYn = true; // 결혼여부
                $scope.weddingday = true; // 결혼기념일
                $scope.memberTelNo = true; // 전화번호
                // $scope.phonneNo = false; // 핸드폰번호
                $scope.memberShortNo = true; // 단축번호
                $scope.memberEmail = true; // E-MAIL
                $scope.memberPostNo = true; // 우편번호
                $scope.memberAddr = true; // 주소
                $scope.memberAddrDtl = true; // 상세주소
                $scope.emailRecvYn = true; // 이메일수신
                $scope.smsRecvYn = true; // SMS수신
                $scope.avablPoint = true; // 가용포인트
                $scope.totSavePoint = true; // 누적포인트
                //양식 샘플 초기값
                // params.membrClassCd = '기본';
                // params.membrStore = '매장명';
                var membrClassCd = memberClassList[0].name;
                var membrStore = regstrStoreList[0].name;
                params.membrClassCd = membrClassCd;
                params.membrStore = membrStore;
                params.gendrFg = '남자';
                params.weddingYn = '미혼';
                params.emailRecvYn = '미수신';
                params.smsRecvYn = '미수신';
                params.avablPoint = 0;
                params.totSavePoint = 0;
            }
            // 회원포인트조정
            else if ($scope.uploadFg === 'memberPoint') {
                $scope.prodBarcdCdVisibleFg = false; // 상품코드/바코드
                $scope.prodCdVisibleFg = false; // 상품코드
                $scope.barcdCdVisibleFg = false; // 바코드
                $scope.unitQtyVisibleFg = false; // 단위수량
                $scope.etcQtyVisibleFg = false; // 낱개수량
                $scope.qtyVisibleFg = false; // 수량
                $scope.uprcVisibleFg = false; // 단가
                $scope.remarkVisibleFg = false; // 비고
                $scope.gChk = false;
                $scope.memberResult = false;
                $scope.membrClassCd = false;
                $scope.membrNo = true;
                $scope.membrNm = true;
                $scope.membrCardNo = true;
                $scope.avablPoint = false;
                $scope.totAdjPoint = true;
                $scope.totAdjPointAfter = false;
                $scope.tmpTotAdjPoint = false;
                params.membrNo = "0000000001"
                params.totAdjPoint = 0
            }
            var newRow = flex.collectionView.addNew();
            for (var prop in params) {
                newRow[prop] = params[prop];
            }
            flex.collectionView.commitNew();
        };

        // 엑셀파일이 변경된 경우
        $scope.excelFileChanged = function () {
            if ($('#excelUpFile')[0].files[0]) {
                // 엑셀업로드 전 현재 세션ID 와 동일한 자료를 삭제한다.
                $scope.deleteData('excel');
            }
        };


        // 텍스트파일이 변경된 경우
        $scope.textFileChanged = function () {
            if ($('#textUpFile')[0].files[0]) {
                // 업로드 전 현재 세션ID 와 동일한 자료를 삭제한다.
                $scope.deleteData('text');
            }
        };


        // 현재 세션ID 와 동일한 데이터 삭제
        $scope.deleteData = function (upFg) {
            var params = {};
            $http({
                method: 'POST', //방식
                url: "/iostock/cmmExcelUpload/excelUpload/excelUpload/delete.sb", /* 통신할 URL */
                params: params, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                if ($scope._httpStatusCheck(response, true)) {
                }
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope._popMsg(response.data.message);
                return false;
            }).then(function () {
                // 'complete' code here
                if (upFg === 'excel') {
                    $scope.excelUpload(); // 삭제 완료 된 후 엑셀업로드 호출
                } else if (upFg === 'text') {
                    $scope.textUpload(); // 삭제 완료 된 후 텍스트업로드 호출
                }
            });
        };


        // 텍스트 업로드
        $scope.textUpload = function () {
            $scope.excelTextFg = 'text';
            // 업로드 progress 관련 기본값 세팅
            $scope.stepCnt = 100; // 한번에 DB에 저장할 숫자 세팅
            $scope.progressCnt = 0;   // 처리된 숫자

            // 선택한 파일이 있을 경우
            if ($('#textUpFile')[0].files[0]) {
                var file = $('#textUpFile')[0].files[0];
                var fileName = file.name;
                var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

                // 확장자 체크. 확장자가 txt가 아닌 경우 오류메시지
                if (fileExtension.toLowerCase() !== '.txt') {
                    $("#textUpFile").val('');
                    $scope._popMsg(messages['excelUpload.not.textFile']); // 텍스트 파일만 업로드 됩니다.(*.txt)
                    return false;
                }

                var fr = new FileReader();

                fr.onloadend = function (e) {
                    // var data = e.target.result;
                    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
                    $timeout(function () {
                        var uploadData = '';
                        var bytes = new Uint8Array(e.target.result);
                        var length = bytes.byteLength;
                        for (var i = 0; i < length; i++) {
                            // 값이 스페이스인 경우 건너뜀.
                            if (bytes[i] === 32) {
                                continue;
                            }

                            // 마지막 데이터인 경우
                            if (i === (length - 1)) {
                                // 마지막 데이터가 엔터가 아닌 경우 json 형식 데이터 만들때 split 해서 쓰기 위해 enter 값을 마지막에 추가해준다.
                                if (bytes[i] !== 13) {
                                    uploadData += String.fromCharCode(bytes[i]);
                                    uploadData += String.fromCharCode(13);
                                    // uploadData += '\n';
                                }
                            } else {
                                uploadData += String.fromCharCode(bytes[i]);
                            }
                        }

                        // 읽어온 파일데이터가 null 이 아닌 경우
                        if (nvl(uploadData, '') !== '') {
                            var jsonData = $scope.textUploadToJsonConvert(uploadData);

                            if (jsonData.length > 0) {
                                $timeout(function () {
                                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                                    $scope.save(jsonData);
                                }, 10);
                            } else {
                                $scope._popMsg(messages['excelUpload.noData'], function () {
                                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                                }); // 업로드 할 데이터가 없습니다.
                            }
                        } else {
                            $scope._popMsg(messages['excelUpload.noData'], function () {
                                $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                            }); // 업로드 할 데이터가 없습니다.
                        }
                    }, 10);
                }

                // r.readAsBinaryString(file);
                fr.readAsArrayBuffer(file);
            }
        };


        // 텍스트 업로드 한 데이터를 JSON 형태로 변경한다.
        $scope.textUploadToJsonConvert = function (uploadData) {
            var uploadDataArr = uploadData.split(String.fromCharCode(13));
            // var uploadDataArr       = uploadData.split('\n');
            var uploadDataArrLength = uploadDataArr.length;
            var jsonData = [];
            var item = {};
            var columnNum = 2; // 텍스트 업로드시 1줄의 JSON 데이터 컬럼 수 설정

            for (var i = 0; i < uploadDataArrLength; i++) {
                // String.fromCharCode(13) 으로 replace 를 하면 제대로 되지 않음..그래서 \n으로 replace 함..
                if (nvl(uploadDataArr[i].replace(/\n/gi, ''), '') !== '') {
                    // if (nvl(uploadDataArr[i].replace(/String.fromCharCode(13)/gi, ''), '') !== '') {
                    // var data          = uploadDataArr[i].replace(/String.fromCharCode(13)/gi, '');
                    var data = uploadDataArr[i].replace(/\n/gi, '').trim();
                    var dataArr = data.split(',');
                    var dataArrLength = dataArr.length;
                    item = {};

                    // 1줄의 데이터를 , 로 split 한 자료의 길이가 columnNum 보다 작은 경우 수량 없음 오류 메시지
                    if (dataArrLength < columnNum) {
                        $scope._popMsg(messages['excelUpload.not.qty']); // 업로드 데이터 중 수량이 없는 데이터가 존재합니다.
                        jsonData = [];
                        break;
                    }

                    for (var j = 0; j < dataArrLength; j++) {
                        var value = nvl(dataArr[j], '').trim();
                        if (value !== '') {
                            //1줄의 데이터가 columnNum 보다 많은 경우 양식이 이상한 것이므로 for문 종료
                            if (j >= columnNum) break;

                            if (j % columnNum === 0) {
                                item.prodBarcdCd = value;
                            } else if (j % columnNum === 1) {
                                item.qty = value;
                            }
                        }
                    }

                    jsonData.push(item);
                }
            }

            return jsonData;
        };

        // 회원엑셀업로드
        $scope.memberExcelUpload = function () {
            // 선택한 파일이 있으면
            if ($('#memberExcelUpload')[0].files[0]) {
                var file = $('#memberExcelUpload')[0].files[0];
                var fileName = file.name;
                var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

                // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
                if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
                    $timeout(function () {
                        var flex = $scope.flex;
                        wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#memberExcelUpload')[0].files[0], {includeColumnHeaders: true}
                            , function (workbook) {
                                $timeout(function () {
                                    $scope.memberExcelUploadToJsonConvert();
                                }, 10);
                            }
                        );
                    }, 10);
                } else {
                    $("#memberExcelUpload").val('');
                    $scope._popMsg(messages['excelUpload.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                    return false;
                }
            }
        };

        // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
        $scope.memberExcelUploadToJsonConvert = function () {
            var scope = agrid.getScope('memberExcelUploadCtrl');
            var jsonData = [];
            var item = {};
            var rowLength = $scope.flex.rows.length;
            if (rowLength === 0) {
                $scope._popMsg(messages['excelUpload.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
                return false;
            }

            // 업로드 된 데이터 JSON 형태로 생성
            for (var r = 0; r < rowLength; r++) {
                item = {};
                for (var c = 0; c < $scope.flex.columns.length; c++) {
                    if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                        var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header];
                        var cellValue = $scope.flex.getCellData(r, c, false) + '';
                        item[colBinding] = cellValue;
                        item["gChk"] = false;
                    }
                }
                // item.uploadFg = $scope.uploadFg;
                jsonData.push(item);

            }
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            $timeout(function () {
                if ($scope.valChk(jsonData, scope.isCheckedMembr)) {
                    scope.data = new wijmo.collections.CollectionView(jsonData);
                } else {
                    scope.data = new wijmo.collections.CollectionView(jsonData);
                }
                scope.data.trackChanges = true;
            }, 10);
        };

        //회원 엑셀 값체크
        $scope.valChk = function (jsonData, isChecked) {
            $scope.totalRows = jsonData.length;
            var scope = agrid.getScope('memberExcelUploadCtrl');
            var failCnt = 0;
            var params = [];
            var msg = '';
            for (var i = 0; i < $scope.totalRows; i++) {
                var item = jsonData[i];
                failCnt = 0;

                // 회원명(한글) 값 체크
                if (nvl(item.membrNm, '') !== '') {
                    if(isChecked) { // 중복체크(회원명, 전화번호, 카드번호)할 경우 검증
                        // Grid 내에서의 회원명(한글) 중복체크
                        var isDuplicateNmInGrid = false;
                        for (var j = 0; j < $scope.totalRows; j++) {
                            var itemj = jsonData[j];
                            if (j != i && itemj.membrNm === item.membrNm) {
                                isDuplicateNmInGrid = true;
                                break;
                            }
                        }
                        if (isDuplicateNmInGrid) {
                            msg = messages["member.excel.nm.add.overlap"]; // 중복되는 회원명(한글)이 있습니다. 다시 확인해주세요.
                            item.result = msg;
                            failCnt++;
                            continue;
                        }
                        // DB상의 회원명(한글) 중복체크
                        var params = {};
                        params.membrOrgnCd = '${sessionScope.sessionInfo.orgnCd}';
                        params.membrNm = item.membrNm;
                        if (isDuplicateMemberNm(params)) { // 회원명(한글) 중복체크
                            msg = messages["member.excel.nm.add.overlap"]; // 중복되는 회원명(한글)이 있습니다. 다시 확인해주세요.
                            item.result = msg;
                            failCnt++;
                            continue;
                        }
                    }

                    // 회원명(한글) 최대길이 체크
                    if (nvl(item.membrNm + '', '').getByteLengthForOracle() > 100) {
                        msg = messages["member.excel.nm.kr"] + messages["excelUpload.overLength"] + " 100 " + messages["excelUpload.bateLengthInfo"]; // 회원명(한글)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100 (영문:2byte, 한글:3byte)
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                } //else { // 필수값
                //     msg = messages["member.excel.nm.kr"] + messages["excelUpload.require.data"]; // 회원명(한글)(이)가 없는 데이터가 존재합니다. 데이터 및 양식을 확인해주세요.
                //     item.result = msg;
                //     failCnt++;
                //     continue;
                // }

                // 회원명(영문) 최대길이 체크
                if (nvl(item.memberEngNm, '') !== '' && nvl(item.memberEngNm + '', '').getByteLengthForOracle() > 100) {
                    msg = messages["member.excel.nm.en"] + messages["excelUpload.overLength"] + " 100 " + messages["excelUpload.bateLengthInfo"]; // 회원명(영문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100 (영문:2byte, 한글:3byte)
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                // 회원등급분류 값 체크
                if (nvl(item.membrClassCd, '') !== '') {
                    // 회원등급분류 콤보박스
                    if (!$scope.comboboxChk(item.membrClassCd, memberClassList, "membrClassCd")) {
                        item.result = $scope.comboErrMsg;
                        failCnt++;
                        continue;
                    }
                } else { // 필수값
                    msg = messages["member.excel.membrClassCd"] + messages["excelUpload.require.data"]; // 회원등급분류(이)가 없는 데이터가 존재합니다. 데이터 및 양식을 확인해주세요.
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                // 등록매장 값 체크
                if (nvl(item.membrStore, '') !== '') {
                    // 등록매장 콤보박스
                    if (!$scope.comboboxChk(item.membrStore, regstrStoreList, "store")) {
                        item.result = $scope.comboErrMsg;
                        failCnt++;
                        continue;
                    }
                } else { // 필수값
                    msg = messages["member.excel.store"] + messages["excelUpload.require.data"]; // 등록매장(이)가 없는 데이터가 존재합니다. 데이터 및 양식을 확인해주세요.
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                // 성별구분 값 체크
                if (nvl(item.gendrFg, '') !== '') {
                    // 성별구분 콤보박스
                    if (!$scope.comboboxChk(item.gendrFg, genderDataMap, "gendrFg")) {
                        item.result = $scope.comboErrMsg;
                        failCnt++;
                        continue;
                    }
                } else { // 필수값
                    msg = messages["member.excel.gendrFg"] + messages["excelUpload.require.data"]; // 성별구분(이)가 없는 데이터가 존재합니다. 데이터 및 양식을 확인해주세요.
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                // 회원카드번호
                if (nvl(item.membrCardNo, '') !== '') {
                    if(isChecked) { // 중복체크(회원명, 전화번호, 카드번호)할 경우 검증
                        // Grid 내에서의 회원카드번호 중복체크
                        var isDuplicateCardNoInGrid = false;
                        for (var j = 0; j < $scope.totalRows; j++) {
                            var itemj = jsonData[j];
                            if (j != i && itemj.membrCardNo === item.membrCardNo) {
                                isDuplicateCardNoInGrid = true;
                                break;
                            }
                        }
                        if (isDuplicateCardNoInGrid) {
                            msg = messages["regist.card.add.overlap"]; // 중복되는 카드번호가 있습니다. 다시 확인해주세요.
                            item.result = msg;
                            failCnt++;
                            continue;
                        }
                        // DB상의 회원카드번호 중복체크
                        var params = {};
                        params.membrOrgnCd = '${sessionScope.sessionInfo.orgnCd}';
                        params.membrCardNo = item.membrCardNo;
                        if (isDuplicateMemberCard(params)) { // 회원카드 중복체크
                            // msg = messages["member.excel.membrCardNo"] + " : " + messages["member.excel.upload.duplicate.data"]; // 회원카드번호(이)가 중복되었습니다. 다시 확인하세요.
                            msg = messages["regist.card.add.overlap"]; // 중복되는 카드번호가 있습니다. 다시 확인해주세요.
                            item.result = msg;
                            failCnt++;
                            continue;
                        }
                    }
                    var numChkregexp = /[^A-za-z0-9]/g;
                    if (numChkregexp.test(item.membrCardNo)) {
                        msg = messages["member.excel.membrCardNo"] + messages["cmm.require.number.en"]; // 회원카드번호(은)는 숫자와 영문만 입력할 수 있습니다.
                        item.result = msg;
                        failCnt++;
                        continue;
                    }

                    // 회원카드번호 최대길이 체크
                    if (nvl(item.membrCardNo, '') !== '' && nvl(item.membrCardNo + '', '').getByteLengthForOracle() > 40) {
                        msg = messages["member.excel.membrCardNo"] + messages["excelUpload.overLength"] + " 40 " + messages["excelUpload.bateLengthInfo"]; // 회원카드번호의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 40 (영문:2byte, 한글:3byte)
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                }

                // 생년월일
                if (nvl(item.birthday, '') !== '') {
                    // 생년월일 최대길이 체크
                    if (nvl(item.birthday + '', '').getByteLengthForOracle() > 8) {
                        msg = messages["member.excel.birthday"] + messages["excelUpload.overLength"] + " 8 " + messages["excelUpload.bateLengthInfo"];
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                    // valid Date
                    if (!$scope.isValidDate(item.birthday)) {
                        msg = messages["member.excel.birthday"] + ' : ' + messages["member.excel.upload.invalid.date"]; // 생년월일 : 날짜 형식이 올바르지 않습니다.
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                }

                // 결혼여부
                if (nvl(item.weddingYn, '') !== '') {
                    // 결혼여부 콤보박스
                    if (!$scope.comboboxChk(item.weddingYn, weddingDataMap, "weddingYn")) {
                        item.result = $scope.comboErrMsg;
                        failCnt++;
                        continue;
                    }
                } else { // 필수값
                    msg = messages["member.excel.weddingYn"] + messages["excelUpload.require.data"]; // 결혼여부(이)가 없는 데이터가 존재합니다. 데이터 및 양식을 확인해주세요.
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                if (angular.equals(nvl(item.weddingYn, ''), '미혼') || angular.equals(nvl(item.weddingYn, ''), 'N')) {
                    item.weddingday = '';
                }
                // 결혼기념일
                if (nvl(item.weddingday, '') !== '') {
                    // 결혼기념일 최대길이 체크
                    if (nvl(item.weddingday + '', '').getByteLengthForOracle() > 8) {
                        msg = messages["member.excel.weddingday"] + messages["excelUpload.overLength"] + " 8 " + messages["excelUpload.bateLengthInfo"];
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                    // valid Date
                    if (!$scope.isValidDate(item.weddingday)) {
                        msg = messages["member.excel.weddingday"] + ' : ' + messages["member.excel.upload.invalid.date"]; // 결혼기념일 : 날짜 형식이 올바르지 않습니다.
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                }

                // 전화번호
                if (nvl(item.memberTelNo, '') !== '') {
                    if(isChecked) { // 중복체크(회원명, 전화번호, 카드번호)할 경우 검증
                        // Grid 내에서의 전화번호 중복체크
                        var isDuplicateTelNoInGrid = false;
                        for (var j = 0; j < $scope.totalRows; j++) {
                            var itemj = jsonData[j];
                            if (j != i && itemj.memberTelNo === item.memberTelNo) {
                                isDuplicateTelNoInGrid = true;
                                break;
                            }
                        }
                        if (isDuplicateTelNoInGrid) {
                            msg = messages["member.excel.telNo.add.overlap"]; // 중복되는 전화번호가 있습니다. 다시 확인해주세요.
                            item.result = msg;
                            failCnt++;
                            continue;
                        }
                        // DB상의 전화번호 중복체크
                        var params = {};
                        params.membrOrgnCd = '${sessionScope.sessionInfo.orgnCd}';
                        params.telNo = item.memberTelNo;
                        if (isDuplicateMemberTelNo(params)) { // 전화번호 중복체크
                            msg = messages["member.excel.telNo.add.overlap"]; // 중복되는 전화번호가 있습니다. 다시 확인해주세요.
                            item.result = msg;
                            failCnt++;
                            continue;
                        }
                    }
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test(nvl(item.memberTelNo, 0))) {
                        msg = messages["member.excel.telNo"] + messages["excelUpload.not.numberData"]; // 전화번호의 값에 숫자가 아닌 값이 존재합니다. 데이터 및 양식을 확인해주세요.
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                    // 전화번호 최대길이 체크
                    if (nvl(item.memberTelNo + '', '').getByteLengthForOracle() > 16) {
                        msg = messages["member.excel.telNo"] + messages["excelUpload.overLength"] + " 16 " + messages["excelUpload.bateLengthInfo"]; // 전화번호의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 16 (영문:2byte, 한글:3byte)
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                } else { // 필수값
                    msg = messages["member.excel.telNo"] + messages["excelUpload.require.data"]; // 전화번호(이)가 없는 데이터가 존재합니다. 데이터 및 양식을 확인해주세요.
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                // 단축번호
                if (nvl(item.memberShortNo, '') !== '') {
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test(nvl(item.memberShortNo, 0))) {
                        msg = messages["member.excel.shortNo"] + messages["excelUpload.not.numberData"]; // 단축번호의 값에 숫자가 아닌 값이 존재합니다. 데이터 및 양식을 확인해주세요.
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                    // 단축번호 최대길이 체크
                    if (nvl(item.memberShortNo + '', '').getByteLengthForOracle() > 4) {
                        msg = messages["member.excel.shortNo"] + messages["excelUpload.overLength"] + " 4 " + messages["excelUpload.bateLengthInfo"]; // 단축번호의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 4 (영문:2byte, 한글:3byte)
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                }

                // E-MAIL
                if (nvl(item.memberEmail, '') !== '') {
                    var chkexp = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/; // email check regex
                    // E-MAIL 형식 체크
                    if (!chkexp.test(nvl(item.memberEmail, 0))) {
                        msg = messages["member.excel.email"] + " : " + messages["cmm.input.fail"]; // E-MAIL : 입력한 정보가 올바르지 않습니다.
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                    // E-MAIL 최대길이 체크
                    if (nvl(item.memberEmail + '', '').getByteLengthForOracle() > 200) {
                        msg = messages["member.excel.email"] + messages["excelUpload.overLength"] + " 200 " + messages["excelUpload.bateLengthInfo"]; // E-MAIL의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 200 (영문:2byte, 한글:3byte)
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                }

                // 우편번호 최대길이 체크
                if (nvl(item.memberPostNo, '') !== '' && nvl(item.memberPostNo + '', '').getByteLengthForOracle() > 5) {
                    msg = messages["member.excel.postNo"] + messages["excelUpload.overLength"] + " 5 " + messages["excelUpload.bateLengthInfo"]; // 우편번호의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 5 (영문:2byte, 한글:3byte)
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                // 주소 최대길이 체크
                if (nvl(item.memberAddr, '') !== '' && nvl(item.memberAddr + '', '').getByteLengthForOracle() > 200) {
                    msg = messages["member.excel.addr"] + messages["excelUpload.overLength"] + " 200 " + messages["excelUpload.bateLengthInfo"]; // 주소의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 200 (영문:2byte, 한글:3byte)
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                // 상세주소 최대길이 체크
                if (nvl(item.memberAddrDtl, '') !== '' && nvl(item.memberAddrDtl + '', '').getByteLengthForOracle() > 200) {
                    msg = messages["member.excel.addrDtl"] + messages["excelUpload.overLength"] + " 200 " + messages["excelUpload.bateLengthInfo"]; // 상세주소의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 200 (영문:2byte, 한글:3byte)
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                // 이메일수신
                if (nvl(item.emailRecvYn, '') !== '') {
                    // 이메일수신 콤보박스
                    if (!$scope.comboboxChk(item.emailRecvYn, recvDataMap, "emailRecvYn")) {
                        item.result = $scope.comboErrMsg;
                        failCnt++;
                        continue;
                    }
                } else { // 필수값
                    msg = messages["member.excel.emailRecvYn"] + messages["excelUpload.require.data"]; // 이메일수신(이)가 없는 데이터가 존재합니다. 데이터 및 양식을 확인해주세요.
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                // SMS수신
                if (nvl(item.smsRecvYn, '') !== '') {
                    // SMS수신 콤보박스
                    if (!$scope.comboboxChk(item.smsRecvYn, recvDataMap, "smsRecvYn")) {
                        item.result = $scope.comboErrMsg;
                        failCnt++;
                        continue;
                    }
                } else { // 필수값
                    msg = messages["member.excel.smsRecvYn"] + messages["excelUpload.require.data"]; // SMS수신(이)가 없는 데이터가 존재합니다. 데이터 및 양식을 확인해주세요.
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                // 가용포인트
                if (nvl(item.avablPoint, '') !== '') {
                    // 가용포인트 숫자가 아닌 값
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test(nvl(item.avablPoint, 0))) {
                        msg = messages["member.excel.avablPoint"] + messages["excelUpload.not.numberData"]; // 가용포인트의 값에 숫자가 아닌 값이 존재합니다. 데이터 및 양식을 확인해주세요.
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                } else {
                    item.avablPoint = 0;
                }

                // 누적포인트
                if (nvl(item.totSavePoint, '') !== '') {
                    // 누적포인트 숫자가 아닌 값
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test(nvl(item.totSavePoint, 0))) {
                        msg = messages["member.excel.totPoint"] + messages["excelUpload.not.numberData"]; // 누적포인트의 값에 숫자가 아닌 값이 존재합니다. 데이터 및 양식을 확인해주세요.
                        item.result = msg;
                        failCnt++;
                        continue;
                    }
                } else {
                    item.totSavePoint = 0;
                }

                // 가용포인트는 누적포인트보다 클 수 없습니다.
                if (parseInt(item.avablPoint) > parseInt(item.totSavePoint))
                {
                    msg = messages["member.excel.upload.check.avablPoint"]; // 가용포인트는 누적포인트보다 클 수 없습니다.
                    item.result = msg;
                    failCnt++;
                    continue;
                }

                if (failCnt > 0) {
                    return false;
                }
                item.result = "검증성공";
            }
            scope.data = new wijmo.collections.CollectionView(jsonData);
            return true;
        };
        // 콤보박스 검증
        $scope.comboboxChk = function (comboData, obj, str) {
            filter = obj.filter(function (e) {
                if (e.name == comboData) {
                    return e.name == comboData;
                } else {
                    if (e.value == comboData) {
                        return e.value == comboData;
                    }
                }
            });

            if (filter.length === 0) {
                var messageName = "member.excel.".concat('', str);
                $scope.comboErrMsg = messages[messageName] + " : " + messages["cmm.input.fail"]; // messageName value : 입력한 정보가 올바르지 않습니다.
                return false;
            }
            $scope.comboErrMsg = '성공';
            return true;
        }

        // isValidDate, format: yyyyMMdd
        $scope.isValidDate = function (str_yyyyMMdd) {
            var pattern = /[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])/;
            return pattern.test(str_yyyyMMdd);
        };
        // 회원포인트업로드
        $scope.memberPointUpload = function () {
            // 선택한 파일이 있으면
            if ($('#memberPointUpload')[0].files[0]) {
                var file = $('#memberPointUpload')[0].files[0];
                var fileName = file.name;
                var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

                // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
                if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
                    $timeout(function () {
                        var flex = $scope.flex;
                        wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#memberPointUpload')[0].files[0], {includeColumnHeaders: true}
                            , function (workbook) {
                                $timeout(function () {
                                    $scope.memberPointUploadToJsonConvert();
                                }, 10);
                            }
                        );
                    }, 10);
                } else {
                    $("#memberPointUpload").val('');
                    $scope._popMsg(messages['excelUpload.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                    return false;
                }
            }
        };

        // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
        $scope.memberPointUploadToJsonConvert = function () {
            var scope = agrid.getScope('memberPointCtrl');
            var jsonData = [];

            var item = {};
            var rowLength = $scope.flex.rows.length;
            if (rowLength === 0) {
                $scope._popMsg(messages['excelUpload.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
                return false;
            }

            // 업로드 된 데이터 JSON 형태로 생성
            for (var r = 0; r < rowLength; r++) {
                item = {};
                for (var c = 0; c < $scope.flex.columns.length; c++) {
                    if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                        var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header];
                        var cellValue = $scope.flex.getCellData(r, c, false) + '';
                        if (colBinding === "totAdjPoint") {
                            item["tmpTotAdjPoint"] = cellValue;
                        } else {
                            item[colBinding] = cellValue;
                            item["gChk"] = false;
                        }

                    }
                }
                // item.uploadFg = $scope.uploadFg;
                jsonData.push(item);

            }
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            $scope.ajaxChk(jsonData)
            // $timeout(function () {
            // if ($scope.valPointChk(jsonData)) {
            // 로딩바 show
            // 가상로그인 대응한 session id 설정

            // if ($scope.valPointChk(resultList)) {
            //     scope.data = new wijmo.collections.CollectionView(resultList);
            // } else {
            //     scope.data = new wijmo.collections.CollectionView(resultList);
            // }
            // $.postJSONArray("/membr/info/point/point/getMemberPointList.sb", params, function (result) {
            // }, function (err) {
            //     s_alert.pop(err.message);
            // });
            // if ($scope.valPointChk(jsonData)) {
            // }
            // else {
            //     scope.data = new wijmo.collections.CollectionView(jsonData);
            // }
            // }, 10);
        };
        $scope.ajaxChk = function (jsonData) {
            var scope = agrid.getScope('memberPointCtrl');
            var url = "/membr/info/point/point/getMemberPointListChk.sb";
            var resultList = [];
            if (document.getElementsByName('sessionId')[0]) {
                url += '?sid=' + document.getElementsByName("sessionId")[0].value;
            }
            console.log(scope.listScale);
            scope.$broadcast('loadingPopupActive');
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(jsonData),
                cache: false,
                async: false,
                dataType: "json",
                contentType: 'application/json',
                processData: false,
                success: function (result) {
                    if (result.status === "OK") {
                        resultList = result.data;

                        if ($scope.valPointChk(resultList)) {
                            // scope.data = new wijmo.collections.CollectionView(resultList, {
                            //     pageSize: Number(scope.listScale)
                            // });
                            scope.data = new wijmo.collections.CollectionView(resultList);
                        } else {
                            scope.data = new wijmo.collections.CollectionView(resultList);
                        }
                        scope.data.trackChanges = true;
                    } else if (result.status === "SERVER_ERROR") {
                        s_alert.pop(result.message);
                    } else {
                        var msg = result.status + " : " + result.message;
                        //alert(msg);
                    }
                }
            }).fail(function () {
            });
            scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        };
        //회원 포인트조정 값체크
        $scope.valPointChk = function (resultList) {
            var params = new Array();
            var scope = agrid.getScope('memberPointCtrl');
            var msg = '';
            if (document.getElementsByName('sessionId')[0]) {
                resultList['sid'] = document.getElementsByName('sessionId')[0].value;
            }
            $scope.totalRows = resultList.length;
            for (var i = 0; i < $scope.totalRows; i++) {
                var item = resultList[i];
                // 필수값 및 길이 체크
                // 회원명(한글
                // item.memberResult = "";
                // 회원등급분류 콤보박스
                // if (!$scope.comboboxPointChk(item.membrClassCd, "class")) {
                //     item.memberResult = $scope.comboErrMsg;
                //     // return false;
                // }
                // // 회원등급분류
                // if (nvl(item.membrClassCd, '') === '') {
                //     msg = messages["cmm.excel.result"];
                //     item.memberResult = msg;
                //     // return false;
                // }

                var numChkregexp = /[^0-9]/g;
                if (numChkregexp.test(item.totAdjPoint)) {
                    // return false;
                    item.totAdjPoint = 0;
                }

                if (item.memberResult != messages["cmm.excel.result"]) {
                    item.totAdjPointAfter = Number(item.avablPoint) + Number(item.totAdjPoint);
                }
            }
            return true;
        };

        // 콤보박스 검증
        $scope.comboboxPointChk = function (comboData, fg) {
            $scope.comboErrMsg = '';
            var filter = [];
            if (fg === "class") {
                filter = memberClassList.filter(function (e) {
                    if (e.name == comboData) {
                        return e.name == comboData;
                    } else {
                        if (e.value == comboData) {
                            return e.value == comboData;
                        }
                    }
                });
                if (filter.length == 0) {
                    $scope.comboErrMsg = messages["cmm.excel.result"];
                    return false;
                }
            }
            $scope.comboErrMsg = '성공';
            return true;
        };

        // 엑셀 업로드
        $scope.excelUpload = function () {
            $scope.excelTextFg = 'excel';
            // 업로드 progress 관련 기본값 세팅
            $scope.stepCnt = 100; // 한번에 DB에 저장할 숫자 세팅
            $scope.progressCnt = 0;   // 처리된 숫자

            // 선택한 파일이 있으면
            if ($('#excelUpFile')[0].files[0]) {
                var file = $('#excelUpFile')[0].files[0];
                var fileName = file.name;
                var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

                // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
                if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

                    $timeout(function () {
                        var flex = $scope.flex;
                        wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#excelUpFile')[0].files[0], {includeColumnHeaders: true}
                            , function (workbook) {
                                $timeout(function () {
                                    $scope.excelUploadToJsonConvert();
                                }, 10);
                            }
                        );
                    }, 10);
                } else {
                    $("#excelUpFile").val('');
                    $scope._popMsg(messages['excelUpload.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                    return false;
                }
            }
        };


        // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
        $scope.excelUploadToJsonConvert = function () {
            var jsonData = [];
            var item = {};
            var rowLength = $scope.flex.rows.length;

            if (rowLength === 0) {
                $scope._popMsg(messages['excelUpload.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
                return false;
            }

            // 업로드 된 데이터 JSON 형태로 생성
            for (var r = 0; r < rowLength; r++) {
                item = {};
                for (var c = 0; c < $scope.flex.columns.length; c++) {
                    if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                        var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header];
                        var cellValue = $scope.flex.getCellData(r, c, false) + '';
                        item[colBinding] = cellValue;
                    }
                }
                // item.uploadFg = $scope.uploadFg;
                jsonData.push(item);
            }
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            $timeout(function () {
                $scope.save(jsonData);
            }, 10);
        };


        // DB에 저장
        $scope.save = function (jsonData) {
            $scope.totalRows = jsonData.length;
            var params = [];
            var msg = '';

            // 저장 시작이면 업로드 중 팝업 오픈
            if ($scope.progressCnt === 0) {
                $timeout(function () {
                    $scope.excelUploadingPopup(true);
                    $("#progressCnt").html($scope.progressCnt);
                    $("#totalRows").html($scope.totalRows);
                }, 10);
            }

            // stepCnt 만큼 데이터 DB에 저장
            var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
            for (var i = $scope.progressCnt; i < loopCnt; i++) {
                var item = jsonData[i];

                // 필수값 및 길이 체크
                // 상품코드/바코드
                if (nvl(item.prodBarcdCd, '') === '') {
                    msg = messages["excelUpload.prodBarcdCd"] + messages["excelUpload.require.data"]; // 상품코드/바코드(이)가 없는 데이터가 존재합니다.
                    $scope.valueCheckErrPopup(msg);
                    return false;
                }


                // 엑셀업로드시 업로드구분에 따른 필수값 체크
                if ($scope.excelTextFg === 'excel') {
                    // 주문등록, 반품등록, 분배마감, 반품마감, 거래처 발주등록, 거래처 입고등록
                    if ($scope.uploadFg === 'order' || $scope.uploadFg === 'dstbCloseStore' || $scope.uploadFg === 'vendr') {
                        // 단위수량
                        if (nvl(item.unitQty, '') === '') {
                            msg = messages["excelUpload.unitQty"] + messages["excelUpload.require.data"]; // 단위수량(이)가 없는 데이터가 존재합니다.
                            $scope.valueCheckErrPopup(msg);
                            return false;
                        }
                    }
                    // 실사,조정,폐기
                    else if ($scope.uploadFg === 'acins' || $scope.uploadFg === 'adj' || $scope.uploadFg === 'disuse') {
                        // 수량
                        if (nvl(item.qty, '') === '') {
                            msg = messages["excelUpload.qty"] + messages["excelUpload.require.data"]; // 수량(이)가 없는 데이터가 존재합니다.
                            $scope.valueCheckErrPopup(msg);
                            return false;
                        }
                    }
                }

                // 상품코드/바코드 최대길이 체크
                if (nvl(item.prodBarcdCd, '') !== '' && nvl(item.prodBarcdCd + '', '').getByteLengthForOracle() > 50) {
                    msg = messages["excelUpload.prodBarcdCd"] + messages["excelUpload.overLength"] + " 50 " + messages["excelUpload.bateLengthInfo"]; // 상품코드/바코드의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 50
                    $scope.valueCheckErrPopup(msg);
                    return false;
                }

                // 단위수량 값 체크
                if (nvl(item.unitQty, '') !== '') {
                    // 최대크기
                    if (parseInt(nvl(item.unitQty, 0)) > 99999999) {
                        msg = messages["excelUpload.unitQty"] + messages["excelUpload.overSize"] + " 99999999"; // 단위수량의 데이터 중 크기가 너무 큰 데이터가 있습니다. 최대 : 99999999
                        $scope.valueCheckErrPopup(msg);
                        return false;
                    }
                    // 숫자가 아닌 값
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test(nvl(item.unitQty, 0))) {
                        msg = messages["excelUpload.unitQty"] + messages["excelUpload.not.numberData"]; // 단위수량의 값에 숫자가 아닌 값이 존재합니다. 데이터 및 양식을 확인해주세요.
                        $scope.valueCheckErrPopup(msg);
                        return false;
                    }
                }

                // 낱개수량 값 체크
                if (nvl(item.etcQty, '') !== '') {
                    // 최대크기
                    if (parseInt(nvl(item.etcQty, 0)) > 99999999) {
                        msg = messages["excelUpload.etcQty"] + messages["excelUpload.overSize"] + " 99999999"; // 낱개수량의 데이터 중 크기가 너무 큰 데이터가 있습니다. 최대 : 99999999
                        $scope.valueCheckErrPopup(msg);
                        return false;
                    }
                    // 숫자가 아닌 값
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test(nvl(item.etcQty, 0))) {
                        msg = messages["excelUpload.etcQty"] + messages["excelUpload.not.numberData"]; // 낱개수량의 값에 숫자가 아닌 값이 존재합니다. 데이터 및 양식을 확인해주세요.
                        $scope.valueCheckErrPopup(msg);
                        return false;
                    }
                }

                // 수량 값 체크
                if (nvl(item.qty, '') !== '') {
                    // 최대크기
                    if (parseInt(nvl(item.qty, 0)) > 99999999) {
                        msg = messages["excelUpload.qty"] + messages["excelUpload.overSize"] + " 99999999"; // 수량의 데이터 중 크기가 너무 큰 데이터가 있습니다. 최대 : 99999999
                        $scope.valueCheckErrPopup(msg);
                        return false;
                    }
                    // 숫자가 아닌 값
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test(nvl(item.qty, 0))) {
                        msg = messages["excelUpload.qty"] + messages["excelUpload.not.numberData"]; // 수량의 값에 숫자가 아닌 값이 존재합니다. 데이터 및 양식을 확인해주세요.
                        $scope.valueCheckErrPopup(msg);
                        return false;
                    }
                }

                // 단가 값 체크
                if (nvl(item.uprc, '') !== '') {
                    // 최대크기
                    if (parseInt(nvl(item.uprc, 0)) > 9999999999) {
                        msg = messages["excelUpload.uprc"] + messages["excelUpload.overSize"] + " 9999999999"; // 단가의 데이터 중 크기가 너무 큰 데이터가 있습니다. 최대 : 9999999999
                        $scope.valueCheckErrPopup(msg);
                        return false;
                    }
                    // 숫자가 아닌 값
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test(nvl(item.uprc, 0))) {
                        msg = messages["excelUpload.uprc"] + messages["excelUpload.not.numberData"]; // 단가의 값에 숫자가 아닌 값이 존재합니다. 데이터 및 양식을 확인해주세요.
                        $scope.valueCheckErrPopup(msg);
                        return false;
                    }
                }

                // 비고 최대길이 체크
                if (nvl(item.remark, '') !== '' && nvl(item.remark + '', '').getByteLengthForOracle() > 1000) {
                    msg = messages["excelUpload.remark"] + messages["excelUpload.overLength"] + " 1000 " + messages["excelUpload.bateLengthInfo"]; // 비고의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 1000
                    $scope.valueCheckErrPopup(msg);
                    return false;
                }

                item.uploadFg = $scope.uploadFg;
                // 매장코드를 직접 파라미터로 넘겨주는 경우에만 storeCd를 받아서 temp 테이블에 넣음.
                if (nvl($scope.storeCd, '') !== '') {
                    item.storeCd = $scope.storeCd;
                }

                params.push(item);
            }

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/iostock/cmmExcelUpload/excelUpload/excelUpload/save.sb', /* 통신할 URL */
                data: params, /* 파라메터로 보낼 데이터 : @requestBody */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                if ($scope._httpStatusCheck(response, true)) {
                    $scope._popMsg(messages['cmm.saveSucc']);
                }
            }, function errorCallback(response) {
                $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
                if (response.data.message) {
                    $scope._popMsg(response.data.message);
                } else {
                    $scope._popMsg(messages['cmm.saveFail']);
                }
                return false;
            }).then(function () {
                // 'complete' code here
                // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
                if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                    // 처리된 숫자 변경
                    $scope.progressCnt = loopCnt;
                    // 팝업의 progressCnt 값 변경
                    $("#progressCnt").html($scope.progressCnt);
                    $scope.save(jsonData);
                } else {
                    $scope.saveUpdateProdCd();
                }
            });
        };


        // 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트
        // 업데이트 완료 후 callback 호출 함.
        $scope.saveUpdateProdCd = function () {
            var params = {};
            params.uploadFg = $scope.uploadFg;

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/iostock/cmmExcelUpload/excelUpload/excelUpload/saveUpdateProdCd.sb', /* 통신할 URL */
                params: params, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                if ($scope._httpStatusCheck(response, true)) {
                    if (nvl($scope.parentCtrl, '') !== '') {
                        var parentScope = agrid.getScope($scope.parentCtrl);
                        parentScope.uploadCallBack();
                    }
                }
            }, function errorCallback(response) {
                $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
                if (response.data.message) {
                    $scope._popMsg(response.data.message);
                } else {
                    $scope._popMsg(messages['cmm.saveFail']);
                }
                return false;
            }).then(function () {
            });
        };


        // 엑셀업로딩 팝업 열기
        $scope.excelUploadingPopup = function (showFg) {
            if (showFg) {
                // 팝업내용 동적 생성
                var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['excelUpload.excelUploading'] + '</p>';
                innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span> / <span class="bk" id="totalRows">0</span></div>';
                innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
                // html 적용
                $scope._loadingPopup.content.innerHTML = innerHtml;
                // 팝업 show
                $scope._loadingPopup.show(true);
            } else {
                $scope._loadingPopup.hide(true);
            }
        };


        // 업로드 한 데이터 값체크 중 에러시 에러팝업 띄우기 및 엑셀업로딩 팝업 닫기
        $scope.valueCheckErrPopup = function (msg) {
            $scope._popMsg(msg, function () {
                $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
            });
        };

    }])
    ;

    // 회원명 중복체크
    function isDuplicateMemberNm(params) {
        var checked = false;
        var url = "/membr/info/view/base/getMemberNmCount.sb";
        // 가상로그인시 세션활용
        if (document.getElementsByName("sessionId")[0]) {
            url += '?sid=' + document.getElementsByName("sessionId")[0].value;
        }
        $.ajax({
            type: "POST",
            cache: false,
            async: false,
            dataType: "json",
            url: url,
            data: params,
            success: function (result) {
                if (result.data > 0) {
                    checked = true;
                }
            }
        });
        return checked;
    }

    // 전화번호 중복체크
    function isDuplicateMemberTelNo(params) {
        var checked = false;
        var url = "/membr/info/view/base/getMemberTelNoCount.sb";
        // 가상로그인시 세션활용
        if (document.getElementsByName("sessionId")[0]) {
            url += '?sid=' + document.getElementsByName("sessionId")[0].value;
        }
        $.ajax({
            type: "POST",
            cache: false,
            async: false,
            dataType: "json",
            url: url,
            data: params,
            success: function (result) {
                if (result.data > 0) {
                    checked = true;
                }
            }
        });
        return checked;
    }

    // 회원카드번호 중복체크
    function isDuplicateMemberCard(params) {
        var checked = false;
        var url = "/membr/info/view/base/registCardInfoCount.sb";
        // 가상로그인시 세션활용
        if (document.getElementsByName("sessionId")[0]) {
            url += '?sid=' + document.getElementsByName("sessionId")[0].value;
        }
        $.ajax({
            type: "POST",
            cache: false,
            async: false,
            dataType: "json",
            url: url,
            data: params,
            success: function (result) {
                if (result.data > 0) {
                    checked = true;
                }
            }
        });
        return checked;
    }

    function selectValueFromIndex(wijmoGridDataMapList, index) {
        var value = '';
        for (var i=0; wijmoGridDataMapList; i++) {
            if (i === index) {
                value = wijmoGridDataMapList[i].name;
                return value;
            }
        }
    }
</script>

<%-- 수불 엑셀업로드 에러내역 공통 팝업 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUpload/excelUploadErrInfo.jsp">
</c:import>
