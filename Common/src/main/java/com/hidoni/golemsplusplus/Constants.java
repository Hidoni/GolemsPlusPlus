package com.hidoni.golemsplusplus;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "golemsplusplus";
    public static final String MOD_NAME = "Golems++";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final ResourceLocation SNOW_GOLEM_HEAD_ITEMS_TAG_LOCATION = new ResourceLocation(Constants.MOD_ID, "snow_golem_head_items");
    public static final ResourceLocation VISION_BLOCKING_HEAD_ITEMS_TAG_LOCATION = new ResourceLocation(Constants.MOD_ID, "vision_blocking_head_items");
}