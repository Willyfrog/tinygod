addSbtPlugin("com.github.philcali" % "sbt-lwjgl-plugin" % "3.1.3")

resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.0.0")

resolvers += Resolver.url("sbt-plugin-releases", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.7.4")

addSbtPlugin("com.github.retronym" % "sbt-onejar" % "0.7")

addSbtPlugin("de.djini" % "xsbt-webstart" % "0.0.4")
