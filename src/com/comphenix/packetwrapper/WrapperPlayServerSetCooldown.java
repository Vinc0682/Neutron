/**
 * This file is part of PacketWrapper.
 * Copyright (C) 2012-2015 Kristian S. Strangeland
 * Copyright (C) 2015 dmulloy2
 *
 * PacketWrapper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PacketWrapper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with PacketWrapper.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.comphenix.packetwrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.EquivalentConverter;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.MethodAccessor;
import com.comphenix.protocol.utility.MinecraftReflection;
import org.bukkit.Material;

public class WrapperPlayServerSetCooldown extends AbstractPacket {
	private static final Class<?> ITEM_CLASS = MinecraftReflection.getMinecraftClass("Item");
	public static final PacketType TYPE = PacketType.Play.Server.SET_COOLDOWN;

	public WrapperPlayServerSetCooldown() {
		super(new PacketContainer(TYPE), TYPE);
		handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerSetCooldown(PacketContainer packet) {
		super(packet, TYPE);
	}

	public Material getItem() {
		return handle.getModifier().<Material>withType(ITEM_CLASS, new ItemConverter()).read(0);
	}

	public void setItem(Material value) {
		handle.getModifier().<Material>withType(ITEM_CLASS, new ItemConverter()).write(0, value);
	}

	public int getTicks() {
		return handle.getIntegers().read(0);
	}

	public void setTicks(int value) {
		handle.getIntegers().write(0, value);
	}

	private static class ItemConverter implements EquivalentConverter<Material> {
		private static MethodAccessor getMaterial = null;
		private static MethodAccessor getItem = null;

		@Override
		public Material getSpecific(Object generic) {
			if (getMaterial == null) {
				getMaterial = Accessors.getMethodAccessor(MinecraftReflection.getCraftBukkitClass("util.CraftMagicNumbers"), "getMaterial", ITEM_CLASS);
			}

			return (Material) getMaterial.invoke(null, generic);
		}

		@Override
		public Object getGeneric(Class<?> genericType, Material specific) {
			if (getItem == null) {
				getItem = Accessors.getMethodAccessor(MinecraftReflection.getCraftBukkitClass("util.CraftMagicNumbers"), "getItem", Material.class);
			}

			return getItem.invoke(null, specific);
		}

		@Override
		public Class<Material> getSpecificType() {
			return Material.class;
		}
	}
}