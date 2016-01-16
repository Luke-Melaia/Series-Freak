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
package net.lm.seriesfreak.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author Luke Melaia
 */
public class FileUtils {

    public static String[] getFileTreeNames(File directory, int depth) throws IOException {
        final List<File> files = new ArrayList<>();

        Path start = Paths.get(directory.getAbsolutePath());
        Files.walkFileTree(start, EnumSet.of(FileVisitOption.FOLLOW_LINKS), depth, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                boolean directory = attrs.isDirectory();
                boolean hidden = Files.isHidden(file);

                if (!directory && !hidden) {
                    files.add(file.toFile());
                }

                return FileVisitResult.CONTINUE;
            }
        });

        String[] fileNames = new String[files.size()];

        for (int i = 0; i < files.size(); i++) {
            fileNames[i] = files.get(i).getAbsolutePath();
        }

        Arrays.sort(fileNames, Comparators.ALPHANUM_COMPARATOR);
        return fileNames;
    }

    public static File[] getFileTree(File directory, int depth) throws IOException {
        String[] fileNames = getFileTreeNames(directory, depth);

        File[] files = new File[fileNames.length];

        for (int i = 0; i < fileNames.length; i++) {
            files[i] = new File(fileNames[i]);
        }

        return files;
    }
}
