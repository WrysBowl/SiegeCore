package net.siegemc.core.Dungeons;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import net.siegemc.core.Core;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SchematicPaster {
    public static Clipboard loadSchematic(InputStream resource, ClipboardFormat clipboardFormat) throws IOException {
        Clipboard clipboard = null;
        ClipboardReader reader = clipboardFormat.getReader(resource);
        clipboard = reader.read();
        return clipboard;
    }

    // USE THIS FOR FILES IN THE PLUGIN's RESOURCES FOLDER
    public static void pasteSchematic(String resourceName, Location pos, String schematicFormat /*MCEDIT or SPONGE*/, boolean ignoreAirBlocks) throws IOException, WorldEditException {
        EditSession builder = WorldEdit.getInstance()
                .newEditSession(BukkitAdapter.adapt(pos.getWorld()));
        ClipboardFormat format = ClipboardFormats.findByAlias(schematicFormat);
        if (format == null) return;
        Operation operation = new ClipboardHolder(loadSchematic(Core.plugin().getResource(resourceName), format))
                .createPaste(builder)
                .to(BukkitAdapter.asBlockVector(pos))
                .ignoreAirBlocks(ignoreAirBlocks)
                .build();
        Operations.complete(operation);
        builder.close();
    }


    // USE THIS FOR FILES NOT IN THE PLUGIN's RESOURCES FOLDER
    public static void pasteSchematic(File file, Location pos, String schematicFormat /*MCEDIT or SPONGE*/, boolean ignoreAirBlocks) throws IOException, WorldEditException {
        EditSession builder = WorldEdit.getInstance()
                .newEditSession(BukkitAdapter.adapt(pos.getWorld()));
        ClipboardFormat format = ClipboardFormats.findByAlias(schematicFormat);
        if (format == null) return;
        Operation operation = new ClipboardHolder(loadSchematic(new FileInputStream(file), format))
                .createPaste(builder)
                .to(BukkitAdapter.asBlockVector(pos))
                .ignoreAirBlocks(ignoreAirBlocks)
                .build();
        Operations.complete(operation);
        builder.close();
    }
}
