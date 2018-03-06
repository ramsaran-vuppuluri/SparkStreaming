import StreamingConstants.{ReadStreamFormat, WriteStreamFormat}
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

object ConsoleStream extends App {
  val spark = LoadSpark(appName = "ConsoleStream").getSparkSession

  spark.read.csv("")

  //streamFromSocket

  val schema: StructType = StructType(Seq(StructField("DEST_COUNTRY_NAME", StringType, true), StructField("ORIGIN_COUNTRY_NAME", StringType, true), StructField("count", LongType, true)))

  val activityDataStream = spark.readStream.format(ReadStreamFormat.JSON.getFormat).schema(schema).option("maxFilesPerTrigger", 1).load("/Users/saha/Documents/flight-data/json/")

  activityDataStream.writeStream.format(WriteStreamFormat.CONSOLE.getFormat).outputMode(OutputMode.Append).trigger(Trigger.ProcessingTime(5000)).queryName("consoleStream").start.awaitTermination(60000)

  private def streamFromSocket = {
    import spark.implicits._

    val readLine = spark.readStream.format(ReadStreamFormat.SOCKET.getFormat).option("host", "localhost").option("port", 9999).load.as[String]

    // nc -lk 9999 should be up by now.

    val wordCount = readLine.flatMap(_.split(" ")).groupBy("value").count

    wordCount.writeStream.format(WriteStreamFormat.CONSOLE.getFormat).outputMode(OutputMode.Complete).start.awaitTermination(150000)
  }
}