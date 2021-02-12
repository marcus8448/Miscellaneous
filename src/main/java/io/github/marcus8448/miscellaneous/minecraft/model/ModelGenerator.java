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

package io.github.marcus8448.miscellaneous.minecraft.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModelGenerator {

    public static void run(String[] strings) {
        try {
            if (strings.length < 3) throw new RuntimeException("Not enough arguments!");
            final String modid = strings[0];
            final String outputDir = strings[1];

            new File(outputDir + "/assets/" + modid + "/blockstates").mkdirs();
            new File(outputDir + "/assets/" + modid + "/textures").mkdirs();
            new File(outputDir + "/assets/" + modid + "/models/block").mkdirs();
            new File(outputDir + "/assets/" + modid + "/models/item").mkdirs();


            String[] identifiers = new String[strings.length - 2];
            System.arraycopy(strings, 2, identifiers, 0, strings.length - 2);

            for (String id : identifiers) {
                Type type = Type.DEFAULT;
                id = modid + ":" + id;
                if (id.toLowerCase().contains("slab")) {
                    type = Type.SLAB;
                    System.out.println("Setting type to 'Slab'");
                } else if (id.toLowerCase().contains("stair")) {
                    type = Type.STAIR;
                    System.out.println("Setting type to 'Stair'");
                } else if (id.toLowerCase().contains("wall")) {
                    type = Type.WALL;
                    System.out.println("Setting type to 'Wall'");
                }
                new File(outputDir + "/assets/" + modid + "/blockstates/" + id + ".json").createNewFile();
                new File(outputDir + "/assets/" + modid + "/models/block/" + id + ".json").createNewFile();
                new File(outputDir + "/assets/" + modid + "/models/item/" + id + ".json").createNewFile();
                String modelXID = modid + ":block/" + id;

                try (FileWriter writer = new FileWriter(outputDir + "/assets/" + modid + "/blockstates/" + id.split(":")[1].toLowerCase() + ".json", false)) {
                    if (type == Type.DEFAULT) {
                        writer.write("{\n" +
                                     "  \"variants\": {\n" +
                                     "    \"\": {\n" +
                                     "      \"model\": \"" + modelXID + "\"\n" +
                                     "    }\n" +
                                     "  }\n" +
                                     "}");
                    } else if (type == Type.SLAB) {
                        writer.write("{\n" +
                                     "    \"variants\": {\n" +
                                     "        \"type=bottom\": { \"model\": \"" + modelXID + "\" },\n" +
                                     "        \"type=top\": { \"model\": \"" + modelXID + "_top" + "\" },\n" +
                                     "        \"type=double\": { \"model\": \"" + modid + ":block/" + id.replace("_slab_", "").replace("_slab", "").replace("slab_", "") + "\" }\n" +
                                     "    }\n" +
                                     "}\n");
                    } else if (type == Type.STAIR) {
                        writer.write("{\n" +
                                     "    \"variants\": {\n" +
                                     "        \"facing=east,half=bottom,shape=straight,waterlogged=true\":  { \"model\": \"" + modelXID + "\" },\n" +
                                     "        \"facing=west,half=bottom,shape=straight,waterlogged=true\":  { \"model\": \"" + modelXID + "\", \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=bottom,shape=straight,waterlogged=true\":  { \"model\": \"" + modelXID + "\", \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=bottom,shape=straight,waterlogged=true\":  { \"model\": \"" + modelXID + "\", \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=bottom,shape=outer_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\" },\n" +
                                     "        \"facing=west,half=bottom,shape=outer_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=bottom,shape=outer_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=bottom,shape=outer_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=bottom,shape=outer_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=bottom,shape=outer_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=bottom,shape=outer_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\" },\n" +
                                     "        \"facing=north,half=bottom,shape=outer_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=bottom,shape=inner_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\" },\n" +
                                     "        \"facing=west,half=bottom,shape=inner_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=bottom,shape=inner_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=bottom,shape=inner_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=bottom,shape=inner_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=bottom,shape=inner_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=bottom,shape=inner_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\" },\n" +
                                     "        \"facing=north,half=bottom,shape=inner_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=top,shape=straight,waterlogged=true\":  { \"model\": \"" + modelXID + "\", \"x\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=top,shape=straight,waterlogged=true\":  { \"model\": \"" + modelXID + "\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=top,shape=straight,waterlogged=true\":  { \"model\": \"" + modelXID + "\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=top,shape=straight,waterlogged=true\":  { \"model\": \"" + modelXID + "\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=top,shape=outer_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=top,shape=outer_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=top,shape=outer_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=top,shape=outer_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=top,shape=outer_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=top,shape=outer_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=top,shape=outer_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=top,shape=outer_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=top,shape=inner_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=top,shape=inner_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=top,shape=inner_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=top,shape=inner_right,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=top,shape=inner_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=top,shape=inner_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=top,shape=inner_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=top,shape=inner_left,waterlogged=true\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
                                     "\n" +
                                     "        \"facing=east,half=bottom,shape=straight,waterlogged=false\":  { \"model\": \"" + modelXID + "\" },\n" +
                                     "        \"facing=west,half=bottom,shape=straight,waterlogged=false\":  { \"model\": \"" + modelXID + "\", \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=bottom,shape=straight,waterlogged=false\":  { \"model\": \"" + modelXID + "\", \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=bottom,shape=straight,waterlogged=false\":  { \"model\": \"" + modelXID + "\", \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=bottom,shape=outer_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\" },\n" +
                                     "        \"facing=west,half=bottom,shape=outer_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=bottom,shape=outer_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=bottom,shape=outer_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=bottom,shape=outer_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=bottom,shape=outer_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=bottom,shape=outer_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\" },\n" +
                                     "        \"facing=north,half=bottom,shape=outer_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=bottom,shape=inner_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\" },\n" +
                                     "        \"facing=west,half=bottom,shape=inner_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=bottom,shape=inner_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=bottom,shape=inner_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=bottom,shape=inner_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=bottom,shape=inner_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=bottom,shape=inner_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\" },\n" +
                                     "        \"facing=north,half=bottom,shape=inner_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=top,shape=straight,waterlogged=false\":  { \"model\": \"" + modelXID + "\", \"x\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=top,shape=straight,waterlogged=false\":  { \"model\": \"" + modelXID + "\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=top,shape=straight,waterlogged=false\":  { \"model\": \"" + modelXID + "\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=top,shape=straight,waterlogged=false\":  { \"model\": \"" + modelXID + "\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=top,shape=outer_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=top,shape=outer_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=top,shape=outer_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=top,shape=outer_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=top,shape=outer_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=top,shape=outer_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=top,shape=outer_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=top,shape=outer_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_outer\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=top,shape=inner_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=top,shape=inner_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 270, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=top,shape=inner_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=top,shape=inner_right,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=east,half=top,shape=inner_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=west,half=top,shape=inner_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 180, \"uvlock\": true },\n" +
                                     "        \"facing=south,half=top,shape=inner_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 90, \"uvlock\": true },\n" +
                                     "        \"facing=north,half=top,shape=inner_left,waterlogged=false\":  { \"model\": \"" + modelXID + "_inner\", \"x\": 180, \"y\": 270, \"uvlock\": true }\n" +
                                     "    }\n" +
                                     "}\n");
                    } else {
                        writer.write("{\n" +
                                     "  \"multipart\": [\n" +
                                     "    {\n" +
                                     "      \"when\": {\n" +
                                     "        \"up\": \"true\"\n" +
                                     "      },\n" +
                                     "      \"apply\": {\n" +
                                     "        \"model\": \"" + modelXID + "_post\"\n" +
                                     "      }\n" +
                                     "    },\n" +
                                     "    {\n" +
                                     "      \"when\": {\n" +
                                     "        \"north\": \"true\"\n" +
                                     "      },\n" +
                                     "      \"apply\": {\n" +
                                     "        \"model\": \"" + modelXID + "_side\",\n" +
                                     "        \"uvlock\": true\n" +
                                     "      }\n" +
                                     "    },\n" +
                                     "    {\n" +
                                     "      \"when\": {\n" +
                                     "        \"east\": \"true\"\n" +
                                     "      },\n" +
                                     "      \"apply\": {\n" +
                                     "        \"model\": \"" + modelXID + "_side\",\n" +
                                     "        \"y\": 90,\n" +
                                     "        \"uvlock\": true\n" +
                                     "      }\n" +
                                     "    },\n" +
                                     "    {\n" +
                                     "      \"when\": {\n" +
                                     "        \"south\": \"true\"\n" +
                                     "      },\n" +
                                     "      \"apply\": {\n" +
                                     "        \"model\": \"" + modelXID + "_side\",\n" +
                                     "        \"y\": 180,\n" +
                                     "        \"uvlock\": true\n" +
                                     "      }\n" +
                                     "    },\n" +
                                     "    {\n" +
                                     "      \"when\": {\n" +
                                     "        \"west\": \"true\"\n" +
                                     "      },\n" +
                                     "      \"apply\": {\n" +
                                     "        \"model\": \"" + modid + ":block/mars_cobblestone_wall_side\",\n" +
                                     "        \"y\": 270,\n" +
                                     "        \"uvlock\": true\n" +
                                     "      }\n" +
                                     "    }\n" +
                                     "  ]\n" +
                                     "}\n");
                    }

                    writer.flush();
                }

                try (FileWriter writer = new FileWriter(outputDir + "/assets/" + modid + "/models/block/" + id + ".json", false)) {
                    if (type == Type.DEFAULT) {
                        writer.write("{\n" +
                                     "  \"parent\": \"block/cube_all\",\n" +
                                     "  \"textures\": {\n" +
                                     "    \"all\": \"" + modelXID + "\"\n" +
                                     "  }\n" +
                                     "}");
                        writer.flush();
                    } else if (type == Type.SLAB) {
                        String texture = id.replace("_slab_", "");
                        texture = texture.replace("_slab", "");
                        texture = texture.replace("slab_", "");
                        texture = texture.replace("slab", "");
                        writer.write("{\n" +
                                     "    \"parent\": \"block/slab\",\n" +
                                     "    \"textures\": {\n" +
                                     "        \"bottom\": \"" + modid + ":block/" + texture + "\",\n" +
                                     "        \"top\": \"" + modid + ":block/" + texture + "\",\n" +
                                     "        \"side\": \"" + modid + ":block/" + texture + "\"\n" +
                                     "    }\n" +
                                     "}\n");
                        writer.flush();
                        writer.close();

                        try (FileWriter writer1 = new FileWriter(outputDir + "/assets/" + modid + "/models/block/" + id + "_top" + ".json", false)) {
                            writer1.write("{\n" +
                                         "    \"parent\": \"block/slab_top\",\n" +
                                         "    \"textures\": {\n" +
                                         "        \"bottom\": \"" + modid + ":block/" + texture + "\",\n" +
                                         "        \"top\": \"" + modid + ":block/" + texture + "\",\n" +
                                         "        \"side\": \"" + modid + ":block/" + texture + "\"\n" +
                                         "    }\n" +
                                         "}\n");
                            writer1.flush();
                        }
                    } else if (type == Type.STAIR) {
                        String texture = id.replace("_stairs_", "");
                        texture = texture.replace("_stairs", "");
                        texture = texture.replace("stairs_", "");
                        texture = texture.replace("_stair_", "");
                        texture = texture.replace("_stair", "");
                        texture = texture.replace("stair_", "");
                        writer.write("{\n" +
                                     "    \"parent\": \"block/stairs\",\n" +
                                     "    \"textures\": {\n" +
                                     "        \"bottom\": \"" + modid + ":block/" + texture + "\",\n" +
                                     "        \"top\": \"" + modid + ":block/" + texture + "\",\n" +
                                     "        \"side\": \"" + modid + ":block/" + texture + "\"\n" +
                                     "    }\n" +
                                     "}\n");
                        writer.flush();
                        writer.close();

                        try (FileWriter writer1 = new FileWriter(outputDir + "/assets/" + modid + "/models/block/" + id + "_inner.json", false)) {
                            writer1.write("{\n" +
                                         "    \"parent\": \"block/inner_stairs\",\n" +
                                         "    \"textures\": {\n" +
                                         "        \"bottom\": \"" + modid + ":block/" + texture + "\",\n" +
                                         "        \"top\": \"" + modid + ":block/" + texture + "\",\n" +
                                         "        \"side\": \"" + modid + ":block/" + texture + "\"\n" +
                                         "    }\n" +
                                         "}\n");
                            writer1.flush();
                        }

                        try (FileWriter writer1 = new FileWriter(outputDir + "/assets/" + modid + "/models/block/" + id + "_outer.json", false)) {
                            writer1.write("{\n" +
                                         "    \"parent\": \"block/outer_stairs\",\n" +
                                         "    \"textures\": {\n" +
                                         "        \"bottom\": \"" + modid + ":block/" + texture + "\",\n" +
                                         "        \"top\": \"" + modid + ":block/" + texture + "\",\n" +
                                         "        \"side\": \"" + modid + ":block/" + texture + "\"\n" +
                                         "    }\n" +
                                         "}\n");
                        }

                    } else if (type == Type.WALL) {
                        String texture = id.replace("_wall_", "");
                        texture = texture.replace("_wall", "");
                        texture = texture.replace("wall_", "");
                        new File(outputDir + "/assets/" + modid + "/models/block/" + id + ".json").delete();
                        try (FileWriter writer1 = new FileWriter(outputDir + "/assets/" + modid + "/models/block/" + id + "_inventory.json", false)) {
                            writer1.write("{\n" +
                                         "  \"parent\": \"block/wall_inventory\",\n" +
                                         "  \"textures\": {\n" +
                                         "    \"wall\": \"" + modid + ":block/" + texture + "\"\n" +
                                         "  }\n" +
                                         "}");
                            writer1.flush();
                        }

                        try (FileWriter writer1 = new FileWriter(outputDir + "/assets/" + modid + "/models/block/" + id + "_post.json", false)) {
                            writer1.write("{\n" +
                                         "  \"parent\": \"block/template_wall_post\",\n" +
                                         "  \"textures\": {\n" +
                                         "    \"wall\": \"" + modid + ":block/" + texture + "\"\n" +
                                         "  }\n" +
                                         "}");
                            writer1.flush();
                        }
                        try (FileWriter writer1 = new FileWriter(outputDir + "/assets/" + modid + "/models/block/" + id + "_side.json", false)) {
                            writer1.write("{\n" +
                                         "  \"parent\": \"block/template_wall_side\",\n" +
                                         "  \"textures\": {\n" +
                                         "    \"wall\": \"" + modid + ":block/" + texture + "\"\n" +
                                         "  }\n" +
                                         "}");
                            writer1.flush();
                        }
                    }
                    writer.flush();
                }

                try (FileWriter writer = new FileWriter(outputDir + "/assets/" + modid + "/models/item/" + id + ".json", false)) {

                    if (type != Type.WALL) {
                        writer.write("{\n" +
                                     "  \"parent\": \"" + modelXID + "\"\n" +
                                     "}");
                    } else {
                        writer.write("{\n" + //TODO
                                     "  \"parent\": \"" + modelXID + "_inventory\"\n" +
                                     "}");
                    }
                    writer.flush();
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private enum Type {
        DEFAULT,
        SLAB,
        STAIR,
        WALL
    }
}
