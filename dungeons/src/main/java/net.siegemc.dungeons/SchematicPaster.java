package net.siegemc.dungeons;

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
    /**
     * Load a schematic from an {@link InputStream}
     * @param resource {@link InputStream}
     * @param clipboardFormat The format of the clipboard
     * @return Returns a {@link Clipboard}
     * @throws IOException Thrown when the resource can't be read.
     */
    public static Clipboard loadSchematic(InputStream resource, ClipboardFormat clipboardFormat) throws IOException {
        Clipboard clipboard = null;
        ClipboardReader reader = clipboardFormat.getReader(resource);
        clipboard = reader.read();
        return clipboard;
    }

    // USE THIS FOR FILES IN THE PLUGIN's RESOURCES FOLDER

    /**
     * Paste the schematic from a resource in the plugins' resources folder
     * @param resourcePath The path of the resource (relative to the plugins' resources folder)
     * @param pos The position to paste the schematic in
     * @param schematicFormat The format of the schematic, either "MCEDIT" or "SPONGE" for the moment.
     * @param ignoreAirBlocks Whether or not air blocks are skipped while pasting
     * @throws IOException Thrown when the resource can't be read
     * @throws WorldEditException Thrown when the schematic wasn't able to be pasted
     */
    public static void pasteSchematic(String resourcePath, Location pos, String schematicFormat /*MCEDIT or SPONGE*/, boolean ignoreAirBlocks) throws IOException, WorldEditException {
        EditSession builder = WorldEdit.getInstance()
                .newEditSession(BukkitAdapter.adapt(pos.getWorld()));
        ClipboardFormat format = ClipboardFormats.findByAlias(schematicFormat);
        if (format == null) return;
        Operation operation = new ClipboardHolder(loadSchematic(Core.plugin().getResource(resourcePath), format))
                .createPaste(builder)
                .to(BukkitAdapter.asBlockVector(pos))
                .ignoreAirBlocks(ignoreAirBlocks)
                .build();
        Operations.complete(operation);
        builder.close();
    }


    // USE THIS FOR FILES NOT IN THE PLUGIN's RESOURCES FOLDER
    /**
     * Paste the schematic from a file (not necessarily in the plugins' resources folder)
     * @param file The schematic's file
     * @param pos The position to paste the schematic in
     * @param schematicFormat The format of the schematic, either "MCEDIT" or "SPONGE" for the moment.
     * @param ignoreAirBlocks Whether or not air blocks are skipped while pasting
     * @throws IOException Thrown when the resource can't be read
     * @throws WorldEditException Thrown when the schematic wasn't able to be pasted
     */
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

    /**
     * Paste the contents of a clipboard.
     * @param board The clipboard containing the schematic
     * @param pos The position to paste the schematic in
     * @param schematicFormat The format of the schematic, either "MCEDIT" or "SPONGE" for the moment.
     * @param ignoreAirBlocks Whether or not air blocks are skipped while pasting
     * @throws IOException Thrown when the resource can't be read
     * @throws WorldEditException Thrown when the schematic wasn't able to be pasted
     */
    public static void pasteSchematic(Clipboard board, Location pos, String schematicFormat /*MCEDIT or SPONGE*/, boolean ignoreAirBlocks) throws IOException, WorldEditException {
        EditSession builder = WorldEdit.getInstance()
                .newEditSession(BukkitAdapter.adapt(pos.getWorld()));
        ClipboardFormat format = ClipboardFormats.findByAlias(schematicFormat);
        if (format == null) return;
        Operation operation = new ClipboardHolder(board)
                .createPaste(builder)
                .to(BukkitAdapter.asBlockVector(pos))
                .ignoreAirBlocks(ignoreAirBlocks)
                .build();
        Operations.complete(operation);
        builder.close();
    }
}
