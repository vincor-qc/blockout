package io.github.vincorqc.lockout.tasks;

import io.github.vincorqc.lockout.util.AdvancementList;
import io.github.vincorqc.lockout.util.MobList;
import io.github.vincorqc.lockout.util.TaskDifficulty;

import java.util.Random;

public class KillTask extends Task {
    private final String mob;

    public KillTask(TaskDifficulty difficulty) {
        super(difficulty);

        // Assign Random Advancement
        Random r = new Random();
        if(difficulty == TaskDifficulty.EASY)
            this.mob = MobList.EASY_MOBS[r.nextInt(MobList.EASY_MOBS.length)];

        else if(difficulty == TaskDifficulty.MEDIUM)
            this.mob = MobList.MEDIUM_MOBS[r.nextInt(MobList.MEDIUM_MOBS.length)];

        else if(difficulty == TaskDifficulty.HARD)
            this.mob = MobList.HARD_MOBS[r.nextInt(MobList.HARD_MOBS.length)];

        else
            this.mob = MobList.EXPERT_MOBS[r.nextInt(MobList.EXPERT_MOBS.length)];

        title = "Kill a " + mob;
    }

    public String getMob() {
        return mob;
    }
}
