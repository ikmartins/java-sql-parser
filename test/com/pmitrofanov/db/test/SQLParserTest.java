/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmitrofanov.db.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.pmitrofanov.db.sql.*;
import java.io.*;

/**
 *
 * @author pavel
 */
public class SQLParserTest {
    private SQLParser parser;
    private BufferedWriter writer;

    public SQLParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws IOException {
        PipedReader reader = new PipedReader();
        writer = new BufferedWriter(new PipedWriter(reader));
        try {
            parser.ReInit(reader);
        } catch (NullPointerException e) {
            parser = new SQLParser(reader);
        }
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    public void passString(String sql) {
        try {
            writer.write(sql);
            writer.close();
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }
        try {
            parser.SqlScript();
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }

    public void failString(String sql) {
        boolean caught = false;
        try {
            passString(sql);
        } catch (AssertionError e) {
            caught = true;
        }
        if (!caught) {
            fail();
        }
    }

    @Test
    public void testWithoutFrom() {
        passString("select 1;");
    }

    @Test
    public void testNumericLiteral() {
        passString("select 1 from t1;");
    }

    @Test
    public void testStringLiteral() {
        passString("select '1' from t1;");
    }

    @Test
    public void testSimpleAlias() {
        passString("select 1 x from t1;");
    }

    @Test
    public void testUnqualifiedCol() {
        passString("select x from t1;");
    }

    @Test
    public void testQualifiedCol() {
        passString("select t1.x from t1;");
    }

    @Test
    public void testInnerJoin() {
        passString("select x, y from t1 inner join t2 on col1=col2;");
    }

    @Test
    public void testLeftJoin() {
        passString("select x, y from t1 left join t2 on col1=col2;");
    }

    @Test
    public void testOuterJoin() {
        passString("select x, y from t1 left outer join t2 on col1=col2;");
    }

    @Test
    public void testCrossJoin() {
        passString("select x, y from t1 cross join t2;");
    }

    @Test
    public void testUnqualifiedEquijoin() {
        passString("select x, y from t1, t2 where col1=col2;");
    }

    @Test
    public void testCorrelatedSelectSubquery() {
        passString("select (select x from t2 where i2=i1), y from t1;");
    }

    @Test
    public void testComplex() {
        passString("select t1.x, t2.y, 0 as zero from table1 t1 inner join table2 t2 on t2.j=y2.j;");
    }

    @Test
    public void testStar() {
        passString("select * from t1;");
    }

    @Test
    public void testQualifiedStar() {
        passString("select t1.* from t1;");
    }

    @Test
    public void testAliasQualifiedStar() {
        passString("select t.* from t1 t;");
    }

    @Test
    public void testCountStar() {
        passString("select count(*) from t1;");
    }

    @Test
    public void testCountLiteral() {
        passString("select count(10) from t1;");
    }

    @Test
    public void testCountCol() {
        passString("select count(x) from t1;");
    }

    @Test
    public void testCountExpr() {
        passString("select count(x+2) from t1;");
    }

    @Test
    public void testGroupBy() {
        passString("select y,count(x+2) from t1 group by y;");
    }

    @Test
    public void testGroupByList() {
        passString("select x,y,count(z+2) from t1 group by x,y;");
    }

    @Test
    public void testFailGroupByAlias() {
        failString("select x,y,count(z+2) from t1 group by x,y y1;");
    }

    @Test
    public void testOrderBy() {
        passString("select x,y from t1 order by y;");
    }

    @Test
    public void testOrderByAsc() {
        passString("select x,y from t1 order by y asc;");
    }

    @Test
    public void testOrderByList() {
        passString("select x,y from t1 order by x desc, y asc, z, 3;");
    }

    @Test
    public void testLimit() {
        passString("select id from t1 limit 10;");
    }

    @Test
    public void testLimitOffset() {
        passString("select id from t1 limit 5,10;");
    }

    @Test
    public void testInlineView() {
        passString("select x,y from (select x,y from t1);");
    }

    @Test
    public void testInlineViewAlias() {
        passString("select x,y from (select x,y from t1) a;");
    }

    @Test
    public void testInlineViewEquijoin() {
        passString("select x,y from (select x,y from t1), t2 where c1=c2;");
    }
}