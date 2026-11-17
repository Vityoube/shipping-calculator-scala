scalaVersion := "3.9.0"

lazy val root = rootProject
  .settings(
    name := "shipping-calculator-scala",
    libraryDependencies ++= Seq(
      //You can add library dependencies here, for example,
      //"org.scalatest" %% "scalatest" % "3.2.19" % Test,
      //"org.scalameta" %% "munit" % "1.2.3" % Test
    )
  )

addSbtPlugin("nl.gn0s1s" % "sbt-dotenv" % "3.4.0")
