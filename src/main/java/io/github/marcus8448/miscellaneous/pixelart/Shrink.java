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

package io.github.marcus8448.miscellaneous.pixelart;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Locale;

public class Shrink {
    public static void shrink(String[] strings) {
        try {
            if (strings.length < 3) throw new RuntimeException("Not enough arguments!");
            try (OutputStream outputStream = new FileOutputStream(strings[1])) {
                int scale = Integer.parseInt(strings[2]);
                String[] split = strings[1].toLowerCase(Locale.ROOT).split("\\.");
                BufferedImage image = ImageIO.read(new FileImageInputStream(new File(strings[0])));
                int type = image.getType();
                if (strings.length > 3) type = Integer.parseInt(strings[3]);
                BufferedImage output = new BufferedImage(image.getWidth() / scale, image.getHeight() / scale, type);
                for (int x = 0; x < image.getWidth(); x += scale) {
                    for (int y = 0; y < image.getHeight(); y += scale) {
                        int color = image.getRGB(x, y);
                        output.setRGB(x / scale, y / scale, color);
                    }
                }
                if (!ImageIO.write(output, split[split.length - 1], outputStream)) {
                    throw new RuntimeException("Failed to write image! Format: '" + split[split.length - 1] + "' (" + type + ")");
                };
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
