package cn.itcast.movie_back_end.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

public class TimestampToLongTypeHandler extends BaseTypeHandler<Long> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Long aLong, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, new java.sql.Timestamp(aLong));
    }

    @Override
    public Long getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return timestamp != null ? timestamp.getTime() : null;
    }

    @Override
    public Long getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        return timestamp != null ? timestamp.getTime() : null;
    }

    @Override
    public Long getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        java.sql.Timestamp timestamp = cs.getTimestamp(columnIndex);
        return timestamp != null ? timestamp.getTime() : null;
    }
}
