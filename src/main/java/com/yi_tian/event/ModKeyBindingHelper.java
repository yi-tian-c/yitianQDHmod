package com.yi_tian.event;

import com.yi_tian.item.ModItems;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindingHelper {
//    public static KeyBinding exampleKey;
//    public static KeyBinding rightKey;
//    public static void register(){
//        // 注册热键
//        exampleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
//                "key.yi_tian.yourkey", // 热键的翻译键
//                InputUtil.Type.KEYSYM, // 热键的类型
//                GLFW.GLFW_KEY_R, // 默认键值
//                "category.yi_tian.keys" // 热键分类的翻译键
//        ));
//    }
//public static final KeyBinding SWITCH_STAFF_KEY = new KeyBinding(
//        "key.yi_tian.switch_staff",
//        InputUtil.Type.KEYSYM,
//        GLFW.GLFW_KEY_R,
//        "category.yi_tian.keys"
//);
//
//    public static void register() {
//        KeyBindingHelper.registerKeyBinding(SWITCH_STAFF_KEY);
//    }
//
//    public static void initialize() {
//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            while (SWITCH_STAFF_KEY.wasPressed()) {
//                if (client.player != null) {
//                    ItemStack mainHand = client.player.getMainHandStack();
//                    if (isBoneStaff(mainHand.getItem())) {
//                        ClientPlayNetworking.send(NetworkHandler.SWITCH_STAFF_PACKET, PacketByteBufs.empty());
//                    }
//                }
//            }
//        });
//    }
//
//    private static boolean isBoneStaff(Item item) {
//        return item == ModItems.BONE_STAFF || item == ModItems.BONE_STAFF2 || item == ModItems.BONE_STAFF3;
//    }
}
