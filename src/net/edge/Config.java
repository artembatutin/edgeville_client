package net.edge;

import net.edge.sign.SignLink;
import net.edge.util.FileToolkit;
import net.edge.util.io.Buffer;

import java.io.*;

public class Config {
	
	public static Config def = new Config();

	/**
	 * Selected Menus
	 * OLD: 1
	 * OLD ALPHA: 2
	 * MIDDLE: 3
	 * NEW: 4
	 * NEW ALPHA: 5
	 * CUSTOM: 6
	 */
	private int SELECTED_MENU = 4;

	/**
	 * Hitsplats
	 * OLD-317: 0
	 * NEW-562: 1
	 * NEWEST-660: 2
	 */
	private int HITSPLATS = 2;

	/**
	 * Hitbars
	 * OLD-317: 0
	 * NEW-562: 1
	 */
	private int HITBARS = 1;

	/**
	 * The 10x hitpoints toggle.
	 */
	private boolean TEN_X_HITS = true;

	/**
	 * The game-frame revision id.
	 */
	private int GAME_FRAME = 562;

	/*
	 * Debug configurations
	 */
	private boolean DEBUG_DATA = false;
	private boolean FPS_ON = false;
	private boolean DEBUG_INDEXES = false;

	/*
	 * Detail configurations
	 */
	private boolean LOW_MEM = false;
	private boolean GROUND_DECORATION = true;
	private boolean GROUND_MATERIALS = false;
	private boolean SMOOTH_FOG = false;
	private boolean TWEENING = false;
	private boolean RETAIN_MODEL_PRECISION = true;
	private boolean MAP_ANTIALIASING = true;

	/*
	 * View toggle configurations
	 */
	private boolean DRAW_ORBS = true;
	private boolean DRAW_SKILL_ORBS = false;

	/*
	 * Style configurations
	 */
	private boolean DISPLAY_NAMES = false;
	private int SPLIT_PRIVATE_CHAT_COLOR = 0xffff;
	private boolean CHARACTER_PREVIEW = true;
	/** Roofs being off all the time. */
	private boolean ROOF_OFF = true;

	/**
	 * Saves the configurations.
	 */
	public void save() {
		Buffer data = new Buffer(new byte[19]);
		//17 bytes and 1 short.
		data.putShort(GAME_FRAME);
		data.putByte(SELECTED_MENU);
		data.putByte(HITSPLATS);
		data.putByte(HITBARS);
		data.putByte(TEN_X_HITS ? 1 : 0);
		data.putByte(FPS_ON ? 1 : 0);
		data.putByte(LOW_MEM ? 1 : 0);
		data.putByte(GROUND_DECORATION ? 1 : 0);
		data.putByte(GROUND_MATERIALS ? 1 : 0);
		data.putByte(SMOOTH_FOG ? 1 : 0);
		data.putByte(TWEENING ? 1 : 0);
		data.putByte(RETAIN_MODEL_PRECISION ? 1 : 0);
		data.putByte(MAP_ANTIALIASING ? 1 : 0);
		data.putByte(DRAW_ORBS ? 1 : 0);
		data.putByte(DRAW_SKILL_ORBS ? 1 : 0);
		data.putByte(DISPLAY_NAMES ? 1 : 0);
		data.putByte(CHARACTER_PREVIEW ? 1 : 0);
		data.putByte(ROOF_OFF ? 1 : 0);
		FileToolkit.writeFile(SignLink.getCacheDir() + "settings", data.data);
	}

	/**
	 * Loads the configurations on startup.
	 */
	public void load() {
		byte[] data = FileToolkit.readFile(SignLink.getCacheDir() + "settings");
		if(data != null && data.length > 0) {
			Buffer buf = new Buffer(data);
			GAME_FRAME = buf.getUShort();
			System.out.println(GAME_FRAME);
			SELECTED_MENU = buf.getUByte();
			HITSPLATS = buf.getUByte();
			HITBARS = buf.getUByte();
			TEN_X_HITS = buf.getBoolean();
			FPS_ON = buf.getBoolean();
			LOW_MEM = buf.getBoolean();
			GROUND_DECORATION = buf.getBoolean();
			GROUND_MATERIALS = buf.getBoolean();
			SMOOTH_FOG = buf.getBoolean();
			TWEENING = buf.getBoolean();
			RETAIN_MODEL_PRECISION = buf.getBoolean();
			MAP_ANTIALIASING = buf.getBoolean();
			DRAW_ORBS = buf.getBoolean();
			DRAW_SKILL_ORBS = buf.getBoolean();
			DISPLAY_NAMES = buf.getBoolean();
			CHARACTER_PREVIEW = buf.getBoolean();
			ROOF_OFF = buf.getBoolean();
		}
	}
	
	public int getSELECTED_MENU() {
		return SELECTED_MENU;
	}
	
	public void setSELECTED_MENU(int SELECTED_MENU) {
		this.SELECTED_MENU = SELECTED_MENU;
		save();
	}
	
	public int getHITSPLATS() {
		return HITSPLATS;
	}
	
