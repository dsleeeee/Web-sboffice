<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjAlimtalkManualLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:640px;height:755px;" fade-in="false" fade-out="false">
    <div ng-controller="alimtalkManualCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="alimtalkManual.info"/>
            <%-- 카카오계정(채널) 생성 --%>
            <a href="https://accounts.kakao.com/login/kakaobusiness?continue=https://business.kakao.com/dashboard/?sid%3Dpfraa" target="_blank">
                <button class="btn_skyblue fr" style="margin-top:13px; margin-right:320px;"><s:message code='alimtalkIdRegister.kakaoPlusFriendId' /></button>
            </a>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="subCon">
            <%-- 매뉴얼1 --%>
            <div class="w100" id="divManual1" style="display: none;">
                <div>
                    <%--<img src="/resource/solbipos/css/img/alimtalk/manual1.jpg" style="width:100%; height:630px;" />--%>
                    <img src="/resource/solbipos/css/img/alimtalk/manual1.jpg" style="width:100%;" />
                </div>
                <div class="pageNum mt10">
                    <ul>
                        <li class="btn_previous"><a href="#"></a></li>
                        <li><a href="#" class="pagenav on" ng-click="pageChange(1)">1</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(2)">2</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(3)">3</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(4)">4</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(5)">5</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(6)">6</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(7)">7</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(8)">8</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(9)">9</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(10)">10</a></li>
                        <li class="btn_next" ng-click="pageChange(2)"><a href="#"></a></li>
                    </ul>
                </div>
            </div>
            <%-- 매뉴얼2 --%>
            <div class="w100" id="divManual2" style="display: none;">
                <div style="text-align: center;">
                    <img src="/resource/solbipos/css/img/alimtalk/manual2.jpg" style="height:630px;" />
                </div>
                <div class="pageNum mt10">
                    <ul>
                        <li class="btn_previous" ng-click="pageChange(1)"><a href="#"></a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(1)">1</a></li>
                        <li><a href="#" class="pagenav on" ng-click="pageChange(2)">2</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(3)">3</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(4)">4</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(5)">5</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(6)">6</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(7)">7</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(8)">8</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(9)">9</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(10)">10</a></li>
                        <li class="btn_next" ng-click="pageChange(3)"><a href="#"></a></li>
                    </ul>
                </div>
            </div>
            <%-- 매뉴얼3 --%>
            <div class="w100" id="divManual3" style="display: none;">
                <div style="text-align: center;">
                    <img src="/resource/solbipos/css/img/alimtalk/manual3.jpg" style="height:630px;" />
                </div>
                <div class="pageNum mt10">
                    <ul>
                        <li class="btn_previous" ng-click="pageChange(2)"><a href="#"></a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(1)">1</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(2)">2</a></li>
                        <li><a href="#" class="pagenav on" ng-click="pageChange(3)">3</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(4)">4</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(5)">5</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(6)">6</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(7)">7</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(8)">8</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(9)">9</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(10)">10</a></li>
                        <li class="btn_next" ng-click="pageChange(4)"><a href="#"></a></li>
                    </ul>
                </div>
            </div>
            <%-- 매뉴얼4 --%>
            <div class="w100" id="divManual4" style="display: none;">
                <div style="height:630px;">
                    <img src="/resource/solbipos/css/img/alimtalk/manual4.jpg" style="width:100%;" />
                </div>
                <div class="pageNum mt10">
                    <ul>
                        <li class="btn_previous" ng-click="pageChange(3)"><a href="#"></a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(1)">1</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(2)">2</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(3)">3</a></li>
                        <li><a href="#" class="pagenav on" ng-click="pageChange(4)">4</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(5)">5</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(6)">6</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(7)">7</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(8)">8</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(9)">9</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(10)">10</a></li>
                        <li class="btn_next" ng-click="pageChange(5)"><a href="#"></a></li>
                    </ul>
                </div>
            </div>
            <%-- 매뉴얼5 --%>
            <div class="w100" id="divManual5" style="display: none;">
                <div style="height:630px;">
                    <img src="/resource/solbipos/css/img/alimtalk/manual5.jpg" style="width:100%;" />
                </div>
                <div class="pageNum mt10">
                    <ul>
                        <li class="btn_previous" ng-click="pageChange(4)"><a href="#"></a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(1)">1</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(2)">2</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(3)">3</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(4)">4</a></li>
                        <li><a href="#" class="pagenav on" ng-click="pageChange(5)">5</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(6)">6</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(7)">7</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(8)">8</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(9)">9</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(10)">10</a></li>
                        <li class="btn_next" ng-click="pageChange(6)"><a href="#"></a></li>
                    </ul>
                </div>
            </div>
            <%-- 매뉴얼6 --%>
            <div class="w100" id="divManual6" style="display: none;">
                <div style="height:630px;">
                    <img src="/resource/solbipos/css/img/alimtalk/manual6.jpg" style="width:100%;" />
                </div>
                <div class="pageNum mt10">
                    <ul>
                        <li class="btn_previous" ng-click="pageChange(5)"><a href="#"></a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(1)">1</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(2)">2</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(3)">3</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(4)">4</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(5)">5</a></li>
                        <li><a href="#" class="pagenav on" ng-click="pageChange(6)">6</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(7)">7</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(8)">8</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(9)">9</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(10)">10</a></li>
                        <li class="btn_next" ng-click="pageChange(7)"><a href="#"></a></li>
                    </ul>
                </div>
            </div>
            <%-- 매뉴얼7 --%>
            <div class="w100" id="divManual7" style="display: none;">
                <div style="height:630px;">
                    <img src="/resource/solbipos/css/img/alimtalk/manual7.jpg" style="width:100%;" />
                </div>
                <div class="pageNum mt10">
                    <ul>
                        <li class="btn_previous" ng-click="pageChange(6)"><a href="#"></a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(1)">1</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(2)">2</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(3)">3</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(4)">4</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(5)">5</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(6)">6</a></li>
                        <li><a href="#" class="pagenav on" ng-click="pageChange(7)">7</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(8)">8</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(9)">9</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(10)">10</a></li>
                        <li class="btn_next" ng-click="pageChange(8)"><a href="#"></a></li>
                    </ul>
                </div>
            </div>
            <%-- 매뉴얼8 --%>
            <div class="w100" id="divManual8" style="display: none;">
                <div style="height:630px;">
                    <img src="/resource/solbipos/css/img/alimtalk/manual8.jpg" style="width:100%;" />
                </div>
                <div class="pageNum mt10">
                    <ul>
                        <li class="btn_previous" ng-click="pageChange(7)"><a href="#"></a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(1)">1</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(2)">2</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(3)">3</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(4)">4</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(5)">5</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(6)">6</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(7)">7</a></li>
                        <li><a href="#" class="pagenav on" ng-click="pageChange(8)">8</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(9)">9</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(10)">10</a></li>
                        <li class="btn_next" ng-click="pageChange(9)"><a href="#"></a></li>
                    </ul>
                </div>
            </div>
            <%-- 매뉴얼9 --%>
            <div class="w100" id="divManual9" style="display: none;">
                <div style="height:630px;">
                    <img src="/resource/solbipos/css/img/alimtalk/manual9.jpg" style="width:100%;" />
                </div>
                <div class="pageNum mt10">
                    <ul>
                        <li class="btn_previous" ng-click="pageChange(8)"><a href="#"></a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(1)">1</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(2)">2</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(3)">3</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(4)">4</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(5)">5</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(6)">6</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(7)">7</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(8)">8</a></li>
                        <li><a href="#" class="pagenav on" ng-click="pageChange(9)">9</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(10)">10</a></li>
                        <li class="btn_next" ng-click="pageChange(10)"><a href="#"></a></li>
                    </ul>
                </div>
            </div>
            <%-- 매뉴얼10 --%>
            <div class="w100" id="divManual10" style="display: none;">
                <div style="height:630px;">
                    <img src="/resource/solbipos/css/img/alimtalk/manual10.jpg" style="width:100%;" />
                </div>
                <div class="pageNum mt10">
                    <ul>
                        <li class="btn_previous" ng-click="pageChange(9)"><a href="#"></a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(1)">1</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(2)">2</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(3)">3</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(4)">4</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(5)">5</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(6)">6</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(7)">7</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(8)">8</a></li>
                        <li><a href="#" class="pagenav" ng-click="pageChange(9)">9</a></li>
                        <li><a href="#" class="pagenav on" ng-click="pageChange(10)">10</a></li>
                        <li class="btn_next"><a href="#"></a></li>
                    </ul>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkSendType/alimtalkManual.js?ver=20220414.01" charset="utf-8"></script>
