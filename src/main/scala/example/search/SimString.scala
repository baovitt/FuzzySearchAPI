package baovitt.search

// CREDIT TO: https://github.com/dav009/FuzzyDict (with alterations)

import baovitt.search.utils.Ngram

class SimString(words: Vector[String]):

    private val wordsToIndex = words.zipWithIndex.toMap

    private val ngramSizeWordTriples = words.map(Ngram(_)).zipWithIndex.flatMap {
        case (ngrams: List[String], wordIndex: Int) =>
            ngrams.map{
                ngram =>
                (ngram, ngrams.size, wordIndex )
            }
    }
    
    private val lookupTable = ngramSizeWordTriples.map { triple =>
        (triple._1 + "_" + triple._2, triple._3)
    }.groupBy(_._1).mapValues(_.map(_._2).toSet)

    private def getMatches(size:Int, ngram:String)={
        lookupTable.get(ngram+"_"+size)
    }

    private def overlapJoin(features: List[String], minOverlap: Int, sizeOfQuery: Int) =

        val candidates = features.map(getMatches(sizeOfQuery, _)).flatten.sortBy(_.size)

        val candidatesCounts = 
            candidates.slice(0, features.size - minOverlap + 1).map(_.toList).flatten

        val extraCounts = candidates.slice(features.size - minOverlap + 1, features.size).flatMap { currentMatches =>
            candidatesCounts.toSet.toList.flatMap{
            word =>
                if (currentMatches.contains(word))
                Some(word)
                else
                None
            }
        }

        (candidatesCounts ++ extraCounts).groupBy(i => i).map(t => (t._1, t._2.length) ).toMap
            .filter{ t => minOverlap <= t._2 }.keySet

    def search(query: String, threshold: Double, measure: Measure) =
        val features = Ngram(query)

        val matchesSize = Range(measure.min(threshold, features.size), measure.max(threshold, features.size) + 1)
        val matches = matchesSize.flatMap { l =>
            overlapJoin(features, measure.t(threshold, features.toSet.size, l), l)
        }.toSet

        matches.map(this.words(_))
            

end SimString

object SimString:
    def apply(words: List[String]) = new SimString(words.toVector)
end SimString