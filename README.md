# More Player Badges
This Minecraft mod is an "add-on" that adds more player-badges to the [Better Statistics Screen](https://github.com/TheCSMods/mc-better-stats) mod. The primary goal of this mod is to make the game more engaging and interesting by promoting engagement in varous aspects of the game. More info on that later on in this description.

### Requirements (v1.0+)
- [Installing this mod](#): Client (Required if installed on server) ➖ Server (Required)
- [TCDCommons API](https://modrinth.com/mod/Eldc1g37): Client (Embedded aka Optional) ➖ Server (Embedded aka Optional)
- [Better Statistics Screen](https://modrinth.com/mod/n6PXGAoM) - Client (Optional) ➖ Server (Optional)

### Some additional QNA-style notes
1. First of all, why a separate mod instead of putting the badges into `betterstats`?
    - There are a few reasons for this. The primary reason being that `betterstats` primarily aims at being a "client-side" mod, whereas this mod introduces "server-side" player-badges and features related to said player-badges. So in other words, while it is optional to install `betterstats` on the server, for this mod however, not as much (I mean technically you *could* install this on just one side, but then the features would end up looking weird and broken on the other side).
    - As mentioned earlier, `betterstats` on the server is "optional". This means that if `betterstats` did not get installed on the server, and the "server-side" badges were placed there instead, they'd effectively become unobtainable (which defeats the whole point of "optional").
    - The next reason is because I want `betterstats` and `tcdcommons` to "lay the foundation" for the player-badge feature, without actually "bloating" it with its own badges. Sure, `betterstats` does have a few of its own "client-side" player-badges, but those are all "client-side" and are also there for "demonstration" purposes so people can get to experience what that feature is like.

### Now for more info about these player-badges
1. What's the "end-goal" with the player-badges featured in this mod?
    - As briefly mentioned earlier, the player-badges featured in this mod aim to make the game more engaging and interesting. Player-badges generally aim to be something more "unique" and "separate" from Advancements. They aim to be kind of like special "novelties" that are given to players who do special things. For this reason, some (if not most) player-badges featured in this mod may be very hard to obtain. Another "end-goal" is to promote engagement in various aspects of the game. Like for example, the "Collaborative synergy" badge aims to promote engagement with other players (and mobs) in the game, "Loot hunter" promotes exploring and finding treasure chests, "Architect of the Realm" promotes building, and so on..
2. I noticed some badges look like they might depend on multiplayer. Do they really?
    - No. The goal is not to make any of the badges "multiplayer-exclusive". As such, all badges are obtainable in singleplayer as well. Some badges may have differing criteria based on whether or not the server is a singleplayer or a dedicated one, however this is done for balancing reasons. Like for example, "Loot hunter" is programmed to be easier to obtain on multiplayer because there are more players looting more chests in multiplayer worlds.
3. The badge descriptions all appear to be vague and do not exactly tell me how to obtain them. Why?
    - This is done intentionally for the same reason as the one mentioned in question number one (1). By not outright stating how to obtain the badge, the player is encouraged to explore and experiment, to look around and try and figure out what the description is trying to hint at. At the same time, wouldn't it be cool when you finally obtain and show-off your badge to someone, they're like "Wow, how did you get that?" instead of being like "Oh right, you just right-clicked a chest". As for some of those "client-side" "stat-based" badges that are in this mod, I do also plan on eventually coming up with a more creative way of obtaining them that involves more activity than just them being "stat-based", which would resolve issues such as "just right click a chest a bunch of times" for example. Anyways, TLDR; Adds mystery, promotes engagement and exploration.
    - On another note, I also get that eventually with time, the player will find out how everything works, but that's pretty much an inherent flaw that every single feature of every single video game out there has to face. The goal here is to keep it fun and interesting for the player for as long as possible, and potentially adding some "replayablility" in terms of obtaining the badges.
4. (sidenote question) Will "Chat artisan" promote spamming?
    - No, it shouldn't. While the primary goal of that badge is to encourage player interactions, conversations, and cooperation, there are things in plate to deter spam. First, the badge is "client-side" (but relies on a server-side stat) and will very likely stay that way. This is done so that when the badge is finally earned, the earning will not be broadcast in chat, meaning less incentive to spam for it. The next system in place "rate-limits" the "Chat messages sent" stat, and does not account for things like spammy-looking messages and duplicate messages. So this badge is more of a "client-side" "novelty" letting the player know they have interacted with the chat enough to obtain that badge.
    - Also the badge is obtainable in singleplayer as well. All you have to do is send one single message in chat (a simple "hello" will do for example).

### And of-course, some screenshots
An image showing what v1.0 of the mod looks like.
![2023-10-20_12 06 40](https://github.com/TheCSMods/demo-repository/assets/66475965/69d33807-40d0-4e2f-92bf-9736d9d72bb1)

An image showing what the "Loot hunter" badge looks like in v1.0.
![2023-10-20_12 06 59](https://github.com/TheCSMods/demo-repository/assets/66475965/614165f9-eb20-4b68-926a-d44164a4a779)

An image showing what the "Collaborative synergy" badge looks like in v1.0.
![2023-10-20_12 07 15](https://github.com/TheCSMods/demo-repository/assets/66475965/1f47abb3-cc8a-4be0-80db-4a8a6578c77a)

### Feedback and suggestions
Personally, I would love to get some feedback and suggestions on these badges, and potentially ideas for more of them. Like for example, it'd be so cool to have badges that have many differing but also interesting and creative ways of obtaining them. If you do have any thoughts you might wanna share, feel free to do so!