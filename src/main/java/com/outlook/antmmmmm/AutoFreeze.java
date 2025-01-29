package com.outlook.antmmmmm;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public class AutoFreeze extends JavaPlugin {

    // 默认的日志记录在线人数的时间间隔
    // Default interval for logging the number of online players
    private int logPlayerCountInterval = 0;

    // 默认的检测玩家人数并冻结/解冻游戏的频率
    // Default frequency for monitoring player counts to freeze/unfreeze the game
    private int playerCountCheckInterval = 10;

    // 静态变量，用于记录计数
    // Static variable for counting
    private static int times = 0;

    // 静态变量，用于记录游戏是否冻结
    // Static variable to record whether the game is frozen
    private static boolean frozen = false;

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
        playerCountCheckInterval = settingsConfig.getInt("playerCountCheckInterval", 10); // 默认值为10

        // 启动定时任务
        // Start the scheduled task
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                // 获取当前在线玩家人数
                // Get the current number of online players
                int playerCount = Bukkit.getOnlinePlayers().size();

                // 如果logPlayerCountInterval不为0，则记录在线人数
                // If logPlayerCountInterval is not 0, log the number of online players
                if (logPlayerCountInterval > 0 && times == logPlayerCountInterval / playerCountCheckInterval) {
                    getLogger().info("在线人数: " + playerCount); // 在日志中记录在线人数
                    getLogger().info("Number of online players: " + playerCount); // Log the number of online players
                    times = 0; // 重置计数
                }

                // 检查是否需要冻结或解冻游戏
                // Check if the game needs to be frozen or unfrozen
                if (playerCount == 0 && !frozen) {
                    // 如果没有玩家且游戏未冻结，则冻结游戏
                    // If there are no players and the game is not frozen, freeze the game
                    frozen = true;
                    getLogger().info("游戏已冻结"); // 在日志中记录游戏已冻结
                    getLogger().info("Game has been frozen"); // Log that the game has been frozen
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tick freeze"); // 执行冻结命令
                } else if (playerCount > 0 && frozen) {
                    // 如果有玩家且游戏已冻结，则解冻游戏
                    // If there are players and the game is frozen, unfreeze the game
                    frozen = false;
                    getLogger().info("游戏已解冻"); // 在日志中记录游戏已解冻
                    getLogger().info("Game has been unfrozen"); // Log that the game has been unfrozen
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tick unfreeze"); // 执行解冻命令
                }

                // 增加计数
                // Increment the counter
                times++;
            }
        }, 0L, playerCountCheckInterval); // 0L表示立即开始，playerCountCheckInterval表示每playerCountCheckInterval个tick执行一次
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 检查命令发送者是否为玩家
        // Check if the command sender is a player
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String playerName = player.getName();
            UUID playerUUID = player.getUniqueId();
            getLogger().info(playerName + " [uuid: " + playerUUID.toString() + "] issued server command: /freeze.settings"); // 在日志中记录玩家执行命令
            getLogger().info(playerName + " [uuid: " + playerUUID.toString() + "] issued server command: /freeze.settings"); // Log the player executing the command
        } else {
            getLogger().info("CONSOLE executed the command: /freeze.settings"); // 在日志中记录控制台执行命令
            getLogger().info("CONSOLE executed the command: /freeze.settings"); // Log the console executing the command
        }
        return true;
    }

    @Override
    public void onDisable() {
        // 插件禁用时执行的代码
        // Code to be executed when the plugin is disabled
        getLogger().info("AutoFreeze has been disabled!"); // 在日志中记录插件已禁用
        getLogger().info("AutoFreeze has been disabled!"); // Log that the plugin has been disabled
    }

    // 获取当前在线玩家人数的方法
    // Method to get the current number of online players
    public int getOnlinePlayerCount() {
        return Bukkit.getOnlinePlayers().size();
    }
}