package com.example.fitnesstrakcer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class ImcCalc(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "result") val result: Double,
    @ColumnInfo(name = "createdDate") val createdDate: Date = Date()
)
