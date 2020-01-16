package ksqeib.Intensify.listener;

import ksqeib.Intensify.main.NewAPI;
import ksqeib.Intensify.util.Dataer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
            damage -= NewAPI.getDefense(NewAPI.addAll(NewAPI.getItemInHand(le), NewAPI.getItemInOffHand(le), le.getEquipment().getHelmet(), le.getEquipment().getChestplate(), le.getEquipment().getLeggings(), le.getEquipment().getBoots()));
            if (damage < 0) {
                damage = 0;
            }
            e.setDamage(damage);
        }
    }

    //优化完成
    @EventHandler(priority = EventPriority.HIGHEST)
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        Entity Damager = e.getDamager();
        double damage = e.getDamage();
        if (((Damager instanceof Damageable) || (Damager instanceof Projectile)) && (e.getEntity() instanceof Damageable)) {
            if (Damager instanceof Damageable) {
                if ((e.getEntity() instanceof LivingEntity) && (Damager instanceof LivingEntity)) {
                    LivingEntity Damagerr = (LivingEntity) Damager;
                    LivingEntity entity = (LivingEntity) e.getEntity();
                    List<ItemStack> list1 = NewAPI.addAll(NewAPI.getItemInHand(Damagerr), NewAPI.getItemInOffHand(Damagerr), Damagerr.getEquipment().getHelmet(), Damagerr.getEquipment().getChestplate(), Damagerr.getEquipment().getLeggings(), Damagerr.getEquipment().getBoots());
                    List<ItemStack> list2 = NewAPI.addAll(NewAPI.getItemInHand(entity), NewAPI.getItemInOffHand(entity), entity.getEquipment().getHelmet(), entity.getEquipment().getChestplate(), entity.getEquipment().getLeggings(), entity.getEquipment().getBoots());
                    damage += NewAPI.getBloodSuck(list1) * damage / 100;
                    damage += NewAPI.getDamage(list1);
                    if (Damagerr.getHealth() + NewAPI.getBloodSuck(list1) * damage / 100 < NewAPI.getMaxHealth(Damagerr)) {
                        Damagerr.setHealth(Damagerr.getHealth() + NewAPI.getBloodSuck(list1) * damage / 100);
                    } else {
                        Damagerr.setHealth(NewAPI.getMaxHealth(Damagerr));
                    }
//                    if (NewAPI.getReboundDamage(list2) * damage / 100 > 0) {
//                        if (canPVP(Damagerr, entity)) {
//                            Damagerr.damage(NewAPI.getReboundDamage(list2) * damage / 100);
//                        }
//                    }
                    damage -= NewAPI.getDefense(list2);
                    try {
                        NewAPI.removeDurability(list2);
                    } catch (NoSuchMethodError ee) {
                    }
                }
            } else {
                Projectile psw = (Projectile) Damager;
                ProjectileSource ps = psw.getShooter();
                if ((ps instanceof LivingEntity) && (e.getEntity() instanceof LivingEntity)) {
                    LivingEntity p = (LivingEntity) ps;
                    LivingEntity entity = (LivingEntity) e.getEntity();
                    List<ItemStack> list2 = NewAPI.addAll(NewAPI.getItemInHand(entity), NewAPI.getItemInOffHand(entity), entity.getEquipment().getHelmet(), entity.getEquipment().getChestplate(), entity.getEquipment().getLeggings(), entity.getEquipment().getBoots());
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
                    damage -= NewAPI.getDefense(list2);
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
        if(!(ps instanceof LivingEntity))return;
        LivingEntity le = (LivingEntity) ps;
        ShEntityIdMap.put(psw.getEntityId(), NewAPI.getDamage(NewAPI.addAll(NewAPI.getItemInHand(le), NewAPI.getItemInOffHand(le), le.getEquipment().getHelmet(), le.getEquipment().getChestplate(), le.getEquipment().getLeggings(), le.getEquipment().getBoots())));
        XxEntityIdMap.put(psw.getEntityId(), NewAPI.getBloodSuck(NewAPI.addAll(NewAPI.getItemInHand(le), NewAPI.getItemInOffHand(le), le.getEquipment().getHelmet(), le.getEquipment().getChestplate(), le.getEquipment().getLeggings(), le.getEquipment().getBoots())));
    }

    //优化完成
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerExpChangeEvent(PlayerExpChangeEvent e) {
        int value = e.getAmount();
        Player p = e.getPlayer();
        Double i = NewAPI.getExperience(NewAPI.addAll(NewAPI.getItemInHand(p), NewAPI.getItemInOffHand(p), p.getInventory().getHelmet(), p.getInventory().getChestplate(), p.getInventory().getLeggings(), p.getInventory().getBoots()));
        int jy = 0;
        if (i != 0) {
            jy = (int) (value * (100 + i) / 100);
        } else {
            return;
        }
        e.setAmount(jy);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Dataer.instance.playerSuitEffectList.containsKey(p.getUniqueId())) {
            NewAPI.setMaxHealth(p, Dataer.instance.playerSuitEffectHealthList.get(p.getUniqueId()));
            Dataer.instance.playerSuitEffectHealthList.remove(p.getUniqueId());
            Dataer.instance.playerSuitEffectList.remove(p.getUniqueId());
        }
    }

}
