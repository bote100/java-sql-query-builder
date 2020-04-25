package com.github.danfickle.javasqlquerybuilder.test;

import com.github.danfickle.javasqlquerybuilder.QbFactory;
import com.github.danfickle.javasqlquerybuilder.QbInsert;
import com.github.danfickle.javasqlquerybuilder.QbSelect;
import com.github.danfickle.javasqlquerybuilder.QbWhere;
import com.github.danfickle.javasqlquerybuilder.generic.QbFactoryImp;
import com.github.danfickle.javasqlquerybuilder.generic.QbSelectImp;

import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

public class Sample {
    /**
     * Sample of using java-sql-query-builder with various queries.
     * IMPORTANT:
     * For clarity exception handling and cleanup is left out.
     * You should make sure all sql objects are closed.
     */
    static void sample() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lol?useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin", "root", "");

        QbFactory f = new QbFactoryImp(conn);

        // Update first_name, last_name and age for a given id...
        QbInsert up = f.newInsertQuery();
        up
                .intelliSet("first_name", "VORNAME")
                .intelliSet("last_name", "NACHNAME")
                .intelliSet("id", ThreadLocalRandom.current().nextInt(100))
                .intelliSet("age", ThreadLocalRandom.current().nextInt(100))
                .inTable("myTable")
                .exec();

        // Simple select query, retrieves id, age and last_name for records which
        // don't have the given id...

        PreparedStatement stmt;

        QbSelect sel = f.newSelectQuery();
        sel.select("id", "age","last_name")
                .from("myTable")
                .groupBy("age")
                .where()
                .where("age", QbWhere.QbWhereOperator.GREATER_THAN_EQUALS, 20);

        sel.fetch().forEach(doc -> System.out.println(doc.getString("id") + " | " + doc.getInt("age") + "| " +  doc.getString("last_name")));


//        sel.select(f.newAvg(f.newStdField("age"), "avg_age"))
//                .from("myTable")
//                .groupBy(f.newStdField("first_name"))
//                .having()
//                .where("avg_age", QbWhere.QbWhereOperator.GREATER_THAN, ":avg_age");

    }
}
