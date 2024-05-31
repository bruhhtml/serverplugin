package me.plugin.OpenWorld.NPCs;

import me.plugin.OpenWorld.NPCs.TransactionClasses.NPCUtils;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class NPCEventController implements Listener {

    @EventHandler
    private void onNPCRightClick(NPCRightClickEvent event) {
        Player clicker = event.getClicker();
        int npcID = event.getNPC().getId();
        String npcName = event.getNPC().getName();

        switch (npcID) {
            case 2:
            case 4:
                clicker.openInventory(NPCUtils.openNPCGUI(npcName, NPCUtils.getNPCCategories(npcName), NPCUtils.getNPCMaterials(npcName)));
                break;
            default:
                // Handle other NPCs or provide feedback
                break;
        }
    }
}
