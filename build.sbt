packAutoSettings

name := "slick-game-archetype"

version := "1.0"

scalaVersion := "2.11.4"

mainClass in (Compile, run) := Some("Example")

javaOptions in run += "-Djava.library.path=lib/natives"

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

libraryDependencies ++= Seq(
  "org.lwjgl.lwjgl" % "lwjgl" % "2.9.2",
  "org.lwjgl.lwjgl" % "lwjgl-platform" % "2.9.2" classifier "natives-linux",
  "org.slick2d" % "slick2d-core" % "1.0.1",
  "org.scala-lang" % "scala-reflect" % "2.11.4" % "test",
  "org.scala-lang" % "scala-compiler" % "2.11.4" % "test",
  "org.specs2" %% "specs2-core" % "2.4.13" % "test",
  "org.specs2" %% "specs2-mock" % "2.4.13" % "test"
)

unmanagedBase := baseDirectory.value / "lib"

fork in run := true

packageOptions in (Compile, packageBin) += 
    Package.ManifestAttributes( java.util.jar.Attributes.Name.CLASS_PATH -> "" )

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

excludeFilter in libraryDependencies in Runtime := "scala-library.2.11.4.jar"
