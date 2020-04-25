package com.github.danfickle.javasqlquerybuilder.generic;

import com.github.danfickle.javasqlquerybuilder.QbDelete;
import com.github.danfickle.javasqlquerybuilder.QbWhere;
import com.github.danfickle.javasqlquerybuilder.tool.Tuple;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
class QbDeleteImp implements QbDelete
{
	private String m_table;
	private boolean m_all;
	private QbWhere m_where;

	@Getter private final Connection connection;
	
	@Override
	public String getQueryString() 
	{
		if (m_table == null)
			throw new IllegalStateException("Must specify a table");

		if (m_all == false && m_where == null)
			throw new IllegalStateException("Must call all() to delete all records");
		
		StringBuilder builder = new StringBuilder("DELETE FROM ");
		builder.append(QbCommonImp.protectTableName(m_table));

		if (m_where != null)
			builder.append(m_where.toString());

		return builder.toString();
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		if (m_where != null)
			return m_where.getPlaceholderIndex(placeholderName);
		else
			throw new IllegalArgumentException("Placeholder doesn't exist");
	}

	@Override
	public int exec(List<Tuple<String, Object>> args) {
		try {
			final PreparedStatement stmt = this.connection.prepareStatement(getQueryString());

			args.forEach(item -> {
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
	public QbWhere where()
	{
		m_where = new QbWhereImp(false, 1);
		return m_where;
	}

	@Override
	public QbDelete all()
	{
		m_all = true;
		return this;
	}

	@Override
	public QbDelete from(String table) 
	{
		m_table = table;
		return this;
	}
}
