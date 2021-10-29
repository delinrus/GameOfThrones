package ru.skillbranch.gameofthrones.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

abstract class BaseDao<T>(private val tableName: String) {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(list: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(data: T)

    @RawQuery
    protected abstract fun getAll(query: SupportSQLiteQuery): List<T>

    fun getAll(): List<T> {
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName")
        return getAll(query)
    }

    @RawQuery
    protected abstract fun getById(query: SupportSQLiteQuery): T

    fun getById(id: String): T {
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE id = '$id'")
        return getById(query)
    }

    @RawQuery
    protected abstract fun count(query: SupportSQLiteQuery): Int

    fun count(): Int {
        val query = SimpleSQLiteQuery("SELECT COUNT(id) FROM $tableName")
        return count(query)
    }
}