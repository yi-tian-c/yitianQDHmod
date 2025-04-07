package com.yi_tian;

import com.yi_tian.entity.ModEntities;
import com.yi_tian.event.ModEvents;
import com.yi_tian.event.ModKeyBindingHelper;
import com.yi_tian.item.ModItemGroups;
import com.yi_tian.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yi_tian.enchantment.ModEnchantments.registerModEnchantments;

public class YitianMod implements ModInitializer {
	public static final String MOD_ID = "yitianmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModEvents.registerModEvents();
		ModEntities.registerEntities(); // 注册自定义实体
		ModEntities.registerAttributes(); // 注册实体属性
		registerModEnchantments();
//		ModKeyBindingHelper.register();
		// 注册客户端tick事件
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
//	private void R_Key(){
//		ClientTickEvents.END_CLIENT_TICK.register(client -> {
//			// 检测按键是否被按下
//			if (ModKeyBindingHelper.exampleKey.wasPressed()) {
//				YitianMod.LOGGER.info("热键事件触发");
//				// 获取玩家实体
//				ClientPlayerEntity player = client.player;
//				if (player != null) {
//					// 获取玩家手中的物品
//					ItemStack handStack = player.getMainHandStack();
//					// 检查手中的物品是否是法杖
//					if (handStack.getItem() == ModItems.BONE_STAFF) {
//						player.getStackInHand(Hand.MAIN_HAND).setCount(0); // 清空
//						// 替换
////						player.getInventory().setStack(player.getInventory().selectedSlot, new ItemStack(ModItems.BONE_STAFF2));
//						player.getInventory().offerOrDrop(new ItemStack(ModItems.BONE_STAFF2));
//					}
//					else if (handStack.getItem() == ModItems.BONE_STAFF2) {
//						player.getStackInHand(Hand.MAIN_HAND).setCount(0); // 清空
//						// 替换
////						player.getInventory().setStack(player.getInventory().selectedSlot, new ItemStack(ModItems.BONE_STAFF3));
//						player.getInventory().offerOrDrop(new ItemStack(ModItems.BONE_STAFF3));
//					}
//					else if (handStack.getItem() == ModItems.BONE_STAFF3) {
//						player.getStackInHand(Hand.MAIN_HAND).setCount(0); // 清空
//						// 替换
////						player.getInventory().setStack(player.getInventory().selectedSlot, new ItemStack(ModItems.BONE_STAFF));
//						player.getInventory().offerOrDrop(new ItemStack(ModItems.BONE_STAFF));
//					}
//				}
//			}
//		});
//	}
}