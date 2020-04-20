package red.man10.man10wallet

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.ItemMergeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class Man10Wallet : JavaPlugin(), Listener {

    lateinit var money1 : ItemStack
    lateinit var money2 : ItemStack
    lateinit var money3 : ItemStack
    lateinit var money4 : ItemStack
    lateinit var money5 : ItemStack
    var cards = mutableListOf<ItemStack>()
    val wallets = HashMap<String,Wallet>()

    lateinit var inventory: Inventory


    override fun onEnable() {
        // Plugin startup logic
        saveDefaultConfig()

        money1 = config.getItemStack("money1")!!
        money2 = config.getItemStack("money2")!!
        money3 = config.getItemStack("money3")!!
        money4 = config.getItemStack("money4")!!
        money5 = config.getItemStack("money5")!!
        cards = config.getList("cards")!! as MutableList<ItemStack>

        server.pluginManager.registerEvents(this,this)

        inventory = Inventory(this)

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun createWallet(name:String):ItemStack?{

        val data = wallets[name]?:return null

        val wallet = ItemStack(Material.valueOf(data.type))
        val meta = wallet.itemMeta
        meta.setDisplayName(data.title)
        meta.lore = data.lore
        meta.setCustomModelData(data.cmd)

        meta.persistentDataContainer.set(NamespacedKey(this,"walletName"), PersistentDataType.STRING,name)


        meta.persistentDataContainer.set(NamespacedKey(this,"money1"), PersistentDataType.INTEGER,0)
        meta.persistentDataContainer.set(NamespacedKey(this,"money2"), PersistentDataType.INTEGER,0)
        meta.persistentDataContainer.set(NamespacedKey(this,"money3"), PersistentDataType.INTEGER,0)
        meta.persistentDataContainer.set(NamespacedKey(this,"money4"), PersistentDataType.INTEGER,0)
        meta.persistentDataContainer.set(NamespacedKey(this,"money5"), PersistentDataType.INTEGER,0)

        meta.persistentDataContainer.set(NamespacedKey(this,"cards"), PersistentDataType.STRING,"")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON)
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)

        meta.isUnbreakable = true

        if (data.isEnchantEffect){
            meta.addEnchant(Enchantment.LUCK,0,false)
        }

        wallet.itemMeta = meta

        return wallet
    }

    @EventHandler
    fun openWallet(e:PlayerInteractEvent){
        if (e.action != Action.RIGHT_CLICK_AIR || e.action != Action.LEFT_CLICK_BLOCK)return

        val p = e.player

        val item = e.item?:return
        val meta = item.itemMeta
        val name = meta.persistentDataContainer[NamespacedKey(this,"walletName"), PersistentDataType.STRING]?:return

        if (!wallets.keys.contains(name))return

        e.isCancelled = true

    }

    class Wallet{

        var title = "Wallet"
        var lore = mutableListOf<String>()
        var cmd = 0
        var type = "DIAMOND_HOE"
        var cashSize = mutableListOf<Int>()
        var cardSize = 0

        var isEnchantEffect = false

    }

}