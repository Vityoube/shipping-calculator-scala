class Calculator {
  private val calculatorConfig: CalculatorConfig = CalculatorConfig.loadConfig()

  def calculate(totalWeight: BigDecimal): BigDecimal = {
    val rate = findRate(totalWeight)
    (rate * totalWeight) setScale(calculatorConfig.rateScale, calculatorConfig.roundingMode)
  }

  private def findRate(weight: BigDecimal): BigDecimal = {
     weight match {
      case n if n <= 0  => throw new RuntimeException(s"Weight cannot be negative or zero! ${weight} was provided")
      case n if n > 0 => calculatorConfig.rateRanges
        .filter((range, _) => range < n)
        .maxByOption(_._1)
        .map(_._2) match {
        case Some(value) => value
        case None => throw new RuntimeException(s"No range found for weight: ${n}")
      }
    }
  }

}
