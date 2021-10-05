/****************************************************************
 *
 * 파일명 : msgManage.js
 * 설  명 : 메세지관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.22     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 메세지관리 목록 수정
function msgModify(seqNo, title, message) {
    var scope = agrid.getScope('msgManageDtlCtrl');
    var params = {};
    params.seqNo = seqNo;
    params.title = title;
    params.message = message;
    scope.msgModify(params);
}

// 메세지관리 목록 삭제
function msgDel(seqNo) {
    var scope = agrid.getScope('msgManageDtlCtrl');
    var params = {};
    params.seqNo = seqNo;
    scope.msgDel(params);
}

/**
 *  메세지관리 조회 그리드 생성
 */
app.controller('msgManageCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('msgManageCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 그룹코드
                if (col.binding === "msgGrpCd") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }

                if(orgnFg == "STORE") {
                    // 그룹명
                    if (col.binding === "msgGrpNm" || col.binding === "gChk") {
                        var item = s.rows[e.row].dataItem;
                        // 값이 있으면 링크 효과
                        if (parseInt(item[("msgGrpCd")]) < 81) {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            item[("gChk")] = false; // 전체 체크시 오류

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 그룹코드 클릭시 상세정보 조회
                if ( col.binding === "msgGrpCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params = {};
                    params.msgGrpCd = selectedRow.msgGrpCd;
                    params.msgGrpNm = selectedRow.msgGrpNm;

                    var storeScope = agrid.getScope('msgManageDtlCtrl');
                    storeScope._broadcast('msgManageDtlCtrl', params);
                    event.preventDefault();
                }
            }
        });

        // 조회
        $scope.searchMsgManage();
    };

    // <-- 검색 호출 -->
    $scope.$on("msgManageCtrl", function(event, data) {
        $scope.searchMsgManage();
        event.preventDefault();
    });

    $scope.searchMsgManage = function() {
        var params = {};

        $scope._inquiryMain("/adi/sms/msgManage/msgManage/getMsgManageList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('msgManageDtlCtrl');
                storeScope._broadcast('msgManageDtlCtrl', null);
            });
        }, false);
    };

    $scope.searchMsgManageAllSearch = function(data) {
        var params = {};

        $scope._inquiryMain("/adi/sms/msgManage/msgManage/getMsgManageList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('msgManageDtlCtrl');
                storeScope._broadcast('msgManageDtlCtrl', data);
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // <-- 그리드 행 추가 -->
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.status = "I";
        params.gChk = true;
        params.msgGrpCd="자동채번";
        params.msgGrpNm = "";
        params.msgGrpCnt = "0";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };
    // <-- //그리드 행 추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        // 파라미터 설정
        var paramsChk = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                paramsChk.push($scope.flex.collectionView.items[i]);
            }
        }

        if(paramsChk.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        // 메세지그룹을 삭제하시면 <br/> 해당 그룹의 메세지 전체가 삭제됩니다. <br/> 그래도 삭제하시겠습니까?
        $scope._popConfirm(messages["msgManage.delMsgConfirm"], function() {
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];

                if(item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
                $scope.flex.collectionView.itemsRemoved[i].status = "D";
                params.push($scope.flex.collectionView.itemsRemoved[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/msgManage/msgManage/getMsgManageSave.sb", params, function(){ $scope.searchMsgManage();});
        });
    };
    // <-- //그리드 행 삭제 -->

    // <-- 그리드 저장 -->
    $scope.save = function() {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].msgGrpNm === "") {
                $scope._popMsg(messages["msgManage.msgGrpNmBlank"]); // 그룹명을 입력해주세요.
                return false;
            }
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                $scope.flex.collectionView.itemsAdded[i].status = "I";
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/msgManage/msgManage/getMsgManageSave.sb", params, function(){ $scope.searchMsgManage();});
        });
    };
    // <-- //그리드 저장 -->


    // 매장적용
    $scope.storeApply = function () {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        $scope.setSelectedMsgManage(params);
        $scope.wjMsgManageStoreRegistLayer.show(true);
        event.preventDefault();
    };

    // 선택
    $scope.selectedMsgManage;
    $scope.setSelectedMsgManage = function(store) {
        $scope.selectedMsgManage = store;
    };
    $scope.getSelectedMsgManage = function() {
        return $scope.selectedMsgManage;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 메세지관리 매장적용 팝업 핸들러 추가
        $scope.wjMsgManageStoreRegistLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('msgManageStoreRegistCtrl', $scope.getSelectedMsgManage());
            }, 50)
        });
    });
}]);


/**
 *  메세지관리 상세 조회 그리드 생성
 */
