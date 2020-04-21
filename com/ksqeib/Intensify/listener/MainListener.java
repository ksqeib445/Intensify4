package ksqeib.Intensify.listener;

import ksqeib.Intensify.enums.Sectype;
import ksqeib.Intensify.main.NewAPI;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.HashMap;
import java.util.List;

public class MainListener implements Listener {

    HashMap<Integer, Double> ShEntityIdMap = new HashMap<>();
    HashMap<Integer, Double> XxEntityIdMap = new HashMap<>();

    //优化完成
    @EventHandler(priority = EventPriority.HIGHEST)
    public void EntityDamageEvent(EntityDamageEvent e) {
        if ((e.getEntity() instanceof LivingEntity) && e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            double damage = e.getDamage();
            LivingEntity le = (LivingEntity) e.getEntity();
            damage -= NewAPI.getAddLevel(NewAPI.getAddAll(le), Sectype.defense);
            if (damage < 0) {
                damage = 0;
            }
            e.setDamage(damage);
        }
    }

    //优化完成
    @EventHandler(priority = EventPriority.HIGHEST)
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        double damage = e.getDamage();
        if (((damager instanceof Damageable) || (damager instanceof Projectile)) && (e.getEntity() instanceof Damageable)) {
            if (damager instanceof Damageable) {
                if ((e.getEntity() instanceof LivingEntity) && (damager instanceof LivingEntity)) {
                    LivingEntity damagerr = (LivingEntity) damager;
                    LivingEntity entity = (LivingEntity) e.getEntity();
                    List<ItemStack> list1 = NewAPI.getAddAll(damagerr);
                    List<ItemStack> list2 = NewAPI.getAddAll(entity);
                    double bs = NewAPI.getAddLevel(list1, Sectype.bloodSuck);
                    damage += bs * damage / 100;
                    damage += NewAPI.getAddLevel(list1, Sectype.damage);
                    if (damagerr.getHealth() + bs * damage / 100 < NewAPI.getMaxHealth(damagerr)) {
                        damagerr.setHealth(damagerr.getHealth() + bs * damage / 100);
                    } else {
                        damagerr.setHealth(NewAPI.getMaxHealth(damagerr));
                    }
//                    if (NewAPI.getReboundDamage(list2) * damage / 100 > 0) {
//                        if (canPVP(Damagerr, entity)) {
//                            Damagerr.damage(NewAPI.getReboundDamage(list2) * damage / 100);
//                        }
//                    }
                    damage -= NewAPI.getAddLevel(list2, Sectype.defense);
                    try {
                        NewAPI.removeDurability(list2);
                    } catch (NoSuchMethodError ee) {
                    }
                }
            } else {
                Projectile psw = (Projectile) damager;
                ProjectileSource ps = psw.getShooter();
                if ((ps instanceof LivingEntity) && (e.getEntity() instanceof LivingEntity)) {
                    LivingEntity p = (LivingEntity) ps;
                    LivingEntity entity = (LivingEntity) e.getEntity();
                    List<ItemStack> list2 = NewAPI.getAddAll(entity);
//                    if (NewAPI.getReboundDamage(list2) * damage / 100 > 0) {
//                        if (canPVP(p, entity)) {
//                            p.damage(NewAPI.getReboundDamage(list2) * damage / 100);
//                        }
//                    }
                    if (XxEntityIdMap.get(psw.getEntityId()) != null && ShEntityIdMap.get(psw.getEntityId()) != null) {
                        damage += XxEntityIdMap.get(psw.getEntityId()) * damage / 100;
                        damage += ShEntityIdMap.get(psw.getEntityId());
                        if (p.getHealth() + XxEntityIdMap.get(psw.getEntityId()) * damage / 100 < NewAPI.getMaxHealth(p)) {
                            p.setHealth(p.getHealth() + XxEntityIdMap.get(psw.getEntityId()) * damage / 100);
                        } else {
                            p.setHealth(NewAPI.getMaxHealth(p));
                        }
                    }
                    damage -= NewAPI.getAddLevel(list2, Sectype.defense);
                    try {
                        NewAPI.removeDurability(list2);
                    } catch (NoSuchMethodError ee) {
                    }
                }
            }
        }
        if (damage < 0) {
            damage = 0;
        }
        e.setDamage(damage);
    }

    //优化完成
    @EventHandler(priority = EventPriority.HIGHEST)
    public void ProjectileLaunchEvent(ProjectileLaunchEvent e) {
        Projectile psw = e.getEntity();
        ProjectileSource ps = psw.getShooter();
        if (!(ps instanceof LivingEntity)) return;
        LivingEntity le = (LivingEntity) ps;
        ShEntityIdMap.put(psw.getEntityId(), NewAPI.getAddLevel(NewAPI.getAddAll(le), Sectype.damage));
        XxEntityIdMap.put(psw.getEntityId(), NewAPI.getAddLevel(NewAPI.getAddAll(le), Sectype.bloodSuck));
    }

    //优化完成
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerExpChangeEvent(PlayerExpChangeEvent e) {
        int value = e.getAmount();
        Player p = e.getPlayer();
        Double i = NewAPI.getAddLevel(NewAPI.getAddAll(p), Sectype.experience);
        if (i == 0) return;
        e.setAmount((int) (value * (100 + i) / 100));
    }

}
