package com.docsmart.microcalc.utils

/**
 * Objet contenant toutes les fonctions de calcul pour micro-entrepreneurs
 */
object CalculsCharges {

    // Taux de cotisations selon l'activité
    const val TAUX_VENTE = 0.128        // 12.8% pour vente de marchandises
    const val TAUX_PRESTATIONS = 0.220   // 22.0% pour prestations de services BNC
    const val TAUX_LIBERALES = 0.220     // 22.0% pour professions libérales

    // Seuils de CA annuels (2024)
    const val SEUIL_VENTE = 188700.0
    const val SEUIL_PRESTATIONS = 77700.0

    /**
     * Calcule les cotisations sociales
     * @param chiffreAffaires Le CA mensuel ou annuel
     * @param typeActivite "vente", "prestations" ou "liberales"
     * @return Le montant des cotisations
     */
    fun calculerCotisations(chiffreAffaires: Double, typeActivite: String, avecACRE: Boolean = false): Double {
        val taux = when (typeActivite.lowercase()) {
            "vente" -> TAUX_VENTE
            "prestations" -> TAUX_PRESTATIONS
            "liberales" -> TAUX_LIBERALES
            else -> TAUX_PRESTATIONS
        }
        // Si ACRE activé, diviser le taux par 2
        val tauxFinal = if (avecACRE) taux / 2 else taux
        return chiffreAffaires * tauxFinal
    }

    /**
     * Calcule le revenu net après cotisations
     */
    fun calculerRevenuNet(chiffreAffaires: Double, typeActivite: String, avecACRE: Boolean = false): Double {
        val cotisations = calculerCotisations(chiffreAffaires, typeActivite, avecACRE)
        return chiffreAffaires - cotisations
    }

    /**
     * Vérifie si le seuil annuel est dépassé
     */
    fun verifierSeuil(caAnnuel: Double, typeActivite: String): Boolean {
        val seuil = when (typeActivite.lowercase()) {
            "vente" -> SEUIL_VENTE
            "prestations" -> SEUIL_PRESTATIONS
            "liberales" -> SEUIL_PRESTATIONS
            else -> SEUIL_PRESTATIONS
        }
        return caAnnuel > seuil
    }

    /**
     * Calcule le pourcentage du seuil atteint
     */
    fun pourcentageSeuil(caAnnuel: Double, typeActivite: String): Double {
        val seuil = when (typeActivite.lowercase()) {
            "vente" -> SEUIL_VENTE
            "prestations" -> SEUIL_PRESTATIONS
            else -> SEUIL_PRESTATIONS
        }
        return (caAnnuel / seuil) * 100
    }
    /**
     * Calcule le CA annuel total à partir d'une liste de calculs
     */
    fun calculerCAnnuel(calculs: List<com.docsmart.microcalc.data.CalculEntity>, annee: Int): Double {
        return calculs
            .filter { it.annee == annee }
            .sumOf { it.chiffreAffaires }
    }

    /**
     * Calcule le montant restant avant d'atteindre le seuil
     */
    fun montantRestantAvantSeuil(caAnnuel: Double, typeActivite: String): Double {
        val seuil = when (typeActivite.lowercase()) {
            "vente" -> SEUIL_VENTE
            "prestations" -> SEUIL_PRESTATIONS
            "liberales" -> SEUIL_PRESTATIONS
            else -> SEUIL_PRESTATIONS
        }
        return (seuil - caAnnuel).coerceAtLeast(0.0)
    }
}