app.controller('msgManageDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('msgManageDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $("#lblMsgManageTxtByte").text("0");
    $("#lblMsgManageMsgType").text("SMS");

    // <-- 검색 호출 -->
    $scope.$on("msgManageDtlCtrl", function(event, data) {
        $("#divMsgManageMsgComment").html("");
        // 신규
        $scope.msgNew();

        if(data != undefined) {
            $("#lblMsgGrpCd").text(data.msgGrpCd);
            $("#lblMsgGrpNmInfo").text(" / ");
            $("#lblMsgGrpNm").text(data.msgGrpNm);

            $scope.searchMsgManageDtl(data);

        } else {
            $("#lblMsgGrpCd").text("");
            $("#lblMsgGrpNmInfo").text("");
            $("#lblMsgGrpNm").text("");
        }
        event.preventDefault();
    });

    $scope.searchMsgManageDtl = function(data) {
        var params = {};
        params.msgGrpCd = data.msgGrpCd;

        $scope._postJSONQuery.withOutPopUp("/adi/sms/msgManage/msgManage/getMsgManageDtlList.sb", params, function(response) {
            var list = response.data.data.list;
            var innerHtml = "";

            if(list.length > 0) {
                for(var i=0; i < list.length; i++) {
                    innerHtml += "<div style=\"float:left; text-align:center; width:125px; height:160px; padding-top:10px; padding-right:10px;\">";
                    innerHtml += "<table>";
                    innerHtml += "<colgroup>";
                    innerHtml += "<col class=\"w100\" />";
                    innerHtml += "</colgroup>";
                    innerHtml += "<tbody>";
                    innerHtml += "<table>";
                    innerHtml += "<tr><td><input type=\"text\" class=\"sb-input-msg w100\" value=\""+ list[i].title +"\" readonly/></td></tr>";
                    innerHtml += "<tr style=\"height: 10px\"></tr>";
                    innerHtml += "<tr><td><textarea style=\"width:100%; height:90px; overflow-x:hidden; background-color: #EAF7FF\" readonly>" + list[i].message + "</textarea></td></tr>";
                    innerHtml += "<tr style=\"height: 10px\"></tr>";
                    // 본사 메세지 수정 불가
                    if(orgnFg == "STORE") {
                        if(parseInt(params.msgGrpCd) > 80) {
                            innerHtml += "<tr><td>";
                            innerHtml += "<span style=\"padding-right: 10px;\"><a href=\"#\" onclick=\"msgModify(\'"+ list[i].seqNo + "\', \'"+ list[i].title + "\', \'"+ list[i].message + "\')\"><img src=\"/resource/solbipos/css/img/sms/btn_upd.jpg\"></a></span>";
                            innerHtml += "<span><a href=\"#\" onclick=\"msgDel(\'"+ list[i].seqNo + "\')\"><img src=\"/resource/solbipos/css/img/sms/btn_del.jpg\"></a></span>";
                            innerHtml += "</td></tr>";
                        }
                    }
                    innerHtml += "</table>";
                    innerHtml += "</div>";
                }
                $("#divMsgManageMsgComment").html(innerHtml);
            }
        }, false);
    };
    // <-- //검색 호출 -->

    // 자동변환
    $scope.addMsg = function(str) {
        var msgContent = $("#msgManageMessageContent").val();
        $("#msgManageMessageContent").val(msgContent + str);

        // 바이트
        $scope.showByte();
    };

    // 바이트
    $scope.showByte = function() {
        if($("#lblMsgGrpCd").text() == "") {
            $scope._popMsg(messages["msgManage.msgGrpCdAlert"]); // 메세지그룹을 선택해주세요.
            return false;
        }

        $("#lblMsgManageTxtByte").text($("#msgManageMessageContent").val().getByteLength());

        if($("#msgManageMessageContent").val().getByteLength() > 80) {
            $("#lblMsgManageMsgType").text("LMS");
        } else {
            $("#lblMsgManageMsgType").text("SMS");
        }
    };

    // 신규
    $scope.msgNew = function () {
        $("#msgManageTitle").val("");
        $("#msgManageMessageContent").val("");
        $("#lblMsgManageTxtByte").text("0");
        $("#lblMsgManageMsgType").text("SMS");
        $("#lblSeqNo").text("");
    };

    // 저장
    $scope.msgSave = function () {
        // 본사 메세지 수정 불가
        if(orgnFg == "STORE") {
            if (parseInt($("#lblMsgGrpCd").text()) < 81) {
                $scope._popMsg(messages["msgManage.saveHqMsgGrpCdAlert"]); // 본사 메세지그룹에는 신규등록 할 수 없습니다.
                return false;
            }
        }

        if($("#msgManageTitle").val() == "") {
            $scope._popMsg(messages["msgManage.titleAlert"]); // 제목을 입력해주세요.
            return false;
        }
        if($("#msgManageMessageContent").val() == "") {
            $scope._popMsg(messages["msgManage.messageContentAlert"]); // 메세지를 입력해주세요.
            return false;
        }

        var seqNo = $("#lblSeqNo").text();

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            var params = {};
            params.msgGrpCd = $("#lblMsgGrpCd").text();
            params.title = $("#msgManageTitle").val();
            params.content = $("#msgManageMessageContent").val();
            if(seqNo == "") {
                params.seqNo = "";
                params.status = "I";
            } else {
                params.seqNo = seqNo;
                params.status = "U";
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/msgManage/msgManage/getMsgManageDtlSave.sb", params, function(){ $scope.allSearch(); });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        // 메세지관리 조회
        var params = {};
        params.msgGrpCd = $("#lblMsgGrpCd").text();
        params.msgGrpNm = $("#lblMsgGrpNm").text();

        // 신규,삭제,수정된 내역 재조회
        var msgManageScope = agrid.getScope('msgManageCtrl');
        msgManageScope.searchMsgManageAllSearch(params);
    };

    // 메세지관리 목록 수정
    $scope.msgModify = function (data) {
        $("#lblSeqNo").text(data.seqNo);
        $("#msgManageTitle").val(data.title);
        $("#msgManageMessageContent").val(data.message);

        // 바이트
        $scope.showByte();
    };

    // 메세지관리 목록 삭제
    $scope.msgDel = function (data) {
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            var params = {};
            params.msgGrpCd = $("#lblMsgGrpCd").text();
            params.seqNo = data.seqNo;
            params.status = "D";

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/msgManage/msgManage/getMsgManageDtlSave.sb", params, function(){ $scope.allSearch(); });
        });
    };
}]);