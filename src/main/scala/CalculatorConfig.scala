import java.io.FileInputStream
import java.util.Properties
import scala.math.BigDecimal.RoundingMode
import scala.jdk.CollectionConverters.*
import scala.util.Using

case class CalculatorConfig(rateScale: Int, roundingMode: RoundingMode.Value,  rateRanges: Map[BigDecimal, BigDecimal]) {

}

object CalculatorConfig {
  private val PRECISION_PARAM = "precision"
  private val ROUNDING_MODE_PARAM = "roundingMode"
  private val RANGE_PREFIX = "range."

  private def parseRateScale(properties: Map[String, String]): Int = {
    properties.get(PRECISION_PARAM) match {
      case Some(value) => value.toIntOption match {
        case Some(number) => number
        case None => throw new RuntimeException(s"${PRECISION_PARAM} is not an integer number: ${value}")
      }
      case None => throw new RuntimeException(s"${PRECISION_PARAM} is not provided")
    }
  }

  private def parseRoundingMode(properties: Map[String, String]): RoundingMode.Value = {
    properties.get(ROUNDING_MODE_PARAM) match {
      case Some(value) => value.toUpperCase match {
        case "CEILING" => RoundingMode.CEILING
        case "UP" => RoundingMode.UP
        case "DOWN" => RoundingMode.DOWN
        case "FLOOR" => RoundingMode.FLOOR
        case "HALF_UP" => RoundingMode.HALF_UP
        case "HALF_DOWN" => RoundingMode.HALF_DOWN
        case "HALF_EVEN" => RoundingMode.HALF_EVEN
        case "UNNECESSARY" => RoundingMode.UNNECESSARY
        case _ => throw new RuntimeException(s"Unknown Rounding Mode provided: ${value}")
      }
      case None => throw new RuntimeException(s"${ROUNDING_MODE_PARAM} is not provided")
    }
  }

  private def parseRateRanges(properties: Map[String, String]): Map[BigDecimal, BigDecimal] = {
    properties
      .filter((key, _) => key.startsWith(RANGE_PREFIX))
      .map((key, value) => (BigDecimal(key.replace(RANGE_PREFIX, "")), BigDecimal(value)))
  }

  def loadConfig(): CalculatorConfig = {
    val propsMap: Map[String, String] = Using(new FileInputStream("configuration.properties")) { stream =>
      val properties = new Properties()
      properties.load(stream)
      properties.asScala.toMap
    }.get
    val rateScale = parseRateScale(propsMap)
    val roundingMode = parseRoundingMode(propsMap)
    val rateRanges = parseRateRanges(propsMap)
    CalculatorConfig(rateScale = rateScale, roundingMode = roundingMode, rateRanges = rateRanges)
  }
}
