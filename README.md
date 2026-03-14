# Hora, Idioma e Tela | Java

Mini app em Java Swing que exibe informacoes do sistema em uma interface grafica moderna.

## O que o projeto mostra

- Data e hora atual usando a API moderna `java.time` (atualiza em tempo real)
- Idioma e pais padrao do sistema operacional
- Resolucao da tela (via Toolkit e DisplayMode)
- Versao do sistema operacional e da JVM
- Interface grafica com botao de atualizacao manual
- Tema claro/escuro com alternancia por botao
- Icone customizado na janela
- Organizacao em abas (`Visao geral`, `Tela` e `Sobre`)

## Requisitos

- Windows, Linux ou macOS
- JDK 21 ou superior

## Instalar JDK no Windows (winget)

```powershell
winget install -e --id EclipseAdoptium.Temurin.21.JDK --accept-package-agreements --accept-source-agreements
```

Depois, feche e abra o terminal novamente e valide:

```powershell
java -version
javac -version
```

Se `javac` nao for reconhecido, adicione o `bin` do JDK ao `PATH`.
Exemplo comum:

```text
C:\Program Files\Eclipse Adoptium\jdk-21.x.x-hotspot\bin
```

## Como executar

```powershell
javac -d . HoraDoSistema.java
java com.mycompany.horadosistema.HoraDoSistema
```

## Como usar a aplicacao

- Abra a janela `Hora do Sistema`
- Veja os dados do sistema sendo atualizados automaticamente
- Clique em `Atualizar agora` para atualizar manualmente
- Use `Tema escuro` / `Tema claro` para alternar o visual
- Navegue pelas abas para separar informacoes gerais, de tela e descricao do app

## Exemplo de informacoes exibidas

```text
Data e hora: sexta-feira, 13 de marco de 2026 14:25:42
Idioma: portugues
Pais: Brasil
Resolucao (toolkit): 1920x1080
Resolucao (display): 1920x1080
Sistema operacional: Windows 11 10.0
Java: 21.0.10
```
