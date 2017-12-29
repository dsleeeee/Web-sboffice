package kr.co.solbipos.structure;

import java.util.HashMap;
import kr.co.solbipos.utils.spring.ObjectUtil;
import kr.co.solbipos.utils.spring.StringUtil;

public class DefaultMap<Value> extends HashMap<String, Value> {
    private static final long serialVersionUID = 717352001507309726L;

    public String getStr(Object k) {
        return ObjectUtil.cast(String.class, get(k), "");
    }

    public int getInt(Object k) {
        return ObjectUtil.cast(Integer.class, get(k), 0);
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
