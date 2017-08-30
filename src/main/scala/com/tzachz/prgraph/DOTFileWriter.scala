package com.tzachz.prgraph

import java.nio.file.{Files, Path, StandardOpenOption}

/**
  * Created by tzachz on 8/30/17
  */
class DOTFileWriter(depFile: Path) {

  def writeFile[T](edges: Seq[T], getSource: T => String, getDest: T => String): Unit = {
    println(s"Generating graph file $depFile")

    overwriteFile("digraph {\n")
    // connections:
    edges.foreach(e => appendLine(s""""${getSource(e)}" -> "${getDest(e)}";"""))
    // labels and graphing instructions for PRs:
    edges.foreach(e => appendLine(s""""${getDest(e)}" [shape=polygon,sides=4,skew=0,label="$e"];"""))
    appendLine("}")
  }

  private def overwriteFile(text: String) {
    Files.write(depFile, text.getBytes, StandardOpenOption.CREATE)
    Files.write(depFile, text.getBytes, StandardOpenOption.TRUNCATE_EXISTING)
  }

  private def appendLine(text: String) = Files.write(depFile, (text + "\n").getBytes, StandardOpenOption.APPEND, StandardOpenOption.CREATE)

}
