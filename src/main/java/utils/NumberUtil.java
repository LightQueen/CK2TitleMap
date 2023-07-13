package utils;

import com.google.common.base.Strings;

import java.math.BigDecimal;

public class NumberUtil {

    public static BigDecimal parseBigDecimal(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException(StringUtil.join(
                    "Parse [", value, "] BigDecimal failed"
            ), e);
        }
    }


}
