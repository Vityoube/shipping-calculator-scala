object Main {

  @main
  def main(): Unit = {
    val calculator = Calculator()
    print(calculator.calculate(BigDecimal("9.56")))
  }
}
