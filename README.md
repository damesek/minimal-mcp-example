# Modex Minimal MCP Example

A **minimal** Model Context Protocol (MCP) plugin for Claude, built with [Modex](https://github.com/theronic/modex). This repository demonstrates how to wrap two simple tools—`hello` and `inc`—in a standalone Clojure server and integrate it into the Claude desktop application.

---

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Project Structure](#project-structure)
3. [Building the Uberjar](#building-the-uberjar)
4. [Running the Server](#running-the-server)
5. [Claude Desktop Configuration](#claude-desktop-configuration)
6. [Troubleshooting](#troubleshooting)
7. [License](#license)

---

## Prerequisites

- **Java 11+**: Required to run the generated JAR.
- **Clojure CLI Tools**: Used by the `build.sh` script to compile and package.
- **Git**: To fetch the Modex dependency from GitHub.

---

## Project Structure

```
├── build.sh                 # Script to assemble the uberjar
├── deps.edn                 # Clojure CLI dependencies configuration
└── src
    └── modex
        └── minimal
            └── core.clj     # Namespace: modex.minimal.core
```

- **`build.sh`**: Uses `hf.depstar` to produce `target/minimal-mcp.jar` with AOT compilation and proper manifest.
- **`deps.edn`**: Declares Clojure, Modex, and other dependencies (including the Git SHA for Modex).
- **`src/modex/minimal/core.clj`**: Defines two tools—`hello` and `inc`—and the `-main` entrypoint that starts the MCP server.

---

## Building the Uberjar

Make the build script executable and run it:

```bash
chmod +x build.sh
./build.sh
```

Upon success, you’ll have:

```
target/minimal-mcp.jar
```

---

## Running the Server

Start the MCP server from the generated JAR:

```bash
java -jar target/minimal-mcp.jar
```

The server will listen on `stdin`/`stdout` for JSON-RPC v2.0 requests (e.g., from Claude).

---

## Claude Desktop Configuration

To wire this plugin into Claude’s desktop app, edit your `config.json` and add:

```json
{
  "mcpServers": {
    "minimal-mcp": {
      "command": "java",
      "args": ["-jar", "/absolute/path/to/target/minimal-mcp.jar"]
    }
  },
  "globalShortcut": ""
}
```

- Replace `/absolute/path/to/target/minimal-mcp.jar` with the actual path on your machine.
- Optionally set a `globalShortcut` for quick activation.

---

## Troubleshooting

- **`ClassNotFoundException`**: Ensure your `src/modex/minimal/core.clj` has:
  ```clojure
  (ns modex.minimal.core
    (:gen-class)
    …)
  ```
  and that `build.sh` specifies `:main-class modex.minimal.core`.

- **Git dependency errors**: In `deps.edn`, pin Modex correctly:
  ```clojure
  theronic/modex {:git/url "https://github.com/theronic/modex.git"
                  :sha     "<commit-sha>"}
  ```

- **Missing manifest entry**: Verify with:
  ```bash
  unzip -p target/minimal-mcp.jar META-INF/MANIFEST.MF
  ```
  You should see:
  ```
  Main-Class: modex.minimal.core
  ```

---

## License

This example is provided under the MIT License. Feel free to adapt and extend it to build your own MCP tools with Modex.

