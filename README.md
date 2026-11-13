# NCS Block

Клиентский Fabric-аддон для **Noob's Container Searcher**: запрещает рерол библиотекаря.
Остальной функционал NCS (поиск, подсветка, изменения в сундуках) работает как обычно.

Отдельный jar для каждой версии Minecraft (версия мода - `1.0.0`):
- `ncs-block-1.0.0+mc1.21.11.jar`
- `ncs-block-1.0.0+mc26.2.jar`

## Установка

Положить jar своей версии в `mods` рядом с NCS. Без NCS игра не запустится (NCS указан в `depends`).

## Что делает

- Вкладка «Рерол» в меню NCS (`G`) затемнена, перед названием замок. При наведении -
  подсказка «Этот функционал запрещён на СПм» (`assets/ncs_block/lang/*.json`).
  Клик по вкладке ничего не делает, переключиться на неё нельзя (`switchTab` отменяется).
- `ServerRules.rerollDisabled()` всегда `true` - как будто сервер поставил датапак `disable-librarian-reroll`.
- Сам рерол не запустится, даже если до него как-то дойти: клики по жителю/лектерну не
  перехватываются, `tick()` рерола не выполняется, начатый выбор сбрасывается.

## Сборка

Нужен jar NCS той же версии в `libs/ncs-<mc>.jar` (только для компиляции). Собрать его из исходников NCS:

```
cd ..\Noobs-Container-Searcher-master
.\gradlew.bat build -Pmc=1.21.11 -PnoBump
copy build\libs\noobs-container-searcher.jar ..\NCS-Block\libs\ncs-1.21.11.jar
```

Затем:

```
.\gradlew.bat build -Pmc=1.21.11
.\gradlew.bat build -Pmc=26.2
```

JAR: `build/libs/`. Иконка мода - `icon.png` в корне проекта (при сборке кладётся в `assets/ncs_block/`).
