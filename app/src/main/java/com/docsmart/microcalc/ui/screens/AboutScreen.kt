package com.docsmart.microcalc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("À propos") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section Disclaimer
            SectionCard(
                title = "⚠️ Avertissement",
                content = """
                    Calcul Charges ME est un outil d'estimation à but informatif uniquement.
                    
                    Les calculs fournis sont indicatifs et basés sur les taux officiels en vigueur. Ils ne constituent en aucun cas un conseil juridique, fiscal ou comptable.
                    
                    L'éditeur ne peut être tenu responsable des erreurs, omissions ou inexactitudes dans les calculs, ni des conséquences de leur utilisation.
                    
                    Il est de votre responsabilité de vérifier les informations auprès de l'URSSAF et de votre expert-comptable avant toute déclaration officielle.
                    
                    Les taux et seuils sont susceptibles d'évoluer. Consultez toujours les sources officielles.
                """.trimIndent()
            )

            // Section Politique de confidentialité
            SectionCard(
                title = "🔒 Politique de confidentialité",
                content = """
                    Données collectées : AUCUNE
                    
                    Calcul Charges ME ne collecte, ne stocke et ne transmet aucune donnée personnelle à des serveurs externes.
                    
                    • Tous vos calculs sont stockés localement sur votre appareil
                    • Aucune connexion Internet n'est requise pour utiliser l'application
                    • Aucun tracker ou système d'analyse n'est utilisé
                    • Aucune donnée n'est partagée avec des tiers
                    
                    Suppression des données :
                    Vous pouvez supprimer vos calculs à tout moment via le bouton "Supprimer" dans l'historique. La désinstallation de l'application supprime définitivement toutes les données locales.
                    
                    Conformité RGPD :
                    L'application respecte le Règlement Général sur la Protection des Données (RGPD) en ne collectant aucune donnée personnelle.
                """.trimIndent()
            )

            // Section CGU
            SectionCard(
                title = "📄 Conditions Générales d'Utilisation",
                content = """
                    En utilisant Calcul Charges ME, vous acceptez les conditions suivantes :
                    
                    1. Utilisation
                    L'application est fournie "en l'état" sans garantie d'aucune sorte. L'utilisation est à vos propres risques.
                    
                    2. Exactitude des informations
                    Vous êtes responsable de l'exactitude des montants saisis et de la vérification des résultats.
                    
                    3. Propriété intellectuelle
                    L'application et son contenu sont protégés par le droit d'auteur.
                    
                    4. Modification des CGU
                    Ces conditions peuvent être modifiées à tout moment. La version actuelle fait foi.
                    
                    5. Droit applicable
                    Ces CGU sont régies par le droit français.
                """.trimIndent()
            )

            // Section Mentions légales
            SectionCard(
                title = "ℹ️ Mentions légales",
                content = """
                    Éditeur : DocSmart
                    
                    Application : Calcul Charges ME
                    Version : 1.0
                    
                    Hébergement des données : 
                    Stockage local sur l'appareil de l'utilisateur uniquement
                    
                    Contact : 
                    Pour toute question, contactez-nous via le Google Play Store
                    
                    Taux officiels sources :
                    • URSSAF (urssaf.fr)
                    • Service-Public.fr
                    
                    Dernière mise à jour : Janvier 2026
                """.trimIndent()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SectionCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}