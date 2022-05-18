package io.github.vincorqc.lockout.handlers;

import io.github.vincorqc.lockout.common.LockoutMod;
import io.github.vincorqc.lockout.networking.LockoutPacketHandler;
import io.github.vincorqc.lockout.networking.packets.TeamPacket;
import io.github.vincorqc.lockout.networking.packets.TeamScorePacket;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TeamHandler {
    private static final HashMap<String, Integer> playerTeams = new HashMap<>();
    private static final HashMap<Integer, Integer> teamScores = new HashMap<>();

    public static void addPlayer(Player p) {
        if(playerTeams.containsKey(p.getName().getString())) return;

        playerTeams.put(p.getName().getString(), -1);
    }

    public static void setTeam(Player p, int team) {
        if(playerTeams.containsKey(p.getName().getString())) {
            playerTeams.replace(p.getName().getString(), team);
        } else {
            addPlayer(p);
        }

        if(team == -1) return;
        if(!teamScores.containsKey(team)) teamScores.put(team, 0);
    }

    public static Integer getTeam(Player p) {
        return playerTeams.get(p.getName().getString());
    }

    public static void incrementScore(int team) {
        if(teamScores.containsKey(team)) {
            teamScores.replace(team, teamScores.get(team) + 1);
            if(teamScores.get(team) > 12) {
                LockoutGameHandler.setGameStarted(false);
                TextComponent text = new TextComponent("Team " + team + " won!");

                for(Player p : LockoutMod.server.getPlayerList().getPlayers()) {
                    LockoutMod.server.getPlayerList().broadcastMessage(text, ChatType.SYSTEM, p.getUUID());
                }
            }
        }
    }

    public static int getScore(int team) {
        if(teamScores.containsKey(team)) return teamScores.get(team);
        return 0;
    }

    public static void setScore(int team, int score) {
        teamScores.put(team, score);
    }

    public static void syncTeamData() {
        for(String name : playerTeams.keySet()) {
            LockoutPacketHandler.sendAll(new TeamPacket(name, playerTeams.get(name)));
        }
    }

    public static void syncTeamScores() {
        for(int team : teamScores.keySet()) {
            if(teamScores.containsKey(team)) LockoutPacketHandler.sendAll(new TeamScorePacket(team, teamScores.get(team)));
        }
    }
}