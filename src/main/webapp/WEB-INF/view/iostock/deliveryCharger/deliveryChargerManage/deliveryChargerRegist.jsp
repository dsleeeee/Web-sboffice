<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    angular.module('mainApp', []).controller('dlvrInfoCtrl', ['$scope', '$http', function($scope, $http) {
        $scope.master = {};

        $scope.$on('dlvrInfoCtrl', function(event, message) {
            console.log(message.dlvrCd);
        });

        $scope.searchDlvr = function() {
            var param = {};
            param.dlvrCd = '0001';

            $http({
                method: 'POST', //방식
                url: "/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/dlvrInfo.sb", /* 통신할 URL */
                params: param, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                // this callback will be called asynchronously
                // when the response is available

                console.log("====response====");
                console.log(response);

                var data = response.data.data;
                // if (data === undefined || data === null) {
                //     s_alert.pop(response.data.message);
                //     return;
                // }
                $scope.dlvr = data;
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                console.log(response);
                s_alert.pop(response.data.message);
                return;
            }).then(function() {
                // "complete" code here
                // if ( callback != null ) {
                //     setTimeout(function() {
                //         callback;
                //     }, 10);
                // }
            });
        };

        $scope.submitForm = function() {
            $http({
                method: 'POST', //방식
                url: "/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/dlvrSave.sb", /* 통신할 URL */
                params: $scope.dlvr, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                // this callback will be called asynchronously
                // when the response is available

                console.log("====response====");
                console.log(response);

                // var data = response.data.data;
                // if (data === undefined || data === null) {
                //     s_alert.pop(response.data.message);
                //     return;
                // }
                s_alert.pop(messages["cmm.saveSucc"]);

                // 초기화
                $scope.dlvr = angular.copy($scope.master);

            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                console.log(response);
                s_alert.pop(response.data.message);
                return;
            }).then(function() {
                // "complete" code here
                // if ( callback != null ) {
                //     setTimeout(function() {
                //         callback;
                //     }, 10);
                // }
            });
        };

    }]);

    function openRegist() {
        $("#fullDimmedLayer").show();
        $("#dlvrRegistLayer").show();
    }

    <%-- 닫기 버튼 클릭 --%>
    $(".btn_close").click(function(e){
        // resetForm();
        $("#fullDimmedLayer").hide();
        $("#dlvrRegistLayer").hide();
    });

    function valueCheck(sendUrl) {
        <%-- 기사명을 입력해주세요. --%>
        var msg = "<s:message code='deliveryCharger.dlvrNm'/> <s:message code='cmm.require.text'/>";
        if($("#dlvrNm").val() === "") {
            s_alert.popOk(msg, function(){ $("#dlvrNm").focus(); });
            return;
        }

        <%-- 기사명의 길이가 너무 깁니다. --%>
        var msg = "<s:message code='deliveryCharger.dlvrNm'/> <s:message code='deliveryCharger.textOver'/>";
        if($("#dlvrNm").val().getKr3ByteLength() > 18) {
            s_alert.popOk(msg, function(){ $("#dlvrNm").select(); });
            return;
        }

        <%-- 차량번호를 입력해주세요. --%>
        var msg = "<s:message code='deliveryCharger.carNo'/> <s:message code='cmm.require.text'/>";
        if($("#carNo").val() === "") {
            s_alert.popOk(msg, function(){ $("#carNo").focus(); });
            return;
        }

        <%-- 차량번호의 길이가 너무 깁니다. --%>
        var msg = "<s:message code='deliveryCharger.carNo'/> <s:message code='deliveryCharger.textOver'/>";
        if($("#carNo").val().getKr3ByteLength() > 14) {
            s_alert.popOk(msg, function(){ $("#carNo").select(); });
            return;
        }

        <%-- 전화번호는 숫자만 입력할 수 있습니다. --%>
        var msg = "<s:message code='deliveryCharger.telNo'/> <s:message code='cmm.require.number'/>";
        var numChkregexp = /[^0-9]/g;
        if(numChkregexp.test($("#telNo").val())) {
            s_alert.popOk(msg, function(){ $("#telNo").select(); });
            return;
        }

        <%-- 전화번호를 정확히 입력해주세요. --%>
        var msg = "<s:message code='deliveryCharger.telNo'/> <s:message code='deliveryCharger.validCheck'/>";
        if($("#telNo").val() !== "" && $("#telNo").val().length < 10) {
            s_alert.popOk(msg, function(){ $("#telNo").select(); });
            return;
        }

        <%-- 핸드폰번호는 숫자만 입력할 수 있습니다. --%>
        var msg = "<s:message code='deliveryCharger.hpNo'/> <s:message code='cmm.require.number'/>";
        if(numChkregexp.test($("#hpNo").val())) {
            s_alert.popOk(msg, function(){ $("#hpNo").select(); });
            return;
        }

        <%-- 핸드폰번호를 정확히 입력해주세요. --%>
        var msg = "<s:message code='deliveryCharger.hpNo'/> <s:message code='deliveryCharger.validCheck'/>";
        if($("#hpNo").val() !== "" && $("#hpNo").val().length < 10) {
            s_alert.popOk(msg, function(){ $("#hpNo").select(); });
            return;
        }


        save(sendUrl);
    }

