package de.staticred.server;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.staticred.server.chestsystem.commands.ChestCommandExecutor;
import de.staticred.server.chestsystem.db.ChestDAO;
import de.staticred.server.commands.*;
import de.staticred.server.commands.arenacmd.ArenaCommandExecutor;
import de.staticred.server.db.DataBaseConnection;
import de.staticred.server.db.EventDAO;
import de.staticred.server.db.PerkDAO;
import de.staticred.server.eventblocker.*;
import de.staticred.server.filemanagment.ConfigFileManagment;
import de.staticred.server.objects.Event;
import de.staticred.server.objects.Perks;
import de.staticred.server.objects.Shop;
import de.staticred.server.objects.ShopItem;
import de.staticred.server.scoreboard.Scoreboard;
import de.staticred.server.util.ArenaFileManager;
import de.staticred.server.util.ArenaSignFileManager;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Score;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin {

    public static Main INSTANCE;

    public static Main getInstance() {
        return INSTANCE;
    }
    public static HashMap<Player, List<Perks>> enabledPerks = new HashMap<>();
    public static List<Player> flyWarn = new ArrayList<>();
    public static LuckPerms api;
    public static boolean vote = false;
    public static int voteYes;
    public static int voteNo;
    public static boolean canVote;
    public static List<Player> voted = new ArrayList<>();
    public static boolean ban;
    public static boolean confirmed;
    public static Economy eco;
    public static boolean updater;
    public static List<String> resultMessage = new ArrayList<>();
    public static int task;
    public static List<Player> cooldown = new ArrayList<>();
    public static HashMap<Player, Integer> playerTaskHashMap = new HashMap<>();
    public static int onlineplayer;
    public static Event currentEvent = null;
    public static double shopMultiplier = 1;
    public static ArrayList<String> leftInGame = new ArrayList<>();


    @Override
    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            try {
                for(Perks perk : PerkDAO.getInstance().getPerks(p.getUniqueId())) {
                    executePerkChange(p,perk,false);
                }
                p.closeInventory();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            System.out.println("Verbindung zu Vault fehlgeschlagen!");
            return false;
        }
        System.out.println("Verbindung zu Vault erfolgreich hergestellt");
        eco = rsp.getProvider();
        return eco != null;
    }

    public static void sendMessageToBungee(Player p, String channel, String subchannel, Object... data) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subchannel);

        for(Object object : data) {
            out.writeUTF(object.toString());
        }

        p.sendPluginMessage(Main.getInstance(), channel, out.toByteArray());
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        ConfigFileManagment.INSTANCE.loadFile();
        loadPerkDatabase();
        loadHeadTable();
        loadPremiumVote();
        loadTableVote();
        setupEconomy();


        ArenaSignFileManager.getInstance().loadFile();

        getServer().getPluginManager().registerEvents(new DeathEvent(),this);
        getServer().getPluginManager().registerEvents(new FlyToggleEvent(),this);
        getServer().getPluginManager().registerEvents(new HungerEvent(),this);
        getServer().getPluginManager().registerEvents(new LoginEvent(),this);
        getServer().getPluginManager().registerEvents(new MoveEvent(),this);
        getServer().getPluginManager().registerEvents(new InventoryClickEvent(),this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEvent(),this);
        getServer().getPluginManager().registerEvents(new EXPChangeEvent(),this);
        getServer().getPluginManager().registerEvents(new ChatEvent(),this);
        getServer().getPluginManager().registerEvents(new FireBurnEvent(),this);
        getServer().getPluginManager().registerEvents(new CommandListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerPortalEvent(),this);
        getServer().getPluginManager().registerEvents(new EventInventoryListener(),this);
        getServer().getPluginManager().registerEvents(new DamageEvent(),this);
        getServer().getPluginManager().registerEvents(new EntityDeathEvent(),this);
        getServer().getPluginManager().registerEvents(new VillagerClickedEvent(),this);
        getServer().getPluginManager().registerEvents(new PlotInventoryListener(),this);
        getServer().getPluginManager().registerEvents(new SignChangeEvent(),this);
        getServer().getPluginManager().registerEvents(new OnSignClick(),this);
        getServer().getPluginManager().registerEvents(new PlayerBlockDestroyEvent(),this);
        getServer().getPluginManager().registerEvents(new AnvilEvent(),this);
        getServer().getPluginManager().registerEvents(new PlayerSwitchWoirldEvent(),this);

        getServer().getMessenger().registerIncomingPluginChannel( this, "c:bungeecord", new PluginMessager()); // we register the incoming channel
        getServer().getMessenger().registerOutgoingPluginChannel( this, "c:bungeecord");

        getCommand("head").setExecutor(new HeadCommandExecutor());
        getCommand("premium").setExecutor(new PremiumCommandExecutor());
        getCommand("zvote").setExecutor(new VoteCommandExecutor());
        getCommand("v").setExecutor(new VCommandExecutor());
        getCommand("perks").setExecutor(new PerksCommandExecutor());
        getCommand("debug").setExecutor(new DebugCommandExecutor());
        getCommand("showborder").setExecutor(new ShowBorderCommandExecutor());
        getCommand("openshop").setExecutor(new ShopCommandExecutor());
        getCommand("spec").setExecutor(new SpecCommandExecutor());
        getCommand("event").setExecutor(new EventCommandExecutor());
        getCommand("tickets").setExecutor(new TicketCommandExecutor());
        getCommand("spawnvillager").setExecutor(new SpawnUpgradeVillagerCommandExecutor());
        getCommand("arena").setExecutor(new ArenaCommandExecutor());
        getCommand("chest").setExecutor(new ChestCommandExecutor());


        try {
            EventDAO.getInstance().loadTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ChestDAO.getInstance().loadTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel( this, "BungeeCord", new PluginChannelListener()); // we register the incoming channel



        loadPerms();
        for(Player p : Bukkit.getOnlinePlayers()) {

            if(!updater) {
                Scoreboard.startUpdater();
                updater = true;
                ArenaFileManager.getInstance().loadFile();
            }

            for(PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }

            p.setFlying(false);
            p.setAllowFlight(false);

            if(!enabledPerks.containsKey(p)) enabledPerks.put(p,new ArrayList<>());
            try {
                for(Perks perk : PerkDAO.getInstance().getPerks(p.getUniqueId())) {
                    executePerkChange(p,perk,true);
                    enabledPerks.get(p).add(perk);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    }


    public void loadPerkDatabase() {
        try{
            DataBaseConnection con = DataBaseConnection.INSTANCE;
            con.openConnection();
            con.executeUpdate("CREATE TABLE IF NOT EXISTS perks(UUID VARCHAR(36), perk VARCHAR(100))");
            con.closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void executePerkChange(Player p, Perks perk, boolean activation) {
        if(perk == Perks.FLY_PERK) {
            if(p.hasPermission("perk.fly")) {
                if(activation) p.setAllowFlight(true);
                if(!activation) p.setAllowFlight(false);
            }else{
                p.setAllowFlight(false);
                p.setFlying(false);
            }
        }
        if(perk == Perks.FAST_DESTROY_PERK) {
            if(p.hasPermission("perk.fastdestroy")) {
                if(activation) p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999, 2,true,false));
                if(!activation) p.removePotionEffect(PotionEffectType.FAST_DIGGING);
            }else{
                p.removePotionEffect(PotionEffectType.FAST_DIGGING);
            }


        }
        if(perk == Perks.SPEED_PERK) {
            if(activation) p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1,true,false));
            if(!activation) p.removePotionEffect(PotionEffectType.SPEED);
        }
    }


    public void loadPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        }
    }

    public void loadTableVote() {
        try{
            DataBaseConnection con = DataBaseConnection.INSTANCE;
            con.openConnection();
            con.executeUpdate("CREATE TABLE IF NOT EXISTS votes(UUID VARCHAR(36), timeStamp LONG, ban boolean)");
            con.closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadPremiumVote() {
        try{
            DataBaseConnection con = DataBaseConnection.INSTANCE;
            con.openConnection();
            con.executeUpdate("CREATE TABLE IF NOT EXISTS premium(UUID VARCHAR(36) PRIMARY KEY, timeStamp LONG)");
            con.closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void loadHeadTable() {
        try{
            DataBaseConnection con = DataBaseConnection.INSTANCE;
            con.openConnection();
            con.executeUpdate("CREATE TABLE IF NOT EXISTS heads(UUID VARCHAR(36) PRIMARY KEY, timeStamp LONG)");
            con.closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Shop getShop() {

        Shop shop = new Shop("§aShop",5,Main.getInstance());


        shop.addItem(new ShopItem(Material.GRASS_BLOCK,"§aOverworldshop",0,0,1));
        shop.addItem(new ShopItem(Material.DIAMOND_ORE,"§aErzshop",0,0,1));
        shop.addItem(new ShopItem(Material.COOKED_BEEF,"§aEssenshop",0,0,1));
        shop.addItem(new ShopItem(Material.WATER_BUCKET,"§aMeeresshop",0,0,1));
        shop.addItem(new ShopItem(Material.NETHER_BRICK,"§aNethershop",0,0,1));
        shop.addItem(new ShopItem(Material.END_STONE,"§aEndshop",0,0,1));
        shop.addItem(new ShopItem(Material.STRING,"§aDropshop",0,0,1));
        shop.addItem(new ShopItem(Material.WHITE_WOOL,"§aWolleshop",0,0,1));
        shop.addItem(new ShopItem(Material.BEACON,"§aAdmin shop",0,0,1));
        shop.addItem(new ShopItem(Material.GOLDEN_APPLE,"§6§lBONUS-SHOP",0,0,1));



        return shop;
    }

    public static Shop getAdminShop() {
        Shop shop = new Shop("Admin shop",4,Main.getInstance());

        shop.addItem(new ShopItem(Material.ENDER_CHEST,"§8Enderchest",2500,0,1));
        shop.addItem(new ShopItem(Material.DRAGON_EGG,"§8Drachenei",150000,0,1));
        shop.addItem(new ShopItem(Material.SADDLE,"§8Sattel",3500,0,1));
        shop.addItem(new ShopItem(Material.DIAMOND_HORSE_ARMOR,"§8Dia Pferderüstung",2500,0,1));
        shop.addItem(new ShopItem(Material.IRON_HORSE_ARMOR,"§8Eisen Pferderüstung",1500,0,1));
        shop.addItem(new ShopItem(Material.GOLDEN_HORSE_ARMOR,"§8Gold Pferderüstung",1250,0,1));
        shop.addItem(new ShopItem(Material.CONDUIT,"§8Aquisator",8000,0,1));
        shop.addItem(new ShopItem(Material.EXPERIENCE_BOTTLE,"§8XP Bottle",1000,0,64));
        shop.addItem(new ShopItem(Material.NAME_TAG,"§8Nametag",1500,0,1));
        shop.addItem(new ShopItem(Material.TURTLE_HELMET,"§8Schiltkrötenpanzer",3000,0,1));
        shop.addItem(new ShopItem(Material.TRIDENT,"§8Dreizack",4000,0,1));
        shop.addItem(new ShopItem(Material.TOTEM_OF_UNDYING,"§8Totem der Unsterblichkeit",5000,0,1));
        shop.addItem(new ShopItem(Material.DRAGON_BREATH,"§8Drachenatem",6000,0,64));
        shop.addItem(new ShopItem(Material.ELYTRA,"§8Elytra",50000,0,1));
        shop.addItem(new ShopItem(Material.COBWEB,"§8Spinnennetz",2500,0,64));
        shop.addItem(new ShopItem(Material.FIREWORK_ROCKET,"§8Rakete",500,0,64));
        shop.addItem(new ShopItem(Material.BEACON,"§8Beacon",40000,0,1));

        shop.addItem(new ShopItem(Material.WOLF_SPAWN_EGG,"§bWolf",15000,0,1));
        shop.addItem(new ShopItem(Material.PANDA_SPAWN_EGG,"§bPanda",10000,0,1));
        shop.addItem(new ShopItem(Material.HORSE_SPAWN_EGG,"§bHorse",10000,0,1));
        shop.addItem(new ShopItem(Material.PARROT_SPAWN_EGG,"§bPapagei",12000,0,1));
        shop.addItem(new ShopItem(Material.CAT_SPAWN_EGG,"§bKatze",12000,0,1));
        shop.addItem(new ShopItem(Material.VILLAGER_SPAWN_EGG,"§bVillager",20000,0,1));

        shop.addItem(new ShopItem(Material.COW_SPAWN_EGG,"§bKuh",2000,0,1));
        shop.addItem(new ShopItem(Material.SHEEP_SPAWN_EGG,"§bSchaf",2000,0,1));
        shop.addItem(new ShopItem(Material.PIG_SPAWN_EGG,"§bSchwein (Haram)",2000,0,1));


        shop.addItem(new ShopItem(Material.BARRIER,"§cBack",0,0,1));


        return shop;
    }

    public static Shop getPerkShop() {
        Shop shop = new Shop("Perk shop",4,Main.getInstance());

        shop.addItem(new ShopItem(Material.FEATHER,"§bFlyperk",100000,0,1));
        shop.addItem(new ShopItem(Material.CHEST,"§bKeep-Inventory",125000,0,1));
        shop.addItem(new ShopItem(Material.EXPERIENCE_BOTTLE,"§bKeep-XP",70000,0,1));
        shop.addItem(new ShopItem(Material.COOKED_BEEF,"§bAnti-Hunger",70000,0,1));
        shop.addItem(new ShopItem(Material.DIAMOND_BOOTS,"§bSpeed",60000,0,1));
        shop.addItem(new ShopItem(Material.DIAMOND_PICKAXE,"§bSchneller abbauen",70000,0,1));
        shop.addItem(new ShopItem(Material.ENDER_EYE,"§bNightvision",30000,0,1));
        shop.addItem(new ShopItem(Material.EXPERIENCE_BOTTLE,"§bDouble XP",70000,0,1));
        shop.addItem(new ShopItem(Material.ENCHANTED_GOLDEN_APPLE,"§b5 Plots",100000,0,1));
        shop.addItem(new ShopItem(Material.ENDER_CHEST,"§b/ec",15000,0,1));
        shop.addItem(new ShopItem(Material.PAPER,"§b/invsee",15000,0,1));
        shop.addItem(new ShopItem(Material.CRAFTING_TABLE,"§b/workbench",15000,0,1));

        shop.addItem(new ShopItem(Material.BARRIER,"§cBack",0,0,1));


        return shop;
    }

    public static Shop getEndShop() {
        Shop shop = new Shop("End shop",4,Main.getInstance());
        shop.addItem(new ShopItem(Material.PURPUR_BLOCK,"§8Purpurblock",800,0,64));
        shop.addItem(new ShopItem(Material.DRAGON_HEAD,"§8Drachenkopf",2500,200,1));
        shop.addItem(new ShopItem(Material.SHULKER_BOX,"§8Shulkerbox",10000,500,1));
        shop.addItem(new ShopItem(Material.END_ROD,"§8Endstab",2500,300,64));
        shop.addItem(new ShopItem(Material.BARRIER,"§cBack",0,0,1));

        return shop;
    }

    public static Shop getSeaShop() {
        Shop shop = new Shop("Meeres shop",4,Main.getInstance());

        shop.addItem(new ShopItem(Material.PRISMARINE,"§8Prisamrin",800,180,64));
        shop.addItem(new ShopItem(Material.DARK_PRISMARINE,"§8Dunkler Prisamrin",1500,150,64));
        shop.addItem(new ShopItem(Material.SEA_LANTERN,"§8See laterne",1500,300,64));
        shop.addItem(new ShopItem(Material.DRIED_KELP_BLOCK,"§8Seetangblock",600,70,64));
        shop.addItem(new ShopItem(Material.TURTLE_EGG,"§8Schildkrötenei",1500,300,64));
        shop.addItem(new ShopItem(Material.TUBE_CORAL_BLOCK,"§8Korallenblock",1000,150,64));
        shop.addItem(new ShopItem(Material.BRAIN_CORAL_BLOCK,"§8Korallenblock2",1000,150,64));
        shop.addItem(new ShopItem(Material.BUBBLE_CORAL_BLOCK,"§8Korallenblock3",1000,150,64));
        shop.addItem(new ShopItem(Material.FIRE_CORAL_BLOCK,"§8Korallenblock4",1000,150,64));
        shop.addItem(new ShopItem(Material.HORN_CORAL_BLOCK,"§8Korallenblock",1000,200,64));
        shop.addItem(new ShopItem(Material.NAUTILUS_SHELL,"§8Nautilusschale",600,100,1));
        shop.addItem(new ShopItem(Material.HEART_OF_THE_SEA,"§8Herz des Meeres",5000,400,1));
        shop.addItem(new ShopItem(Material.SPONGE,"§8Schwamm",5000,500,64));
        shop.addItem(new ShopItem(Material.BARRIER,"§cBack",0,0,1));


        return shop;

    }

    public static Shop getDropShop() {
        Shop shop = new Shop("Drop shop",4,Main.getInstance());

        shop.addItem(new ShopItem(Material.LEATHER,"§8Leather",400,50,64));
        shop.addItem(new ShopItem(Material.FEATHER,"§8Feather",400,50,64));
        shop.addItem(new ShopItem(Material.STRING, "§8Faden", 200, 10, 64));
        shop.addItem(new ShopItem(Material.SLIME_BALL, "§8Schleimball", 800, 150, 64));
        shop.addItem(new ShopItem(Material.INK_SAC, "§8Tintenbeutel", 250, 50, 64));
        shop.addItem(new ShopItem(Material.ROTTEN_FLESH, "§8Erotisches Fleisch", 250, 25, 64));
        shop.addItem(new ShopItem(Material.ZOMBIE_HEAD, "§8Zombie kopf", 12000, 600, 64));
        shop.addItem(new ShopItem(Material.CREEPER_HEAD, "§8Creeper kopf", 12000, 600, 64));
        shop.addItem(new ShopItem(Material.BARRIER,"§cBack",0,0,1));

        return shop;
    }

    public static Shop getNetherShop() {
        Shop shop = new Shop("Nether shop",4,Main.getInstance());
        shop.addItem(new ShopItem(Material.NETHER_BRICK,"§8Netherziegel",300,60,64));
        shop.addItem(new ShopItem(Material.QUARTZ_BLOCK,"§8Quartzblock",1500,250,64));
        shop.addItem(new ShopItem(Material.GLOWSTONE,"§8Glowstone",2000,0,64));
        shop.addItem(new ShopItem(Material.BARRIER,"§cBack",0,0,1));

        return shop;
    }

    public static Shop getFoodShop() {
        Shop shop = new Shop("Food shop",4,Main.getInstance());
        shop.addItem(new ShopItem(Material.COOKED_BEEF,"§8Steak",50,0,64));
        shop.addItem(new ShopItem(Material.COOKED_SALMON,"§8Lachs",50,50,64));
        shop.addItem(new ShopItem(Material.COOKED_COD,"§8Fich",50,50,64));
        shop.addItem(new ShopItem(Material.TROPICAL_FISH,"§8Fisch",50,50,64));
        shop.addItem(new ShopItem(Material.HONEY_BLOCK,"§8Honigblock",50,250,64));
        shop.addItem(new ShopItem(Material.BARRIER,"§cBack",0,0,1));

        return shop;
    }

    public static Shop getOverworldShop() {
        Shop shop = new Shop("Overworld shop",4,Main.getInstance());
        shop.addItem(new ShopItem(Material.DIRT,"§8Dirt",50,0,64));
        shop.addItem(new ShopItem(Material.SAND,"§8Sand",100,20,64));
        shop.addItem(new ShopItem(Material.RED_SAND,"§8Roter Sand",120,25,64));
        shop.addItem(new ShopItem(Material.OAK_LOG,"§8Eiche",500,100,64));
        shop.addItem(new ShopItem(Material.BIRCH_LOG,"§8Birke",500,100,64));
        shop.addItem(new ShopItem(Material.SPRUCE_LOG,"§8Fichte",500,100,64));
        shop.addItem(new ShopItem(Material.JUNGLE_LOG,"§8Jungel",500,100,64));
        shop.addItem(new ShopItem(Material.ACACIA_LOG,"§8Akazie",500,100,64));
        shop.addItem(new ShopItem(Material.DARK_OAK_LOG,"§8Schwarzeiche",600,125,64));
        shop.addItem(new ShopItem(Material.GRAVEL, "§8Kies", 200, 40, 64));
        shop.addItem(new ShopItem(Material.SANDSTONE, "§8Sandstein", 200, 30, 64));
        shop.addItem(new ShopItem(Material.RED_SANDSTONE, "§8Roter Sandstein", 250, 35, 64));
        shop.addItem(new ShopItem(Material.BRICK, "§8Ziegelstein", 600, 150, 64));
        shop.addItem(new ShopItem(Material.COBBLESTONE, "§8Bruchstein", 100, 0, 64));
        shop.addItem(new ShopItem(Material.GRANITE, "§8Granit", 30, 0, 64));
        shop.addItem(new ShopItem(Material.DIORITE, "§8Diorit", 30, 0, 64));
        shop.addItem(new ShopItem(Material.ANDESITE, "§8Andesit", 30, 0, 64));
        shop.addItem(new ShopItem(Material.BARRIER,"§cBack",0,0,1));


        return shop;
    }

    public static Shop getOreShop() {
        Shop shop = new Shop("Erze shop",4,Main.getInstance());
        shop.addItem(new ShopItem(Material.REDSTONE,"§8Redstone",500,100,64));
        shop.addItem(new ShopItem(Material.COAL,"§8Kohle",600,120,64));
        shop.addItem(new ShopItem(Material.IRON_INGOT,"§8Eisen",600,50,64));
        shop.addItem(new ShopItem(Material.GOLD_INGOT,"§8Gold",1200,250,64));
        shop.addItem(new ShopItem(Material.LAPIS_LAZULI,"§8Lapis",800,100,64));
        shop.addItem(new ShopItem(Material.DIAMOND,"§8Diamant",1000,450,64));
        shop.addItem(new ShopItem(Material.BARRIER,"§cBack",0,0,1));

        return shop;
    }

    public static Shop getWoolShop() {
        Shop shop = new Shop("Wolle shop",4,Main.getInstance());

        shop.addItem(new ShopItem(Material.WHITE_WOOL,"§8Weiße wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.BLACK_WOOL,"§8Schwarze wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.RED_WOOL,"§8Rote wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.ORANGE_WOOL,"§8Orangene wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.GREEN_WOOL,"§8Grüne wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.BLUE_WOOL,"§8Blaue wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.LIME_WOOL,"§8Hellgrüne wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.YELLOW_WOOL,"§8Gelbe wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.CYAN_WOOL,"§8Türkise wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.MAGENTA_WOOL,"§8Magenta wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.BROWN_WOOL,"§8Braune wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.PURPLE_WOOL,"§8Lila wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.GRAY_WOOL,"§8Graue wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.LIGHT_BLUE_WOOL,"§8Hellblaue wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.LIGHT_GRAY_WOOL,"§8Hellgraue wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.PINK_WOOL,"§8Pink wolle", 200, 60, 64));
        shop.addItem(new ShopItem(Material.BARRIER,"§cBack",0,0,1));

        return shop;
    }



}
