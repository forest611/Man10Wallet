package red.man10.man10wallet

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class Inventory(private val pl : Man10Wallet) {

    fun open(item:ItemStack){

        val meta = item.itemMeta
        val name = meta.persistentDataContainer[NamespacedKey(pl,"walletName"), PersistentDataType.STRING]?:return

        val money = mutableListOf(
                meta.persistentDataContainer[NamespacedKey(pl,"money1"), PersistentDataType.INTEGER]?:return,
                meta.persistentDataContainer[NamespacedKey(pl,"money2"), PersistentDataType.INTEGER]?:return,
                meta.persistentDataContainer[NamespacedKey(pl,"money3"), PersistentDataType.INTEGER]?:return,
                meta.persistentDataContainer[NamespacedKey(pl,"money4"), PersistentDataType.INTEGER]?:return,
                meta.persistentDataContainer[NamespacedKey(pl,"money5"), PersistentDataType.INTEGER]?:return
                )

        val data = pl.wallets[name]?:return

        val status = mutableListOf("§e§l現在のステータス",
        "§9§l1万通貨：${money[0]}/${data.cashSize[0]}",
        "§9§l10万通貨：${money[1]}/${data.cashSize[1]}",
        "§9§l100万通貨：${money[2]}/${data.cashSize[2]}",
        "§9§l1000万通貨：${money[3]}/${data.cashSize[3]}",
        "§9§l1億通貨：${money[4]}/${data.cashSize[4]}")

        val inv = Bukkit.createInventory(null,9,"§a§lWallet")

        val save = ItemStack(Material.CHEST)
        val saveMeta = save.itemMeta

        saveMeta.setDisplayName("§9§l§nお金やカードをしまう")
        saveMeta.lore = status
        save.itemMeta = saveMeta

        inv.setItem(0,save)
        inv.setItem(1,save)
        inv.setItem(2,save)
        inv.setItem(3,save)
        
        val quit = ItemStack(Material.RED_STAINED_GLASS_PANE)
        val quitMeta = quit.itemMeta
        quitMeta.setDisplayName("§3§l閉じる")
        
        inv.setItem(4,quit)
        
        val take = ItemStack(Material.DISPENSER)
        val takeMeta = take.itemMeta
        takeMeta.setDisplayName("§9§lお金やカードを取り出す")
        saveMeta.lore = status
        take.itemMeta = takeMeta

        inv.setItem(5,take)
        inv.setItem(6,take)
        inv.setItem(7,take)
        inv.setItem(8,take)


    }


}