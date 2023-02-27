package io.xiung.report;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class command implements CommandExecutor {
    public static Inventory menu;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            if(!(args.length == 2)) return false;
            Player report_p = Bukkit.getPlayer(args[0]);
            assert report_p != null;
            if(sender.getName().equals(report_p.getName())){
                sender.sendMessage(ChatColor.RED + "你不能檢舉自己, you can't report yourself.");
                return false;
            }
            openMenu((Player) sender, args[0], args[1]);
            return true;
        }else {
            sender.sendMessage(ChatColor.RED + "該指令僅能在遊戲中使用, this command can only execute in game.");
            return false;
        }

    }

    public void openMenu(Player player, String reported, String reason){
        ItemStack report_item = new ItemStack(Material.REDSTONE);
        ItemMeta report_meta = report_item.getItemMeta();
        TextComponent report_name = Component.text("確認檢舉玩家 !", TextColor.fromHexString("#bf3939"), TextDecoration.BOLD);
        report_meta.displayName(report_name);
        TextComponent report_id = Component.text(reported, TextColor.fromHexString("#b3b3b3")).decoration(TextDecoration.ITALIC, false);
        TextComponent report_reason = Component.text(reason, TextColor.fromHexString("#ffffff")).decoration(TextDecoration.ITALIC, false);
        List<Component> lores = new ArrayList<>();
        lores.add(report_id);
        lores.add(report_reason);
        report_meta.lore(lores);
        report_item.setItemMeta(report_meta);

        TextComponent menu_name = Component.text("你是否確定檢舉該玩家 ?", TextColor.fromHexString("#ff7940"), TextDecoration.BOLD);
        menu = Bukkit.createInventory(null, 9, menu_name);
        menu.setItem(4, report_item);

        player.openInventory(menu);
    }
}
