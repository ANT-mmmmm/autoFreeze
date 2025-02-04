
### [中文](#ch-section)  [English](#en-section)
# 自动冻结
<a id="ch-section"></a>

## 功能介绍

### 基础功能
1. **自动暂停和恢复游戏**  
   当服务器中无人游玩时，自动暂停游戏，节省服务器资源和电费。当有玩家上线时，自动恢复游戏。

2. **可配置的日志记录**  
   可通过`settings.yml`文件配置“在日志中和控制台记录在线人数的时间间隔”。

3. **可配置的玩家检测频率**  
   可通过`settings.yml`文件配置“检测玩家人数并冻结/解冻游戏的频率”。


## 配置文件
```yaml
# 配置文件 autoFreeze v1.1
# 设为0即禁用
# 单位：游戏刻 tick (1/20s)

# 在日志中和控制台记录在线人数的时间间隔
logPlayerCountInterval: 0
```

## 同作用 其他加载器的类似模组
### Fabric: Tick Stasis
[Modrinth](https://modrinth.com/mod/tick-stasis "链接")

---
<a id="en-section"></a>
# AutoFreeze


## Features

### Basic Features
1. **Automatic Game Pausing and Resuming**  
   Automatically pause the game when no players are online to save server resources and electricity. Resume the game automatically when a player joins.

2. **Configurable Logging**  
   You can configure the "interval for logging the number of online players in the logs and console" through the `settings.yml` file.

3. **Configurable Player Detection Frequency**  
   You can configure the "frequency for checking player counts and freezing/unfreezing the game" through the `settings.yml` file.


## Configuration File
```yaml
# Configuration File
# Set to 0 to disable
# Unit: Game tick (1/20s)

# Interval for logging the number of online players in the console and logs
logPlayerCountInterval: 0
```
## Similar Modules for Other Loaders
### Fabric: Tick Stasis
[Modrinth](https://modrinth.com/mod/tick-stasis "Link") 