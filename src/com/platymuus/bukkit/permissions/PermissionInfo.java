package com.platymuus.bukkit.permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class representing the global and world nodes attached to a player or
 * group.
 */
public class PermissionInfo {

	private final PermissionsPlugin plugin;
	private final String node;

	protected PermissionInfo(PermissionsPlugin plugin, String node) {
		this.plugin = plugin;
		this.node = node;
	}

	/**
	 * Gets the list of groups this group/player inherits permissions from.
	 * 
	 * @return The list of groups.
	 */
	public List<Group> getGroups() {
		ArrayList<Group> result = new ArrayList<Group>();

		if (node.startsWith("users")) {
			for (String key : plugin.api.getPlayerGroups(node.replace("users/", ""))) {
				Group group = plugin.getGroup(key);
				if (group != null) {
					result.add(group);
				}
			}
		} else if (node.startsWith("groups")) {
			for (String key : plugin.api.getGroupInheritance(node.replace("groups/", ""))) {
				Group group = plugin.getGroup(key);
				if (group != null) {
					result.add(group);
				}
			}
		}

		return result;
	}

	/**
	 * Gets a map of non-world-specific permission nodes to boolean values that
	 * this group/player defines.
	 * 
	 * @return The map of permissions.
	 */
	public Map<String, Boolean> getPermissions() {
		if (node.startsWith("users")) {
			return plugin.api.getPlayerPermissions(node.replace("users/", ""));
		} else if (node.startsWith("groups")) {
			return plugin.api.getGroupPermissions(node.replace("groups/", ""));
		} else {
			return new HashMap<String, Boolean>();
		}
	}

	/**
	 * Gets a list of worlds this group/player defines world-specific
	 * permissions for.
	 * 
	 * @return The list of worlds.
	 */
	public Set<String> getWorlds() {
		if (node.startsWith("users")) {
			return new HashSet<String>(plugin.api.getPlayerWorlds(node.replace("users/", "")));
		} else if (node.startsWith("groups")) {
			return new HashSet<String>(plugin.api.getGroupWorlds(node.replace("groups/", "")));
		} else {
			return new HashSet<String>();
		}
	}

	/**
	 * Gets a map of world-specific permission nodes to boolean values that this
	 * group/player defines.
	 * 
	 * @param world
	 *            The name of the world.
	 * @return The map of permissions.
	 */
	public Map<String, Boolean> getWorldPermissions(String world) {
		if (node.startsWith("users")) {
			return plugin.api.getPlayerPermissions(node.replace("users/", ""), world);
		} else if (node.startsWith("groups")) {
			return plugin.api.getGroupPermissions(node.replace("groups/", ""), world);
		} else {
			return new HashMap<String, Boolean>();
		}
	}

}
