# 🔶 Hykkit (HytaleServer Core)

**Hykkit** — это неофициальный форк **HytaleServer**, предназначенный для запуска и расширения серверной части игры **Hytale**.

Форк позволяет запустить сервер Hytale и зайти на него с любого лаунчера (например, Hykkit Launcher ☺️)

---

## Donate

<p align="center">
  <a href="https://www.donationalerts.com/r/floerka">
    <img src="https://i.imgur.com/G70vkGO.png" width="400" alt="Оплатить мне кофе сегодня" />
  </a>
</p>

## Last Updates

- Все изменения сервера перенесены в hykkit.yml [(ДОКУМЕНТАЦИЯ)](https://github.com/FLOERKA/Hykkit-Core/blob/code/hykkit-config-info.md)
- Включено изменение Online-Mode в hykkit.yml
- Включено изменение Player-Collisions в hykkit.yml
- Включена возможность изменить сообщения в messages.yml
- Исправлена ошибка всех остальных offline-форков
- Добавлен API для удобства разработки модов
- Исправлене ошибок захода игроков с авторизацией на пиратку

---

## On Future

- Изменение скинов прямо в игре

## API

Ядро предоставляет удобный API для разработки модов на сервер
### Hykkit
Класс Hykkit предоставляет собой набор статик методов для работы с основными функциями сервера:

Пример набора методов:
```java
Optional<Universe> universe = Hykkit.getUniverse();
HykkitPlayer hykkitPlayer = Hykkit.getHykkitPlayer();
PlayerRef playerRef = Hykkit.getPlayer();
List<PlayerRef> allPlayers = Hykkit.getAllPlayers();
World world = Hykkit.getWorld("name");

void kickAll();
void registerListener(JavaPlugin plugin, Object listener);

registerListener(plugin, new PlayerListener());

```

### HykkitPlayer
Класс HykkitPlayer предоставляет собой набор методов для работы с игроками:

Пример набора методов:
```java
void damage(Damage.Source source, DamageCause cause, float amount);
void damage(float amount);
void setGameMode(GameMode gameMode);
void executeCommand(String command);
Optional<World> getWorld();
Optional<Vector3d> getLocation();
Optional<Location> getHykkitLoc();
teleport(Vector3d vector3d);
boolean isFly();
setFly(boolean enable);
List<PlayerRef> getNearbyPlayers(int radius);

```

### HykkitContext
Класс предоставляет собой утилиту для удобства работы с CommandBuffer и HykkitPlayer
Для изменения параметров игрока внутри работающего потока нужно использовать CommandBuffer
```java
HykkitPlayer player;
CommandBuffer buffer;
HykkitContext.enter(buffer);
player.teleport(newLocation);
HykkitContext.exit;
```
---

> Hykkit **не является официальным продуктом Hypixel Studios**  
> Проект создан исключительно в исследовательских и образовательных целях.

---

## 📬 Контакты

Фидбек и возможности улучшения всегда приветствуются у меня в личке: 
Telegram/VK: `@unitcoder`


