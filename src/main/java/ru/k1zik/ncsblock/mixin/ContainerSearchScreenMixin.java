package ru.k1zik.ncsblock.mixin;

import com.noobfly.containersearcher.ContainerSearchScreen;
import com.noobfly.containersearcher.compat.Gfx;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ContainerSearchScreen.class, remap = false)
public abstract class ContainerSearchScreenMixin {
	private static final int NCSBLOCK_HEADER_HEIGHT = 34;
	private static final int NCSBLOCK_LOCKED_TEXT = 0xFF4A4A52;
	private static final int NCSBLOCK_LOCKED_HOVER = 0xFF6A6A74;
	private static final int NCSBLOCK_LOCKED_BG = 0xFF121215;
	private static final int NCSBLOCK_LOCK_HOVER = 0xFFB8484A;
	private static final String[] NCSBLOCK_LOCK = {
		".XXX.",
		"X...X",
		"X...X",
		"XXXXX",
		"XX.XX",
		"XX.XX",
		"XXXXX"
	};

	@Shadow(remap = false)
	private int mx;

	@Shadow(remap = false)
	private int my;

	@Shadow(remap = false)
	private boolean hovered(int x, int y, int w, int h) {
		throw new AssertionError();
	}

	@Shadow(remap = false)
	private void hit(int x, int y, int w, int h, Runnable action) {
		throw new AssertionError();
	}

	@Shadow(remap = false)
	private static void icon(Gfx g, String[] bitmap, int x, int y, int color) {
		throw new AssertionError();
	}

	private static boolean ncsblock$isReroll(Object tab) {
		return tab instanceof Enum<?> e && e.name().equals("REROLL");
	}

	@Inject(method = "tabButton", at = @At("HEAD"), cancellable = true, remap = false)
	private void ncsblock$lockedTab(Gfx g, @Coerce Object target, Component label, int x, int top, boolean live,
	                                CallbackInfoReturnable<Integer> cir) {
		if (!ncsblock$isReroll(target)) {
			return;
		}
		Font font = Minecraft.getInstance().font;
		int tw = font.width(label) + 24 + 9;
		int h = NCSBLOCK_HEADER_HEIGHT - 4;
		boolean hover = hovered(x, top + 4, tw, h);
		if (hover) {
			g.fill(x, top + 10, x + tw, top + NCSBLOCK_HEADER_HEIGHT - 1, NCSBLOCK_LOCKED_BG);
		}
		int textX = x + 12;
		icon(g, NCSBLOCK_LOCK, textX, top + 15, hover ? NCSBLOCK_LOCK_HOVER : NCSBLOCK_LOCKED_TEXT);
		textX += 9;
		g.text(font, label, textX, top + 16, hover ? NCSBLOCK_LOCKED_HOVER : NCSBLOCK_LOCKED_TEXT);
		if (hover) {
			g.tooltip(font, List.of(Component.translatable("ncs_block.reroll_locked").withStyle(ChatFormatting.RED)), mx, my);
		}
		hit(x, top + 4, tw, h, () -> { });
		cir.setReturnValue(x + tw + 2);
	}

	@Inject(method = "switchTab", at = @At("HEAD"), cancellable = true, remap = false)
	private void ncsblock$noRerollTab(@Coerce Object next, CallbackInfo ci) {
		if (ncsblock$isReroll(next)) {
			ci.cancel();
		}
	}
}
