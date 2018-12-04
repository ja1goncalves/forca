enablePlugins(ScalaJSPlugin)

name := "forca"
scalaVersion := "2.12.3" // or any other Scala version >= 2.10.2

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true