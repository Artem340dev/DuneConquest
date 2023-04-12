package org.millida.duneconquest.threads;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public abstract class AbstractThread extends BukkitRunnable {
    int seconds;

    public void start(JavaPlugin plugin) {
        this.runTaskTimer(plugin, seconds * 20L, seconds * 20L);
    }

    public void stop() {
        this.cancel();
    }
}