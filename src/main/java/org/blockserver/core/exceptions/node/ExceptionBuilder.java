/*
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.core.exceptions.node;

public class ExceptionBuilder {
    private StringBuilder builder = new StringBuilder("\t");

    public ExceptionBuilder(String name, ExceptionNode... nodes) {
        builder.append(name);
        for (ExceptionNode node : nodes)
            appendNode(node);
    }

    public ExceptionBuilder appendNode(ExceptionNode node) {
        if (node == null)
            return this;
        builder.append("\n\t");
        builder.append(node.getName());
        builder.append(": '");
        builder.append(node.getValue());
        builder.append('\'');
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
