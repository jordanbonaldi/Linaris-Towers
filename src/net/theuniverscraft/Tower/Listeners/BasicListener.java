package net.theuniverscraft.Tower.Listeners;

import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;
import net.theuniverscraft.Tower.*;
import net.theuniverscraft.Tower.Enum.*;

public class BasicListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        this.cancel((Cancellable)event);
    }
    
    @EventHandler
    public void onPlayerInteractEntity(final PlayerInteractEntityEvent event) {
        this.cancel((Cancellable)event);
    }
    
    @EventHandler
    public void onPlayerDropItem(final PlayerDropItemEvent event) {
        this.cancel((Cancellable)event);
    }
    
    @EventHandler
    public void onPlayerPickupItemEvent(final PlayerPickupItemEvent event) {
        this.cancel((Cancellable)event);
    }
    
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        this.cancel((Cancellable)event);
    }
    
    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        this.cancel((Cancellable)event);
    }
    
    @EventHandler
    public void onBlockPlace(final PlayerItemConsumeEvent event) {
        this.cancel((Cancellable)event);
    }
    
    @EventHandler
    public void onEntityShootBow(final EntityShootBowEvent event) {
        this.cancel((Cancellable)event);
    }
    
    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent event) {
        this.cancel((Cancellable)event);
    }
    
    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        this.cancel((Cancellable)event);
    }
    
    private void cancel(final Cancellable event) {
        final GameState state = Tower.getInstance().getGameState();
        if (state != GameState.GAME && state != GameState.INIT_GAME && state != GameState.END_GAME) {
            event.setCancelled(true);
        }
    }
}
