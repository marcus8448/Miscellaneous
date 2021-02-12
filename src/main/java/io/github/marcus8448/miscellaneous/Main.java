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

package io.github.marcus8448.miscellaneous;

import java.util.*;

import static io.github.marcus8448.miscellaneous.Constants.*;

public class Main {

    public static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println(COPYRIGHT);

        while (true) {
            System.out.print("Please enter a run mode: ");
            String s = SCANNER.nextLine();
            if (s.toLowerCase(Locale.ROOT).equals("exit")) break;
            RunMode mode = tryParse(s);
            if (mode != null) {
                System.out.println("Run mode '" + mode.toString() + "' selected.");
                System.out.println("Please enter arguments: ");
                List<String> list = new LinkedList<>();
                int i = 0;
                while (true) {
                    System.out.print(++i + ": ");
                    s = SCANNER.nextLine();
                    if (s.toLowerCase(Locale.ROOT).equals("done")) break;
                    list.add(s);
                }
                try {
                    mode.run(list.toArray(new String[0]));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Failed to run '" + mode.toString() + "! Reason: " + e.getMessage());
                }
                System.out.println("Done!");
            } else {
                System.err.println("Invalid mode!");
            }
        }

        SCANNER.close();
    }

    private static RunMode tryParse(String s) {
        return RunMode.valueOfNullable(s);
    }
}
