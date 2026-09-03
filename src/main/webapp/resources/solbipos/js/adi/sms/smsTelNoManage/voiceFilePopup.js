/****************************************************************
 * 파일명 : voiceFilePopup.js
 * 설  명 : 음성파일 등록 팝업 JavaScript
 * **************************************************************/
var app = agrid.getApp();

var VOICE_FILE_MAX = 10; // 최대 첨부 개수

app.controller('voiceFileCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 공통 컨트롤러 상속
    angular.extend(this, new RootController('voiceFileCtrl', $scope, $http, true));

    // 신청건 키
    $scope.keyOrgnCd = "";
    $scope.keyUserId = "";
    $scope.keyCertId = "";

    // grid 초기화
    $scope.initGrid = function (s, e) {
        // 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col  = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                // 다운로드 : 서버에 저장된 파일(idx 있음)만 링크
                if (col.binding === "download") {
                    if (nvl(item["idx"], "") !== "") {
                        wijmo.addClass(e.cell, 'wijLink');
                    } else {
                        e.cell.innerHTML = "";
                    }
                }
                // 삭제 : 항상 링크
                if (col.binding === "del") {
                    if (item["del"] === '삭제') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 셀 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;

                // 다운로드 클릭 (서버 파일만)
                if (col.binding === "download" && nvl(selectedRow["idx"], "") !== "") {
                    voiceFileDownload(selectedRow.fileNm, selectedRow.orginlFileNm);
                }

                // 삭제 클릭
                if (col.binding === "del" && selectedRow["del"] === '삭제') {
                    // 서버 저장 파일
                    if (nvl(selectedRow["idx"], "") !== "") {
                        $scope._popConfirm(messages["smsGeneralNoManage2.voiceFileDelConfirm"], function () {
                            var params = {};
                            params.orgnCd = $scope.keyOrgnCd;
                            params.userId = $scope.keyUserId;
                            params.certId = $scope.keyCertId;
                            params.idx    = selectedRow.idx;
                            $scope._postJSONSave.withOutPopUp("/adi/sms/smsTelNoManage/smsGeneralNoManage2/delVoiceFile.sb", params, function () {
                                $scope.searchVoiceFile();
                                $scope.refreshMainGrid();
                            });
                        });
                    } else {
                        // 로컬(저장 전) 파일 : input + 그리드에서 제거
                        var files = document.getElementById("voiceFile").files;
                        var dt = new DataTransfer();
                        for (var i = 0; i < files.length; i++) {
                            if (files[i].name !== selectedRow["orginlFileNm"]) {
                                dt.items.add(files.item(i));
                            }
                        }
                        document.getElementById("voiceFile").files = dt.files;

                        for (var j = $scope.flex.collectionView.items.length - 1; j >= 0; j--) {
                            if ($scope.flex.collectionView.items[j].orginlFileNm === selectedRow["orginlFileNm"]
                                && nvl($scope.flex.collectionView.items[j].idx, "") === "") {
                                $scope.flex.collectionView.removeAt(j);
                            }
                        }
                    }
                }
            }
        });
    };

    // 팝업 오픈 : broadcast로 키 받아 조회 (smsPreview 팝업 패턴)
    $scope.$on("voiceFileCtrl", function (event, data) {
        if (data !== undefined && !isEmptyObject(data)) {
            $scope.keyOrgnCd = data.orgnCd;
            $scope.keyUserId = data.userId;
            $scope.keyCertId = data.certId;

            // 첨부파일 input 리셋 (이전 선택 잔여 제거)
            $("#voiceFile").val("");
            if ($("#voiceFileForm").length > 0) { $("#voiceFileForm")[0].reset(); }

            $scope.searchVoiceFile();
        }
        event.preventDefault();
    });

    // 음성파일 목록 조회
    $scope.searchVoiceFile = function () {
        var params = {};
        params.orgnCd = $scope.keyOrgnCd;
        params.userId = $scope.keyUserId;
        params.certId = $scope.keyCertId;

        $scope._postJSONQuery.withOutPopUp("/adi/sms/smsTelNoManage/smsGeneralNoManage2/getVoiceFileList.sb", params, function (response) {
            var list = (response.data && response.data.data) ? response.data.data.list : null;
            var cv = new wijmo.collections.CollectionView(list ? list : []);
            cv.trackChanges = true;
            $scope.data = cv;
            if ($scope.flex) { $scope.flex.refresh(); }   // 이전 로컬행 잔여 방지
        });
    };

    // 첨부파일 추가(로컬) → 그리드에 즉시 표시
    $scope.previewFile = function () {
        var fileInput = document.getElementById("voiceFile");
        var files = fileInput.files;

        // 서버 저장분 유지, 로컬 추가분만 초기화
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            if (nvl($scope.flex.collectionView.items[i].idx, "") === "") {
                $scope.flex.collectionView.removeAt(i);
            }
        }

        // 10개 제한 (서버 저장분 + 선택분)
        var serverCnt = 0;
        for (var k = 0; k < $scope.flex.collectionView.items.length; k++) {
            if (nvl($scope.flex.collectionView.items[k].idx, "") !== "") { serverCnt++; }
        }
        if (serverCnt + files.length > VOICE_FILE_MAX) {
            $scope._popMsg(messages["smsGeneralNoManage2.voiceFileMaxOver"]);
            $("#voiceFile").val("");
            return;
        }

        $timeout(function () {
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                var params = {};
                params.orginlFileNm = file.name;
                params.del      = "삭제";
                params.download = "";
                params.fileExt  = file.name.substring(file.name.lastIndexOf(".") + 1);
                params.fileNm   = "";
                params.idx      = "";
                $scope._addRow(params);
            }
        }, 300);
    };

    // 저장
    $("#funcSaveVoiceFile").click(function () {
        var fileEl = document.getElementById("voiceFile");
        if (!fileEl || !fileEl.files || fileEl.files.length <= 0) {
            $scope._popMsg(messages["smsGeneralNoManage2.voiceFileSelect"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function () {
            var formData = new FormData($("#voiceFileForm")[0]);
            formData.append("orgnCd", $scope.keyOrgnCd);
            formData.append("userId", $scope.keyUserId);
            formData.append("certId", $scope.keyCertId);

            $.ajax({
                url: "/adi/sms/smsTelNoManage/smsGeneralNoManage2/saveVoiceFile.sb",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                cache: false,
                success: function (result) {
                    $timeout(function () {
                        if (result.status === "OK") {
                            $scope._popMsg(messages["cmm.saveSucc"]);
                            $("#voiceFile").val("");
                            $("#voiceFileForm")[0].reset();
                            $scope.searchVoiceFile();   // 업로드한 파일(서버) 재조회 → 다운로드/삭제 표시
                            $scope.refreshMainGrid();   // 메인 그리드 건수 갱신
                        } else {
                            $scope._popMsg(nvl(result.message, messages["cmm.saveFail"]));
                        }
                    });
                },
                error: function () {
                    $scope._popMsg(messages["cmm.saveFail"]);
                }
            });
        });
    });

    // 메인 그리드(음성파일 건수) 갱신 : 조회 완료 메시지 없이(silent) 건수만 갱신
    $scope.refreshMainGrid = function () {
        var mainScope = agrid.getScope('smsGeneralNoManage2Ctrl');
        if (mainScope) { mainScope.searchSmsGeneralNoManage(true); }
    };

    // 팝업 닫기 : 입력/그리드 초기화
    $scope.close = function () {
        $("#voiceFile").val("");
        if ($("#voiceFileForm").length > 0) { $("#voiceFileForm")[0].reset(); }

        var cv = new wijmo.collections.CollectionView([]);
        cv.trackChanges = true;
        $scope.data = cv;
        if ($scope.flex) { $scope.flex.refresh(); }

        $scope.wjVoiceFileLayer.hide();
        event.preventDefault();
    };
}]);

// 첨부파일 추가 트리거(input onchange)
function voiceFilePreview() {
    var scope = agrid.getScope('voiceFileCtrl');
    scope.previewFile();
}

// 음성파일 다운로드 (폼 submit)
function voiceFileDownload(fileNm, downloadFileName) {
    var form = document.createElement("form");
    form.method = "post";
    form.action = "/adi/sms/smsTelNoManage/smsGeneralNoManage2/downloadVoiceFile.sb";

    var f1 = document.createElement("input");
    f1.type = "hidden"; f1.name = "fileName"; f1.value = fileNm;
    form.appendChild(f1);

    var f2 = document.createElement("input");
    f2.type = "hidden"; f2.name = "downloadFileName"; f2.value = downloadFileName;
    form.appendChild(f2);

    document.body.appendChild(form);
    form.submit();
    document.body.removeChild(form);
}
