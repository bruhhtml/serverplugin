package me.plugin.OpenWorld.NPCs.Merchant;

import me.plugin.MainLobby.NPCs.Banker.GUIs.BankGUI;
import me.plugin.OpenWorld.NPCs.Merchant.GUIs.CategoryGUI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MerchantController implements Listener {

    @EventHandler
    private void npcClick(NPCRightClickEvent e) {

        String npcName = e.getNPC().getName();
        Player clicker = (Player) e.getClicker();

        if (npcName.equalsIgnoreCase("Merchant")) {

            CategoryGUI.openGUI(clicker.getPlayer());

        } else if (npcName.equalsIgnoreCase("Banker")) {

            BankGUI.openGUI(clicker.getPlayer());

        }

    }

}
