/*
 * Copyright (C) 2015 Luke Melaia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.lm.seriesfreak.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import net.lm.seriesfreak.database.data.entries.EntryBase;
import net.lm.seriesfreak.database.data.entries.TableEntry;
import net.lm.seriesfreak.ui.language.node.LTextItem;

/**
 *
 * @author Luke Melaia
 */
public class Printer {

    private static final String CELL_OPENING = "<td>", CELL_CLOSING = "</td>";

    private static final String ROW_OPENING = "<tr>", ROW_CLOSING = "</tr>";

    private static final LTextItem ENTRY_LIST = new LTextItem("entry_list", null).register();

    private static final LTextItem NAME_COLUMN = new LTextItem("name_col", null).register();

    private static final LTextItem EPISODE_COLUMN = new LTextItem("episode_col", null).register();

    private static final LTextItem TYPE_COLUMN = new LTextItem("type_col", null).register();

    private static final LTextItem DATE_COLUMN = new LTextItem("date_col", null).register();

    private static final LTextItem RATING_COLUMN = new LTextItem("rating_col", null).register();

    private final Scanner template;

    private final File output;

    private final EntryBase[] entries;

    private final String name;

    private String html = "";

    public Printer(EntryBase[] entries, File output, String name) {
        this.entries = entries;
        this.output = output;
        this.name = name;

        template = new Scanner(getClass().getResourceAsStream("/templates/entries.template"));
    }

    public void print() throws IOException {
        read();
        setTitle();
        setColumns();
        setTableRows();
        write();
    }

    private void write() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write(html);
            writer.flush();
        }
    }

    private void read() {

        do {
            html += template.nextLine() + "\n";
        } while (template.hasNextLine());

        template.close();
    }

    private void setTitle() {
        html = html.replace("{@title}", name + "'s " + ENTRY_LIST.getText());
    }

    private void setColumns() {
        html = html.replace("{@col1}", NAME_COLUMN.getText());
        html = html.replace("{@col2}", EPISODE_COLUMN.getText());
        html = html.replace("{@col3}", TYPE_COLUMN.getText());
        html = html.replace("{@col4}", DATE_COLUMN.getText());
        html = html.replace("{@col5}", RATING_COLUMN.getText());
    }

    private void setTableRows() {
        String ret = "";

        for (EntryBase entry : entries) {
            TableEntry tableEntry = new TableEntry(entry);

            ret += "\n" + entryRow(tableEntry.getName(),
                    tableEntry.getProgress().getText(),
                    tableEntry.getType(),
                    tableEntry.getDate(),
                    tableEntry.getRating());
        }

        html = html.replace("{@entries}", ret);
    }

    private String entryRow(String name, String episodes, String type, String date, String rating) {
        String ret = "\t\t\t" + ROW_OPENING + "\n";

        String nameCell = CELL_OPENING + name + CELL_CLOSING;
        String episodeCell = CELL_OPENING + episodes + CELL_CLOSING;
        String typeCell = CELL_OPENING + type + CELL_CLOSING;
        String dateCell = CELL_OPENING + date + CELL_CLOSING;
        String ratingCell = CELL_OPENING + rating + CELL_CLOSING;

        ret += "\t\t\t\t" + nameCell + episodeCell + typeCell + dateCell + ratingCell;

        return ret + "\n\t\t\t" + ROW_CLOSING;
    }
}
