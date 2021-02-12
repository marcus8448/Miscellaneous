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

package io.github.marcus8448.miscellaneous.minecraft;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class FabricProjectGenerator {
    public static void generate(String[] strings) {
        if (strings.length < 3) throw new RuntimeException("Not enough arguments!");
        final String modid = strings[0];
        final String modName = strings[1];
        final String description = strings[2];
        if (!Character.isUpperCase(modName.charAt(0))) throw new RuntimeException();
        try {
            File file = new File("fabric-starter/");
            delete(file);
            System.out.println("Cloning...");
            Process process = Runtime.getRuntime().exec("git clone https://github.com/marcus8448/fabric-starter.git");
            process.onExit().get();
            System.out.println("Cloned.");
            delete(new File("fabric-starter/.git/"));

            assert file.isDirectory();
            Queue<File> queue = new LinkedList<>();
            queue.add(file);
            while (!queue.isEmpty()) {
                File file1 = queue.poll();
                File tmp;
                if (file1.isDirectory()) {
                    if (file1.getName().contains("modid")) {
                        tmp = new File(file1.getParent() + "/" + file1.getName().replace("modid", modid));
                        file1.renameTo(tmp);
                        file1 = tmp;
                    }
                    if (file1.listFiles() != null) {
                        queue.addAll(Arrays.asList(Objects.requireNonNull(file1.listFiles())));
                    }
                } else {
                    if (file1.getName().contains("modid")) {
                        tmp = new File(file1.getParent() + "/" + file1.getName().replace("modid", modid));
                        file1.renameTo(tmp);
                        file1 = tmp;
                    } else if (file1.getName().contains("ModName")) {
                        tmp = new File(file1.getParent() + "/" + file1.getName().replace("ModName", modName).replace(" ", ""));
                        file1.renameTo(tmp);
                        file1 = tmp;
                    }
                    if (file1.getName().endsWith(".java")
                        || file1.getName().endsWith(".json")
                        || file1.getName().endsWith(".gradle")
                        || file1.getName().endsWith(".properties")) {
                        List<String> lines = Files.readAllLines(file1.toPath());
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
                            for (String s : lines) {
                                writer.write(s.replace("modid", modid).replace("Mod Desc", description).replace("Mod Name", modName).replace("ModName", modName.replace(" ", "")));
                                writer.write('\n');
                            }
                            writer.flush();
                        }
                    }
                }
            }
            file.renameTo(new File(modid + '/'));
        } catch (IOException | InterruptedException | ExecutionException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void delete(File file) {
        if (file.isDirectory()) {
            if (file.listFiles() != null) {
                for (File file1 : file.listFiles()) {
                    delete(file1);
                }
            }
        }
        file.delete();
    }
}
