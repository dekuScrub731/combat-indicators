package com.combatindicator;

import net.runelite.client.config.*;
import java.awt.*;

@ConfigGroup("combatindicator")
public interface CombatIndicatorConfig extends Config
{
	// =========== Player Combat Section ===========
	@ConfigSection(
			name = "Player Highlight (Combat)",
			description = "The outline style of Player while in combat",
			position = 0
	)
	String playerHighlightCombat = "playerHighlightCombat";

	@Alpha
	@ConfigItem(
			position = 0,
			keyName = "combatHighlightColor",
			name = "Highlight Color",
			description = "Color of the Player highlight (in combat)",
			section = playerHighlightCombat
	)
	default Color combatHighlightColor()
	{
		return new Color(255,255,255,160);
	}

	@ConfigItem(
			position = 1,
			keyName = "combatBorderWidth",
			name = "Border Width",
			description = "Width of the Player highlight (in combat)",
			section = playerHighlightCombat
	)
	@Range(
			min = 0,
			max = 20
	)
	default int combatBorderWidth()
	{
		return 3;
	}

	@ConfigItem(
			position = 2,
			keyName = "combatOutlineFeather",
			name = "Outline feather",
			description = "Specify between 0-4 how much of the model outline should be faded",
			section = playerHighlightCombat
	)
	@Range(
			min = 0,
			max = 4
	)
	default int combatOutlineFeather()
	{
		return 4;
	}


	// =========== Player Non-Combat Section ===========
	@ConfigSection(
			name = "Player Highlight (Non-Combat)",
			description = "The outline style of Player while not in combat",
			position = 1
	)
	String playerHighlightNonCombat = "playerHighlightNonCombat";

	@Alpha
	@ConfigItem(
			position = 0,
			keyName = "nonCombatHighlightColor",
			name = "Highlight Color",
			description = "Color of the Player highlight (non-combat)",
			section = playerHighlightNonCombat
	)
	default Color nonCombatHighlightColor()
	{
		return new Color(0,0,0,160);
	}

	@ConfigItem(
			position = 1,
			keyName = "nonCombatBorderWidth",
			name = "Border Width",
			description = "Width of the Player highlight (non-combat)",
			section = playerHighlightNonCombat
	)
	@Range(
			min = 0,
			max = 20
	)
	default int nonCombatBorderWidth()
	{
		return 0;
	}

	@ConfigItem(
			position = 2,
			keyName = "nonCombatOutlineFeather",
			name = "Outline feather",
			description = "Specify between 0-4 how much of the model outline should be faded",
			section = playerHighlightNonCombat
	)
	@Range(
			min = 0,
			max = 4
	)
	default int nonCombatOutlineFeather()
	{
		return 4;
	}


	// =========== NPC Section ===========
	@ConfigSection(
			name = "NPC Highlight (Combat)",
			description = "The outline style of NPC while targeting the Player",
			position = 2
	)
	String npcHighlightCombat = "npcHighlightCombat";

	@Alpha
	@ConfigItem(
			position = 0,
			keyName = "npcColor",
			name = "Highlight Color",
			description = "Color of the NPC highlight",
			section = npcHighlightCombat
	)
	default Color npcHighlightColor()
	{
		return new Color(255,0,0,160);
	}

	@ConfigItem(
			position = 1,
			keyName = "npcBorderWidth",
			name = "Border Width",
			description = "Width of the highlighted NPC border",
			section = npcHighlightCombat
	)
	@Range(
			min = 0,
			max = 20
	)
	default int npcBorderWidth()
	{
		return 3;
	}

	@ConfigItem(
			position = 2,
			keyName = "npcOutlineFeather",
			name = "Outline feather",
			description = "Specify between 0-4 how much of the model outline should be faded",
			section = npcHighlightCombat
	)
	@Range(
			min = 0,
			max = 4
	)
	default int npcOutlineFeather()
	{
		return 4;
	}

}
