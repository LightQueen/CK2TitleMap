package dto;

import com.google.common.base.Strings;
import lombok.Data;
import utils.ObjectValueConverter;
import utils.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Field {
    private String name;
    private String value;
    private FieldType fieldType;
    public Object getValue(ResultSet rs,int index) throws SQLException {
        return rs.getObject(index);
    }
    protected Object prepareParameterForStatement(Object value) {
        FieldType fieldType = getFieldType();
        Class<?> typeClass = fieldType.getTypeClass();
        if (typeClass.isAssignableFrom(value.getClass())) {
            return value;
        }
        ObjectValueConverter convert = ObjectValueConverter.getConvert(typeClass);
        if (convert != null) {
            return convert.convert(value);
        }
        throw new IllegalArgumentException(StringUtil.join(
                "Field [", getName(), " expect a ",
                fieldType.getTypeClass().getName(), " value, but got ", value.getClass().getName()));
    }


    public Object setStatementParameter(
            PreparedStatement ps, Object value, int index) throws SQLException {
        if (value == null || (value instanceof String && Strings.isNullOrEmpty(((String) value)))) {
            ps.setNull(index, getFieldType().getSqlType());
            return null;
        } else {
            Object preparedValue = prepareParameterForStatement(value);
            if ( preparedValue == null) {
                throw new IllegalArgumentException(StringUtil.join(
                        "Field [", getName(), "] can not be null"
                ));
            }
            ps.setObject(index, preparedValue, getFieldType().getSqlType());
            return preparedValue;
        }
    }

}
