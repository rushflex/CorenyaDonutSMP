package com.corenya.donutsmp.managers;

import com.corenya.donutsmp.CorenyaDonutSMP;
import com.corenya.donutsmp.models.PlayerData;
import com.corenya.donutsmp.models.ScoreboardStyle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ScoreboardPlayerStylePreferenceTest {

    @SuppressWarnings("unchecked")
    private static <T> T allocate(Class<T> type) throws Exception {
        Constructor<Object> objectConstructor = Object.class.getConstructor();
        Constructor<?> constructor = ReflectionFactory.getReflectionFactory()
                .newConstructorForSerialization(type, objectConstructor);
        return (T) constructor.newInstance();
    }

    private static void set(Class<?> owner, Object instance, String name, Object value) throws Exception {
        Field field = owner.getDeclaredField(name);
        field.setAccessible(true);
        field.set(instance, value);
    }

    @Test
    void defaultScoreboardStyleReadsFromConfiguredModeOrLegacy() throws Exception {
        CorenyaDonutSMP plugin = allocate(CorenyaDonutSMP.class);
        ConfigManager configManager = allocate(ConfigManager.class);
        set(CorenyaDonutSMP.class, plugin, "configManager", configManager);

        YamlConfiguration config = new YamlConfiguration();
        config.set("SCOREBOARD.MODE", "MODERN");
        set(ConfigManager.class, configManager, "scoreboard", config);

        ScoreboardManager manager = allocate(ScoreboardManager.class);
        set(ScoreboardManager.class, manager, "plugin", plugin);

        assertEquals(ScoreboardStyle.MODERN, manager.defaultStyle());

        config.set("SCOREBOARD.MODE", "LEGACY");
        assertEquals(ScoreboardStyle.LEGACY, manager.defaultStyle());

        config.set("SCOREBOARD.MODE", null);
        assertEquals(ScoreboardStyle.LEGACY, manager.defaultStyle(), "missing SCOREBOARD.MODE falls back to LEGACY");
    }

    @Test
    void playerPreferenceOverridesConfiguredScoreboardMode() throws Exception {
        CorenyaDonutSMP plugin = allocate(CorenyaDonutSMP.class);
        ConfigManager configManager = allocate(ConfigManager.class);
        PlayerDataManager playerDataManager = allocate(PlayerDataManager.class);

        set(CorenyaDonutSMP.class, plugin, "configManager", configManager);
        set(CorenyaDonutSMP.class, plugin, "playerDataManager", playerDataManager);

        YamlConfiguration config = new YamlConfiguration();
        config.set("SCOREBOARD.MODE", "LEGACY");
        set(ConfigManager.class, configManager, "scoreboard", config);

        UUID uuid = UUID.randomUUID();
        PlayerData data = new PlayerData(uuid, "TestUser");
        data.setScoreboardStyle(ScoreboardStyle.MODERN);

        // PlayerDataManager.get(player) calls get(player.getUniqueId()) or reads cache
        java.util.Map<UUID, PlayerData> cache = new java.util.HashMap<>();
        cache.put(uuid, data);
        set(PlayerDataManager.class, playerDataManager, "cache", cache);

        // Player.getUniqueId() via reflection or wrapper
        // PlayerDataManager.get(Player) calls cache.get(player.getUniqueId())
        // Let's create a proxy or mock dummy player
        Player proxyPlayer = (Player) java.lang.reflect.Proxy.newProxyInstance(
                Player.class.getClassLoader(),
                new Class<?>[]{Player.class},
                (proxy, method, args) -> {
                    if ("getUniqueId".equals(method.getName())) {
                        return uuid;
                    }
                    if ("getName".equals(method.getName())) {
                        return "TestUser";
                    }
                    return null;
                }
        );

        ScoreboardManager manager = allocate(ScoreboardManager.class);
        set(ScoreboardManager.class, manager, "plugin", plugin);

        Method styleOf = ScoreboardManager.class.getDeclaredMethod("styleOf", Player.class);
        styleOf.setAccessible(true);

        assertEquals(ScoreboardStyle.MODERN, styleOf.invoke(manager, proxyPlayer),
                "styleOf must respect the player's stored preference even when scoreboard.yml is set to LEGACY");

        data.setScoreboardStyle(ScoreboardStyle.LEGACY);
        assertEquals(ScoreboardStyle.LEGACY, styleOf.invoke(manager, proxyPlayer));
    }

    @Test
    void newPlayerDataDefaultsToLegacyStyle() {
        PlayerData data = new PlayerData(UUID.randomUUID(), "NewPlayer");
        assertEquals(ScoreboardStyle.LEGACY, data.getScoreboardStyle());
    }

    @Test
    void settingsMenuConfiguresScoreboardStyleAtSlot43() throws Exception {
        YamlConfiguration menus = new YamlConfiguration();
        menus.load(Path.of("src/main/resources/menus.yml").toFile());

        String path = "SETTINGS-MENU.BUTTONS.SCOREBOARD_STYLE";
        assertNotNull(menus.getConfigurationSection(path), "SCOREBOARD_STYLE button must exist in menus.yml");
        assertEquals(43, menus.getInt(path + ".SLOT"));
        assertEquals("PAINTING", menus.getString(path + ".MATERIAL"));
        assertEquals("&#6BF18DScoreboard Style", menus.getString(path + ".DISPLAY-NAME"));
    }
}
