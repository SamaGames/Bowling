package net.samagames.bowling.entities;

import net.minecraft.server.v1_9_R2.World;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.UUID;

/**
 * Created by Rigner for project Bowling.
 */
public class StandBall extends Pin
{

    public static final String[] WOOL_NAMES = { ChatColor.WHITE + "Boule blanche", ChatColor.GOLD + "Boule orange", ChatColor.LIGHT_PURPLE + "Boule magenta",
            ChatColor.AQUA + "Boule bleu clair", ChatColor.YELLOW + "Boule jaune", ChatColor.GREEN + "Boule vert clair", ChatColor.LIGHT_PURPLE + "Boule rose",
            ChatColor.DARK_GRAY + "Bouble grise", ChatColor.GRAY + "Boule gris clair", ChatColor.DARK_AQUA + "Bouble cyan", ChatColor.DARK_PURPLE + "Boule violette",
            ChatColor.BLUE + "Bouble bleue", ChatColor.GOLD + "Boule marron", ChatColor.DARK_GREEN + "Boule verte", ChatColor.DARK_RED + "Boule rouge", ChatColor.BLACK + "Boule noire"};

    private BallDescription ballDescription;
    private UUID owner;

    public StandBall(World world)
    {
        super(world);
    }

    public StandBall(World world, UUID owner, Location location, BallDescription ballDescription)
    {
        this(world, owner, location, new Vector().zero(), ballDescription);
    }

    public StandBall(World world, UUID owner, Location location, Vector vector, BallDescription ballDescription)
    {
        super(world, location, vector);

        ArmorStand bukkitArmorStand = (ArmorStand)this.getBukkitEntity();

        ItemStack itemStack = new ItemStack(Material.WOOL, 1, ballDescription.ballColor);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StandBall.WOOL_NAMES[ballDescription.ballColor]);
        itemStack.setItemMeta(itemMeta);

        bukkitArmorStand.setHelmet(itemStack);
        bukkitArmorStand.setHeadPose(new EulerAngle(Math.PI / 2D, 0D, 0D));

        this.ballDescription = ballDescription;
        this.owner = owner;
    }

    @Override
    public double getWeight()
    {
        return this.ballDescription.ballWeight.weight;
    }

    @Override
    public double getRadius()
    {
        return 0.43D;
    }

    public UUID getOwner()
    {
        return this.owner;
    }

    @Override
    public void m()
    {
        super.m();
        if (this.ballDescription.particle != null)
            this.getBukkitEntity().getWorld().spawnParticle(this.ballDescription.particle, this.locX, this.locY, this.locZ, 4);
    }

    public static class BallDescription
    {
        private BallWeight ballWeight;
        private short ballColor;
        private Particle particle;

        public BallDescription(BallWeight ballWeight, short ballColor, Particle particle)
        {
            this.ballWeight = ballWeight;
            this.ballColor = ballColor;
            this.particle = particle;
        }

        public void setBallWeight(BallWeight ballWeight)
        {
            this.ballWeight = ballWeight;
        }

        public void setParticle(Particle particle)
        {
            this.particle = particle;
        }

        public void setBallColor(short ballColor)
        {
            this.ballColor = ballColor;
        }

        public BallWeight getBallWeight()
        {
            return ballWeight;
        }

        public Particle getParticle()
        {
            return particle;
        }

        public short getBallColor()
        {
            return ballColor;
        }
    }

    public enum BallWeight
    {
        LIGHT(3),
        MEDIUM(5),
        HEAVY(7);

        private double weight;

        BallWeight(double weight)
        {
            this.weight = weight;
        }
    }
}
