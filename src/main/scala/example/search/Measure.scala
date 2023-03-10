package baovitt.search

// CREDIT TO: https://github.com/dav009/FuzzyDict (with alterations)

abstract class Measure:
  def t(threshold: Double, sizeOfA: Int, sizeOfB:Int): Int
  def min(threshold: Double, sizeOfA: Int): Int
  def max(threshold: Double, sizeOfA: Int): Int
end Measure


object Cosine extends Measure:
  override def t(threshold: Double, sizeOfFeaturesA: Int, sizeOfFeaturesB: Int): Int=
    Math.ceil(threshold * Math.sqrt(sizeOfFeaturesA * sizeOfFeaturesB)).toInt

  override def max(threshold: Double, sizeOfFeaturesA: Int): Int=
    Math.floor(sizeOfFeaturesA/(threshold * threshold)).toInt

  override def min(threshold: Double, sizeOfFeaturesA: Int): Int=
    Math.ceil(threshold * threshold * sizeOfFeaturesA).toInt
end Cosine