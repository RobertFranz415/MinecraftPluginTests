package com.valiantrealms.zombiesmc.util.skills;

import com.valiantrealms.zombiesmc.ZombiesMC;
import com.valiantrealms.zombiesmc.util.ConfigUtil;
import org.bukkit.event.Listener;

import java.util.Random;
import java.util.UUID;

public class Strength implements Listener {
    private final ZombiesMC plugin;
    private ConfigUtil con;
    private Random rand = new Random();

    public Strength(ZombiesMC plugin){
        this.plugin = plugin;
        this.setConfig();
    }

    public void levelUp(UUID id){
        plugin.getPlayers().get(id).getSkills()[5] += plugin.getSkillSettings().getConfig().getDouble("stamina.leveling.stamina-increase-per-level");
        plugin.getPlayers().get(id).setHealth();
    }

    public double meleeDamage(UUID id, double originalDamage){
        double unarmed = (plugin.getPlayers().get(id).getSkills()[5]) * con.getConfig().getDouble("strength.damage-increase");
        double critChance = unarmed * con.getConfig().getDouble("strength.crit-chance-increase");

        if(critChance > con.getConfig().getDouble("strength.max-crit-chance")){
            critChance = con.getConfig().getDouble("strength.max-crit-chance");
        }

        double damage = originalDamage + (unarmed * originalDamage);

        double num = (Math.floor(rand.nextDouble() * 1000))/10;

        boolean isCrit = (num <= critChance);
        if(isCrit){ damage = damage * con.getConfig().getDouble("strength.crit-damage-mult"); }

        return damage;
    }

    public void setConfig() { con = plugin.getSkillSettings(); }
}
