package com.github.danfickle.javasqlquerybuilder.generic;

import com.github.danfickle.javasqlquerybuilder.QbField;
import com.github.danfickle.javasqlquerybuilder.QbInsert;
import com.github.danfickle.javasqlquerybuilder.tool.Tuple;
import com.google.common.collect.Lists;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * The default implementation of QbInsert.
 *
 * @author DanFickle
 */
@RequiredArgsConstructor
class QbInsertImp implements QbInsert {
    private String m_table;
    private Map<String, Integer> m_placeholders;
    private List<QbField> m_fields;

    @Getter
    private final Connection connection;
    private final List<Tuple<String, Object>> argList = Lists.newArrayList();

    @Override
    public String getQueryString() {
        if (m_fields == null || m_table == null || m_placeholders == null)
            throw new IllegalStateException("Table name or fields missing");

        StringBuilder builder = new StringBuilder("INSERT INTO ");
        builder.append(QbCommonImp.protectTableName(m_table));
        builder.append(" (");
        QbCommonImp.joinFieldNames(builder, m_fields);
        builder.append(") VALUES (");
        QbCommonImp.createPlaceholders(builder, m_fields.size());
        builder.append(')');

        return builder.toString();
    }

    @Override
    public int getPlaceholderIndex(String placeholderName) {
        if (m_placeholders == null)
            throw new IllegalArgumentException("Placeholder doesn't exist");

        Integer idx = m_placeholders.get(placeholderName);

        if (idx == null)
            throw new IllegalArgumentException("Placeholder doesn't exist");
        else
            return idx;
    }

	@Override
	public int exec(List<Tuple<String, Object>> args) {
        this.argList.addAll(args);
		try {
			final PreparedStatement stmt = this.connection.prepareStatement(getQueryString());

			argList.forEach(item -> {
				try {
					stmt.setObject(getPlaceholderIndex(item.getA()), item.getB());
				} catch (SQLException throwables) { throwables.printStackTrace(); }
			});

			return stmt.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return -1;
	}

	@Override
    public QbInsert set(QbField field, String placeholder) {
        if (m_fields == null)
            m_fields = new ArrayList<>();
        if (m_placeholders == null)
            m_placeholders = new HashMap<>();

        if (m_placeholders.containsKey(placeholder))
            throw new IllegalArgumentException("Duplicate placeholder");

        m_fields.add(field);
        m_placeholders.put(placeholder, m_placeholders.size() + 1);
        return this;
    }

    @Override
    public QbInsert intelliSet(String name, Object val) {
        argList.add(new Tuple<>(":" + name, val));
        return set(name, ":" + name);
    }

    @Override
    public void exec() {
        exec(Collections.emptyList());
    }

    @Override
    public QbInsert set(String name, String val) {
        return set(new QbStdFieldImp(name), val);
    }

    @Override
    public QbInsert inTable(String table) {
        m_table = table;
        return this;
    }
}
