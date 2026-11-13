package ru.k1zik.ncsblock;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public final class NcsBlockMixinPlugin implements IMixinConfigPlugin {
	private static final Logger LOGGER = LoggerFactory.getLogger("ncs-block");
	private static final String NCS_ID = "noobs_container_searcher";
	private static final String SERVER_RULES = "com/noobfly/containersearcher/ServerRules.class";

	@Override
	public void onLoad(String mixinPackage) {
		ModContainer ncs = FabricLoader.getInstance().getModContainer(NCS_ID)
			.orElseThrow(() -> new IllegalStateException("[NCS Block] Noob's Container Searcher is not installed"));

		if (ncs.findPath(SERVER_RULES).isPresent()) {
			LOGGER.info("[NCS Block] plain NCS jar, classes are already on the classpath");
			return;
		}

		String implJar = implJarName();
		Optional<Path> embedded = ncs.findPath(implJar);
		if (embedded.isEmpty()) {
			throw new IllegalStateException("[NCS Block] " + implJar + " not found inside Noob's Container Searcher "
				+ ncs.getMetadata().getVersion().getFriendlyString() + ", the reroll tab can not be locked");
		}
		try {
			Path temp = Files.createTempFile("ncs-block-impl-", ".jar");
			temp.toFile().deleteOnExit();
			Files.copy(embedded.get(), temp, StandardCopyOption.REPLACE_EXISTING);
			FabricLauncherBase.getLauncher().addToClassPath(temp);
			LOGGER.info("[NCS Block] put {} from NCS on the classpath ({})", implJar, temp);
		} catch (IOException exception) {
			throw new IllegalStateException("[NCS Block] could not extract " + implJar + " from NCS", exception);
		}
	}

	private static String implJarName() {
		Properties properties = new Properties();
		try (InputStream in = NcsBlockMixinPlugin.class.getResourceAsStream("/ncs_block.properties")) {
			if (in == null) {
				throw new IllegalStateException("[NCS Block] ncs_block.properties is missing from the jar");
			}
			properties.load(in);
		} catch (IOException exception) {
			throw new IllegalStateException("[NCS Block] could not read ncs_block.properties", exception);
		}
		return "impl/" + properties.getProperty("ncs_impl") + ".jar";
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}
}
