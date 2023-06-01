package org.learn.calcite;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.pretty.SqlPrettyWriter;

//https://mp.weixin.qq.com/s/Rab7TRpGO6BtyCI9KMDFRQ

public class SqlParserTest {
    public static void main(String args[]) {
        try {
            SqlPrettyWriter prettyWriter = new SqlPrettyWriter(MysqlSqlDialect.DEFAULT);
            SqlParser.Config config = SqlParser.configBuilder()
                    .setLex(Lex.MYSQL) //使用mysql 语法
                    .build();
            //String sql="select id,name,age FROM stu where age<20";
            String sql = "select id,name,age FROM stu where age<20";
            //SqlParser 语法解析器
            SqlParser sqlParser = SqlParser.create(sql, config);
            SqlNode sqlNode = null;
            sqlNode = sqlParser.parseStmt();
            String format = prettyWriter.format(sqlNode);
            // 还原某个方言的SQL
            System.out.println(format);
            System.out.println(sqlNode.toSqlString(OracleSqlDialect.DEFAULT));
            String exp = "id = 1 and name='jack'";
            SqlParser expParser = SqlParser.create(exp, config);
            sqlNode = expParser.parseExpression();
            System.out.println(sqlNode.toSqlString(OracleSqlDialect.DEFAULT));

            if (SqlKind.SELECT.equals(sqlNode.getKind())) {

                SqlSelect sqlSelect = (SqlSelect) sqlNode;
                SqlNode from = sqlSelect.getFrom();
                SqlNode where = sqlSelect.getWhere();
                SqlNodeList selectList = sqlSelect.getSelectList();
                //标识符
                if (SqlKind.IDENTIFIER.equals(from.getKind())) {
                    System.out.println(from.toString());
                }

                if (SqlKind.LESS_THAN.equals(where.getKind())) {
                    SqlBasicCall sqlBasicCall = (SqlBasicCall) where;
                    for (SqlNode sqlNode1 : sqlBasicCall.getOperandList()) {
                        if (SqlKind.LITERAL.equals(sqlNode1.getKind())) {
                            System.out.println(sqlNode1.toString());
                        }
                    }
                }

                selectList.getList().forEach(x -> {
                    if (SqlKind.IDENTIFIER.equals(x.getKind())) {
                        System.out.println(x.toString());
                    }
                });
            }
        } catch (SqlParseException e) {
            throw new RuntimeException("", e);
        }
    }
}
