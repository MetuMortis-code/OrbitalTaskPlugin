/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package me.metumortis.orbitaltask

import me.metumortis.orbitaltask.commands.Bal
import me.metumortis.orbitaltask.commands.Earn
import me.metumortis.orbitaltask.commands.Give
import me.metumortis.orbitaltask.commands.SetBal
import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection

class Main : JavaPlugin() {
    companion object {
        lateinit var connection: Connection
    }
    override fun onEnable() {
        // Create data folder and config if they don't exist
        if(!this.dataFolder.exists()) this.dataFolder.mkdir()
        this.saveDefaultConfig()
        this.reloadConfig()
        // Setting executors
        getCommand("bal")!!.setExecutor(Bal(this))
        getCommand("setBal")!!.setExecutor(SetBal(this))
        getCommand("give")!!.setExecutor(Give(this))
        getCommand("earn")!!.setExecutor(Earn(this))

        // Connect to database
        val connection = connect(this.dataFolder.absolutePath+"/data.sqlite")
        Main.connection = connection
        // Create statement
        val statement = connection.createStatement()
        // Create tables if they don't exist
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS balances (uuid VARCHAR(36) PRIMARY KEY, balance INTEGER)")
        statement.close()
    }

    override fun onDisable() {
        connection.close()
    }
}



