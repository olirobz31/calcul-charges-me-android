package com.docsmart.microcalc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.docsmart.microcalc.data.AppDatabase
import com.docsmart.microcalc.data.CalculEntity
import com.docsmart.microcalc.utils.CalculsCharges
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToHistory: () -> Unit, onNavigateToAbout: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = remember { AppDatabase.getDatabase(context) }

    var chiffreAffaires by remember { mutableStateOf("") }
    var typeActivite by remember { mutableStateOf("prestations") }
    var expanded by remember { mutableStateOf(false) }
    var avecACRE by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val activites = listOf(
        "vente" to "Vente de marchandises",
        "prestations" to "Prestations de services",
        "liberales" to "Professions libérales"
    )

    // Calculs en temps réel
    val ca = chiffreAffaires.toDoubleOrNull() ?: 0.0
    val cotisations = CalculsCharges.calculerCotisations(ca, typeActivite, avecACRE)
    val revenuNet = CalculsCharges.calculerRevenuNet(ca, typeActivite, avecACRE)

    // Récupérer tous les calculs pour calculer le CA annuel
    val tousLesCalculs by database.calculDao().getAllCalculs().collectAsState(initial = emptyList())
    val anneeActuelle = Calendar.getInstance().get(Calendar.YEAR)
    val caAnnuel = CalculsCharges.calculerCAnnuel(tousLesCalculs, anneeActuelle)
    val pourcentageSeuil = CalculsCharges.pourcentageSeuil(caAnnuel, typeActivite)
    val montantRestant = CalculsCharges.montantRestantAvantSeuil(caAnnuel, typeActivite)

    // Fonction de sauvegarde
    fun sauvegarderCalcul() {
        if (ca > 0) {
            val calendar = Calendar.getInstance()
            val moisFormat = SimpleDateFormat("MMMM yyyy", Locale.FRENCH)

            val calcul = CalculEntity(
                date = System.currentTimeMillis(),
                chiffreAffaires = ca,
                typeActivite = typeActivite,
                cotisations = cotisations,
                revenuNet = revenuNet,
                mois = moisFormat.format(calendar.time),
                annee = calendar.get(Calendar.YEAR)
            )

            scope.launch {
                database.calculDao().insertCalcul(calcul)
                snackbarHostState.showSnackbar("Calcul sauvegardé !")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calcul Charges ME") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    TextButton(onClick = onNavigateToHistory) {
                        Text("Historique")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Sélection du type d'activité
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = activites.find { it.first == typeActivite }?.second ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Type d'activité") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    activites.forEach { (key, label) ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = {
                                typeActivite = key
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Champ du CA
            OutlinedTextField(
                value = chiffreAffaires,
                onValueChange = { chiffreAffaires = it },
                label = { Text("Chiffre d'affaires (€)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            // Toggle ACRE
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "J'ai l'ACRE",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Taux réduits 1ère année",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                Switch(
                    checked = avecACRE,
                    onCheckedChange = { avecACRE = it }
                )
            }

            // Carte d alerte de seuil
            if (caAnnuel > 0) {
                AlerteSeuilCard(
                    caAnnuel = caAnnuel,
                    pourcentage = pourcentageSeuil,
                    montantRestant = montantRestant,
                    typeActivite = typeActivite
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Carte des résultats
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Résultats",
                        style = MaterialTheme.typography.titleLarge
                    )

                    HorizontalDivider()

                    ResultRow("Cotisations sociales", cotisations)
                    ResultRow("Revenu net", revenuNet)

                    // Taux affiché
                    val taux = when(typeActivite) {
                        "vente" -> "12,8%"
                        else -> "22%"
                    }
                    Text(
                        text = "Taux appliqué : $taux",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    )
                }
            }

            // Bouton Sauvegarder
            Button(
                onClick = { sauvegarderCalcul() },
                modifier = Modifier.fillMaxWidth(),
                enabled = ca > 0
            ) {
                Text("Sauvegarder ce calcul")
            }

            // Bouton À propos
            TextButton(
                onClick = onNavigateToAbout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("À propos et mentions légales")
            }
        }
    }
}

@Composable
fun ResultRow(label: String, montant: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "%.2f €".format(montant),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AlerteSeuilCard(
    caAnnuel: Double,
    pourcentage: Double,
    montantRestant: Double,
    typeActivite: String
) {
    val seuil = when(typeActivite) {
        "vente" -> 188700.0
        else -> 77700.0
    }

    // Couleur selon le pourcentage
    val couleurCarte = when {
        pourcentage >= 100 -> MaterialTheme.colorScheme.errorContainer
        pourcentage >= 80 -> Color(0xFFFFF4E6) // Orange clair
        else -> Color(0xFFE8F5E9) // Vert clair
    }

    val couleurTexte = when {
        pourcentage >= 100 -> MaterialTheme.colorScheme.onErrorContainer
        pourcentage >= 80 -> Color(0xFFE65100) // Orange foncé
        else -> Color(0xFF2E7D32) // Vert foncé
    }

    val messageAlerte = when {
        pourcentage >= 100 -> "⚠️ Seuil dépassé !"
        pourcentage >= 80 -> "⚠️ Attention : proche du seuil"
        else -> "✅ Dans les limites"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = couleurCarte)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = messageAlerte,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = couleurTexte
            )

            Text(
                text = "CA annuel 2026 : %.2f €".format(caAnnuel),
                style = MaterialTheme.typography.bodyMedium
            )

            // Barre de progression
            LinearProgressIndicator(
                progress = { (pourcentage / 100).toFloat().coerceAtMost(1f) },
                modifier = Modifier.fillMaxWidth(),
                color = couleurTexte,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "%.1f%% du seuil".format(pourcentage),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Seuil : %.0f €".format(seuil),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (montantRestant > 0) {
                Text(
                    text = "Reste : %.2f €".format(montantRestant),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = couleurTexte
                )
            }
        }
    }
}