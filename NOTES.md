# Publish note

1. Merge branch to main
2. Set version to not SNAPSHOT and set a tag
```shell
git add pom.xml
git commit -m "1.0.2 version"
git tag 1.0.2
```

1. Publish and push commits avec le profil pushish-central et le settings-nist4j.xml
```shell
mvn -Ppublish-central clean deploy
git push
git push origin 1.0.2
```

1. Aller sur [https://central.sonatype.com/publishing/deployments]() et valider la publication
2. Preparer la prochaine version set 1.0.1-SNAPSHOT to pom.xml
```shell
git add pom.xml
git commit -m "prepare 1.0.3 version"
git push
```
