package supercoder79.simplexterrain.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import supercoder79.simplexterrain.terrain.WorldType;
import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelGeneratorType.class)
public class MixinLevelGeneratorType {
	@Shadow @Final private int id;

	@Inject(at = @At("HEAD"), method = "getTypeFromName", cancellable = true)
	private static void getTypeFromName(String name, CallbackInfoReturnable<LevelGeneratorType> info) {
		if (WorldType.STR_TO_WT_MAP.containsKey(name)) {
			info.setReturnValue(WorldType.STR_TO_WT_MAP.get(name).generatorType);
		}
	}

	//dirty hack but i can't be assed to figure out why the lang file won't work
	@Inject(method = "getTranslationKey", at = @At("HEAD"), cancellable = true)
	void getTranslationKey(CallbackInfoReturnable<String> cir) {
		if (this.id == 9) cir.setReturnValue("Simplex Noise");
	}
}