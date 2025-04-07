package com.yi_tian.datagen;

import com.yi_tian.YitianMod;
import com.yi_tian.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends FabricRecipeProvider {

    public ModRecipesProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_SWORD, 1)
                .pattern("#")
                .pattern("#")
                .pattern(".")
                .input('#', Items.BONE_BLOCK)
                .input('.', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.CRYING_OBSIDIAN),
                        FabricRecipeProvider.conditionsFromItem(Items.CRYING_OBSIDIAN))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_sword"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_AXE, 1)
                .pattern("##")
                .pattern("#.")
                .pattern(" .")
                .input('#', Items.BONE_BLOCK)
                .input('.', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.CRYING_OBSIDIAN),
                        FabricRecipeProvider.conditionsFromItem(Items.CRYING_OBSIDIAN))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_axe"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_SHOVEL, 1)
                .pattern("#")
                .pattern(".")
                .pattern(".")
                .input('#', Items.BONE_BLOCK)
                .input('.', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.CRYING_OBSIDIAN),
                        FabricRecipeProvider.conditionsFromItem(Items.CRYING_OBSIDIAN))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_shovel"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_PICKAXE, 1)
                .pattern("###")
                .pattern(" . ")
                .pattern(" . ")
                .input('#', Items.BONE_BLOCK)
                .input('.', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.CRYING_OBSIDIAN),
                        FabricRecipeProvider.conditionsFromItem(Items.CRYING_OBSIDIAN))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_pickaxe"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_BOOTS, 1)
                .pattern("# #")
                .pattern("# #")
                .input('#', Items.BONE_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.CRYING_OBSIDIAN),
                        FabricRecipeProvider.conditionsFromItem(Items.CRYING_OBSIDIAN))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_boots"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_CHEST, 1)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .input('#', Items.BONE_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.CRYING_OBSIDIAN),
                        FabricRecipeProvider.conditionsFromItem(Items.CRYING_OBSIDIAN))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_chest"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_LEGGING, 1)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .input('#', Items.BONE_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.CRYING_OBSIDIAN),
                        FabricRecipeProvider.conditionsFromItem(Items.CRYING_OBSIDIAN))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_leggings"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_HELMET, 1)
                .pattern("###")
                .pattern("# #")
                .input('#', Items.BONE_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.CRYING_OBSIDIAN),
                        FabricRecipeProvider.conditionsFromItem(Items.CRYING_OBSIDIAN))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_helmet"));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_COMBINE)
                .input(ModItems.BONE_AXE,1)
                .input(ModItems.BONE_SHOVEL,1)
                .input(ModItems.BONE_SWORD,1)
                .input(ModItems.BONE_PICKAXE,1)
                .criterion("has_item", RecipeProvider.conditionsFromItem(Items.BONE_BLOCK))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_combine"));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_STAFF)
                .input(ModItems.BONE_AXE,1)
                .input(ModItems.BONE_SHOVEL,1)
                .input(ModItems.BONE_SWORD,1)
                .input(ModItems.BONE_PICKAXE,1)
                .input(ModItems.BONE_COMBINE,1)
                .input(ModItems.BONE_BOOTS,1)
                .input(ModItems.BONE_CHEST,1)
                .input(ModItems.BONE_LEGGING,1)
                .input(ModItems.BONE_HELMET,1)
                .criterion("has_item", RecipeProvider.conditionsFromItem(Items.BONE_BLOCK))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_staff"));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_STAFF2)
                .input(ModItems.BONE_STAFF,1)
                .input(Items.GLOWSTONE_DUST,1)
                .criterion("has_item", RecipeProvider.conditionsFromItem(Items.BONE_BLOCK))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_staff2"));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_STAFF3)
                .input(ModItems.BONE_STAFF2,1)
                .input(Items.LAPIS_LAZULI,1)
                .criterion("has_item", RecipeProvider.conditionsFromItem(Items.BONE_BLOCK))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_staff3"));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_STAFF4)
                .input(ModItems.BONE_STAFF3,1)
                .input(Items.AMETHYST_SHARD,1)
                .criterion("has_item", RecipeProvider.conditionsFromItem(Items.BONE_BLOCK))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_staff4"));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BONE_STAFF)
                .input(ModItems.BONE_STAFF4,1)
                .input(Items.EMERALD,1)
                .criterion("has_item", RecipeProvider.conditionsFromItem(Items.BONE_BLOCK))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "bone_staff1"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BATON, 1)
                .pattern("#")
                .pattern("#")
                .input('#', Items.IRON_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, Identifier.of(YitianMod.MOD_ID, "baton"));
    }
}
