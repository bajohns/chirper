import sbt._

class ChirperStreamerProject(info: ProjectInfo) extends DefaultWebProject(info) with AutoCompilerPlugins
{
  override def useDefaultConfigurations = true

  val httpclient = "commons-httpclient" % "commons-httpclient" % "3.1"
  val logging    = "commons-logging" % "commons-logging" % "1.1"
  val json = "org.json" % "json" % "20080701"
  val scalaj_collection = "org.scalaj" %% "scalaj-collection" % "1.0"
  val configgy   = "net.lag" % "configgy" % "1.5.2" from "http://repo.bumnetworks.com/snapshots/net/lag/configgy/1.5.2/configgy-1.5.2.jar"

  // Logging
  System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
  // System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
  // System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "debug");

  // From FE
  val jettyVersion = "6.1.22"
  val servletVersion = "2.5"
  val slf4jVersion = "1.6.0"
  val scalatraVersion = "2.0.0-SNAPSHOT"
  val scalateVersion = "1.2"
  val scalaTestVersion = "1.2-for-scala-2.8.0.final-SNAPSHOT"

  val jetty6 = "org.mortbay.jetty" % "jetty" % jettyVersion % "test"
  val servletApi = "javax.servlet" % "servlet-api" % servletVersion % "provided"

  val scalaTest = "org.scalatest" % "scalatest" % scalaTestVersion % "test"
  val scalatra = "org.scalatra" %% "scalatra" % scalatraVersion
  val scalate = "org.fusesource.scalate" % "scalate-core" % scalateVersion
  val scalatraScalate = "org.scalatra" %% "scalatra-scalate" % scalatraVersion

  val sfl4japi = "org.slf4j" % "slf4j-api" % slf4jVersion % "runtime"
  val sfl4jnop = "org.slf4j" % "slf4j-nop" % slf4jVersion % "runtime"

  // repositories
  val scalaToolsSnapshots    = "Scala Tools Repository" at "http://nexus.scala-tools.org/content/repositories/snapshots/"
  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val sonatypeNexusReleases  = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"
  val fuseSourceSnapshots    = "FuseSource Snapshot Repository" at "http://repo.fusesource.com/nexus/content/repositories/snapshots"

  // Show unchecked errors when compiling
  override def compileOptions = super.compileOptions ++ Seq(Unchecked)
}