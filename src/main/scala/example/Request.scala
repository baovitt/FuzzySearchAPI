package baovitt

import zio.json.*

case class FuzzyMatchRequest(map: Map[String, String], query: String, threshold: Double)

object FuzzyMatchRequest:
  given JsonEncoder[FuzzyMatchRequest] =
    DeriveJsonEncoder.gen[FuzzyMatchRequest]
  given JsonDecoder[FuzzyMatchRequest] =
    DeriveJsonDecoder.gen[FuzzyMatchRequest]
end FuzzyMatchRequest