package com.yi_tian;

import com.yi_tian.datagen.ModBlockTagsProvider;
import com.yi_tian.datagen.ModENUSLanProvider;
import com.yi_tian.datagen.ModModelsProvider;
import com.yi_tian.datagen.ModRecipesProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class YitianModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModENUSLanProvider::new);
		pack.addProvider(ModModelsProvider::new);
		pack.addProvider(ModRecipesProvider::new);
		pack.addProvider(ModBlockTagsProvider::new);
	}
}
