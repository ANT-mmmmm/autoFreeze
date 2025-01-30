package com.outlook.antmmmmm;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.UUID;

public class autoFreeze extends JavaPlugin {

    //获取在线人数
    public int playerCount = 0;

    //taskId
    public int logPlayerCount=0;

    // 默认的日志记录在线人数的时间间隔
    // Default interval for logging the number of online players
    public int logPlayerCountInterval = 0;

    // 控制台实例
    public CommandSender console = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {
        // 初始化自定义配置文件
        // Initialize the custom configuration file
        File settingsFile = new File(getDataFolder(), "settings.yml");

        // 如果文件不存在，从插件的resources目录中复制默认文件
        // If the file does not exist, copy the default file from the plugin's resources directory
        if (!settingsFile.exists()) {
            settingsFile.getParentFile().mkdirs(); // 确保目录存在
            saveResource("settings.yml", false); // 从插件的resources目录中复制文件
        }

        // 加载自定义配置文件
        // Load the custom configuration file
        FileConfiguration settingsConfig = YamlConfiguration.loadConfiguration(settingsFile);

        // 读取配置项
        // Read configuration items
        logPlayerCountInterval = settingsConfig.getInt("logPlayerCountInterval", 0); // 默认值为0

        // 注册监听器
        getServer().getPluginManager().registerEvents(new PlayerEventsListener(this), this);

        if (logPlayerCountInterval!=0){
            logPlayerCount=Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run(){
                    getLogger().info(playerCount+" players online.");
                }
            },0L,logPlayerCountInterval);        }


        Bukkit.dispatchCommand(console,"tick freeze");
    }



    @Override
    public void onDisable() {
        // 插件禁用时执行的代码
        // Code to be executed when the plugin is disabled
        getLogger().info("AutoFreeze has been disabled!"); // 在日志中记录插件已禁用
    }

}

// 监听器类
class PlayerEventsListener implements Listener {

    private final autoFreeze plugin;

    // 在线人数
    public PlayerEventsListener(autoFreeze plugin){
        this.plugin=plugin;
    }



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.playerCount=Bukkit.getOnlinePlayers().size();
        if (plugin.playerCount==1){
            Bukkit.dispatchCommand(plugin.console, "tick unfreeze");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.playerCount=Bukkit.getOnlinePlayers().size();
        if (plugin.playerCount==1){
            Bukkit.dispatchCommand(plugin.console, "tick freeze");
        }
    }
}