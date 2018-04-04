package kr.co.solbipos.adi.service.dclz.dclzmanage;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.adi.domain.dclz.dclzmanage.DclzManage;
import kr.co.solbipos.adi.persistence.dclz.dclzmanage.DclzManageMapper;

/**
 * 부가서비스 > 근태관리 > 근태관리
 * 
 * @author 정용길
 *
 */
@Service
public class DclzManageServiceImpl implements DclzManageService {

    @Autowired
    DclzManageMapper dclzManageMapper;

    @Override
    public <E> List<E> selectDclzManage(DclzManage dclzManage) {
        return dclzManageMapper.selectDclzManage(dclzManage);
    }

    @Override
    public int insertDclzManage(DclzManage dclzManage) {
        return dclzManageMapper.insertDclzManage(dclzManage);
    }

    @Override
    public int updateDclzManage(DclzManage dclzManage) {
        return dclzManageMapper.updateDclzManage(dclzManage);
    }

    @Override
    public int deleteDclzManage(DclzManage dclzManage) {
        return dclzManageMapper.deleteDclzManage(dclzManage);
    }

    @Override
    public <E> List<E> selectStoreEmployee(DclzManage dclzManage) {
        return dclzManageMapper.selectStoreEmployee(dclzManage);
    }

    @Override
    public int selectWorkCheck(DclzManage dclzManage) {
        return dclzManageMapper.selectWorkCheck(dclzManage);
    }
}
