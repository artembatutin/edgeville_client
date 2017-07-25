package net.edge.activity.panel.impl;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.edge.Config;
import net.edge.activity.panel.Panel;
import net.edge.cache.unit.ObjectType;
import net.edge.cache.unit.NPCType;
import net.edge.game.Scene;
import net.edge.media.Rasterizer2D;
import net.edge.media.img.BitmapImage;
import net.edge.util.string.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class DropPanel extends Panel {
	
	public static Chance getChance(String s) {
		for(Chance chance : Chance.values()) {
			if(chance.name.equals(s.toLowerCase())) {
				return chance;
			}
		}
		return null;
	}
	
	public enum Chance {
		ALWAYS(0xffffff),
		COMMON(0x66ff66),
		UNCOMMON(0x33cc33),
		VERY_UNCOMMON(0xff9933),
		RARE(0xcc3300),
		VERY_RARE(0x990000),
		EXTREMELY_RARE(0x660000);
		
		public String name;
		
		public int color;
		
		Chance(int color) {
			this.color = color;
			name = this.name().replaceAll("_", " ").toLowerCase();
		}
	}
	
	public enum Drop {
		HERB_SEEDS(5291, 2, 1, 3, 5292, 2, 1, 3, 5293, 2, 1, 3, 5294, 2, 1, 3, 6311, 2, 1, 3, 5295, 2, 1, 3, 12176, 2, 1, 3, 5296, 2, 1, 3, 5297, 2, 1, 3, 14870, 2, 1, 3, 5298, 2, 1, 3, 5299, 2, 1, 3, 5300, 2, 1, 3, 5301, 2, 1, 3, 5302, 2, 1, 3, 5303, 2, 1, 3, 5304, 3, 1, 3, 5304, 3, 1, 3, 21621, 3, 1, 3),
		FLOWER_SEEDS(5096, 2, 1, 3, 5097, 2, 1, 3, 5098, 2, 1, 3, 5099, 2, 1, 3, 5100, 3, 1, 3, 14583, 3, 1, 3),
		ALLOTMENT_SEEDS(5318, 2, 1, 3, 5319, 2, 1, 3, 5324, 2, 1, 3, 5322, 2, 1, 3, 5323, 2, 1, 3, 5321, 2, 1, 3, 5322, 3, 1, 3),
		CHARMS(12158, 2, 1, 3, 12159, 2, 1, 3, 12160, 2, 1, 2, 12163, 2, 1, 2),
		LOW_RUNES(554, 2, 0, 100, 555, 2, 0, 100, 556, 2, 0, 100, 557, 2, 0, 100, 558, 2, 0, 100, 559, 2, 0, 100),
		MED_RUNES(554, 2, 50, 100, 555, 2, 50, 100, 556, 2, 50, 100, 557, 2, 50, 100, 564, 2, 50, 100, 560, 2, 50, 100, 561, 2, 50, 100, 562, 2, 50, 100, 563, 2, 50, 100, 565, 2, 50, 100, 566, 2, 50, 100),
		HIGH_RUNES(554, 2, 50, 250, 555, 2, 50, 250, 556, 2, 50, 250, 557, 2, 50, 250, 564, 2, 50, 250, 560, 2, 50, 250, 561, 2, 50, 250, 562, 2, 50, 250, 563, 2, 50, 250, 565, 2, 50, 250, 566, 2, 50, 250),
		LOW_HERBS(199, 2, 1, 1, 201, 2, 1, 1, 203, 2, 1, 1, 205, 2, 1, 1, 207, 2, 1, 1),
		MED_HERBS(207, 2, 1, 1, 3049, 2, 1, 1, 209, 2, 1, 1, 211, 2, 1, 1, 213, 2, 1, 1),
		HIGH_HERBS(208, 3, 1, 5, 3050, 3, 1, 5, 210, 3, 1, 5, 212, 3, 1, 5, 214, 3, 1, 5, 3052, 3, 1, 2, 216, 3, 1, 2, 2486, 3, 1, 2, 218, 3, 1, 2, 220, 3, 1, 2),
		MED_MINERALS(445, 2, 1, 20, 448, 3, 1, 10, 450, 3, 1, 10, 452, 4, 1, 5),
		LOW_GEMS(1624, 2, 1, 5, 1622, 2, 1, 5),
		MED_GEMS(1624, 2, 1, 10, 1622, 2, 1, 10, 1620, 2, 1, 5, 1618, 2, 1, 5),
		HIGH_GEMS(1624, 3, 10, 40, 1622, 3, 10, 40, 1620, 3, 10, 20, 1618, 3, 10, 20, 1632, 3, 1, 5, 6572, 3, 1, 2),
		BARROWS(4708, 3, 1, 1, 4710, 3, 1, 1, 4712, 3, 1, 1, 4714, 3, 1, 1, 4716, 5, 1, 1, 4718, 5, 1, 1, 4720, 5, 1, 1, 4722, 5, 1, 1, 4724, 3, 1, 1, 4726, 3, 1, 1, 4728, 3, 1, 1, 4730, 3, 1, 1, 4732, 3, 1, 1, 4734, 3, 1, 1, 4736, 3, 1, 1, 4738, 3, 1, 1, 4740, 1, 10, 120, 4745, 3, 1, 1, 4747, 3, 1, 1, 4749, 3, 1, 1, 4751, 3, 1, 1, 4753, 3, 1, 1, 4755, 3, 1, 1, 4757, 3, 1, 1, 4759, 3, 1, 1),
		CASKETS(405, 3, 1, 1);
		
		public int[] items;
		public int[] min;
		public int[] max;
		public int[] chance;
		public String name;
		
		Drop(int... ids) {
			items = new int[ids.length / 4];
			min = new int[ids.length / 4];
			max = new int[ids.length / 4];
			chance = new int[ids.length / 4];
			name = StringUtils.formatName(this.name().replaceAll("_", " ").toLowerCase());
			for(int id = 0; id < ids.length; id += 4) {
				items[id / 4] = ids[id];
				chance[id / 4] = ids[id + 1];
				min[id / 4] = ids[id + 2];
				max[id / 4] = ids[id + 3];
			}
		}
	}
	
	/**
	 * The npc type of this drop.
	 */
	private NPCType type;
	
	/**
	 * Array of table drops.
	 */
	private Drop[] drops = Drop.values();
	
	/**
	 * Array of chances.
	 */
	private Chance[] chances = Chance.values();
	
	/**
	 * Scroll bar manipulated value.
	 */
	private int scrollPos, scrollMax, scrollDragPos;
	
	/**
	 * The condition if the scroll is being dragged.
	 */
	private boolean scrollDrag = false;
	
	/**
	 * The cached search to not use a loop all the time.
	 */
	private String cachedSearch;
	
	/**
	 * An array of all npcs that have drops.
	 */
	private NPCType[] seekable;
	
	/**
	 * An array of all found npcs in our search.
	 */
	private int[] result;
	
	/**
	 * The hovered cache.
	 */
	private int cacheHover = -1;
	
	@Override
	public boolean process() {
		if(type == null && client.npcInfoId != 0) {
			type = NPCType.get(client.npcInfoId);
		}
	    /* Initialization */
		int beginX = 4;
		int beginY = 0;
		if(client.uiRenderer.isResizableOrFull()) {
			beginX = client.windowWidth / 2 - 380;
			beginY = client.windowHeight / 2 - 250;
		}
		
		int max1 = 1;
		int max2 = 0;
		if(client.panelSearch && seekable != null) {
			max1 = 32 * seekable.length;
			max2 = 0;
		} else if(!client.panelSearch) {
			max1 = 43 * client.npcDropsId.length;
			max2 = (32 * client.npcDropsId.length);
		}
		scrollMax = Math.max((max1 > max2 ? max1 : max2) - 285, 0);

        /* Scrolling */
		if(client.mouseInRegion(beginX + 5, beginY + 50, beginX + 493, beginY + 365)) {
			scrollPos += client.mouseWheelAmt * 24;
			if(scrollPos < 0) {
				scrollPos = 0;
			}
			if(scrollPos > scrollMax) {
				scrollPos = scrollMax;
			}
		}
		if(!scrollDrag) {
			int height = 268;
			if(scrollMax > 0) {
				height = 275 * 268 / (scrollMax + 275);
			}
			int pos = 0;
			if(scrollPos != 0) {
				pos = scrollPos * 268 / (scrollMax + 275) + 1;
			}
			int x = 485;
			int y = 46 + pos;
			if(client.mouseDragButton == 1 && client.mouseInRegion(x, y, x + 20, y + height)) {
				scrollDrag = true;
				scrollDragPos = scrollPos;
			}
		} else if(client.mouseDragButton != 1) {
			scrollDrag = false;
		} else {
			int d = (client.mouseY - client.clickY) * (scrollMax + 275) / 268;
			scrollPos = scrollDragPos + d;
			if(scrollPos < 0) {
				scrollPos = 0;
			}
			if(scrollPos > scrollMax) {
				scrollPos = scrollMax;
			}
		}
		

        /* Exit */
		if(processClose(beginX, beginY)) {
			return true;
		}
		
		int offset = -scrollPos + (Config.def.panelStyle == 2 ? 52 : 42);
		if(!client.panelSearch) {
			if(client.leftClickInRegion(beginX + 280, beginY + 12, beginX + 337, beginY + 37)) {
				client.panelSearch = true;
				client.npcSug = false;
				client.promptInput = "";
				client.panelSearchInput = "";
				client.promptInputTitle = "What monster are you searching for?";
				client.messagePromptRaised = true;
				type = null;
			}
		}
		if(!client.panelSearch && type != null) {
			if(client.leftClickInRegion(beginX + 340, beginY + 12, beginX + 437, beginY + 47)) {
				client.npcSug = true;
				client.panelSearch = false;
				client.promptInputTitle = "What would be the name of the item?";
				client.messagePromptRaised = true;
			}
		} else if(client.panelSearch) {
			if(client.panelSearchInput != null) {
				if(!Objects.equals(cachedSearch, client.panelSearchInput)) {
					cachedSearch = client.panelSearchInput;
					IntArrayList ids = new IntArrayList();
					for(int i = 0; i < seekable.length; i++) {
						NPCType n = seekable[i];
						if(n == null)
							continue;
						if(n.name == null)
							continue;
						if(Objects.equals(n.name, "null"))
							continue;
						if(n.name.toLowerCase().contains(cachedSearch.toLowerCase()))
							ids.add(i);
					}
					result = new int[ids.size()];
					for(int i = 0; i < ids.size(); i++) {
						result[i] = ids.getInt(i);
					}
				}
			}
			if(result != null && result.length > 0) {
				for(int i : result) {
					if(client.leftClickInRegion(beginX + 6, beginY + offset, beginX + 468, beginY + 30 + offset)) {
						client.outBuffer.putOpcode(134);
						client.outBuffer.putShort(seekable[i].id);
						client.panelSearchInput = null;
						return true;
					}
					offset += 31;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void update() {
		/* Initialization */
		int beginX = 8;
		int beginY = 0;
		if(client.uiRenderer.isResizableOrFull()) {
			beginX = client.windowWidth / 2 - 380;
			beginY = client.windowHeight / 2 - 250;
		}

		/* Main background */
		drawMain(beginX, beginY + 8, 500, 328, 0x000000, 0x63625e, 200);
		drawOver(beginX, beginY);
		drawClose(beginX, beginY);
		
		if(client.panelSearch) {
			fancyFont.drawLeftAlignedEffectString("Searching: " + client.panelSearchInput, beginX + 20, beginY + 32, 0xF3B13F, true);
		} else {
			fancyFont.drawLeftAlignedEffectString(type.name + " - lvl " + type.combatLevel, beginX + 20, beginY + 32, 0xF3B13F, true);
			Rasterizer2D.fillRoundedRectangle(beginX + 340, beginY + 12, 97, 25, 2, Config.def.panelStyle == 2 ? 0xF3B13F : 0x000000, 60);
			if(client.mouseInRegion(beginX + 340, beginY + 12, beginX + 437, beginY + 47)) {
				Rasterizer2D.fillRoundedRectangle(beginX + 340, beginY + 12, 97, 25, 2, 0xF3B13F, 20);
			}
			fancyFont.drawCenteredString("Suggest drop", beginX + 390, beginY + 30, 0xF3B13F);
			Rasterizer2D.fillRoundedRectangle(beginX + 280, beginY + 12, 57, 25, 2, Config.def.panelStyle == 2 ? 0xF3B13F : 0x000000, 60);
			if(client.mouseInRegion(beginX + 280, beginY + 12, beginX + 337, beginY + 37)) {
				Rasterizer2D.fillRoundedRectangle(beginX + 280, beginY + 12, 57, 25, 2, 0xF3B13F, 20);
			}
			fancyFont.drawCenteredString("Search", beginX + 309, beginY + 30, 0xF3B13F);
		}
		
		if(Config.def.panelStyle == 2)
			Rasterizer2D.setClip(beginX + 5, beginY + 50, beginX + 493, beginY + 330);
		else
			Rasterizer2D.setClip(beginX + 5, beginY + 42, beginX + 493, beginY + 330);
		int offset = -scrollPos + (Config.def.panelStyle == 2 ? 52 : 42);
		
		if(client.panelSearch) {
			if(result != null && result.length > 0) {
				for(int i : result) {
					NPCType npc = seekable[i];
						if(npc == null)
						continue;
					Rasterizer2D.fillRectangle(beginX + 6, beginY + offset, 300, 30, 0x0000, 100);
					Rasterizer2D.fillRectangle(beginX + 307, beginY + offset, 90, 30, 0x0000, 100);
					Rasterizer2D.fillRectangle(beginX + 398, beginY + offset, 70, 30, 0x0000, 100);
					if(client.mouseInRegion(beginX + 6, beginY + offset, beginX + 468, beginY + 30 + offset)) {
						Rasterizer2D.drawRectangle(beginX + 6, beginY + offset, 460, 31, 0xffffff);
					}
					plainFont.drawLeftAlignedString(npc.name, beginX + 15, beginY + offset + 17, 0xffffff);
					plainFont.drawLeftAlignedString("Combat: " +npc.combatLevel, beginX + 320, beginY + offset + 17, 0xffffff);
					smallFont.drawLeftAlignedString("Id: " +npc.id, beginX + 410, beginY + offset + 17, 0xffffff);
					offset += 31;
				}
			}
		} else {
			if(type == null)
				return;
			Rasterizer2D.drawVerticalLine(beginX + 126, beginY + 39, 290, 0x000000);
			cacheHover = -1;
			for(int c = 0; c < client.npcDrops.length; c++) {
				Drop drop = drops[client.npcDrops[c]];
				Rasterizer2D.fillRectangle(beginX + 6, beginY + offset, 120, 30, 0x0000, 100);
				plainFont.drawLeftAlignedString(drop.name, beginX + 15, beginY + offset + 17, 0xffffff);
				if(client.mouseInRegion(beginX + 6, beginY + offset, beginX + 126, beginY + offset + 30)) {
					Rasterizer2D.fillRectangle(beginX + 6, beginY + offset, 120, 30, 0x0000, 40);
					cacheHover = client.npcDrops[c];
				}
				offset += 31;
			}
			
			offset = -scrollPos + 52;
			if(cacheHover == -1) {
				for(int u = 0; u < client.npcDropsId.length; u++) {
					int id = client.npcDropsId[u];
					int min = client.npcDropsMin[u];
					int max = client.npcDropsMax[u];
					Chance ch = chances[client.npcDropsChance[u]];
					ObjectType obj = ObjectType.get(id);
					if(obj == null)
						continue;
					BitmapImage image = ObjectType.getIcon(id, max, 0);
					if(image != null)
						image.drawImage(beginX + 140, beginY + offset);
					//Rasterizer2D.fillRectangle(beginX + 6, beginY + offset, 120, 30, 0x0000, 100);
					plainFont.drawLeftAlignedString(obj.name, beginX + 190, beginY + offset + 14, 0xffffff);
					smallFont.drawLeftAlignedString(ch.name, beginX + 190, beginY + offset + 26, ch.color);
					if(min == 1 && max > 1) {
						smallFont.drawLeftAlignedString("Up to " + max, beginX + 340, beginY + offset + 20, 0xffffff);
					} else if(min != 1) {
						smallFont.drawLeftAlignedString("From " + min + " up to " + max, beginX + 340, beginY + offset + 20, 0xffffff);
					}
					Rasterizer2D.drawHorizontalLine(beginX + 127, beginY + offset + 38, 345, 0x000000);
					offset += 41;
				}
			} else {
				for(int u = 0; u < drops[cacheHover].items.length; u++) {
					int id = drops[cacheHover].items[u];
					int min = drops[cacheHover].min[u];
					int max = drops[cacheHover].max[u];
					Chance ch = chances[drops[cacheHover].chance[u]];
					ObjectType obj = ObjectType.get(id);
					if(obj == null)
						continue;
					BitmapImage image = ObjectType.getIcon(id, max, 0);
					if(image != null)
						image.drawImage(beginX + 140, beginY + offset);
					//Rasterizer2D.fillRectangle(beginX + 6, beginY + offset, 120, 30, 0x0000, 100);
					plainFont.drawLeftAlignedString(obj.name, beginX + 190, beginY + offset + 14, 0xffffff);
					smallFont.drawLeftAlignedString(ch.name, beginX + 190, beginY + offset + 26, ch.color);
					if(min == 1 && max > 1) {
						smallFont.drawLeftAlignedString("Up to " + max, beginX + 340, beginY + offset + 20, 0xffffff);
					} else if(min != 1) {
						smallFont.drawLeftAlignedString("From " + min + " up to " + max, beginX + 340, beginY + offset + 20, 0xffffff);
					}
					Rasterizer2D.drawHorizontalLine(beginX + 127, beginY + offset + 38, 345, 0x000000);
					offset += 41;
				}
			}
		}

		/* Scroll bar */
		Rasterizer2D.drawRectangle(476 + beginX, 55 + beginY, 12, 270, 0xffffff, 60);
		int height = 268;
		if(scrollMax > 0) {
			height = 275 * 268 / (scrollMax + 275);
		}
		int pos = 0;
		if(scrollPos != 0) {
			pos = scrollPos * 268 / (scrollMax + 275) + 1;
		}
		Rasterizer2D.fillRectangle(477 + beginX, 56 + pos + beginY, 10, height, 0x222222, 120);
		Rasterizer2D.removeClip();
		
	}
	
	@Override
	public void initialize() {
		client.npcSug = false;
		client.panelSearchInput = "";
		URL url;
		IntArrayList ids = new IntArrayList();
		try {
			url = new URL("http://edgeville.net/game/drops.txt");
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			br.lines().forEach(line -> {
				String[] l = line.split("-");
				for(String num : l) {
					if(num.length() == 0)
						continue;
					int id = Integer.parseInt(num);
					if(id > 0)
						ids.add(id);
				}
			});
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		seekable = new NPCType[ids.size()];
		for(int i = 0; i < ids.size(); i++) {
			int id = ids.getInt(i);
			if(id > 0 && id < NPCType.size())
				seekable[i] = NPCType.get(id);
		}
		ids.clear();
	}
	
	@Override
	public void reset() {
		client.npcSug = false;
		client.panelSearchInput = "";
		client.promptInput = "";
		client.promptInputTitle = "";
		client.messagePromptRaised = false;
		type = null;
	}
	
	@Override
	public int getId() {
		return 10;
	}
	
	@Override
	public boolean blockedMove() {
		return false;
	}
	
}