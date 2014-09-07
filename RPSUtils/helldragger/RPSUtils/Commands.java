package helldragger.RPSUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


class Commands implements CommandExecutor
{
	/**
	 * @uml.property  name="plugin"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="cmdListener:RolePlaySpecialityWeapons.RPSWPlugin"
	 */
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args)
	{
		return true;
	}

	void sendHelp(CommandSender sender)
	{
		
	}

}
