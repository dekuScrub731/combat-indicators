package com.combatindicator;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import javax.inject.Inject;
import java.awt.*;

@Slf4j
class CombatIndicatorOverlay extends OverlayPanel
{
	private final Client client;
	private final CombatIndicatorConfig config;
	private final CombatIndicatorPlugin plugin;
	private final ModelOutlineRenderer modelOutlineRenderer;

	@Inject
	private CombatIndicatorOverlay(Client client, CombatIndicatorConfig config, CombatIndicatorPlugin plugin, ModelOutlineRenderer modelOutlineRenderer)
	{
		this.client = client;
		this.config = config;
		this.plugin = plugin;
		this.modelOutlineRenderer = modelOutlineRenderer;
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.MED);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		// Local Player highlighting
		final Player localPlayer = client.getLocalPlayer();
		if (plugin.playerInCombat) {
			if (config.combatBorderWidth() > 0)
				modelOutlineRenderer.drawOutline(localPlayer, config.combatBorderWidth(), config.combatHighlightColor(), config.combatOutlineFeather());
		}
		else {
			if (config.nonCombatBorderWidth() > 0)
				modelOutlineRenderer.drawOutline(localPlayer, config.nonCombatBorderWidth(), config.nonCombatHighlightColor(), config.nonCombatOutlineFeather());
		}

		// Target NPC enemy highlighting
		for (NPC npc : plugin.combatList)
			if (config.npcBorderWidth()>0)
				modelOutlineRenderer.drawOutline(npc, config.npcBorderWidth(), config.npcHighlightColor(), config.npcOutlineFeather());

		return super.render(graphics);
	}
}