	public void setHITSPLATS(int HITSPLATS) {
		this.HITSPLATS = HITSPLATS;
		save();
	}
	
	public int getHITBARS() {
		return HITBARS;
	}
	
	public void setHITBARS(int HITBARS) {
		this.HITBARS = HITBARS;
		save();
	}
	
	public boolean isTEN_X_HITS() {
		return TEN_X_HITS;
	}
	
	public void setTEN_X_HITS(boolean TEN_X_HITS) {
		this.TEN_X_HITS = TEN_X_HITS;
		save();
	}
	
	public int getGAME_FRAME() {
		return GAME_FRAME;
	}
	
	public void setGAME_FRAME(int GAME_FRAME) {
		this.GAME_FRAME = GAME_FRAME;
		save();
	}
	
	public boolean isDEBUG_DATA() {
		return DEBUG_DATA;
	}
	
	public void setDEBUG_DATA(boolean DEBUG_DATA) {
		this.DEBUG_DATA = DEBUG_DATA;
	}
	
	public boolean isFPS_ON() {
		return FPS_ON;
	}
	
	public void setFPS_ON(boolean FPS_ON) {
		this.FPS_ON = FPS_ON;
		save();
	}
	
	public boolean isDEBUG_INDEXES() {
		return DEBUG_INDEXES;
	}
	
	public void setDEBUG_INDEXES(boolean DEBUG_INDEXES) {
		this.DEBUG_INDEXES = DEBUG_INDEXES;
	}
	
	public boolean isLOW_MEM() {
		return LOW_MEM;
	}
	
	public void setLOW_MEM(boolean LOW_MEM) {
		this.LOW_MEM = LOW_MEM;
		save();
	}
	
	public boolean isGROUND_DECORATION() {
		return GROUND_DECORATION;
	}
	
	public void setGROUND_DECORATION(boolean GROUND_DECORATION) {
		this.GROUND_DECORATION = GROUND_DECORATION;
		save();
	}
	
	public boolean isGROUND_MATERIALS() {
		return GROUND_MATERIALS;
	}
	
	public void setGROUND_MATERIALS(boolean GROUND_MATERIALS) {
		this.GROUND_MATERIALS = GROUND_MATERIALS;
		save();
	}
	
	public boolean isSMOOTH_FOG() {
		return SMOOTH_FOG;
	}
	
	public void setSMOOTH_FOG(boolean SMOOTH_FOG) {
		this.SMOOTH_FOG = SMOOTH_FOG;
		save();
	}
	
	public boolean isTWEENING() {
		return TWEENING;
	}
	
	public void setTWEENING(boolean TWEENING) {
		this.TWEENING = TWEENING;
		save();
	}
	
	public boolean isRETAIN_MODEL_PRECISION() {
		return RETAIN_MODEL_PRECISION;
	}
	
	public void setRETAIN_MODEL_PRECISION(boolean RETAIN_MODEL_PRECISION) {
		this.RETAIN_MODEL_PRECISION = RETAIN_MODEL_PRECISION;
		save();
	}
	
	public boolean isMAP_ANTIALIASING() {
		return MAP_ANTIALIASING;
	}
	
	public void setMAP_ANTIALIASING(boolean MAP_ANTIALIASING) {
		this.MAP_ANTIALIASING = MAP_ANTIALIASING;
		save();
	}
	
	public boolean isDRAW_ORBS() {
		return DRAW_ORBS;
	}
	
	public void setDRAW_ORBS(boolean DRAW_ORBS) {
		this.DRAW_ORBS = DRAW_ORBS;
		save();
	}
	
	public boolean isDRAW_SKILL_ORBS() {
		return DRAW_SKILL_ORBS;
	}
	
	public void setDRAW_SKILL_ORBS(boolean DRAW_SKILL_ORBS) {
		this.DRAW_SKILL_ORBS = DRAW_SKILL_ORBS;
		save();
	}
	
	public boolean isDISPLAY_NAMES() {
		return DISPLAY_NAMES;
	}
	
	public void setDISPLAY_NAMES(boolean DISPLAY_NAMES) {
		this.DISPLAY_NAMES = DISPLAY_NAMES;
		save();
	}
	
	public int getSPLIT_PRIVATE_CHAT_COLOR() {
		return SPLIT_PRIVATE_CHAT_COLOR;
	}
	
	public void setSPLIT_PRIVATE_CHAT_COLOR(int SPLIT_PRIVATE_CHAT_COLOR) {
		this.SPLIT_PRIVATE_CHAT_COLOR = SPLIT_PRIVATE_CHAT_COLOR;
	}
	
	public boolean isCHARACTER_PREVIEW() {
		return CHARACTER_PREVIEW;
	}
	
	public void setCHARACTER_PREVIEW(boolean CHARACTER_PREVIEW) {
		this.CHARACTER_PREVIEW = CHARACTER_PREVIEW;
	}
	
	public boolean isROOF_OFF() {
		return ROOF_OFF;
	}
	
	public void setROOF_OFF(boolean ROOF_OFF) {
		this.ROOF_OFF = ROOF_OFF;
		save();
	}
	
}
