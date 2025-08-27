#!/bin/zsh

./gradlew clean
./gradlew buildPlugin
rm -rf "$HOME/.config/idea/bunny-intellij-plugin-*.zip"
cp -f "$HOME"/Bunny/intellij-plugins/bunny-intellij-plugin/build/distributions/*.zip "$HOME/.config/idea/"