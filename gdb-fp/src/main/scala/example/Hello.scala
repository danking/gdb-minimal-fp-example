package example

import scala.io.Source
import com.intel.genomicsdb.GenomicsDBFeatureReader
import htsjdk.variant.vcf.VCFHeader
import scala.collection.JavaConversions._
import scala.collection.JavaConverters.asScalaIteratorConverter

object Hello extends App {
  private def doubleToBitString(d: Double): String =
    java.lang.Long.toBinaryString(java.lang.Double.doubleToRawLongBits(d))

  val gdbReader = new GenomicsDBFeatureReader(
    "/private/tmp/foo/loader-config.json",
    "/private/tmp/foo/query.json",
    new htsjdk.variant.vcf.VCFCodec())

  val rows = gdbReader
    .iterator
    .asScala
    .toIndexedSeq

  val af = rows(1).getAttributes().get("AF")
  println("Class of the AF field in the Attributes map: " + af.getClass().getName())
  val afAsDouble = af.asInstanceOf[String].toDouble
  println("af.toDouble, then printed as a String by Scala: " + afAsDouble)

  println("")
  println("the line in question:")
  val vcflines = Source.fromFile("/private/tmp/foo/jack2.vcf").getLines.toList
  val line = vcflines(27)
  println(line)
  val start = line.indexOf("AF=") + 3
  val end = line.indexOf(";", start)
  val afOriginalString = line.substring(start,end)
  println(s"original numeric text: ${afOriginalString}")
  val afOriginalAsDouble = afOriginalString.toDouble
  println(s"original numeric text parsed by Scala, then printed as a String by Scala: ${afOriginalAsDouble}")
  println("")
  println(s"fromGDB == fromVCF?  ${afAsDouble == afOriginalAsDouble}")
  println(s"fromGDB binary: ${doubleToBitString(afAsDouble)}")
  println(s"fromVCF binary: ${doubleToBitString(afOriginalAsDouble)}")
}
