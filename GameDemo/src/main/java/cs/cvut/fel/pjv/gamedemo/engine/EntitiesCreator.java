package cs.cvut.fel.pjv.gamedemo.engine;

import cs.cvut.fel.pjv.gamedemo.common_classes.*;
import cs.cvut.fel.pjv.gamedemo.engine.utils.RandomHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntitiesCreator {
    private static final Logger logger = LogManager.getLogger(EntitiesCreator.class);
    /**
     * Set the entity as the default enemy.
     * Speed and health are set to random values within the specified range.
     * Note: hitbox size, attack range size, damage and cooldown are set to default values, but it will be changed in the future (randomized).
     */
    private static void setAsDefaultEnemy(Entity entity) {
        logger.debug("Setting entity " + entity.getName() + " as default enemy...");
        entity.setType(Constants.EntityType.ENEMY);
        entity.setBehaviour(Constants.Behaviour.AGGRESSIVE);
        entity.setSpeedX((int) (Math.random() * (Constants.ENEMY_BASIC_SPEED_X_MAX - Constants.ENEMY_BASIC_SPEED_X_MIN) + Constants.ENEMY_BASIC_SPEED_X_MIN));
        entity.setSpeedY((int) (Math.random() * (Constants.ENEMY_BASIC_SPEED_Y_MAX - Constants.ENEMY_BASIC_SPEED_Y_MIN) + Constants.ENEMY_BASIC_SPEED_Y_MIN));
        int maxHealth = (int) (Math.random() * (Constants.ENEMY_BASIC_MAX_HEALTH_MAX - Constants.ENEMY_BASIC_MAX_HEALTH_MIN) + Constants.ENEMY_BASIC_MAX_HEALTH_MIN);
        maxHealth = (maxHealth / 10) * 10;
        entity.setMaxHealth(maxHealth);
        entity.setHealth(maxHealth);
        entity.setDamage(Constants.ENEMY_BASIC_DAMAGE);
        entity.setCooldown(Constants.ENEMY_BASIC_COOLDOWN);
        logger.debug("Entity " + entity.getName() + " set as default enemy");
    }

    /**
     * Create a zombie entity.
     * @return - the zombie entity
     */
    public static Entity createZombie() {
        logger.debug("Creating zombie...");
        Entity zombie = new Entity(Constants.ZOMBIE, Constants.ENEMY_TEXTURE_PATH + Constants.ZOMBIE + "_front.png");
        setAsDefaultEnemy(zombie);
        logger.debug("Zombie created");
        return zombie;
    }

    /**
     * Create a robot entity.
     * @return - the robot entity
     */
    public static Entity createRobot() {
        logger.debug("Creating robot...");
        Entity robot = new Entity(Constants.ROBOT, Constants.ENEMY_TEXTURE_PATH + Constants.ROBOT + "_front.png");
        setAsDefaultEnemy(robot);
        robot.setIntelligence(Constants.Intelligence.HIGH);
        logger.debug("Robot created");
        return robot;
    }

    /**
     * Create a gentleman entity.
     * @return - the gentleman entity
     */
    public static Entity createGentleman() {
        logger.debug("Creating gentleman...");
        Entity gentleman = new Entity(Constants.GENTLEMAN, Constants.ENEMY_TEXTURE_PATH + Constants.GENTLEMAN + "_front.png");
        setAsDefaultEnemy(gentleman);
        gentleman.setIntelligence(Constants.Intelligence.MEDIUM);
        logger.debug("Gentleman created");
        return gentleman;
    }
    /**
     * Set the entity as the default NPC.
     * Speed and health are set to random values within the specified range.
     * Note: hitbox size, attack range size, damage and cooldown are set to default values, but it will be changed in the future (randomized).
     */
    public static void setAsDefaultNPC(Entity npc) {
        logger.debug("Setting entity " + npc.getName() + " as default NPC...");
        setAsDefaultEnemy(npc);
        npc.setType(Constants.EntityType.NPC);
        npc.setBehaviour(Constants.Behaviour.NEUTRAL);
        npc.setIntelligence(Constants.Intelligence.values()[(int) (Math.random() * Constants.Intelligence.values().length)]);
        if (npc.getDialoguePath() == null) {
            npc.setDialoguePath(RandomHandler.getRandomDialogueThatStartsWith(npc.getName()));
        }
        npc.setNegativeThreshold((int) (Math.random() * (Constants.ENTITY_BASIC_MAX_NEGATIVE_THRESHOLD - Constants.ENTITY_BASIC_MIN_NEGATIVE_THRESHOLD) + Constants.ENTITY_BASIC_MIN_NEGATIVE_THRESHOLD));
        logger.debug("Entity " + npc.getName() + " set as default NPC");
    }

    /**
     * Create an NPC entity.
     * @param type - the type of the wagon
     * @return - the NPC entity according to the wagon type
     */
    public static Entity createNPC(String type) {
        logger.debug("Creating NPC...");
        String[] names = Constants.WAGON_TYPE_NPC.get(type);
        String name = names[(int) (Math.random() * names.length)];
        Entity npc = new Entity(name, Constants.NPC_TEXTURE_PATH + "default/" + name + "_front.png");
        setAsDefaultNPC(npc);
        logger.debug("NPC created");
        return npc;
    }

    /**
     * Set the entity as the default player.
     * @param player - the player entity
     */
    public static void setAsDefaultPlayer(Player player) {
        logger.debug("Setting entity " + player.getName() + " as default player...");
        player.setType(Constants.EntityType.PLAYER);
        player.setBehaviour(Constants.Behaviour.AGGRESSIVE);
        player.setHealth(Constants.PLAYER_MAX_HEALTH);
        player.setDamage(Constants.PLAYER_BASIC_DAMAGE);
        player.setHitBoxSize(Constants.PLAYER_HITBOX);
        player.setAttackRangeSize(Constants.PLAYER_ATTACK_RANGE);
        player.setCooldown(Constants.PLAYER_COOLDOWN);
        player.setHeight(2);
        logger.debug("Entity " + player.getName() + " set as default player");
    }

    /**
     * Create a bully entity.
     * @return - the bully entity
     */
    public static Entity createBully() {
        logger.debug("Creating bully...");
        Entity bully = new Entity(Constants.BULLY, Constants.ENEMY_TEXTURE_PATH + "zombie_front.png");
        setAsDefaultEnemy(bully);
        bully.setBehaviour(Constants.Behaviour.BULLY);
        bully.setIntelligence(Constants.Intelligence.MEDIUM);
        bully.setCooldown(Constants.BULLY_BASIC_KIDNAP_COOLDOWN);
        bully.setSpeedX(Constants.PLAYER_BASIC_SPEED_X - 1);
        bully.setSpeedY(Constants.PLAYER_BASIC_SPEED_Y - 1);
        logger.debug("Bully created");
        return bully;
    }

    /**
     * Set the entity as the default boss.
     * @param entity - the boss entity
     * @param bossStats - the boss stats
     */
    public static void setAsBoss(Entity entity, int[] bossStats) {
        logger.debug("Setting entity " + entity.getName() + " as boss...");
        int health = bossStats[0];
        int damage = bossStats[1];
        int hitbox = bossStats[2];
        int attackRange = bossStats[3];
        int cooldown = bossStats[4];
        entity.setType(Constants.EntityType.BOSS);
        entity.setBehaviour(Constants.Behaviour.AGGRESSIVE);
        entity.setHeight(Constants.BOSS_BASIC_HEIGHT);
        entity.setSpeedX(Constants.BOSS_BASIC_SPEED_X);
        entity.setSpeedY(Constants.BOSS_BASIC_SPEED_Y);
        entity.setMaxHealth(health);
        entity.setHealth(health);
        entity.setHitBoxSize(hitbox);
        entity.setAttackRangeSize(attackRange);
        entity.setDamage(damage);
        entity.setCooldown(cooldown);
        logger.debug("Entity " + entity.getName() + " set as boss");
    }

    /**
     * Create a boss entity.
     * @return - the boss entity
     */
    public static Entity createGuard() {
        logger.debug("Creating guard...");
        Entity guard = new Entity("guard", Constants.ENEMY_TEXTURE_PATH + "guard_front.png");
        EntitiesCreator.setAsGuard(guard);
        guard.setWidth(3);
        logger.debug("Guard created");
        return guard;
    }

    /**
     * Set the entity as the guard.
     * @param guard - the guard entity
     */
    public static void setAsGuard(Entity guard) {
        logger.debug("Setting entity " + guard.getName() + " as guard...");
        setAsDefaultNPC(guard);
        guard.setType(Constants.EntityType.GUARD);
        if (Events.isPlayerKilledGuard()) {
            guard.setBehaviour(Constants.Behaviour.BULLY);
        }
        guard.setIntelligence(Constants.Intelligence.HIGH);
        guard.setCooldown(1);
        guard.setAttackRangeSize(2);
        guard.setSpeedX(Constants.PLAYER_BASIC_SPEED_X - 1);
        guard.setSpeedY(Constants.PLAYER_BASIC_SPEED_Y - 1);
        logger.debug("Entity " + guard.getName() + " set as guard");
    }

    /**
     * Create a grandmother entity.
     * @param wagon - the wagon where the grandmother is created
     */
    public static void createGrandmother(Wagon wagon) {
        logger.debug("Creating grandmother...");
        Entity grandmother = new Vendor(Constants.GRANDMOTHER, Constants.ENEMY_TEXTURE_PATH + Constants.CONDUCTOR + "_front.png");
        grandmother.setWidth(3);
        grandmother.setType(Constants.EntityType.CONDUCTOR);
        grandmother.setDialoguePath("grandmother_default.json");
        initConductor(wagon, grandmother);
        logger.debug("Grandmother created");
    }

    /**
     * Create a conductor entity.
     * @param wagon - the wagon where the conductor is created
     */
    public static void createConductor(Wagon wagon) {
        logger.debug("Creating conductor...");
        Entity conductor = new Entity(Constants.CONDUCTOR, Constants.ENEMY_TEXTURE_PATH + Constants.CONDUCTOR + "_front.png");
        EntitiesCreator.setAsDefaultNPC(conductor);
        conductor.setType(Constants.EntityType.CONDUCTOR);
        initConductor(wagon, conductor);
        logger.debug("Conductor created");
    }

    /**
     * Initialize the conductor.
     * @param wagon - the wagon where the conductor is created
     * @param conductor - the conductor entity
     */
    private static void initConductor(Wagon wagon, Entity conductor) {
        logger.debug("Initializing conductor...");
        conductor.setCurrentWagon(wagon);
        conductor.setPositionX(wagon.getDoorRightTarget().getIsoX() - 32);
        conductor.setPositionY(wagon.getDoorRightTarget().getIsoY() - 80);
        conductor.setHitBoxSize(1);
        conductor.setAttackRangeSize(2);
        conductor.setNegativeThreshold(3);
        conductor.setWidth(3);
        conductor.setSpeedX(3);
        conductor.setSpeedY(3);
        conductor.getPreviousPositions().clear();
        conductor.setCounter(0);
        wagon.addEntity(conductor);
        logger.debug("Conductor initialized");
    }

    /**
     * Create a random enemy.
     * @param wagonType - the type of the wagon
     * @return - the random enemy entity according to the wagon type
     */
    public static Entity createRandomEnemy(String wagonType) {
        logger.debug("Creating random enemy...");
        Entity enemy;
        try {
            Method[] enemyCreators = Constants.WAGON_TYPE_ENEMIES.get(wagonType);
            Method enemyCreator = enemyCreators[(int) (Math.random() * enemyCreators.length)];
            enemy = (Entity) enemyCreator.invoke(EntitiesCreator.class);
            logger.info("Random enemy created: " + enemy.getName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("Error while creating random enemy: " + e);
            return null;
        }
        return enemy;
    }
}