</script>


<div id="fullDimmedLayer" class="fullDimmed" style="display:none;"></div>
<div id="dlvrRegistLayer" class="layer" style="display:none;" ng-controller="dlvrInfoCtrl">
    <div class="layer_inner">
        <!--layerContent-->
        <div class="title w800">
            <p class="tit">배송기사</p>
            <a href="javascript:;" class="btn_close"></a>
            <div class="con sc2" style="height:500px;"><!--높이는 style로 조정-->
                <form id="dlvrForm" ng-submit="submitForm()">
                    <table class="tblType01">
                        <colgroup>
                            <col class="w15" />
                            <col class="w35" />
                            <col class="w15" />
                            <col class="w35" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <th><s:message code="deliveryCharger.dlvrCdNm" /><em class="imp">*</em></th>
                            <td><input type="text" id="dlvrCd" class="sb-input" style="width:49%" ng-model="dlvr.dlvrCd" readonly/> <input type="text" id="dlvrNm" class="sb-input" style="width:49%" ng-model="dlvr.dlvrNm" maxlength="18"/></td>
                            <th><s:message code="deliveryCharger.carNo" /><em class="imp">*</em></th>
                            <td><input type="text" id="carNo" class="sb-input w100" maxlength="14" ng-model="dlvr.carNo"/></td>
                        </tr>
                        <tr>
                            <th><s:message code="deliveryCharger.telNo"/></th>
                            <td><input type="text" id="telNo" class="sb-input w100" maxlength="15" ng-model="dlvr.telNo"/></td>
                            <th><s:message code="deliveryCharger.hpNo"/></th>
                            <td><input type="text" id="hpNo" class="sb-input w100" maxlength="15" ng-model="dlvr.hpNo"/></td>
                        </tr>
                        <tr>
                            <th><s:message code="deliveryCharger.useYn"/></th>
                            <td>
                                <select id="useYn" ng-model="dlvr.useYn">
                                    <option value="Y"><s:message code="deliveryCharger.useYnY"/></option>
                                    <option value="N"><s:message code="deliveryCharger.useYnN"/></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th><s:message code="deliveryCharger.remark"/></th>
                            <td colspan="3">
                                <div>
                                    <textarea id="remark" class="w100 tArea1" style="height:100px;" ng-model="dlvr.remark"></textarea>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
                <div class="mt40 tr">
                    <button id="btnAdd" class="btn_skyblue fr mr5" ng-click="searchDlvr()">조회</button>
                    <a href="#" class="btn_grayS2">창고추가</a>
                    <a href="#" class="btn_grayS2">창고삭제</a>
                </div>
                <!--위즈모 테이블-->
                <div>

                </div>
                <!--//위즈모 테이블-->
            </div>
        </div>
        <!--//layerContent-->
    </div>
</div>


