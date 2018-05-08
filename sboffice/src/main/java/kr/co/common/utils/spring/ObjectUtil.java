package kr.co.common.utils.spring;

import java.beans.PropertyEditor;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.springframework.beans.propertyeditors.ByteArrayPropertyEditor;
import org.springframework.beans.propertyeditors.CharArrayPropertyEditor;
import org.springframework.beans.propertyeditors.CharacterEditor;
import org.springframework.beans.propertyeditors.CharsetEditor;
import org.springframework.beans.propertyeditors.ClassArrayEditor;
import org.springframework.beans.propertyeditors.ClassEditor;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomMapEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.FileEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.util.ObjectUtils;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.domain.cmm.CmmVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectUtil extends ObjectUtils {
    @SuppressWarnings("serial")
    private static final Map<Class<?>, PropertyEditor> EDITOR =
            new HashMap<Class<?>, PropertyEditor>() {
                StringArrayPropertyEditor sae = new StringArrayPropertyEditor();
                {
                    put(String.class, new StringTrimmerEditor(true));
                    put(Charset.class, new CharsetEditor());
                    put(Class.class, new ClassEditor());
                    put(Class[].class, new ClassArrayEditor());
                    put(Currency.class, new CurrencyEditor());
                    put(File.class, new FileEditor());

                    put(Collection.class, new CustomCollectionEditor(Collection.class));
                    put(Set.class, new CustomCollectionEditor(Set.class));
                    put(SortedSet.class, new CustomCollectionEditor(SortedSet.class));
                    put(List.class, new CustomCollectionEditor(List.class));
                    put(SortedMap.class, new CustomMapEditor(SortedMap.class));

                    put(byte[].class, new ByteArrayPropertyEditor());
                    put(char[].class, new CharArrayPropertyEditor());

                    put(char.class, new CharacterEditor(false));
                    put(Character.class, new CharacterEditor(true));

                    put(boolean.class, new CustomBooleanEditor(false));
                    put(Boolean.class, new CustomBooleanEditor(true));

                    put(byte.class, new CustomNumberEditor(Byte.class, false));
                    put(Byte.class, new CustomNumberEditor(Byte.class, true));
                    put(short.class, new CustomNumberEditor(Short.class, false));
                    put(Short.class, new CustomNumberEditor(Short.class, true));
                    put(int.class, new CustomNumberEditor(Integer.class, false));
                    put(Integer.class, new CustomNumberEditor(Integer.class, true));
                    put(long.class, new CustomNumberEditor(Long.class, false));
                    put(Long.class, new CustomNumberEditor(Long.class, true));
                    put(float.class, new CustomNumberEditor(Float.class, false));
                    put(Float.class, new CustomNumberEditor(Float.class, true));
                    put(double.class, new CustomNumberEditor(Double.class, false));
                    put(Double.class, new CustomNumberEditor(Double.class, true));
                    put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
                    put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));

                    put(String[].class, sae);
                    put(short[].class, sae);
                    put(int[].class, sae);
                    put(long[].class, sae);
                }
            };

    /**
     * <pre>
     *   ex1 ) ObjectUtil &gt; objectUtil
     * </pre>
     * 
     * @param Object
     * @return A string but First lowercase character name.
     */
    public static String getDefaultVariableName(Object o) {
        if (o == null)
            return null;

        String str = o.getClass().getSimpleName();

        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 
     * @param c Cast Class
     * @param v Value
     * @param d Default Value
     * @return cast value
     */
    public static <T> T cast(Class<T> c, Object v, T d) {
        PropertyEditor pe = EDITOR.get(c);

        if (pe == null)
            return d;

        try {
            if (v instanceof Character || v instanceof CharSequence)
                pe.setAsText(v.toString());
            else
                pe.setValue(v);

            @SuppressWarnings("unchecked")
            T t = (T) (c.equals(String.class) ? pe.getAsText() : pe.getValue());

            return t == null ? d : t;
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error("Cast Fail : {} to {}", v, c.getName());

            return d;
        }
    }

    public static DefaultMap<Object> convertDefultMap(Object o) {
        DefaultMap<Object> m = new DefaultMap<Object>();

        if (o instanceof CmmVO) {
            Field[] fields = o.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                Object val = null;

                try {
                    val = field.get(o);
                } catch (IllegalAccessException e) {
                    if (log.isErrorEnabled())
                        log.error(e.getMessage());

                    return m;
                }

                if (!isEmpty(val))
                    m.put(field.getName(), val);
            }

        }

        return m;
    }

    /**
     * 내용 복사
     * 
     * @param target BaseDomain
     * @param value BaseDomain
     */
    public static void copyValue(CmmVO target, CmmVO value) {
        if (target == null || target == value)
            return;

        Class<?> clazz = target.getClass();

        for (Field valueField : value.getClass().getDeclaredFields()) {
            try {
                Field targetField = clazz.getDeclaredField(valueField.getName());

                valueField.setAccessible(true);
                targetField.setAccessible(true);
                targetField.set(target, valueField.get(value));
            } catch (NoSuchFieldException | IllegalAccessException e) {
            }
        }
    }

    /**
     * 내용 복사
     * 
     * @param clazz Class&lt;T&gt;
     * @param value BaseDomain
     * @return clazz type instance T
     */
    public static <T extends CmmVO> T copyValue(Class<T> clazz, CmmVO value) {
        if (value.getClass().equals(clazz))
            return null;

        T t = null;

        try {
            t = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
        }

        copyValue(t, value);

        return t;
    }
}
