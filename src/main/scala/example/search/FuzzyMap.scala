package baovitt.search

// CREDIT TO: https://github.com/dav009/FuzzyDict

class FuzzyMap[T](tuples: List[(String, T)]) extends Traversable[(String, T)]:

  private val hashMap = tuples.toMap

  private def search(query: String, threshold: Double, measure: Measure) =
    SimString(tuples.map(_._1)).search(query, threshold, measure).toList
  
  def getMatches(query: String, threshold: Double, measure: Measure): List[(String, T)] =
    search(query, threshold, measure).map { matchedString => 
      (matchedString, hashMap.get(matchedString).get)
    }

  def get(query: String, threshold: Double, measure: Measure): List[T] =
    search(query, threshold, measure).flatMap(hashMap.get(_))

  override def foreach[U](f: ((String, T)) => U) = hashMap.foreach(f)
  override def iterator: Iterator[(String, T)] = tuples.toIterator
end FuzzyMap

object FuzzyMap:
  def apply[T](tuples: List[(String, T)]) = new FuzzyMap(tuples)
end FuzzyMap