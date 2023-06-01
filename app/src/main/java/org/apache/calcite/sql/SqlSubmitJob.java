package org.apache.calcite.sql;

import java.util.List;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.util.ImmutableNullableList;

/**
 * Author yuqi
 * Time 10/5/19 16:33
 **/
public class SqlSubmitJob extends SqlCall {

  private final SqlNode job;

  public SqlSubmitJob(SqlParserPos pos,SqlNode jobNode) {
    super(pos);
    this.job=jobNode;
  }

  @Override
  public SqlOperator getOperator() {
    return new SqlSpecialOperator("submit job as",
        SqlKind.OTHER_FUNCTION);
  }

  @Override
  public void unparse(SqlWriter writer, int leftPrec, int rightPrec) {
    writer.keyword("submit job as");
  }


  @Override
  public List<SqlNode> getOperandList() {
    return ImmutableNullableList.of(job);
  }
}
