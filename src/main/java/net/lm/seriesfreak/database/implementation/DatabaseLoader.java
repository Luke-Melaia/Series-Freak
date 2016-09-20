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
package net.lm.seriesfreak.database.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipException;

/**
 *
 * @author Luke Melaia
 */
class DatabaseLoader {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private JsonObject data = new JsonObject();

    private JsonParser parser = new JsonParser();

    private boolean compress = true;

    private File currentFile;

    private final int fileVersion;//NOT USED YET.

    public DatabaseLoader(int fileVersion) {
        this.fileVersion = fileVersion;
    }

    public DatabaseLoader(int fileVersion, boolean compress) {
        this.fileVersion = fileVersion;
        this.compress = compress;
        if (!compress) {
            log.warn("Compression turned off");
        }
    }

    public void save(File file) throws InaccessibleFileException, IOException {
        this.currentFile = file;
        exceptionSave();
    }
    
    public void save() throws InaccessibleFileException, IOException {
        if (currentFile == null) {
            log.warn("Attempting to save with no file open");
            throw new IllegalStateException("Cannot save - no file open");
        }

        exceptionSave();
        log.info("No error throwen during saving");
    }

    public boolean checkVersion() {
        if (!data.has("version")) {
            log.warn("Attempting to check version with no file open");
            throw new IllegalStateException("Cannot check version - no file open");
        }

        int version = data.get("version").getAsInt();
        log.info("Database file version: " + version);

        if (version > fileVersion) {
            return false;
        }

        return true;
    }
    
    public void open(File file) throws CorruptFileException, InaccessibleFileException, IOException {
        this.currentFile = file;
        exceptionOpen();
    }

    private void exceptionOpen() throws CorruptFileException, InaccessibleFileException, IOException {
        try {
            openFrom();
        } catch (JsonIOException | IllegalStateException | EOFException | ZipException | JsonSyntaxException ex) {
            log.error("Save file corrupt", ex);
            throw new CorruptFileException(ex);
        } catch (FileNotFoundException ex) {
            log.error("Save file inaccessable", ex);
            throw new InaccessibleFileException(ex);
        }
    }

    private void exceptionSave() throws InaccessibleFileException, IOException {
        try {
            saveTo();
        } catch (FileNotFoundException ex) {
            throw new InaccessibleFileException(ex);
        }
    }

    private void saveTo() throws IOException {
        if (!currentFile.exists()) {
            log.info("The file: " + currentFile + "doesn't exist for saving");
        }

        try (Writer writer = (compress)
                ? new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(currentFile)), Charset.forName("UTF-8"))
                : new OutputStreamWriter(new FileOutputStream(currentFile), Charset.forName("UTF-8"))) {
            writer.write(gson.toJson(data));
            writer.flush();
            writer.close();
        }
    }

    private void openFrom() throws IOException {
        if (!currentFile.exists()) {
            log.error("The file: " + currentFile + " doesn't exist. Can't save");
        }

        try (Reader reader = (compress)
                ? new InputStreamReader(new GZIPInputStream(new FileInputStream(currentFile)), Charset.forName("UTF-8"))
                : new InputStreamReader(new FileInputStream(currentFile), Charset.forName("UTF-8"))) {
            data = parser.parse(reader).getAsJsonObject();
            reader.close();
        }
    }

    public JsonObject getData() {
        return this.data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public File getCurrentFile() {
        return currentFile;
    }
}
