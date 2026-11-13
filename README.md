# NCS Block

**English** | [Русский](README.ru.md)

A client-side Fabric addon for **Noob's Container Searcher** that blocks the librarian reroll feature.
Everything else in NCS (search, highlights, container changes) works as usual.

## Installation

Put the jar for your Minecraft version into `mods` next to NCS. The game will not start without NCS (it is listed in `depends`).

## What it does

- The "Reroll" tab in the NCS menu (`G`) is dimmed and has a padlock before its name. Hovering it shows
  a tooltip saying the feature is disabled (`assets/ncs_block/lang/*.json`).
  Clicking the tab does nothing, and the screen can never switch to it (`switchTab` is cancelled).
- `ServerRules.rerollDisabled()` always returns `true`, as if the server had installed the `disable-librarian-reroll` datapack.
- The reroll itself never runs, even if it is reached some other way: clicks on the villager or lectern spot
  are not captured, the reroll `tick()` does not run, and a started selection is reset.

## Building

The NCS jar for the same Minecraft version is needed in `libs/ncs-<mc>.jar` (compile only). Build it from the NCS sources:

```
cd ..\Noobs-Container-Searcher-master
.\gradlew.bat build -Pmc=1.21.11 -PnoBump
copy build\libs\noobs-container-searcher.jar ..\NCS-Block\libs\ncs-1.21.11.jar
```

Then:

```
.\gradlew.bat build -Pmc=1.21.11
.\gradlew.bat build -Pmc=26.2
```

Jars end up in `build/libs/`. The mod icon is `src/main/resources/assets/ncs_block/icon.png`.

## License

This project is licensed under the [MIT License](https://github.com/K2zik/NCS-block/blob/main/LICENSE).
