package com.docsmart.microcalc.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculDao {

    // Récupérer tous les calculs triés par date (le plus récent en premier)
    @Query("SELECT * FROM calculs ORDER BY date DESC")
    fun getAllCalculs(): Flow<List<CalculEntity>>

    // Récupérer les calculs d'une année spécifique
    @Query("SELECT * FROM calculs WHERE annee = :annee ORDER BY date DESC")
    fun getCalculsByYear(annee: Int): Flow<List<CalculEntity>>

    // Récupérer un calcul par son ID
    @Query("SELECT * FROM calculs WHERE id = :id")
    suspend fun getCalculById(id: Int): CalculEntity?

    // Insérer un nouveau calcul
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalcul(calcul: CalculEntity)

    // Supprimer un calcul
    @Delete
    suspend fun deleteCalcul(calcul: CalculEntity)

    // Supprimer tous les calculs
    @Query("DELETE FROM calculs")
    suspend fun deleteAllCalculs()
}