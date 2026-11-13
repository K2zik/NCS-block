# NCS Block

[English](README.md) | **Русский**

Клиентский Fabric-аддон для **Noob's Container Searcher**: запрещает рерол библиотекаря.
Остальной функционал NCS (поиск, подсветка, изменения в сундуках) работает как обычно.

## Установка

Положить jar своей версии в `mods` рядом с NCS. Без NCS игра не запустится (NCS указан в `depends`).

## Что делает

- Вкладка «Рерол» в меню NCS (`G`) затемнена, перед названием замок. При наведении -
  подсказка о том, что функционал запрещён (`assets/ncs_block/lang/*.json`).
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

JAR: `build/libs/`. Иконка мода - `src/main/resources/assets/ncs_block/icon.png`.

## Лицензия

Проект распространяется по лицензии [MIT](https://github.com/K2zik/NCS-block/blob/main/LICENSE).
