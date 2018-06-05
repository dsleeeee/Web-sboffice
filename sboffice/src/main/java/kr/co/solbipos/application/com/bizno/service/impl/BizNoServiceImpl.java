package kr.co.solbipos.application.com.bizno.service.impl;

import org.springframework.stereotype.Service;
import kr.co.solbipos.application.com.bizno.service.BizNoService;
import kr.co.solbipos.application.com.bizno.service.BizNoVO;

@Service
public class BizNoServiceImpl implements BizNoService {

    @Override
    public boolean bizNoVerify(BizNoVO bizNoVO) {

        int total = 0;
        int temp = 0;
        int checkDigit[] = {1, 3, 7, 1, 3, 7, 1, 3, 5}; // 사업자번호 유효성 체크 필요한 수
        // 입력받은 사업자번호
        String bizNo = bizNoVO.getBizNo().replaceAll("[^0-9]", "");
        // 사업자번호의 길이가 맞는지를 확인한다.
        if ( bizNo.length() != 10 ) {
            return false;
        }
        // 숫자가 아닌 값이 들어왔는지를 확인한다.
        for ( int i = 0; i < 9; i++ ) {
            if ( bizNo.charAt(i) < '0' || bizNo.charAt(i) > '9' ) {
                return false;
            }
            // 검증식
            total = total + (Character.getNumericValue(bizNo.charAt(i)) * checkDigit[temp]);
            temp++;
        }
        // 사업자번호의 총 합계
        total += (Character.getNumericValue(bizNo.charAt(8)) * 5) / 10;
        // 마지막 유효숫자와 검증식을 통한 값의 비교
        if ( (10 - (total % 10)) % 10 == Character.getNumericValue(bizNo.charAt(9)) ) {
            return true;
        } else {
            return false;
        }
    }

}
