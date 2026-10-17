# Changelog

All notable changes to **OBSIDIA TOOLS** will be documented in this file.

## [1.0.1] - 2026-10-17

### Fixed
- **GitHub Actions CI/CD Stability**: Configured `gradle/actions/setup-gradle@v4` on the Ubuntu runner and isolated workflow triggers to version tags (`v*`) and manual dispatch to completely stop continuous email spam on routine commits.
- **CI Keystore Provisioning**: Automated keystore generation in GitHub Actions workflow so release APK assembly never fails on missing keystores.
- **Dynamic Repository URLs**: Removed hardcoded username assumptions; added dynamic repository path input in the app to prevent 404 links across different GitHub accounts.
- **Transparent Public Distribution**: Clarified private versus public repository download permissions and verified artifact integrity.

## [1.0.0] - 2026-10-16

### Added
- **Initial Official Release**: Full Android native implementation using Jetpack Compose and Material 3.
- **Premium Dark Obsidian Aesthetic**: Charcoal obsidian backgrounds, crimson red accents, and metallic chrome borders based on the reference design.
- **Adaptive App Launcher Icon**: Custom obsidian hexagonal emblem with crimson crystal core across all Android mipmap densities.
- **Home Dashboard**: Real-time device specifications, quick CPU/RAM/Battery metrics, and one-tap Quick Optimize.
- **Graphics Control**:
  - 4 quick presets: Battery Saver, Balanced, Performance, Custom.
  - Comprehensive options: Graphics Quality, Resolution Scale, Frame Rate Target (30-120 FPS), Texture, Shadow, Anti-Aliasing.
  - Strict non-root hardware validation and before/after configuration state tracking.
- **Real-Time Monitor**:
  - Circular dual gauges for CPU & RAM.
  - Battery thermistor readings and Choreographer display frame rate cadence.
  - Historical CPU usage wave graph.
  - Transparent data source labeling.
- **Game Management & Launcher**:
  - Detection and filtering of installed games and popular mobile esports titles.
  - Dedicated Game Launcher screen with hero artwork banner, direct play trigger, and optimization checklist.
- **Settings & Utilities**:
  - Language toggle between Bahasa Indonesia and English.
  - Safe application cache clearing.
  - DPI and screen resolution guide.
  - Android Private DNS setup intent guide.
  - Non-root security and privacy disclosures.
  - Integrated GitHub Releases dialog with SHA-256 checksum verification.
