###### Road map
- [X] Support Annotation based integration
- [ ] Support java agent instrument


####### Release process

- Run `./gradlew uploadArchives` to build and upload archives to `oss`
- Login to [nexus oss](https://oss.sonatype.org). 
- Find uploaded artifacts in Staging Repositories.
- `Close` and `Release` staged artifacts.

For more information, refer to [this blog](http://www.sonatype.org/nexus/2015/04/28/how-to-publish-your-open-source-library-to-maven-central/).
