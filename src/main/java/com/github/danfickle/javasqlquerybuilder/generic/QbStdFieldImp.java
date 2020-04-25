package com.github.danfickle.javasqlquerybuilder.generic;

import com.github.danfickle.javasqlquerybuilder.QbField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Immutable class to implement a standard un-qualified field.
 *
 * @author DanFickle
 */
@Getter
@RequiredArgsConstructor
class QbStdFieldImp implements QbField {
    private final String m_fieldName;

    @Override
    public String toString() {
        return '`' + m_fieldName + '`';
    }
}