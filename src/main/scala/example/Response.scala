package baovitt

import zio.json.*

case class FuzzyMatchResponse(response: List[String])

object FuzzyMatchResponse:
  given JsonEncoder[FuzzyMatchResponse] =
    DeriveJsonEncoder.gen[FuzzyMatchResponse]
  given JsonDecoder[FuzzyMatchResponse] =
    DeriveJsonDecoder.gen[FuzzyMatchResponse]
end FuzzyMatchResponse