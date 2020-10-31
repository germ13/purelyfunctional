(ns purelyfunctional.399)

(defn digitize [n]
  (map #(- (int %) 48)
       (seq (str n))))

(defn digit-search [digits]
  (loop [coll digits
         accumulator #{}]
    (if (empty? coll)
      nil
      (if
       (clojure.set/subset? #{0 1 2 3 4 5 6 7 8 9}
                            (clojure.set/union (set (digitize (first coll)))
                                               accumulator))
        (first coll)
        (recur (rest coll)
               (clojure.set/union
                (set (digitize (first coll)))))))))
