# iLO3 Remote Console — standalone (Windows .exe)

Standalone launcher for the **HP iLO3 Integrated Remote Console (KVM)**. The iLO3
Java applet is turned into a standalone Windows application with a simple login
form — **no browser, no Java Web Start, no Java installation required**.

Based on [`LossyDragon/ILO3-Standalone-Remote-Console`](https://github.com/LossyDragon/ILO3-Standalone-Remote-Console)
(with fixes: bundled image/keyboard-hook resources, TLS 1.1, GUI login form, `.exe` packaging).

## Для чего / Purpose
- Подключение к KVM-консоли сервера HP iLO3 без браузера и Java-апплета.
- Форма входа: **адрес сервера и порт**, **логин**, **пароль**.
- Запуск как обычного Windows-приложения (`.exe`).

## Требования / Requirements
- **Java устанавливать НЕ обязательно.** В готовом `.exe` встроен JRE (JDK 17).
- Windows x64.
- Для сборки из исходников: **JDK 17** + Gradle (или Gradle wrapper).

## Порты / Ports
| Порт | Назначение | По умолчанию |
|---|---|---|
| **Web / SSL (HTTPS)** | вход/логин iLO (поле «адрес:порт») | **443** (в тесте — 444) |
| **Console (rc_port)** | KVM-консоль | **17990** |
| **Virtual media (vm_port)** | виртуальные носители | **17988** |

- Поле адреса принимает `host` или `host:port` (например `192.168.0.24:444`).
  Если порт не указан — **443**.
- Порты консоли/медиа iLO3 сообщает сам (`rc_port`/`vm_port`), обычно 17990/17988.
- Телнет-порт 23 у iLO3 **не используется**.

## Использование / Usage
1. Запустите **`iLO3RemoteConsole.exe`** (без аргументов → форма входа).
2. Введите **Адрес** (`host:port`), **Логин**, **Пароль** → **«Подключиться»**.
3. Откроется окно KVM-консоли.
- Альтернативно (CLI): `iLO3RemoteConsole.exe <host> <user> <pass>` — автологин.

## Сборка / Build
```bat
gradlew build          :: или gradle build (нужен JDK 17)
gradlew fatJar         :: собирает build/libs/ILO3RemCon-fat.jar (со всеми зависимостями)
jpackage --type app-image --name iLO3RemoteConsole ^
  --input dist_app --main-jar ILO3RemCon-fat.jar --main-class MainKt ^
  --java-options "-Djava.security.properties=java.security" ^
  --java-options "-Dhttps.protocols=TLSv1.1" --dest dist_out
```

## Важно / Security
- iLO3 использует **TLS 1.1**; приложение ослабляет `java.security` и **отключает
  проверку сертификата** (trust-all). Используйте только в доверенной сети.
- Не коммитьте `config.properties` (в .gitignore).

## Credits
- Основано на `LossyDragon/ILO3-Standalone-Remote-Console`.
- HP iLO3 Integrated Remote Console © Hewlett-Packard. Код «как есть» для пользователей iLO3.
