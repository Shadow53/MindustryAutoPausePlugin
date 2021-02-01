package com.shadow53;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;

public class AutoPausePlugin extends Plugin{
    private boolean enabled = true;
    private int playerCount = 0;

    //called when game initializes
    @Override
    public void init(){
        Events.on(PlayerJoin.class, event -> {
            if (enabled) {
                playerCount += 1;
                if (playerCount == 1) {
                    Vars.state.serverPaused = false;
                }
            }
        });

        Events.on(PlayerLeave.class, event -> {
            if (enabled) {
                playerCount -= 1;
                if (playerCount == 0) {
                    Vars.state.serverPaused = true;
                }
            }
        });
    }

    public void registerClientCommands(CommandHandler handler) {
        handler.<Player>register("autopause", "<on/off>", "Enabled/disable autopause", (arg, player) -> {
            if (arg.length == 0) {
                player.sendMessage("[scarlet]Error: Second parameter required: 'on' or 'off'");
            }

            if (!(arg[0].equals("on") || arg[0].equals("off"))) {
                player.sendMessage("[scarlet]Error: Second parameter must be either 'on' or 'off'");
            }

            enabled = arg[0].equals("on");
        });
    }
}
