package helldragger.RPSUtils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.sk89q.worldguard.protection.ApplicableRegionSet;

class Events implements Listener
{
	private RPSUPlugin plugin;

	Events(RPSUPlugin plugin)
	{
		this.plugin = plugin;
	}



	








	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 */


	@EventHandler
	void onPlayerTeleport(PlayerTeleportEvent event){
		if( event.getCause() == TeleportCause.COMMAND
				|| event.getCause() == TeleportCause.PLUGIN
				|| (event.getCause() == TeleportCause.ENDER_PEARL
					& Config.ALLOW_ENDERPEARL))
		{
			Location endroitTP = event.getTo();
			World world = endroitTP.getWorld();

			ApplicableRegionSet regionsduTP = plugin.wgp.getRegionManager(world).getApplicableRegions(endroitTP);

			if(regionsduTP.getFlag(plugin.ANTIBACK).booleanValue())

			{
				event.setCancelled(true);	
				event.getPlayer().sendMessage(ChatColor.RED+"Vous ne pouvez pas acceder a cet endroit par téléportation");
			}

		}

	}


}
