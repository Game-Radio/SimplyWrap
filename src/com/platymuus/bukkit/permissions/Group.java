package com.platymuus.bukkit.permissions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

/**
 * A class representing a permissions group.
 */
public class Group {

	private PermissionsPlugin plugin;
	private String name;

	protected Group(PermissionsPlugin plugin, String name) {
		this.plugin = plugin;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<String> getPlayers() {
		return plugin.api.getAllPlayers();
	}

	public List<Player> getOnlinePlayers() {
		ArrayList<Player> result = new ArrayList<Player>();
		for (String user : getPlayers()) {
			Player player = plugin.getServer().getPlayer(user);
			if (player != null && player.isOnline()) {
				result.add(player);
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		return !(o == null || !(o instanceof Group)) && name.equalsIgnoreCase(((Group) o).getName());
	}

	@Override
	public String toString() {
		return "Group{name=" + name + "}";
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
