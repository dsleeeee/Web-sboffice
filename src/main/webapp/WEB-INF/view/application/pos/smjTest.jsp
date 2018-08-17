<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>

<f:form modelAttribute="sessionInfo" method="post" action="/application/pos/posLogin.sb">
    매장코드 : <input type="text" id="storeCd" name="storeCd" value="D000001"/>
    <%--<input type="text" id="userId" name="userId" value="111"/>--%>
    하드웨어인증키 : <input type="text" id="hwAuthKey" name="hwAuthKey" value="1234"/>
    요청url : <input type="text" id="url" name="url" value="simpleMemberJoin"/>

    <button type="submit" class="btn_skyblue">gogogogo</button>
</f:form>

