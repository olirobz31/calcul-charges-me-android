package com.docsmart.microcalc.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculs")
data class CalculEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Long,                    // Timestamp de la sauvegarde
    val chiffreAffaires: Double,       // CA entré
    val typeActivite: String,          // "vente", "prestations", "liberales"
    val cotisations: Double,           // Montant des cotisations
    val revenuNet: Double,             // Revenu après cotisations
    val mois: String,                  // Ex: "Janvier 2024"
    val annee: Int                     // Ex: 2024
)