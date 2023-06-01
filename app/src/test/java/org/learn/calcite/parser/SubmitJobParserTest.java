package org.learn.calcite.parser;

import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.impl.SubmitJobSqlParserImpl;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.junit.jupiter.api.Test;

// https://blog.csdn.net/dafei1288/article/details/102735371
// https://github.com/yuqi1129/calcite-test/blob/master/src/main/codegen/templates/Parser.jj
// https://github.com/quxiucheng/apache-calcite-tutorial/tree/a7d63273d0c7585fc65ad250c99a67a201bcb8b5/calcite-tutorial-2-parser/parser-3-calcite-tutorial
// https://xie.infoq.cn/article/7601e809de20aea301611194b
// https://www.jianshu.com/p/7a7725f6a62c
public class SubmitJobParserTest {

  @Test()
  public void testSqlParse() {
    String sql = "select * from test";
    final FrameworkConfig config = Frameworks.newConfigBuilder()
        .parserConfig(SqlParser.configBuilder()
            .setCaseSensitive(false)
            .setQuoting(Quoting.BACK_TICK)
            .setQuotedCasing(Casing.TO_UPPER)
            .setUnquotedCasing(Casing.TO_UPPER)
            .setConformance(SqlConformanceEnum.ORACLE_12)
            .build())
        .build();
    SqlParser parser = SqlParser.create(sql, config.getParserConfig());

    try {
      SqlNode sqlNode = parser.parseStmt();
      System.out.println(sqlNode.toString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testSqlSubmitJob() {
    String sql = "SUBMIT JOB AS 'select * from test'";
    // sql = "submit job as 'select * from test'";

    final FrameworkConfig config = Frameworks.newConfigBuilder()
        .parserConfig(SqlParser.configBuilder()
            .setParserFactory(SubmitJobSqlParserImpl.FACTORY)
            .setCaseSensitive(false)
            .setQuoting(Quoting.BACK_TICK)
            .setQuotedCasing(Casing.TO_UPPER)
            .setUnquotedCasing(Casing.TO_UPPER)
            .setConformance(SqlConformanceEnum.MYSQL_5)
            .build())
        .build();
    SqlParser parser = SqlParser.create(sql, config.getParserConfig());

    try {
      SqlNode sqlNode = parser.parseStmt();
      System.out.println(sqlNode.toString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

//   @Test
//   public void testSubmitWrongOK() {
//     String sql = "select fun1('1314') ";
//
//     final FrameworkConfig config = Frameworks.newConfigBuilder()
//         .parserConfig(SqlParser.configBuilder()
//             .setParserFactory(SubmitJobSqlParserImpl.FACTORY)
//             .setCaseSensitive(false)
//             .setQuoting(Quoting.BACK_TICK)
//             .setQuotedCasing(Casing.TO_UPPER)
//             .setUnquotedCasing(Casing.TO_UPPER)
//             .setConformance(SqlConformanceEnum.ORACLE_12)
//             .build()).operatorTable(SqlStdOperatorTable.instance())
//         .build();
//     SqlParser parser = SqlParser.create(sql, config.getParserConfig());
//
//     try {
//       SqlNode sqlNode = parser.parseStmt();
//       System.out.println(sqlNode.toString());
//     } catch (Exception e) {
//       throw new RuntimeException(e);
//     }
//   }
// }
