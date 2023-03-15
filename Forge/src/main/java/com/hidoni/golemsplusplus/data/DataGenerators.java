package com.hidoni.golemsplusplus.data;

import com.hidoni.golemsplusplus.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators() {

    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ModBlockTagsProvider modBlockTagsProvider = new ModBlockTagsProvider(generator.getPackOutput(), lookupProvider, Constants.MOD_ID, fileHelper); // Dummy for Item Tag Provider
        generator.addProvider(false, modBlockTagsProvider);
        generator.addProvider(true, new ModItemTagsProvider(generator.getPackOutput(), lookupProvider, modBlockTagsProvider, Constants.MOD_ID, fileHelper));

    }
}

