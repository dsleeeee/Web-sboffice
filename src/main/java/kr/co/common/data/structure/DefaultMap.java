package kr.co.common.data.structure;

import kr.co.common.utils.spring.ObjectUtil;
import kr.co.common.utils.spring.StringUtil;

import java.util.HashMap;

public class DefaultMap<Value> extends HashMap<String, Value> {
    private static final long serialVersionUID = -7482195151960285597L;

    @SuppressWarnings("unchecked")
    public DefaultMap() {
        /** 생성자를 통한 공통변수-기본값 설정 */
        super.put("gChk", (Value) new Boolean(false));
    }
    
    public String getStr(Object k) {
        return ObjectUtil.cast(String.class, get(k), "");
    }

    public int getInt(Object k) {
        return ObjectUtil.cast(Integer.class, get(k), 0);
    }
    public long getLong(Object k) {
        return ObjectUtil.cast(Long.class, get(k), 0L);
    }

    @Override
    public Value get(Object k) { 
        return super.get(k);
    }

    @Override
    public Value put(String k, Value v) {
        return super.put(StringUtil.toCamelCaseName(k), v);
    }
}
