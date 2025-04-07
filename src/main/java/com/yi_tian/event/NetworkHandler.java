package com.yi_tian.event;

import com.yi_tian.YitianMod;
import com.yi_tian.item.ModItems;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class NetworkHandler {
//    public static final Identifier SWITCH_STAFF_PACKET = Identifier.of(YitianMod.MOD_ID, "switch_staff");
//
//    public static void registerServerPackets() {
//        ServerPlayNetworking.registerGlobalReceiver(SWITCH_STAFF_PACKET, (server, player, handler, buf, sender) -> {
//            server.excute(() -> {
//                ItemStack mainHand = player.player().getMainHandStack();
//                Item currentItem = mainHand.getItem();
//                Item nextItem = getNextStaff(currentItem);
//
//                if (nextItem != null) {
//                    ItemStack newStack = new ItemStack(nextItem, mainHand.getCount());
//                    newStack.setNbt(mainHand.getNbt());
//                    player.setStackInHand(Hand.MAIN_HAND, newStack);
//                }
//            });
//        });
//    }
//
//    private static Item getNextStaff(Item current) {
//        if (current == ModItems.BONE_STAFF) return ModItems.BONE_STAFF2;
//        if (current == ModItems.BONE_STAFF2) return ModItems.BONE_STAFF3;
//        if (current == ModItems.BONE_STAFF3) return ModItems.BONE_STAFF;
//        return null;
//    }
}
