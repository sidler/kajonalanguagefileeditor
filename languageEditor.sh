#!/bin/bash
dir=$(cd -P -- "$(dirname -- "$0")" && pwd -P )
cd "$dir"
java -jar ./dist/KajonaLanguageEditorGui-2.0-SNAPSHOT.jar
