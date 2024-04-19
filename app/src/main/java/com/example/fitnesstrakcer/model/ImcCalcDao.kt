package com.example.fitnesstrakcer.model

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ImcCalcDao {

    @Insert
    fun insert() {}
}