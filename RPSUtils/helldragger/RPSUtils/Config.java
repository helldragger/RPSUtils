package helldragger.RPSUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.InvalidConfigurationException;


 class Config
{
	
	
	public static  boolean ALLOW_ENDERPEARL = false;
	static ConfigFile CONFIG = new ConfigFile("config.yml");

	static void loadConfig(RPSUPlugin rpsuPlugin, ConfigFile config) throws IOException,
			InvalidConfigurationException
	{
		File folder = rpsuPlugin.getDataFolder();

		String dataPath = folder.getPath() + File.separator + "configuration" + File.separator;

		File configFile = new File(dataPath + config.getName());

		if (!configFile.exists())
		{
			InputStream is = null;
		    OutputStream os = null;
		    try {
		        is = rpsuPlugin.getResource(config.getName());
		        os = new FileOutputStream(configFile);
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

		ConfigFile defaultConfig = new ConfigFile(config.getName());
		defaultConfig.load(rpsuPlugin.getResource(config.getName()));

		config.load(configFile);
		config.setDefaults(defaultConfig);
		config.options().copyDefaults(true);
		config.save(configFile);
	}

	 static void loadConfigValues(RPSUPlugin rpsuPlugin)
	{
		ALLOW_ENDERPEARL = CONFIG.getBoolean("allow enderpearls",false);
	}

	
	
	 static void removeOldData(RPSUPlugin rpsuPlugin)
	{
		try
		{
			deleteOldConfigs(rpsuPlugin);
		} 
		catch (IOException | InvalidConfigurationException e)
		{
			
		}
		
		
		
	}
	
	private static void deleteOldConfigs(RPSUPlugin plugin) throws IOException, InvalidConfigurationException
	{
		File folder = plugin.getDataFolder();
		
		new File(folder.getPath() + File.separator + "config.yml").delete();
		
		for (File file : new File(folder.getPath() + File.separator + "configuration").listFiles())
		{
			file.delete();
		}
	}
}