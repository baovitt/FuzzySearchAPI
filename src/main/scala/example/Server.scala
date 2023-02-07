package baovitt

import baovitt.search.*

import zio.*
import zio.json.*
import zio.http.*
import zio.http.model.Method

object AppServer extends ZIOAppDefault:

  import FuzzyMatchRequest.{given, *}
  import FuzzyMatchResponse.{given, *}

  val app = Http.collectZIO[Request] {
    case req @ (Method.POST -> !! / "fuzzysearch") => 
      val request = req.body.asString.map(_.fromJson[FuzzyMatchRequest]) catchAll {
        case _ => ZIO.succeed(Left("""{"error":"invalid request"}"""))
      }

      request map {
        _ match
          case Right(r: FuzzyMatchRequest) =>
            val fuzzyMap = FuzzyMap(r.map.toList)
            
            Response.text(
              FuzzyMatchResponse(fuzzyMap.get(r.query, r.threshold, Cosine)).toJson
            )
          case _ => Response.text("""{"error":"invalid request"}""")
      }
    case _ => ZIO.succeed(Response.text("404 Error: Endpoint doesn't exist ): \n\n Correct endpoint is /fuzzysearch"))
  }

  override val run = Server.serve(app).provide(Server.default)
end AppServer
