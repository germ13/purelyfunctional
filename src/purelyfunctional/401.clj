(ns purelyfunctional.401)

;; create a (count coll) x (count coll) matrix with all possible pair sums from collection
;; restrict upper right matrix, this will omit commutative pair sums
;; omit diagonal to omit reflexive sums.
;; filter only pairs that sum to target and sort
(defn sums-of-pairs [coll target]
  (for [x (map-indexed vector coll)
        y (map-indexed vector coll)
        :when (and (< (first x) (first y)) 
                   (= (+ (second x) (second y)) target))] 
   (vec (sort [(second x) (second y)]))))

(sums-of-pairs [1 3 2 3 4 5] 7)