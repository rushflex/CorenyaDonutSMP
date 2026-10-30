# Commands & Permissions

143 commands. Almost all need `corenyadonutsmp.command.<name>` just to run. Changing data
usually needs a second node (`corenyadonutsmp.admin.*` or `corenyadonutsmp.staff.*`).
You can also turn a whole feature off in [config.yml](Config-config.yml) — that is not a
permission.

**On this page:** [how permissions work](#how-permissions-are-layered) ·
[feature toggles](#feature-toggles-and-disabled-commands) ·
[economy](#economy--currency) · [shop](#shop--selling) ·
[auction & orders](#auction-house--orders) · [teleport](#teleportation--homes) ·
[crates](#crates--spawners) · [PvP](#pvp-duels-ffa-and-ranked) ·
[social](#social--chat) · [settings](#player-settings--info) ·
[staff](#staff--moderation) · [punishments](#punishments) ·
[network](#network--maintenance) · [admin](#admin--utility) ·
[numbered tiers](#numbered-permission-tiers) ·
[full list](#permission-reference) ·
[quirks](#known-inconsistencies)

## How permissions are layered

There are four layers, applied in this order:

1. **Command gate.** The `permission:` field of the command in `plugin.yml`. Failure produces the command's `permission-message:` and the executor is never called. Almost all of these are `corenyadonutsmp.command.<name>`; the exceptions are the punishment commands, `/offend` and `/god`, which are gated by `corenyadonutsmp.staff.*` nodes directly.
2. **Feature gate.** The command's `FEATURES.<KEY>.ENABLED` toggle in `config.yml`. This is not a permission; see [Feature toggles and disabled commands](#feature-toggles-and-disabled-commands).
3. **Action check.** A runtime `hasPermission` check inside the handler, usually `corenyadonutsmp.admin.<feature>` for destructive or configuration actions, `corenyadonutsmp.staff.<feature>` for moderation actions, or a bare node such as `corenyadonutsmp.message` for ordinary player behaviour.
4. **Tier resolution.** For quotas and cooldowns, the plugin scans a numbered permission family such as `corenyadonutsmp.homes.<N>` and derives a value. See [Numbered permission tiers](#numbered-permission-tiers).

Parent and wildcard nodes worth knowing:

| Node | Default | Role |
|------|---------|------|
| `corenyadonutsmp.command.*` | op | Wildcard granting every declared `corenyadonutsmp.command.<name>` child. |
| `corenyadonutsmp.admin` | op | Parent granting `corenyadonutsmp.command.*` plus every `corenyadonutsmp.admin.*` child. Also short-circuits the `/topsell` check and enables the update-check notice on join. |
| `corenyadonutsmp.staff.mode` | op | Parent granting staff mode plus 27 further staff utility children, including `corenyadonutsmp.chat.color`. |
| `corenyadonutsmp.staff.punishments.create` | op | Parent granting `punishments.ban`, `punishments.blacklist`, `punishments.mute` and `staff.vcmute`. |
| `corenyadonutsmp.staff.punishments.remove` | op | Parent granting `punishments.unban`, `punishments.unblacklist`, `punishments.unmute` and `staff.vcunmute`. |
| `corenyadonutsmp.staff.alerts.receive` | op | Parent granting `staff.helpop.receive` and `staff.report.receive`. |
| `corenyadonutsmp.command.ban` and siblings | op | Each punishment command node is itself a parent that grants the matching `corenyadonutsmp.staff.punishments.*` child, which is declared with default `false`. |

Related pages: [Configuration Reference](Configuration-Reference), [Staff & Security](Staff-and-Security), [Economy & Marketplaces](Economy-and-Marketplaces), [config.yml](Config-config.yml).

## Feature toggles and disabled commands

Every command belongs to at most one feature group. A group is disabled by setting `FEATURES.<KEY>.ENABLED` to `false` in `config.yml`; if that path is absent the plugin falls back to the legacy `COMMANDS.<KEY>` boolean, and if neither path exists the feature is treated as enabled. `FEATURES.<KEY>.ENABLED` can also be toggled in-game with `/corenyadonutsmp features <enable|disable|toggle> <key>` or through the feature GUI.

`FEATURES_SETTINGS.DISABLED_COMMAND_ACTION` controls what a disabled command does. It accepts three values:

| Value | Aliases accepted | Behaviour |
|-------|------------------|-----------|
| `MESSAGE` | default when unset or unrecognised | The command stays registered and replies with the `FEATURES.DISABLED` message. |
| `UNKNOWN` | `HIDE` | The command stays registered but behaves as though it does not exist. |
| `UNREGISTER` | `DISABLE`, `OFF`, `REMOVE` | The command is removed from the command map, so clients never see it. |

Tab completion is suppressed for any command whose feature group is disabled, regardless of the action setting. Feature keys include `HOMES`, `SELL`, `SHOP`, `CRATES`, `DUELS`, `FFA`, `PVP_ARENA`, `AUCTION_HOUSE`, `ORDERS`, `PUNISHMENTS`, `STAFF_MODE`, `SPAWN_STASH`, `HIDE`, `FRIENDS`, `SAFETY`, `MAINTENANCE` and roughly forty more; see [Configuration Reference](Configuration-Reference) for the complete list.

## Economy & Currency

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/balance` | `/bal`, `/money` | `/balance [player]` | `corenyadonutsmp.command.balance` | Show your own money balance, or another player's. |
| `/pay` | — | `/pay <player> <amount>` | `corenyadonutsmp.command.pay` | Transfer money to another player. |
| `/addmoney` | — | `/addmoney <player> <amount>` | `corenyadonutsmp.command.addmoney`; action check `corenyadonutsmp.admin.addmoney` | Add money to a player's balance. |
| `/removemoney` | — | `/removemoney <player> <amount>` | `corenyadonutsmp.command.removemoney`; action check `corenyadonutsmp.admin.removemoney` | Remove money from a player's balance. |
| `/setmoney` | — | `/setmoney <player> <amount>` | `corenyadonutsmp.command.setmoney`; action check `corenyadonutsmp.admin.setmoney` | Set a player's balance to an exact value. |
| `/shards` | — | `/shards [player]` or `/shards everywhere <status\|debug> [player]` | `corenyadonutsmp.command.shards`; `everywhere` requires `corenyadonutsmp.admin.shards` | Check shard balances, or inspect Shards Everywhere eligibility. |
| `/shardpay` | — | `/shardpay <player> <amount>` | `corenyadonutsmp.command.shardpay` | Transfer shards to another player. |
| `/addshards` | — | `/addshards <player> <amount>` | `corenyadonutsmp.command.addshards`; action check `corenyadonutsmp.admin.shards` | Add shards to a player's balance. |
| `/removeshards` | — | `/removeshards <player> <amount>` | `corenyadonutsmp.command.removeshards`; action check `corenyadonutsmp.admin.shards` | Remove shards from a player's balance. |
| `/setshards` | — | `/setshards <player> <amount>` | `corenyadonutsmp.command.setshards`; action check `corenyadonutsmp.admin.shards` | Set a player's shard balance to an exact value. |
| `/bounty` | — | `/bounty <list\|add\|set\|info> [player] [amount]` | `corenyadonutsmp.command.bounty` | Place, inspect and list bounties. With no arguments, behaves as `list`. |

`/addshards`, `/removeshards` and `/setshards` are all handled by the same executor and share the single action node `corenyadonutsmp.admin.shards`.

## Shop & Selling

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/shop` | — | `/shop [reload\|legacy]` | `corenyadonutsmp.command.shop`; `reload` requires `corenyadonutsmp.admin.shop` | Open the shop menu. `legacy` opens the pre-dialog chest interface. |
| `/shardshop` | — | `/shardshop [reload\|legacy]` | `corenyadonutsmp.command.shardshop`; `reload` requires `corenyadonutsmp.admin.shop` | Open the shard-currency shop. Shares the executor with `/shop`. |
| `/sell` | — | `/sell` | `corenyadonutsmp.command.sell` | Open the sell menu. |
| `/sellall` | — | `/sellall` | `corenyadonutsmp.command.sellall` | Open the confirmation menu to sell every sellable item in your inventory. |
| `/sellhand` | — | `/sellhand [amount]` | `corenyadonutsmp.command.sellhand` | Sell the item held in your main hand. Any `[amount]` argument is ignored at runtime and the command sells the entire held stack; see [Known inconsistencies](#known-inconsistencies). |
| `/sellhistory` | — | `/sellhistory` | `corenyadonutsmp.command.sellhistory` | View your recent sell transactions. |
| `/sellmulti` | — | `/sellmulti [category]` | `corenyadonutsmp.command.sellmulti` | Open the sell multiplier menu. |
| `/sellmultiplier` | — | `/sellmultiplier [category]` | `corenyadonutsmp.command.sellmulti` | Identical to `/sellmulti`; note that it reuses the `sellmulti` node rather than declaring its own. |
| `/sellprogress` | — | `/sellprogress [category]` | `corenyadonutsmp.command.sellprogress` | Open the sell multiplier progress menu. |
| `/topsell` | `/sellstats` | `/topsell [gui\|items\|volume\|sellers\|export\|web\|html\|reset]` | `corenyadonutsmp.command.topsell`; action check `corenyadonutsmp.admin.sellstats` or `corenyadonutsmp.admin` | Admin sell statistics and economy metrics. |
| `/worth` | `/prices` | `/worth [browse\|hand\|reload\|<amount> <item>]` | `corenyadonutsmp.command.worth`; `reload` requires `corenyadonutsmp.admin.worth` | Open the worth browser or price a specific item. |
| `/meta` | `/farmingmeta` | `/meta` | `corenyadonutsmp.command.meta` | Show the item that is currently the farming meta. |

The five sell commands (`/sell`, `/sellall`, `/sellhand`, `/sellhistory`, `/sellmulti` and its aliases) are all served by one executor, which also accepts `admin`, `stats` and `top` as a first argument to redirect into the sell statistics view.

### `/topsell` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| *(none)*, `gui`, `menu` | — | `corenyadonutsmp.admin.sellstats` | Open the sell statistics GUI. |
| `items`, `revenue`, `topitems` | `[limit]` | `corenyadonutsmp.admin.sellstats` | Rank items by revenue. |
| `volume`, `topvolume` | `[limit]` | `corenyadonutsmp.admin.sellstats` | Rank items by quantity sold. |
| `sellers`, `topsellers`, `players` | `[limit]` | `corenyadonutsmp.admin.sellstats` | Rank players by sell revenue. |
| `export`, `report` | — | `corenyadonutsmp.admin.sellstats` | Write a statistics report to disk. |
| `web`, `site`, `url`, `paste` | — | `corenyadonutsmp.admin.sellstats` | Upload the report to a web paste service. |
| `html` | — | `corenyadonutsmp.admin.sellstats` | Export the report as HTML. |
| `reset`, `wipe`, `clear` | `confirm` | `corenyadonutsmp.admin.sellstats` | Clear stored sell statistics. Requires `confirm` as the second argument. |

### `/worth` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| *(none)*, `browse`, `prices` | — | Command gate only | Open the worth browser. The `/prices` alias always opens the browser. |
| `hand`, `held`, `item`, `check` | — | Command gate only | Price the item in your main hand. |
| `reload` | — | `corenyadonutsmp.admin.worth` | Reload `worth.yml`, the worth manager and the farming meta. |
| *(numeric)* | `<amount> <item>` | Command gate only | Price an arbitrary quantity of a named material. |

## Auction House & Orders

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/auctionhouse` | `/ah` | `/auctionhouse [sell\|my\|claims\|cancel\|search\|limit\|fastbuy\|fastsell\|reload]` | `corenyadonutsmp.command.auctionhouse`; per-action `corenyadonutsmp.auctionhouse.<action>` or `donutauction.<action>`; `reload` requires `corenyadonutsmp.admin.auctionhouse` | Open and operate the Auction House. |
| `/orders` | — | `/orders [my\|collect\|reload]` | `corenyadonutsmp.command.orders`; `reload` requires `corenyadonutsmp.admin.orders` | Open the Orders board, view your own orders, or collect fulfilled orders. |

### `/auctionhouse` subcommands

Each action is checked against `corenyadonutsmp.auctionhouse.<action>` **or** the legacy alias `donutauction.<action>`; holding `corenyadonutsmp.admin.auctionhouse` satisfies any of them.

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| *(none)* | — | `corenyadonutsmp.auctionhouse.use` / `donutauction.use` | Open the browse menu. |
| `search` | `<item>` | `corenyadonutsmp.auctionhouse.use` / `donutauction.use` | Open the browse menu already filtered to that item. Same 64 character cap as the sign search. |
| `sell` | `<price>` | `corenyadonutsmp.auctionhouse.sell` / `donutauction.sell` | List the held item. Subject to the listing limit tier. |
| `my` | — | `corenyadonutsmp.auctionhouse.my` / `donutauction.my` | View your active listings. |
| `claims` | — | `corenyadonutsmp.auctionhouse.claims` / `donutauction.claims` | View and collect sale proceeds and expired items. |
| `cancel` | — | `corenyadonutsmp.auctionhouse.cancel` / `donutauction.cancel` | Cancel one of your own listings. |
| `limit` | — | `corenyadonutsmp.auctionhouse.limit` / `donutauction.limit` | Report your current listing limit. Not listed in the `plugin.yml` usage string. |
| `fastbuy` | — | `corenyadonutsmp.auctionhouse.fastbuy` / `donutauction.fastbuy` | Toggle the confirmation-free buying preference. Not declared in `plugin.yml`. |
| `fastsell` | — | `corenyadonutsmp.auctionhouse.fastsell` / `donutauction.fastsell` | Toggle the confirmation-free selling preference. Not declared in `plugin.yml`. |
| `reload` | — | `corenyadonutsmp.admin.auctionhouse` | Reload Auction House settings. |

Words that are not one of those subcommands are a search as well. `/ah neth boot prot 4` opens the browser on netherite boots with Protection IV or higher, and `/ah search neth boot prot 4` does the same thing. `prot4` and `prot iv` count as Protection IV.

Buying from the browse menu is checked against `corenyadonutsmp.auctionhouse.buy` / `donutauction.buy`.

## Teleportation & Homes

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/home` | — | `/home [name]` | `corenyadonutsmp.command.home` | Teleport to one of your homes. |
| `/homes` | — | `/homes` | `corenyadonutsmp.command.homes` | Open the homes dialog. |
| `/sethome` | — | `/sethome [name]` | `corenyadonutsmp.command.sethome` | Create a home at your position, subject to your home slot tier. |
| `/delhome` | — | `/delhome <name>` | `corenyadonutsmp.command.delhome` | Delete one of your homes. |
| `/renamehome` | — | `/renamehome <old> <new>` | `corenyadonutsmp.command.renamehome` | Rename one of your homes. |
| `/spawn` | — | `/spawn [set\|setspawn\|setup]` | `corenyadonutsmp.command.spawn`; the sub-arguments require `corenyadonutsmp.command.setspawn` or `corenyadonutsmp.admin.setup` | Teleport to spawn, or set the spawn location. |
| `/setspawn` | — | `/setspawn` | `corenyadonutsmp.command.setspawn`; also accepts `corenyadonutsmp.admin.setup` | Set the server spawn location. |
| `/afk` | — | `/afk [set\|setafk\|setup]` | `corenyadonutsmp.command.afk`; the sub-arguments require `corenyadonutsmp.command.setafk` or `corenyadonutsmp.admin.setup` | Go to the AFK zone, or set its location. |
| `/setafk` | — | `/setafk` | `corenyadonutsmp.command.setafk`; also accepts `corenyadonutsmp.admin.setup` | Set the AFK zone location. |
| `/rtp` | — | `/rtp [world]` | `corenyadonutsmp.command.rtp` | Random teleport, with cooldown resolved from the RTP cooldown tier. |
| `/rtpq` | `/rtpqueue` | `/rtpq [join\|leave]` | `corenyadonutsmp.command.rtpq` | Join or leave the RTP matchmaking queue. Queue position uses the RTP priority tier. |
| `/warp` | — | `/warp [name]` | `corenyadonutsmp.command.warp` | Teleport to a public warp, or open the warp list. |
| `/warpmanager` | — | `/warpmanager <create\|delete\|list> [name]` | `corenyadonutsmp.command.warpmanager`; action checks `corenyadonutsmp.admin.warpmanager`, `.setwarp`, `.delwarp` | Manage public warps. |
| `/setwarp` | — | `/setwarp <name>` | `corenyadonutsmp.command.setwarp`; action check `corenyadonutsmp.admin.setwarp` | Create a warp at your position. Shares the executor with `/warpmanager`. |
| `/delwarp` | — | `/delwarp <name>` | `corenyadonutsmp.command.delwarp`; action check `corenyadonutsmp.admin.delwarp` | Delete a warp. Shares the executor with `/warpmanager`. |
| `/portalmanager` | — `/portalmanager <list\|info\|create\|delete\|setcuboid\|setdestination\|setdisplay\|toggle\|setpriority\|sethologramhere>` | see Usage | `corenyadonutsmp.command.portalmanager`; action check `corenyadonutsmp.admin.portalmanager` | Manage RTP portal triggers. |
| `/tpa` | — | `/tpa <player>` | `corenyadonutsmp.command.tpa` | Request to teleport to a player. |
| `/tpahere` | — | `/tpahere <player>` | `corenyadonutsmp.command.tpahere` | Request that a player teleports to you. |
| `/tpaccept` | — | `/tpaccept [player]` | `corenyadonutsmp.command.tpaccept` | Accept a pending teleport request. A name accepts that player. No name accepts the newest request. |
| `/tpadeny` | — | `/tpadeny [player]` | `corenyadonutsmp.command.tpadeny` | Deny a pending teleport request. A name denies that player. No name denies the newest request. |
| `/tpacancel` | — | `/tpacancel` | `corenyadonutsmp.command.tpacancel` | Cancel your outgoing teleport requests. |
| `/tpauto` | — | `/tpauto` | `corenyadonutsmp.command.tpauto` | Toggle automatic acceptance of `/tpa` requests. While it is on, the action bar says you have tpauto on. Dying turns it off. |
| `/tpahereauto` | — | `/tpahereauto` | `corenyadonutsmp.command.tpahereauto` | Toggle automatic acceptance of `/tpahere` requests. |

`/tpa`, `/tpahere`, `/tpaccept`, `/tpadeny` and `/tpacancel` are all served by a single executor that dispatches on the command label. More than one request can be waiting. Naming the player accepts or denies that request, and the others stay until they expire or you act on them.

### `/portalmanager` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| `list` | — | `corenyadonutsmp.admin.portalmanager` | List configured portal triggers. |
| `info` | `<portal>` | `corenyadonutsmp.admin.portalmanager` | Show one portal's configuration. |
| `create` | `<portal>` | `corenyadonutsmp.admin.portalmanager` | Create a portal trigger. |
| `delete` | `<portal>` | `corenyadonutsmp.admin.portalmanager` | Delete a portal trigger. |
| `setcuboid` | `<portal> <cuboid>` | `corenyadonutsmp.admin.portalmanager` | Bind the trigger to a cuboid region. |
| `setdestination` | `<portal> <destination>` | `corenyadonutsmp.admin.portalmanager` | Set where the portal sends players. |
| `setdisplay` | `<portal> <display...>` | `corenyadonutsmp.admin.portalmanager` | Set the portal's display name. |
| `toggle` | `<portal>` | `corenyadonutsmp.admin.portalmanager` | Enable or disable the trigger. |
| `setpriority` | `<portal> <number>` | `corenyadonutsmp.admin.portalmanager` | Set evaluation priority when regions overlap. |
| `sethologramhere` | `<portal>` | `corenyadonutsmp.admin.portalmanager` | Move the portal hologram to your position. |

### `/warpmanager` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| `create` | `<name>` | `corenyadonutsmp.admin.setwarp` or `corenyadonutsmp.admin.warpmanager` | Create a warp at your position. |
| `delete` | `<name>` | `corenyadonutsmp.admin.delwarp` or `corenyadonutsmp.admin.warpmanager` | Delete a warp. |
| `list` | — | `corenyadonutsmp.admin.warpmanager` | List all warps. |

## Crates & Spawners

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/crate` | — | `/crate <create\|delete\|type\|open\|keys\|reload\|key\|take\|set\|keyall\|add\|edit\|remove\|bind\|unbind\|listbound\|info>` | `corenyadonutsmp.command.crate`; action checks `corenyadonutsmp.admin.crate`, `.crate.reload`, `.crate.keyall` | Full crate management. |
| `/crates` | — | `/crates` | `corenyadonutsmp.command.crates` | Open the crates menu. Same executor as `/crate`. |
| `/keys` | — | `/keys` | `corenyadonutsmp.command.keys`; falls back to `corenyadonutsmp.command.crates` | Open your crate keys menu. Same executor as `/crate`. |
| `/spawner` | `/spawners` | `/spawner [give\|info\|panel\|reload\|remove\|split]` | `corenyadonutsmp.command.spawner`; action check `corenyadonutsmp.admin.spawner` | Manage Donut-style spawners. |
| `/amethysttool` | — | `/amethysttool give <player> <type> [duration]` or `/amethysttool reload` | `corenyadonutsmp.command.amethysttool`; action check `corenyadonutsmp.admin.amethysttool` | Give and manage timed amethyst tools. |

### `/crate` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| `create` | `<id>` | `corenyadonutsmp.admin.crate` | Create a crate definition. |
| `delete` | `<id>` | `corenyadonutsmp.admin.crate` | Delete a crate definition. |
| `type` | `<id> <type>` | `corenyadonutsmp.admin.crate` | Set the crate's opening type. |
| `open` | `<id>` | Command gate only | Open a crate you hold keys for. |
| `keys` | `[player]` | Command gate; `corenyadonutsmp.admin.crate` to view others | Show key balances. |
| `key` | `<player> <id> <amount>` | `corenyadonutsmp.admin.crate` | Add keys to a player. |
| `take` | `<player> <id> <amount>` | `corenyadonutsmp.admin.crate` | Remove keys from a player. |
| `set` | `<player> <id> <amount>` | `corenyadonutsmp.admin.crate` | Set a player's key balance. |
| `keyall` | `<id> <amount>` | `corenyadonutsmp.admin.crate.keyall` | Give keys to every online player. |
| `add` | `<id>` | `corenyadonutsmp.admin.crate` | Open the GUI editor. |
| `add` | `<id> <slot>` | `corenyadonutsmp.admin.crate` | Add the held item as a reward at that slot. |
| `add` | `<id> <slot> command <cmd…>` | `corenyadonutsmp.admin.crate` | Add a console-command reward. `{player}` is replaced when it runs. |
| `add` | `<id> <slot> money <amount>` | `corenyadonutsmp.admin.crate` | Add a money reward. |
| `add` | `<id> <slot> shards <amount>` | `corenyadonutsmp.admin.crate` | Add a shard reward. |
| `edit` | `<id> [slot]` | `corenyadonutsmp.admin.crate` | Edit rewards. No slot opens the GUI. |
| `remove` | `<id> <slot>` | `corenyadonutsmp.admin.crate` | Remove the reward in that slot. |
| `bind` | `<id>` | `corenyadonutsmp.admin.crate` | Bind a crate to the chest you are looking at. |
| `unbind` | `[id]` | `corenyadonutsmp.admin.crate` | Remove a chest binding. |
| `listbound` | — | `corenyadonutsmp.admin.crate` | List every bound chest. Not listed in the `plugin.yml` usage string. |
| `info` | — | Command gate only | Show crate information. |
| `reload` | — | `corenyadonutsmp.admin.crate.reload` | Reload crate configuration. |

In the GUI editor, naming a placeholder item `[CMD] …`, `[MONEY] <amount>` or `[SHARDS] <amount>` creates the matching non-item reward when you place it in a slot. See [Crates & Spawners](Crates-and-Spawners).

### `/spawner` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| `give` | `<player> <type> [amount]` | `corenyadonutsmp.admin.spawner` | Give a managed spawner item. |
| `info` | — | `corenyadonutsmp.admin.spawner` | Inspect the spawner you are looking at. |
| `panel` | — | `corenyadonutsmp.admin.spawner` | Open the spawner management panel. |
| `split` | `<amount>` | `corenyadonutsmp.admin.spawner` | Split a stacked spawner. |
| `remove`, `forcebreak` | — | `corenyadonutsmp.admin.spawner` | Force-remove the targeted spawner. |
| `reload` | — | `corenyadonutsmp.admin.spawner` | Reload spawner configuration. |

Breaking a managed spawner normally requires a Silk Touch pickaxe when `REQUIRE_SILK_TOUCH` is enabled; `corenyadonutsmp.spawner.bypass` (default `false`) waives that requirement.

## PvP: Duels, FFA and Ranked

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/duel` | — | `/duel [player\|accept\|deny\|claims\|reload]` | `corenyadonutsmp.command.duel`; `reload` requires `corenyadonutsmp.admin.duels` | Challenge a player or manage your duel state. |
| `/create` | — | `/create <invite\|friends> <player> [map]` | `corenyadonutsmp.command.create` | Create a duel challenge with an explicit privacy mode. Belongs to the `DUELS` feature. |
| `/queue` | — | `/queue [join\|leave]` | `corenyadonutsmp.command.queue` | Open, join or leave the duel queue. |
| `/draw` | — | `/draw` | `corenyadonutsmp.command.draw` | Request or accept a duel draw. |
| `/leave` | — | `/leave` | `corenyadonutsmp.command.leave` | Leave a duel or FFA queue or match. |
| `/arena` | `/duelarena` | `/arena <create\|delete\|setpos1\|setpos2\|setreturn\|setdisplay\|enable\|disable\|queue\|list\|reload>` | `corenyadonutsmp.command.arena`; action check `corenyadonutsmp.admin.duels` | Manage duel arenas. |
| `/ffa` | — | `/ffa [join\|reload\|arena ...\|help]` | `corenyadonutsmp.command.ffa`; `reload` and `arena` require `corenyadonutsmp.admin.ffa` | Join the instanced FFA queue. |
| `/ffaarena` | — | `/ffaarena <create\|delete\|setpos\|setpos1\|setpos2\|setspawn1\|setspawn2\|setreturn\|setdisplay\|settings\|enable\|disable\|list\|reload>` | `corenyadonutsmp.command.ffaarena`; action check `corenyadonutsmp.admin.ffa` | Manage FFA arenas. |
| `/ffastats` | — | `/ffastats [player]` | `corenyadonutsmp.command.ffastats` | View FFA info (FFA currently does not track victories, defeats, draws, or streaks). |
| `/pvp` | — | see the subcommand table below | `corenyadonutsmp.command.pvp`; administrative subcommands require `corenyadonutsmp.admin.pvp` | Ranked PvP arena: play, inspect and administer. |

### `/pvp` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| `join` | — | Command gate only | Join the ranked arena. |
| `leave` | — | Command gate only | Leave the arena or queue. |
| `kit` | `[kit]`, or `create\|edit\|delete\|icon\|permission\|slot\|display ...` | Editing requires `corenyadonutsmp.admin.pvp` | Select a kit, or manage kit definitions. |
| `stats` | `[player]` | Command gate only | Show ranked statistics. |
| `top` | `[page]` | Command gate only | Show the ranked ladder. |
| `leaderboard`, `lb` | — | Command gate only | Open the leaderboard menu. |
| `queue` | `[leave]` | Command gate only | Show or leave the ranked queue. |
| `history` | `[player]` | Command gate only | Show recent ranked matches. |
| `sync` | — | Command gate only | Generate a Discord sync code to link account. |
| `assign` | `[player] [player] [kit]` | `corenyadonutsmp.admin.pvp` | Start a match between two online players (or open assign menu). |
| `wand` | — | `corenyadonutsmp.admin.pvp` | Receive the arena selection wand. |
| `create` | `<name>` | `corenyadonutsmp.admin.pvp` | Create an arena from the current selection. |
| `setspawn`, `setspawn2` | — | `corenyadonutsmp.admin.pvp` | Set the two duel spawn points. |
| `setlobby` | — | `corenyadonutsmp.admin.pvp` | Set the arena lobby. |
| `setboundary` | — | `corenyadonutsmp.admin.pvp` | Set the arena boundary from the selection. |
| `schematic` | `<name>`, `load` | `corenyadonutsmp.admin.pvp` | Save or load an arena schematic. |
| `reset` | — | `corenyadonutsmp.admin.pvp` | Reset the arena structure from schematic. |
| `reload` | — | `corenyadonutsmp.admin.pvp` | Reload ranked arena configuration. |

Individual kits may additionally carry their own permission string, set with `/pvp kit permission <kit> <node|none>`.

### `/arena` and `/ffaarena` subcommands

Both commands require `corenyadonutsmp.admin.duels` and `corenyadonutsmp.admin.ffa` respectively for every subcommand, checked once on entry.

| Subcommand | Available on | Arguments | Effect |
|------------|--------------|-----------|--------|
| `list` | both | — | List arenas. |
| `create` | both | `<name>` | Create an arena. |
| `delete` | both | `<name>` | Delete an arena. |
| `enable` / `disable` | both | `<name>` | Toggle arena availability. |
| `setdisplay` | both | `<name> <display...>` | Set the arena display name. |
| `setpos1` / `setpos2` | both | `<name>` | Set the arena's corner positions. `/ffaarena` also accepts `setpos`. |
| `setreturn` | both | `<name>` | Set the return location used when a match ends. |
| `queue` | `/arena` | `<name>` | Inspect or configure the arena queue. |
| `setspawn1` / `setspawn2` | `/ffaarena` | `<name>` | Set FFA spawn points. |
| `settings` | `/ffaarena` | `<name> <nohunger\|noweather\|alwaysmorning\|nofalldamage> <on\|off>` | Toggle per-arena rules. |
| `reload` | both | — | Reload arena configuration. |

## Social & Chat

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/chat` | — | `/chat <help\|mute\|unmute\|delay\|clear>` | `corenyadonutsmp.command.chat`; each action has its own `corenyadonutsmp.staff.chat.*` node | Manage global chat. With no arguments, shows help. |
| `/msg` | `/message`, `/tell`, `/whisper`, `/w` | `/msg <player> <message>` | `corenyadonutsmp.command.msg`; action check `corenyadonutsmp.message` | Send a private message. |
| `/reply` | `/r` | `/reply <message>` | `corenyadonutsmp.command.reply`; action check `corenyadonutsmp.message` | Reply to your last private conversation. |
| `/pm` | `/togglepm`, `/privatemessages` | `/pm` | `corenyadonutsmp.command.pm`; action check `corenyadonutsmp.message.toggle` | Toggle whether you receive private messages. |
| `/ignore` | — | `/ignore <player\|list>` | `corenyadonutsmp.command.ignore`; action check `corenyadonutsmp.ignore` | Add a player to your ignore list, or list it. |
| `/unignore` | — | `/unignore <player>` | `corenyadonutsmp.command.unignore`; action check `corenyadonutsmp.ignore` | Remove a player from your ignore list. |
| `/helpop` | — | `/helpop <message>` | `corenyadonutsmp.command.helpop`; action check `corenyadonutsmp.helpop` | Request staff assistance. |
| `/report` | — | `/report <player> <reason>` | `corenyadonutsmp.command.report`; action check `corenyadonutsmp.report` | Report a player to online staff. |
| `/team` | — | `/team <create\|disband\|invite\|kick\|join\|leave\|home\|sethome\|delhome\|chat\|info\|pvp>` | `corenyadonutsmp.command.team` | Team creation, membership, team home and team chat. |
| `/friend` | — | `/friend [list\|friends\|following\|followers\|add\|remove\|search\|reload]` | `corenyadonutsmp.command.friend`; action check `corenyadonutsmp.friends`; `reload` requires `donutfriends.admin` | Friends and follows. Same executor as `/friends`. |
| `/friends` | — | `/friends [list\|friends\|following\|followers\|add\|remove\|search\|reload]` | `corenyadonutsmp.command.friends`; action check `corenyadonutsmp.friends`; `reload` requires `donutfriends.admin` | Friends and follows. |
| `/discord` | — | `/discord` | `corenyadonutsmp.command.discord` | Show the Discord link from `SOCIAL.DISCORD`. |
| `/twitter` | — | `/twitter` | `corenyadonutsmp.command.twitter` | Show the Twitter/X link from `SOCIAL.TWITTER`. |
| `/store` | — | `/store` | `corenyadonutsmp.command.store` | Show the store link from `SOCIAL.STORE`. |
| `/social` | `/media` | `/social` | `corenyadonutsmp.command.social` | Show all configured social links. |
| `/voicechatconsent` | `/vcconsent`, `/voiceconsent` | `/voicechatconsent [revoke]` | `corenyadonutsmp.command.voicechatconsent` | Open the voice chat consent menu, or revoke consent. |

`/discord`, `/twitter`, `/store` and `/social` share a single executor that selects the configuration key from the command label.

A received `/msg` is clickable when the sender is a player. The click puts `/msg <name> ` into the chat box, with a space after the name, so the reply can be typed immediately. That name is the one other players see, including a disguise alias. The receiver also hears `PRIVATE_MESSAGE.RECEIVED` from `sounds.yml`, unless notification sounds are off in `/settings` or that sound value is empty.

### `/chat` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| *(none)*, `help` | — | Any of the five `staff.chat.*` nodes below | Show the chat help text. |
| `mute` | — | `corenyadonutsmp.staff.chat.mute` | Mute global chat. |
| `unmute` | — | `corenyadonutsmp.staff.chat.unmute` | Unmute global chat. |
| `delay` | `<seconds\|off>` | `corenyadonutsmp.staff.chat.delay` | Set or clear the global chat delay, bounded by the configured maximum. |
| `clear` | — | `corenyadonutsmp.staff.chat.clear` | Visually clear global chat. |

Entry to `/chat` requires at least one of `corenyadonutsmp.staff.chat.use`, `.mute`, `.unmute`, `.delay` or `.clear`. The three bypass nodes `corenyadonutsmp.staff.chat.bypass.mute`, `.bypass.delay` and `.bypass.filter` exempt a player from the corresponding restriction.

### `/team` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| `create` | `<name>` | Command gate only | Create a team. |
| `disband` | — | Command gate only | Disband your team (leader only). |
| `invite` | `<player>` | Command gate only | Invite a player. |
| `join` | `<team>` | Command gate only | Accept a pending invite. |
| `leave` | — | Command gate only | Leave your team. |
| `kick` | `<player>` | Command gate only | Remove a member. |
| `home` / `sethome` / `delhome` | — | Command gate only | Use, set or delete the team home. |
| `chat` | — | Command gate only | Toggle team chat. |
| `info` | `[team]` | Command gate only | Show team information. |
| `pvp` | — | Command gate only | Toggle team-internal PvP. |

## Player Settings & Info

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/menu` | `/donut`, `/dialog` | `/menu` | `corenyadonutsmp.command.menu` | Open the main dialog menu. |
| `/settings` | — | `/settings` | `corenyadonutsmp.command.settings` | Open your personal settings menu. |
| `/stats` | — | `/stats [player]` | `corenyadonutsmp.command.stats` | View player statistics. |
| `/ping` | — | `/ping [player]` | `corenyadonutsmp.command.ping` | Check your latency or another online player's. |
| `/playtime` | `/pt` | `/playtime [player]` | `corenyadonutsmp.command.playtime` | Check playtime. |
| `/leaderboard` | `/lb`, `/top`, `/leaderboards`, `/baltop` | `/leaderboard [type]` | `corenyadonutsmp.command.leaderboard` | View leaderboards. |
| `/ranks` | `/rank` | `/ranks` | `corenyadonutsmp.command.ranks` | View rank perks. |
| `/rules` | — | `/rules` | `corenyadonutsmp.command.rules` | View server rules. |
| `/help` | — | `/help` | `corenyadonutsmp.command.help` | View the plugin help menu. |
| `/nightvision` | `/nv` | `/nightvision` | `corenyadonutsmp.command.nightvision` | Toggle night vision. |
| `/phantom` | — | `/phantom` | `corenyadonutsmp.command.phantom` | Toggle phantom spawning for yourself. |
| `/enderchest` | `/ec` | `/enderchest [reload]` | `corenyadonutsmp.command.enderchest`; also referenced as `corenyadonutsmp.enderchest`; `reload` requires `corenyadonutsmp.admin.enderchest` | Open your custom Ender Chest, sized by your row tier. |
| `/safety` | — | `/safety [reload\|add\|give]` | `corenyadonutsmp.command.safety`; action checks `safety.use`, `safety.add`, `safety.reload` | Safety information and safety item handling. |

`/safety` is unusual in that its runtime nodes live in a bare `safety.*` namespace rather than under `corenyadonutsmp.`. None of `safety.use`, `safety.add` or `safety.reload` is declared in `plugin.yml`.

## Staff & Moderation

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/staffmode` | `/staff` | `/staffmode [player\|reload]` | `corenyadonutsmp.command.staffmode`; `reload` uses `corenyadonutsmp.admin.staffmode`; toggling others uses `corenyadonutsmp.staff.mode.others` | Toggle the staff moderation hotbar. |
| `/stafflist` | — | `/stafflist` | `corenyadonutsmp.command.stafflist`; hotbar equivalent `corenyadonutsmp.staff.mode.stafflist` | Open the online staff list. |
| `/staffchat` | `/sc` | `/staffchat <message>` | `corenyadonutsmp.command.staffchat`; action check `corenyadonutsmp.staff.chat.use` | Send a message to network staff chat. |
| `/vanish` | — | `/vanish` | `corenyadonutsmp.command.vanish`; hotbar equivalent `corenyadonutsmp.staff.mode.vanish` | Toggle vanish while in staff mode. |
| `/freeze` | — | `/freeze <player>` or `/freeze reload` | `corenyadonutsmp.command.freeze`; runtime nodes `corenyadonutsmp.staff.freeze`, `.freeze.alert`, `.freeze.exempt`; `reload` uses `corenyadonutsmp.admin.freeze` | Freeze or unfreeze a player. |
| `/invsee` | `/inventorysee` | `/invsee <player>` or `/invsee reload` | `corenyadonutsmp.command.invsee`; runtime nodes `corenyadonutsmp.staff.invsee`, `.invsee.modify`; `reload` uses `corenyadonutsmp.admin.invsee` | View another player's inventory. |
| `/ecsee` | — | `/ecsee <player>` | `corenyadonutsmp.command.ecsee`; editing requires `corenyadonutsmp.admin.ecsee.edit` | Inspect another player's custom Ender Chest. |
| `/profileviewer` | `/pv` | `/profileviewer <player>` | `corenyadonutsmp.command.profileviewer`; runtime node `corenyadonutsmp.staff.profileviewer` | View a player's profile and homes. |
| `/seehomes` | `/homesee` | `/seehomes <player>` | `corenyadonutsmp.command.seehomes`; action check `corenyadonutsmp.staff.seehomes` | Browse another player's homes and teleport to them. |
| `/alts` | — | `/alts <player>` | `corenyadonutsmp.command.alts`; action check `corenyadonutsmp.staff.alts` | View alternate accounts by shared IP history. |
| `/chatlog` | — | `/chatlog [player]` | `corenyadonutsmp.command.chatlog`; action check `corenyadonutsmp.admin.chatlog` | Browse logged public chat, server-wide or per player. |
| `/logs` | — | `/logs [player]` | `corenyadonutsmp.command.logs`; action check `corenyadonutsmp.admin.logs` | Browse logged shop, auction and admin activity. |
| `/teleport` | `/tp`, `/tphere`, `/tpall` | `/teleport <player>`, `/teleport here <player>`, `/teleport all`, `/teleport top`, `/teleport <x> <y> <z> [world]` | `corenyadonutsmp.command.teleport`; action check `corenyadonutsmp.staff.teleport` | Staff teleport suite. |
| `/randomteleport` | `/randomtp` | `/randomteleport` | `corenyadonutsmp.command.randomteleport`; hotbar equivalent `corenyadonutsmp.staff.mode.randomtp` | Teleport to a random online player. |
| `/findplayer` | `/fp` | `/findplayer <player>` | `corenyadonutsmp.command.findplayer` | Report a player's current location. |
| `/fly` | — | `/fly [player]` | `corenyadonutsmp.command.fly`; action check `corenyadonutsmp.staff.fly`; player-facing node configurable via `FLY-SYSTEM.PLAYER-FLY-PERMISSION`, default `corenyadonutsmp.player.fly` | Toggle flight for yourself or another player. |
| `/flyspeed` | `/fs` | `/flyspeed <1-10> [player]` | `corenyadonutsmp.command.flyspeed`; also accepts `corenyadonutsmp.staff.flyspeed` or `corenyadonutsmp.staff.fly`; affecting others needs `corenyadonutsmp.command.flyspeed.others` | Adjust flying speed. |
| `/heal` | — | `/heal [player]` | `corenyadonutsmp.command.heal`; action check `corenyadonutsmp.staff.heal` | Restore health. |
| `/feed` | — | `/feed [player]` | `corenyadonutsmp.command.feed`; action check `corenyadonutsmp.staff.feed` | Restore hunger. |
| `/gamemode` | `/gm`, `/gmc`, `/gms`, `/gma`, `/gmsp` | `/gamemode <survival\|creative\|adventure\|spectator\|0-3> [player]` | `corenyadonutsmp.command.gamemode`; action checks `corenyadonutsmp.staff.gamemode` and `.gamemode.others` | Change game mode. The short aliases imply the mode. |
| `/god` | `/godmode` | `/god [player]` | `corenyadonutsmp.staff.god` | Toggle god mode. This command has no `corenyadonutsmp.command.*` node. |
| `/rename` | — | `/rename <name...>` or `/rename <reset\|clear\|remove>` | `corenyadonutsmp.command.rename`; action check `corenyadonutsmp.staff.rename` | Rename the item in your hand. |
| `/hide` | — | `/hide [status\|check <player>\|scramble\|remove [player]\|list]` | `corenyadonutsmp.command.hide`; feature nodes `corenyadonutsmp.hide.scramble`, `.disguise`, `.admin`, `.bypass` | Persistent identity scrambling. With no arguments, opens the hide menu. |
| `/disguise` | — | `/disguise [<alias>] [skin]` | `corenyadonutsmp.command.disguise`; feature nodes as for `/hide` | Apply a configured disguise. With no arguments, opens the disguise alias menu. |
| `/fakeplayer` | `/fplayer` | `/fakeplayer` | `corenyadonutsmp.command.fakeplayer`; runtime check `corenyadonutsmp.staff.fakeplayer` (legacy prefix) | Manage fake players used for bait and testing. |
| `/spawnstash` | `/stash` | `/spawnstash [spawn <type>\|list\|remove <id>\|reload]` | `corenyadonutsmp.command.spawnstash`; runtime checks `corenyadonutsmp.staff.spawnstash` and `corenyadonutsmp.admin.spawnstash` | Spawn and manage staff bait stashes. |
| `/amod` | — | `/amod` | `corenyadonutsmp.command.amod`; action check `anvilmod.admin` | Anvil moderation tooling. |

### `/hide` and `/disguise` subcommands

| Subcommand | Command | Arguments | Permission | Effect |
|------------|---------|-----------|------------|--------|
| *(none)* | `/hide` | — | Command gate only | Open the hide menu. |
| `status`, `check` | `/hide` | `[player]` | Own status: command gate. Another player: `corenyadonutsmp.hide.admin` | Show hide state. |
| `scramble` | `/hide` | — | `corenyadonutsmp.hide.scramble` | Generate and apply a scrambled identity. |
| `remove`, `removal` | `/hide` | `[player]` | Own: command gate. Another player: `corenyadonutsmp.hide.admin` | Clear a hide state. |
| `list` | `/hide` | — | `corenyadonutsmp.hide.admin` | List all hidden players. |
| *(none)* | `/disguise` | — | Command gate only | Open the disguise alias menu. |
| *(alias)* | `/disguise` | `<alias> [skin]` | `corenyadonutsmp.hide.disguise` | Apply a disguise, resolving a skin username or skin URL. |

`corenyadonutsmp.hide.bypass` lets a holder see through other players' hidden identities. None of the four `corenyadonutsmp.hide.*` nodes is declared in `plugin.yml`.

### `/spawnstash` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| *(none)* | — | `corenyadonutsmp.staff.spawnstash` | Show stash status and usage. |
| `spawn` | `<type>` | `corenyadonutsmp.staff.spawnstash` | Spawn a bait stash of the given type. |
| `list` | — | `corenyadonutsmp.staff.spawnstash` | List active stashes. |
| `remove` | `<id>` | `corenyadonutsmp.admin.spawnstash` | Remove a stash and roll back its blocks. |
| `reload` | — | `corenyadonutsmp.admin.spawnstash` | Reload SpawnStash configuration. |

`corenyadonutsmp.staff.spawnstash.alert` receives stash trip alerts, and `corenyadonutsmp.staff.spawnstash.bypass` prevents a holder from triggering one.

## Punishments

All punishment commands share one executor, which dispatches on the command label and checks a per-action node. Because each `corenyadonutsmp.command.<name>` node is declared as a parent of the matching `corenyadonutsmp.staff.punishments.*` child, granting the command node is sufficient in a default setup.

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/ban` | — | `/ban <player> [reason]` | `corenyadonutsmp.staff.punishments.ban` | Permanently ban a player and record the punishment. |
| `/tempban` | — | `/tempban <player> <time> [reason]` | `corenyadonutsmp.staff.punishments.ban` | Temporarily ban a player. Time accepts `30s`, `15m`, `2h`, `5d` or compound forms such as `5d 15m 30s`. |
| `/unban` | `/pardon` | `/unban <player> [reason]` | `corenyadonutsmp.staff.punishments.unban` | Remove active ban records. |
| `/mute` | — | `/mute <player> [reason]` | `corenyadonutsmp.staff.punishments.mute` | Permanently mute a player. |
| `/tempmute` | — | `/tempmute <player> <time> [reason]` | `corenyadonutsmp.staff.punishments.mute` | Temporarily mute a player. |
| `/unmute` | — | `/unmute <player> [reason]` | `corenyadonutsmp.staff.punishments.unmute` | Remove active mute records. |
| `/vcmute` | — | `/vcmute <player> [reason]` | `corenyadonutsmp.staff.vcmute` | Mute a player in voice chat. |
| `/vcunmute` | — | `/vcunmute <player> [reason]` | `corenyadonutsmp.staff.vcunmute` | Remove active voice chat mute records. |
| `/blacklist` | — | `/blacklist <player> [reason]` | `corenyadonutsmp.staff.punishments.blacklist` | Blacklist a player. |
| `/unblacklist` | — | `/unblacklist <player> [reason]` | `corenyadonutsmp.staff.punishments.unblacklist` | Remove active blacklist records. |
| `/warn` | — | `/warn <player> [reason]` | `corenyadonutsmp.staff.punishments.create` | Warn a player and record the punishment. |
| `/kick` | — | `/kick <player> [reason]` | `corenyadonutsmp.staff.punishments.create` | Kick a player and record the punishment. |
| `/offend` | — | `/offend <player> <reason> [time] [wipe]` | `corenyadonutsmp.staff.punishments.offend`; also checks `corenyadonutsmp.staff.punishments.create` | Apply a preset offence from `offenses.yml`, with an optional duration and wipe override (`wipe`/`true`/`nowipe`/`false`). |
| `/punishments` | `/phistory` | `/punishments [player]` | `corenyadonutsmp.command.punishments`; GUI actions use `corenyadonutsmp.staff.punishments.view` and `.delete` | Browse punishment history, server-wide or per player. |

Two nodes govern who may be punished: a target holding `corenyadonutsmp.admin.punishments.exempt` cannot be punished, unless the issuing staff member holds `corenyadonutsmp.admin.punishments.exempt.bypass`.

## Network & Maintenance

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/servers` | — | `/servers` | `corenyadonutsmp.command.servers`; action check `corenyadonutsmp.servers` | View network server status. |
| `/maintenance` | — | `/maintenance [on\|off\|status\|setlobby]` | `corenyadonutsmp.command.maintenance`; every subcommand requires `corenyadonutsmp.admin.maintenance` | Toggle maintenance mode and set the redirect lobby. |
| `/clearlag` | — | `/clearlag` | `corenyadonutsmp.command.clearlag`; action check `corenyadonutsmp.admin.clearlag` | Manually clear lag entities. |
| `/serverwipe` | — | `/serverwipe <preview\|status\|prepare\|confirm\|cancel>` | `corenyadonutsmp.command.serverwipe`; action check `corenyadonutsmp.admin.serverwipe` | Preview and run a full server data wipe. |
| `/playerwipe` | `/pwipe`, `/wipe` | `/playerwipe <player> [confirm]` | `corenyadonutsmp.command.playerwipe`; action check `corenyadonutsmp.admin.playerwipe` | Wipe everything the plugin stores about one player. |
| `/playerunwipe` | `/punwipe`, `/unwipe` | `/playerunwipe <player> [confirm]` | `corenyadonutsmp.command.playerunwipe`; action check `corenyadonutsmp.admin.playerunwipe` | Restore a wiped player from the backup their wipe created. |

### `/maintenance` subcommands

| Subcommand | Aliases | Arguments | Permission | Effect |
|------------|---------|-----------|------------|--------|
| `on` | `start`, `enable` | `[duration]` | `corenyadonutsmp.admin.maintenance` | Enter maintenance mode, optionally for a fixed period such as `15m`. |
| `off` | `stop`, `disable` | — | `corenyadonutsmp.admin.maintenance` | Leave maintenance mode. |
| `status` | — | — | `corenyadonutsmp.admin.maintenance` | Report maintenance state. |
| `setlobby` | — | `<server>` | `corenyadonutsmp.admin.maintenance` | Set the lobby that players are redirected to. |

### `/serverwipe` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| `preview` | — | `corenyadonutsmp.admin.serverwipe` | Report what a wipe would delete. |
| `status` | — | `corenyadonutsmp.admin.serverwipe` | Report the state of any staged wipe. |
| `prepare` | — | `corenyadonutsmp.admin.serverwipe` | Stage a wipe and print the confirmation token. |
| `confirm` | `<token>` | `corenyadonutsmp.admin.serverwipe` | Execute the staged wipe using the token from `prepare`. |
| `cancel` | — | `corenyadonutsmp.admin.serverwipe` | Discard the staged wipe. |

`/playerwipe` and `/playerunwipe` both preview by default and require `confirm` as a second argument to act.

## Admin & Utility

| Command | Aliases | Usage | Permission | Description |
|---------|---------|-------|------------|-------------|
| `/corenyadonutsmp` | `/cds`, `/cdsmp` | `/corenyadonutsmp <reload\|statswipe\|optimize\|setup\|features\|maintenance>` | `corenyadonutsmp.command.corenyadonutsmp`; each subcommand has its own `corenyadonutsmp.admin.*` node | Plugin administration entry point. |
| `/cuboid` | — | `/cuboid <wand\|create\|delete\|list\|setspawn\|delspawn\|bind\|reload>` | `corenyadonutsmp.command.cuboid`; action check `corenyadonutsmp.admin.cuboid` | Manage cuboid regions and bind them to systems. |
| `/kill` | — | `/kill` | `corenyadonutsmp.command.kill` | Open a confirmation menu to kill yourself. Blocked while combat-tagged. |

### `/corenyadonutsmp` subcommands

| Subcommand | Arguments | Permission | Effect |
|------------|-----------|------------|--------|
| `reload` | — | `corenyadonutsmp.admin.reload` | Reload every plugin configuration file. |
| `statswipe` | *(none)*, `menu`, `gui`, or `<target> confirm` | `corenyadonutsmp.admin.statswipe` | Open the stats wipe GUI, or wipe a named target directly. |
| `optimize`, `optimization` | `[status\|reload\|reset]` | `corenyadonutsmp.admin.optimize` | View optimisation status, reload optimisation settings, or reset runtime counters. Not documented in the `plugin.yml` usage string beyond `optimize`. |
| `setup` | `status`, `setspawn`, `setafk`, `commands <group>`, `apply single-paper confirm` | `corenyadonutsmp.admin.setup` | Run the guided setup tools. Command groups are `starter`, `economy`, `market`, `pvp`, `staff` and `admin`. |
| `features` | *(none)*, `menu`, `gui`, or `<enable\|disable\|toggle> <key>` | `corenyadonutsmp.admin.features` | Manage feature toggles from chat or the GUI. |
| `maintenance` | `[on\|off\|status\|setlobby <server>]` | `corenyadonutsmp.admin.maintenance` | Maintenance control, mirroring `/maintenance`. |

### `/cuboid` subcommands

| Subcommand | Aliases | Arguments | Permission | Effect |
|------------|---------|-----------|------------|--------|
| `wand` | — | — | `corenyadonutsmp.admin.cuboid` | Receive the selection wand. Left click sets position 1, right click position 2. |
| `create` | `save` | `<name>` | `corenyadonutsmp.admin.cuboid` | Save the current selection as a named cuboid. |
| `delete` | — | `<name>` | `corenyadonutsmp.admin.cuboid` | Delete a cuboid. |
| `list` | — | — | `corenyadonutsmp.admin.cuboid` | List cuboids. |
| `bind` | `system` | `<cuboid> <spawn\|shard\|rtp-zone\|rtp-queue> <true\|false>` | `corenyadonutsmp.admin.cuboid` | Bind or unbind a cuboid to a plugin system. The role accepts variants such as `shards`, `rtpzone` and `rtpq`. |
| `setspawn` | — | `<cuboid>` | `corenyadonutsmp.admin.cuboid` | Set the cuboid's spawn point. |
| `delspawn` | — | `<cuboid>` | `corenyadonutsmp.admin.cuboid` | Clear the cuboid's spawn point. |

## Numbered permission tiers

Six permission families end in a number. The plugin does not read the number from a configuration file; it probes the permissible for each candidate node in turn and derives a value from whichever nodes are present. In every case the node must be granted exactly — inheriting a wildcard does not produce a tier value.

| Family | Scanned range | Resolution | Effect |
|--------|---------------|-----------|--------|
| `corenyadonutsmp.homes.<N>` | 1–100 | Highest granted N | Maximum home slots. |
| `corenyadonutsmp.homes.page.<N>` | 1–100 | Highest granted N, multiplied by 5 | Maximum home GUI pages; five homes per page. Combined with the previous family by taking the larger result. |
| `corenyadonutsmp.rtp.cooldown.<seconds>` | any integer ≥ 0 | **Lowest** granted value | RTP cooldown override in seconds. The shortest cooldown wins, so `cooldown.10` beats `cooldown.60`. |
| `corenyadonutsmp.rtp.priority.<N>` | any integer | Highest granted N | RTP queue priority; higher jumps ahead. |
| `corenyadonutsmp.enderchest.rows.<N>` | 1–54 | Highest granted N, clamped to 1–6 | Ender Chest row count. Values above 6 clamp to the largest chest rather than falling back to the default. |
| `corenyadonutsmp.auctionhouse.limit.<N>` | 1–100 | Highest granted N | Auction House listing limit. |

Notes on how the tiers combine with configuration:

- Home slots also honour arbitrary permission-to-number mappings under `SETTINGS.HOME-PERMISSIONS.PERMISSIONS`, and the whole mechanism can be disabled with `SETTINGS.HOME-PERMISSIONS.ENABLED: false`. When no tier applies, `SETTINGS.HOME-DEFAULT` is used.
- Ender Chest rows also honour `ENDER-CHEST.ROW-PERMISSIONS.PERMISSIONS`, and `ENDER-CHEST.ROW-PERMISSIONS.ON-DOWNGRADE` decides whether items are returned (`RETURN-ITEMS`) or the chest keeps its old size (`KEEP-SIZE`) when a player loses a tier.
- RTP cooldowns also honour `SETTINGS.RANK-COOLDOWNS.PERMISSIONS`, again taking the lowest matching value, and fall back to the per-world cooldown.
- The Auction House limit resolver additionally scans the bare numeric family `corenyadonutsmp.auctionhouse.<N>` and the legacy `donutauction.limit.<N>`, and a configured mapping can return an unlimited value.

## Permission reference

Every node below is declared in `src/main/resources/plugin.yml`, 260 in total. Defaults are reproduced exactly as declared: `op` means operators only, `true` means everyone, `false` means nobody until explicitly granted.

### `corenyadonutsmp.admin.*`

`corenyadonutsmp.admin` (default `op`) is a parent that grants `corenyadonutsmp.command.*` and all forty children listed here.

| Node | Default | Description |
|------|---------|-------------|
| `corenyadonutsmp.admin` | op | Full admin access; parent of the entire family. |
| `corenyadonutsmp.admin.addmoney` | op | Add money to player balances. |
| `corenyadonutsmp.admin.amethysttool` | op | Manage and give amethyst tools. |
| `corenyadonutsmp.admin.auctionhouse` | op | Reload Auction House settings; also satisfies every per-action AH check. |
| `corenyadonutsmp.admin.chatlog` | op | Browse logged public chat messages. |
| `corenyadonutsmp.admin.clearlag` | op | Manually clear lag. |
| `corenyadonutsmp.admin.crate` | op | Manage crate balances, settings and chest bindings. |
| `corenyadonutsmp.admin.crate.keyall` | op | Trigger crate key-all rewards manually. |
| `corenyadonutsmp.admin.crate.reload` | op | Reload crate settings. |
| `corenyadonutsmp.admin.cuboid` | op | Manage cuboid regions. |
| `corenyadonutsmp.admin.delwarp` | op | Delete warp points. |
| `corenyadonutsmp.admin.duels` | op | Manage the duel system and arenas. |
| `corenyadonutsmp.admin.ecsee` | op | Declared without a description; the Ender Chest inspection admin node. |
| `corenyadonutsmp.admin.enderchest` | op | Reload Ender Chest settings. |
| `corenyadonutsmp.admin.features` | op | Manage feature toggles. |
| `corenyadonutsmp.admin.ffa` | op | Manage the FFA system and arenas. |
| `corenyadonutsmp.admin.freeze` | op | Reload and administer Freeze settings. |
| `corenyadonutsmp.admin.invsee` | op | Reload and administer Invsee settings. |
| `corenyadonutsmp.admin.logs` | op | Browse a player's logged shop, auction and admin activity. |
| `corenyadonutsmp.admin.maintenance` | op | Toggle maintenance mode and manage the allowed player list. |
| `corenyadonutsmp.admin.optimize` | op | View and reload runtime optimisation controls. |
| `corenyadonutsmp.admin.orders` | op | Reload and manage Orders settings. |
| `corenyadonutsmp.admin.playerunwipe` | op | Preview and restore a wiped player's stored data. |
| `corenyadonutsmp.admin.playerwipe` | op | Preview and wipe a single player's stored data. |
| `corenyadonutsmp.admin.portalmanager` | op | Manage RTP portal triggers. |
| `corenyadonutsmp.admin.punishments.exempt` | op | Cannot be punished by staff who lack the exempt bypass. |
| `corenyadonutsmp.admin.punishments.exempt.bypass` | op | Punish players who are otherwise exempt. |
| `corenyadonutsmp.admin.pvp` | op | Manage the ranked PvP arena and its kits. |
| `corenyadonutsmp.admin.reload` | op | Reload all CorenyaDonutSMP configuration files. |
| `corenyadonutsmp.admin.removemoney` | op | Remove money from player balances. |
| `corenyadonutsmp.admin.serverwipe` | op | Preview and run a full server data wipe. |
| `corenyadonutsmp.admin.setmoney` | op | Set player balances. |
| `corenyadonutsmp.admin.setup` | op | Run the setup tools and set the spawn and AFK zone locations. |
| `corenyadonutsmp.admin.setwarp` | op | Set warp points. |
| `corenyadonutsmp.admin.shards` | op | Shard administration and Shards Everywhere status. |
| `corenyadonutsmp.admin.shop` | op | Reload and validate shop settings. |
| `corenyadonutsmp.admin.spawner` | op | Manage internal spawner systems. |
| `corenyadonutsmp.admin.staffmode` | op | Reload and administer Staff Mode settings. |
| `corenyadonutsmp.admin.statswipe` | op | Open and execute Stats Wipe tools. |
| `corenyadonutsmp.admin.warpmanager` | op | Manage public warp points. |
| `corenyadonutsmp.admin.worth` | op | Reload worth settings. |

### `corenyadonutsmp.command.*`

`corenyadonutsmp.command.*` (default `op`) is a wildcard granting all 136 declared children. Defaults split into two groups.

Nodes with default `true` (available to everyone):

`afk`, `auctionhouse`, `balance`, `bounty`, `chat`, `crates`, `delhome`, `discord`, `draw`, `duel`, `enderchest`, `ffa`, `ffastats`, `friend`, `friends`, `help`, `helpop`, `home`, `homes`, `ignore`, `keys`, `leaderboard`, `leave`, `menu`, `meta`, `msg`, `nightvision`, `orders`, `pay`, `phantom`, `ping`, `playtime`, `pm`, `pvp`, `queue`, `ranks`, `renamehome`, `reply`, `report`, `rtp`, `rtpq`, `rules`, `sell`, `sellall`, `sellhand`, `sellhistory`, `sellmulti`, `sellprogress`, `sethome`, `settings`, `shardpay`, `shards`, `shardshop`, `shop`, `social`, `spawn`, `spawner`, `stats`, `store`, `team`, `tpa`, `tpacancel`, `tpaccept`, `tpadeny`, `tpahere`, `tpahereauto`, `tpauto`, `twitter`, `unignore`, `voicechatconsent`, `warp`, `worth`.

Nodes with default `op`:

`addmoney`, `addshards`, `alts`, `amethysttool`, `amod`, `arena`, `ban`, `blacklist`, `chatlog`, `clearlag`, `crate`, `create`, `cuboid`, `delwarp`, `disguise`, `ecsee`, `fakeplayer`, `feed`, `ffaarena`, `findplayer`, `fly`, `flyspeed`, `flyspeed.others`, `freeze`, `gamemode`, `heal`, `hide`, `invsee`, `kick`, `kill`, `logs`, `maintenance`, `mute`, `offend`, `playerunwipe`, `playerwipe`, `portalmanager`, `profileviewer`, `punishments`, `randomteleport`, `removemoney`, `removeshards`, `rename`, `safety`, `seehomes`, `servers`, `serverwipe`, `setmoney`, `setshards`, `setwarp`, `spawnstash`, `staffchat`, `stafflist`, `staffmode`, `teleport`, `tempban`, `tempmute`, `topsell`, `corenyadonutsmp`, `unban`, `unblacklist`, `unmute`, `vanish`, `vcmute`, `vcunmute`, `warn`, `warpmanager`.

Ten of these nodes are declared as parents of a punishment child, so granting the command node also grants the action node:

| Command node | Child granted |
|--------------|---------------|
| `corenyadonutsmp.command.ban` | `corenyadonutsmp.staff.punishments.ban` |
| `corenyadonutsmp.command.tempban` | `corenyadonutsmp.staff.punishments.ban` |
| `corenyadonutsmp.command.unban` | `corenyadonutsmp.staff.punishments.unban` |
| `corenyadonutsmp.command.mute` | `corenyadonutsmp.staff.punishments.mute` |
| `corenyadonutsmp.command.tempmute` | `corenyadonutsmp.staff.punishments.mute` |
| `corenyadonutsmp.command.unmute` | `corenyadonutsmp.staff.punishments.unmute` |
| `corenyadonutsmp.command.blacklist` | `corenyadonutsmp.staff.punishments.blacklist` |
| `corenyadonutsmp.command.unblacklist` | `corenyadonutsmp.staff.punishments.unblacklist` |
| `corenyadonutsmp.command.vcmute` | `corenyadonutsmp.staff.vcmute` |
| `corenyadonutsmp.command.vcunmute` | `corenyadonutsmp.staff.vcunmute` |
| `corenyadonutsmp.command.warn` | `corenyadonutsmp.staff.punishments.create` |
| `corenyadonutsmp.command.kick` | `corenyadonutsmp.staff.punishments.create` |
| `corenyadonutsmp.command.offend` | `corenyadonutsmp.staff.punishments.offend` |

Note that `corenyadonutsmp.command.flyspeed.others` is the only command node that is not simply a command name; it gates the `[player]` argument of `/flyspeed` rather than the command itself.

### `corenyadonutsmp.staff.*`

| Node | Default | Description |
|------|---------|-------------|
| `corenyadonutsmp.staff.mode` | op | Parent: full staff access for Staff Mode and staff utility commands. Grants 29 children including `corenyadonutsmp.chat.color`. |
| `corenyadonutsmp.staff.mode.betterview` | op | Toggle better view from the staff mode hotbar. |
| `corenyadonutsmp.staff.mode.others` | op | Toggle staff mode for other players. |
| `corenyadonutsmp.staff.mode.randomtp` | op | Teleport to a random player from the hotbar. |
| `corenyadonutsmp.staff.mode.spectator` | op | Toggle spectator from the staff mode hotbar. |
| `corenyadonutsmp.staff.mode.seevanished` | op | See vanished staff members. |
| `corenyadonutsmp.staff.mode.stafflist` | op | Open the staff list from the hotbar. |
| `corenyadonutsmp.staff.mode.vanish` | op | Toggle vanish from the hotbar. |
| `corenyadonutsmp.staff.alerts.receive` | op | Parent: receive helpop and report alerts. |
| `corenyadonutsmp.staff.alerts.bypass-cooldown` | op | Bypass helpop and report cooldowns. |
| `corenyadonutsmp.staff.helpop.receive` | op | Receive helpop alerts. |
| `corenyadonutsmp.staff.report.receive` | op | Receive report alerts. |
| `corenyadonutsmp.staff.alts` | op | View alternate accounts by shared IP history. |
| `corenyadonutsmp.staff.chat.use` | op | Send and receive network staff chat. |
| `corenyadonutsmp.staff.chat.mute` | op | Mute global chat. |
| `corenyadonutsmp.staff.chat.unmute` | op | Unmute global chat. |
| `corenyadonutsmp.staff.chat.delay` | op | Configure the global chat delay. |
| `corenyadonutsmp.staff.chat.clear` | op | Clear global chat visually. |
| `corenyadonutsmp.staff.chat.bypass.mute` | op | Bypass the global chat mute. |
| `corenyadonutsmp.staff.chat.bypass.delay` | op | Bypass the global chat delay. |
| `corenyadonutsmp.staff.chat.bypass.filter` | op | Bypass global chat filters. |
| `corenyadonutsmp.staff.feed` | op | Restore hunger for yourself or another player. |
| `corenyadonutsmp.staff.fly` | op | Toggle flight for yourself or another player. |
| `corenyadonutsmp.staff.flyspeed` | op | Adjust flying speed. |
| `corenyadonutsmp.staff.freeze` | op | Freeze and unfreeze players. |
| `corenyadonutsmp.staff.freeze.alert` | op | Receive freeze moderation alerts. |
| `corenyadonutsmp.staff.freeze.exempt` | op | Cannot be frozen by non-admin staff. |
| `corenyadonutsmp.staff.gamemode` | op | Change your own game mode. |
| `corenyadonutsmp.staff.gamemode.others` | op | Change another online player's game mode. |
| `corenyadonutsmp.staff.god` | op | Toggle god mode; also the command gate for `/god`. |
| `corenyadonutsmp.staff.heal` | op | Restore health for yourself or another player. |
| `corenyadonutsmp.staff.invsee` | op | View other players' inventories. |
| `corenyadonutsmp.staff.invsee.modify` | op | Reserved for future editable Invsee sessions. |
| `corenyadonutsmp.staff.profileviewer` | op | View player profiles and homes. |
| `corenyadonutsmp.staff.rename` | op | Rename held items. |
| `corenyadonutsmp.staff.seehomes` | op | Browse any player's homes and teleport to them. |
| `corenyadonutsmp.staff.teleport` | op | Access the staff teleport commands. |
| `corenyadonutsmp.staff.punishments.create` | op | Parent: create punishment records. Grants `punishments.ban`, `punishments.blacklist`, `punishments.mute`, `staff.vcmute`. |
| `corenyadonutsmp.staff.punishments.remove` | op | Parent: remove active punishments. Grants `punishments.unban`, `punishments.unblacklist`, `punishments.unmute`, `staff.vcunmute`. |
| `corenyadonutsmp.staff.punishments.view` | op | View punishment history. |
| `corenyadonutsmp.staff.punishments.delete` | op | Delete punishment history records from the GUI. |
| `corenyadonutsmp.staff.punishments.ban` | false | Issue bans. Declared without a description. |
| `corenyadonutsmp.staff.punishments.unban` | false | Remove bans. |
| `corenyadonutsmp.staff.punishments.mute` | false | Issue mutes. |
| `corenyadonutsmp.staff.punishments.unmute` | false | Remove mutes. |
| `corenyadonutsmp.staff.punishments.blacklist` | false | Issue blacklists. |
| `corenyadonutsmp.staff.punishments.unblacklist` | false | Remove blacklists. |
| `corenyadonutsmp.staff.vcmute` | false | Issue voice chat mutes. |
| `corenyadonutsmp.staff.vcunmute` | false | Remove voice chat mutes. |

### `corenyadonutsmp.auctionhouse.*`

| Node | Default | Description |
|------|---------|-------------|
| `corenyadonutsmp.auctionhouse.use` | true | Open the Auction House menu. |
| `corenyadonutsmp.auctionhouse.buy` | true | Buy items from the Auction House. |
| `corenyadonutsmp.auctionhouse.sell` | true | Sell items on the Auction House. |
| `corenyadonutsmp.auctionhouse.my` | true | View your own listings. |
| `corenyadonutsmp.auctionhouse.claims` | true | View and collect claims. |
| `corenyadonutsmp.auctionhouse.cancel` | true | Cancel your own listings. |
| `corenyadonutsmp.auctionhouse.limit` | true | Check listing limits. |

### `donutauction.*` (legacy aliases)

Every node here mirrors the `corenyadonutsmp.auctionhouse.*` node of the same name. The Auction House checks both families, so either one grants the action; the family exists so that permission groups written for the older DonutAuction plugin continue to work.

| Node | Default | Description |
|------|---------|-------------|
| `donutauction.use` | true | Open the Auction House menu (alias). |
| `donutauction.buy` | true | Buy items (alias). |
| `donutauction.sell` | true | Sell items (alias). |
| `donutauction.my` | true | View your own listings (alias). |
| `donutauction.claims` | true | View and collect claims (alias). |
| `donutauction.cancel` | true | Cancel your own listings (alias). |
| `donutauction.limit` | true | Check listing limits (alias). |

### `rank.*`

| Node | Default | Description |
|------|---------|-------------|
| `media` | false | Display the configurable Media tablist badge (default configured node). |
| `rank.media` | false | Display the configurable Media tablist badge (legacy alias). |
| `rank.media.plus` | false | Display the configurable Media+ tablist badge. |
| `rank.media.include` | false | Include the player in media badge handling. |

### Other `corenyadonutsmp` nodes

| Node | Default | Description |
|------|---------|-------------|
| `corenyadonutsmp.chat.color` | op | Use colour codes and gradients in private messages and staff chat. Granted by `corenyadonutsmp.staff.mode`. |
| `corenyadonutsmp.enderchest` | true | Open the custom Ender Chest. |
| `corenyadonutsmp.friends` | true | Access the friends feature. |
| `corenyadonutsmp.helpop` | true | Use `/helpop`. |
| `corenyadonutsmp.ignore` | true | Add and remove players from your ignore list. |
| `corenyadonutsmp.ignore.bypass` | op | Bypass private-message ignore checks. |
| `corenyadonutsmp.message` | true | Send and reply to private messages. |
| `corenyadonutsmp.message.toggle` | true | Toggle private message receiving. |
| `corenyadonutsmp.message.bypass-disabled` | op | Message a player who has private messages disabled. |
| `corenyadonutsmp.report` | true | Report players to staff. |
| `corenyadonutsmp.servers` | false | Access the network servers status menu. |
| `corenyadonutsmp.shards.everywhere` | false | Receive passive Shards Everywhere rewards. Overridable via `SHARDS.EVERYWHERE.REQUIRED-PERMISSION`. |
| `corenyadonutsmp.spawner.bypass` | false | Break spawners without a Silk Touch pickaxe while `REQUIRE_SILK_TOUCH` is enabled. |

### Nodes checked in Java but not declared in `plugin.yml`

These 38 nodes are enforced at runtime but have no declaration, so they have no Bukkit default and will not appear in permission-plugin tab completion. They must be granted explicitly. The five numbered prefixes are listed here in their raw form because they are only ever used as a prefix plus an integer.

| Node | Where it is used |
|------|------------------|
| `anvilmod.admin` | `/amod` |
| `safety.use`, `safety.add`, `safety.reload` | `/safety` (these three are additional to the 38 and likewise undeclared) |
| `donutauction.fastbuy` | Auction House browse menu |
| `donutauction.fastsell` | `/auctionhouse fastsell` |
| `donutfriends.admin` | `/friend reload`, `/friends reload` |
| `donutplus` | Economy placeholder expansion |
| `donutplusplus`, `donutplusplusplus` | Auction "your items" menu |
| `corenyadonutsmp.donutplus` | Economy placeholder expansion |
| `corenyadonutsmp.donutplusplus`, `corenyadonutsmp.donutplusplusplus` | Auction "your items" menu |
| `corenyadonutsmp.staff.fakeplayer` | `/fakeplayer` — note the legacy prefix without `2` |
| `corenyadonutsmp.staff.fakeplayer.alert` | Fake player alerts |
| `corenyadonutsmp.staff.fakeplayer.bypass` | Fake player detection bypass |
| `corenyadonutsmp.staff.alerts.receive` | Fake player alerts |
| `corenyadonutsmp.admin.ecsee.edit` | Editing another player's Ender Chest |
| `corenyadonutsmp.admin.sellstats` | `/topsell` |
| `corenyadonutsmp.admin.spawnstash` | `/spawnstash remove`, `/spawnstash reload` |
| `corenyadonutsmp.admin.teleportareas.delete` | Teleport area menu |
| `corenyadonutsmp.auctionhouse.fastbuy` | Auction House browse menu |
| `corenyadonutsmp.auctionhouse.fastsell` | `/auctionhouse fastsell` |
| `corenyadonutsmp.auctionhouse.limit.<N>` | Auction House listing limit tier |
| `corenyadonutsmp.command.setafk` | `/setafk` and `/afk set` |
| `corenyadonutsmp.command.setspawn` | `/setspawn` and `/spawn set` |
| `corenyadonutsmp.enderchest.rows.<N>` | Ender Chest row tier |
| `corenyadonutsmp.hide.scramble` | `/hide scramble` |
| `corenyadonutsmp.hide.disguise` | `/disguise <alias>` |
| `corenyadonutsmp.hide.admin` | Administering other players' hide states |
| `corenyadonutsmp.hide.bypass` | Seeing through hidden identities |
| `corenyadonutsmp.homes.page.<N>` | Home page tier |
| `corenyadonutsmp.player.fly` | Player-facing flight; configurable via `FLY-SYSTEM.PLAYER-FLY-PERMISSION` |
| `corenyadonutsmp.preventdrop.bypass` | Item drop prevention; configurable via `PREVENT-ITEM-DROP.BYPASS-PERMISSION` |
| `corenyadonutsmp.rtp.cooldown.<seconds>` | RTP cooldown tier |
| `corenyadonutsmp.rtp.priority.<N>` | RTP queue priority tier |
| `corenyadonutsmp.staff.punishments.offend` | `/offend` — this is the command's gate, declared on the command but not in the permissions block |
| `corenyadonutsmp.staff.spawnstash` | `/spawnstash` |
| `corenyadonutsmp.staff.spawnstash.alert` | SpawnStash trip alerts |
| `corenyadonutsmp.staff.spawnstash.bypass` | Not triggering a SpawnStash |
| `corenyadonutsmp.updatechecker` | Receiving update notices on join, alongside `corenyadonutsmp.admin` and operator status |

`corenyadonutsmp.homes.<N>` is also used only as a numbered prefix, though the plain `corenyadonutsmp.homes.` string appears in the source as a constant rather than a full node.

## Known inconsistencies

These are real, verified mismatches between `plugin.yml` and the Java source. They are documented here so that administrators building permission groups are not surprised by them; none of them prevents the plugin from starting.

**`/fakeplayer` checks a legacy permission prefix.** The command gate is `corenyadonutsmp.command.fakeplayer`, but `FakePlayerManager` checks `corenyadonutsmp.staff.fakeplayer` — the older namespace without the trailing `2`. The related alert and bypass nodes are `corenyadonutsmp.staff.fakeplayer.alert` and `corenyadonutsmp.staff.fakeplayer.bypass`, and fake player alerts are also delivered to holders of `corenyadonutsmp.staff.alerts.receive`. Staff who need `/fakeplayer` therefore require nodes from both namespaces, and `corenyadonutsmp.*` wildcards will not cover them.

**`/spawnstash` command node and runtime node differ.** The command gate is `corenyadonutsmp.command.spawnstash` (default `op`), but every action inside the command checks `corenyadonutsmp.staff.spawnstash` for ordinary use and `corenyadonutsmp.admin.spawnstash` for `remove` and `reload`. Neither runtime node is declared in `plugin.yml`, and `corenyadonutsmp.admin.spawnstash` is not a child of `corenyadonutsmp.admin`. Grant all three explicitly.

**`/god` has no `command.` node.** Unlike every other staff command, `/god` is gated directly by `corenyadonutsmp.staff.god` in `plugin.yml`. There is no `corenyadonutsmp.command.god`, so `corenyadonutsmp.command.*` does not grant it; `corenyadonutsmp.staff.mode` does, because `staff.god` is one of its children.

**`/setspawn`, `/setafk` and `/offend` gates are undeclared.** These three commands name `corenyadonutsmp.command.setspawn`, `corenyadonutsmp.command.setafk` and `corenyadonutsmp.staff.punishments.offend` as their `permission:` value, but those nodes are absent from the `permissions:` block. As undeclared nodes they have no default and are not children of `corenyadonutsmp.command.*`, so operators pass via operator status while non-operator staff need an explicit grant. Note that `/spawn set` and `/afk set` accept `corenyadonutsmp.admin.setup` as an alternative, which *is* declared.

**The `donutauction.*` alias family is fully live.** All seven Auction House actions accept either `corenyadonutsmp.auctionhouse.<action>` or `donutauction.<action>`, and both families are declared with default `true`. The listing limit resolver additionally reads `donutauction.limit.<N>`. If you intend to restrict the Auction House, you must negate both families; revoking only the `corenyadonutsmp.` nodes leaves the aliases granting access.

**Some subcommands are absent from the declared usage strings.** `/auctionhouse` implements `limit`, `fastbuy` and `fastsell`; `/topsell` implements `web`, `html` and `reset`; `/pvp` implements `sync`; `/crate` implements `listbound`; `/corenyadonutsmp` implements `setup`, `features` and `maintenance` and accepts `optimization` as an alias of `optimize`. The `usage:` strings in `plugin.yml` predate these additions, so `/help`-style output derived from `plugin.yml` will understate what the commands can do.

**`/sellmultiplier` reuses another command's node.** It is declared as its own command but is gated by `corenyadonutsmp.command.sellmulti`, not a `command.sellmultiplier` node. There is no such node.

**`/safety` uses a bare namespace.** Its runtime checks are `safety.use`, `safety.add` and `safety.reload`, outside the plugin's own namespace and undeclared in `plugin.yml`.

**Twenty-three declared nodes never appear in Java.** Besides the `corenyadonutsmp.command.*` family, which Bukkit enforces from `plugin.yml` and which therefore does not need string literals, several feature nodes are declared but not referenced in the source: `corenyadonutsmp.admin.ecsee`, `corenyadonutsmp.staff.freeze`, `corenyadonutsmp.staff.freeze.alert`, `corenyadonutsmp.staff.freeze.exempt`, `corenyadonutsmp.staff.invsee.modify`, `corenyadonutsmp.staff.mode` and its six `mode.*` children, `corenyadonutsmp.enderchest`, and the `auctionhouse`/`donutauction` `cancel`, `claims`, `limit`, `my` and `sell` nodes, which are constructed dynamically as `"corenyadonutsmp.auctionhouse." + action` rather than written out. In the Auction House case the nodes do work; in the freeze and staff-mode cases they are consumed by their respective managers through parent relationships and hotbar wiring rather than direct literal checks, so treat them as advisory until you have tested your own group setup.

**`/sellhand [amount]` ignores the quantity argument.** `plugin.yml` advertises `/sellhand [amount]` and tab completion suggests quantity values, but the command executor always sells the complete item stack held in your main hand.

