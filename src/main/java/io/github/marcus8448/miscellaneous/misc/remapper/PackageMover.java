/*
 * Copyright (C) 2021  marcus8448
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.marcus8448.miscellaneous.misc.remapper;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;

public class PackageMover {
    public static void move(String[] args) {
        if (args.length == 0) throw new RuntimeException("Not enough arguments!");
        File directory = new File(args[0]);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (!file.isDirectory()) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String s;
                    while (true) {
                        try {
                            s = "/" + reader.readLine().split("//")[1].split(" ")[1].split(";")[0].replace(".", "/") + "/";
                        } catch (IndexOutOfBoundsException ex) {
                            continue;
                        }
                        break;
                    }
                    reader.close();
                    reader = new BufferedReader(new FileReader(file));
                    File file1 = new File(directory.toString() + s + file.getName());
                    file1.mkdirs();
                    file1.delete();
                    System.out.println(file1.getAbsolutePath());
                    file1.createNewFile();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file1));
                    while (reader.ready()) {
                        writer.write(reader.readLine());
                        writer.write('\n');
                    }
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}