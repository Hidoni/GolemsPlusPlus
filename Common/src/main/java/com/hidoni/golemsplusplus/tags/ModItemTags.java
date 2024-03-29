package com.hidoni.golemsplusplus.tags;

import com.hidoni.golemsplusplus.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> SNOW_GOLEM_HEAD_ITEMS_TAG = TagKey.create(Registries.ITEM, Constants.SNOW_GOLEM_HEAD_ITEMS_TAG_LOCATION);
    public static final TagKey<Item> VISION_BLOCKING_HEAD_ITEMS_TAG = TagKey.create(Registries.ITEM, Constants.VISION_BLOCKING_HEAD_ITEMS_TAG_LOCATION);
}
