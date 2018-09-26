package validator

object ValidatorConverterImplicits {
  implicit def toBoolean(validator: Validator): Boolean = validator.toBoolean
}
