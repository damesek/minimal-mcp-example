#!/usr/bin/env bash
set -euo pipefail

# Ensure target directory exists
mkdir -p target

echo "→ Building uberjar: minimal Clojure MCP example"

clojure -T:build uber

echo "✔ Built Minimal Clojure MCP example"

