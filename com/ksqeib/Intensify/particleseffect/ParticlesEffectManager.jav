package ksqeib.Intensify.particleseffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;


public class ParticlesEffectManager {

    public static double getDistance(double x1, double y1, double x2, double y2) {
        double x = Math.abs(x2 - x1);
        double y = Math.abs(y2 - y1);
        return Math.sqrt(x * x + y * y);
    }

    public static double getAngle(double x1, double y1, double x2, double y2) {
        double x = x2 - x1;
        double y = y2 - y1;
        double hypotenuse = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double cos = x / hypotenuse;
        double radian = Math.acos(cos);
        double angle = 180 / (Math.PI / radian);
        if (y < 0) {
            angle = -angle;
        } else if ((y == 0) && (x < 0)) {
            angle = 180;
        }
        return angle;
    }

//    public void Draw_Wings(LivingEntity le, int dj) {
//        Wings w = NewCustomCuiLian.customCuilianManager.customCuilianLevelList.get(dj).wings;
//        int[][] a = w.EffectList;
//        for (int i = a.length - 1; i >= 0; i--) {
//            for (int j = a[i].length - 1; j >= 0; j--) {
//                if (a[i][j] != 0) {
//                    if (KsAPI.serverVersion==3) {
//                        Draw(le.getLocation(), le.getLocation().add(-a[i].length * 1.0 / 10 + j * 1.0 / 5 + 0.1, 0.2, 0), 1.8 - i * 1.0 / 5 + NewCustomCuiLian.customCuilianManager.customCuilianLevelList.get(dj).wings.z, ParticlesFor1_9.getParticle(a[i][j]), w.distanceAdd);
//                    } else if (KsAPI.serverVersion==2) {
//                        Draw(le.getLocation(), le.getLocation().add(-a[i].length * 1.0 / 10 + j * 1.0 / 5 + 0.1, 0.2, 0), 1.8 - i * 1.0 / 5 + NewCustomCuiLian.customCuilianManager.customCuilianLevelList.get(dj).wings.z, ParticlesFor1_8.getParticleEffectType(a[i][j]), w.distanceAdd);
//                    } else if (KsAPI.serverVersion==1) {
//                        Draw(le.getLocation(), le.getLocation().add(-a[i].length * 1.0 / 10 + j * 1.0 / 5 + 0.1, 0.2, 0), 1.8 - i * 1.0 / 5 + NewCustomCuiLian.customCuilianManager.customCuilianLevelList.get(dj).wings.z, ParticlesFor1_7.getEffect(a[i][j]), w.distanceAdd);
//                    }
//                }
//            }
//        }
//    }

    public void Draw(Location PlayerLocation, Location EffectLocation, double g, Particle pe, double addAngle) {
        Location Ploc = PlayerLocation.clone();
        Location Eloc = EffectLocation.clone();
        double jdc = getAngle(Ploc.getX(), Ploc.getY(), Eloc.getX(), Eloc.getY());
        if (jdc < 90) {
            jdc += addAngle;
        } else {
            jdc -= addAngle;
        }
        double jl = getDistance(Ploc.getX(), Ploc.getY(), Eloc.getX(), Eloc.getY());
        double radians = Math.toRadians(jdc + Ploc.getYaw() - 180);
        double x = Math.cos(radians) * jl;
        double y = Math.sin(radians) * jl;
        Ploc.add(x, g, y);
        Ploc.getWorld().spawnParticle(pe, Ploc, 0);
        Ploc.subtract(x, g, y);
    }

    public void Draw(Location PlayerLocation, Location EffectLocation, double g, ParticleEffect1_8 pe, double addAngle) {
        Location Ploc = PlayerLocation.clone();
        Location Eloc = EffectLocation.clone();
        double jdc = getAngle(Ploc.getX(), Ploc.getY(), Eloc.getX(), Eloc.getY());
        if (jdc < 90) {
            jdc += addAngle;
        } else {
            jdc -= addAngle;
        }
        double jl = getDistance(Ploc.getX(), Ploc.getY(), Eloc.getX(), Eloc.getY());
        double radians = Math.toRadians(jdc + Ploc.getYaw() - 180);
        double x = Math.cos(radians) * jl;
        double y = Math.sin(radians) * jl;
        Ploc.add(x, g, y);
        Bukkit.getOnlinePlayers().forEach((p) -> {
            pe.display(Ploc.getDirection(), 0, Ploc, p);
        });
        Ploc.subtract(x, g, y);
    }

    public void Draw(Location PlayerLocation, Location EffectLocation, double g, ParticleEffect1_7 pe, double addAngle) {
        Location Ploc = PlayerLocation.clone();
        Location Eloc = EffectLocation.clone();
        double jdc = getAngle(Ploc.getX(), Ploc.getY(), Eloc.getX(), Eloc.getY());
        if (jdc < 90) {
            jdc += addAngle;
        } else {
            jdc -= addAngle;
        }
        double jl = getDistance(Ploc.getX(), Ploc.getY(), Eloc.getX(), Eloc.getY());
        double radians = Math.toRadians(jdc + Ploc.getYaw() - 180);
        double x = Math.cos(radians) * jl;
        double y = Math.sin(radians) * jl;
        Ploc.add(x, g, y);
        Bukkit.getOnlinePlayers().forEach((p) -> {
            pe.display(Ploc.getDirection(), 0, Ploc, p);
        });
        Ploc.subtract(x, g, y);
    }
}
