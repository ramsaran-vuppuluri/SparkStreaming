import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

final case class LoadSpark(master: String = "local", appName: String) {
  val sparkConf = new SparkConf().setMaster(master).setAppName(appName)

  def getSparkSession: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate
}