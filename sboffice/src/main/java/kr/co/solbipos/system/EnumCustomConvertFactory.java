package kr.co.solbipos.system;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import kr.co.solbipos.enums.CodeEnum;


/**
 * Custom Converter - Request Parameter To Enum(extends Enum & CodeEnum)
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EnumCustomConvertFactory<X extends Enum<X> & CodeEnum>
        implements ConverterFactory<String, X> {

    @Override
    public <T extends X> Converter<String, T> getConverter(Class<T> targetType) {

        return new EnumCustomConvert(targetType);
    }

    private class EnumCustomConvert<T extends X> implements Converter<String, T> {

        private final Class<T> enumType;

        public EnumCustomConvert(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {

            if (source.isEmpty() || !enumType.isEnum()) {
                return null;
            }

            for (T value : enumType.getEnumConstants()) {
                if (value.getCode().equals(source))
                    return value;
            }

            return null;
        }
    }
}
