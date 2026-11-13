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

```
.\gradlew.bat build -Pmc=1.21.11
.\gradlew.bat build -Pmc=26.2
```

JAR: `build/libs/`. Noob's Container Searcher скачивается с Modrinth автоматически (только для компиляции),
его версия задаётся в `gradle.properties` (`ncs_version`). Иконка мода - `src/main/resources/assets/ncs_block/icon.png`.

## Лицензия

Проект распространяется по лицензии [MIT](https://github.com/K2zik/NCS-block/blob/main/LICENSE).
