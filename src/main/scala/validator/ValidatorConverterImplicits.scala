package validator

import scala.language.implicitConversions

object ValidatorConverterImplicits {
  implicit def toBoolean(validator: Validator): Boolean = validator.toBoolean
}
