# Publish note

1. Merge branch to main
2. Set version to not SNAPSHOT and set a tag
```shell
git add pom.xml
git commit -m "0.6.0 version"
git tag 0.6.0
```
3. Publish and push commits avec le profil pushish-central et le settings-nist4j.xml
```shell
mvn -Ppublish-central clean deploy
git push
git push origin 0.6.0
```
4. Aller sur [https://central.sonatype.com/publishing/deployments]() et valider la publication
5. Preparer la prochaine version set 0.6.1-SNAPSHOT to pom.xml
```shell
git add pom.xml
git commit -m "prepare 0.6.1 version"
git push
```