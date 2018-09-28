#!/bin/bash

yarn build
mvn compile
mvn dependency:copy-dependencies
git add --all
git commit -m "DEPLOY v$(cat package.json | grep version | cut -d '"' -f 4) $(date "+DATE: %Y-%m-%d / TIME: %H:%M:%S")"
git push heroku master
heroku open