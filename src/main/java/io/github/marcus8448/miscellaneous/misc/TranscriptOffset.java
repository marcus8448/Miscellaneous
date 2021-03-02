package io.github.marcus8448.miscellaneous.misc;

import java.io.*;
import java.util.regex.Pattern;

public class TranscriptOffset {
    private static final Pattern TIME_PATTERN = Pattern.compile("..:..:..(\\.....)?(\\....)?(\\...)?(\\..)? --> ..:..:..(\\.....)?(\\....)?(\\...)?(\\..)?");

    public static void offset(String[] args) {
        System.out.println("I made this quickly, and did not account for edge cases like seconds going over 59");
        System.out.println("You'll still need manual editing after.");
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))) {
                int offset = Integer.parseInt(args[2]);
                String line = reader.readLine();
                writer.write(line);
                writer.write('\n');
                String[] timeSplit;
                if (!line.equals("WEBVTT")) throw new AssertionError();
                while (reader.ready()) {
                    line = reader.readLine();
                    if (TIME_PATTERN.matcher(line).find()) {
                        timeSplit = line.split(":");
                        String s = String.valueOf(Integer.parseInt(timeSplit[2].split("\\.")[0].split(" ")[0]) + 5);
                        if (s.length() == 1) s = '0' + s;
                        String s2 = String.valueOf(Integer.parseInt(timeSplit[4].split("\\.")[0]) + offset);
                        if (s2.length() == 1) s2 = '0' + s2;
                        String out = timeSplit[0] + ':' + timeSplit[1] + ':' + s + '.' + timeSplit[2].split("\\.")[1]  + ':' + timeSplit[3] + ':' + s2 + '.' + timeSplit[4].split("\\.")[1];
                        writer.write(out);
                    } else {
                        writer.write(line);
                    }
                    writer.write('\n');
                    writer.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
