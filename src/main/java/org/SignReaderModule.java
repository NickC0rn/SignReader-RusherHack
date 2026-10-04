package org.example;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.rusherhack.client.api.events.world.EventChunk;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.event.subscribe.Subscribe;
import org.rusherhack.core.setting.BooleanSetting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignReaderModule extends ToggleableModule {

    private final BooleanSetting showCoords = new BooleanSetting("ShowCoords", "Show sign coordinates in chat", false);
    private final Set<BlockPos> seenSigns = new HashSet<>();

    public SignReaderModule() {
        super("SignReader", "Prints sign contents to chat", ModuleCategory.CLIENT);
        this.registerSettings(this.showCoords);
    }

    @Subscribe
    private void onChunkLoad(EventChunk.Load event) {
        LevelChunk chunk = event.getChunk();
        if (chunk == null) return;

        for (Map.Entry<BlockPos, BlockEntity> entry : chunk.getBlockEntities().entrySet()) {
            BlockEntity be = entry.getValue();
            if (!(be instanceof SignBlockEntity sign)) continue;

            BlockPos pos = entry.getKey();
            if (seenSigns.contains(pos)) continue;
            seenSigns.add(pos);

            List<String> lines = new ArrayList<>();
            readFace(sign, false, lines);
            readFace(sign, true, lines);

            if (lines.isEmpty()) continue;

            String text = "\u00a7b" + String.join(" | ", lines);

            if (this.showCoords.getValue()) {
                String coords = String.format(" \u00a77[%d, %d, %d]", pos.getX(), pos.getY(), pos.getZ());
                ChatUtils.print("\u00a77[Sign] " + text + coords);
            } else {
                ChatUtils.print("\u00a77[Sign] " + text);
            }
        }
    }

    private void readFace(SignBlockEntity sign, boolean back, List<String> out) {
        var face = back ? sign.getBackText() : sign.getFrontText();
        for (int i = 0; i < 4; i++) {
            String line = face.getMessage(i, false).getString().trim();
            if (!line.isEmpty()) out.add(line);
        }
    }

    @Override
    public void onDisable() {
        seenSigns.clear();
    }
}