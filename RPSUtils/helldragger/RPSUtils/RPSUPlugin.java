package helldragger.RPSUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.BooleanFlag;



public class RPSUPlugin extends JavaPlugin
{



	/**
	 * @uml.property  name="pm"
	 * @uml.associationEnd  
	 */
	PluginManager pm;
	/**
	 * @uml.property  name="pdf"
	 * @uml.associationEnd  
	 */
	PluginDescriptionFile pdf;
	static Logger log;

	/**
	 * @uml.property  name="events"
	 * @uml.associationEnd  inverse="plugin:RolePlaySpecialityWeapons.Events"
	 */
	Events events;
	/**
	 * @uml.property  name="cmdListener"
	 * @uml.associationEnd  inverse="plugin:RolePlaySpecialityWeapons.Commands"
	 */
	Commands cmdListener;
	WorldGuardPlugin wgp;
	WGCustomFlagsPlugin customFlags;
	public BooleanFlag ANTIBACK = new BooleanFlag("ANTIBACK");




	@Override
	public void onEnable()
	{
		pm = getServer().getPluginManager();
		pdf = getDescription();
		log = Logger.getLogger("Minecraft");
		
		try {
			loadData();
		} catch (IOException | InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		events = new Events(this);

		pm.registerEvents(this.events, this);

		log.info("RolePlayspeciality Utils v" + pdf.getVersion() + " by " + pdf.getAuthors() + " is now enabled!");
	}




	@Override
	public void onDisable()
	{


		log.info("RolePlayspeciality Utils v" + pdf.getVersion() + " by " + pdf.getAuthors() + " is now disabled.");
	}




	public void onReload()
	{
		log.info("Reloading RolePlaySpeciality Utils v" + pdf.getVersion() +"...");

		log.info("Loading plugin data...");
		

		log.info("RolePlaySpeciality Utils has successfully reloaded.");
	}




	private WGCustomFlagsPlugin getWGCustomFlags()
	{
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WGCustomFlags");

		if (plugin == null || !(plugin instanceof WGCustomFlagsPlugin))
		{
			return null;
		}

		return (WGCustomFlagsPlugin) plugin;
	}



	private WorldGuardPlugin getWorldGuard() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null; // Maybe you want throw an exception instead
		}

		return (WorldGuardPlugin) plugin;
	}


	private void loadData() throws IOException, InvalidConfigurationException
	{
		File folder = getDataFolder();

		if (!folder.exists())
		{
			folder.mkdir();
		}
		else
		{
			File oldConfigFile = new File(folder.getPath() + File.separator + "config.yml");

			if (oldConfigFile.exists())
			{
				oldConfigFile.delete();
			}

		}

		String dataPath = folder.getPath() + File.separator;

		File configFolder = new File(dataPath + "configuration");

		if (!configFolder.exists())
		{
			configFolder.mkdir();
		}
		
		
		wgp = getWorldGuard();

		if (wgp instanceof WorldGuardPlugin)
		{
			customFlags = getWGCustomFlags();

			if (customFlags instanceof WGCustomFlagsPlugin)
			{
				customFlags.addCustomFlag(ANTIBACK);
				
			}
			else
				log.warning("WorldGuard customFlags non présent, anti /back inactif!");

		}
		else
			log.warning("WorldGuard non présent, anti /back inactif!");





		Config.removeOldData(this);
		Config.loadConfig(this, Config.CONFIG);
		Config.loadConfigValues(this);
	}

	
	static void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}


}

