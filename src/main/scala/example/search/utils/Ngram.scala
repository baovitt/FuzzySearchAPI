package baovitt.search.utils

// CREDIT TO: https://github.com/dav009/FuzzyDict (with alterations)

object Ngram:
  def apply(string: String): List[String] = 
    (s"__${string}__").toCharArray().sliding(3).map(_.mkString("")).toList
end Ngram