package com.platymuus.bukkit.permissions;

import java.util.ArrayList;
import java.util.List;

import net.crystalyx.bukkit.simplyperms.SimplyAPI;
import net.crystalyx.bukkit.simplyperms.SimplyPlugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for PermissionsBukkit.
 */
public class PermissionsPlugin extends JavaPlugin {

	protected SimplyAPI api;

	// -- Basic stuff
	@Override
	public void onEnable() {
		api = ((SimplyPlugin) getServer().getPluginManager().getPlugin("SimplyPerms")).getAPI();

		// How are you gentlemen
		getLogger().info("SimplyWrapper successfully enabled !");
	}

	@Override
	public void onDisable() {
		// Good day to you! I said good day!
		getLogger().info("SimplyWrapper successfully disabled !");
	}

	public FileConfiguration getConfig() {
		return getServer().getPluginManager().getPlugin("SimplyPerms").getConfig();
	}

	// -- External API
	/**
	 * Get the group with the given name.
	 * 
	 * @param groupName The name of the group.
	 * @return A Group if it exists or null otherwise.
	 */
	public Group getGroup(String groupName) {
		if (api.getAllGroups().contains(groupName)) {
			return new Group(this, groupName);
		}
		return null;
	}

	/**
	 * Returns a list of groups a player is in.
	 * 
	 * @param playerName The name of the player.
	 * @return The groups this player is in. May be empty.
	 */
	public List<Group> getGroups(String playerName) {
		ArrayList<Group> result = new ArrayList<Group>();
		if (api.isPlayerInDB(playerName)) {
			for (String key : api.getPlayerGroups(playerName)) {
				result.add(new Group(this, key));
			}
		} else {
			result.add(new Group(this, api.getDefaultGroup()));
		}
		return result;
	}

	/**
	 * Returns permission info on the given player.
	 * 
	 * @param playerName The name of the player.
	 * @return A PermissionsInfo about this player.
	 */
	public PermissionInfo getPlayerInfo(String playerName) {
		if (!api.isPlayerInDB(playerName)) {
			return null;
		} else {
			return new PermissionInfo(this, "users/" + playerName);
		}
	}

	/**
	 * Returns a list of all defined groups.
	 * 
	 * @return The list of groups.
	 */
	public List<Group> getAllGroups() {
		ArrayList<Group> result = new ArrayList<Group>();
		for (String key : api.getAllGroups()) {
			result.add(new Group(this, key));
		}
		return result;
	}

}
