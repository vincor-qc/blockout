package io.github.vincorqc.lockout.handlers;

import io.github.vincorqc.lockout.tasks.*;
import io.github.vincorqc.lockout.util.TaskDifficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Random;

public class LockoutGameHandler {

    public static final Task[][] grid = new Task[5][5];

    protected static final ArrayList<ObtainTask> obtainTasks = new ArrayList<>();
    protected static final ArrayList<AdvancementTask> advancementTasks = new ArrayList<>();
    protected static final ArrayList<KillTask> killTasks = new ArrayList<>();
    protected static final ArrayList<EffectTask> effectTasks = new ArrayList<>();
    protected static final ArrayList<MineTask> mineTasks = new ArrayList<>();
    protected static final ArrayList<DeathTask> deathTasks = new ArrayList<>();
    protected static final ArrayList<CollectTask> collectTasks = new ArrayList<>();

    private static final ArrayList<String> usedTasks = new ArrayList<>();

    public static void generateGrid() {
        for(int r = 0; r < 5; r++) {
            for(int c = 0; c < 5; c++) {
                Task t = generateTask();

                while(usedTasks.contains(t.getTitle())) {
                    t = generateTask();
                }

                usedTasks.add(t.getTitle());
                addToLists(t);

                grid[r][c] = t;
            }
        }
    }


    /*-----------------*/
    /* PRIVATE METHODS */
    /*-----------------*/

    private static Task generateTask() {
        Task t = null;

        Random r = new Random();
        int diff = r.nextInt(100);

        if(diff < 35) {
            int type = r.nextInt(100);
            TaskDifficulty d = TaskDifficulty.EASY;

            if(type < 25) t = new AdvancementTask(d);
            else if(type < 35) t = new ObtainTask(d);
            else if(type < 50) t = new KillTask(d);
            else if(type < 70) t = new EffectTask(d);
            else if(type < 85) t = new MineTask(d);
            else if(type < 100) t = new DeathTask(d);

        } else if(diff < 80) {
            int type = r.nextInt(100);
            TaskDifficulty d = TaskDifficulty.MEDIUM;

            if(type < 20) t = new AdvancementTask(d);
            else if(type < 35) t = new ObtainTask(d);
            else if(type < 55) t = new KillTask(d);
            else if(type < 65) t = new EffectTask(d);
            else if(type < 80) t = new MineTask(d);
            else if(type < 100) t = new DeathTask(d);

        } else if(diff < 94) {
            int type = r.nextInt(100);
            TaskDifficulty d = TaskDifficulty.HARD;

            if(type < 30) t = new AdvancementTask(d);
            else if(type < 40) t = new ObtainTask(d);
            else if(type < 50) t = new KillTask(d);
            else if(type < 70) t = new CollectTask(d);
            else if(type < 74) t = new MineTask(d);
            else if(type < 100) t = new DeathTask(d);

        } else {
            int type = r.nextInt(100);
            TaskDifficulty d = TaskDifficulty.EXPERT;

            if(type < 25) t = new AdvancementTask(d);
            else if(type < 50) t = new ObtainTask(d);
            else if(type < 75) t = new KillTask(d);
            else if(type < 100) t = new CollectTask(d);
        }

        return t;
    }

    private static void addToLists(Task t) {
        if(t instanceof ObtainTask) obtainTasks.add((ObtainTask) t);
        if(t instanceof AdvancementTask) advancementTasks.add((AdvancementTask) t);
        if(t instanceof KillTask) killTasks.add((KillTask) t);
        if(t instanceof EffectTask) effectTasks.add((EffectTask) t);
        if(t instanceof MineTask) mineTasks.add((MineTask) t);
        if(t instanceof DeathTask) deathTasks.add((DeathTask) t);
        if(t instanceof CollectTask) collectTasks.add((CollectTask) t);
    }

    public static String asString() {
        String res = "";

        for(Task[] r : grid) {
            for(Task t : r) {
                res += t.getTitle() + " - TEAM: " + t.getTeam() + "\n";
            }
        }

        return res;
    }
}
