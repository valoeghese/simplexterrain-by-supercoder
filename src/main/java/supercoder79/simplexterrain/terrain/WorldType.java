package supercoder79.simplexterrain.terrain;

import com.google.common.collect.Maps;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSourceConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.level.LevelGeneratorType;
import supercoder79.simplexterrain.SimplexTerrain;
import supercoder79.simplexterrain.mixin.AccessorLevelGeneratorType;

import java.util.Map;

public class WorldType<T extends ChunkGenerator<?>> {
    public static final Map<LevelGeneratorType, WorldType<?>> LGT_TO_WT_MAP = Maps.newHashMap();
    public static final Map<String, WorldType<?>> STR_TO_WT_MAP = Maps.newHashMap();

    public final LevelGeneratorType generatorType;
    public final WorldTypeChunkGeneratorFactory<T> chunkGenSupplier;

    public WorldType(String name, WorldTypeChunkGeneratorFactory<T> chunkGenSupplier) {
        this.generatorType = AccessorLevelGeneratorType.create(9, name);
        generatorType.setCustomizable(false);
        this.chunkGenSupplier = chunkGenSupplier;

        if (this.generatorType == null) {
            throw new NullPointerException("An old world type has a null generator type: " + name + "!");
        }

        LGT_TO_WT_MAP.put(generatorType, this);
        STR_TO_WT_MAP.put(name, this);
    }

    public static final WorldType<WorldChunkGenerator> SIMPLEX = new WorldType<>("simplex", (world) -> {
        OverworldChunkGeneratorConfig chunkGenConfig = new OverworldChunkGeneratorConfig();

        VanillaLayeredBiomeSourceConfig config = new VanillaLayeredBiomeSourceConfig().setLevelProperties(world.getLevelProperties()).setGeneratorSettings(chunkGenConfig);
        return SimplexTerrain.WORLDGEN_TYPE.create(world, SimplexTerrain.WORLD_BIOME_SOURCE.applyConfig(config), chunkGenConfig);
    });

    public interface WorldTypeChunkGeneratorFactory<T extends ChunkGenerator<?>> {
        T create(World world);
    }
}
