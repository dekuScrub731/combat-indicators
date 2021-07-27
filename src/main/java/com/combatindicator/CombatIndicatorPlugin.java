package com.combatindicator;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@PluginDescriptor(
		name = "Combat Indicator",
		description = "Show highlight of Player and NPCs when in combat",
		tags = {"display", "combat", "highlight", "indicator"}
)
public class CombatIndicatorPlugin extends Plugin
{
	protected List<NPC> combatList = new ArrayList<NPC>();
	protected boolean playerInCombat = false;

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private CombatIndicatorOverlay overlay;

	@Inject
	private CombatIndicatorConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.debug("Combat Indicator started");
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.debug("Combat Indicator stopped");
		overlayManager.remove(overlay);
	}

	@Provides
	CombatIndicatorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CombatIndicatorConfig.class);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		playerInCombat = false;
		combatList.clear();
	}

	@Subscribe
	public void onGameTick(GameTick gameTick)
	{

		//in case anything gets past the InteractingChanged event
		for (NPC npc : client.getNpcs()) {
			if (npc.getInteracting() == client.getLocalPlayer() && npc.getCombatLevel() > 0 && !combatList.contains(npc)) {
				combatList.add(npc);
			}

		}

		//remove NPCs no longer targeting player
		combatList.removeIf(npc -> npc!=null && npc.getInteracting() != client.getLocalPlayer());

		//if nothing is targeting player, remove combat indicator
		if (combatList.size()>0)
			playerInCombat = true;
		else
			playerInCombat = false;
	}

	@Subscribe
	public void onInteractingChanged(InteractingChanged event)
	{
		NPC opponent = null;

		//Add NPCs targeted by player
		if (event.getSource() == client.getLocalPlayer() && event.getTarget() != null) {
			opponent = (NPC) event.getTarget();
		}

		//Add NPCs that target the player
		else if (event.getTarget() == client.getLocalPlayer() && event.getSource() != null) {
			try {
				opponent = (NPC) event.getSource();
			}
			catch (ClassCastException e) {
				log.debug("Class " + event.getSource().getClass().getName() + " cannot be cast to NPC. Skipping.");
			}
		}

		//Only add if NPC has a combat level and is not already in our list
		if (opponent!=null && opponent.getCombatLevel()>0 && !combatList.contains(opponent))
			combatList.add(opponent);
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned npcDespawned)
	{
		NPC npc = npcDespawned.getNpc();
		if (npc != null && combatList.contains(npc)){
			combatList.remove(npc);
		}
	}

}
