package cn.swu.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MapTypeHandler extends BaseTypeHandler<Map<String, Object>> {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType) throws SQLException {
    PGobject jsonObject = new PGobject();
    jsonObject.setType("jsonb");
    try {
      jsonObject.setValue(objectMapper.writeValueAsString(parameter));
    } catch (JsonProcessingException e) {
      throw new SQLException("Error converting JsonNode to JSON string", e);
    }
    ps.setObject(i, jsonObject);
  }

  @Override
  public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return parseJson(rs.getString(columnName));
  }

  @Override
  public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return parseJson(rs.getString(columnIndex));
  }

  @Override
  public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return parseJson(cs.getString(columnIndex));
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> parseJson(String json) throws SQLException {
    if (json == null || json.isEmpty()) {
      return null;
    }
    try {
      return objectMapper.readValue(json, Map.class);
    } catch (JsonProcessingException e) {
      throw new SQLException("Error parsing JSON string", e);
    }
  }
}
