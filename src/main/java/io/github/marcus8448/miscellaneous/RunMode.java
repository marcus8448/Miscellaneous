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

import io.github.marcus8448.miscellaneous.minecraft.FabricProjectGenerator;
import io.github.marcus8448.miscellaneous.minecraft.model.ModelGenerator;
import io.github.marcus8448.miscellaneous.misc.remapper.PackageMover;
import io.github.marcus8448.miscellaneous.pixelart.Enlarge;
import io.github.marcus8448.miscellaneous.pixelart.Shrink;

import java.util.Locale;
import java.util.function.Consumer;

public enum RunMode {
    ENLARGE_PIXEL_ART(Enlarge::enlarge),
    SHRINK_PIXEL_ART(Shrink::shrink),
    GENERATE_MODELS(ModelGenerator::run),
    MOVE_COMMENTED_CLASSES(PackageMover::move),
    GENERATE_FABRIC(FabricProjectGenerator::generate);

    private final Consumer<String[]> creator;

    RunMode(Consumer<String[]> creator) {
        this.creator = creator;
    }

    public static RunMode valueOfNullable(String s) {
        try {
            return valueOf(s.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public void run(String[] s) {
        this.creator.accept(s);
    }
}
