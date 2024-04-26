package com.example.fitnesstrakcer.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImcCalcDao {
    @Insert
    fun insert(imc: ImcCalc)

    @Delete
    fun delete(imc: ImcCalc)

    @Query("SELECT * FROM ImcCalc WHERE type = :type")
    fun getRegisterByType(type: String): List<ImcCalc>
}