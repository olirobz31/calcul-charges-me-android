# 💰 Calcul Charges ME

Application Android pour calculer les cotisations sociales des micro-entrepreneurs français.

[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Language](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)
[![MinSDK](https://img.shields.io/badge/MinSDK-26-orange.svg)](https://developer.android.com/about/versions/oreo)
[![License](https://img.shields.io/badge/License-Proprietary-red.svg)]()

## 📱 Description

**Calcul Charges ME** est une application Android native qui permet aux micro-entrepreneurs et auto-entrepreneurs français de calculer instantanément leurs cotisations sociales URSSAF.

L'application offre une interface simple et intuitive pour :
- Calculer les cotisations sociales en temps réel
- Suivre l'évolution du chiffre d'affaires annuel
- Recevoir des alertes de seuils automatiques
- Conserver un historique complet des calculs

**🔗 Site web :** [https://olirobz31.github.io/calcul-charges-me-website/](https://olirobz31.github.io/calcul-charges-me-website/)

## ✨ Fonctionnalités

### Calculateur intelligent
- ⚡ **Calculs en temps réel** - Résultats instantanés pendant la saisie
- 🎯 **3 types d'activités** - Vente (12,8%), Prestations BNC (22%), Libérales (22%)
- 🎁 **Support ACRE** - Taux réduits automatiques pour la 1ère année
- 💶 **Affichage détaillé** - Cotisations, revenu net et taux appliqué

### Alertes de seuils
- 📊 **Surveillance automatique** - Calcul du CA annuel cumulé
- 🟢 **Alertes visuelles** - Vert / Orange / Rouge selon l'avancement
- 📈 **Barre de progression** - Pourcentage et montant restant
- ⚠️ **Seuils 2024** - 77 700€ (services) et 188 700€ (vente)

### Gestion des données
- 💾 **Historique illimité** - Sauvegarde locale de tous les calculs
- 📅 **Organisation par date** - Tri chronologique automatique
- 🗑️ **Suppression flexible** - Individuelle ou totale
- 🔒 **100% local** - Aucune donnée envoyée sur Internet

### Conformité légale
- ⚖️ **Mentions légales** - CGU complètes intégrées
- 🔐 **RGPD conforme** - Aucune collecte de données
- ⚠️ **Disclaimer** - Avertissements clairs sur l'utilisation

## 🛠️ Stack Technique

### Langage & Framework
- **Kotlin** - 100% Kotlin, langage moderne et sûr
- **Jetpack Compose** - UI déclarative et réactive
- **Material Design 3** - Design system Google moderne

### Architecture
- **MVVM** - Separation of Concerns
- **Room Database** - Persistence locale SQLite
- **Kotlin Coroutines** - Gestion asynchrone
- **Flow** - Reactive data streams

### Librairies principales
```gradle
// UI
androidx.compose.ui
androidx.compose.material3

// Base de données
androidx.room:room-runtime:2.6.1
androidx.room:room-ktx:2.6.1
com.google.devtools.ksp:2.0.21-1.0.27

// Navigation
androidx.lifecycle:lifecycle-runtime-ktx
```

## 📂 Structure du projet

```
com.docsmart.microcalc/
├── data/
│   ├── CalculEntity.kt      # Entité Room pour les calculs
│   ├── CalculDao.kt          # DAO - opérations base de données
│   └── AppDatabase.kt        # Configuration Room
├── ui/
│   ├── screens/
│   │   ├── HomeScreen.kt     # Écran principal avec calculateur
│   │   ├── HistoryScreen.kt  # Historique des calculs
│   │   └── AboutScreen.kt    # Mentions légales
│   └── theme/
│       ├── Color.kt          # Palette de couleurs
│       ├── Theme.kt          # Configuration du thème
│       └── Type.kt           # Typographie
├── utils/
│   └── CalculsCharges.kt     # Logique de calcul des cotisations
└── MainActivity.kt           # Point d'entrée et navigation
```

## 🎨 Captures d'écran

### Écran principal
Interface intuitive avec sélection du type d'activité, saisie du CA, toggle ACRE et affichage des résultats en temps réel.

### Alertes de seuils
Carte visuelle avec code couleur (vert/orange/rouge) indiquant la progression vers les seuils légaux de CA.

### Historique
Liste complète des calculs sauvegardés avec possibilité de suppression individuelle ou totale.

### Mentions légales
Écran dédié avec politique de confidentialité, CGU et disclaimer complets.

## 📊 Calculs

### Taux de cotisations 2024
| Type d'activité | Taux | Avec ACRE (1ère année) |
|----------------|------|------------------------|
| Vente de marchandises | 12,8% | 6,4% |
| Prestations de services BNC | 22% | 11% |
| Professions libérales | 22% | 11% |

### Seuils de CA 2024
| Type d'activité | Seuil annuel |
|----------------|--------------|
| Prestations de services | 77 700 € |
| Vente de marchandises | 188 700 € |

### Formules
```kotlin
cotisations = chiffreAffaires × taux
revenuNet = chiffreAffaires - cotisations
tauxACRE = tauxNormal / 2
pourcentageSeuil = (caAnnuel / seuil) × 100
```

## 🔒 Confidentialité & Sécurité

- ✅ **Zéro collecte de données** - Aucune information envoyée à des serveurs
- ✅ **Stockage 100% local** - Base de données SQLite sur l'appareil
- ✅ **Pas de permissions sensibles** - Aucune permission dangereuse demandée
- ✅ **Pas de tracking** - Aucun analytics ou SDK tiers
- ✅ **Conforme RGPD** - Protection des données par défaut

## 🚀 Installation

### Prérequis
- Android Studio Hedgehog (2023.1.1) ou supérieur
- JDK 17
- Android SDK 34
- Kotlin 2.0.21

### Cloner le projet
```bash
git clone https://github.com/olirobz31/calcul-charges-me-android.git
cd calcul-charges-me-android
```

### Ouvrir dans Android Studio
1. Ouvrir Android Studio
2. File → Open
3. Sélectionner le dossier du projet
4. Attendre la synchronisation Gradle

### Lancer l'application
1. Connecter un appareil Android ou lancer un émulateur
2. Cliquer sur Run ▶️
3. L'application se lance sur l'appareil

## 🏗️ Build

### Debug
```bash
./gradlew assembleDebug
```

### Release
```bash
./gradlew bundleRelease
```

Le fichier `.aab` sera généré dans `app/release/`

## 📱 Configuration minimale

- **Android** : 8.0 (Oreo) - API 26
- **RAM** : 2 GB minimum
- **Stockage** : 50 MB
- **Internet** : Non requis (fonctionne 100% hors ligne)

## 🗺️ Roadmap

### Version 1.0 (Actuelle)
- [x] Calculateur de base avec 3 types d'activités
- [x] Support ACRE
- [x] Alertes de seuils
- [x] Historique avec sauvegarde locale
- [x] Mentions légales complètes

### Version 1.1 (Prochaine)
- [ ] Versement libératoire de l'impôt
- [ ] Export PDF des calculs
- [ ] Graphiques d'évolution du CA
- [ ] Widget Android
- [ ] Mode sombre

### Version 1.2 (Future)
- [ ] Estimation CFE
- [ ] Support multi-activités
- [ ] Notifications de rappel de déclaration
- [ ] Sauvegarde cloud optionnelle

## 🤝 Contribution

Ce projet est actuellement en développement privé. Les contributions ne sont pas acceptées pour le moment.

## 📄 Licence

Copyright © 2026 DocSmart. Tous droits réservés.

Ce code est propriétaire et ne peut être utilisé, copié, modifié ou distribué sans autorisation explicite.

## 👨‍💻 Auteur

**DocSmart**
- GitHub: [@olirobz31](https://github.com/olirobz31)
- Site web: [https://olirobz31.github.io/calcul-charges-me-website/](https://olirobz31.github.io/calcul-charges-me-website/)

## 📞 Support

Pour toute question, bug ou suggestion :
- Ouvrir une issue sur GitHub
- Contacter via le Google Play Store (bientôt disponible)

## ⚠️ Disclaimer

**Calcul Charges ME est un outil d'estimation à but informatif uniquement.**

Les calculs fournis sont indicatifs et basés sur les taux officiels URSSAF en vigueur. Ils ne constituent en aucun cas un conseil juridique, fiscal ou comptable.

Il est de la responsabilité de l'utilisateur de vérifier les informations auprès de l'URSSAF et d'un expert-comptable avant toute déclaration officielle.

L'éditeur ne peut être tenu responsable des erreurs, omissions ou conséquences de l'utilisation des résultats fournis.

## 🔗 Liens utiles

- [URSSAF - Site officiel](https://www.urssaf.fr)
- [Service-Public.fr - Micro-entrepreneur](https://www.service-public.fr/professionnels-entreprises/vosdroits/F23267)
- [Documentation Android](https://developer.android.com/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)

---

**Développé avec ❤️ pour les micro-entrepreneurs français**