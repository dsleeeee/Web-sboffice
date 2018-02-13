<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimal-ui">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><s:message code="title.web"/></title>
</head>
<body>
  <div class="Error_wrap">
    <div class="Error_inner">
      <div class="Error_ico"></div>
      <div class="Error_txt_01">
        <ul>
          <li><s:message code="error.access.denied"/></li>
          <li>잘못된 주소를 입력하셨거나, 시스템 오류가 발생하여 페이지를 불러올 수 없습니다.</li>
          <li>잠시 후 다시 시도해주세요.</li>
        </ul>
      </div>
    </div>
  </div>
</body>
</html>