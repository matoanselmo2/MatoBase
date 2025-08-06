# 🧩 MatoBase (prod. MatoPlugins)

Uma base moderna, modular e escalável para desenvolvimento de plugins Bukkit/Paper usando Java + HikariCP + Guice (Injeção de Dependência).

---

## 📁 Estrutura do Projeto

```plaintext
me.mato.plugin.base/
├── api/                 # Contratos e modelos compartilhados
│   ├── dao/             # Interfaces de DAO
│   ├── database/        # Interfaces/configs de banco de dados
│   └── model/           # Modelos de dados (ex: PlayerDataModel)
│
├── command/             # Sistema de comandos modulares
│   ├── BaseCommand.java
│   ├── CommandContext.java
│   ├── SubCommand.java
│   └── impl/            # Comandos concretos
│       ├── ExampleCommand.java
│       └── sub/         # Subcomandos
│           └── ExampleSubCommand.java
│
├── core/                # Implementações reais (não expostas via API)
│   ├── gui/             # GUIs customizadas
│   ├── manager/         # Gerenciadores (CommandManager, GuiManager...)
│   ├── registry/        # Registro de comandos, listeners, etc
│   └── service/         # BootstrapService e outros serviços
│
├── database/            # Sistema de banco de dados
│   ├── config/          # Configurações para MySQL/SQLite
│   ├── engine/          # Engines de conexão (usando HikariCP)
│   ├── functional/      # Interfaces funcionais (SQLConsumer etc)
│   ├── DatabaseFactory  # Fábrica para criar a engine correta
│   └── DatabaseType     # Enum de tipos de banco
│
├── di/                  # Injeção de dependência com Guice
│   └── PluginModule.java
│
├── util/                # Utilitários diversos (ex: Permission.java)
│
└── MatoBase.java        # Classe principal do plugin
```

---

## 🚀 Funcionalidades Integradas

* 🔌 **Injeção de dependência com Guice**
* 🛢️ **Conexão com MySQL e SQLite usando HikariCP**
* 🧠 **DAO + Cache local com player data**
* 🧾 **Sistema de Comandos e Subcomandos modular**
* 🖼️ **Gerenciamento dinâmico de GUIs com injeção de dependência**

---

## 🧠 Sistema de Comandos

* Baseado em `BaseCommand` com suporte nativo a `SubCommand`
* Usa `CommandContext` para abstrair argumentos e validação
* Suporte a tab-complete inteligente para subcomandos

### Exemplo:

```java
public class ExampleCommand extends BaseCommand {
    public ExampleCommand() {
        super("example", "Comando de exemplo", Permission.EXEMPLO);
        registerSubCommand(new ExampleSubCommand());
    }

    @Override
    public void execute(CommandContext context) {
        context.sender().sendMessage("Você executou o comando de exemplo!");
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
        context.sender().sendMessage("Subcomando de exemplo executado.");
    }
}
```

### CommandContext simplifica uso de argumentos:

```java
String name = context.arg(0);
Player target = context.playerOrSelf(0);
int amount = context.intArg(1, 10); // com default
```

---

## 🔐 Sistema de Permissões Escalável

* Define permissões usando **enums tipadas com metadados**
* Suporte a:

    * `description` (descrição da permissão)
    * `PermissionDefault` (quem recebe por padrão: OP, TRUE, etc)
    * Permissões **filhas** (`children`)
* Registro automático via **Dependency Injection com Guice**
* Documentação automática gerada em `permissions.md`
* Suporte a permissões dinâmicas com `PermissionBuilder`

---

### ✨ Exemplo: Criando uma enum de permissões

```java
public enum ExamplePermission implements PermissionNode {

    USE(PermissionBuilder.create("matobase.example.use")
        .description("Permite usar a funcionalidade de exemplo")
        .defaultValue(PermissionDefault.TRUE)),

    ADMIN(PermissionBuilder.create("matobase.example.admin")
        .description("Acessa funcionalidades administrativas")
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

### 🧪 Registro via Guice

No seu `PluginModule.java`:

```java
Multibinder<PermissionNode[]> permissionBinder = Multibinder.newSetBinder(binder(), PermissionNode[].class);
permissionBinder.addBinding().toInstance(ExamplePermission.values());
```

> O `PermissionRegistry` vai registrar todas as permissões automaticamente no boot.

---

### 🧾 Geração automática de documentação

Arquivo gerado: `plugins/MatoBase/permissions.md`

```markdown
## Example

- `matobase.example.use` - *TRUE*  
  > Permite usar a funcionalidade de exemplo

- `matobase.example.admin` - *OP*  
  > Acessa funcionalidades administrativas
```

---

### 🧩 Integração com comandos

Todos os comandos usam `PermissionNode` ao invés de `enum Permission` fixa:

```java
public class ExampleCommand extends BaseCommand {
    public ExampleCommand() {
        super("example", "Comando de exemplo", ExamplePermission.USE);
    }

    @Override
    public void execute(CommandContext context) {
        context.sender().sendMessage("Você executou /example!");
    }
}
```

---

## 📦 DAO com cache + query moderna (exemplo de PlayerDAO)

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

## 🧠 Arquitetura com BootstrapService

* Toda lógica de inicialização (criação de tabelas, registro de GUIs, comandos etc) fica centralizada
* Ideal para manutenção e organização de lógica de boot

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

## 📋 Requisitos

* Java 17 ou superior
* Bukkit/Paper API
* Guice
* HikariCP

---

Feito com cafeína por [Mato](https://github.com/matoanselmo2) ☕
