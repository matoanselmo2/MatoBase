# 📚 MatoBase - Documentação do Plugin
[English Version](https://github.com/matoanselmo2/MatoBase/blob/master/readme-en.md)

## 📌 Visão Geral
O **MatoBase** é um sistema modular para desenvolvimento de plugins Bukkit/Paper, oferecendo:

- ✅ Sistema avançado de comandos com argumentos tipados
- ✅ Gerenciamento de permissões integrado
- ✅ Mensagens configuráveis via YAML
- ✅ Placeholders dinâmicos
- ✅ Subcomandos hierárquicos

## 🚀 Começando

### Pré-requisitos
- Java 8+
- Bukkit/Paper 1.12.2 (Outras versões podem requerir ajustes)
- Maven/Gradle (para compilação)

### Mensagens (`messages.yml`)
```yaml
reload-message: "&aConfiguração recarregada com sucesso!"
no-permission: "&cVocê não tem permissão para executar este comando!"

command:
  no-description: "&cNenhuma descrição fornecida para este comando."
  insufficient-args: "&cArgumentos insuficientes! &7(Esperados: %expected%, Recebidos: %received%) \n&cUso: &f%usage%"
  invalid-arg: "&cArgumento inválido na posição %position%: &f%argument% &7(Esperado: %expected%) \n&cUso: &f%usage%"

argument:
  invalid-player: "&cO jogador %name% não existe ou está offline!"
  invalid-integer: "&cO valor %value% não é um número inteiro válido!"
  invalid-boolean: "&cO valor %value% não é um booleano válido!"
```

## 💻 Sistema de Comandos

### Estrutura Básica

```java
import me.mato.plugin.util.Permissions;

// Comando normal (sem sub-comandos)
public class MeuComando extends NormalCommand {
    public MeuComando() {
        super("nome", Permissions.<PERMISSION>, Argument.STRING, Argument.INTEGER);
    }

    @Override
    protected void execute(CommandSender sender, Object[] args) {
        // Lógica do comando
    }
}

// Comando com sub-comandos
public class MeuComandoParente extends ParentCommand {
    public MeuComandoParente() {
        super("nome", Permissions.<PERMISSION>, Argument.STRING, Argument.INTEGER);
        registerSubCommand(new MeuSubComando()); // Sub-comandos extendem NormalCommand
    }

    @Override
    protected void execute(CommandSender sender, Object[] args) {
        // Lógica do comando
    }
}
```

### Registrando Comandos
```java
public class CommandManager {
    // ...

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
        
        registerCommand(new MeuComando());
        registerCommand(new MeuComandoParente());
    }

    // ...
}
```

### Tipos de Argumentos Suportados
| Tipo         | Descrição                     | Exemplo        |
|--------------|-------------------------------|----------------|
| `PLAYER`     | Jogador online                | `Notch`        |
| `INTEGER`    | Número inteiro                | `42`           |
| `BOOLEAN`    | Verdadeiro/Falso              | `true`         |
| `STRING`     | Texto                         | `Olá mundo!`   |

___

### Acessando o ConfigHelper
```java
ConfigHelper config = MatoBase.getInstance().getConfigHelper();
String msg = config.getMessage("path.mensagem", sender, "placeholder", valor);
```

## 🧩 Placeholders Customizados

### Registrando um Placeholder
```java
placeholder.register("online", sender -> String.valueOf(Bukkit.getOnlinePlayers().size()));
```

### Usando Placeholders
```yaml
mensagem-exemplo: "&aJogadores online: %online%"
```

## 🔄 Comandos Disponíveis

| Comando               | Descrição                     | Permissão          |
|-----------------------|-------------------------------|--------------------|
| `/matobase reload`    | Recarrega as configurações    | `matobase.admin`  |
| `/matobase test`      | Comando de teste              | `matobase.admin`    |

___

## 📝 Créditos
Desenvolvido por [Mato (Mateus Anselmo)] - [GitHub](https://github.com/matoanselmo2)

## 📜 Licença
MIT License - Veja [LICENSE.md](LICENSE.md) para detalhes
