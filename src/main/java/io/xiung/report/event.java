package io.xiung.report;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static io.xiung.report.command.menu;

public class event implements Listener {

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e){
        if(e.getInventory().equals(menu)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e){
        if(!e.getInventory().equals(menu)) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        if(clickedItem == null || clickedItem.getType().isAir()) return;

        final Player player = (Player) e.getWhoClicked();

        if(clickedItem.getType().name().equals("REDSTONE")){
            TextComponent reported = (TextComponent) clickedItem.getItemMeta().lore().get(0);
            TextComponent reason = (TextComponent) clickedItem.getItemMeta().lore().get(1);

            //webhook
            String webhookURL = main.config.getString("webhookURL");
            String title = "<:6441redverified:987589598655942726> 檢舉通知";
            String id = reported.content();
            String uuid = Objects.requireNonNull(Bukkit.getPlayer(reported.content())).getUniqueId().toString();
            String reason_content = reason.content();

            DiscordWebhook webhook = new DiscordWebhook(webhookURL);
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(title)
                    .addField("檢舉人", player.getName() + "(" + player.getUniqueId().toString() +")", false)
                    .addField("被檢舉人", id + "(" + uuid + ")", false)
                    .addField("原因", "**" + reason_content + "**", false)
            );

            try {
                webhook.execute();
            }catch (java.io.IOException exception){
                System.out.println(exception.toString());
            }

            player.closeInventory();
        }

    }
}
