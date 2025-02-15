[![Description Banner](https://i.ibb.co/7tRvNf1x/banner.png)](https://modrinth.com/plugin/cuboidteleport)
# **💨A simple plugin that adds teleports**
**✨Features:**
- HEX Color support
- Custom config.yml
- Customisable localization - Russian and English
- Very simple and non-loading plugin

**🏗️Commands:**
- `/home [name]` - teleport on home
- `/sethome [name]` - set home
- `/delhome [name]` - remove home
- `/warp [warp name]` - teleport to warp
- `/warps` - list of warps
- `/setwarp [warp name]` - set warp
- `/delwarp [warp name]` - delete warp
- `/rtp` - random teleport
- `/tpr` - random teleport
- `/tpa [player]` - send request to player
- `/call [player]` - send request to player
- `/tpaccept` - accept request
- `/tpacancel` - cancel request
- `/tpdeny` - deny request
- `/ctp help` - shows commands list
- `/ctp reload` - reload configuration

**⚙️config.yml preview:**
```
#
# CuboidTeleport
# v1.2
# by camper_crafting
#
locale: en
message_prefix: "&#6000FF&lC&#5E0AFF&lu&#5C14FF&lb&#591FFF&lo&#5729FF&li&#5533FF&ld&#533DFF&lT&#5048FF&le&#4E52FF&ll&#4C5CFF&le&#4A66FF&lp&#4771FF&lo&#457BFF&lr&#4385FF&lt&r &7|&r "

# Teleport settings
teleport_radius: 1000 # RTP radius
```

### **🌈How to create gradient - https://www.birdflop.com/resources/rgb/**

**🇬🇧msg_en.yml preview:**
```
#
# CuboidTeleport
# v1.2
# by camper_crafting
# English language plugin
#
only_players: "&#ff0000Only players can use this command!&r"
usage_call: "&#ff0000Usage: &n/call [player]&r"
player_not_found: "&#ff0000Player not found.&r"
usage_tpa: "&#ff0000Usage: &n/tpa [player]&r"
no_self_request: "&#ff0000You cannot send a teleport request to yourself.&r"
home_set: "&#15ff00Home %home% set!&r"
home_teleported: "&#15ff00Teleported to home %home%!&r"
home_removed: "&#ff0000Home %home% removed!&r"
home_not_exist: "&#ff0000Home %home% does not exist!&r"
config_reloaded: "&#15ff00Configuration reloaded successfully!&r"
help_message: "&#4385FFcommands:&r\n
&#4385FF/ctp help &7- &#6000FFshow corresponding message&r\n
&#4385FF/ctp reload &7- &#6000FFreload configuration&r\n
&#4385FF/sethome [name] &7- &#6000FFset home point&r\n
&#4385FF/home [name] &7- &#6000FFteleport to home point&r\n
&#4385FF/delhome [name] &7- &#6000FFdelete home point&r\n
&#4385FF/tpa [player] &7- &#6000FFsend request to teleport player&r\n
&#4385FF/call [player] &7- &#6000FFedit player teleportation request&r\n
&#4385FF/tpaccept &7- &#6000FFaccept teleportation request&r\n
&#4385FF/tpacancel &7- &#6000FFreject teleportation request&r\n
&#4385FF/tpdeny &7- &#6000FFcancel teleportation request&r\n
&#4385FF/rtp &7- &#6000FFrandom teleport&r\n
&#4385FF/tpr &7- &#6000FFrandom teleport&r\n
&#4385FF/warp &7- &#6000FFteleport to warp&r\n
&#4385FF/warps &7- &#6000FFwarp list&r\n
&#4385FF/setwarp &7- &#6000FFset warp&r\n
&#4385FF/delwarp &7- &#6000FFdelete warp&r"
no_permission: "&#ff0000You don't have permission to use this command!&r"
request_sent: "&#15ff00Teleport request sent to %target%.&r"
request_received: "&#6000FF%sender%&r &#4385FFhas requested to teleport to you.&r\n&#4385FFType &#15ff00/tpaccept&r &#4385FFto accept or &#ff0000/tpacancel&r &#4385FFto deny.&r"
request_timed_out: "&#ff0000Teleport request has timed out.&r"
request_accepted: "&#15ff00Teleport request accepted.&r"
request_denied: "&#ff0000Teleport request denied.&r"
no_pending_request: "&#ff0000You have no pending teleport requests.&r"
request_pending: "&#ff0000You already have a pending teleport request.&r"
sender_offline: "&#ff0000The player who sent the request is no longer online.&r"
request_cancelled: "&#ff0000Teleport request from %sender% has been cancelled.&r"
rtp_success: "&#15ff00You have been teleported to a random location.&r"
rtp_fail: "&#ff0000Could not find a safe location. Please try again.&r"
warp_set: "&#15ff00Warp %warp% has been set.&r"
warp_exists: "&#ff0000Warp %warp% already exists.&r"
warp_not_found: "&#ff0000Warp %warp% not found.&r"
warp_teleport: "&#15ff00Teleported to warp %warp%.&r"
warp_deleted: "&#15ff00Warp %warp% has been deleted.&r"
usage_setwarp: "&#ff0000Usage: &n/setwarp [name]&r"
usage_warp: "&#ff0000Usage: &n/warp [name]&r"
usage_delwarp: "&#ff0000Usage: &n/delwarp [name]&r"
warps_list: "&#4385FFAvailable warps: &#6000FF&n%warps%&r"
no_warps: "&#ff0000No warps have been set yet.&r"
```

**📃TODO list:**
- More customizable `config.yml`
