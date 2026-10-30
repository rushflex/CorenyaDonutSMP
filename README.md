<p align="center">
  <img src="images/mainn.png" alt="CorenyaDonutSMP" width="720">
</p>

<h1 align="center">CorenyaDonutSMP</h1>

<p align="center">
  Free Paper, Purpur, Pufferfish, Spigot, and Folia plugin for DonutSMP-style Minecraft servers.
  Economy, PvP, marketplace, staff tools, menus, and network utilities in one production-focused plugin.
</p>

<p align="center">
  <img alt="Java 21" src="https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=openjdk&logoColor=white">
  <img alt="Platform" src="https://img.shields.io/badge/Platform-Paper%20%7C%20Purpur%20%7C%20Pufferfish%20%7C%20Spigot%20%7C%20Folia-2ea44f?style=for-the-badge">
  <img alt="Free" src="https://img.shields.io/badge/Distribution-Free-green?style=for-the-badge">
  <img alt="License" src="https://img.shields.io/badge/License-Proprietary-red?style=for-the-badge">
</p>

## Overview

CorenyaDonutSMP is a complete Paper Minecraft server plugin built for DonutSMP-style survival networks. It combines player economy, teams, homes, warps, random teleport, shop, sell, worth, crates, shards, PvP systems, staff utilities, network communication, and GUI-driven workflows into one plugin.

The goal is to reduce the number of separate plugins required for a modern SMP server while keeping configuration, player data, permissions, placeholders, and staff operations consistent across the entire server experience.

## Documentation

This README is the quick reference. The full documentation set lives in [`docs/wiki/`](docs/wiki):

| Page | Contents |
| --- | --- |
| [Home](docs/wiki/Home.md) | Documentation index and technical quick facts |
| [Installation & Setup](docs/wiki/Installation-and-Setup.md) | Server engine setup, SQLite/MySQL/MongoDB storage, and Redis networking |
| [Commands & Permissions](docs/wiki/Commands-and-Permissions.md) | Full command syntax, aliases, and permission nodes |
| [Configuration Reference](docs/wiki/Configuration-Reference.md) | In-depth setup guidance for the larger config files, alongside the per-file `Config-*.yml.md` guides |
| [Economy & Marketplaces](docs/wiki/Economy-and-Marketplaces.md) | Money, shards, shop, sell, Auction House, and Orders |
| [Duels & FFA](docs/wiki/Duels-and-FFA.md) | Duel arenas, queues, rollbacks, and instanced FFA |
| [Ranked PvP Arena](docs/wiki/Ranked-PvP-Arena.md) | Persistent arena, Elo rating, kit editor, and schematic reset |
| [Crates & Spawners](docs/wiki/Crates-and-Spawners.md) | Crate definitions, keys, and Donut-style spawners |
| [Cuboids & Portals](docs/wiki/Cuboids-and-Portals.md) | Region selection, feature zone binding, and portal triggers |
| [Staff & Security](docs/wiki/Staff-and-Security.md) | Staff mode, punishments, detection tools, and moderation |
| [Placeholders & Integrations](docs/wiki/Placeholders-and-Integrations.md) | PlaceholderAPI expansions and third-party plugin support |
| [Localization & Messages](docs/wiki/Localization-and-Messages.md) | 8-language translations, language selection, and message keys |
| [FAQ](docs/wiki/FAQ.md) | Common questions and troubleshooting |
| [Dialog API & older clients](docs/wiki/Dialog-API-and-Older-Clients.md) | Java 1.21.5-and-older clients and Bedrock (Geyser) limits; Quick Buy + Floodgate |

## Highlights

| Area | Included systems |
| --- | --- |
| Platforms | Separate Paper/Purpur/Pufferfish/Spigot and Folia builds with compatibility checks against the latest published APIs |
| Economy | Money, shards, player payments, Vault provider, shop, sell workflows, sell multipliers, worth browser, and sell history |
| Marketplaces | Auction House, Orders board, category filters, claims, delivery, and search |
| Player systems | Teams, friends/follows, homes, warps, private messages, ignore lists, profiles, settings, and custom Ender Chests |
| Progression | Stats, playtime, leaderboards, scoreboards, tablists, bounties, and PlaceholderAPI expansions |
| Teleportation | Spawn, AFK areas, TPA, RTP, portals, cuboid triggers, teleport areas, and safe-location checks |
| PvP | Duels, private invites, map queues, FFA instances, arena rollback, fast crystals, and combat handling |
| Custom content | Crates, virtual keys, Donut-style spawners, amethyst tools, enchantment GUI, filters, and configurable menus |
| Staff and moderation | Staff mode, freeze, vanish, hide/disguise, invsee, ecsee, punishments, alts, reports, helpop, and anvil moderation |
| Detection tools | Spawn-stash bait, fake-player bait, alerts, bypass permissions, and crash protection |
| Network | Redis staff chat and alerts, server-status menus, maintenance routing, Discord webhooks, and Lunar/Apollo support |
| Operations | Automatic configuration sync and backups, feature toggles, setup tools, optimization controls, stats wipe, and guarded server wipe |
| Localization | English, Spanish, Indonesian, Portuguese, German, French, Russian, and Simplified Chinese language packs |

## Screenshots

Gameplay clips:

|   |   |
| :---: | :---: |
| <img src="images/gif1.gif" alt="CorenyaDonutSMP gameplay clip 1" width="420"> | <img src="images/gif2.gif" alt="CorenyaDonutSMP gameplay clip 2" width="420"> |
| <img src="images/gif3.gif" alt="CorenyaDonutSMP gameplay clip 3" width="420"> | <img src="images/gif4.gif" alt="CorenyaDonutSMP gameplay clip 4" width="420"> |
| <img src="images/gif5.gif" alt="CorenyaDonutSMP gameplay clip 5" width="420"> | <img src="images/gif6.gif" alt="CorenyaDonutSMP gameplay clip 6" width="420"> |

## Requirements

