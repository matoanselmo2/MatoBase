# 📚 MatoBase - Plugin Documentation

## 📌 Overview
**MatoBase** is a modular system for Bukkit/Paper plugin development, offering:

- ✅ Advanced command system with typed arguments
- ✅ Built-in permission management
- ✅ Configurable messages via YAML
- ✅ Dynamic placeholders
- ✅ Hierarchical subcommands

## 🚀 Getting Started

### Prerequisites
- Java 8+
- Bukkit/Paper 1.12.2 (Other versions may require adjustments)
- Maven/Gradle (for compilation)

### Messages (`messages.yml`)
```yaml  
reload-message: "&aConfiguration reloaded successfully!"  
no-permission: "&cYou don't have permission to execute this command!"  

command:  
  no-description: "&cNo description provided for this command."  
  insufficient-args: "&cInsufficient arguments! &7(Expected: %expected%, Received: %received%) \n&cUsage: &f%usage%"  
  invalid-arg: "&cInvalid argument at position %position%: &f%argument% &7(Expected: %expected%) \n&cUsage: &f%usage%"  

argument:  
  invalid-player: "&cPlayer %name% does not exist or is offline!"  
  invalid-integer: "&cValue %value% is not a valid integer!"  
  invalid-boolean: "&cValue %value% is not a valid boolean!"  
```  

## 💻 Command System

### Basic Structure

```java  
import me.mato.plugin.util.Permissions;  

// Normal command (no subcommands)  
public class MyCommand extends NormalCommand {  
    public MyCommand() {  
        super("name", Permissions.<PERMISSION>, Argument.STRING, Argument.INTEGER);  
    }  

    @Override  
    protected void execute(CommandSender sender, Object[] args) {  
        // Command logic  
    }  
}  

// Command with subcommands  
public class MyParentCommand extends ParentCommand {  
    public MyParentCommand() {  
        super("name", Permissions.<PERMISSION>, Argument.STRING, Argument.INTEGER);  
        registerSubCommand(new MySubCommand()); // Subcommands extend NormalCommand  
    }  

    @Override  
    protected void execute(CommandSender sender, Object[] args) {  
        // Command logic  
    }  
}  
```  

### Registering Commands
```java  
public class CommandManager {  
    // ...  

    public CommandManager(JavaPlugin plugin) {  
        this.plugin = plugin;  
          
        registerCommand(new MyCommand());  
        registerCommand(new MyParentCommand());  
    }  

    // ...  
}  
```  

### Supported Argument Types
| Type         | Description                     | Example        |  
|--------------|---------------------------------|----------------|  
| `PLAYER`     | Online player                  | `Notch`        |  
| `INTEGER`    | Whole number                   | `42`           |  
| `BOOLEAN`    | True/False                     | `true`         |  
| `STRING`     | Text                           | `Hello world!` |  

___  

### Accessing ConfigHelper
```java  
ConfigHelper config = MatoBase.getInstance().getConfigHelper();  
String msg = config.getMessage("path.message", sender, "placeholder", value);  
```  

## 🧩 Custom Placeholders

### Registering a Placeholder
```java  
placeholder.register("online", sender -> String.valueOf(Bukkit.getOnlinePlayers().size()));  
```  

### Using Placeholders
```yaml  
example-message: "&aOnline players: %online%"  
```  

## 🔄 Available Commands

| Command               | Description                     | Permission          |  
|-----------------------|---------------------------------|--------------------|  
| `/matobase reload`    | Reloads configurations          | `matobase.reload`  |  
| `/matobase test`      | Test command                    | `matobase.test`    |  

___  

## 📝 Credits
Developed by [Mato (Mateus Anselmo)] - [GitHub](https://github.com/matoanselmo2)

## 📜 License
MIT License - See [LICENSE.md](LICENSE.md) for details