<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <!--layer:For Center screen-->
    <div id="_brmkFullDimmed" class="fullDimmed" style="display: none;"></div>
    <div id="_brmkLayer" class="layer" style="display: none;">
        <div class="layer_inner">
            <!--layerContent-->
            <div class="title w870px">
                <p class="tit">즐겨찾기 관리</p>
                <a href="#" class="btn_close bkmk_close"></a>
                <div class="con sc2" style="height:550px;"><!--높이는 style로 조정-->
                    <div class="mt10">
                        <table class="tblType01">
                            <colgroup>
                                <col class="w25"/>
                                <col class="w50"/>
                                <col class="w10"/>
                                <col class="w15"/>
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>
                                    메뉴명
                                </th>
                                <td>
                                    <input type="text" class="sb-input w100" id="srchBkmkResrceNm" ng-model="bkmkResrceNm" />
                                </td>
                                <td>
                                    <%-- 조회 --%>
                                    <button class="btn_skyblue bkmk_search"><s:message code="cmm.search" /></button>
                                </td>
                                <td>
                                    <label class="red" id="lblBkmkSearchCnt"></label><span class="red">개 조회됨</span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <p class="s14 mt10">* 조회시 메뉴명은 <span class="red">빨간색</span>으로 표시됩니다. 조회된 <span class="red">메뉴명</span> 클릭시 해당 메뉴로 이동됩니다.</p>
                    <p class="s14">* 즐겨찾기 할 메뉴의 <span class="txtIcFav"></span>을 선택하시면 즐겨찾기 탭에 저장되어 편리하게 이용할 수 있습니다.</p>
                    <p class="s14 mt10">* 고정메뉴를 <span class="txtIcFix"></span>설정해 놓으면 로그인시 고정메뉴가 상단에 표시됩니다. 아이콘을 클릭해서 고정메뉴를 선택해보세요. (최대 3개)</p>
                    <div id="bkmkMenu" class="menuOrg mt10 mb10">
                    </div>
                </div>
                <div class="btnSet">
                    <span><a href="#" class="btn_blue bkmk_save">저장</a></span>
                </div>
            </div>
            <!--//layerContent-->
        </div>
    </div>
    <!--//layer:For Center screen-->

<script type="text/javascript" src="/resource/solbipos/js/application/layer/bkmk.js?ver=20240528.01" charset="utf-8"></script>
