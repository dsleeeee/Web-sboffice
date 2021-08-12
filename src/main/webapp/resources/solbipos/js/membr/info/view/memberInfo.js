/****************************************************************
 *
 * 파일명 : memberInfo.js
 * 설  명 : 회원정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.08     김지은      1.0
 * 2020.06.25     이재영
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 검색기간 콤보박스
var periodData = [
    {"name":"기간 미사용","value":"all"},
    {"name":"가입일","value":"reg"},
    {"name":"최종방문일","value":"last"}
];

// 광고성 SMS전송 DropBoxDataMap
var marketingSmsGubunComboData = [
    {"name": "전체", "value": ""},
    {"name": "1개월전", "value": "1"},
    {"name": "2개월전", "value": "2"},
    {"name": "3개월전", "value": "3"},
    {"name": "4개월전", "value": "4"},
    {"name": "5개월전", "value": "5"},
    {"name": "6개월전", "value": "6"}
];

/**********************************************************************
 *  회원정보 그리드
 **********************************************************************/
app.controller('memberCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberCtrl', $scope, $http, $timeout, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("rMemberClass", memberClassList);
    $scope._setComboData("rMemberClassSelect", memberClassSelect);
    $scope._setComboData("periodType", periodData); // 검색기간 콤보박스

    $scope.memberSaleList = [
        {value: "0", name: messages["regist.save.sale"]},
        {value: "1", name: messages["regist.save.sale_amount"]}
    ];

    $scope.memberPointList = [
        {value: "0", name: messages["regist.membr.point.add"] + messages["regist.membr.point"]},
        {value: "1", name: messages["regist.membr.point.use"] + messages["regist.membr.point"]},
        {value: "2", name: messages["regist.membr.point.adj"] + messages["regist.membr.point"]},
        {value: "3", name: messages["regist.membr.point.ava"] + messages["regist.membr.point"]}
    ];

    $scope._getComboDataQuery('072', 'emailRecvYn', 'A');
    $scope._getComboDataQuery('072', 'smsRecvYn', 'A');
    $scope._getComboDataQuery('032', 'anvType', 'A');
    // $scope._getComboDataQuery('077', 'periodType', ''); // 검색기간 콤보박스
    $scope._getComboDataQuery('076', 'weddingYn', 'A');
    $scope._getComboDataQuery('055', 'gendrFg', 'A');
    $scope._getComboDataQuery('067', 'useYnAll', 'A');
    $scope._getComboDataQuery('072', 'recvYn', 'S');
    $scope._getComboDataQuery('299', 'membrCardFg', 'A');
    /*$scope._setComboData("periodType", periodDataMap);*/
    $scope._setComboData("marketingSmsGubunCombo", marketingSmsGubunComboData); // 광고성 SMS전송

    // 선택 회원
    $scope.selectedMember;
    $scope.setSelectedMember = function (member) {
        $scope.selectedMember = member;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    if (orgnFg === "STORE") {
        if(hqOfficeCd !== "00000") {
            $scope.storeMembr = true;
        }
    } else {
        $scope.storeMembr = false;
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 회원번호, 회원명 클릭시 상세정보 팝업
                if (col.binding === "membrNo" || col.binding === "membrNm") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
                // 후불적용매장등록 클릭시 매장선택 팝업
                if (col.binding === "postpaidStore") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
                // 생일, 최초방문일, 최종방문일, 가입일 날짜 형식 맞추기
                if (col.binding === "birthday" || col.binding === "firstSaleDate" || col.binding === "lastSaleDate") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                }
                if(col.binding == "regDt"){
                    e.cell.innerHTML = getFormatDate(e.cell.innerText.substring(0, 8));
                }

                // 포인트 - 가용
                if (col.binding === "avablPoint") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 회원선택
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 회원번호, 회원명 클릭시 상세정보 팝업
                if (col.binding === "membrNo" || col.binding === "membrNm") {
                    var selectedData = s.rows[ht.row].dataItem;
                    $scope.setSelectedMember(selectedData);
                    $scope._broadcast('responseGet', selectedData);
                    $scope.memberRegistLayer.show(true);
                    // $scope.memberInfoDetailLayer.show(true);
                    event.preventDefault();
                }

                // 후불적용매장등록 클릭시 매장선택 팝업
                if (col.binding === "postpaidStore") {
                    var selectedData = s.rows[ht.row].dataItem;
                    // 해당 매장의 등록매장이 본사의 디폴트 매장과 동일할 경우에만 후불적용 매장을 등록할 수 있다.
                    $scope.setSelectedMember(selectedData);
                    $scope.postpaidStoreRegistLayer.show(true);
                    event.preventDefault();
                }

                // 포인트 - 가용
                if (col.binding === "avablPoint") {
                    var selectedData = s.rows[ht.row].dataItem;
                    $scope.setSelectedMember(selectedData);
                    $scope.wjMemberInfoPointLayer.show(true);
                    event.preventDefault();
                }
            }
        });

        // <-- 그리드 헤더3줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.gChk = messages["cmm.chk"];
        dataItem.membrNo = messages["regist.membr.no"];
        dataItem.membrNm = messages["regist.membr.nm"];
        dataItem.membrClassCd = messages["regist.class.cd"];
        dataItem.membrClassNm = messages["regist.class.nm"];
        dataItem.membrCardNo = messages["regist.card.no"];
        dataItem.birthday = messages["regist.brthd"];
        /*dataItem.phoneNo = messages["regist.tel"];*/
        dataItem.telNo = messages["regist.tel"];
        dataItem.shortNo = messages["regist.membr.stortNo"];
        // dataItem.regStoreCd = messages["regist.membr.regStore"];
        // dataItem.regStoreNm = messages["regist.membr.regStore"];
        dataItem.emailRecvYn = messages["regist.email.recv"];
        dataItem.smsRecvYn = messages["regist.sms.recv"];
        dataItem.useYn = messages["regist.useYn"];
        dataItem.postpaidStore = messages["regist.membr.store"];
        dataItem.addr = messages["cmm.addr"];

        dataItem.point = messages["regist.membr.total"];
        dataItem.totSavePoint = messages["regist.membr.total"];
        dataItem.totUsePoint = messages["regist.membr.total"];
        dataItem.totAdjPoint = messages["regist.membr.total"];
        dataItem.avablPoint = messages["regist.membr.total"];

        dataItem.save = messages["regist.membr.total"];
        dataItem.pointAccCnt = messages["regist.membr.total"];
        dataItem.totSaleAmt = messages["regist.membr.total"];

        dataItem.visit = messages["regist.membr.total"];
        dataItem.firstSaleDate = messages["regist.membr.total"];
        dataItem.lastSaleDate = messages["regist.membr.total"];

        dataItem.point = messages["regist.membr.use.store.total"];
        dataItem.storeTotSavePoint = messages["regist.membr.use.store.total"];
        dataItem.storeTotUsePoint = messages["regist.membr.use.store.total"];
        dataItem.storeTotAdjPoint = messages["regist.membr.use.store.total"];

        dataItem.save = messages["regist.membr.use.store.total"];
        dataItem.storePointAccCnt = messages["regist.membr.use.store.total"];
        dataItem.storeTotSaleAmt = messages["regist.membr.use.store.total"];

        dataItem.visit = messages["regist.membr.use.store.total"];
        dataItem.storeFirstSaleDate = messages["regist.membr.use.store.total"];
        dataItem.storeLastSaleDate = messages["regist.membr.use.store.total"];

        dataItem.regDt = messages["regist.membr.total"];
        s.columnHeaders.rows[0].dataItem = dataItem;

        //둘째줄 헤더
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1 = {};
        dataItem1.gChk = messages["cmm.chk"];
        dataItem1.membrNo = messages["regist.membr.no"];
        dataItem1.membrNm = messages["regist.membr.nm"];
        dataItem1.membrClassCd = messages["regist.class.cd"];
        dataItem1.membrClassNm = messages["regist.class.nm"];
        dataItem1.membrCardNo = messages["regist.card.no"];
        dataItem1.birthday = messages["regist.brthd"];
        /*dataItem1.phoneNo = messages["regist.tel"];*/
        dataItem1.telNo = messages["regist.tel"];
        dataItem1.shortNo = messages["regist.membr.stortNo"];
        dataItem1.regStoreCd = messages["regist.membr.regStore"];
        dataItem1.regStoreNm = messages["regist.membr.regStore"];
        dataItem1.emailRecvYn = messages["regist.email.recv"];
        dataItem1.smsRecvYn = messages["regist.sms.recv"];
        dataItem1.useYn = messages["regist.useYn"];
        dataItem1.postpaidStore = messages["regist.membr.store"];
        dataItem1.addr = messages["cmm.addr"];

        dataItem1.point = messages["regist.membr.point"];
        dataItem1.totSavePoint = messages["regist.membr.point"];
        dataItem1.totUsePoint = messages["regist.membr.point"];
        dataItem1.totAdjPoint = messages["regist.membr.point"];
        dataItem1.avablPoint = messages["regist.membr.point"];

        dataItem1.save = messages["regist.membr.save"];
        dataItem1.pointAccCnt = messages["regist.membr.save"];
        dataItem1.totSaleAmt = messages["regist.membr.save"];

        dataItem1.visit = messages["regist.membr.visit"];
        dataItem1.firstSaleDate = messages["regist.membr.visit"];
        dataItem1.lastSaleDate = messages["regist.membr.visit"];

        dataItem1.point = messages["regist.membr.point"];
        dataItem1.storeTotSavePoint = messages["regist.membr.point"];
        dataItem1.storeTotUsePoint = messages["regist.membr.point"];
        dataItem1.storeTotAdjPoint = messages["regist.membr.point"];

        dataItem1.save = messages["regist.membr.save"];
        dataItem1.storePointAccCnt = messages["regist.membr.save"];
        dataItem1.storeTotSaleAmt = messages["regist.membr.save"];

        dataItem1.visit = messages["regist.membr.visit"];
        dataItem1.storeFirstSaleDate = messages["regist.membr.visit"];
        dataItem1.storeLastSaleDate = messages["regist.membr.visit"];

        dataItem1.regDt = messages["regist.membr.day"];

        s.columnHeaders.rows[1].dataItem = dataItem1;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display: 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display: 'table-cell',
                    verticalAlign: 'middle',
                    textAlign: 'center'
                });
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
        // <-- //그리드 헤더3줄 -->
    };

    // 조회 버튼 클릭
    $scope.$on("memberCtrl", function (event, data) {
        // 이출, 이입매장 초기화
        /*$("#regStoreCd").val("");
        $("#regStoreNm").val(messages["cmm.select"]);*/
        $scope.getMemberList();
        event.preventDefault();
    });

    // 회원 목록 조회
    $scope.getMemberList = function () {
        var params = {};
        params.periodType = $scope.periodType;
        params.periodStartDate = dateToDaystring($scope.periodStartDate).replaceAll('-', '');
        params.periodEndDate = dateToDaystring($scope.periodEndDate).replaceAll('-', '');
        params.anvType = $scope.anvType;
        params.anvStartDate = dateToDaystring($scope.anvStartDate).replaceAll('-', '');
        params.anvEndDate = dateToDaystring($scope.anvEndDate).replaceAll('-', '');

        params.startSaveSale = $scope.startSaveSale;
        params.endSaveSale = $scope.endSaveSale;
        params.startAvablPoint = $scope.startAvablPoint;
        params.endAvablPoint = $scope.endAvablPoint;
        params.shortNo = $scope.shortNo;
        params.weddingYn = $scope.weddingYn;
        params.memberClass = $scope.memberClass;
        params.phoneNo = $scope.phoneNo;

        params.listScale = $scope.listScale;

        params.membrClassCd = $scope.member.membrClassCd;
        params.membrNo = $("#memberNo").val();
        params.membrNm = $("#memberNm").val();
        params.membrEngNm = $scope.member.memberEngNm;

        params.storeMembr = $scope.storeMembr;
        params.visitStoreMembr = $scope.visitStoreMembr;
        //$scope.regUseStore;

        params.regStoreCd = $("#regStoreCd").val();
        params.telNo = $("#telNo").val();
        params.regUseStoreCd = $("#regUseStoreCd").val();
        params.membrCardNo = $("#membrCardNo").val();
        params.cstCardUseFg = $scope.cstCardUseFg;
        params.emailAddr = $("#emailAddr").val();
        params.emailRecvYn = $scope.emailRecvYn;
        params.smsRecvYn = $scope.smsRecvYn;
        params.gendrFg = $scope.gendrFg;
        params.memberSaleFg = $scope.memberSaleFg;
        params.memberPointFg = $scope.memberPointFg;
        params.useYn = $scope.useYn;
        // params.useYn = 'Y';

        // console.log('params ', params);
        if (orgnFg === "HQ") {
            if (params.regUseStoreCd !== null && params.regUseStoreCd !== '') {
                $scope.regStoreChk = true
            } else {
                $scope.regStoreChk = false
            }
        }else {
            if(hqOfficeCd !== "00000") {
                if ($scope.visitStoreMembr) {
                    $scope.regStoreChk = true
                } else if (!$scope.visitStoreMembr) {
                    $scope.regStoreChk = false
                }
            }
        }

        $scope._inquiryMain("/membr/info/view/view/getMemberlist.sb", params, function () {});
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.regStoreShow = function () {
        $scope._broadcast('regStoreCtrl');
    };
    $scope.regUseStoreShow = function () {
        $scope._broadcast('regUseStoreCtrl');
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 회원조회 팝업 핸들러 추가
        $scope.memberInfoDetailLayer.shown.addHandler(function (s) {
            setTimeout(function () {
                $scope._broadcast('memberInfoDetailCtrl', $scope.getSelectedMember());
            }, 50)
        });

        // 회원 등록 및 수정 팝업 핸들러 추가
        $scope.memberRegistLayer.shown.addHandler(function (s) {
            setTimeout(function () {
                $scope._broadcast('memberRegistInfo', $scope.getSelectedMember());
            }, 50)
        });

        // 후불회원등록 팝업 핸들러 추가
        $scope.postpaidStoreRegistLayer.shown.addHandler(function (s) {
            setTimeout(function () {
                $scope._broadcast('postpaidStoreRegistCtrl', $scope.getSelectedMember());
            }, 50)
        });

        // 회원 구매내역,포인트 조회 팝업 핸들러 추가
        $scope.wjMemberInfoPointLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('memberInfoPointCtrl', $scope.getSelectedMember());
            }, 50)
        });

        // 회원 포인트 이관 팝업 핸들러 추가
        $scope.wjMemberPointMoveLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('memberPointMoveCtrl', null);
            }, 50)
        });

        // 회원 포인트 조정 팝업 핸들러 추가
        $scope.wjMemberPointAdjustLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('memberPointAdjustCtrl', null);
            }, 50)
        });

        // SMS전송 팝업 핸들러 추가
        $scope.wjSmsSendViewPopLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsSendViewPopYCtrl', $scope.getSelectedMember());
            }, 50)
        });
    });

    // 신규회원 등록
    $scope.registMember = function () {
        $scope.setSelectedMember(null);
        $scope._broadcast('responseGet', {});
        $scope.memberRegistLayer.show(true);
    };

    // 회원 삭제
    $scope.deleteMember = function () {
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                var obj = {};
                obj.membrNo = $scope.flex.collectionView.items[i].membrNo;
                params.push(obj);
            }
        }

        // 회원 사용여부 '미사용'으로 변경 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/membr/info/view/base/remove.sb", params, function () {
            $scope.getMemberList()
        });
    };

    // <-- 엑셀다운로드 호출 -->
    $scope.excelDownload = function(){
        var params       = {};
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope._broadcast('memberExcelCtrl', params);
    };
    // <-- //엑셀다운로드 호출 -->

    $scope.$watch('newMemberYn', function (val) {
        if(val === true ){
            $scope.memberNm = "";
            $scope.member.memberEngNm = "";
            $scope.memberNo = "";
        }
    });

    // 회원 거래처 매핑
    $scope.memberVendorMapping = function () {
        var params = {};
        $scope._broadcast('memberVendorMappingCtrl', params);
    };

    // 회원 포인트 이관
    $scope.memberPointMove = function () {
        $scope.wjMemberPointMoveLayer.show(true);
        event.preventDefault();
    };

    // 회원 포인트 조정
    $scope.memberPointAdjust = function () {
        $scope.wjMemberPointAdjustLayer.show(true);
        event.preventDefault();
    };

    // SMS전송
    $scope.smsSendPop = function () {
        if($scope.flex.rows.length > 0) {
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].membrNo = $scope.flex.collectionView.items[i].membrNo;
                    $scope.flex.collectionView.items[i].membrNm = $scope.flex.collectionView.items[i].membrNm;
                    $scope.flex.collectionView.items[i].telNo = $scope.flex.collectionView.items[i].telNo;
                    $scope.flex.collectionView.items[i].orgnFg = "C";
                    $scope.flex.collectionView.items[i].orgnCd = $scope.orgnCd;
                    $scope.flex.collectionView.items[i].userId = "";
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }
        $scope.setSelectedMember(params);
        $scope.wjSmsSendViewPopLayer.show(true);
        event.preventDefault();
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };
}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('memberExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');

        // <-- 그리드 헤더3줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.gChk = messages["cmm.chk"];
        dataItem.membrNo = messages["regist.membr.no"];
        dataItem.membrNm = messages["regist.membr.nm"];
        dataItem.membrClassCd = messages["regist.class.cd"];
        dataItem.membrClassNm = messages["regist.class.nm"];
        dataItem.membrCardNo = messages["regist.card.no"];
        dataItem.birthday = messages["regist.brthd"];
        /*dataItem.phoneNo = messages["regist.tel"];*/
        dataItem.telNo = messages["regist.tel"];
        dataItem.shortNo = messages["regist.membr.stortNo"];
        // dataItem.regStoreCd = messages["regist.membr.regStore"];
        // dataItem.regStoreNm = messages["regist.membr.regStore"];
        dataItem.emailRecvYn = messages["regist.email.recv"];
        dataItem.smsRecvYn = messages["regist.sms.recv"];
        dataItem.useYn = messages["regist.useYn"];
        dataItem.postpaidStore = messages["regist.membr.store"];
        dataItem.addr = messages["cmm.addr"];

        dataItem.point = messages["regist.membr.total"];
        dataItem.totSavePoint = messages["regist.membr.total"];
        dataItem.totUsePoint = messages["regist.membr.total"];
        dataItem.totAdjPoint = messages["regist.membr.total"];
        dataItem.avablPoint = messages["regist.membr.total"];

        dataItem.save = messages["regist.membr.total"];
        dataItem.pointAccCnt = messages["regist.membr.total"];
        dataItem.totSaleAmt = messages["regist.membr.total"];

        dataItem.visit = messages["regist.membr.total"];
        dataItem.firstSaleDate = messages["regist.membr.total"];
        dataItem.lastSaleDate = messages["regist.membr.total"];

        dataItem.point = messages["regist.membr.use.store.total"];
        dataItem.storeTotSavePoint = messages["regist.membr.use.store.total"];
        dataItem.storeTotUsePoint = messages["regist.membr.use.store.total"];
        dataItem.storeTotAdjPoint = messages["regist.membr.use.store.total"];

        dataItem.save = messages["regist.membr.use.store.total"];
        dataItem.storePointAccCnt = messages["regist.membr.use.store.total"];
        dataItem.storeTotSaleAmt = messages["regist.membr.use.store.total"];

        dataItem.visit = messages["regist.membr.use.store.total"];
        dataItem.storeFirstSaleDate = messages["regist.membr.use.store.total"];
        dataItem.storeLastSaleDate = messages["regist.membr.use.store.total"];

        dataItem.regDt = messages["regist.membr.total"];
        s.columnHeaders.rows[0].dataItem = dataItem;

        //둘째줄 헤더
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1 = {};
        dataItem1.gChk = messages["cmm.chk"];
        dataItem1.membrNo = messages["regist.membr.no"];
        dataItem1.membrNm = messages["regist.membr.nm"];
        dataItem1.membrClassCd = messages["regist.class.cd"];
        dataItem1.membrClassNm = messages["regist.class.nm"];
        dataItem1.membrCardNo = messages["regist.card.no"];
        dataItem1.birthday = messages["regist.brthd"];
        /*dataItem1.phoneNo = messages["regist.tel"];*/
        dataItem1.telNo = messages["regist.tel"];
        dataItem1.shortNo = messages["regist.membr.stortNo"];
        dataItem1.regStoreCd = messages["regist.membr.regStore"];
        dataItem1.regStoreNm = messages["regist.membr.regStore"];
        dataItem1.emailRecvYn = messages["regist.email.recv"];
        dataItem1.smsRecvYn = messages["regist.sms.recv"];
        dataItem1.useYn = messages["regist.useYn"];
        dataItem1.postpaidStore = messages["regist.membr.store"];
        dataItem1.addr = messages["cmm.addr"];

        dataItem1.point = messages["regist.membr.point"];
        dataItem1.totSavePoint = messages["regist.membr.point"];
        dataItem1.totUsePoint = messages["regist.membr.point"];
        dataItem1.totAdjPoint = messages["regist.membr.point"];
        dataItem1.avablPoint = messages["regist.membr.point"];

        dataItem1.save = messages["regist.membr.save"];
        dataItem1.pointAccCnt = messages["regist.membr.save"];
        dataItem1.totSaleAmt = messages["regist.membr.save"];

        dataItem1.visit = messages["regist.membr.visit"];
        dataItem1.firstSaleDate = messages["regist.membr.visit"];
        dataItem1.lastSaleDate = messages["regist.membr.visit"];

        dataItem1.point = messages["regist.membr.point"];
        dataItem1.storeTotSavePoint = messages["regist.membr.point"];
        dataItem1.storeTotUsePoint = messages["regist.membr.point"];
        dataItem1.storeTotAdjPoint = messages["regist.membr.point"];

        dataItem1.save = messages["regist.membr.save"];
        dataItem1.storePointAccCnt = messages["regist.membr.save"];
        dataItem1.storeTotSaleAmt = messages["regist.membr.save"];

        dataItem1.visit = messages["regist.membr.visit"];
        dataItem1.storeFirstSaleDate = messages["regist.membr.visit"];
        dataItem1.storeLastSaleDate = messages["regist.membr.visit"];

        dataItem1.regDt = messages["regist.membr.day"];

        s.columnHeaders.rows[1].dataItem = dataItem1;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display: 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display: 'table-cell',
                    verticalAlign: 'middle',
                    textAlign: 'center'
                });
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
        // <-- //그리드 헤더3줄 -->
    };

    // <-- 검색 호출 -->
    $scope.$on("memberExcelCtrl", function(event, data) {
        $scope.getMemberListExcel();
        event.preventDefault();
    });

    $scope.getMemberListExcel = function(){
        var params = {};
        params.periodType = $scope.periodType;
        params.periodStartDate = dateToDaystring($scope.periodStartDate).replaceAll('-', '');
        params.periodEndDate = dateToDaystring($scope.periodEndDate).replaceAll('-', '');
        params.anvType = $scope.anvType;
        params.anvStartDate = dateToDaystring($scope.anvStartDate).replaceAll('-', '');
        params.anvEndDate = dateToDaystring($scope.anvEndDate).replaceAll('-', '');

        params.startSaveSale = $scope.startSaveSale;
        params.endSaveSale = $scope.endSaveSale;
        params.startAvablPoint = $scope.startAvablPoint;
        params.endAvablPoint = $scope.endAvablPoint;
        params.shortNo = $scope.shortNo;
        params.weddingYn = $scope.weddingYn;
        params.memberClass = $scope.memberClass;
        params.phoneNo = $scope.phoneNo;

        params.listScale = $scope.listScale;

        params.membrClassCd = $scope.member.membrClassCd;
        params.membrNo = $("#memberNo").val();
        params.membrNm = $("#memberNm").val();
        params.membrEngNm = $scope.member.memberEngNm;

        params.storeMembr = $scope.storeMembr;
        params.visitStoreMembr = $scope.visitStoreMembr;
        //$scope.regUseStore;

        params.regStoreCd = $("#regStoreCd").val();
        params.telNo = $("#telNo").val();
        params.regUseStoreCd = $("#regUseStoreCd").val();
        params.membrCardNo = $("#membrCardNo").val();
        params.cstCardUseFg = $scope.cstCardUseFg;
        params.emailAddr = $("#emailAddr").val();
        params.emailRecvYn = $scope.emailRecvYn;
        params.smsRecvYn = $scope.smsRecvYn;
        params.gendrFg = $scope.gendrFg;
        params.memberSaleFg = $scope.memberSaleFg;
        params.memberPointFg = $scope.memberPointFg;
        // params.useYn = $scope.useYn;
        params.useYn = 'Y';

        $scope._inquiryMain("/membr/info/view/view/getMemberListExcel.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
            $timeout(function()	{
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.excelFlex,
                    {
                        includeColumnHeaders: 	true,
                        includeCellStyles	: 	false,
                        includeColumns      :	function (column) {
                            return column.visible;
                        }
                    },
                    '회원정보_'+getToday()+'.xlsx',
                    function () {
                        $timeout(function () {
                            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                        }, 10);
                    }
                );
            }, 10);

        });
    };
    // <-- //검색 호출 -->

}]);