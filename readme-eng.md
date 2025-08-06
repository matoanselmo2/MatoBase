# 🧩 MatoBase (by MatoPlugins)

A modern, modular, and scalable foundation for developing Bukkit/Paper plugins using Java + HikariCP + Guice (Dependency Injection).

---

## 📁 Project Structure

```plaintext
me.mato.plugin.base/
├── api/                 # Shared contracts and models
│   ├── dao/             # DAO interfaces
│   ├── database/        # Database configs/interfaces
│   └── model/           # Data models (e.g., PlayerDataModel)
│
├── command/             # Modular command system
│   ├── BaseCommand.java
│   ├── CommandContext.java
│   ├── SubCommand.java
│   └── impl/            # Concrete commands
│       ├── ExampleCommand.java
│       └── sub/         # Subcommands
│           └── ExampleSubCommand.java
│
├── core/                # Internal implementations (not exposed via API)
│   ├── gui/             # Custom GUIs
│   ├── manager/         # Managers (CommandManager, GuiManager, etc.)
│   ├── registry/        # Command/listener registration
│   └── service/         # BootstrapService and other services
│
├── database/            # Database system
│   ├── config/          # MySQL/SQLite configuration
│   ├── engine/          # Connection engines (HikariCP-based)
│   ├── functional/      # Functional interfaces (SQLConsumer, etc.)
│   ├── DatabaseFactory  # Factory to create correct engine
│   └── DatabaseType     # Enum of database types
│
├── di/                  # Dependency injection with Guice
│   └── PluginModule.java
│
├── util/                # Various utilities (e.g., Permission.java)
│
└── MatoBase.java        # Main plugin class
```

---

## 🚀 Built-in Features

* 🔌 **Dependency injection powered by Guice**
* 🛢️ **MySQL and SQLite support via HikariCP**
* 🧠 **DAO + local cache for player data**
* 🧾 **Modular command and subcommand system**
* 🖼️ **Dynamic GUI management with DI support**

---

## 🧠 Command System

* Built around a `BaseCommand` class with native `SubCommand` support
* Uses `CommandContext` to abstract arguments and validation
* Intelligent tab-completion support for subcommands

### Example:

```java
public class ExampleCommand extends BaseCommand {
    public ExampleCommand() {
        super("example", "Example command", Permission.EXAMPLE);
        registerSubCommand(new ExampleSubCommand());
    }

    @Override
    public void execute(CommandContext context) {
        context.sender().sendMessage("You executed the example command!");
    }
}
```

```java
public class ExampleSubCommand implements SubCommand {
    @Override
    public String name() {
        return "sub";
    }

    @Override
    public void execute(CommandContext context) {
        context.sender().sendMessage("Example subcommand executed.");
    }
}
```

### CommandContext makes argument parsing easier:

```java
String name = context.arg(0);
Player target = context.playerOrSelf(0);
int amount = context.intArg(1, 10); // with default value
```

---

## 🔐 Scalable Permission System

* Define permissions using **typed enums with metadata**
* Supports:

    * `description` (permission description)
    * `PermissionDefault` (who gets it by default: OP, TRUE, etc.)
    * **Children** permissions
* Auto-registration via **Guice Dependency Injection**
* Auto-generated documentation in `permissions.md`
* Support for dynamic permissions using `PermissionBuilder`

---

### ✨ Example: Creating a permissions enum

```java
public enum ExamplePermission implements PermissionNode {

    USE(PermissionBuilder.create("matobase.example.use")
        .description("Allows using the example feature")
        .defaultValue(PermissionDefault.TRUE)),

    ADMIN(PermissionBuilder.create("matobase.example.admin")
        .description("Accesses admin features")
        .defaultValue(PermissionDefault.OP)
        .child("matobase.example.admin.use", true));

    private final PermissionBuilder builder;

    ExamplePermission(PermissionBuilder builder) {
        this.builder = builder;
    }

    @Override public String getPermission() { return builder.getPermission(); }
    @Override public String getDescription() { return builder.getDescription(); }
    @Override public PermissionDefault getDefaultValue() { return builder.getDefaultValue(); }

    public void register() {
        builder.register();
    }
}
```

---

### 🧪 Registration via Guice

Inside your `PluginModule.java`:

```java
Multibinder<PermissionNode[]> permissionBinder = Multibinder.newSetBinder(binder(), PermissionNode[].class);
permissionBinder.addBinding().toInstance(ExamplePermission.values());
```

> `PermissionRegistry` will automatically register all permissions during boot.

---

### 🧾 Auto-generated documentation

Generated file: `plugins/MatoBase/permissions.md`

```markdown
## Example

- `matobase.example.use` - *TRUE*  
  > Allows using the example feature

- `matobase.example.admin` - *OP*  
  > Accesses admin features
```

---

### 🧩 Seamless command integration

All commands use `PermissionNode` instead of hardcoded `enum Permission`:

```java
public class ExampleCommand extends BaseCommand {
    public ExampleCommand() {
        super("example", "Example command", ExamplePermission.USE);
    }

    @Override
    public void execute(CommandContext context) {
        context.sender().sendMessage("You executed /example!");
    }
}
```

---

## 📦 DAO with cache + modern queries (PlayerDAO example)

```java
public class PlayerDAO {
    private final AbstractDatabaseEngine engine;
    private final Map<UUID, PlayerDataModel> playerCache = new HashMap<>();

    public void loadPlayerCache() {
        String sql = "SELECT uuid, name, level FROM players";

        engine.executeQuery(sql, stmt -> {}, rs -> {
            playerCache.clear();
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String name = rs.getString("name");
                int level = rs.getInt("level");
                playerCache.put(uuid, new PlayerDataModel(uuid, name, level));
            }
        });
    }
}
```

---

## 🧠 Architecture with BootstrapService

* All startup logic (table creation, GUI/command registration, etc.) is centralized
* Ideal for maintenance and organizing initialization logic

```java
@Override
public void onEnable() {
    this.injector = Guice.createInjector(new PluginModule(this));
    this.bootstrap = injector.getInstance(BootstrapService.class);
    
    bootstrap.initialize();
}

@Override
public void onDisable() {
    bootstrap.shutdown();
}
```

---

## 📋 Requirements

* Java 17 or higher
* Bukkit/Paper API
* Guice
* HikariCP

---

Made with caffeine by [Mato](https://github.com/matoanselmo2) ☕