| Requirement | Notes |
| --- | --- |
| Plugin version | `1.0` |
| Java | Bytecode targets Java 21. The supported Minecraft versions (26.1.2 and newer) require a Java 25 server runtime. |
| Paper / Purpur / Pufferfish / Spigot | Minecraft `26.1.2` through `26.3` |
| Folia | Minecraft `26.1.2` through `26.3` |
| Java client | **1.21.6 or newer.** 1.21.5 and older cannot render Dialog API menus. Block those versions on the proxy or ViaVersion. See [Dialog API & older clients](docs/wiki/Dialog-API-and-Older-Clients.md). |
| Bedrock (Geyser) | **Dialog API menus do not work** (`/menu`, `/ah`, `/pay`, and similar). `/shop` opens a chest grid; pinning items needs **Floodgate** (Geyser alone is not enough). See [Dialog API & older clients](docs/wiki/Dialog-API-and-Older-Clients.md#bedrock-players-geyser--floodgate). |
| Hard dependencies | PlaceholderAPI and ProtocolLib (declared under `depend` in `plugin.yml`; the plugin will not load without them) |
| Default storage | SQLite, bundled through the shaded JDBC driver |
| Alternative storage | MySQL or MongoDB |
| Optional network layer | Redis for cross-server staff chat, alerts, maintenance, reports, helpop, and server status |
| Build environment | Maven available as `mvn`, internet access, and a JDK 21 or newer toolchain (CI builds on JDK 25) |

Required plugins (the server will not enable CorenyaDonutSMP without them):

- PlaceholderAPI
- ProtocolLib

Optional integrations:

- LuckPerms
- Vault
- Apollo
- SkinsRestorer
- Multiverse-Core
- floodgate
- Simple Voice Chat (`voicechat`)

The plugin starts without the optional integrations. Their related permission, economy, client, skin, world, voice chat, and Bedrock features activate only when the corresponding plugin is installed.

> [!WARNING]
> **Minecraft 1.21.5 and older clients cannot use this plugin's menus.** CorenyaDonutSMP uses Mojang's native [Dialog API](https://minecraft.wiki/w/Dialog), added in Java Edition 1.21.6. Those screens are drawn by the Minecraft client itself. A 1.21.5 (or older) client — typically joining through ViaVersion or ViaBackwards — shows a broken, unusable layout. Commands such as `/ah`, `/menu`, `/homes`, `/pay`, and `/settings` will not present a working screen.
>
> This is not a plugin bug and cannot be fixed here. Other plugins that use Dialog API behave the same way. The chest-menu fallback in `menus.yml` only applies when the **server** lacks Paper's Dialog API (Spigot, or Paper older than 1.21.6). On the Paper versions this plugin supports, dialogs are sent to every player; there is no per-client switch to chest GUIs.
>
> **Require Java clients 1.21.6 or newer.** Block 1.21.5 and below on Velocity, BungeeCord, or ViaVersion. Full write-up: [Dialog API & older clients](docs/wiki/Dialog-API-and-Older-Clients.md).
>
> **Bedrock players (Geyser)** cannot use Java Dialog API screens. That is expected, not a plugin bug. Most hub and economy menus (`/menu`, `/ah`, `/pay`, `/homes` dialog flow, and similar) are Java-only. **`/shop` (Quick Buy)** uses a chest menu Geyser can show; to pin items from Bedrock install **Floodgate** alongside Geyser so the plugin can send Bedrock forms (`QUICK-BUY.BEDROCK` in `shop.yml`). Without Floodgate, pinning falls back to a chest catalogue. Details: [Bedrock players (Geyser / Floodgate)](docs/wiki/Dialog-API-and-Older-Clients.md#bedrock-players-geyser--floodgate).

## Building

Build the project using standard Maven or the build script:

```bat
build.bat
```

Or run Maven directly:

```bash
mvn clean package
```

The build compiles the codebase against the target API and packages a single unified JAR that automatically detects and adapts to Paper, Purpur, Pufferfish, Spigot, or Folia at runtime.

Generated artifact is saved to the `target/` directory:

- `CorenyaDonutSMP-1.0.jar` (shaded JAR)

## Installation

1. Stop the Minecraft server.
2. Place the plugin jar into the server `plugins/` directory.
3. Start the server once so the default configuration files are generated.
4. Configure storage in `database.yml`.
5. Review the core gameplay files such as `config.yml`, `menus.yml`, `shop.yml`, `worth.yml`, `rtp.yml`, and `languages/<locale>.yml`.
6. Restart the server after first setup.

For production networks, MySQL plus Redis is recommended. For a single-server setup, SQLite is usually enough.

> [!WARNING]
> Do not share private customer files, database credentials, Discord tokens, Redis passwords, or other sensitive server data.

## Configuration

| File | Purpose |
| --- | --- |
| `config.yml` | Language selection, feature toggles, locations, portals, chat, AFK, cuboid binds, combat, crystals, shards, tablist, optimization, and general gameplay |
| `languages/*.yml` | Player-facing command, gameplay, moderation, economy, teleport, menu, and system text |
| `death-messages.yml` | Death-message rules and message templates |
| `menus.yml` | Shared GUI layouts for teams, homes, profiles, settings, leaderboards, shops, staff tools, rules, servers, and other menus |
| `dialog.yml` | 1.21.6+ dialog screens: the main menu grid, settings categories, pay, stats, leaderboards, homes, friends, teleport, the RTP queue prompt, and the generated ESC-screen datapack |
| `scoreboard.yml` | Modern and legacy sidebar layouts, title, lines, refresh behavior, and display rules |
| `shop.yml` | Money and shard shop categories, items, prices, permissions, currencies, and command rewards |
| `sounds.yml` | Sound effects for menus, commands, teleportation, shops, shards, boosters, and custom systems |
| `pvp.yml` | Ranked PvP arena, Elo rating, kits, boundary, broadcasts, and schematic reset |
| `rtp.yml` | Random teleport worlds, radius, cooldowns, safety checks, denied worlds, messages, and GUI |
| `worth.yml` | Sell values, worth display, container handling, browser categories, and blocked items |
| `amethyst-tools.yml` | Amethyst tool types, durations, permissions, effects, items, actions, and messages |
| `ender-chest.yml` | Custom Ender Chest size, behavior, ecsee access, layout, and messages |
| `invsee.yml` | Inventory inspection behavior, layout, permissions, and messages |
| `freeze.yml` | Freeze behavior, permissions, alerts, inventory handling, and messages |
| `auction-house.yml` | Listing limits, pricing, claims, restrictions, sorting, categories, and Auction House GUI |
| `orders.yml` | Order limits, pricing, delivery, matching, filters, sorting, Bedrock input, network behavior, and GUI |
| `enchantments.yml` | Enchantment GUI and item-specific enchantment options |
| `filter.yml` | Item category filters used by marketplace and inventory workflows |
| `duels.yml` | Duel maps, world borders, queues, countdowns, cross-server options, arena settings, rules, and GUI |
| `ffa.yml` | FFA queue, arena rules, rollback, player-state handling, and arena definitions |
| `crates.yml` | Crate definitions, keys, rewards, animations, holograms, particles, and settings |
| `spawners.yml` | Donut-style spawner types, drops, storage, and GUI |
| `spawn-stash.yml` | Temporary bait-stash types, detection rules, alerts, cleanup, and messages |
| `network.yml` | Redis network identity, staff chat, reports, helpop, server status, and maintenance routing |
| `staff-mode.yml` | Staff-mode permissions, hotbar items, vanish, better view, staff list, fake players, and menus |
| `hide.yml` | Identity scrambling, aliases, disguises, skins, cooldowns, bypass rules, GUI, and messages |
| `database.yml` | SQLite, MySQL, MongoDB, and Redis connection settings |
| `server-wipe.yml` | Guarded wipe targets, protected worlds, confirmation token lifetime, backups, and messages |
| `discord.yml` | Discord webhook endpoints and event-specific webhook controls |
| `anvil-moderation.yml` | Banned anvil words, punishments, and per-player moderation data |
| `offenses.yml` | Preset offense rules, punishment types, and durations used by `/offend` |

Language files are stored under `languages/`:

- `en_US.yml`
- `es_ES.yml`
- `id_ID.yml`
- `pt_BR.yml`
- `de_DE.yml`
- `fr_FR.yml`
- `ru_RU.yml`
- `zh_CN.yml`

On startup and reload, missing bundled configuration paths are merged into existing files. Existing files are backed up under `config-backups/` before an automatic update. Live crate definitions, duel/FFA arenas, and deployment-specific network server entries are treated as user-managed data and are not restored after removal.

## Commands

Commands can be disabled through their related feature toggle. Arguments in `<angle brackets>` are required; arguments in `[square brackets]` are optional. Every command has a dedicated permission node registered in `plugin.yml`. Most follow the `corenyadonutsmp.command.<command>` pattern; a few staff commands use a `corenyadonutsmp.staff.*` node instead, as listed below.

| Command | Aliases | Usage | Permission Node |
| --- | --- | --- | --- |
| `/addmoney` | - | `/addmoney <player> <amount>` | `corenyadonutsmp.command.addmoney` |
| `/addshards` | - | `/addshards <player> <amount>` | `corenyadonutsmp.command.addshards` |
| `/afk` | - | `/afk` | `corenyadonutsmp.command.afk` |
| `/alts` | - | `/alts <player>` | `corenyadonutsmp.command.alts` |
| `/amethysttool` | - | `/amethysttool give <player> <type> [duration]` or `/amethysttool reload` | `corenyadonutsmp.command.amethysttool` |
| `/amod` | - | `/amod <add\|reload>` | `corenyadonutsmp.command.amod` |
| `/arena` | `/duelarena` | `/arena <create\|delete\|setpos1\|setpos2\|setreturn\|setdisplay\|enable\|disable\|queue\|list\|reload>` | `corenyadonutsmp.command.arena` |
| `/auctionhouse` | `/ah` | `/auctionhouse [sell\|my\|claims\|cancel\|limit\|fastbuy\|fastsell\|reload]` | `corenyadonutsmp.command.auctionhouse` |
| `/balance` | `/bal`, `/money` | `/balance [player]` | `corenyadonutsmp.command.balance` |
| `/ban` | - | `/ban <player> [reason]` | `corenyadonutsmp.command.ban` |
| `/blacklist` | - | `/blacklist <player> [reason]` | `corenyadonutsmp.command.blacklist` |
| `/bounty` | - | `/bounty <add\|set\|info\|list> [player] [amount]` | `corenyadonutsmp.command.bounty` |
| `/chat` | - | `/chat <help\|mute\|unmute\|delay\|clear>` | `corenyadonutsmp.command.chat` |
| `/chatlog` | - | `/chatlog [player]` | `corenyadonutsmp.command.chatlog` |
| `/clearlag` | - | `/clearlag` | `corenyadonutsmp.command.clearlag` |
| `/crate` | - | `/crate <create\|delete\|type\|open\|keys\|reload\|key\|take\|set\|keyall\|add\|edit\|remove\|bind\|unbind\|info>` | `corenyadonutsmp.command.crate` |
| `/crates` | - | `/crates` | `corenyadonutsmp.command.crates` |
| `/create` | - | `/create <invite\|friends> <player> [map]` | `corenyadonutsmp.command.create` |
| `/cuboid` | - | `/cuboid <wand\|create\|delete\|list\|setspawn\|delspawn\|bind <cuboid> <spawn\|shard\|rtp-zone\|rtp-queue> <true\|false>\|reload>` | `corenyadonutsmp.command.cuboid` |
| `/delhome` | - | `/delhome <name>` | `corenyadonutsmp.command.delhome` |
| `/delwarp` | - | `/delwarp <name>` | `corenyadonutsmp.command.delwarp` |
| `/discord` | - | `/discord` | `corenyadonutsmp.command.discord` |
| `/disguise` | - | `/disguise [player-name\|url]` or `/disguise <alias> <player-name\|url>` | `corenyadonutsmp.command.disguise` |
| `/draw` | - | `/draw` | `corenyadonutsmp.command.draw` |
| `/duel` | - | `/duel [player\|accept\|deny\|claims\|reload]` | `corenyadonutsmp.command.duel` |
| `/ecsee` | - | `/ecsee <player>` | `corenyadonutsmp.command.ecsee` |
| `/enderchest` | `/ec` | `/enderchest [reload]` | `corenyadonutsmp.command.enderchest` |
| `/fakeplayer` | `/fplayer` | `/fakeplayer` | `corenyadonutsmp.command.fakeplayer` |
| `/feed` | - | `/feed [player]` | `corenyadonutsmp.command.feed` |
| `/ffa` | - | `/ffa [join\|reload\|arena ...]` | `corenyadonutsmp.command.ffa` |
| `/ffaarena` | - | `/ffaarena <create\|delete\|setpos\|setdisplay\|settings\|enable\|disable\|list\|reload>` | `corenyadonutsmp.command.ffaarena` |
| `/ffastats` | - | `/ffastats [player]` | `corenyadonutsmp.command.ffastats` |
| `/findplayer` | `/fp` | `/findplayer <player>` | `corenyadonutsmp.command.findplayer` |
| `/fly` | - | `/fly [player]` | `corenyadonutsmp.command.fly` |
| `/flyspeed` | `/fs` | `/flyspeed <1-10> [player]` | `corenyadonutsmp.command.flyspeed` |
| `/freeze` | - | `/freeze <player>` or `/freeze reload` | `corenyadonutsmp.command.freeze` |
| `/friend` | - | `/friend` | `corenyadonutsmp.command.friend` |
| `/friends` | - | `/friends [list\|follow\|remove\|search\|following\|followers\|friends]` | `corenyadonutsmp.command.friends` |
| `/gamemode` | `/gm`, `/gmc`, `/gms`, `/gma`, `/gmsp` | `/gamemode <mode> [player]` | `corenyadonutsmp.command.gamemode` |
| `/god` | `/godmode` | `/god [player]` | `corenyadonutsmp.staff.god` |
| `/heal` | - | `/heal [player]` | `corenyadonutsmp.command.heal` |
| `/help` | - | `/help` | `corenyadonutsmp.command.help` |
| `/helpop` | - | `/helpop <message>` | `corenyadonutsmp.command.helpop` |
| `/hide` | - | `/hide [status\|scramble\|remove\|check <player>\|list]` | `corenyadonutsmp.command.hide` |
| `/home` | - | `/home [name]` | `corenyadonutsmp.command.home` |
| `/homes` | - | `/homes` | `corenyadonutsmp.command.homes` |
| `/ignore` | - | `/ignore <player\|list>` | `corenyadonutsmp.command.ignore` |
| `/invsee` | `/inventorysee` | `/invsee <player>` or `/invsee reload` | `corenyadonutsmp.command.invsee` |
| `/keys` | - | `/keys` | `corenyadonutsmp.command.keys` |
| `/kick` | - | `/kick <player> [reason]` | `corenyadonutsmp.command.kick` |
| `/kill` | - | `/kill` | `corenyadonutsmp.command.kill` |
| `/leaderboard` | `/lb`, `/top`, `/leaderboards`, `/baltop` | `/leaderboard [type]` | `corenyadonutsmp.command.leaderboard` |
| `/leave` | - | `/leave` | `corenyadonutsmp.command.leave` |
| `/logs` | - | `/logs` | `corenyadonutsmp.command.logs` |
| `/maintenance` | - | `/maintenance <on [duration]\|off\|status\|setlobby [server]>` | `corenyadonutsmp.command.maintenance` |
| `/menu` | `/donut`, `/dialog` | `/menu` | `corenyadonutsmp.command.menu` |
| `/meta` | `/farmingmeta` | `/meta` | `corenyadonutsmp.command.meta` |
| `/msg` | `/message`, `/tell`, `/whisper`, `/w` | `/msg <player> <message>` | `corenyadonutsmp.command.msg` |
| `/mute` | - | `/mute <player> [reason]` | `corenyadonutsmp.command.mute` |
| `/nightvision` | `/nv` | `/nightvision` | `corenyadonutsmp.command.nightvision` |
| `/offend` | - | `/offend <player> <reason> [time]` | `corenyadonutsmp.staff.punishments.offend` |
| `/orders` | - | `/orders [my\|collect\|reload\|search query]` | `corenyadonutsmp.command.orders` |
| `/pay` | - | `/pay <player> <amount>` | `corenyadonutsmp.command.pay` |
| `/phantom` | - | `/phantom` | `corenyadonutsmp.command.phantom` |
| `/ping` | - | `/ping [player]` | `corenyadonutsmp.command.ping` |
| `/playerunwipe` | `/punwipe`, `/unwipe` | `/playerunwipe <player> [confirm]` | `corenyadonutsmp.command.playerunwipe` |
| `/playerwipe` | `/pwipe`, `/wipe` | `/playerwipe <player> [confirm]` | `corenyadonutsmp.command.playerwipe` |
| `/playtime` | `/pt` | `/playtime [player]` | `corenyadonutsmp.command.playtime` |
| `/pm` | `/togglepm`, `/privatemessages` | `/pm` | `corenyadonutsmp.command.pm` |
| `/portalmanager` | - | `/portalmanager <list\|info\|create\|delete\|setcuboid\|setdestination\|setdisplay\|toggle\|setpriority\|sethologramhere>` | `corenyadonutsmp.command.portalmanager` |
| `/profileviewer` | `/pv` | `/profileviewer <player>` | `corenyadonutsmp.command.profileviewer` |
| `/punishments` | `/phistory` | `/punishments <player>` | `corenyadonutsmp.command.punishments` |
| `/pvp` | - | `/pvp [join\|leave\|kit\|stats\|top\|queue\|leaderboard\|history\|assign\|wand\|create\|setspawn\|setspawn2\|setlobby\|setboundary\|schematic\|reset\|reload]` | `corenyadonutsmp.command.pvp` |
| `/queue` | - | `/queue [join\|leave] [map]` | `corenyadonutsmp.command.queue` |
| `/randomteleport` | `/randomtp` | `/randomteleport` | `corenyadonutsmp.command.randomteleport` |
| `/ranks` | `/rank` | `/ranks` | `corenyadonutsmp.command.ranks` |
| `/removemoney` | - | `/removemoney <player> <amount>` | `corenyadonutsmp.command.removemoney` |
| `/removeshards` | - | `/removeshards <player> <amount>` | `corenyadonutsmp.command.removeshards` |
| `/rename` | - | `/rename <name...\|reset>` | `corenyadonutsmp.command.rename` |
| `/renamehome` | - | `/renamehome <old> <new>` | `corenyadonutsmp.command.renamehome` |
| `/reply` | `/r` | `/reply <message>` | `corenyadonutsmp.command.reply` |
| `/report` | - | `/report <player> <reason>` | `corenyadonutsmp.command.report` |
| `/rtp` | - | `/rtp [world]` | `corenyadonutsmp.command.rtp` |
| `/rtpq` | `/rtpqueue` | `/rtpq [join\|leave]` | `corenyadonutsmp.command.rtpq` |
| `/rules` | - | `/rules` | `corenyadonutsmp.command.rules` |
| `/safety` | - | `/safety [reload\|add [player]]` | `corenyadonutsmp.command.safety` |
| `/seehomes` | `/homesee` | `/seehomes <player>` | `corenyadonutsmp.command.seehomes` |
| `/sell` | - | `/sell` | `corenyadonutsmp.command.sell` |
| `/sellall` | - | `/sellall` | `corenyadonutsmp.command.sellall` |
| `/sellhand` | - | `/sellhand [amount]` | `corenyadonutsmp.command.sellhand` |
| `/sellhistory` | - | `/sellhistory` | `corenyadonutsmp.command.sellhistory` |
| `/sellmulti` | - | `/sellmulti [category]` | `corenyadonutsmp.command.sellmulti` |
| `/sellmultiplier` | - | `/sellmultiplier [category]` | `corenyadonutsmp.command.sellmulti` |
| `/sellprogress` | - | `/sellprogress [category]` | `corenyadonutsmp.command.sellprogress` |
| `/servers` | - | `/servers` | `corenyadonutsmp.command.servers` |
| `/serverwipe` | - | `/serverwipe <preview\|prepare\|confirm\|cancel\|status>` | `corenyadonutsmp.command.serverwipe` |
| `/setafk` | - | `/setafk` | `corenyadonutsmp.command.setafk` |
| `/sethome` | - | `/sethome [name]` | `corenyadonutsmp.command.sethome` |
| `/setmoney` | - | `/setmoney <player> <amount>` | `corenyadonutsmp.command.setmoney` |
| `/setshards` | - | `/setshards <player> <amount>` | `corenyadonutsmp.command.setshards` |
| `/setspawn` | - | `/setspawn` | `corenyadonutsmp.command.setspawn` |
| `/settings` | - | `/settings` | `corenyadonutsmp.command.settings` |
| `/setwarp` | - | `/setwarp <name>` | `corenyadonutsmp.command.setwarp` |
| `/shardpay` | - | `/shardpay <player> <amount>` | `corenyadonutsmp.command.shardpay` |
| `/shards` | - | `/shards [player]` or `/shards everywhere <status\|debug> [player]` | `corenyadonutsmp.command.shards` |
| `/shardshop` | - | `/shardshop` | `corenyadonutsmp.command.shardshop` |
| `/shop` | - | `/shop [reload]` | `corenyadonutsmp.command.shop` |
| `/social` | `/media` | `/social` | `corenyadonutsmp.command.social` |
| `/spawn` | - | `/spawn` | `corenyadonutsmp.command.spawn` |
| `/spawner` | `/spawners` | `/spawner [give\|info\|panel\|reload\|remove\|split]` | `corenyadonutsmp.command.spawner` |
| `/spawnstash` | `/stash` | `/spawnstash [type\|spawn\|list\|remove\|reload]` | `corenyadonutsmp.command.spawnstash` |
| `/staffchat` | `/sc` | `/staffchat <message>` | `corenyadonutsmp.command.staffchat` |
| `/stafflist` | - | `/stafflist` | `corenyadonutsmp.command.stafflist` |
| `/staffmode` | `/staff` | `/staffmode [player\|reload]` | `corenyadonutsmp.command.staffmode` |
| `/stats` | - | `/stats [player]` | `corenyadonutsmp.command.stats` |
| `/store` | - | `/store` | `corenyadonutsmp.command.store` |
| `/team` | - | `/team <create\|disband\|invite\|kick\|join\|leave\|home\|sethome\|delhome\|chat\|info\|pvp>` | `corenyadonutsmp.command.team` |
| `/teleport` | `/tp`, `/tphere`, `/tpall` | `/teleport <player\|here <player>\|all\|top\|x y z [world]>` | `corenyadonutsmp.command.teleport` |
| `/tempban` | - | `/tempban <player> <time> [reason]` | `corenyadonutsmp.command.tempban` |
| `/tempmute` | - | `/tempmute <player> <time> [reason]` | `corenyadonutsmp.command.tempmute` |
| `/topsell` | `/sellstats` | `/topsell [gui\|items\|volume\|sellers\|export]` | `corenyadonutsmp.command.topsell` |
| `/tpa` | - | `/tpa <player>` | `corenyadonutsmp.command.tpa` |
| `/tpacancel` | - | `/tpacancel` | `corenyadonutsmp.command.tpacancel` |
| `/tpaccept` | - | `/tpaccept [player]` | `corenyadonutsmp.command.tpaccept` |
| `/tpadeny` | - | `/tpadeny [player]` | `corenyadonutsmp.command.tpadeny` |
| `/tpahere` | - | `/tpahere <player>` | `corenyadonutsmp.command.tpahere` |
| `/tpahereauto` | - | `/tpahereauto` | `corenyadonutsmp.command.tpahereauto` |
| `/tpauto` | - | `/tpauto` | `corenyadonutsmp.command.tpauto` |
| `/twitter` | - | `/twitter` | `corenyadonutsmp.command.twitter` |
| `/corenyadonutsmp` | `/cds`, `/cdsmp` | `/corenyadonutsmp <reload\|statswipe\|optimize\|setup\|features\|maintenance>` | `corenyadonutsmp.command.corenyadonutsmp` |
| `/unban` | `/pardon` | `/unban <player> [reason]` | `corenyadonutsmp.command.unban` |
| `/unblacklist` | - | `/unblacklist <player> [reason]` | `corenyadonutsmp.command.unblacklist` |
| `/unignore` | - | `/unignore <player>` | `corenyadonutsmp.command.unignore` |
| `/unmute` | - | `/unmute <player> [reason]` | `corenyadonutsmp.command.unmute` |
| `/vanish` | - | `/vanish` | `corenyadonutsmp.command.vanish` |
| `/vcmute` | - | `/vcmute <player> [reason]` | `corenyadonutsmp.staff.vcmute` |
| `/vcunmute` | - | `/vcunmute <player> [reason]` | `corenyadonutsmp.staff.vcunmute` |
| `/voicechatconsent` | `/vcconsent`, `/voiceconsent` | `/voicechatconsent [revoke]` | `corenyadonutsmp.command.voicechatconsent` |
| `/warn` | - | `/warn <player> [reason]` | `corenyadonutsmp.command.warn` |
| `/warp` | - | `/warp [name]` | `corenyadonutsmp.command.warp` |
| `/warpmanager` | - | `/warpmanager <create\|delete\|list> [name]` | `corenyadonutsmp.command.warpmanager` |
| `/worth` | `/prices` | `/worth [hand\|reload]` | `corenyadonutsmp.command.worth` |

Temporary punishment durations accept combined values such as `30s`, `15m`, `2h`, `5d`, or `5d 15m 30s`.

Running `/baltop` with no arguments opens the money leaderboard directly instead of the leaderboard type menu.

## Permissions

`true` means the permission is granted by default to all players, `op` means it defaults to server operators, and `false` means it must be granted explicitly. `corenyadonutsmp.admin` is the main admin parent node, and `corenyadonutsmp.command.*` grants access to all plugin commands.

### Main Parent Nodes

| Permission Node | Default | Description |
| --- | --- | --- |
| `corenyadonutsmp.admin` | `op` | Main admin parent node giving access to administrative commands, reload, wipe, and management systems |
| `corenyadonutsmp.command.*` | `op` | Grants access to execute all CorenyaDonutSMP commands |
| `corenyadonutsmp.staff.mode` | `op` | Staff moderation mode parent node (vanish, betterview, randomtp, staff list, tools) |
| `corenyadonutsmp.staff.alerts.receive` | `op` | Parent node for receiving staff alerts (`helpop` and `report`) |
| `corenyadonutsmp.staff.punishments.create` | `op` | Parent node for issuing punishments (`warn`, `kick`, `ban`, `mute`, `blacklist`) |
| `corenyadonutsmp.staff.punishments.remove` | `op` | Parent node for removing active punishments (`unban`, `unmute`, `unblacklist`) |

### System & Staff Permissions

| Permission | Default | Description |
| --- | --- | --- |
| `anvilmod.admin` | `op` | Admin access for anvil word moderation |
| `safety.add` | `op` | Add players to safety check bypass list |
| `safety.reload` | `op` | Reload safety configuration |
| `safety.use` | `true` | Standard safety system permission |
| `corenyadonutsmp.admin.addmoney` | `op` | Add money to player balances |
| `corenyadonutsmp.admin.amethysttool` | `op` | Give and reload amethyst tools |
| `corenyadonutsmp.admin.auctionhouse` | `op` | Reload and manage Auction House settings |
| `corenyadonutsmp.admin.chatlog` | `op` | Browse logged public chat messages |
| `corenyadonutsmp.admin.clearlag` | `op` | Trigger lag clear manually |
| `corenyadonutsmp.admin.crate` | `op` | Manage crate balances, chest bindings, and settings |
| `corenyadonutsmp.admin.crate.keyall` | `op` | Trigger key-all rewards manually |
| `corenyadonutsmp.admin.crate.reload` | `op` | Reload crate configuration |
| `corenyadonutsmp.admin.cuboid` | `op` | Create, bind, and manage cuboid regions |
| `corenyadonutsmp.admin.delwarp` | `op` | Delete public warp points |
| `corenyadonutsmp.admin.duels` | `op` | Manage duel settings and arenas |
| `corenyadonutsmp.admin.ecsee` | `op` | View other players' Ender Chest contents |
| `corenyadonutsmp.admin.enderchest` | `op` | Reload Ender Chest settings |
| `corenyadonutsmp.admin.features` | `op` | Access and toggle runtime feature switches |
| `corenyadonutsmp.admin.ffa` | `op` | Manage FFA arenas and settings |
| `corenyadonutsmp.admin.freeze` | `op` | Reload freeze settings |
| `corenyadonutsmp.admin.invsee` | `op` | Reload Invsee settings |
| `corenyadonutsmp.admin.logs` | `op` | View command log history |
| `corenyadonutsmp.admin.maintenance` | `op` | Manage maintenance mode |
| `corenyadonutsmp.admin.maintenance.bypass` | `op` | Join server while maintenance mode is enabled |
| `corenyadonutsmp.admin.optimize` | `op` | Access runtime optimization controls |
| `corenyadonutsmp.admin.orders` | `op` | Manage and reload Orders board settings |
| `corenyadonutsmp.admin.playerunwipe` | `op` | Preview and restore a wiped player's stored data |
| `corenyadonutsmp.admin.playerwipe` | `op` | Preview and wipe a single player's stored data |
| `corenyadonutsmp.admin.portalmanager` | `op` | Create, display, and manage RTP portals |
| `corenyadonutsmp.admin.punishments.exempt` | `op` | Cannot be punished by staff who lack the exempt bypass |
| `corenyadonutsmp.admin.punishments.exempt.bypass` | `op` | Punish players who are exempt from being punished |
| `corenyadonutsmp.admin.pvp` | `op` | Manage the ranked PvP arena and its kits |
| `corenyadonutsmp.admin.reload` | `op` | Reload all CorenyaDonutSMP configurations |
| `corenyadonutsmp.admin.removemoney` | `op` | Remove money from player balances |
| `corenyadonutsmp.admin.sellstats` | `op` | View top sell statistics and economy metrics |
| `corenyadonutsmp.admin.serverwipe` | `op` | Execute guarded server wipe operations |
| `corenyadonutsmp.admin.setmoney` | `op` | Set player money balances |
| `corenyadonutsmp.admin.setup` | `op` | Use interactive setup status and tools |
| `corenyadonutsmp.admin.setwarp` | `op` | Set public warp points |
| `corenyadonutsmp.admin.shards` | `op` | Inspect Shards Everywhere status |
| `corenyadonutsmp.admin.shop` | `op` | Reload shop settings |
| `corenyadonutsmp.admin.spawner` | `op` | Give and manage Donut-style spawners |
| `corenyadonutsmp.admin.spawnstash` | `op` | Manage bait spawn stashes |
| `corenyadonutsmp.admin.staffmode` | `op` | Reload Staff Mode settings |
| `corenyadonutsmp.admin.statswipe` | `op` | Execute player stats wipe |
| `corenyadonutsmp.admin.teleportareas.delete` | `op` | Delete configured teleport areas |
| `corenyadonutsmp.admin.warpmanager` | `op` | Manage public warps |
| `corenyadonutsmp.admin.worth` | `op` | Reload sell/worth settings |
| `corenyadonutsmp.command.flyspeed` | `op` | Use `/flyspeed` |
| `corenyadonutsmp.command.offend` | `op` | Use `/offend` (parent of `corenyadonutsmp.staff.punishments.offend`) |

### Staff Moderation & Alert Permissions

| Permission | Default | Description |
| --- | --- | --- |
| `corenyadonutsmp.staff.alerts.bypass-cooldown` | `op` | Bypass report and helpop cooldowns |
| `corenyadonutsmp.staff.alerts.receive` | `op` | Receive helpop and report staff alerts |
| `corenyadonutsmp.staff.alts` | `op` | Check IP history and alt accounts |
| `corenyadonutsmp.staff.chat.bypass.delay` | `op` | Bypass global chat delay |
| `corenyadonutsmp.staff.chat.bypass.filter` | `op` | Bypass global chat filter |
| `corenyadonutsmp.staff.chat.bypass.mute` | `op` | Bypass global chat mute |
| `corenyadonutsmp.staff.chat.clear` | `op` | Clear chat screen visually |
| `corenyadonutsmp.staff.chat.delay` | `op` | Set global chat delay |
| `corenyadonutsmp.staff.chat.mute` | `op` | Mute global chat |
| `corenyadonutsmp.staff.chat.unmute` | `op` | Unmute global chat |
| `corenyadonutsmp.staff.chat.use` | `op` | Send and receive staff chat |
| `corenyadonutsmp.staff.fakeplayer` | `op` | Spawn fake player bait entities |
| `corenyadonutsmp.staff.fakeplayer.alert` | `op` | Receive fake player bait attack alerts |
| `corenyadonutsmp.staff.fakeplayer.bypass` | `op` | Bypass fake player bait triggers |
| `corenyadonutsmp.staff.feed` | `op` | Feed players |
| `corenyadonutsmp.staff.fly` | `op` | Toggle flight mode |
| `corenyadonutsmp.staff.flyspeed` | `op` | Adjust flying speed for yourself or another player |
| `corenyadonutsmp.staff.freeze` | `op` | Freeze/unfreeze players for inspection |
| `corenyadonutsmp.staff.freeze.alert` | `op` | Receive player freeze alerts |
| `corenyadonutsmp.staff.freeze.exempt` | `op` | Exempt from being frozen by staff |
| `corenyadonutsmp.staff.gamemode` | `op` | Change own gamemode |
| `corenyadonutsmp.staff.gamemode.others` | `op` | Change other players' gamemodes |
| `corenyadonutsmp.staff.god` | `op` | Toggle god mode |
| `corenyadonutsmp.staff.heal` | `op` | Heal players |
| `corenyadonutsmp.staff.helpop.receive` | `op` | Receive helpop request alerts |
| `corenyadonutsmp.staff.invsee` | `op` | Inspect player inventories |
| `corenyadonutsmp.staff.invsee.modify` | `op` | Modify player inventories in invsee |
| `corenyadonutsmp.staff.mode.betterview` | `op` | Toggle better view in staff mode |
| `corenyadonutsmp.staff.mode.others` | `op` | Toggle staff mode for other players |
| `corenyadonutsmp.staff.mode.randomtp` | `op` | Random teleport in staff mode |
| `corenyadonutsmp.staff.mode.seevanished` | `op` | See vanished staff members |
| `corenyadonutsmp.staff.mode.stafflist` | `op` | Open online staff list |
| `corenyadonutsmp.staff.mode.vanish` | `op` | Toggle vanish in staff mode |
| `corenyadonutsmp.staff.profileviewer` | `op` | View player profiles and homes |
| `corenyadonutsmp.staff.seehomes` | `op` | Browse any player's homes and teleport to them |
| `corenyadonutsmp.staff.punishments.ban` | `false` | Apply ban and tempban punishments |
| `corenyadonutsmp.staff.punishments.blacklist` | `false` | Apply blacklist punishments |
| `corenyadonutsmp.staff.punishments.delete` | `op` | Delete punishment logs from GUI |
| `corenyadonutsmp.staff.punishments.mute` | `false` | Apply mute and tempmute punishments |
| `corenyadonutsmp.staff.punishments.offend` | `op` | Apply preset offense punishments from `offenses.yml` |
| `corenyadonutsmp.staff.punishments.unban` | `false` | Remove active bans |
| `corenyadonutsmp.staff.punishments.unblacklist` | `false` | Remove active blacklists |
| `corenyadonutsmp.staff.punishments.unmute` | `false` | Remove active mutes |
| `corenyadonutsmp.staff.punishments.view` | `op` | View punishment history |
| `corenyadonutsmp.staff.rename` | `op` | Rename items |
| `corenyadonutsmp.staff.report.receive` | `op` | Receive player report alerts |
| `corenyadonutsmp.staff.spawnstash` | `op` | Place and inspect spawn stash bait |
| `corenyadonutsmp.staff.spawnstash.alert` | `op` | Receive spawn stash trigger alerts |
| `corenyadonutsmp.staff.spawnstash.bypass` | `op` | Bypass spawn stash detection |
| `corenyadonutsmp.staff.teleport` | `op` | Access staff teleport tools |
| `corenyadonutsmp.staff.vcmute` | `false` | Apply voice chat mute punishments |
| `corenyadonutsmp.staff.vcunmute` | `false` | Remove voice chat mute punishments |

### Player & Feature Permissions

| Permission | Default | Description |
| --- | --- | --- |
| `donutauction.use` / `corenyadonutsmp.auctionhouse.use` | `true` / `false` | Open Auction House GUI |
| `donutauction.buy` / `corenyadonutsmp.auctionhouse.buy` | `true` / `false` | Buy items on Auction House |
| `donutauction.sell` / `corenyadonutsmp.auctionhouse.sell` | `true` / `false` | Sell items on Auction House |
| `donutauction.my` / `corenyadonutsmp.auctionhouse.my` | `true` / `false` | View own listings on Auction House |
| `donutauction.claims` / `corenyadonutsmp.auctionhouse.claims` | `true` / `false` | Collect claims from Auction House |
| `donutauction.cancel` / `corenyadonutsmp.auctionhouse.cancel` | `true` / `false` | Cancel own listings on Auction House |
| `donutauction.limit` / `corenyadonutsmp.auctionhouse.limit` | `true` / `false` | Check listing limits on Auction House |
| `donutauction.fastbuy` / `corenyadonutsmp.auctionhouse.fastbuy` | `false` / `op` | Fast buy command access |
| `donutauction.fastsell` / `corenyadonutsmp.auctionhouse.fastsell` | `false` / `op` | Fast sell command access |
| `corenyadonutsmp.command.friend` | `true` | Use `/friend` |
| `corenyadonutsmp.command.sellmulti` | `true` | Open the sell multiplier menu (`/sellmulti`, `/sellmultiplier`) |
| `corenyadonutsmp.command.sellprogress` | `true` | Open the sell multiplier progress menu (`/sellprogress`) |
| `corenyadonutsmp.enderchest` | `true` | Open custom Ender Chest |
| `corenyadonutsmp.friends` | `true` | Friends and follow system |
| `corenyadonutsmp.helpop` | `true` | Use `/helpop` to request staff help |
| `corenyadonutsmp.hide.admin` | `op` | Manage and inspect player disguises |
| `corenyadonutsmp.hide.bypass` | `op` | Bypass disguise restrictions |
| `corenyadonutsmp.hide.disguise` | `op` | Change skin and disguise alias |
| `corenyadonutsmp.hide.scramble` | `op` | Scramble public username |
| `corenyadonutsmp.ignore` | `true` | Ignore and unignore players |
| `corenyadonutsmp.ignore.bypass` | `op` | Bypass private message ignore filter |
| `corenyadonutsmp.message` | `true` | Send and reply to private messages |
| `corenyadonutsmp.message.bypass-disabled` | `op` | Bypass recipient disabled PMs |
| `corenyadonutsmp.message.toggle` | `true` | Toggle private messages on/off |
| `corenyadonutsmp.report` | `true` | Report players to online staff |
| `corenyadonutsmp.servers` | `false` | View network server status GUI |
| `corenyadonutsmp.shards.everywhere` | `false` | Receive passive Shards Everywhere rewards |
| `corenyadonutsmp.shardshop` | `true` | Open Shard Shop GUI |
| `corenyadonutsmp.spawner.bypass` | `false` | Break spawners without a Silk Touch pickaxe while `REQUIRE_SILK_TOUCH` is enabled |
| `media` | `false` | Display configurable Media tablist badge (requires explicit LuckPerms assignment, not auto-granted to OP) |
| `rank.media` | `false` | Display configurable Media tablist badge legacy alias |
| `rank.media.plus` | `false` | Display configurable Media+ tablist badge (requires explicit LuckPerms assignment, not auto-granted to OP) |
| `rank.media.include` | `false` | Include player in media badge handling (requires explicit LuckPerms assignment, not auto-granted to OP) |

## Placeholders

CorenyaDonutSMP includes built-in [PlaceholderAPI](https://placeholderapi.com/) expansion modules for player economy, statistics, locations, countdowns, leaderboards, player ranks, and disguise states.

Placeholder expansion identifiers supported: `%economy_*%`, `%cds_*%`, `%corenyadonutsmp_*%`, `%economylb_*%`, `%economyrank_*%`, and `%hide_*%`.

### Economy & Player Placeholders (`%economy_*%` / `%cds_*%` / `%corenyadonutsmp_*%`)

| Placeholder | Description | Example Output |
| --- | --- | --- |
| `%economy_money%` | Raw money balance | `12500.50` |
| `%economy_money_formatted%` | Formatted money with currency symbol | `$12,500.50` |
| `%economy_money_short%` / `%economy_nicestMoney%` | Compact formatted money amount | `12.5k` |
| `%economy_money_short_formatted%` | Formatted compact money with symbol | `$12.5k` |
| `%economy_shards%` | Raw shards balance | `500` |
| `%economy_shards_formatted%` | Formatted shards balance | `500 Shards` |
| `%economy_shards_short%` / `%economy_nicestShards%` | Compact formatted shards amount | `1.2k` |
| `%economy_shards_short_formatted%` | Formatted compact shards with symbol | `1.2k Shards` |
| `%economy_kills%` | Total player kill count | `42` |
| `%economy_deaths%` | Total player death count | `10` |
| `%economy_killstreak%` | Current active killstreak | `5` |
| `%economy_highestkillstreak%` | Highest recorded killstreak | `12` |
| `%economy_playtime%` | Total formatted playtime duration | `3d 14h 22m` |
| `%economy_blocksplaced%` | Total blocks placed count | `15400` |
| `%economy_blocksbroken%` | Total blocks broken count | `48200` |
| `%economy_mobskilled%` | Total mob kills count | `1280` |
| `%economy_moneyspent%` | Total money spent in shop/marketplaces | `50000.00` |
| `%economy_moneymade%` | Total money earned from selling/markets | `120000.00` |
| `%economy_team%` | Player's team name | `TITANS` or `none` |
| `%economy_username%` | Public display username (respects `/hide` disguise) | `Steve` |
| `%economy_ping%` | Player ping latency in ms | `24` |
| `%economy_x%` / `%economy_coord_x%` | Player X coordinate (respects coordinate obfuscation) | `120` |
| `%economy_y%` / `%economy_coord_y%` | Player Y coordinate (respects coordinate obfuscation) | `64` |
| `%economy_z%` / `%economy_coord_z%` | Player Z coordinate (respects coordinate obfuscation) | `-350` |
| `%economy_coords%` / `%economy_location%` | Formatted X, Y, Z coordinates string | `120, 64, -350` |
| `%economy_randomized_coords%` | Returns `true`/`false` if coordinate obfuscation is active | `false` |
| `%economy_donutplus%` | Displays Donut+ badge if player has permission | `&d&lDonut+ &r` |
| `%economy_keyall_countdown%` | Formatted countdown until automatic key-all reward | `05:32` |
| `%economy_booster_countdown%` | Formatted countdown for active shard booster | `14:20` or `inactive` |
| `%economy_rtp_countdown%` | Formatted countdown for RTP zone cooldown | `00:45` or `disabled` |
| `%economy_billford_countdown%` | Formatted countdown for Billford rotation | `02:15:00` or `disabled` |
| `%economy_shard_cuboid_display%` | Shard cuboid display status indicator | `[Inside Zone]` |
| `%economy_shard_cuboid_status%` | Shard cuboid status | `inside` or `outside` |
| `%economy_shard_cuboid_name%` | Name of active shard cuboid region | `MainShardArea` or `none` |
| `%economy_money_symbol%` | Currency symbol for money | `$` |
| `%economy_money_symbol_colored%` | Colored currency symbol for money | `&$` |
| `%economy_shards_symbol%` | Currency symbol for shards | `⬟` |
| `%economy_shards_symbol_colored%` | Colored currency symbol for shards | `&d⬟` |

### Leaderboard Placeholders (`%economylb_*%` / `%economy_top_*%`)

Syntax: `%economylb_<type>_<position>_<property>%` or `%economy_top_<type>_<position>_<property>%`

- **Leaderboard Types (`<type>`)**: `money`, `shards`, `kills`, `deaths`, `killstreak`, `highestkillstreak`, `playtime`, `blocksplaced`, `blocksbroken`, `mobskilled`, `moneyspent`, `moneymade`
- **Positions (`<position>`)**: Rank index starting from `1` (e.g. `1`, `2`, `3`, `10`)
- **Properties (`<property>`)**:
  - `name`: Username of the player at position
  - `value`: Full un-truncated value of the player at position
  - `value_short` / `short`: Compact formatted value (e.g. `15.4M`)
  - `rank`: Rank number index
  - `display`: Pre-formatted entry line e.g. `#1 Notch: $15.4M`

Examples:

```
%economylb_money_1_name%       -> Notch
%economylb_money_1_value%      -> $15,400,000.00
%economylb_money_1_value_short% -> 15.4M
%economylb_kills_3_display%     -> #3 Alex: 450
%economy_top_shards_1_name%    -> EnderKing
```

### Rank Leaderboard Placeholders (`%economyrank_*%`)

Syntax: `%economyrank_<type>%`

Returns the player's personal rank position number on the specified leaderboard type (e.g. `1`, `15`, or `0` if unranked).

Examples:
- `%economyrank_money%` -> `5`
- `%economyrank_kills%` -> `1`

### Hide & Disguise Placeholders (`%hide_*%`)

Syntax: `%hide_<property>%`

| Placeholder | Description | Example Output |
| --- | --- | --- |
| `%hide_active%` | Returns `true` or `false` if player has an active disguise or alias | `true` |
| `%hide_name%` / `%hide_public_name%` | Player's formatted public display name | `ShadowNinja` |
| `%hide_plain_name%` | Player's plain unformatted public display name | `ShadowNinja` |
| `%hide_mode%` | Active disguise mode (`NONE`, `SCRAMBLE`, `ALIAS`, `DISGUISE`) | `ALIAS` |
| `%hide_alias%` | Active custom alias string | `ShadowNinja` |
| `%hide_skin%` | Active custom skin username | `CustomSkin123` |

## License and Terms

CorenyaDonutSMP is free, proprietary software.

- The plugin is free to use but remains under a proprietary license.
- Redistribution, resale, sublicensing, public mirroring, or unauthorized sharing is not permitted without written permission.
- You may modify the source for use on your own server; modified builds may not be distributed.
- The shaded jar bundles third-party libraries under their own licenses (Apache 2.0, MIT, and GPLv2 with the Universal FOSS Exception).
- For full licensing terms, see [LICENSE.md](LICENSE.md).
- For contribution guidelines and rules, see [CONTRIBUTING.md](CONTRIBUTING.md).

Copyright (c) 2026 CorenyaDonutSMP. All rights reserved.

## Support

| Channel | Use it for |
| --- | --- |
| [GitHub Issues](https://github.com/rushflex/CorenyaDonutSMP/issues) | Bug reports, feature requests, and documentation problems |
| [Discord](https://dsc.gg/corenya) | Setup help, configuration questions, and general discussion |
| [`docs/wiki/`](docs/wiki) | Guides, configuration reference, and the FAQ |

When reporting an issue, include:

- Plugin version and jar file name
- Server software and version (Paper, Purpur, Pufferfish, Spigot, or Folia)
- Java version
- Relevant configuration snippets with secrets removed
- Console errors or stack traces
- Steps to reproduce the issue

Do not share database credentials, Redis passwords, Discord webhook URLs, or other sensitive server data in public channels.
