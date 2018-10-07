#!/bin/bash

fail() {
	echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
	echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
	echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
	echo "!!!!"
	echo "!!!!   DEPLOY FAILED "
	echo "!!!!"
	echo "!!!!   b:$*    !!!!!!!!! "
	echo "!!!!"
	exit 1
}

# Verify tests
CI=1 yarn test  || fail "yarn test"
mvn test  || fail "mvn test"

# Compile
yarn build  || fail "yarn build"
mvn compile  || fail "mvn compile"
mvn dependency:copy-dependencies  || fail "mvn dependency:copy-dependencies"

# Prepare deploy folder
rm -fr ./deploy
mkdir ./deploy
mv ./build ./deploy
mv ./target ./deploy

# Commit and deploy
git add --all || fail "git add --all"
git commit -m "DEPLOY v$(cat package.json | grep version | cut -d '"' -f 4) $(date "+DATE: %Y-%m-%d / TIME: %H:%M:%S")" || fail "git commit"
git push heroku master || fail "git push heroku master"

# All ok.
heroku open
echo "OK."
