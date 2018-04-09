package kr.co.solbipos.system;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import kr.co.solbipos.enums.CodeEnum;


/**
 * Custom Converter - Request Parameter To Enum(extends Enum & CodeEnum)
 * http://www.baeldung.com/spring-type-conversions
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EnumCustomConvertFactory<X extends CodeEnum>
    implements ConverterFactory<String, X> {

    private static class EnumCustomConvert<T extends CodeEnum>
        implements Converter<String, T> {

        private final Class<T> enumType;

        public EnumCustomConvert(Class<T> enumType) {
            this.enumType = enumType;
        }

        public T convert(String source) {
            if (source.isEmpty() || !enumType.isEnum()) {
                return null;
            }
            for (T value : enumType.getEnumConstants()) {
                if (value.getCode().equals(source.trim()))
                    return value;
            }

            return null;
        }
    }

    @Override
    public <T extends X> Converter<String, T> getConverter(Class<T> targetType) {
        return new EnumCustomConvert(targetType);
    }

}
