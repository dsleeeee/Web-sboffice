<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<wj-popup id="wjOutStockEnvstInfoLayer" control="wjOutStockEnvstInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:850px;height:750px;">
    <div id="outStockEnvstInfoLayer" class="wj-dialog wj-dialog-columns" ng-controller="outStockEnvstInfoCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="outstockConfm.outStockEnvstInfo"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 700px;">
            <span class="fl bk s12">파란색:기본값</span>
            <%-- 출고 안내문--%>
            <table class="tblType01 mt20" id="tblInOut">
                <colgroup>
                    <col class="w25"/>
                    <col class="w20"/>
                    <col class="w55"/>
                </colgroup>
                <tbody>
                    <tr>
                        <th colspan="3" class="tc">본사환경</th>
                    </tr>
                    <tr>
                        <th class="tc">환경설정</th>
                        <th class="tc">옵션</th>
                        <th class="tc">설명</th>
                    </tr>
                    <tr>
                        <td rowspan="2">[0011] 출고가-부가세포함여부</td>
                        <td>부가세-포함</td>
                        <td>매장에서 주문/반품 시 상품 공급단가에 부가세가 포함되어 총금액에 합산</br>
                            ▶ 원가단가:1000, 금액:910, 부가세:90, 총금액:1000</td>
                    </tr>
                    <tr>
                        <td class="blue">부가세-별도</td>
                        <td>매장에서 주문/반품 시 상품 공급단가에 부가세가 별도로 추가되어 총금액에 합산</br>
                            ▶ 원가단가:1000, 금액:1000, 부가세:100, 총금액:1100</td>
                    </tr>
                    <tr>
                        <td rowspan="3">[1042] 수발주옵션</td>
                        <td>매장확정안함</td>
                        <td>매장에서 주문/반품 확정 진행불가</td>
                    </tr>
                    <tr>
                        <td class="blue">매장확정(분배마감)</td>
                        <td>매장에서 주문/반품 확정 진행 시 분배/반품 마감까지 진행</td>
                    </tr>
                    <tr>
                        <td>매장확정(출고자료생성)</td>
                        <td>매장에서 주문/반품 확정 진행 시 출고/반품 자료생성</td>
                    </tr>
                    <tr>
                        <td rowspan="3">[1043] 매장입고시수량변경</td>
                        <td>자동입고</td>
                        <td>본사에서 출고 확정 시 매장에 자동으로 입고확정 처리</td>
                    </tr>
                    <tr>
                        <td>변경불가</td>
                        <td>매장에서 입고확정 시 입고수량 변경 불가</td>
                    </tr>
                    <tr>
                        <td class="blue">변경가능</td>
                        <td>매장에서 입고확정 시 입고수량 변경 가능</td>
                    </tr>
                    <tr>
                        <td rowspan="2">[1044] 출고요청일자선택</td>
                        <td>선택불가</td>
                        <td>-</td>
                    </tr>
                    <tr>
                        <td class="blue">선택가능</td>
                        <td>-</td>
                    </tr>
                    <tr>
                        <td rowspan="3">[1242] 거래처출고구분</td>
                        <td class="blue">본사주문</td>
                        <td>출고자료 생성 시 본사로 출고전표 생성</td>
                    </tr>
                    <tr>
                        <td>거래처선택주문</td>
                        <td>매장에서 주문 시 거래처를 선택해서 주문</td>
                    </tr>
                    <tr>
                        <td>거래처별출고전표자동생성</td>
                        <td>출고자료 생성 시 상품 거래처별 출고전표 자동생성(거래처가 직배송 사용 시)</td>
                    </tr>
                    <tr>
                        <td rowspan="2">[1304] 재고-수불사용여부</td>
                        <td>재고사용</td>
                        <td>좌측 메뉴리스트에 재고관리, 수불관리 메뉴 표시</td>
                    </tr>
                    <tr>
                        <td class="blue">재고미사용</td>
                        <td>좌측 메뉴리스트에 재고관리, 수불관리 메뉴 미표시</td>
                    </tr>
                    <tr>
                        <th colspan="3" class="tc">매장환경</th>
                    </tr>
                    <tr>
                        <th class="tc">환경설정</th>
                        <th class="tc">옵션</th>
                        <th class="tc">설명</th>
                    </tr>
                    <tr>
                        <td rowspan="2">[0011] 출고가-부가세포함여부</td>
                        <td>부가세-포함</td>
                        <td>매장에서 주문/반품 시 상품 공급단가에 부가세가 포함되어 총금액에 합산</td>
                    </tr>
                    <tr>
                        <td class="blue">부가세-별도</td>
                        <td>매장에서 주문/반품 시 상품 공급단가에 부가세가 별도로 추가되어 총금액에 합산</td>
                    </tr>
                    <tr>
                        <td rowspan="3">[1043] 매장입고시수량변경</td>
                        <td>자동입고</td>
                        <td>본사에서 출고 확정 시 매장에 자동으로 입고확정 처리</td>
                    </tr>
                    <tr>
                        <td>변경불가</td>
                        <td>입고확정 시 입고수량 변경 불가</td>
                    </tr>
                    <tr>
                        <td class="blue">변경가능</td>
                        <td>입고확정 시 입고수량 변경 가능</td>
                    </tr>
                    <tr>
                        <td rowspan="2">[1044] 출고요청일자선택</td>
                        <td>선택불가</td>
                        <td>상품 입출고 요청등록 시 요청일자 선택불가</td>
                    </tr>
                    <tr>
                        <td class="blue">선택가능</td>
                        <td>상품 입출고 요청등록 시 요청일자 선택가능</td>
                    </tr>
                    <tr>
                        <td rowspan="2">[1304] 재고-수불사용여부</td>
                        <td>재고사용</td>
                        <td>좌측 메뉴리스트에 재고관리, 수불관리 메뉴 표시</td>
                    </tr>
                    <tr>
                        <td class="blue">재고미사용</td>
                        <td>좌측 메뉴리스트에 재고관리, 수불관리 메뉴 미표시</td>
                    </tr>
                    <tr>
                        <th colspan="3" class="tc">화면 내 설정</th>
                    </tr>
                    <tr>
                        <th class="tc">환경설정</th>
                        <th class="tc">옵션</th>
                        <th class="tc">설명</th>
                    </tr>
                    <tr>
                        <td rowspan="2">거래처정보의 부가세포함여부</td>
                        <td>포함</td>
                        <td>거래처 입/출고 등록 시 상품 원가단가에 부가세가 포함되어 총금액에 합산</td>
                    </tr>
                    <tr>
                        <td class="blue">별도</td>
                        <td>거래처 입/출고 등록 시 상품 원가단가에 부가세가 별도로 추가되어 총금액에 합산</td>
                    </tr>
                    <tr>
                        <td rowspan="3">상품정보의 과세여부</td>
                        <td class="blue">과세</td>
                        <td>거래처 입/출고, 본사-매장 주문/반품 등록 시 부가세가 적용되어 금액에 반영</td>
                    </tr>
                    <tr>
                        <td>면세</td>
                        <td>거래처 입/출고, 본사-매장 주문/반품 등록 시 부가세가 미적용되어 금액에 반영</br>
                            <p class="red">※부가세 설정과 관계 없이 부가세 미적용</p></td>
                    </tr>
                    <tr>
                        <td>의제면세</td>
                        <td>거래처 입/출고, 본사-매장 주문/반품 등록 시 부가세가 미적용되어 금액에 반영</br>
                            <p class="red">※부가세 설정과 관계 없이 부가세 미적용</p></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/outstockConfm/outStockEnvstInfo.js?ver=20260123.01" charset="utf-8"></script>