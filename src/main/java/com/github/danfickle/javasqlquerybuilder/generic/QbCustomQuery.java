package com.github.danfickle.javasqlquerybuilder.generic;

import com.github.danfickle.javasqlquerybuilder.QbQuery;
import com.github.danfickle.javasqlquerybuilder.tool.Tuple;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Immutable class to implement a custom query.
 *
 * @author DanFickle
 */
@Data
class QbCustomQuery implements QbQuery {
    private final String m_sql;
    private final Connection connection;

    @Override
    public String getQueryString() {
        return m_sql;
    }

    @Override
    public int getPlaceholderIndex(String placeholderName) {
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
}