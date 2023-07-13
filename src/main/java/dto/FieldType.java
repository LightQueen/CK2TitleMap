package dto;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum FieldType {

    OBJECT(Types.OTHER, "object", Object.class),
    BOOLEAN(Types.BOOLEAN, "boolean", Boolean.class),
    DATETIME(Types.TIMESTAMP, "dateTime", LocalDateTime.class),
    TIMESTAMP(Types.TIMESTAMP, "timestamp", LocalDateTime.class),
    DECIMAL(Types.DECIMAL, "decimal", BigDecimal.class),
    ID(Types.BIGINT, "id", Long.class),
    FOREIGN_KEY(Types.BIGINT, "foreignKey", Long.class),
    HEADER_ID(Types.BIGINT, "headerId", Long.class),
    INTEGER(Types.INTEGER, "integer", Integer.class),
    SMALL_INTEGER(Types.SMALLINT, "smallInteger", Integer.class),
    BIG_INTEGER(Types.BIGINT, "bigInteger", Long.class),
    STRING(Types.VARCHAR, "string", String.class),
    CHOICE(Types.VARCHAR, "choice", String.class),
    MULTIPLE_CHOICE(Types.VARCHAR, "multipleChoice", String.class),
    FILE(Types.VARCHAR, "file", String.class),
    STRING_DATE(Types.VARCHAR, "stringDate", LocalDate.class),
    STRING_ENCRYPTED(Types.VARCHAR, "stringEncrypted", String.class),
    STRING_HASHED(Types.VARCHAR, "stringHashed", String.class),
    STRING_TIME(Types.VARCHAR, "stringTime", LocalTime.class),
    TEXT(Types.VARCHAR, "text", String.class),
    BINARY(Types.BINARY, "binary", InputStream.class),
    VERSION(Types.INTEGER, "version", Integer.class);

    private final int sqlType;
    private final String name;
    private final Class typeClass;

    FieldType(int sqlType, String name, Class typeClass) {
        this.name = name;
        this.sqlType = sqlType;
        this.typeClass = typeClass;
    }

    public int getSqlType() {
        return sqlType;
    }

    public String getName() {
        return name;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

}
