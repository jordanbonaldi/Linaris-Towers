package net.theuniverscraft.Tower.Enum;

import org.bukkit.*;

public enum TeamColor
{
    RED("RED", 0, "Rouge", ChatColor.RED, DyeColor.RED), 
    BLUE("BLUE", 1, "Bleu", ChatColor.BLUE, DyeColor.BLUE),
    YELLOW("YELLOW", 1, "Jaune", ChatColor.YELLOW, DyeColor.YELLOW),
    GREEN("GREEN", 1, "Vert", ChatColor.GREEN, DyeColor.GREEN);

    
    private String m_local;
    private ChatColor m_chatColor;
    private DyeColor m_dyeColor;
    
    private TeamColor(final String s, final int n, final String local, final ChatColor chatColor, final DyeColor dyeColor) {
        this.m_local = local;
        this.m_chatColor = chatColor;
        this.m_dyeColor = dyeColor;
    }
    
    public ChatColor getChatColor() {
        return this.m_chatColor;
    }
    
    public DyeColor getDyeColor() {
        return this.m_dyeColor;
    }
    
    public String toLocal() {
        return this.m_local;
    }
    
    public static TeamColor getByeDyeColor(final DyeColor dyeColor) {
        TeamColor[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final TeamColor color = values[i];
            if (color.getDyeColor().equals((Object)dyeColor)) {
                return color;
            }
        }
        return null;
    }
    
    public static TeamColor getByeChatColor(final ChatColor chatColor) {
        TeamColor[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final TeamColor color = values[i];
            if (color.getChatColor().equals((Object)chatColor)) {
                return color;
            }
        }
        return null;
    }
}
