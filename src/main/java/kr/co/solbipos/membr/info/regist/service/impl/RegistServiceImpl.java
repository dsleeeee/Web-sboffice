package kr.co.solbipos.membr.info.regist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.ObjectUtil;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("registService")
@Transactional
public class RegistServiceImpl implements RegistService {

    @Autowired
    RegistMapper mapper;

    @Override
    public List<DefaultMap<String>> selectRgstrStore() {
        return mapper.selectRgstrStore();
    }

    @Override
    public int insertRegistMember(RegistVO registVO) {
        return mapper.insertRegistMember(registVO);
    }

    @Override
    public RegistVO selectMember(RegistVO registVO) {
        return mapper.selectMember(registVO);
    }

    @Override
    public <E> List<E> selectMembers(RegistVO registVO) {
        return mapper.selectMembers(registVO);
    }

    @Override
    public int updateMember(RegistVO registVO) {
        return mapper.updateMember(registVO);
    }

    @Override
    public int deleteMember(RegistVO registVO) {
        return mapper.deleteMember(registVO);
    }

    @Override
    public int insertMembrCard(RegistVO registVO) {
        return mapper.insertMembrCard(registVO);
    }

    @Override
    public int updateMembrCard(RegistVO registVO) {
        return mapper.updateMembrCard(registVO);
    }

    @Override
    public int saveRegistMember(RegistVO registVO) {
        RegistVO chkR = selectMember(registVO);
        int result = 0;
        // 없는 회원이면 신규 저장
        if(ObjectUtil.isEmpty(chkR)) {
            result = insertRegistMember(registVO);
            if(result == 1) {
                // 회원카드 등록
                insertMembrCard(registVO);
            }
        }
        // 있는 회원이면 수정
        else {
            result = updateMember(registVO);
            if(result == 1) {
                // 회원카드 수정
                updateMembrCard(registVO);
            }
        }
        return result;
    }
}
