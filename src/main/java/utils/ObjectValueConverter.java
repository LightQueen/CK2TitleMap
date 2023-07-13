package utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.HashMap;
import java.util.Map;

public abstract class ObjectValueConverter {

    static final int DATE_TIME_LEN = "2021-05-17T10:30:00".length();
    static final int TIMESTAMP_LEN = "2021-05-17T10:30:00.123".length();

    static final Map<Class<?>, ObjectValueConverter> converterMap = new HashMap<>();

    static {
        converterMap.put(Object.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                return input;
            }
        });
        converterMap.put(Boolean.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                if (input instanceof String) {
                    String value = (String) input;
                    if (value.isEmpty()) {
                        return null;
                    }
                    Boolean object = BooleanUtil.toBooleanObject(value);
                    if (object == null) {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Can not parse [", value, "] to boolean"
                        ));
                    }
                    return object;
                } else if (input instanceof Boolean) {
                    return input;
                } else if (input instanceof Integer) {
                    //将int类型转为boolen类型,0为false,非零为true
                    return (Integer) input != 0;
                } else {
                    if (input == null) {
                        return input;
                    } else {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Value [", String.valueOf(input), "] type [",
                                String.valueOf(input.getClass()), "] can not cast to Boolean"
                        ));
                    }
                }
            }
        });
        converterMap.put(Byte.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                if (input instanceof String) {
                    String value = (String) input;
                    if (value.isEmpty()) {
                        return null;
                    }
                    return Byte.valueOf(value);
                } else if (input instanceof Byte) {
                    return input;
                } else {
                    if (input == null) {
                        return null;
                    } else {
                        return Byte.valueOf(String.valueOf(input));
                    }
                }
            }
        });
        converterMap.put(Short.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                if (input instanceof String) {
                    String value = (String) input;
                    if (value.isEmpty()) {
                        return null;
                    }
                    return Short.valueOf(value);
                } else if (input instanceof Integer) {
                    int value = (Integer) input;
                    short ret = (short) value;
                    if (ret != value) {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Value [", String.valueOf(input), "] type [",
                                String.valueOf(input.getClass()), "] can not cast to Short"
                        ));
                    }
                    return ret;
                } else if (input instanceof Short) {
                    return input;
                } else if (input instanceof Long) {
                    long value = (Long) input;
                    short ret = (short) value;
                    if (ret != value) {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Value [", String.valueOf(input), "] type [",
                                String.valueOf(input.getClass()), "] can not cast to Short"
                        ));
                    }
                    return ret;
                } else {
                    if (input == null) {
                        return input;
                    } else {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Value [", String.valueOf(input), "] type [",
                                String.valueOf(input.getClass()), "] can not cast to Short"
                        ));
                    }
                }
            }
        });
        converterMap.put(Integer.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                if (input instanceof String) {
                    String value = (String) input;
                    if (value.isEmpty()) {
                        return null;
                    }
                    return Integer.valueOf(value);
                } else if (input instanceof Integer) {
                    return input;
                } else if (input instanceof Long) {
                    long value = (Long) input;
                    short ret = (short) value;
                    if (ret != value) {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Value [", String.valueOf(input), "] type [",
                                String.valueOf(input.getClass()), "] can not cast to Integer"
                        ));
                    }
                    return ret;
                } else if (input instanceof Short) {
                    return ((Short) input).intValue();
                } else {
                    if (input == null) {
                        return input;
                    } else {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Value [", String.valueOf(input), "] type [",
                                String.valueOf(input.getClass()), "] can not cast to Integer"
                        ));
                    }
                }
            }
        });
        converterMap.put(Long.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                if (input instanceof String) {
                    String value = (String) input;
                    if (value.isEmpty()) {
                        return null;
                    }
                    return Long.valueOf(value);
                } else if (input instanceof BigInteger) {
                    return ((BigInteger) input).longValue();
                } else if (input instanceof Long) {
                    return input;
                } else if (input instanceof Integer) {
                    return ((Integer) input).longValue();
                } else if (input instanceof Short) {
                    return ((Short) input).longValue();
                } else {
                    if (input == null) {
                        return input;
                    } else {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Value [", String.valueOf(input), "] type [",
                                String.valueOf(input.getClass()), "] can not cast to Long"
                        ));
                    }
                }
            }
        });
        converterMap.put(Float.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                if (input instanceof String) {
                    String value = (String) input;
                    if (value.isEmpty()) {
                        return null;
                    }
                    return Float.valueOf(value);
                } else {
                    return input;
                }
            }
        });
        converterMap.put(Double.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                if (input instanceof String) {
                    String value = (String) input;
                    if (value.isEmpty()) {
                        return null;
                    }
                    return Double.valueOf(value);
                } else {
                    return input;
                }
            }
        });
        converterMap.put(BigDecimal.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                Object newValue = input;
                if (!(input instanceof BigDecimal)) {
                    if (input instanceof String) {
                        newValue = NumberUtil.parseBigDecimal((String) input);
                    } else if (input instanceof BigInteger) {
                        newValue = new BigDecimal((BigInteger) input);
                    } else if (input instanceof Integer) {
                        newValue = new BigDecimal((Integer) input);
                    } else if (input instanceof Long) {
                        newValue = new BigDecimal((Long) input);
                    } else if (input instanceof Short) {
                        newValue = new BigDecimal((Short) input);
                    } else if (input instanceof Double) {
                        newValue = BigDecimal.valueOf((Double) input);
                    } else if (input instanceof Float) {
                        newValue = BigDecimal.valueOf((Float) input);
                    } else {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Can not convert [", String.valueOf(input), "] to [", BigDecimal.class.getName(), "]"
                        ));
                    }
                }
                return newValue;
            }
        });
        converterMap.put(String.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                if (input == null) {
                    return null;
                }
                return String.valueOf(input);
            }
        });
        converterMap.put(LocalDateTime.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                if (input instanceof String) {
                    String value = (String) input;
                    if (value.isEmpty()) {
                        return null;
                    }
                    if (value.length() == TIMESTAMP_LEN) {
                        return ObjectValueConverter.parseTimestamp(value);
                    } else if (value.length() == DATE_TIME_LEN) {
                        return ObjectValueConverter.parseDateTime(value);
                    } else {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Illegal LocalDateTime input [", value, "]"
                        ));
                    }
                } else {
                    return input;
                }
            }
        });
        converterMap.put(LocalDate.class, new ObjectValueConverter() {

            int getYear(String value) {
                return (value.charAt(0) - '0') * 1000
                        + (value.charAt(1) - '0') * 100
                        + (value.charAt(2) - '0') * 10
                        + (value.charAt(3) - '0') * 1;
            }

            int getMonth(String value) {
                return (value.charAt(5) - '0') * 10
                        + (value.charAt(6) - '0') * 1;
            }

            @Override
            public Object convert(Object input) {
                if (input instanceof String) {
                    String value = (String) input;
                    if (value.isEmpty()) {
                        return null;
                    }
                    if (value.length() == "2021-03-30".length()) {
                        int year = getYear(value);
                        int month = getMonth(value);
                        int day = (value.charAt(8) - '0') * 10
                                + (value.charAt(9) - '0') * 1;
                        return LocalDate.of(year, month, day);
                    } else if (value.length() == "2021-03".length()) {
                        int year = getYear(value);
                        int month = getMonth(value);
                        return LocalDate.of(year, month, 1);
                    } else if (value.length() == "2021".length()) {
                        int year = getYear(value);
                        return LocalDate.of(year, 1, 1);
                    } else {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Illegal LocalDate input [", value, "]"
                        ));
                    }
                } else {
                    return input;
                }
            }
        });
        converterMap.put(LocalTime.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                if (input instanceof String) {
                    String value = (String) input;
                    if (value.isEmpty()) {
                        return null;
                    }
                    if (value.length() != "15:30:00".length()) {
                        throw new IllegalArgumentException(StringUtil.join(
                                "Illegal LocalTime input [", value, "]"
                        ));
                    }
                    int hour = (value.charAt(0) - '0') * 10
                            + (value.charAt(1) - '0') * 1;
                    int minute = (value.charAt(3) - '0') * 10
                            + (value.charAt(4) - '0') * 1;
                    int second = (value.charAt(6) - '0') * 10
                            + (value.charAt(7) - '0') * 1;
                    return LocalTime.of(hour, minute, second);
                } else {
                    return input;
                }
            }
        });

        converterMap.put(OffsetDateTime.class, new ObjectValueConverter() {
            @Override
            public Object convert(Object input) {
                if (input instanceof String) {
                    String value = (String) input;
                    if (value.isEmpty()) {
                        return null;
                    }
                    LocalDateTime localDateTime;
                    ZoneOffset zoneOffset;
                    int index = value.lastIndexOf('+');
                    if (index == -1) {
                        if (value.charAt(value.length() - 1) != 'Z') {
                            throw new IllegalArgumentException(StringUtil.join(
                                    "Illegal OffsetDateTime input [", value, "]"
                            ));
                        }
                        if (value.length() == DATE_TIME_LEN + 1) {
                            localDateTime = ObjectValueConverter.parseDateTime(value);
                        } else if (value.length() == TIMESTAMP_LEN + 1) {
                            localDateTime = ObjectValueConverter.parseTimestamp(value);
                        } else {
                            throw new IllegalArgumentException(StringUtil.join(
                                    "Illegal OffsetDateTime input [", value, "]"
                            ));
                        }
                        zoneOffset = ZoneOffset.UTC;
                    } else {
                        if (index == DATE_TIME_LEN) {
                            localDateTime = ObjectValueConverter.parseDateTime(value);
                        } else if (index == TIMESTAMP_LEN) {
                            localDateTime = ObjectValueConverter.parseTimestamp(value);
                        } else {
                            throw new IllegalArgumentException(StringUtil.join(
                                    "Illegal OffsetDateTime input [", value, "]"
                            ));
                        }
                        String zoneId = value.substring(index);
                        zoneOffset = ZoneOffset.of(zoneId);
                    }
                    return OffsetDateTime.of(localDateTime, zoneOffset);
                } else {
                    return input;
                }
            }
        });

        converterMap.put(boolean.class, converterMap.get(Boolean.class));
        converterMap.put(byte.class, converterMap.get(Byte.class));
        converterMap.put(short.class, converterMap.get(Short.class));
        converterMap.put(int.class, converterMap.get(Integer.class));
        converterMap.put(long.class, converterMap.get(Long.class));
        converterMap.put(float.class, converterMap.get(Float.class));
        converterMap.put(double.class, converterMap.get(Double.class));
    }

    private static LocalDateTime parseDateTime(String value) {
        int year = (value.charAt(0) - '0') * 1000
                + (value.charAt(1) - '0') * 100
                + (value.charAt(2) - '0') * 10
                + (value.charAt(3) - '0') * 1;
        int month = (value.charAt(5) - '0') * 10
                + (value.charAt(6) - '0') * 1;
        int day = (value.charAt(8) - '0') * 10
                + (value.charAt(9) - '0') * 1;
        int hour = (value.charAt(11) - '0') * 10
                + (value.charAt(12) - '0') * 1;
        int minute = (value.charAt(14) - '0') * 10
                + (value.charAt(15) - '0') * 1;
        int second = (value.charAt(17) - '0') * 10
                + (value.charAt(18) - '0') * 1;
        int nano = 0 * 1000000;
        return LocalDateTime.of(year, month, day, hour, minute, second, nano);
    }

    private static LocalDateTime parseTimestamp(String value) {
        int year = (value.charAt(0) - '0') * 1000
                + (value.charAt(1) - '0') * 100
                + (value.charAt(2) - '0') * 10
                + (value.charAt(3) - '0') * 1;
        int month = (value.charAt(5) - '0') * 10
                + (value.charAt(6) - '0') * 1;
        int day = (value.charAt(8) - '0') * 10
                + (value.charAt(9) - '0') * 1;
        int hour = (value.charAt(11) - '0') * 10
                + (value.charAt(12) - '0') * 1;
        int minute = (value.charAt(14) - '0') * 10
                + (value.charAt(15) - '0') * 1;
        int second = (value.charAt(17) - '0') * 10
                + (value.charAt(18) - '0') * 1;
        int millisecond = (value.charAt(20) - '0') * 100
                + (value.charAt(21) - '0') * 10
                + (value.charAt(22) - '0') * 1;
        int nano = millisecond * 1000000;
        return LocalDateTime.of(year, month, day, hour, minute, second, nano);
    }

    public abstract Object convert(Object input);

    public static ObjectValueConverter getConvert(Class<?> clazz) {
        if (clazz.isEnum()) {
            return new ObjectValueConverter() {
                @Override
                public Object convert(Object input) {
                    if (input == null) {
                        return null;
                    }
                    if (input instanceof String) {
                        return Enum.valueOf((Class<? extends Enum>) clazz, (String) input);
                    }
                    if (input.getClass() == clazz) {
                        return input;
                    }
                    throw new IllegalArgumentException(StringUtil.join(
                            "Illegal Enum(", clazz.getName(), ") input [", input.toString(), "]"
                    ));
                }
            };
        } else {
            return converterMap.get(clazz);
        }
    }

}
